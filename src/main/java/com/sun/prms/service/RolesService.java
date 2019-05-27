package com.sun.prms.service;

import com.sun.prms.domain.Roles;
import com.sun.prms.repository.RolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Roles.
 */
@Service
@Transactional
public class RolesService {

    private final Logger log = LoggerFactory.getLogger(RolesService.class);

    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    /**
     * Save a roles.
     *
     * @param roles the entity to save
     * @return the persisted entity
     */
    public Roles save(Roles roles) {
        log.debug("Request to save Roles : {}", roles);
        return rolesRepository.save(roles);
    }

    /**
     * Get all the roles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Roles> findAll(Pageable pageable) {
        log.debug("Request to get all Roles");
        return rolesRepository.findAll(pageable);
    }


    /**
     * Get one roles by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Roles> findOne(Integer id) {
        log.debug("Request to get Roles : {}", id);
        return rolesRepository.findById(id);
    }

    /**
     * Delete the roles by id.
     *
     * @param id the id of the entity
     */
    public void delete(Integer id) {
        log.debug("Request to delete Roles : {}", id);
        rolesRepository.deleteById(id);
    }
}
