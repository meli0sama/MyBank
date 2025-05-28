package services;

import models.Client;
import models.Compte;
import models.Pret;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PretService {
    private static final String FICHIERPRETS = "prets.txt";

    // Enregistrement du prêt dans le fichier
    public static void enregistrerPret(Pret pret) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIERPRETS, true))) {
            bw.write(pret.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Pret> listerPrets() throws IOException {
    List<Pret> prets = new ArrayList<>();
    File fichier = new File("prets.txt");
    if (!fichier.exists()) return prets;

    try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
        String ligne;
        while ((ligne = reader.readLine()) != null) {
            Pret p = Pret.fromString(ligne);
            if (p != null) prets.add(p);
        }
    }
    return prets;
}

// Réécriture complète de prets.txt
public static void reenregistrerTousLesPrets(List<Pret> prets) throws IOException {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("prets.txt"))) {
        for (Pret p : prets) {
            bw.write(p.toString());
            bw.newLine();
        }
    }
}


    // Calcul des mensualités
    public static double calculMensualite(double montant, double taux, int dureeMois) {
        double tauxMensuel = taux / 100 / 12;
        return (montant * tauxMensuel) / (1 - Math.pow(1 + tauxMensuel, -dureeMois));
    }

    // Traite une demande de prêt pour un salarié
    public static String traiterPretSalarie(Client client, Compte compte, double montantDemande, int dureeMois) throws IOException {
        double taux = 7.0;
        double mensualite = calculMensualite(montantDemande, taux, dureeMois);

        if (dureeMois > 120) {
            return "Durée maximale dépassée (10 ans).";
        }

        if ((mensualite > client.getRevenusMensuels() / 3)) {
            return "Mensualité dépasse 1/3 du salaire.";
        }

        if ((client.getAge() + (dureeMois / 12)) > 60) {
            return "Âge à la fin du prêt dépasse la limite de 60 ans.";
        }

        Pret pret = new Pret(client.getIdClient(), client.getTypeClient(), montantDemande, dureeMois, taux, mensualite, montantDemande);
        enregistrerPret(pret);

        compte.setSolde(compte.getSolde() + montantDemande);
        CompteService.mettreAJourCompte(compte);

        return "Prêt accordé avec succès. Mensualité : " + String.format("%.2f", mensualite);
    }

    // Traite un prêt court terme pour particulier ou société
    public static String traiterPretCourtTerme(Client client, Compte compte, double montantDemande, int dureeMois) throws IOException {
        if (dureeMois < 3 || dureeMois > 24) {
            return "Durée invalide pour un prêt court terme.";
        }

        double taux = client.getTypeClient().equalsIgnoreCase("Particulier") ? 5.0 : 6.0;
        double plafond = compte.getSolde() * 3;

        if (montantDemande > plafond) {
            return "Montant demandé dépasse le plafond autorisé (3x solde).";
        }

        double mensualite = calculMensualite(montantDemande, taux, dureeMois);
        Pret pret = new Pret(client.getIdClient(), client.getTypeClient(), montantDemande, dureeMois, taux, mensualite, montantDemande);
        enregistrerPret(pret);

        // Mise à jour du solde du compte
        compte.setSolde(compte.getSolde() + montantDemande);
        CompteService.mettreAJourCompte(compte);

        return "Prêt court terme accordé. Mensualité : " + String.format("%.2f", mensualite);
    }

    // Charger tous les prêts (utile pour suivi, remboursements...)
    public static List<Pret> chargerPrets() {
        List<Pret> liste = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FICHIERPRETS))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                liste.add(Pret.fromString(ligne));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }
}
