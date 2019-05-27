package com.sun.prms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Roles.
 */
@Entity
@Table(name = "sunhrm_jan.ohrm_customer")
public class HrmCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(name = "name")
    private String name;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public HrmCustomer name(String name) {
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
        HrmCustomer roles = (HrmCustomer) o;
        if (roles.getCustomerId() == null || getCustomerId() == null) {
            return false;
        }
        return Objects.equals(getCustomerId(), roles.getCustomerId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCustomerId());
    }

    @Override
    public String toString() {
        return "Roles{" +
            "id=" + getCustomerId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
