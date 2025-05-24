package services;

import models.Compte;

import java.io.*;
import java.util.*;

public class CompteDAO {
    private static final String FILENAME = "comptes.txt";

    public static void saveCompte(Compte compte) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true))) {
            bw.write(compte.toString());
            bw.newLine();
        }
    }

    public static List<Compte> getAllComptes() throws IOException {
        List<Compte> comptes = new ArrayList<>();
        File file = new File(FILENAME);
        if(!file.exists()) return comptes;

        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
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
        for(Compte c : getAllComptes()) {
            if(c.getIdClient() == clientId) {
                comptesClient.add(c);
            }
        }
        return comptesClient;
    }
}
