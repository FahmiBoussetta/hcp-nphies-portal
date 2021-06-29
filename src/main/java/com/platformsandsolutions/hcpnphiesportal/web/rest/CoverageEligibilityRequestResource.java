package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.CoverageEligibilityRequest;
import com.platformsandsolutions.hcpnphiesportal.repository.CoverageEligibilityRequestRepository;
import com.platformsandsolutions.hcpnphiesportal.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.CoverageEligibilityRequest}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CoverageEligibilityRequestResource {

    private final Logger log = LoggerFactory.getLogger(CoverageEligibilityRequestResource.class);

    private static final String ENTITY_NAME = "coverageEligibilityRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoverageEligibilityRequestRepository coverageEligibilityRequestRepository;

    public CoverageEligibilityRequestResource(CoverageEligibilityRequestRepository coverageEligibilityRequestRepository) {
        this.coverageEligibilityRequestRepository = coverageEligibilityRequestRepository;
    }

    /**
     * {@code POST  /coverage-eligibility-requests} : Create a new coverageEligibilityRequest.
     *
     * @param coverageEligibilityRequest the coverageEligibilityRequest to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coverageEligibilityRequest, or with status {@code 400 (Bad Request)} if the coverageEligibilityRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coverage-eligibility-requests")
    public ResponseEntity<CoverageEligibilityRequest> createCoverageEligibilityRequest(
        @Valid @RequestBody CoverageEligibilityRequest coverageEligibilityRequest
    ) throws URISyntaxException {
        log.debug("REST request to save CoverageEligibilityRequest : {}", coverageEligibilityRequest);
        if (coverageEligibilityRequest.getId() != null) {
            throw new BadRequestAlertException("A new coverageEligibilityRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoverageEligibilityRequest result = coverageEligibilityRequestRepository.save(coverageEligibilityRequest);
        return ResponseEntity
            .created(new URI("/api/coverage-eligibility-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coverage-eligibility-requests/:id} : Updates an existing coverageEligibilityRequest.
     *
     * @param id the id of the coverageEligibilityRequest to save.
     * @param coverageEligibilityRequest the coverageEligibilityRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coverageEligibilityRequest,
     * or with status {@code 400 (Bad Request)} if the coverageEligibilityRequest is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coverageEligibilityRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coverage-eligibility-requests/{id}")
    public ResponseEntity<CoverageEligibilityRequest> updateCoverageEligibilityRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CoverageEligibilityRequest coverageEligibilityRequest
    ) throws URISyntaxException {
        log.debug("REST request to update CoverageEligibilityRequest : {}, {}", id, coverageEligibilityRequest);
        if (coverageEligibilityRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coverageEligibilityRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coverageEligibilityRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CoverageEligibilityRequest result = coverageEligibilityRequestRepository.save(coverageEligibilityRequest);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coverageEligibilityRequest.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /coverage-eligibility-requests/:id} : Partial updates given fields of an existing coverageEligibilityRequest, field will ignore if it is null
     *
     * @param id the id of the coverageEligibilityRequest to save.
     * @param coverageEligibilityRequest the coverageEligibilityRequest to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coverageEligibilityRequest,
     * or with status {@code 400 (Bad Request)} if the coverageEligibilityRequest is not valid,
     * or with status {@code 404 (Not Found)} if the coverageEligibilityRequest is not found,
     * or with status {@code 500 (Internal Server Error)} if the coverageEligibilityRequest couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/coverage-eligibility-requests/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CoverageEligibilityRequest> partialUpdateCoverageEligibilityRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CoverageEligibilityRequest coverageEligibilityRequest
    ) throws URISyntaxException {
        log.debug("REST request to partial update CoverageEligibilityRequest partially : {}, {}", id, coverageEligibilityRequest);
        if (coverageEligibilityRequest.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coverageEligibilityRequest.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coverageEligibilityRequestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CoverageEligibilityRequest> result = coverageEligibilityRequestRepository
            .findById(coverageEligibilityRequest.getId())
            .map(
                existingCoverageEligibilityRequest -> {
                    if (coverageEligibilityRequest.getGuid() != null) {
                        existingCoverageEligibilityRequest.setGuid(coverageEligibilityRequest.getGuid());
                    }
                    if (coverageEligibilityRequest.getParsed() != null) {
                        existingCoverageEligibilityRequest.setParsed(coverageEligibilityRequest.getParsed());
                    }
                    if (coverageEligibilityRequest.getPriority() != null) {
                        existingCoverageEligibilityRequest.setPriority(coverageEligibilityRequest.getPriority());
                    }
                    if (coverageEligibilityRequest.getIdentifier() != null) {
                        existingCoverageEligibilityRequest.setIdentifier(coverageEligibilityRequest.getIdentifier());
                    }
                    if (coverageEligibilityRequest.getServicedDate() != null) {
                        existingCoverageEligibilityRequest.setServicedDate(coverageEligibilityRequest.getServicedDate());
                    }
                    if (coverageEligibilityRequest.getServicedDateEnd() != null) {
                        existingCoverageEligibilityRequest.setServicedDateEnd(coverageEligibilityRequest.getServicedDateEnd());
                    }

                    return existingCoverageEligibilityRequest;
                }
            )
            .map(coverageEligibilityRequestRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coverageEligibilityRequest.getId().toString())
        );
    }

    /**
     * {@code GET  /coverage-eligibility-requests} : get all the coverageEligibilityRequests.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coverageEligibilityRequests in body.
     */
    @GetMapping("/coverage-eligibility-requests")
    public List<CoverageEligibilityRequest> getAllCoverageEligibilityRequests(
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get all CoverageEligibilityRequests");
        return coverageEligibilityRequestRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /coverage-eligibility-requests/:id} : get the "id" coverageEligibilityRequest.
     *
     * @param id the id of the coverageEligibilityRequest to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coverageEligibilityRequest, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coverage-eligibility-requests/{id}")
    public ResponseEntity<CoverageEligibilityRequest> getCoverageEligibilityRequest(@PathVariable Long id) {
        log.debug("REST request to get CoverageEligibilityRequest : {}", id);
        Optional<CoverageEligibilityRequest> coverageEligibilityRequest = coverageEligibilityRequestRepository.findOneWithEagerRelationships(
            id
        );
        return ResponseUtil.wrapOrNotFound(coverageEligibilityRequest);
    }

    /**
     * {@code DELETE  /coverage-eligibility-requests/:id} : delete the "id" coverageEligibilityRequest.
     *
     * @param id the id of the coverageEligibilityRequest to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coverage-eligibility-requests/{id}")
    public ResponseEntity<Void> deleteCoverageEligibilityRequest(@PathVariable Long id) {
        log.debug("REST request to delete CoverageEligibilityRequest : {}", id);
        coverageEligibilityRequestRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
