package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.Artwork;
import ca.mcgill.ecse321.mms.model.Room;

import java.util.List;

public interface ArtworkRepository extends CrudRepository<Artwork, String> {
	public Artwork findArtworkByArtworkID(int id);
	public Artwork findArtworkByName(String name);
	public List<Artwork> findAllByArtist(String artist);
	public List<Artwork> findAllByRoomRoomID(int roomID);
	public List<Artwork> findAll();
}