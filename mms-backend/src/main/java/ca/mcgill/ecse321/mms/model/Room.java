/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.mms.model;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

// line 92 "model.ump"
// line 195 "model.ump"
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Id
  private int roomID;

  //Room Associations
  @ManyToOne
  private MMS museumManagementSystem;

  @OneToMany(mappedBy="room")
  private List<Artwork> artworks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(int aRoomID, MMS aMuseumManagementSystem)
  {
    roomID = aRoomID;
    boolean didAddMuseumManagementSystem = setMuseumManagementSystem(aMuseumManagementSystem);
    if (!didAddMuseumManagementSystem)
    {
      throw new RuntimeException("Unable to create room due to museumManagementSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    artworks = new ArrayList<Artwork>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoomID(int aRoomID)
  {
    boolean wasSet = false;
    roomID = aRoomID;
    wasSet = true;
    return wasSet;
  }

  public int getRoomID()
  {
    return roomID;
  }
  /* Code from template association_GetOne */
  public MMS getMuseumManagementSystem()
  {
    return museumManagementSystem;
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
  /* Code from template association_SetOneToAtMostN */
  public boolean setMuseumManagementSystem(MMS aMuseumManagementSystem)
  {
    boolean wasSet = false;
    //Must provide museumManagementSystem to room
    if (aMuseumManagementSystem == null)
    {
      return wasSet;
    }

    //museumManagementSystem already at maximum (11)
    if (aMuseumManagementSystem.numberOfRooms() >= MMS.maximumNumberOfRooms())
    {
      return wasSet;
    }
    
    MMS existingMuseumManagementSystem = museumManagementSystem;
    museumManagementSystem = aMuseumManagementSystem;
    if (existingMuseumManagementSystem != null && !existingMuseumManagementSystem.equals(aMuseumManagementSystem))
    {
      boolean didRemove = existingMuseumManagementSystem.removeRoom(this);
      if (!didRemove)
      {
        museumManagementSystem = existingMuseumManagementSystem;
        return wasSet;
      }
    }
    museumManagementSystem.addRoom(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtworks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addArtwork(Artwork aArtwork)
  {
    boolean wasAdded = false;
    if (artworks.contains(aArtwork)) { return false; }
    Room existingRoom = aArtwork.getRoom();
    if (existingRoom == null)
    {
      aArtwork.setRoom(this);
    }
    else if (!this.equals(existingRoom))
    {
      existingRoom.removeArtwork(aArtwork);
      addArtwork(aArtwork);
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
    if (artworks.contains(aArtwork))
    {
      artworks.remove(aArtwork);
      aArtwork.setRoom(null);
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
    MMS placeholderMuseumManagementSystem = museumManagementSystem;
    this.museumManagementSystem = null;
    if(placeholderMuseumManagementSystem != null)
    {
      placeholderMuseumManagementSystem.removeRoom(this);
    }
    while( !artworks.isEmpty() )
    {
      artworks.get(0).setRoom(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "roomID" + ":" + getRoomID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museumManagementSystem = "+(getMuseumManagementSystem()!=null?Integer.toHexString(System.identityHashCode(getMuseumManagementSystem())):"null");
  }
}