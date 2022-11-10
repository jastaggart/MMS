package ca.mcgill.ecse321.mms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import ca.mcgill.ecse321.mms.model.DisplayStatus;
import ca.mcgill.ecse321.mms.model.Storage;
import ca.mcgill.ecse321.mms.repository.ArtworkRepository;
import ca.mcgill.ecse321.mms.repository.RoomRepository;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.dto.ArtworkRequestDto;
import ca.mcgill.ecse321.mms.dto.ArtworkResponseDto;

@ExtendWith(MockitoExtension.class)
public class ArtworkServiceTests {

    // Replace the repositories with a "mock" that exposes the same interface
	@Mock
	ArtworkRepository artworkRepository;

	@Mock
	RoomRepository roomRepository;

	@Mock
	MMSRepository mmsRepository;
	
	// Get a service that uses the mock repository
	@InjectMocks
	ArtworkService artworkService;

    @Test
	public void testGetArtworkById() {
		final int id = 5;
		final String name = "Starry Night";
        final String artist = "Vincent van Gogh";
        final Storage room = new Storage();
        room.setRoomID(1);
        
        Artwork starryNight = new Artwork();
        starryNight.setName(name);
        starryNight.setArtist(artist);
        starryNight.setRoom(room); 
		when(artworkRepository.findArtworkByArtworkID(id)).thenAnswer((InvocationOnMock invocation) -> starryNight);
		
		// Test that the service behaves properly
		ArtworkResponseDto artwork = artworkService.getArtworkById(id);
		
		assertNotNull(artwork);
		assertEquals(name, artwork.getName());
        assertEquals(artist, artwork.getArtist());
        assertEquals(room.getRoomID(), artwork.getRoomRoomID());
	}

    @Test
	public void testGetArtworkByInvalidId() {
		final int invalidId = 99;
	
		when(artworkRepository.findArtworkByArtworkID(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);
		
		MMSException exception = assertThrows(MMSException.class, () -> artworkService.getArtworkById(invalidId));

		assertEquals("Artwork with ID " + invalidId + " not found.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
	}

    @Test
	public void testGetArtworkByName() {
		final int id = 5;
		final String name = "Starry Night";
        final String artist = "Vincent van Gogh";
        final Storage room = new Storage();
        room.setRoomID(1);
        
        Artwork starryNight = new Artwork();
        starryNight.setName(name);
        starryNight.setArtist(artist);
        starryNight.setRoom(room); 
		when(artworkRepository.findArtworkByName(name)).thenAnswer((InvocationOnMock invocation) -> starryNight);
		
		ArtworkResponseDto artwork = artworkService.getArtworkByName(name);
		
		assertNotNull(artwork);
		assertEquals(name, artwork.getName());
        assertEquals(artist, artwork.getArtist());
        assertEquals(room.getRoomID(), artwork.getRoomRoomID());
	}

    @Test
	public void testGetArtworkByInvalidName() {
		final String invalidName = "Mona Lisa";
	
		when(artworkRepository.findArtworkByName(invalidName)).thenAnswer((InvocationOnMock invocation) -> null);
		
		MMSException exception = assertThrows(MMSException.class, () -> artworkService.getArtworkByName(invalidName));

		assertEquals("Artwork with name " + invalidName + " not found.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
	}
    
    // get artwork by artist
    // get artwork by invalid artist
    // get artworks by room id
    // get artworks by room id empty room
    // get all artworks
    // get all artworks, no artworks
    // create artwork
    // move artwork to room storage (i.e. check status)
    // move artwork to room display room 
}


