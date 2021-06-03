package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ReconciliationDetailItem;
import com.platformsandsolutions.hcpnphiesportal.repository.ReconciliationDetailItemRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ReconciliationDetailItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReconciliationDetailItemResource {

    private final Logger log = LoggerFactory.getLogger(ReconciliationDetailItemResource.class);

    private static final String ENTITY_NAME = "reconciliationDetailItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReconciliationDetailItemRepository reconciliationDetailItemRepository;

    public ReconciliationDetailItemResource(ReconciliationDetailItemRepository reconciliationDetailItemRepository) {
        this.reconciliationDetailItemRepository = reconciliationDetailItemRepository;
    }

    /**
     * {@code POST  /reconciliation-detail-items} : Create a new reconciliationDetailItem.
     *
     * @param reconciliationDetailItem the reconciliationDetailItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reconciliationDetailItem, or with status {@code 400 (Bad Request)} if the reconciliationDetailItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reconciliation-detail-items")
    public ResponseEntity<ReconciliationDetailItem> createReconciliationDetailItem(
        @RequestBody ReconciliationDetailItem reconciliationDetailItem
    ) throws URISyntaxException {
        log.debug("REST request to save ReconciliationDetailItem : {}", reconciliationDetailItem);
        if (reconciliationDetailItem.getId() != null) {
            throw new BadRequestAlertException("A new reconciliationDetailItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReconciliationDetailItem result = reconciliationDetailItemRepository.save(reconciliationDetailItem);
        return ResponseEntity
            .created(new URI("/api/reconciliation-detail-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reconciliation-detail-items/:id} : Updates an existing reconciliationDetailItem.
     *
     * @param id the id of the reconciliationDetailItem to save.
     * @param reconciliationDetailItem the reconciliationDetailItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reconciliationDetailItem,
     * or with status {@code 400 (Bad Request)} if the reconciliationDetailItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reconciliationDetailItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reconciliation-detail-items/{id}")
    public ResponseEntity<ReconciliationDetailItem> updateReconciliationDetailItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReconciliationDetailItem reconciliationDetailItem
    ) throws URISyntaxException {
        log.debug("REST request to update ReconciliationDetailItem : {}, {}", id, reconciliationDetailItem);
        if (reconciliationDetailItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reconciliationDetailItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reconciliationDetailItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReconciliationDetailItem result = reconciliationDetailItemRepository.save(reconciliationDetailItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reconciliationDetailItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reconciliation-detail-items/:id} : Partial updates given fields of an existing reconciliationDetailItem, field will ignore if it is null
     *
     * @param id the id of the reconciliationDetailItem to save.
     * @param reconciliationDetailItem the reconciliationDetailItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reconciliationDetailItem,
     * or with status {@code 400 (Bad Request)} if the reconciliationDetailItem is not valid,
     * or with status {@code 404 (Not Found)} if the reconciliationDetailItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the reconciliationDetailItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reconciliation-detail-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ReconciliationDetailItem> partialUpdateReconciliationDetailItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReconciliationDetailItem reconciliationDetailItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReconciliationDetailItem partially : {}, {}", id, reconciliationDetailItem);
        if (reconciliationDetailItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reconciliationDetailItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reconciliationDetailItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReconciliationDetailItem> result = reconciliationDetailItemRepository
            .findById(reconciliationDetailItem.getId())
            .map(
                existingReconciliationDetailItem -> {
                    if (reconciliationDetailItem.getIdentifier() != null) {
                        existingReconciliationDetailItem.setIdentifier(reconciliationDetailItem.getIdentifier());
                    }
                    if (reconciliationDetailItem.getPredecessor() != null) {
                        existingReconciliationDetailItem.setPredecessor(reconciliationDetailItem.getPredecessor());
                    }
                    if (reconciliationDetailItem.getType() != null) {
                        existingReconciliationDetailItem.setType(reconciliationDetailItem.getType());
                    }
                    if (reconciliationDetailItem.getDate() != null) {
                        existingReconciliationDetailItem.setDate(reconciliationDetailItem.getDate());
                    }
                    if (reconciliationDetailItem.getAmount() != null) {
                        existingReconciliationDetailItem.setAmount(reconciliationDetailItem.getAmount());
                    }

                    return existingReconciliationDetailItem;
                }
            )
            .map(reconciliationDetailItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reconciliationDetailItem.getId().toString())
        );
    }

    /**
     * {@code GET  /reconciliation-detail-items} : get all the reconciliationDetailItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reconciliationDetailItems in body.
     */
    @GetMapping("/reconciliation-detail-items")
    public List<ReconciliationDetailItem> getAllReconciliationDetailItems() {
        log.debug("REST request to get all ReconciliationDetailItems");
        return reconciliationDetailItemRepository.findAll();
    }

    /**
     * {@code GET  /reconciliation-detail-items/:id} : get the "id" reconciliationDetailItem.
     *
     * @param id the id of the reconciliationDetailItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reconciliationDetailItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reconciliation-detail-items/{id}")
    public ResponseEntity<ReconciliationDetailItem> getReconciliationDetailItem(@PathVariable Long id) {
        log.debug("REST request to get ReconciliationDetailItem : {}", id);
        Optional<ReconciliationDetailItem> reconciliationDetailItem = reconciliationDetailItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reconciliationDetailItem);
    }

    /**
     * {@code DELETE  /reconciliation-detail-items/:id} : delete the "id" reconciliationDetailItem.
     *
     * @param id the id of the reconciliationDetailItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reconciliation-detail-items/{id}")
    public ResponseEntity<Void> deleteReconciliationDetailItem(@PathVariable Long id) {
        log.debug("REST request to delete ReconciliationDetailItem : {}", id);
        reconciliationDetailItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
