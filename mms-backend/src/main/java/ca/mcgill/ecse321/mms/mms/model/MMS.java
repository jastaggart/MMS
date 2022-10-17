/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 4 "model.ump"
// line 200 "model.ump"
public class MMS
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DisplayStatus { OnDisplay, InStorage, OnLoan }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MMS Attributes
  private String openingHours;
  private String closingHours;
  private int passFee;

  //MMS Associations
  private List<User> user;
  private List<Room> room;
  private List<Artwork> artwork;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MMS(String aOpeningHours, String aClosingHours, int aPassFee)
  {
    openingHours = aOpeningHours;
    closingHours = aClosingHours;
    passFee = aPassFee;
    user = new ArrayList<User>();
    room = new ArrayList<Room>();
    artwork = new ArrayList<Artwork>();
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
    User aUser = user.get(index);
    return aUser;
  }

  public List<User> getUser()
  {
    List<User> newUser = Collections.unmodifiableList(user);
    return newUser;
  }

  public int numberOfUser()
  {
    int number = user.size();
    return number;
  }

  public boolean hasUser()
  {
    boolean has = user.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = user.indexOf(aUser);
    return index;
  }
  /* Code from template association_GetMany */
  public Room getRoom(int index)
  {
    Room aRoom = room.get(index);
    return aRoom;
  }

  public List<Room> getRoom()
  {
    List<Room> newRoom = Collections.unmodifiableList(room);
    return newRoom;
  }

  public int numberOfRoom()
  {
    int number = room.size();
    return number;
  }

  public boolean hasRoom()
  {
    boolean has = room.size() > 0;
    return has;
  }

  public int indexOfRoom(Room aRoom)
  {
    int index = room.indexOf(aRoom);
    return index;
  }
  /* Code from template association_GetMany */
  public Artwork getArtwork(int index)
  {
    Artwork aArtwork = artwork.get(index);
    return aArtwork;
  }

  public List<Artwork> getArtwork()
  {
    List<Artwork> newArtwork = Collections.unmodifiableList(artwork);
    return newArtwork;
  }

  public int numberOfArtwork()
  {
    int number = artwork.size();
    return number;
  }

  public boolean hasArtwork()
  {
    boolean has = artwork.size() > 0;
    return has;
  }

  public int indexOfArtwork(Artwork aArtwork)
  {
    int index = artwork.indexOf(aArtwork);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUser()
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
    if (user.contains(aUser)) { return false; }
    MMS existingMuseumManagementSystem = aUser.getMuseumManagementSystem();
    boolean isNewMuseumManagementSystem = existingMuseumManagementSystem != null && !this.equals(existingMuseumManagementSystem);
    if (isNewMuseumManagementSystem)
    {
      aUser.setMuseumManagementSystem(this);
    }
    else
    {
      user.add(aUser);
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
      user.remove(aUser);
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
      if(index > numberOfUser()) { index = numberOfUser() - 1; }
      user.remove(aUser);
      user.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(user.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUser()) { index = numberOfUser() - 1; }
      user.remove(aUser);
      user.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRoom()
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
    if (room.contains(aRoom)) { return false; }
    MMS existingMuseumManagementSystem = aRoom.getMuseumManagementSystem();
    boolean isNewMuseumManagementSystem = existingMuseumManagementSystem != null && !this.equals(existingMuseumManagementSystem);
    if (isNewMuseumManagementSystem)
    {
      aRoom.setMuseumManagementSystem(this);
    }
    else
    {
      room.add(aRoom);
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
      room.remove(aRoom);
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
      if(index > numberOfRoom()) { index = numberOfRoom() - 1; }
      room.remove(aRoom);
      room.add(index, aRoom);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoomAt(Room aRoom, int index)
  {
    boolean wasAdded = false;
    if(room.contains(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRoom()) { index = numberOfRoom() - 1; }
      room.remove(aRoom);
      room.add(index, aRoom);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoomAt(aRoom, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtwork()
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
    if (artwork.contains(aArtwork)) { return false; }
    MMS existingMuseumManagementSystem = aArtwork.getMuseumManagementSystem();
    boolean isNewMuseumManagementSystem = existingMuseumManagementSystem != null && !this.equals(existingMuseumManagementSystem);
    if (isNewMuseumManagementSystem)
    {
      aArtwork.setMuseumManagementSystem(this);
    }
    else
    {
      artwork.add(aArtwork);
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
      artwork.remove(aArtwork);
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
      if(index > numberOfArtwork()) { index = numberOfArtwork() - 1; }
      artwork.remove(aArtwork);
      artwork.add(index, aArtwork);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArtworkAt(Artwork aArtwork, int index)
  {
    boolean wasAdded = false;
    if(artwork.contains(aArtwork))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtwork()) { index = numberOfArtwork() - 1; }
      artwork.remove(aArtwork);
      artwork.add(index, aArtwork);
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
    while (user.size() > 0)
    {
      User aUser = user.get(user.size() - 1);
      aUser.delete();
      user.remove(aUser);
    }
    
    while (room.size() > 0)
    {
      Room aRoom = room.get(room.size() - 1);
      aRoom.delete();
      room.remove(aRoom);
    }
    
    while (artwork.size() > 0)
    {
      Artwork aArtwork = artwork.get(artwork.size() - 1);
      aArtwork.delete();
      artwork.remove(aArtwork);
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