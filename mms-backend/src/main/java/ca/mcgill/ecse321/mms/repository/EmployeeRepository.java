package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
	
}