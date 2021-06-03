package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ListEligibilityPurposeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ListEligibilityPurposeEnumRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ListEligibilityPurposeEnum}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ListEligibilityPurposeEnumResource {

    private final Logger log = LoggerFactory.getLogger(ListEligibilityPurposeEnumResource.class);

    private static final String ENTITY_NAME = "listEligibilityPurposeEnum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListEligibilityPurposeEnumRepository listEligibilityPurposeEnumRepository;

    public ListEligibilityPurposeEnumResource(ListEligibilityPurposeEnumRepository listEligibilityPurposeEnumRepository) {
        this.listEligibilityPurposeEnumRepository = listEligibilityPurposeEnumRepository;
    }

    /**
     * {@code POST  /list-eligibility-purpose-enums} : Create a new listEligibilityPurposeEnum.
     *
     * @param listEligibilityPurposeEnum the listEligibilityPurposeEnum to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listEligibilityPurposeEnum, or with status {@code 400 (Bad Request)} if the listEligibilityPurposeEnum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-eligibility-purpose-enums")
    public ResponseEntity<ListEligibilityPurposeEnum> createListEligibilityPurposeEnum(
        @RequestBody ListEligibilityPurposeEnum listEligibilityPurposeEnum
    ) throws URISyntaxException {
        log.debug("REST request to save ListEligibilityPurposeEnum : {}", listEligibilityPurposeEnum);
        if (listEligibilityPurposeEnum.getId() != null) {
            throw new BadRequestAlertException("A new listEligibilityPurposeEnum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListEligibilityPurposeEnum result = listEligibilityPurposeEnumRepository.save(listEligibilityPurposeEnum);
        return ResponseEntity
            .created(new URI("/api/list-eligibility-purpose-enums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-eligibility-purpose-enums/:id} : Updates an existing listEligibilityPurposeEnum.
     *
     * @param id the id of the listEligibilityPurposeEnum to save.
     * @param listEligibilityPurposeEnum the listEligibilityPurposeEnum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listEligibilityPurposeEnum,
     * or with status {@code 400 (Bad Request)} if the listEligibilityPurposeEnum is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listEligibilityPurposeEnum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-eligibility-purpose-enums/{id}")
    public ResponseEntity<ListEligibilityPurposeEnum> updateListEligibilityPurposeEnum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListEligibilityPurposeEnum listEligibilityPurposeEnum
    ) throws URISyntaxException {
        log.debug("REST request to update ListEligibilityPurposeEnum : {}, {}", id, listEligibilityPurposeEnum);
        if (listEligibilityPurposeEnum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listEligibilityPurposeEnum.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listEligibilityPurposeEnumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ListEligibilityPurposeEnum result = listEligibilityPurposeEnumRepository.save(listEligibilityPurposeEnum);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listEligibilityPurposeEnum.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /list-eligibility-purpose-enums/:id} : Partial updates given fields of an existing listEligibilityPurposeEnum, field will ignore if it is null
     *
     * @param id the id of the listEligibilityPurposeEnum to save.
     * @param listEligibilityPurposeEnum the listEligibilityPurposeEnum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listEligibilityPurposeEnum,
     * or with status {@code 400 (Bad Request)} if the listEligibilityPurposeEnum is not valid,
     * or with status {@code 404 (Not Found)} if the listEligibilityPurposeEnum is not found,
     * or with status {@code 500 (Internal Server Error)} if the listEligibilityPurposeEnum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/list-eligibility-purpose-enums/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ListEligibilityPurposeEnum> partialUpdateListEligibilityPurposeEnum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListEligibilityPurposeEnum listEligibilityPurposeEnum
    ) throws URISyntaxException {
        log.debug("REST request to partial update ListEligibilityPurposeEnum partially : {}, {}", id, listEligibilityPurposeEnum);
        if (listEligibilityPurposeEnum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listEligibilityPurposeEnum.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listEligibilityPurposeEnumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ListEligibilityPurposeEnum> result = listEligibilityPurposeEnumRepository
            .findById(listEligibilityPurposeEnum.getId())
            .map(
                existingListEligibilityPurposeEnum -> {
                    if (listEligibilityPurposeEnum.getErp() != null) {
                        existingListEligibilityPurposeEnum.setErp(listEligibilityPurposeEnum.getErp());
                    }

                    return existingListEligibilityPurposeEnum;
                }
            )
            .map(listEligibilityPurposeEnumRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listEligibilityPurposeEnum.getId().toString())
        );
    }

    /**
     * {@code GET  /list-eligibility-purpose-enums} : get all the listEligibilityPurposeEnums.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listEligibilityPurposeEnums in body.
     */
    @GetMapping("/list-eligibility-purpose-enums")
    public List<ListEligibilityPurposeEnum> getAllListEligibilityPurposeEnums() {
        log.debug("REST request to get all ListEligibilityPurposeEnums");
        return listEligibilityPurposeEnumRepository.findAll();
    }

    /**
     * {@code GET  /list-eligibility-purpose-enums/:id} : get the "id" listEligibilityPurposeEnum.
     *
     * @param id the id of the listEligibilityPurposeEnum to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listEligibilityPurposeEnum, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-eligibility-purpose-enums/{id}")
    public ResponseEntity<ListEligibilityPurposeEnum> getListEligibilityPurposeEnum(@PathVariable Long id) {
        log.debug("REST request to get ListEligibilityPurposeEnum : {}", id);
        Optional<ListEligibilityPurposeEnum> listEligibilityPurposeEnum = listEligibilityPurposeEnumRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listEligibilityPurposeEnum);
    }

    /**
     * {@code DELETE  /list-eligibility-purpose-enums/:id} : delete the "id" listEligibilityPurposeEnum.
     *
     * @param id the id of the listEligibilityPurposeEnum to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-eligibility-purpose-enums/{id}")
    public ResponseEntity<Void> deleteListEligibilityPurposeEnum(@PathVariable Long id) {
        log.debug("REST request to delete ListEligibilityPurposeEnum : {}", id);
        listEligibilityPurposeEnumRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
