/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.mms.model;
import java.util.*;

import javax.persistence.Entity;

// line 100 "model.ump"
// line 200 "model.ump"
@Entity
public class Storage extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Storage(int aRoomID, MMS aMuseumManagementSystem)
  {
    super(aRoomID, aMuseumManagementSystem);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}