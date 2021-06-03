package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationSubDetailNotes;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationSubDetailNotesRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationSubDetailNotes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdjudicationSubDetailNotesResource {

    private final Logger log = LoggerFactory.getLogger(AdjudicationSubDetailNotesResource.class);

    private static final String ENTITY_NAME = "adjudicationSubDetailNotes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjudicationSubDetailNotesRepository adjudicationSubDetailNotesRepository;

    public AdjudicationSubDetailNotesResource(AdjudicationSubDetailNotesRepository adjudicationSubDetailNotesRepository) {
        this.adjudicationSubDetailNotesRepository = adjudicationSubDetailNotesRepository;
    }

    /**
     * {@code POST  /adjudication-sub-detail-notes} : Create a new adjudicationSubDetailNotes.
     *
     * @param adjudicationSubDetailNotes the adjudicationSubDetailNotes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjudicationSubDetailNotes, or with status {@code 400 (Bad Request)} if the adjudicationSubDetailNotes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjudication-sub-detail-notes")
    public ResponseEntity<AdjudicationSubDetailNotes> createAdjudicationSubDetailNotes(
        @RequestBody AdjudicationSubDetailNotes adjudicationSubDetailNotes
    ) throws URISyntaxException {
        log.debug("REST request to save AdjudicationSubDetailNotes : {}", adjudicationSubDetailNotes);
        if (adjudicationSubDetailNotes.getId() != null) {
            throw new BadRequestAlertException("A new adjudicationSubDetailNotes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdjudicationSubDetailNotes result = adjudicationSubDetailNotesRepository.save(adjudicationSubDetailNotes);
        return ResponseEntity
            .created(new URI("/api/adjudication-sub-detail-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adjudication-sub-detail-notes/:id} : Updates an existing adjudicationSubDetailNotes.
     *
     * @param id the id of the adjudicationSubDetailNotes to save.
     * @param adjudicationSubDetailNotes the adjudicationSubDetailNotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudicationSubDetailNotes,
     * or with status {@code 400 (Bad Request)} if the adjudicationSubDetailNotes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjudicationSubDetailNotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjudication-sub-detail-notes/{id}")
    public ResponseEntity<AdjudicationSubDetailNotes> updateAdjudicationSubDetailNotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdjudicationSubDetailNotes adjudicationSubDetailNotes
    ) throws URISyntaxException {
        log.debug("REST request to update AdjudicationSubDetailNotes : {}, {}", id, adjudicationSubDetailNotes);
        if (adjudicationSubDetailNotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudicationSubDetailNotes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationSubDetailNotesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdjudicationSubDetailNotes result = adjudicationSubDetailNotesRepository.save(adjudicationSubDetailNotes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudicationSubDetailNotes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adjudication-sub-detail-notes/:id} : Partial updates given fields of an existing adjudicationSubDetailNotes, field will ignore if it is null
     *
     * @param id the id of the adjudicationSubDetailNotes to save.
     * @param adjudicationSubDetailNotes the adjudicationSubDetailNotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudicationSubDetailNotes,
     * or with status {@code 400 (Bad Request)} if the adjudicationSubDetailNotes is not valid,
     * or with status {@code 404 (Not Found)} if the adjudicationSubDetailNotes is not found,
     * or with status {@code 500 (Internal Server Error)} if the adjudicationSubDetailNotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adjudication-sub-detail-notes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AdjudicationSubDetailNotes> partialUpdateAdjudicationSubDetailNotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdjudicationSubDetailNotes adjudicationSubDetailNotes
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdjudicationSubDetailNotes partially : {}, {}", id, adjudicationSubDetailNotes);
        if (adjudicationSubDetailNotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudicationSubDetailNotes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationSubDetailNotesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdjudicationSubDetailNotes> result = adjudicationSubDetailNotesRepository
            .findById(adjudicationSubDetailNotes.getId())
            .map(
                existingAdjudicationSubDetailNotes -> {
                    if (adjudicationSubDetailNotes.getNote() != null) {
                        existingAdjudicationSubDetailNotes.setNote(adjudicationSubDetailNotes.getNote());
                    }

                    return existingAdjudicationSubDetailNotes;
                }
            )
            .map(adjudicationSubDetailNotesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudicationSubDetailNotes.getId().toString())
        );
    }

    /**
     * {@code GET  /adjudication-sub-detail-notes} : get all the adjudicationSubDetailNotes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjudicationSubDetailNotes in body.
     */
    @GetMapping("/adjudication-sub-detail-notes")
    public List<AdjudicationSubDetailNotes> getAllAdjudicationSubDetailNotes() {
        log.debug("REST request to get all AdjudicationSubDetailNotes");
        return adjudicationSubDetailNotesRepository.findAll();
    }

    /**
     * {@code GET  /adjudication-sub-detail-notes/:id} : get the "id" adjudicationSubDetailNotes.
     *
     * @param id the id of the adjudicationSubDetailNotes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjudicationSubDetailNotes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjudication-sub-detail-notes/{id}")
    public ResponseEntity<AdjudicationSubDetailNotes> getAdjudicationSubDetailNotes(@PathVariable Long id) {
        log.debug("REST request to get AdjudicationSubDetailNotes : {}", id);
        Optional<AdjudicationSubDetailNotes> adjudicationSubDetailNotes = adjudicationSubDetailNotesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adjudicationSubDetailNotes);
    }

    /**
     * {@code DELETE  /adjudication-sub-detail-notes/:id} : delete the "id" adjudicationSubDetailNotes.
     *
     * @param id the id of the adjudicationSubDetailNotes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjudication-sub-detail-notes/{id}")
    public ResponseEntity<Void> deleteAdjudicationSubDetailNotes(@PathVariable Long id) {
        log.debug("REST request to delete AdjudicationSubDetailNotes : {}", id);
        adjudicationSubDetailNotesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
