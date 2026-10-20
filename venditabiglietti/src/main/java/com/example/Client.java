
package com.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try (
            Socket socket = new Socket("localhost", 3000);

            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );

            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true
            );

            Scanner scanner = new Scanner(System.in);
        ) {

            System.out.println("Connesso al server!");
            System.out.println(in.readLine());

            String comando;

            while (true) {

                System.out.print("Inserisci comando: ");
                comando = scanner.nextLine();

                out.println(comando);

                if (comando.equalsIgnoreCase("BUY")) {

                    System.out.print("Tipo e quantità (es. Gold 2): ");
                    String acquisto = scanner.nextLine();

                    out.println(acquisto);
                }

                String risposta = in.readLine();

                if (risposta == null) {
                    System.out.println("Server disconnesso");
                    break;
                }

                System.out.println("Server: " + risposta);

                if (comando.equalsIgnoreCase("QUIT")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}
