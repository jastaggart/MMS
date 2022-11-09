package ca.mcgill.ecse321.mms.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.mms.model.Loan;
import ca.mcgill.ecse321.mms.model.Visitor;
import ca.mcgill.ecse321.mms.model.StaffMember;
import ca.mcgill.ecse321.mms.model.Artwork;
import ca.mcgill.ecse321.mms.model.DisplayStatus;
import ca.mcgill.ecse321.mms.repository.LoanRepository;
import ca.mcgill.ecse321.mms.repository.VisitorRepository;
import ca.mcgill.ecse321.mms.repository.StaffMemberRepository;
import ca.mcgill.ecse321.mms.repository.ArtworkRepository;

import ca.mcgill.ecse321.mms.dto.LoanRequestDto;
import ca.mcgill.ecse321.mms.dto.LoanResponseDto;
import ca.mcgill.ecse321.mms.exception.MMSException;

@Service
public class LoanService {

    @Autowired(required = true)
    LoanRepository loanRepository;

    @Autowired(required = true)
    VisitorRepository visitorRepository;

    @Autowired(required = true)
    StaffMemberRepository staffMemberRepository;

    @Autowired(required = true)
    ArtworkRepository artworkRepository;

    @Transactional
	public LoanResponseDto getLoanById(int id) {
		Loan loan = loanRepository.findLoanByLoanID(id);
		if (loan == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Loan with ID " + id + " not found.");
		}
		return new LoanResponseDto(loan);
	}

    @Transactional
	public List<LoanResponseDto> getAllLoans() {
		List<Loan> loans = loanRepository.findAll();

        if (loans == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No loans found.");
        }


        List<LoanResponseDto> loanResponses = new ArrayList<LoanResponseDto>();
        for (Loan loan : loans) {
            loanResponses.add(new LoanResponseDto(loan));
        }
        return loanResponses;
	}

    @Transactional
	public List<LoanResponseDto> getLoansByVisitorID(int visitorID) {
		List<Loan> loans = loanRepository.findLoanByLoanRequestorVisitorID(visitorID);

		if (loans == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No loans made by visitor with visitorID " + visitorID + ".");
		}

		List<LoanResponseDto> loanResponses = new ArrayList<LoanResponseDto>();
        for (Loan loan : loans) {
            loanResponses.add(new LoanResponseDto(loan));
        }
        return loanResponses;
	}

    @Transactional
	public List<LoanResponseDto> getLoansByStaffMemberID(int staffMemberID) {
		List<Loan> loans = loanRepository.findLoanByLoanApproverStaffMemberID(staffMemberID);

		if (loans == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No loans to approve for staff member with staffMemberID " + staffMemberID + ".");
		}

		List<LoanResponseDto> loanResponses = new ArrayList<LoanResponseDto>();
        for (Loan loan : loans) {
            loanResponses.add(new LoanResponseDto(loan));
        }
        return loanResponses;
	}

    /*

        create loan

        approve loan

        reject loan

        loan fee?
    
     */

}