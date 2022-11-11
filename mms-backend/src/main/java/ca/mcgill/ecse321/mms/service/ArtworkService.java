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
import ca.mcgill.ecse321.mms.model.Storage;
import ca.mcgill.ecse321.mms.repository.ArtworkRepository;
import ca.mcgill.ecse321.mms.repository.RoomRepository;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.exception.MMSException;

@Service
public class ArtworkService {

    @Autowired(required = true)
    ArtworkRepository artworkRepository;

    @Autowired(required = true)
    RoomRepository roomRepository;

    @Autowired(required = true)
    MMSRepository mmsRepository;

    @Transactional
	public Artwork getArtworkById(int id) {
		Artwork artwork = artworkRepository.findArtworkByArtworkID(id);
		if (artwork == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Artwork with ID " + id + " not found.");
		}
		return artwork;
	}

    @Transactional
	public Artwork getArtworkByName(String name) {
		Artwork artwork = artworkRepository.findArtworkByName(name);
		if (artwork == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Artwork with name " + name + " not found.");
		}
		return artwork;
	}

    @Transactional
	public List<Artwork> getArtworksByArtist(String artist) {
		List<Artwork> artworks = artworkRepository.findAllByArtist(artist);

		if (artworks == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No artworks by " + artist + " found.");
		}

		return artworks;
	}

    @Transactional
	public List<Artwork> getArtworksByRoomID(int roomID) {
		List<Artwork> artworks = artworkRepository.findAllByRoomRoomID(roomID);

		if (artworks == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No artworks in room with roomID " + roomID + ".");
		}

		return artworks;
	}

    @Transactional
	public List<Artwork> getAllArtworks() {
		List<Artwork> artworks = artworkRepository.findAll();

        if (artworks == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No artworks to display.");
        }

        return artworks;
	}

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

}