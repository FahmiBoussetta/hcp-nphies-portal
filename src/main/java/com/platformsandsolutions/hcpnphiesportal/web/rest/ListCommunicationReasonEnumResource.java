package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ListCommunicationReasonEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ListCommunicationReasonEnumRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ListCommunicationReasonEnum}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ListCommunicationReasonEnumResource {

    private final Logger log = LoggerFactory.getLogger(ListCommunicationReasonEnumResource.class);

    private static final String ENTITY_NAME = "listCommunicationReasonEnum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListCommunicationReasonEnumRepository listCommunicationReasonEnumRepository;

    public ListCommunicationReasonEnumResource(ListCommunicationReasonEnumRepository listCommunicationReasonEnumRepository) {
        this.listCommunicationReasonEnumRepository = listCommunicationReasonEnumRepository;
    }

    /**
     * {@code POST  /list-communication-reason-enums} : Create a new listCommunicationReasonEnum.
     *
     * @param listCommunicationReasonEnum the listCommunicationReasonEnum to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listCommunicationReasonEnum, or with status {@code 400 (Bad Request)} if the listCommunicationReasonEnum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-communication-reason-enums")
    public ResponseEntity<ListCommunicationReasonEnum> createListCommunicationReasonEnum(
        @RequestBody ListCommunicationReasonEnum listCommunicationReasonEnum
    ) throws URISyntaxException {
        log.debug("REST request to save ListCommunicationReasonEnum : {}", listCommunicationReasonEnum);
        if (listCommunicationReasonEnum.getId() != null) {
            throw new BadRequestAlertException("A new listCommunicationReasonEnum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListCommunicationReasonEnum result = listCommunicationReasonEnumRepository.save(listCommunicationReasonEnum);
        return ResponseEntity
            .created(new URI("/api/list-communication-reason-enums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-communication-reason-enums/:id} : Updates an existing listCommunicationReasonEnum.
     *
     * @param id the id of the listCommunicationReasonEnum to save.
     * @param listCommunicationReasonEnum the listCommunicationReasonEnum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listCommunicationReasonEnum,
     * or with status {@code 400 (Bad Request)} if the listCommunicationReasonEnum is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listCommunicationReasonEnum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-communication-reason-enums/{id}")
    public ResponseEntity<ListCommunicationReasonEnum> updateListCommunicationReasonEnum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListCommunicationReasonEnum listCommunicationReasonEnum
    ) throws URISyntaxException {
        log.debug("REST request to update ListCommunicationReasonEnum : {}, {}", id, listCommunicationReasonEnum);
        if (listCommunicationReasonEnum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listCommunicationReasonEnum.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listCommunicationReasonEnumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ListCommunicationReasonEnum result = listCommunicationReasonEnumRepository.save(listCommunicationReasonEnum);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listCommunicationReasonEnum.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /list-communication-reason-enums/:id} : Partial updates given fields of an existing listCommunicationReasonEnum, field will ignore if it is null
     *
     * @param id the id of the listCommunicationReasonEnum to save.
     * @param listCommunicationReasonEnum the listCommunicationReasonEnum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listCommunicationReasonEnum,
     * or with status {@code 400 (Bad Request)} if the listCommunicationReasonEnum is not valid,
     * or with status {@code 404 (Not Found)} if the listCommunicationReasonEnum is not found,
     * or with status {@code 500 (Internal Server Error)} if the listCommunicationReasonEnum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/list-communication-reason-enums/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ListCommunicationReasonEnum> partialUpdateListCommunicationReasonEnum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListCommunicationReasonEnum listCommunicationReasonEnum
    ) throws URISyntaxException {
        log.debug("REST request to partial update ListCommunicationReasonEnum partially : {}, {}", id, listCommunicationReasonEnum);
        if (listCommunicationReasonEnum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listCommunicationReasonEnum.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listCommunicationReasonEnumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ListCommunicationReasonEnum> result = listCommunicationReasonEnumRepository
            .findById(listCommunicationReasonEnum.getId())
            .map(
                existingListCommunicationReasonEnum -> {
                    if (listCommunicationReasonEnum.getCr() != null) {
                        existingListCommunicationReasonEnum.setCr(listCommunicationReasonEnum.getCr());
                    }

                    return existingListCommunicationReasonEnum;
                }
            )
            .map(listCommunicationReasonEnumRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listCommunicationReasonEnum.getId().toString())
        );
    }

    /**
     * {@code GET  /list-communication-reason-enums} : get all the listCommunicationReasonEnums.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listCommunicationReasonEnums in body.
     */
    @GetMapping("/list-communication-reason-enums")
    public List<ListCommunicationReasonEnum> getAllListCommunicationReasonEnums() {
        log.debug("REST request to get all ListCommunicationReasonEnums");
        return listCommunicationReasonEnumRepository.findAll();
    }

    /**
     * {@code GET  /list-communication-reason-enums/:id} : get the "id" listCommunicationReasonEnum.
     *
     * @param id the id of the listCommunicationReasonEnum to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listCommunicationReasonEnum, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-communication-reason-enums/{id}")
    public ResponseEntity<ListCommunicationReasonEnum> getListCommunicationReasonEnum(@PathVariable Long id) {
        log.debug("REST request to get ListCommunicationReasonEnum : {}", id);
        Optional<ListCommunicationReasonEnum> listCommunicationReasonEnum = listCommunicationReasonEnumRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listCommunicationReasonEnum);
    }

    /**
     * {@code DELETE  /list-communication-reason-enums/:id} : delete the "id" listCommunicationReasonEnum.
     *
     * @param id the id of the listCommunicationReasonEnum to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-communication-reason-enums/{id}")
    public ResponseEntity<Void> deleteListCommunicationReasonEnum(@PathVariable Long id) {
        log.debug("REST request to delete ListCommunicationReasonEnum : {}", id);
        listCommunicationReasonEnumRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
