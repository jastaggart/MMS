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
import ca.mcgill.ecse321.mms.model.DisplayRoom;
import ca.mcgill.ecse321.mms.model.Room;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.RoomRepository;

@ExtendWith(MockitoExtension.class)
public class DisplayRoomServiceTests {

    // Replace the repository with a "mock" that exposes the same interface
	@Mock
	RoomRepository roomRepository;

    // Replace the repository with a "mock" that exposes the same interface
	@Mock
	MMSRepository mmsRepository;
	
	// Get a service that uses the mock repository
	@InjectMocks
	DisplayRoomService displayRoomService;

    @Test
	public void testCreateDisplayRoom() {
		// Just return the room with no modification
		when(roomRepository.save(any(Room.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		
		final String size = "Big";
        final int maximumCapacity = 300;
        final int displayRoomNumber = 5;
		final DisplayRoom newDisplayRoom = new DisplayRoom();
        newDisplayRoom.setSize(size);
        newDisplayRoom.setMaximumCapacity(maximumCapacity);
        newDisplayRoom.setDisplayRoomNumber(displayRoomNumber);
		DisplayRoom returnedDisplayRoom = displayRoomService.createDisplayRoom(newDisplayRoom);
		
		assertNotNull(returnedDisplayRoom);
		assertEquals(size, returnedDisplayRoom.getSize());
        assertEquals(maximumCapacity, returnedDisplayRoom.getMaximumCapacity());
        assertEquals(displayRoomNumber, returnedDisplayRoom.getDisplayRoomNumber());

		// Check that the service actually saved the displayroom
		verify(roomRepository, times(1)).save(newDisplayRoom);
	}

	@Test
	public void testCreateInvalidDisplayRoomBySize() {

		final String size = "Invalied Size";
        final int maximumCapacity = 200;
        final int displayRoomNumber = 5;
		final DisplayRoom newDisplayRoom = new DisplayRoom();
        newDisplayRoom.setSize(size);
        newDisplayRoom.setMaximumCapacity(maximumCapacity);
        newDisplayRoom.setDisplayRoomNumber(displayRoomNumber);
		MMSException exception = assertThrows(MMSException.class, () -> displayRoomService.createDisplayRoom(newDisplayRoom));

		assertEquals("Size of room must be Big or Small", exception.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
	}

	@Test
	public void testCreateInvalidDisplayRoomByRoomNumber() {

		final String size = "Small";
        final int maximumCapacity = 200;
        final int displayRoomNumber = -10;
		final DisplayRoom newDisplayRoom = new DisplayRoom();
        newDisplayRoom.setSize(size);
        newDisplayRoom.setMaximumCapacity(maximumCapacity);
        newDisplayRoom.setDisplayRoomNumber(displayRoomNumber);
		MMSException exception = assertThrows(MMSException.class, () -> displayRoomService.createDisplayRoom(newDisplayRoom));

		assertEquals("Display room number cannot be less than 0", exception.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
	}

	@Test
	public void testCreateInvalidDisplayRoomBySizeAndMaxCapacity() {

		final String size = "Big";
        final int maximumCapacity = 200;
        final int displayRoomNumber = 5;
		final DisplayRoom newDisplayRoom = new DisplayRoom();
        newDisplayRoom.setSize(size);
        newDisplayRoom.setMaximumCapacity(maximumCapacity);
        newDisplayRoom.setDisplayRoomNumber(displayRoomNumber);

		MMSException exception = assertThrows(MMSException.class, () -> displayRoomService.createDisplayRoom(newDisplayRoom));

		assertEquals("Incompatible entries for room size and maximum capacity", exception.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
	}


	@Test
	public void testGetDisplayRoomByID() {

		final int id = 72;
		final String size = "Small";
		final int maximumCapacity = 200;
		final int displayRoomNumber = 26;
		final DisplayRoom smallDisplayRoom = new DisplayRoom();
		smallDisplayRoom.setSize(size);
		smallDisplayRoom.setMaximumCapacity(maximumCapacity);
		smallDisplayRoom.setDisplayRoomNumber(displayRoomNumber);

		when(roomRepository.findRoomByRoomID(id)).thenAnswer((InvocationOnMock invocation) -> smallDisplayRoom);

		DisplayRoom displayRoom = displayRoomService.getDisplayRoomById(id);

		assertNotNull(displayRoom);
		assertEquals(size, displayRoom.getSize());
		assertEquals(maximumCapacity, displayRoom.getMaximumCapacity());
		assertEquals(displayRoomNumber, displayRoom.getDisplayRoomNumber());

	}

	@Test
	public void testGetDisplayRoomByInvalidId() {
		final int invalidId = 9999;
		
		when(roomRepository.findRoomByRoomID(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

		MMSException exception = assertThrows(MMSException.class, () -> displayRoomService.getDisplayRoomById(invalidId));

		assertEquals("No room exists with provided roomID", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
	}

	@Test
	public void testGetDisplayRoomByRoomNumber() {
		final String size = "Big";
		final int maximumCapacity = 300;
		final int displayRoomNumber = 39;
		final DisplayRoom bigDisplayRoom = new DisplayRoom();
		bigDisplayRoom.setSize(size);
		bigDisplayRoom.setMaximumCapacity(maximumCapacity);
		bigDisplayRoom.setDisplayRoomNumber(displayRoomNumber);
		List<Room> displayRooms = new ArrayList<>();
		displayRooms.add(bigDisplayRoom);

		when(roomRepository.findAll()).thenReturn(displayRooms);

		DisplayRoom displayRoom = displayRoomService.getDisplayRoomByRoomNumber(displayRoomNumber);

		assertNotNull(displayRoom);
		assertEquals(size, displayRoom.getSize());
		assertEquals(maximumCapacity, displayRoom.getMaximumCapacity());
		assertEquals(displayRoomNumber, displayRoom.getDisplayRoomNumber());

	}

	@Test
	public void testGetDisplayRoomByInvalidRoomNumber() {
		final int invalidDisplayRoomNumber = 42;
		List<Room> displayRooms = new ArrayList<>();

		when(roomRepository.findAll()).thenReturn(displayRooms);

		MMSException exception = assertThrows(MMSException.class, () -> displayRoomService.getDisplayRoomByRoomNumber(invalidDisplayRoomNumber));

		assertEquals("No room exists with provided roomNumber", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

	}
    
}


