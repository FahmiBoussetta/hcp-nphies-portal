package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Accident;
import com.platformsandsolutions.hcpnphiesportal.repository.AccidentRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Accident}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AccidentResource {

    private final Logger log = LoggerFactory.getLogger(AccidentResource.class);

    private static final String ENTITY_NAME = "accident";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccidentRepository accidentRepository;

    public AccidentResource(AccidentRepository accidentRepository) {
        this.accidentRepository = accidentRepository;
    }

    /**
     * {@code POST  /accidents} : Create a new accident.
     *
     * @param accident the accident to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accident, or with status {@code 400 (Bad Request)} if the accident has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accidents")
    public ResponseEntity<Accident> createAccident(@Valid @RequestBody Accident accident) throws URISyntaxException {
        log.debug("REST request to save Accident : {}", accident);
        if (accident.getId() != null) {
            throw new BadRequestAlertException("A new accident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Accident result = accidentRepository.save(accident);
        return ResponseEntity
            .created(new URI("/api/accidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accidents/:id} : Updates an existing accident.
     *
     * @param id the id of the accident to save.
     * @param accident the accident to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accident,
     * or with status {@code 400 (Bad Request)} if the accident is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accident couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accidents/{id}")
    public ResponseEntity<Accident> updateAccident(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Accident accident
    ) throws URISyntaxException {
        log.debug("REST request to update Accident : {}, {}", id, accident);
        if (accident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accident.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accidentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Accident result = accidentRepository.save(accident);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accident.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /accidents/:id} : Partial updates given fields of an existing accident, field will ignore if it is null
     *
     * @param id the id of the accident to save.
     * @param accident the accident to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accident,
     * or with status {@code 400 (Bad Request)} if the accident is not valid,
     * or with status {@code 404 (Not Found)} if the accident is not found,
     * or with status {@code 500 (Internal Server Error)} if the accident couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/accidents/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Accident> partialUpdateAccident(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Accident accident
    ) throws URISyntaxException {
        log.debug("REST request to partial update Accident partially : {}, {}", id, accident);
        if (accident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accident.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accidentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Accident> result = accidentRepository
            .findById(accident.getId())
            .map(
                existingAccident -> {
                    if (accident.getDate() != null) {
                        existingAccident.setDate(accident.getDate());
                    }
                    if (accident.getType() != null) {
                        existingAccident.setType(accident.getType());
                    }

                    return existingAccident;
                }
            )
            .map(accidentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accident.getId().toString())
        );
    }

    /**
     * {@code GET  /accidents} : get all the accidents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accidents in body.
     */
    @GetMapping("/accidents")
    public List<Accident> getAllAccidents() {
        log.debug("REST request to get all Accidents");
        return accidentRepository.findAll();
    }

    /**
     * {@code GET  /accidents/:id} : get the "id" accident.
     *
     * @param id the id of the accident to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accident, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accidents/{id}")
    public ResponseEntity<Accident> getAccident(@PathVariable Long id) {
        log.debug("REST request to get Accident : {}", id);
        Optional<Accident> accident = accidentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(accident);
    }

    /**
     * {@code DELETE  /accidents/:id} : delete the "id" accident.
     *
     * @param id the id of the accident to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accidents/{id}")
    public ResponseEntity<Void> deleteAccident(@PathVariable Long id) {
        log.debug("REST request to delete Accident : {}", id);
        accidentRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
