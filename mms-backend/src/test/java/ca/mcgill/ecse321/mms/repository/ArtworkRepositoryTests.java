package ca.mcgill.ecse321.mms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Time;
import java.sql.Date;

import ca.mcgill.ecse321.mms.model.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtworkRepositoryTests {
    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private MMSRepository mmsRepository;
    
    @Autowired
    private RoomRepository roomRepository;

    @AfterEach
    public void clearDatabase() {
        artworkRepository.deleteAll();
        loanRepository.deleteAll();
        roomRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadArtwork() {
        // Creating the fields that will be set to the MMS instance
        Time openingTime = Time.valueOf("08:00:00");
        Time closingTime = Time.valueOf("17:00:00");
        int museumPassFee = 25;
        // Creating the MMS instance 
        MMS museum = new MMS();
        // Setting the fields of the created MMS instance
        museum.setOpeningHours(openingTime);
        museum.setClosingHours(closingTime);
        museum.setPassFee(museumPassFee);
        // Saving the MMS instance in the mmsRepository table and fetch ID
        museum =  mmsRepository.save(museum);
        int museumID = museum.getMuseumID();


        // Creating the fields that will be set to the created Storage object
        int maximumCapacityBigRoom = 300;
        int displayRoomNumber = 2;
        // Creating the Storage object 
        Storage storage = new Storage();
        // Setting the fields of the created Storage object
        storage.setMuseumManagementSystem(museum);
        // Saving the Storage instance in the roomRepository table and fetch ID
        storage = roomRepository.save(storage);
        int storageID = storage.getRoomID();
        

        // Creating fields for Loan object
        int loanFee = 10;
        Date startDate = Date.valueOf("2022-10-25");
        Date endDate = Date.valueOf("2022-10-31");
        boolean isApproved = true;
        // Creating Loan object
        Loan loan = new Loan();
        // Setting fields of Loan object
        loan.setLoanFee(loanFee);
        loan.setStartDate(startDate);
        loan.setEndDate(endDate);
        loan.setIsApproved(isApproved);
        // Save Loan object in loanRepository table and fetch ID
        loan = loanRepository.save(loan);

        // Creating fields for Artwork object
        boolean availableForLoan = true;
        String status = DisplayStatus.InStorage.name();
        String name = "Painting";
        String artist = "John Smith";
        // Creating Artwork object
        Artwork artwork = new Artwork();
        // Setting fields of Artwork object
        artwork.setAvailableForLoan(availableForLoan);
        artwork.setName(name);
        artwork.setArtist(artist);
        artwork.setStatus(status);
        artwork.setRoom(storage);
        artwork.setMuseumManagementSystem(museum);
        artwork.addLoan(loan);
        // Saving Artwork instance in artworkRepository table and fetch ID
        artwork = artworkRepository.save(artwork);
        int artworkID = artwork.getArtworkID();


        artwork = null;
        // Retrieve Artwork object from artworkRepository table in database
        artwork = artworkRepository.findArtworkByArtworkID(artworkID);

        // Assertions
        // Check not null
        assertNotNull(artwork);

        // Check attributes
        assertEquals(availableForLoan, artwork.getAvailableForLoan());
        assertEquals(status, artwork.getStatus());
        assertEquals(name, artwork.getName());
        assertEquals(artist, artwork.getArtist());
        
        // Check Associations
        assertNotNull(artwork.getLoans());

        assertNotNull(artwork.getMuseumManagementSystem());
        assertEquals(museumID, artwork.getMuseumManagementSystem().getMuseumID());

        assertNotNull(artwork.getRoom());
        assertEquals(storageID, artwork.getRoom().getRoomID()); 
        
        





    }


}