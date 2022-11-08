package ca.mcgill.ecse321.mms.dto;
import ca.mcgill.ecse321.mms.model.DisplayRoom;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class DisplayRoomRequestDto {

    @NotNull
	@NotBlank
    private String size;
    @NotNull
    private int maximumCapacity;
    @NotNull
    private int displayRoomNumber;

    public String getSize() {
        return this.size;
    }

    public void setSize(String roomSize) {
        this.size = roomSize;
    }

    public int getMaximumCapacity() {
        return this.maximumCapacity;
    }

    public void setMaximumCapacity(int maxCapacity) {
        this.maximumCapacity = maxCapacity;
    }

    public int getDisplayRoomNumber() {
        return this.displayRoomNumber;
    }

    public void setDisplayRoomNumber(int roomNumber) {
        this.displayRoomNumber = roomNumber;
    }

    public DisplayRoom toModel() {
		DisplayRoom displayRoom = new DisplayRoom();
        displayRoom.setSize(this.size);
        displayRoom.setMaximumCapacity(this.maximumCapacity);
        displayRoom.setDisplayRoomNumber(this.displayRoomNumber);
		return displayRoom;
	}

}
