package ca.mcgill.ecse321.mms.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.mms.model.DisplayRoom;

public interface DisplayRoomRepository extends CrudRepository<DisplayRoom, String> {
	
}