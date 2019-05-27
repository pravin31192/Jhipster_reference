package com.sun.prms.repository;


import com.sun.prms.domain.HrmSubUnit;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Roles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HrmOhrmSubunitRepository extends JpaRepository<HrmSubUnit, Integer> {

}
