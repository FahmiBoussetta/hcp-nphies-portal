package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.OperationOutcome;
import com.platformsandsolutions.hcpnphiesportal.repository.OperationOutcomeRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.OperationOutcome}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OperationOutcomeResource {

    private final Logger log = LoggerFactory.getLogger(OperationOutcomeResource.class);

    private static final String ENTITY_NAME = "operationOutcome";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OperationOutcomeRepository operationOutcomeRepository;

    public OperationOutcomeResource(OperationOutcomeRepository operationOutcomeRepository) {
        this.operationOutcomeRepository = operationOutcomeRepository;
    }

    /**
     * {@code POST  /operation-outcomes} : Create a new operationOutcome.
     *
     * @param operationOutcome the operationOutcome to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new operationOutcome, or with status {@code 400 (Bad Request)} if the operationOutcome has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/operation-outcomes")
    public ResponseEntity<OperationOutcome> createOperationOutcome(@RequestBody OperationOutcome operationOutcome)
        throws URISyntaxException {
        log.debug("REST request to save OperationOutcome : {}", operationOutcome);
        if (operationOutcome.getId() != null) {
            throw new BadRequestAlertException("A new operationOutcome cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperationOutcome result = operationOutcomeRepository.save(operationOutcome);
        return ResponseEntity
            .created(new URI("/api/operation-outcomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /operation-outcomes/:id} : Updates an existing operationOutcome.
     *
     * @param id the id of the operationOutcome to save.
     * @param operationOutcome the operationOutcome to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationOutcome,
     * or with status {@code 400 (Bad Request)} if the operationOutcome is not valid,
     * or with status {@code 500 (Internal Server Error)} if the operationOutcome couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/operation-outcomes/{id}")
    public ResponseEntity<OperationOutcome> updateOperationOutcome(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OperationOutcome operationOutcome
    ) throws URISyntaxException {
        log.debug("REST request to update OperationOutcome : {}, {}", id, operationOutcome);
        if (operationOutcome.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operationOutcome.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationOutcomeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OperationOutcome result = operationOutcomeRepository.save(operationOutcome);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operationOutcome.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /operation-outcomes/:id} : Partial updates given fields of an existing operationOutcome, field will ignore if it is null
     *
     * @param id the id of the operationOutcome to save.
     * @param operationOutcome the operationOutcome to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated operationOutcome,
     * or with status {@code 400 (Bad Request)} if the operationOutcome is not valid,
     * or with status {@code 404 (Not Found)} if the operationOutcome is not found,
     * or with status {@code 500 (Internal Server Error)} if the operationOutcome couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/operation-outcomes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OperationOutcome> partialUpdateOperationOutcome(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OperationOutcome operationOutcome
    ) throws URISyntaxException {
        log.debug("REST request to partial update OperationOutcome partially : {}, {}", id, operationOutcome);
        if (operationOutcome.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, operationOutcome.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!operationOutcomeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OperationOutcome> result = operationOutcomeRepository
            .findById(operationOutcome.getId())
            .map(
                existingOperationOutcome -> {
                    if (operationOutcome.getValue() != null) {
                        existingOperationOutcome.setValue(operationOutcome.getValue());
                    }
                    if (operationOutcome.getSystem() != null) {
                        existingOperationOutcome.setSystem(operationOutcome.getSystem());
                    }
                    if (operationOutcome.getParsed() != null) {
                        existingOperationOutcome.setParsed(operationOutcome.getParsed());
                    }

                    return existingOperationOutcome;
                }
            )
            .map(operationOutcomeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, operationOutcome.getId().toString())
        );
    }

    /**
     * {@code GET  /operation-outcomes} : get all the operationOutcomes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of operationOutcomes in body.
     */
    @GetMapping("/operation-outcomes")
    public List<OperationOutcome> getAllOperationOutcomes() {
        log.debug("REST request to get all OperationOutcomes");
        return operationOutcomeRepository.findAll();
    }

    /**
     * {@code GET  /operation-outcomes/:id} : get the "id" operationOutcome.
     *
     * @param id the id of the operationOutcome to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the operationOutcome, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/operation-outcomes/{id}")
    public ResponseEntity<OperationOutcome> getOperationOutcome(@PathVariable Long id) {
        log.debug("REST request to get OperationOutcome : {}", id);
        Optional<OperationOutcome> operationOutcome = operationOutcomeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(operationOutcome);
    }

    /**
     * {@code DELETE  /operation-outcomes/:id} : delete the "id" operationOutcome.
     *
     * @param id the id of the operationOutcome to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/operation-outcomes/{id}")
    public ResponseEntity<Void> deleteOperationOutcome(@PathVariable Long id) {
        log.debug("REST request to delete OperationOutcome : {}", id);
        operationOutcomeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
