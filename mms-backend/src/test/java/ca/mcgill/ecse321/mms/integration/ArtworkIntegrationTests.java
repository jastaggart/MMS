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
import org.springframework.http.HttpMethod; // TODO
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.mms.repository.ArtworkRepository;
import ca.mcgill.ecse321.mms.integration.DisplayRoomDto;

import java.util.List;
import java.util.ArrayList;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ArtworkIntegrationTests {

	@Autowired
	private TestRestTemplate client;
	
	@Autowired
	private ArtworkRepository artworkRepo;

    @BeforeEach
	@AfterEach
	public void clearDatabase() {
		artworkRepo.deleteAll();
	}
	
	@Test
	public void testCreateAndGetArtwork() {
		ArtworkDto response = testCreateArtwork();
		testGetArtworkByArtworkID(response.getArtworkID());
		testGetArtworkByName(response.getName());
	}

    private ArtworkDto testCreateArtwork() {
		ResponseEntity<ArtworkDto> response = client.postForEntity("/artwork", new ArtworkDto("Starry Night", "Vincent van Gogh"), ArtworkDto.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals("Starry Night", response.getBody().getName(), "Response has correct name");
		assertEquals("Vincent van Gogh", response.getBody().getArtist(), "Response has correct artist");
		assertTrue(response.getBody().getArtworkID() > 0, "Response has valid ID");
		
		return response.getBody();
    }

	@Test
	public void testCreateArtworkInvalid() {
		ResponseEntity<ArtworkDto> response = client.postForEntity("/artwork", new ArtworkDto("", ""), ArtworkDto.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

	private void testGetArtworkByArtworkID(int id) {
		ResponseEntity<ArtworkDto> response = client.getForEntity("/artwork/artworkID/" + id, ArtworkDto.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals("Starry Night", response.getBody().getName(), "Response has correct name");
		assertEquals("Vincent van Gogh", response.getBody().getArtist(), "Response has correct artist");
		assertTrue(response.getBody().getArtworkID() == id, "Response has valid ID");
    }

	@Test
	public void testGetArtworkByArtworkIDInvalid() {
		int invalidArtworkID = 9999;
		ResponseEntity<String> response = client.getForEntity("/artwork/artworkID/" + invalidArtworkID, String.class);

		assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Artwork with ID " + invalidArtworkID + " not found.", response.getBody());
	}

	private void testGetArtworkByName(String name) {
		ResponseEntity<ArtworkDto> response = client.getForEntity("/artwork/name/" + name, ArtworkDto.class);
		
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals("Starry Night", response.getBody().getName(), "Response has correct name");
		assertEquals("Vincent van Gogh", response.getBody().getArtist(), "Response has correct artist");
		assertTrue(response.getBody().getArtworkID() > 0, "Response has valid ID");
    }

	@Test
	public void testGetArtworkByNameInvalid() {
		String invalidName = "Painting";
		ResponseEntity<String> response = client.getForEntity("/artwork/name/" + invalidName, String.class);

		assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Artwork with name " + invalidName + " not found.", response.getBody());
    }

	@Test
	public void testMoveArtworkToRoom() {
		ResponseEntity<ArtworkDto> createdArtwork = client.postForEntity("/artwork", new ArtworkDto("Starry Night", "Vincent van Gogh"), ArtworkDto.class);
		assertNotNull(createdArtwork);
		assertEquals(HttpStatus.CREATED, createdArtwork.getStatusCode(), "Response has correct status");

		ResponseEntity<DisplayRoomDto> createdDisplayRoom = client.postForEntity("/displayRoom", new DisplayRoomDto("Big", 300, 5), DisplayRoomDto.class);
		assertNotNull(createdDisplayRoom);
		assertEquals(HttpStatus.CREATED, createdDisplayRoom.getStatusCode());

		int artworkID = createdArtwork.getBody().getArtworkID();
		int roomID = createdDisplayRoom.getBody().roomID; 
		ResponseEntity<ArtworkDto> response = client.exchange("/artworks/move/" + artworkID + "/" + roomID, HttpMethod.PUT, createdArtwork, ArtworkDto.class);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals(roomID, response.getBody().getRoomRoomID(), "Response has correct roomID");
		assertEquals("Starry Night", response.getBody().getName(), "Response has correct name");
		assertEquals("Vincent van Gogh", response.getBody().getArtist(), "Response has correct artist");
		assertTrue(response.getBody().getArtworkID() > 0, "Response has valid ID");
	}    
}

class ArtworkDto {
	private int artworkID;
	private String name;
	private String artist;
	private int roomRoomID;
	
	// Need default constructor so that Jackson can instantiate the object
	public ArtworkDto() {}

	public ArtworkDto(String name, String artist) {
		this.name = name;
		this.artist = artist;
		this.roomRoomID = 1; // storage room
	}

	public int getArtworkID() {
		return this.artworkID;
	}

	public String getName() {
		return this.name;
	}

	public String getArtist() {
		return this.artist;
	}

	public int getRoomRoomID() {
		return this.roomRoomID;
	}

	public void setRoomRoomID(int roomRoomID) {
		this.roomRoomID = roomRoomID;
	}
}

