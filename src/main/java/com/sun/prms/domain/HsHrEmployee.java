package com.sun.prms.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Roles.
 */
@Entity
@Table(name = "sunhrm_jan.hs_hr_employee")
public class HsHrEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "emp_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empNumber;

    @Column(name = "emp_lastname")
    private String empLastname;
    
    @Column(name = "emp_firstname")
    private String empFirstname;

    @Column(name = "termination_id")
    private Integer terminationId;

    @Column(name = "emp_work_email")
    private String empWorkEmail;

    @Column(name = "work_station")
    private Integer workStation;

    @Column(name = "employee_id")
    private Integer employeeId;
    
    
    // -----End of Variable declarations -----------------------
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Integer getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(Integer empNumber) {
        this.empNumber = empNumber;
    }
    
    // 2) ---------------------------------
    public String getEmpFirstname() {
        return empFirstname;
    }

    public HsHrEmployee empFirstname(String empFirstname) {
        this.empFirstname = empFirstname;
        return this;
    }

    public void setEmpFirstname(String empFirstname) {
        this.empFirstname = empFirstname;
    }
    
    // 3) ---------------------------------
    public String getEmpLastname() {
        return empLastname;
    }

    public HsHrEmployee empLastname(String empLastname) {
        this.empLastname = empLastname;
        return this;
    }

    public void setName(String empLastname) {
        this.empLastname = empLastname;
    }
    
    // 4) ------------------------------------------
    public Integer getTerminationId() {
        return terminationId;
    }

    public HsHrEmployee terminationId(Integer terminationId) {
        this.terminationId = terminationId;
        return this;
    }

    public void setTerminationId(Integer terminationId) {
        this.terminationId = terminationId;
    }
    
    // 5)-------------------------------------------------
    public String getEmpWorkEmail() {
        return empWorkEmail;
    }

    public HsHrEmployee empWorkEmail(String empWorkEmail) {
        this.empWorkEmail = empWorkEmail;
        return this;
    }

    public void setCreatedAt(String empWorkEmail) {
        this.empWorkEmail = empWorkEmail;
    }
    
    // 6) ----------------------------------------
    public Integer getWorkStation() {
        return workStation;
    }

    public HsHrEmployee workStation(Integer workStation) {
        this.workStation = workStation;
        return this;
    }

    public void setWorkStation(Integer workStation) {
        this.workStation = workStation;
    }
    // 7)------------------------------------
    public Integer getEmployeeId() {
        return employeeId;
    }

    public HsHrEmployee employeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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
        HsHrEmployee roles = (HsHrEmployee) o;
        if (roles.getEmpNumber() == null || getEmpNumber() == null) {
            return false;
        }
        return Objects.equals(getEmpNumber(), roles.getEmpNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmpNumber());
    }

    @Override
    public String toString() {
        return "Roles{" +
            "emp_number=" + getEmpNumber() +
            ", emp_firstname='" + getEmpFirstname() + "'" +
            ", emp_lastname='" + getEmpLastname() + "'" +
            ", work_station='" + getWorkStation() + "'" +
            ", termination_id='" + getTerminationId() + "'" +
            ", emp_work_email='" + getEmpWorkEmail() + "'" +
            "}";
    }
}
