/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.mms.model;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

// line 108 "model.ump"
// line 235 "model.ump"
@Entity
@DiscriminatorValue("DisplayRoom")
public class DisplayRoom extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DisplayRoom Attributes
  @Column(name="roomSize", columnDefinition="VARCHAR(40)")
  
  private String size;

  @Column(name="roomMaximumCapacity")
  private int maximumCapacity;

  @Column(name="displayRoomNumber")
  private int displayRoomNumber;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DisplayRoom() {
    super();
  }

  public DisplayRoom(int aRoomID, MMS aMuseumManagementSystem, String aSize, int aMaximumCapacity, int aDisplayRoomNumber)
  {
    super(aRoomID, aMuseumManagementSystem);
    size = aSize;
    maximumCapacity = aMaximumCapacity;
    displayRoomNumber = aDisplayRoomNumber;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSize(String aSize)
  {
    boolean wasSet = false;
    size = aSize;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaximumCapacity(int aMaximumCapacity)
  {
    boolean wasSet = false;
    maximumCapacity = aMaximumCapacity;
    wasSet = true;
    return wasSet;
  }

  public boolean setDisplayRoomNumber(int aDisplayRoomNumber)
  {
    boolean wasSet = false;
    displayRoomNumber = aDisplayRoomNumber;
    wasSet = true;
    return wasSet;
  }

  public String getSize()
  {
    return size;
  }

  public int getMaximumCapacity()
  {
    return maximumCapacity;
  }

  public int getDisplayRoomNumber()
  {
    return displayRoomNumber;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "maximumCapacity" + ":" + getMaximumCapacity()+ "," +
            "displayRoomNumber" + ":" + getDisplayRoomNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "size" + "=" + (getSize() != null ? !getSize().equals(this)  ? getSize().toString().replaceAll("  ","    ") : "this" : "null");
  }
}