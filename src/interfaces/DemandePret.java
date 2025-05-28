package interfaces;

import models.Client;
import models.Compte;
import services.CompteService;
import services.PretService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class DemandePret extends JFrame {

    public DemandePret(Client client) {
        setTitle("Demande de Prêt - " + client.getNom() + " " + client.getPrenom());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        Color backgroundColor = new Color(230, 240, 255);
        Color headerColor = new Color(7, 33, 32);
        Color buttonColor = new Color(7, 33, 32);
        Color textColor = Color.WHITE;

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(headerColor);
        header.setPreferredSize(new Dimension(500, 60));

        try {
            ImageIcon logo = new ImageIcon("src/images/logo.jpg");
            Image image = logo.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(image));
            header.add(logoLabel);
        } catch (Exception e) {
            // Pas de logo ? pas de problème
        }

        JLabel titleLabel = new JLabel(" Demande de Prêt");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(textColor);
        header.add(titleLabel);

        // Formulaire
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.setBackground(backgroundColor);

        JLabel labelCompte = new JLabel("N° Compte :");
        JTextField fieldCompte = new JTextField();
        JLabel labelMontant = new JLabel("Montant du prêt :");
        JTextField fieldMontant = new JTextField();
        JLabel labelDuree = new JLabel("Durée (mois) :");
        JTextField fieldDuree = new JTextField();

        JButton btnEnvoyer = new JButton("Envoyer");
        btnEnvoyer.setBackground(buttonColor);
        btnEnvoyer.setForeground(textColor);
        btnEnvoyer.setFocusPainted(false);

        // Charger les comptes du client
        try {
            List<Compte> comptes = CompteService.getComptesByClientId(client.getIdClient());
            if (!comptes.isEmpty()) {
                fieldCompte.setText(String.valueOf(comptes.get(0).getNumeroCompte())); // Prérempli avec le premier
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        formPanel.add(labelCompte);
        formPanel.add(fieldCompte);
        formPanel.add(labelMontant);
        formPanel.add(fieldMontant);
        formPanel.add(labelDuree);
        formPanel.add(fieldDuree);
        formPanel.add(new JLabel());
        formPanel.add(btnEnvoyer);

        btnEnvoyer.addActionListener((ActionEvent e) -> {
            try {
                int numCompte = Integer.parseInt(fieldCompte.getText());
                double montant = Double.parseDouble(fieldMontant.getText());
                int duree = Integer.parseInt(fieldDuree.getText());

                Compte compte = CompteService.getCompteByNumero(numCompte);
                if (compte == null) {
                    JOptionPane.showMessageDialog(this, "Compte introuvable !");
                    return;
                }

                String message;
                if (client.getTypeClient().equalsIgnoreCase("Salarie")) {
                    message = PretService.traiterPretSalarie(client, compte, montant, duree);
                } else {
                    message = PretService.traiterPretCourtTerme(client, compte, montant, duree);
                }

                JOptionPane.showMessageDialog(this, message);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs numériques valides.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors du traitement de la demande.");
                ex.printStackTrace();
            }
        });

        panel.add(header, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        add(panel);
    }
}
