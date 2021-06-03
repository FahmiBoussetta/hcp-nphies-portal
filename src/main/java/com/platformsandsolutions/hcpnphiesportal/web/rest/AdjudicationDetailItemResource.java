package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationDetailItem;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationDetailItemRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationDetailItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdjudicationDetailItemResource {

    private final Logger log = LoggerFactory.getLogger(AdjudicationDetailItemResource.class);

    private static final String ENTITY_NAME = "adjudicationDetailItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjudicationDetailItemRepository adjudicationDetailItemRepository;

    public AdjudicationDetailItemResource(AdjudicationDetailItemRepository adjudicationDetailItemRepository) {
        this.adjudicationDetailItemRepository = adjudicationDetailItemRepository;
    }

    /**
     * {@code POST  /adjudication-detail-items} : Create a new adjudicationDetailItem.
     *
     * @param adjudicationDetailItem the adjudicationDetailItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjudicationDetailItem, or with status {@code 400 (Bad Request)} if the adjudicationDetailItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjudication-detail-items")
    public ResponseEntity<AdjudicationDetailItem> createAdjudicationDetailItem(
        @Valid @RequestBody AdjudicationDetailItem adjudicationDetailItem
    ) throws URISyntaxException {
        log.debug("REST request to save AdjudicationDetailItem : {}", adjudicationDetailItem);
        if (adjudicationDetailItem.getId() != null) {
            throw new BadRequestAlertException("A new adjudicationDetailItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdjudicationDetailItem result = adjudicationDetailItemRepository.save(adjudicationDetailItem);
        return ResponseEntity
            .created(new URI("/api/adjudication-detail-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adjudication-detail-items/:id} : Updates an existing adjudicationDetailItem.
     *
     * @param id the id of the adjudicationDetailItem to save.
     * @param adjudicationDetailItem the adjudicationDetailItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudicationDetailItem,
     * or with status {@code 400 (Bad Request)} if the adjudicationDetailItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjudicationDetailItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjudication-detail-items/{id}")
    public ResponseEntity<AdjudicationDetailItem> updateAdjudicationDetailItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdjudicationDetailItem adjudicationDetailItem
    ) throws URISyntaxException {
        log.debug("REST request to update AdjudicationDetailItem : {}, {}", id, adjudicationDetailItem);
        if (adjudicationDetailItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudicationDetailItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationDetailItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdjudicationDetailItem result = adjudicationDetailItemRepository.save(adjudicationDetailItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudicationDetailItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /adjudication-detail-items/:id} : Partial updates given fields of an existing adjudicationDetailItem, field will ignore if it is null
     *
     * @param id the id of the adjudicationDetailItem to save.
     * @param adjudicationDetailItem the adjudicationDetailItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjudicationDetailItem,
     * or with status {@code 400 (Bad Request)} if the adjudicationDetailItem is not valid,
     * or with status {@code 404 (Not Found)} if the adjudicationDetailItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the adjudicationDetailItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/adjudication-detail-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AdjudicationDetailItem> partialUpdateAdjudicationDetailItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdjudicationDetailItem adjudicationDetailItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdjudicationDetailItem partially : {}, {}", id, adjudicationDetailItem);
        if (adjudicationDetailItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adjudicationDetailItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adjudicationDetailItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdjudicationDetailItem> result = adjudicationDetailItemRepository
            .findById(adjudicationDetailItem.getId())
            .map(
                existingAdjudicationDetailItem -> {
                    if (adjudicationDetailItem.getSequence() != null) {
                        existingAdjudicationDetailItem.setSequence(adjudicationDetailItem.getSequence());
                    }

                    return existingAdjudicationDetailItem;
                }
            )
            .map(adjudicationDetailItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjudicationDetailItem.getId().toString())
        );
    }

    /**
     * {@code GET  /adjudication-detail-items} : get all the adjudicationDetailItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjudicationDetailItems in body.
     */
    @GetMapping("/adjudication-detail-items")
    public List<AdjudicationDetailItem> getAllAdjudicationDetailItems() {
        log.debug("REST request to get all AdjudicationDetailItems");
        return adjudicationDetailItemRepository.findAll();
    }

    /**
     * {@code GET  /adjudication-detail-items/:id} : get the "id" adjudicationDetailItem.
     *
     * @param id the id of the adjudicationDetailItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjudicationDetailItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjudication-detail-items/{id}")
    public ResponseEntity<AdjudicationDetailItem> getAdjudicationDetailItem(@PathVariable Long id) {
        log.debug("REST request to get AdjudicationDetailItem : {}", id);
        Optional<AdjudicationDetailItem> adjudicationDetailItem = adjudicationDetailItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(adjudicationDetailItem);
    }

    /**
     * {@code DELETE  /adjudication-detail-items/:id} : delete the "id" adjudicationDetailItem.
     *
     * @param id the id of the adjudicationDetailItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjudication-detail-items/{id}")
    public ResponseEntity<Void> deleteAdjudicationDetailItem(@PathVariable Long id) {
        log.debug("REST request to delete AdjudicationDetailItem : {}", id);
        adjudicationDetailItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
