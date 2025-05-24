package interfaces;

import models.Client;
import services.ClientDAO;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Accueil extends JFrame {
    private JComboBox<String> comboClients;
    private List<Client> clients;

    public Accueil() {
        setTitle("🏦 Banque - Accueil");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel labelTitre = new JLabel("Bienvenue à la Banque", SwingConstants.CENTER);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 22));
        add(labelTitre, BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton btnNouveauClient = new JButton("Créer un nouveau client");
        btnNouveauClient.addActionListener(e -> ouvrirFormulaireClient());

        comboClients = new JComboBox<>();
        chargerClients();

        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.addActionListener(e -> seConnecter());

        centre.add(btnNouveauClient);
        centre.add(new JLabel("Ou sélectionnez un client :"));
        centre.add(comboClients);
        centre.add(btnConnexion);

        add(centre, BorderLayout.CENTER);
        setVisible(true);
    }

    private void chargerClients() {
        try {
            clients = ClientDAO.getAllClients();
            comboClients.removeAllItems();
            for (Client c : clients) {
                comboClients.addItem(c.getId() + " - " + c.getNomPrenom());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement clients.");
        }
    }

    private void ouvrirFormulaireClient() {
        new FormulaireClient(this); // supposé exister
    }

    private void seConnecter() {
        int index = comboClients.getSelectedIndex();
        if (index != -1) {
            Client client = clients.get(index);
            new TableauDeBord(client).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Accueil());
    }
}
