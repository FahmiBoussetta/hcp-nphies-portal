package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ListCommunicationMediumEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ListCommunicationMediumEnumRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ListCommunicationMediumEnum}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ListCommunicationMediumEnumResource {

    private final Logger log = LoggerFactory.getLogger(ListCommunicationMediumEnumResource.class);

    private static final String ENTITY_NAME = "listCommunicationMediumEnum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListCommunicationMediumEnumRepository listCommunicationMediumEnumRepository;

    public ListCommunicationMediumEnumResource(ListCommunicationMediumEnumRepository listCommunicationMediumEnumRepository) {
        this.listCommunicationMediumEnumRepository = listCommunicationMediumEnumRepository;
    }

    /**
     * {@code POST  /list-communication-medium-enums} : Create a new listCommunicationMediumEnum.
     *
     * @param listCommunicationMediumEnum the listCommunicationMediumEnum to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listCommunicationMediumEnum, or with status {@code 400 (Bad Request)} if the listCommunicationMediumEnum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-communication-medium-enums")
    public ResponseEntity<ListCommunicationMediumEnum> createListCommunicationMediumEnum(
        @RequestBody ListCommunicationMediumEnum listCommunicationMediumEnum
    ) throws URISyntaxException {
        log.debug("REST request to save ListCommunicationMediumEnum : {}", listCommunicationMediumEnum);
        if (listCommunicationMediumEnum.getId() != null) {
            throw new BadRequestAlertException("A new listCommunicationMediumEnum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListCommunicationMediumEnum result = listCommunicationMediumEnumRepository.save(listCommunicationMediumEnum);
        return ResponseEntity
            .created(new URI("/api/list-communication-medium-enums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-communication-medium-enums/:id} : Updates an existing listCommunicationMediumEnum.
     *
     * @param id the id of the listCommunicationMediumEnum to save.
     * @param listCommunicationMediumEnum the listCommunicationMediumEnum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listCommunicationMediumEnum,
     * or with status {@code 400 (Bad Request)} if the listCommunicationMediumEnum is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listCommunicationMediumEnum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-communication-medium-enums/{id}")
    public ResponseEntity<ListCommunicationMediumEnum> updateListCommunicationMediumEnum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListCommunicationMediumEnum listCommunicationMediumEnum
    ) throws URISyntaxException {
        log.debug("REST request to update ListCommunicationMediumEnum : {}, {}", id, listCommunicationMediumEnum);
        if (listCommunicationMediumEnum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listCommunicationMediumEnum.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listCommunicationMediumEnumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ListCommunicationMediumEnum result = listCommunicationMediumEnumRepository.save(listCommunicationMediumEnum);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listCommunicationMediumEnum.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /list-communication-medium-enums/:id} : Partial updates given fields of an existing listCommunicationMediumEnum, field will ignore if it is null
     *
     * @param id the id of the listCommunicationMediumEnum to save.
     * @param listCommunicationMediumEnum the listCommunicationMediumEnum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listCommunicationMediumEnum,
     * or with status {@code 400 (Bad Request)} if the listCommunicationMediumEnum is not valid,
     * or with status {@code 404 (Not Found)} if the listCommunicationMediumEnum is not found,
     * or with status {@code 500 (Internal Server Error)} if the listCommunicationMediumEnum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/list-communication-medium-enums/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ListCommunicationMediumEnum> partialUpdateListCommunicationMediumEnum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListCommunicationMediumEnum listCommunicationMediumEnum
    ) throws URISyntaxException {
        log.debug("REST request to partial update ListCommunicationMediumEnum partially : {}, {}", id, listCommunicationMediumEnum);
        if (listCommunicationMediumEnum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listCommunicationMediumEnum.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listCommunicationMediumEnumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ListCommunicationMediumEnum> result = listCommunicationMediumEnumRepository
            .findById(listCommunicationMediumEnum.getId())
            .map(
                existingListCommunicationMediumEnum -> {
                    if (listCommunicationMediumEnum.getCm() != null) {
                        existingListCommunicationMediumEnum.setCm(listCommunicationMediumEnum.getCm());
                    }

                    return existingListCommunicationMediumEnum;
                }
            )
            .map(listCommunicationMediumEnumRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listCommunicationMediumEnum.getId().toString())
        );
    }

    /**
     * {@code GET  /list-communication-medium-enums} : get all the listCommunicationMediumEnums.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listCommunicationMediumEnums in body.
     */
    @GetMapping("/list-communication-medium-enums")
    public List<ListCommunicationMediumEnum> getAllListCommunicationMediumEnums() {
        log.debug("REST request to get all ListCommunicationMediumEnums");
        return listCommunicationMediumEnumRepository.findAll();
    }

    /**
     * {@code GET  /list-communication-medium-enums/:id} : get the "id" listCommunicationMediumEnum.
     *
     * @param id the id of the listCommunicationMediumEnum to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listCommunicationMediumEnum, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-communication-medium-enums/{id}")
    public ResponseEntity<ListCommunicationMediumEnum> getListCommunicationMediumEnum(@PathVariable Long id) {
        log.debug("REST request to get ListCommunicationMediumEnum : {}", id);
        Optional<ListCommunicationMediumEnum> listCommunicationMediumEnum = listCommunicationMediumEnumRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listCommunicationMediumEnum);
    }

    /**
     * {@code DELETE  /list-communication-medium-enums/:id} : delete the "id" listCommunicationMediumEnum.
     *
     * @param id the id of the listCommunicationMediumEnum to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-communication-medium-enums/{id}")
    public ResponseEntity<Void> deleteListCommunicationMediumEnum(@PathVariable Long id) {
        log.debug("REST request to delete ListCommunicationMediumEnum : {}", id);
        listCommunicationMediumEnumRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
