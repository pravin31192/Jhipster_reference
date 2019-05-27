package com.sun.prms.service.dto;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class MultipleResourcesDTO {
    private Integer role;
    private Float billValue;
    private Float percentageOfWork;
    private Integer empId;
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public Float getBillValue() {
		return billValue;
	}
	public void setBillValue(Float billValue) {
		this.billValue = billValue;
	}
	public Float getPercentageOfWork() {
		return percentageOfWork;
	}
	public void setPercentageOfWork(Float percentageOfWork) {
		this.percentageOfWork = percentageOfWork;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
}
