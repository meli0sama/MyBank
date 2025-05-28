package services;

import models.Compte;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CompteService {
    private static final String FICHIERCOMPTES = "comptes.txt";

    public static void saveCompte(Compte compte) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIERCOMPTES, true))) {
            bw.write(compte.toString());
            bw.newLine();
        }
    }

    public static List<Compte> listCompte() throws IOException {
        List<Compte> comptes = new ArrayList<>();
        File file = new File(FICHIERCOMPTES);
        if (!file.exists()) return comptes;

        try (BufferedReader br = new BufferedReader(new FileReader(FICHIERCOMPTES))) {
            String line;
            while ((line = br.readLine()) != null) {
                Compte compte = Compte.fromString(line);
                if (compte != null) comptes.add(compte);
            }
        }
        return comptes;
    }

    public static List<Compte> getComptesByClientId(int clientId) throws IOException {
        List<Compte> comptesClient = new ArrayList<>();
        for (Compte c : listCompte()) {
            if (c.getIdClient() == clientId) {
                comptesClient.add(c);
            }
        }
        return comptesClient;
    }

    public static Compte getCompteByNumero(int numeroCompte) throws IOException {
        for (Compte c : listCompte()) {
            if (c.getNumeroCompte() == numeroCompte) {
                return c;
            }
        }
        return null;
    }

    public static void
    mettreAJourCompte(Compte compte) throws IOException {
        List<Compte> comptes = listCompte();

        for (int i = 0; i < comptes.size(); i++) {
            if (comptes.get(i).getNumeroCompte() == compte.getNumeroCompte()) {
                comptes.set(i, compte);
                break;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIERCOMPTES, false))) {
            for (Compte c : comptes) {
                bw.write(c.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
