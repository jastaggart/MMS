/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.mms.model;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// line 62 "model.ump"
// line 162 "model.ump"
@Entity
public class Shift
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Shift Attributes
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Id
  private int shiftID;

  @Column(name="shiftDate")
  private Date date;

  @Column(name="shiftStartTime")
  private Time startHour;

  @Column(name="shiftEndTime")
  private Time endHour;


  //Shift Associations
  @ManyToOne
  private Owner shiftAssigner;
  
  @ManyToOne
  private Employee shiftAssignee;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Shift() {
  }
  
  public Shift(Date aDate, Time aStartHour, Time aEndHour, int aShiftID, Owner aShiftAssigner, Employee aShiftAssignee)
  {
    date = aDate;
    startHour = aStartHour;
    endHour = aEndHour;
    shiftID = aShiftID;
    boolean didAddShiftAssigner = setShiftAssigner(aShiftAssigner);
    if (!didAddShiftAssigner)
    {
      throw new RuntimeException("Unable to create shift due to shiftAssigner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public boolean setStartHour(Time aStartHour)
  {
    boolean wasSet = false;
    startHour = aStartHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndHour(Time aEndHour)
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

  public Time getStartHour()
  {
    return startHour;
  }

  public Time getEndHour()
  {
    return endHour;
  }

  public int getShiftID()
  {
    return shiftID;
  }
  /* Code from template association_GetOne */
  public Owner getShiftAssigner()
  {
    return shiftAssigner;
  }
  /* Code from template association_GetOne */
  public Employee getShiftAssignee()
  {
    return shiftAssignee;
  }
  /* Code from template association_SetOneToMany */
  public boolean setShiftAssigner(Owner aShiftAssigner)
  {
    boolean wasSet = false;
    if (aShiftAssigner == null)
    {
      return wasSet;
    }

    Owner existingShiftAssigner = shiftAssigner;
    shiftAssigner = aShiftAssigner;
    if (existingShiftAssigner != null && !existingShiftAssigner.equals(aShiftAssigner))
    {
      existingShiftAssigner.removeShift(this);
    }
    shiftAssigner.addShift(this);
    wasSet = true;
    return wasSet;
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
    Owner placeholderShiftAssigner = shiftAssigner;
    this.shiftAssigner = null;
    if(placeholderShiftAssigner != null)
    {
      placeholderShiftAssigner.removeShift(this);
    }
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
            "  " + "shiftAssigner = "+(getShiftAssigner()!=null?Integer.toHexString(System.identityHashCode(getShiftAssigner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "shiftAssignee = "+(getShiftAssignee()!=null?Integer.toHexString(System.identityHashCode(getShiftAssignee())):"null");
  }
}