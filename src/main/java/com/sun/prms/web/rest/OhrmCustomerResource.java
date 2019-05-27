package com.sun.prms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sun.prms.domain.OhrmCustomer;
import com.sun.prms.service.OhrmCustomerService;
import com.sun.prms.web.rest.errors.BadRequestAlertException;
import com.sun.prms.web.rest.util.HeaderUtil;
import com.sun.prms.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OhrmCustomer.
 */
@RestController
@RequestMapping("/api")
public class OhrmCustomerResource {

    private final Logger log = LoggerFactory.getLogger(OhrmCustomerResource.class);

    private static final String ENTITY_NAME = "ohrmCustomer";

    private final OhrmCustomerService ohrmCustomerService;

    public OhrmCustomerResource(OhrmCustomerService ohrmCustomerService) {
        this.ohrmCustomerService = ohrmCustomerService;
    }

    /**
     * POST  /ohrm-customers : Create a new ohrmCustomer.
     *
     * @param ohrmCustomer the ohrmCustomer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ohrmCustomer, or with status 400 (Bad Request) if the ohrmCustomer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ohrm-customers")
    @Timed
    public ResponseEntity<OhrmCustomer> createOhrmCustomer(@RequestBody OhrmCustomer ohrmCustomer) throws URISyntaxException {
        log.debug("REST request to save OhrmCustomer : {}", ohrmCustomer);
        if (ohrmCustomer.getId() != null) {
            throw new BadRequestAlertException("A new ohrmCustomer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OhrmCustomer result = ohrmCustomerService.save(ohrmCustomer);
        return ResponseEntity.created(new URI("/api/ohrm-customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ohrm-customers : Updates an existing ohrmCustomer.
     *
     * @param ohrmCustomer the ohrmCustomer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ohrmCustomer,
     * or with status 400 (Bad Request) if the ohrmCustomer is not valid,
     * or with status 500 (Internal Server Error) if the ohrmCustomer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ohrm-customers")
    @Timed
    public ResponseEntity<OhrmCustomer> updateOhrmCustomer(@RequestBody OhrmCustomer ohrmCustomer) throws URISyntaxException {
        log.debug("REST request to update OhrmCustomer : {}", ohrmCustomer);
        if (ohrmCustomer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OhrmCustomer result = ohrmCustomerService.save(ohrmCustomer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ohrmCustomer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ohrm-customers : get all the ohrmCustomers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ohrmCustomers in body
     */
    @GetMapping("/ohrm-customers")
    @Timed
    public ResponseEntity<List<OhrmCustomer>> getAllOhrmCustomers(Pageable pageable) {
        log.debug("REST request to get a page of OhrmCustomers");
        Page<OhrmCustomer> page = ohrmCustomerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ohrm-customers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /ohrm-customers/:id : get the "id" ohrmCustomer.
     *
     * @param id the id of the ohrmCustomer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ohrmCustomer, or with status 404 (Not Found)
     */
    @GetMapping("/ohrm-customers/{id}")
    @Timed
    public ResponseEntity<OhrmCustomer> getOhrmCustomer(@PathVariable Integer id) {
        log.debug("REST request to get OhrmCustomer : {}", id);
        Optional<OhrmCustomer> ohrmCustomer = ohrmCustomerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ohrmCustomer);
    }

    /**
     * DELETE  /ohrm-customers/:id : delete the "id" ohrmCustomer.
     *
     * @param id the id of the ohrmCustomer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ohrm-customers/{id}")
    @Timed
    public ResponseEntity<Void> deleteOhrmCustomer(@PathVariable Integer id) {
        log.debug("REST request to delete OhrmCustomer : {}", id);
        ohrmCustomerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
