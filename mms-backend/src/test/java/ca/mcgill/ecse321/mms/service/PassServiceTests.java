package ca.mcgill.ecse321.mms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.mms.dto.PassResponseDto;
import ca.mcgill.ecse321.mms.exception.MMSException;
import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.model.Pass;
import ca.mcgill.ecse321.mms.model.Visitor;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.PassRepository;
import ca.mcgill.ecse321.mms.repository.VisitorRepository;

@ExtendWith(MockitoExtension.class)
public class PassServiceTests {
    
    @Mock
    PassRepository passRepository;

    @Mock
    VisitorRepository visitorRepository;

    @Mock
    MMSRepository mmsRepository;

    @InjectMocks
    PassService passService;

    @Test
    public void testCreatePass() throws ParseException {
        when (passRepository.save(any(Pass.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        final String date = "02-02-2002";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date parsedDate = formatter.parse(date);
        final Visitor visitor = new Visitor();

        final Pass pass = new Pass();
        pass.setPassDate(parsedDate);
        pass.setPassPurchaser(visitor);

        Pass returnedPass = passService.createPass(visitor, date);

        assertNotNull(returnedPass);
        assertEquals(parsedDate, returnedPass.getPassDate());
        assertEquals(visitor, returnedPass.getPassPurchaser());
    }

    @Test
    public void testGetPassById() {
        final int passId = 1;
        final Date date = new Date();
        final Visitor visitor = new Visitor();

        Pass pass = new Pass();
        pass.setPassDate(date);
        pass.setPassPurchaser(visitor);

        when(passRepository.findPassByPassID(passId)).thenAnswer((InvocationOnMock invocation) -> pass);

        Pass returnedPass = passService.getPassById(passId);

        assertNotNull(returnedPass);
        assertEquals(visitor, returnedPass.getPassPurchaser());
        assertEquals(date, returnedPass.getPassDate());
        
    }

    @Test
    public void testGetPassByInvalidId() {
        final int invalidId = -1;

        when(passRepository.findPassByPassID(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

        MMSException exception = assertThrows(MMSException.class, () -> passService.getPassById(invalidId));

        assertEquals("Pass with ID " + invalidId + " not found.", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testGetPassesByVisitorId() {
        final Date firstDate = new Date();
        final int visitorId = 1;
        final Visitor visitor = new Visitor();
        visitor.setVisitorID(visitorId);

        Pass firstPass = new Pass();
        firstPass.setPassDate(firstDate);
        firstPass.setPassPurchaser(visitor);

        final Date secondDate = new Date();

        Pass secondPass = new Pass();
        secondPass.setPassDate(secondDate);
        secondPass.setPassPurchaser(visitor);

        final ArrayList<Pass> passes = new ArrayList<Pass>();
        passes.add(firstPass);
        passes.add(secondPass);

        when(passRepository.findPassByPassPurchaserVisitorID(visitor.getVisitorID())).thenAnswer((InvocationOnMock invocation) -> passes);

        List<Pass> returnedPasses = passService.getPassesByVisitorId(visitor.getVisitorID());
	
		assertNotNull(returnedPasses);
		assertEquals(passes, returnedPasses);


    }

    @Test
    public void testGetPassesByVisitorIdWithNoPasses() {
        final int visitorId = 2;

        when(passRepository.findPassByPassPurchaserVisitorID(visitorId)).thenAnswer((InvocationOnMock invocation) -> null);

        MMSException exception = assertThrows(MMSException.class, () -> passService.getPassesByVisitorId(visitorId));

        assertEquals("No passes owned by visitor with visitorID " + visitorId + ".", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testGetAllPasses() {
        final Date firstDate = new Date();
        final int visitorId = 1;
        final Visitor visitor = new Visitor();
        visitor.setVisitorID(visitorId);

        Pass firstPass = new Pass();
        firstPass.setPassDate(firstDate);
        firstPass.setPassPurchaser(visitor);

        final Visitor secondVisitor = new Visitor();
        final Date secondDate = new Date();

        Pass secondPass = new Pass();
        secondPass.setPassDate(secondDate);
        secondPass.setPassPurchaser(secondVisitor);

        final ArrayList<Pass> passes = new ArrayList<Pass>();
        passes.add(firstPass);
        passes.add(secondPass);

        when(passRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> passes);

        List<Pass> returnedPasses = passService.getAllPasses();
		
		assertNotNull(returnedPasses);
		assertEquals(passes, returnedPasses);
	}

    
}
