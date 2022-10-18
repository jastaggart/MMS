/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 2 "model.ump"
// line 218 "model.ump"
public class DisplayStatus
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Status { OnDisplay, InStorage, OnLoan }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DisplayStatus Attributes
  private Status displayStatus;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DisplayStatus(Status aDisplayStatus)
  {
    displayStatus = aDisplayStatus;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDisplayStatus(Status aDisplayStatus)
  {
    boolean wasSet = false;
    displayStatus = aDisplayStatus;
    wasSet = true;
    return wasSet;
  }

  public Status getDisplayStatus()
  {
    return displayStatus;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "displayStatus" + "=" + (getDisplayStatus() != null ? !getDisplayStatus().equals(this)  ? getDisplayStatus().toString().replaceAll("  ","    ") : "this" : "null");
  }
}