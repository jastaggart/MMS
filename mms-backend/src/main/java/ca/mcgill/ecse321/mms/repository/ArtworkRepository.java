package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.Artwork;

public interface ArtworkRepository extends CrudRepository<Artwork, String> {
	
}