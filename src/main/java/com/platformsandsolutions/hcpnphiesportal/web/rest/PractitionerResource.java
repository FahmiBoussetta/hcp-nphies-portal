package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Practitioner;
import com.platformsandsolutions.hcpnphiesportal.repository.PractitionerRepository;
import com.platformsandsolutions.hcpnphiesportal.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Practitioner}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PractitionerResource {

    private final Logger log = LoggerFactory.getLogger(PractitionerResource.class);

    private static final String ENTITY_NAME = "practitioner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PractitionerRepository practitionerRepository;

    public PractitionerResource(PractitionerRepository practitionerRepository) {
        this.practitionerRepository = practitionerRepository;
    }

    /**
     * {@code POST  /practitioners} : Create a new practitioner.
     *
     * @param practitioner the practitioner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new practitioner, or with status {@code 400 (Bad Request)} if the practitioner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/practitioners")
    public ResponseEntity<Practitioner> createPractitioner(@Valid @RequestBody Practitioner practitioner) throws URISyntaxException {
        log.debug("REST request to save Practitioner : {}", practitioner);
        if (practitioner.getId() != null) {
            throw new BadRequestAlertException("A new practitioner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Practitioner result = practitionerRepository.save(practitioner);
        return ResponseEntity
            .created(new URI("/api/practitioners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /practitioners/:id} : Updates an existing practitioner.
     *
     * @param id the id of the practitioner to save.
     * @param practitioner the practitioner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated practitioner,
     * or with status {@code 400 (Bad Request)} if the practitioner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the practitioner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/practitioners/{id}")
    public ResponseEntity<Practitioner> updatePractitioner(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Practitioner practitioner
    ) throws URISyntaxException {
        log.debug("REST request to update Practitioner : {}, {}", id, practitioner);
        if (practitioner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, practitioner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!practitionerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Practitioner result = practitionerRepository.save(practitioner);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, practitioner.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /practitioners/:id} : Partial updates given fields of an existing practitioner, field will ignore if it is null
     *
     * @param id the id of the practitioner to save.
     * @param practitioner the practitioner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated practitioner,
     * or with status {@code 400 (Bad Request)} if the practitioner is not valid,
     * or with status {@code 404 (Not Found)} if the practitioner is not found,
     * or with status {@code 500 (Internal Server Error)} if the practitioner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/practitioners/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Practitioner> partialUpdatePractitioner(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Practitioner practitioner
    ) throws URISyntaxException {
        log.debug("REST request to partial update Practitioner partially : {}, {}", id, practitioner);
        if (practitioner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, practitioner.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!practitionerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Practitioner> result = practitionerRepository
            .findById(practitioner.getId())
            .map(
                existingPractitioner -> {
                    if (practitioner.getGuid() != null) {
                        existingPractitioner.setGuid(practitioner.getGuid());
                    }
                    if (practitioner.getForceId() != null) {
                        existingPractitioner.setForceId(practitioner.getForceId());
                    }
                    if (practitioner.getPractitionerLicense() != null) {
                        existingPractitioner.setPractitionerLicense(practitioner.getPractitionerLicense());
                    }
                    if (practitioner.getGender() != null) {
                        existingPractitioner.setGender(practitioner.getGender());
                    }

                    return existingPractitioner;
                }
            )
            .map(practitionerRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, practitioner.getId().toString())
        );
    }

    /**
     * {@code GET  /practitioners} : get all the practitioners.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of practitioners in body.
     */
    @GetMapping("/practitioners")
    public List<Practitioner> getAllPractitioners() {
        log.debug("REST request to get all Practitioners");
        return practitionerRepository.findAll();
    }

    /**
     * {@code GET  /practitioners/:id} : get the "id" practitioner.
     *
     * @param id the id of the practitioner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the practitioner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/practitioners/{id}")
    public ResponseEntity<Practitioner> getPractitioner(@PathVariable Long id) {
        log.debug("REST request to get Practitioner : {}", id);
        Optional<Practitioner> practitioner = practitionerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(practitioner);
    }

    /**
     * {@code DELETE  /practitioners/:id} : delete the "id" practitioner.
     *
     * @param id the id of the practitioner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/practitioners/{id}")
    public ResponseEntity<Void> deletePractitioner(@PathVariable Long id) {
        log.debug("REST request to delete Practitioner : {}", id);
        practitionerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
