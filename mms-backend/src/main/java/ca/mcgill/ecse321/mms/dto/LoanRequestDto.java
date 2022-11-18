package ca.mcgill.ecse321.mms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ca.mcgill.ecse321.mms.model.Loan;
import ca.mcgill.ecse321.mms.dto.VisitorRequestDto; 
import ca.mcgill.ecse321.mms.dto.ArtworkRequestDto;

import java.sql.Date;

public class LoanRequestDto {
	@NotNull
	@NotBlank
	private String startDate;

    @NotNull
	@NotBlank
	private String endDate;

    private int loanRequestorID;

    private int artworkID;
	
    public String getStartDate()
    {
        return startDate;
    }

    public String getEndDate()
    { 
        return endDate;
    }

    public int getLoanRequestorID()
    {
        return loanRequestorID;
    }

    public int getArtworkID()
    {
        return artworkID;
    }

    public void setStartDate(String aStartDate)
    {
        startDate = aStartDate;
    }

    public void setEndDate(String aEndDate)
    {
        endDate = aEndDate;
    }

    public void setLoanRequestorID(int aLoanRequestorID)
    {
        loanRequestorID = aLoanRequestorID;
    }

    public void setArtworkID(int aArtworkID)
    {
        artworkID = aArtworkID;
    }

    public Loan toModel() {
		Loan loan = new Loan();
        loan.setStartDate(Date.valueOf(this.startDate));
        loan.setEndDate(Date.valueOf(this.endDate));
		return loan;
	}
	
}