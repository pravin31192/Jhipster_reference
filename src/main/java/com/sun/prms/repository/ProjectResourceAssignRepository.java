package com.sun.prms.repository;

import com.sun.prms.domain.HrmOhrmProject;
import com.sun.prms.domain.ProjectResourceAssign;
import com.sun.prms.service.dto.ProjectResourceReportPieChartDTO;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectResourceAssign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectResourceAssignRepository extends JpaRepository<ProjectResourceAssign, Integer> {
	@Query(value= "Select * from project_resource_assign where status = 1 and project_id=?1", nativeQuery = true)
	public List<ProjectResourceAssign> findProjectResources(Integer projectId);
	
	@Query(value="SELECT p from ProjectResourceAssign p where p.projectId= :projectId and p.empId= :resourceId and p.role= :roleId and status=1")
	public ProjectResourceAssign checkRoleResourceProjectAlreadyAssigned(
			@Param("resourceId") Integer resourceId,
			@Param("projectId") Integer projectId,
			@Param("roleId") Integer roleId
	);
	
	@Query(value="select bill_value from project_resource_assign where project_id = ?1 and emp_id = ?2", nativeQuery=true)
	Float getBillValueOfResource(Integer project, Integer resource);

	public List<ProjectResourceAssign> findByProjectId(Integer projectId);
	
	@Query(value="select sum(percentage_of_work) from project_resource_assign where emp_id = ?1 and status=1", nativeQuery=true)
	public Integer getAllocatedPercentage(Integer selectedResource);
	
	// @Query(value="SELECT id,project_id,emp_id,role,from_date,to_date,billing_type,bill_value,SUM(percentage_of_work) as percentage_of_work,project_name,emp_name,role_name,client_name,actual_staff_hours,estimated_staff_hours,location,status,created_at,updated_at FROM project_resource_assign WHERE status = 1 "+ "GROUP BY emp_id HAVING SUM(percentage_of_work) BETWEEN ?1 AND ?2", nativeQuery=true)
	// public List<ProjectResourceAssign> resourcesOnAvailability(Integer from, Integer to);

	@Query(value="SELECT id, project_id,emp_id, role, from_date,to_date, billing_type, bill_value, SUM(percentage_of_work) as percentage_of_work, project_name, emp_name,role_name,client_name,actual_staff_hours,estimated_staff_hours,location,status,created_at,updated_at FROM (SELECT"+
	"(CASE WHEN prms.project_resource_assign.id is null THEN sunhrm_jan.hs_hr_employee.emp_number else prms.project_resource_assign.id  END) as id,"+
	"(CASE WHEN prms.project_resource_assign.project_id IS null THEN 0 else prms.project_resource_assign.project_id END) as project_id,"+
	"(CASE WHEN prms.project_resource_assign.emp_id is null THEN sunhrm_jan.hs_hr_employee.emp_number else prms.project_resource_assign.emp_id  END) as emp_id,"+
	"(CASE WHEN prms.project_resource_assign.role IS null THEN null else prms.project_resource_assign.role END) as role,"+
	"(CASE WHEN prms.project_resource_assign.from_date IS null THEN null else prms.project_resource_assign.from_date END) as from_date,"+
	"(CASE WHEN prms.project_resource_assign.to_date IS null THEN null else prms.project_resource_assign.to_date END) as to_date,"+
	"(CASE WHEN prms.project_resource_assign.billing_type IS null THEN null else prms.project_resource_assign.billing_type END) as billing_type,"+
	"(CASE WHEN prms.project_resource_assign.bill_value IS null THEN 0 else prms.project_resource_assign.bill_value END) as bill_value,"+
	"(CASE WHEN prms.project_resource_assign.percentage_of_work IS null THEN 0 else prms.project_resource_assign.percentage_of_work END) as percentage_of_work,"+
	"(CASE WHEN prms.project_resource_assign.project_name IS null THEN null else prms.project_resource_assign.project_name END) as project_name,"+
	"(CASE WHEN prms.project_resource_assign.emp_id is null THEN concat(sunhrm_jan.hs_hr_employee.emp_firstname,' ',sunhrm_jan.hs_hr_employee.emp_lastname) else prms.project_resource_assign.emp_name  END) as emp_name,"+
	"(CASE WHEN prms.project_resource_assign.role_name IS null THEN null else prms.project_resource_assign.role_name END) as role_name,"+
	"(CASE WHEN prms.project_resource_assign.client_name IS null THEN null else prms.project_resource_assign.client_name END) as client_name,"+
	"(CASE WHEN prms.project_resource_assign.actual_staff_hours IS null THEN 0 else prms.project_resource_assign.actual_staff_hours END) as actual_staff_hours,"+
	"(CASE WHEN prms.project_resource_assign.estimated_staff_hours IS null THEN 0 else prms.project_resource_assign.estimated_staff_hours END) as estimated_staff_hours,"+
	"(CASE WHEN prms.project_resource_assign.location IS null THEN null else prms.project_resource_assign.location END) as location,"+
	"(CASE WHEN prms.project_resource_assign.status IS null THEN 1 else prms.project_resource_assign.status END) as status,"+
	"(CASE WHEN prms.project_resource_assign.created_at IS null THEN null else prms.project_resource_assign.created_at END) as created_at,"+
	"(CASE WHEN prms.project_resource_assign.updated_at IS null THEN null else prms.project_resource_assign.updated_at END) as updated_at "+
	"FROM prms.project_resource_assign RIGHT OUTER JOIN sunhrm_jan.hs_hr_employee ON prms.project_resource_assign.emp_id = sunhrm_jan.hs_hr_employee.emp_number) as T "+
	"WHERE status = 1 "+
	"GROUP BY emp_id HAVING percentage_of_work BETWEEN ?1 AND ?2",nativeQuery=true)
	public List<ProjectResourceAssign> resourcesOnAvailability(Integer from, Integer to);

	@Query(value="SELECT emp_number as emp_id FROM sunhrm_jan.hs_hr_employee WHERE emp_number NOT IN (SELECT emp_id FROM prms.project_resource_assign WHERE percentage_of_work BETWEEN ?1 AND ?2)",nativeQuery=true)
	public List<ProjectResourceAssign> resourcesOnAvailability2(Integer from, Integer to);

	@Query(value="SELECT * FROM project_resource_assign WHERE status = 1 and client_name = ?1 ORDER BY emp_id DESC", nativeQuery=true)
	public List<ProjectResourceAssign> getResourcesByClient(String projectName);
	
	@Query(value="SELECT project_name as projectName, COUNT(1) as projectCount FROM project_resource_assign  WHERE status = 1 GROUP BY project_id", nativeQuery=true)
	public List<ProjectResourceReportPieChartDTO> getResourceCountOnProjectForPieChart();
	
	@Query(value="SELECT p FROM ProjectResourceAssign p "
			+ "WHERE (p.empName LIKE %?1%) "
			+ "AND (p.projectName LIKE %?2%) "
			+ "AND (p.clientName  LIKE %?3%) ")
			public List<ProjectResourceAssign> searchByKeywords(
			String empName,
			String projectName,
			String clientName
			
	);
	
	@Modifying
	@Query(value="UPDATE project_resource_assign SET status = 2 WHERE id = ?1", nativeQuery=true)
	public void releaseFromProject(Integer id);
}
