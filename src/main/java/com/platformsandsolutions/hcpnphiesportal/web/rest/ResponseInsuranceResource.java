package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ResponseInsurance;
import com.platformsandsolutions.hcpnphiesportal.repository.ResponseInsuranceRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ResponseInsurance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResponseInsuranceResource {

    private final Logger log = LoggerFactory.getLogger(ResponseInsuranceResource.class);

    private static final String ENTITY_NAME = "responseInsurance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponseInsuranceRepository responseInsuranceRepository;

    public ResponseInsuranceResource(ResponseInsuranceRepository responseInsuranceRepository) {
        this.responseInsuranceRepository = responseInsuranceRepository;
    }

    /**
     * {@code POST  /response-insurances} : Create a new responseInsurance.
     *
     * @param responseInsurance the responseInsurance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responseInsurance, or with status {@code 400 (Bad Request)} if the responseInsurance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/response-insurances")
    public ResponseEntity<ResponseInsurance> createResponseInsurance(@RequestBody ResponseInsurance responseInsurance)
        throws URISyntaxException {
        log.debug("REST request to save ResponseInsurance : {}", responseInsurance);
        if (responseInsurance.getId() != null) {
            throw new BadRequestAlertException("A new responseInsurance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponseInsurance result = responseInsuranceRepository.save(responseInsurance);
        return ResponseEntity
            .created(new URI("/api/response-insurances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /response-insurances/:id} : Updates an existing responseInsurance.
     *
     * @param id the id of the responseInsurance to save.
     * @param responseInsurance the responseInsurance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responseInsurance,
     * or with status {@code 400 (Bad Request)} if the responseInsurance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responseInsurance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/response-insurances/{id}")
    public ResponseEntity<ResponseInsurance> updateResponseInsurance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResponseInsurance responseInsurance
    ) throws URISyntaxException {
        log.debug("REST request to update ResponseInsurance : {}, {}", id, responseInsurance);
        if (responseInsurance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responseInsurance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responseInsuranceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResponseInsurance result = responseInsuranceRepository.save(responseInsurance);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responseInsurance.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /response-insurances/:id} : Partial updates given fields of an existing responseInsurance, field will ignore if it is null
     *
     * @param id the id of the responseInsurance to save.
     * @param responseInsurance the responseInsurance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responseInsurance,
     * or with status {@code 400 (Bad Request)} if the responseInsurance is not valid,
     * or with status {@code 404 (Not Found)} if the responseInsurance is not found,
     * or with status {@code 500 (Internal Server Error)} if the responseInsurance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/response-insurances/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ResponseInsurance> partialUpdateResponseInsurance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResponseInsurance responseInsurance
    ) throws URISyntaxException {
        log.debug("REST request to partial update ResponseInsurance partially : {}, {}", id, responseInsurance);
        if (responseInsurance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responseInsurance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responseInsuranceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResponseInsurance> result = responseInsuranceRepository
            .findById(responseInsurance.getId())
            .map(
                existingResponseInsurance -> {
                    if (responseInsurance.getNotInforceReason() != null) {
                        existingResponseInsurance.setNotInforceReason(responseInsurance.getNotInforceReason());
                    }
                    if (responseInsurance.getInforce() != null) {
                        existingResponseInsurance.setInforce(responseInsurance.getInforce());
                    }
                    if (responseInsurance.getBenefitStart() != null) {
                        existingResponseInsurance.setBenefitStart(responseInsurance.getBenefitStart());
                    }
                    if (responseInsurance.getBenefitEnd() != null) {
                        existingResponseInsurance.setBenefitEnd(responseInsurance.getBenefitEnd());
                    }

                    return existingResponseInsurance;
                }
            )
            .map(responseInsuranceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responseInsurance.getId().toString())
        );
    }

    /**
     * {@code GET  /response-insurances} : get all the responseInsurances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responseInsurances in body.
     */
    @GetMapping("/response-insurances")
    public List<ResponseInsurance> getAllResponseInsurances() {
        log.debug("REST request to get all ResponseInsurances");
        return responseInsuranceRepository.findAll();
    }

    /**
     * {@code GET  /response-insurances/:id} : get the "id" responseInsurance.
     *
     * @param id the id of the responseInsurance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responseInsurance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/response-insurances/{id}")
    public ResponseEntity<ResponseInsurance> getResponseInsurance(@PathVariable Long id) {
        log.debug("REST request to get ResponseInsurance : {}", id);
        Optional<ResponseInsurance> responseInsurance = responseInsuranceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(responseInsurance);
    }

    /**
     * {@code DELETE  /response-insurances/:id} : delete the "id" responseInsurance.
     *
     * @param id the id of the responseInsurance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/response-insurances/{id}")
    public ResponseEntity<Void> deleteResponseInsurance(@PathVariable Long id) {
        log.debug("REST request to delete ResponseInsurance : {}", id);
        responseInsuranceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
