package com.sun.prms.service;

import com.sun.prms.domain.Billing;
import com.sun.prms.domain.EmployeeSalary;
import com.sun.prms.domain.HrmSubUnit;
import com.sun.prms.domain.HrmTimesheetItem;
import com.sun.prms.domain.HsHrEmployee;
import com.sun.prms.domain.Project;
import com.sun.prms.domain.ProjectResourceAssign;
import com.sun.prms.domain.RoleUserMapping;
import com.sun.prms.repository.BillingRepository;
import com.sun.prms.repository.EmployeeSalaryRepository;
import com.sun.prms.repository.HrmHsHrEmployeeRepository;
import com.sun.prms.repository.HrmOhrmSubunitRepository;
import com.sun.prms.repository.HrmTimesheetItemRepository;
import com.sun.prms.repository.ProjectRepository;
import com.sun.prms.repository.ProjectResourceAssignRepository;
import com.sun.prms.repository.RoleUserMappingRepository;
import com.sun.prms.service.dto.BillingInitialReportDTO;
import com.sun.prms.service.dto.CBillingInitialReportDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing Billing.
 */
@Service
@Transactional
public class BillingService {

    private final Logger log = LoggerFactory.getLogger(BillingService.class);

    private final BillingRepository billingRepository;
    private final ProjectResourceAssignRepository projectResourceAssignRepository;
    private final HrmTimesheetItemRepository hrmTimesheetItemRepository;
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final ProjectRepository projectRepository;
    private final RoleUserMappingRepository roleUserMappingRepository;
    private final HrmOhrmSubunitRepository hrmOhrmSubunitRepository;
    private final HrmHsHrEmployeeRepository hrmHsHrEmployeeRepository;

    public BillingService(BillingRepository billingRepository, 
    	ProjectResourceAssignRepository projectResourceAssignRepository, 
    	HrmTimesheetItemRepository hrmTimesheetItemRepository,
    	EmployeeSalaryRepository employeeSalaryRepository,
    	ProjectRepository projectRepository,
    	RoleUserMappingRepository roleUserMappingRepository,
    	HrmOhrmSubunitRepository hrmOhrmSubunitRepository,
    	HrmHsHrEmployeeRepository hrmHsHrEmployeeRepository
    ) {
        this.billingRepository = billingRepository;
        this.projectResourceAssignRepository = projectResourceAssignRepository;
        this.hrmTimesheetItemRepository = hrmTimesheetItemRepository;
        this.employeeSalaryRepository = employeeSalaryRepository;
        this.projectRepository = projectRepository;
        this.roleUserMappingRepository = roleUserMappingRepository;
        this.hrmOhrmSubunitRepository = hrmOhrmSubunitRepository;
        this.hrmHsHrEmployeeRepository = hrmHsHrEmployeeRepository;
    }

    /**
     * Save a billing.
     *
     * @param billing the entity to save
     * @return the persisted entity
     */
    public Billing save(Billing billing) {
        log.debug("Request to save Billing : {}", billing);
        // To save 1) project name 2) resource name 3) ClientName
        // 1) save project name
        String projectName = null;
        Project selectedProject = projectRepository.findByHrmProjectId(billing.getProject());
        projectName = selectedProject.getProjectName();
        billing.setProjectName(projectName);
        // 2) save resource name
        HsHrEmployee employeeRow = hrmHsHrEmployeeRepository.findByEmpNumber(billing.getResource());
        String empName = employeeRow.getEmpFirstname()+" "+employeeRow.getEmpLastname();
        billing.setResourceName(empName);
        // 3) Save the client name
        billing.setClientName(selectedProject.getClientName());
        return billingRepository.save(billing);
    }

    /**
     * Get all the billings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Billing> findAll(Pageable pageable) {
        log.debug("Request to get all Billings");
        return billingRepository.findAll(pageable);
    }


    /**
     * Get one billing by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Billing> findOne(Long id) {
        log.debug("Request to get Billing : {}", id);
        return billingRepository.findById(id);
    }

    /**
     * Delete the billing by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Billing : {}", id);
        billingRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<ProjectResourceAssign> resourcesOnProjectService(Integer projectId) {
    	Project project = projectRepository.findByHrmProjectId(projectId);
    	if (project != null) {
    		Integer prmsProjectId = project.getId();
    		return projectResourceAssignRepository.findProjectResources(prmsProjectId);
    	} else {
    		return null;
    	}
    }
    
    
    @Transactional(readOnly = true)
    public Integer getTotalWorkedHours(Integer projectId, Integer resourceId) {
        // Get the sunhrm-project-id which is already saved in the project table.
    	/*Project project = projectRepository.getOne(projectId);
    	projectId = project.getHrmProjectId();*/
    	Integer value = hrmTimesheetItemRepository.totalHoursOfResource(projectId, resourceId);
    	if (value != null) {
    		value = (value/60) / 60;
    	} else {
    		value = 0;
    	}
    	value = (int) (Math.round(value * 100.0) / 100.0);
    	return value;
    	// return hrmTimesheetItemRepository.totalHoursOfResource(projectId, resourceId);
    }
    
    @Transactional(readOnly = true)
    public EmployeeSalary getSalaryOfResource(Integer resourceId) {
    	 return employeeSalaryRepository.findByEmployeeId(resourceId);
    }
    
    @Transactional(readOnly = true)
    public List<HrmSubUnit> getSubunitsInHrm() {
    	 return hrmOhrmSubunitRepository.findAll();
    }
    
    @Transactional(readOnly = true)
	public List<CBillingInitialReportDTO> getInitialBillingReport() {
    	List<BillingInitialReportDTO> fromRepo = billingRepository.getInitialReport(); 
		List<CBillingInitialReportDTO> toBeSent = new ArrayList<CBillingInitialReportDTO>();
		if (fromRepo != null) {
			for (int i = 0; i < fromRepo.size(); i++) {
				CBillingInitialReportDTO row = new CBillingInitialReportDTO();
				BillingInitialReportDTO resultRow = fromRepo.get(i);
				row.setProjectName(resultRow.getProjectName());
				row.setProject(resultRow.getProject());
				row.setClientId(resultRow.getClientId());
				row.setClientName(resultRow.getClientName());
				row.setStartDate(resultRow.getStartDate());
				row.setEndDate(resultRow.getEndDate());
				row.setTotal(resultRow.getTotal());
				row.setDifferenceInDays(resultRow.getDifferenceInDays());
				
				String startDateString = resultRow.getStartDate();
				String endDateString = resultRow.getEndDate();
				DateFormat newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar calendar = new GregorianCalendar();
				Calendar endDateCalendar = new GregorianCalendar();
				Date date = null;
				Date endDate = null;
				try {
					date = newDate.parse(startDateString);
					endDate = newDate.parse(endDateString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				calendar.setTime(date);
				endDateCalendar.setTime(endDate);
				
				Integer startMonth = calendar.get(Calendar.MONTH) + 1;
				Integer endMonth = endDateCalendar.get(Calendar.MONTH) + 1;
				row.setFromMonth(startMonth);
				row.setToMonth(endMonth);
				// System.out.println(resultRow.getStartDate());
				toBeSent.add(row);
			}
		}
		
		/*Integer clientId = toBeSent.get(0).getClientId();
		for (CBillingInitialReportDTO temp : toBeSent) {
			if (temp.getClientId() == clientId) {
				
			}
		}*/
		
		
		return toBeSent;
	}

	public Float getBillValueOfResource(Integer project, Integer resource) {
		Project projectRow = projectRepository.findByHrmProjectId(project);
		Integer prmsProjectId = projectRow.getId();
		return projectResourceAssignRepository.getBillValueOfResource(prmsProjectId, resource);
	}
	
	 @Transactional(readOnly = true)
		public List<Billing> searchByQuery(
				String resourceName, 
				String projectName, 
				String clientName
			
			//String projectType
		) {
	    	System.out.println(resourceName);
	    	System.out.println(projectName);
	    	System.out.println(clientName);
	    	//System.out.println(projectType);
	    	
	    if(resourceName != null && !resourceName.isEmpty()) {
	    		
	    	} else {
	    		resourceName ="%";
	        	System.out.println("resourceName"+resourceName);
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
	    	
			List<Billing> searchResultForProject = billingRepository.searchByKeywords(resourceName,projectName,clientName);
			return searchResultForProject;
		}
		
}
