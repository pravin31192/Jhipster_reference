package com.sun.prms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A EmployeeSalary.
 */
@Entity
@Table(name = "employee_salary")
public class EmployeeSalary implements Serializable {

   
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Integer employeeId;
    
    @Column(name="employee_name")
    private String employeeName;
    
    @Column(name="department_name")
    private String departmentName;

	@Column(name = "salary")
    private Float salary;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "created_by")
    private Integer createdBy;
    
    @Transient
    private int departmentId;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }
    
    public EmployeeSalary employeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	 public String getDepartmentName() {
		return departmentName;
	}
	 
	 

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
		
    public Float getSalary() {
        return salary;
    }

    public EmployeeSalary salary(Float salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public EmployeeSalary createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public EmployeeSalary updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public EmployeeSalary createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    /*@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmployeeSalary employeeSalary = (EmployeeSalary) o;
        if (employeeSalary.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeSalary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeSalary{" +
            "id=" + getId() +
            ", employeeId=" + getEmployeeId() +
            ", salary=" + getSalary() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", createdBy=" + getCreatedBy() +
            "}";
    }*/
}
