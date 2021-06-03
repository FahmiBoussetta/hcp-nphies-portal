package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ResponseInsuranceItem;
import com.platformsandsolutions.hcpnphiesportal.repository.ResponseInsuranceItemRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ResponseInsuranceItem}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResponseInsuranceItemResource {

    private final Logger log = LoggerFactory.getLogger(ResponseInsuranceItemResource.class);

    private static final String ENTITY_NAME = "responseInsuranceItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponseInsuranceItemRepository responseInsuranceItemRepository;

    public ResponseInsuranceItemResource(ResponseInsuranceItemRepository responseInsuranceItemRepository) {
        this.responseInsuranceItemRepository = responseInsuranceItemRepository;
    }

    /**
     * {@code POST  /response-insurance-items} : Create a new responseInsuranceItem.
     *
     * @param responseInsuranceItem the responseInsuranceItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responseInsuranceItem, or with status {@code 400 (Bad Request)} if the responseInsuranceItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/response-insurance-items")
    public ResponseEntity<ResponseInsuranceItem> createResponseInsuranceItem(@RequestBody ResponseInsuranceItem responseInsuranceItem)
        throws URISyntaxException {
        log.debug("REST request to save ResponseInsuranceItem : {}", responseInsuranceItem);
        if (responseInsuranceItem.getId() != null) {
            throw new BadRequestAlertException("A new responseInsuranceItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponseInsuranceItem result = responseInsuranceItemRepository.save(responseInsuranceItem);
        return ResponseEntity
            .created(new URI("/api/response-insurance-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /response-insurance-items/:id} : Updates an existing responseInsuranceItem.
     *
     * @param id the id of the responseInsuranceItem to save.
     * @param responseInsuranceItem the responseInsuranceItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responseInsuranceItem,
     * or with status {@code 400 (Bad Request)} if the responseInsuranceItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responseInsuranceItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/response-insurance-items/{id}")
    public ResponseEntity<ResponseInsuranceItem> updateResponseInsuranceItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResponseInsuranceItem responseInsuranceItem
    ) throws URISyntaxException {
        log.debug("REST request to update ResponseInsuranceItem : {}, {}", id, responseInsuranceItem);
        if (responseInsuranceItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responseInsuranceItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responseInsuranceItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResponseInsuranceItem result = responseInsuranceItemRepository.save(responseInsuranceItem);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responseInsuranceItem.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /response-insurance-items/:id} : Partial updates given fields of an existing responseInsuranceItem, field will ignore if it is null
     *
     * @param id the id of the responseInsuranceItem to save.
     * @param responseInsuranceItem the responseInsuranceItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responseInsuranceItem,
     * or with status {@code 400 (Bad Request)} if the responseInsuranceItem is not valid,
     * or with status {@code 404 (Not Found)} if the responseInsuranceItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the responseInsuranceItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/response-insurance-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ResponseInsuranceItem> partialUpdateResponseInsuranceItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResponseInsuranceItem responseInsuranceItem
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResponseInsuranceItem partially : {}, {}", id, responseInsuranceItem);
        if (responseInsuranceItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responseInsuranceItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responseInsuranceItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResponseInsuranceItem> result = responseInsuranceItemRepository
            .findById(responseInsuranceItem.getId())
            .map(
                existingResponseInsuranceItem -> {
                    if (responseInsuranceItem.getCategory() != null) {
                        existingResponseInsuranceItem.setCategory(responseInsuranceItem.getCategory());
                    }
                    if (responseInsuranceItem.getExcluded() != null) {
                        existingResponseInsuranceItem.setExcluded(responseInsuranceItem.getExcluded());
                    }
                    if (responseInsuranceItem.getName() != null) {
                        existingResponseInsuranceItem.setName(responseInsuranceItem.getName());
                    }
                    if (responseInsuranceItem.getDescription() != null) {
                        existingResponseInsuranceItem.setDescription(responseInsuranceItem.getDescription());
                    }
                    if (responseInsuranceItem.getNetwork() != null) {
                        existingResponseInsuranceItem.setNetwork(responseInsuranceItem.getNetwork());
                    }
                    if (responseInsuranceItem.getUnit() != null) {
                        existingResponseInsuranceItem.setUnit(responseInsuranceItem.getUnit());
                    }
                    if (responseInsuranceItem.getTerm() != null) {
                        existingResponseInsuranceItem.setTerm(responseInsuranceItem.getTerm());
                    }

                    return existingResponseInsuranceItem;
                }
            )
            .map(responseInsuranceItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responseInsuranceItem.getId().toString())
        );
    }

    /**
     * {@code GET  /response-insurance-items} : get all the responseInsuranceItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responseInsuranceItems in body.
     */
    @GetMapping("/response-insurance-items")
    public List<ResponseInsuranceItem> getAllResponseInsuranceItems() {
        log.debug("REST request to get all ResponseInsuranceItems");
        return responseInsuranceItemRepository.findAll();
    }

    /**
     * {@code GET  /response-insurance-items/:id} : get the "id" responseInsuranceItem.
     *
     * @param id the id of the responseInsuranceItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responseInsuranceItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/response-insurance-items/{id}")
    public ResponseEntity<ResponseInsuranceItem> getResponseInsuranceItem(@PathVariable Long id) {
        log.debug("REST request to get ResponseInsuranceItem : {}", id);
        Optional<ResponseInsuranceItem> responseInsuranceItem = responseInsuranceItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(responseInsuranceItem);
    }

    /**
     * {@code DELETE  /response-insurance-items/:id} : delete the "id" responseInsuranceItem.
     *
     * @param id the id of the responseInsuranceItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/response-insurance-items/{id}")
    public ResponseEntity<Void> deleteResponseInsuranceItem(@PathVariable Long id) {
        log.debug("REST request to delete ResponseInsuranceItem : {}", id);
        responseInsuranceItemRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
