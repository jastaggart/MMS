/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Date;

// line 58 "model.ump"
// line 154 "model.ump"
public class Shift
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shift Attributes
  private Date date;
  private String startHour;
  private String endHour;
  private int shiftID;

  //Shift Associations
  private Employee shiftAssignee;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Shift(Date aDate, String aStartHour, String aEndHour, int aShiftID, Employee aShiftAssignee)
  {
    date = aDate;
    startHour = aStartHour;
    endHour = aEndHour;
    shiftID = aShiftID;
    boolean didAddShiftAssignee = setShiftAssignee(aShiftAssignee);
    if (!didAddShiftAssignee)
    {
      throw new RuntimeException("Unable to create shift due to shiftAssignee. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartHour(String aStartHour)
  {
    boolean wasSet = false;
    startHour = aStartHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndHour(String aEndHour)
  {
    boolean wasSet = false;
    endHour = aEndHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setShiftID(int aShiftID)
  {
    boolean wasSet = false;
    shiftID = aShiftID;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public String getStartHour()
  {
    return startHour;
  }

  public String getEndHour()
  {
    return endHour;
  }

  public int getShiftID()
  {
    return shiftID;
  }
  /* Code from template association_GetOne */
  public Employee getShiftAssignee()
  {
    return shiftAssignee;
  }
  /* Code from template association_SetOneToMany */
  public boolean setShiftAssignee(Employee aShiftAssignee)
  {
    boolean wasSet = false;
    if (aShiftAssignee == null)
    {
      return wasSet;
    }

    Employee existingShiftAssignee = shiftAssignee;
    shiftAssignee = aShiftAssignee;
    if (existingShiftAssignee != null && !existingShiftAssignee.equals(aShiftAssignee))
    {
      existingShiftAssignee.removeShift(this);
    }
    shiftAssignee.addShift(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Employee placeholderShiftAssignee = shiftAssignee;
    this.shiftAssignee = null;
    if(placeholderShiftAssignee != null)
    {
      placeholderShiftAssignee.removeShift(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "startHour" + ":" + getStartHour()+ "," +
            "endHour" + ":" + getEndHour()+ "," +
            "shiftID" + ":" + getShiftID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "shiftAssignee = "+(getShiftAssignee()!=null?Integer.toHexString(System.identityHashCode(getShiftAssignee())):"null");
  }
}