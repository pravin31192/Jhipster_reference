package com.sun.prms.repository;

import com.sun.prms.domain.OhrmCustomer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OhrmCustomer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OhrmCustomerRepository extends JpaRepository<OhrmCustomer, Integer> {

}
