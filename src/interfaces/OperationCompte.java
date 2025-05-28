package interfaces;

import models.Client;
import models.Compte;
import services.CompteService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class OperationCompte extends JFrame {

    public OperationCompte(Client client) {
        setTitle("Opération sur Compte");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        Color backgroundColor = new Color(230, 240, 255);
        Color headerColor = new Color(7, 33, 32);
        Color buttonColor = new Color(7, 33, 32);
        Color textColor = Color.WHITE;

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(headerColor);
        header.setPreferredSize(new Dimension(500, 60));

        ImageIcon logo = new ImageIcon("src/images/logo.jpg");
        Image image = logo.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(image));
        header.add(logoLabel);

        JLabel titleLabel = new JLabel(" Opération sur Compte");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(textColor);
        header.add(titleLabel);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.setBackground(backgroundColor);

        JLabel labelCompte = new JLabel("Sélectionner Compte :");
        JComboBox<String> comboComptes = new JComboBox<>();
        JLabel soldeLabel = new JLabel("Solde : ---");

        try {
            List<Compte> comptes = CompteService.getComptesByClientId(client.getIdClient());


            for (Compte c : comptes) {
                comboComptes.addItem(c.getNumeroCompte() + " - " + c.getType());
            }

            // Mise à jour du solde dès qu'on sélectionne un compte
            comboComptes.addActionListener(e -> {
                try {
                    String selected = (String) comboComptes.getSelectedItem();
                    if (selected != null) {
                        int numCompte = Integer.parseInt(selected.split(" - ")[0]);
                        Compte compte = CompteService.getCompteByNumero(numCompte);
                        if (compte != null) {
                            soldeLabel.setText("Solde : " + compte.getSolde() + " FCFA");
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            // Déclenche le premier affichage
            if (comboComptes.getItemCount() > 0)
                comboComptes.setSelectedIndex(0);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erreur de chargement des comptes.", "Erreur", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        JLabel labelMontant = new JLabel("Montant :");
        JTextField fieldMontant = new JTextField();

        JLabel labelType = new JLabel("Type d'opération :");
        String[] types = {"Dépot", "Retrait"};
        JComboBox<String> comboType = new JComboBox<>(types);

        JButton btnValider = new JButton("Valider");
        btnValider.setBackground(buttonColor);
        btnValider.setForeground(textColor);
        btnValider.setFocusPainted(false);

        formPanel.add(labelCompte);
        formPanel.add(comboComptes);
        formPanel.add(new JLabel());
        formPanel.add(soldeLabel);
        formPanel.add(labelMontant);
        formPanel.add(fieldMontant);
        formPanel.add(labelType);
        formPanel.add(comboType);
        formPanel.add(new JLabel());
        formPanel.add(btnValider);

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) comboComptes.getSelectedItem();
                if (selected == null) {
                    JOptionPane.showMessageDialog(null, "Aucun compte sélectionné.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int numCompte = Integer.parseInt(selected.split(" - ")[0]);
                String montantStr = fieldMontant.getText().trim();
                String type = (String) comboType.getSelectedItem();

                if (montantStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez entrer un montant.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double montant;
                try {
                    montant = Double.parseDouble(montantStr);
                    if (montant <= 0) {
                        JOptionPane.showMessageDialog(null, "Le montant doit être positif.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Montant invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Compte compte = CompteService.getCompteByNumero(numCompte);
                    if (compte == null) {
                        JOptionPane.showMessageDialog(null, "Compte introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (type.equals("Dépot")) {
                        compte.setSolde(compte.getSolde() + montant);
                    } else {
                        if (compte.getSolde() < montant) {
                            JOptionPane.showMessageDialog(null, "Solde insuffisant.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        compte.setSolde(compte.getSolde() - montant);
                    }

                    CompteService.mettreAJourCompte(compte);
                    soldeLabel.setText("Solde : " + compte.getSolde() + " FCFA");

                    JOptionPane.showMessageDialog(null, "Opération effectuée avec succès !");
                    fieldMontant.setText("");

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur d'accès au fichier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        panel.add(header, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        add(panel);
    }
}
