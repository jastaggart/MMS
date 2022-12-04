package ca.mcgill.ecse321.mms.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.mms.model.Artwork;
import ca.mcgill.ecse321.mms.model.DisplayStatus;
import ca.mcgill.ecse321.mms.model.Room;
import ca.mcgill.ecse321.mms.model.DisplayRoom;
import ca.mcgill.ecse321.mms.repository.ArtworkRepository;
import ca.mcgill.ecse321.mms.repository.RoomRepository;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.LoanRepository;
import ca.mcgill.ecse321.mms.exception.MMSException;

@Service
public class ArtworkService {

    @Autowired(required = true)
    ArtworkRepository artworkRepository;

    @Autowired(required = true)
    RoomRepository roomRepository;

    @Autowired(required = true)
    MMSRepository mmsRepository;

    @Autowired(required = true)
    LoanRepository loanRepository;

    /**
     * Gets an Artwork by its artworkID attribute
     * 
     * @param id - Artwork artworkID
     * @return - The fetched Artwork
     */
    @Transactional
	public Artwork getArtworkById(int id) {
		Artwork artwork = artworkRepository.findArtworkByArtworkID(id);
		if (artwork == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Artwork with ID " + id + " not found.");
		}
		return artwork;
	}

    /**
     * Gets an Artwork by its name attribute
     * 
     * @param name - Artwork name
     * @return - The fetched Artwork
     */
    @Transactional
	public Artwork getArtworkByName(String name) {
		Artwork artwork = artworkRepository.findArtworkByName(name);
		if (artwork == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Artwork with name " + name + " not found.");
		}
		return artwork;
	}

    /**
     * Gets a list of Artworks by their artist attribute
     * 
     * @param artist - Artworks' artist
     * @return - The fetched list of Artworks
     */
    @Transactional
	public List<Artwork> getArtworksByArtist(String artist) {
		List<Artwork> artworks = artworkRepository.findAllByArtist(artist);

		if (artworks.isEmpty()) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No artworks by " + artist + " found.");
		}

		return artworks;
	}

    /**
     * Gets a list of Artworks by their roomID attribute
     * 
     * @param roomID - Artworks' roomID
     * @return - The fetched list of Artworks
     */
    @Transactional
	public List<Artwork> getArtworksByRoomID(int roomID) {
		List<Artwork> artworks = artworkRepository.findAllByRoomRoomID(roomID);

		if (artworks.isEmpty()) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No artworks in room with roomID " + roomID + ".");
		}

		return artworks;
	}

    /**
     * Gets a list of all Artworks in the artworkRepository
     * 
     * @return - The fetched list of Artworks
     */
    @Transactional
	public List<Artwork> getAllArtworks() {
		List<Artwork> artworks = artworkRepository.findAll();

        if (artworks.isEmpty()) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No artworks to display.");
        }

        return artworks;
	}

    /**
     * Creates an Artwork using specific Artwork data and saves it in the artworkRepository
     * @param artwork - Artwork object to create and save in the artworkRepository
     * @return - The created Artwork
     */
    @Transactional
    public Artwork createArtwork(Artwork artwork) {
        artwork.setAvailableForLoan(true);
        artwork.setStatus(DisplayStatus.InStorage.name());

        artwork.setRoom(roomRepository.findRoomByRoomID(1)); // Set to storage room

        artwork.setMuseumManagementSystem(mmsRepository.findMMSByMuseumID(1));
        // Note: Loans remains empty 

        artwork = artworkRepository.save(artwork);
        return artwork;
    }

    /**
     * Moves an Artwork to a specified room
     * 
     * @param artworkID - artworkID of Artwork to move
     * @param roomID - roomID of room to move the Artwork to
     * @return - The updated Artwork
     */
    @Transactional
    public Artwork moveArtworkToRoom(int artworkID, int roomID) {
        Artwork artwork = artworkRepository.findArtworkByArtworkID(artworkID);
        if (artwork == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Artwork with ID " + artworkID + " not found.");
		}
        
        Room destRoom = roomRepository.findRoomByRoomID(roomID);
        if (destRoom == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "Room with ID " + roomID + " not found.");
        }

        if (roomID == 1) { // moving to storage
            artwork.setStatus(DisplayStatus.InStorage.name());
        }
        else { // moving to a display room
            // If the display room is not full (number of artworks in display room < max capacity)
            if (((DisplayRoom) destRoom).getArtworks().size() < ((DisplayRoom) destRoom).getMaximumCapacity()) {
                artwork.setStatus(DisplayStatus.OnDisplay.name());
            }
            else {
                throw new MMSException(HttpStatus.CONFLICT, "Display room is full.");
            }
        }

        artwork.setRoom(destRoom);
        return artwork;
    }

    /**
     * Deletes an Artwork
     * 
     * @param artworkID - artworkID of Artwork to delete
     * @return - deleted Artwork
     */
    @Transactional
    public Artwork deleteArtworkByArtworkID(int artworkID) {
        Artwork artwork = artworkRepository.findArtworkByArtworkID(artworkID);
        if (artwork == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Artwork with ID " + artworkID + " not found.");
		}
        
        // Remove artwork to delete from its room
        Room room = artwork.getRoom();
        room.removeArtwork(artwork);

        artworkRepository.deleteArtworkByArtworkID(artworkID);

        return artwork;
    }

}