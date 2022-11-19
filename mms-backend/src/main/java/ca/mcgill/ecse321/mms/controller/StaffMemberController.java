package ca.mcgill.ecse321.mms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.mms.dto.StaffMemberRequestDto;
import ca.mcgill.ecse321.mms.dto.StaffMemberResponseDto;
import ca.mcgill.ecse321.mms.dto.VisitorResponseDto;
import ca.mcgill.ecse321.mms.model.StaffMember;
import ca.mcgill.ecse321.mms.service.StaffMemberService;

@RestController
public class StaffMemberController {
	@Autowired
	StaffMemberService staffMemberService;

	/**
	 * Get all staff members after HTTP request and puts into the HTTP response
	 * 
	 * @return - All staff members as a list of response entity Dtos
	 */
	@GetMapping("/staffMembers")
	public ResponseEntity<List<StaffMemberResponseDto>> getAllStaffMembers() {
		List<StaffMemberResponseDto> staffMemberResponseDto = convListToDto(staffMemberService.getAllStaffMembers());
		return new ResponseEntity<List<StaffMemberResponseDto>>(staffMemberResponseDto, HttpStatus.OK);
	}

	/**
	 * Gets a staff member by staffMemberID after HTTP request and puts into the
	 * HTTP response
	 * 
	 * @param id - staffMemberID of the StaffMember
	 * @return - The fetched staff member
	 */
	@GetMapping("/staffMember/staffMemberId/{id}")
	public ResponseEntity<StaffMemberResponseDto> getStaffMemberById(@PathVariable int id) {
		StaffMemberResponseDto staffMember = new StaffMemberResponseDto(staffMemberService.getStaffMemberById(id));
		return new ResponseEntity<StaffMemberResponseDto>(staffMember, HttpStatus.OK);
	}

	/**
	 * Gets a staff member by staffMemberName after HTTP request and puts into the
	 * HTTP response
	 * 
	 * @param name - staffMemberName of the StaffMember
	 * @return - The fetched staff member
	 */
	@GetMapping("/staffMember/staffMemberName/{name}")
	public ResponseEntity<StaffMemberResponseDto> getStaffMemberByName(@PathVariable String name) {
		StaffMemberResponseDto staffMember = new StaffMemberResponseDto(staffMemberService.getStaffMemberByName(name));
		return new ResponseEntity<StaffMemberResponseDto>(staffMember, HttpStatus.OK);
	}

	/**
	 * Creates a staff member using Dto data after HTTP request and puts it into
	 * HTTP response
	 * 
	 * @param staffMemberRequest - The staffMemberRequestDto data
	 * @return - The created staffMember as a response Dto
	 */
	@PostMapping("/staffMember")
	public ResponseEntity<StaffMemberResponseDto> createStaffMember(
			@Valid @RequestBody StaffMemberRequestDto staffMemberRequestDtoRequest) {
		StaffMember staffMember = staffMemberRequestDtoRequest.toModel();
		StaffMember createdStaffMember = staffMemberService.createStaffMember(staffMember);
		StaffMemberResponseDto response = new StaffMemberResponseDto(createdStaffMember);
		return new ResponseEntity<StaffMemberResponseDto>(response, HttpStatus.CREATED);
	}

	/**
	 * modify a staff member data in the persistence layer
	 * 
	 * @param staffMemberId - The id used to identify a particular staff member*
	 * @param username      - The username of the employee (modified or new)
	 * @param email         - The email of the employee (modified or new)
	 * @param password      - The password of the employee (modified or new)
	 * @return - The modified version of staffMember as a response Dto
	 */
	@PutMapping("/staffMember/modify/{staffMemberID}")
	public ResponseEntity<StaffMemberResponseDto> modifyStaffMember(@PathVariable int staffMemberId, String username,String email, String password) {
		StaffMember staffMember = staffMemberService.modifyStaffMember(staffMemberId, username, email, password);
		StaffMemberResponseDto response = new StaffMemberResponseDto(staffMember);
		return new ResponseEntity<StaffMemberResponseDto>(response, HttpStatus.OK);
	}

	/**
	 * Gets a staff member by staffMemberID then delete them from the persistence layer
	 * 
	 * @param id - staffMemberID of the StaffMember
	 * @return - The deleted staff member
	 */
	@DeleteMapping("/staffMember/delete/{id}")
	public ResponseEntity<StaffMemberResponseDto> deleteStaffMember(@PathVariable int staffMemberId) {
		StaffMember deletedStaffMember = staffMemberService.deleteStaffMember(staffMemberId);
		return new ResponseEntity<StaffMemberResponseDto>(new StaffMemberResponseDto(deletedStaffMember), HttpStatus.OK);
	}

	private List<StaffMemberResponseDto> convListToDto(List<StaffMember> staffMembers) {
		List<StaffMemberResponseDto> staffMemberResponses = new ArrayList<StaffMemberResponseDto>();
		for (StaffMember staffMember : staffMembers) {
			staffMemberResponses.add(new StaffMemberResponseDto(staffMember));
		}
		return staffMemberResponses;
	}

}
