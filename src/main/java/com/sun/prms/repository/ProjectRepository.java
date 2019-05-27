package com.sun.prms.repository;

import com.sun.prms.domain.Project;
import com.sun.prms.domain.ProjectModelForSearch;
import com.sun.prms.domain.ProjectResourceAssign;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query(value = "SELECT * FROM project ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Project findTopByOrderByIdDesc(Project p);

	Project findByHrmProjectId(Integer projectId);
	
	/*@Query(value="SELECT * FROM project WHERE "
			+ "project_name LIKE '%?1%' "
			+ "OR client_name LIKE '%?2%' "
			+ "OR status = ?3 "
			+ "OR type = ?4 ", nativeQuery=true)
	List<Project> searchByKeywords(String projectName, String clientName, Integer projectStatus, Integer projectType);*/
	
	//Working Search criteria
	/*@Query(value="SELECT p FROM Project p "
			+ "WHERE (p.projectName LIKE %?1%) "
			+ "AND (p.clientName  LIKE %?2%) "
			+ "AND cast(p.status as string) like %?3%   "
			+ "AND cast(p.type as string) LIKE %?4%")
	public List<Project> searchByKeywords(
			String projectName,
			String clientName,
			String projectStatus,
			String projectType
	);*/
	
	@Query(value="SELECT p FROM ProjectModelForSearch p "
			+ "WHERE (p.projectName LIKE %?1%) "
			+ "AND (p.clientName  LIKE %?2%) "
			+ "AND cast(p.status as string) like %?3%   "
			+ "AND cast(p.type as string) LIKE %?4% "
			+ "AND cast(p.percentageComplete as string) LIKE %?5%") 
			
	public List<ProjectModelForSearch> searchByKeywords(
			String projectName,
			String clientName,
			String projectStatus,
			String projectType,
			String percentageComplete
	);
	
	
//	+ "WHERE (p.projectName LIKE ':projectName%' OR :projectName IS NULL or :projectName = '') "
//	+ "AND (p.clientName  LIKE ':clientName%' OR :clientName IS NULL or :clientName = '') "
//	+ "AND (p.status = :projectStatus OR :projectStatus IS NULL or :projectStatus = '') "
//	+ "AND (p.type = :projectType OR :projectType IS NULL or :projectType = '')")
	
//	+ "WHERE (p.projectName LIKE :projectName) "
//	+ "AND (p.clientName  LIKE :clientName) "
//	+ "AND (p.status = :projectStatus "
//	+ "AND (p.type = :projectType")
	
	
	
}
