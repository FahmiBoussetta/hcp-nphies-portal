package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ReferenceIdentifier;
import com.platformsandsolutions.hcpnphiesportal.repository.ReferenceIdentifierRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ReferenceIdentifier}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReferenceIdentifierResource {

    private final Logger log = LoggerFactory.getLogger(ReferenceIdentifierResource.class);

    private static final String ENTITY_NAME = "referenceIdentifier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReferenceIdentifierRepository referenceIdentifierRepository;

    public ReferenceIdentifierResource(ReferenceIdentifierRepository referenceIdentifierRepository) {
        this.referenceIdentifierRepository = referenceIdentifierRepository;
    }

    /**
     * {@code POST  /reference-identifiers} : Create a new referenceIdentifier.
     *
     * @param referenceIdentifier the referenceIdentifier to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new referenceIdentifier, or with status {@code 400 (Bad Request)} if the referenceIdentifier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reference-identifiers")
    public ResponseEntity<ReferenceIdentifier> createReferenceIdentifier(@RequestBody ReferenceIdentifier referenceIdentifier)
        throws URISyntaxException {
        log.debug("REST request to save ReferenceIdentifier : {}", referenceIdentifier);
        if (referenceIdentifier.getId() != null) {
            throw new BadRequestAlertException("A new referenceIdentifier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReferenceIdentifier result = referenceIdentifierRepository.save(referenceIdentifier);
        return ResponseEntity
            .created(new URI("/api/reference-identifiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reference-identifiers/:id} : Updates an existing referenceIdentifier.
     *
     * @param id the id of the referenceIdentifier to save.
     * @param referenceIdentifier the referenceIdentifier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referenceIdentifier,
     * or with status {@code 400 (Bad Request)} if the referenceIdentifier is not valid,
     * or with status {@code 500 (Internal Server Error)} if the referenceIdentifier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reference-identifiers/{id}")
    public ResponseEntity<ReferenceIdentifier> updateReferenceIdentifier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReferenceIdentifier referenceIdentifier
    ) throws URISyntaxException {
        log.debug("REST request to update ReferenceIdentifier : {}, {}", id, referenceIdentifier);
        if (referenceIdentifier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, referenceIdentifier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!referenceIdentifierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReferenceIdentifier result = referenceIdentifierRepository.save(referenceIdentifier);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, referenceIdentifier.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reference-identifiers/:id} : Partial updates given fields of an existing referenceIdentifier, field will ignore if it is null
     *
     * @param id the id of the referenceIdentifier to save.
     * @param referenceIdentifier the referenceIdentifier to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referenceIdentifier,
     * or with status {@code 400 (Bad Request)} if the referenceIdentifier is not valid,
     * or with status {@code 404 (Not Found)} if the referenceIdentifier is not found,
     * or with status {@code 500 (Internal Server Error)} if the referenceIdentifier couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reference-identifiers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ReferenceIdentifier> partialUpdateReferenceIdentifier(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReferenceIdentifier referenceIdentifier
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReferenceIdentifier partially : {}, {}", id, referenceIdentifier);
        if (referenceIdentifier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, referenceIdentifier.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!referenceIdentifierRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReferenceIdentifier> result = referenceIdentifierRepository
            .findById(referenceIdentifier.getId())
            .map(
                existingReferenceIdentifier -> {
                    if (referenceIdentifier.getRef() != null) {
                        existingReferenceIdentifier.setRef(referenceIdentifier.getRef());
                    }
                    if (referenceIdentifier.getIdValue() != null) {
                        existingReferenceIdentifier.setIdValue(referenceIdentifier.getIdValue());
                    }
                    if (referenceIdentifier.getIdentifier() != null) {
                        existingReferenceIdentifier.setIdentifier(referenceIdentifier.getIdentifier());
                    }
                    if (referenceIdentifier.getDisplay() != null) {
                        existingReferenceIdentifier.setDisplay(referenceIdentifier.getDisplay());
                    }

                    return existingReferenceIdentifier;
                }
            )
            .map(referenceIdentifierRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, referenceIdentifier.getId().toString())
        );
    }

    /**
     * {@code GET  /reference-identifiers} : get all the referenceIdentifiers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of referenceIdentifiers in body.
     */
    @GetMapping("/reference-identifiers")
    public List<ReferenceIdentifier> getAllReferenceIdentifiers() {
        log.debug("REST request to get all ReferenceIdentifiers");
        return referenceIdentifierRepository.findAll();
    }

    /**
     * {@code GET  /reference-identifiers/:id} : get the "id" referenceIdentifier.
     *
     * @param id the id of the referenceIdentifier to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the referenceIdentifier, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reference-identifiers/{id}")
    public ResponseEntity<ReferenceIdentifier> getReferenceIdentifier(@PathVariable Long id) {
        log.debug("REST request to get ReferenceIdentifier : {}", id);
        Optional<ReferenceIdentifier> referenceIdentifier = referenceIdentifierRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(referenceIdentifier);
    }

    /**
     * {@code DELETE  /reference-identifiers/:id} : delete the "id" referenceIdentifier.
     *
     * @param id the id of the referenceIdentifier to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reference-identifiers/{id}")
    public ResponseEntity<Void> deleteReferenceIdentifier(@PathVariable Long id) {
        log.debug("REST request to delete ReferenceIdentifier : {}", id);
        referenceIdentifierRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
