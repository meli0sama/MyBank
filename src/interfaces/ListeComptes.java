package interfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

public class ListeComptes extends JFrame {
    public ListeComptes() {
        setTitle("\uD83D\uDCBC Liste des comptes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] colonnes = {"N° Compte", "ID Client", "Type", "Solde", "Date d'ouverture"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        try (BufferedReader br = new BufferedReader(new FileReader("comptes.txt"))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                model.addRow(ligne.split("\\|"));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }

        add(new JScrollPane(new JTable(model)));
        setVisible(true);
    }
}
