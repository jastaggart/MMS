package ca.mcgill.ecse321.mms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ca.mcgill.ecse321.mms.model.Visitor;

public class VisitorRequestDto {
    @NotNull
    @NotBlank
    private String username;
    
    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String email;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public Visitor toModel() {
        Visitor visitor = new Visitor();
        visitor.setUsername(username);
        visitor.setPassword(password);
        visitor.setEmail(email);
        return visitor;
    }
}
