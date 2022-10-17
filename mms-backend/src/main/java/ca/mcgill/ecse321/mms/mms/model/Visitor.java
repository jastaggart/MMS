/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import java.sql.Date;

// line 20 "model.ump"
// line 116 "model.ump"
public class Visitor extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Visitor Attributes
  private int visitorID;

  //Visitor Associations
  private List<Pass> pass;
  private List<Loan> loan;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Visitor(String aUsername, String aPassword, String aEmail, MMS aMuseumManagementSystem, int aVisitorID)
  {
    super(aUsername, aPassword, aEmail, aMuseumManagementSystem);
    visitorID = aVisitorID;
    pass = new ArrayList<Pass>();
    loan = new ArrayList<Loan>();
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
  /* Code from template association_GetMany */
  public Pass getPass(int index)
  {
    Pass aPass = pass.get(index);
    return aPass;
  }

  public List<Pass> getPass()
  {
    List<Pass> newPass = Collections.unmodifiableList(pass);
    return newPass;
  }

  public int numberOfPass()
  {
    int number = pass.size();
    return number;
  }

  public boolean hasPass()
  {
    boolean has = pass.size() > 0;
    return has;
  }

  public int indexOfPass(Pass aPass)
  {
    int index = pass.indexOf(aPass);
    return index;
  }
  /* Code from template association_GetMany */
  public Loan getLoan(int index)
  {
    Loan aLoan = loan.get(index);
    return aLoan;
  }

  public List<Loan> getLoan()
  {
    List<Loan> newLoan = Collections.unmodifiableList(loan);
    return newLoan;
  }

  public int numberOfLoan()
  {
    int number = loan.size();
    return number;
  }

  public boolean hasLoan()
  {
    boolean has = loan.size() > 0;
    return has;
  }

  public int indexOfLoan(Loan aLoan)
  {
    int index = loan.indexOf(aLoan);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPass()
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
    if (pass.contains(aPass)) { return false; }
    Visitor existingPassPu = aPass.getPassPu();
    boolean isNewPassPu = existingPassPu != null && !this.equals(existingPassPu);
    if (isNewPassPu)
    {
      aPass.setPassPu(this);
    }
    else
    {
      pass.add(aPass);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePass(Pass aPass)
  {
    boolean wasRemoved = false;
    //Unable to remove aPass, as it must always have a passPu
    if (!this.equals(aPass.getPassPu()))
    {
      pass.remove(aPass);
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
      if(index > numberOfPass()) { index = numberOfPass() - 1; }
      pass.remove(aPass);
      pass.add(index, aPass);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePassAt(Pass aPass, int index)
  {
    boolean wasAdded = false;
    if(pass.contains(aPass))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPass()) { index = numberOfPass() - 1; }
      pass.remove(aPass);
      pass.add(index, aPass);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPassAt(aPass, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoan()
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
    if (loan.contains(aLoan)) { return false; }
    Visitor existingLoanRequestor = aLoan.getLoanRequestor();
    boolean isNewLoanRequestor = existingLoanRequestor != null && !this.equals(existingLoanRequestor);
    if (isNewLoanRequestor)
    {
      aLoan.setLoanRequestor(this);
    }
    else
    {
      loan.add(aLoan);
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
      loan.remove(aLoan);
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
      if(index > numberOfLoan()) { index = numberOfLoan() - 1; }
      loan.remove(aLoan);
      loan.add(index, aLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLoanAt(Loan aLoan, int index)
  {
    boolean wasAdded = false;
    if(loan.contains(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoan()) { index = numberOfLoan() - 1; }
      loan.remove(aLoan);
      loan.add(index, aLoan);
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
    for(int i=pass.size(); i > 0; i--)
    {
      Pass aPass = pass.get(i - 1);
      aPass.delete();
    }
    for(int i=loan.size(); i > 0; i--)
    {
      Loan aLoan = loan.get(i - 1);
      aLoan.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "visitorID" + ":" + getVisitorID()+ "]";
  }
}