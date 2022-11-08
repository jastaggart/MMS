package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.Artwork;

import java.util.List;

public interface ArtworkRepository extends CrudRepository<Artwork, String> {
	public Artwork findArtworkByArtworkID(int id);
	public Artwork findArtworkByName(String name);
	public List<Artwork> findAll();
}