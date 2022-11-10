package ca.mcgill.ecse321.mms.dto;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.mms.model.Pass;
import ca.mcgill.ecse321.mms.repository.VisitorRepository;

public class PassRequestDto {

    @NotNull
    private int passID;

    @NotNull
    private String passDate;

    @NotNull
    private int visitorID;

    public int getPassId() {
        return this.passID;
    }

    public void setPassId(int passID) {
        this.passID = passID;
    }

    public String getPassDate() {
        return this.passDate;
    }

    public void setPassDate(String passDate) {
        this.passDate = passDate;
    }

    public int getVisitorID() {
        return this.visitorID;
    }

    public void setVisitorID(int visitorID) {
        this.visitorID = visitorID;
    }

    public Pass toModel() throws ParseException {
        Pass pass = new Pass();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date = formatter.parse(this.passDate);
        pass.setPassDate(date);
        pass.setPassID(this.passID);
       // pass.setPassPurchaser(this.visitorID);

        return pass;
    }

}