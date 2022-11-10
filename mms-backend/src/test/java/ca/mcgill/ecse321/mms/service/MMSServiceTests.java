package ca.mcgill.ecse321.mms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Time;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.mms.exception.MMSException;
import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.repository.MMSRepository;

@ExtendWith(MockitoExtension.class)
public class MMSServiceTests {

     // Replace the repository with a "mock" that exposes the same interface
	@Mock
	MMSRepository mmsRepository;

    // Get a service that uses the mock repository
	@InjectMocks
	MMSService mmsService;

    @Test
	public void testGetMuseum() {

		final int id = 1;
		final Time openingTime= java.sql.Time.valueOf("09:00:00");
        final Time closingTime= java.sql.Time.valueOf("21:00:00");
        final int passFee = 25;
		final MMS museum = new MMS();
		museum.setOpeningHours(openingTime);
		museum.setClosingHours(closingTime);
		museum.setPassFee(passFee);

		when(mmsRepository.findMMSByMuseumID(id)).thenAnswer((InvocationOnMock invocation) -> museum);

		MMS mms = mmsService.getMuseum();

		assertNotNull(mms);
		assertEquals(openingTime, mms.getOpeningHours());
		assertEquals(closingTime, mms.getClosingHours());
		assertEquals(passFee, museum.getPassFee());
	}

    @Test
    public void testUpdateMuseumPassFee() {

		final int id = 1;
		final Time openingTime= java.sql.Time.valueOf("09:00:00");
        final Time closingTime= java.sql.Time.valueOf("21:00:00");
        final int passFee = 40;
		final MMS museum = new MMS();
		museum.setOpeningHours(openingTime);
		museum.setClosingHours(closingTime);
		museum.setPassFee(passFee);

		when(mmsRepository.findMMSByMuseumID(id)).thenAnswer((InvocationOnMock invocation) -> museum);

		MMS mms = mmsService.updateMuseumPassFee(40);

		assertNotNull(mms);
		assertEquals(openingTime, mms.getOpeningHours());
		assertEquals(closingTime, mms.getClosingHours());
		assertEquals(passFee, museum.getPassFee());
	}

    @Test
    public void testUpdateMuseumPassFeeByInvalidFee() {

        final int invalidPassFee = -10;

		
        MMSException exception = assertThrows(MMSException.class, () -> mmsService.updateMuseumPassFee(invalidPassFee));
        
        assertEquals("Pass fee cannot be less than 0$", exception.getMessage());
		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
	}
    
    
}
