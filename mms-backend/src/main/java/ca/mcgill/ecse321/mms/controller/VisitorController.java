package ca.mcgill.ecse321.mms.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.mms.dto.VisitorRequestDto;
import ca.mcgill.ecse321.mms.dto.VisitorResponseDto;
import ca.mcgill.ecse321.mms.model.Visitor;
import ca.mcgill.ecse321.mms.service.VisitorService;

@RestController
public class VisitorController {
    @Autowired
    VisitorService visitorService;

    @PostMapping ("/visitor") 
    public ResponseEntity<VisitorResponseDto> createVisitor(@Valid @RequestBody VisitorRequestDto request) {
        Visitor visitorToCreate = request.toModel();
        Visitor createdVisitor = visitorService.createVisitor(visitorToCreate);
        VisitorResponseDto response = new VisitorResponseDto(createdVisitor);
        return new ResponseEntity<VisitorResponseDto>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/visitor/id/{id}")
    public ResponseEntity<VisitorResponseDto> getVisitorById(@PathVariable int id) {
        Visitor visitor = visitorService.getVisitorById(id);
		return new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(visitor), HttpStatus.OK);
    }

    @GetMapping("/visitor/username/{username}")
    public ResponseEntity<VisitorResponseDto> getVisitorByUsername(@PathVariable String username) {
        Visitor visitor = visitorService.getVisitorByUsername(username);
		return new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(visitor), HttpStatus.OK);
    }

    @GetMapping("/visitor/email/{email}")
    public ResponseEntity<VisitorResponseDto> getVisitorByEmail(@PathVariable String email ) {
        Visitor visitor = visitorService.getVisitorByEmail(email);
		return new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(visitor), HttpStatus.OK);
    }

    @GetMapping("/visitors")
    public List<ResponseEntity<VisitorResponseDto>> getAllVisitors() {
        List<Visitor> visitors = visitorService.getAllVisitors();
        List<ResponseEntity<VisitorResponseDto>> visitorsDTO = new ArrayList<>();
        for (Visitor visitor : visitors) {
            visitorsDTO.add(new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(visitor), HttpStatus.OK));
        }
		return visitorsDTO;
    }

    @DeleteMapping("/visitor/delete/{id}")
    public ResponseEntity<VisitorResponseDto> deleteVisitor(@PathVariable int id) {
        Visitor deletedVisitor = visitorService.deleteVisitorById(id);
		return new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(deletedVisitor), HttpStatus.OK);
    }

    @PutMapping ("/visitor/edit-username/{username}")
    public ResponseEntity<VisitorResponseDto> editUsername(@Valid @RequestBody VisitorRequestDto request, @PathVariable String username) {
        Visitor visitor = request.toModel();
        Visitor editedVisitor = visitorService.editVisitorUsername(visitor, username);
        return new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(editedVisitor), HttpStatus.OK);
    }

    @PutMapping ("/visitor/edit-password")
    public ResponseEntity<VisitorResponseDto> editPassword(@Valid @RequestBody VisitorRequestDto request, @RequestParam String oldPassword, @RequestParam String newPassword) {
        Visitor visitor = request.toModel();
        Visitor editedVisitor = visitorService.editVisitorPassword(visitor, oldPassword, newPassword);
        return new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(editedVisitor), HttpStatus.OK);
    }

    @PutMapping ("/visitor/edit-email/{email}")
    public ResponseEntity<VisitorResponseDto> editEmail(@Valid @RequestBody VisitorRequestDto request, @PathVariable String email) {
        Visitor visitor = request.toModel();
        Visitor editedVisitor = visitorService.editVisitorEmail(visitor, email);
        return new ResponseEntity<VisitorResponseDto>(new VisitorResponseDto(editedVisitor), HttpStatus.OK);
    }
    
}
