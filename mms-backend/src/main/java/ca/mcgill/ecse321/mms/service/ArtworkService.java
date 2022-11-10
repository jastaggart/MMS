package ca.mcgill.ecse321.mms.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.mms.model.Artwork;
import ca.mcgill.ecse321.mms.model.DisplayStatus;
import ca.mcgill.ecse321.mms.model.Room;
import ca.mcgill.ecse321.mms.repository.ArtworkRepository;
import ca.mcgill.ecse321.mms.repository.RoomRepository;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.dto.ArtworkRequestDto;
import ca.mcgill.ecse321.mms.dto.ArtworkResponseDto;
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
	public ArtworkResponseDto getArtworkById(int id) {
		Artwork artwork = artworkRepository.findArtworkByArtworkID(id);
		if (artwork == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Artwork with ID " + id + " not found.");
		}
		return new ArtworkResponseDto(artwork);
	}

    @Transactional
	public ArtworkResponseDto getArtworkByName(String name) {
		Artwork artwork = artworkRepository.findArtworkByName(name);
		if (artwork == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Artwork with name " + name + " not found.");
		}
		return new ArtworkResponseDto(artwork);
	}

    @Transactional
	public ArtworkResponseDto getArtworkByArtist(String artist) {
		Artwork artwork = artworkRepository.findArtworkByArtist(artist);
		if (artwork == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Artwork with artist " + artist + " not found.");
		}
		return new ArtworkResponseDto(artwork);
	}

    @Transactional
	public List<ArtworkResponseDto> getArtworksByRoomID(int roomID) {
		List<Artwork> artworks = artworkRepository.findArtworkByRoomRoomID(roomID);

		if (artworks == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No artworks in room with roomID " + roomID + ".");
		}

		List<ArtworkResponseDto> artworkResponses = new ArrayList<ArtworkResponseDto>();
        for (Artwork artwork : artworks) {
            artworkResponses.add(new ArtworkResponseDto(artwork));
        }
        return artworkResponses;
	}

    @Transactional
	public List<ArtworkResponseDto> getAllArtworks() {
		List<Artwork> artworks = artworkRepository.findAll();

        if (artworks == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No artworks to display.");
        }


        List<ArtworkResponseDto> artworkResponses = new ArrayList<ArtworkResponseDto>();
        for (Artwork artwork : artworks) {
            artworkResponses.add(new ArtworkResponseDto(artwork));
        }
        return artworkResponses;
	}

    @Transactional
    public ArtworkResponseDto createArtwork(ArtworkRequestDto artworkRequest) {
        Artwork artwork = new Artwork();
        artwork.setName(artworkRequest.getName());
        artwork.setArtist(artworkRequest.getArtist());

        artwork.setName(artworkRequest.getName());
        artwork.setArtist(artworkRequest.getArtist());

        artwork.setAvailableForLoan(true);
        artwork.setStatus(DisplayStatus.InStorage.name());

        artwork.setRoom(roomRepository.findRoomByRoomID(1)); // Set to storage room

        artwork.setMuseumManagementSystem(mmsRepository.findMMSByMuseumID(1));

        // Note: Loans remains empty 

        artwork = artworkRepository.save(artwork);
        return new ArtworkResponseDto(artwork);
    }

     @Transactional
    public ArtworkResponseDto moveArtworkToRoom(int artworkID, int roomID) {
        Artwork artwork = artworkRepository.findArtworkByArtworkID(artworkID);

        if (artwork == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Artwork with ID " + artworkID + " not found.");
		}

        artwork.setRoom(roomRepository.findRoomByRoomID(roomID));

        if (roomID == 1) { // moving to storage
            artwork.setStatus(DisplayStatus.InStorage.name());
        }
        else { // moving to a display room
            artwork.setStatus(DisplayStatus.OnDisplay.name());
        }

        return new ArtworkResponseDto(artwork);
    }

}