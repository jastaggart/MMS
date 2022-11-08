package ca.mcgill.ecse321.mms.dto;

import ca.mcgill.ecse321.mms.model.Artwork;
import ca.mcgill.ecse321.mms.model.Loan;
import ca.mcgill.ecse321.mms.model.Room;
import ca.mcgill.ecse321.mms.model.MMS;

public class ArtworkResponseDto {
    private int artworkID;
    private boolean availableForLoan;
    private String status;
    private String name;
    private String artist;
    private Room room;
    private MMS museumManagementSystem;

    public ArtworkResponseDto(Artwork artwork) {
        this.artworkID = artwork.getArtworkID();
        this.availableForLoan = artwork.getAvailableForLoan();
        this.status = artwork.getStatus();
        this.artist = artwork.getArtist();
        this.room = artwork.getRoom();
        this.museumManagementSystem = artwork.getMuseumManagementSystem();
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

    public Room getRoom()
    {
        return room;
    }

    public MMS getMuseumManagementSystem()
    {
        return museumManagementSystem;
    }

}