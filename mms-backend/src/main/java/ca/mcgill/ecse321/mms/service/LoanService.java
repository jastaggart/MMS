package ca.mcgill.ecse321.mms.service;

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
	public Loan getLoanById(int id) {
		Loan loan = loanRepository.findLoanByLoanID(id);
		if (loan == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Loan with ID " + id + " not found.");
		}
		return loan;
	}

    @Transactional
	public List<Loan> getAllLoans() {
		List<Loan> loans = loanRepository.findAll();

        if (loans == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No loans found.");
        }

        return loans;
	}

    @Transactional
	public List<Loan> getLoansByVisitorID(int visitorID) {
		List<Loan> loans = loanRepository.findAllByLoanRequestorVisitorID(visitorID);

		if (loans == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No loans made by visitor with visitorID " + visitorID + ".");
		}

		return loans;
	}

    // @Transactional
	// public List<Loan> getLoansByStaffMemberID(int staffMemberID) {
	// 	List<Loan> loans = loanRepository.findAllByLoanApproverStaffMemberID(staffMemberID);

	// 	if (loans == null) {
	// 		throw new MMSException(HttpStatus.NOT_FOUND, "No loans to approve for staff member with staffMemberID " + staffMemberID + ".");
	// 	}

	// 	return loans;
	// }

    @Transactional
    public Loan createLoan(Loan loan) {
        final int initialLoanFee = 10;

        loan.setLoanFee(initialLoanFee);
        loan.setIsApproved(false); // not approved yet
        
        // Note: Before loan is approved, artwork status remains the same 
        // i.e. it stays in storage or its current display room
       
        loan = loanRepository.save(loan);
        return loan;
    }

    @Transactional
    public Loan approveLoan(int loanID) {
        Loan loan = loanRepository.findLoanByLoanID(loanID);
        loan.setIsApproved(true);
        loan.getArtwork().setStatus(DisplayStatus.OnLoan.name());

        return loan;
    }

    @Transactional
    public Loan rejectLoan(int loanID) {
        Loan loan = loanRepository.findLoanByLoanID(loanID);
        if (loan == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Loan with ID " + loanID + " not found.");
		}
        loan.setIsApproved(false);
        // Note: If loan is rejected, artwork status remains the same
        // i.e. it stays in storage or its current display room

        return loan;
    }

    @Transactional
    public Loan updateLoanFee(int loanID, int loanFee) {
        Loan loan = loanRepository.findLoanByLoanID(loanID);
        if (loan == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Loan with ID " + loanID + " not found.");
		}
        loan.setLoanFee(loanFee);

        return loan;
    }

}