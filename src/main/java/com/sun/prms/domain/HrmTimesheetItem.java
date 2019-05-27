package com.sun.prms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Roles.
 */
@Entity
@Table(name = "sunhrm_jan.ohrm_timesheet_item")
public class HrmTimesheetItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "timesheet_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer timesheetItemId;

    @Column(name = "timesheet_id")
    private Integer timesheetId;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "employee_id")
    private Integer employeeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Integer getTimesheetItemId() {
        return timesheetItemId;
    }

    public void setTimesheetItemId(Integer timesheetItemId) {
        this.timesheetItemId = timesheetItemId;
    }
    
    // --------------------------------------------------

    public Integer getTimesheetId() {
        return timesheetId;
    }

    public HrmTimesheetItem timesheetId(Integer timesheetId) {
        this.timesheetId = timesheetId;
        return this;
    }

    public void setTimesheetId(Integer timesheetId) {
        this.timesheetId = timesheetId;
    }
    // -----------------------------------
    public Integer getDuration() {
        return duration;
    }

    public HrmTimesheetItem duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    // -----------------------------------
    public Integer getProjectId() {
        return projectId;
    }

    public HrmTimesheetItem projectId(Integer projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    // -----------------------------
    public Integer getEmployeeId() {
        return employeeId;
    }

    public HrmTimesheetItem employeeId(Integer employeeId) {
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
        HrmTimesheetItem roles = (HrmTimesheetItem) o;
        if (roles.getTimesheetItemId() == null || getTimesheetItemId() == null) {
            return false;
        }
        return Objects.equals(getTimesheetItemId(), roles.getTimesheetItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTimesheetItemId());
    }

    @Override
    public String toString() {
        return "Roles{" +
            "timesheet_item_id=" + getTimesheetItemId() +
            ", timesheet_id='" + getTimesheetId() + "'" +
            ", duration='" + getDuration() + "'" +
            ", project_id='" + getProjectId() + "'" +
            ", employee_id='" + getEmployeeId() + "'" +
            "}";
    }
}