package Main;

import utils.InputUtils;

public class MenuPrincipale {

    public static void menuPrincipale(){

        while (true){
            System.out.println("1. Search available rooms");
            System.out.println("2. View all rooms");
            System.out.println("3. Create reservation");
            System.out.println("4. My reservations");
            System.out.println("5. Reservation details");
            System.out.println("6. Update reservation");
            System.out.println("7. Cancel reservation");
            System.out.println("8. Afficher Profile");
            System.out.println("9. Logout");
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