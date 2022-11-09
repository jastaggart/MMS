package ca.mcgill.ecse321.mms.dto;
import java.util.List;

import ca.mcgill.ecse321.mms.model.DisplayRoom;
import ca.mcgill.ecse321.mms.model.MMS;


public class DisplayRoomResponseDto {

    private int roomID;
    private MMS mms;
    private String size;
    private int maximumCapacity;
    private int displayRoomNumber;

    public DisplayRoomResponseDto(DisplayRoom displayRoom) {
        this.roomID = displayRoom.getRoomID();
        this.mms = displayRoom.getMuseumManagementSystem();
        this.size = displayRoom.getSize();
        this.maximumCapacity = displayRoom.getMaximumCapacity();
        this.displayRoomNumber = displayRoom.getDisplayRoomNumber();
    }

    public int getRoomID() {
        return this.roomID;
    }

    public MMS getMMS() {
        return this.mms;
    }

    public String getSize() {
        return this.size;
    }

    public int getMaximumCapacity() {
        return this.maximumCapacity;
    }

    public int getDisplayRoomNumber() {
        return this.displayRoomNumber;
    }

}
