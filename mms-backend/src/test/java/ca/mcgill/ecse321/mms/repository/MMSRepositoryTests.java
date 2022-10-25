package ca.mcgill.ecse321.mms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Time;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.mms.model.MMS;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MMSRepositoryTests {

    @Autowired
    private MMSRepository mmsRepository;

    @AfterEach
	public void clearDatabase() {
		mmsRepository.deleteAll();
	}

    @Test
    public void testPersistAndLoadMMS() {

        // Creating the fields that will be set to the MMS instance
        Time openingTime = Time.valueOf("09:00:00");
        Time closingTime = Time.valueOf("21:00:00");
        int museumPassFee = 20;

        // Creating the MMS instance to which the rooms will be linked to
        MMS museum = new MMS();

        // Setting the fields of the created MMS instance
        museum.setOpeningHours(openingTime);
        museum.setClosingHours(closingTime);
        museum.setPassFee(museumPassFee);

        // Saving the MMS instance in the mmsRepository table
        museum = mmsRepository.save(museum);

        // Fetching the museumID of the newly created MMS object that was saved in the mmsRepository table
        int museumID = museum.getMuseumID();
        museum = null;

        //Retrieving the MMS object from the mmsRepository table using the method defined in the MMSRepository interface
        museum = mmsRepository.findMMSByMuseumID(museumID);

        // Assertion Tests
        assertNotNull(museum);
        assertEquals(openingTime, museum.getOpeningHours());
        assertEquals(closingTime, museum.getClosingHours());
        assertEquals(museumPassFee, museum.getPassFee());
        assertEquals(museumID, museum.getMuseumID());

    }
}