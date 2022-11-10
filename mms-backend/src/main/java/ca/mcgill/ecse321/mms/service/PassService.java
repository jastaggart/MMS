package ca.mcgill.ecse321.mms.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    public PassResponseDto getPassById(int id) {
        Pass pass = passRepository.findPassByPassID(id);
        if (pass == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "Pass with ID " + id + " not found.");
		}
        return new PassResponseDto(pass);
    }

    @Transactional
    public List<PassResponseDto> getPassesByVisitorId(int visitorID) {
        List<Pass> passes = passRepository.findPassByPassPurchaserVisitorID(visitorID);
        if (passes == null) {
			throw new MMSException(HttpStatus.NOT_FOUND, "No passes owned by visitor with visitorID " + visitorID + ".");
		}
        List<PassResponseDto> passResponses = new ArrayList<PassResponseDto>();
        for (Pass pass : passes) {
            passResponses.add(new PassResponseDto(pass));
        }
        return passResponses;
    }

    @Transactional
	public List<PassResponseDto> getAllPasses() {
		List<Pass> passes = passRepository.findAll();

        if (passes == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No passes found.");
        }

        List<PassResponseDto> passResponses = new ArrayList<PassResponseDto>();
        for (Pass pass : passes) {
            passResponses.add(new PassResponseDto(pass));
        }
        return passResponses;
	}


    @Transactional
    public PassResponseDto createPass(PassRequestDto passRequest) {
        Pass pass = new Pass();
        pass.setPassDate(passRequest.getPassDate());
        pass.setPassID(passRequest.getPassId());
        pass.setPassPurchaser(passRequest.getPassPurchaser().toModel());

        pass = passRepository.save(pass);
        return new PassResponseDto(pass);
    }

}
