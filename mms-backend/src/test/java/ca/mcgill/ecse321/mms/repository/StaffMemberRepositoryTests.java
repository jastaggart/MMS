package ca.mcgill.ecse321.mms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.mms.model.Employee;
import ca.mcgill.ecse321.mms.model.Owner;
import ca.mcgill.ecse321.mms.model.Loan;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StaffMemberRepositoryTests {

  @Autowired
  private StaffMemberRepository staffMemberRepository;

  @Autowired
  private LoanRepository loanRepository;

  @AfterEach
  public void clearDatabase() {
    loanRepository.deleteAll();
    staffMemberRepository.deleteAll();
  }
  
  @Test
  public void testPersistAndLoadStaffMember() {
    // Creating the fields that will be set to the created owner and employee objects
    String ownerUsername = "Marwan";
    String ownerPassword = "password";
    String ownerEmail = "marwan@mail.com";
    
    String employeeUsername = "Bob";
    String employeePassword = "123";
    String employeeEmail = "bob@mail.com";
    
    // Creating and setting fields to the owner and employee objects
    Owner owner = new Owner();
    Employee employee = new Employee();
    
    owner.setUsername(ownerUsername);
    owner.setPassword(ownerPassword);
    owner.setEmail(ownerEmail);
    
    employee.setUsername(employeeUsername);
    employee.setPassword(employeePassword);
    employee.setEmail(employeeEmail);
    
    // Creating fields for Loan object
    int loanFee = 10;
    Date startDate = Date.valueOf("2022-10-25");
    Date endDate = Date.valueOf("2022-10-31");
    boolean isApproved = true;
    // Creating Loan object
    Loan loan = new Loan();
    // Setting fields of Loan object
    loan.setLoanFee(loanFee);
    loan.setStartDate(startDate);
    loan.setEndDate(endDate);
    loan.setIsApproved(isApproved);
    loan.setLoanApprover(employee);
    employee.addLoan(loan);
    
    // Saving the owner, employee and loan objects
    owner =  staffMemberRepository.save(owner);
    employee =  staffMemberRepository.save(employee);
    loan = loanRepository.save(loan);
    int ownerId = owner.getStaffMemberID();
    int employeeId = employee.getStaffMemberID();
    int loanID = loan.getLoanID();
    
    owner = null;
    employee = null;
    loan = null;

    owner =(Owner) staffMemberRepository.findStaffMemberByStaffMemberID(ownerId);
    employee =(Employee) staffMemberRepository.findStaffMemberByStaffMemberID(employeeId);
    loan =(Loan) loanRepository.findLoanByLoanID(loanID);
    
    // Assertion tests
    
    //owner
    assertNotNull(owner);
    assertEquals(ownerId, owner.getStaffMemberID());
    assertEquals(ownerUsername, owner.getUsername());
    assertEquals(ownerPassword, owner.getPassword());
    assertEquals(ownerEmail, owner.getEmail());
    
    //employee
    assertNotNull(employee);
    assertEquals(employeeId, employee.getStaffMemberID());
    assertEquals(employeeUsername, employee.getUsername());
    assertEquals(employeePassword, employee.getPassword());
    assertEquals(employeeEmail, employee.getEmail());
    
    //loan
    assertNotNull(loan);
    assertEquals(loanFee, loan.getLoanFee());
    assertEquals(startDate, loan.getStartDate());
    assertEquals(endDate, loan.getEndDate());
    assertEquals(isApproved, loan.getIsApproved());
    
    //check for the employee/loan association
    assertEquals(employeeId, loan.getLoanApprover().getStaffMemberID());
  }
}
