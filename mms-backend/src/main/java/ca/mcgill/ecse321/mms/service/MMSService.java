package ca.mcgill.ecse321.mms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.mms.exception.MMSException;
import ca.mcgill.ecse321.mms.model.MMS;
import ca.mcgill.ecse321.mms.repository.MMSRepository;


@Service
public class MMSService {


    @Autowired(required = true)
    MMSRepository mmsRepository;


    /**
     * Return the museum object from the mmsRepository. There will always be only one museum in the repository and it has id = 1
     * 
     * @return - Fetched museum
     */
    public MMS getMuseum() {

        MMS museum = mmsRepository.findMMSByMuseumID(1);
        return museum;
    }



    /**
     * 
     * Updates the sole museum object in the mmsRepository
     * 
     * @param openingTime - Opening time to update 
     * @param closingTime - Closing time to update
     * @param passFee - passFee to update
     * @return - museum
     */
    public MMS updateMuseumPassFee(int passFee) {

        parameterCheckForUpdateMuseum(passFee);

        MMS museum = mmsRepository.findMMSByMuseumID(1);
        if(museum == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No museum exists.");
        } else {
            museum.setPassFee(passFee);
            mmsRepository.save(museum);
        }

        return museum;
    }
    

    /**
     * Method to check if the inputted parameters are valid in order to update museum object
     * 
     * @param openingTime - Opening time to update 
     * @param closingTime - Closing time to update
     * @param passFee - passFee to update
     */
    public void parameterCheckForUpdateMuseum(int passFee) {
       
        String errorMessage = "";

        if(passFee < 0) {
            errorMessage = "Pass fee cannot be less than 0$";
        } 

        if(errorMessage.length() != 0) {
            throw new MMSException(HttpStatus.BAD_REQUEST, errorMessage);
        }

    }

    
}
