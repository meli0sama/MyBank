import models.*;
import services.*;
import interfaces.Accueil;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class MaFenetre {
    public static void main(String[] args) throws IOException {
        try {
            List<Client> clients = ClientDAO.getAllClients();
            for(Client c : clients) {
                System.out.println("Client ID " + c.getId() + ": " + c.getNomPrenom() + ", type: " + c.getType());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        // Ajouter ce code dans le main pour tester :
        Client client = new Client("Aissatou Ndiaye", "Particulier", 300000, 28);
        ClientDAO.saveClient(client);

        Compte compte = new Compte(client.getId(), "Courant", 100000);
        CompteDAO.saveCompte(compte);

// Affichage
        List<Compte> comptes = CompteDAO.getAllComptes();
        for(Compte c : comptes) {
            System.out.println("Compte n°" + c.getNumero() + ", client " + c.getIdClient() + ", solde: " + c.getSolde());
        }
        SwingUtilities.invokeLater(Accueil::new);
    }

}


/*
import javax.swing.*;
public class MaFenetre extends JFrame {

public MaFenetre() {
        super("Banque");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setSize(720, 480);
        setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        MaFenetre maFenetre = new MaFenetre();
        maFenetre.setVisible(true);

    }

}
*/
