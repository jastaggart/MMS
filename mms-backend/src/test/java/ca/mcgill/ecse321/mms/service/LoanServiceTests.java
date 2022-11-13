package ca.mcgill.ecse321.mms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.mms.exception.MMSException;
import ca.mcgill.ecse321.mms.model.Artwork;
import ca.mcgill.ecse321.mms.model.Employee;
import ca.mcgill.ecse321.mms.model.Loan;
import ca.mcgill.ecse321.mms.model.StaffMember;
import ca.mcgill.ecse321.mms.model.Visitor;
import ca.mcgill.ecse321.mms.repository.ArtworkRepository;
import ca.mcgill.ecse321.mms.repository.LoanRepository;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.StaffMemberRepository;
import ca.mcgill.ecse321.mms.repository.VisitorRepository;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTests {
    @Mock
    LoanRepository loanRepository;

    @Mock
    VisitorRepository visitorRepository;

    @Mock
    StaffMemberRepository staffMemberRepository;

    @Mock
    ArtworkRepository artworkRepository;

    @Mock
    MMSRepository mmsRepository;

    @InjectMocks
    LoanService loanService;

    @Test
    public void testCreateLoan() {
        when (loanRepository.save(any(Loan.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        final Artwork artwork = new Artwork();
        final Date startDate = new Date(2000, 1, 1);
        final Date endDate = new Date(2000, 1, 10);
        final Boolean isApproved = false;
        final StaffMember staffMember = new Employee();
        final int loanFee = 10;
        final Visitor visitor = new Visitor();

        Loan loan = new Loan();

        loan.setArtwork(artwork);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setIsApproved(isApproved);
        loan.setLoanApprover(staffMember);
        loan.setLoanRequestor(visitor);

        Loan returnedLoan = loanService.createLoan(loan);

        assertNotNull(returnedLoan);
        assertEquals(artwork, returnedLoan.getArtwork());
        assertEquals(startDate, returnedLoan.getStartDate());
        assertEquals(endDate, returnedLoan.getEndDate());
        assertEquals(isApproved, returnedLoan.getIsApproved());
        assertEquals(staffMember, returnedLoan.getLoanApprover());
        assertEquals(loanFee, returnedLoan.getLoanFee());
        assertEquals(visitor, returnedLoan.getLoanRequestor());
    }

    @Test
    public void testGetLoanById() {
        final int loanId = 1;
        final Artwork artwork = new Artwork();
        final Date startDate = new Date(2000, 1, 1);
        final Date endDate = new Date(2000, 1, 10);
        final Boolean isApproved = false;
        final StaffMember staffMember = new Employee();
        final int loanFee = 10;
        final Visitor visitor = new Visitor();

        Loan loan = new Loan();

        loan.setArtwork(artwork);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setIsApproved(isApproved);
        loan.setLoanApprover(staffMember);
        loan.setLoanFee(loanFee);
        loan.setLoanRequestor(visitor);

        when(loanRepository.findLoanByLoanID(loanId)).thenAnswer((InvocationOnMock invocation) -> loan);

        Loan returnedLoan = loanService.getLoanById(loanId);

        assertNotNull(returnedLoan);
        assertEquals(artwork, returnedLoan.getArtwork());
        assertEquals(startDate, returnedLoan.getStartDate());
        assertEquals(endDate, returnedLoan.getEndDate());
        assertEquals(isApproved, returnedLoan.getIsApproved());
        assertEquals(staffMember, returnedLoan.getLoanApprover());
        assertEquals(loanFee, returnedLoan.getLoanFee());
        assertEquals(visitor, returnedLoan.getLoanRequestor());
    }

    @Test
    public void testGetLoanByInvalidId() {
        final int invalidId = -1;

        when(loanRepository.findLoanByLoanID(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

        MMSException exception = assertThrows(MMSException.class, () -> loanService.getLoanById(invalidId));

        assertEquals("Loan with ID " + invalidId + " not found.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testGetLoansByVisitorId() {
        final int loanId = 1;
        final Artwork artwork = new Artwork();
        final Date startDate = new Date(2000, 1, 1);
        final Date endDate = new Date(2000, 1, 10);
        final Boolean isApproved = false;
        final StaffMember staffMember = new Employee();
        final int loanFee = 10;
        final Visitor visitor = new Visitor();

        Loan loan = new Loan();

        loan.setArtwork(artwork);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setIsApproved(isApproved);
        loan.setLoanApprover(staffMember);
        loan.setLoanFee(loanFee);
        loan.setLoanRequestor(visitor);

        final int secondLoanId = 2;
        final Artwork secondArtwork = new Artwork();
        final Date secondStartDate = new Date(2000, 2, 1);
        final Date secondEndDate = new Date(2000, 2, 10);
        final Boolean secondIsApproved = false;
        final StaffMember secondStaffMember = new Employee();
        final int secondLoanFee = 10;

        Loan secondLoan = new Loan();

        secondLoan.setArtwork(secondArtwork);
        secondLoan.setStartDate(secondStartDate);
        secondLoan.setEndDate(secondEndDate);
        secondLoan.setIsApproved(secondIsApproved);
        secondLoan.setLoanApprover(secondStaffMember);
        secondLoan.setLoanFee(secondLoanFee);
        secondLoan.setLoanRequestor(visitor);

        final ArrayList<Loan> loans = new ArrayList<Loan>();
        loans.add(loan);
        loans.add(secondLoan);

        when(loanRepository.findAllByLoanRequestorVisitorID(visitor.getVisitorID())).thenAnswer((InvocationOnMock invocation) -> loans);

        List<Loan> returnedLoans = loanService.getLoansByVisitorID(visitor.getVisitorID());
	
		assertNotNull(returnedLoans);
		assertEquals(loans, returnedLoans);

    }

    @Test
    public void testGetLoansByVisitorIDWithNoLoans() {
        final int visitorId = 3;

        when(loanRepository.findAllByLoanRequestorVisitorID(visitorId)).thenAnswer((InvocationOnMock invocation) -> null);

        MMSException exception = assertThrows(MMSException.class, () -> loanService.getLoansByVisitorID(visitorId));

        assertEquals("No loans made by visitor with visitorID " + visitorId + ".", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testGetAllLoans() {
        final int loanId = 1;
        final Artwork artwork = new Artwork();
        final Date startDate = new Date(2000, 1, 1);
        final Date endDate = new Date(2000, 1, 10);
        final Boolean isApproved = false;
        final StaffMember staffMember = new Employee();
        final int loanFee = 10;
        final Visitor visitor = new Visitor();

        Loan loan = new Loan();

        loan.setArtwork(artwork);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setIsApproved(isApproved);
        loan.setLoanApprover(staffMember);
        loan.setLoanFee(loanFee);
        loan.setLoanRequestor(visitor);

        final int secondLoanId = 2;
        final Artwork secondArtwork = new Artwork();
        final Date secondStartDate = new Date(2000, 2, 1);
        final Date secondEndDate = new Date(2000, 2, 10);
        final Boolean secondIsApproved = false;
        final StaffMember secondStaffMember = new Employee();
        final int secondLoanFee = 10;
        final Visitor secondVisitor = new Visitor();

        Loan secondLoan = new Loan();

        secondLoan.setArtwork(secondArtwork);
        secondLoan.setStartDate(secondStartDate);
        secondLoan.setEndDate(secondEndDate);
        secondLoan.setIsApproved(secondIsApproved);
        secondLoan.setLoanApprover(secondStaffMember);
        secondLoan.setLoanFee(secondLoanFee);
        secondLoan.setLoanRequestor(secondVisitor);

        final ArrayList<Loan> loans = new ArrayList<Loan>();
        loans.add(loan);
        loans.add(secondLoan);

        when(loanRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> loans);

        List<Loan> returnedLoans = loanService.getAllLoans();
	
		assertNotNull(returnedLoans);
		assertEquals(loans, returnedLoans);
    }

    @Test
    public void testApproveLoan() {
        final int loanId = 1;
        final Artwork artwork = new Artwork();
        final Date startDate = new Date(2000, 1, 1);
        final Date endDate = new Date(2000, 1, 10);
        final Boolean isApproved = false;
        final StaffMember staffMember = new Employee();
        final int loanFee = 10;
        final Visitor visitor = new Visitor();

        Loan loan = new Loan();

        loan.setArtwork(artwork);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setIsApproved(isApproved);
        loan.setLoanApprover(staffMember);
        loan.setLoanFee(loanFee);
        loan.setLoanRequestor(visitor);

        when(loanRepository.findLoanByLoanID(loanId)).thenAnswer((InvocationOnMock invocation) -> loan);

        loanService.approveLoan(loanId);

        Loan returnedLoan = loanService.getLoanById(loanId);

        assertNotNull(returnedLoan);
        assertEquals(true, returnedLoan.getIsApproved());
    }

    @Test
    public void testApproveLoanInvalidId() {
        final int invalidId = -1;

        when(loanRepository.findLoanByLoanID(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

        MMSException exception = assertThrows(MMSException.class, () -> loanService.approveLoan(invalidId));

        assertEquals("Loan with ID " + invalidId + " not found.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testRejectLoan() {
        final int loanId = 1;
        final Artwork artwork = new Artwork();
        final Date startDate = new Date(2000, 1, 1);
        final Date endDate = new Date(2000, 1, 10);
        final Boolean isApproved = true;
        final StaffMember staffMember = new Employee();
        final int loanFee = 10;
        final Visitor visitor = new Visitor();

        Loan loan = new Loan();

        loan.setArtwork(artwork);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setIsApproved(isApproved);
        loan.setLoanApprover(staffMember);
        loan.setLoanFee(loanFee);
        loan.setLoanRequestor(visitor);

        when(loanRepository.findLoanByLoanID(loanId)).thenAnswer((InvocationOnMock invocation) -> loan);

        loanService.rejectLoan(loanId);

        Loan returnedLoan = loanService.getLoanById(loanId);

        assertNotNull(returnedLoan);
        assertEquals(false, returnedLoan.getIsApproved());
    }
    
    @Test
    public void testRejectLoanInvalidId() {
        final int invalidId = -1;

        when(loanRepository.findLoanByLoanID(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

        MMSException exception = assertThrows(MMSException.class, () -> loanService.rejectLoan(invalidId));

        assertEquals("Loan with ID " + invalidId + " not found.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testUpdateLoanFee() {
        final int loanId = 1;
        final Artwork artwork = new Artwork();
        final Date startDate = new Date(2000, 1, 1);
        final Date endDate = new Date(2000, 1, 10);
        final Boolean isApproved = false;
        final StaffMember staffMember = new Employee();
        final int loanFee = 10;
        final Visitor visitor = new Visitor();
        final int newLoanFee = 20;

        Loan loan = new Loan();

        loan.setLoanID(loanId);
        loan.setArtwork(artwork);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setIsApproved(isApproved);
        loan.setLoanApprover(staffMember);
        loan.setLoanFee(loanFee);
        loan.setLoanRequestor(visitor);

        when(loanRepository.findLoanByLoanID(loanId)).thenAnswer((InvocationOnMock invocation) -> loan);

        loanService.updateLoanFee(loanId, newLoanFee);

        Loan returnedLoan = loanService.getLoanById(loanId);

        assertNotNull(returnedLoan);
        assertEquals(newLoanFee, returnedLoan.getLoanFee());
    }

    @Test
    public void testUpdateLoanFeeInvalidId() {
        final int loanId = 1;
        final Artwork artwork = new Artwork();
        final Date startDate = new Date(2000, 1, 1);
        final Date endDate = new Date(2000, 1, 10);
        final Boolean isApproved = false;
        final StaffMember staffMember = new Employee();
        final int loanFee = 10;
        final Visitor visitor = new Visitor();
        final int newLoanFee = 20;

        Loan loan = new Loan();

        loan.setLoanID(loanId);
        loan.setArtwork(artwork);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setIsApproved(isApproved);
        loan.setLoanApprover(staffMember);
        loan.setLoanFee(loanFee);
        loan.setLoanRequestor(visitor);

        MMSException exception = assertThrows(MMSException.class, () -> loanService.updateLoanFee(2, 15));
        
        assertEquals("Loan with ID " + 2 + " not found.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

    }
    


    
}
