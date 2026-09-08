import Model.User;
import Service.AuthService;

import java.util.List;
import java.util.Scanner;
import  Exception.EmailAlreadyExistsException;
import  Exception.InvalidCredentialsException;

public class Main {

    private static AuthService service;

    public static int menuAuth() {
        System.out.println(" ===== Menu ===== ");
        System.out.println("1-Register");
        System.out.println("2-Login");
        System.out.println("3-Exite");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrer Votre Choix : ");

        return scanner.nextInt();

    }

    public static void menuLogin() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            try {
                System.out.println("Email : ");
                String email = scanner.nextLine();
                System.out.println("Password : ");
                String password = scanner.nextLine();
                User user = service.Login(email, password);
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

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("FullName : ");
                String fullName = scanner.nextLine();

                System.out.println("Phone : ");
                String phone = scanner.nextLine();

                System.out.println("Email : ");
                String email = scanner.nextLine();
                System.out.println("Password : ");
                String password = scanner.nextLine();

                User user = service.Register(fullName, email, phone, password);

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

    public static void menuPrincipale(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Search available rooms");
        System.out.println("2. View all rooms");
        System.out.println("3. Create reservation");
        System.out.println("4. My reservations");
        System.out.println("5. Reservation details");
        System.out.println("6. Update reservation");
        System.out.println("7. Cancel reservation");
        System.out.println("8. Update profile");
        System.out.println("9. Change password");
        System.out.println("10. Logout");
        System.out.println("0. Exit");

        System.out.println("Entrer une Choix");
        int choix = scanner.nextInt();
        switch (choix) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            default:
                break;
        }
    }

    static void main(String[] args) {
        service = new AuthService();
            while(true) {
                int choix = menuAuth();
                switch (choix) {
                    case 1:
                        System.out.println("Register");
                        menuRegister();
                        List<User> users = service.getRepo().findAll();
                        for(User user : users){
                            System.out.println("Nom : " + user.getFullName());
                            System.out.println("Email : " + user.getEmail());
                            System.out.println("Phone : " + user.getPhone());
                        }
                        break;
                    case 2:
                        System.out.println("Login");
                        menuLogin();
                        System.out.println("Welcome ");
                        menuPrincipale();
                        break;
                    default:
                        System.out.println("Good Day");
                        break;
                }
            }
        }
}
