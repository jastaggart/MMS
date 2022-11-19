package ca.mcgill.ecse321.mms.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.mms.dto.ShiftRequestDto;
import ca.mcgill.ecse321.mms.dto.ShiftResponseDto;
import ca.mcgill.ecse321.mms.dto.StaffMemberResponseDto;
import ca.mcgill.ecse321.mms.exception.MMSException;
import ca.mcgill.ecse321.mms.model.Employee;
import ca.mcgill.ecse321.mms.model.Owner;
import ca.mcgill.ecse321.mms.model.Shift;
import ca.mcgill.ecse321.mms.model.StaffMember;
import ca.mcgill.ecse321.mms.repository.ShiftRepository;
import ca.mcgill.ecse321.mms.repository.StaffMemberRepository;

@Service
public class ShiftService {

  @Autowired
  ShiftRepository shiftRepository;
  
  @Autowired
  StaffMemberService staffMemberService;
  
  @Autowired
  StaffMemberRepository staffMemberRepository;
  
  
  /**
   * 
   * Create shift and saves in shiftRepository
   * 
   * @param shift - shift to be created and stored (contains information on date, start and end hour)
   * @param shiftAssignerID - id of the shift assigner to be linked
   * @param shiftAssigneeID - id of the shift assignee to be linked
   * @return - shift dto containing data of the newly created shift 
   */
@Transactional
public ShiftResponseDto createShift(Shift shift, int shiftAssignerID, int shiftAssigneeID) {
  
  //default values for missing fields 
  if (shift.getDate() == null) shift.setDate(Date.valueOf("2022-01-01"));
  if (shift.getStartHour() == null) shift.setStartHour(Time.valueOf("08:00:00"));
  if (shift.getEndHour() == null) shift.setEndHour(Time.valueOf("17:00:00"));

  //check if startHour is before endHour 
  if (!shift.getStartHour().before(shift.getEndHour())) throw new MMSException(HttpStatus.BAD_REQUEST, "Start time: " + shift.getStartHour() + " is not before End time: " + shift.getEndHour() + ".");
  
  //check if shift assigner is the owner, and shift assignee is an employee
  if (shiftAssignerID != 1) throw new MMSException(HttpStatus.BAD_REQUEST, "Employee not authorized to assign a shift.");
  if (shiftAssigneeID == 1) throw new MMSException(HttpStatus.BAD_REQUEST, "Cannot assign shift to the owner.");
  
  //If the employee cannot be found, staffMemberService will throw exception
  staffMemberService.getStaffMemberById(shiftAssigneeID);

  
  shift.setShiftAssigner((Owner) staffMemberRepository.findStaffMemberByStaffMemberID(shiftAssignerID));
  shift.setShiftAssignee((Employee) staffMemberRepository.findStaffMemberByStaffMemberID(shiftAssigneeID));
  
  shift = shiftRepository.save(shift);
  return new ShiftResponseDto(shift);
}
  
  
  
  /**
   * 
   * Find shift by Id
   * 
   * @param shiftID - id of the shift to be found
   * @return - shift dto containing data of the found shift 
   */
  @Transactional
  public ShiftResponseDto getShiftByShiftID(int shiftID) {
    Shift shift = shiftRepository.findShiftByShiftID(shiftID);
    
    //check if shift id is valid
    if (shift == null) {
        throw new MMSException(HttpStatus.NOT_FOUND, "Shift with id: " + shiftID + " not found.");
    }
    return new ShiftResponseDto(shift);
  }
  
  
  
  /**
   * 
   * Find shifts by employee id
   * 
   * @param employeeID - id of the employee whose shifts are to be found
   * @return - list of shift dto containing data of each found shift 
   */
  @Transactional
  public List<ShiftResponseDto> getShiftsByEmployeeID(int employeeID) {
    
    //Check if employee exists and if the id corresponds to an employee (not the owner)
    staffMemberService.getStaffMemberById(employeeID);
    if (employeeID == 1) {
      throw new MMSException(HttpStatus.BAD_REQUEST, "No shift for the owner.");
    }
    
    //Check if there is at least one shift assigned to the employee
    List<Shift> shifts = shiftRepository.findShiftByShiftAssigneeStaffMemberID(employeeID);
    if (shifts.size() == 0) {
        throw new MMSException(HttpStatus.NOT_FOUND, "No shift assigned to employee with id: " + employeeID + ".");
    }
    
    List<ShiftResponseDto> shiftResponses = new ArrayList<ShiftResponseDto>();
    for (Shift shift : shifts) {
        shiftResponses.add(new ShiftResponseDto(shift));
    }
    
    return shiftResponses;
    
  }
  
  
  
  /**
   * 
   * Find shifts on a given date
   * 
   * @param date - date on which the shifts are to be retrieved
   * @return - list of shift dto containing data of each found shift 
   */
  @Transactional
  public List<ShiftResponseDto> getShiftsByDate(String date) {
    
    //Check if there is at least one shift on this date
    List<Shift> shifts = shiftRepository.findShiftByDate(Date.valueOf(date));
    if (shifts == null || shifts.isEmpty()) {
        throw new MMSException(HttpStatus.NOT_FOUND, "No shift on date: " + date + ".");
    }
    
    List<ShiftResponseDto> shiftResponses = new ArrayList<ShiftResponseDto>();
    for (Shift shift : shifts) {
        shiftResponses.add(new ShiftResponseDto(shift));
    }
    
    return shiftResponses;
    
  }
  
  
  
  /**
   * 
   * Get all shifts from repository
   * 
   * @return - list of shift dto containing data of each found shift 
   */
  @Transactional
  public List<ShiftResponseDto> getAllShifts() {
    
    //Check if there is at least one shift
    List<Shift> shifts = shiftRepository.findAll();
    if (shifts == null || shifts.isEmpty()) {
        throw new MMSException(HttpStatus.NOT_FOUND, "No shift to display.");
    }


    List<ShiftResponseDto> shiftResponses = new ArrayList<ShiftResponseDto>();
    for (Shift shift : shifts) {
        shiftResponses.add(new ShiftResponseDto(shift));
    }
    return shiftResponses;
  }
  
  
  /**
   * 
   * Reassign shift to another employee
   * 
   * @param shiftID - id of the shift to be reassigned
   * @param employeeID - id of the employee that the shift will be assigned to
   * @return - shift dto of the reassigned shift 
   */
  @Transactional
  public ShiftResponseDto reassignShift(int shiftID, int employeeID) {
    
    //Check if employee exists and if the id corresponds to an employee (not the owner)
    staffMemberService.getStaffMemberById(employeeID);
    if (employeeID == 1) {
      throw new MMSException(HttpStatus.BAD_REQUEST, "Cannot assign shift to the owner.");
    }
    //Check if shift exists
    getShiftByShiftID(shiftID);
    
    
    Shift shift = shiftRepository.findShiftByShiftID(shiftID);
    Employee employee = (Employee) staffMemberRepository.findStaffMemberByStaffMemberID(employeeID);
    
    shift.setShiftAssignee(employee);
    return new ShiftResponseDto(shift);
  }
  
  
  /**
   * 
   * Modify shift date, start hour and end hour
   * 
   * @param shiftID - id of the shift to be modified
   * @param date - new value for date
   * @param date - new value for startHour
   * @param date - new value for endHour
   * @return - shift dto of the modified shift 
   */
  @Transactional
  public ShiftResponseDto modifyShift(int shiftID, String date, String startHour, String endHour) {
    Shift shift = shiftRepository.findShiftByShiftID(shiftID);
    
    //Check if shift exists
    getShiftByShiftID(shiftID);
    
    //default value for missing fields (take original values)
    if (date == null) date = shift.getDate().toString();
    if (startHour == null) startHour = shift.getStartHour().toString();
    if (endHour == null) endHour = shift.getEndHour().toString();
    
    //check if starthour is before endHour 
    if (!Time.valueOf(startHour).before(Time.valueOf(endHour))) throw new MMSException(HttpStatus.BAD_REQUEST, "Start time: " + startHour + " is not before End time: " + endHour + ".");
    
    shift.setDate(Date.valueOf(date));
    shift.setStartHour(Time.valueOf(startHour));
    shift.setEndHour(Time.valueOf(endHour));
    
    return new ShiftResponseDto(shift);
  }

  
  /**
   * 
   * Delete shift
   * 
   * @param shiftID - id of the shift to be deleted
   * @return - shift dto of the deleted shift 
   */
  @Transactional
  public ShiftResponseDto deleteShift(int shiftID) {
    Shift shift = shiftRepository.findShiftByShiftID(shiftID);
    
    //Check if shift exists
    getShiftByShiftID(shiftID);
    
    //remove link to shift assigner and assignee
    Owner owner = shift.getShiftAssigner();
    Employee employee = shift.getShiftAssignee();
    owner.removeShift(shift);
    owner.removeShift(shift);
    
    //remove shift from repository
    shiftRepository.deleteShiftByShiftID(shiftID);
    
    return new ShiftResponseDto(shift);
  }

  
}
