package com.sun.prms.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO representing a password change required data - current and new password.
 */
public interface BillingInitialReportDTO {
	
	public String getProjectName();
	public Integer getProject();
	public Integer getClientId();
	public String getClientName();
	public String getStartDate();
	public String getEndDate();
	public BigDecimal getTotal();
	public Integer getDifferenceInDays();
	
	/*public Integer getProject() {
		return project;
	}*/
	
}
