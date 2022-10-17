/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 70 "model.ump"
// line 163 "model.ump"
public class Artwork
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DisplayStatus { OnDisplay, InStorage, OnLoan }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Artwork Attributes
  private boolean availableForLoan;
  private DisplayStatus status;
  private int artworkID;

  //Artwork Associations
  private List<Loan> loa;
  private Room room;
  private MMS museumManagementSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artwork(boolean aAvailableForLoan, DisplayStatus aStatus, int aArtworkID, MMS aMuseumManagementSystem)
  {
    availableForLoan = aAvailableForLoan;
    status = aStatus;
    artworkID = aArtworkID;
    loa = new ArrayList<Loan>();
    boolean didAddMuseumManagementSystem = setMuseumManagementSystem(aMuseumManagementSystem);
    if (!didAddMuseumManagementSystem)
    {
      throw new RuntimeException("Unable to create artwork due to museumManagementSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAvailableForLoan(boolean aAvailableForLoan)
  {
    boolean wasSet = false;
    availableForLoan = aAvailableForLoan;
    wasSet = true;
    return wasSet;
  }

  public boolean setStatus(DisplayStatus aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setArtworkID(int aArtworkID)
  {
    boolean wasSet = false;
    artworkID = aArtworkID;
    wasSet = true;
    return wasSet;
  }

  public boolean getAvailableForLoan()
  {
    return availableForLoan;
  }

  public DisplayStatus getStatus()
  {
    return status;
  }

  public int getArtworkID()
  {
    return artworkID;
  }
  /* Code from template association_GetMany */
  public Loan getLoa(int index)
  {
    Loan aLoa = loa.get(index);
    return aLoa;
  }

  public List<Loan> getLoa()
  {
    List<Loan> newLoa = Collections.unmodifiableList(loa);
    return newLoa;
  }

  public int numberOfLoa()
  {
    int number = loa.size();
    return number;
  }

  public boolean hasLoa()
  {
    boolean has = loa.size() > 0;
    return has;
  }

  public int indexOfLoa(Loan aLoa)
  {
    int index = loa.indexOf(aLoa);
    return index;
  }
  /* Code from template association_GetOne */
  public Room getRoom()
  {
    return room;
  }

  public boolean hasRoom()
  {
    boolean has = room != null;
    return has;
  }
  /* Code from template association_GetOne */
  public MMS getMuseumManagementSystem()
  {
    return museumManagementSystem;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoa()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Loan addLoa(int aLoanFee, Date aStartDate, Date aEndDate, int aLoanID, boolean aIsApproved, Visitor aLoanRequestor, StaffMember aLoanApprover)
  {
    return new Loan(aLoanFee, aStartDate, aEndDate, aLoanID, aIsApproved, aLoanRequestor, aLoanApprover, this);
  }

  public boolean addLoa(Loan aLoa)
  {
    boolean wasAdded = false;
    if (loa.contains(aLoa)) { return false; }
    Artwork existingArtwork = aLoa.getArtwork();
    boolean isNewArtwork = existingArtwork != null && !this.equals(existingArtwork);
    if (isNewArtwork)
    {
      aLoa.setArtwork(this);
    }
    else
    {
      loa.add(aLoa);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLoa(Loan aLoa)
  {
    boolean wasRemoved = false;
    //Unable to remove aLoa, as it must always have a artwork
    if (!this.equals(aLoa.getArtwork()))
    {
      loa.remove(aLoa);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLoaAt(Loan aLoa, int index)
  {  
    boolean wasAdded = false;
    if(addLoa(aLoa))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoa()) { index = numberOfLoa() - 1; }
      loa.remove(aLoa);
      loa.add(index, aLoa);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLoaAt(Loan aLoa, int index)
  {
    boolean wasAdded = false;
    if(loa.contains(aLoa))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoa()) { index = numberOfLoa() - 1; }
      loa.remove(aLoa);
      loa.add(index, aLoa);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLoaAt(aLoa, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setRoom(Room aRoom)
  {
    boolean wasSet = false;
    Room existingRoom = room;
    room = aRoom;
    if (existingRoom != null && !existingRoom.equals(aRoom))
    {
      existingRoom.removeArtwork(this);
    }
    if (aRoom != null)
    {
      aRoom.addArtwork(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMuseumManagementSystem(MMS aMuseumManagementSystem)
  {
    boolean wasSet = false;
    if (aMuseumManagementSystem == null)
    {
      return wasSet;
    }

    MMS existingMuseumManagementSystem = museumManagementSystem;
    museumManagementSystem = aMuseumManagementSystem;
    if (existingMuseumManagementSystem != null && !existingMuseumManagementSystem.equals(aMuseumManagementSystem))
    {
      existingMuseumManagementSystem.removeArtwork(this);
    }
    museumManagementSystem.addArtwork(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=loa.size(); i > 0; i--)
    {
      Loan aLoa = loa.get(i - 1);
      aLoa.delete();
    }
    if (room != null)
    {
      Room placeholderRoom = room;
      this.room = null;
      placeholderRoom.removeArtwork(this);
    }
    MMS placeholderMuseumManagementSystem = museumManagementSystem;
    this.museumManagementSystem = null;
    if(placeholderMuseumManagementSystem != null)
    {
      placeholderMuseumManagementSystem.removeArtwork(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "availableForLoan" + ":" + getAvailableForLoan()+ "," +
            "artworkID" + ":" + getArtworkID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "room = "+(getRoom()!=null?Integer.toHexString(System.identityHashCode(getRoom())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "museumManagementSystem = "+(getMuseumManagementSystem()!=null?Integer.toHexString(System.identityHashCode(getMuseumManagementSystem())):"null");
  }
}