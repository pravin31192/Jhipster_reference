package com.sun.prms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Roles.
 */
@Entity
@Table(name = "sunhrm_jan.ohrm_project")
public class HrmOhrmProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    @Column(name = "name")
    private String name;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "is_deleted")
    private Integer isDeleted;



    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    //----------------------------
    public String getName() {
        return name;
    }

    public HrmOhrmProject name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    // -------------------------------------
    public Integer getCustomerId() {
        return customerId;
    }

    public HrmOhrmProject customerId(Integer customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    // ----------------------------------------
    public Integer getIsDeleted() {
        return isDeleted;
    }

    public HrmOhrmProject isDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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
        HrmOhrmProject roles = (HrmOhrmProject) o;
        if (roles.getProjectId() == null || getProjectId() == null) {
            return false;
        }
        return Objects.equals(getProjectId(), roles.getProjectId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProjectId());
    }

    @Override
    public String toString() {
        return "Roles{" +
            "id=" + getProjectId() +
            ", name='" + getName() + "'" +
            ", client_id='" + getCustomerId() + "'" +
            ", is_deleted='" + getIsDeleted() + "'" +
            "}";
    }
}
