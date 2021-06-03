package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Diagnosis;
import com.platformsandsolutions.hcpnphiesportal.repository.DiagnosisRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Diagnosis}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DiagnosisResource {

    private final Logger log = LoggerFactory.getLogger(DiagnosisResource.class);

    private static final String ENTITY_NAME = "diagnosis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiagnosisRepository diagnosisRepository;

    public DiagnosisResource(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    /**
     * {@code POST  /diagnoses} : Create a new diagnosis.
     *
     * @param diagnosis the diagnosis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diagnosis, or with status {@code 400 (Bad Request)} if the diagnosis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/diagnoses")
    public ResponseEntity<Diagnosis> createDiagnosis(@Valid @RequestBody Diagnosis diagnosis) throws URISyntaxException {
        log.debug("REST request to save Diagnosis : {}", diagnosis);
        if (diagnosis.getId() != null) {
            throw new BadRequestAlertException("A new diagnosis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Diagnosis result = diagnosisRepository.save(diagnosis);
        return ResponseEntity
            .created(new URI("/api/diagnoses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /diagnoses/:id} : Updates an existing diagnosis.
     *
     * @param id the id of the diagnosis to save.
     * @param diagnosis the diagnosis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diagnosis,
     * or with status {@code 400 (Bad Request)} if the diagnosis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diagnosis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/diagnoses/{id}")
    public ResponseEntity<Diagnosis> updateDiagnosis(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Diagnosis diagnosis
    ) throws URISyntaxException {
        log.debug("REST request to update Diagnosis : {}, {}", id, diagnosis);
        if (diagnosis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diagnosis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diagnosisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Diagnosis result = diagnosisRepository.save(diagnosis);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diagnosis.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /diagnoses/:id} : Partial updates given fields of an existing diagnosis, field will ignore if it is null
     *
     * @param id the id of the diagnosis to save.
     * @param diagnosis the diagnosis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diagnosis,
     * or with status {@code 400 (Bad Request)} if the diagnosis is not valid,
     * or with status {@code 404 (Not Found)} if the diagnosis is not found,
     * or with status {@code 500 (Internal Server Error)} if the diagnosis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/diagnoses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Diagnosis> partialUpdateDiagnosis(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Diagnosis diagnosis
    ) throws URISyntaxException {
        log.debug("REST request to partial update Diagnosis partially : {}, {}", id, diagnosis);
        if (diagnosis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diagnosis.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diagnosisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Diagnosis> result = diagnosisRepository
            .findById(diagnosis.getId())
            .map(
                existingDiagnosis -> {
                    if (diagnosis.getSequence() != null) {
                        existingDiagnosis.setSequence(diagnosis.getSequence());
                    }
                    if (diagnosis.getDiagnosis() != null) {
                        existingDiagnosis.setDiagnosis(diagnosis.getDiagnosis());
                    }
                    if (diagnosis.getType() != null) {
                        existingDiagnosis.setType(diagnosis.getType());
                    }
                    if (diagnosis.getOnAdmission() != null) {
                        existingDiagnosis.setOnAdmission(diagnosis.getOnAdmission());
                    }

                    return existingDiagnosis;
                }
            )
            .map(diagnosisRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diagnosis.getId().toString())
        );
    }

    /**
     * {@code GET  /diagnoses} : get all the diagnoses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diagnoses in body.
     */
    @GetMapping("/diagnoses")
    public List<Diagnosis> getAllDiagnoses() {
        log.debug("REST request to get all Diagnoses");
        return diagnosisRepository.findAll();
    }

    /**
     * {@code GET  /diagnoses/:id} : get the "id" diagnosis.
     *
     * @param id the id of the diagnosis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diagnosis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/diagnoses/{id}")
    public ResponseEntity<Diagnosis> getDiagnosis(@PathVariable Long id) {
        log.debug("REST request to get Diagnosis : {}", id);
        Optional<Diagnosis> diagnosis = diagnosisRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diagnosis);
    }

    /**
     * {@code DELETE  /diagnoses/:id} : delete the "id" diagnosis.
     *
     * @param id the id of the diagnosis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/diagnoses/{id}")
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {
        log.debug("REST request to delete Diagnosis : {}", id);
        diagnosisRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
