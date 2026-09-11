package Service;

import Model.Reservation;
import Model.Room;
import Model.RoomStatus;
import Model.User;
import Repository.impl.InMemoryRoomRepository;
import Exception.RoomNotFoundException;

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
                 new IllegalArgumentException("Room not found"));
         return room;
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

    public void updateStatusAvailable(String roomNumber) throws RoomNotFoundException{
        Room room = this.findRoom(roomNumber);
        this.repo.updateStatus(room , RoomStatus.AVAILABLE );
    }

    public void updateStatusMAINTENANCE(String roomNumber) throws  RoomNotFoundException{
        Room room = this.findRoom(roomNumber);
        this.repo.updateStatus(room , RoomStatus.MAINTENANCE );
    }



}
