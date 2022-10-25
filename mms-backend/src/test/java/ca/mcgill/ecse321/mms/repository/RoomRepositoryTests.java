package ca.mcgill.ecse321.mms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Time;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.mms.model.DisplayRoom;
import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.model.RoomSize;
import ca.mcgill.ecse321.mms.model.Storage;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RoomRepositoryTests {

    @Autowired
    private MMSRepository mmsRepository;

    @Autowired
    private RoomRepository roomRepository;

    @AfterEach
	public void clearDatabase() {
        roomRepository.deleteAll();
		mmsRepository.deleteAll();
	}

    @Test
    public void testPersistAndLoadRoom() {

        // Creating the fields that will be set to the MMS instance
        Time openingTime = Time.valueOf("08:00:00");
        Time closingTime = Time.valueOf("17:00:00");
        int museumPassFee = 25;

        // Creating the MMS instance to which the rooms will be linked to 
        MMS museum = new MMS();

        // Setting the fields of the created MMS instance
        museum.setOpeningHours(openingTime);
        museum.setClosingHours(closingTime);
        museum.setPassFee(museumPassFee);

        // Saving the MMS instance in the mmsRepository table
        museum =  mmsRepository.save(museum);
    
        // Creating the fields that will be set to the created DisplayRoom objects
        int maximumCapacitySmallRoom = 200;
        int displayRoomNumberSmallRoom = 1;
        int maximumCapacityBigRoom = 300;
        int displayRoomNumberBigRoom = 2;
        
        // Creating the DisplayRoom and Storage Objects
        DisplayRoom smallDisplayRoom = new DisplayRoom();
        DisplayRoom bigDisplayRoom = new DisplayRoom();
        Storage storage = new Storage();

        // Setting the fields of the created DisplayRoom Instances
        smallDisplayRoom.setSize(RoomSize.Small.name());
        smallDisplayRoom.setMaximumCapacity(maximumCapacitySmallRoom);
        smallDisplayRoom.setDisplayRoomNumber(displayRoomNumberSmallRoom);
        smallDisplayRoom.setMuseumManagementSystem(museum);

        bigDisplayRoom.setSize(RoomSize.Big.name());
        bigDisplayRoom.setMaximumCapacity(maximumCapacityBigRoom);
        bigDisplayRoom.setDisplayRoomNumber(displayRoomNumberBigRoom);
        bigDisplayRoom.setMuseumManagementSystem(museum);

        storage.setMuseumManagementSystem(museum);

        // Saving the DisplayRoom instance in the roomRepository table
        smallDisplayRoom = roomRepository.save(smallDisplayRoom);
        bigDisplayRoom = roomRepository.save(bigDisplayRoom);
        storage = roomRepository.save(storage);

        // Fetching the roomID of the newly created DisplayRoom object that was saved in the roomRepository table
        int smallRoomID = smallDisplayRoom.getRoomID();
        int bigRoomID = bigDisplayRoom.getRoomID();
        int storageID = storage.getRoomID();

        smallDisplayRoom = null;
        bigDisplayRoom = null;
        storage = null;
        museum = null;

        // Retrieving the DisplayRoom object from the roomRepository table using the method defined in the RoomRepository interface
        smallDisplayRoom = (DisplayRoom) roomRepository.findRoomByRoomID(smallRoomID);
        bigDisplayRoom = (DisplayRoom) roomRepository.findRoomByRoomID(bigRoomID);
        storage = (Storage) roomRepository.findRoomByRoomID(storageID);
       

        // Assertion Tests
        assertNotNull(smallDisplayRoom);
        assertNotNull(smallDisplayRoom.getMuseumManagementSystem());
        assertEquals(smallRoomID, smallDisplayRoom.getRoomID());
        assertEquals("Small", smallDisplayRoom.getSize());
        assertEquals(maximumCapacitySmallRoom, smallDisplayRoom.getMaximumCapacity());
        assertEquals(displayRoomNumberSmallRoom, smallDisplayRoom.getDisplayRoomNumber());

        assertNotNull(bigDisplayRoom);
        assertNotNull(bigDisplayRoom.getMuseumManagementSystem());
        assertEquals(bigRoomID, bigDisplayRoom.getRoomID());
        assertEquals("Big", bigDisplayRoom.getSize());
        assertEquals(maximumCapacityBigRoom, bigDisplayRoom.getMaximumCapacity());
        assertEquals(displayRoomNumberBigRoom, bigDisplayRoom.getDisplayRoomNumber());

        assertNotNull(storage);
        assertNotNull(storage.getMuseumManagementSystem());
        assertEquals(storageID, storage.getRoomID());

    }
}