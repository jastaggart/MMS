/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.mms.model;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// line 43 "model.ump"
// line 137 "model.ump"
@Entity


public class Pass
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Pass Attributes
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Id
  private int passID;

  @Column(name="passValidDate")
  private Date passDate;

  //Pass Associations
  @ManyToOne
  private Visitor passPurchaser;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Pass() {

  }
  
  public Pass(int aPassID, Date aPassDate, Visitor aPassPurchaser)
  {
    passID = aPassID;
    passDate = aPassDate;
    boolean didAddPassPurchaser = setPassPurchaser(aPassPurchaser);
    if (!didAddPassPurchaser)
    {
      throw new RuntimeException("Unable to create pass due to passPurchaser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPassID(int aPassID)
  {
    boolean wasSet = false;
    passID = aPassID;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassDate(Date aPassDate)
  {
    boolean wasSet = false;
    passDate = aPassDate;
    wasSet = true;
    return wasSet;
  }

  public int getPassID()
  {
    return passID;
  }

  public Date getPassDate()
  {
    return passDate;
  }
  /* Code from template association_GetOne */
  public Visitor getPassPurchaser()
  {
    return passPurchaser;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPassPurchaser(Visitor aPassPurchaser)
  {
    boolean wasSet = false;
    if (aPassPurchaser == null)
    {
      return wasSet;
    }

    Visitor existingPassPurchaser = passPurchaser;
    passPurchaser = aPassPurchaser;
    if (existingPassPurchaser != null && !existingPassPurchaser.equals(aPassPurchaser))
    {
      existingPassPurchaser.removePass(this);
    }
    passPurchaser.addPass(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Visitor placeholderPassPurchaser = passPurchaser;
    this.passPurchaser = null;
    if(placeholderPassPurchaser != null)
    {
      placeholderPassPurchaser.removePass(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "passID" + ":" + getPassID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "passDate" + "=" + (getPassDate() != null ? !getPassDate().equals(this)  ? getPassDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "passPurchaser = "+(getPassPurchaser()!=null?Integer.toHexString(System.identityHashCode(getPassPurchaser())):"null");
  }
}