package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.StaffMember;

public interface StaffMemberRepository extends CrudRepository<StaffMember, String> {
	
}
