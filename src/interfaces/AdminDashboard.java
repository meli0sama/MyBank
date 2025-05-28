package interfaces;

import models.Client;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("🛠️ Tableau de bord Admin");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 15, 15));

        JButton btnClients = new JButton("👥 Liste des clients");
        JButton btnComptes = new JButton("💼 Liste des comptes");
        JButton btnPrets = new JButton("💰 Liste des prêts");
        JButton btnStats = new JButton("📊 Statistiques");
        JButton btnRemboursements = new JButton("🔄 Ajouter remboursement");
        JButton btnAccueil = new JButton("🏠 Retour à l'accueil");

        btnClients.addActionListener(e -> new ListeClients().setVisible(true));
        btnComptes.addActionListener(e -> new ListeComptes().setVisible(true));
        btnPrets.addActionListener(e -> new ListePrets().setVisible(true));
        btnStats.addActionListener(e -> new Statistiques().setVisible(true));
        Client client = null;
        btnRemboursements.addActionListener(e -> new RemboursementPret().setVisible(true));
        btnAccueil.addActionListener(e -> {
            dispose();
            new Accueil().setVisible(true);
        });

        add(btnClients);
        add(btnComptes);
        add(btnPrets);
        add(btnRemboursements);
        add(btnStats);
        add(btnAccueil);

        setVisible(true);
    }
}


