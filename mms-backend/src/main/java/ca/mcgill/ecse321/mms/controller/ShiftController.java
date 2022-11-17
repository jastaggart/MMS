package ca.mcgill.ecse321.mms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.mms.dto.ShiftRequestDto;
import ca.mcgill.ecse321.mms.dto.ShiftResponseDto;
import ca.mcgill.ecse321.mms.model.Shift;
import ca.mcgill.ecse321.mms.service.ShiftService;

@RestController
public class ShiftController {
  @Autowired
  ShiftService shiftService;

  /**
   * Get all shifts after HTTP request and puts into a list of HTTP response
   * 
   * @return - The fetched shifts as Dto
   */
  @GetMapping(value={"/shifts","/shifts/"})
  public ResponseEntity<List<ShiftResponseDto>> getAllShifts() {
    List<ShiftResponseDto> shiftsDto = shiftService.getAllShifts();
    return new ResponseEntity<List<ShiftResponseDto>>(shiftsDto, HttpStatus.OK);
  }

  
  /**
   * Get shift by shiftID after HTTP request and puts into the HTTP response
   * 
   * @param shiftID - id of the shift to get
   * @return - The fetched shift as Dto
   */
  @GetMapping(value={"/shift/shiftID/{shiftID}", "/shift/shiftID/{shiftID}/"}) 
  public ResponseEntity<ShiftResponseDto> getShiftByShiftID(@PathVariable int shiftID) {
      ShiftResponseDto shiftDto = shiftService.getShiftByShiftID(shiftID);
      return new ResponseEntity<ShiftResponseDto>(shiftDto, HttpStatus.OK);
  }
  
  
  /**
   * Get all shifts of an employee by employeeID after HTTP request and puts into a list of HTTP response
   * 
   * @param employeeID - id of the employee to get shifts from
   * @return - The fetched shifts as Dto
   */
  @GetMapping(value={"/shift/employee/{employeeID}","/shift/employee/{employeeID}/"}) 
  public ResponseEntity<List<ShiftResponseDto>> getShiftsByEmployeeID(@PathVariable int employeeID) {
    List<ShiftResponseDto> shiftsDto = shiftService.getShiftsByEmployeeID(employeeID);
    return new ResponseEntity<List<ShiftResponseDto>>(shiftsDto, HttpStatus.OK);
  }
  
  
  /**
   * Get all shifts on a date after HTTP request and puts into a list of HTTP response
   * 
   * @param date - date to get shifts from
   * @return - The fetched shifts as Dto
   */
  @GetMapping(value={"/shift/date/{date}","/shift/employee/{date}/"}) 
  public ResponseEntity<List<ShiftResponseDto>> getShiftsByDate(@PathVariable String date) {
    List<ShiftResponseDto> shiftsDto = shiftService.getShiftsByDate(date);
    return new ResponseEntity<List<ShiftResponseDto>>(shiftsDto, HttpStatus.OK);
  }

  
  /**
   * Reassign a shift to another employee after HTTP request
   * 
   * @param shiftID - id of the shift to reassign
   * @param employeeID - id of the employee to reassign the shift to
   * @return - The modified shift as Dto
   */
  @PutMapping(value={"/shift/reassign/{shiftID}","/shift/reassign/{shiftID}/"})
  public ResponseEntity<ShiftResponseDto> reassignShift(@PathVariable int shiftID, int employeeID) {
    ShiftResponseDto shiftDto = shiftService.reassignShift(shiftID, employeeID);
      return new ResponseEntity<ShiftResponseDto>(shiftDto, HttpStatus.OK);
  }

  
  /**
   * Modify a shift after HTTP request
   * 
   * @param date - the new date value
   * @param startHour - the new start hour value
   * @param endHour - the new end hour value
   * @return - The modified shift as Dto
   */
  @PutMapping(value={"/shift/modify/{shiftID}","/shift/modify/{shiftID}/"})
  public ResponseEntity<ShiftResponseDto> modifyShift(@PathVariable int shiftID, String date, String startHour, String endHour) {
    ShiftResponseDto shiftDto = shiftService.modifyShift(shiftID, date, startHour, endHour);
    return new ResponseEntity<ShiftResponseDto>(shiftDto, HttpStatus.OK);
  }

  
  /**
   * Creates a shift using Dto data after HTTP request and puts it into HTTP response
   * 
   * @param shiftRequest - The ShiftRequestDto data
   * @return - The created shift as Dto
   */
  @PostMapping(value={"/shift","/shift/"})
  public ResponseEntity<ShiftResponseDto> createShift(@Valid @RequestBody ShiftRequestDto shiftRequest) {
    Shift shift = shiftRequest.toModel();
    ShiftResponseDto shiftDto = shiftService.createShift(shift, shiftRequest.getShiftAssignerID(), shiftRequest.getShiftAssigneeID());
    return new ResponseEntity<ShiftResponseDto>(shiftDto, HttpStatus.CREATED);
  }
  

  /**
   * Deletes a shift after HTTP request and puts it into HTTP response
   * 
   * @param shiftID - id of the shift to be deleted
   * @return - deleted shift as dto
   */
  @DeleteMapping({"/shift/delete/{shiftID}","/shift/delete/{shiftID}/"})
  public ResponseEntity<ShiftResponseDto> deleteShift(@PathVariable int shiftID) {
    ShiftResponseDto shiftDto = shiftService.deleteShift(shiftID);
    return new ResponseEntity<ShiftResponseDto>(shiftDto, HttpStatus.OK);
  }

}
