package ca.mcgill.ecse321.mms.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.mms.dto.VisitorRequestDto;
import ca.mcgill.ecse321.mms.dto.VisitorResponseDto;
import ca.mcgill.ecse321.mms.model.Visitor;
import ca.mcgill.ecse321.mms.service.VisitorService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8087")
public class VisitorController {
    @Autowired
    VisitorService visitorService;

    /**
     * Creates a visitor using Dto data after HTTP request and puts it into HTTP response
     * @param request - The VisitorRequestDto data
     * @return - the VisitorResponseDto for the created visitor
     */
    @PostMapping ("/visitor") 
    public ResponseEntity<VisitorResponseDto> createVisitor(@Valid @RequestBody VisitorRequestDto request) {
        Visitor visitorToCreate = request.toModel();
        Visitor createdVisitor = visitorService.createVisitor(visitorToCreate);
        VisitorResponseDto response = new VisitorResponseDto(createdVisitor);
        return new ResponseEntity<VisitorResponseDto>(response, HttpStatus.CREATED);
    }
    
    /**
     * Gets a visitor by its id after HTTP request and puts it into HTTP response
     * @param id - Id of Visitor to be retrieved
     * @return - the VisitorResponseDto for the fetched visitor
     */
    @GetMapping("/visitor/id/{id}")
    public ResponseEntity<VisitorResponseDto> getVisitorById(@PathVariable int id) {
        Visitor visitor = visitorService.getVisitorById(id);
		return new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(visitor), HttpStatus.OK);
    }

    /**
     * Gets a visitor by its username after HTTP request and puts it into HTTP response
     * @param username - Username of Visitor to be retrieved
     * @return - the VisitorResponseDto for the fetched visitor
     */
    @GetMapping("/visitor/username/{username}")
    public ResponseEntity<VisitorResponseDto> getVisitorByUsername(@PathVariable String username) {
        Visitor visitor = visitorService.getVisitorByUsername(username);
		return new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(visitor), HttpStatus.OK);
    }

    /**
     * Gets a visitor by its email after HTTP request and puts it into HTTP response
     * @param email - Email of Visitor to be retrieved
     * @return - the VisitorResponseDto for the fetched visitor
     */
    @GetMapping("/visitor/email/{email}")
    public ResponseEntity<VisitorResponseDto> getVisitorByEmail(@PathVariable String email ) {
        Visitor visitor = visitorService.getVisitorByEmail(email);
		return new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(visitor), HttpStatus.OK);
    }

    /**
     * Gets all existing visitors after HTTP request and puts it into HTTP response
     * @return - The list of visitors as VisitorResponseDtos
     */
    @GetMapping("/visitors")
    public List<ResponseEntity<VisitorResponseDto>> getAllVisitors() {
        List<Visitor> visitors = visitorService.getAllVisitors();
        List<ResponseEntity<VisitorResponseDto>> visitorsDTO = new ArrayList<>();
        for (Visitor visitor : visitors) {
            visitorsDTO.add(new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(visitor), HttpStatus.OK));
        }
		return visitorsDTO;
    }

    /**
     * Deletes an existing visitor by its id after HTTP request and puts it into HTTP response
     * @param id - Id of the visitor to delete
     * @return - The deleted visitor as a VisitorResponseDto
     */
    @DeleteMapping("/visitor/delete/{id}")
    public ResponseEntity<VisitorResponseDto> deleteVisitor(@PathVariable int id) {
        Visitor deletedVisitor = visitorService.deleteVisitorById(id);
		return new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(deletedVisitor), HttpStatus.OK);
    }

    
    
}
