package services;

import models.Client;

import java.io.*;
import java.util.*;

public class ClientDAO {
    private static final String FILENAME = "clients.txt";

    // Sauvegarder un client dans le fichier (ajout)
    public static void saveClient(Client client) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME, true))) {
            bw.write(client.toString());
            bw.newLine();
        }
    }

    // Lire tous les clients
    public static List<Client> getAllClients() throws IOException {
        List<Client> clients = new ArrayList<>();
        File file = new File(FILENAME);
        if(!file.exists()) return clients;

        try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while((line = br.readLine()) != null) {
                Client c = Client.fromString(line);
                if(c != null) clients.add(c);
            }
        }
        return clients;
    }
}
