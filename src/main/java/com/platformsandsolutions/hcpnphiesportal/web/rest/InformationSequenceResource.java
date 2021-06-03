package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.InformationSequence;
import com.platformsandsolutions.hcpnphiesportal.repository.InformationSequenceRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.InformationSequence}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InformationSequenceResource {

    private final Logger log = LoggerFactory.getLogger(InformationSequenceResource.class);

    private static final String ENTITY_NAME = "informationSequence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InformationSequenceRepository informationSequenceRepository;

    public InformationSequenceResource(InformationSequenceRepository informationSequenceRepository) {
        this.informationSequenceRepository = informationSequenceRepository;
    }

    /**
     * {@code POST  /information-sequences} : Create a new informationSequence.
     *
     * @param informationSequence the informationSequence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new informationSequence, or with status {@code 400 (Bad Request)} if the informationSequence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/information-sequences")
    public ResponseEntity<InformationSequence> createInformationSequence(@RequestBody InformationSequence informationSequence)
        throws URISyntaxException {
        log.debug("REST request to save InformationSequence : {}", informationSequence);
        if (informationSequence.getId() != null) {
            throw new BadRequestAlertException("A new informationSequence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InformationSequence result = informationSequenceRepository.save(informationSequence);
        return ResponseEntity
            .created(new URI("/api/information-sequences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /information-sequences/:id} : Updates an existing informationSequence.
     *
     * @param id the id of the informationSequence to save.
     * @param informationSequence the informationSequence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informationSequence,
     * or with status {@code 400 (Bad Request)} if the informationSequence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the informationSequence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/information-sequences/{id}")
    public ResponseEntity<InformationSequence> updateInformationSequence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InformationSequence informationSequence
    ) throws URISyntaxException {
        log.debug("REST request to update InformationSequence : {}, {}", id, informationSequence);
        if (informationSequence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, informationSequence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!informationSequenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InformationSequence result = informationSequenceRepository.save(informationSequence);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, informationSequence.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /information-sequences/:id} : Partial updates given fields of an existing informationSequence, field will ignore if it is null
     *
     * @param id the id of the informationSequence to save.
     * @param informationSequence the informationSequence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informationSequence,
     * or with status {@code 400 (Bad Request)} if the informationSequence is not valid,
     * or with status {@code 404 (Not Found)} if the informationSequence is not found,
     * or with status {@code 500 (Internal Server Error)} if the informationSequence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/information-sequences/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<InformationSequence> partialUpdateInformationSequence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InformationSequence informationSequence
    ) throws URISyntaxException {
        log.debug("REST request to partial update InformationSequence partially : {}, {}", id, informationSequence);
        if (informationSequence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, informationSequence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!informationSequenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InformationSequence> result = informationSequenceRepository
            .findById(informationSequence.getId())
            .map(
                existingInformationSequence -> {
                    if (informationSequence.getInfSeq() != null) {
                        existingInformationSequence.setInfSeq(informationSequence.getInfSeq());
                    }

                    return existingInformationSequence;
                }
            )
            .map(informationSequenceRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, informationSequence.getId().toString())
        );
    }

    /**
     * {@code GET  /information-sequences} : get all the informationSequences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of informationSequences in body.
     */
    @GetMapping("/information-sequences")
    public List<InformationSequence> getAllInformationSequences() {
        log.debug("REST request to get all InformationSequences");
        return informationSequenceRepository.findAll();
    }

    /**
     * {@code GET  /information-sequences/:id} : get the "id" informationSequence.
     *
     * @param id the id of the informationSequence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the informationSequence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/information-sequences/{id}")
    public ResponseEntity<InformationSequence> getInformationSequence(@PathVariable Long id) {
        log.debug("REST request to get InformationSequence : {}", id);
        Optional<InformationSequence> informationSequence = informationSequenceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(informationSequence);
    }

    /**
     * {@code DELETE  /information-sequences/:id} : delete the "id" informationSequence.
     *
     * @param id the id of the informationSequence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/information-sequences/{id}")
    public ResponseEntity<Void> deleteInformationSequence(@PathVariable Long id) {
        log.debug("REST request to delete InformationSequence : {}", id);
        informationSequenceRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
