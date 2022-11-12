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

    @Transactional
	public Pass getPassById(int id) {
		Pass pass = passRepository.findPassByPassID(id);
		if (pass == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Pass with ID " + id + " not found.");
		}
		return pass;
	}

    @Transactional
    public List<Pass> getPassesByVisitorId(int visitorID) {
        List<Pass> passes = passRepository.findPassByPassPurchaserVisitorID(visitorID);
        if (passes == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No passes owned by visitor with visitorID " + visitorID + ".");
		}

        return passes;
    }

    @Transactional
	public List<Pass> getAllPasses() {
		List<Pass> passes = passRepository.findAll();

        if (passes == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No passes found.");
        }

        return passes;
	}

    

}
