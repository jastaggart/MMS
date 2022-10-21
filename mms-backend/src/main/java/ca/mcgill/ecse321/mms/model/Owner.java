/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 50 "model.ump"
// line 146 "model.ump"
public class Owner extends StaffMember
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private List<Shift> shifts;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aUsername, String aPassword, String aEmail, int aStaffMemberID, MMS aMuseumManagementSystem)
  {
    super(aUsername, aPassword, aEmail, aStaffMemberID, aMuseumManagementSystem);
    shifts = new ArrayList<Shift>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Shift getShift(int index)
  {
    Shift aShift = shifts.get(index);
    return aShift;
  }

  public List<Shift> getShifts()
  {
    List<Shift> newShifts = Collections.unmodifiableList(shifts);
    return newShifts;
  }

  public int numberOfShifts()
  {
    int number = shifts.size();
    return number;
  }

  public boolean hasShifts()
  {
    boolean has = shifts.size() > 0;
    return has;
  }

  public int indexOfShift(Shift aShift)
  {
    int index = shifts.indexOf(aShift);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfShifts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Shift addShift(Date aDate, String aStartHour, String aEndHour, int aShiftID, Employee aShiftAssignee)
  {
    return new Shift(aDate, aStartHour, aEndHour, aShiftID, this, aShiftAssignee);
  }

  public boolean addShift(Shift aShift)
  {
    boolean wasAdded = false;
    if (shifts.contains(aShift)) { return false; }
    Owner existingShiftAssigner = aShift.getShiftAssigner();
    boolean isNewShiftAssigner = existingShiftAssigner != null && !this.equals(existingShiftAssigner);
    if (isNewShiftAssigner)
    {
      aShift.setShiftAssigner(this);
    }
    else
    {
      shifts.add(aShift);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeShift(Shift aShift)
  {
    boolean wasRemoved = false;
    //Unable to remove aShift, as it must always have a shiftAssigner
    if (!this.equals(aShift.getShiftAssigner()))
    {
      shifts.remove(aShift);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addShiftAt(Shift aShift, int index)
  {  
    boolean wasAdded = false;
    if(addShift(aShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShifts()) { index = numberOfShifts() - 1; }
      shifts.remove(aShift);
      shifts.add(index, aShift);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveShiftAt(Shift aShift, int index)
  {
    boolean wasAdded = false;
    if(shifts.contains(aShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShifts()) { index = numberOfShifts() - 1; }
      shifts.remove(aShift);
      shifts.add(index, aShift);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addShiftAt(aShift, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=shifts.size(); i > 0; i--)
    {
      Shift aShift = shifts.get(i - 1);
      aShift.delete();
    }
    super.delete();
  }

}