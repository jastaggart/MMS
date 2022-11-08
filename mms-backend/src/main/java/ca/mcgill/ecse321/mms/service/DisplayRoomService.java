package ca.mcgill.ecse321.mms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public DisplayRoom createDisplayRoom(DisplayRoom displayRoom) {

        parameterCheckForDisplayRoom(displayRoom.getSize(), displayRoom.getMaximumCapacity(), displayRoom.getDisplayRoomNumber());
        displayRoom.setMuseumManagementSystem(mmsRepository.findMMSByMuseumID(1));
        displayRoom = roomRepository.save(displayRoom);
        return displayRoom;
    }
















    private void parameterCheckForDisplayRoom(String size, int maximumCapacity, int displayRoomNumber){

        String errorMessage = "";
        List<Room> rooms = roomRepository.findAll();
        
        for(int i = 0; i < rooms.size(); i++) {
            if(((DisplayRoom) rooms.get(i)).getDisplayRoomNumber() == displayRoomNumber) {
                errorMessage = "Display room with display room number " + displayRoomNumber + " already exists";
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
            throw new IllegalArgumentException(errorMessage);
        }
    }
    
}
