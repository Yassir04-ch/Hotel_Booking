package Repository.impl;

import Model.Reservation;
import Repository.ReservationRepository;

import java.util.*;

public class InMemoryReservationRepository implements ReservationRepository {

    private HashMap<UUID , Reservation> reservations = new HashMap<>();


    @Override
    public void save(Reservation reservation){
        this.reservations.put(reservation.getId(), reservation);
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
       return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public Optional<Reservation> findByCode(String code) {
        for (Reservation reservation : this.reservations.values()) {
            if (reservation.getReservationCode().equals(code)) {
                return Optional.of(reservation);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Reservation> findByUserId(UUID userId) {
        List<Reservation> listReservations = new ArrayList<>();
        for(Reservation reservation : this.reservations.values()){
            if(reservation.getUserId().equals(userId)){
                listReservations.add(reservation);
            }
        }
        return listReservations;
    }

    @Override
    public List<Reservation> findByRoomNumber(String roomNumber) {
        List<Reservation> listReservations = new ArrayList<>();
        for(Reservation reservation : this.reservations.values()){
            if(reservation.getRoomNumber().equals(roomNumber)){
                listReservations.add(reservation);
            }
        }
        return listReservations;
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> listReservation = new ArrayList<>();
        for (Reservation reservation : this.reservations.values()){
            listReservation.add(reservation);
        }
        return listReservation;
    }
}
