package interfaces;

import models.Client;

import javax.swing.*;
import java.awt.*;

public class TableauDeBord extends JFrame {

    public TableauDeBord(Client client) {
        setTitle("🏦 Mon Espace Client");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnInfos = new JButton("👤 Mes informations");
        JButton btnCompte = new JButton("💼 Mon compte");
        JButton btnOperation = new JButton("💳 Dépôt / Retrait");
        JButton btnPret = new JButton("💸 Demande de prêt");
        JButton btnRembourser = new JButton("📆 Rembourser prêt");
        JButton btnQuitter = new JButton("🚪 Quitter");

        btnInfos.addActionListener(e -> new FicheClient(client).setVisible(true));
        btnCompte.addActionListener(e -> new ConsultationSolde().setVisible(true));
        btnOperation.addActionListener(e -> new OperationCompte(client).setVisible(true));
        btnPret.addActionListener(e -> new DemandePret(client).setVisible(true));
        btnRembourser.addActionListener(e -> new RemboursementPret().setVisible(true));
        btnQuitter.addActionListener(e -> {
            dispose();
            new Accueil().setVisible(true);
        });

        panel.add(btnInfos);
        panel.add(btnCompte);
        panel.add(btnOperation);
        panel.add(btnPret);
        panel.add(btnRembourser);
        panel.add(btnQuitter);

        add(panel);
        setVisible(true);
    }
}
