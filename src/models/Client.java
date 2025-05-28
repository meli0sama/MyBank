package models;

public class Client {
    private static int compteurId = 0; // compteur pour générer ID auto-incrémenté

    private int idClient;
    private String nom;
    private String prenom;
    private String typeClient; // Particulier, Salarié, Société
    private double revenusMensuels;
    private int age;

    public Client(int idClient, String nom, String prenom, String type, double revenusMensuels, int age) {
        this.idClient = compteurId++;
        this.nom = nom;
        this.prenom = prenom;
        this.typeClient = type;
        this.revenusMensuels = revenusMensuels;
        this.age = age;
    }

    public static Client fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 6) return null;
        Client client = new Client(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), Integer.parseInt(parts[5]));
        client.idClient = Integer.parseInt(parts[0]);
        if (client.idClient >= compteurId) compteurId = client.idClient + 1;
        return client;
    }

    // Getters & setters
    public int getIdClient() {
        return idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(String type) {
        this.typeClient = type;
    }

    public double getRevenusMensuels() {
        return revenusMensuels;
    }

    public void setRevenusMensuels(double revenusMensuels) {
        this.revenusMensuels = revenusMensuels;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return idClient + "|" + nom + "|" + prenom + "|" + typeClient + "|" + revenusMensuels + "|" + age;
    }
}