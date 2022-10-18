/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Date;

// line 66 "model.ump"
// line 159 "model.ump"
public class Loan
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Loan Attributes
  private int loanFee;
  private Date startDate;
  private Date endDate;
  private int loanID;
  private boolean isApproved;

  //Loan Associations
  private Visitor loanRequestor;
  private StaffMember loanApprover;
  private Artwork artwork;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Loan(int aLoanFee, Date aStartDate, Date aEndDate, int aLoanID, boolean aIsApproved, Visitor aLoanRequestor, StaffMember aLoanApprover, Artwork aArtwork)
  {
    loanFee = aLoanFee;
    startDate = aStartDate;
    endDate = aEndDate;
    loanID = aLoanID;
    isApproved = aIsApproved;
    boolean didAddLoanRequestor = setLoanRequestor(aLoanRequestor);
    if (!didAddLoanRequestor)
    {
      throw new RuntimeException("Unable to create loan due to loanRequestor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddLoanApprover = setLoanApprover(aLoanApprover);
    if (!didAddLoanApprover)
    {
      throw new RuntimeException("Unable to create loan due to loanApprover. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddArtwork = setArtwork(aArtwork);
    if (!didAddArtwork)
    {
      throw new RuntimeException("Unable to create loa due to artwork. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLoanFee(int aLoanFee)
  {
    boolean wasSet = false;
    loanFee = aLoanFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setLoanID(int aLoanID)
  {
    boolean wasSet = false;
    loanID = aLoanID;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsApproved(boolean aIsApproved)
  {
    boolean wasSet = false;
    isApproved = aIsApproved;
    wasSet = true;
    return wasSet;
  }

  public int getLoanFee()
  {
    return loanFee;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public int getLoanID()
  {
    return loanID;
  }

  public boolean getIsApproved()
  {
    return isApproved;
  }
  /* Code from template association_GetOne */
  public Visitor getLoanRequestor()
  {
    return loanRequestor;
  }
  /* Code from template association_GetOne */
  public StaffMember getLoanApprover()
  {
    return loanApprover;
  }
  /* Code from template association_GetOne */
  public Artwork getArtwork()
  {
    return artwork;
  }
  /* Code from template association_SetOneToMany */
  public boolean setLoanRequestor(Visitor aLoanRequestor)
  {
    boolean wasSet = false;
    if (aLoanRequestor == null)
    {
      return wasSet;
    }

    Visitor existingLoanRequestor = loanRequestor;
    loanRequestor = aLoanRequestor;
    if (existingLoanRequestor != null && !existingLoanRequestor.equals(aLoanRequestor))
    {
      existingLoanRequestor.removeLoan(this);
    }
    loanRequestor.addLoan(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setLoanApprover(StaffMember aLoanApprover)
  {
    boolean wasSet = false;
    if (aLoanApprover == null)
    {
      return wasSet;
    }

    StaffMember existingLoanApprover = loanApprover;
    loanApprover = aLoanApprover;
    if (existingLoanApprover != null && !existingLoanApprover.equals(aLoanApprover))
    {
      existingLoanApprover.removeLoan(this);
    }
    loanApprover.addLoan(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setArtwork(Artwork aArtwork)
  {
    boolean wasSet = false;
    if (aArtwork == null)
    {
      return wasSet;
    }

    Artwork existingArtwork = artwork;
    artwork = aArtwork;
    if (existingArtwork != null && !existingArtwork.equals(aArtwork))
    {
      existingArtwork.removeLoa(this);
    }
    artwork.addLoa(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Visitor placeholderLoanRequestor = loanRequestor;
    this.loanRequestor = null;
    if(placeholderLoanRequestor != null)
    {
      placeholderLoanRequestor.removeLoan(this);
    }
    StaffMember placeholderLoanApprover = loanApprover;
    this.loanApprover = null;
    if(placeholderLoanApprover != null)
    {
      placeholderLoanApprover.removeLoan(this);
    }
    Artwork placeholderArtwork = artwork;
    this.artwork = null;
    if(placeholderArtwork != null)
    {
      placeholderArtwork.removeLoa(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "loanFee" + ":" + getLoanFee()+ "," +
            "loanID" + ":" + getLoanID()+ "," +
            "isApproved" + ":" + getIsApproved()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "loanRequestor = "+(getLoanRequestor()!=null?Integer.toHexString(System.identityHashCode(getLoanRequestor())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "loanApprover = "+(getLoanApprover()!=null?Integer.toHexString(System.identityHashCode(getLoanApprover())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "artwork = "+(getArtwork()!=null?Integer.toHexString(System.identityHashCode(getArtwork())):"null");
  }
}