package ca.mcgill.ecse321.mms.controller;

import java.text.ParseException;
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

import ca.mcgill.ecse321.mms.dto.PassRequestDto;
import ca.mcgill.ecse321.mms.dto.PassResponseDto;
import ca.mcgill.ecse321.mms.model.Pass;
import ca.mcgill.ecse321.mms.model.Visitor;
import ca.mcgill.ecse321.mms.repository.VisitorRepository;
import ca.mcgill.ecse321.mms.service.PassService;

@RestController
@Validated
public class PassController {
    
    @Autowired(required = true)
    PassService passService;

	@Autowired
	VisitorRepository visitorRepository;

    @GetMapping("/passes")
    public ResponseEntity<List<PassResponseDto>> getAllPasses() {
		List<PassResponseDto> passes = passService.getAllPasses();
		return new ResponseEntity<List<PassResponseDto>>(passes, HttpStatus.OK);
	}

    @GetMapping("/pass/{id}")
    public ResponseEntity<PassResponseDto> getPassById(@PathVariable int id) {
		PassResponseDto pass = passService.getPassById(id);
		return new ResponseEntity<PassResponseDto>(pass, HttpStatus.OK);
	}

    @GetMapping("/pass/{visitorID}") 
	public ResponseEntity<List<PassResponseDto>> getPassesByVisitorId(@PathVariable int visitorID) {
		List<PassResponseDto> passes = passService.getPassesByVisitorId(visitorID);
		return new ResponseEntity<List<PassResponseDto>>(passes, HttpStatus.OK);
	}

	// @PostMapping("/pass")
	// public ResponseEntity<PassResponseDto> createPass(@Valid @RequestBody PassRequestDto passRequest) throws ParseException {
	// 	PassResponseDto pass = passService.createPass(passRequest); 
	// 	return new ResponseEntity<PassResponseDto>(pass, HttpStatus.CREATED);  
	// }

	@PostMapping("/pass")
    public ResponseEntity<PassResponseDto> createPass(@Valid @RequestBody PassRequestDto passRequest) throws ParseException {
			System.out.println(passRequest.getPassDate());
			System.out.println(passRequest.getVisitorID());
			Visitor visitorRequestingPass = visitorRepository.findVisitorByVisitorID(passRequest.getVisitorID());		
            Pass createdPass = passService.createPass(visitorRequestingPass, passRequest.getPassDate());
            PassResponseDto response = new PassResponseDto(createdPass);  
            return new ResponseEntity<PassResponseDto>(response, HttpStatus.CREATED);       
    }
    

}
