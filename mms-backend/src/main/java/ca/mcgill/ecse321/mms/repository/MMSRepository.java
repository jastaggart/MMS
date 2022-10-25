package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.mms.model.MMS;

public interface MMSRepository extends CrudRepository<MMS, String> {
    public MMS findMMSByMuseumID(int id);
}