package Service;

import Model.*;
import Repository.impl.InMemoryReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import Exception.InvalidReservationDateException;
import  Exception.ReservationNotFoundException;
import  Exception.RoomNotFoundException;
import  Exception.RoomUnavailableException;

public class ReservationService {
    private InMemoryReservationRepository repo ;
    private RoomService roomService ;

    public ReservationService(RoomService roomService){
        this.repo = new InMemoryReservationRepository();
        this.roomService =roomService ;
    }


    public void validateDates(LocalDate checkIn, LocalDate checkOut
                ) throws InvalidReservationDateException {

        if (checkIn.isBefore(LocalDate.now())) {
            throw new InvalidReservationDateException("Date checkin invalide.");
        }

        if (!checkOut.isAfter(checkIn)) {
            throw new InvalidReservationDateException("Date checkout invalide.");
        }
    }

    public boolean checkDate(Room room, LocalDate checkIn, LocalDate checkOut){

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
                         int numberOfGuests) throws InvalidReservationDateException , RoomNotFoundException , RoomUnavailableException {

        this.validateDates(checkIn, checkOut);

        Room room = roomService.findRoom(roomNumber);

            if (numberOfGuests <= 0) {
                throw new InvalidReservationDateException("Le nombre de personnes invalide.");
            }

            if (numberOfGuests > room.getCapacity()) {
                throw new InvalidReservationDateException("Le nombre de personnes dépasse la capacité de la chambre.");
            }

            if (room.getStatus() != RoomStatus.AVAILABLE) {
                throw new RoomUnavailableException("La chambre n'est pas disponible.");
            }

            if (!checkDate(room, checkIn, checkOut)) {
                throw new RoomUnavailableException(
                        "La chambre est déja réservée dans " + checkIn);
            }

            int days =(int) ChronoUnit.DAYS.between(checkIn, checkOut);

            BigDecimal totalPrice =
                    room.getPrice().multiply(BigDecimal.valueOf(days));

            Reservation reservation = new Reservation(UUID.randomUUID(), UUID.randomUUID().toString(), AuthService.getUserLogin().getId(), room.getRoomNumber(), checkIn, checkOut, numberOfGuests, days, totalPrice, ReservationStatus.CONFIRMED, LocalDate.now());

            this.repo.save(reservation);

            System.out.println("Reservation crée.");

    }

    public List<Reservation> userReservation(){
        UUID userId = AuthService.getUserLogin().getId();
        List<Reservation>  reservations =  this.repo.findByUserId(userId);
        return reservations;
    }


    public void cancelReservation(String code) throws  ReservationNotFoundException {
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

    public List<Room> roomAvailableDate(LocalDate checkin , LocalDate chechout)
            throws RoomUnavailableException , InvalidReservationDateException{

        this.validateDates(checkin , chechout);

        List<Room> listRoom = new ArrayList<>();
        List<Room> rooms = this.roomService.getAllRooms().stream().
                filter(room -> room.getStatus() == RoomStatus.AVAILABLE).toList();

        for (Room room : rooms) {
                if (this.checkDate(room, checkin, chechout)) {
                    listRoom.add(room);
                }
        }

        if (listRoom.isEmpty()) {
            throw new RoomUnavailableException("Aucune chambre disponible pour cette période.");
        }

        return listRoom;
    }

    public List<Reservation> sortReservationsByCreatedAt(){
        Person user = AuthService.getUserLogin();
        List<Reservation> reservations = this.repo.findByUserId(user.getId());
        reservations.sort((a,b) -> a.getCreatedAt().compareTo(b.getCreatedAt()));
        return reservations;
    }

    public List<Reservation> sortReservationsByCheckIn(){
        Person user = AuthService.getUserLogin();
        List<Reservation> reservations = this.repo.findByUserId(user.getId());
        reservations.sort((a,b) -> a.getCheckIn().compareTo(b.getCheckIn()) );
        return reservations;
    }

    public void getAll(){
        List<Reservation> reservations = this.repo.findAll();
        if(reservations.isEmpty()){
            System.out.println("Aucune Reservation");
            return;
        }
        for(Reservation reservation : reservations){
            System.out.println("Code de réservation : " + reservation.getReservationCode());
            System.out.println("UserId : " + reservation.getUserId());
            System.out.println("Numéro de chambre : " + reservation.getRoomNumber());
            System.out.println("Date de départ : " + reservation.getCheckIn());
            System.out.println("Date d'arrivée : " + reservation.getCheckOut());
            System.out.println("Nombre de personnes : " + reservation.getNumberOfGuests());
            System.out.println("Nombre de nuits : " + reservation.getNumberOfNights());
            System.out.println("Prix total : " + reservation.getTotalPrice() + " DH");
            System.out.println("Statut : " + reservation.getStatus());
            System.out.println("Date de création : " + reservation.getCreatedAt());        }

    }

    public void reservationStatistique(){
        List<Reservation> reservations =  this.repo.findAll();
        int totaleReservation =(int) reservations.stream().count();
        int annuleReservation = (int) reservations.stream().filter(e -> e.getStatus() == ReservationStatus.CANCELLED).count();
        BigDecimal revenuTotal = BigDecimal.ZERO;
        List<Reservation> reservationsConfirm = reservations.stream().filter(e ->e.getStatus() == ReservationStatus.CONFIRMED).toList();
        for(Reservation reservation : reservationsConfirm){
            revenuTotal = revenuTotal.add(reservation.getTotalPrice());
        }

        System.out.println("réservations totales : "+totaleReservation);
        System.out.println("réservations annulées : "+annuleReservation);
        System.out.println("revenu total : "+revenuTotal);

    }

    public  void RoomPlusReserver(){
        List<Reservation> reservations =  this.repo.findAll();
        HashMap<String , Integer> roomsReserve = new HashMap<>();

        for(Reservation reservation : reservations) {
            if(!roomsReserve.containsKey(reservation.getRoomNumber())){
                roomsReserve.put(reservation.getRoomNumber(),1);
            }else{
                roomsReserve.put(reservation.getRoomNumber(),roomsReserve.get(reservation.getRoomNumber()) + 1);
            }
        }
        String numberRoom =  this.getRoomPlusReserver(roomsReserve);

        if (numberRoom == null) {
            System.out.println("Aucune réservation trouvée");
            return;
        }

        try{
          Room room = this.roomService.findRoom(numberRoom);
            System.out.println("=======================");
            System.out.println("roomNumber : " + room.getRoomNumber());
            System.out.println("capacity : " + room.getCapacity());
            System.out.println("price : " + room.getPrice());
            System.out.println("type : " + room.getType());
            System.out.println("status : " + room.getStatus());
            System.out.println("=======================");

        }catch (RoomNotFoundException e){
            System.out.println("Erreur : "+e.getMessage());
        }
    }

    public String getRoomPlusReserver(HashMap<String ,Integer> rooms){
        String  maxRoom = "";
        int max = 0;
        for(Map.Entry<String , Integer> room : rooms.entrySet()){
            if( room.getValue() > max){
                max = room.getValue();
                maxRoom = room.getKey();
            }
        }
        return maxRoom;
    }

}
