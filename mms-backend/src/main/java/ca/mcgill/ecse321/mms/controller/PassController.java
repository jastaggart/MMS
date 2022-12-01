package ca.mcgill.ecse321.mms.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.mms.dto.PassRequestDto;
import ca.mcgill.ecse321.mms.dto.PassResponseDto;
import ca.mcgill.ecse321.mms.model.Pass;
import ca.mcgill.ecse321.mms.model.Visitor;
import ca.mcgill.ecse321.mms.repository.VisitorRepository;
import ca.mcgill.ecse321.mms.service.PassService;

@RestController
@Validated
@CrossOrigin(origins = "http://127.0.0.1:8087")
public class PassController {
    
    @Autowired(required = true)
    PassService passService;

	@Autowired
	VisitorRepository visitorRepository;

    /**
     * Gets a list of all Passes and puts into the HTTP response
     * 
     * @return - The fetched list of Passes
     */
    @GetMapping("/passes")
    public ResponseEntity<List<PassResponseDto>> getAllPasses() {
		List<PassResponseDto> passes = convListToDto(passService.getAllPasses());
		return new ResponseEntity<List<PassResponseDto>>(passes, HttpStatus.OK);
	}

    /**
     * Gets a Pass by passID after HTTP request and puts into the HTTP response
     * 
     * @param id - passID of the pass to get
     * @return - The fetched Pass
     */
    @GetMapping("/pass/{id}")
    public ResponseEntity<PassResponseDto> getPassById(@PathVariable int id) {
		PassResponseDto pass = new PassResponseDto(passService.getPassById(id));
		return new ResponseEntity<PassResponseDto>(pass, HttpStatus.OK);
	}

    /**
     * Gets a Pass by passID after HTTP request and puts into the HTTP response
     * 
     * @param id - passID of the Pass to get
     * @return - The fetched Pass
     */
    @GetMapping("/pass/visitor/{visitorID}") 
	public ResponseEntity<List<PassResponseDto>> getPassesByVisitorId(@PathVariable int visitorID) {
		List<PassResponseDto> passes = convListToDto(passService.getPassesByVisitorId(visitorID));
		return new ResponseEntity<List<PassResponseDto>>(passes, HttpStatus.OK);
	}

     /**
     * Gets a Pass by passDate after HTTP request and puts into the HTTP response
     * 
     * @param date - passDate of the Pass to get
     * @return - The fetched Pass
     * @throws ParseException
     */
    @GetMapping("/pass/date/{passDate}") 
	public ResponseEntity<List<PassResponseDto>> getPassesByDate(@PathVariable String passDate) throws ParseException {
		List<PassResponseDto> passes = convListToDto(passService.getPassesByDate(passDate));
		return new ResponseEntity<List<PassResponseDto>>(passes, HttpStatus.OK);
	}


    /**
     * Creates a Pass using Dto data after HTTP request and puts it into HTTP response
     * 
     * @param passRequest - The PassRequestDto data
     * @return - The created Pass as Dto
     */
	@PostMapping("/pass")
    public ResponseEntity<PassResponseDto> createPass(@Valid @RequestBody PassRequestDto passRequest) throws ParseException {
			Visitor visitorRequestingPass = visitorRepository.findVisitorByVisitorID(passRequest.getVisitorID());		
            Pass createdPass = passService.createPass(visitorRequestingPass.getVisitorID(), passRequest.getPassDate());
            PassResponseDto response = new PassResponseDto(createdPass);  
            return new ResponseEntity<PassResponseDto>(response, HttpStatus.CREATED);       
    }

    /**
     * Converts a list of Passes to a list of PassResponseDto 
     * Note: This is a private helper method
	 *
     * @param passes - list of Passes
     * @return - list of PassResponseDtos
     */
	private List<PassResponseDto> convListToDto(List<Pass> passes) {
        List<PassResponseDto> passResponses = new ArrayList<PassResponseDto>();
        for (Pass pass : passes) {
            passResponses.add(new PassResponseDto(pass));
        }
        return passResponses;
    }
    

}
