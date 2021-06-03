package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.DetailItem;
import com.platformsandsolutions.hcpnphiesportal.repository.DetailItemRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.DetailItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DetailItemResource {

    private final Logger log = LoggerFactory.getLogger(DetailItemResource.class);

    private static final String ENTITY_NAME = "detailItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailItemRepository detailItemRepository;

    public DetailItemResource(DetailItemRepository detailItemRepository) {
        this.detailItemRepository = detailItemRepository;
    }

    /**
     * {@code POST  /detail-items} : Create a new detailItem.
     *
     * @param detailItem the detailItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailItem, or with status {@code 400 (Bad Request)} if the detailItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-items")
    public ResponseEntity<DetailItem> createDetailItem(@Valid @RequestBody DetailItem detailItem) throws URISyntaxException {
        log.debug("REST request to save DetailItem : {}", detailItem);
        if (detailItem.getId() != null) {
            throw new BadRequestAlertException("A new detailItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailItem result = detailItemRepository.save(detailItem);
        return ResponseEntity
            .created(new URI("/api/detail-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-items/:id} : Updates an existing detailItem.
     *
     * @param id the id of the detailItem to save.
     * @param detailItem the detailItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailItem,
     * or with status {@code 400 (Bad Request)} if the detailItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-items/{id}")
    public ResponseEntity<DetailItem> updateDetailItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DetailItem detailItem
    ) throws URISyntaxException {
        log.debug("REST request to update DetailItem : {}, {}", id, detailItem);
        if (detailItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetailItem result = detailItemRepository.save(detailItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detail-items/:id} : Partial updates given fields of an existing detailItem, field will ignore if it is null
     *
     * @param id the id of the detailItem to save.
     * @param detailItem the detailItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailItem,
     * or with status {@code 400 (Bad Request)} if the detailItem is not valid,
     * or with status {@code 404 (Not Found)} if the detailItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detail-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DetailItem> partialUpdateDetailItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DetailItem detailItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetailItem partially : {}, {}", id, detailItem);
        if (detailItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailItem> result = detailItemRepository
            .findById(detailItem.getId())
            .map(
                existingDetailItem -> {
                    if (detailItem.getSequence() != null) {
                        existingDetailItem.setSequence(detailItem.getSequence());
                    }
                    if (detailItem.getTax() != null) {
                        existingDetailItem.setTax(detailItem.getTax());
                    }
                    if (detailItem.getTransportationSRCA() != null) {
                        existingDetailItem.setTransportationSRCA(detailItem.getTransportationSRCA());
                    }
                    if (detailItem.getImaging() != null) {
                        existingDetailItem.setImaging(detailItem.getImaging());
                    }
                    if (detailItem.getLaboratory() != null) {
                        existingDetailItem.setLaboratory(detailItem.getLaboratory());
                    }
                    if (detailItem.getMedicalDevice() != null) {
                        existingDetailItem.setMedicalDevice(detailItem.getMedicalDevice());
                    }
                    if (detailItem.getOralHealthIP() != null) {
                        existingDetailItem.setOralHealthIP(detailItem.getOralHealthIP());
                    }
                    if (detailItem.getOralHealthOP() != null) {
                        existingDetailItem.setOralHealthOP(detailItem.getOralHealthOP());
                    }
                    if (detailItem.getProcedure() != null) {
                        existingDetailItem.setProcedure(detailItem.getProcedure());
                    }
                    if (detailItem.getServices() != null) {
                        existingDetailItem.setServices(detailItem.getServices());
                    }
                    if (detailItem.getMedicationCode() != null) {
                        existingDetailItem.setMedicationCode(detailItem.getMedicationCode());
                    }
                    if (detailItem.getQuantity() != null) {
                        existingDetailItem.setQuantity(detailItem.getQuantity());
                    }
                    if (detailItem.getUnitPrice() != null) {
                        existingDetailItem.setUnitPrice(detailItem.getUnitPrice());
                    }

                    return existingDetailItem;
                }
            )
            .map(detailItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailItem.getId().toString())
        );
    }

    /**
     * {@code GET  /detail-items} : get all the detailItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailItems in body.
     */
    @GetMapping("/detail-items")
    public List<DetailItem> getAllDetailItems() {
        log.debug("REST request to get all DetailItems");
        return detailItemRepository.findAll();
    }

    /**
     * {@code GET  /detail-items/:id} : get the "id" detailItem.
     *
     * @param id the id of the detailItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-items/{id}")
    public ResponseEntity<DetailItem> getDetailItem(@PathVariable Long id) {
        log.debug("REST request to get DetailItem : {}", id);
        Optional<DetailItem> detailItem = detailItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(detailItem);
    }

    /**
     * {@code DELETE  /detail-items/:id} : delete the "id" detailItem.
     *
     * @param id the id of the detailItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-items/{id}")
    public ResponseEntity<Void> deleteDetailItem(@PathVariable Long id) {
        log.debug("REST request to delete DetailItem : {}", id);
        detailItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
