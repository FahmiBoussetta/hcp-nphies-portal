package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.CostToBeneficiaryComponent;
import com.platformsandsolutions.hcpnphiesportal.repository.CostToBeneficiaryComponentRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.CostToBeneficiaryComponent}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CostToBeneficiaryComponentResource {

    private final Logger log = LoggerFactory.getLogger(CostToBeneficiaryComponentResource.class);

    private static final String ENTITY_NAME = "costToBeneficiaryComponent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CostToBeneficiaryComponentRepository costToBeneficiaryComponentRepository;

    public CostToBeneficiaryComponentResource(CostToBeneficiaryComponentRepository costToBeneficiaryComponentRepository) {
        this.costToBeneficiaryComponentRepository = costToBeneficiaryComponentRepository;
    }

    /**
     * {@code POST  /cost-to-beneficiary-components} : Create a new costToBeneficiaryComponent.
     *
     * @param costToBeneficiaryComponent the costToBeneficiaryComponent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new costToBeneficiaryComponent, or with status {@code 400 (Bad Request)} if the costToBeneficiaryComponent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cost-to-beneficiary-components")
    public ResponseEntity<CostToBeneficiaryComponent> createCostToBeneficiaryComponent(
        @Valid @RequestBody CostToBeneficiaryComponent costToBeneficiaryComponent
    ) throws URISyntaxException {
        log.debug("REST request to save CostToBeneficiaryComponent : {}", costToBeneficiaryComponent);
        if (costToBeneficiaryComponent.getId() != null) {
            throw new BadRequestAlertException("A new costToBeneficiaryComponent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CostToBeneficiaryComponent result = costToBeneficiaryComponentRepository.save(costToBeneficiaryComponent);
        return ResponseEntity
            .created(new URI("/api/cost-to-beneficiary-components/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cost-to-beneficiary-components/:id} : Updates an existing costToBeneficiaryComponent.
     *
     * @param id the id of the costToBeneficiaryComponent to save.
     * @param costToBeneficiaryComponent the costToBeneficiaryComponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated costToBeneficiaryComponent,
     * or with status {@code 400 (Bad Request)} if the costToBeneficiaryComponent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the costToBeneficiaryComponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cost-to-beneficiary-components/{id}")
    public ResponseEntity<CostToBeneficiaryComponent> updateCostToBeneficiaryComponent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CostToBeneficiaryComponent costToBeneficiaryComponent
    ) throws URISyntaxException {
        log.debug("REST request to update CostToBeneficiaryComponent : {}, {}", id, costToBeneficiaryComponent);
        if (costToBeneficiaryComponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, costToBeneficiaryComponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!costToBeneficiaryComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CostToBeneficiaryComponent result = costToBeneficiaryComponentRepository.save(costToBeneficiaryComponent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, costToBeneficiaryComponent.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cost-to-beneficiary-components/:id} : Partial updates given fields of an existing costToBeneficiaryComponent, field will ignore if it is null
     *
     * @param id the id of the costToBeneficiaryComponent to save.
     * @param costToBeneficiaryComponent the costToBeneficiaryComponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated costToBeneficiaryComponent,
     * or with status {@code 400 (Bad Request)} if the costToBeneficiaryComponent is not valid,
     * or with status {@code 404 (Not Found)} if the costToBeneficiaryComponent is not found,
     * or with status {@code 500 (Internal Server Error)} if the costToBeneficiaryComponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cost-to-beneficiary-components/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CostToBeneficiaryComponent> partialUpdateCostToBeneficiaryComponent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CostToBeneficiaryComponent costToBeneficiaryComponent
    ) throws URISyntaxException {
        log.debug("REST request to partial update CostToBeneficiaryComponent partially : {}, {}", id, costToBeneficiaryComponent);
        if (costToBeneficiaryComponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, costToBeneficiaryComponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!costToBeneficiaryComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CostToBeneficiaryComponent> result = costToBeneficiaryComponentRepository
            .findById(costToBeneficiaryComponent.getId())
            .map(
                existingCostToBeneficiaryComponent -> {
                    if (costToBeneficiaryComponent.getType() != null) {
                        existingCostToBeneficiaryComponent.setType(costToBeneficiaryComponent.getType());
                    }
                    if (costToBeneficiaryComponent.getIsMoney() != null) {
                        existingCostToBeneficiaryComponent.setIsMoney(costToBeneficiaryComponent.getIsMoney());
                    }
                    if (costToBeneficiaryComponent.getValue() != null) {
                        existingCostToBeneficiaryComponent.setValue(costToBeneficiaryComponent.getValue());
                    }

                    return existingCostToBeneficiaryComponent;
                }
            )
            .map(costToBeneficiaryComponentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, costToBeneficiaryComponent.getId().toString())
        );
    }

    /**
     * {@code GET  /cost-to-beneficiary-components} : get all the costToBeneficiaryComponents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of costToBeneficiaryComponents in body.
     */
    @GetMapping("/cost-to-beneficiary-components")
    public List<CostToBeneficiaryComponent> getAllCostToBeneficiaryComponents() {
        log.debug("REST request to get all CostToBeneficiaryComponents");
        return costToBeneficiaryComponentRepository.findAll();
    }

    /**
     * {@code GET  /cost-to-beneficiary-components/:id} : get the "id" costToBeneficiaryComponent.
     *
     * @param id the id of the costToBeneficiaryComponent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the costToBeneficiaryComponent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cost-to-beneficiary-components/{id}")
    public ResponseEntity<CostToBeneficiaryComponent> getCostToBeneficiaryComponent(@PathVariable Long id) {
        log.debug("REST request to get CostToBeneficiaryComponent : {}", id);
        Optional<CostToBeneficiaryComponent> costToBeneficiaryComponent = costToBeneficiaryComponentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(costToBeneficiaryComponent);
    }

    /**
     * {@code DELETE  /cost-to-beneficiary-components/:id} : delete the "id" costToBeneficiaryComponent.
     *
     * @param id the id of the costToBeneficiaryComponent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cost-to-beneficiary-components/{id}")
    public ResponseEntity<Void> deleteCostToBeneficiaryComponent(@PathVariable Long id) {
        log.debug("REST request to delete CostToBeneficiaryComponent : {}", id);
        costToBeneficiaryComponentRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
