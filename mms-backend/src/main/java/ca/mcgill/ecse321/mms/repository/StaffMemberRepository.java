package ca.mcgill.ecse321.mms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.StaffMember;

public interface StaffMemberRepository extends CrudRepository<StaffMember, String> {
	public StaffMember findStaffMemberByStaffMemberID(int id);
	public StaffMember findStaffMemberByUsername(String name);
	public List<StaffMember> findAll();
}