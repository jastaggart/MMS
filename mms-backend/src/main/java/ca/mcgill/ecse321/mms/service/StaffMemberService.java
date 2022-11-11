package ca.mcgill.ecse321.mms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.mms.dto.StaffMemberResponseDto;
import ca.mcgill.ecse321.mms.exception.MMSException;
import ca.mcgill.ecse321.mms.model.StaffMember;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.StaffMemberRepository;

public class StaffMemberService {
	@Autowired(required = true)
    StaffMemberRepository staffMemberRepository;

    @Autowired(required = true)
    MMSRepository mmsRepository;
    
    @Transactional
    public StaffMemberResponseDto findStaffMemberById(int id) {
    	StaffMember staffMember = staffMemberRepository.findStaffMemberByStaffMemberID(id);
    	if(staffMember == null) {
    		throw new MMSException(HttpStatus.NOT_FOUND, "No staffmember with the id " + id + " was found.");
    	}
    	
    	return new StaffMemberResponseDto(staffMember);
    }
    
    @Transactional
    public StaffMemberResponseDto findStaffMemberByName(String name) {
    	StaffMember staffMember = staffMemberRepository.findStaffMemberByStaffMemberName(name);
    	if(staffMember == null) {
    		throw new MMSException(HttpStatus.NOT_FOUND, "No staffmember with the name " +name + " was found.");
    	}
    	
    	return new StaffMemberResponseDto(staffMember);
    }
    
    @Transactional
    public StaffMemberResponseDto createStaffMember(StaffMember staffMember) {
    	StaffMember currentStaffMember = staffMemberRepository.findStaffMemberByStaffMemberID(staffMember.getStaffMemberID());
    	
    	if(currentStaffMember!=null) {

            throw new MMSException(HttpStatus.CONFLICT, "Staff member already exists.");
    	}
    	staffMemberRepository.save(currentStaffMember);
    	return new StaffMemberResponseDto(currentStaffMember);
    }
    
    @Transactional
    public List<StaffMemberResponseDto> findAll(){
    	List<StaffMember> staffMemberList = (List<StaffMember>) staffMemberRepository.findAll();
    	List<StaffMemberResponseDto> staffMemberResponseDtoList = new ArrayList<StaffMemberResponseDto>();
    	if(staffMemberList==null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No staff members found.");
    	}
    	
    	for(StaffMember staffMember: staffMemberList) {
    		staffMemberResponseDtoList.add(new StaffMemberResponseDto(staffMember));
    	}
    	
    	return staffMemberResponseDtoList;
    }
}
