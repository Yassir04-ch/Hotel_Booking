package Main;

import Model.*;
import Exception.InvalidReservationDateException;
import Exception.RoomUnavailableException;
import Exception.RoomNotFoundException;
import utils.DateUtils;
import utils.InputUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RoomMenu {

    public static void saveRoom(){
        Room room1 = new Room(
                "101",
                RoomType.SINGLE,
                1,
                new BigDecimal("500.00"),
                RoomStatus.MAINTENANCE
        );

        Room room2 = new Room(
                "303",
                RoomType.DOUBLE,
                2,
                new BigDecimal("800.00"),
                RoomStatus.AVAILABLE
        );

        Main.RoomService.getRepo().save(room1);
        Main.RoomService.getRepo().save(room2);
    }

    public static void afficherRooms(){
        saveRoom();
        List<Room> rooms = Main.RoomService.getAllRooms();

        if (rooms.isEmpty()){
            System.out.println("Aucune chambre disponible.");
            return;
        }

        for(Room room : rooms){
            System.out.println("=======================");
            System.out.println("roomNumber : " + room.getRoomNumber());
            System.out.println("capacity : " + room.getCapacity());
            System.out.println("price : " + room.getPrice());
            System.out.println("type : " + room.getType());
            System.out.println("status : " + room.getStatus());
            System.out.println("=======================");
        }
    }

    public static void afficherRoomsAvailable(){
        saveRoom();
        List<Room> rooms = Main.RoomService.getAvailableRoom();

        if (rooms.isEmpty()){
            System.out.println("Aucune chambre disponible.");
            return;
        }

        for(Room room : rooms){
            System.out.println("=======================");
            System.out.println("roomNumber : " + room.getRoomNumber());
            System.out.println("capacity : " + room.getCapacity());
            System.out.println("price : " + room.getPrice());
            System.out.println("type : " + room.getType());
            System.out.println("status : " + room.getStatus());
            System.out.println("=======================");
        }
    }

    public static void roomAvailableDate(){
        try{
            LocalDate checkin = DateUtils.readDate("Entrer  Date d'arrivée ex (2026-09-15) : ");

            LocalDate checkout = DateUtils.readDate("Entrer Checkout ex (2026-09-15) : ");

            List<Room> rooms = Main.ReservationService.roomAvailableDate(checkin , checkout);

            for(Room room : rooms){
                System.out.println("=======================");
                System.out.println("roomNumber : " + room.getRoomNumber());
                System.out.println("capacity : " + room.getCapacity());
                System.out.println("price : " + room.getPrice());
                System.out.println("type : " + room.getType());
                System.out.println("status : " + room.getStatus());
                System.out.println("=======================");
            }

        }catch (RoomUnavailableException e){
            System.out.println("Erreur :" + e.getMessage());

        }catch (InvalidReservationDateException e){
            System.out.println("Erreur :" + e.getMessage());
        }
    }

    public static RoomType roomType(){
        System.out.println("type de room");
        System.out.println("1-SINGLE");
        System.out.println("1-DOUBLE");
        System.out.println("1-SUITE");
        int choix = InputUtils.readInt("Choose type : ");
        RoomType type;
        switch (choix) {
            case 1:
                type = RoomType.SINGLE;
                break;

            case 2:
                type = RoomType.DOUBLE;
                break;

            case 3:
                type = RoomType.SUITE;
                break;

            default:
                throw new IllegalArgumentException("Type de chambre invalide");
        }
        return type;
    }

    public static void creetRoom(){
        System.out.println("===crée Room ===");
        String roomNumber = InputUtils.readString("Entrer room number : ");
        int capacity = InputUtils.readInt("Entrer capacity : ");
        BigDecimal price = InputUtils.readBigDecimal("Entrer Prix : ");

        RoomType type = roomType();

        Main.RoomService.creetRoom(roomNumber,capacity,price,type);

    }

    public static void updateRoom(){
        String roomNumber = InputUtils.readString("Entrer room number : ");
        int capacity = InputUtils.readInt("Entrer capacity : ");
        BigDecimal price = InputUtils.readBigDecimal("Entrer Prix : ");
        RoomType type = roomType();
        try {
                Main.RoomService.updateRoom(roomNumber,capacity,price,type);

                System.out.println("Room crée");
        }catch (RoomNotFoundException e){
            System.out.println("Erreur : "+e.getMessage());
        }
    }

    public static void updateStatusAvailable(){
        String roomNumber = InputUtils.readString("Entrer room number : ");
        try {
            Main.RoomService.updateStatusAvailable(roomNumber);
            System.out.println("status Update ");
        }catch (RoomNotFoundException | RoomUnavailableException e){
            System.out.println("Erreur : "+e.getMessage());
        }
    }

    public static void updateStatusMAINTENANCE(){
        String roomNumber = InputUtils.readString("Entrer room number : ");
        try {
            Main.RoomService.updateStatusMAINTENANCE(roomNumber);
            System.out.println("status Update ");
        }catch (RoomNotFoundException | RoomUnavailableException e){
            System.out.println("Erreur : "+e.getMessage());
        }
    }
}