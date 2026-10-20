package com.example;

import java.util.HashMap;
import java.util.Map;

public class GestoreBiglietti {

    private Map<String, Integer> disponibilita = new HashMap<>();

    public GestoreBiglietti () {
        disponibilita.put("Gold", 10);
        disponibilita.put("Pit", 30);
        disponibilita.put("Parterre", 60);
    }

    //Biglietti disponibili
    public synchronized String getDisponibilita(){
        return "AVAIL Gold:" + disponibilita.get("Gold") 
               + " Pit: " + disponibilita.get("Pit")
               + " Parterre: " + disponibilita.get("Parterre");
    }

    //acquista biglietti
    public synchronized String acquista(String tipo, int quantita) {
        if (!disponibilita.containsKey(tipo)) {
            return "ERRORE SCONOSCIUTO"; 
        }

        if (quantita <= 0) {
            return "ERRORE SINTASSI";
        }

        int disponibili = disponibilita.get(tipo);
        if (quantita > disponibili) {
            return "KO";
        }

        disponibilita.put(tipo, disponibili - quantita);

        return "OK";
    }
}
