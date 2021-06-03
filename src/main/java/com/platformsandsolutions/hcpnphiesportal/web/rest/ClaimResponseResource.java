package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ClaimResponse;
import com.platformsandsolutions.hcpnphiesportal.repository.ClaimResponseRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ClaimResponse}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClaimResponseResource {

    private final Logger log = LoggerFactory.getLogger(ClaimResponseResource.class);

    private static final String ENTITY_NAME = "claimResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClaimResponseRepository claimResponseRepository;

    public ClaimResponseResource(ClaimResponseRepository claimResponseRepository) {
        this.claimResponseRepository = claimResponseRepository;
    }

    /**
     * {@code POST  /claim-responses} : Create a new claimResponse.
     *
     * @param claimResponse the claimResponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new claimResponse, or with status {@code 400 (Bad Request)} if the claimResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/claim-responses")
    public ResponseEntity<ClaimResponse> createClaimResponse(@RequestBody ClaimResponse claimResponse) throws URISyntaxException {
        log.debug("REST request to save ClaimResponse : {}", claimResponse);
        if (claimResponse.getId() != null) {
            throw new BadRequestAlertException("A new claimResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaimResponse result = claimResponseRepository.save(claimResponse);
        return ResponseEntity
            .created(new URI("/api/claim-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /claim-responses/:id} : Updates an existing claimResponse.
     *
     * @param id the id of the claimResponse to save.
     * @param claimResponse the claimResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimResponse,
     * or with status {@code 400 (Bad Request)} if the claimResponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the claimResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/claim-responses/{id}")
    public ResponseEntity<ClaimResponse> updateClaimResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClaimResponse claimResponse
    ) throws URISyntaxException {
        log.debug("REST request to update ClaimResponse : {}, {}", id, claimResponse);
        if (claimResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClaimResponse result = claimResponseRepository.save(claimResponse);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, claimResponse.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /claim-responses/:id} : Partial updates given fields of an existing claimResponse, field will ignore if it is null
     *
     * @param id the id of the claimResponse to save.
     * @param claimResponse the claimResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimResponse,
     * or with status {@code 400 (Bad Request)} if the claimResponse is not valid,
     * or with status {@code 404 (Not Found)} if the claimResponse is not found,
     * or with status {@code 500 (Internal Server Error)} if the claimResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/claim-responses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ClaimResponse> partialUpdateClaimResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClaimResponse claimResponse
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClaimResponse partially : {}, {}", id, claimResponse);
        if (claimResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClaimResponse> result = claimResponseRepository
            .findById(claimResponse.getId())
            .map(
                existingClaimResponse -> {
                    if (claimResponse.getValue() != null) {
                        existingClaimResponse.setValue(claimResponse.getValue());
                    }
                    if (claimResponse.getSystem() != null) {
                        existingClaimResponse.setSystem(claimResponse.getSystem());
                    }
                    if (claimResponse.getParsed() != null) {
                        existingClaimResponse.setParsed(claimResponse.getParsed());
                    }
                    if (claimResponse.getOutcome() != null) {
                        existingClaimResponse.setOutcome(claimResponse.getOutcome());
                    }

                    return existingClaimResponse;
                }
            )
            .map(claimResponseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, claimResponse.getId().toString())
        );
    }

    /**
     * {@code GET  /claim-responses} : get all the claimResponses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of claimResponses in body.
     */
    @GetMapping("/claim-responses")
    public List<ClaimResponse> getAllClaimResponses() {
        log.debug("REST request to get all ClaimResponses");
        return claimResponseRepository.findAll();
    }

    /**
     * {@code GET  /claim-responses/:id} : get the "id" claimResponse.
     *
     * @param id the id of the claimResponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the claimResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/claim-responses/{id}")
    public ResponseEntity<ClaimResponse> getClaimResponse(@PathVariable Long id) {
        log.debug("REST request to get ClaimResponse : {}", id);
        Optional<ClaimResponse> claimResponse = claimResponseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(claimResponse);
    }

    /**
     * {@code DELETE  /claim-responses/:id} : delete the "id" claimResponse.
     *
     * @param id the id of the claimResponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/claim-responses/{id}")
    public ResponseEntity<Void> deleteClaimResponse(@PathVariable Long id) {
        log.debug("REST request to delete ClaimResponse : {}", id);
        claimResponseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
