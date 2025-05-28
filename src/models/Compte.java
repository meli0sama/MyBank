package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Compte {
    private static int compteurNumero = 1000;

    private int numero;
    private int idClient;
    private String type; // Courant, Épargne, Société
    private double solde;
    private String dateOuverture;

    public Compte(int idClient, String type, double solde) {
        this.numero = compteurNumero++;
        this.idClient = idClient;
        this.type = type;
        this.solde = solde;
        this.dateOuverture = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    public static void initialiserCompteurDepuisFichier(String fichierComptes) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fichierComptes))) {
        String ligne;
        int maxNumero = 999; // le compteur commencera à 1000
        while ((ligne = reader.readLine()) != null) {
            String[] parts = ligne.split("\\|");
            if (parts.length > 0) {
                int numero = Integer.parseInt(parts[0]);
                if (numero > maxNumero) {
                    maxNumero = numero;
                }
            }
        }
        compteurNumero = maxNumero + 1;
    } catch (IOException e) {
        // Fichier inexistant, on garde compteur à 1000
        compteurNumero = 1000;
    }
}


    public static Compte fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 5) return null;

        Compte compte = new Compte(
                Integer.parseInt(parts[1]), // idClient
                parts[2],                   // type
                Double.parseDouble(parts[3])// solde
        );
        compte.numero = Integer.parseInt(parts[0]);
        compte.dateOuverture = parts[4];

        if (compte.numero >= compteurNumero) {
            compteurNumero = compte.numero + 1;
        }

        return compte;
    }

    // Getters et setters
    public int getNumeroCompte() {
        return numero;
    }

    public void setNumeroCompte(int numero) {
        this.numero = numero;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public String getDateOuverture() {
        return dateOuverture;
    }

    @Override
    public String toString() {
        return numero + "|" + idClient + "|" + type + "|" + solde + "|" + dateOuverture;
    }
}


