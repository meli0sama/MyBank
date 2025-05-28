package interfaces;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Statistiques extends JFrame {
    public Statistiques() {
        setTitle("📊 Statistiques");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int nbClients = compterLignes("clients.txt");
        int nbPrets = compterLignes("prets.txt");
        int nbComptes = compterLignes("comptes.txt");

        panel.add(new JLabel("👥 Clients enregistrés : " + nbClients));
        panel.add(new JLabel("💼 Comptes créés : " + nbComptes));
        panel.add(new JLabel("💰 Prêts en cours : " + nbPrets));

        add(panel);
        setVisible(true);
    }

    private int compterLignes(String fichier) {
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            int count = 0;
            while (br.readLine() != null) count++;
            return count;
        } catch (IOException e) {
            return 0;
        }
    }
}
