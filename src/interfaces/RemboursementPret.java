package interfaces;

import models.Client;
import models.Pret;
import services.ClientService;
import services.PretService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class RemboursementPret extends JFrame {

    private final DefaultTableModel model;

    private JTable table; // À rendre global

public RemboursementPret() {
    setTitle("Remboursements des Prêts");
    setSize(750, 400);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLayout(new BorderLayout());

    String[] colonnes = {"Numéro client", "Nom client", "Montant remboursé", "Montant restant", "Mensualité"};
    model = new DefaultTableModel(colonnes, 0);
    table = new JTable(model); // Rendu global
    JScrollPane scrollPane = new JScrollPane(table);

    JButton btnRembourser = new JButton("💸 Effectuer un remboursement");
    JButton btnFermer = new JButton("Fermer");

    JPanel panelBoutons = new JPanel();
    panelBoutons.add(btnRembourser);
    panelBoutons.add(btnFermer);

    add(scrollPane, BorderLayout.CENTER);
    add(panelBoutons, BorderLayout.SOUTH);

    btnFermer.addActionListener(e -> dispose());
    btnRembourser.addActionListener(e -> effectuerRemboursement());

    chargerRemboursements();
    setVisible(true);
}

private void chargerRemboursements() {
    model.setRowCount(0); // Nettoyer avant de charger
    List<Pret> prets = PretService.chargerPrets();

    for (Pret pret : prets) {
        try {
            Client client = ClientService.getClientById(pret.getIdClient());
            double montantRembourse = pret.getMontant() - pret.getMontantRestant();
            model.addRow(new Object[]{
                    client.getIdClient(),
                    client.getNom() + " " + client.getPrenom(),
                    String.format("%.2f", montantRembourse),
                    String.format("%.2f", pret.getMontantRestant()),
                    String.format("%.2f", pret.getMensualite())
            });
        } catch (IOException e) {
            System.err.println("Erreur chargement client pour prêt : " + e.getMessage());
        }
    }
}

private void effectuerRemboursement() {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Veuillez sélectionner un prêt à rembourser.");
        return;
    }

    int idClient = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

    try {
        List<Pret> prets = PretService.chargerPrets();
        boolean updated = false;

        for (Pret pret : prets) {
            if (pret.getIdClient() == idClient && pret.getMontantRestant() > 0) {
                double nouveauMontant = pret.getMontantRestant() - pret.getMensualite();
                if (nouveauMontant < 0) nouveauMontant = 0;
                pret.setMontantRestant(nouveauMontant);
                updated = true;
                break;
            }
        }

        if (updated) {
            // Réécrire dans prets.txt
            PretService.reenregistrerTousLesPrets(prets);
            chargerRemboursements();
            JOptionPane.showMessageDialog(this, "💰 Remboursement effectué !");
        } else {
            JOptionPane.showMessageDialog(this, "Aucun prêt à rembourser pour ce client.");
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Erreur lors du remboursement : " + e.getMessage());
    }
}

}
