package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.CRErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.CRErrorMessagesRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.CRErrorMessages}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CRErrorMessagesResource {

    private final Logger log = LoggerFactory.getLogger(CRErrorMessagesResource.class);

    private static final String ENTITY_NAME = "cRErrorMessages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CRErrorMessagesRepository cRErrorMessagesRepository;

    public CRErrorMessagesResource(CRErrorMessagesRepository cRErrorMessagesRepository) {
        this.cRErrorMessagesRepository = cRErrorMessagesRepository;
    }

    /**
     * {@code POST  /cr-error-messages} : Create a new cRErrorMessages.
     *
     * @param cRErrorMessages the cRErrorMessages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cRErrorMessages, or with status {@code 400 (Bad Request)} if the cRErrorMessages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cr-error-messages")
    public ResponseEntity<CRErrorMessages> createCRErrorMessages(@RequestBody CRErrorMessages cRErrorMessages) throws URISyntaxException {
        log.debug("REST request to save CRErrorMessages : {}", cRErrorMessages);
        if (cRErrorMessages.getId() != null) {
            throw new BadRequestAlertException("A new cRErrorMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CRErrorMessages result = cRErrorMessagesRepository.save(cRErrorMessages);
        return ResponseEntity
            .created(new URI("/api/cr-error-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cr-error-messages/:id} : Updates an existing cRErrorMessages.
     *
     * @param id the id of the cRErrorMessages to save.
     * @param cRErrorMessages the cRErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cRErrorMessages,
     * or with status {@code 400 (Bad Request)} if the cRErrorMessages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cRErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cr-error-messages/{id}")
    public ResponseEntity<CRErrorMessages> updateCRErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CRErrorMessages cRErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to update CRErrorMessages : {}, {}", id, cRErrorMessages);
        if (cRErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cRErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cRErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CRErrorMessages result = cRErrorMessagesRepository.save(cRErrorMessages);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cRErrorMessages.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cr-error-messages/:id} : Partial updates given fields of an existing cRErrorMessages, field will ignore if it is null
     *
     * @param id the id of the cRErrorMessages to save.
     * @param cRErrorMessages the cRErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cRErrorMessages,
     * or with status {@code 400 (Bad Request)} if the cRErrorMessages is not valid,
     * or with status {@code 404 (Not Found)} if the cRErrorMessages is not found,
     * or with status {@code 500 (Internal Server Error)} if the cRErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cr-error-messages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CRErrorMessages> partialUpdateCRErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CRErrorMessages cRErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to partial update CRErrorMessages partially : {}, {}", id, cRErrorMessages);
        if (cRErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cRErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cRErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CRErrorMessages> result = cRErrorMessagesRepository
            .findById(cRErrorMessages.getId())
            .map(
                existingCRErrorMessages -> {
                    if (cRErrorMessages.getMessage() != null) {
                        existingCRErrorMessages.setMessage(cRErrorMessages.getMessage());
                    }

                    return existingCRErrorMessages;
                }
            )
            .map(cRErrorMessagesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cRErrorMessages.getId().toString())
        );
    }

    /**
     * {@code GET  /cr-error-messages} : get all the cRErrorMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cRErrorMessages in body.
     */
    @GetMapping("/cr-error-messages")
    public List<CRErrorMessages> getAllCRErrorMessages() {
        log.debug("REST request to get all CRErrorMessages");
        return cRErrorMessagesRepository.findAll();
    }

    /**
     * {@code GET  /cr-error-messages/:id} : get the "id" cRErrorMessages.
     *
     * @param id the id of the cRErrorMessages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cRErrorMessages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cr-error-messages/{id}")
    public ResponseEntity<CRErrorMessages> getCRErrorMessages(@PathVariable Long id) {
        log.debug("REST request to get CRErrorMessages : {}", id);
        Optional<CRErrorMessages> cRErrorMessages = cRErrorMessagesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cRErrorMessages);
    }

    /**
     * {@code DELETE  /cr-error-messages/:id} : delete the "id" cRErrorMessages.
     *
     * @param id the id of the cRErrorMessages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cr-error-messages/{id}")
    public ResponseEntity<Void> deleteCRErrorMessages(@PathVariable Long id) {
        log.debug("REST request to delete CRErrorMessages : {}", id);
        cRErrorMessagesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
