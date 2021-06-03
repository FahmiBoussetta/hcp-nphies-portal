package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.OpeOutErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.OpeOutErrorMessagesRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.OpeOutErrorMessages}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OpeOutErrorMessagesResource {

    private final Logger log = LoggerFactory.getLogger(OpeOutErrorMessagesResource.class);

    private static final String ENTITY_NAME = "opeOutErrorMessages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpeOutErrorMessagesRepository opeOutErrorMessagesRepository;

    public OpeOutErrorMessagesResource(OpeOutErrorMessagesRepository opeOutErrorMessagesRepository) {
        this.opeOutErrorMessagesRepository = opeOutErrorMessagesRepository;
    }

    /**
     * {@code POST  /ope-out-error-messages} : Create a new opeOutErrorMessages.
     *
     * @param opeOutErrorMessages the opeOutErrorMessages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opeOutErrorMessages, or with status {@code 400 (Bad Request)} if the opeOutErrorMessages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ope-out-error-messages")
    public ResponseEntity<OpeOutErrorMessages> createOpeOutErrorMessages(@RequestBody OpeOutErrorMessages opeOutErrorMessages)
        throws URISyntaxException {
        log.debug("REST request to save OpeOutErrorMessages : {}", opeOutErrorMessages);
        if (opeOutErrorMessages.getId() != null) {
            throw new BadRequestAlertException("A new opeOutErrorMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OpeOutErrorMessages result = opeOutErrorMessagesRepository.save(opeOutErrorMessages);
        return ResponseEntity
            .created(new URI("/api/ope-out-error-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ope-out-error-messages/:id} : Updates an existing opeOutErrorMessages.
     *
     * @param id the id of the opeOutErrorMessages to save.
     * @param opeOutErrorMessages the opeOutErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opeOutErrorMessages,
     * or with status {@code 400 (Bad Request)} if the opeOutErrorMessages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opeOutErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ope-out-error-messages/{id}")
    public ResponseEntity<OpeOutErrorMessages> updateOpeOutErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpeOutErrorMessages opeOutErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to update OpeOutErrorMessages : {}, {}", id, opeOutErrorMessages);
        if (opeOutErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opeOutErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opeOutErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OpeOutErrorMessages result = opeOutErrorMessagesRepository.save(opeOutErrorMessages);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opeOutErrorMessages.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ope-out-error-messages/:id} : Partial updates given fields of an existing opeOutErrorMessages, field will ignore if it is null
     *
     * @param id the id of the opeOutErrorMessages to save.
     * @param opeOutErrorMessages the opeOutErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opeOutErrorMessages,
     * or with status {@code 400 (Bad Request)} if the opeOutErrorMessages is not valid,
     * or with status {@code 404 (Not Found)} if the opeOutErrorMessages is not found,
     * or with status {@code 500 (Internal Server Error)} if the opeOutErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ope-out-error-messages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OpeOutErrorMessages> partialUpdateOpeOutErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpeOutErrorMessages opeOutErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to partial update OpeOutErrorMessages partially : {}, {}", id, opeOutErrorMessages);
        if (opeOutErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opeOutErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opeOutErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OpeOutErrorMessages> result = opeOutErrorMessagesRepository
            .findById(opeOutErrorMessages.getId())
            .map(
                existingOpeOutErrorMessages -> {
                    if (opeOutErrorMessages.getMessage() != null) {
                        existingOpeOutErrorMessages.setMessage(opeOutErrorMessages.getMessage());
                    }

                    return existingOpeOutErrorMessages;
                }
            )
            .map(opeOutErrorMessagesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opeOutErrorMessages.getId().toString())
        );
    }

    /**
     * {@code GET  /ope-out-error-messages} : get all the opeOutErrorMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opeOutErrorMessages in body.
     */
    @GetMapping("/ope-out-error-messages")
    public List<OpeOutErrorMessages> getAllOpeOutErrorMessages() {
        log.debug("REST request to get all OpeOutErrorMessages");
        return opeOutErrorMessagesRepository.findAll();
    }

    /**
     * {@code GET  /ope-out-error-messages/:id} : get the "id" opeOutErrorMessages.
     *
     * @param id the id of the opeOutErrorMessages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opeOutErrorMessages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ope-out-error-messages/{id}")
    public ResponseEntity<OpeOutErrorMessages> getOpeOutErrorMessages(@PathVariable Long id) {
        log.debug("REST request to get OpeOutErrorMessages : {}", id);
        Optional<OpeOutErrorMessages> opeOutErrorMessages = opeOutErrorMessagesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(opeOutErrorMessages);
    }

    /**
     * {@code DELETE  /ope-out-error-messages/:id} : delete the "id" opeOutErrorMessages.
     *
     * @param id the id of the opeOutErrorMessages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ope-out-error-messages/{id}")
    public ResponseEntity<Void> deleteOpeOutErrorMessages(@PathVariable Long id) {
        log.debug("REST request to delete OpeOutErrorMessages : {}", id);
        opeOutErrorMessagesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
