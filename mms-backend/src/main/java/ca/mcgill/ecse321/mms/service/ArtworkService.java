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

       
      
    }

    
}