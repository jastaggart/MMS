package ca.mcgill.ecse321.mms.dto;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ca.mcgill.ecse321.mms.model.Pass;

public class PassRequestDto {

    @NotNull
    private int passID;

    @NotNull
    private Date passDate;

    @NotNull
    private VisitorResponseDto visitor;


    public int getPassId() {
        return this.passID;
    }

    public void setPassId(int passID) {
        this.passID = passID;
    }

    public Date getPassDate() {
        return this.passDate;
    }

    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }

    public VisitorRequestDto getPassPurchaser() {
        return this.visitor;
    }

    public void setPassPurchaser(VisitorResponseDto visitor) {
        this.visitor = visitor;
    }

    public Pass toModel() {
        Pass pass = new Pass();
        pass.setPassDate(this.passDate);
        pass.setPassID(this.passID);
        pass.setPassPurchaser(this.visitor.toModel());

        return pass;
    }

}