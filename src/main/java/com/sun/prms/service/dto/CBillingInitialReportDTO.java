package com.sun.prms.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class CBillingInitialReportDTO {
	
	private String projectName;
    private Integer project;
    private Integer clientId;
    private String clientName;
    private String startDate;
    private String endDate;
    private BigDecimal total;
    private Integer differenceInDays;
    private Integer fromMonth;
    private Integer toMonth;
    
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Integer getProject() {
		return project;
	}
	public void setProject(Integer project) {
		this.project = project;
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public Integer getDifferenceInDays() {
		return differenceInDays;
	}
	public void setDifferenceInDays(Integer differenceInDays) {
		this.differenceInDays = differenceInDays;
	}
	public Integer getFromMonth() {
		return fromMonth;
	}
	public void setFromMonth(Integer fromMonth) {
		this.fromMonth = fromMonth;
	}
	public Integer getToMonth() {
		return toMonth;
	}
	public void setToMonth(Integer toMonth) {
		this.toMonth = toMonth;
	}
    
	
}
