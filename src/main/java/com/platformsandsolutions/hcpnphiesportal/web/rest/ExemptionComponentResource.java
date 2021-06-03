package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ExemptionComponent;
import com.platformsandsolutions.hcpnphiesportal.repository.ExemptionComponentRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ExemptionComponent}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ExemptionComponentResource {

    private final Logger log = LoggerFactory.getLogger(ExemptionComponentResource.class);

    private static final String ENTITY_NAME = "exemptionComponent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExemptionComponentRepository exemptionComponentRepository;

    public ExemptionComponentResource(ExemptionComponentRepository exemptionComponentRepository) {
        this.exemptionComponentRepository = exemptionComponentRepository;
    }

    /**
     * {@code POST  /exemption-components} : Create a new exemptionComponent.
     *
     * @param exemptionComponent the exemptionComponent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exemptionComponent, or with status {@code 400 (Bad Request)} if the exemptionComponent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exemption-components")
    public ResponseEntity<ExemptionComponent> createExemptionComponent(@Valid @RequestBody ExemptionComponent exemptionComponent)
        throws URISyntaxException {
        log.debug("REST request to save ExemptionComponent : {}", exemptionComponent);
        if (exemptionComponent.getId() != null) {
            throw new BadRequestAlertException("A new exemptionComponent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExemptionComponent result = exemptionComponentRepository.save(exemptionComponent);
        return ResponseEntity
            .created(new URI("/api/exemption-components/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exemption-components/:id} : Updates an existing exemptionComponent.
     *
     * @param id the id of the exemptionComponent to save.
     * @param exemptionComponent the exemptionComponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exemptionComponent,
     * or with status {@code 400 (Bad Request)} if the exemptionComponent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exemptionComponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exemption-components/{id}")
    public ResponseEntity<ExemptionComponent> updateExemptionComponent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ExemptionComponent exemptionComponent
    ) throws URISyntaxException {
        log.debug("REST request to update ExemptionComponent : {}, {}", id, exemptionComponent);
        if (exemptionComponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, exemptionComponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!exemptionComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExemptionComponent result = exemptionComponentRepository.save(exemptionComponent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exemptionComponent.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /exemption-components/:id} : Partial updates given fields of an existing exemptionComponent, field will ignore if it is null
     *
     * @param id the id of the exemptionComponent to save.
     * @param exemptionComponent the exemptionComponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exemptionComponent,
     * or with status {@code 400 (Bad Request)} if the exemptionComponent is not valid,
     * or with status {@code 404 (Not Found)} if the exemptionComponent is not found,
     * or with status {@code 500 (Internal Server Error)} if the exemptionComponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/exemption-components/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ExemptionComponent> partialUpdateExemptionComponent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ExemptionComponent exemptionComponent
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExemptionComponent partially : {}, {}", id, exemptionComponent);
        if (exemptionComponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, exemptionComponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!exemptionComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExemptionComponent> result = exemptionComponentRepository
            .findById(exemptionComponent.getId())
            .map(
                existingExemptionComponent -> {
                    if (exemptionComponent.getType() != null) {
                        existingExemptionComponent.setType(exemptionComponent.getType());
                    }
                    if (exemptionComponent.getStart() != null) {
                        existingExemptionComponent.setStart(exemptionComponent.getStart());
                    }
                    if (exemptionComponent.getEnd() != null) {
                        existingExemptionComponent.setEnd(exemptionComponent.getEnd());
                    }

                    return existingExemptionComponent;
                }
            )
            .map(exemptionComponentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exemptionComponent.getId().toString())
        );
    }

    /**
     * {@code GET  /exemption-components} : get all the exemptionComponents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exemptionComponents in body.
     */
    @GetMapping("/exemption-components")
    public List<ExemptionComponent> getAllExemptionComponents() {
        log.debug("REST request to get all ExemptionComponents");
        return exemptionComponentRepository.findAll();
    }

    /**
     * {@code GET  /exemption-components/:id} : get the "id" exemptionComponent.
     *
     * @param id the id of the exemptionComponent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exemptionComponent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exemption-components/{id}")
    public ResponseEntity<ExemptionComponent> getExemptionComponent(@PathVariable Long id) {
        log.debug("REST request to get ExemptionComponent : {}", id);
        Optional<ExemptionComponent> exemptionComponent = exemptionComponentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(exemptionComponent);
    }

    /**
     * {@code DELETE  /exemption-components/:id} : delete the "id" exemptionComponent.
     *
     * @param id the id of the exemptionComponent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exemption-components/{id}")
    public ResponseEntity<Void> deleteExemptionComponent(@PathVariable Long id) {
        log.debug("REST request to delete ExemptionComponent : {}", id);
        exemptionComponentRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
