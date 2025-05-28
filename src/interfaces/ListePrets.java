package interfaces;

import models.Pret;
import services.PretService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ListePrets extends JFrame {

    public ListePrets() {
        setTitle("📄 Liste des prêts");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] colonnes = {"ID Prêt", "ID Client", "Type Client", "Montant", "Durée", "Taux (%)", "Mensualité", "Reste à payer"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        try {
            List<Pret> prets = PretService.listerPrets();
            for (Pret p : prets) {
                model.addRow(new Object[]{
                        p.getIdClient(),
                        p.getTypeClient(),
                        String.format("%.2f", p.getMontant()),
                        p.getDureeMois(),
                        String.format("%.2f", p.getTauxInteret()),
                        String.format("%.2f", p.getMensualite()),
                        String.format("%.2f", p.getMontantRestant())
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement prêts : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
