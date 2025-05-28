package interfaces;

import models.Client;
import models.Compte;
import services.ClientService;
import services.CompteService;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Accueil extends JFrame {

    public Accueil() {
        setTitle("🏦 Bienvenue - Système Bancaire");
        setIconImage(new ImageIcon(getClass().getResource("/images/logo.jpg")).getImage());
        setSize(900, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Partie gauche : Image
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/banque.jpg"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        add(imageLabel, BorderLayout.WEST);

        // Partie droite : Choix du rôle
        JPanel rolePanel = new JPanel();
        rolePanel.setLayout(new BoxLayout(rolePanel, BoxLayout.Y_AXIS));
        rolePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        rolePanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Choisissez votre rôle");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton clientBtn = new JButton("👤 Client");
        JButton adminBtn = new JButton("👨‍💼 Admin");

        for (JButton btn : new JButton[]{clientBtn, adminBtn}) {
            btn.setMaximumSize(new Dimension(200, 40));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setBackground(new Color(30, 144, 255));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            rolePanel.add(Box.createRigidArea(new Dimension(0, 20)));
            rolePanel.add(btn);
        }

        rolePanel.add(Box.createRigidArea(new Dimension(0, 20)));

        add(rolePanel, BorderLayout.CENTER);

        // Actions
        clientBtn.addActionListener(e -> afficherInterfaceClient());
        adminBtn.addActionListener(e -> afficherInterfaceAdmin());

        setVisible(true);
    }


    private void afficherInterfaceClient() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/banque.jpg"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(450, 450, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        add(imageLabel, BorderLayout.WEST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 14));
        tabs.addTab("🔐 Connexion", createLoginPanel());
        tabs.addTab("🆕 Créer un compte", createSignupPanel());

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rightPanel.add(tabs, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private void afficherInterfaceAdmin() {
        getContentPane().removeAll();
        setLayout(new BorderLayout());

        JLabel labelTitre = new JLabel("Connexion Admin", JLabel.CENTER);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitre.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField tfAdminId = new JTextField(12);
        JPasswordField tfPassword = new JPasswordField(12);
        JButton btnLogin = new JButton("Connexion");

        btnLogin.setBackground(new Color(139, 0, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 13));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ID Admin :"), gbc);
        gbc.gridx = 1;
        panel.add(tfAdminId, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        panel.add(tfPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            String id = tfAdminId.getText().trim();
            String pass = new String(tfPassword.getPassword()).trim();

            if (id.equals("admin") && pass.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Bienvenue, Administrateur !");
                new AdminDashboard().setVisible(true); // À créer
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Identifiants incorrects !");
            }
        });

        add(labelTitre, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelId = new JLabel("ID Client :");
        JTextField tfId = new JTextField(12);
        JButton btnLogin = new JButton("Connexion");

        btnLogin.setBackground(new Color(34, 139, 34));
        btnLogin.setForeground(Color.white);
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelId, gbc);
        gbc.gridx = 1;
        panel.add(tfId, gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                Client client = ClientService.getClientById(id);
                if (client != null) {
                    JOptionPane.showMessageDialog(this, "Bienvenue " + client.getPrenom() + " " + client.getNom());
                    new TableauDeBord(client).setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "ID introuvable.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID invalide.");
            }
        });

        return panel;
    }

    private JPanel createSignupPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField tfNom = new JTextField(15);
        JTextField tfPrenom = new JTextField(15);
        JComboBox<String> cbTypeClient = new JComboBox<>(new String[]{"Particulier", "Salarié", "Société"});
        JTextField tfSalaire = new JTextField(10);
        JTextField tfAge = new JTextField(5);
        JComboBox<String> cbTypeCompte = new JComboBox<>(new String[]{"Courant", "Épargne", "Société"});
        JTextField tfSoldeInitial = new JTextField(10);
        JButton btnSignup = new JButton("Créer le compte");

        btnSignup.setBackground(new Color(0, 120, 215));
        btnSignup.setForeground(Color.white);
        btnSignup.setFocusPainted(false);
        btnSignup.setFont(new Font("Arial", Font.BOLD, 13));
        btnSignup.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Prenom :"), gbc);
        gbc.gridx = 1;
        panel.add(tfPrenom, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        panel.add(tfNom, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Type de client :"), gbc);
        gbc.gridx = 1;
        panel.add(cbTypeClient, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Salaire / Revenu :"), gbc);
        gbc.gridx = 1;
        panel.add(tfSalaire, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Âge :"), gbc);
        gbc.gridx = 1;
        panel.add(tfAge, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Type de compte :"), gbc);
        gbc.gridx = 1;
        panel.add(cbTypeCompte, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Solde initial :"), gbc);
        gbc.gridx = 1;
        panel.add(tfSoldeInitial, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(btnSignup, gbc);

        btnSignup.addActionListener(e -> {
            try {
                String nom = tfNom.getText().trim();
                String prenom = tfPrenom.getText().trim();
                String typeClient = Objects.requireNonNull(cbTypeClient.getSelectedItem()).toString();
                double salaire = Double.parseDouble(tfSalaire.getText().trim());
                double soldeInitial = Double.parseDouble(tfSoldeInitial.getText().trim());
                String typeCompte = Objects.requireNonNull(cbTypeCompte.getSelectedItem()).toString();

                int age;
                try {
                    age = Integer.parseInt(tfAge.getText().trim());
                    if (age < 18 || age > 100) {
                        JOptionPane.showMessageDialog(this, "L'âge doit être entre 18 et 100.");
                        return;
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer un âge valide.");
                    return;
                }

                int id = ClientService.getNextId();

                Client client = new Client(id, nom, prenom, typeClient, salaire, age);
                ClientService.saveClient(nom, prenom, typeClient, salaire, age);

                Compte compte = new Compte(client.getIdClient(), typeCompte, soldeInitial);
                CompteService.saveCompte(compte);

                JOptionPane.showMessageDialog(this,
                        "Client et compte créés !\nID Client : " + client.getIdClient() +
                                "\nNuméro de compte : " + compte.getNumeroCompte());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });

        return panel;
    }
}
