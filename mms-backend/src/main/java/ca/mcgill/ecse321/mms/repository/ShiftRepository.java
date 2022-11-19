package ca.mcgill.ecse321.mms.repository;

import java.sql.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.mms.model.Pass;
import ca.mcgill.ecse321.mms.model.Shift;

public interface ShiftRepository extends CrudRepository<Shift, String> {
  public Shift findShiftByShiftID(int id);
  public List<Shift> findShiftByShiftAssigneeStaffMemberID(int employeeID);
  public List<Shift> findShiftByDate(Date date);
  public List<Shift> findAll();
  public void deleteShiftByShiftID(int id);
}