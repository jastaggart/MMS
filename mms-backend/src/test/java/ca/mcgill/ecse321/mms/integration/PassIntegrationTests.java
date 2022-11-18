package ca.mcgill.ecse321.mms.integration;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.mms.model.Visitor;
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
public class PassIntegrationTests {

	@Autowired
	private TestRestTemplate client;

	@Autowired
	private PassRepository passRepository;

	@BeforeEach
	@AfterEach
	public void clearDatabase() {
		passRepository.deleteAll();
	}

	@Test
	public void testCreateAndGetPassSingle() throws ParseException {
		PassDto response = testCreatePass();
		testGetPassByPassID(response.getPassID());
	}

	private PassDto testCreatePass() throws ParseException {
		final String date = "02-02-2002";
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date parsedDate = formatter.parse(date);

		Visitor visitor = new Visitor();

		ResponseEntity<PassDto> response = client.postForEntity("/pass", new PassDto(parsedDate, 1), PassDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		// assertEquals(parsedDate, response.getBody().getPassDate(), "Response has
		// correct date");
		assertEquals(1, response.getBody().getVisitorID(), "Response has correct visitorID");
		assertTrue(response.getBody().getPassID() > 0, "Response has valid ID");

		return response.getBody();
	}

	private void testGetPassByPassID(int id) throws ParseException {
		final String date = "02-02-2002";
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date parsedDate = formatter.parse(date);

		ResponseEntity<PassDto> response = client.getForEntity("/pass/" + id, PassDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		// assertEquals(parsedDate, response.getBody().getPassDate(), "Response has
		// correct date");
		assertEquals(1, response.getBody().getVisitorID(), "Response has correct visitorID");
		assertTrue(response.getBody().getPassID() == id, "Response has valid ID");
	}

	@Test
	public void testGetPassByPassIDPassDoesNotExist() {
		int invalidPassID = 100;
		ResponseEntity<String> response = client.getForEntity("/pass/" + invalidPassID, String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Pass with ID " + invalidPassID + " not found.", response.getBody());
	}

	@Test
	public void testGetPassByInvalidPassID() {
		int invalidPassID = -1;
		ResponseEntity<String> response = client.getForEntity("/pass/" + invalidPassID, String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Pass ID " + invalidPassID + " is invalid.", response.getBody());
	}

	@Test
	public void testGetPassByVisitorIDVisitorDoesNotExist() throws ParseException {
		int invalidVisitorID = 10010;
		final String date = "02-02-2002";
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		Date parsedDate = formatter.parse(date);

		Visitor visitor = new Visitor();

		ResponseEntity<String> response = client.getForEntity("/pass/visitor/" + invalidVisitorID, String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("No passes owned by visitor with visitorID " + invalidVisitorID + ".", response.getBody());
	}

	@Test
	public void testGetPassByInvalidVisitorID() {
		int invalidVisitorID = -1;
		ResponseEntity<String> response = client.getForEntity("/pass/visitor/" + invalidVisitorID, String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Visitor ID " + invalidVisitorID + " is invalid.", response.getBody());

	}

	@Test
	public void testGetPassesByVisitorIdWithNoPasses() {
		Visitor visitor = new Visitor();

		ResponseEntity<String> response = client.getForEntity("/pass/visitor/" + visitor.getVisitorID(), String.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals( "No passes owned by visitor with visitorID " + visitor.getVisitorID() + ".", response.getBody());
	}

}

class PassDto {
	private int passID;
	private Date passDate;
	private int visitorID;

	public PassDto() {
	}

	public PassDto(Date passDate, int visitorID) {
		this.passDate = passDate;
		this.visitorID = visitorID;
	}

	public int getPassID() {
		return this.passID;
	}

	public Date getPassDate() {
		return this.passDate;
	}

	public int getVisitorID() {
		return this.visitorID;
	}

}