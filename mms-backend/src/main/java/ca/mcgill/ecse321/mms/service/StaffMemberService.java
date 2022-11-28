package ca.mcgill.ecse321.mms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.mms.exception.MMSException;
import ca.mcgill.ecse321.mms.model.StaffMember;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.StaffMemberRepository;

@Service
public class StaffMemberService {
	@Autowired(required = true)
    StaffMemberRepository staffMemberRepository;

    @Autowired(required = true)
    MMSRepository mmsRepository;
    
    @Transactional
    public StaffMember getStaffMemberById(int id) {
    	StaffMember staffMember = staffMemberRepository.findStaffMemberByStaffMemberID(id);
    	if(staffMember == null) {
    		throw new MMSException(HttpStatus.NOT_FOUND, "No staff member with the id " + id + " was found.");
    	}
    	
    	return staffMember;
    }
    
    @Transactional
    public StaffMember getStaffMemberByName(String name) {
    	StaffMember staffMember = staffMemberRepository.findStaffMemberByUsername(name);
    	if(staffMember == null) {
    		throw new MMSException(HttpStatus.NOT_FOUND, "No staffmember with the name " +name + " was found.");
    	}
    	
    	return staffMember;
    }
    
    @Transactional
    public StaffMember createStaffMember(StaffMember staffMember) {
    	StaffMember currentStaffMember =staffMemberRepository.findStaffMemberByUsername(staffMember.getUsername());
    	
    	if(currentStaffMember!=null) {
            throw new MMSException(HttpStatus.CONFLICT, "Staff member already exists.");
    	}
    	staffMember = staffMemberRepository.save(staffMember);
    	staffMember.setMuseumManagementSystem(mmsRepository.findMMSByMuseumID(1));
    	return staffMember;
    }
    
    @Transactional
    public StaffMember deleteStaffMember(int staffMemberId) {
    	StaffMember staffMember =staffMemberRepository.findStaffMemberByStaffMemberID(staffMemberId);
    	if(staffMember==null) {
    		throw new MMSException(HttpStatus.NOT_FOUND, "Staff member not found.");
        }
    	staffMemberRepository.delete(staffMember);
    	return staffMember;
    }
    
    @Transactional
    public List<StaffMember> getAllStaffMembers(){
    	List<StaffMember> staffMemberList = staffMemberRepository.findAll();
    	if(staffMemberList.size()==0) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No staff members found.");
    	}
    	return staffMemberList;
    }
    
    @Transactional
    public StaffMember modifyStaffMember(int staffMemberId, String username, String email, String password) {
    	StaffMember staffMember = staffMemberRepository.findStaffMemberByStaffMemberID(staffMemberId);
    	if(staffMember == null) {
    		throw new MMSException(HttpStatus.NOT_FOUND, "No staffmember with the id " +staffMemberId + " was found.");
    	}
    	staffMember.setUsername(username);
    	staffMember.setEmail(email);
    	staffMember.setPassword(password);
    	return staffMember;
    }

    public StaffMember getStaffMemberByEmail(String email) {
        StaffMember staffMember = staffMemberRepository.findStaffMemberByEmail(email);
        if (staffMember == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "Staff member account with this email was not found.");  
        }
        return staffMember;
    }
}
