package ca.mcgill.ecse321.mms.service;

import java.util.List;
import java.util.Locale;

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

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    /**
     * Gets a Loan by its loanID attribute
     * 
     * @param id - Loan loanID
     * @return - The fetched Loan
     */
    @Transactional
	public Loan getLoanById(int id) {
		Loan loan = loanRepository.findLoanByLoanID(id);
		if (loan == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Loan with ID " + id + " not found.");
		}
		return loan;
	}

    /**
     * Gets a list of all Loans in the loanRepository
     * 
     * @return - The fetched list of Loans
     */
    @Transactional
	public List<Loan> getAllLoans() {
		List<Loan> loans = loanRepository.findAll();

        if (loans == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No loans found.");
        }

        if (loans.isEmpty()) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No loans found.");
        }

        return loans;
	}

    /**
     * Gets a Loan by its visitorID attribute
     * 
     * @param visitorID - Loan visitorID
     * @return - The fetched Loan
     */
    @Transactional
	public List<Loan> getLoansByVisitorID(int visitorID) {
		List<Loan> loans = loanRepository.findAllByLoanRequestorVisitorID(visitorID);
        
		if (loans == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No loans made by visitor with visitorID " + visitorID + ".");
		}

        if (loans.isEmpty()) {
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

    /**
     * Creates a Loan using specific Loan data and saves it in the loanRepository
     * @param loan - Loan object to create and save in the loanRepository
     * @return - The created Loan
     * @throws ParseException
     */
    @Transactional
    public Loan createLoan(Loan loan, int loanRequestorID, int artworkID) throws ParseException {
        final int initialLoanFee = 10;
        loan.setLoanFee(initialLoanFee);
        loan.setIsApproved(false); // not approved yet
        loan.setLoanRequestor(visitorRepository.findVisitorByVisitorID(loanRequestorID));
        loan.setArtwork(artworkRepository.findArtworkByArtworkID(artworkID));

        if (!loan.getArtwork().getAvailableForLoan()) {
            throw new MMSException(HttpStatus.BAD_REQUEST, "Specified artwork not available for loan.");
        }
        
        // Note: Before loan is approved, artwork status remains the same 
        // i.e. it stays in storage or its current display room
       
        loan = loanRepository.save(loan);
        return loan;
    }

    /**
     * Approves a Loan by updating its isApproved field
     * @param loanID - Loan loanID
     * @param staffMemberID - StaffMember staffMemberID
     * @return - The approved loan
     */
    @Transactional
    public Loan approveLoan(int loanID, int staffMemberID) {
        Loan loan = loanRepository.findLoanByLoanID(loanID);
        StaffMember loanApprover = staffMemberRepository.findStaffMemberByStaffMemberID(staffMemberID); // currently logged in staff member, must exist

        if (loan == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Loan with ID " + loanID + " not found.");
		}

        loan.setIsApproved(true);
        loan.getArtwork().setStatus(DisplayStatus.OnLoan.name());
        loan.getArtwork().setAvailableForLoan(false);
        loan.setLoanApprover(loanApprover);

        return loan;
    }

    /**
     * Rejects a Loan by updating its isApproved field
     * @param loanID - Loan loanID
     * @param staffMemberID - StaffMember staffMemberID
     * @return - The rejected loan
     */
    @Transactional
    public Loan rejectLoan(int loanID, int staffMemberID) {
        Loan loan = loanRepository.findLoanByLoanID(loanID);
        StaffMember loanApprover = staffMemberRepository.findStaffMemberByStaffMemberID(staffMemberID); // currently logged in staff member, must exist

        if (loan == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Loan with ID " + loanID + " not found.");
		}

        loan.setIsApproved(false);
        loan.setLoanApprover(loanApprover);
        // Note: If loan is rejected, artwork status remains the same
        // i.e. it stays in storage or its current display room

        return loan;
    }

    /**
     * Updates the fee of a Loan by updating its loanFee field
     * @param loanID - Loan loanID
     * @return - The updated loan
     */
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