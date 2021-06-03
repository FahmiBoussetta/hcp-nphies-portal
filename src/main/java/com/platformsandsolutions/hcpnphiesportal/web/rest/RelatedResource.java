package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.Related;
import com.platformsandsolutions.hcpnphiesportal.repository.RelatedRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.Related}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RelatedResource {

    private final Logger log = LoggerFactory.getLogger(RelatedResource.class);

    private static final String ENTITY_NAME = "related";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelatedRepository relatedRepository;

    public RelatedResource(RelatedRepository relatedRepository) {
        this.relatedRepository = relatedRepository;
    }

    /**
     * {@code POST  /relateds} : Create a new related.
     *
     * @param related the related to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new related, or with status {@code 400 (Bad Request)} if the related has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relateds")
    public ResponseEntity<Related> createRelated(@RequestBody Related related) throws URISyntaxException {
        log.debug("REST request to save Related : {}", related);
        if (related.getId() != null) {
            throw new BadRequestAlertException("A new related cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Related result = relatedRepository.save(related);
        return ResponseEntity
            .created(new URI("/api/relateds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relateds/:id} : Updates an existing related.
     *
     * @param id the id of the related to save.
     * @param related the related to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated related,
     * or with status {@code 400 (Bad Request)} if the related is not valid,
     * or with status {@code 500 (Internal Server Error)} if the related couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relateds/{id}")
    public ResponseEntity<Related> updateRelated(@PathVariable(value = "id", required = false) final Long id, @RequestBody Related related)
        throws URISyntaxException {
        log.debug("REST request to update Related : {}, {}", id, related);
        if (related.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, related.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relatedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Related result = relatedRepository.save(related);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, related.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /relateds/:id} : Partial updates given fields of an existing related, field will ignore if it is null
     *
     * @param id the id of the related to save.
     * @param related the related to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated related,
     * or with status {@code 400 (Bad Request)} if the related is not valid,
     * or with status {@code 404 (Not Found)} if the related is not found,
     * or with status {@code 500 (Internal Server Error)} if the related couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/relateds/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Related> partialUpdateRelated(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Related related
    ) throws URISyntaxException {
        log.debug("REST request to partial update Related partially : {}, {}", id, related);
        if (related.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, related.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relatedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Related> result = relatedRepository
            .findById(related.getId())
            .map(
                existingRelated -> {
                    if (related.getRelationShip() != null) {
                        existingRelated.setRelationShip(related.getRelationShip());
                    }

                    return existingRelated;
                }
            )
            .map(relatedRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, related.getId().toString())
        );
    }

    /**
     * {@code GET  /relateds} : get all the relateds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relateds in body.
     */
    @GetMapping("/relateds")
    public List<Related> getAllRelateds() {
        log.debug("REST request to get all Relateds");
        return relatedRepository.findAll();
    }

    /**
     * {@code GET  /relateds/:id} : get the "id" related.
     *
     * @param id the id of the related to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the related, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relateds/{id}")
    public ResponseEntity<Related> getRelated(@PathVariable Long id) {
        log.debug("REST request to get Related : {}", id);
        Optional<Related> related = relatedRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(related);
    }

    /**
     * {@code DELETE  /relateds/:id} : delete the "id" related.
     *
     * @param id the id of the related to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relateds/{id}")
    public ResponseEntity<Void> deleteRelated(@PathVariable Long id) {
        log.debug("REST request to delete Related : {}", id);
        relatedRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
