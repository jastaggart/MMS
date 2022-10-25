package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.Shift;

public interface ShiftRepository extends CrudRepository<Shift, String> {
	
}