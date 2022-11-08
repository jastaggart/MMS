package ca.mcgill.ecse321.mms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping("/displayRoom")
    public ResponseEntity<DisplayRoomResponseDto> createDisplayRoom(@Valid @RequestBody DisplayRoomRequestDto displayRoomRequest) {
        DisplayRoom displayRoomToCreate = displayRoomRequest.toModel();
        DisplayRoom createdDisplayRoom = displayRoomService.createDisplayRoom(displayRoomToCreate);
        DisplayRoomResponseDto response = new DisplayRoomResponseDto(createdDisplayRoom);
        return new ResponseEntity<DisplayRoomResponseDto>(response, HttpStatus.CREATED);        
    }



    
}
