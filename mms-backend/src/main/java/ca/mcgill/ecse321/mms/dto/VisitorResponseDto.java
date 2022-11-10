package ca.mcgill.ecse321.mms.dto;

import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.model.Visitor;

public class VisitorResponseDto {
    
    private String username;
    private String password;
    private String email;
    private int visitorId;
    private MMS museumManagementSystem;
    
    public VisitorResponseDto(Visitor visitor) {
        this.username = visitor.getUsername();
        this.password = visitor.getPassword();
        this.email = visitor.getEmail();
        this.visitorId = visitor.getVisitorID();
        this.museumManagementSystem = visitor.getMuseumManagementSystem();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public int getVisitorId() {
        return this.visitorId;
    }

    public MMS getMuseumManagementSystem() {
        return this.museumManagementSystem;
    }

}
