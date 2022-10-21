/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 34 "model.ump"
// line 130 "model.ump"
public abstract class StaffMember extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //StaffMember Attributes
  private int staffMemberID;

  //StaffMember Associations
  private List<Loan> loans;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StaffMember(String aUsername, String aPassword, String aEmail, MMS aMuseumManagementSystem, int aStaffMemberID)
  {
    super(aUsername, aPassword, aEmail, aMuseumManagementSystem);
    staffMemberID = aStaffMemberID;
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
            "staffMemberID" + ":" + getStaffMemberID()+ "]";
  }
}