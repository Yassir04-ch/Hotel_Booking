package Service;

import Model.Reservation;
import Model.ReservationStatus;
import Model.Room;
import Model.RoomStatus;
import Repository.impl.InMemoryReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import Exception.InvalidReservationDateException;
import  Exception.ReservationNotFoundException;
import  Exception.RoomNotFoundException;

public class ReservationService {
    private InMemoryReservationRepository repo ;
    private RoomService roomService ;

    public ReservationService(RoomService roomService){
        this.repo = new InMemoryReservationRepository();
        this.roomService =roomService ;
    }


    public boolean checkDate(Room room, LocalDate checkIn, LocalDate checkOut)
            throws InvalidReservationDateException {

        if (checkIn.isBefore(LocalDate.now())) {
            throw new InvalidReservationDateException(
                    "Date checkin invalide."
            );
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidReservationDateException(
                    "Date checkout invalide."
            );
        }

        List<Reservation> listReservations =
                this.repo.findByRoomNumber(room.getRoomNumber());

        for (Reservation reservation : listReservations) {

            if (reservation.getStatus() == ReservationStatus.CONFIRMED) {

                if (checkIn.isBefore(reservation.getCheckOut())
                        && checkOut.isAfter(reservation.getCheckIn())) {

                    return false;
                }
            }
        }

        return true;
    }

    public void createReservation(String roomNumber, LocalDate checkIn, LocalDate checkOut,
                         int numberOfGuests) throws InvalidReservationDateException , RoomNotFoundException {

        try {

            Room room = roomService.findRoom(roomNumber);

            if (numberOfGuests <= 0) {
                throw new InvalidReservationDateException(
                        "Le nombre de personnes invalide."
                );
            }

            if (numberOfGuests > room.getCapacity()) {
                throw new InvalidReservationDateException(
                        "Le nombre de personnes dépasse la capacité de la chambre."
                );
            }

            if (room.getStatus() != RoomStatus.AVAILABLE) {
                throw new IllegalArgumentException(
                        "La chambre n'est pas disponible."
                );
            }

            if (!checkDate(room, checkIn, checkOut)) {
                throw new InvalidReservationDateException(
                        "La chambre est déja réservée dans " + checkIn);
            }

            int days =(int) ChronoUnit.DAYS.between(checkIn, checkOut);

            BigDecimal totalPrice =
                    room.getPrice().multiply(BigDecimal.valueOf(days));

            Reservation reservation = new Reservation(UUID.randomUUID(), UUID.randomUUID().toString(), AuthService.getUserLogin().getId(), room.getRoomNumber(), checkIn, checkOut, numberOfGuests, days, totalPrice, ReservationStatus.CONFIRMED, LocalDate.now());

            this.repo.save(reservation);

            System.out.println("Reservation créée.");

        } catch (IllegalArgumentException e) {

            System.out.println("Erreur : " + e.getMessage());

        } catch (InvalidReservationDateException e) {

            System.out.println("Erreur : " + e.getMessage());
        }catch (RoomNotFoundException e){

        }
    }

    public List<Reservation> userReservation(){
        UUID userId = AuthService.getUserLogin().getId();
        List<Reservation>  reservations =  this.repo.findByUserId(userId);
        return reservations;
    }


    public void cancelReservation(String code) throws  ReservationNotFoundException{
        Reservation reservation = this.repo.findByCode(code).orElseThrow(() ->
                new ReservationNotFoundException("Réservation Not Found."));

        if(reservation.getStatus() != ReservationStatus.CONFIRMED){
            throw new IllegalArgumentException("Cette réservation est déja annulée ou Terminée");
        }
        if (reservation.getCheckIn().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("impossible annuler une reservation déja commencée");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);

        this.repo.save(reservation);

        System.out.println("Reservation Cancelled");
    }


    public void updateReservation(String code, String roomNumber, LocalDate checkIn, LocalDate checkout, int numberGuest) throws ReservationNotFoundException,
            InvalidReservationDateException , RoomNotFoundException
    {

        Reservation reservation = this.repo.findByCode(code).orElseThrow(() ->
                        new ReservationNotFoundException("Reservation not found")
                );

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new IllegalArgumentException("Cette réservation n'est pas confirmée.");
        }

        if (reservation.getCheckIn().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("impossible de modifier une réservation déja commencée.");
        }

        Room room = this.roomService.findRoom(roomNumber);

        if (numberGuest > room.getCapacity()) {
            throw new InvalidReservationDateException("Le nombre de personnes dépasse la capacité de la chambre");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        this.repo.save(reservation);
        boolean valid = this.checkDate(room, checkIn, checkout);

        if (!valid) {
            reservation.setStatus(ReservationStatus.CONFIRMED);
            this.repo.save(reservation);
            throw new InvalidReservationDateException("La chambre est déjà réservée dans cette période.");
        }

        reservation.setRoomNumber(roomNumber);
        reservation.setCheckIn(checkIn);
        reservation.setCheckOut(checkout);
        reservation.setNumberOfGuests(numberGuest);

        int nights = (int) ChronoUnit.DAYS.between(checkIn, checkout);

        reservation.setNumberOfNights(nights);

        BigDecimal totalPrice = room.getPrice().multiply(BigDecimal.valueOf(nights));

        reservation.setTotalPrice(totalPrice);

        this.repo.update(reservation);

        System.out.println("Reservation est modifiée.");
    }


}
