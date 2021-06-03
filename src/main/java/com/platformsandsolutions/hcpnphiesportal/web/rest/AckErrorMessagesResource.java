package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.AckErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.AckErrorMessagesRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.AckErrorMessages}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AckErrorMessagesResource {

    private final Logger log = LoggerFactory.getLogger(AckErrorMessagesResource.class);

    private static final String ENTITY_NAME = "ackErrorMessages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AckErrorMessagesRepository ackErrorMessagesRepository;

    public AckErrorMessagesResource(AckErrorMessagesRepository ackErrorMessagesRepository) {
        this.ackErrorMessagesRepository = ackErrorMessagesRepository;
    }

    /**
     * {@code POST  /ack-error-messages} : Create a new ackErrorMessages.
     *
     * @param ackErrorMessages the ackErrorMessages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ackErrorMessages, or with status {@code 400 (Bad Request)} if the ackErrorMessages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ack-error-messages")
    public ResponseEntity<AckErrorMessages> createAckErrorMessages(@RequestBody AckErrorMessages ackErrorMessages)
        throws URISyntaxException {
        log.debug("REST request to save AckErrorMessages : {}", ackErrorMessages);
        if (ackErrorMessages.getId() != null) {
            throw new BadRequestAlertException("A new ackErrorMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AckErrorMessages result = ackErrorMessagesRepository.save(ackErrorMessages);
        return ResponseEntity
            .created(new URI("/api/ack-error-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ack-error-messages/:id} : Updates an existing ackErrorMessages.
     *
     * @param id the id of the ackErrorMessages to save.
     * @param ackErrorMessages the ackErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ackErrorMessages,
     * or with status {@code 400 (Bad Request)} if the ackErrorMessages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ackErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ack-error-messages/{id}")
    public ResponseEntity<AckErrorMessages> updateAckErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AckErrorMessages ackErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to update AckErrorMessages : {}, {}", id, ackErrorMessages);
        if (ackErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ackErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ackErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AckErrorMessages result = ackErrorMessagesRepository.save(ackErrorMessages);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ackErrorMessages.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ack-error-messages/:id} : Partial updates given fields of an existing ackErrorMessages, field will ignore if it is null
     *
     * @param id the id of the ackErrorMessages to save.
     * @param ackErrorMessages the ackErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ackErrorMessages,
     * or with status {@code 400 (Bad Request)} if the ackErrorMessages is not valid,
     * or with status {@code 404 (Not Found)} if the ackErrorMessages is not found,
     * or with status {@code 500 (Internal Server Error)} if the ackErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ack-error-messages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AckErrorMessages> partialUpdateAckErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AckErrorMessages ackErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to partial update AckErrorMessages partially : {}, {}", id, ackErrorMessages);
        if (ackErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ackErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ackErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AckErrorMessages> result = ackErrorMessagesRepository
            .findById(ackErrorMessages.getId())
            .map(
                existingAckErrorMessages -> {
                    if (ackErrorMessages.getMessage() != null) {
                        existingAckErrorMessages.setMessage(ackErrorMessages.getMessage());
                    }

                    return existingAckErrorMessages;
                }
            )
            .map(ackErrorMessagesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ackErrorMessages.getId().toString())
        );
    }

    /**
     * {@code GET  /ack-error-messages} : get all the ackErrorMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ackErrorMessages in body.
     */
    @GetMapping("/ack-error-messages")
    public List<AckErrorMessages> getAllAckErrorMessages() {
        log.debug("REST request to get all AckErrorMessages");
        return ackErrorMessagesRepository.findAll();
    }

    /**
     * {@code GET  /ack-error-messages/:id} : get the "id" ackErrorMessages.
     *
     * @param id the id of the ackErrorMessages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ackErrorMessages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ack-error-messages/{id}")
    public ResponseEntity<AckErrorMessages> getAckErrorMessages(@PathVariable Long id) {
        log.debug("REST request to get AckErrorMessages : {}", id);
        Optional<AckErrorMessages> ackErrorMessages = ackErrorMessagesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ackErrorMessages);
    }

    /**
     * {@code DELETE  /ack-error-messages/:id} : delete the "id" ackErrorMessages.
     *
     * @param id the id of the ackErrorMessages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ack-error-messages/{id}")
    public ResponseEntity<Void> deleteAckErrorMessages(@PathVariable Long id) {
        log.debug("REST request to delete AckErrorMessages : {}", id);
        ackErrorMessagesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
