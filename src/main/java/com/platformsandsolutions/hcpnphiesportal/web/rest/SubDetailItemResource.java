package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.SubDetailItem;
import com.platformsandsolutions.hcpnphiesportal.repository.SubDetailItemRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.SubDetailItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SubDetailItemResource {

    private final Logger log = LoggerFactory.getLogger(SubDetailItemResource.class);

    private static final String ENTITY_NAME = "subDetailItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubDetailItemRepository subDetailItemRepository;

    public SubDetailItemResource(SubDetailItemRepository subDetailItemRepository) {
        this.subDetailItemRepository = subDetailItemRepository;
    }

    /**
     * {@code POST  /sub-detail-items} : Create a new subDetailItem.
     *
     * @param subDetailItem the subDetailItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subDetailItem, or with status {@code 400 (Bad Request)} if the subDetailItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-detail-items")
    public ResponseEntity<SubDetailItem> createSubDetailItem(@Valid @RequestBody SubDetailItem subDetailItem) throws URISyntaxException {
        log.debug("REST request to save SubDetailItem : {}", subDetailItem);
        if (subDetailItem.getId() != null) {
            throw new BadRequestAlertException("A new subDetailItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubDetailItem result = subDetailItemRepository.save(subDetailItem);
        return ResponseEntity
            .created(new URI("/api/sub-detail-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-detail-items/:id} : Updates an existing subDetailItem.
     *
     * @param id the id of the subDetailItem to save.
     * @param subDetailItem the subDetailItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subDetailItem,
     * or with status {@code 400 (Bad Request)} if the subDetailItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subDetailItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-detail-items/{id}")
    public ResponseEntity<SubDetailItem> updateSubDetailItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubDetailItem subDetailItem
    ) throws URISyntaxException {
        log.debug("REST request to update SubDetailItem : {}, {}", id, subDetailItem);
        if (subDetailItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subDetailItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subDetailItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubDetailItem result = subDetailItemRepository.save(subDetailItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subDetailItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sub-detail-items/:id} : Partial updates given fields of an existing subDetailItem, field will ignore if it is null
     *
     * @param id the id of the subDetailItem to save.
     * @param subDetailItem the subDetailItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subDetailItem,
     * or with status {@code 400 (Bad Request)} if the subDetailItem is not valid,
     * or with status {@code 404 (Not Found)} if the subDetailItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the subDetailItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sub-detail-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SubDetailItem> partialUpdateSubDetailItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubDetailItem subDetailItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubDetailItem partially : {}, {}", id, subDetailItem);
        if (subDetailItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subDetailItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subDetailItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubDetailItem> result = subDetailItemRepository
            .findById(subDetailItem.getId())
            .map(
                existingSubDetailItem -> {
                    if (subDetailItem.getSequence() != null) {
                        existingSubDetailItem.setSequence(subDetailItem.getSequence());
                    }
                    if (subDetailItem.getTax() != null) {
                        existingSubDetailItem.setTax(subDetailItem.getTax());
                    }
                    if (subDetailItem.getTransportationSRCA() != null) {
                        existingSubDetailItem.setTransportationSRCA(subDetailItem.getTransportationSRCA());
                    }
                    if (subDetailItem.getImaging() != null) {
                        existingSubDetailItem.setImaging(subDetailItem.getImaging());
                    }
                    if (subDetailItem.getLaboratory() != null) {
                        existingSubDetailItem.setLaboratory(subDetailItem.getLaboratory());
                    }
                    if (subDetailItem.getMedicalDevice() != null) {
                        existingSubDetailItem.setMedicalDevice(subDetailItem.getMedicalDevice());
                    }
                    if (subDetailItem.getOralHealthIP() != null) {
                        existingSubDetailItem.setOralHealthIP(subDetailItem.getOralHealthIP());
                    }
                    if (subDetailItem.getOralHealthOP() != null) {
                        existingSubDetailItem.setOralHealthOP(subDetailItem.getOralHealthOP());
                    }
                    if (subDetailItem.getProcedure() != null) {
                        existingSubDetailItem.setProcedure(subDetailItem.getProcedure());
                    }
                    if (subDetailItem.getServices() != null) {
                        existingSubDetailItem.setServices(subDetailItem.getServices());
                    }
                    if (subDetailItem.getMedicationCode() != null) {
                        existingSubDetailItem.setMedicationCode(subDetailItem.getMedicationCode());
                    }
                    if (subDetailItem.getQuantity() != null) {
                        existingSubDetailItem.setQuantity(subDetailItem.getQuantity());
                    }
                    if (subDetailItem.getUnitPrice() != null) {
                        existingSubDetailItem.setUnitPrice(subDetailItem.getUnitPrice());
                    }

                    return existingSubDetailItem;
                }
            )
            .map(subDetailItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subDetailItem.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-detail-items} : get all the subDetailItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subDetailItems in body.
     */
    @GetMapping("/sub-detail-items")
    public List<SubDetailItem> getAllSubDetailItems() {
        log.debug("REST request to get all SubDetailItems");
        return subDetailItemRepository.findAll();
    }

    /**
     * {@code GET  /sub-detail-items/:id} : get the "id" subDetailItem.
     *
     * @param id the id of the subDetailItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subDetailItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-detail-items/{id}")
    public ResponseEntity<SubDetailItem> getSubDetailItem(@PathVariable Long id) {
        log.debug("REST request to get SubDetailItem : {}", id);
        Optional<SubDetailItem> subDetailItem = subDetailItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subDetailItem);
    }

    /**
     * {@code DELETE  /sub-detail-items/:id} : delete the "id" subDetailItem.
     *
     * @param id the id of the subDetailItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-detail-items/{id}")
    public ResponseEntity<Void> deleteSubDetailItem(@PathVariable Long id) {
        log.debug("REST request to delete SubDetailItem : {}", id);
        subDetailItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
