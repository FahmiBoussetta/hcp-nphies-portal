package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Coverage;
import com.platformsandsolutions.hcpnphiesportal.repository.CoverageRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Coverage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CoverageResource {

    private final Logger log = LoggerFactory.getLogger(CoverageResource.class);

    private static final String ENTITY_NAME = "coverage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoverageRepository coverageRepository;

    public CoverageResource(CoverageRepository coverageRepository) {
        this.coverageRepository = coverageRepository;
    }

    /**
     * {@code POST  /coverages} : Create a new coverage.
     *
     * @param coverage the coverage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coverage, or with status {@code 400 (Bad Request)} if the coverage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coverages")
    public ResponseEntity<Coverage> createCoverage(@Valid @RequestBody Coverage coverage) throws URISyntaxException {
        log.debug("REST request to save Coverage : {}", coverage);
        if (coverage.getId() != null) {
            throw new BadRequestAlertException("A new coverage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Coverage result = coverageRepository.save(coverage);
        return ResponseEntity
            .created(new URI("/api/coverages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coverages/:id} : Updates an existing coverage.
     *
     * @param id the id of the coverage to save.
     * @param coverage the coverage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coverage,
     * or with status {@code 400 (Bad Request)} if the coverage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coverage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coverages/{id}")
    public ResponseEntity<Coverage> updateCoverage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Coverage coverage
    ) throws URISyntaxException {
        log.debug("REST request to update Coverage : {}, {}", id, coverage);
        if (coverage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coverage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coverageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Coverage result = coverageRepository.save(coverage);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coverage.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /coverages/:id} : Partial updates given fields of an existing coverage, field will ignore if it is null
     *
     * @param id the id of the coverage to save.
     * @param coverage the coverage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coverage,
     * or with status {@code 400 (Bad Request)} if the coverage is not valid,
     * or with status {@code 404 (Not Found)} if the coverage is not found,
     * or with status {@code 500 (Internal Server Error)} if the coverage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/coverages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Coverage> partialUpdateCoverage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Coverage coverage
    ) throws URISyntaxException {
        log.debug("REST request to partial update Coverage partially : {}, {}", id, coverage);
        if (coverage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coverage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coverageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Coverage> result = coverageRepository
            .findById(coverage.getId())
            .map(
                existingCoverage -> {
                    if (coverage.getGuid() != null) {
                        existingCoverage.setGuid(coverage.getGuid());
                    }
                    if (coverage.getForceId() != null) {
                        existingCoverage.setForceId(coverage.getForceId());
                    }
                    if (coverage.getCoverageType() != null) {
                        existingCoverage.setCoverageType(coverage.getCoverageType());
                    }
                    if (coverage.getSubscriberId() != null) {
                        existingCoverage.setSubscriberId(coverage.getSubscriberId());
                    }
                    if (coverage.getDependent() != null) {
                        existingCoverage.setDependent(coverage.getDependent());
                    }
                    if (coverage.getRelationShip() != null) {
                        existingCoverage.setRelationShip(coverage.getRelationShip());
                    }
                    if (coverage.getNetwork() != null) {
                        existingCoverage.setNetwork(coverage.getNetwork());
                    }
                    if (coverage.getSubrogation() != null) {
                        existingCoverage.setSubrogation(coverage.getSubrogation());
                    }

                    return existingCoverage;
                }
            )
            .map(coverageRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coverage.getId().toString())
        );
    }

    /**
     * {@code GET  /coverages} : get all the coverages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coverages in body.
     */
    @GetMapping("/coverages")
    public List<Coverage> getAllCoverages() {
        log.debug("REST request to get all Coverages");
        return coverageRepository.findAll();
    }

    /**
     * {@code GET  /coverages/:id} : get the "id" coverage.
     *
     * @param id the id of the coverage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coverage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coverages/{id}")
    public ResponseEntity<Coverage> getCoverage(@PathVariable Long id) {
        log.debug("REST request to get Coverage : {}", id);
        Optional<Coverage> coverage = coverageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(coverage);
    }

    /**
     * {@code DELETE  /coverages/:id} : delete the "id" coverage.
     *
     * @param id the id of the coverage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coverages/{id}")
    public ResponseEntity<Void> deleteCoverage(@PathVariable Long id) {
        log.debug("REST request to delete Coverage : {}", id);
        coverageRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
