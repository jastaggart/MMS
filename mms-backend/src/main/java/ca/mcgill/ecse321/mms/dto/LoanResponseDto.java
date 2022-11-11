package ca.mcgill.ecse321.mms.dto;

import ca.mcgill.ecse321.mms.model.Loan;
import ca.mcgill.ecse321.mms.dto.VisitorResponseDto;
//import ca.mcgill.ecse321.mms.dto.StaffMemberResponseDto;
import ca.mcgill.ecse321.mms.dto.ArtworkResponseDto;

import java.sql.Date;

public class LoanResponseDto {
    private int loanID;
    private int loanFee;
    private String startDate;
    private String endDate;
    private boolean isApproved;
    private VisitorResponseDto loanRequestor;
    // private StaffMemberResponseDto loanApprover;
    private ArtworkResponseDto artwork;

    public LoanResponseDto(Loan loan) {
        this.loanID = loan.getLoanID();
        this.loanFee = loan.getLoanFee();
        this.startDate = loan.getStartDate().toString();
        this.endDate = loan.getEndDate().toString();
        this.isApproved = loan.getIsApproved();
        this.loanRequestor = new VisitorResponseDto(loan.getLoanRequestor());
        // this.loanApprover = new StaffMemberResponseDto(loan.getLoanApprover());
        this.artwork = new ArtworkResponseDto(loan.getArtwork());
    }

    public int getLoanID()
    {
        return loanID;
    }

    public int getLoanFee()
    {
        return loanFee;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public boolean getIsApproved()
    {
        return isApproved;
    }

    public VisitorResponseDto getLoanRequestor() {
        return loanRequestor;
    }
    
    // public StaffMemberResponseDto getLoanApprover() {
    //     return loanApprover;
    // }

    public ArtworkResponseDto getArtwork() {
        return artwork;
    }

}