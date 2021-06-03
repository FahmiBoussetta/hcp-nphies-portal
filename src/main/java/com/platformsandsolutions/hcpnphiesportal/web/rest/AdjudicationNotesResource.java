package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationNotes;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationNotesRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationNotes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdjudicationNotesResource {

    private final Logger log = LoggerFactory.getLogger(AdjudicationNotesResource.class);

    private static final String ENTITY_NAME = "adjudicationNotes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjudicationNotesRepository adjudicationNotesRepository;

    public AdjudicationNotesResource(AdjudicationNotesRepository adjudicationNotesRepository) {
        this.adjudicationNotesRepository = adjudicationNotesRepository;
    }

    /**
     * {@code POST  /adjudication-notes} : Create a new adjudicationNotes.
     *
     * @param adjudicationNotes the adjudicationNotes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjudicationNotes, or with status {@code 400 (Bad Request)} if the adjudicationNotes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjudication-notes")
    public ResponseEntity<AdjudicationNotes> createAdjudicationNotes(@RequestBody AdjudicationNotes adjudicationNotes)
        throws URISyntaxException {
        log.debug("REST request to save AdjudicationNotes : {}", adjudicationNotes);
        if (adjudicationNotes.getId() != null) {
            throw new BadRequestAlertException("A new adjudicationNotes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdjudicationNotes result = adjudicationNotesRepository.save(adjudicationNotes);
        return ResponseEntity
            .created(new URI("/api/adjudication-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adjudication-notes/:id} : Updates an existing adjudicationNotes.
     *
     * @param id the id of the adjudicationNotes to save.
     * @param adjudicationNotes the adjudicationNotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudicationNotes,
     * or with status {@code 400 (Bad Request)} if the adjudicationNotes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjudicationNotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjudication-notes/{id}")
    public ResponseEntity<AdjudicationNotes> updateAdjudicationNotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdjudicationNotes adjudicationNotes
    ) throws URISyntaxException {
        log.debug("REST request to update AdjudicationNotes : {}, {}", id, adjudicationNotes);
        if (adjudicationNotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudicationNotes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationNotesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdjudicationNotes result = adjudicationNotesRepository.save(adjudicationNotes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudicationNotes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adjudication-notes/:id} : Partial updates given fields of an existing adjudicationNotes, field will ignore if it is null
     *
     * @param id the id of the adjudicationNotes to save.
     * @param adjudicationNotes the adjudicationNotes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudicationNotes,
     * or with status {@code 400 (Bad Request)} if the adjudicationNotes is not valid,
     * or with status {@code 404 (Not Found)} if the adjudicationNotes is not found,
     * or with status {@code 500 (Internal Server Error)} if the adjudicationNotes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adjudication-notes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AdjudicationNotes> partialUpdateAdjudicationNotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AdjudicationNotes adjudicationNotes
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdjudicationNotes partially : {}, {}", id, adjudicationNotes);
        if (adjudicationNotes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudicationNotes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationNotesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdjudicationNotes> result = adjudicationNotesRepository
            .findById(adjudicationNotes.getId())
            .map(
                existingAdjudicationNotes -> {
                    if (adjudicationNotes.getNote() != null) {
                        existingAdjudicationNotes.setNote(adjudicationNotes.getNote());
                    }

                    return existingAdjudicationNotes;
                }
            )
            .map(adjudicationNotesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudicationNotes.getId().toString())
        );
    }

    /**
     * {@code GET  /adjudication-notes} : get all the adjudicationNotes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjudicationNotes in body.
     */
    @GetMapping("/adjudication-notes")
    public List<AdjudicationNotes> getAllAdjudicationNotes() {
        log.debug("REST request to get all AdjudicationNotes");
        return adjudicationNotesRepository.findAll();
    }

    /**
     * {@code GET  /adjudication-notes/:id} : get the "id" adjudicationNotes.
     *
     * @param id the id of the adjudicationNotes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjudicationNotes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjudication-notes/{id}")
    public ResponseEntity<AdjudicationNotes> getAdjudicationNotes(@PathVariable Long id) {
        log.debug("REST request to get AdjudicationNotes : {}", id);
        Optional<AdjudicationNotes> adjudicationNotes = adjudicationNotesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adjudicationNotes);
    }

    /**
     * {@code DELETE  /adjudication-notes/:id} : delete the "id" adjudicationNotes.
     *
     * @param id the id of the adjudicationNotes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjudication-notes/{id}")
    public ResponseEntity<Void> deleteAdjudicationNotes(@PathVariable Long id) {
        log.debug("REST request to delete AdjudicationNotes : {}", id);
        adjudicationNotesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
