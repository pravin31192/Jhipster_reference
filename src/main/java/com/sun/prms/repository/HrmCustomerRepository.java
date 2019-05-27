package com.sun.prms.repository;

import com.sun.prms.domain.HrmCustomer;
import com.sun.prms.domain.HrmOhrmProject;
import com.sun.prms.domain.HrmTimesheetItem;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Roles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HrmCustomerRepository extends JpaRepository<HrmCustomer, Integer> {

	HrmCustomer findByCustomerId(Integer clientId);

}
