package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GestoreClienti extends Thread {

    private Socket client;
    private GestoreBiglietti biglietti;

    public GestoreClienti(Socket client, GestoreBiglietti biglietti) {
        this.client = client;
        this.biglietti = biglietti;
    }

    public void run(){

        try {
            
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            out.println("Welcome");

            String messaggio;
            while ((messaggio = in.readLine()) != null) {
                System.out.println("Client: " + messaggio);

                if (messaggio.equalsIgnoreCase("QUIT")) {
                    out.println("BYE");
                    break;
                } else {
                    out.println("OK");
                }
            }



        } catch (IOException e) {
            System.out.println("Errore client: " + e.getMessage());
        } finally {
            try{
                client.close();
            } catch(IOException e) {
                System.out.println("Errore chiusura socket");
            }
        }
    }   
}
