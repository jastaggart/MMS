package ca.mcgill.ecse321.mms.service;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ca.mcgill.ecse321.mms.dto.ShiftRequestDto;
import ca.mcgill.ecse321.mms.dto.ShiftResponseDto;
import ca.mcgill.ecse321.mms.exception.MMSException;
import ca.mcgill.ecse321.mms.model.Artwork;
import ca.mcgill.ecse321.mms.model.Employee;
import ca.mcgill.ecse321.mms.model.Owner;
import ca.mcgill.ecse321.mms.model.Shift;
import ca.mcgill.ecse321.mms.model.StaffMember;
import ca.mcgill.ecse321.mms.repository.ShiftRepository;
import ca.mcgill.ecse321.mms.repository.StaffMemberRepository;


@ExtendWith(MockitoExtension.class)
public class ShiftServiceTests {
  
//Replace the repository with a "mock" that exposes the same interface
  @Mock
  ShiftRepository shiftRepository;
  
  @Mock
  StaffMemberRepository staffMemberRepository;
  
  @InjectMocks
  StaffMemberService staffMemberService = spy(StaffMemberService.class); //putting both @Spy @InjectMocks
  

  @InjectMocks
  ShiftService shiftService;
  
  @Test
  public void testCreateShift() {
      when(shiftRepository.save(any(Shift.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
      final int shiftAssignerID = 1;
      final int shiftAssigneeID = 2;
      final Owner mrKrabs = new Owner(); 
      final Employee spongeBob = new Employee();
      mrKrabs.setStaffMemberID(shiftAssignerID);
      spongeBob.setStaffMemberID(shiftAssigneeID);
      final int shiftID = 0;
      final String date = "2022-11-11";
      final String startHour = "09:00:00";
      final String endHour = "12:00:00";
      
      final Shift shift = new Shift();
      shift.setShiftID(shiftID);
      shift.setDate(Date.valueOf(date));
      shift.setStartHour(Time.valueOf(startHour));
      shift.setEndHour(Time.valueOf(endHour));

      
      when(staffMemberRepository.findStaffMemberByStaffMemberID(shiftAssignerID)).thenAnswer((InvocationOnMock invocation) -> mrKrabs);
      when(staffMemberRepository.findStaffMemberByStaffMemberID(shiftAssigneeID)).thenAnswer((InvocationOnMock invocation) -> spongeBob);
      
      ShiftResponseDto returnedShiftDto = shiftService.createShift(shift, shiftAssignerID, shiftAssigneeID);
      
      shift.setShiftAssigner(mrKrabs);  //linking shift assigner and assignee to shift so it can be compared with the returned dto
      shift.setShiftAssignee(spongeBob);

      assertNotNull(returnedShiftDto);
      assertEquals(date, returnedShiftDto.getDate());
      assertEquals(startHour, returnedShiftDto.getStartHour());
      assertEquals(endHour, returnedShiftDto.getEndHour());
      assertEquals(shiftAssignerID, returnedShiftDto.getShiftAssigner().getStaffMemberID());
      assertEquals(shiftAssigneeID, returnedShiftDto.getShiftAssignee().getStaffMemberID());
      verify(shiftRepository, times(1)).save(shift);
  }
  
  @Test
  public void testCreateShiftMissingFields() {   //Some of fields are missing (date, startHour, endHour)
    when(shiftRepository.save(any(Shift.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
    final int shiftAssignerID = 1;
    final int shiftAssigneeID = 2;
    final Owner mrKrabs = new Owner();
    final Employee spongeBob = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    spongeBob.setStaffMemberID(shiftAssigneeID);
    
    final String defaultDate = "2022-01-01";
    final String defaultStartHour = "08:00:00";
    final String defaultEndHour = "17:00:00";
    final Shift shift = new Shift();
    
    
    when(staffMemberRepository.findStaffMemberByStaffMemberID(shiftAssignerID)).thenAnswer((InvocationOnMock invocation) -> mrKrabs);
    when(staffMemberRepository.findStaffMemberByStaffMemberID(shiftAssigneeID)).thenAnswer((InvocationOnMock invocation) -> spongeBob);
    
    ShiftResponseDto returnedShiftDto = shiftService.createShift(shift, shiftAssignerID, shiftAssigneeID);

    shift.setShiftAssigner(mrKrabs);
    shift.setShiftAssignee(spongeBob);
    
    assertNotNull(returnedShiftDto);
    assertEquals(defaultDate, returnedShiftDto.getDate());
    assertEquals(defaultStartHour, returnedShiftDto.getStartHour());
    assertEquals(defaultEndHour, returnedShiftDto.getEndHour());
    assertEquals(shiftAssignerID, returnedShiftDto.getShiftAssigner().getStaffMemberID());
    assertEquals(shiftAssigneeID, returnedShiftDto.getShiftAssignee().getStaffMemberID());
    verify(shiftRepository, times(1)).save(shift);
  }
  
  @Test
  public void testCreateShiftInvalidAssigner() {    //Shift assigner is an employee, not the owner
    final int invalidShiftAssignerID = 3;
    final int shiftAssigneeID = 2;
    final Employee squidward = new Employee(); 
    final Employee spongeBob = new Employee();
    squidward.setStaffMemberID(invalidShiftAssignerID);
    spongeBob.setStaffMemberID(shiftAssigneeID);
    final int shiftID = 0;
    final String date = "2022-11-11";
    final String startHour = "09:00:00";
    final String endHour = "12:00:00";
    
    final Shift shift = new Shift();
    shift.setShiftID(shiftID);
    shift.setDate(Date.valueOf(date));
    shift.setStartHour(Time.valueOf(startHour));
    shift.setEndHour(Time.valueOf(endHour));

    
    MMSException exception = assertThrows(MMSException.class, () -> shiftService.createShift(shift, invalidShiftAssignerID, shiftAssigneeID));

    assertEquals("Employee not authorized to assign a shift.", exception.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

  }
  
  @Test
  public void testCreateShiftInvalidAssignee() {    //Shift assignee is the owner, not an employee
    final int shiftAssignerID = 1;
    final int invalidShiftAssigneeID = 1;
    final Owner mrKrabs = new Owner(); 
    mrKrabs.setStaffMemberID(shiftAssignerID);
    final int shiftID = 0;
    final String date = "2022-11-11";
    final String startHour = "09:00:00";
    final String endHour = "12:00:00";
    
    final Shift shift = new Shift();
    shift.setShiftID(shiftID);
    shift.setDate(Date.valueOf(date));
    shift.setStartHour(Time.valueOf(startHour));
    shift.setEndHour(Time.valueOf(endHour));

    
    MMSException exception = assertThrows(MMSException.class, () -> shiftService.createShift(shift, shiftAssignerID, invalidShiftAssigneeID));

    assertEquals("Cannot assign shift to the owner.", exception.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
  }
  
  @Test
  public void testCreateShiftMissingEmployee() {    //Shift assignee with shiftAssignee id not found
    final int shiftAssignerID = 1;
    final Owner plankton = new Owner();
    plankton.setStaffMemberID(shiftAssignerID);
    final int shiftID = 0;
    final String date = "2022-11-11";
    final String startHour = "14:00:00";
    final String endHour = "17:00:00";
    final Shift shift = new Shift();
    shift.setShiftID(shiftID);
    shift.setDate(Date.valueOf(date));
    shift.setStartHour(Time.valueOf(startHour));
    shift.setEndHour(Time.valueOf(endHour));
    
    final int emptyShiftAssigneeID = 99;
    
    
    when(staffMemberRepository.findStaffMemberByStaffMemberID(emptyShiftAssigneeID)).thenAnswer((InvocationOnMock invocation) -> null);

    MMSException exception = assertThrows(MMSException.class, () -> shiftService.createShift(shift, shiftAssignerID, emptyShiftAssigneeID));

    assertEquals("No staff member with the id 99 was found.", exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
  }
  
  @Test
  public void testCreateShiftInvalidHour() {    //EndHour is before StartHour 
    final int shiftAssignerID = 1;
    final int shiftAssigneeID = 2;
    final Owner mrKrabs = new Owner();
    final Employee squidWard = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    squidWard.setStaffMemberID(shiftAssigneeID);
    
    final String date = "2022-11-11";
    final String startHour = "12:00:00";
    final String endHour = "08:00:00";
    final Shift shift = new Shift();
    shift.setDate(Date.valueOf(date));
    shift.setStartHour(Time.valueOf(startHour));
    shift.setEndHour(Time.valueOf(endHour));
    
    MMSException exception = assertThrows(MMSException.class, () -> shiftService.createShift(shift, shiftAssignerID, shiftAssigneeID));

    assertEquals("Start time: 12:00:00 is not before End time: 08:00:00.", exception.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
  }
  
  @Test
  public void testGetShiftByShiftID() {
    final int shiftAssignerID = 1;
    final int shiftAssigneeID = 2;
    final Owner mrKrabs = new Owner(); 
    final Employee spongeBob = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    spongeBob.setStaffMemberID(shiftAssigneeID);

    final int shiftID = 1;
    final String date = "2022-11-11";
    final String startHour = "08:00:00";
    final String endHour = "12:00:00";
    final Shift shift = new Shift();
    shift.setShiftID(shiftID);
    shift.setDate(Date.valueOf(date));
    shift.setStartHour(Time.valueOf(startHour));
    shift.setEndHour(Time.valueOf(endHour));
    shift.setShiftAssigner(mrKrabs);
    shift.setShiftAssignee(spongeBob);

    when(shiftRepository.findShiftByShiftID(shiftID)).thenAnswer((InvocationOnMock invocation) -> shift);

    // Test that the service behaves properly
    ShiftResponseDto returnedShiftDto = shiftService.getShiftByShiftID(shiftID);

    assertNotNull(returnedShiftDto);
    assertEquals(date, returnedShiftDto.getDate());
    assertEquals(startHour, returnedShiftDto.getStartHour());
    assertEquals(endHour, returnedShiftDto.getEndHour());
    assertEquals(shiftAssignerID, returnedShiftDto.getShiftAssigner().getStaffMemberID());
    assertEquals(shiftAssigneeID, returnedShiftDto.getShiftAssignee().getStaffMemberID());
  }
  
  @Test
  public void testGetShiftByInvalidShiftID() {
    final int shiftAssignerID = 1;
    final int shiftAssigneeID = 2;
    final Owner mrKrabs = new Owner(); 
    final Employee spongeBob = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    spongeBob.setStaffMemberID(shiftAssigneeID);

    final int shiftID = 1;
    final String date = "2022-11-11";
    final String startHour = "08:00:00";
    final String endHour = "12:00:00";
    final Shift shift = new Shift();
    shift.setShiftID(shiftID);
    shift.setDate(Date.valueOf(date));
    shift.setStartHour(Time.valueOf(startHour));
    shift.setEndHour(Time.valueOf(endHour));
    shift.setShiftAssigner(mrKrabs);
    shift.setShiftAssignee(spongeBob);

    
    final int invalidShiftID = 99;
    
    when(shiftRepository.findShiftByShiftID(invalidShiftID)).thenAnswer((InvocationOnMock invocation) -> null);

    MMSException exception = assertThrows(MMSException.class, () -> shiftService.getShiftByShiftID(invalidShiftID));

    assertEquals("Shift with id: 99 not found.", exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
  }
  
  @Test
  public void testGetShiftsByEmployeeID() {
    final int shiftAssignerID = 1;
    final int shiftAssigneeID = 2;
    final Owner mrKrabs = new Owner(); 
    final Employee spongeBob = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    spongeBob.setStaffMemberID(shiftAssigneeID);

    //first shift
    final int shiftID1 = 1;
    final String date1 = "2022-10-10";
    final String startHour1 = "08:00:00";
    final String endHour1 = "12:00:00";
    final Shift shift1 = new Shift();
    shift1.setShiftID(shiftID1);
    shift1.setDate(Date.valueOf(date1));
    shift1.setStartHour(Time.valueOf(startHour1));
    shift1.setEndHour(Time.valueOf(endHour1));
    shift1.setShiftAssigner(mrKrabs);
    shift1.setShiftAssignee(spongeBob);
    
    //second shift
    final int shiftID2 = 2;
    final String date2 = "2022-10-10";
    final String startHour2 = "15:00:00";
    final String endHour2 = "17:00:00";
    final Shift shift2 = new Shift();
    shift2.setShiftID(shiftID2);
    shift2.setDate(Date.valueOf(date2));
    shift2.setStartHour(Time.valueOf(startHour2));
    shift2.setEndHour(Time.valueOf(endHour2));
    shift2.setShiftAssigner(mrKrabs);
    shift2.setShiftAssignee(spongeBob);

    //list of shifts
    final ArrayList<Shift> shifts = new ArrayList<Shift>();
    shifts.add(shift1);
    shifts.add(shift2);
    
    //list of shifts dto to be compared
    List<ShiftResponseDto> shiftsDto = new ArrayList<ShiftResponseDto>();
    for (Shift shift : shifts) {
      shiftsDto.add(new ShiftResponseDto(shift));
    }

    when(shiftRepository.findShiftByShiftAssigneeStaffMemberID(shiftAssigneeID)).thenAnswer((InvocationOnMock invocation) -> shifts);
    when(staffMemberRepository.findStaffMemberByStaffMemberID(shiftAssigneeID)).thenAnswer((InvocationOnMock invocation) -> spongeBob);


    List<ShiftResponseDto> returnedShiftsDto = shiftService.getShiftsByEmployeeID(shiftAssigneeID);
    
    assertNotNull(returnedShiftsDto);
    assertThat(shiftsDto).usingRecursiveComparison().isEqualTo(returnedShiftsDto);
  }
  
  @Test
  public void testGetShiftsByInvalidEmployeeID() {   //Employee id corresponds to the owner (not an employee)
    final int invalidShiftAssigneeID = 1; //owner id
    final Owner mrKrabs = new Owner(); 
    mrKrabs.setStaffMemberID(1);

    when(staffMemberRepository.findStaffMemberByStaffMemberID(invalidShiftAssigneeID)).thenAnswer((InvocationOnMock invocation) -> mrKrabs);
    
    MMSException exception = assertThrows(MMSException.class, () -> shiftService.getShiftsByEmployeeID(invalidShiftAssigneeID));

    assertEquals("No shift for the owner.", exception.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
  }
  
  @Test
  public void testGetShiftsByEmployeeIDMissingEmployee() {   //Employee with employee id cannot be found 
    
    final int emptyShiftAssigneeID = 99; 
    
    when(staffMemberRepository.findStaffMemberByStaffMemberID(emptyShiftAssigneeID)).thenAnswer((InvocationOnMock invocation) -> null);

    MMSException exception = assertThrows(MMSException.class, () -> shiftService.getShiftsByEmployeeID(emptyShiftAssigneeID));

    assertEquals("No staff member with the id 99 was found.", exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
  }
  
  @Test
  public void testGetShiftsByEmployeeIDMissingShift() {   //Employee with employee id does not have any shift
    final int shiftAssigneeID = 2;
    final Employee patrick = new Employee();
    patrick.setStaffMemberID(shiftAssigneeID);
    
    when(staffMemberRepository.findStaffMemberByStaffMemberID(shiftAssigneeID)).thenAnswer((InvocationOnMock invocation) -> patrick);

    MMSException exception = assertThrows(MMSException.class, () -> shiftService.getShiftsByEmployeeID(shiftAssigneeID));

    assertEquals("No shift assigned to employee with id: 2.", exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
  }
  
  @Test
  public void testGetShiftsByDate() {
    final int shiftAssignerID = 1;
    final int shiftAssigneeID1 = 2;
    final int shiftAssigneeID2 = 3;
    final Owner mrKrabs = new Owner(); 
    final Employee spongeBob = new Employee();
    final Employee squidward = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    spongeBob.setStaffMemberID(shiftAssigneeID1);
    squidward.setStaffMemberID(shiftAssigneeID2);

    final String date = "2022-10-10";
    
    //first shift
    final int shiftID1 = 1;
    final String startHour1 = "08:00:00";
    final String endHour1 = "12:00:00";
    final Shift shift1 = new Shift();
    shift1.setShiftID(shiftID1);
    shift1.setDate(Date.valueOf(date));
    shift1.setStartHour(Time.valueOf(startHour1));
    shift1.setEndHour(Time.valueOf(endHour1));
    shift1.setShiftAssigner(mrKrabs);
    shift1.setShiftAssignee(spongeBob);
    
    //second shift
    final int shiftID2 = 2;
    final String startHour2 = "15:00:00";
    final String endHour2 = "17:00:00";
    final Shift shift2 = new Shift();
    shift2.setShiftID(shiftID2);
    shift2.setDate(Date.valueOf(date));
    shift2.setStartHour(Time.valueOf(startHour2));
    shift2.setEndHour(Time.valueOf(endHour2));
    shift2.setShiftAssigner(mrKrabs);
    shift2.setShiftAssignee(squidward);

    //list of shifts
    final ArrayList<Shift> shifts = new ArrayList<Shift>();
    shifts.add(shift1);
    shifts.add(shift2);
    
    //list of shifts dto to be compared
    List<ShiftResponseDto> shiftsDto = new ArrayList<ShiftResponseDto>();
    for (Shift shift : shifts) {
      shiftsDto.add(new ShiftResponseDto(shift));
    }

    when(shiftRepository.findShiftByDate(date)).thenAnswer((InvocationOnMock invocation) -> shifts);
    
    List<ShiftResponseDto> returnedShiftsDto = shiftService.getShiftsByDate(date);
    
    assertNotNull(returnedShiftsDto);
    assertThat(shiftsDto).usingRecursiveComparison().isEqualTo(returnedShiftsDto);
  }
  
  @Test
  public void testGetShiftsByDateMissingShift() {   //No shift on the given date
    final String emptyDate = "2022-01-01";
    
    when(shiftRepository.findShiftByDate(emptyDate)).thenAnswer((InvocationOnMock invocation) -> null);
    
    MMSException exception = assertThrows(MMSException.class, () -> shiftService.getShiftsByDate(emptyDate));

    assertEquals("No shift on date: 2022-01-01.", exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
  }
  
  @Test
  public void testGetAllShifts() {   
    final int shiftAssignerID = 1;
    final int shiftAssigneeID1 = 2;
    final int shiftAssigneeID2 = 3;
    final Owner mrKrabs = new Owner(); 
    final Employee spongeBob = new Employee();
    final Employee squidward = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    spongeBob.setStaffMemberID(shiftAssigneeID1);
    squidward.setStaffMemberID(shiftAssigneeID2);

    //first shift
    final int shiftID1 = 1;
    final String date1 = "2022-10-10";
    final String startHour1 = "08:00:00";
    final String endHour1 = "12:00:00";
    final Shift shift1 = new Shift();
    shift1.setShiftID(shiftID1);
    shift1.setDate(Date.valueOf(date1));
    shift1.setStartHour(Time.valueOf(startHour1));
    shift1.setEndHour(Time.valueOf(endHour1));
    shift1.setShiftAssigner(mrKrabs);
    shift1.setShiftAssignee(spongeBob);
    
    //second shift
    final int shiftID2 = 2;
    final String date2 = "2022-10-10";
    final String startHour2 = "15:00:00";
    final String endHour2 = "17:00:00";
    final Shift shift2 = new Shift();
    shift2.setShiftID(shiftID2);
    shift2.setDate(Date.valueOf(date2));
    shift2.setStartHour(Time.valueOf(startHour2));
    shift2.setEndHour(Time.valueOf(endHour2));
    shift2.setShiftAssigner(mrKrabs);
    shift2.setShiftAssignee(squidward);

    //list of shifts
    final ArrayList<Shift> shifts = new ArrayList<Shift>();
    shifts.add(shift1);
    shifts.add(shift2);
    
    //list of shifts dto to be compared
    List<ShiftResponseDto> shiftsDto = new ArrayList<ShiftResponseDto>();
    for (Shift shift : shifts) {
      shiftsDto.add(new ShiftResponseDto(shift));
    }

    when(shiftRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> shifts);

    List<ShiftResponseDto> returnedShiftsDto = shiftService.getAllShifts();
    
    assertNotNull(returnedShiftsDto);
    assertThat(shiftsDto).usingRecursiveComparison().isEqualTo(returnedShiftsDto);
  }
  
  @Test
  public void testGetAllShiftsMissingShift() {  //no shift in repository 
    when(shiftRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> null);

    MMSException exception = assertThrows(MMSException.class, () -> shiftService.getAllShifts());

    assertEquals("No shift to display.", exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
  }
  
  @Test
  public void testReassignShift() {
    final int shiftAssignerID = 1;
    final int oldShiftAssigneeID = 2;
    final int newShiftAssigneeID = 3;
    final Owner mrKrabs = new Owner();
    final Employee oldShiftAssignee = new Employee();
    final Employee newShiftAssignee = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    oldShiftAssignee.setStaffMemberID(oldShiftAssigneeID);
    newShiftAssignee.setStaffMemberID(newShiftAssigneeID);
    
    final int shiftID = 1;
    final String date = "2022-11-11";
    final String startHour = "09:00:00";
    final String endHour = "12:00:00";
    final Shift shift = new Shift();
    shift.setShiftID(shiftID);
    shift.setDate(Date.valueOf(date));
    shift.setStartHour(Time.valueOf(startHour));
    shift.setEndHour(Time.valueOf(endHour));
    shift.setShiftAssigner(mrKrabs);
    shift.setShiftAssignee(oldShiftAssignee);
    
    when(shiftRepository.findShiftByShiftID(shiftID)).thenAnswer((InvocationOnMock invocation) -> shift);
    when(staffMemberRepository.findStaffMemberByStaffMemberID(newShiftAssigneeID)).thenAnswer((InvocationOnMock invocation) -> newShiftAssignee);
    
    ShiftResponseDto returnedShiftDto = shiftService.reassignShift(shiftID, newShiftAssigneeID);
    
    assertNotNull(returnedShiftDto);
    assertEquals(date, returnedShiftDto.getDate());
    assertEquals(startHour, returnedShiftDto.getStartHour());
    assertEquals(endHour, returnedShiftDto.getEndHour());
    assertEquals(shiftAssignerID, returnedShiftDto.getShiftAssigner().getStaffMemberID());
    assertEquals(newShiftAssigneeID, returnedShiftDto.getShiftAssignee().getStaffMemberID());
      
  }
  
  @Test
  public void testReassignShiftInvalidShiftID() {   //Shift with shiftID cannot be found
    final int newShiftAssigneeID = 2;
    final Employee newShiftAssignee = new Employee();
    newShiftAssignee.setStaffMemberID(newShiftAssigneeID);
    
    final int invalidShiftID = 99;

    when(shiftRepository.findShiftByShiftID(invalidShiftID)).thenAnswer((InvocationOnMock invocation) -> null);
    when(staffMemberRepository.findStaffMemberByStaffMemberID(newShiftAssigneeID)).thenAnswer((InvocationOnMock invocation) -> newShiftAssignee);
    
    MMSException exception = assertThrows(MMSException.class, () -> shiftService.reassignShift(invalidShiftID, newShiftAssigneeID));

    assertEquals("Shift with id: 99 not found.", exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
      
  }
  
  @Test
  public void testReassignShiftInvalidEmployeeID() {   //Employee Id corresponds to the owner, not an employee
    final int shiftAssignerID = 1;
    final int oldShiftAssigneeID = 2;
    final Owner mrKrabs = new Owner();
    final Employee oldShiftAssignee = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    oldShiftAssignee.setStaffMemberID(oldShiftAssigneeID);
    
    final int shiftID = 1;
    final String date = "2022-01-01";
    final String startHour = "09:00:00";
    final String endHour = "12:00:00";
    final Shift shift = new Shift();
    shift.setShiftID(shiftID);
    shift.setDate(Date.valueOf(date));
    shift.setStartHour(Time.valueOf(startHour));
    shift.setEndHour(Time.valueOf(endHour));
    shift.setShiftAssigner(mrKrabs);
    shift.setShiftAssignee(oldShiftAssignee);
    
    final int invalidNewAssigneeID = 1;    //id of the owner

    when(staffMemberRepository.findStaffMemberByStaffMemberID(invalidNewAssigneeID)).thenAnswer((InvocationOnMock invocation) -> mrKrabs);
    
    MMSException exception = assertThrows(MMSException.class, () -> shiftService.reassignShift(shiftID, invalidNewAssigneeID));

    assertEquals("Cannot assign shift to the owner.", exception.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
  }
  
  @Test
  public void testReassignShiftMissingEmployee() {   //Employee with employeeID cannot be found
    final int shiftAssignerID = 1;
    final int oldShiftAssigneeID = 2;
    final Owner mrKrabs = new Owner();
    final Employee oldShiftAssignee = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    oldShiftAssignee.setStaffMemberID(oldShiftAssigneeID);
    
    final int shiftID = 1;
    final String date = "2022-01-01";
    final String startHour = "09:00:00";
    final String endHour = "12:00:00";
    final Shift shift = new Shift();
    shift.setShiftID(shiftID);
    shift.setDate(Date.valueOf(date));
    shift.setStartHour(Time.valueOf(startHour));
    shift.setEndHour(Time.valueOf(endHour));
    shift.setShiftAssigner(mrKrabs);
    shift.setShiftAssignee(oldShiftAssignee);
    
    final int emptyNewAssigneeID = 99;
    
    when(staffMemberRepository.findStaffMemberByStaffMemberID(emptyNewAssigneeID)).thenAnswer((InvocationOnMock invocation) -> null);
    
    MMSException exception = assertThrows(MMSException.class, () -> shiftService.reassignShift(shiftID, emptyNewAssigneeID));

    assertEquals("No staff member with the id 99 was found.", exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
  }
  
  @Test
  public void testModifyShift() {
    final int shiftAssignerID = 1;
    final int shiftAssigneeID = 2;
    final Owner mrKrabs = new Owner();
    final Employee spongeBob = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    spongeBob.setStaffMemberID(shiftAssigneeID);
    
    final int shiftID = 1;
    final String oldDate = "2022-10-10";
    final String oldStartHour = "08:00:00";
    final String oldEndHour = "09:00:00";
    final Shift shift = new Shift();
    shift.setShiftID(shiftID);
    shift.setDate(Date.valueOf(oldDate));
    shift.setStartHour(Time.valueOf(oldStartHour));
    shift.setEndHour(Time.valueOf(oldEndHour));
    shift.setShiftAssigner(mrKrabs);
    shift.setShiftAssignee(spongeBob);
    
    final String newDate = "2022-10-10";
    final String newStartHour = "14:00:00";
    final String newEndHour = "17:00:00";
    
    when(shiftRepository.findShiftByShiftID(shiftID)).thenAnswer((InvocationOnMock invocation) -> shift);
    
    ShiftResponseDto returnedShiftDto = shiftService.modifyShift(shiftID, newDate, newStartHour, newEndHour);
    
    assertNotNull(returnedShiftDto);
    assertEquals(newDate, returnedShiftDto.getDate());
    assertEquals(newStartHour, returnedShiftDto.getStartHour());
    assertEquals(newEndHour, returnedShiftDto.getEndHour());
  }
  
  @Test
  public void testModifyShiftInvalidShiftID() { //Shift with shiftID cannot be found
    final String newDate = "2022-10-10";
    final String newStartHour = "14:00:00";
    final String newEndHour = "17:00:00";
    
    final int invalidShiftID = 99;
    
    when(shiftRepository.findShiftByShiftID(invalidShiftID)).thenAnswer((InvocationOnMock invocation) -> null);
    
    MMSException exception = assertThrows(MMSException.class, () -> shiftService.modifyShift(invalidShiftID, newDate, newStartHour, newEndHour));

    assertEquals("Shift with id: 99 not found.", exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
  }
  
  @Test
  public void testModifyShiftInvalidHour() {   //EndHour is before StartHour 
    final int shiftAssignerID = 1;
    final int shiftAssigneeID = 2;
    final Owner mrKrabs = new Owner();
    final Employee spongeBob = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    spongeBob.setStaffMemberID(shiftAssigneeID);
    
    final int shiftID = 1;
    final String oldDate = "2022-10-10";
    final String oldStartHour = "08:00:00";
    final String oldEndHour = "09:00:00";
    final Shift shift = new Shift();
    shift.setShiftID(shiftID);
    shift.setDate(Date.valueOf(oldDate));
    shift.setStartHour(Time.valueOf(oldStartHour));
    shift.setEndHour(Time.valueOf(oldEndHour));
    shift.setShiftAssigner(mrKrabs);
    shift.setShiftAssignee(spongeBob);
    
    final String newDate = "2022-10-10";
    final String invalidNewStartHour = "14:00:02";
    final String invalidNewEndHour = "14:00:01";
    
    when(shiftRepository.findShiftByShiftID(shiftID)).thenAnswer((InvocationOnMock invocation) -> shift);
    
    MMSException exception = assertThrows(MMSException.class, () -> shiftService.modifyShift(shiftID, newDate, invalidNewStartHour, invalidNewEndHour));

    assertEquals("Start time: 14:00:02 is not before End time: 14:00:01.", exception.getMessage());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    
  }
  
  @Test
  public void testDeleteShift() {
    final int shiftAssignerID = 1;
    final int shiftAssigneeID = 2;
    final Owner mrKrabs = new Owner();
    final Employee spongeBob = new Employee();
    mrKrabs.setStaffMemberID(shiftAssignerID);
    spongeBob.setStaffMemberID(shiftAssigneeID);
    
    final int shiftID = 1;
    final String date = "2022-10-10";
    final String startHour = "08:00:00";
    final String endHour = "12:00:00";
    final Shift shift = new Shift();
    shift.setShiftID(shiftID);
    shift.setDate(Date.valueOf(date));
    shift.setStartHour(Time.valueOf(startHour));
    shift.setEndHour(Time.valueOf(endHour));
    shift.setShiftAssigner(mrKrabs);
    shift.setShiftAssignee(spongeBob);
    
    
    when(shiftRepository.findShiftByShiftID(shiftID)).thenAnswer((InvocationOnMock invocation) -> shift);
    
    ShiftResponseDto returnedShiftDto = shiftService.deleteShift(shiftID);
    
    assertNotNull(returnedShiftDto);
    
    verify(shiftRepository, times(1)).deleteShiftByShiftID(shiftID);
  }
  
  @Test
  public void testDeleteShiftInvalidShiftID() {   // shift with shiftId not found
    
    final int invalidShiftID = 99;
    
    
    when(shiftRepository.findShiftByShiftID(invalidShiftID)).thenAnswer((InvocationOnMock invocation) -> null);
    
    MMSException exception = assertThrows(MMSException.class, () -> shiftService.deleteShift(invalidShiftID));

    assertEquals("Shift with id: 99 not found.", exception.getMessage());
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
  }

}
