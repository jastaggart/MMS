package ca.mcgill.ecse321.mms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.mms.dto.StaffMemberResponseDto;
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
    		//throw exception
    	}
    	
    	return new StaffMemberResponseDto(staffMember);
    }
    
    @Transactional
    public StaffMemberResponseDto findStaffMemberByName(String name) {
    	StaffMember staffMember = staffMemberRepository.findStaffMemberByStaffMemberName(name);
    	if(staffMember == null) {
    		//throw exception
    	}
    	
    	return new StaffMemberResponseDto(staffMember);
    }
    
    @Transactional
    public StaffMemberResponseDto createStaffMember(StaffMember staffMember) {
    	StaffMember currentStaffMember = staffMemberRepository.findStaffMemberByStaffMemberID(staffMember.getStaffMemberID());
    	
    	if(currentStaffMember!=null) {
    		//--> staffMember exists
    		//throw exception
    	}
    	staffMemberRepository.save(currentStaffMember);
    	return new StaffMemberResponseDto(currentStaffMember);
    }
    
    @Transactional
    public List<StaffMemberResponseDto> findAll(){
    	List<StaffMember> staffMemberList = (List<StaffMember>) staffMemberRepository.findAll();
    	List<StaffMemberResponseDto> staffMemberResponseDtoList = new ArrayList<StaffMemberResponseDto>();
    	if(staffMemberList==null) {
    		//list is empty
    		//throw exception
    	}
    	
    	for(StaffMember staffMember: staffMemberList) {
    		staffMemberResponseDtoList.add(new StaffMemberResponseDto(staffMember));
    	}
    	
    	return staffMemberResponseDtoList;
    }
}
