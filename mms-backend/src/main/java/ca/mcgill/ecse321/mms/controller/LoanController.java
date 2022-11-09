package ca.mcgill.ecse321.mms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.mms.dto.LoanRequestDto;
import ca.mcgill.ecse321.mms.dto.LoanResponseDto;
import ca.mcgill.ecse321.mms.service.LoanService;

import java.util.List;

@RestController
public class LoanController {

    @Autowired
    LoanService loanService;

    @GetMapping("/loan/{id}")
	public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable int id) {
		LoanResponseDto loan = loanService.getLoanById(id);
		return new ResponseEntity<LoanResponseDto>(loan, HttpStatus.OK);
	}

    @GetMapping("/loans")
	public ResponseEntity<List<LoanResponseDto>> getAllLoans() {
		List<LoanResponseDto> loans = loanService.getAllLoans();
		return new ResponseEntity<List<LoanResponseDto>>(loans, HttpStatus.OK);
	}

	@GetMapping("/loan/{visitorID}") 
	public ResponseEntity<List<LoanResponseDto>> getLoansByVisitorID(@PathVariable int visitorID) {
		List<LoanResponseDto> loans = loanService.getLoansByVisitorID(visitorID);
		return new ResponseEntity<List<LoanResponseDto>>(loans, HttpStatus.OK);
	}

	@GetMapping("/loan/{staffMemberID}") 
	public ResponseEntity<List<LoanResponseDto>> getLoansByStaffMemberID(@PathVariable int staffMemberID) {
		List<LoanResponseDto> loans = loanService.getLoansByStaffMemberID(staffMemberID);
		return new ResponseEntity<List<LoanResponseDto>>(loans, HttpStatus.OK);
	}
	

}