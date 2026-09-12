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




}
