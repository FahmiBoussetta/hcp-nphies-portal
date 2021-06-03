package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ComErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.ComErrorMessagesRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ComErrorMessages}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ComErrorMessagesResource {

    private final Logger log = LoggerFactory.getLogger(ComErrorMessagesResource.class);

    private static final String ENTITY_NAME = "comErrorMessages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComErrorMessagesRepository comErrorMessagesRepository;

    public ComErrorMessagesResource(ComErrorMessagesRepository comErrorMessagesRepository) {
        this.comErrorMessagesRepository = comErrorMessagesRepository;
    }

    /**
     * {@code POST  /com-error-messages} : Create a new comErrorMessages.
     *
     * @param comErrorMessages the comErrorMessages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new comErrorMessages, or with status {@code 400 (Bad Request)} if the comErrorMessages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/com-error-messages")
    public ResponseEntity<ComErrorMessages> createComErrorMessages(@RequestBody ComErrorMessages comErrorMessages)
        throws URISyntaxException {
        log.debug("REST request to save ComErrorMessages : {}", comErrorMessages);
        if (comErrorMessages.getId() != null) {
            throw new BadRequestAlertException("A new comErrorMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComErrorMessages result = comErrorMessagesRepository.save(comErrorMessages);
        return ResponseEntity
            .created(new URI("/api/com-error-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /com-error-messages/:id} : Updates an existing comErrorMessages.
     *
     * @param id the id of the comErrorMessages to save.
     * @param comErrorMessages the comErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comErrorMessages,
     * or with status {@code 400 (Bad Request)} if the comErrorMessages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the comErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/com-error-messages/{id}")
    public ResponseEntity<ComErrorMessages> updateComErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ComErrorMessages comErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to update ComErrorMessages : {}, {}", id, comErrorMessages);
        if (comErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComErrorMessages result = comErrorMessagesRepository.save(comErrorMessages);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comErrorMessages.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /com-error-messages/:id} : Partial updates given fields of an existing comErrorMessages, field will ignore if it is null
     *
     * @param id the id of the comErrorMessages to save.
     * @param comErrorMessages the comErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated comErrorMessages,
     * or with status {@code 400 (Bad Request)} if the comErrorMessages is not valid,
     * or with status {@code 404 (Not Found)} if the comErrorMessages is not found,
     * or with status {@code 500 (Internal Server Error)} if the comErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/com-error-messages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ComErrorMessages> partialUpdateComErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ComErrorMessages comErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to partial update ComErrorMessages partially : {}, {}", id, comErrorMessages);
        if (comErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, comErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!comErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComErrorMessages> result = comErrorMessagesRepository
            .findById(comErrorMessages.getId())
            .map(
                existingComErrorMessages -> {
                    if (comErrorMessages.getMessage() != null) {
                        existingComErrorMessages.setMessage(comErrorMessages.getMessage());
                    }

                    return existingComErrorMessages;
                }
            )
            .map(comErrorMessagesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, comErrorMessages.getId().toString())
        );
    }

    /**
     * {@code GET  /com-error-messages} : get all the comErrorMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comErrorMessages in body.
     */
    @GetMapping("/com-error-messages")
    public List<ComErrorMessages> getAllComErrorMessages() {
        log.debug("REST request to get all ComErrorMessages");
        return comErrorMessagesRepository.findAll();
    }

    /**
     * {@code GET  /com-error-messages/:id} : get the "id" comErrorMessages.
     *
     * @param id the id of the comErrorMessages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the comErrorMessages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/com-error-messages/{id}")
    public ResponseEntity<ComErrorMessages> getComErrorMessages(@PathVariable Long id) {
        log.debug("REST request to get ComErrorMessages : {}", id);
        Optional<ComErrorMessages> comErrorMessages = comErrorMessagesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(comErrorMessages);
    }

    /**
     * {@code DELETE  /com-error-messages/:id} : delete the "id" comErrorMessages.
     *
     * @param id the id of the comErrorMessages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/com-error-messages/{id}")
    public ResponseEntity<Void> deleteComErrorMessages(@PathVariable Long id) {
        log.debug("REST request to delete ComErrorMessages : {}", id);
        comErrorMessagesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
