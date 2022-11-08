package ca.mcgill.ecse321.mms.dto;

import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.model.Storage;

public class StorageDTO {

    private int roomID;
    private MMS mms;

    public StorageDTO(Storage displayRoom) {
        this.roomID = displayRoom.getRoomID();
        this.mms = displayRoom.getMuseumManagementSystem();
    }

    public int getRoomID() {
        return this.roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public MMS getMMS() {
        return this.mms;
    }

    public void setMMS(MMS mms) {
        this.mms = mms;
    }
    
}
