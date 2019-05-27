package com.sun.prms.repository;

import com.sun.prms.domain.HrmOhrmProject;
import com.sun.prms.domain.HrmTimesheetItem;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Roles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HrmOhrmProjectRepository extends JpaRepository<HrmOhrmProject, Integer> {

	// List<HrmOhrmProject> findByCustomerId(Integer clientId);
	
   /* @Query(value="SELECT SUM(duration)  from sunhrm_jan.ohrm_timesheet_item where employee_id = ?2 and project_id = ?1 ",
    		nativeQuery = true)
    Float totalHoursOfResource(Integer projectId,Integer resourceId);*/
	// public List<HrmOhrmProject> findAll();
	
//	@Query(value="SELECT * from sunhrm_jan.ohrm_project where customer_id = ?1 and is_deleted = 1",
//    		nativeQuery = true)
//	List<HrmOhrmProject> findByCustomerId(Integer clientId);
	@Query(value="SELECT p from HrmOhrmProject p where p.customerId= :clientId and p.isDeleted=0")
	List<HrmOhrmProject> findByCustomerId(@Param("clientId") Integer clientId);

}
