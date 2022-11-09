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
import ca.mcgill.ecse321.mms.model.DisplayRoom;
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
    public void testCreateValidDisplayRoom() {

		ResponseEntity<DisplayRoomDto> response = client.postForEntity("/displayRoom", new DisplayRoomDto("Big", 300, 5), DisplayRoomDto.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Big", response.getBody().size);
        assertEquals(300, response.getBody().maximumCapacity);
        assertEquals(5, response.getBody().displayRoomNumber);
		assertTrue(response.getBody(). roomID > 0);
	}
    
    @Test
    public void testCreateInvalidDisplayRoomByRoomSize() {
        ResponseEntity<String> response = client.postForEntity("/displayRoom", new DisplayRoomDto("Invalid", 300, 5), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Size of room must be Big or Small", response.getBody());

    }

    @Test
    public void testCreateInvalidDisplayRoomByMaxCapacity() {
        ResponseEntity<String> response = client.postForEntity("/displayRoom", new DisplayRoomDto("Big", 200, 5), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals( "Incompatible entries for room size and maximum capacity", response.getBody());
       
    }

    @Test
    public void testCreateInvalidDisplayRoomByRoomNumber() {
        ResponseEntity<String> response = client.postForEntity("/displayRoom", new DisplayRoomDto("Small", 200, -1), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals( "Display room number cannot be less than 0", response.getBody());  
    }

    @Test
    public void testCreateInvalidDisplayRoomByDuplicateRoomNumber() {

        DisplayRoom existingDisplayRoom = new DisplayRoom();
        existingDisplayRoom.setSize("Big");
        existingDisplayRoom.setMaximumCapacity(300);
        existingDisplayRoom.setDisplayRoomNumber(15);
        existingDisplayRoom.setMuseumManagementSystem(mmsRepository.findMMSByMuseumID(1));
        roomRepository.save(existingDisplayRoom);

        ResponseEntity<String> response = client.postForEntity("/displayRoom", new DisplayRoomDto("Small", 200, 15), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Display room with display room number 15 already exists", response.getBody());  
    }


    @Test
    public void testCreateAndGetDisplayRoomByRoomId() {
        int roomID = testCreateDisplayRoom();
        testGetDisplayRoomByRoomID(roomID);
    }

 
    private int testCreateDisplayRoom() {
		ResponseEntity<DisplayRoomDto> response = client.postForEntity("/displayRoom", new DisplayRoomDto("Big", 300, 5), DisplayRoomDto.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Big", response.getBody().size);
        assertEquals(300, response.getBody().maximumCapacity);
        assertEquals(5, response.getBody().displayRoomNumber);
		assertTrue(response.getBody(). roomID > 0);

        return response.getBody().roomID;
	}

    private void testGetDisplayRoomByRoomID(int roomID) {
		ResponseEntity<DisplayRoomDto> response = client.getForEntity("/displayRoom/roomID/" + roomID, DisplayRoomDto .class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Big", response.getBody().size);
        assertEquals(300, response.getBody().maximumCapacity);
        assertEquals(5, response.getBody().displayRoomNumber);
		assertTrue(response.getBody().roomID == roomID);
	}

    @Test
    public void testGetDisplayRoomwithNonExistentRoomID() {
        ResponseEntity<String> response = client.getForEntity("/displayRoom/roomID/" + 120000, String .class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No room exists with provided roomID", response.getBody());
    }


    @Test
    public void testCreateAndGetDisplayRoomByRoomNumber() {
        int roomNumber = testCreateDisplayRoom2();
        testGetDisplayRoomByRoomNumber(roomNumber);
    }

    private int testCreateDisplayRoom2() {
		ResponseEntity<DisplayRoomDto> response = client.postForEntity("/displayRoom", new DisplayRoomDto("Small", 200, 20), DisplayRoomDto.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Small", response.getBody().size);
        assertEquals(200, response.getBody().maximumCapacity);
        assertEquals(20, response.getBody().displayRoomNumber);
		assertTrue(response.getBody(). roomID > 0);

        return response.getBody().displayRoomNumber;
	}

    private void testGetDisplayRoomByRoomNumber(int roomNumber) {
		ResponseEntity<DisplayRoomDto> response = client.getForEntity("/displayRoom/roomNumber/" + roomNumber, DisplayRoomDto .class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Small", response.getBody().size);
        assertEquals(200, response.getBody().maximumCapacity);
        assertEquals(20, response.getBody().displayRoomNumber);
    }

    @Test
    public void testGetDisplayRoomwithNonExistentRoomNumber() {
        ResponseEntity<String> response = client.getForEntity("/displayRoom/roomNumber/" + -10, String .class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No room exists with provided roomNumber", response.getBody());
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

