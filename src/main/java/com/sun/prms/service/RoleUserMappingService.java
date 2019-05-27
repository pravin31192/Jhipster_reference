package com.sun.prms.service;

import com.sun.prms.domain.HsHrEmployee;
import com.sun.prms.domain.RoleUserMapping;
import com.sun.prms.domain.Roles;
import com.sun.prms.repository.HrmHsHrEmployeeRepository;
import com.sun.prms.repository.RoleUserMappingRepository;
import com.sun.prms.repository.RolesRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing RoleUserMapping.
 */
@Service
@Transactional
public class RoleUserMappingService {

    private final Logger log = LoggerFactory.getLogger(RoleUserMappingService.class);

    private final RoleUserMappingRepository roleUserMappingRepository;
    private final HrmHsHrEmployeeRepository hrmHsHrEmployeeRepository;
    private final RolesRepository rolesRepository;

    public RoleUserMappingService(
    	RoleUserMappingRepository roleUserMappingRepository,
    	HrmHsHrEmployeeRepository hrmHsHrEmployeeRepository,
    	RolesRepository rolesRepository
    ) {
        this.roleUserMappingRepository = roleUserMappingRepository;
        this.hrmHsHrEmployeeRepository = hrmHsHrEmployeeRepository;
        this.rolesRepository = rolesRepository;
    }

    /**
     * Save a roleUserMapping.
     *
     * @param roleUserMapping the entity to save
     * @return the persisted entity
     */
    public RoleUserMapping save(RoleUserMapping roleUserMapping) {
        log.debug("Request to save RoleUserMapping : {}", roleUserMapping);
        HsHrEmployee employeeRow = hrmHsHrEmployeeRepository.findByEmpNumber(roleUserMapping.getUserId());
        String employeeName = employeeRow.getEmpFirstname()+" "+employeeRow.getEmpLastname();
        roleUserMapping.setUserName(employeeName);
        String roleName = null;
        Roles rolewRow = rolesRepository.getOne(roleUserMapping.getRoleId());
        roleName = rolewRow.getName();
        roleUserMapping.setRoleName(roleName);
        return roleUserMappingRepository.save(roleUserMapping);
    }

    /**
     * Get all the roleUserMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RoleUserMapping> findAll(Pageable pageable) {
        log.debug("Request to get all RoleUserMappings");
        return roleUserMappingRepository.findAll(pageable);
    }


    /**
     * Get one roleUserMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RoleUserMapping> findOne(Long id) {
        log.debug("Request to get RoleUserMapping : {}", id);
        return roleUserMappingRepository.findById(id);
    }

    /**
     * Delete the roleUserMapping by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RoleUserMapping : {}", id);
        roleUserMappingRepository.deleteById(id);
    }
    
    /**
     * Get one roleUserMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public List<RoleUserMapping> usersOnSelectedRole(Integer roleId) {
        log.debug("Request to get RoleUserMapping : {}", roleId);
        return roleUserMappingRepository.findByRoleId(roleId);
    }
}
