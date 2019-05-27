package com.sun.prms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Roles.
 */
@Entity
@Table(name = "sunhrm_jan.ohrm_timesheet")
public class HrmTimesheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "timesheet_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "state")
    private String state;

/*    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;*/

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    // -----
    public String getState() {
        return state;
    }

    public HrmTimesheet state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

   /* public String getDescription() {
        return description;
    }

    public HrmTimesheet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public HrmTimesheet createdAt(LocalDate createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public HrmTimesheet updatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }*/
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrmTimesheet roles = (HrmTimesheet) o;
        if (roles.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roles.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Roles{" +
            "id=" + getId() +
            /*", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +*/
            "}";
    }
}
