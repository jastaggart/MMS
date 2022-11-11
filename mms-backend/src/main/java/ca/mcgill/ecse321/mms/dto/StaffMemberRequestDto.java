package ca.mcgill.ecse321.mms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ca.mcgill.ecse321.mms.model.Employee;
import ca.mcgill.ecse321.mms.model.StaffMember;

public class StaffMemberRequestDto {
	
	@NotNull
	@NotBlank
	private String username;
	
	@NotNull
	@NotBlank
	private String password;
	
	@NotNull
	@NotBlank
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public StaffMember toModel() {
		StaffMember staffMember = new Employee();
		staffMember.setUsername(this.username);
		staffMember.setEmail(this.email);
		staffMember.setPassword(this.password);
		
		return staffMember;
	}
}
