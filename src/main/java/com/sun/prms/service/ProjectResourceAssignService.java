package com.sun.prms.service;

import com.sun.prms.domain.HrmCustomer;
import com.sun.prms.domain.HrmSubUnit;
import com.sun.prms.domain.HsHrEmployee;
import com.sun.prms.domain.Project;
import com.sun.prms.domain.ProjectResourceAssign;
import com.sun.prms.domain.RoleUserMapping;
import com.sun.prms.repository.HrmCustomerRepository;
import com.sun.prms.repository.HrmHsHrEmployeeRepository;
import com.sun.prms.repository.HrmOhrmSubunitRepository;
import com.sun.prms.repository.ProjectRepository;
import com.sun.prms.repository.ProjectResourceAssignRepository;
import com.sun.prms.repository.RoleUserMappingRepository;
import com.sun.prms.service.dto.BillingInitialReportDTO;
import com.sun.prms.service.dto.MultipleResourcesDTO;
import com.sun.prms.service.dto.ProjectResourceAssignDTO;
import com.sun.prms.service.dto.ProjectResourceReportPieChartDTO;
import com.sun.prms.service.dto.ResourceCountOnProjectGraphDTO;
import com.sun.prms.service.dto.ResourcesOfClientDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing ProjectResourceAssign.
 */
@Service
@Transactional
public class ProjectResourceAssignService {

    private final Logger log = LoggerFactory.getLogger(ProjectResourceAssignService.class);

    private final ProjectResourceAssignRepository projectResourceAssignRepository;
    private final RoleUserMappingRepository roleUserMappingRepository;
    private final ProjectRepository projectRepository;
    private final HrmCustomerRepository hrmCustomerRepository;
    private final HrmOhrmSubunitRepository hrmOhrmSubunitRepository; 
    private final HrmHsHrEmployeeRepository hrmHsHrEmployeeRepository;
    
    public ProjectResourceAssignService(
    		ProjectResourceAssignRepository projectResourceAssignRepository,
    		RoleUserMappingRepository roleUserMappingRepository,
    		ProjectRepository projectRepository,
    		HrmCustomerRepository hrmCustomerRepository,
    		HrmOhrmSubunitRepository hrmOhrmSubunitRepository,
    		HrmHsHrEmployeeRepository hrmHsHrEmployeeRepository
    ) {
        this.projectResourceAssignRepository = projectResourceAssignRepository;
        this.roleUserMappingRepository = roleUserMappingRepository;
        this.projectRepository = projectRepository;
        this.hrmCustomerRepository = hrmCustomerRepository;
        this.hrmOhrmSubunitRepository = hrmOhrmSubunitRepository;
        this.hrmHsHrEmployeeRepository = hrmHsHrEmployeeRepository;
    }

    /**
     * Save a projectResourceAssign.
     *
     * @param projectResourceAssign the entity to save
     * @return the persisted entity
     */
    /*public ProjectResourceAssign save(ProjectResourceAssign projectResourceAssign) {
        log.debug("Request to save ProjectResourceAssign : {}", projectResourceAssign);
        // 1) Load RoleUserMapping-Table to find out the Employee Full Name
        RoleUserMapping roleMap = roleUserMappingRepository.findByUserId(projectResourceAssign.getEmpId());
        String empName = roleMap.getUserName();
        projectResourceAssign.setEmpName(empName);
        // 2) Load the  RoleUserMapping to find out the Role name.
        String roleName = roleMap.getRoleName();
        projectResourceAssign.setRoleName(roleName);
        // 3) Load the project to save the project name from project table to the project-resource
        Integer val =  projectResourceAssign.getProjectId();
        Project project = projectRepository.getOne(val);
        projectResourceAssign.setProjectName(project.getProjectName());
        // 4) load the client name from the project table to save in the table.
        String clientName = project.getClientName();
        projectResourceAssign.setClientName(clientName);
        // 5) Assigning the default status. Where 1 is active
        projectResourceAssign.setStatus(1);
        return projectResourceAssignRepository.save(projectResourceAssign);
    }*/
    
    /**
     * Save a projectResourceAssign.
     *
     * @param projectResourceAssign the entity to save
     * @return the persisted entity
     */
    public ProjectResourceAssign save(ProjectResourceAssignDTO projectResourceAssignDto) {
    	log.debug("Request to save ProjectResourceAssign : {}", projectResourceAssignDto);
    	ProjectResourceAssign projectResourceAssign = null;
    	// For each and every resources we need to loop, to save as individual rows.
    	MultipleResourcesDTO selectedResources[] = projectResourceAssignDto.getMultipleResources();
    	for (int i = 0; i < selectedResources.length; i++) {
    		projectResourceAssign = new ProjectResourceAssign();
    		projectResourceAssign.setId(projectResourceAssignDto.getId());
            // 1) Saving ProjectId
            projectResourceAssign.setProjectId(projectResourceAssignDto.getProjectId());
            // 2) Saving Employee Id
            projectResourceAssign.setEmpId(selectedResources[i].getEmpId());
            RoleUserMapping roleMap = roleUserMappingRepository.findByUserId(projectResourceAssign.getEmpId());
            HrmSubUnit hrmSubUnit = hrmOhrmSubunitRepository.getOne(selectedResources[i].getRole());
            // 3) saving role Id
            projectResourceAssign.setRole(hrmSubUnit.getId());
            // 4) Saving from date and to Date
            projectResourceAssign.setFromDate(projectResourceAssignDto.getFromDate());
            // 5) Saving To Date
            projectResourceAssign.setToDate(projectResourceAssignDto.getToDate());
            // 6) Saving billing Type
            projectResourceAssign.setBillingType(projectResourceAssignDto.getBillingType());
            // 7) Saving bill Value
            projectResourceAssign.setBillValue(selectedResources[i].getBillValue());
            // 8) Saving percentage Of Work
            projectResourceAssign.setPercentageOfWork(selectedResources[i].getPercentageOfWork());
            // 9) Saving Project Name
            Integer val =  projectResourceAssign.getProjectId();
            Project project = projectRepository.getOne(val);
            projectResourceAssign.setProjectName(project.getProjectName());
            // 10) Saving Employee name
            HsHrEmployee employeeRow = hrmHsHrEmployeeRepository.findByEmpNumber(projectResourceAssign.getEmpId());
            String empName = employeeRow.getEmpFirstname()+" "+employeeRow.getEmpLastname();
            projectResourceAssign.setEmpName(empName);
            // 11) Save Role Name
            String roleName = hrmSubUnit.getName();
            projectResourceAssign.setRoleName(roleName);
            // 12) Save Client name
            String clientName = project.getClientName();
            projectResourceAssign.setClientName(clientName);
    		// 13) Saving actual Staff hours
            projectResourceAssign.setActualStaffHours(projectResourceAssignDto.getActualStaffHours());
            // 14) Saving estimated working hours
            projectResourceAssign.setEstimatedStaffHours(projectResourceAssignDto.getEstimatedStaffHours());
            // 15) Saving location
            projectResourceAssign.setLocation(projectResourceAssignDto.getLocation());
            // 16) Saving Status
            projectResourceAssign.setStatus(1);
            projectResourceAssignRepository.save(projectResourceAssign);
    	} 
    	
    	return projectResourceAssign;
    }
    

    /**
     * Get all the projectResourceAssigns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProjectResourceAssign> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectResourceAssigns");
        return projectResourceAssignRepository.findAll(pageable);
    }


    /**
     * Get one projectResourceAssign by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProjectResourceAssign> findOne(Integer id) {
        log.debug("Request to get ProjectResourceAssign : {}", id);
        return projectResourceAssignRepository.findById(id);
    }
    
    @Transactional(readOnly = false)
    public Optional<ProjectResourceAssign> releaseFromProject(Integer id) {
        log.debug("Request to get ProjectResourceAssign : {}", id);
        projectResourceAssignRepository.releaseFromProject(id);
        return projectResourceAssignRepository.findById(id);
    }

    /**
     * Delete the projectResourceAssign by id.
     *
     * @param id the id of the entity
     */
    public void delete(Integer id) {
        log.debug("Request to delete ProjectResourceAssign : {}", id);
        projectResourceAssignRepository.deleteById(id);
    }
    
    /*@Transactional(readOnly = true)
    public List<ProjectResourceAssign> resourcesOnProjectService(Integer projectId) {
        return projectResourceAssignRepository.findProjectResources(projectId);
    }*/
    
    
    /**
     * 
     * @param resourceId Integer Selected resource from the project-resource-assign page
     * @param projectId Integer  Selected project from the project-resource-assign page
     * @param roleId Integer     Selected role from the project-resource-assign page
     * @return result boolean
     */
    @Transactional(readOnly = true)
    public Boolean checkRoleResourceProjectAlreadyAssigned(Integer resourceId, Integer projectId, Integer roleId) {
    	ProjectResourceAssign selectedRow = projectResourceAssignRepository.checkRoleResourceProjectAlreadyAssigned(
    		resourceId,
    		projectId,
    		roleId
    	);
    	Boolean result = null;
    	if (selectedRow == null) {
    		result = false;
    	} else {
    		result = true;
    	}
    	return result;
    }
    
    @Transactional(readOnly = true)
	public List<ProjectResourceAssign> getResourcesOfPrmsProject(Integer projectId) {
		List<ProjectResourceAssign> result = projectResourceAssignRepository.findByProjectId(projectId);
		return result;
	}
    
    /**
     * 
     * @param enteredPerncentage
     * @param selectedResource
     * @return
     */
	public Integer validatePercentate(Integer enteredPerncentage, Integer selectedResource) {
		Integer allocatedPercentage;
		allocatedPercentage = projectResourceAssignRepository.getAllocatedPercentage(selectedResource);
		if (allocatedPercentage == null) {
			return 0;
		} else {
			return allocatedPercentage;
		}
	}
	
	/**
	 * To get the resources who are available on the selected range.
	 * @param availabilityRange
	 * @return
	 */
	public List<ProjectResourceAssign> resourcesOnAvailability(Integer availabilityRange) {
		Integer from = 0;
		Integer to = 0;
		System.out.println("availibility range is: " + availabilityRange);
		switch (availabilityRange) {
		case 0:
			from = 101;
			to = 1000;
			break;
		case 1: 
			from = 75;
			to = 100;
			break;
		case 2:
			from = 50;
			to = 74;
			break;
		case 3:
			from = 25;
			to = 49;
			break;
		case 4:
			from = 0;
			to = 24;
			break;
		}
		System.out.println("Range from: "+from);
		System.out.println("Range up to: "+to);
		List<ProjectResourceAssign> resourcesOnAvailability = projectResourceAssignRepository.resourcesOnAvailability(from, to);
		
		System.out.println("Resource on Availibility: "+resourcesOnAvailability);
		return resourcesOnAvailability;
	}

	
	/******************************
	 * For View Resource by Client*
	 * ****************************/
	public List<ResourcesOfClientDTO> resourcesOfClient(Integer clientId) {
		HrmCustomer customerRow = hrmCustomerRepository.getOne(clientId);
		String clientName = customerRow.getName();
		System.out.println("client name "+clientName);
		List<ProjectResourceAssign> resultSet = projectResourceAssignRepository.getResourcesByClient(clientName);
		List<ResourcesOfClientDTO> dtoResultSet = new ArrayList<ResourcesOfClientDTO>();
		Integer tempEmpId = null;
		Float tempTotal = null;
		Map<String, Float> resourceMapping = new HashMap<>();
		String key="";
		Float totalAllocation = 0F;
		if (resultSet != null) {
			tempEmpId = resultSet.get(0).getEmpId();
			
			for (int i = 0; i < resultSet.size(); i++) {
				ResourcesOfClientDTO clientResources = new ResourcesOfClientDTO();
				clientResources.setEmployeeName(resultSet.get(i).getEmpName());
				clientResources.setProjectName(resultSet.get(i).getProjectName());
				clientResources.setPercentageOfAllocation(resultSet.get(i).getPercentageOfWork());
				clientResources.setBillValue(resultSet.get(i).getBillValue());
				clientResources.setFromDate(resultSet.get(i).getFromDate());
				clientResources.setToDate(resultSet.get(i).getToDate());
				System.out.println("----------------------------------------------------------");
				System.out.println("Starting point Emp ID: "+resultSet.get(i).getEmpId() );
				System.out.println("Starting point TEMP Emp ID: "+tempEmpId );
			
				
				//setting the hashmap key
				key = resultSet.get(i).getEmpName();
				System.out.println("Key is: "+key);
				System.out.println("Value is " + resultSet.get(i).getPercentageOfWork());
				
				//updating the hashmap value
				if(resourceMapping.containsKey(key)) {
					System.out.println("Inside the If of the loop");
					System.out.println("Old %: "+ resourceMapping.get(key));
					System.out.println("New %: "+ resultSet.get(i).getPercentageOfWork());
					resourceMapping.put(key, resourceMapping.get(key) + resultSet.get(i).getPercentageOfWork());
					tempTotal = totalAllocation;
					totalAllocation = resourceMapping.get(key);
					System.out.println("Total Allocation:"+totalAllocation);
				}
				else {
					System.out.println("Inside the Else of the loop");
					resourceMapping.put(key,resultSet.get(i).getPercentageOfWork());
					totalAllocation = resourceMapping.get(key);
					tempTotal = totalAllocation;
					System.out.println("Total Allocation:"+totalAllocation);
				}
				
				
					
/*				totalAllocation = resourceMapping.containsKey(resultSet.get(i).getEmpId())?resourceMapping.get(resultSet.get(i).getEmpId()):0F;
				totalAllocation+= resultSet.get(i).getPercentageOfWork();
				resourceMapping.put(resultSet.get(i).getEmpId(), totalAllocation);
				System.out.println("Resource Mapping is equal to: "+resourceMapping);
*/				
				/*
				if (tempEmpId.equals(currentEmpId)) {
					System.out.println("if loop : "+tempEmpId);
					totalAllocation = totalAllocation + resultSet.get(i).getPercentageOfWork();
					tempTotal = totalAllocation;
					System.out.println("if loop total allocation : "+totalAllocation);
				} else {
					System.out.println("else loop : "+tempEmpId);
					tempEmpId = resultSet.get(i).getEmpId();
					tempTotal = 0F;
					totalAllocation = 0F;
					totalAllocation = totalAllocation + resultSet.get(i).getPercentageOfWork();
					tempTotal = totalAllocation;
					System.out.println("else loop totalAllocation : "+totalAllocation);
					
				}
				*/
				System.out.println("----------------------------------------------------------");
				
				
				
			}
			System.out.println("-------------------------------------");
			System.out.println(resourceMapping);
			for (int j = 0; j < resultSet.size(); j++) {
				ResourcesOfClientDTO clientResourcesNew = new ResourcesOfClientDTO();
				clientResourcesNew.setEmployeeName(resultSet.get(j).getEmpName());
				clientResourcesNew.setProjectName(resultSet.get(j).getProjectName());
				
				clientResourcesNew.setPercentageOfAllocation(resultSet.get(j).getPercentageOfWork());
				clientResourcesNew.setBillValue(resultSet.get(j).getBillValue());
				clientResourcesNew.setFromDate(resultSet.get(j).getFromDate());
				clientResourcesNew.setToDate(resultSet.get(j).getToDate());
			
				clientResourcesNew.setTotalAllocation(resourceMapping.get(resultSet.get(j).getEmpName()));
				dtoResultSet.add(clientResourcesNew);
			}
			
			
			
			
			return dtoResultSet;
		} else {
			return null;
		}
	}
	
	
	@Transactional(readOnly = true)
	public List<ResourceCountOnProjectGraphDTO> getResourceCountOnProjectForPieChart() {
		List<ProjectResourceReportPieChartDTO> result = projectResourceAssignRepository.getResourceCountOnProjectForPieChart();
		
		List<ResourceCountOnProjectGraphDTO> resultSet = new ArrayList<ResourceCountOnProjectGraphDTO>();
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				ResourceCountOnProjectGraphDTO newResult = new ResourceCountOnProjectGraphDTO();
				newResult.setProjectName(result.get(i).getProjectName());
				newResult.setResourceCount(result.get(i).getProjectCount());
				resultSet.add(newResult);
			}
			return resultSet;
		} else {
			return null;
		}
	}
	
	 @Transactional(readOnly = true)
		public List<ProjectResourceAssign> searchByQuery(
				String empName, 
				String projectName, 
				String clientName
			
			//String projectType
		) {
	    	System.out.println(empName);
	    	System.out.println(projectName);
	    	System.out.println(clientName);
	    	//System.out.println(projectType);
	    	
	    if(empName != null && !empName.isEmpty()) {
	    		
	    	} else {
	    		empName ="%";
	        	System.out.println("employeeName"+empName);
	    	}
	    	
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
	    /*	if(projectType != null && !projectType.isEmpty()) {
	    		

	    	} else {
	    		projectType ="%";
	        	System.out.println("projectType new"+projectType);
	    	}*/
	    	
			List<ProjectResourceAssign> searchResultForProject = projectResourceAssignRepository.searchByKeywords(empName,projectName,clientName);
			return searchResultForProject;
		}
		
	
}
