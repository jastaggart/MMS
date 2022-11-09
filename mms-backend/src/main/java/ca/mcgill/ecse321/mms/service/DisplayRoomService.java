package ca.mcgill.ecse321.mms.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.mms.exception.MMSException;
import ca.mcgill.ecse321.mms.model.DisplayRoom;
import ca.mcgill.ecse321.mms.model.Room;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.RoomRepository;

@Service
public class DisplayRoomService {

    @Autowired(required = true)
    RoomRepository roomRepository;

    @Autowired(required = true)
    MMSRepository mmsRepository;

    /**
     * Creates a displayRoom using specific displayRoom data and saves it in the roomRepository
     * @param displayRoom - DisplayRoom object to create and save in the roomRepository
     * @return - The created DisplayRoom
     */
    @Transactional
    public DisplayRoom createDisplayRoom(DisplayRoom displayRoom) {

        parameterCheckForDisplayRoom(displayRoom.getSize(), displayRoom.getMaximumCapacity(), displayRoom.getDisplayRoomNumber());
        checkForMaxNumberOfRooms(displayRoom.getSize());
        displayRoom.setMuseumManagementSystem(mmsRepository.findMMSByMuseumID(1));
        displayRoom = roomRepository.save(displayRoom);
        return displayRoom;
    }


    /**
     * Gets the displayRoom by its roomID attribute
     * 
     * @param roomID - DisplayRoom roomID
     * @return - The fetched displayRoom
     */
    @Transactional
    public DisplayRoom getDisplayRoomById(int roomID) {


        if(roomID == 1) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No room exists with provided roomID");
        }

        DisplayRoom fetchedDisplayRoom = (DisplayRoom) roomRepository.findRoomByRoomID(roomID);
        if(fetchedDisplayRoom == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No room exists with provided roomID");
        }
        return fetchedDisplayRoom;
    }

    /**
     * Gets the displayRoom by its roomNumber attribute
     * 
     * @param roomNumber - DisplayRoom roomNumber
     * @return - The fetched DisplayRoom
     */
    @Transactional
    public DisplayRoom getDisplayRoomByRoomNumber(int roomNumber) {

        DisplayRoom retrievedRoom = null;
        List<Room> rooms = roomRepository.findAll();

        for(int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).getRoomID() != 1 &&((DisplayRoom) rooms.get(i)).getDisplayRoomNumber() == roomNumber) {
                retrievedRoom = (DisplayRoom) rooms.get(i);
            }
        }

        if(retrievedRoom == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No room exists with provided roomNumber");
        }

        return retrievedRoom;
    }


    /**
     * Retrieves all the display rooms in the roomRepository
     * 
     * @return - List of DisplayRooms retrieved
     */
    @Transactional
    public List<DisplayRoom> getAllDisplayRooms() {
        List<DisplayRoom> displayRooms = new ArrayList<>();
        List<Room> rooms = roomRepository.findAll();
        
        for(int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).getRoomID() != 1) {
                displayRooms.add((DisplayRoom) rooms.get(i));
            }
        }

        return displayRooms;

    }


    /**
     * Checks whether when creating a room of Size size, there is already the maximum number of rooms of that size
     * 
     * @param size - Size of room to create
     */
    private void checkForMaxNumberOfRooms(String size) {

        String errorMessage = "";
        int countSmallRooms = 0;
        int countBigRooms = 0;
        List<Room> rooms = roomRepository.findAll();
        
        for(int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).getRoomID() != 1) {
                if(((DisplayRoom) rooms.get(i)).getSize().equals("Big")) {
                countBigRooms++;
                } else if(((DisplayRoom) rooms.get(i)).getSize().equals("Small")) {
                    countSmallRooms++;
                }
            }
        }

        if(size.equals("Big") && countBigRooms >= 5) {
            errorMessage = "Museum already has the maximum number of Big Display Rooms";
        }

        if(size.equals("Small") && countSmallRooms >= 5) {
            errorMessage = "Museum already has the maximum number of Small Display Rooms";
        }

        if(countSmallRooms + countBigRooms >= 10) {
            errorMessage = "Museum has reached the maximum number of display rooms";
        }

        if(errorMessage.length() != 0) {
            throw new MMSException(HttpStatus.BAD_REQUEST, errorMessage);
        }

    }


    /**
     * Checks whether when trying to create a room, the passed in parameters are valid
     * 
     * @param size - Size of room to create
     * @param maximumCapacity - Maximum capacity of room to create
     * @param displayRoomNumber - Display room number of room to create
     */
    private void parameterCheckForDisplayRoom(String size, int maximumCapacity, int displayRoomNumber) {

        String errorMessage = "";
        List<Room> rooms = roomRepository.findAll();
        
        for(int i = 0; i < rooms.size(); i++) {

            if(rooms.get(i).getRoomID() != 1) {
                if(((DisplayRoom) rooms.get(i)).getDisplayRoomNumber() == displayRoomNumber) {
                    errorMessage = "Display room with display room number " + displayRoomNumber + " already exists";
                }
            }
        }

        if(!size.equals("Big") && !size.equals("Small")) {
            errorMessage = "Size of room must be Big or Small";
        }

        if((size.equals("Small") && maximumCapacity != 200) || (size.equals("Big") && maximumCapacity != 300)) {
            errorMessage = "Incompatible entries for room size and maximum capacity";
        }

        if(displayRoomNumber < 0) {
            errorMessage = "Display room number cannot be less than 0";
        }
            
        if(errorMessage.length() != 0) {
            throw new MMSException(HttpStatus.BAD_REQUEST, errorMessage);
        }

    }
}
    
