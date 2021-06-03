package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.PayNotErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.PayNotErrorMessagesRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.PayNotErrorMessages}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PayNotErrorMessagesResource {

    private final Logger log = LoggerFactory.getLogger(PayNotErrorMessagesResource.class);

    private static final String ENTITY_NAME = "payNotErrorMessages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayNotErrorMessagesRepository payNotErrorMessagesRepository;

    public PayNotErrorMessagesResource(PayNotErrorMessagesRepository payNotErrorMessagesRepository) {
        this.payNotErrorMessagesRepository = payNotErrorMessagesRepository;
    }

    /**
     * {@code POST  /pay-not-error-messages} : Create a new payNotErrorMessages.
     *
     * @param payNotErrorMessages the payNotErrorMessages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payNotErrorMessages, or with status {@code 400 (Bad Request)} if the payNotErrorMessages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pay-not-error-messages")
    public ResponseEntity<PayNotErrorMessages> createPayNotErrorMessages(@RequestBody PayNotErrorMessages payNotErrorMessages)
        throws URISyntaxException {
        log.debug("REST request to save PayNotErrorMessages : {}", payNotErrorMessages);
        if (payNotErrorMessages.getId() != null) {
            throw new BadRequestAlertException("A new payNotErrorMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayNotErrorMessages result = payNotErrorMessagesRepository.save(payNotErrorMessages);
        return ResponseEntity
            .created(new URI("/api/pay-not-error-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pay-not-error-messages/:id} : Updates an existing payNotErrorMessages.
     *
     * @param id the id of the payNotErrorMessages to save.
     * @param payNotErrorMessages the payNotErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payNotErrorMessages,
     * or with status {@code 400 (Bad Request)} if the payNotErrorMessages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payNotErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pay-not-error-messages/{id}")
    public ResponseEntity<PayNotErrorMessages> updatePayNotErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayNotErrorMessages payNotErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to update PayNotErrorMessages : {}, {}", id, payNotErrorMessages);
        if (payNotErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payNotErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payNotErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PayNotErrorMessages result = payNotErrorMessagesRepository.save(payNotErrorMessages);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payNotErrorMessages.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pay-not-error-messages/:id} : Partial updates given fields of an existing payNotErrorMessages, field will ignore if it is null
     *
     * @param id the id of the payNotErrorMessages to save.
     * @param payNotErrorMessages the payNotErrorMessages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payNotErrorMessages,
     * or with status {@code 400 (Bad Request)} if the payNotErrorMessages is not valid,
     * or with status {@code 404 (Not Found)} if the payNotErrorMessages is not found,
     * or with status {@code 500 (Internal Server Error)} if the payNotErrorMessages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pay-not-error-messages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PayNotErrorMessages> partialUpdatePayNotErrorMessages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayNotErrorMessages payNotErrorMessages
    ) throws URISyntaxException {
        log.debug("REST request to partial update PayNotErrorMessages partially : {}, {}", id, payNotErrorMessages);
        if (payNotErrorMessages.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payNotErrorMessages.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payNotErrorMessagesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PayNotErrorMessages> result = payNotErrorMessagesRepository
            .findById(payNotErrorMessages.getId())
            .map(
                existingPayNotErrorMessages -> {
                    if (payNotErrorMessages.getMessage() != null) {
                        existingPayNotErrorMessages.setMessage(payNotErrorMessages.getMessage());
                    }

                    return existingPayNotErrorMessages;
                }
            )
            .map(payNotErrorMessagesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payNotErrorMessages.getId().toString())
        );
    }

    /**
     * {@code GET  /pay-not-error-messages} : get all the payNotErrorMessages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payNotErrorMessages in body.
     */
    @GetMapping("/pay-not-error-messages")
    public List<PayNotErrorMessages> getAllPayNotErrorMessages() {
        log.debug("REST request to get all PayNotErrorMessages");
        return payNotErrorMessagesRepository.findAll();
    }

    /**
     * {@code GET  /pay-not-error-messages/:id} : get the "id" payNotErrorMessages.
     *
     * @param id the id of the payNotErrorMessages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payNotErrorMessages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pay-not-error-messages/{id}")
    public ResponseEntity<PayNotErrorMessages> getPayNotErrorMessages(@PathVariable Long id) {
        log.debug("REST request to get PayNotErrorMessages : {}", id);
        Optional<PayNotErrorMessages> payNotErrorMessages = payNotErrorMessagesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(payNotErrorMessages);
    }

    /**
     * {@code DELETE  /pay-not-error-messages/:id} : delete the "id" payNotErrorMessages.
     *
     * @param id the id of the payNotErrorMessages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pay-not-error-messages/{id}")
    public ResponseEntity<Void> deletePayNotErrorMessages(@PathVariable Long id) {
        log.debug("REST request to delete PayNotErrorMessages : {}", id);
        payNotErrorMessagesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
