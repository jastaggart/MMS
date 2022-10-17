/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 84 "model.ump"
// line 180 "model.ump"
public class DisplayRoom extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DisplayRoom Attributes
  private int roomNumber;
  private int maximumCapacity;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DisplayRoom(MMS aMuseumManagementSystem, int aRoomNumber, int aMaximumCapacity)
  {
    super(aMuseumManagementSystem);
    roomNumber = aRoomNumber;
    maximumCapacity = aMaximumCapacity;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoomNumber(int aRoomNumber)
  {
    boolean wasSet = false;
    roomNumber = aRoomNumber;
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

  public int getRoomNumber()
  {
    return roomNumber;
  }

  public int getMaximumCapacity()
  {
    return maximumCapacity;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "roomNumber" + ":" + getRoomNumber()+ "," +
            "maximumCapacity" + ":" + getMaximumCapacity()+ "]";
  }
}