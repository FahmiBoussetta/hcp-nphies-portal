package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.InsuranceBenefit;
import com.platformsandsolutions.hcpnphiesportal.repository.InsuranceBenefitRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.InsuranceBenefit}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InsuranceBenefitResource {

    private final Logger log = LoggerFactory.getLogger(InsuranceBenefitResource.class);

    private static final String ENTITY_NAME = "insuranceBenefit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InsuranceBenefitRepository insuranceBenefitRepository;

    public InsuranceBenefitResource(InsuranceBenefitRepository insuranceBenefitRepository) {
        this.insuranceBenefitRepository = insuranceBenefitRepository;
    }

    /**
     * {@code POST  /insurance-benefits} : Create a new insuranceBenefit.
     *
     * @param insuranceBenefit the insuranceBenefit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new insuranceBenefit, or with status {@code 400 (Bad Request)} if the insuranceBenefit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/insurance-benefits")
    public ResponseEntity<InsuranceBenefit> createInsuranceBenefit(@RequestBody InsuranceBenefit insuranceBenefit)
        throws URISyntaxException {
        log.debug("REST request to save InsuranceBenefit : {}", insuranceBenefit);
        if (insuranceBenefit.getId() != null) {
            throw new BadRequestAlertException("A new insuranceBenefit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsuranceBenefit result = insuranceBenefitRepository.save(insuranceBenefit);
        return ResponseEntity
            .created(new URI("/api/insurance-benefits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /insurance-benefits/:id} : Updates an existing insuranceBenefit.
     *
     * @param id the id of the insuranceBenefit to save.
     * @param insuranceBenefit the insuranceBenefit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated insuranceBenefit,
     * or with status {@code 400 (Bad Request)} if the insuranceBenefit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the insuranceBenefit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/insurance-benefits/{id}")
    public ResponseEntity<InsuranceBenefit> updateInsuranceBenefit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InsuranceBenefit insuranceBenefit
    ) throws URISyntaxException {
        log.debug("REST request to update InsuranceBenefit : {}, {}", id, insuranceBenefit);
        if (insuranceBenefit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, insuranceBenefit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!insuranceBenefitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InsuranceBenefit result = insuranceBenefitRepository.save(insuranceBenefit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, insuranceBenefit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /insurance-benefits/:id} : Partial updates given fields of an existing insuranceBenefit, field will ignore if it is null
     *
     * @param id the id of the insuranceBenefit to save.
     * @param insuranceBenefit the insuranceBenefit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated insuranceBenefit,
     * or with status {@code 400 (Bad Request)} if the insuranceBenefit is not valid,
     * or with status {@code 404 (Not Found)} if the insuranceBenefit is not found,
     * or with status {@code 500 (Internal Server Error)} if the insuranceBenefit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/insurance-benefits/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<InsuranceBenefit> partialUpdateInsuranceBenefit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InsuranceBenefit insuranceBenefit
    ) throws URISyntaxException {
        log.debug("REST request to partial update InsuranceBenefit partially : {}, {}", id, insuranceBenefit);
        if (insuranceBenefit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, insuranceBenefit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!insuranceBenefitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InsuranceBenefit> result = insuranceBenefitRepository
            .findById(insuranceBenefit.getId())
            .map(
                existingInsuranceBenefit -> {
                    if (insuranceBenefit.getAllowed() != null) {
                        existingInsuranceBenefit.setAllowed(insuranceBenefit.getAllowed());
                    }
                    if (insuranceBenefit.getUsed() != null) {
                        existingInsuranceBenefit.setUsed(insuranceBenefit.getUsed());
                    }

                    return existingInsuranceBenefit;
                }
            )
            .map(insuranceBenefitRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, insuranceBenefit.getId().toString())
        );
    }

    /**
     * {@code GET  /insurance-benefits} : get all the insuranceBenefits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of insuranceBenefits in body.
     */
    @GetMapping("/insurance-benefits")
    public List<InsuranceBenefit> getAllInsuranceBenefits() {
        log.debug("REST request to get all InsuranceBenefits");
        return insuranceBenefitRepository.findAll();
    }

    /**
     * {@code GET  /insurance-benefits/:id} : get the "id" insuranceBenefit.
     *
     * @param id the id of the insuranceBenefit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the insuranceBenefit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/insurance-benefits/{id}")
    public ResponseEntity<InsuranceBenefit> getInsuranceBenefit(@PathVariable Long id) {
        log.debug("REST request to get InsuranceBenefit : {}", id);
        Optional<InsuranceBenefit> insuranceBenefit = insuranceBenefitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(insuranceBenefit);
    }

    /**
     * {@code DELETE  /insurance-benefits/:id} : delete the "id" insuranceBenefit.
     *
     * @param id the id of the insuranceBenefit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/insurance-benefits/{id}")
    public ResponseEntity<Void> deleteInsuranceBenefit(@PathVariable Long id) {
        log.debug("REST request to delete InsuranceBenefit : {}", id);
        insuranceBenefitRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
