package models;

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
        this.dateOuverture = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    // Getters et setters
    public int getNumero() { return numero; }
    public int getIdClient() { return idClient; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }
    public String getDateOuverture() { return dateOuverture; }

    @Override
    public String toString() {
        return numero + "|" + idClient + "|" + type + "|" + solde + "|" + dateOuverture;
    }

    public static Compte fromString(String line) {
        String[] parts = line.split("\\|");
        if(parts.length < 5) return null;

        Compte compte = new Compte(
                Integer.parseInt(parts[1]), // idClient
                parts[2],
                Double.parseDouble(parts[3])
        );
        compte.numero = Integer.parseInt(parts[0]);
        compte.dateOuverture = parts[4];

        if(compte.numero >= compteurNumero)
            compteurNumero = compte.numero + 1;

        return compte;
    }
}


