package ca.mcgill.ecse321.mms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.model.Pass;
import ca.mcgill.ecse321.mms.model.Visitor;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class VisitorRepositoryTests {

    @Autowired
    private MMSRepository mmsRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired PassRepository passRepository;

    @AfterEach
    public void clearDatabase(){
        passRepository.deleteAll();
        visitorRepository.deleteAll();
        mmsRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadVisitor(){
        
        //Creating a MMS object:
        //Create the fields that will be set to the MMS instance
        Time openingTime = Time.valueOf("08:00:00");
        Time closingTime = Time.valueOf("17:00:00");
        int museumPassFee = 25;

        //Create the MMS instance to which a visitor will be linked to 
        MMS museum = new MMS();

        //Set the parameters of the created MMS instance
        museum.setOpeningHours(openingTime);
        museum.setClosingHours(closingTime);
        museum.setPassFee(museumPassFee);

        //Save the MMS instance in the mmsRepository table
        museum =  mmsRepository.save(museum);

        //Creating a visitor object:
        //Create the fields that will be set to the Visitor instance 
        String username = "artlover1";
        String password = "iloveart1234";
        String email = "artlover1@gmail.com";
        Visitor visitor = new Visitor();
        
        //Set visitor paramaters
        visitor.setUsername(username);
        visitor.setPassword(password);
        visitor.setEmail(email);
        visitor.setMuseumManagementSystem(museum);

        //Save visitor object to database
        visitor = visitorRepository.save(visitor);

        //Creating a Pass instance:
        //Create field for the Pass instance
        Date passDate = Date.valueOf("2022-10-31");
        Pass pass = new Pass();
        
        //Set fields for the Pass instance
        pass.setPassDate(passDate);
        pass.setPassPurchaser(visitor);

        //Save pass to database
        pass = passRepository.save(pass);

        //Get id of the Visitor object, MMS object, and Pass object
        int visitorId = visitor.getVisitorID();
        int museumId = museum.getMuseumID();
        int passId = pass.getPassID();
        
        //Read MMS object from database
        museum = null;
        museum = mmsRepository.findMMSByMuseumID(museumId);
        
        //Read visitor object from database
        visitor = null;
        visitor = visitorRepository.findVisitorByVisitorID(visitorId);

        //Read pass object from database
        pass = null;
        pass = passRepository.findPassByPassID(passId);

        //Assert that visitor object has correct attributes
        assertNotNull(visitor);
        assertEquals(visitorId, visitor.getVisitorID());
        assertEquals(username, visitor.getUsername());
        assertEquals(password, visitor.getPassword());
        assertEquals(email, visitor.getEmail());

        assertNotNull(museum);
        assertEquals(museum.getMuseumID(), visitor.getMuseumManagementSystem().getMuseumID());
        
        assertNotNull(pass);
        assertEquals(visitorId, pass.getPassPurchaser().getVisitorID());
        
    }
}