package ca.mcgill.ecse321.mms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.mms.model.Room;

public interface RoomRepository extends CrudRepository<Room, String> {
    public Room findRoomByRoomID(int id);
    public List<Room> findAll();
}
    
