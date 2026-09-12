package Main;

import Model.Admin;
import Model.Person;
import Service.AuthService;
import Service.RoomService;
import Service.ReservationService;

import java.util.UUID;

public class Main {

    static AuthService Authservice;
    static RoomService RoomService;
    static ReservationService ReservationService;

    public static void main(String[] args) {

        Authservice = new AuthService();
        RoomService = new RoomService();
        ReservationService = new ReservationService(RoomService);
        Authservice.Register("admin_prin" ,"admin@gmail.com","0987654321","admin123","admin");;
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
                    if(AuthService.getUserLogin().getRole().equals("user")){
                    MenuPrincipale.menuPrincipale();
                    }
                    else {
                        AdminMenu.menuAdmin();
                    }
                    break;

                default:
                    System.out.println("Good Day");
                    System.exit(0);
                    break;
            }
        }
    }
}