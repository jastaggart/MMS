package ca.mcgill.ecse321.mms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.Locale;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.model.Pass;
import ca.mcgill.ecse321.mms.model.Visitor;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PassRepositoryTests {

    @Autowired
    private MMSRepository mmsRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private PassRepository passRepository;

    @AfterEach
    public void clearDatabase() {
        passRepository.deleteAll();
        visitorRepository.deleteAll();
        mmsRepository.deleteAll();
    }

    @Test
    public void testPersistandLoadPass() throws ParseException {

        // Creating the MMS instance
        MMS museum = new MMS();

        // Creating the fields for the MMS instance
        Time openingTime = Time.valueOf("09:00:00");
        Time closingTime = Time.valueOf("18:00:00");
        int museumPassFee = 30;
        
        // Setting the fields for MMS
        museum.setOpeningHours(openingTime);
        museum.setClosingHours(closingTime);
        museum.setPassFee(museumPassFee);

        // Saving the MMS instance
        museum = mmsRepository.save(museum);

        // Creating the Visitor instance
        Visitor visitor = new Visitor();

        // Creating the fields for the Visitor instance
        String username = "Joe";
        String password = "password123";
        String email = "joe@email.com";
        
        // Setting the fields for Visitor
        visitor.setUsername(username);
        visitor.setPassword(password);
        visitor.setEmail(email);
        visitor.setMuseumManagementSystem(museum);

        //saving the Visitor instance
        visitor = visitorRepository.save(visitor);

        // Creating the Pass instance
        Pass pass = new Pass();

        // Creating date for Pass
        final String date = "02-02-2002";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date passDate = formatter.parse(date);

        // Setting the fields for Pass
        pass.setPassDate(passDate);
        pass.setPassPurchaser(visitor);

        // Saving the pass instance
        pass = passRepository.save(pass);

        // passID of Pass object
        int passID = pass.getPassID();

        pass = null;

        //Retrieving the Pass object from passRepository table
        pass = passRepository.findPassByPassID(passID);

        //Assertion Tests
        assertNotNull(pass);
        assertNotNull(pass.getPassPurchaser());
        assertEquals(passID, pass.getPassID());
        assertEquals(passDate, pass.getPassDate());

    }

}