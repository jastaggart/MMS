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

import java.sql.Date;

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

    @Transactional
    public LoanResponseDto createLoan(LoanRequestDto loanRequest) {
        final int initialLoanFee = 10;

        Loan loan = new Loan();
        loan.setLoanFee(initialLoanFee);
        loan.setStartDate(loanRequest.getStartDate());
        loan.setEndDate(loanRequest.getEndDate());
        loan.setIsApproved(false); // not approved yet
        // loan.setLoanRequestor(loanRequest.getLoanRequestor().toModel());
        // loan.setLoanApprover(loanRequest.getLoanApprover().toModel());
        // Note: Before loan is approved, artwork status remains the same 
        // i.e. it stays in storage or its current display room
        loan.setArtwork(loanRequest.getArtwork().toModel()); 
        
        loan = loanRepository.save(loan);

        return new LoanResponseDto(loan);
    }

    @Transactional
    public LoanResponseDto approveLoan(int loanID) {
        Loan loan = loanRepository.findLoanByLoanID(loanID);
        loan.setIsApproved(true);
        loan.getArtwork().setStatus(DisplayStatus.OnLoan.name());

        return new LoanResponseDto(loan);
    }

    @Transactional
    public LoanResponseDto rejectLoan(int loanID) {
        Loan loan = loanRepository.findLoanByLoanID(loanID);
        if (loan == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Loan with ID " + loanID + " not found.");
		}
        loan.setIsApproved(false);
        // Note: If loan is rejected, artwork status remains the same
        // i.e. it stays in storage or its current display room

        return new LoanResponseDto(loan);
    }

    @Transactional
    public LoanResponseDto updateLoanFee(int loanID, int loanFee) {
        Loan loan = loanRepository.findLoanByLoanID(loanID);
        if (loan == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Loan with ID " + loanID + " not found.");
		}
        loan.setLoanFee(loanFee);

        return new LoanResponseDto(loan);
    }
}