package ca.mcgill.ecse321.mms.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.mms.repository.VisitorRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class VisitorIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private VisitorRepository visitorRepo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        visitorRepo.deleteAll();
    }


    private VisitorDto testCreateVisitor() {
        ResponseEntity<VisitorDto> response = client.postForEntity("/visitor",
                new VisitorDto("garfield", "lasagna", "ihatemondays@mail.com"), VisitorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("garfield", response.getBody().getUsername());
        assertEquals("lasagna", response.getBody().getPassword());
        assertEquals("ihatemondays@mail.com", response.getBody().getEmail());
      
        return response.getBody();
    }

    @Test
    public void testCreateAndGetVisitor() {
        VisitorDto response = testCreateVisitor();
        //testGetVisitorById(response.getVisitorId());
        testGetVisitorByUsername(response.getUsername());
        testGetVisitorByEmail(response.getEmail());
    }

    @Test
    public void testCreateVisitorByInvalidEmail() {
        ResponseEntity<String> response = client.postForEntity("/visitor",
                new VisitorDto("garfield", "lasagna", "ihatemondays@"), String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email.", response.getBody());
    }

    @Test
    public void testCreateVisitorByTakenEmail() {
        
        client.postForEntity("/visitor",
                new VisitorDto("bugsB", "carrots", "whatsupdoc@mail.com"), VisitorDto.class);
        
        ResponseEntity<String> response = client.postForEntity("/visitor",
                new VisitorDto("lolaB", "bugsB", "whatsupdoc@mail.com"), String.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("A visitor with this email is already registered.", response.getBody());
    }

    @Test
    public void testCreateVisitorByTakenUsername() {
        client.postForEntity("/visitor",
                new VisitorDto("bugsB", "carrots", "carrots1@mail.com"), VisitorDto.class);
        
        ResponseEntity<String> response = client.postForEntity("/visitor",
                new VisitorDto("bugsB", "carrots", "carrots@mail.com"), String.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("This username is not available.", response.getBody());
    }
   
    private void testGetVisitorById(int id) {
        ResponseEntity<VisitorDto> response = client.getForEntity("/visitor/id/" + id, VisitorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("garfield", response.getBody().username);
        assertEquals("lasagna", response.getBody().password);
        assertEquals("ihatemondays@mail.com", response.getBody().email);
        assertTrue(response.getBody().id == id);
    }

    @Test
    public void testGetVisitorByInvalidId() {
        ResponseEntity<String> response = client.getForEntity("/visitor/id/" + 999, String.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Visitor account not found.", response.getBody());
    }

    private void testGetVisitorByUsername(String username) {
        ResponseEntity<VisitorDto> response = client.getForEntity("/visitor/username/" + username, VisitorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("garfield", response.getBody().username);
        assertEquals("lasagna", response.getBody().password);
        assertEquals("ihatemondays@mail.com", response.getBody().email);
    }

    @Test
    public void testGetVisitorByInvalidUsername() {
        ResponseEntity<String> response = client.getForEntity("/visitor/username/" + "odie", String.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Visitor account with this username was not found.", response.getBody());
    }

    private void testGetVisitorByEmail(String email) {
        ResponseEntity<VisitorDto> response = client.getForEntity("/visitor/email/" + email, VisitorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("garfield", response.getBody().username);
        assertEquals("lasagna", response.getBody().password);
        assertEquals("ihatemondays@mail.com", response.getBody().email);
    }

    @Test
    public void testGetVisitorByInvalidEmail() {
        String invalidEmail = "odie@mail.com";
        ResponseEntity<String> response = client.getForEntity("/visitor/email/" + invalidEmail, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Visitor account with this email was not found.", response.getBody());
    }

}

class VisitorDto {
    public int id;
    public String username;
    public String password;
    public String email;

    public VisitorDto() {
    }

    public VisitorDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getVisitorId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }
}
