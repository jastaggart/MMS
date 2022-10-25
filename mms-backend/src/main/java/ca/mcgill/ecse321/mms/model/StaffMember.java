/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.mms.model;
import java.util.*;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.sql.Date;

// line 36 "model.ump"
// line 132 "model.ump"
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="employeeType", discriminatorType = DiscriminatorType.STRING)
public abstract class StaffMember extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //StaffMember Attributes
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Id
  private int staffMemberID;

  //StaffMember Associations
  @ManyToOne
  private MMS museumManagementSystem;

  @OneToMany(mappedBy="loanApprover")
  private List<Loan> loans;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StaffMember(String aUsername, String aPassword, String aEmail, int aStaffMemberID, MMS aMuseumManagementSystem)
  {
    super(aUsername, aPassword, aEmail);
    staffMemberID = aStaffMemberID;
    boolean didAddMuseumManagementSystem = setMuseumManagementSystem(aMuseumManagementSystem);
    if (!didAddMuseumManagementSystem)
    {
      throw new RuntimeException("Unable to create staffMember due to museumManagementSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    loans = new ArrayList<Loan>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStaffMemberID(int aStaffMemberID)
  {
    boolean wasSet = false;
    staffMemberID = aStaffMemberID;
    wasSet = true;
    return wasSet;
  }

  public int getStaffMemberID()
  {
    return staffMemberID;
  }
  /* Code from template association_GetOne */
  public MMS getMuseumManagementSystem()
  {
    return museumManagementSystem;
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
    museumManagementSystem = aMuseumManagementSystem;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoans()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Loan addLoan(int aLoanFee, Date aStartDate, Date aEndDate, int aLoanID, boolean aIsApproved, Visitor aLoanRequestor, Artwork aArtwork)
  {
    return new Loan(aLoanFee, aStartDate, aEndDate, aLoanID, aIsApproved, aLoanRequestor, this, aArtwork);
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    StaffMember existingLoanApprover = aLoan.getLoanApprover();
    boolean isNewLoanApprover = existingLoanApprover != null && !this.equals(existingLoanApprover);
    if (isNewLoanApprover)
    {
      aLoan.setLoanApprover(this);
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
    //Unable to remove aLoan, as it must always have a loanApprover
    if (!this.equals(aLoan.getLoanApprover()))
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
    this.museumManagementSystem = null;
   
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
            "staffMemberID" + ":" + getStaffMemberID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museumManagementSystem = "+(getMuseumManagementSystem()!=null?Integer.toHexString(System.identityHashCode(getMuseumManagementSystem())):"null");
  }
}