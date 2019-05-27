package com.sun.prms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Roles.
 */
@Entity
@Table(name = "sunhrm_jan.ohrm_subunit")
public class HrmSubUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    // ------------
    public String getName() {
        return name;
    }

    public HrmSubUnit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
        HrmSubUnit roles = (HrmSubUnit) o;
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
            ", name='" + getName() + "'" +
            "}";
    }
}
