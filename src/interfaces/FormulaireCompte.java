package interfaces;

import models.Client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.Objects;

public class FormulaireCompte extends JFrame {

    private final JTextField tfNom;
    private final JTextField tfSalaire;
    private final JTextField tfAge;
    private final JTextField tfSoldeInitial;
    private final JComboBox<String> cbTypeClient;
    private final JComboBox<String> cbTypeCompte;

    public FormulaireCompte() {
        setTitle("🧾 Création de Compte");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));

        tfNom = new JTextField();
        tfSalaire = new JTextField();
        tfAge = new JTextField();
        tfSoldeInitial = new JTextField();

        cbTypeClient = new JComboBox<>(new String[]{"Particulier", "Salarié", "Société"});
        cbTypeCompte = new JComboBox<>(new String[]{"Courant", "Épargne", "Société"});

        panel.add(new JLabel("Nom & Prénom :"));
        panel.add(tfNom);
        panel.add(new JLabel("Type Client :"));
        panel.add(cbTypeClient);
        panel.add(new JLabel("Salaire/Revenu :"));
        panel.add(tfSalaire);
        panel.add(new JLabel("Âge :"));
        panel.add(tfAge);
        panel.add(new JLabel("Type de Compte :"));
        panel.add(cbTypeCompte);
        panel.add(new JLabel("Solde Initial :"));
        panel.add(tfSoldeInitial);

        JButton btnCreer = new JButton("Créer le compte");
        btnCreer.addActionListener(e -> creerCompte());

        panel.add(new JLabel());
        panel.add(btnCreer);

        add(panel);
    }

    private void creerCompte() {
        try {
            String nom = tfNom.getText().trim();
            String typeClient = Objects.requireNonNull(cbTypeClient.getSelectedItem()).toString();
            double salaire = Double.parseDouble(tfSalaire.getText().trim());
            int age = Integer.parseInt(tfAge.getText().trim());
            String typeCompte = Objects.requireNonNull(cbTypeCompte.getSelectedItem()).toString();
            double solde = Double.parseDouble(tfSoldeInitial.getText().trim());

            if (nom.isEmpty() || salaire <= 0 || age <= 0 || solde < 0) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs correctement.");
                return;
            }

            // Créer le client
            Client client = new Client(nom, typeClient, salaire, age);
            ecrireDansFichier("clients.txt", client.toString());

            // Créer le compte
            String numCompte = "CPT" + System.currentTimeMillis();
            String date = LocalDate.now().toString();
            String ligneCompte = numCompte + "|" + client.getId() + "|" + typeCompte + "|" + solde + "|" + date;
            ecrireDansFichier("comptes.txt", ligneCompte);

            JOptionPane.showMessageDialog(this, "Client et compte créés avec succès !");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
        }
    }

    private void ecrireDansFichier(String nomFichier, String ligne) throws IOException {
        FileWriter fw = new FileWriter(nomFichier, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(ligne);
        bw.newLine();
        bw.close();
    }
}
