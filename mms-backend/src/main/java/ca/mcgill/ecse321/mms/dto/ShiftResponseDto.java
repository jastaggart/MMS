package ca.mcgill.ecse321.mms.dto;

import ca.mcgill.ecse321.mms.model.Shift;

public class ShiftResponseDto {
  private int shiftID;
  private String date;
  private String startHour;
  private String endHour;
  private StaffMemberResponseDto shiftAssigner;
  private StaffMemberResponseDto shiftAssignee;

  public ShiftResponseDto(Shift shift) {
    this.shiftID = shift.getShiftID();
    this.date = shift.getDate().toString();
    this.startHour = shift.getStartHour().toString();
    this.endHour = shift.getEndHour().toString();
    this.shiftAssigner = new StaffMemberResponseDto(shift.getShiftAssigner());
    this.shiftAssignee = new StaffMemberResponseDto(shift.getShiftAssignee());
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

  public StaffMemberResponseDto getShiftAssigner() {
    return this.shiftAssigner;
  }
  
  public StaffMemberResponseDto getShiftAssignee() {
    return this.shiftAssignee;
  }
}
