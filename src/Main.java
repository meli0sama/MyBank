import models.Compte;
import widgets.SplashScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Compte.initialiserCompteurDepuisFichier("comptes.txt");
        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();
            splash.showSplashWithProgress();
        });
    }
}
