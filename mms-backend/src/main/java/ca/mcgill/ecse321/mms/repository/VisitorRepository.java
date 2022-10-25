package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.Visitor;

public interface VisitorRepository extends CrudRepository<Visitor, String> {
	
}