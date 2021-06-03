package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.HumanName;
import com.platformsandsolutions.hcpnphiesportal.repository.HumanNameRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.HumanName}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HumanNameResource {

    private final Logger log = LoggerFactory.getLogger(HumanNameResource.class);

    private static final String ENTITY_NAME = "humanName";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HumanNameRepository humanNameRepository;

    public HumanNameResource(HumanNameRepository humanNameRepository) {
        this.humanNameRepository = humanNameRepository;
    }

    /**
     * {@code POST  /human-names} : Create a new humanName.
     *
     * @param humanName the humanName to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new humanName, or with status {@code 400 (Bad Request)} if the humanName has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/human-names")
    public ResponseEntity<HumanName> createHumanName(@Valid @RequestBody HumanName humanName) throws URISyntaxException {
        log.debug("REST request to save HumanName : {}", humanName);
        if (humanName.getId() != null) {
            throw new BadRequestAlertException("A new humanName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HumanName result = humanNameRepository.save(humanName);
        return ResponseEntity
            .created(new URI("/api/human-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /human-names/:id} : Updates an existing humanName.
     *
     * @param id the id of the humanName to save.
     * @param humanName the humanName to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated humanName,
     * or with status {@code 400 (Bad Request)} if the humanName is not valid,
     * or with status {@code 500 (Internal Server Error)} if the humanName couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/human-names/{id}")
    public ResponseEntity<HumanName> updateHumanName(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HumanName humanName
    ) throws URISyntaxException {
        log.debug("REST request to update HumanName : {}, {}", id, humanName);
        if (humanName.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, humanName.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!humanNameRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HumanName result = humanNameRepository.save(humanName);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, humanName.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /human-names/:id} : Partial updates given fields of an existing humanName, field will ignore if it is null
     *
     * @param id the id of the humanName to save.
     * @param humanName the humanName to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated humanName,
     * or with status {@code 400 (Bad Request)} if the humanName is not valid,
     * or with status {@code 404 (Not Found)} if the humanName is not found,
     * or with status {@code 500 (Internal Server Error)} if the humanName couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/human-names/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HumanName> partialUpdateHumanName(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HumanName humanName
    ) throws URISyntaxException {
        log.debug("REST request to partial update HumanName partially : {}, {}", id, humanName);
        if (humanName.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, humanName.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!humanNameRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HumanName> result = humanNameRepository
            .findById(humanName.getId())
            .map(
                existingHumanName -> {
                    if (humanName.getFamily() != null) {
                        existingHumanName.setFamily(humanName.getFamily());
                    }

                    return existingHumanName;
                }
            )
            .map(humanNameRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, humanName.getId().toString())
        );
    }

    /**
     * {@code GET  /human-names} : get all the humanNames.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of humanNames in body.
     */
    @GetMapping("/human-names")
    public List<HumanName> getAllHumanNames() {
        log.debug("REST request to get all HumanNames");
        return humanNameRepository.findAll();
    }

    /**
     * {@code GET  /human-names/:id} : get the "id" humanName.
     *
     * @param id the id of the humanName to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the humanName, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/human-names/{id}")
    public ResponseEntity<HumanName> getHumanName(@PathVariable Long id) {
        log.debug("REST request to get HumanName : {}", id);
        Optional<HumanName> humanName = humanNameRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(humanName);
    }

    /**
     * {@code DELETE  /human-names/:id} : delete the "id" humanName.
     *
     * @param id the id of the humanName to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/human-names/{id}")
    public ResponseEntity<Void> deleteHumanName(@PathVariable Long id) {
        log.debug("REST request to delete HumanName : {}", id);
        humanNameRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
