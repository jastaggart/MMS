package ca.mcgill.ecse321.mms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.mms.model.Loan;
import ca.mcgill.ecse321.mms.dto.LoanRequestDto;
import ca.mcgill.ecse321.mms.dto.LoanResponseDto;
import ca.mcgill.ecse321.mms.service.LoanService;

import java.util.List;
import java.text.ParseException;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8087")
public class LoanController {

    @Autowired
    LoanService loanService;

	/**
     * Gets a Loan by loanID after HTTP request and puts into the HTTP response
     * 
     * @param id - loanID of the Loan to get
     * @return - The fetched Loan
     */
    @GetMapping("/loan/loanID/{id}")
	public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable int id) {
		LoanResponseDto loan = new LoanResponseDto(loanService.getLoanById(id));
		return new ResponseEntity<LoanResponseDto>(loan, HttpStatus.OK);
	}

	/**
     * Gets a list of all Loans and puts into the HTTP response
     * 
     * @return - The fetched list of Artworks
     */
    @GetMapping("/loans")
	public ResponseEntity<List<LoanResponseDto>> getAllLoans() {
		List<LoanResponseDto> loans = convListToDto(loanService.getAllLoans());
		return new ResponseEntity<List<LoanResponseDto>>(loans, HttpStatus.OK);
	}

	/**
     * Gets a Loan by visitorID after HTTP request and puts into the HTTP response
     * 
     * @param visitorID - visitorID of the Loan to get
     * @return - The fetched Loan
     */
	@GetMapping("/loan/visitorID/{visitorID}") 
	public ResponseEntity<List<LoanResponseDto>> getLoansByVisitorID(@PathVariable int visitorID) {
		List<LoanResponseDto> loans = convListToDto(loanService.getLoansByVisitorID(visitorID));
		return new ResponseEntity<List<LoanResponseDto>>(loans, HttpStatus.OK);
	}

	// @GetMapping("/loan/{staffMemberID}") 
	// public ResponseEntity<List<LoanResponseDto>> getLoansByStaffMemberID(@PathVariable int staffMemberID) {
	// 	List<LoanResponseDto> loans = convListToDto(loanService.getLoansByStaffMemberID(staffMemberID));
	// 	return new ResponseEntity<List<LoanResponseDto>>(loans, HttpStatus.OK);
	// }

	/**
     * Creates a Loan using Dto data after HTTP request and puts it into HTTP response
     * 
     * @param loanRequest - The LoanRequestDto data
     * @return - The created Loan as Dto
	 * @throws ParseException
     */
	@PostMapping("/loan")
	public ResponseEntity<LoanResponseDto> createLoan(@Valid @RequestBody LoanRequestDto loanRequest) throws ParseException {
		LoanResponseDto loan = new LoanResponseDto(loanService.createLoan(loanRequest.toModel(), loanRequest.getLoanRequestorID(), loanRequest.getArtworkID()));
		return new ResponseEntity<LoanResponseDto>(loan, HttpStatus.CREATED);
	}

	/**
     * Approves a Loan by loanID after HTTP request and puts into the HTTP response
     * 
     * @param loanID - loanID of the Loan to approve
     * @param staffMemberID - staffMemberID of staff member who's approving the loan
     * @return - The updated Loan
     */
	@PutMapping("/loans/approve/{loanID}/{staffMemberID}")
	public ResponseEntity<LoanResponseDto> approveLoan(@PathVariable int loanID, @PathVariable int staffMemberID)  {
		LoanResponseDto loan = new LoanResponseDto(loanService.approveLoan(loanID, staffMemberID));
		return new ResponseEntity<LoanResponseDto>(loan, HttpStatus.OK);
	}

	/**
     * Rejects a Loan by loanID after HTTP request and puts into the HTTP response
     * 
     * @param loanID - loanID of the Loan to reject
     * @param staffMemberID - staffMemberID of staff member who's rejecting the loan
     * @return - The rejected Loan
     */
	@PutMapping("/loans/reject/{loanID}/{staffMemberID}")
	public ResponseEntity<LoanResponseDto> rejectLoan(@PathVariable int loanID, @PathVariable int staffMemberID) {
		LoanResponseDto loan = new LoanResponseDto(loanService.rejectLoan(loanID, staffMemberID));
		return new ResponseEntity<LoanResponseDto>(loan, HttpStatus.OK);
	}

     @PutMapping("/loans/close/{loanID}")
	public ResponseEntity<LoanResponseDto> closeLoan(@PathVariable int loanID)  {
		LoanResponseDto loan = new LoanResponseDto(loanService.closeLoan(loanID));
		return new ResponseEntity<LoanResponseDto>(loan, HttpStatus.OK);
	}

	/**
     * Updates the fee of a Loan by loanID after HTTP request and puts into the HTTP response
     * 
     * @param loanID - loanID of the Loan to update the fee of
     * @return - The updated Loan
     */
	@PutMapping("/loans/updateLoanFee/{loanID}")
	public ResponseEntity<LoanResponseDto> updateLoanFee(@PathVariable int loanID, @RequestParam int loanFee) {
		LoanResponseDto loan = new LoanResponseDto(loanService.updateLoanFee(loanID, loanFee));
		return new ResponseEntity<LoanResponseDto>(loan, HttpStatus.OK);
	}
	
	/**
     * Converts a list of Loans to a list of LoanResponseDtos 
     * Note: This is a private helper method
	 *
     * @param artworks - list of Loans
     * @return - list of LoanResponseDtos
     */
	private List<LoanResponseDto> convListToDto(List<Loan> loans) {
        List<LoanResponseDto> loanResponses = new ArrayList<LoanResponseDto>();
        for (Loan loan : loans) {
            loanResponses.add(new LoanResponseDto(loan));
        }
        return loanResponses;
    }

}