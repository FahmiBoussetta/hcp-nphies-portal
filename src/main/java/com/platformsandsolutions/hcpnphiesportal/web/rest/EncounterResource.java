package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Encounter;
import com.platformsandsolutions.hcpnphiesportal.repository.EncounterRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Encounter}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EncounterResource {

    private final Logger log = LoggerFactory.getLogger(EncounterResource.class);

    private static final String ENTITY_NAME = "encounter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EncounterRepository encounterRepository;

    public EncounterResource(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    /**
     * {@code POST  /encounters} : Create a new encounter.
     *
     * @param encounter the encounter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new encounter, or with status {@code 400 (Bad Request)} if the encounter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/encounters")
    public ResponseEntity<Encounter> createEncounter(@RequestBody Encounter encounter) throws URISyntaxException {
        log.debug("REST request to save Encounter : {}", encounter);
        if (encounter.getId() != null) {
            throw new BadRequestAlertException("A new encounter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Encounter result = encounterRepository.save(encounter);
        return ResponseEntity
            .created(new URI("/api/encounters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /encounters/:id} : Updates an existing encounter.
     *
     * @param id the id of the encounter to save.
     * @param encounter the encounter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encounter,
     * or with status {@code 400 (Bad Request)} if the encounter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the encounter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/encounters/{id}")
    public ResponseEntity<Encounter> updateEncounter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Encounter encounter
    ) throws URISyntaxException {
        log.debug("REST request to update Encounter : {}, {}", id, encounter);
        if (encounter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, encounter.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!encounterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Encounter result = encounterRepository.save(encounter);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, encounter.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /encounters/:id} : Partial updates given fields of an existing encounter, field will ignore if it is null
     *
     * @param id the id of the encounter to save.
     * @param encounter the encounter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encounter,
     * or with status {@code 400 (Bad Request)} if the encounter is not valid,
     * or with status {@code 404 (Not Found)} if the encounter is not found,
     * or with status {@code 500 (Internal Server Error)} if the encounter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/encounters/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Encounter> partialUpdateEncounter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Encounter encounter
    ) throws URISyntaxException {
        log.debug("REST request to partial update Encounter partially : {}, {}", id, encounter);
        if (encounter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, encounter.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!encounterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Encounter> result = encounterRepository
            .findById(encounter.getId())
            .map(
                existingEncounter -> {
                    if (encounter.getGuid() != null) {
                        existingEncounter.setGuid(encounter.getGuid());
                    }
                    if (encounter.getForceId() != null) {
                        existingEncounter.setForceId(encounter.getForceId());
                    }
                    if (encounter.getIdentifier() != null) {
                        existingEncounter.setIdentifier(encounter.getIdentifier());
                    }
                    if (encounter.getEncounterClass() != null) {
                        existingEncounter.setEncounterClass(encounter.getEncounterClass());
                    }
                    if (encounter.getStart() != null) {
                        existingEncounter.setStart(encounter.getStart());
                    }
                    if (encounter.getEnd() != null) {
                        existingEncounter.setEnd(encounter.getEnd());
                    }
                    if (encounter.getServiceType() != null) {
                        existingEncounter.setServiceType(encounter.getServiceType());
                    }
                    if (encounter.getPriority() != null) {
                        existingEncounter.setPriority(encounter.getPriority());
                    }

                    return existingEncounter;
                }
            )
            .map(encounterRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, encounter.getId().toString())
        );
    }

    /**
     * {@code GET  /encounters} : get all the encounters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of encounters in body.
     */
    @GetMapping("/encounters")
    public List<Encounter> getAllEncounters() {
        log.debug("REST request to get all Encounters");
        return encounterRepository.findAll();
    }

    /**
     * {@code GET  /encounters/:id} : get the "id" encounter.
     *
     * @param id the id of the encounter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the encounter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/encounters/{id}")
    public ResponseEntity<Encounter> getEncounter(@PathVariable Long id) {
        log.debug("REST request to get Encounter : {}", id);
        Optional<Encounter> encounter = encounterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(encounter);
    }

    /**
     * {@code DELETE  /encounters/:id} : delete the "id" encounter.
     *
     * @param id the id of the encounter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/encounters/{id}")
    public ResponseEntity<Void> deleteEncounter(@PathVariable Long id) {
        log.debug("REST request to delete Encounter : {}", id);
        encounterRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
