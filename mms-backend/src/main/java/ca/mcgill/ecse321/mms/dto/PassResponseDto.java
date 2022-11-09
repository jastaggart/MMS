package ca.mcgill.ecse321.mms.dto;
import java.sql.Date;

import ca.mcgill.ecse321.mms.model.Pass;

public class PassResponseDto {
    private int passID;
    private Date passDate;
    private VisitorResponseDto visitor;

    public PassResponseDto(Pass pass) {
        this.passID = pass.getPassID();
        this.passDate = pass.getPassDate();
        this.visitor = new VisitorResponseDto(pass.getPassPurchaser());
    }

    public int getPassID() {
        return this.passID;
    }

    public Date getPassDate() {
        return this.passDate;
    }

    public VisitorResponseDto getVisitor() {
        return this.visitor;
    }



}