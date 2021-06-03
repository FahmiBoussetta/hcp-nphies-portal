package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationDetailNotes;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationDetailNotesRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationDetailNotes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdjudicationDetailNotesResource {

    private final Logger log = LoggerFactory.getLogger(AdjudicationDetailNotesResource.class);

    private static final String ENTITY_NAME = "adjudicationDetailNotes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjudicationDetailNotesRepository adjudicationDetailNotesRepository;

    public AdjudicationDetailNotesResource(AdjudicationDetailNotesRepository adjudicationDetailNotesRepository) {
        this.adjudicationDetailNotesRepository = adjudicationDetailNotesRepository;
    }

    /**
     * {@code POST  /adjudication-detail-notes} : Create a new adjudicationDetailNotes.
     *
     * @param adjudicationDetailNotes the adjudicationDetailNotes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjudicationDetailNotes, or with status {@code 400 (Bad Request)} if the adjudicationDetailNotes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjudication-detail-notes")
    public ResponseEntity<AdjudicationDetailNotes> createAdjudicationDetailNotes(
        @RequestBody AdjudicationDetailNotes adjudicationDetailNotes
    ) throws URISyntaxException {
        log.debug("REST request to save AdjudicationDetailNotes : {}", adjudicationDetailNotes);
        if (adjudicationDetailNotes.getId() != null) {
            throw new BadRequestAlertException("A new adjudicationDetailNotes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdjudicationDetailNotes result = adjudicationDetailNotesRepository.save(adjudicationDetailNotes);
        return ResponseEntity
            .created(new URI("/api/adjudication-detail-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adjudication-detail-notes/:id} : Updates an existing adjudicationDetailNotes.
     *
     * @param id the id of the adjudicationDetailNotes to save.
     * @param adjudicationDetailNotes the adjudicationDetailNotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudicationDetailNotes,
     * or with status {@code 400 (Bad Request)} if the adjudicationDetailNotes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjudicationDetailNotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjudication-detail-notes/{id}")
    public ResponseEntity<AdjudicationDetailNotes> updateAdjudicationDetailNotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdjudicationDetailNotes adjudicationDetailNotes
    ) throws URISyntaxException {
        log.debug("REST request to update AdjudicationDetailNotes : {}, {}", id, adjudicationDetailNotes);
        if (adjudicationDetailNotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudicationDetailNotes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationDetailNotesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdjudicationDetailNotes result = adjudicationDetailNotesRepository.save(adjudicationDetailNotes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudicationDetailNotes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adjudication-detail-notes/:id} : Partial updates given fields of an existing adjudicationDetailNotes, field will ignore if it is null
     *
     * @param id the id of the adjudicationDetailNotes to save.
     * @param adjudicationDetailNotes the adjudicationDetailNotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudicationDetailNotes,
     * or with status {@code 400 (Bad Request)} if the adjudicationDetailNotes is not valid,
     * or with status {@code 404 (Not Found)} if the adjudicationDetailNotes is not found,
     * or with status {@code 500 (Internal Server Error)} if the adjudicationDetailNotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adjudication-detail-notes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AdjudicationDetailNotes> partialUpdateAdjudicationDetailNotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdjudicationDetailNotes adjudicationDetailNotes
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdjudicationDetailNotes partially : {}, {}", id, adjudicationDetailNotes);
        if (adjudicationDetailNotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudicationDetailNotes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationDetailNotesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdjudicationDetailNotes> result = adjudicationDetailNotesRepository
            .findById(adjudicationDetailNotes.getId())
            .map(
                existingAdjudicationDetailNotes -> {
                    if (adjudicationDetailNotes.getNote() != null) {
                        existingAdjudicationDetailNotes.setNote(adjudicationDetailNotes.getNote());
                    }

                    return existingAdjudicationDetailNotes;
                }
            )
            .map(adjudicationDetailNotesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudicationDetailNotes.getId().toString())
        );
    }

    /**
     * {@code GET  /adjudication-detail-notes} : get all the adjudicationDetailNotes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjudicationDetailNotes in body.
     */
    @GetMapping("/adjudication-detail-notes")
    public List<AdjudicationDetailNotes> getAllAdjudicationDetailNotes() {
        log.debug("REST request to get all AdjudicationDetailNotes");
        return adjudicationDetailNotesRepository.findAll();
    }

    /**
     * {@code GET  /adjudication-detail-notes/:id} : get the "id" adjudicationDetailNotes.
     *
     * @param id the id of the adjudicationDetailNotes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjudicationDetailNotes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjudication-detail-notes/{id}")
    public ResponseEntity<AdjudicationDetailNotes> getAdjudicationDetailNotes(@PathVariable Long id) {
        log.debug("REST request to get AdjudicationDetailNotes : {}", id);
        Optional<AdjudicationDetailNotes> adjudicationDetailNotes = adjudicationDetailNotesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adjudicationDetailNotes);
    }

    /**
     * {@code DELETE  /adjudication-detail-notes/:id} : delete the "id" adjudicationDetailNotes.
     *
     * @param id the id of the adjudicationDetailNotes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjudication-detail-notes/{id}")
    public ResponseEntity<Void> deleteAdjudicationDetailNotes(@PathVariable Long id) {
        log.debug("REST request to delete AdjudicationDetailNotes : {}", id);
        adjudicationDetailNotesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
