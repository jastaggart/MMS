package ca.mcgill.ecse321.mms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;

import ca.mcgill.ecse321.mms.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanRepositoryTests {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private VisitorRepository visitorRepository;
    
    @Autowired
    private StaffMemberRepository staffMemberRepository;

    @Autowired
    private ArtworkRepository artworkRepository;

    @AfterEach
    public void clearDatabase() {
        loanRepository.deleteAll();
        visitorRepository.deleteAll();
        staffMemberRepository.deleteAll();
        artworkRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadArtwork() {
        // Creating fields for Visitor object
        String loanReqUsername = "mary";
        String loanReqPassword = "pass123";
        String loanReqEmail = "mary@mail.com";
        // Creating the Visitor object
        Visitor loanRequestor = new Visitor();
        // Setting fields of Visitor object
        loanRequestor.setUsername(loanReqUsername);
        loanRequestor.setPassword(loanReqPassword);
        loanRequestor.setEmail(loanReqEmail);
        // Saving the Visitor object in the visitorRepository table and fetch ID
        loanRequestor =  visitorRepository.save(loanRequestor);
        int loanReqID = loanRequestor.getVisitorID();


        // Creating fields for Employee object
        String loanAppUsername = "bob";
        String loanAppPassword = "password";
        String loanAppEmail = "bob@mail.com";
        // Creating the Employee object
        Employee loanApprover = new Employee();
        // Setting fields of Employee object
        loanApprover.setUsername(loanAppUsername);
        loanApprover.setPassword(loanAppPassword);
        loanApprover.setEmail(loanAppEmail);
        // Saving the Employee object in the staffMemberRepository table and fetch ID
        loanApprover =  staffMemberRepository.save(loanApprover);
        int loanAppID = loanApprover.getStaffMemberID();


        // Creating fields for Artwork object
        boolean availableForLoan = true;
        String status = DisplayStatus.InStorage.name();
        String name = "Painting";
        String artist = "John Smith";
        // Creating Artwork object
        Artwork artwork = new Artwork();
        // Setting fields of Artwork object
        artwork.setAvailableForLoan(availableForLoan);
        artwork.setName(name);
        artwork.setArtist(artist);
        artwork.setStatus(status);
        // Saving Artwork object in artworkRepository table and fetch ID
        artwork = artworkRepository.save(artwork);
        int artworkID = artwork.getArtworkID();


        // Creating fields for Loan object
        int loanFee = 10;
        Date startDate = Date.valueOf("2022-10-25");
        Date endDate = Date.valueOf("2022-10-31");
        boolean isApproved = true;
        // Creating Loan object
        Loan loan = new Loan();
        // Setting fields of Loan object
        loan.setLoanFee(loanFee);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setIsApproved(isApproved);
        loan.setLoanRequestor(loanRequestor);
        loan.setLoanApprover(loanApprover);
        loan.setArtwork(artwork);
        // Save Loan object in loanRepository table and fetch ID
        loan = loanRepository.save(loan);
        int loanID = loan.getLoanID();


        loan = null;
        // Retrieve Artwork object from artworkRepository table in database
        loan = loanRepository.findLoanByLoanID(loanID);

        // Assertions
        // Check not null
        assertNotNull(loan);
        // Check attributes
        assertEquals(loanFee, loan.getLoanFee());
        assertEquals(startDate, loan.getStartDate());
        assertEquals(endDate, loan.getEndDate());
        assertEquals(isApproved, loan.getIsApproved());
        // Check Associations
        assertNotNull(loan.getLoanRequestor());
        assertEquals(loanReqID, loan.getLoanRequestor().getVisitorID());

        assertNotNull(loan.getLoanApprover());
        assertEquals(loanAppID, loan.getLoanApprover().getStaffMemberID());

        assertNotNull(loan.getArtwork());
        assertEquals(artworkID, loan.getArtwork().getArtworkID());

    }

}