package ca.mcgill.ecse321.mms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ca.mcgill.ecse321.mms.model.Loan;
//import ca.mcgill.ecse321.mms.dto.VisitorRequestDto; // TODO uncomment when VisitorRequestDto done
import ca.mcgill.ecse321.mms.dto.ArtworkRequestDto;

import java.sql.Date;

public class LoanRequestDto {
	@NotNull
	@NotBlank
	private Date startDate;

    @NotNull
	@NotBlank
	private Date endDate;

    // @NotNull
    // @NotBlank
    // private VisitorRequestDto loanRequestor;

    @NotNull
    @NotBlank
    private ArtworkRequestDto artwork;
	
    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    { 
        return endDate;
    }

    // public VisitorRequestDto getLoanRequestor()
    // {
    //     return loanRequestor;
    // }

    public ArtworkRequestDto getArtwork()
    {
        return artwork;
    }

    public void setStartDate(Date aStartDate)
    {
        startDate = aStartDate;
    }

    public void setEndDate(Date aEndDate)
    {
        endDate = aEndDate;
    }

    // public void setLoanRequestor(VisitorRequestDto aLoanRequestor)
    // {
    //     loanRequestor = aLoanRequestor;
    // }

    public void setArtwork(ArtworkRequestDto aArtwork)
    {
        artwork = aArtwork;
    }

    public Loan toModel() {
		Loan loan = new Loan();
        loan.setStartDate(this.startDate);
        loan.setEndDate(this.endDate);
        // loan.setLoanRequestor(this.loanRequestor.toModel());
        loan.setArtwork(this.artwork.toModel());
		return loan;
	}
	
}