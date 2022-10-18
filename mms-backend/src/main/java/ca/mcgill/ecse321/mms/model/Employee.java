/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 52 "model.ump"
// line 149 "model.ump"
public class Employee extends StaffMember
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Associations
  private List<Shift> shift;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aUsername, String aPassword, String aEmail, MMS aMuseumManagementSystem, int aStaffMemberID)
  {
    super(aUsername, aPassword, aEmail, aMuseumManagementSystem, aStaffMemberID);
    shift = new ArrayList<Shift>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Shift getShift(int index)
  {
    Shift aShift = shift.get(index);
    return aShift;
  }

  public List<Shift> getShift()
  {
    List<Shift> newShift = Collections.unmodifiableList(shift);
    return newShift;
  }

  public int numberOfShift()
  {
    int number = shift.size();
    return number;
  }

  public boolean hasShift()
  {
    boolean has = shift.size() > 0;
    return has;
  }

  public int indexOfShift(Shift aShift)
  {
    int index = shift.indexOf(aShift);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfShift()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Shift addShift(Date aDate, String aStartHour, String aEndHour, int aShiftID)
  {
    return new Shift(aDate, aStartHour, aEndHour, aShiftID, this);
  }

  public boolean addShift(Shift aShift)
  {
    boolean wasAdded = false;
    if (shift.contains(aShift)) { return false; }
    Employee existingShiftAssignee = aShift.getShiftAssignee();
    boolean isNewShiftAssignee = existingShiftAssignee != null && !this.equals(existingShiftAssignee);
    if (isNewShiftAssignee)
    {
      aShift.setShiftAssignee(this);
    }
    else
    {
      shift.add(aShift);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeShift(Shift aShift)
  {
    boolean wasRemoved = false;
    //Unable to remove aShift, as it must always have a shiftAssignee
    if (!this.equals(aShift.getShiftAssignee()))
    {
      shift.remove(aShift);
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
      if(index > numberOfShift()) { index = numberOfShift() - 1; }
      shift.remove(aShift);
      shift.add(index, aShift);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveShiftAt(Shift aShift, int index)
  {
    boolean wasAdded = false;
    if(shift.contains(aShift))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfShift()) { index = numberOfShift() - 1; }
      shift.remove(aShift);
      shift.add(index, aShift);
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
    for(int i=shift.size(); i > 0; i--)
    {
      Shift aShift = shift.get(i - 1);
      aShift.delete();
    }
    super.delete();
  }

}