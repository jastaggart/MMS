package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.Loan;

public interface LoanRepository extends CrudRepository<Loan, String> {
	
}