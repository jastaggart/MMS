package ca.mcgill.ecse321.mms.dto;

import ca.mcgill.ecse321.mms.model.Artwork;
import ca.mcgill.ecse321.mms.dto.MMSResponseDto;

public class ArtworkResponseDto {
    private int artworkID;
    private boolean availableForLoan;
    private String status;
    private String name;
    private String artist;
    private int roomRoomID;
    private MMSResponseDto museumManagementSystem;

    public ArtworkResponseDto(Artwork artwork) {
        this.artworkID = artwork.getArtworkID();
        this.availableForLoan = artwork.getAvailableForLoan();
        this.status = artwork.getStatus();
        this.name = artwork.getName();
        this.artist = artwork.getArtist();
        this.roomRoomID = artwork.getRoom().getRoomID();
        this.museumManagementSystem = new MMSResponseDto(artwork.getMuseumManagementSystem());
    }

    public int getArtworkID()
    {
        return artworkID;
    }

    public boolean getAvailableForLoan()
    {
        return availableForLoan;
    }

    public String getStatus()
    {
        return status;
    }

    public String getName()
    {
        return name;
    }

    public String getArtist()
    {
        return artist;
    }

    public int getRoomRoomID()
    {
        return roomRoomID;
    }

    public MMSResponseDto getMuseumManagementSystem()
    {
        return museumManagementSystem;
    }

}