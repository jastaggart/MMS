/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 11 "model.ump"
// line 206 "model.ump"
public class MMS
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MMS Attributes
  private String openingHours;
  private String closingHours;
  private int passFee;
  private int museumID;

  //MMS Associations
  private List<Room> rooms;
  private List<StaffMember> staffMembers;
  private List<Visitor> visitors;
  private List<Artwork> artworks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MMS(String aOpeningHours, String aClosingHours, int aPassFee, int aMuseumID)
  {
    openingHours = aOpeningHours;
    closingHours = aClosingHours;
    passFee = aPassFee;
    museumID = aMuseumID;
    rooms = new ArrayList<Room>();
    staffMembers = new ArrayList<StaffMember>();
    visitors = new ArrayList<Visitor>();
    artworks = new ArrayList<Artwork>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOpeningHours(String aOpeningHours)
  {
    boolean wasSet = false;
    openingHours = aOpeningHours;
    wasSet = true;
    return wasSet;
  }

  public boolean setClosingHours(String aClosingHours)
  {
    boolean wasSet = false;
    closingHours = aClosingHours;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassFee(int aPassFee)
  {
    boolean wasSet = false;
    passFee = aPassFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setMuseumID(int aMuseumID)
  {
    boolean wasSet = false;
    museumID = aMuseumID;
    wasSet = true;
    return wasSet;
  }

  public String getOpeningHours()
  {
    return openingHours;
  }

  public String getClosingHours()
  {
    return closingHours;
  }

  public int getPassFee()
  {
    return passFee;
  }

  public int getMuseumID()
  {
    return museumID;
  }
  /* Code from template association_GetMany */
  public Room getRoom(int index)
  {
    Room aRoom = rooms.get(index);
    return aRoom;
  }

  public List<Room> getRooms()
  {
    List<Room> newRooms = Collections.unmodifiableList(rooms);
    return newRooms;
  }

  public int numberOfRooms()
  {
    int number = rooms.size();
    return number;
  }

  public boolean hasRooms()
  {
    boolean has = rooms.size() > 0;
    return has;
  }

  public int indexOfRoom(Room aRoom)
  {
    int index = rooms.indexOf(aRoom);
    return index;
  }
  /* Code from template association_GetMany */
  public StaffMember getStaffMember(int index)
  {
    StaffMember aStaffMember = staffMembers.get(index);
    return aStaffMember;
  }

  public List<StaffMember> getStaffMembers()
  {
    List<StaffMember> newStaffMembers = Collections.unmodifiableList(staffMembers);
    return newStaffMembers;
  }

  public int numberOfStaffMembers()
  {
    int number = staffMembers.size();
    return number;
  }

  public boolean hasStaffMembers()
  {
    boolean has = staffMembers.size() > 0;
    return has;
  }

  public int indexOfStaffMember(StaffMember aStaffMember)
  {
    int index = staffMembers.indexOf(aStaffMember);
    return index;
  }
  /* Code from template association_GetMany */
  public Visitor getVisitor(int index)
  {
    Visitor aVisitor = visitors.get(index);
    return aVisitor;
  }

  public List<Visitor> getVisitors()
  {
    List<Visitor> newVisitors = Collections.unmodifiableList(visitors);
    return newVisitors;
  }

  public int numberOfVisitors()
  {
    int number = visitors.size();
    return number;
  }

  public boolean hasVisitors()
  {
    boolean has = visitors.size() > 0;
    return has;
  }

  public int indexOfVisitor(Visitor aVisitor)
  {
    int index = visitors.indexOf(aVisitor);
    return index;
  }
  /* Code from template association_GetMany */
  public Artwork getArtwork(int index)
  {
    Artwork aArtwork = artworks.get(index);
    return aArtwork;
  }

  public List<Artwork> getArtworks()
  {
    List<Artwork> newArtworks = Collections.unmodifiableList(artworks);
    return newArtworks;
  }

  public int numberOfArtworks()
  {
    int number = artworks.size();
    return number;
  }

  public boolean hasArtworks()
  {
    boolean has = artworks.size() > 0;
    return has;
  }

  public int indexOfArtwork(Artwork aArtwork)
  {
    int index = artworks.indexOf(aArtwork);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRooms()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfRooms()
  {
    return 11;
  }
  /* Code from template association_AddOptionalNToOne */


  public boolean addRoom(Room aRoom)
  {
    boolean wasAdded = false;
    if (rooms.contains(aRoom)) { return false; }
    if (numberOfRooms() >= maximumNumberOfRooms())
    {
      return wasAdded;
    }

    MMS existingMuseumManagementSystem = aRoom.getMuseumManagementSystem();
    boolean isNewMuseumManagementSystem = existingMuseumManagementSystem != null && !this.equals(existingMuseumManagementSystem);
    if (isNewMuseumManagementSystem)
    {
      aRoom.setMuseumManagementSystem(this);
    }
    else
    {
      rooms.add(aRoom);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRoom(Room aRoom)
  {
    boolean wasRemoved = false;
    //Unable to remove aRoom, as it must always have a museumManagementSystem
    if (!this.equals(aRoom.getMuseumManagementSystem()))
    {
      rooms.remove(aRoom);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoomAt(Room aRoom, int index)
  {  
    boolean wasAdded = false;
    if(addRoom(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoomAt(Room aRoom, int index)
  {
    boolean wasAdded = false;
    if(rooms.contains(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoomAt(aRoom, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfStaffMembers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addStaffMember(StaffMember aStaffMember)
  {
    boolean wasAdded = false;
    if (staffMembers.contains(aStaffMember)) { return false; }
    MMS existingMuseumManagementSystem = aStaffMember.getMuseumManagementSystem();
    boolean isNewMuseumManagementSystem = existingMuseumManagementSystem != null && !this.equals(existingMuseumManagementSystem);
    if (isNewMuseumManagementSystem)
    {
      aStaffMember.setMuseumManagementSystem(this);
    }
    else
    {
      staffMembers.add(aStaffMember);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStaffMember(StaffMember aStaffMember)
  {
    boolean wasRemoved = false;
    //Unable to remove aStaffMember, as it must always have a museumManagementSystem
    if (!this.equals(aStaffMember.getMuseumManagementSystem()))
    {
      staffMembers.remove(aStaffMember);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addStaffMemberAt(StaffMember aStaffMember, int index)
  {  
    boolean wasAdded = false;
    if(addStaffMember(aStaffMember))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStaffMembers()) { index = numberOfStaffMembers() - 1; }
      staffMembers.remove(aStaffMember);
      staffMembers.add(index, aStaffMember);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStaffMemberAt(StaffMember aStaffMember, int index)
  {
    boolean wasAdded = false;
    if(staffMembers.contains(aStaffMember))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStaffMembers()) { index = numberOfStaffMembers() - 1; }
      staffMembers.remove(aStaffMember);
      staffMembers.add(index, aStaffMember);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStaffMemberAt(aStaffMember, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfVisitors()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Visitor addVisitor(String aUsername, String aPassword, String aEmail, int aVisitorID)
  {
    return new Visitor(aUsername, aPassword, aEmail, aVisitorID, this);
  }

  public boolean addVisitor(Visitor aVisitor)
  {
    boolean wasAdded = false;
    if (visitors.contains(aVisitor)) { return false; }
    MMS existingMuseumManagementSystem = aVisitor.getMuseumManagementSystem();
    boolean isNewMuseumManagementSystem = existingMuseumManagementSystem != null && !this.equals(existingMuseumManagementSystem);
    if (isNewMuseumManagementSystem)
    {
      aVisitor.setMuseumManagementSystem(this);
    }
    else
    {
      visitors.add(aVisitor);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeVisitor(Visitor aVisitor)
  {
    boolean wasRemoved = false;
    //Unable to remove aVisitor, as it must always have a museumManagementSystem
    if (!this.equals(aVisitor.getMuseumManagementSystem()))
    {
      visitors.remove(aVisitor);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addVisitorAt(Visitor aVisitor, int index)
  {  
    boolean wasAdded = false;
    if(addVisitor(aVisitor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVisitors()) { index = numberOfVisitors() - 1; }
      visitors.remove(aVisitor);
      visitors.add(index, aVisitor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveVisitorAt(Visitor aVisitor, int index)
  {
    boolean wasAdded = false;
    if(visitors.contains(aVisitor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVisitors()) { index = numberOfVisitors() - 1; }
      visitors.remove(aVisitor);
      visitors.add(index, aVisitor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addVisitorAt(aVisitor, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtworks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Artwork addArtwork(boolean aAvailableForLoan, DisplayStatus aStatus, int aArtworkID, String aName, String aArtist)
  {
    return new Artwork(aAvailableForLoan, aStatus, aArtworkID, aName, aArtist, this);
  }

  public boolean addArtwork(Artwork aArtwork)
  {
    boolean wasAdded = false;
    if (artworks.contains(aArtwork)) { return false; }
    MMS existingMuseumManagementSystem = aArtwork.getMuseumManagementSystem();
    boolean isNewMuseumManagementSystem = existingMuseumManagementSystem != null && !this.equals(existingMuseumManagementSystem);
    if (isNewMuseumManagementSystem)
    {
      aArtwork.setMuseumManagementSystem(this);
    }
    else
    {
      artworks.add(aArtwork);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeArtwork(Artwork aArtwork)
  {
    boolean wasRemoved = false;
    //Unable to remove aArtwork, as it must always have a museumManagementSystem
    if (!this.equals(aArtwork.getMuseumManagementSystem()))
    {
      artworks.remove(aArtwork);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addArtworkAt(Artwork aArtwork, int index)
  {  
    boolean wasAdded = false;
    if(addArtwork(aArtwork))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtworks()) { index = numberOfArtworks() - 1; }
      artworks.remove(aArtwork);
      artworks.add(index, aArtwork);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArtworkAt(Artwork aArtwork, int index)
  {
    boolean wasAdded = false;
    if(artworks.contains(aArtwork))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtworks()) { index = numberOfArtworks() - 1; }
      artworks.remove(aArtwork);
      artworks.add(index, aArtwork);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addArtworkAt(aArtwork, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (rooms.size() > 0)
    {
      Room aRoom = rooms.get(rooms.size() - 1);
      aRoom.delete();
      rooms.remove(aRoom);
    }
    
    for(int i=staffMembers.size(); i > 0; i--)
    {
      StaffMember aStaffMember = staffMembers.get(i - 1);
      aStaffMember.delete();
    }
    for(int i=visitors.size(); i > 0; i--)
    {
      Visitor aVisitor = visitors.get(i - 1);
      aVisitor.delete();
    }
    while (artworks.size() > 0)
    {
      Artwork aArtwork = artworks.get(artworks.size() - 1);
      aArtwork.delete();
      artworks.remove(aArtwork);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "openingHours" + ":" + getOpeningHours()+ "," +
            "closingHours" + ":" + getClosingHours()+ "," +
            "passFee" + ":" + getPassFee()+ "," +
            "museumID" + ":" + getMuseumID()+ "]";
  }
}