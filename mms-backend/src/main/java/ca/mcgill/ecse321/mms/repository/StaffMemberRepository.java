package ca.mcgill.ecse321.mms.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.StaffMember;

public interface StaffMemberRepository extends CrudRepository<StaffMember, String> {
	public StaffMember findStaffMemberByStaffMemberID(int id);
	public StaffMember findStaffMemberByStaffMemberName(String name);
	public List<StaffMember> findAll();
}