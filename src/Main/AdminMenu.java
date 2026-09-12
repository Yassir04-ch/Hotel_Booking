package Main;

import utils.InputUtils;

public class AdminMenu {

    public static void menuAdmin(){

        while (true){
            System.out.println("1. Add room");
            System.out.println("2. View all rooms");
            System.out.println("3. Update room");
            System.out.println("4. Set room maintenance");
            System.out.println("5. View all reservations");
            System.out.println("6. View all clients");
            System.out.println("7. Logout");
            System.out.println("0. Exit");

            int choix = InputUtils.readInt("Entrer une Choix");

            switch (choix) {

                case 1:
                    RoomMenu.roomAvailableDate();
                    break;

                case 2:
                    RoomMenu.afficherRooms();
                    break;

                case 3:
                    ReservationMenu.createReservation();
                    break;

                case 4:
                    ReservationMenu.userReservation(Main.ReservationService.userReservation());
                    break;

                case 5:
                    break;

                case 6:
                    ReservationMenu.updateReservation();
                    break;

                case 7:
                    ReservationMenu.cancelReservation();
                    break;

                case 8:
                    ProfileMenu.menuProfile();
                    break;

                case 9:
                    Main.Authservice.logOut();
                    System.out.println("Logout");
                    return;

                case 0:
                    System.out.println("Good Day");
                    System.exit(0);

                default:
                    break;
            }
        }
    }
}
