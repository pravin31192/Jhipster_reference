package com.sun.prms.service;

import com.sun.prms.domain.OhrmCustomer;
import com.sun.prms.repository.OhrmCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing OhrmCustomer.
 */
@Service
@Transactional
public class OhrmCustomerService {

    private final Logger log = LoggerFactory.getLogger(OhrmCustomerService.class);

    private final OhrmCustomerRepository ohrmCustomerRepository;

    public OhrmCustomerService(OhrmCustomerRepository ohrmCustomerRepository) {
        this.ohrmCustomerRepository = ohrmCustomerRepository;
    }

    /**
     * Save a ohrmCustomer.
     *
     * @param ohrmCustomer the entity to save
     * @return the persisted entity
     */
    public OhrmCustomer save(OhrmCustomer ohrmCustomer) {
        log.debug("Request to save OhrmCustomer : {}", ohrmCustomer);
        return ohrmCustomerRepository.save(ohrmCustomer);
    }

    /**
     * Get all the ohrmCustomers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OhrmCustomer> findAll(Pageable pageable) {
        log.debug("Request to get all OhrmCustomers");
        return ohrmCustomerRepository.findAll(pageable);
    }


    /**
     * Get one ohrmCustomer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OhrmCustomer> findOne(Integer id) {
        log.debug("Request to get OhrmCustomer : {}", id);
        return ohrmCustomerRepository.findById(id);
    }

    /**
     * Delete the ohrmCustomer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Integer id) {
        log.debug("Request to delete OhrmCustomer : {}", id);
        ohrmCustomerRepository.deleteById(id);
    }
}
