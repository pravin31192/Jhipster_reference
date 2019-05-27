package com.sun.prms.repository;

import com.sun.prms.domain.Billing;
import com.sun.prms.domain.ProjectResourceAssign;
import com.sun.prms.service.dto.BillingInitialReportDTO;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Billing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
	
	@Query(value="SELECT "
			+ "b.project_name as projectName, "
			+ "b.project, "
			+ "b.client_id as clientId, "
			+ "b.client_name as clientName, "
			+ "p.start_date as startDate, "
			+ "p.end_date as endDate, "
			+ "SUM(bill_rate) as total, "
			+ "DATEDIFF(p.end_date,p.start_date) as differenceInDays "
			+ "FROM `billing` b "
			+ "join "
			+ "project as p on p.hrm_project_id = b.project "
			+ "GROUP BY project "
			+ "order by client_id", nativeQuery=true)
	List<BillingInitialReportDTO> getInitialReport();
	
	@Query(value="SELECT b FROM Billing b "
			+ "WHERE (b.resourceName LIKE %?1%) "
			+ "AND (b.projectName LIKE %?2%) "
			+ "AND (b.clientName  LIKE %?3%) ")
			public List<Billing> searchByKeywords(
			String empName,
			String projectName,
			String clientName
			
	);

}
