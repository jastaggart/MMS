package ca.mcgill.ecse321.mms.dto;

import javax.validation.constraints.Min;
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

  @Min(1)
  private int shiftAssignerID; 

  @Min(1)
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
    Shift shift = new Shift();
    shift.setDate(Date.valueOf(this.date));
    shift.setStartHour(Time.valueOf(this.startHour));
    shift.setEndHour(Time.valueOf(this.endHour));

    //assigning owner and employee to shift in service (cannot be done here)
    return shift;
  }
}
