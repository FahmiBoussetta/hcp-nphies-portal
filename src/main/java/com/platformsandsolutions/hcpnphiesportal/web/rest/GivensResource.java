package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Givens;
import com.platformsandsolutions.hcpnphiesportal.repository.GivensRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Givens}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GivensResource {

    private final Logger log = LoggerFactory.getLogger(GivensResource.class);

    private static final String ENTITY_NAME = "givens";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GivensRepository givensRepository;

    public GivensResource(GivensRepository givensRepository) {
        this.givensRepository = givensRepository;
    }

    /**
     * {@code POST  /givens} : Create a new givens.
     *
     * @param givens the givens to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new givens, or with status {@code 400 (Bad Request)} if the givens has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/givens")
    public ResponseEntity<Givens> createGivens(@Valid @RequestBody Givens givens) throws URISyntaxException {
        log.debug("REST request to save Givens : {}", givens);
        if (givens.getId() != null) {
            throw new BadRequestAlertException("A new givens cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Givens result = givensRepository.save(givens);
        return ResponseEntity
            .created(new URI("/api/givens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /givens/:id} : Updates an existing givens.
     *
     * @param id the id of the givens to save.
     * @param givens the givens to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated givens,
     * or with status {@code 400 (Bad Request)} if the givens is not valid,
     * or with status {@code 500 (Internal Server Error)} if the givens couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/givens/{id}")
    public ResponseEntity<Givens> updateGivens(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Givens givens
    ) throws URISyntaxException {
        log.debug("REST request to update Givens : {}, {}", id, givens);
        if (givens.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, givens.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!givensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Givens result = givensRepository.save(givens);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, givens.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /givens/:id} : Partial updates given fields of an existing givens, field will ignore if it is null
     *
     * @param id the id of the givens to save.
     * @param givens the givens to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated givens,
     * or with status {@code 400 (Bad Request)} if the givens is not valid,
     * or with status {@code 404 (Not Found)} if the givens is not found,
     * or with status {@code 500 (Internal Server Error)} if the givens couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/givens/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Givens> partialUpdateGivens(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Givens givens
    ) throws URISyntaxException {
        log.debug("REST request to partial update Givens partially : {}, {}", id, givens);
        if (givens.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, givens.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!givensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Givens> result = givensRepository
            .findById(givens.getId())
            .map(
                existingGivens -> {
                    if (givens.getGiven() != null) {
                        existingGivens.setGiven(givens.getGiven());
                    }
                    if (givens.getPrefix() != null) {
                        existingGivens.setPrefix(givens.getPrefix());
                    }
                    if (givens.getSuffix() != null) {
                        existingGivens.setSuffix(givens.getSuffix());
                    }
                    if (givens.getTextName() != null) {
                        existingGivens.setTextName(givens.getTextName());
                    }

                    return existingGivens;
                }
            )
            .map(givensRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, givens.getId().toString())
        );
    }

    /**
     * {@code GET  /givens} : get all the givens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of givens in body.
     */
    @GetMapping("/givens")
    public List<Givens> getAllGivens() {
        log.debug("REST request to get all Givens");
        return givensRepository.findAll();
    }

    /**
     * {@code GET  /givens/:id} : get the "id" givens.
     *
     * @param id the id of the givens to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the givens, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/givens/{id}")
    public ResponseEntity<Givens> getGivens(@PathVariable Long id) {
        log.debug("REST request to get Givens : {}", id);
        Optional<Givens> givens = givensRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(givens);
    }

    /**
     * {@code DELETE  /givens/:id} : delete the "id" givens.
     *
     * @param id the id of the givens to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/givens/{id}")
    public ResponseEntity<Void> deleteGivens(@PathVariable Long id) {
        log.debug("REST request to delete Givens : {}", id);
        givensRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
