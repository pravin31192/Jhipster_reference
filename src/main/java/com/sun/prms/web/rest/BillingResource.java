package com.sun.prms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sun.prms.domain.Billing;
import com.sun.prms.domain.EmployeeSalary;
import com.sun.prms.domain.HrmSubUnit;
import com.sun.prms.domain.ProjectResourceAssign;
import com.sun.prms.service.BillingService;
import com.sun.prms.service.dto.BillingInitialReportDTO;
import com.sun.prms.service.dto.CBillingInitialReportDTO;
import com.sun.prms.service.dto.ResourceSearch;
import com.sun.prms.web.rest.errors.BadRequestAlertException;
import com.sun.prms.web.rest.util.HeaderUtil;
import com.sun.prms.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Billing.
 */
@RestController
@RequestMapping("/api")
public class BillingResource {

    private final Logger log = LoggerFactory.getLogger(BillingResource.class);

    private static final String ENTITY_NAME = "billing";

    private final BillingService billingService;
    

    public BillingResource(BillingService billingService) {
        this.billingService = billingService;
    }

    /**
     * POST  /billings : Create a new billing.
     *
     * @param billing the billing to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billing, or with status 400 (Bad Request) if the billing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/billings")
    @Timed
    public ResponseEntity<Billing> createBilling(@RequestBody Billing billing) throws URISyntaxException {
        log.debug("REST request to save Billing : {}", billing);
        if (billing.getId() != null) {
            throw new BadRequestAlertException("A new billing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Billing result = billingService.save(billing);
        return ResponseEntity.created(new URI("/api/billings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /billings : Updates an existing billing.
     *
     * @param billing the billing to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billing,
     * or with status 400 (Bad Request) if the billing is not valid,
     * or with status 500 (Internal Server Error) if the billing couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/billings")
    @Timed
    public ResponseEntity<Billing> updateBilling(@RequestBody Billing billing) throws URISyntaxException {
        log.debug("REST request to update Billing : {}", billing);
        if (billing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Billing result = billingService.save(billing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, billing.getId().toString()))
            .body(result);
    }

    /**
     * GET  /billings : get all the billings.
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of billings in body
     */
    @GetMapping("/billings")
    @Timed
    public ResponseEntity<List<Billing>> getAllBillings(Pageable pageable) {
        log.debug("REST request to get a page of Billings");
        Page<Billing> page = billingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/billings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /billings/:id : get the "id" billing.
     *
     * @param id the id of the billing to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billing, or with status 404 (Not Found)
     */
    @GetMapping("/billings/{id}")
    @Timed
    public ResponseEntity<Billing> getBilling(@PathVariable Long id) {
        log.debug("REST request to get Billing : {}", id);
        Optional<Billing> billing = billingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billing);
    }

    /**
     * DELETE  /billings/:id : delete the "id" billing.
     *
     * @param id the id of the billing to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/billings/{id}")
    @Timed
    public ResponseEntity<Void> deleteBilling(@PathVariable Long id) {
        log.debug("REST request to delete Billing : {}", id);
        billingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * To get the list of resources who are all on the selected 
     * @param projectId
     * @return
     */
    @GetMapping("/get-resources-of-selected-project/{projectId}")
    @Timed
    public List<ProjectResourceAssign> getResourcesOfProject(@PathVariable Integer projectId) {
	    List<ProjectResourceAssign> listOfResourcesOnProject = this.billingService.resourcesOnProjectService(projectId);
		return listOfResourcesOnProject;
    	
    }
    
    // get-salary-working-hours
    /**
     * To get the list of resources who are all on the selected 
     * @param projectId
     * @return
     */
    @GetMapping("/get-hours-of-resource/{projectId}/{resourceId}")
    @Timed
    public Integer getHoursOfResource(@PathVariable Integer projectId, @PathVariable Integer resourceId) {
    	Integer totalHoursWorked = this.billingService.getTotalWorkedHours(projectId, resourceId);
		return totalHoursWorked;
    	
    }
    
    /**
     * To get the list of resources who are all on the selected 
     * @param projectId
     * @return
     */
    @GetMapping("/get-salary-of-resource/{resourceId}")
    @Timed
    public EmployeeSalary getSalaryOfResource( @PathVariable Integer resourceId) {
    	return this.billingService.getSalaryOfResource(resourceId);
    }
    
    /**
     * To get the list of departments or sub-units in HRM
     * @param projectId
     * @return
     */
    @GetMapping("/get-subunits-in-hrm")
    @Timed
    public List<HrmSubUnit> getSubUnitsInHrm() {
    	return this.billingService.getSubunitsInHrm();
    }
    
    @GetMapping("/initial-billing-report")
    @Timed
    public List<CBillingInitialReportDTO> getInitialBillingReport() {    	
    	List<CBillingInitialReportDTO> resultSet = this.billingService.getInitialBillingReport();
    	return resultSet;
    }
    
    @GetMapping("/number-of-months-of-project")
    @Timed
    public Integer numberOfMonthsOfProject() {
    	return null;
    }
    
    @GetMapping("billvalue-of-resource-project/{project}/{resource}")
    @Timed
    public Float getBillValueOfResource(@PathVariable Integer project, @PathVariable Integer resource) {
    	Float billValue = billingService.getBillValueOfResource(project, resource);
    	return billValue;
    }
    
    @PostMapping("billing-search")
    @Timed
    public ResponseEntity<List<Billing>> getResourceBySearch(
    	 @RequestBody ResourceSearch resourceSearch
    ) {
    	
    	// return projectService.searchByQuery(projectName, clientName, projectType);
    	List<Billing> result = billingService.searchByQuery(
    			resourceSearch.getEmpName(), 
    			resourceSearch.getProjectName(), 
    			resourceSearch.getClientName()
    	);
    	return ResponseEntity.ok().body(result);
    	// return null;
    }
    
    
}
