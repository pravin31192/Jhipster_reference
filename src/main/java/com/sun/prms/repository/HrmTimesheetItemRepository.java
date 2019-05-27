package com.sun.prms.repository;

import com.sun.prms.domain.HrmTimesheetItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Roles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HrmTimesheetItemRepository extends JpaRepository<HrmTimesheetItem, Integer> {
	
    @Query(value="SELECT SUM(duration)  from sunhrm_jan.ohrm_timesheet_item where employee_id = ?2 and project_id = ?1 ",
    		nativeQuery = true)
    Integer totalHoursOfResource(Integer projectId,Integer resourceId);

}
