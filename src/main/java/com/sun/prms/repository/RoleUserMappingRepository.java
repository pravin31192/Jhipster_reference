package com.sun.prms.repository;

import com.sun.prms.domain.RoleUserMapping;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RoleUserMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleUserMappingRepository extends JpaRepository<RoleUserMapping, Long> {
	public List<RoleUserMapping> findByRoleId(Integer roleId);

	public RoleUserMapping findByUserId(Integer empId);
}
