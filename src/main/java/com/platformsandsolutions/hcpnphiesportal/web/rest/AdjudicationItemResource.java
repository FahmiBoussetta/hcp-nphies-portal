package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationItem;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationItemRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdjudicationItemResource {

    private final Logger log = LoggerFactory.getLogger(AdjudicationItemResource.class);

    private static final String ENTITY_NAME = "adjudicationItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjudicationItemRepository adjudicationItemRepository;

    public AdjudicationItemResource(AdjudicationItemRepository adjudicationItemRepository) {
        this.adjudicationItemRepository = adjudicationItemRepository;
    }

    /**
     * {@code POST  /adjudication-items} : Create a new adjudicationItem.
     *
     * @param adjudicationItem the adjudicationItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjudicationItem, or with status {@code 400 (Bad Request)} if the adjudicationItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjudication-items")
    public ResponseEntity<AdjudicationItem> createAdjudicationItem(@Valid @RequestBody AdjudicationItem adjudicationItem)
        throws URISyntaxException {
        log.debug("REST request to save AdjudicationItem : {}", adjudicationItem);
        if (adjudicationItem.getId() != null) {
            throw new BadRequestAlertException("A new adjudicationItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdjudicationItem result = adjudicationItemRepository.save(adjudicationItem);
        return ResponseEntity
            .created(new URI("/api/adjudication-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adjudication-items/:id} : Updates an existing adjudicationItem.
     *
     * @param id the id of the adjudicationItem to save.
     * @param adjudicationItem the adjudicationItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudicationItem,
     * or with status {@code 400 (Bad Request)} if the adjudicationItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjudicationItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjudication-items/{id}")
    public ResponseEntity<AdjudicationItem> updateAdjudicationItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdjudicationItem adjudicationItem
    ) throws URISyntaxException {
        log.debug("REST request to update AdjudicationItem : {}, {}", id, adjudicationItem);
        if (adjudicationItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudicationItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdjudicationItem result = adjudicationItemRepository.save(adjudicationItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudicationItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adjudication-items/:id} : Partial updates given fields of an existing adjudicationItem, field will ignore if it is null
     *
     * @param id the id of the adjudicationItem to save.
     * @param adjudicationItem the adjudicationItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudicationItem,
     * or with status {@code 400 (Bad Request)} if the adjudicationItem is not valid,
     * or with status {@code 404 (Not Found)} if the adjudicationItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the adjudicationItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adjudication-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AdjudicationItem> partialUpdateAdjudicationItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdjudicationItem adjudicationItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdjudicationItem partially : {}, {}", id, adjudicationItem);
        if (adjudicationItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudicationItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdjudicationItem> result = adjudicationItemRepository
            .findById(adjudicationItem.getId())
            .map(
                existingAdjudicationItem -> {
                    if (adjudicationItem.getOutcome() != null) {
                        existingAdjudicationItem.setOutcome(adjudicationItem.getOutcome());
                    }
                    if (adjudicationItem.getSequence() != null) {
                        existingAdjudicationItem.setSequence(adjudicationItem.getSequence());
                    }

                    return existingAdjudicationItem;
                }
            )
            .map(adjudicationItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudicationItem.getId().toString())
        );
    }

    /**
     * {@code GET  /adjudication-items} : get all the adjudicationItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjudicationItems in body.
     */
    @GetMapping("/adjudication-items")
    public List<AdjudicationItem> getAllAdjudicationItems() {
        log.debug("REST request to get all AdjudicationItems");
        return adjudicationItemRepository.findAll();
    }

    /**
     * {@code GET  /adjudication-items/:id} : get the "id" adjudicationItem.
     *
     * @param id the id of the adjudicationItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjudicationItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjudication-items/{id}")
    public ResponseEntity<AdjudicationItem> getAdjudicationItem(@PathVariable Long id) {
        log.debug("REST request to get AdjudicationItem : {}", id);
        Optional<AdjudicationItem> adjudicationItem = adjudicationItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adjudicationItem);
    }

    /**
     * {@code DELETE  /adjudication-items/:id} : delete the "id" adjudicationItem.
     *
     * @param id the id of the adjudicationItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjudication-items/{id}")
    public ResponseEntity<Void> deleteAdjudicationItem(@PathVariable Long id) {
        log.debug("REST request to delete AdjudicationItem : {}", id);
        adjudicationItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
