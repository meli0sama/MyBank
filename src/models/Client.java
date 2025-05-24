package models;

public class Client {
    private static int compteurId = 1; // compteur pour générer ID auto-incrémenté

    private int id;
    private String nomPrenom;
    private String type; // Particulier, Salarié, Société
    private double salaire;
    private int age;

    public Client(String nomPrenom, String type, double salaire, int age) {
        this.id = compteurId++;
        this.nomPrenom = nomPrenom;
        this.type = type;
        this.salaire = salaire;
        this.age = age;
    }

    // Getters & setters
    public int getId() { return id; }
    public String getNomPrenom() { return nomPrenom; }
    public void setNomPrenom(String nomPrenom) { this.nomPrenom = nomPrenom; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getSalaire() { return salaire; }
    public void setSalaire(double salaire) { this.salaire = salaire; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        // Format pour fichier : id|nomPrenom|type|salaire|age
        return id + "|" + nomPrenom + "|" + type + "|" + salaire + "|" + age;
    }

    // Parse un Client depuis une ligne fichier
    public static Client fromString(String line) {
        String[] parts = line.split("\\|");
        if(parts.length < 5) return null;
        Client c = new Client(parts[1], parts[2], Double.parseDouble(parts[3]), Integer.parseInt(parts[4]));
        c.id = Integer.parseInt(parts[0]);
        if(c.id >= compteurId) compteurId = c.id + 1;
        return c;
    }
}
