package Main;

import Model.Reservation;
import Exception.InvalidReservationDateException;
import Exception.ReservationNotFoundException;
import Exception.RoomNotFoundException;
import Exception.RoomUnavailableException;
import utils.DateUtils;
import utils.InputUtils;

import java.time.LocalDate;
import java.util.List;

public class ReservationMenu {

    public static void createReservation() {
        try{
            RoomMenu.afficherRoomsAvailable();

            String roomNumber =
                    InputUtils.readString("Entrer RoomNumber : ");

            LocalDate checkIn = DateUtils.readDate("Entrer  Date d'arrivée ex (2026-09-15) : ");

            LocalDate checkout =
                    DateUtils.readDate("Entrer Checkout ex (2026-09-15) : ");

            int numberOfGuests = InputUtils.readInt("Entrer numbre des persone ");

            Main.ReservationService.createReservation(roomNumber, checkIn, checkout, numberOfGuests);

        }catch (InvalidReservationDateException e){
            System.out.println("Erreur :" + e.getMessage());

        }catch (RoomNotFoundException e){
            System.out.println("Erreur :" + e.getMessage());

        }catch (RoomUnavailableException e){
            System.out.println("Erreur :" + e.getMessage());
        }
    }

    public static void userReservation(List<Reservation> reservations){
        if(reservations.isEmpty()){
            System.out.println("Auccune reservation");
            return;
        }

        System.out.println("===== Réservation =====");

        for (Reservation reservation : reservations){
            System.out.println("Code de réservation : " + reservation.getReservationCode());
            System.out.println("Numéro de chambre : " + reservation.getRoomNumber());
            System.out.println("Date de départ : " + reservation.getCheckIn());
            System.out.println("Date d'arrivée : " + reservation.getCheckOut());
            System.out.println("Nombre de personnes : " + reservation.getNumberOfGuests());
            System.out.println("Nombre de nuits : " + reservation.getNumberOfNights());
            System.out.println("Prix total : " + reservation.getTotalPrice() + " DH");
            System.out.println("Statut : " + reservation.getStatus());
            System.out.println("Date de création : " + reservation.getCreatedAt());
        }

        sortReservation();
    }

    public static void sortReservation(){
        System.out.println("==== Trier mes réservations ===");
        System.out.println("1. Trier par date de création");
        System.out.println("2. Trier par date d'arrivée");

        int choix = InputUtils.readInt("Entrer votre choix : ");

        switch (choix){
            case 1:
                userReservation(Main.ReservationService.sortReservationsByCreatedAt());
                break;

            case 2:
                userReservation(Main.ReservationService.sortReservationsByCheckIn());
                break;

            default:
                System.out.println("Choix invalide");
                break;
        }
    }

    public static void cancelReservation(){
        try{
            userReservation(Main.ReservationService.userReservation());

            String code = InputUtils.readString("Entrer code du Reservation");

            Main.ReservationService.cancelReservation(code);

        }catch (ReservationNotFoundException e){
            System.out.println("Erreur : "+e.getMessage());
        }
    }

    public static void updateReservation(){
        try{
            userReservation(
                    Main.ReservationService.userReservation()
            );

            String reservationCode =
                    InputUtils.readString("Entrer code de reservation");

            LocalDate checkIn =
                    DateUtils.readDate("Entrer Date de départ ");

            LocalDate checkout =
                    DateUtils.readDate("Entrer Date d'arrivée ");

            String roomNumber =
                    InputUtils.readString("Entrer nombre de room");

            int numberGuest =
                    InputUtils.readInt("Entrer nombre des persones");

            Main.ReservationService.updateReservation(
                    reservationCode,
                    roomNumber,
                    checkIn,
                    checkout,
                    numberGuest
            );

        }catch (ReservationNotFoundException e){
            System.out.println("Erreur : "+e.getMessage());

        }catch (InvalidReservationDateException e){
            System.out.println("Erreur : "+e.getMessage());

        }catch (RoomNotFoundException e){
            System.out.println("Erreur : "+e.getMessage());
        }
    }
}