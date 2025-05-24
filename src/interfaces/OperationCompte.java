package interfaces;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class OperationCompte extends JFrame {

    private final JTextField tfNumero;
    private final JTextField tfMontant;
    private final JRadioButton rbDepot;

    public OperationCompte() {
        setTitle("💳 Dépôt / Retrait");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        tfNumero = new JTextField();
        tfMontant = new JTextField();
        rbDepot = new JRadioButton("Dépôt");
        JRadioButton rbRetrait = new JRadioButton("Retrait");

        ButtonGroup group = new ButtonGroup();
        group.add(rbDepot);
        group.add(rbRetrait);
        rbDepot.setSelected(true);

        panel.add(new JLabel("Numéro de Compte :"));
        panel.add(tfNumero);
        panel.add(new JLabel("Montant :"));
        panel.add(tfMontant);
        panel.add(rbDepot);
        panel.add(rbRetrait);

        JButton btnValider = new JButton("Valider l'opération");
        btnValider.addActionListener(e -> executerOperation());

        panel.add(new JLabel());
        panel.add(btnValider);

        add(panel);
    }

    private void executerOperation() {
        String numCompte = tfNumero.getText().trim();
        double montant;

        try {
            montant = Double.parseDouble(tfMontant.getText().trim());
            if (montant <= 0) {
                JOptionPane.showMessageDialog(this, "Montant invalide.");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Entrez un montant valide.");
            return;
        }

        File comptesFile = new File("comptes.txt");
        List<String> lignes = new ArrayList<>();
        boolean trouve = false;

        try (BufferedReader br = new BufferedReader(new FileReader(comptesFile))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] parts = ligne.split("\\|");
                if (parts[0].equals(numCompte)) {
                    trouve = true;
                    double solde = Double.parseDouble(parts[3]);
                    if (rbDepot.isSelected()) {
                        solde += montant;
                    } else {
                        if (solde < montant) {
                            JOptionPane.showMessageDialog(this, "Solde insuffisant pour le retrait.");
                            return;
                        }
                        solde -= montant;
                    }
                    ligne = parts[0] + "|" + parts[1] + "|" + parts[2] + "|" + solde + "|" + parts[4];
                }
                lignes.add(ligne);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur de lecture : " + e.getMessage());
            return;
        }

        if (!trouve) {
            JOptionPane.showMessageDialog(this, "Compte introuvable.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(comptesFile))) {
            for (String l : lignes) {
                bw.write(l);
                bw.newLine();
            }
            JOptionPane.showMessageDialog(this, "Opération réussie !");
            dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur d’écriture : " + e.getMessage());
        }
    }
}
