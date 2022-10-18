/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 11 "model.ump"
// line 214 "model.ump"
public class MMS
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MMS Attributes
  private String openingHours;
  private String closingHours;
  private int passFee;

  //MMS Associations
  private List<User> users;
  private List<Room> rooms;
  private List<Artwork> artworks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MMS(String aOpeningHours, String aClosingHours, int aPassFee)
  {
    openingHours = aOpeningHours;
    closingHours = aClosingHours;
    passFee = aPassFee;
    users = new ArrayList<User>();
    rooms = new ArrayList<Room>();
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
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
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
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public User addUser(String aUsername, String aPassword, String aEmail)
  {
    return new User(aUsername, aPassword, aEmail, this);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    MMS existingMuseumManagementSystem = aUser.getMuseumManagementSystem();
    boolean isNewMuseumManagementSystem = existingMuseumManagementSystem != null && !this.equals(existingMuseumManagementSystem);
    if (isNewMuseumManagementSystem)
    {
      aUser.setMuseumManagementSystem(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a museumManagementSystem
    if (!this.equals(aUser.getMuseumManagementSystem()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRooms()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Room addRoom()
  {
    return new Room(this);
  }

  public boolean addRoom(Room aRoom)
  {
    boolean wasAdded = false;
    if (rooms.contains(aRoom)) { return false; }
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
  public static int minimumNumberOfArtworks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Artwork addArtwork(boolean aAvailableForLoan, DisplayStatus aStatus, int aArtworkID)
  {
    return new Artwork(aAvailableForLoan, aStatus, aArtworkID, this);
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
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (rooms.size() > 0)
    {
      Room aRoom = rooms.get(rooms.size() - 1);
      aRoom.delete();
      rooms.remove(aRoom);
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
            "passFee" + ":" + getPassFee()+ "]";
  }
}