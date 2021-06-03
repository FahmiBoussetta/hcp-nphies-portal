package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ListSpecialtyEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ListSpecialtyEnumRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ListSpecialtyEnum}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ListSpecialtyEnumResource {

    private final Logger log = LoggerFactory.getLogger(ListSpecialtyEnumResource.class);

    private static final String ENTITY_NAME = "listSpecialtyEnum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListSpecialtyEnumRepository listSpecialtyEnumRepository;

    public ListSpecialtyEnumResource(ListSpecialtyEnumRepository listSpecialtyEnumRepository) {
        this.listSpecialtyEnumRepository = listSpecialtyEnumRepository;
    }

    /**
     * {@code POST  /list-specialty-enums} : Create a new listSpecialtyEnum.
     *
     * @param listSpecialtyEnum the listSpecialtyEnum to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listSpecialtyEnum, or with status {@code 400 (Bad Request)} if the listSpecialtyEnum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-specialty-enums")
    public ResponseEntity<ListSpecialtyEnum> createListSpecialtyEnum(@RequestBody ListSpecialtyEnum listSpecialtyEnum)
        throws URISyntaxException {
        log.debug("REST request to save ListSpecialtyEnum : {}", listSpecialtyEnum);
        if (listSpecialtyEnum.getId() != null) {
            throw new BadRequestAlertException("A new listSpecialtyEnum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListSpecialtyEnum result = listSpecialtyEnumRepository.save(listSpecialtyEnum);
        return ResponseEntity
            .created(new URI("/api/list-specialty-enums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-specialty-enums/:id} : Updates an existing listSpecialtyEnum.
     *
     * @param id the id of the listSpecialtyEnum to save.
     * @param listSpecialtyEnum the listSpecialtyEnum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listSpecialtyEnum,
     * or with status {@code 400 (Bad Request)} if the listSpecialtyEnum is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listSpecialtyEnum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-specialty-enums/{id}")
    public ResponseEntity<ListSpecialtyEnum> updateListSpecialtyEnum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListSpecialtyEnum listSpecialtyEnum
    ) throws URISyntaxException {
        log.debug("REST request to update ListSpecialtyEnum : {}, {}", id, listSpecialtyEnum);
        if (listSpecialtyEnum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listSpecialtyEnum.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listSpecialtyEnumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ListSpecialtyEnum result = listSpecialtyEnumRepository.save(listSpecialtyEnum);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listSpecialtyEnum.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /list-specialty-enums/:id} : Partial updates given fields of an existing listSpecialtyEnum, field will ignore if it is null
     *
     * @param id the id of the listSpecialtyEnum to save.
     * @param listSpecialtyEnum the listSpecialtyEnum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listSpecialtyEnum,
     * or with status {@code 400 (Bad Request)} if the listSpecialtyEnum is not valid,
     * or with status {@code 404 (Not Found)} if the listSpecialtyEnum is not found,
     * or with status {@code 500 (Internal Server Error)} if the listSpecialtyEnum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/list-specialty-enums/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ListSpecialtyEnum> partialUpdateListSpecialtyEnum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListSpecialtyEnum listSpecialtyEnum
    ) throws URISyntaxException {
        log.debug("REST request to partial update ListSpecialtyEnum partially : {}, {}", id, listSpecialtyEnum);
        if (listSpecialtyEnum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listSpecialtyEnum.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listSpecialtyEnumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ListSpecialtyEnum> result = listSpecialtyEnumRepository
            .findById(listSpecialtyEnum.getId())
            .map(
                existingListSpecialtyEnum -> {
                    if (listSpecialtyEnum.getS() != null) {
                        existingListSpecialtyEnum.setS(listSpecialtyEnum.getS());
                    }

                    return existingListSpecialtyEnum;
                }
            )
            .map(listSpecialtyEnumRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listSpecialtyEnum.getId().toString())
        );
    }

    /**
     * {@code GET  /list-specialty-enums} : get all the listSpecialtyEnums.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listSpecialtyEnums in body.
     */
    @GetMapping("/list-specialty-enums")
    public List<ListSpecialtyEnum> getAllListSpecialtyEnums() {
        log.debug("REST request to get all ListSpecialtyEnums");
        return listSpecialtyEnumRepository.findAll();
    }

    /**
     * {@code GET  /list-specialty-enums/:id} : get the "id" listSpecialtyEnum.
     *
     * @param id the id of the listSpecialtyEnum to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listSpecialtyEnum, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-specialty-enums/{id}")
    public ResponseEntity<ListSpecialtyEnum> getListSpecialtyEnum(@PathVariable Long id) {
        log.debug("REST request to get ListSpecialtyEnum : {}", id);
        Optional<ListSpecialtyEnum> listSpecialtyEnum = listSpecialtyEnumRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listSpecialtyEnum);
    }

    /**
     * {@code DELETE  /list-specialty-enums/:id} : delete the "id" listSpecialtyEnum.
     *
     * @param id the id of the listSpecialtyEnum to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-specialty-enums/{id}")
    public ResponseEntity<Void> deleteListSpecialtyEnum(@PathVariable Long id) {
        log.debug("REST request to delete ListSpecialtyEnum : {}", id);
        listSpecialtyEnumRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
