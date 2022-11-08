package ca.mcgill.ecse321.mms.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.RoomRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DisplayRoomIntegrationTests {
    
    @Autowired
	private TestRestTemplate client;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MMSRepository mmsRepository;
    
    @BeforeEach
	@AfterEach
	public void clearDatabase() {
		roomRepository.deleteAll();
	}

    @Test
	public void testCreateAndGetDisplayRoom() {
	
	}

    @Test
    public void testCreateDisplayRoom() {
		ResponseEntity<DisplayRoomDto> response = client.postForEntity("/displayRoom", new DisplayRoomDto("Big", 300, 5), DisplayRoomDto.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has incorrect status");
		assertNotNull(response.getBody(), "Response has no body");
		assertEquals("Big", response.getBody().size, "Response has incorrect size");
        assertEquals(300, response.getBody().maximumCapacity, "Response has incorrect maximumCapacity");
        assertEquals(5, response.getBody().displayRoomNumber, "Response has incorrect displayRoomNumber");
		assertTrue(response.getBody(). roomID > 0, "Response has invalid roomID");
	
		
	}

}

    class DisplayRoomDto {
    
        public int roomID;
        public String size;
        public int maximumCapacity;
        public int displayRoomNumber;
        
        public DisplayRoomDto() {}
        
        public DisplayRoomDto(String size, int maximumCapacity, int displayRoomNumber) {
            this.size = size;
            this.maximumCapacity = maximumCapacity;
            this.displayRoomNumber = displayRoomNumber;
        }
    
    }

