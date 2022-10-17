/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Date;

// line 32 "model.ump"
// line 126 "model.ump"
public class Pass
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Pass Attributes
  private int passID;
  private Date passDate;

  //Pass Associations
  private Visitor passPu;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Pass(int aPassID, Date aPassDate, Visitor aPassPu)
  {
    passID = aPassID;
    passDate = aPassDate;
    boolean didAddPassPu = setPassPu(aPassPu);
    if (!didAddPassPu)
    {
      throw new RuntimeException("Unable to create pass due to passPu. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public Visitor getPassPu()
  {
    return passPu;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPassPu(Visitor aPassPu)
  {
    boolean wasSet = false;
    if (aPassPu == null)
    {
      return wasSet;
    }

    Visitor existingPassPu = passPu;
    passPu = aPassPu;
    if (existingPassPu != null && !existingPassPu.equals(aPassPu))
    {
      existingPassPu.removePass(this);
    }
    passPu.addPass(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Visitor placeholderPassPu = passPu;
    this.passPu = null;
    if(placeholderPassPu != null)
    {
      placeholderPassPu.removePass(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "passID" + ":" + getPassID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "passDate" + "=" + (getPassDate() != null ? !getPassDate().equals(this)  ? getPassDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "passPu = "+(getPassPu()!=null?Integer.toHexString(System.identityHashCode(getPassPu())):"null");
  }
}