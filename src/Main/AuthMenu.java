package Main;

import Model.User;
import Service.AuthService;
import Exception.EmailAlreadyExistsException;
import Exception.InvalidCredentialsException;
import utils.InputUtils;

public class AuthMenu {

    public static int menuAuth() {
        System.out.println(" ===== Menu ===== ");
        System.out.println("1-Register");
        System.out.println("2-Login");
        System.out.println("3-Exite");
        int choix = InputUtils.readInt("Entrer Votre Choix : ");
        return choix;
    }

    public static void menuLogin() {
        while(true) {
            try {
                String email = InputUtils.readString("Email : ");
                String password = InputUtils.readString("Mode passe : ");
                Main.Authservice.Login(email, password);
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");

            } catch (InvalidCredentialsException e) {

                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");

            }
        }
    }

    public static void menuRegister() {

        while (true) {
            try {
                String fullName = InputUtils.readString("FullName : ");

                String phone = InputUtils.readString("Phone : ");

                String email = InputUtils.readString("Email : ");
                String password = InputUtils.readString("Mode passe : ");

                Main.Authservice.Register(fullName, email, phone, password , "user");

                System.out.println("Register réussi !");
                return;

            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");
            } catch (EmailAlreadyExistsException e) {
                System.out.println("Erreur : " + e.getMessage());
                System.out.println("Veuillez réessayer");
            }
        }
    }
}