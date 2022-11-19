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
import ca.mcgill.ecse321.mms.model.Visitor;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.VisitorRepository;

@ExtendWith(MockitoExtension.class)
public class VisitorServiceTests {
    
    @Mock
    private VisitorRepository visitorRepo;

    @Mock
    private MMSRepository mmsRepo;

    @InjectMocks
    private VisitorService visitorService;

    @Test
    public void testCreateVisitor() {
        when(visitorRepo.save(any(Visitor.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        final String username = "artlover1";
        final String email = "artlover1@mail.com";
        final String password = "iloveart123";
        final Visitor artlover1 = new Visitor();
        artlover1.setUsername(username);
        artlover1.setEmail(email);
        artlover1.setPassword(password);
        Visitor returnedVisitor = visitorService.createVisitor(artlover1);

        assertNotNull(returnedVisitor);
        assertEquals(username, returnedVisitor.getUsername());
        assertEquals(email, returnedVisitor.getEmail());
        assertEquals(password, returnedVisitor.getPassword());
        verify(visitorRepo, times(1)).save(artlover1);
    }

    @Test
    public void testCreateVisitorByInvalidEmail() {
        final String username = "artlover1";
        final String email = "artlover1@mailcom.";
        final String password = "iloveart123";
        final Visitor artlover1 = new Visitor();
        artlover1.setUsername(username);
        artlover1.setEmail(email);
        artlover1.setPassword(password);
        MMSException exception = assertThrows(MMSException.class, () -> visitorService.createVisitor(artlover1));

        assertEquals("Invalid email.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testCreateVisitorByTakenEmail() {
       
        final String existingUsername = "artlover2";
        final String existingEmail = "artlover1@mail.com";
        final String existingPassword = "iloveart123";
        final Visitor existingVisitor = new Visitor();
        existingVisitor.setUsername(existingUsername);
        existingVisitor.setEmail(existingEmail);
        existingVisitor.setPassword(existingPassword);
       
        final String username = "artlover1";
        final String email = "artlover1@mail.com";
        final String password = "iloveart12";
        final Visitor artlover1 = new Visitor();
        artlover1.setUsername(username);
        artlover1.setEmail(email);
        artlover1.setPassword(password);

        when(visitorRepo.findVisitorByEmail(email)).thenAnswer((InvocationOnMock invocation) -> existingVisitor);
        MMSException exception = assertThrows(MMSException.class, () -> visitorService.createVisitor(artlover1));

        assertEquals("A visitor with this email is already registered.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testCreateVisitorByTakenUsername() {
        
        final String existingUsername = "artlover1";
        final Visitor existingVisitor = new Visitor();
        existingVisitor.setUsername(existingUsername);
        
        final String username = "artlover1";
        final String email = "artlover2@mail.com";
        final String password = "iloveart12";
        final Visitor artlover1 = new Visitor();
        artlover1.setUsername(username);
        artlover1.setEmail(email);
        artlover1.setPassword(password);
        when(visitorRepo.findVisitorByUsername(username)).thenAnswer((InvocationOnMock invocation) -> existingVisitor);

        MMSException exception = assertThrows(MMSException.class, () -> visitorService.createVisitor(artlover1));

        assertEquals("This username is not available.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testCreateVisitorByBlankUsername() {
        final String username = "";
        final String email = "artlover1@mail.com";
        final String password = "iloveart123";
        final Visitor visitor = new Visitor();
        visitor.setUsername(username);
        visitor.setEmail(email);
        visitor.setPassword(password);

        MMSException exception = assertThrows(MMSException.class, () -> visitorService.createVisitor(visitor));

         assertEquals("Username cannot be blank.", exception.getMessage());
         assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
     }

    @Test
    public void testGetVisitorById() {
        final int id = 2;
        final String username = "artlover1";
        final String email = "artlover1@mail.com";
        final String password = "iloveart123";
        final Visitor artlover1 = new Visitor();
        artlover1.setVisitorID(id);
        artlover1.setUsername(username);
        artlover1.setEmail(email);
        artlover1.setPassword(password);
        when(visitorRepo.findVisitorByVisitorID(id)).thenAnswer((InvocationOnMock invocation) -> artlover1);
        
        Visitor visitor = visitorService.getVisitorById(id);

        assertNotNull(visitor);
        assertEquals(username, visitor.getUsername());
        assertEquals(email, visitor.getEmail());
        assertEquals(password, visitor.getPassword());
    }

    @Test
    public void testGetVisitorByInvalidId() {
        final int id = 2;
        when(visitorRepo.findVisitorByVisitorID(id)).thenAnswer((InvocationOnMock invocation) -> null);
        
        MMSException exception = assertThrows(MMSException.class, () -> visitorService.getVisitorById(id));
        assertEquals("Visitor account not found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testGetVisitorByUsername() {
        final int id = 2;
        final String username = "artlover1";
        final String email = "artlover1@mail.com";
        final String password = "iloveart123";
        final Visitor artlover1 = new Visitor();
        artlover1.setVisitorID(id);
        artlover1.setUsername(username);
        artlover1.setEmail(email);
        artlover1.setPassword(password);
        when(visitorRepo.findVisitorByUsername(username)).thenAnswer((InvocationOnMock invocation) -> artlover1);
        
        Visitor visitor = visitorService.getVisitorByUsername(username);

        assertNotNull(visitor);
        assertEquals(id, visitor.getVisitorID());
        assertEquals(email, visitor.getEmail());
        assertEquals(password, visitor.getPassword());
    }

    @Test
    public void testGetVisitorByInvalidUsername() {
        final String username = "invalid";
        when(visitorRepo.findVisitorByUsername(username)).thenAnswer((InvocationOnMock invocation) -> null);
        
        MMSException exception = assertThrows(MMSException.class, () -> visitorService.getVisitorByUsername(username));
        assertEquals("Visitor account with this username was not found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testGetVisitorByEmail() {
        final int id = 2;
        final String username = "artlover1";
        final String email = "artlover1@mail.com";
        final String password = "iloveart123";
        final Visitor artlover1 = new Visitor();
        artlover1.setVisitorID(id);
        artlover1.setUsername(username);
        artlover1.setEmail(email);
        artlover1.setPassword(password);
        when(visitorRepo.findVisitorByEmail(email)).thenAnswer((InvocationOnMock invocation) -> artlover1);
        
        Visitor visitor = visitorService.getVisitorByEmail(email);

        assertNotNull(visitor);
        assertEquals(username, visitor.getUsername());
        assertEquals(password, visitor.getPassword());
        assertEquals(id, visitor.getVisitorID());
    }

    @Test
    public void testGetVisitorByInvalidEmail() {
        final String email = "invalid@mail.com";
        when(visitorRepo.findVisitorByEmail(email)).thenAnswer((InvocationOnMock invocation) -> null);
        
        MMSException exception = assertThrows(MMSException.class, () -> visitorService.getVisitorByEmail(email));
        assertEquals("Visitor account with this email was not found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testGetAllVisitors() {
        final String username = "bugsB";
        final String email = "whatsupdoc@mail.com";
        final String password = "carrots";
        final Visitor visitor = new Visitor();
        visitor.setUsername(username);
        visitor.setEmail(email);
        visitor.setPassword(password);
        
        final String username2 = "lolaB";
        final String email2 = "lola@mail.com";
        final String password2 = "bugs";
        final Visitor visitor2 = new Visitor();
        visitor2.setUsername(username2);
        visitor2.setEmail(email2);
        visitor2.setPassword(password2);

        final ArrayList<Visitor> visitors = new ArrayList<>();
        visitors.add(visitor);
        visitors.add(visitor2);

        when(visitorRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> visitors);
        List<Visitor> visitorsList = visitorService.getAllVisitors();

        assertNotNull(visitorsList);
        assertEquals(visitors, visitorsList);
    }

    @Test 
    public void testGetAllVisitorsWhenNoVisitors() {
        when(visitorRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> null);
        MMSException exception = assertThrows(MMSException.class, () -> visitorService.getAllVisitors());
        assertEquals("No visitor accounts found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    public void testDeleteVisitorById() {
      
        final int id = 2;
        final String username = "garfield";
        final Visitor visitor = new Visitor();
        visitor.setVisitorID(id);
        visitor.setUsername(username);

        when(visitorRepo.findVisitorByVisitorID(id)).thenAnswer((InvocationOnMock invocation) -> visitor);
        Visitor deletedVisitor = visitorService.deleteVisitorById(id);

        assertNotNull(deletedVisitor);
        assertEquals(id, deletedVisitor.getVisitorID());
        assertEquals(username, deletedVisitor.getUsername());
    }

    @Test
    public void testDeleteVisitorByInvalidId() {

        final int id = 999;
        when(visitorRepo.findVisitorByVisitorID(id)).thenAnswer((InvocationOnMock invocation) -> null);

        MMSException exception = assertThrows(MMSException.class, () -> visitorService.deleteVisitorById(id));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Visitor account not found.", exception.getMessage());
    }

    @Test
    public void testEditVisitorUsername() {
        final String currentUsername = "winniethepooh";
        Visitor visitor = new Visitor();
        visitor.setUsername(currentUsername);
        final String newUsername = "honeylover";

        when(visitorRepo.findVisitorByUsername(newUsername)).thenAnswer((InvocationOnMock invocation) -> null);
        when(visitorRepo.save(visitor)).thenAnswer((InvocationOnMock invocation) -> visitor);

        Visitor editedVisitor = visitorService.editVisitorUsername(visitor, newUsername);
        assertNotNull(visitor);
        assertEquals(newUsername, editedVisitor.getUsername());
    }

    @Test
    public void testEditVisitorUsernameWithTakenUsername() {
        final String currentUsername = "winniethepooh";
        Visitor visitor = new Visitor();
        visitor.setUsername(currentUsername);
        final String existingUsername = "honeylover";
        Visitor existingVisitor = new Visitor();
        existingVisitor.setUsername(existingUsername);

        when(visitorRepo.findVisitorByUsername(existingUsername)).thenAnswer((InvocationOnMock invocation) -> existingVisitor);
        
        MMSException exception = assertThrows(MMSException.class, () -> visitorService.editVisitorUsername(visitor, existingUsername));
        assertEquals("This username is not available.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    public void testEditVisitorUsernameWithEmptyUsername() {
        Visitor visitor = new Visitor();
        final String newUsername = "";
    
        MMSException exception = assertThrows(MMSException.class, () -> visitorService.editVisitorUsername(visitor, newUsername));
        assertEquals("Username cannot be blank.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testEditVisitorEmail() {
        final String email = "charliebrown@mail.com";
        Visitor visitor = new Visitor();
        
        when(visitorRepo.findVisitorByEmail(email)).thenAnswer((InvocationOnMock invocation) -> null);
        Visitor editedVisitor = visitorService.editVisitorEmail(visitor, email);

        assertNotNull(editedVisitor);
        assertEquals(email, editedVisitor.getEmail());
    }

    @Test
    public void testEditVisitorEmailWithTakenEmail() {
        final String email = "charliebrown@mail.com";
        Visitor existingVisitor = new Visitor();
        //existingVisitor.setEmail(email);
        Visitor visitor = new Visitor();
        when(visitorRepo.findVisitorByEmail(email)).thenAnswer((InvocationOnMock invocation) -> existingVisitor);
        
        MMSException exception = assertThrows(MMSException.class, () -> visitorService.editVisitorEmail(visitor, email));
    
        assertEquals("A visitor with this email is already registered.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void testEditVisitorPassword() {
        final String username = "imhungry";
        final String currentPassword = "soupdumplings";
        final String newPassword = "sushi";
        Visitor visitor = new Visitor();
        visitor.setUsername(username);
        visitor.setPassword(currentPassword);

        when(visitorRepo.findVisitorByUsername(username)).thenAnswer((InvocationOnMock invocation) -> visitor);
        when(visitorRepo.save(visitor)).thenAnswer((InvocationOnMock invocation) -> visitor);

        Visitor editedVisitor = visitorService.editVisitorPassword(visitor, currentPassword, newPassword);

        assertNotNull(editedVisitor);
        assertEquals(newPassword, editedVisitor.getPassword());
    }

    @Test
    public void testEditVisitorPasswordWithInvalidPassword() {
        final String username = "imhungry";
        final String currentPassword = "soupdumplings";
        final String newPassword = "sushi";
        Visitor visitor = new Visitor();
        visitor.setUsername(username);
        visitor.setPassword(currentPassword);

        when(visitorRepo.findVisitorByUsername(username)).thenAnswer((InvocationOnMock invocation) -> visitor);
        
        MMSException exception = assertThrows(MMSException.class, () -> visitorService.editVisitorPassword(visitor, newPassword, newPassword));

        assertEquals("Incorrect password.", exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }

    @Test
    public void testAuthenticateVisitor() {
        final String username = "snoopy";
        final String password = "joecool";
        final Visitor visitor = new Visitor();
        visitor.setUsername(username);
        visitor.setPassword(password);

        when(visitorRepo.findVisitorByUsername(username)).thenAnswer((InvocationOnMock invocation) -> visitor);

        Visitor authenticatedVisitor = visitorService.authenticateVisitor(username, password);

        assertNotNull(authenticatedVisitor);
        assertEquals(username, authenticatedVisitor.getUsername());
        assertEquals(password, authenticatedVisitor.getPassword());
    }

    @Test
    public void testAuthenticateVisitorByEmptyUsername() {
        final String username = "";
        final String password = "hihi";

        MMSException exception = assertThrows(MMSException.class, () -> visitorService.authenticateVisitor(username, password));
        assertEquals("Invalid username input.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
    @Test
    public void testAuthenticateVisitorByInvalidUsername() {
        final String username = "hello";
        final String password = "hihi";
        when(visitorRepo.findVisitorByUsername(username)).thenAnswer((InvocationOnMock invocation) -> null);

        MMSException exception = assertThrows(MMSException.class, () -> visitorService.authenticateVisitor(username, password));
        assertEquals("Invalid username input.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
    @Test
    public void testAuthenticateVisitorByInvalidPassword() {
        final String username = "hello";
        final String password = "hihi";
        final String wrongPassword = "byebye";
        Visitor visitor = new Visitor();
        visitor.setUsername(username);
        visitor.setPassword(password);

        when(visitorRepo.findVisitorByUsername(username)).thenAnswer((InvocationOnMock invocation) -> visitor);

        MMSException exception = assertThrows(MMSException.class, () -> visitorService.authenticateVisitor(username, wrongPassword));
        assertEquals("Incorrect password.", exception.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }
}
