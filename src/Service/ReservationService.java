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
import java.util.UUID;
import Exception.InvalidReservationDateException;
import  Exception.ReservationNotFoundException;

public class ReservationService {
    private InMemoryReservationRepository repo ;
    private RoomService roomService ;

    public ReservationService(RoomService roomService){
        this.repo = new InMemoryReservationRepository();
        this.roomService =roomService ;
    }

    public void createReservation(String roomNumber, LocalDate checkIn, LocalDate checkOut,
               int numberOfGuests) throws InvalidReservationDateException {
        try{
            Room room = roomService.findRoom(roomNumber);

            List<Reservation> listReservations =
                    this.repo.findByRoomNumber(room.getRoomNumber());

            if (checkIn.isBefore(LocalDate.now())) {
                throw new InvalidReservationDateException(
                        "Date check-in invalide."
                );
            }

            if (!checkOut.isAfter(checkIn)) {
                throw new InvalidReservationDateException(
                        "Date check-out invalide."
                );
            }

            if (numberOfGuests <= 0) {
                throw new InvalidReservationDateException(
                        "Le nombre de personnes invalide"
                );
            }

            if (numberOfGuests > room.getCapacity()) {
                throw new InvalidReservationDateException(
                        "Le nombre de personnes invalide"
                );
            }

            if (room.getStatus() != RoomStatus.AVAILABLE) {
                throw new IllegalArgumentException(
                        "La chambre n'est pas disponible."
                );
            }

            for (Reservation reservation : listReservations) {

                if (reservation.getStatus() == ReservationStatus.CONFIRMED) {
                    if (checkIn.isBefore(reservation.getCheckOut()) && checkOut.isAfter(reservation.getCheckIn())) {
                        throw new InvalidReservationDateException("La chambre est déjà réservée dans la date "
                                + reservation.getCheckIn() +"--" +reservation.getCheckOut() );
                    }
                }
            }

            long days = ChronoUnit.DAYS.between(checkIn, checkOut);
            int daynuit = (int) days;

            BigDecimal totalPrice = room.getPrice().multiply(BigDecimal.valueOf(days));

            Reservation reservation = new Reservation(UUID.randomUUID(),UUID.randomUUID().toString() ,
                    AuthService.getUserLogin().getId(), room.getRoomNumber(), checkIn, checkOut, numberOfGuests,
                    daynuit, totalPrice, ReservationStatus.CONFIRMED, LocalDate.now()
            );

            this.repo.save(reservation);

            System.out.println("Reservation crée");

        }catch (IllegalArgumentException e){
           System.out.println("Erreur : " + e.getMessage());
        }catch (InvalidReservationDateException e){
            System.out.println("Erreur : "+e.getMessage());
        }

    }

    public List<Reservation> userReservation(){
        UUID userId = AuthService.getUserLogin().getId();
        List<Reservation>  reservations =  this.repo.findByUserId(userId);
        return reservations;
    }

    public void cancelReservation(String code) throws  ReservationNotFoundException{
        Reservation reservation = this.repo.findByCode(code).orElseThrow(() ->
                new ReservationNotFoundException("Reservation Not Found."));

        if(reservation.getStatus() != ReservationStatus.CONFIRMED){
            throw new IllegalArgumentException("Cette réservation est déjà annulée ou Terminée");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);

        this.repo.save(reservation);

        System.out.println("Reservation Cancelled");
    }

}
