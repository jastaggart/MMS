package ca.mcgill.ecse321.mms.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.mms.model.Artwork;
import ca.mcgill.ecse321.mms.model.DisplayStatus;
import ca.mcgill.ecse321.mms.repository.ArtworkRepository;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.dto.ArtworkRequestDto;
import ca.mcgill.ecse321.mms.dto.ArtworkResponseDto;

@Service
public class ArtworkService {

    @Autowired(required = true)
    ArtworkRepository artworkRepository;

    @Transactional
	public ArtworkResponseDto getArtworkById(int id) {
		Artwork artwork = artworkRepository.findArtworkByArtworkID(id);
		if (artwork == null) {
			throw new RuntimeException("Artwork not found.");
		}
		return new ArtworkResponseDto(artwork);
	}

    @Transactional
	public List<ArtworkResponseDto> getAllArtworks() {
		List<Artwork> artworks = artworkRepository.findAll();

        if (artworks == null) {
            throw new RuntimeException("No artworks to display.");
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

        // TODO set room, mms
        // loans remains empty

        artwork = artworkRepository.save(artwork);
        return new ArtworkResponseDto(artwork);
    }

    // TODO 
    // move artworks between rooms

    
}