package ca.mcgill.ecse321.mms.dto;

import java.util.List;

import ca.mcgill.ecse321.mms.model.Loan;
import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.model.StaffMember;

public class StaffMemberResponseDto {
	
	private int staffMemberID;
    private MMS mms;
	private String username;
	private String email;
	private String password;
	private List<Loan> loan;
	
	public StaffMemberResponseDto(StaffMember staffMember) {
		this.staffMemberID = staffMember.getStaffMemberID();
		this.mms = staffMember.getMuseumManagementSystem();
		this.username = staffMember.getUsername();
		this.email =  staffMember.getEmail();
		this.password = staffMember.getPassword();
	}

	public int getStaffMemberID() {
		return staffMemberID;
	}

	public void setStaffMemberID(int staffMemberID) {
		this.staffMemberID = staffMemberID;
	}

	public MMS getMms() {
		return mms;
	}

	public void setMms(MMS mms) {
		this.mms = mms;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Loan> getLoan() {
		return loan;
	}

	public void setLoan(List<Loan> loan) {
		this.loan = loan;
	}
	
	
}
