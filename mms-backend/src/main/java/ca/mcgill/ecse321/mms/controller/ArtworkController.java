package ca.mcgill.ecse321.mms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.mms.model.Artwork;
import ca.mcgill.ecse321.mms.dto.ArtworkRequestDto;
import ca.mcgill.ecse321.mms.dto.ArtworkResponseDto;
import ca.mcgill.ecse321.mms.service.ArtworkService;

import java.util.List;
import java.util.ArrayList;

@RestController
public class ArtworkController {

    @Autowired
    ArtworkService artworkService;

    @GetMapping("/artwork/artworkID/{id}")
	public ResponseEntity<ArtworkResponseDto> getArtworkById(@PathVariable int id) {
		ArtworkResponseDto artwork = new ArtworkResponseDto(artworkService.getArtworkById(id));
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.OK);
	}

	@GetMapping("/artwork/name/{name}")
	public ResponseEntity<ArtworkResponseDto> getArtworkByName(@PathVariable String name) {
		ArtworkResponseDto artwork = new ArtworkResponseDto(artworkService.getArtworkByName(name));
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.OK);
	}

	@GetMapping("/artwork/artist/{artist}")
	public ResponseEntity<List<ArtworkResponseDto>> getArtworksByArtist(@PathVariable String artist) {
		List<ArtworkResponseDto> artworks = convListToDto(artworkService.getArtworksByArtist(artist));
		return new ResponseEntity<List<ArtworkResponseDto>>(artworks, HttpStatus.OK);
	}


	@GetMapping("/artwork/roomID/{roomID}") 
	public ResponseEntity<List<ArtworkResponseDto>> getArtworksByRoomID(@PathVariable int roomID) {
		List<ArtworkResponseDto> artworks = convListToDto(artworkService.getArtworksByRoomID(roomID));
		return new ResponseEntity<List<ArtworkResponseDto>>(artworks, HttpStatus.OK);
	}

    @GetMapping("/artworks")
	public ResponseEntity<List<ArtworkResponseDto>> getAllArtworks() {
		List<ArtworkResponseDto> artworks = convListToDto(artworkService.getAllArtworks());
		return new ResponseEntity<List<ArtworkResponseDto>>(artworks, HttpStatus.OK);
	}

    @PostMapping("/artwork")
	public ResponseEntity<ArtworkResponseDto> createArtwork(@Valid @RequestBody ArtworkRequestDto artworkRequest) {
		ArtworkResponseDto artwork = new ArtworkResponseDto(artworkService.createArtwork(artworkRequest.toModel()));
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.CREATED);
	}

	@PutMapping("/artworks/move/{artworkID}/{roomID}")
	public ResponseEntity<ArtworkResponseDto> moveArtworkToRoom(@PathVariable int artworkID, @PathVariable int roomID) {
		// Note: use roomID of storage if moving to storage
		ArtworkResponseDto artwork = new ArtworkResponseDto(artworkService.moveArtworkToRoom(artworkID, roomID)); 
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.OK);
	}

	private List<ArtworkResponseDto> convListToDto(List<Artwork> artworks) {
        List<ArtworkResponseDto> artworkResponses = new ArrayList<ArtworkResponseDto>();
        for (Artwork artwork : artworks) {
            artworkResponses.add(new ArtworkResponseDto(artwork));
        }
        return artworkResponses;
    }
	
}