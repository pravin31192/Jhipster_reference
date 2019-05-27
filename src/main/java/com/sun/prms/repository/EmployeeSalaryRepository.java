package com.sun.prms.repository;

import com.sun.prms.domain.EmployeeSalary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeSalary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, Long> {
	public EmployeeSalary findByEmployeeId(Integer employeeId);

}
