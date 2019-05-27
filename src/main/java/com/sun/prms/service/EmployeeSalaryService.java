package com.sun.prms.service;

import com.sun.prms.domain.EmployeeSalary;
import com.sun.prms.domain.HrmSubUnit;
import com.sun.prms.domain.HsHrEmployee;
import com.sun.prms.repository.EmployeeSalaryRepository;
import com.sun.prms.repository.HrmHsHrEmployeeRepository;
import com.sun.prms.repository.HrmOhrmSubunitRepository;

import ch.qos.logback.core.net.SyslogOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing EmployeeSalary.
 */
@Service
@Transactional
public class EmployeeSalaryService {

    private final Logger log = LoggerFactory.getLogger(EmployeeSalaryService.class);

    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final HrmOhrmSubunitRepository hrmOhrmSubunitRepository;
    private final HrmHsHrEmployeeRepository hrmHsHrEmployeeRepository; 

    public EmployeeSalaryService(
    	EmployeeSalaryRepository employeeSalaryRepository,
    	HrmOhrmSubunitRepository hrmOhrmSubunitRepository,
    	HrmHsHrEmployeeRepository hrmHsHrEmployeeRepository
    ) {
        this.employeeSalaryRepository = employeeSalaryRepository;
        this.hrmOhrmSubunitRepository = hrmOhrmSubunitRepository;
        this.hrmHsHrEmployeeRepository = hrmHsHrEmployeeRepository;
    }

    /**
     * Save a employeeSalary.
     *
     * @param employeeSalary the entity to save
     * @return the persisted entity
     */
    public EmployeeSalary save(EmployeeSalary employeeSalary,Integer departmentId) {
        log.debug("Request to save EmployeeSalary : {}", employeeSalary);
        // To save the employee name in the database.
        String employeeName = null;
        HsHrEmployee employeeRow = hrmHsHrEmployeeRepository.findByEmpNumber(employeeSalary.getEmployeeId());
        employeeName = employeeRow.getEmpFirstname()+' '+employeeRow.getEmpLastname();
        employeeSalary.setEmployeeName(employeeName);
        String departmentName = null;
        System.out.println("$$$$$$$$$$$$$$$"+departmentId);
        HrmSubUnit departmentRow = hrmOhrmSubunitRepository.getOne(departmentId);
        departmentName = departmentRow.getName();
        employeeSalary.setDepartmentName(departmentName);
        return employeeSalaryRepository.save(employeeSalary);
    }

    /**
     * Get all the employeeSalaries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EmployeeSalary> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeSalaries");
        return employeeSalaryRepository.findAll(pageable);
    }


    /**
     * Get one employeeSalary by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EmployeeSalary> findOne(Long id) {
        log.debug("Request to get EmployeeSalary : {}", id);
        return employeeSalaryRepository.findById(id);
    }

    /**
     * Delete the employeeSalary by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployeeSalary : {}", id);
        employeeSalaryRepository.deleteById(id);
    }
    
    /**
     * To get all the subunits(Departments) that are in HRM
     * @return
     */
	public List<HrmSubUnit> getAllSubUnits() {
		return hrmOhrmSubunitRepository.findAll();
	}
	
	
	public List<HsHrEmployee> getEmployeesOfDepartment(Integer departmentId) {
		return hrmHsHrEmployeeRepository.findByWorkStation(departmentId);
	}
}
