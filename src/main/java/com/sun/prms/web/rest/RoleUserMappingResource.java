package com.sun.prms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sun.prms.domain.RoleUserMapping;
import com.sun.prms.service.RoleUserMappingService;
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
 * REST controller for managing RoleUserMapping.
 */
@RestController
@RequestMapping("/api")
public class RoleUserMappingResource {

    private final Logger log = LoggerFactory.getLogger(RoleUserMappingResource.class);

    private static final String ENTITY_NAME = "roleUserMapping";

    private final RoleUserMappingService roleUserMappingService;

    public RoleUserMappingResource(RoleUserMappingService roleUserMappingService) {
        this.roleUserMappingService = roleUserMappingService;
    }

    /**
     * POST  /role-user-mappings : Create a new roleUserMapping.
     *
     * @param roleUserMapping the roleUserMapping to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roleUserMapping, or with status 400 (Bad Request) if the roleUserMapping has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/role-user-mappings")
    @Timed
    public ResponseEntity<RoleUserMapping> createRoleUserMapping(@RequestBody RoleUserMapping roleUserMapping) throws URISyntaxException {
        log.debug("REST request to save RoleUserMapping : {}", roleUserMapping);
        if (roleUserMapping.getId() != null) {
            throw new BadRequestAlertException("A new roleUserMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleUserMapping result = roleUserMappingService.save(roleUserMapping);
        return ResponseEntity.created(new URI("/api/role-user-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /role-user-mappings : Updates an existing roleUserMapping.
     *
     * @param roleUserMapping the roleUserMapping to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roleUserMapping,
     * or with status 400 (Bad Request) if the roleUserMapping is not valid,
     * or with status 500 (Internal Server Error) if the roleUserMapping couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/role-user-mappings")
    @Timed
    public ResponseEntity<RoleUserMapping> updateRoleUserMapping(@RequestBody RoleUserMapping roleUserMapping) throws URISyntaxException {
        log.debug("REST request to update RoleUserMapping : {}", roleUserMapping);
        if (roleUserMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoleUserMapping result = roleUserMappingService.save(roleUserMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roleUserMapping.getId().toString()))
            .body(result);
    }

    /**
     * GET  /role-user-mappings : get all the roleUserMappings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roleUserMappings in body
     */
    @GetMapping("/role-user-mappings")
    @Timed
    public ResponseEntity<List<RoleUserMapping>> getAllRoleUserMappings(Pageable pageable) {
        log.debug("REST request to get a page of RoleUserMappings");
        Page<RoleUserMapping> page = roleUserMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/role-user-mappings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /role-user-mappings/:id : get the "id" roleUserMapping.
     *
     * @param id the id of the roleUserMapping to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roleUserMapping, or with status 404 (Not Found)
     */
    @GetMapping("/role-user-mappings/{id}")
    @Timed
    public ResponseEntity<RoleUserMapping> getRoleUserMapping(@PathVariable Long id) {
        log.debug("REST request to get RoleUserMapping : {}", id);
        Optional<RoleUserMapping> roleUserMapping = roleUserMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleUserMapping);
    }

    /**
     * DELETE  /role-user-mappings/:id : delete the "id" roleUserMapping.
     *
     * @param id the id of the roleUserMapping to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/role-user-mappings/{id}")
    @Timed
    public ResponseEntity<Void> deleteRoleUserMapping(@PathVariable Long id) {
        log.debug("REST request to delete RoleUserMapping : {}", id);
        roleUserMappingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * GET  /role-user-mappings/:id : get the "id" roleUserMapping.
     *
     * @param roleId the RoleId of the roleUserMapping to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roleUserMapping, or with status 404 (Not Found)
     */
    @GetMapping("/resource-on-role/{roleId}")
    @Timed
    public List<RoleUserMapping> getUsersBasedOnRole(@PathVariable Integer roleId) {
        log.debug("REST request to get RoleUserMapping : {}", roleId);
        List<RoleUserMapping> roleUserMapping = roleUserMappingService.usersOnSelectedRole(roleId);
        // return ResponseUtil.wrapOrNotFound(roleUserMapping);
        return roleUserMapping;
    }
}
