package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class GestoreClienti extends Thread {

    private Socket client;
    private GestoreBiglietti biglietti;

    private static Set<String> utenti = new HashSet<>();

    public GestoreClienti(Socket client, GestoreBiglietti biglietti) {
        this.client = client;
        this.biglietti = biglietti;
    }

    @Override
    public void run() {

        String username = null;
        boolean acquisto = false;

        try (
                Socket socket = client;
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(
                        socket.getOutputStream(), true)) {

            out.println("WELCOME");

            String messaggio;

            while ((messaggio = in.readLine()) != null) {

                messaggio = messaggio.trim();

                if (messaggio.equalsIgnoreCase("QUIT")) {
                    out.println("BYE");
                    break;
                }

                if (username == null) {

                    String[] parti = messaggio.split("\\s+");

                    if (parti[0].equalsIgnoreCase("LOGIN")) {

                        if (parti.length != 2) {
                            out.println("ERR SYNTAX");
                            continue;
                        }

                        synchronized (utenti) {

                            if (utenti.contains(parti[1])) {
                                out.println("ERR USERINUSE");
                            } else {
                                username = parti[1];
                                utenti.add(username);
                                out.println("OK");
                            }
                        }

                    } else {
                        out.println("ERR LOGINREQUIRED");
                    }

                    continue;
                }

                if (acquisto) {

                    acquisto = false;

                    String[] parti = messaggio.split("\\s+");

                    if (parti.length != 2) {
                        out.println("ERR SYNTAX");
                        continue;
                    }

                    try {
                        int quantita = Integer.parseInt(parti[1]);

                        String risultato = biglietti.acquista(parti[0], quantita);

                        out.println(risultato);

                    } catch (NumberFormatException e) {
                        out.println("ERR SYNTAX");
                    }

                    continue;
                }

                if (messaggio.equalsIgnoreCase("N")) {

                    out.println(biglietti.getDisponibilita());

                } else if (messaggio.equalsIgnoreCase("BUY")) {

                    acquisto = true;

                } else {

                    out.println("ERR UNKNOWNCMD");
                }
            }

        } catch (IOException e) {
            System.out.println("Errore client: " + e.getMessage());

        } finally {

            if (username != null) {
                synchronized (utenti) {
                    utenti.remove(username);
                }
            }

            System.out.println("Client disconnesso");
        }
    }
}
