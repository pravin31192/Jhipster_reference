package com.sun.prms.service.dto;

import java.time.LocalDate;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class ProjectResourceAssignDTO {
	private Integer id;
	private Integer projectId;
	private LocalDate fromDate;
	private LocalDate toDate;
	private Integer billingType;
	private Integer location;
	private Float estimatedStaffHours;
	private Float  actualStaffHours;
	private MultipleResourcesDTO[]  multipleResources;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
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
	public Integer getBillingType() {
		return billingType;
	}
	public void setBillingType(Integer billingType) {
		this.billingType = billingType;
	}
	public Integer getLocation() {
		return location;
	}
	public void setLocation(Integer location) {
		this.location = location;
	}
	public Float getEstimatedStaffHours() {
		return estimatedStaffHours;
	}
	public void setEstimatedStaffHours(Float estimatedStaffHours) {
		this.estimatedStaffHours = estimatedStaffHours;
	}
	public Float getActualStaffHours() {
		return actualStaffHours;
	}
	public void setActualStaffHours(Float actualStaffHours) {
		this.actualStaffHours = actualStaffHours;
	}
	public MultipleResourcesDTO[] getMultipleResources() {
		return multipleResources;
	}
	public void setMultipleResources(MultipleResourcesDTO[] multipleResources) {
		this.multipleResources = multipleResources;
	}
	
}
