package interfaces;

import interfaces.ConsultationSolde;
import models.Client;

import javax.swing.*;
import java.awt.*;

public class TableauDeBord extends JFrame {

    public TableauDeBord(Client client) {

        setTitle("🏦 Tableau de Bord - Banque - " + client.getNomPrenom());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton btnCreerCompte = new JButton("Créer un compte");
        JButton btnDepot = new JButton("Dépôt / Retrait");
        JButton btnSolde = new JButton("Consulter solde");
        JButton btnPret = new JButton("Demander un prêt");
        JButton btnQuitter = new JButton("Quitter");

        // Actions des boutons, en passant le client si besoin
        btnCreerCompte.addActionListener(e -> new FormulaireCompte().setVisible(true));
        btnDepot.addActionListener(e -> new OperationCompte().setVisible(true));
        btnSolde.addActionListener(e -> new ConsultationSolde(client).setVisible(true));
        btnPret.addActionListener(e -> new DemandePret(client).setVisible(true));
        btnQuitter.addActionListener(e -> System.exit(0));

        panel.add(btnCreerCompte);
        panel.add(btnDepot);
        panel.add(btnSolde);
        panel.add(btnPret);
        panel.add(btnQuitter);

        add(panel);
    }

    // Pour test rapide - optionnel
    public static void main(String[] args) {
        Client clientTest = new Client("Jean Dupont", "Particulier", 2000, 30);
        SwingUtilities.invokeLater(() -> new TableauDeBord(clientTest).setVisible(true));
    }
}
