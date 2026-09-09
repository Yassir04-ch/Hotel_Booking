package Service;

import Model.Room;
import Repository.impl.InMemoryRoomRepository;

import java.util.List;

public class RoomService {

    private InMemoryRoomRepository repo;

    public RoomService(){
        this.repo = new InMemoryRoomRepository();
    }

    public InMemoryRoomRepository getRepo(){
        return this.repo;
    }

    public Room findRoom(String roomNumber){
         Room room = this.repo.findByRoomNumber(roomNumber).orElseThrow(()->
                 new IllegalArgumentException("Room not found"));
         return room;
    }

    public List<Room> getAllRooms(){
        return this.repo.findAll();
    }



}
