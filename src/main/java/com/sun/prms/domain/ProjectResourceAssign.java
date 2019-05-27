package com.sun.prms.domain;


import javax.persistence.*;

import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ProjectResourceAssign.
 */
@Entity
@Table(name = "project_resource_assign")
public class ProjectResourceAssign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "emp_id")
    private Integer empId;

    @Column(name = "role")
    private Integer role;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "billing_type")
    private Integer billingType;

    @Column(name = "bill_value")
    private Float billValue;

    @Column(name = "percentage_of_work")
    private Float percentageOfWork;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "emp_name")
    private String empName;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "estimated_staff_hours")
    private Float estimatedStaffHours;
    
    @Column(name = "actual_staff_hours")
    private Float actualStaffHours;

    @Column(name = "location")
    private Integer location;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
    
    @javax.persistence.Transient
	private Float available;
    
	@javax.persistence.Transient
	public Integer projectCount;
    
    @javax.persistence.Transient
    public Float getAvailable() {
		this.available = 100 - percentageOfWork ;
		return available;
	}

	public void setAvailable(Float available) {
		this.available = available;
	}
	
	@javax.persistence.Transient
    public Integer getProjectCount() {
		return projectCount;
	}

	public void setProjectCount(Integer projectCount) {
		this.projectCount = projectCount;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public ProjectResourceAssign projectId(Integer projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public ProjectResourceAssign empId(Integer empId) {
        this.empId = empId;
        return this;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getRole() {
        return role;
    }

    public ProjectResourceAssign role(Integer role) {
        this.role = role;
        return this;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public ProjectResourceAssign fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public ProjectResourceAssign toDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Integer getBillingType() {
        return billingType;
    }

    public ProjectResourceAssign billingType(Integer billingType) {
        this.billingType = billingType;
        return this;
    }

    public void setBillingType(Integer billingType) {
        this.billingType = billingType;
    }

    public Float getBillValue() {
        return billValue;
    }

    public ProjectResourceAssign billValue(Float billValue) {
        this.billValue = billValue;
        return this;
    }

    public void setBillValue(Float billValue) {
        this.billValue = billValue;
    }

    public Float getPercentageOfWork() {
        return percentageOfWork;
    }

    public ProjectResourceAssign percentageOfWork(Float percentageOfWork) {
        this.percentageOfWork = percentageOfWork;
        return this;
    }

    public void setPercentageOfWork(Float percentageOfWork) {
        this.percentageOfWork = percentageOfWork;
    }

    public String getProjectName() {
        return projectName;
    }

    public ProjectResourceAssign projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEmpName() {
        return empName;
    }

    public ProjectResourceAssign empName(String empName) {
        this.empName = empName;
        return this;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getRoleName() {
        return roleName;
    }

    public ProjectResourceAssign roleName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getClientName() {
        return clientName;
    }

    public ProjectResourceAssign clientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Float getEstimatedStaffHours() {
        return estimatedStaffHours;
    }

    public ProjectResourceAssign estimatedStaffHours(Float estimatedStaffHours) {
        this.estimatedStaffHours = estimatedStaffHours;
        return this;
    }

    public void setEstimatedStaffHours(Float estimatedStaffHours) {
        this.estimatedStaffHours = estimatedStaffHours;
    }

    public Float getActualStaffHours() {
        return actualStaffHours;
    }

    public ProjectResourceAssign actualStaffHours(Float actualStaffHours) {
        this.actualStaffHours = actualStaffHours;
        return this;
    }

    public void setActualStaffHours(Float actualStaffHours) {
        this.actualStaffHours = actualStaffHours;
    }

    public Integer getLocation() {
        return location;
    }

    public ProjectResourceAssign location(Integer location) {
        this.location = location;
        return this;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Integer getStatus() {
        return status;
    }

    public ProjectResourceAssign status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public ProjectResourceAssign createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public ProjectResourceAssign updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectResourceAssign projectResourceAssign = (ProjectResourceAssign) o;
        if (projectResourceAssign.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectResourceAssign.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

	@Override
	public String toString() {
		return "ProjectResourceAssign [id=" + id + ", projectId=" + projectId + ", empId=" + empId + ", role=" + role
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", billingType=" + billingType + ", billValue="
				+ billValue + ", percentageOfWork=" + percentageOfWork + ", projectName=" + projectName + ", empName="
				+ empName + ", roleName=" + roleName + ", clientName=" + clientName + ", estimatedStaffHours="
				+ estimatedStaffHours + ", actualStaffHours=" + actualStaffHours + ", location=" + location
				+ ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", available="
				+ available + "]";
	}

	/*@Override
	public String toString() {
		return "ProjectResourceAssign [id=" + id + ", projectId=" + projectId + ", empId=" + empId + ", role=" + role
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", billingType=" + billingType + ", billValue="
				+ billValue + ", percentageOfWork=" + percentageOfWork + ", projectName=" + projectName + ", empName="
				+ empName + ", roleName=" + roleName + ", clientName=" + clientName + ", estimatedStaffHours="
				+ estimatedStaffHours + ", actualStaffHours=" + actualStaffHours + ", location=" + location
				+ ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}*/


	
	

  /*  @Override
    public String toString() {
        return "ProjectResourceAssign{" +
            "id=" + getId() +
            ", projectId=" + getProjectId() +
            ", empId=" + getEmpId() +
            ", role=" + getRole() +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", billingType=" + getBillingType() +
            ", billValue=" + getBillValue() +
            ", percentageOfWork=" + getPercentageOfWork() +
            ", projectName='" + getProjectName() + "'" +
            ", empName='" + getEmpName() + "'" +
            ", roleName='" + getRoleName() + "'" +
            ", clientName='" + getClientName() + "'" +
            ", estimatedStaffHours=" + getEstimatedStaffHours() +
            ", actualStaffHours=" + getActualStaffHours() +
            ", location=" + getLocation() +
            ", status=" + getStatus() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }*/
    
    
}
