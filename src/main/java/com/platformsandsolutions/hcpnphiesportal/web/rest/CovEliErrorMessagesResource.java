package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.CovEliErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.CovEliErrorMessagesRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.CovEliErrorMessages}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CovEliErrorMessagesResource {

    private final Logger log = LoggerFactory.getLogger(CovEliErrorMessagesResource.class);

    private static final String ENTITY_NAME = "covEliErrorMessages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CovEliErrorMessagesRepository covEliErrorMessagesRepository;

    public CovEliErrorMessagesResource(CovEliErrorMessagesRepository covEliErrorMessagesRepository) {
        this.covEliErrorMessagesRepository = covEliErrorMessagesRepository;
    }

    /**
     * {@code POST  /cov-eli-error-messages} : Create a new covEliErrorMessages.
     *
     * @param covEliErrorMessages the covEliErrorMessages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new covEliErrorMessages, or with status {@code 400 (Bad Request)} if the covEliErrorMessages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cov-eli-error-messages")
    public ResponseEntity<CovEliErrorMessages> createCovEliErrorMessages(@RequestBody CovEliErrorMessages covEliErrorMessages)
        throws URISyntaxException {
        log.debug("REST request to save CovEliErrorMessages : {}", covEliErrorMessages);
        if (covEliErrorMessages.getId() != null) {
            throw new BadRequestAlertException("A new covEliErrorMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CovEliErrorMessages result = covEliErrorMessagesRepository.save(covEliErrorMessages);
        return ResponseEntity
            .created(new URI("/api/cov-eli-error-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cov-eli-error-messages/:id} : Updates an existing covEliErrorMessages.
     *
     * @param id the id of the covEliErrorMessages to save.
     * @param covEliErrorMessages the covEliErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated covEliErrorMessages,
     * or with status {@code 400 (Bad Request)} if the covEliErrorMessages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the covEliErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cov-eli-error-messages/{id}")
    public ResponseEntity<CovEliErrorMessages> updateCovEliErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CovEliErrorMessages covEliErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to update CovEliErrorMessages : {}, {}", id, covEliErrorMessages);
        if (covEliErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, covEliErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!covEliErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CovEliErrorMessages result = covEliErrorMessagesRepository.save(covEliErrorMessages);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, covEliErrorMessages.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cov-eli-error-messages/:id} : Partial updates given fields of an existing covEliErrorMessages, field will ignore if it is null
     *
     * @param id the id of the covEliErrorMessages to save.
     * @param covEliErrorMessages the covEliErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated covEliErrorMessages,
     * or with status {@code 400 (Bad Request)} if the covEliErrorMessages is not valid,
     * or with status {@code 404 (Not Found)} if the covEliErrorMessages is not found,
     * or with status {@code 500 (Internal Server Error)} if the covEliErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cov-eli-error-messages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CovEliErrorMessages> partialUpdateCovEliErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CovEliErrorMessages covEliErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to partial update CovEliErrorMessages partially : {}, {}", id, covEliErrorMessages);
        if (covEliErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, covEliErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!covEliErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CovEliErrorMessages> result = covEliErrorMessagesRepository
            .findById(covEliErrorMessages.getId())
            .map(
                existingCovEliErrorMessages -> {
                    if (covEliErrorMessages.getMessage() != null) {
                        existingCovEliErrorMessages.setMessage(covEliErrorMessages.getMessage());
                    }

                    return existingCovEliErrorMessages;
                }
            )
            .map(covEliErrorMessagesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, covEliErrorMessages.getId().toString())
        );
    }

    /**
     * {@code GET  /cov-eli-error-messages} : get all the covEliErrorMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of covEliErrorMessages in body.
     */
    @GetMapping("/cov-eli-error-messages")
    public List<CovEliErrorMessages> getAllCovEliErrorMessages() {
        log.debug("REST request to get all CovEliErrorMessages");
        return covEliErrorMessagesRepository.findAll();
    }

    /**
     * {@code GET  /cov-eli-error-messages/:id} : get the "id" covEliErrorMessages.
     *
     * @param id the id of the covEliErrorMessages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the covEliErrorMessages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cov-eli-error-messages/{id}")
    public ResponseEntity<CovEliErrorMessages> getCovEliErrorMessages(@PathVariable Long id) {
        log.debug("REST request to get CovEliErrorMessages : {}", id);
        Optional<CovEliErrorMessages> covEliErrorMessages = covEliErrorMessagesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(covEliErrorMessages);
    }

    /**
     * {@code DELETE  /cov-eli-error-messages/:id} : delete the "id" covEliErrorMessages.
     *
     * @param id the id of the covEliErrorMessages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cov-eli-error-messages/{id}")
    public ResponseEntity<Void> deleteCovEliErrorMessages(@PathVariable Long id) {
        log.debug("REST request to delete CovEliErrorMessages : {}", id);
        covEliErrorMessagesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
