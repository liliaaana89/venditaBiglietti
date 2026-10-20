package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public static void main(String[] args) {

        GestoreBiglietti biglietti = new GestoreBiglietti();

        try {
            ServerSocket server = new ServerSocket(3000);
            System.out.println("Server avviato sulla porta 3000");

            while (true) {

                Socket client = server.accept();

                System.out.println("Nuovo client connesso");

                //aggiungo come oggetto gestoreclienti che estente thread e lo avvia con start()
                GestoreClienti thread = new GestoreClienti(client, biglietti);
                thread.start();;
            }  
         

        } catch (IOException e) {
            System.out.println("Errore del server: " + e.getMessage());
        }
    }
}