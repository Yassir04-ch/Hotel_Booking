package Service;

import Model.*;
import Repository.impl.InMemoryRoomRepository;
import Exception.RoomNotFoundException;
import Exception.RoomUnavailableException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomService {

    private InMemoryRoomRepository repo;

    public RoomService(){
        this.repo = new InMemoryRoomRepository();
    }

    public InMemoryRoomRepository getRepo(){
        return this.repo;
    }

    public Room findRoom(String roomNumber) throws RoomNotFoundException {
        Room room = this.repo.findByRoomNumber(roomNumber).orElseThrow(()->
                new RoomNotFoundException("Room not found"));
        return room;
    }

    public void creetRoom( String roomNumber , int capacity , BigDecimal price,RoomType type ){
        Room room = new Room(roomNumber, type, capacity, price, RoomStatus.AVAILABLE);
        this.repo.save(room);
        System.out.println("Room crée");
    }

    public void updateRoom( String roomNumber , int capacity , BigDecimal price,RoomType type )throws RoomNotFoundException{
        Room room = this.findRoom(roomNumber);
        room.setCapacity(capacity);
        room.setPrice(price);
        room.setType(type);
        this.repo.save(room);
        System.out.println("room update");
    }


    public List<Room> getAllRooms(){
        return this.repo.findAll();
    }

    public List<Room> getAvailableRoom(){
        List<Room> listRoom = new ArrayList<>();
        for (Room room : this.repo.getRooms().values()){
            if(room.getStatus() == RoomStatus.AVAILABLE){
                listRoom.add(room);
            }
        }
        return listRoom;
    }

    public void updateStatusAvailable(String roomNumber) throws RoomNotFoundException,RoomUnavailableException{
        Room room = this.findRoom(roomNumber);
        if(room.getStatus() == RoomStatus.AVAILABLE){
            throw new RoomUnavailableException("Chombre déja en AVAILABLE");
        }
        this.repo.updateStatus(room , RoomStatus.AVAILABLE );
    }

    public void updateStatusMAINTENANCE(String roomNumber) throws  RoomNotFoundException,RoomUnavailableException{
        Room room = this.findRoom(roomNumber);
        if(room.getStatus() == RoomStatus.MAINTENANCE){
            throw new RoomUnavailableException("Chombre déja en MAINTENANCE");
        }
        this.repo.updateStatus(room , RoomStatus.MAINTENANCE );
    }

    public void afficherRooms(List<Room> rooms){
        for (Room room : rooms){
            System.out.println("=======================");
            System.out.println("roomNumber : " + room.getRoomNumber());
            System.out.println("capacity : " + room.getCapacity());
            System.out.println("price : " + room.getPrice());
            System.out.println("type : " + room.getType());
            System.out.println("status : " + room.getStatus());
            System.out.println("=======================");
        }
    }

    public void filterParType(RoomType type){
        List<Room> rooms = this.repo.findAll().stream().filter(e->e.getType() == type).toList();
        if(rooms.isEmpty()){
            System.out.println("Aucune room ");
            return;
        }
       this.afficherRooms(rooms);
    }


    public void filterParPrix(BigDecimal prix){
        List<Room> rooms = this.repo.findAll().stream().filter(e -> e.getPrice().compareTo(prix) <= 0).toList();
        if(rooms.isEmpty()){
            System.out.println("Aucune room");
            return;
        }
       this.afficherRooms(rooms);
    }

    public void filterCapacity(int capacity){
        List<Room> rooms = this.repo.findAll().stream().filter(e -> e.getCapacity() == capacity).toList();
        if(rooms.isEmpty()){
            System.out.println("Aucune room");
            return;
        }
        this.afficherRooms(rooms);
    }



}
