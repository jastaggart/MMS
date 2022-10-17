/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 80 "model.ump"
// line 175 "model.ump"
public class Room
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DisplayStatus { OnDisplay, InStorage, OnLoan }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Associations
  private MMS museumManagementSystem;
  private List<Artwork> artwork;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(MMS aMuseumManagementSystem)
  {
    boolean didAddMuseumManagementSystem = setMuseumManagementSystem(aMuseumManagementSystem);
    if (!didAddMuseumManagementSystem)
    {
      throw new RuntimeException("Unable to create room due to museumManagementSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    artwork = new ArrayList<Artwork>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public MMS getMuseumManagementSystem()
  {
    return museumManagementSystem;
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
      existingMuseumManagementSystem.removeRoom(this);
    }
    museumManagementSystem.addRoom(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtwork()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addArtwork(Artwork aArtwork)
  {
    boolean wasAdded = false;
    if (artwork.contains(aArtwork)) { return false; }
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
      artwork.add(aArtwork);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeArtwork(Artwork aArtwork)
  {
    boolean wasRemoved = false;
    if (artwork.contains(aArtwork))
    {
      artwork.remove(aArtwork);
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
    MMS placeholderMuseumManagementSystem = museumManagementSystem;
    this.museumManagementSystem = null;
    if(placeholderMuseumManagementSystem != null)
    {
      placeholderMuseumManagementSystem.removeRoom(this);
    }
    while( !artwork.isEmpty() )
    {
      artwork.get(0).setRoom(null);
    }
  }

}