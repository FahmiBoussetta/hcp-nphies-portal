package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Payload;
import com.platformsandsolutions.hcpnphiesportal.repository.PayloadRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Payload}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PayloadResource {

    private final Logger log = LoggerFactory.getLogger(PayloadResource.class);

    private static final String ENTITY_NAME = "payload";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayloadRepository payloadRepository;

    public PayloadResource(PayloadRepository payloadRepository) {
        this.payloadRepository = payloadRepository;
    }

    /**
     * {@code POST  /payloads} : Create a new payload.
     *
     * @param payload the payload to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payload, or with status {@code 400 (Bad Request)} if the payload has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payloads")
    public ResponseEntity<Payload> createPayload(@RequestBody Payload payload) throws URISyntaxException {
        log.debug("REST request to save Payload : {}", payload);
        if (payload.getId() != null) {
            throw new BadRequestAlertException("A new payload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Payload result = payloadRepository.save(payload);
        return ResponseEntity
            .created(new URI("/api/payloads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payloads/:id} : Updates an existing payload.
     *
     * @param id the id of the payload to save.
     * @param payload the payload to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payload,
     * or with status {@code 400 (Bad Request)} if the payload is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payload couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payloads/{id}")
    public ResponseEntity<Payload> updatePayload(@PathVariable(value = "id", required = false) final Long id, @RequestBody Payload payload)
        throws URISyntaxException {
        log.debug("REST request to update Payload : {}, {}", id, payload);
        if (payload.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payload.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payloadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Payload result = payloadRepository.save(payload);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payload.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payloads/:id} : Partial updates given fields of an existing payload, field will ignore if it is null
     *
     * @param id the id of the payload to save.
     * @param payload the payload to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payload,
     * or with status {@code 400 (Bad Request)} if the payload is not valid,
     * or with status {@code 404 (Not Found)} if the payload is not found,
     * or with status {@code 500 (Internal Server Error)} if the payload couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payloads/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Payload> partialUpdatePayload(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Payload payload
    ) throws URISyntaxException {
        log.debug("REST request to partial update Payload partially : {}, {}", id, payload);
        if (payload.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payload.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payloadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Payload> result = payloadRepository
            .findById(payload.getId())
            .map(
                existingPayload -> {
                    if (payload.getContentString() != null) {
                        existingPayload.setContentString(payload.getContentString());
                    }

                    return existingPayload;
                }
            )
            .map(payloadRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payload.getId().toString())
        );
    }

    /**
     * {@code GET  /payloads} : get all the payloads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payloads in body.
     */
    @GetMapping("/payloads")
    public List<Payload> getAllPayloads() {
        log.debug("REST request to get all Payloads");
        return payloadRepository.findAll();
    }

    /**
     * {@code GET  /payloads/:id} : get the "id" payload.
     *
     * @param id the id of the payload to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payload, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payloads/{id}")
    public ResponseEntity<Payload> getPayload(@PathVariable Long id) {
        log.debug("REST request to get Payload : {}", id);
        Optional<Payload> payload = payloadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(payload);
    }

    /**
     * {@code DELETE  /payloads/:id} : delete the "id" payload.
     *
     * @param id the id of the payload to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payloads/{id}")
    public ResponseEntity<Void> deletePayload(@PathVariable Long id) {
        log.debug("REST request to delete Payload : {}", id);
        payloadRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
