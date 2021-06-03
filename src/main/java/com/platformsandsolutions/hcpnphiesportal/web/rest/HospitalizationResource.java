package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Hospitalization;
import com.platformsandsolutions.hcpnphiesportal.repository.HospitalizationRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Hospitalization}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HospitalizationResource {

    private final Logger log = LoggerFactory.getLogger(HospitalizationResource.class);

    private static final String ENTITY_NAME = "hospitalization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HospitalizationRepository hospitalizationRepository;

    public HospitalizationResource(HospitalizationRepository hospitalizationRepository) {
        this.hospitalizationRepository = hospitalizationRepository;
    }

    /**
     * {@code POST  /hospitalizations} : Create a new hospitalization.
     *
     * @param hospitalization the hospitalization to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hospitalization, or with status {@code 400 (Bad Request)} if the hospitalization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hospitalizations")
    public ResponseEntity<Hospitalization> createHospitalization(@RequestBody Hospitalization hospitalization) throws URISyntaxException {
        log.debug("REST request to save Hospitalization : {}", hospitalization);
        if (hospitalization.getId() != null) {
            throw new BadRequestAlertException("A new hospitalization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hospitalization result = hospitalizationRepository.save(hospitalization);
        return ResponseEntity
            .created(new URI("/api/hospitalizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hospitalizations/:id} : Updates an existing hospitalization.
     *
     * @param id the id of the hospitalization to save.
     * @param hospitalization the hospitalization to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospitalization,
     * or with status {@code 400 (Bad Request)} if the hospitalization is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hospitalization couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hospitalizations/{id}")
    public ResponseEntity<Hospitalization> updateHospitalization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Hospitalization hospitalization
    ) throws URISyntaxException {
        log.debug("REST request to update Hospitalization : {}, {}", id, hospitalization);
        if (hospitalization.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hospitalization.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hospitalizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Hospitalization result = hospitalizationRepository.save(hospitalization);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hospitalization.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hospitalizations/:id} : Partial updates given fields of an existing hospitalization, field will ignore if it is null
     *
     * @param id the id of the hospitalization to save.
     * @param hospitalization the hospitalization to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospitalization,
     * or with status {@code 400 (Bad Request)} if the hospitalization is not valid,
     * or with status {@code 404 (Not Found)} if the hospitalization is not found,
     * or with status {@code 500 (Internal Server Error)} if the hospitalization couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hospitalizations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Hospitalization> partialUpdateHospitalization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Hospitalization hospitalization
    ) throws URISyntaxException {
        log.debug("REST request to partial update Hospitalization partially : {}, {}", id, hospitalization);
        if (hospitalization.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hospitalization.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hospitalizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Hospitalization> result = hospitalizationRepository
            .findById(hospitalization.getId())
            .map(
                existingHospitalization -> {
                    if (hospitalization.getAdmitSource() != null) {
                        existingHospitalization.setAdmitSource(hospitalization.getAdmitSource());
                    }
                    if (hospitalization.getReAdmission() != null) {
                        existingHospitalization.setReAdmission(hospitalization.getReAdmission());
                    }
                    if (hospitalization.getDischargeDisposition() != null) {
                        existingHospitalization.setDischargeDisposition(hospitalization.getDischargeDisposition());
                    }

                    return existingHospitalization;
                }
            )
            .map(hospitalizationRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hospitalization.getId().toString())
        );
    }

    /**
     * {@code GET  /hospitalizations} : get all the hospitalizations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hospitalizations in body.
     */
    @GetMapping("/hospitalizations")
    public List<Hospitalization> getAllHospitalizations() {
        log.debug("REST request to get all Hospitalizations");
        return hospitalizationRepository.findAll();
    }

    /**
     * {@code GET  /hospitalizations/:id} : get the "id" hospitalization.
     *
     * @param id the id of the hospitalization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hospitalization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hospitalizations/{id}")
    public ResponseEntity<Hospitalization> getHospitalization(@PathVariable Long id) {
        log.debug("REST request to get Hospitalization : {}", id);
        Optional<Hospitalization> hospitalization = hospitalizationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hospitalization);
    }

    /**
     * {@code DELETE  /hospitalizations/:id} : delete the "id" hospitalization.
     *
     * @param id the id of the hospitalization to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hospitalizations/{id}")
    public ResponseEntity<Void> deleteHospitalization(@PathVariable Long id) {
        log.debug("REST request to delete Hospitalization : {}", id);
        hospitalizationRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
