package interfaces;

import models.Client;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ConsultationSolde extends JFrame {

    private JTextField tfNumero;
    private JTextArea taDetails;

    public ConsultationSolde(Client client) {
        setTitle("📊 Consultation Solde");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new BorderLayout());
        tfNumero = new JTextField();
        JButton btnConsulter = new JButton("Consulter");

        btnConsulter.addActionListener(e -> consulterCompte());

        topPanel.add(new JLabel("Numéro de Compte :"), BorderLayout.WEST);
        topPanel.add(tfNumero, BorderLayout.CENTER);
        topPanel.add(btnConsulter, BorderLayout.EAST);

        taDetails = new JTextArea();
        taDetails.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taDetails);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void consulterCompte() {
        String numero = tfNumero.getText().trim();
        File file = new File("comptes.txt");
        boolean trouve = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split("\\|");
                if (parts[0].equals(numero)) {
                    taDetails.setText("🔍 Détails du Compte :\n\n");
                    taDetails.append("Numéro : " + parts[0] + "\n");
                    taDetails.append("ID Client : " + parts[1] + "\n");
                    taDetails.append("Type : " + parts[2] + "\n");
                    taDetails.append("Solde : " + parts[3] + " FCFA\n");
                    taDetails.append("Date Ouverture : " + parts[4]);
                    trouve = true;
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }

        if (!trouve) {
            JOptionPane.showMessageDialog(this, "❌ Compte introuvable.");
            taDetails.setText("");
        }
    }
}
