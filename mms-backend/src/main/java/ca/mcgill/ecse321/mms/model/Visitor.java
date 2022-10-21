/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.mms.model;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.sql.Date;

// line 30 "model.ump"
// line 127 "model.ump"
@Entity
public class Visitor extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Visitor Attributes
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Id
  private int visitorID;

  //Visitor Associations
  @ManyToOne
  private MMS museumManagementSystem;

  @OneToMany(mappedBy="passPurchaser")
  private List<Pass> passes;

  @OneToMany(mappedBy="loanRequestor")
  private List<Loan> loans;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Visitor(String aUsername, String aPassword, String aEmail, int aVisitorID, MMS aMuseumManagementSystem)
  {
    super(aUsername, aPassword, aEmail);
    visitorID = aVisitorID;
    boolean didAddMuseumManagementSystem = setMuseumManagementSystem(aMuseumManagementSystem);
    if (!didAddMuseumManagementSystem)
    {
      throw new RuntimeException("Unable to create visitor due to museumManagementSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    passes = new ArrayList<Pass>();
    loans = new ArrayList<Loan>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setVisitorID(int aVisitorID)
  {
    boolean wasSet = false;
    visitorID = aVisitorID;
    wasSet = true;
    return wasSet;
  }

  public int getVisitorID()
  {
    return visitorID;
  }
  /* Code from template association_GetOne */
  public MMS getMuseumManagementSystem()
  {
    return museumManagementSystem;
  }
  /* Code from template association_GetMany */
  public Pass getPass(int index)
  {
    Pass aPass = passes.get(index);
    return aPass;
  }

  public List<Pass> getPasses()
  {
    List<Pass> newPasses = Collections.unmodifiableList(passes);
    return newPasses;
  }

  public int numberOfPasses()
  {
    int number = passes.size();
    return number;
  }

  public boolean hasPasses()
  {
    boolean has = passes.size() > 0;
    return has;
  }

  public int indexOfPass(Pass aPass)
  {
    int index = passes.indexOf(aPass);
    return index;
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
      existingMuseumManagementSystem.removeVisitor(this);
    }
    museumManagementSystem.addVisitor(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPasses()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Pass addPass(int aPassID, Date aPassDate)
  {
    return new Pass(aPassID, aPassDate, this);
  }

  public boolean addPass(Pass aPass)
  {
    boolean wasAdded = false;
    if (passes.contains(aPass)) { return false; }
    Visitor existingPassPurchaser = aPass.getPassPurchaser();
    boolean isNewPassPurchaser = existingPassPurchaser != null && !this.equals(existingPassPurchaser);
    if (isNewPassPurchaser)
    {
      aPass.setPassPurchaser(this);
    }
    else
    {
      passes.add(aPass);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePass(Pass aPass)
  {
    boolean wasRemoved = false;
    //Unable to remove aPass, as it must always have a passPurchaser
    if (!this.equals(aPass.getPassPurchaser()))
    {
      passes.remove(aPass);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPassAt(Pass aPass, int index)
  {  
    boolean wasAdded = false;
    if(addPass(aPass))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPasses()) { index = numberOfPasses() - 1; }
      passes.remove(aPass);
      passes.add(index, aPass);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePassAt(Pass aPass, int index)
  {
    boolean wasAdded = false;
    if(passes.contains(aPass))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPasses()) { index = numberOfPasses() - 1; }
      passes.remove(aPass);
      passes.add(index, aPass);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPassAt(aPass, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoans()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Loan addLoan(int aLoanFee, Date aStartDate, Date aEndDate, int aLoanID, boolean aIsApproved, StaffMember aLoanApprover, Artwork aArtwork)
  {
    return new Loan(aLoanFee, aStartDate, aEndDate, aLoanID, aIsApproved, this, aLoanApprover, aArtwork);
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    Visitor existingLoanRequestor = aLoan.getLoanRequestor();
    boolean isNewLoanRequestor = existingLoanRequestor != null && !this.equals(existingLoanRequestor);
    if (isNewLoanRequestor)
    {
      aLoan.setLoanRequestor(this);
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
    //Unable to remove aLoan, as it must always have a loanRequestor
    if (!this.equals(aLoan.getLoanRequestor()))
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

  public void delete()
  {
    MMS placeholderMuseumManagementSystem = museumManagementSystem;
    this.museumManagementSystem = null;
    if(placeholderMuseumManagementSystem != null)
    {
      placeholderMuseumManagementSystem.removeVisitor(this);
    }
    for(int i=passes.size(); i > 0; i--)
    {
      Pass aPass = passes.get(i - 1);
      aPass.delete();
    }
    for(int i=loans.size(); i > 0; i--)
    {
      Loan aLoan = loans.get(i - 1);
      aLoan.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "visitorID" + ":" + getVisitorID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museumManagementSystem = "+(getMuseumManagementSystem()!=null?Integer.toHexString(System.identityHashCode(getMuseumManagementSystem())):"null");
  }
}