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

import ca.mcgill.ecse321.mms.dto.ArtworkRequestDto;
import ca.mcgill.ecse321.mms.dto.ArtworkResponseDto;
import ca.mcgill.ecse321.mms.service.ArtworkService;

import java.util.List;

@RestController
public class ArtworkController {

    @Autowired
    ArtworkService artworkService;

    @GetMapping("/artwork/{id}")
	public ResponseEntity<ArtworkResponseDto> getArtworkById(@PathVariable int id) {
		ArtworkResponseDto artwork = artworkService.getArtworkById(id);
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.OK);
	}

	@GetMapping("/artwork/{name}")
	public ResponseEntity<ArtworkResponseDto> getArtworkByName(@PathVariable String name) {
		ArtworkResponseDto artwork = artworkService.getArtworkByName(name);
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.OK);
	}

	@GetMapping("/artwork/{artist}")
	public ResponseEntity<ArtworkResponseDto> getArtworkByArtist(@PathVariable String artist) {
		ArtworkResponseDto artwork = artworkService.getArtworkByArtist(artist);
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.OK);
	}


	@GetMapping("/artwork/{roomID}") 
	public ResponseEntity<List<ArtworkResponseDto>> getArtworksByRoomID(@PathVariable int roomID) {
		List<ArtworkResponseDto> artworks = artworkService.getArtworksByRoomID(roomID);
		return new ResponseEntity<List<ArtworkResponseDto>>(artworks, HttpStatus.OK);
	}

    @GetMapping("/artworks")
	public ResponseEntity<List<ArtworkResponseDto>> getAllArtworks() {
		List<ArtworkResponseDto> artworks = artworkService.getAllArtworks();
		return new ResponseEntity<List<ArtworkResponseDto>>(artworks, HttpStatus.OK);
	}

    @PostMapping("/artwork")
	public ResponseEntity<ArtworkResponseDto> createArtwork(@Valid @RequestBody ArtworkRequestDto artworkRequest) {
		ArtworkResponseDto artwork = artworkService.createArtwork(artworkRequest);
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.CREATED);
	}

	@PutMapping("/artworks")
	public ResponseEntity<ArtworkResponseDto> moveArtworkToRoom(@RequestParam int artworkID, @RequestParam int roomID) {
		// Note: use roomID of storage if moving to storage
		ArtworkResponseDto artwork = artworkService.moveArtworkToRoom(artworkID, roomID); 
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.OK);
	}

}