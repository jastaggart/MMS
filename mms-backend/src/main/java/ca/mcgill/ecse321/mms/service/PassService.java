package ca.mcgill.ecse321.mms.service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.mms.dto.PassRequestDto;
import ca.mcgill.ecse321.mms.dto.PassResponseDto;
import ca.mcgill.ecse321.mms.exception.MMSException;
import ca.mcgill.ecse321.mms.model.Pass;
import ca.mcgill.ecse321.mms.model.Visitor;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.PassRepository;
import ca.mcgill.ecse321.mms.repository.VisitorRepository;

@Service
public class PassService {
    
    @Autowired(required = true)
    PassRepository passRepository;

    @Autowired(required = true)
    MMSRepository mmsRepository;

    @Autowired(required = true)
    VisitorRepository visitorRepository;

    /**
     * Creates a Pass using specific Pass data and saves it in the passRepository
     * @param pass - Pass object to create and save in the passRepository
     * @return - The created Pass
     * @throws ParseException
     */
    @Transactional
    public Pass createPass(Visitor visitor, String date) throws ParseException {
        Pass pass = new Pass();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date parsedDate = formatter.parse(date);
        pass.setPassDate(parsedDate);
        pass.setPassPurchaser(visitor);
        pass = passRepository.save(pass);
        return pass;
    }

    /**
     * Gets a Pass by its passID attribute
     * 
     * @param id - Pass passID
     * @return - The fetched Pass
     */
    @Transactional
	public Pass getPassById(int id) {
        if (id < 0) {
            throw new MMSException(HttpStatus.BAD_REQUEST, "Pass ID " + id + " is invalid.");
        }
		Pass pass = passRepository.findPassByPassID(id);
		if (pass == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Pass with ID " + id + " not found.");
		}
		return pass;
	}

    /**
     * Gets a Pass by its visitorID attribute
     * 
     * @param visitorID - Pass visitorID
     * @return - The fetched Pass
     */
    @Transactional
    public List<Pass> getPassesByVisitorId(int visitorID) {
        if (visitorID < 0) {
            throw new MMSException(HttpStatus.BAD_REQUEST, "Visitor ID " + visitorID + " is invalid.");
        }
        List<Pass> passes = passRepository.findPassByPassPurchaserVisitorID(visitorID);
        if (passes == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No passes owned by visitor with visitorID " + visitorID + ".");
		}
        if (passes.isEmpty()) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No passes owned by visitor with visitorID " + visitorID + ".");
		}

        return passes;
    }

    /**
     * Gets a list of all Passes in the passRepository
     * 
     * @return - The fetched list of Passes
     */
    @Transactional
	public List<Pass> getAllPasses() {
		List<Pass> passes = passRepository.findAll();

        if (passes == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No passes found.");
        }

        return passes;
	}

    

}
