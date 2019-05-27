package com.sun.prms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A OhrmCustomer.
 */
@Entity
@Table(name = "sunhrm_jan.ohrm_customer")
public class OhrmCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "is_deleted")
    private Integer isDeleted;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public OhrmCustomer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public OhrmCustomer description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public OhrmCustomer isDeleted(Integer isDeleted) {
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
        OhrmCustomer ohrmCustomer = (OhrmCustomer) o;
        if (ohrmCustomer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ohrmCustomer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OhrmCustomer{" +
            ", customerId=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", isDeleted=" + getIsDeleted() +
            "}";
    }
}
