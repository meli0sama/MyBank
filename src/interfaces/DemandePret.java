package interfaces;

import models.Client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;

public class DemandePret extends JFrame {

    private JTextField tfNumeroCompte, tfMontant, tfDuree;
    private JTextArea taResultat;

    public DemandePret(Client client) {
        setTitle("💸 Demande de Prêt");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Numéro de Compte :"));
        tfNumeroCompte = new JTextField();
        panel.add(tfNumeroCompte);

        panel.add(new JLabel("Montant demandé :"));
        tfMontant = new JTextField();
        panel.add(tfMontant);

        panel.add(new JLabel("Durée (mois) :"));
        tfDuree = new JTextField();
        panel.add(tfDuree);

        JButton btnSimuler = new JButton("Simuler / Valider");
        btnSimuler.addActionListener(e -> simulerPret());
        panel.add(btnSimuler);

        taResultat = new JTextArea();
        taResultat.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taResultat);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void simulerPret() {
        String numCompte = tfNumeroCompte.getText().trim();
        double montant = Double.parseDouble(tfMontant.getText().trim());
        int duree = Integer.parseInt(tfDuree.getText().trim());

        try {
            String[] compte = trouverCompte(numCompte);
            if (compte == null) {
                taResultat.setText("❌ Compte introuvable.");
                return;
            }

            String idClient = compte[1];
            double solde = Double.parseDouble(compte[3]);

            Client client = chercherClient(idClient);
            if (client == null) {
                taResultat.setText("❌ Client introuvable.");
                return;
            }

            double taux, mensualite;
            String type = client.getType();
            boolean eligible = false;

            if (type.equalsIgnoreCase("Salarié")) {
                if (duree <= 120 && (client.getAge() + duree / 12) < 60) {
                    taux = 0.07;
                    mensualite = (montant * (1 + taux)) / duree;
                    eligible = mensualite <= (client.getSalaire() / 3);
                } else {
                    taResultat.setText("⚠️ Durée trop longue ou âge limite dépassé.");
                    return;
                }
            } else {
                if (duree < 3 || duree > 24) {
                    taResultat.setText("⚠️ Durée non autorisée pour ce type de prêt.");
                    return;
                }
                taux = type.equalsIgnoreCase("Particulier") ? 0.05 : 0.06;
                double montantMax = solde * 3;
                if (montant > montantMax) {
                    taResultat.setText("❌ Montant dépasse 3x solde : max " + montantMax);
                    return;
                }
                mensualite = (montant * (1 + taux)) / duree;
                eligible = true;
            }

            if (eligible) {
                taResultat.setText("✅ Prêt accepté !\n");
                taResultat.append("Montant : " + montant + " FCFA\n");
                taResultat.append("Durée : " + duree + " mois\n");
                taResultat.append("Mensualité : " + (int) mensualite + " FCFA\n");
                enregistrerPret(numCompte, montant, duree, mensualite);
            } else {
                taResultat.setText("❌ Non éligible : mensualité trop élevée.");
            }

        } catch (Exception e) {
            taResultat.setText("Erreur : " + e.getMessage());
        }
    }

    private String[] trouverCompte(String num) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("comptes.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts[0].equals(num)) {
                br.close();
                return parts;
            }
        }
        br.close();
        return null;
    }

    private Client chercherClient(String id) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("clients.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            Client c = Client.fromString(line);
            if (c.getId() == Integer.parseInt(id)) {
                br.close();
                return c;
            }
        }
        br.close();
        return null;
    }

    private void enregistrerPret(String numCompte, double montant, int duree, double mensualite) throws IOException {
        FileWriter fw = new FileWriter("prets.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(numCompte + "|" + montant + "|" + duree + "|" + (int) mensualite + "|" + LocalDate.now() + "\n");
        bw.close();
    }
}
