package ca.mcgill.ecse321.mms.dto;
import java.sql.Time;

import ca.mcgill.ecse321.mms.model.MMS;


public class MMSResponseDto {

  private int museumID;
  private Time openingHours;
  private Time closingHours;
  private int passFee;

  public MMSResponseDto(MMS museum) {
    this.museumID = museum.getMuseumID();
    this.openingHours = museum.getOpeningHours();
    this.closingHours= museum.getClosingHours();
    this.passFee = museum.getPassFee();
  }

  public int getID() {
    return this.museumID;
  }

  public Time getOpenHours() {
    return this.openingHours;
  }    

  public Time getClosingHours() {
    return this.closingHours;
  }

  public int getPassFee() {
    return this.passFee;
  }

}
