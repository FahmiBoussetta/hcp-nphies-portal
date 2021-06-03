package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.CovEliRespErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.CovEliRespErrorMessagesRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.CovEliRespErrorMessages}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CovEliRespErrorMessagesResource {

    private final Logger log = LoggerFactory.getLogger(CovEliRespErrorMessagesResource.class);

    private static final String ENTITY_NAME = "covEliRespErrorMessages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CovEliRespErrorMessagesRepository covEliRespErrorMessagesRepository;

    public CovEliRespErrorMessagesResource(CovEliRespErrorMessagesRepository covEliRespErrorMessagesRepository) {
        this.covEliRespErrorMessagesRepository = covEliRespErrorMessagesRepository;
    }

    /**
     * {@code POST  /cov-eli-resp-error-messages} : Create a new covEliRespErrorMessages.
     *
     * @param covEliRespErrorMessages the covEliRespErrorMessages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new covEliRespErrorMessages, or with status {@code 400 (Bad Request)} if the covEliRespErrorMessages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cov-eli-resp-error-messages")
    public ResponseEntity<CovEliRespErrorMessages> createCovEliRespErrorMessages(
        @RequestBody CovEliRespErrorMessages covEliRespErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to save CovEliRespErrorMessages : {}", covEliRespErrorMessages);
        if (covEliRespErrorMessages.getId() != null) {
            throw new BadRequestAlertException("A new covEliRespErrorMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CovEliRespErrorMessages result = covEliRespErrorMessagesRepository.save(covEliRespErrorMessages);
        return ResponseEntity
            .created(new URI("/api/cov-eli-resp-error-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cov-eli-resp-error-messages/:id} : Updates an existing covEliRespErrorMessages.
     *
     * @param id the id of the covEliRespErrorMessages to save.
     * @param covEliRespErrorMessages the covEliRespErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated covEliRespErrorMessages,
     * or with status {@code 400 (Bad Request)} if the covEliRespErrorMessages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the covEliRespErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cov-eli-resp-error-messages/{id}")
    public ResponseEntity<CovEliRespErrorMessages> updateCovEliRespErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CovEliRespErrorMessages covEliRespErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to update CovEliRespErrorMessages : {}, {}", id, covEliRespErrorMessages);
        if (covEliRespErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, covEliRespErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!covEliRespErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CovEliRespErrorMessages result = covEliRespErrorMessagesRepository.save(covEliRespErrorMessages);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, covEliRespErrorMessages.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cov-eli-resp-error-messages/:id} : Partial updates given fields of an existing covEliRespErrorMessages, field will ignore if it is null
     *
     * @param id the id of the covEliRespErrorMessages to save.
     * @param covEliRespErrorMessages the covEliRespErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated covEliRespErrorMessages,
     * or with status {@code 400 (Bad Request)} if the covEliRespErrorMessages is not valid,
     * or with status {@code 404 (Not Found)} if the covEliRespErrorMessages is not found,
     * or with status {@code 500 (Internal Server Error)} if the covEliRespErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cov-eli-resp-error-messages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CovEliRespErrorMessages> partialUpdateCovEliRespErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CovEliRespErrorMessages covEliRespErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to partial update CovEliRespErrorMessages partially : {}, {}", id, covEliRespErrorMessages);
        if (covEliRespErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, covEliRespErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!covEliRespErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CovEliRespErrorMessages> result = covEliRespErrorMessagesRepository
            .findById(covEliRespErrorMessages.getId())
            .map(
                existingCovEliRespErrorMessages -> {
                    if (covEliRespErrorMessages.getMessage() != null) {
                        existingCovEliRespErrorMessages.setMessage(covEliRespErrorMessages.getMessage());
                    }

                    return existingCovEliRespErrorMessages;
                }
            )
            .map(covEliRespErrorMessagesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, covEliRespErrorMessages.getId().toString())
        );
    }

    /**
     * {@code GET  /cov-eli-resp-error-messages} : get all the covEliRespErrorMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of covEliRespErrorMessages in body.
     */
    @GetMapping("/cov-eli-resp-error-messages")
    public List<CovEliRespErrorMessages> getAllCovEliRespErrorMessages() {
        log.debug("REST request to get all CovEliRespErrorMessages");
        return covEliRespErrorMessagesRepository.findAll();
    }

    /**
     * {@code GET  /cov-eli-resp-error-messages/:id} : get the "id" covEliRespErrorMessages.
     *
     * @param id the id of the covEliRespErrorMessages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the covEliRespErrorMessages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cov-eli-resp-error-messages/{id}")
    public ResponseEntity<CovEliRespErrorMessages> getCovEliRespErrorMessages(@PathVariable Long id) {
        log.debug("REST request to get CovEliRespErrorMessages : {}", id);
        Optional<CovEliRespErrorMessages> covEliRespErrorMessages = covEliRespErrorMessagesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(covEliRespErrorMessages);
    }

    /**
     * {@code DELETE  /cov-eli-resp-error-messages/:id} : delete the "id" covEliRespErrorMessages.
     *
     * @param id the id of the covEliRespErrorMessages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cov-eli-resp-error-messages/{id}")
    public ResponseEntity<Void> deleteCovEliRespErrorMessages(@PathVariable Long id) {
        log.debug("REST request to delete CovEliRespErrorMessages : {}", id);
        covEliRespErrorMessagesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
