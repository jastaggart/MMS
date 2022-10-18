/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.mms.model;

// line 2 "model.ump"
// line 226 "model.ump"
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
  private Status status;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DisplayStatus(Status aStatus)
  {
    status = aStatus;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStatus(Status aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public Status getStatus()
  {
    return status;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null");
  }
}