package com.sun.prms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Billing.
 */
@Entity
@Table(name = "billing")
public class Billing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "project")
    private Integer project;

    @Column(name = "resource")
    private Integer resource;

    @Column(name = "hours_allocated")
    private Double hoursAllocated;

    @Column(name = "bill_rate")
    private Double billRate;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "other_cost")
    private Double otherCost;
    
    @Column(name = "client_name")
    private String clientName;

	@Column(name = "project_name")
    private String projectName;
    
    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public Billing clientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getProject() {
        return project;
    }

    public Billing project(Integer project) {
        this.project = project;
        return this;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Integer getResource() {
        return resource;
    }

    public Billing resource(Integer resource) {
        this.resource = resource;
        return this;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }

    public Double getHoursAllocated() {
        return hoursAllocated;
    }

    public Billing hoursAllocated(Double hoursAllocated) {
        this.hoursAllocated = hoursAllocated;
        return this;
    }

    public void setHoursAllocated(Double hoursAllocated) {
        this.hoursAllocated = hoursAllocated;
    }

    public Double getBillRate() {
        return billRate;
    }

    public Billing billRate(Double billRate) {
        this.billRate = billRate;
        return this;
    }

    public void setBillRate(Double billRate) {
        this.billRate = billRate;
    }

    public Double getSalary() {
        return salary;
    }

    public Billing salary(Double salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public Billing otherCost(Double otherCost) {
        this.otherCost = otherCost;
        return this;
    }

    public void setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
    }
    
    public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Billing createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public Billing updatedAt(LocalDate updatedAt) {
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
        Billing billing = (Billing) o;
        if (billing.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billing.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Billing{" +
            "id=" + getId() +
            ", clientName=" + getClientName() +
            ", project=" + getProject() +
            ", resource=" + getResource() +
            ", hoursAllocated=" + getHoursAllocated() +
            ", billRate=" + getBillRate() +
            ", salary=" + getSalary() +
            ", otherCost=" + getOtherCost() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
