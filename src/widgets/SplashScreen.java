package widgets;

import interfaces.Accueil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SplashScreen extends JWindow {
    private final JProgressBar progressBar;

    public SplashScreen() {
        // Chargement de l'image (assure-toi qu'elle est dans /resources/images/splash.jpg)
        ImageIcon splashIcon = new ImageIcon(getClass().getResource("/images/splash.jpg"));

        JLabel imageLabel = new JLabel(splashIcon);

        // Texte
        JLabel labelChargement = new JLabel("Chargement en cours...", SwingConstants.CENTER);
        labelChargement.setFont(new Font("Arial", Font.BOLD, 14));
        labelChargement.setForeground(Color.DARK_GRAY);

        // Barre de progression
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(0, 153, 76));

        // Layout
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        content.add(imageLabel, BorderLayout.CENTER);
        content.add(labelChargement, BorderLayout.NORTH);
        content.add(progressBar, BorderLayout.SOUTH);

        setContentPane(content);
        setSize(splashIcon.getIconWidth(), splashIcon.getIconHeight() + 50);
        setLocationRelativeTo(null); // Centré
    }

    public void showSplashWithProgress() {
        setVisible(true); // Montrer le Splash

        // Ajout d'un léger délai AVANT de démarrer le timer (laisse Swing rendre la fenêtre)
        SwingUtilities.invokeLater(() -> {
            Timer timer = new Timer(50, new AbstractAction() {
                int value = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    value++;
                    progressBar.setValue(value);

                    if (value >= 100) {
                        ((Timer) e.getSource()).stop();
                        dispose();
                        new Accueil().setVisible(true); // Lance la fenêtre principale
                    }
                }
            });
            timer.start();
        });
    }
}

