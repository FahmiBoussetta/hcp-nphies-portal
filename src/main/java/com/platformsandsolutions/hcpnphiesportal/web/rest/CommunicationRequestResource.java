package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.CommunicationRequest;
import com.platformsandsolutions.hcpnphiesportal.repository.CommunicationRequestRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.CommunicationRequest}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CommunicationRequestResource {

    private final Logger log = LoggerFactory.getLogger(CommunicationRequestResource.class);

    private static final String ENTITY_NAME = "communicationRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommunicationRequestRepository communicationRequestRepository;

    public CommunicationRequestResource(CommunicationRequestRepository communicationRequestRepository) {
        this.communicationRequestRepository = communicationRequestRepository;
    }

    /**
     * {@code POST  /communication-requests} : Create a new communicationRequest.
     *
     * @param communicationRequest the communicationRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new communicationRequest, or with status {@code 400 (Bad Request)} if the communicationRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/communication-requests")
    public ResponseEntity<CommunicationRequest> createCommunicationRequest(@RequestBody CommunicationRequest communicationRequest)
        throws URISyntaxException {
        log.debug("REST request to save CommunicationRequest : {}", communicationRequest);
        if (communicationRequest.getId() != null) {
            throw new BadRequestAlertException("A new communicationRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommunicationRequest result = communicationRequestRepository.save(communicationRequest);
        return ResponseEntity
            .created(new URI("/api/communication-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /communication-requests/:id} : Updates an existing communicationRequest.
     *
     * @param id the id of the communicationRequest to save.
     * @param communicationRequest the communicationRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated communicationRequest,
     * or with status {@code 400 (Bad Request)} if the communicationRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the communicationRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/communication-requests/{id}")
    public ResponseEntity<CommunicationRequest> updateCommunicationRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommunicationRequest communicationRequest
    ) throws URISyntaxException {
        log.debug("REST request to update CommunicationRequest : {}, {}", id, communicationRequest);
        if (communicationRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, communicationRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!communicationRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommunicationRequest result = communicationRequestRepository.save(communicationRequest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, communicationRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /communication-requests/:id} : Partial updates given fields of an existing communicationRequest, field will ignore if it is null
     *
     * @param id the id of the communicationRequest to save.
     * @param communicationRequest the communicationRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated communicationRequest,
     * or with status {@code 400 (Bad Request)} if the communicationRequest is not valid,
     * or with status {@code 404 (Not Found)} if the communicationRequest is not found,
     * or with status {@code 500 (Internal Server Error)} if the communicationRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/communication-requests/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CommunicationRequest> partialUpdateCommunicationRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CommunicationRequest communicationRequest
    ) throws URISyntaxException {
        log.debug("REST request to partial update CommunicationRequest partially : {}, {}", id, communicationRequest);
        if (communicationRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, communicationRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!communicationRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommunicationRequest> result = communicationRequestRepository
            .findById(communicationRequest.getId())
            .map(
                existingCommunicationRequest -> {
                    if (communicationRequest.getValue() != null) {
                        existingCommunicationRequest.setValue(communicationRequest.getValue());
                    }
                    if (communicationRequest.getSystem() != null) {
                        existingCommunicationRequest.setSystem(communicationRequest.getSystem());
                    }
                    if (communicationRequest.getParsed() != null) {
                        existingCommunicationRequest.setParsed(communicationRequest.getParsed());
                    }
                    if (communicationRequest.getLimitDate() != null) {
                        existingCommunicationRequest.setLimitDate(communicationRequest.getLimitDate());
                    }

                    return existingCommunicationRequest;
                }
            )
            .map(communicationRequestRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, communicationRequest.getId().toString())
        );
    }

    /**
     * {@code GET  /communication-requests} : get all the communicationRequests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of communicationRequests in body.
     */
    @GetMapping("/communication-requests")
    public List<CommunicationRequest> getAllCommunicationRequests() {
        log.debug("REST request to get all CommunicationRequests");
        return communicationRequestRepository.findAll();
    }

    /**
     * {@code GET  /communication-requests/:id} : get the "id" communicationRequest.
     *
     * @param id the id of the communicationRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the communicationRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/communication-requests/{id}")
    public ResponseEntity<CommunicationRequest> getCommunicationRequest(@PathVariable Long id) {
        log.debug("REST request to get CommunicationRequest : {}", id);
        Optional<CommunicationRequest> communicationRequest = communicationRequestRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(communicationRequest);
    }

    /**
     * {@code DELETE  /communication-requests/:id} : delete the "id" communicationRequest.
     *
     * @param id the id of the communicationRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/communication-requests/{id}")
    public ResponseEntity<Void> deleteCommunicationRequest(@PathVariable Long id) {
        log.debug("REST request to delete CommunicationRequest : {}", id);
        communicationRequestRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
