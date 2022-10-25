package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.Storage;

public interface StorageRepository extends CrudRepository<Storage, String> {
	
}