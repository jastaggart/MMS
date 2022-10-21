/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 80 "model.ump"
// line 179 "model.ump"
public class Artwork
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Artwork Attributes
  private boolean availableForLoan;
  private DisplayStatus status;
  private int artworkID;
  private String name;
  private String artist;

  //Artwork Associations
  private List<Loan> loans;
  private Room room;
  private MMS museumManagementSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artwork(boolean aAvailableForLoan, DisplayStatus aStatus, int aArtworkID, String aName, String aArtist, MMS aMuseumManagementSystem)
  {
    availableForLoan = aAvailableForLoan;
    status = aStatus;
    artworkID = aArtworkID;
    name = aName;
    artist = aArtist;
    loans = new ArrayList<Loan>();
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

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setArtist(String aArtist)
  {
    boolean wasSet = false;
    artist = aArtist;
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

  public String getName()
  {
    return name;
  }

  public String getArtist()
  {
    return artist;
  }
  /* Code from template association_GetMany */
  public Loan getLoan(int index)
  {
    Loan aLoan = loans.get(index);
    return aLoan;
  }

  public List<Loan> getLoans()
  {
    List<Loan> newLoans = Collections.unmodifiableList(loans);
    return newLoans;
  }

  public int numberOfLoans()
  {
    int number = loans.size();
    return number;
  }

  public boolean hasLoans()
  {
    boolean has = loans.size() > 0;
    return has;
  }

  public int indexOfLoan(Loan aLoan)
  {
    int index = loans.indexOf(aLoan);
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
  public static int minimumNumberOfLoans()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Loan addLoan(int aLoanFee, Date aStartDate, Date aEndDate, int aLoanID, boolean aIsApproved, Visitor aLoanRequestor, StaffMember aLoanApprover)
  {
    return new Loan(aLoanFee, aStartDate, aEndDate, aLoanID, aIsApproved, aLoanRequestor, aLoanApprover, this);
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    Artwork existingArtwork = aLoan.getArtwork();
    boolean isNewArtwork = existingArtwork != null && !this.equals(existingArtwork);
    if (isNewArtwork)
    {
      aLoan.setArtwork(this);
    }
    else
    {
      loans.add(aLoan);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLoan(Loan aLoan)
  {
    boolean wasRemoved = false;
    //Unable to remove aLoan, as it must always have a artwork
    if (!this.equals(aLoan.getArtwork()))
    {
      loans.remove(aLoan);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLoanAt(Loan aLoan, int index)
  {  
    boolean wasAdded = false;
    if(addLoan(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLoanAt(Loan aLoan, int index)
  {
    boolean wasAdded = false;
    if(loans.contains(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLoanAt(aLoan, index);
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
    for(int i=loans.size(); i > 0; i--)
    {
      Loan aLoan = loans.get(i - 1);
      aLoan.delete();
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
            "artworkID" + ":" + getArtworkID()+ "," +
            "name" + ":" + getName()+ "," +
            "artist" + ":" + getArtist()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "room = "+(getRoom()!=null?Integer.toHexString(System.identityHashCode(getRoom())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "museumManagementSystem = "+(getMuseumManagementSystem()!=null?Integer.toHexString(System.identityHashCode(getMuseumManagementSystem())):"null");
  }
}