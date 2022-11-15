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

    @NotNull
    @NotBlank
    private VisitorRequestDto loanRequestor;

    @NotNull
    @NotBlank
    private ArtworkRequestDto artwork;
	
    public String getStartDate()
    {
        return startDate;
    }

    public String getEndDate()
    { 
        return endDate;
    }

    public VisitorRequestDto getLoanRequestor()
    {
        return loanRequestor;
    }

    public ArtworkRequestDto getArtwork()
    {
        return artwork;
    }

    public void setStartDate(String aStartDate)
    {
        startDate = aStartDate;
    }

    public void setEndDate(String aEndDate)
    {
        endDate = aEndDate;
    }

    public void setLoanRequestor(VisitorRequestDto aLoanRequestor)
    {
        loanRequestor = aLoanRequestor;
    }

    public void setArtwork(ArtworkRequestDto aArtwork)
    {
        artwork = aArtwork;
    }

    public Loan toModel() {
		Loan loan = new Loan();
        loan.setStartDate(Date.valueOf(this.startDate));
        loan.setEndDate(Date.valueOf(this.endDate));
        loan.setLoanRequestor(this.loanRequestor.toModel());
        loan.setArtwork(this.artwork.toModel());
		return loan;
	}
	
}