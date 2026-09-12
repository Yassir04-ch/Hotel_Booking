package Main;

import Service.AuthService;
import Service.RoomService;
import Service.ReservationService;

public class Main {

    static AuthService Authservice;
    static RoomService RoomService;
    static ReservationService ReservationService;

    public static void main(String[] args) {

        Authservice = new AuthService();
        RoomService = new RoomService();
        ReservationService = new ReservationService(RoomService);

        while(true) {

            int choix = AuthMenu.menuAuth();

            switch (choix) {

                case 1:
                    System.out.println("Register");
                    AuthMenu.menuRegister();
                    break;

                case 2:
                    System.out.println("Login");
                    AuthMenu.menuLogin();
                    System.out.println("Welcome ");
                    MenuPrincipale.menuPrincipale();
                    break;

                default:
                    System.out.println("Good Day");
                    System.exit(0);
                    break;
            }
        }
    }
}