package Repository.impl;

import Model.Room;
import Model.RoomStatus;
import Repository.RoomRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class InMemoryRoomRepository implements RoomRepository {
    private HashMap<String , Room> rooms = new HashMap<>();

    public  HashMap<String , Room> getRooms(){
        return this.rooms;
    }

    @Override
    public void save(Room room){
        this.rooms.put(room.getRoomNumber() , room);
    }

    @Override
    public Optional<Room> findByRoomNumber(String roomNumber){
         return  Optional.ofNullable(this.rooms.get(roomNumber));
    }

    @Override
    public List<Room> findAll(){
        List<Room> listRooms = new ArrayList<>();
        for (Room room : this.rooms.values()){
            listRooms.add(room);
        }
        return listRooms;
    }

    @Override
    public void updateStatus(Room room , RoomStatus status){
        room.setStatus(status);
        rooms.put(room.getRoomNumber(), room);
    }
}
