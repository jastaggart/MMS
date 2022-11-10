package ca.mcgill.ecse321.mms.dto;
import java.util.Date;

import ca.mcgill.ecse321.mms.model.Pass;

public class PassResponseDto {
    public int passID;
    public Date passDate;
    public int visitorID;

    public PassResponseDto(Pass pass) {
        this.passID = pass.getPassID();
        this.passDate = pass.getPassDate();
        this.visitorID = pass.getPassPurchaser().getVisitorID();
    }

    public int getPassID() {
        return this.passID;
    }

    public Date getPassDate() {
        return this.passDate;
    }

    public int getVisitorID() {
        return this.visitorID;
    }

    public void setPassID(int passID) {
        this.passID = passID;
    }

    public void setPassDate(Date passDate) {
        this.passDate = passDate;
    }

    public void setVisitorID(int visitorID) {
        this.visitorID = visitorID;
    }



}