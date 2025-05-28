package interfaces;

import models.Client;
import models.Compte;
import services.CompteService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConsultationSolde extends JFrame {

    public ConsultationSolde() {
        setTitle("Consultation de Solde");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        // Couleurs
        Color backgroundColor = new Color(230, 240, 255);
        Color headerColor = new Color(7, 33, 32);
        Color buttonColor = new Color(7, 33, 32);
        Color textColor = Color.WHITE;

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);

        // En-tête
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(headerColor);
        header.setPreferredSize(new Dimension(500, 60));

        // Logo
        ImageIcon logo = new ImageIcon("src/images/logo.jpg");
        Image image = logo.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(image));
        header.add(logoLabel);

        JLabel titleLabel = new JLabel(" Consultation de Solde");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(textColor);
        header.add(titleLabel);

        // Formulaire
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.setBackground(backgroundColor);

        JLabel numCompteLabel = new JLabel("Numéro de compte :");
        JTextField numCompteField = new JTextField();

        JLabel soldeLabel = new JLabel("Solde actuel :");
        JTextField soldeField = new JTextField();
        soldeField.setEditable(false);
        soldeField.setBackground(Color.LIGHT_GRAY);

        JButton btnConsulter = new JButton("Consulter");
        btnConsulter.setBackground(buttonColor);
        btnConsulter.setForeground(textColor);
        btnConsulter.setFocusPainted(false);

        formPanel.add(numCompteLabel);
        formPanel.add(numCompteField);
        formPanel.add(soldeLabel);
        formPanel.add(soldeField);
        formPanel.add(new JLabel());
        formPanel.add(btnConsulter);

        // Action
        btnConsulter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = numCompteField.getText().trim();
                try {
                    int numCompte = Integer.parseInt(input);
                    Compte compte = CompteService.getCompteByNumero(numCompte);
                    if (compte != null) {
                        soldeField.setText(compte.getSolde() + " FCFA");
                    } else {
                        soldeField.setText("Compte introuvable !");
                    }
                } catch (NumberFormatException ex) {
                    soldeField.setText("Numéro invalide !");
                } catch (IOException ex) {
                    soldeField.setText("Erreur lecture fichier !");
                }
            }
        });

        panel.add(header, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);

        add(panel);
    }
}
