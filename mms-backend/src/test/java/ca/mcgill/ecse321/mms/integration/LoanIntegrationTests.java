package ca.mcgill.ecse321.mms.integration;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.mms.model.Artwork;
import ca.mcgill.ecse321.mms.model.Employee;
import ca.mcgill.ecse321.mms.model.StaffMember;
import ca.mcgill.ecse321.mms.model.Visitor;
import ca.mcgill.ecse321.mms.repository.ArtworkRepository;
import ca.mcgill.ecse321.mms.repository.LoanRepository;
import ca.mcgill.ecse321.mms.repository.PassRepository;
import ca.mcgill.ecse321.mms.repository.VisitorRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoanIntegrationTests {
    @Autowired
	private TestRestTemplate client;

	@Autowired
	private LoanRepository loanRepository;

    @Autowired
    private ArtworkRepository artworkRepository;

    @BeforeEach
	@AfterEach
	public void clearDatabase() {
		loanRepository.deleteAll();
	}

    private LoanDto testGetLoanByLoanID(int id) throws ParseException {
        int loanRequestorID = 10;

        ResponseEntity<LoanDto> response = client.getForEntity("/loan/loanID/" + id, LoanDto.class);

        assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertEquals(loanRequestorID, response.getBody().getLoanRequestorID());
		assertTrue(response.getBody().getLoanID() == id, "Response has valid ID");

        return response.getBody();
    }

    @Test
	public void testGetLoanByLoanIDLoanDoesNotExist() {
		int invalidLoanID = 100;
		ResponseEntity<String> response = client.getForEntity("/loan/loanID/" + invalidLoanID, String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Loan with ID " + invalidLoanID + " not found.", response.getBody());
	}

    @Test
	public void testGetLoanByVisitorIDVisitorDoesNotExist() throws ParseException {
		int invalidVisitorID = 10010;

		ResponseEntity<String> response = client.getForEntity("/loan/visitorID/" + invalidVisitorID, String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("No loans made by visitor with visitorID " + invalidVisitorID + ".", response.getBody());
	}

    @Test
    public void testGetAllLoansNoLoan() {
        ResponseEntity<String> response = client.getForEntity("/loans", String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("No loans found.", response.getBody());
    }


}

class LoanDto {
    private int loanID;
    private int loanFee;
    private String startDate;
    private String endDate;
    private boolean isApproved;
    private int loanRequestorID;
    private int loanApproverID;
    private int artworkID;

    public LoanDto() {}

    public LoanDto(int loanRequestorID, int artworkID, String startDate, String endDate) {
        this.loanRequestorID = loanRequestorID;
        this.artworkID = artworkID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getLoanID() {
        return loanID;
    }

    public int getLoanFee() {
        return loanFee;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public int getLoanRequestorID() {
        return loanRequestorID;
    }

    public int getLoanApproverID() {
        return loanApproverID;
    }

    public int getArtworkID() {
        return artworkID;
    }

    
}
