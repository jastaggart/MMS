/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 106 "model.ump"
// line 227 "model.ump"
public class DisplayRoom extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DisplayRoom Attributes
  private RoomSize size;
  private int maximumCapacity;
  private int displayRoomNumber;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DisplayRoom(int aRoomID, MMS aMuseumManagementSystem, RoomSize aSize, int aMaximumCapacity, int aDisplayRoomNumber)
  {
    super(aRoomID, aMuseumManagementSystem);
    size = aSize;
    maximumCapacity = aMaximumCapacity;
    displayRoomNumber = aDisplayRoomNumber;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSize(RoomSize aSize)
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

  public RoomSize getSize()
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