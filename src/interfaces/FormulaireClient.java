package interfaces;

import models.Client;
import services.ClientDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class FormulaireClient extends JFrame {
    public FormulaireClient(JFrame parent) {
        setTitle("Créer un client");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 10, 10));

        JTextField txtNom = new JTextField();
        JComboBox<String> comboType = new JComboBox<>(new String[]{"Particulier", "Salarié", "Société"});
        JTextField txtSalaire = new JTextField();
        JTextField txtAge = new JTextField();

        JButton btnEnregistrer = new JButton("Enregistrer");
        btnEnregistrer.addActionListener(e -> {
            try {
                String nom = txtNom.getText();
                String type = comboType.getSelectedItem().toString();
                double salaire = Double.parseDouble(txtSalaire.getText());
                int age = Integer.parseInt(txtAge.getText());

                Client client = new Client(nom, type, salaire, age);
                ClientDAO.saveClient(client);
                JOptionPane.showMessageDialog(this, "Client enregistré avec succès.");
                dispose();
                parent.dispose();
                new Accueil();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur de saisie : " + ex.getMessage());
            }
        });

        add(new JLabel("Nom complet :"));
        add(txtNom);
        add(new JLabel("Type de client :"));
        add(comboType);
        add(new JLabel("Salaire/Revenu mensuel :"));
        add(txtSalaire);
        add(new JLabel("Âge :"));
        add(txtAge);
        add(new JLabel(""));
        add(btnEnregistrer);

        setVisible(true);
    }
}
