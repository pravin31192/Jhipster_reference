package com.sun.prms.repository;


import com.sun.prms.domain.HrmSubUnit;
import com.sun.prms.domain.HsHrEmployee;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Roles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HrmHsHrEmployeeRepository extends JpaRepository<HsHrEmployee, Integer> {
	
	@Query(value="SELECT * from sunhrm_jan.hs_hr_employee where termination_id IS NULL and work_station = ?1 ",
    		nativeQuery = true)
    List<HsHrEmployee> findByWorkStation(Integer departmentId);

	HsHrEmployee findByEmpNumber(Integer employeeId);

}
