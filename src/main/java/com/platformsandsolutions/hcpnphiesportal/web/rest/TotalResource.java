package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Total;
import com.platformsandsolutions.hcpnphiesportal.repository.TotalRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Total}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TotalResource {

    private final Logger log = LoggerFactory.getLogger(TotalResource.class);

    private static final String ENTITY_NAME = "total";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TotalRepository totalRepository;

    public TotalResource(TotalRepository totalRepository) {
        this.totalRepository = totalRepository;
    }

    /**
     * {@code POST  /totals} : Create a new total.
     *
     * @param total the total to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new total, or with status {@code 400 (Bad Request)} if the total has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/totals")
    public ResponseEntity<Total> createTotal(@RequestBody Total total) throws URISyntaxException {
        log.debug("REST request to save Total : {}", total);
        if (total.getId() != null) {
            throw new BadRequestAlertException("A new total cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Total result = totalRepository.save(total);
        return ResponseEntity
            .created(new URI("/api/totals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /totals/:id} : Updates an existing total.
     *
     * @param id the id of the total to save.
     * @param total the total to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated total,
     * or with status {@code 400 (Bad Request)} if the total is not valid,
     * or with status {@code 500 (Internal Server Error)} if the total couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/totals/{id}")
    public ResponseEntity<Total> updateTotal(@PathVariable(value = "id", required = false) final Long id, @RequestBody Total total)
        throws URISyntaxException {
        log.debug("REST request to update Total : {}, {}", id, total);
        if (total.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, total.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!totalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Total result = totalRepository.save(total);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, total.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /totals/:id} : Partial updates given fields of an existing total, field will ignore if it is null
     *
     * @param id the id of the total to save.
     * @param total the total to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated total,
     * or with status {@code 400 (Bad Request)} if the total is not valid,
     * or with status {@code 404 (Not Found)} if the total is not found,
     * or with status {@code 500 (Internal Server Error)} if the total couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/totals/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Total> partialUpdateTotal(@PathVariable(value = "id", required = false) final Long id, @RequestBody Total total)
        throws URISyntaxException {
        log.debug("REST request to partial update Total partially : {}, {}", id, total);
        if (total.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, total.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!totalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Total> result = totalRepository
            .findById(total.getId())
            .map(
                existingTotal -> {
                    if (total.getCategory() != null) {
                        existingTotal.setCategory(total.getCategory());
                    }
                    if (total.getAmount() != null) {
                        existingTotal.setAmount(total.getAmount());
                    }

                    return existingTotal;
                }
            )
            .map(totalRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, total.getId().toString())
        );
    }

    /**
     * {@code GET  /totals} : get all the totals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of totals in body.
     */
    @GetMapping("/totals")
    public List<Total> getAllTotals() {
        log.debug("REST request to get all Totals");
        return totalRepository.findAll();
    }

    /**
     * {@code GET  /totals/:id} : get the "id" total.
     *
     * @param id the id of the total to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the total, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/totals/{id}")
    public ResponseEntity<Total> getTotal(@PathVariable Long id) {
        log.debug("REST request to get Total : {}", id);
        Optional<Total> total = totalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(total);
    }

    /**
     * {@code DELETE  /totals/:id} : delete the "id" total.
     *
     * @param id the id of the total to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/totals/{id}")
    public ResponseEntity<Void> deleteTotal(@PathVariable Long id) {
        log.debug("REST request to delete Total : {}", id);
        totalRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
