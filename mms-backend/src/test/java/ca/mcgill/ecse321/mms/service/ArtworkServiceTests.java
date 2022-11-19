package ca.mcgill.ecse321.mms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import ca.mcgill.ecse321.mms.model.DisplayRoom;
import ca.mcgill.ecse321.mms.model.Storage;
import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.model.Loan;
import ca.mcgill.ecse321.mms.repository.ArtworkRepository;
import ca.mcgill.ecse321.mms.repository.RoomRepository;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.LoanRepository;
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

	@Mock
	LoanRepository loanRepository;
	
	// Get a service that uses the mock repository
	@InjectMocks
	ArtworkService artworkService;

    @Test
	public void testGetArtworkById() {
		final int id = 5;
		final String name = "Starry Night";
        final String artist = "Vincent van Gogh";
        
        Artwork starryNight = new Artwork();
        starryNight.setName(name);
        starryNight.setArtist(artist);

		when(artworkRepository.findArtworkByArtworkID(id)).thenAnswer((InvocationOnMock invocation) -> starryNight);
		
		// Test that the service behaves properly
		Artwork artwork = artworkService.getArtworkById(id);
		
		assertNotNull(artwork);
		assertEquals(name, artwork.getName());
        assertEquals(artist, artwork.getArtist());
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
		final String name = "Starry Night";
        final String artist = "Vincent van Gogh";
        
        Artwork starryNight = new Artwork();
        starryNight.setName(name);
        starryNight.setArtist(artist);

		when(artworkRepository.findArtworkByName(name)).thenAnswer((InvocationOnMock invocation) -> starryNight);
		
		Artwork artwork = artworkService.getArtworkByName(name);
		
		assertNotNull(artwork);
		assertEquals(name, artwork.getName());
        assertEquals(artist, artwork.getArtist());
	}

    @Test
	public void testGetArtworkByInvalidName() {
		final String invalidName = "Mona Lisa";
	
		when(artworkRepository.findArtworkByName(invalidName)).thenAnswer((InvocationOnMock invocation) -> null);
		
		MMSException exception = assertThrows(MMSException.class, () -> artworkService.getArtworkByName(invalidName));

		assertEquals("Artwork with name " + invalidName + " not found.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
	}
    
	@Test
	public void testGetArtworksByArtist() {
		// First artwork
		final String name = "Starry Night";
        final String artist = "Vincent van Gogh";
        final Artwork starryNight = new Artwork();
        starryNight.setName(name);
        starryNight.setArtist(artist);

		// Second artwork
		final String name2 = "Sunflowers";
		final Artwork sunflowers = new Artwork();
        sunflowers.setName(name2);
        sunflowers.setArtist(artist);

		// List of artworks
		final ArrayList<Artwork> vanGoghPaintings = new ArrayList<Artwork>();
		vanGoghPaintings.add(starryNight);
		vanGoghPaintings.add(sunflowers);

		when(artworkRepository.findAllByArtist(artist)).thenAnswer((InvocationOnMock invocation) -> vanGoghPaintings);
		
		List<Artwork> artworks = artworkService.getArtworksByArtist(artist);
		
		assertNotNull(artworks);
		assertEquals(vanGoghPaintings, artworks);
	}

	@Test
	public void testGetArtworkByInvalidArtist() {
		final String invalidArtist = "Claude Monet";
	
		ArrayList<Artwork> empty = new ArrayList<Artwork>();
		when(artworkRepository.findAllByArtist(invalidArtist)).thenAnswer((InvocationOnMock invocation) -> empty);
		
		MMSException exception = assertThrows(MMSException.class, () -> artworkService.getArtworksByArtist(invalidArtist));

		assertEquals("No artworks by " + invalidArtist + " found.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
	}

	@Test
	public void testGetArtworksByRoomID() {
		// First artwork
		final String name = "Starry Night";
		final int roomId = 1;
        final Storage storage = new Storage();
		storage.setRoomID(roomId);
        final Artwork starryNight = new Artwork();
        starryNight.setName(name);
        starryNight.setRoom(storage);

		// Second artwork
		final String name2 = "Sunflowers";
		final Artwork sunflowers = new Artwork();
        sunflowers.setName(name2);
        sunflowers.setRoom(storage);

		// List of artworks
		final ArrayList<Artwork> storageRoomPaintings = new ArrayList<Artwork>();
		storageRoomPaintings.add(starryNight);
		storageRoomPaintings.add(sunflowers);

		when(artworkRepository.findAllByRoomRoomID(roomId)).thenAnswer((InvocationOnMock invocation) -> storageRoomPaintings);
		
		List<Artwork> artworks = artworkService.getArtworksByRoomID(roomId);
		
		assertNotNull(artworks);
		assertEquals(storageRoomPaintings, artworks);
	}
	
	@Test
	public void testGetArtworkByRoomIDEmptyRoom() {
		final int emptyRoomID = 4;
	
		ArrayList<Artwork> empty = new ArrayList<Artwork>();
		when(artworkRepository.findAllByRoomRoomID(emptyRoomID)).thenAnswer((InvocationOnMock invocation) -> empty);
		
		MMSException exception = assertThrows(MMSException.class, () -> artworkService.getArtworksByRoomID(emptyRoomID));

		assertEquals("No artworks in room with roomID " + emptyRoomID + ".", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
	}

	@Test
	public void testGetAllArtworks() {
		// First artwork
		final String name = "Starry Night";
        final String artist = "Vincent van Gogh";
        final Artwork starryNight = new Artwork();
        starryNight.setName(name);
        starryNight.setArtist(artist);

		// Second artwork
		final String name2 = "Sunflowers";
		final Artwork sunflowers = new Artwork();
        sunflowers.setName(name2);
        sunflowers.setArtist(artist);

		// List of artworks
		final ArrayList<Artwork> allArtworks = new ArrayList<Artwork>();
		allArtworks.add(starryNight);
		allArtworks.add(sunflowers);

		when(artworkRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> allArtworks);
		
		List<Artwork> artworks = artworkService.getAllArtworks();
		
		assertNotNull(artworks);
		assertEquals(allArtworks, artworks);
	}
    
	@Test
	public void testGetAllArtworksNoArtworks() {
		ArrayList<Artwork> empty = new ArrayList<Artwork>();
		when(artworkRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> empty);
		
		MMSException exception = assertThrows(MMSException.class, () -> artworkService.getAllArtworks());

		assertEquals("No artworks to display.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
	}

	@Test
	public void testCreateArtwork() {
		when(artworkRepository.save(any(Artwork.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

		final String name = "Starry Night";
        final String artist = "Vincent van Gogh";
        final Artwork starryNight = new Artwork();
        starryNight.setName(name);
        starryNight.setArtist(artist);

		Artwork returnedArtwork = artworkService.createArtwork(starryNight);

		assertNotNull(returnedArtwork);
		assertEquals(name, returnedArtwork.getName());
		assertEquals(artist, returnedArtwork.getArtist());
		// Check that the service actually saved the person
		verify(artworkRepository, times(1)).save(starryNight);

	}
    
	@Test
	public void testMoveArtworkToRoomStorage() {
		// Original room (display room)
		final int displayRoomID = 2;
		final DisplayRoom displayRoom = new DisplayRoom();
		displayRoom.setRoomID(displayRoomID);

		// Destination room (storage)
		final int storageRoomID = 1;
        final Storage storage = new Storage();
		storage.setRoomID(storageRoomID);

		// Artwork
		final int artworkID = 10;
        final Artwork starryNight = new Artwork();
        starryNight.setArtworkID(artworkID);
		starryNight.setRoom(displayRoom);

		when(artworkRepository.findArtworkByArtworkID(artworkID)).thenAnswer((InvocationOnMock invocation) -> starryNight);
		when(roomRepository.findRoomByRoomID(storageRoomID)).thenAnswer((InvocationOnMock invocation) -> storage);

		Artwork artwork = artworkService.moveArtworkToRoom(artworkID, storageRoomID);

		assertNotNull(artwork);
		assertEquals(artworkID, artwork.getArtworkID());
		assertEquals(storageRoomID, artwork.getRoom().getRoomID());
		assertEquals(DisplayStatus.InStorage.name(), artwork.getStatus());
	}
  
	@Test
	public void testMoveArtworkToRoomDisplayRoom() {
		// Destination room (storage)
		final int displayRoomID = 2;
		final String size = "Big";
		final int maximumCapacity = 300;
		final DisplayRoom displayRoom = new DisplayRoom();
		displayRoom.setRoomID(displayRoomID);
		displayRoom.setSize(size);
		displayRoom.setMaximumCapacity(maximumCapacity);

		// Original room (display room)
		final int storageRoomID = 1;
        final Storage storage = new Storage();
		storage.setRoomID(storageRoomID);

		// Artwork
		final int artworkID = 10;
        final Artwork starryNight = new Artwork();
        starryNight.setArtworkID(artworkID);
		starryNight.setRoom(storage);

		when(artworkRepository.findArtworkByArtworkID(artworkID)).thenAnswer((InvocationOnMock invocation) -> starryNight);
		when(roomRepository.findRoomByRoomID(displayRoomID)).thenAnswer((InvocationOnMock invocation) -> displayRoom);

		Artwork artwork = artworkService.moveArtworkToRoom(artworkID, displayRoomID);

		assertNotNull(artwork);
		assertEquals(artworkID, artwork.getArtworkID());
		assertEquals(displayRoomID, artwork.getRoom().getRoomID());
		assertEquals(DisplayStatus.OnDisplay.name(), artwork.getStatus());
	}

	@Test
	public void testMoveArtworkToRoomFullDisplayRoom() {
		// Destination room (storage)
		final int displayRoomID = 2;
		final int maximumCapacity = 0; // for testing purposes, the display room has a capacity of 0 (i.e. always full)
		final DisplayRoom displayRoom = new DisplayRoom();
		displayRoom.setRoomID(displayRoomID);
		displayRoom.setMaximumCapacity(maximumCapacity);

		// Original room (display room)
		final int storageRoomID = 1;
        final Storage storage = new Storage();
		storage.setRoomID(storageRoomID);

		// Artwork
		final int artworkID = 10;
        final Artwork starryNight = new Artwork();
        starryNight.setArtworkID(artworkID);
		starryNight.setRoom(storage);

		when(artworkRepository.findArtworkByArtworkID(artworkID)).thenAnswer((InvocationOnMock invocation) -> starryNight);
		when(roomRepository.findRoomByRoomID(displayRoomID)).thenAnswer((InvocationOnMock invocation) -> displayRoom);

		MMSException exception = assertThrows(MMSException.class, () -> artworkService.moveArtworkToRoom(artworkID, displayRoomID));

		assertEquals("Display room is full.", exception.getMessage());
		assertEquals(HttpStatus.CONFLICT, exception.getStatus());
	}

	@Test
	public void testDeleteArtworkByArtworkID() {
		final int id = 5;
		final String name = "Starry Night";
        final String artist = "Vincent van Gogh";
        final Storage storage = new Storage();
		final int roomId = 1;
		storage.setRoomID(roomId);

        final Artwork starryNight = new Artwork();
        starryNight.setName(name);
        starryNight.setArtist(artist);
		starryNight.setRoom(storage);

		when(artworkRepository.findArtworkByArtworkID(id)).thenAnswer((InvocationOnMock invocation) -> starryNight);

		Artwork artwork = artworkService.deleteArtworkByArtworkID(id);
		
		assertNotNull(artwork);
		assertEquals(name, artwork.getName());
        assertEquals(artist, artwork.getArtist());
		verify(artworkRepository, times(1)).deleteArtworkByArtworkID(id);
	}

    @Test
	public void testDeleteArtworkByArtworkIDInvalid() {
		final int invalidID = 99;
		
		MMSException exception = assertThrows(MMSException.class, () -> artworkService.deleteArtworkByArtworkID(invalidID));

		assertEquals("Artwork with ID " + invalidID + " not found.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
	}
}


