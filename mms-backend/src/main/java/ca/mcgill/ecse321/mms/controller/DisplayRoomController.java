package ca.mcgill.ecse321.mms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.mms.dto.DisplayRoomRequestDto;
import ca.mcgill.ecse321.mms.dto.DisplayRoomResponseDto;
import ca.mcgill.ecse321.mms.model.DisplayRoom;
import ca.mcgill.ecse321.mms.service.DisplayRoomService;

@RestController
@Validated
public class DisplayRoomController {

    @Autowired(required = true)
    DisplayRoomService displayRoomService;

    /**
     *  * Creates a displayRoom using Dto data after HTTP request and puts it into HTTP response
     * 
     * @param displayRoomRequest - The DisplayRoomRequestDto data
     * @return - The created displayRoom as Dto
     * @throws IllegalArgumentException
     */
    @PostMapping("/displayRoom")
    public ResponseEntity<DisplayRoomResponseDto> createDisplayRoom(@Valid @RequestBody DisplayRoomRequestDto displayRoomRequest) {
            DisplayRoom displayRoomToCreate = displayRoomRequest.toModel();
            DisplayRoom createdDisplayRoom = displayRoomService.createDisplayRoom(displayRoomToCreate);
            DisplayRoomResponseDto response = new DisplayRoomResponseDto(createdDisplayRoom);  
            return new ResponseEntity<DisplayRoomResponseDto>(response, HttpStatus.CREATED);       
    }

    /**
     * Gets a displayRoom by roomID after HTTP request and puts into the HTTP response
     * 
     * @param roomID - roomID of the displayRoom to get
     * @return - The fetched displayRoom
     */
    @GetMapping("/displayRoom/roomID/{roomID}")
	public ResponseEntity<DisplayRoomResponseDto> getDisplayRoomById(@PathVariable int roomID) {

            DisplayRoom displayRoomToGet = displayRoomService.getDisplayRoomById(roomID);
            return new ResponseEntity<DisplayRoomResponseDto>(new DisplayRoomResponseDto(displayRoomToGet), HttpStatus.OK);
	}


    /**
     * Gets displayRoom by roomNumber after HTTP request and puts into the HTTP response
     * 
     * @param roomNumber - roomNumber of the displayRoom to get
     * @return - The fetched displayRoom
     */
    @GetMapping("/displayRoom/roomNumber/{roomNumber}")
	public ResponseEntity<DisplayRoomResponseDto> getDisplayRoomByRoomNumber(@PathVariable int roomNumber) {
      
        DisplayRoom displayRoomToGet = displayRoomService.getDisplayRoomByRoomNumber(roomNumber);
        return new ResponseEntity<DisplayRoomResponseDto>(new DisplayRoomResponseDto(displayRoomToGet), HttpStatus.OK);
            
	}


    /**
     * Returns all rooms after HTTP request and puts it into a list of HTTP response
     * 
     * @return - All rooms in the room repository
     */
    @GetMapping("/displayRooms")
    public List<ResponseEntity<DisplayRoomResponseDto>> getAllDisplayRooms() {

        List<ResponseEntity<DisplayRoomResponseDto>> displayRoomDTOList = new ArrayList<>();
        for(DisplayRoom displayRoom: displayRoomService.getAllDisplayRooms()) {
            displayRoomDTOList.add(new ResponseEntity<DisplayRoomResponseDto>(new DisplayRoomResponseDto(displayRoom), HttpStatus.OK));
        }

        return displayRoomDTOList;

}


    
}
