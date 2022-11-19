package ca.mcgill.ecse321.mms.dto;

import ca.mcgill.ecse321.mms.model.Shift;

public class ShiftResponseDto {
  
  private int shiftID;
  private String date;
  private String startHour;
  private String endHour;
  private int shiftAssignerID;
  private int shiftAssigneeID;

  public ShiftResponseDto(Shift shift) {
    this.shiftID = shift.getShiftID();
    this.date = shift.getDate().toString();
    this.startHour = shift.getStartHour().toString();
    this.endHour = shift.getEndHour().toString();
    this.shiftAssignerID = shift.getShiftAssigner().getStaffMemberID();
    this.shiftAssigneeID = shift.getShiftAssignee().getStaffMemberID();
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
