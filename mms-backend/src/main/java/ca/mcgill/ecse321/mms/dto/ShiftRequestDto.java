package ca.mcgill.ecse321.mms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Date;
import java.sql.Time;
import ca.mcgill.ecse321.mms.model.Employee;
import ca.mcgill.ecse321.mms.model.Owner;
import ca.mcgill.ecse321.mms.model.Shift;
import ca.mcgill.ecse321.mms.repository.StaffMemberRepository;

public class ShiftRequestDto {

  @NotNull
  private String date;

  @NotNull
  private String startHour;

  @NotNull
  private String endHour;

  @NotNull
  @NotBlank
  private int shiftAssignerID; 

  @NotNull
  @NotBlank
  private int shiftAssigneeID;

  public String getDate() {
    return this.date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setStartHour(String startHour) {
    this.startHour = startHour;
  }

  public String getStartHour() {
    return this.startHour;
  }

  public void setEndHour(String endHour) {
    this.endHour = endHour;
  }

  public String getEndHour() {
    return this.endHour;
  }

  public int getShiftAssignerID() {
    return this.shiftAssignerID;
  }
  
  public void setShiftAssigner(int shiftAssignerID) {
    this.shiftAssignerID = shiftAssignerID;
  }

  public int getShiftAssigneeID() {
    return this.shiftAssigneeID;
  }

  public void setShiftAssignee(int shiftAssigneeID) {
    this.shiftAssigneeID = shiftAssigneeID;
  }

  public Shift toModel() {
//    @Autowired
//    StaffMemberRepository staffMemberRepository;
    
    Shift shift = new Shift();
    shift.setDate(Date.valueOf(this.date));
    shift.setStartHour(Time.valueOf(this.startHour));
    shift.setEndHour(Time.valueOf(this.endHour));
//    shift.setShiftAssigner((Owner) staffMemberRepository.findStaffMemberByStaffMemberID(this.shiftAssignerID));
//    shift.setShiftAssignee((Employee) staffMemberRepository.findStaffMemberByStaffMemberID(this.shiftAssigneeID));

    //assigning owner and employee to shift in service (cant be done here)
    return shift;
  }
}
