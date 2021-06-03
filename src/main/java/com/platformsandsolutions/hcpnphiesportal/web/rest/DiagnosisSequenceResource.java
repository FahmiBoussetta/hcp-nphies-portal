package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.DiagnosisSequence;
import com.platformsandsolutions.hcpnphiesportal.repository.DiagnosisSequenceRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.DiagnosisSequence}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DiagnosisSequenceResource {

    private final Logger log = LoggerFactory.getLogger(DiagnosisSequenceResource.class);

    private static final String ENTITY_NAME = "diagnosisSequence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiagnosisSequenceRepository diagnosisSequenceRepository;

    public DiagnosisSequenceResource(DiagnosisSequenceRepository diagnosisSequenceRepository) {
        this.diagnosisSequenceRepository = diagnosisSequenceRepository;
    }

    /**
     * {@code POST  /diagnosis-sequences} : Create a new diagnosisSequence.
     *
     * @param diagnosisSequence the diagnosisSequence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diagnosisSequence, or with status {@code 400 (Bad Request)} if the diagnosisSequence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/diagnosis-sequences")
    public ResponseEntity<DiagnosisSequence> createDiagnosisSequence(@RequestBody DiagnosisSequence diagnosisSequence)
        throws URISyntaxException {
        log.debug("REST request to save DiagnosisSequence : {}", diagnosisSequence);
        if (diagnosisSequence.getId() != null) {
            throw new BadRequestAlertException("A new diagnosisSequence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiagnosisSequence result = diagnosisSequenceRepository.save(diagnosisSequence);
        return ResponseEntity
            .created(new URI("/api/diagnosis-sequences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /diagnosis-sequences/:id} : Updates an existing diagnosisSequence.
     *
     * @param id the id of the diagnosisSequence to save.
     * @param diagnosisSequence the diagnosisSequence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diagnosisSequence,
     * or with status {@code 400 (Bad Request)} if the diagnosisSequence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diagnosisSequence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/diagnosis-sequences/{id}")
    public ResponseEntity<DiagnosisSequence> updateDiagnosisSequence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiagnosisSequence diagnosisSequence
    ) throws URISyntaxException {
        log.debug("REST request to update DiagnosisSequence : {}, {}", id, diagnosisSequence);
        if (diagnosisSequence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diagnosisSequence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diagnosisSequenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiagnosisSequence result = diagnosisSequenceRepository.save(diagnosisSequence);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diagnosisSequence.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /diagnosis-sequences/:id} : Partial updates given fields of an existing diagnosisSequence, field will ignore if it is null
     *
     * @param id the id of the diagnosisSequence to save.
     * @param diagnosisSequence the diagnosisSequence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diagnosisSequence,
     * or with status {@code 400 (Bad Request)} if the diagnosisSequence is not valid,
     * or with status {@code 404 (Not Found)} if the diagnosisSequence is not found,
     * or with status {@code 500 (Internal Server Error)} if the diagnosisSequence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/diagnosis-sequences/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DiagnosisSequence> partialUpdateDiagnosisSequence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiagnosisSequence diagnosisSequence
    ) throws URISyntaxException {
        log.debug("REST request to partial update DiagnosisSequence partially : {}, {}", id, diagnosisSequence);
        if (diagnosisSequence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diagnosisSequence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!diagnosisSequenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiagnosisSequence> result = diagnosisSequenceRepository
            .findById(diagnosisSequence.getId())
            .map(
                existingDiagnosisSequence -> {
                    if (diagnosisSequence.getDiagSeq() != null) {
                        existingDiagnosisSequence.setDiagSeq(diagnosisSequence.getDiagSeq());
                    }

                    return existingDiagnosisSequence;
                }
            )
            .map(diagnosisSequenceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, diagnosisSequence.getId().toString())
        );
    }

    /**
     * {@code GET  /diagnosis-sequences} : get all the diagnosisSequences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diagnosisSequences in body.
     */
    @GetMapping("/diagnosis-sequences")
    public List<DiagnosisSequence> getAllDiagnosisSequences() {
        log.debug("REST request to get all DiagnosisSequences");
        return diagnosisSequenceRepository.findAll();
    }

    /**
     * {@code GET  /diagnosis-sequences/:id} : get the "id" diagnosisSequence.
     *
     * @param id the id of the diagnosisSequence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diagnosisSequence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/diagnosis-sequences/{id}")
    public ResponseEntity<DiagnosisSequence> getDiagnosisSequence(@PathVariable Long id) {
        log.debug("REST request to get DiagnosisSequence : {}", id);
        Optional<DiagnosisSequence> diagnosisSequence = diagnosisSequenceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(diagnosisSequence);
    }

    /**
     * {@code DELETE  /diagnosis-sequences/:id} : delete the "id" diagnosisSequence.
     *
     * @param id the id of the diagnosisSequence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/diagnosis-sequences/{id}")
    public ResponseEntity<Void> deleteDiagnosisSequence(@PathVariable Long id) {
        log.debug("REST request to delete DiagnosisSequence : {}", id);
        diagnosisSequenceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
