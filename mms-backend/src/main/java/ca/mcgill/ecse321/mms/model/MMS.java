/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.mms.model;
import java.sql.Time;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


// line 11 "model.ump"
// line 206 "model.ump"
@Entity
public class MMS
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MMS Attributes
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Id
  private int museumID;
  
  @Column(name="museumOpeningTime")
  private Time openingHours;

  @Column(name="museumClosingTime")
  private Time closingHours;

  @Column(name="passFee")
  private int passFee;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MMS() {

  }

  public MMS(Time aOpeningHours, Time aClosingHours, int aPassFee, int aMuseumID)
  {
    openingHours = aOpeningHours;
    closingHours = aClosingHours;
    passFee = aPassFee;
    museumID = aMuseumID;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOpeningHours(Time aOpeningHours)
  {
    boolean wasSet = false;
    openingHours = aOpeningHours;
    wasSet = true;
    return wasSet;
  }

  public boolean setClosingHours(Time aClosingHours)
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

  public Time getOpeningHours()
  {
    return openingHours;
  }

  public Time getClosingHours()
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

  public static int minimumNumberOfRooms()
  {
    return 0;
  }


  public static int maximumNumberOfRooms()
  {
    return 11;
  }


  public static int minimumNumberOfStaffMembers()
  {
    return 0;
  }

  public static int minimumNumberOfVisitors()
  {
    return 0;
  }


  /* Code from template association_AddManyToOne */
  public Visitor addVisitor(String aUsername, String aPassword, String aEmail, int aVisitorID)
  {
    return new Visitor(aUsername, aPassword, aEmail, aVisitorID, this);
  }

  public static int minimumNumberOfArtworks()
  {
    return 0;
  }


  /* Code from template association_AddManyToOne */
  public Artwork addArtwork(boolean aAvailableForLoan, String aStatus, int aArtworkID, String aName, String aArtist)
  {
    return new Artwork(aAvailableForLoan, aStatus, aArtworkID, aName, aArtist, this);
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