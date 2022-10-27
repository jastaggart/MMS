package ca.mcgill.ecse321.mms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Time;
import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse321.mms.model.Shift;
import ca.mcgill.ecse321.mms.model.Owner;
import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.model.Employee;
import ca.mcgill.ecse321.mms.model.DisplayRoom;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShiftRepositoryTests {
  
  @Autowired
  private MMSRepository mmsRepository;
  
  @Autowired
  private StaffMemberRepository staffMemberRepository;

  @Autowired
  private ShiftRepository shiftRepository;

  @AfterEach
  public void clearDatabase() {
    shiftRepository.deleteAll();
    staffMemberRepository.deleteAll();
    mmsRepository.deleteAll();
  }

  @Test
  public void testPersistAndLoadShift() {
    // Creating the fields that will be set to the MMS instance
    Time openingTime = Time.valueOf("08:00:00");
    Time closingTime = Time.valueOf("17:00:00");
    int museumPassFee = 25;

    // Creating and setting fields to the MMS instance 
    MMS museum = new MMS();

    museum.setOpeningHours(openingTime);
    museum.setClosingHours(closingTime);
    museum.setPassFee(museumPassFee);

    // Saving the MMS instance in the mmsRepository table
    museum =  mmsRepository.save(museum);
    
    
    // Creating the fields that will be set to the owner and employee objects
    String ownerUsername = "Marwan";
    String ownerPassword = "password";
    String ownerEmail = "marwan@mail.com";
    
    String employeeUsername = "Bob";
    String employeePassword = "123";
    String employeeEmail = "bob@mail.com";
    
    // Creating and setting fields to the owner and employee objects
    Owner owner = new Owner();
    Employee employee = new Employee();
    
    owner.setUsername(ownerUsername);
    owner.setPassword(ownerPassword);
    owner.setEmail(ownerEmail);
    owner.setMuseumManagementSystem(museum);
    
    employee.setUsername(employeeUsername);
    employee.setPassword(employeePassword);
    employee.setEmail(employeeEmail);
    employee.setMuseumManagementSystem(museum);

    // Saving the owner and employee objects in the staffMemberRepository table, fetch Id 
    owner =  staffMemberRepository.save(owner);
    employee =  staffMemberRepository.save(employee);
    int ownerId = owner.getStaffMemberID();
    int employeeId = employee.getStaffMemberID();
    
    
    // Creating the fields that will be set to the created shift object
    Date date = new Date(2022, 1, 1);
    Time startHour = Time.valueOf("08:00:00");
    Time endHour = Time.valueOf("17:00:00");
    
    //Creating and setting fields to the shift object
    Shift shift = new Shift();
    shift.setDate(date);
    shift.setStartHour(startHour);
    shift.setEndHour(endHour);
    shift.setShiftAssigner(owner);
    shift.setShiftAssignee(employee);
    
    // Saving the shift object in the shiftRepository table, fetch Id 
    shift =  shiftRepository.save(shift);
    int shiftId = shift.getShiftID();
    
    shift = null;
    
    // Retrieving the shift object from the shiftRepository table using the method defined in the ShiftRepository interface
    shift = (Shift) shiftRepository.findShiftByShiftID(shiftId);
    
    // Assertion tests
    assertNotNull(shift);
    assertEquals(shiftId, shift.getShiftID());
    assertEquals(date, shift.getDate());
    assertEquals(startHour, shift.getStartHour());
    assertEquals(endHour, shift.getEndHour());
    
    assertNotNull(shift.getShiftAssigner());
    assertEquals(ownerId, shift.getShiftAssigner().getStaffMemberID());
    
    assertNotNull(shift.getShiftAssignee());
    assertEquals(employeeId, shift.getShiftAssignee().getStaffMemberID());
  }
  
}