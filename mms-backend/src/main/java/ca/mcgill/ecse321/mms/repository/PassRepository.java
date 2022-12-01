package ca.mcgill.ecse321.mms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.Pass;

public interface PassRepository extends CrudRepository<Pass, String> {
	public Pass findPassByPassID(int id);
	public List<Pass> findPassByPassPurchaserVisitorID(int visitorID);
	public List<Pass> findPassByPassDate(Date passDate);
	public List<Pass> findAll();
}