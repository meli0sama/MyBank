package models;

public class Pret {
    private final int idClient;
    private final String typeClient;
    private final double montant;
    private final int dureeMois;
    private final double tauxInteret;
    private final double mensualite;
    private double montantRestant;

    public Pret(int idClient, String typeClient, double montant, int dureeMois, double tauxInteret, double mensualite, double montantRestant) {
        this.idClient = idClient;
        this.typeClient = typeClient;
        this.montant = montant;
        this.dureeMois = dureeMois;
        this.tauxInteret = tauxInteret;
        this.mensualite = mensualite;
        this.montantRestant = montantRestant;
    }

    public static Pret fromString(String ligne) {
        String[] parts = ligne.split("\\|");
        if (parts.length < 8) return null;

        return new Pret(
                Integer.parseInt(parts[0]), // idClient
                parts[1],                   // typeClient
                Double.parseDouble(parts[2]),
                Integer.parseInt(parts[3]),
                Double.parseDouble(parts[4]),
                Double.parseDouble(parts[5]),
                Double.parseDouble(parts[6])
        );
    }

    public String toString() {
        return idClient + "|" + typeClient + "|" + montant + "|" + dureeMois + "|" + tauxInteret + "|" + mensualite + "|" + montantRestant;
    }


    public int getIdClient() {
        return idClient;
    }

    public String getTypeClient() {
        return typeClient;
    }

    public double getMontant() {
        return montant;
    }

    public int getDureeMois() {
        return dureeMois;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public double getMensualite() {
        return mensualite;
    }

    public double getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(double montantRestant) {
        this.montantRestant = montantRestant;
    }
}
