package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.PractitionerRole;
import com.platformsandsolutions.hcpnphiesportal.repository.PractitionerRoleRepository;
import com.platformsandsolutions.hcpnphiesportal.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.PractitionerRole}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PractitionerRoleResource {

    private final Logger log = LoggerFactory.getLogger(PractitionerRoleResource.class);

    private static final String ENTITY_NAME = "practitionerRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PractitionerRoleRepository practitionerRoleRepository;

    public PractitionerRoleResource(PractitionerRoleRepository practitionerRoleRepository) {
        this.practitionerRoleRepository = practitionerRoleRepository;
    }

    /**
     * {@code POST  /practitioner-roles} : Create a new practitionerRole.
     *
     * @param practitionerRole the practitionerRole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new practitionerRole, or with status {@code 400 (Bad Request)} if the practitionerRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/practitioner-roles")
    public ResponseEntity<PractitionerRole> createPractitionerRole(@RequestBody PractitionerRole practitionerRole)
        throws URISyntaxException {
        log.debug("REST request to save PractitionerRole : {}", practitionerRole);
        if (practitionerRole.getId() != null) {
            throw new BadRequestAlertException("A new practitionerRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PractitionerRole result = practitionerRoleRepository.save(practitionerRole);
        return ResponseEntity
            .created(new URI("/api/practitioner-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /practitioner-roles/:id} : Updates an existing practitionerRole.
     *
     * @param id the id of the practitionerRole to save.
     * @param practitionerRole the practitionerRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated practitionerRole,
     * or with status {@code 400 (Bad Request)} if the practitionerRole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the practitionerRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/practitioner-roles/{id}")
    public ResponseEntity<PractitionerRole> updatePractitionerRole(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PractitionerRole practitionerRole
    ) throws URISyntaxException {
        log.debug("REST request to update PractitionerRole : {}, {}", id, practitionerRole);
        if (practitionerRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, practitionerRole.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!practitionerRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PractitionerRole result = practitionerRoleRepository.save(practitionerRole);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, practitionerRole.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /practitioner-roles/:id} : Partial updates given fields of an existing practitionerRole, field will ignore if it is null
     *
     * @param id the id of the practitionerRole to save.
     * @param practitionerRole the practitionerRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated practitionerRole,
     * or with status {@code 400 (Bad Request)} if the practitionerRole is not valid,
     * or with status {@code 404 (Not Found)} if the practitionerRole is not found,
     * or with status {@code 500 (Internal Server Error)} if the practitionerRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/practitioner-roles/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PractitionerRole> partialUpdatePractitionerRole(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PractitionerRole practitionerRole
    ) throws URISyntaxException {
        log.debug("REST request to partial update PractitionerRole partially : {}, {}", id, practitionerRole);
        if (practitionerRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, practitionerRole.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!practitionerRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PractitionerRole> result = practitionerRoleRepository
            .findById(practitionerRole.getId())
            .map(
                existingPractitionerRole -> {
                    if (practitionerRole.getGuid() != null) {
                        existingPractitionerRole.setGuid(practitionerRole.getGuid());
                    }
                    if (practitionerRole.getForceId() != null) {
                        existingPractitionerRole.setForceId(practitionerRole.getForceId());
                    }
                    if (practitionerRole.getStart() != null) {
                        existingPractitionerRole.setStart(practitionerRole.getStart());
                    }
                    if (practitionerRole.getEnd() != null) {
                        existingPractitionerRole.setEnd(practitionerRole.getEnd());
                    }

                    return existingPractitionerRole;
                }
            )
            .map(practitionerRoleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, practitionerRole.getId().toString())
        );
    }

    /**
     * {@code GET  /practitioner-roles} : get all the practitionerRoles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of practitionerRoles in body.
     */
    @GetMapping("/practitioner-roles")
    public List<PractitionerRole> getAllPractitionerRoles() {
        log.debug("REST request to get all PractitionerRoles");
        return practitionerRoleRepository.findAll();
    }

    /**
     * {@code GET  /practitioner-roles/:id} : get the "id" practitionerRole.
     *
     * @param id the id of the practitionerRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the practitionerRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/practitioner-roles/{id}")
    public ResponseEntity<PractitionerRole> getPractitionerRole(@PathVariable Long id) {
        log.debug("REST request to get PractitionerRole : {}", id);
        Optional<PractitionerRole> practitionerRole = practitionerRoleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(practitionerRole);
    }

    /**
     * {@code DELETE  /practitioner-roles/:id} : delete the "id" practitionerRole.
     *
     * @param id the id of the practitionerRole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/practitioner-roles/{id}")
    public ResponseEntity<Void> deletePractitionerRole(@PathVariable Long id) {
        log.debug("REST request to delete PractitionerRole : {}", id);
        practitionerRoleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
