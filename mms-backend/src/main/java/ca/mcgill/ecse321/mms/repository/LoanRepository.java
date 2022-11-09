package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.Loan;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, String> {
	public Loan findLoanByLoanID(int id);
	public List<Loan> findAll();
}