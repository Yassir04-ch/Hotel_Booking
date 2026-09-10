package Repository;
import Model.Room;
import Model.RoomStatus;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    void save(Room room);

    Optional<Room> findByRoomNumber(String roomNumber);

    List<Room> findAll();

    void updateStatus(Room room , RoomStatus status);

}
