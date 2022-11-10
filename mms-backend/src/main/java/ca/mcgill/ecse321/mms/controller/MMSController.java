package ca.mcgill.ecse321.mms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.mms.dto.MMSResponseDto;
import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.service.MMSService;

@RestController
public class MMSController {

    @Autowired(required = true)
    MMSService mmsService;



    /**
     * Gets the museum in the museum repository (Only one in there)
     * 
     * @return - The fetched museum
     */
    @GetMapping("/museum")
    public ResponseEntity<MMSResponseDto> getMuseum() {
        MMS museum = mmsService.getMuseum();
        return new ResponseEntity<MMSResponseDto>(new MMSResponseDto(museum), HttpStatus.OK);
    }

    /**
     * Updates the fields of the museum object in the system
     * 
     * @param passFee - Pass fee to update
     * @return - Updated museum
     */
    @PutMapping("/museum/updateMuseumPassFee/{passFee}")
    public ResponseEntity<MMSResponseDto> updateMuseumPassFee(@PathVariable int passFee) {
        MMS museum = mmsService.updateMuseumPassFee(passFee);
        return new ResponseEntity<MMSResponseDto>(new MMSResponseDto(museum), HttpStatus.OK);
    }
    
}
