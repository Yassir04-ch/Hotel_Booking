package Main;

import utils.InputUtils;

public class AdminMenu {

    public static void menuAdmin(){

        while (true){
            System.out.println("1. Add room");
            System.out.println("2. View all rooms");
            System.out.println("3. Update room");
            System.out.println("4. Set room AVAILABLE");
            System.out.println("5. Set room maintenance");
            System.out.println("6. View all reservations");
            System.out.println("7. View all clients");
            System.out.println("0. Exit");

            int choix = InputUtils.readInt("Entrer une Choix");

            switch (choix) {

                case 1:
                    RoomMenu.creetRoom();
                    break;

                case 2:
                    RoomMenu.afficherRooms();
                    break;

                case 3:
                    RoomMenu.updateRoom();
                    break;

                case 4:
                     RoomMenu.updateStatusAvailable();
                    break;

                case 5:
                    RoomMenu.updateStatusMAINTENANCE();
                    break;

                case 6:
                    ReservationMenu.updateReservation();
                    break;

                case 7:
                    AuthMenu.getAll();
                    break;

                case 0:
                    System.out.println("Good Day");
                    System.exit(0);

                default:
                    break;
            }
        }
    }
}
