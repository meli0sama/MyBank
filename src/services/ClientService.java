package services;

import models.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private static final String FICHIERCLIENT = "clients.txt";

    // Sauvegarder un client dans le fichier (ajout)
    public static void saveClient(String nom, String prenom, String typeClient, double salaire, int age) throws IOException {
        int id = getNextId(); // ID unique
        Client client = new Client(id, nom, prenom, typeClient, salaire, age);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHIERCLIENT, true))) {
            bw.write(client.toString());
            bw.newLine();
        }
    }


    public static int getNextId() throws IOException {
        int maxId = 0;
        for (Client c : listClient()) {
            if (c.getIdClient() > maxId) {
                maxId = c.getIdClient();
            }
        }
        return maxId + 1;
    }


    // Lire tous les clients
    public static List<Client> listClient() throws IOException {
        List<Client> clients = new ArrayList<>();
        File file = new File(FICHIERCLIENT);
        if (!file.exists()) return clients;

        try (BufferedReader br = new BufferedReader(new FileReader(FICHIERCLIENT))) {
            String line;
            while ((line = br.readLine()) != null) {
                Client c = Client.fromString(line);
                if (c != null) clients.add(c);
            }
        }
        return clients;
    }

    public static Client getClientById(int idRecherche) throws IOException {
        for (Client c : ClientService.listClient()) {
            if (c.getIdClient() == idRecherche)
                return c;
        }
        return null;
    }
}
