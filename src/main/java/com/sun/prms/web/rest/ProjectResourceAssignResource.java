package com.sun.prms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sun.prms.domain.ProjectResourceAssign;
import com.sun.prms.service.ProjectResourceAssignService;
import com.sun.prms.service.dto.ProjectResourceAssignDTO;
import com.sun.prms.service.dto.ProjectSearch;
import com.sun.prms.service.dto.ResourceSearch;
import com.sun.prms.service.dto.ResourceCountOnProjectGraphDTO;
import com.sun.prms.service.dto.ResourcesOfClientDTO;
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
 * REST controller for managing ProjectResourceAssign.
 */
@RestController
@RequestMapping("/api")
public class ProjectResourceAssignResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResourceAssignResource.class);

    private static final String ENTITY_NAME = "projectResourceAssign";

    private final ProjectResourceAssignService projectResourceAssignService;

    public ProjectResourceAssignResource(ProjectResourceAssignService projectResourceAssignService) {
        this.projectResourceAssignService = projectResourceAssignService;
    }

    /**
     * POST  /project-resource-assigns : Create a new projectResourceAssign.
     *
     * @param projectResourceAssign the projectResourceAssign to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectResourceAssign, or with status 400 (Bad Request) if the projectResourceAssign has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    /*@PostMapping("/project-resource-assigns")
    @Timed
    public ResponseEntity<ProjectResourceAssign> createProjectResourceAssign(@RequestBody ProjectResourceAssign projectResourceAssign) throws URISyntaxException {
        log.debug("REST request to save ProjectResourceAssign : {}", projectResourceAssign);
        if (projectResourceAssign.getId() != null) {
            throw new BadRequestAlertException("A new projectResourceAssign cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectResourceAssign result = projectResourceAssignService.save(projectResourceAssign);
        return ResponseEntity.created(new URI("/api/project-resource-assigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }*/
    
    /**
     * POST  /project-resource-assigns : Create a new projectResourceAssign.
     *
     * @param projectResourceAssign the projectResourceAssign to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectResourceAssign, or with status 400 (Bad Request) if the projectResourceAssign has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/project-resource-assigns")
    @Timed
    public ResponseEntity<ProjectResourceAssign> createProjectResourceAssign(@RequestBody ProjectResourceAssignDTO projectResourceAssignDto) throws URISyntaxException {
        log.debug("REST request to save ProjectResourceAssign : {}", projectResourceAssignDto);
        // Calling the service file
        ProjectResourceAssign result = projectResourceAssignService.save(projectResourceAssignDto);
        return ResponseEntity.created(new URI("/api/project-resource-assigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project-resource-assigns : Updates an existing projectResourceAssign.
     *
     * @param projectResourceAssign the projectResourceAssign to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectResourceAssign,
     * or with status 400 (Bad Request) if the projectResourceAssign is not valid,
     * or with status 500 (Internal Server Error) if the projectResourceAssign couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/project-resource-assigns")
    @Timed
    public ResponseEntity<ProjectResourceAssign> updateProjectResourceAssign(@RequestBody ProjectResourceAssignDTO projectResourceAssignDto) throws URISyntaxException {
        log.debug("REST request to update ProjectResourceAssign : {}", projectResourceAssignDto);
        if (projectResourceAssignDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectResourceAssign result = projectResourceAssignService.save(projectResourceAssignDto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectResourceAssignDto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project-resource-assigns : get all the projectResourceAssigns.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projectResourceAssigns in body
     */
    @GetMapping("/project-resource-assigns")
    @Timed
    public ResponseEntity<List<ProjectResourceAssign>> getAllProjectResourceAssigns(Pageable pageable) {
        log.debug("REST request to get a page of ProjectResourceAssigns");
        Page<ProjectResourceAssign> page = projectResourceAssignService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/project-resource-assigns");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /project-resource-assigns/:id : get the "id" projectResourceAssign.
     *
     * @param id the id of the projectResourceAssign to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectResourceAssign, or with status 404 (Not Found)
     */
    @GetMapping("/project-resource-assigns/{id}")
    @Timed
    public ResponseEntity<ProjectResourceAssign> getProjectResourceAssign(@PathVariable Integer id) {
        log.debug("REST request to get ProjectResourceAssign : {}", id);
        Optional<ProjectResourceAssign> projectResourceAssign = projectResourceAssignService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectResourceAssign);
    }
    
    @GetMapping("/release-from-project/{id}")
    @Timed
    public ResponseEntity<ProjectResourceAssign> releaseFromProject(@PathVariable Integer id) {
        log.debug("REST request to get ProjectResourceAssign *************** : {}", id);
        projectResourceAssignService.releaseFromProject(id);
        Optional<ProjectResourceAssign> projectResourceAssign = projectResourceAssignService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectResourceAssign);
    }

    /**
     * DELETE  /project-resource-assigns/:id : delete the "id" projectResourceAssign.
     *
     * @param id the id of the projectResourceAssign to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/project-resource-assigns/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectResourceAssign(@PathVariable Integer id) {
        log.debug("REST request to delete ProjectResourceAssign : {}", id);
        projectResourceAssignService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
     
    @GetMapping("/role-resource-already-on-project/{resourceId}/{projectId}/{roleId}")
    @Timed
    public Boolean checkRoleResourceProjectAlreadyAssigned(
    	@PathVariable Integer resourceId,
    	@PathVariable Integer projectId,
    	@PathVariable Integer roleId
    ) {
    	Boolean result = projectResourceAssignService.checkRoleResourceProjectAlreadyAssigned(resourceId, projectId, roleId);
    	return result;
    }
    
    /**
     * To get the list of resources who are all on the selected 
     * @param projectId
     * @return
     */
    @GetMapping("/get-resources-of-selected-prms-project/{projectId}")
    @Timed
    public List<ProjectResourceAssign> getResourcesOfProject(@PathVariable Integer projectId) {
	    List<ProjectResourceAssign> listOfResourcesOnProject = projectResourceAssignService.getResourcesOfPrmsProject(projectId);
		return listOfResourcesOnProject;
    	
    }
    
    @GetMapping("/validate-percentage/{enteredPerncentage}/{selectedResource}")
    @Timed
    public Integer validatePercentate(
    	@PathVariable Integer enteredPerncentage,
    	@PathVariable Integer selectedResource
    ) {
    	Integer result = projectResourceAssignService.validatePercentate(enteredPerncentage, selectedResource);
    	return result;
    }
    
    @GetMapping("/resources-on-availability/{availabilityRange}")
    @Timed
    public List<ProjectResourceAssign> resourcesOnAvailability(
    	@PathVariable Integer availabilityRange
    	
    ) {
    	List<ProjectResourceAssign> result = projectResourceAssignService.resourcesOnAvailability(availabilityRange);
    	return result;
    }
    
    
    @GetMapping("/resources-of-clients/{clientId}")
    @Timed
    public List<ResourcesOfClientDTO> resourcesOfClient(
    	@PathVariable Integer clientId
    	
    ) {
    	List<ResourcesOfClientDTO> result = projectResourceAssignService.resourcesOfClient(clientId);
    	return result;
    }
    
    @GetMapping("/resource-count-on-project-for-pie-chart")
    @Timed
    public List<ResourceCountOnProjectGraphDTO> getResourceCountOnProjectForPieChart() {
    	List<ResourceCountOnProjectGraphDTO> result = projectResourceAssignService.getResourceCountOnProjectForPieChart();
    	return result;
    }
    
    @PostMapping("resource-search")
    @Timed
    public ResponseEntity<List<ProjectResourceAssign>> getResourceBySearch(
    	 @RequestBody ResourceSearch resourceSearch
    ) {
    	
    	// return projectService.searchByQuery(projectName, clientName, projectType);
    	List<ProjectResourceAssign> result = projectResourceAssignService.searchByQuery(
    			resourceSearch.getEmpName(), 
    			resourceSearch.getProjectName(), 
    			resourceSearch.getClientName()
    	);
    	return ResponseEntity.ok().body(result);
    	// return null;
    }
    
    
}
