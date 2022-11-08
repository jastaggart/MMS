package ca.mcgill.ecse321.mms.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.mms.model.Artwork;
import ca.mcgill.ecse321.mms.repository.ArtworkRepository;
import ca.mcgill.ecse321.mms.repository.MMSRepository;

@Service
public class ArtworkService {

    @Autowired(required = true)
    ArtworkRepository artworkRepository;

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
    // set loan fee?

    
}