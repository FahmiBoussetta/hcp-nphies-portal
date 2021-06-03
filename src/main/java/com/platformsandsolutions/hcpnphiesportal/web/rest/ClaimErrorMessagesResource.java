package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ClaimErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.ClaimErrorMessagesRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ClaimErrorMessages}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClaimErrorMessagesResource {

    private final Logger log = LoggerFactory.getLogger(ClaimErrorMessagesResource.class);

    private static final String ENTITY_NAME = "claimErrorMessages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClaimErrorMessagesRepository claimErrorMessagesRepository;

    public ClaimErrorMessagesResource(ClaimErrorMessagesRepository claimErrorMessagesRepository) {
        this.claimErrorMessagesRepository = claimErrorMessagesRepository;
    }

    /**
     * {@code POST  /claim-error-messages} : Create a new claimErrorMessages.
     *
     * @param claimErrorMessages the claimErrorMessages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new claimErrorMessages, or with status {@code 400 (Bad Request)} if the claimErrorMessages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/claim-error-messages")
    public ResponseEntity<ClaimErrorMessages> createClaimErrorMessages(@RequestBody ClaimErrorMessages claimErrorMessages)
        throws URISyntaxException {
        log.debug("REST request to save ClaimErrorMessages : {}", claimErrorMessages);
        if (claimErrorMessages.getId() != null) {
            throw new BadRequestAlertException("A new claimErrorMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaimErrorMessages result = claimErrorMessagesRepository.save(claimErrorMessages);
        return ResponseEntity
            .created(new URI("/api/claim-error-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /claim-error-messages/:id} : Updates an existing claimErrorMessages.
     *
     * @param id the id of the claimErrorMessages to save.
     * @param claimErrorMessages the claimErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimErrorMessages,
     * or with status {@code 400 (Bad Request)} if the claimErrorMessages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the claimErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/claim-error-messages/{id}")
    public ResponseEntity<ClaimErrorMessages> updateClaimErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClaimErrorMessages claimErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to update ClaimErrorMessages : {}, {}", id, claimErrorMessages);
        if (claimErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClaimErrorMessages result = claimErrorMessagesRepository.save(claimErrorMessages);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, claimErrorMessages.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /claim-error-messages/:id} : Partial updates given fields of an existing claimErrorMessages, field will ignore if it is null
     *
     * @param id the id of the claimErrorMessages to save.
     * @param claimErrorMessages the claimErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimErrorMessages,
     * or with status {@code 400 (Bad Request)} if the claimErrorMessages is not valid,
     * or with status {@code 404 (Not Found)} if the claimErrorMessages is not found,
     * or with status {@code 500 (Internal Server Error)} if the claimErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/claim-error-messages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ClaimErrorMessages> partialUpdateClaimErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClaimErrorMessages claimErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClaimErrorMessages partially : {}, {}", id, claimErrorMessages);
        if (claimErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClaimErrorMessages> result = claimErrorMessagesRepository
            .findById(claimErrorMessages.getId())
            .map(
                existingClaimErrorMessages -> {
                    if (claimErrorMessages.getMessage() != null) {
                        existingClaimErrorMessages.setMessage(claimErrorMessages.getMessage());
                    }

                    return existingClaimErrorMessages;
                }
            )
            .map(claimErrorMessagesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, claimErrorMessages.getId().toString())
        );
    }

    /**
     * {@code GET  /claim-error-messages} : get all the claimErrorMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of claimErrorMessages in body.
     */
    @GetMapping("/claim-error-messages")
    public List<ClaimErrorMessages> getAllClaimErrorMessages() {
        log.debug("REST request to get all ClaimErrorMessages");
        return claimErrorMessagesRepository.findAll();
    }

    /**
     * {@code GET  /claim-error-messages/:id} : get the "id" claimErrorMessages.
     *
     * @param id the id of the claimErrorMessages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the claimErrorMessages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/claim-error-messages/{id}")
    public ResponseEntity<ClaimErrorMessages> getClaimErrorMessages(@PathVariable Long id) {
        log.debug("REST request to get ClaimErrorMessages : {}", id);
        Optional<ClaimErrorMessages> claimErrorMessages = claimErrorMessagesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(claimErrorMessages);
    }

    /**
     * {@code DELETE  /claim-error-messages/:id} : delete the "id" claimErrorMessages.
     *
     * @param id the id of the claimErrorMessages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/claim-error-messages/{id}")
    public ResponseEntity<Void> deleteClaimErrorMessages(@PathVariable Long id) {
        log.debug("REST request to delete ClaimErrorMessages : {}", id);
        claimErrorMessagesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
