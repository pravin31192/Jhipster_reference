package com.sun.prms.service;

import com.sun.prms.domain.HrmCustomer;
import com.sun.prms.domain.HrmOhrmProject;
import com.sun.prms.domain.Project;
import com.sun.prms.domain.ProjectModelForSearch;
import com.sun.prms.repository.HrmCustomerRepository;
import com.sun.prms.repository.HrmOhrmProjectRepository;
import com.sun.prms.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;
    private final HrmOhrmProjectRepository hrmOhrmProjectRepository;
    private final HrmCustomerRepository hrmCustomerRepository;

    public ProjectService(ProjectRepository projectRepository, 
    		HrmOhrmProjectRepository hrmOhrmProjectRepository,
    		HrmCustomerRepository hrmCustomerRepository
    		) {
        this.hrmOhrmProjectRepository = hrmOhrmProjectRepository;
        this.projectRepository = projectRepository;
        this.hrmCustomerRepository = hrmCustomerRepository;
    }

    /**
     * Save a project.
     *
     * @param project the entity to save
     * @return the persisted entity
     */
    public Project save(Project project) {
        // Getting the project name from the SUNHRM projects table.
    	// 1) Set the hrm-project name to prms-project
        HrmOhrmProject hrmProject = hrmOhrmProjectRepository.getOne(project.getHrmProjectId());
        project.setProjectName(hrmProject.getName());
        // 2) Save Client Name in Project table
        HrmCustomer selectedCustomer = hrmCustomerRepository.getOne(project.getHrmClientId());
        project.setClientName(selectedCustomer.getName());
        return projectRepository.save(project);
    }

    /**
     * Get all the projects.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Project> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        return projectRepository.findAll(pageable);
    }


    /**
     * Get one project by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Project> findOne(Integer id) {
        log.debug("Request to get Project : {}", id);
        return projectRepository.findById(id);
    }

    /**
     * Delete the project by id.
     *
     * @param id the id of the entity
     */
    public void delete(Integer id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<HrmOhrmProject> getProjectsFromHrm() {
    	return hrmOhrmProjectRepository.findAll(); 
    }
    
    @Transactional(readOnly = true)
    public List<HrmCustomer> getClientsFromHrm() {
    	return hrmCustomerRepository.findAll(); 
    }
    
    @Transactional(readOnly = true)
    public List<HrmOhrmProject> getProjectsOfClientsFromHrm(Integer clientId) {
    	return hrmOhrmProjectRepository.findByCustomerId(clientId); 
    }
    
    @Transactional(readOnly = true)
	public List<ProjectModelForSearch> searchByQuery(
		String projectName, 
		String clientName, 
		String projectStatus,
		String projectType,
		String percentageComplete
	) {

    	System.out.println(projectName);
    	System.out.println(clientName);
    	System.out.println(projectStatus);
    	System.out.println(projectType);
    	System.out.println(percentageComplete);
    	
    	if(projectName != null && !projectName.isEmpty()) {
    		
    	} else {
    		projectName ="%";
        	System.out.println("projectNamenew"+projectName);
    	}
    	// -------------------------------
    	if(clientName != null && !clientName.isEmpty()) {
    		

    	} else {
    		clientName ="%";
        	System.out.println("client name new"+clientName);
    	}
    	// -------------------------------
    	if(projectStatus != null && !projectStatus.isEmpty()) {
    		

    	} else {
    		projectStatus ="%";
        	System.out.println("projectStatus new"+projectStatus);
    	}
    	// -------------------------------
    	if(projectType != null && !projectType.isEmpty()) {
    		

    	} else {
    		projectType ="%";
        	System.out.println("projectType new"+projectType);
    	}
    	
    	// -------------------------------
    	if(percentageComplete != null ) {
    		//percentageComplete = 5f;

    	} else {
    		percentageComplete = "%";
        	System.out.println("percentageComplete new"+percentageComplete);
    	}
    	//int search=(int) Math.round(percentageComplete);
    	//int search= percentageComplete.intValue();
    	
		List<ProjectModelForSearch> searchResultForProject = projectRepository.searchByKeywords(projectName, clientName, projectStatus, projectType,percentageComplete);
		return searchResultForProject;
	}
    
}
