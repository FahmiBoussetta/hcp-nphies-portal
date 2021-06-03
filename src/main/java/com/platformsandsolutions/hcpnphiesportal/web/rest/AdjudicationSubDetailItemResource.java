package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationSubDetailItem;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationSubDetailItemRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationSubDetailItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdjudicationSubDetailItemResource {

    private final Logger log = LoggerFactory.getLogger(AdjudicationSubDetailItemResource.class);

    private static final String ENTITY_NAME = "adjudicationSubDetailItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjudicationSubDetailItemRepository adjudicationSubDetailItemRepository;

    public AdjudicationSubDetailItemResource(AdjudicationSubDetailItemRepository adjudicationSubDetailItemRepository) {
        this.adjudicationSubDetailItemRepository = adjudicationSubDetailItemRepository;
    }

    /**
     * {@code POST  /adjudication-sub-detail-items} : Create a new adjudicationSubDetailItem.
     *
     * @param adjudicationSubDetailItem the adjudicationSubDetailItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjudicationSubDetailItem, or with status {@code 400 (Bad Request)} if the adjudicationSubDetailItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjudication-sub-detail-items")
    public ResponseEntity<AdjudicationSubDetailItem> createAdjudicationSubDetailItem(
        @Valid @RequestBody AdjudicationSubDetailItem adjudicationSubDetailItem
    ) throws URISyntaxException {
        log.debug("REST request to save AdjudicationSubDetailItem : {}", adjudicationSubDetailItem);
        if (adjudicationSubDetailItem.getId() != null) {
            throw new BadRequestAlertException("A new adjudicationSubDetailItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdjudicationSubDetailItem result = adjudicationSubDetailItemRepository.save(adjudicationSubDetailItem);
        return ResponseEntity
            .created(new URI("/api/adjudication-sub-detail-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adjudication-sub-detail-items/:id} : Updates an existing adjudicationSubDetailItem.
     *
     * @param id the id of the adjudicationSubDetailItem to save.
     * @param adjudicationSubDetailItem the adjudicationSubDetailItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudicationSubDetailItem,
     * or with status {@code 400 (Bad Request)} if the adjudicationSubDetailItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjudicationSubDetailItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjudication-sub-detail-items/{id}")
    public ResponseEntity<AdjudicationSubDetailItem> updateAdjudicationSubDetailItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdjudicationSubDetailItem adjudicationSubDetailItem
    ) throws URISyntaxException {
        log.debug("REST request to update AdjudicationSubDetailItem : {}, {}", id, adjudicationSubDetailItem);
        if (adjudicationSubDetailItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudicationSubDetailItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationSubDetailItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdjudicationSubDetailItem result = adjudicationSubDetailItemRepository.save(adjudicationSubDetailItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudicationSubDetailItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adjudication-sub-detail-items/:id} : Partial updates given fields of an existing adjudicationSubDetailItem, field will ignore if it is null
     *
     * @param id the id of the adjudicationSubDetailItem to save.
     * @param adjudicationSubDetailItem the adjudicationSubDetailItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudicationSubDetailItem,
     * or with status {@code 400 (Bad Request)} if the adjudicationSubDetailItem is not valid,
     * or with status {@code 404 (Not Found)} if the adjudicationSubDetailItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the adjudicationSubDetailItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adjudication-sub-detail-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AdjudicationSubDetailItem> partialUpdateAdjudicationSubDetailItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdjudicationSubDetailItem adjudicationSubDetailItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdjudicationSubDetailItem partially : {}, {}", id, adjudicationSubDetailItem);
        if (adjudicationSubDetailItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudicationSubDetailItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationSubDetailItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdjudicationSubDetailItem> result = adjudicationSubDetailItemRepository
            .findById(adjudicationSubDetailItem.getId())
            .map(
                existingAdjudicationSubDetailItem -> {
                    if (adjudicationSubDetailItem.getSequence() != null) {
                        existingAdjudicationSubDetailItem.setSequence(adjudicationSubDetailItem.getSequence());
                    }

                    return existingAdjudicationSubDetailItem;
                }
            )
            .map(adjudicationSubDetailItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudicationSubDetailItem.getId().toString())
        );
    }

    /**
     * {@code GET  /adjudication-sub-detail-items} : get all the adjudicationSubDetailItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjudicationSubDetailItems in body.
     */
    @GetMapping("/adjudication-sub-detail-items")
    public List<AdjudicationSubDetailItem> getAllAdjudicationSubDetailItems() {
        log.debug("REST request to get all AdjudicationSubDetailItems");
        return adjudicationSubDetailItemRepository.findAll();
    }

    /**
     * {@code GET  /adjudication-sub-detail-items/:id} : get the "id" adjudicationSubDetailItem.
     *
     * @param id the id of the adjudicationSubDetailItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjudicationSubDetailItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjudication-sub-detail-items/{id}")
    public ResponseEntity<AdjudicationSubDetailItem> getAdjudicationSubDetailItem(@PathVariable Long id) {
        log.debug("REST request to get AdjudicationSubDetailItem : {}", id);
        Optional<AdjudicationSubDetailItem> adjudicationSubDetailItem = adjudicationSubDetailItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adjudicationSubDetailItem);
    }

    /**
     * {@code DELETE  /adjudication-sub-detail-items/:id} : delete the "id" adjudicationSubDetailItem.
     *
     * @param id the id of the adjudicationSubDetailItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjudication-sub-detail-items/{id}")
    public ResponseEntity<Void> deleteAdjudicationSubDetailItem(@PathVariable Long id) {
        log.debug("REST request to delete AdjudicationSubDetailItem : {}", id);
        adjudicationSubDetailItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
