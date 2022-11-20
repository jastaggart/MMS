package ca.mcgill.ecse321.mms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.mms.exception.MMSException;
import ca.mcgill.ecse321.mms.model.Visitor;
import ca.mcgill.ecse321.mms.repository.LoanRepository;
import ca.mcgill.ecse321.mms.repository.MMSRepository;
import ca.mcgill.ecse321.mms.repository.VisitorRepository;

@Service
public class VisitorService {
    
    @Autowired
    VisitorRepository visitorRepo;

    @Autowired
    MMSRepository mmsRepo;

    @Autowired
    LoanRepository loanRepo;

    /**
     * Creates a Visitor using specified Visitor data and saves it in the visitorRepo
     * @param visitor - Visitor object to create and save in the visitorRepo
     * @return - The created visitor account
     */
    @Transactional
    public Visitor createVisitor(Visitor visitor) {
        String email = visitor.getEmail();
        String username = visitor.getUsername();
        
        if (username.isEmpty() || username == null) {
            throw new MMSException(HttpStatus.BAD_REQUEST, "Username cannot be blank.");
        } 
        
        if (!isValidEmail(email)) {
            throw new MMSException(HttpStatus.BAD_REQUEST, "Invalid email.");
        }
        Visitor existingVisitor = visitorRepo.findVisitorByEmail(email);
        if (existingVisitor != null) {
            throw new MMSException(HttpStatus.BAD_REQUEST, "A visitor with this email is already registered.");  
        }
        existingVisitor = visitorRepo.findVisitorByUsername(username);
        if (existingVisitor != null) {
            throw new MMSException(HttpStatus.BAD_REQUEST, "This username is not available.");  
        }

        visitor.setMuseumManagementSystem(mmsRepo.findMMSByMuseumID(1));
        return visitorRepo.save(visitor);
    }

    /**
     * Gets a Visitor from the visitorRepo by the specified visitor id
     * @param id - Visitor's id 
     * @return - The fetched Visitor
     */
    @Transactional
    public Visitor getVisitorById(int id) {
        Visitor visitor = visitorRepo.findVisitorByVisitorID(id);
        if (visitor == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "Visitor account not found.");  
        }
        return visitor;
    }

    /**
     * Gets a Visitor from the visitorRepo by the specified visitor username
     * @param username - Visitor's username 
     * @return - The fetched Visitor
     */
    @Transactional
    public Visitor getVisitorByUsername(String username) {
        Visitor visitor = visitorRepo.findVisitorByUsername(username);
        if (visitor == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "Visitor account with this username was not found.");  
        }
        return visitor;
    }

    /**
     * Gets a Visitor from the visitorRepo by the specified email address
     * @param email - Visitor's email address
     * @return - The fetched Visitor
     */
    @Transactional
    public Visitor getVisitorByEmail(String email) {
        Visitor visitor = visitorRepo.findVisitorByEmail(email);
        if (visitor == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "Visitor account with this email was not found.");  
        }
        return visitor;
    }

    /**
     * Gets a list of all Visitors in the visitorRepo
     * @return - The list of all Visitors
     */
    @Transactional
    public List<Visitor> getAllVisitors() {
        List<Visitor> visitors = new ArrayList<Visitor>();
        visitors = (List<Visitor>) visitorRepo.findAll();

        if (visitors == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "No visitor accounts found.");  
        }
        return visitors;
    }

    /**
     * Finds the Visitor with the specified id and deletes the Visitor from the visitorRepo 
     * @param id - The id of the Visitor to delete
     * @return - The deleted Visitor
     */
    @Transactional
    public Visitor deleteVisitorById(int id) {
        Visitor visitor = visitorRepo.findVisitorByVisitorID(id);
        if (visitor == null) {
            throw new MMSException(HttpStatus.NOT_FOUND, "Visitor account not found.");  
        }
        visitorRepo.delete(visitor);
        return visitor;
    }

    /**
     * This method checks if the specified username is associated with a valid Visitor in the visitorRepo
     * and that the specified password matches the password of 
     * @param username - Visitor's username
     * @param password - Password for the Visitor with the specified username
     * @return - The Visitor with specified username and password 
     */
    //to be used for logging in
    public Visitor authenticateVisitor (String username, String password) {
        if (username.isEmpty() || (visitorRepo.findVisitorByUsername(username) == null)) {
            throw new MMSException(HttpStatus.BAD_REQUEST, "Invalid username input.");
        }
        Visitor visitor = visitorRepo.findVisitorByUsername(username);
        if (!visitor.getPassword().equals(password)) {
            throw new MMSException(HttpStatus.UNAUTHORIZED, "Incorrect password.");
        }
        return visitor;
    }

    /**
     * This method checks if the input email address is in accordance with the email address syntax specified by RFC 5322.
     * This verifies that the domain name includes at least one dot,
     * and the email address does not contain any leading, trailing, or consecutive dots, 
     * nor any characters that are not permitted by RFC 5322.
     * @param email
     * @return - boolean, where "true" indicates email address is valid
     * 
     * @author Jasmine Taggart
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        return Pattern.matches(emailRegex, email);
    }
}
