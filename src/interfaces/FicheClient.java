package interfaces;

import models.Client;
import models.Compte;
import services.CompteService;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class FicheClient extends JFrame {
    public FicheClient(Client client) {
        setTitle("👤 Fiche client");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(new JLabel("Nom : " + client.getNom()));
        mainPanel.add(new JLabel("Prénom : " + client.getPrenom()));
        mainPanel.add(new JLabel("Type : " + client.getTypeClient()));
        mainPanel.add(new JLabel("Âge : " + client.getAge()));
        mainPanel.add(new JLabel("Salaire : " + client.getRevenusMensuels() + " FCFA"));
        mainPanel.add(new JLabel("ID : " + client.getIdClient()));

        // Section comptes
        mainPanel.add(Box.createVerticalStrut(15)); // Espacement
        mainPanel.add(new JLabel("📄 Comptes :"));

        try {
            List<Compte> comptes = CompteService.getComptesByClientId(client.getIdClient());
            if (comptes.isEmpty()) {
                mainPanel.add(new JLabel("Aucun compte trouvé."));
            } else {
                for (Compte compte : comptes) {
                    String info = "N° compte: " + compte.getNumeroCompte() + "("+getType()+")";
                    mainPanel.add(new JLabel(info));
                }
            }
        } catch (IOException e) {
            mainPanel.add(new JLabel("Erreur lors du chargement des comptes."));
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        add(scrollPane);
        setVisible(true);
    }
}
