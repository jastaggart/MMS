package ca.mcgill.ecse321.mms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import ca.mcgill.ecse321.mms.model.Employee;
import ca.mcgill.ecse321.mms.model.Loan;
import ca.mcgill.ecse321.mms.model.StaffMember;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.StaffMemberRepository;

@ExtendWith(MockitoExtension.class)
public class StaffMemberServiceTests {
	@Mock
    StaffMemberRepository staffMemberRepository;

    @Mock
    MMSRepository mmsRepository;

    @InjectMocks
    StaffMemberService staffMemberService;
    
    @Test
    public void testGetStaffMemberById() {
    	final int staffMemberId = 2;
    	final String username = "johnsmith";
    	final String email = "johnsmith@gmail.com";
    	final String password= "12345678";
    	final StaffMember staffMember = new Employee();
    	staffMember.setUsername(username);
    	staffMember.setEmail(email);
    	staffMember.setPassword(password);
    	
    	when(staffMemberRepository.findStaffMemberByStaffMemberID(staffMemberId)).thenAnswer((InvocationOnMock invocation) -> staffMember);
    	
    	StaffMember returnedStaffMember = staffMemberRepository.findStaffMemberByStaffMemberID(staffMemberId);
    	assertNotNull(returnedStaffMember);
    	assertEquals(username, staffMember.getUsername());
    	assertEquals(email, staffMember.getEmail());
    	assertEquals(password, staffMember.getPassword());
    }
    
    @Test
    public void testGetStaffMemberByInvalidId() {
        final int invalidId = -1;

        when(staffMemberRepository.findStaffMemberByStaffMemberID(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);
    	
        MMSException exception = assertThrows(MMSException.class, () -> staffMemberService.getStaffMemberById(invalidId));

        assertEquals("No staff member with the id " + invalidId + " was found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
    
    @Test
    public void testGetStaffMemberByName() {
    	final String username = "johnsmith";
    	final String email = "johnsmith@gmail.com";
    	final String password= "12345678";
    	final StaffMember staffMember = new Employee();
    	staffMember.setUsername(username);
    	staffMember.setEmail(email);
    	staffMember.setPassword(password);
    	
    	when(staffMemberRepository.findStaffMemberByUsername(username)).thenAnswer((InvocationOnMock invocation) -> staffMember);
    	
    	StaffMember returnedStaffMember = staffMemberRepository.findStaffMemberByUsername(username);
    	assertNotNull(returnedStaffMember);
    	assertEquals(username, staffMember.getUsername());
    	assertEquals(email, staffMember.getEmail());
    	assertEquals(password, staffMember.getPassword());
    }
    
    
    @Test
    public void testGetStaffMemberByInvalidName() {
        final String invalidUsername = "invalidUser";

        when(staffMemberRepository.findStaffMemberByUsername(invalidUsername)).thenAnswer((InvocationOnMock invocation) -> null);
    	
        MMSException exception = assertThrows(MMSException.class, () -> staffMemberService.getStaffMemberByName(invalidUsername));

        assertEquals("No staffmember with the name " +invalidUsername + " was found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
    
    @Test 
    public void testGetAllStaffMembers() {
    	final String firstUsername = "johnsmith";
    	final String firstEmail = "johnsmith@gmail.com";
    	final String firstPassword= "12345678";
    	final StaffMember firstStaffMember = new Employee();
    	firstStaffMember.setUsername(firstUsername);
    	firstStaffMember.setEmail(firstEmail);
    	firstStaffMember.setPassword(firstPassword);
    	
    	String secondUsername = "Bob";
        String secondPassword = "123";
        String secondEmail = "bob@mail.com";
        Employee secondStaffMember = new Employee();
        
        secondStaffMember.setUsername(secondUsername);
        secondStaffMember.setPassword(secondPassword);
        secondStaffMember.setEmail(secondEmail);
        
        ArrayList<StaffMember> staffMemberList = new ArrayList<StaffMember>();
        staffMemberList.add(firstStaffMember);
        staffMemberList.add(secondStaffMember);
        
        when(staffMemberRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> staffMemberList);
        
        List<StaffMember> returnedStaffMemberList = staffMemberRepository.findAll();
        
        assertNotNull(returnedStaffMemberList);
        assertEquals(returnedStaffMemberList, staffMemberList);
    }
    
    @Test 
    public void testGetAllStaffMemberEmptyList() {
        ArrayList<StaffMember> emptyStaffMemberList = new ArrayList<StaffMember>();
        when(staffMemberRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> emptyStaffMemberList);
        
        MMSException exception = assertThrows(MMSException.class, () -> staffMemberService.getAllStaffMembers());
        assertEquals("No staff members found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
    
    @Test 
    public void testCreateStaffMember() {
    	when (staffMemberRepository.save(any(StaffMember.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

    	final String username = "johnsmith";
    	final String email = "johnsmith@gmail.com";
    	final String password= "12345678";
    	final StaffMember staffMember = new Employee();
    	staffMember.setUsername(username);
    	staffMember.setEmail(email);
    	staffMember.setPassword(password);
    	
    	StaffMember returnedStaffMember = staffMemberRepository.save(staffMember);
    	
    	assertNotNull(returnedStaffMember);
    	assertEquals(username, staffMember.getUsername());
    	assertEquals(email, staffMember.getEmail());
    	assertEquals(password, staffMember.getPassword());
    }
    
    @Test
    public void testDeleteStaffMember() {
    	final String username = "johnsmith";
    	final String email = "johnsmith@gmail.com";
    	final String password= "12345678";
    	final StaffMember staffMember = new Employee();
    	staffMember.setUsername(username);
    	staffMember.setEmail(email);
    	staffMember.setPassword(password);
    	
    	when(staffMemberRepository.findStaffMemberByStaffMemberID(staffMember.getStaffMemberID())).thenAnswer((InvocationOnMock invocation) -> staffMember);
    	
    	StaffMember returnedStaffMember = staffMemberService.deleteStaffMember(staffMember.getStaffMemberID());
    	
    	assertNotNull(returnedStaffMember);
    	assertEquals(username, returnedStaffMember.getUsername());
    	assertEquals(email, returnedStaffMember.getEmail());
    	assertEquals(password, returnedStaffMember.getPassword());
    }
  
    @Test
    public void testDeleteStaffMemberByInvalidId() {
    	final int invalidId = -1;

        when(staffMemberRepository.findStaffMemberByStaffMemberID(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);
    	
        MMSException exception = assertThrows(MMSException.class, () -> staffMemberService.deleteStaffMember(invalidId));

        assertEquals("Staff member not found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
    
    
    @Test 
    public void testModifyStaffMember() {
    	final String oldUsername = "johnsmith";
    	final String oldEmail = "johnsmith@gmail.com";
    	final String oldPassword= "12345678";
    	final StaffMember staffMember = new Employee();
    	staffMember.setUsername(oldUsername);
    	staffMember.setEmail(oldEmail);
    	staffMember.setPassword(oldPassword);
    	
    	when(staffMemberRepository.findStaffMemberByStaffMemberID(staffMember.getStaffMemberID())).thenAnswer((InvocationOnMock invocation) -> staffMember);
    	
    	final String newUsername = "johnsmith2";
    	final String newEmail = "johnsmith@gmail.com2";
    	final String newPassword= "123456782";
    	
    	StaffMember returnedStaffMember = staffMemberService.modifyStaffMember(staffMember.getStaffMemberID(), newUsername, newEmail, newPassword);
    	assertNotNull(returnedStaffMember);
    	assertEquals(newUsername, returnedStaffMember.getUsername());
    	assertEquals(newEmail, returnedStaffMember.getEmail());
    	assertEquals(newPassword, returnedStaffMember.getPassword());
    }
    
    @Test
    public void testModifyStaffMemberByInvalidId() {
    	final int invalidId = -1;

        when(staffMemberRepository.findStaffMemberByStaffMemberID(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

    	final String username = "johnsmith";
    	final String email = "johnsmith@gmail.com";
    	final String password= "12345678";
        
        MMSException exception = assertThrows(MMSException.class, () -> staffMemberService.modifyStaffMember(invalidId,username,email,password));

        assertEquals("No staffmember with the id " +invalidId + " was found.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
    
}
