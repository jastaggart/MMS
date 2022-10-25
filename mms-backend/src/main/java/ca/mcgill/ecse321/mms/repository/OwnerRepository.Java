package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, String> {
	
}