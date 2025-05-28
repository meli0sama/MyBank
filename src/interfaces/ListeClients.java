package interfaces;

import models.Client;
import models.Pret;
import services.ClientService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListeClients extends JFrame {
    private DefaultTableModel model;
    private JTable table;

    public ListeClients() {
        setTitle("👥 Liste des clients");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] colonnes = {"ID", "Nom", "Prénom", "Type", "Salaire", "Âge"};
        model = new DefaultTableModel(colonnes, 0);
        chargerDonnees();

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JPanel boutonPanel = new JPanel();
        JButton btnSupprimer = new JButton("🗑️ Supprimer client");
        JButton btnDetail = new JButton("🔍 Voir détails");

        boutonPanel.add(btnDetail);
        boutonPanel.add(btnSupprimer);
        add(boutonPanel, BorderLayout.SOUTH);

        btnSupprimer.addActionListener(e -> supprimerClient());
        btnDetail.addActionListener(e -> voirDetailClient());

        setVisible(true);
    }

    private void chargerDonnees() {
        model.setRowCount(0);
        try {
            List<Client> clients = ClientService.listClient();
            for (Client c : clients) {
                model.addRow(new Object[]{
                        c.getIdClient(),
                        c.getNom(),
                        c.getPrenom(),
                        c.getTypeClient(),
                        c.getRevenusMensuels(),
                        c.getAge()
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur de lecture : " + e.getMessage());
        }
    }

    private void supprimerClient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client à supprimer.");
            return;
        }
        String idASupprimer = model.getValueAt(selectedRow, 0).toString();

        List<String> lignes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("clients.txt"))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (!ligne.startsWith(idASupprimer + "|")) {
                    lignes.add(ligne);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur de lecture : " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("clients.txt"))) {
            for (String l : lignes) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur d'écriture : " + e.getMessage());
        }

        chargerDonnees();
        JOptionPane.showMessageDialog(this, "Client supprimé avec succès.");
    }

    private void voirDetailClient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
            return;
        }

        String id = model.getValueAt(selectedRow, 0).toString();
        String nom = model.getValueAt(selectedRow, 1).toString();
        String prenom = model.getValueAt(selectedRow, 2).toString();
        String type = model.getValueAt(selectedRow, 3).toString();
        String salaire = model.getValueAt(selectedRow, 4).toString();
        String age = model.getValueAt(selectedRow, 5).toString();

        String message = String.format("\uD83D\uDCC4 Détails du client :\n\nID : %s\nNom : %s\nPrénom : %s\nType : %s\nSalaire : %s FCFA\nÂge : %s ans",
                id, nom, prenom, type, salaire, age);

        JOptionPane.showMessageDialog(this, message);
    }
}
