package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.CoverageEligibilityResponse;
import com.platformsandsolutions.hcpnphiesportal.repository.CoverageEligibilityResponseRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.CoverageEligibilityResponse}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CoverageEligibilityResponseResource {

    private final Logger log = LoggerFactory.getLogger(CoverageEligibilityResponseResource.class);

    private static final String ENTITY_NAME = "coverageEligibilityResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoverageEligibilityResponseRepository coverageEligibilityResponseRepository;

    public CoverageEligibilityResponseResource(CoverageEligibilityResponseRepository coverageEligibilityResponseRepository) {
        this.coverageEligibilityResponseRepository = coverageEligibilityResponseRepository;
    }

    /**
     * {@code POST  /coverage-eligibility-responses} : Create a new coverageEligibilityResponse.
     *
     * @param coverageEligibilityResponse the coverageEligibilityResponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coverageEligibilityResponse, or with status {@code 400 (Bad Request)} if the coverageEligibilityResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coverage-eligibility-responses")
    public ResponseEntity<CoverageEligibilityResponse> createCoverageEligibilityResponse(
        @RequestBody CoverageEligibilityResponse coverageEligibilityResponse
    ) throws URISyntaxException {
        log.debug("REST request to save CoverageEligibilityResponse : {}", coverageEligibilityResponse);
        if (coverageEligibilityResponse.getId() != null) {
            throw new BadRequestAlertException("A new coverageEligibilityResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoverageEligibilityResponse result = coverageEligibilityResponseRepository.save(coverageEligibilityResponse);
        return ResponseEntity
            .created(new URI("/api/coverage-eligibility-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coverage-eligibility-responses/:id} : Updates an existing coverageEligibilityResponse.
     *
     * @param id the id of the coverageEligibilityResponse to save.
     * @param coverageEligibilityResponse the coverageEligibilityResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coverageEligibilityResponse,
     * or with status {@code 400 (Bad Request)} if the coverageEligibilityResponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coverageEligibilityResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coverage-eligibility-responses/{id}")
    public ResponseEntity<CoverageEligibilityResponse> updateCoverageEligibilityResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CoverageEligibilityResponse coverageEligibilityResponse
    ) throws URISyntaxException {
        log.debug("REST request to update CoverageEligibilityResponse : {}, {}", id, coverageEligibilityResponse);
        if (coverageEligibilityResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coverageEligibilityResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coverageEligibilityResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CoverageEligibilityResponse result = coverageEligibilityResponseRepository.save(coverageEligibilityResponse);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coverageEligibilityResponse.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /coverage-eligibility-responses/:id} : Partial updates given fields of an existing coverageEligibilityResponse, field will ignore if it is null
     *
     * @param id the id of the coverageEligibilityResponse to save.
     * @param coverageEligibilityResponse the coverageEligibilityResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coverageEligibilityResponse,
     * or with status {@code 400 (Bad Request)} if the coverageEligibilityResponse is not valid,
     * or with status {@code 404 (Not Found)} if the coverageEligibilityResponse is not found,
     * or with status {@code 500 (Internal Server Error)} if the coverageEligibilityResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/coverage-eligibility-responses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CoverageEligibilityResponse> partialUpdateCoverageEligibilityResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CoverageEligibilityResponse coverageEligibilityResponse
    ) throws URISyntaxException {
        log.debug("REST request to partial update CoverageEligibilityResponse partially : {}, {}", id, coverageEligibilityResponse);
        if (coverageEligibilityResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coverageEligibilityResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coverageEligibilityResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CoverageEligibilityResponse> result = coverageEligibilityResponseRepository
            .findById(coverageEligibilityResponse.getId())
            .map(
                existingCoverageEligibilityResponse -> {
                    if (coverageEligibilityResponse.getValue() != null) {
                        existingCoverageEligibilityResponse.setValue(coverageEligibilityResponse.getValue());
                    }
                    if (coverageEligibilityResponse.getSystem() != null) {
                        existingCoverageEligibilityResponse.setSystem(coverageEligibilityResponse.getSystem());
                    }
                    if (coverageEligibilityResponse.getParsed() != null) {
                        existingCoverageEligibilityResponse.setParsed(coverageEligibilityResponse.getParsed());
                    }
                    if (coverageEligibilityResponse.getOutcome() != null) {
                        existingCoverageEligibilityResponse.setOutcome(coverageEligibilityResponse.getOutcome());
                    }
                    if (coverageEligibilityResponse.getServiced() != null) {
                        existingCoverageEligibilityResponse.setServiced(coverageEligibilityResponse.getServiced());
                    }
                    if (coverageEligibilityResponse.getServicedEnd() != null) {
                        existingCoverageEligibilityResponse.setServicedEnd(coverageEligibilityResponse.getServicedEnd());
                    }
                    if (coverageEligibilityResponse.getDisposition() != null) {
                        existingCoverageEligibilityResponse.setDisposition(coverageEligibilityResponse.getDisposition());
                    }
                    if (coverageEligibilityResponse.getNotInforceReason() != null) {
                        existingCoverageEligibilityResponse.setNotInforceReason(coverageEligibilityResponse.getNotInforceReason());
                    }

                    return existingCoverageEligibilityResponse;
                }
            )
            .map(coverageEligibilityResponseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coverageEligibilityResponse.getId().toString())
        );
    }

    /**
     * {@code GET  /coverage-eligibility-responses} : get all the coverageEligibilityResponses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coverageEligibilityResponses in body.
     */
    @GetMapping("/coverage-eligibility-responses")
    public List<CoverageEligibilityResponse> getAllCoverageEligibilityResponses() {
        log.debug("REST request to get all CoverageEligibilityResponses");
        return coverageEligibilityResponseRepository.findAll();
    }

    /**
     * {@code GET  /coverage-eligibility-responses/:id} : get the "id" coverageEligibilityResponse.
     *
     * @param id the id of the coverageEligibilityResponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coverageEligibilityResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coverage-eligibility-responses/{id}")
    public ResponseEntity<CoverageEligibilityResponse> getCoverageEligibilityResponse(@PathVariable Long id) {
        log.debug("REST request to get CoverageEligibilityResponse : {}", id);
        Optional<CoverageEligibilityResponse> coverageEligibilityResponse = coverageEligibilityResponseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(coverageEligibilityResponse);
    }

    /**
     * {@code DELETE  /coverage-eligibility-responses/:id} : delete the "id" coverageEligibilityResponse.
     *
     * @param id the id of the coverageEligibilityResponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coverage-eligibility-responses/{id}")
    public ResponseEntity<Void> deleteCoverageEligibilityResponse(@PathVariable Long id) {
        log.debug("REST request to delete CoverageEligibilityResponse : {}", id);
        coverageEligibilityResponseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
