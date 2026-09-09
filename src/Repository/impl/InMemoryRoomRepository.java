package Repository.impl;

import Model.Room;
import Repository.RoomRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class InMemoryRoomRepository implements RoomRepository {
    private HashMap<String , Room> rooms = new HashMap<>();

    @Override
    public void save(Room room){
        this.rooms.put(room.getRoomNumber() , room);
    }

    @Override
    public Optional<Room> findByRoomNumber(String roomNumber){
         return  Optional.ofNullable(rooms.get(roomNumber));
    }

    @Override

    public List<Room> findAll(){
        List<Room> listRooms = new ArrayList<>();
        for (Room room : rooms.values()){
            listRooms.add(room);
        }
        return listRooms;
    }
}
