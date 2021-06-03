package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Adjudication;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Adjudication}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdjudicationResource {

    private final Logger log = LoggerFactory.getLogger(AdjudicationResource.class);

    private static final String ENTITY_NAME = "adjudication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjudicationRepository adjudicationRepository;

    public AdjudicationResource(AdjudicationRepository adjudicationRepository) {
        this.adjudicationRepository = adjudicationRepository;
    }

    /**
     * {@code POST  /adjudications} : Create a new adjudication.
     *
     * @param adjudication the adjudication to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjudication, or with status {@code 400 (Bad Request)} if the adjudication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjudications")
    public ResponseEntity<Adjudication> createAdjudication(@Valid @RequestBody Adjudication adjudication) throws URISyntaxException {
        log.debug("REST request to save Adjudication : {}", adjudication);
        if (adjudication.getId() != null) {
            throw new BadRequestAlertException("A new adjudication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Adjudication result = adjudicationRepository.save(adjudication);
        return ResponseEntity
            .created(new URI("/api/adjudications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adjudications/:id} : Updates an existing adjudication.
     *
     * @param id the id of the adjudication to save.
     * @param adjudication the adjudication to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudication,
     * or with status {@code 400 (Bad Request)} if the adjudication is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjudication couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjudications/{id}")
    public ResponseEntity<Adjudication> updateAdjudication(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Adjudication adjudication
    ) throws URISyntaxException {
        log.debug("REST request to update Adjudication : {}, {}", id, adjudication);
        if (adjudication.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudication.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Adjudication result = adjudicationRepository.save(adjudication);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudication.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adjudications/:id} : Partial updates given fields of an existing adjudication, field will ignore if it is null
     *
     * @param id the id of the adjudication to save.
     * @param adjudication the adjudication to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudication,
     * or with status {@code 400 (Bad Request)} if the adjudication is not valid,
     * or with status {@code 404 (Not Found)} if the adjudication is not found,
     * or with status {@code 500 (Internal Server Error)} if the adjudication couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adjudications/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Adjudication> partialUpdateAdjudication(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Adjudication adjudication
    ) throws URISyntaxException {
        log.debug("REST request to partial update Adjudication partially : {}, {}", id, adjudication);
        if (adjudication.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudication.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Adjudication> result = adjudicationRepository
            .findById(adjudication.getId())
            .map(
                existingAdjudication -> {
                    if (adjudication.getCategory() != null) {
                        existingAdjudication.setCategory(adjudication.getCategory());
                    }
                    if (adjudication.getReason() != null) {
                        existingAdjudication.setReason(adjudication.getReason());
                    }
                    if (adjudication.getAmount() != null) {
                        existingAdjudication.setAmount(adjudication.getAmount());
                    }
                    if (adjudication.getValue() != null) {
                        existingAdjudication.setValue(adjudication.getValue());
                    }

                    return existingAdjudication;
                }
            )
            .map(adjudicationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudication.getId().toString())
        );
    }

    /**
     * {@code GET  /adjudications} : get all the adjudications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjudications in body.
     */
    @GetMapping("/adjudications")
    public List<Adjudication> getAllAdjudications() {
        log.debug("REST request to get all Adjudications");
        return adjudicationRepository.findAll();
    }

    /**
     * {@code GET  /adjudications/:id} : get the "id" adjudication.
     *
     * @param id the id of the adjudication to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjudication, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjudications/{id}")
    public ResponseEntity<Adjudication> getAdjudication(@PathVariable Long id) {
        log.debug("REST request to get Adjudication : {}", id);
        Optional<Adjudication> adjudication = adjudicationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adjudication);
    }

    /**
     * {@code DELETE  /adjudications/:id} : delete the "id" adjudication.
     *
     * @param id the id of the adjudication to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjudications/{id}")
    public ResponseEntity<Void> deleteAdjudication(@PathVariable Long id) {
        log.debug("REST request to delete Adjudication : {}", id);
        adjudicationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
