package ca.mcgill.ecse321.mms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    
}
