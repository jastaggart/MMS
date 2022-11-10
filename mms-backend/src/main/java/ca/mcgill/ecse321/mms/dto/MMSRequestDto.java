package ca.mcgill.ecse321.mms.dto;
import java.sql.Time;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import ca.mcgill.ecse321.mms.model.MMS;

public class MMSRequestDto {

    @NotBlank
    @NotNull
    private Time openingHours;
    @NotBlank
    @NotNull
    private Time closingHours;
    @NotBlank
    @NotNull
    private int passFee;

    public Time getOpenHours() {
        return this.openingHours;
    }    

    public void setOpeningHours(Time openingTime) {
        this.openingHours = openingTime;
    }
     
    public Time getClosingHours() {
        return this.closingHours;
    }

    public void setClosingHours(Time closingTime) {
        this.closingHours = closingTime;
    }


    public int getPassFee() {
        return this.passFee;
    }

    public void setPassFee(int passFee) {
        this.passFee = passFee;
    }

    public MMS toModel() {
		MMS museum = new MMS();
		museum.setOpeningHours(this.openingHours);
        museum.setClosingHours(this.closingHours);
        museum.setPassFee(this.passFee);
		return museum;
	}

    
}
