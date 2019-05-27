package com.sun.prms.domain;


import javax.persistence.*;

import org.hibernate.annotations.CascadeType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
public class ProjectModelForSearch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "project_name")
    private String projectName;
    
    @Column(name = "hrm_project_id")
    private Integer hrmProjectId;

	@Column(name = "client_name")
    private String clientName;
	
	@Column(name = "hrm_client_id")
    private Integer hrmClientId;

	@Column(name = "client_code")
    private String clientCode;

    @Column(name = "project_code")
    private String projectCode;

    @Column(name = "billable_value")
    private Float billableValue;

    @Column(name = "no_of_resource")
    private Integer noOfResources;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "status")
    private Integer status;

    @Column(name = "type")
    private Integer type;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "estimated_staff_hours")
    private Float estimatedStaffHours;

    @Column(name = "actual_staff_hours")
    private Float actualStaffHours;

    @Column(name = "percentage_complete")
    private String percentageComplete;

    @Column(name = "details")
    private String details;

    @Column(name = "deliverables")
    private String deliverables;

    @Column(name = "attachments")
    private String attachments;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
    
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

	public String getProjectName() {
        return projectName;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ProjectModelForSearch projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    // ----------
    public Integer getHrmProjectId() {
		return hrmProjectId;
	}

	public void setHrmProjectName(Integer hrmProjectId) {
		this.hrmProjectId = hrmProjectId;
	}
	// ----------------
    public String getClientName() {
        return clientName;
    }

    public ProjectModelForSearch clientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    // ------------------------------------
    public Integer getHrmClientId() {
		return hrmClientId;
	}

	public void setHrmClientId(Integer hrmClientId) {
		this.hrmClientId = hrmClientId;
	}
	// ========================================

    public String getClientCode() {
        return clientCode;
    }

    public ProjectModelForSearch clientCode(String clientCode) {
        this.clientCode = clientCode;
        return this;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public ProjectModelForSearch projectCode(String projectCode) {
        this.projectCode = projectCode;
        return this;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Float getBillableValue() {
        return billableValue;
    }

    public ProjectModelForSearch billableValue(Float billableValue) {
        this.billableValue = billableValue;
        return this;
    }

    public void setBillableValue(Float billableValue) {
        this.billableValue = billableValue;
    }

    public Integer getNoOfResources() {
        return noOfResources;
    }

    public ProjectModelForSearch noOfResources(Integer noOfResources) {
        this.noOfResources = noOfResources;
        return this;
    }

    public void setNoOfResources(Integer noOfResources) {
        this.noOfResources = noOfResources;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public ProjectModelForSearch createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getStatus() {
        return status;
    }

    public ProjectModelForSearch status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public ProjectModelForSearch type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ProjectModelForSearch startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ProjectModelForSearch endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Float getEstimatedStaffHours() {
        return estimatedStaffHours;
    }

    public ProjectModelForSearch estimatedStaffHours(Float estimatedStaffHours) {
        this.estimatedStaffHours = estimatedStaffHours;
        return this;
    }

    public void setEstimatedStaffHours(Float estimatedStaffHours) {
        this.estimatedStaffHours = estimatedStaffHours;
    }

    public Float getActualStaffHours() {
        return actualStaffHours;
    }

    public ProjectModelForSearch actualStaffHours(Float actualStaffHours) {
        this.actualStaffHours = actualStaffHours;
        return this;
    }

    public void setActualStaffHours(Float actualStaffHours) {
        this.actualStaffHours = actualStaffHours;
    }

    public String getPercentageComplete() {
        return percentageComplete;
    }

    public ProjectModelForSearch percentageComplete(String percentageComplete) {
        this.percentageComplete = percentageComplete;
        return this;
    }

    public void setPercentageComplete(String percentageComplete) {
    	
                 	 this.percentageComplete = percentageComplete;	 
         
    	    }

    public String getDetails() {
        return details;
    }

    public ProjectModelForSearch details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDeliverables() {
        return deliverables;
    }

    public ProjectModelForSearch deliverables(String deliverables) {
        this.deliverables = deliverables;
        return this;
    }

    public void setDeliverables(String deliverables) {
        this.deliverables = deliverables;
    }

    public String getAttachments() {
        return attachments;
    }

    public ProjectModelForSearch attachments(String attachments) {
        this.attachments = attachments;
        return this;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public ProjectModelForSearch createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public ProjectModelForSearch updatedAt(LocalDate updatedAt) {
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
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", projectName='" + getProjectName() + "'" +
            ", hrmProjectId='" + getHrmProjectId() + "'" +
            ", clientName='" + getClientName() + "'" +
            ", clientCode='" + getClientCode() + "'" +
            ", projectCode='" + getProjectCode() + "'" +
            ", billableValue=" + getBillableValue() +
            ", noOfResources=" + getNoOfResources() +
            ", createdBy=" + getCreatedBy() +
            ", status=" + getStatus() +
            ", type=" + getType() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", estimatedStaffHours=" + getEstimatedStaffHours() +
            ", actualStaffHours=" + getActualStaffHours() +
            ", percentageComplete=" + getPercentageComplete() +
            ", details='" + getDetails() + "'" +
            ", deliverables='" + getDeliverables() + "'" +
            ", attachments='" + getAttachments() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
