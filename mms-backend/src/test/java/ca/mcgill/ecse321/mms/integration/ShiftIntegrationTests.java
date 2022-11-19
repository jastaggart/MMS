package ca.mcgill.ecse321.mms.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import ca.mcgill.ecse321.mms.model.Employee;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.ShiftRepository;
import ca.mcgill.ecse321.mms.repository.StaffMemberRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ShiftIntegrationTests {

  @Autowired
  private TestRestTemplate client;

  @Autowired
  private ShiftRepository shiftRepository;

  @Autowired
  private StaffMemberRepository staffMemberRepository;
  
  @Autowired
  private MMSRepository mmsRepository;

  @BeforeEach
  @AfterEach
  public void clearDatabase() {
    shiftRepository.deleteAll();
  }

  @Test
  public void testCreateAndGetShift() {
    ShiftDto response = testCreateShift();
    testGetShiftByShiftID(response.getShiftID());
    testGetShiftsByEmployeeID(response.getShiftAssigneeID());
    testGetShiftsByDate(response.getDate());
    testGetAllShifts(response);
  }

  private void testGetShiftByShiftID(int shiftID) {
    ResponseEntity<ShiftDto> response = client.getForEntity("/shift/shiftID/" + shiftID, ShiftDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertTrue(response.getBody().getShiftID() == shiftID, "Response has correct ID");
  }

  @Test
  public void testGetShiftByInvalidShiftID() { //no shift associated with this shiftID
    int invalidShiftID = 99;
    ResponseEntity<String> response = client.getForEntity("/shift/shiftID/" + invalidShiftID, String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Shift with id: " + invalidShiftID + " not found.", response.getBody());
  }



  private void testGetShiftsByEmployeeID(int employeeID) {  
    ResponseEntity<ShiftDto[]> response = client.getForEntity("/shift/employee/" + employeeID, ShiftDto[].class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(1, response.getBody()[0].getShiftAssignerID(), "Response has correct shift assigner id");
    assertEquals(employeeID, response.getBody()[0].getShiftAssigneeID(), "Response has correct shift assignee id");
  }



  @Test
  public void testGetShiftsByEmployeeIDNoEmployee() { //no employee associated with this id
    int invalidEmployeeID = 99;
    ResponseEntity<String> response = client.getForEntity("/shift/employee/" + invalidEmployeeID, String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("No staff member with the id " + invalidEmployeeID + " was found.", response.getBody());
  }



  private void testGetShiftsByDate(String date) {
    ResponseEntity<ShiftDto[]> response = client.getForEntity("/shift/date/" + date, ShiftDto[].class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals(date, response.getBody()[0].getDate(), "Response has correct date");
  }

  @Test
  public void testGetShiftsByDateNoDate() {  //no shift on this date
    String invalidDate = "2022-01-01";
    ResponseEntity<String> response = client.getForEntity("/shift/date/" + invalidDate, String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("No shift on date: " + invalidDate + ".", response.getBody());
  }



  private void testGetAllShifts(ShiftDto storedShift) {
    ResponseEntity<ShiftDto[]> response = client.getForEntity("/shifts", ShiftDto[].class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals("2022-11-11", response.getBody()[0].getDate(), "Response has correct date");
    assertEquals("08:00:00", response.getBody()[0].getStartHour(), "Response has correct start hour");
    assertEquals("17:00:00", response.getBody()[0].getEndHour(), "Response has correct end hour");
    assertEquals(1, response.getBody()[0].getShiftAssignerID(), "Response has correct shift assigner id");
    assertEquals(storedShift.getShiftAssigneeID(), response.getBody()[0].getShiftAssigneeID(), "Response has correct shift assignee id");
    assertTrue(response.getBody()[0].getShiftID() == storedShift.getShiftID(), "Response has correct ID");
  }

  @Test
  public void testGetAllShiftsNoShift() {    //no shift in repo
    ResponseEntity<String> response = client.getForEntity("/shifts", String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("No shift to display.", response.getBody());
  }

  
  private ShiftDto testCreateShift() {
    Employee shiftAssignee = new Employee();
    shiftAssignee.setMuseumManagementSystem(mmsRepository.findMMSByMuseumID(1));
    staffMemberRepository.save(shiftAssignee);
    int shiftAssigneeID = shiftAssignee.getStaffMemberID();
    
    ResponseEntity<ShiftDto> response = client.postForEntity("/shift", new ShiftDto("2022-11-11", "08:00:00", "17:00:00", 1, shiftAssigneeID), ShiftDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals("2022-11-11", response.getBody().getDate(), "Response has correct date");
    assertEquals("08:00:00", response.getBody().getStartHour(), "Response has correct start hour");
    assertEquals("17:00:00", response.getBody().getEndHour(), "Response has correct end hour");
    assertEquals(1, response.getBody().getShiftAssignerID(), "Response has correct shift assigner id");
    assertEquals(shiftAssigneeID, response.getBody().getShiftAssigneeID(), "Response has correct shift assignee id");
    assertTrue(response.getBody().getShiftID() > 0, "Response has valid ID");

    return response.getBody();
  }
  
  
  @Test
  public void testCreateShiftInvalidEmployeeID() {  //attempt to assign the shift to owner (id=1)
    int ownerID = 1;

    ResponseEntity<String> response = client.postForEntity("/shift", new ShiftDto("2022-11-11", "08:00:00", "17:00:00", 1, ownerID), String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Cannot assign shift to the owner.", response.getBody());
  }
  
  
  @Test
  public void testReassignShift() {
    //create old shift assignee and new shift assignee, save them to repo and fetch their id
    Employee oldShiftAssignee = new Employee();
    Employee newShiftAssignee = new Employee();
    oldShiftAssignee.setMuseumManagementSystem(mmsRepository.findMMSByMuseumID(1));
    newShiftAssignee.setMuseumManagementSystem(mmsRepository.findMMSByMuseumID(1));
    staffMemberRepository.save(oldShiftAssignee);
    staffMemberRepository.save(newShiftAssignee);
    
    int oldShiftAssigneeID = oldShiftAssignee.getStaffMemberID();
    int newShiftAssigneeID = newShiftAssignee.getStaffMemberID();
    
    //create a shift assigned to old shift assignee
    ResponseEntity<ShiftDto> shift = client.postForEntity("/shift", new ShiftDto("2022-11-11", "08:00:00", "17:00:00", 1, oldShiftAssigneeID), ShiftDto.class);
    assertNotNull(shift);
    assertEquals(HttpStatus.CREATED, shift.getStatusCode(), "Response has correct status");

    int shiftID = shift.getBody().getShiftID();
    
    //assign to new shift assignee 
    ResponseEntity<ShiftDto> response = client.exchange("/shift/reassign/" + shiftID + "/" + newShiftAssigneeID, HttpMethod.PUT, shift, ShiftDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
    assertEquals("2022-11-11", response.getBody().getDate(), "Response has correct date");
    assertEquals("08:00:00", response.getBody().getStartHour(), "Response has correct start hour");
    assertEquals("17:00:00", response.getBody().getEndHour(), "Response has correct end hour");
    assertEquals(1, response.getBody().getShiftAssignerID(), "Response has correct shift assigner id");
    assertEquals(newShiftAssigneeID, response.getBody().getShiftAssigneeID(), "Response has correct shift assignee id");
    assertTrue(response.getBody().getShiftID() == shiftID, "Response has correct ID");
  }

  
  @Test
  public void testReassignShiftNoNewShiftAssignee() {  //no employee associated to the id of the new shift assignee 
    //create old shift assignee, save to repo and fetch id
    Employee oldShiftAssignee = new Employee();
    oldShiftAssignee.setMuseumManagementSystem(mmsRepository.findMMSByMuseumID(1));
    staffMemberRepository.save(oldShiftAssignee);
    
    int oldShiftAssigneeID = oldShiftAssignee.getStaffMemberID();

    //create a shift assigned to old shift assignee
    ResponseEntity<ShiftDto> shift = client.postForEntity("/shift", new ShiftDto("2022-11-11", "08:00:00", "17:00:00", 1, oldShiftAssigneeID), ShiftDto.class);
    assertNotNull(shift);
    assertEquals(HttpStatus.CREATED, shift.getStatusCode(), "Response has correct status");

    int shiftID = shift.getBody().getShiftID();
    int invalidNewShiftAssigneeID = 99;
    
    
    ResponseEntity<String> response = client.exchange("/shift/reassign/" + shiftID + "/" + invalidNewShiftAssigneeID, HttpMethod.PUT, shift, String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("No staff member with the id " + invalidNewShiftAssigneeID + " was found.", response.getBody());
  }
  
  
  @Test
  public void testDeleteShift() {
    //create shift assignee instance and save to repository, fetch its id
    Employee shiftAssignee = new Employee();
    shiftAssignee.setMuseumManagementSystem(mmsRepository.findMMSByMuseumID(1));
    staffMemberRepository.save(shiftAssignee);
    
    int shiftAssigneeID = shiftAssignee.getStaffMemberID();
    
    //create a shift assigned to shift assignee
    ResponseEntity<ShiftDto> shift = client.postForEntity("/shift", new ShiftDto("2022-11-11", "08:00:00", "17:00:00", 1, shiftAssigneeID), ShiftDto.class);
    assertNotNull(shift);
    assertEquals(HttpStatus.CREATED, shift.getStatusCode(), "Response has correct status");

    int shiftID = shift.getBody().getShiftID();
    
    //delete the shift
    ResponseEntity<ShiftDto> response = client.exchange("/shift/delete/" + shiftID, HttpMethod.DELETE, shift, ShiftDto.class);

    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    assertNotNull(response.getBody(), "Response has body");
  }
  
  @Test
  public void testDeleteShiftInvalidShiftID() { //shift associated with shift id is not found in repository
    int invalidShiftID = 99;
    ResponseEntity<ShiftDto> shift = null;
    ResponseEntity<String> response = client.exchange("/shift/delete/" + invalidShiftID, HttpMethod.DELETE, shift, String.class);

    assertNotNull(response);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Shift with id: " + invalidShiftID + " not found.", response.getBody());
  }


}





class ShiftDto {
  private int shiftID;
  private String date;
  private String startHour;
  private String endHour;
  private int shiftAssignerID;
  private int shiftAssigneeID;

  // Need default constructor so that can instantiate the object
  public ShiftDto() {}

  public ShiftDto(String date, String startHour, String endHour, int shiftAssignerID, int shiftAssigneeID) {
    this.date = date;
    this.startHour = startHour;
    this.endHour = endHour;
    this.shiftAssignerID = shiftAssignerID;// = 1; -----------
    this.shiftAssigneeID = shiftAssigneeID;
  }

  public int getShiftID() {
    return this.shiftID;
  }

  public String getDate() {
    return this.date;
  }

  public String getStartHour() {
    return this.startHour;
  }

  public String getEndHour() {
    return this.endHour;
  }

  public int getShiftAssignerID() {
    return this.shiftAssignerID;
  }

  public int getShiftAssigneeID() {
    return this.shiftAssigneeID;
  }

}
