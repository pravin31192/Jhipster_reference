package com.sun.prms.service.dto;

import java.time.LocalDate;

/**
 * A DTO representing Resources of Clients.
 */
public class ResourcesOfClientDTO {
    public String employeeName;
    public String projectName;
    public Float percentageOfAllocation;
    public Float billValue;
    public LocalDate fromDate;
    public LocalDate toDate;
    public Float totalAllocation;
    
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Float getPercentageOfAllocation() {
		return percentageOfAllocation;
	}
	public void setPercentageOfAllocation(Float percentageOfAllocation) {
		this.percentageOfAllocation = percentageOfAllocation;
	}
	public Float getBillValue() {
		return billValue;
	}
	public void setBillValue(Float billValue) {
		this.billValue = billValue;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public Float getTotalAllocation() {
		return totalAllocation;
	}
	public void setTotalAllocation(Float totalAllocation) {
		this.totalAllocation = totalAllocation;
	}
    
    
}
