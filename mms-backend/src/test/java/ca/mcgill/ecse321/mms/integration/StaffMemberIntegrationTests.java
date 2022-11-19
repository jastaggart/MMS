package ca.mcgill.ecse321.mms.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.mms.model.Loan;
import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.StaffMemberRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StaffMemberIntegrationTests {

	@Autowired
	private TestRestTemplate client;

	@Autowired
	private StaffMemberRepository staffMemberRepository;

	@Autowired
	private MMSRepository mmsRepository;

	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		staffMemberRepository.deleteAll();
	}
	
	@Test
	public void testCreateAndGetStaffMember() {
		StaffMemberDto response = testCreateStaffMember();
		testGetStaffMemberById(response.getStaffMemberID());
		//testGetLoanByRequestor(response.getLoan());
	}
	
	public StaffMemberDto testGetStaffMemberById(int id) {
		ResponseEntity<StaffMemberDto> response = client.getForEntity("/staffMember/staffMemberId/" + id,
				StaffMemberDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertEquals(id, response.getBody().getStaffMemberID());
		assertTrue(response.getBody().getStaffMemberID() == id, "Response has valid ID");

		return response.getBody();
	}

	@Test
	public void testGetStaffMemberByIDStaffMemberDoesNotExist() {
		int invalidStaffMemberID = -1;
		ResponseEntity<String> response = client.getForEntity("/staffMember/staffMemberId/" + invalidStaffMemberID,
				String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("No staff member with the id " + invalidStaffMemberID + " was found.", response.getBody());
	}
	
	public StaffMemberDto testGetStaffMemberByName(String name) {
		ResponseEntity<StaffMemberDto> response = client.getForEntity("/staffMember/staffMemberName/" + name,
				StaffMemberDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertEquals(name, response.getBody().getUsername());
		assertTrue(response.getBody().getUsername() == name, "Response has valid username");

		return response.getBody();
	}
	
	@Test
	public void testGetStaffMemberByNameStaffMemberDoesNotExist() {
		String invalidStaffMemberName = "invalid name";
		ResponseEntity<String> response = client.getForEntity("/staffMember/staffMemberName/" + invalidStaffMemberName,
				String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("No staffmember with the name " +invalidStaffMemberName + " was found.", response.getBody());
	}
	
	@Test
	public StaffMemberDto testCreateStaffMember() {
		ResponseEntity<StaffMemberDto> response = client.postForEntity("/staffMember",
                new StaffMemberDto("johnsmith", "12345678", "jj@gmail.com"), StaffMemberDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("johnsmith", response.getBody().getUsername());
        assertEquals("12345678", response.getBody().getPassword());
        assertEquals("jj@gmail.com", response.getBody().getEmail());
        
        return response.getBody();
	}
	
}

class StaffMemberDto {

	private int staffMemberID;
	private MMS mms;
	private String username;
	private String email;
	private String password;
	private List<Loan> loan;

	public StaffMemberDto() {
	}

	public StaffMemberDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

	public int getStaffMemberID() {
		return staffMemberID;
	}

	public void setStaffMemberID(int staffMemberID) {
		this.staffMemberID = staffMemberID;
	}

	public MMS getMms() {
		return mms;
	}

	public void setMms(MMS mms) {
		this.mms = mms;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Loan> getLoan() {
		return loan;
	}

	public void setLoan(List<Loan> loan) {
		this.loan = loan;
	}

}
