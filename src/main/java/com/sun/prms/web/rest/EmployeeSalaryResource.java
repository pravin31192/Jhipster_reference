package com.sun.prms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sun.prms.domain.EmployeeSalary;
import com.sun.prms.domain.HrmSubUnit;
import com.sun.prms.domain.HsHrEmployee;
import com.sun.prms.security.AuthoritiesConstants;
import com.sun.prms.service.EmployeeSalaryService;
import com.sun.prms.web.rest.errors.BadRequestAlertException;
import com.sun.prms.web.rest.util.HeaderUtil;
import com.sun.prms.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EmployeeSalary.
 */
@RestController
@RequestMapping("/api")
// @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
public class EmployeeSalaryResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeSalaryResource.class);

    private static final String ENTITY_NAME = "employeeSalary";

    private final EmployeeSalaryService employeeSalaryService;

    public EmployeeSalaryResource(EmployeeSalaryService employeeSalaryService) {
        this.employeeSalaryService = employeeSalaryService;
    }

    /**
     * POST  /employee-salaries : Create a new employeeSalary.
     *
     * @param employeeSalary the employeeSalary to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeSalary, or with status 400 (Bad Request) if the employeeSalary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-salaries/{departmentId}")
    @Timed
    // @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<EmployeeSalary> createEmployeeSalary(@RequestBody EmployeeSalary employeeSalary,@PathVariable Integer departmentId) throws URISyntaxException {
        log.debug("REST request to save EmployeeSalary : {}", employeeSalary);
        if (employeeSalary.getId() != null) {
            throw new BadRequestAlertException("A new employeeSalary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeSalary result = employeeSalaryService.save(employeeSalary,departmentId);
        return ResponseEntity.created(new URI("/api/employee-salaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-salaries : Updates an existing employeeSalary.
     *
     * @param employeeSalary the employeeSalary to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeSalary,
     * or with status 400 (Bad Request) if the employeeSalary is not valid,
     * or with status 500 (Internal Server Error) if the employeeSalary couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-salaries")
    @Timed
    // @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<EmployeeSalary> updateEmployeeSalary(@RequestBody EmployeeSalary employeeSalary) throws URISyntaxException {
        log.debug("REST request to update EmployeeSalary : {}", employeeSalary);
        if (employeeSalary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Integer departmentId = null;
        EmployeeSalary result = employeeSalaryService.save(employeeSalary, departmentId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeSalary.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-salaries : get all the employeeSalaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of employeeSalaries in body
     */
    @GetMapping("/employee-salaries")
    @Timed
    // @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<List<EmployeeSalary>> getAllEmployeeSalaries(Pageable pageable) {
        log.debug("REST request to get a page of EmployeeSalaries");
        Page<EmployeeSalary> page = employeeSalaryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employee-salaries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /employee-salaries/:id : get the "id" employeeSalary.
     *
     * @param id the id of the employeeSalary to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeSalary, or with status 404 (Not Found)
     */
    @GetMapping("/employee-salaries/{id}")
    @Timed
    // @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<EmployeeSalary> getEmployeeSalary(@PathVariable Long id) {
        log.debug("REST request to get EmployeeSalary : {}", id);
        Optional<EmployeeSalary> employeeSalary = employeeSalaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeSalary);
    }

    /**
     * DELETE  /employee-salaries/:id : delete the "id" employeeSalary.
     *
     * @param id the id of the employeeSalary to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-salaries/{id}")
    @Timed
    // @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteEmployeeSalary(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeSalary : {}", id);
        employeeSalaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    
    @GetMapping("/sub-units-in-hrm")
    @Timed
    public List<HrmSubUnit> getAllSubUnits() {
    	return employeeSalaryService.getAllSubUnits();
    }
    
    
    @GetMapping("/employees-in-department/{departmentId}")
    @Timed
    public List<HsHrEmployee> getEmployeesOfDepartment(@PathVariable Integer departmentId) {
    	return employeeSalaryService.getEmployeesOfDepartment(departmentId);
    }
}
