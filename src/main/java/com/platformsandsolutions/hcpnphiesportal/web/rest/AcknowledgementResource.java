package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Acknowledgement;
import com.platformsandsolutions.hcpnphiesportal.repository.AcknowledgementRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Acknowledgement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AcknowledgementResource {

    private final Logger log = LoggerFactory.getLogger(AcknowledgementResource.class);

    private static final String ENTITY_NAME = "acknowledgement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcknowledgementRepository acknowledgementRepository;

    public AcknowledgementResource(AcknowledgementRepository acknowledgementRepository) {
        this.acknowledgementRepository = acknowledgementRepository;
    }

    /**
     * {@code POST  /acknowledgements} : Create a new acknowledgement.
     *
     * @param acknowledgement the acknowledgement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new acknowledgement, or with status {@code 400 (Bad Request)} if the acknowledgement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/acknowledgements")
    public ResponseEntity<Acknowledgement> createAcknowledgement(@RequestBody Acknowledgement acknowledgement) throws URISyntaxException {
        log.debug("REST request to save Acknowledgement : {}", acknowledgement);
        if (acknowledgement.getId() != null) {
            throw new BadRequestAlertException("A new acknowledgement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Acknowledgement result = acknowledgementRepository.save(acknowledgement);
        return ResponseEntity
            .created(new URI("/api/acknowledgements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /acknowledgements/:id} : Updates an existing acknowledgement.
     *
     * @param id the id of the acknowledgement to save.
     * @param acknowledgement the acknowledgement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acknowledgement,
     * or with status {@code 400 (Bad Request)} if the acknowledgement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the acknowledgement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/acknowledgements/{id}")
    public ResponseEntity<Acknowledgement> updateAcknowledgement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Acknowledgement acknowledgement
    ) throws URISyntaxException {
        log.debug("REST request to update Acknowledgement : {}, {}", id, acknowledgement);
        if (acknowledgement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acknowledgement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!acknowledgementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Acknowledgement result = acknowledgementRepository.save(acknowledgement);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, acknowledgement.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /acknowledgements/:id} : Partial updates given fields of an existing acknowledgement, field will ignore if it is null
     *
     * @param id the id of the acknowledgement to save.
     * @param acknowledgement the acknowledgement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acknowledgement,
     * or with status {@code 400 (Bad Request)} if the acknowledgement is not valid,
     * or with status {@code 404 (Not Found)} if the acknowledgement is not found,
     * or with status {@code 500 (Internal Server Error)} if the acknowledgement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/acknowledgements/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Acknowledgement> partialUpdateAcknowledgement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Acknowledgement acknowledgement
    ) throws URISyntaxException {
        log.debug("REST request to partial update Acknowledgement partially : {}, {}", id, acknowledgement);
        if (acknowledgement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, acknowledgement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!acknowledgementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Acknowledgement> result = acknowledgementRepository
            .findById(acknowledgement.getId())
            .map(
                existingAcknowledgement -> {
                    if (acknowledgement.getValue() != null) {
                        existingAcknowledgement.setValue(acknowledgement.getValue());
                    }
                    if (acknowledgement.getSystem() != null) {
                        existingAcknowledgement.setSystem(acknowledgement.getSystem());
                    }
                    if (acknowledgement.getParsed() != null) {
                        existingAcknowledgement.setParsed(acknowledgement.getParsed());
                    }

                    return existingAcknowledgement;
                }
            )
            .map(acknowledgementRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, acknowledgement.getId().toString())
        );
    }

    /**
     * {@code GET  /acknowledgements} : get all the acknowledgements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acknowledgements in body.
     */
    @GetMapping("/acknowledgements")
    public List<Acknowledgement> getAllAcknowledgements() {
        log.debug("REST request to get all Acknowledgements");
        return acknowledgementRepository.findAll();
    }

    /**
     * {@code GET  /acknowledgements/:id} : get the "id" acknowledgement.
     *
     * @param id the id of the acknowledgement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the acknowledgement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acknowledgements/{id}")
    public ResponseEntity<Acknowledgement> getAcknowledgement(@PathVariable Long id) {
        log.debug("REST request to get Acknowledgement : {}", id);
        Optional<Acknowledgement> acknowledgement = acknowledgementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(acknowledgement);
    }

    /**
     * {@code DELETE  /acknowledgements/:id} : delete the "id" acknowledgement.
     *
     * @param id the id of the acknowledgement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/acknowledgements/{id}")
    public ResponseEntity<Void> deleteAcknowledgement(@PathVariable Long id) {
        log.debug("REST request to delete Acknowledgement : {}", id);
        acknowledgementRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
