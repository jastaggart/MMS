package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.Pass;

public interface PassRepository extends CrudRepository<Pass, String> {
	public Pass findPassByPassID(int id);
}