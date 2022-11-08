package ca.mcgill.ecse321.mms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ca.mcgill.ecse321.mms.model.Artwork;

public class ArtworkRequestDto {
	@NotNull
	@NotBlank
	private String name;

    @NotNull
	@NotBlank
	private String artist;
	
	public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    public Artwork toModel() {
		Artwork artwork = new Artwork();
        artwork.setName(this.name);
        artwork.setArtist(this.artist);
		return artwork;
	}
}