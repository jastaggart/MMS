package ca.mcgill.ecse321.mms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@CrossOrigin(origins = "http://127.0.0.1:8087")
public class ArtworkController {

    @Autowired
    ArtworkService artworkService;

	/**
     * Gets an Artwork by artworkID after HTTP request and puts into the HTTP response
     * 
     * @param id - artworkID of the Artwork to get
     * @return - The fetched Artwork
     */
    @GetMapping("/artwork/artworkID/{id}")
	public ResponseEntity<ArtworkResponseDto> getArtworkById(@PathVariable int id) {
		ArtworkResponseDto artwork = new ArtworkResponseDto(artworkService.getArtworkById(id));
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.OK);
	}

	/**
     * Gets an Artwork by name after HTTP request and puts into the HTTP response
     * 
     * @param name - name of the Artwork to get
     * @return - The fetched Artwork
     */
	@GetMapping("/artwork/name/{name}")
	public ResponseEntity<ArtworkResponseDto> getArtworkByName(@PathVariable String name) {
		ArtworkResponseDto artwork = new ArtworkResponseDto(artworkService.getArtworkByName(name));
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.OK);
	}

	/**
     * Gets a list of Artworks by artist after HTTP request and puts into the HTTP response
     * 
     * @param artist - artist of the Artworks to get
     * @return - The fetched list of Artworks
     */
	@GetMapping("/artwork/artist/{artist}")
	public ResponseEntity<List<ArtworkResponseDto>> getArtworksByArtist(@PathVariable String artist) {
		List<ArtworkResponseDto> artworks = convListToDto(artworkService.getArtworksByArtist(artist));
		return new ResponseEntity<List<ArtworkResponseDto>>(artworks, HttpStatus.OK);
	}

	/**
     * Gets a list of Artworks by roomID after HTTP request and puts into the HTTP response
     * 
     * @param roomID - roomID of the Artworks to get
     * @return - The fetched list of Artworks
     */
	@GetMapping("/artwork/roomID/{roomID}") 
	public ResponseEntity<List<ArtworkResponseDto>> getArtworksByRoomID(@PathVariable int roomID) {
		List<ArtworkResponseDto> artworks = convListToDto(artworkService.getArtworksByRoomID(roomID));
		return new ResponseEntity<List<ArtworkResponseDto>>(artworks, HttpStatus.OK);
	}

	/**
     * Gets a list of all Artworks and puts into the HTTP response
     * 
     * @return - The fetched list of Artworks
     */
    @GetMapping("/artworks")
	public ResponseEntity<List<ArtworkResponseDto>> getAllArtworks() {
		List<ArtworkResponseDto> artworks = convListToDto(artworkService.getAllArtworks());
		return new ResponseEntity<List<ArtworkResponseDto>>(artworks, HttpStatus.OK);
	}

	/**
     * Creates an Artwork using Dto data after HTTP request and puts it into HTTP response
     * 
     * @param artworkRequest - The ArtworkRequestDto data
     * @return - The created Artwork as Dto
     */
    @PostMapping("/artwork")
	public ResponseEntity<ArtworkResponseDto> createArtwork(@Valid @RequestBody ArtworkRequestDto artworkRequest) {
		ArtworkResponseDto artwork = new ArtworkResponseDto(artworkService.createArtwork(artworkRequest.toModel()));
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.CREATED);
	}

	/**
     * Moves a specified Artwork into a specified Room found the HTTP request 
     * 
     * @param artworkID - artworkID of the Artwork to move
	 * @param roomID - roomID of the Room to move the Artwork to 
     * @return - The updated Artwork
     */
	@PutMapping("/artworks/move/{artworkID}/{roomID}")
	public ResponseEntity<ArtworkResponseDto> moveArtworkToRoom(@PathVariable int artworkID, @PathVariable int roomID) {
		// Note: use roomID of storage if moving to storage
		ArtworkResponseDto artwork = new ArtworkResponseDto(artworkService.moveArtworkToRoom(artworkID, roomID)); 
		return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.OK);
	}

     /**
     * Deletes a specified Artwork
     * 
     * @return - deleted Artwork
     */
     @DeleteMapping("/artwork/delete/{artworkID}")
     public ResponseEntity<ArtworkResponseDto> deleteArtworkByID(@PathVariable int artworkID) {
          ArtworkResponseDto artwork = new ArtworkResponseDto(artworkService.deleteArtworkByArtworkID(artworkID));
          return new ResponseEntity<ArtworkResponseDto>(artwork, HttpStatus.OK);
     }

	/**
     * Converts a list of Artworks to a list of ArtworkResponseDtos 
     * Note: This is a private helper method
	 *
     * @param artworks - list of Artworks
     * @return - list of ArtworkResponseDtos
     */
	private List<ArtworkResponseDto> convListToDto(List<Artwork> artworks) {
        List<ArtworkResponseDto> artworkResponses = new ArrayList<ArtworkResponseDto>();
        for (Artwork artwork : artworks) {
            artworkResponses.add(new ArtworkResponseDto(artwork));
        }
        return artworkResponses;
    }
	
}