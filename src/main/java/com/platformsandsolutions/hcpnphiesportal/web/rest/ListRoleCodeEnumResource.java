package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ListRoleCodeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ListRoleCodeEnumRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ListRoleCodeEnum}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ListRoleCodeEnumResource {

    private final Logger log = LoggerFactory.getLogger(ListRoleCodeEnumResource.class);

    private static final String ENTITY_NAME = "listRoleCodeEnum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListRoleCodeEnumRepository listRoleCodeEnumRepository;

    public ListRoleCodeEnumResource(ListRoleCodeEnumRepository listRoleCodeEnumRepository) {
        this.listRoleCodeEnumRepository = listRoleCodeEnumRepository;
    }

    /**
     * {@code POST  /list-role-code-enums} : Create a new listRoleCodeEnum.
     *
     * @param listRoleCodeEnum the listRoleCodeEnum to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listRoleCodeEnum, or with status {@code 400 (Bad Request)} if the listRoleCodeEnum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-role-code-enums")
    public ResponseEntity<ListRoleCodeEnum> createListRoleCodeEnum(@RequestBody ListRoleCodeEnum listRoleCodeEnum)
        throws URISyntaxException {
        log.debug("REST request to save ListRoleCodeEnum : {}", listRoleCodeEnum);
        if (listRoleCodeEnum.getId() != null) {
            throw new BadRequestAlertException("A new listRoleCodeEnum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListRoleCodeEnum result = listRoleCodeEnumRepository.save(listRoleCodeEnum);
        return ResponseEntity
            .created(new URI("/api/list-role-code-enums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-role-code-enums/:id} : Updates an existing listRoleCodeEnum.
     *
     * @param id the id of the listRoleCodeEnum to save.
     * @param listRoleCodeEnum the listRoleCodeEnum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listRoleCodeEnum,
     * or with status {@code 400 (Bad Request)} if the listRoleCodeEnum is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listRoleCodeEnum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-role-code-enums/{id}")
    public ResponseEntity<ListRoleCodeEnum> updateListRoleCodeEnum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListRoleCodeEnum listRoleCodeEnum
    ) throws URISyntaxException {
        log.debug("REST request to update ListRoleCodeEnum : {}, {}", id, listRoleCodeEnum);
        if (listRoleCodeEnum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listRoleCodeEnum.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listRoleCodeEnumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ListRoleCodeEnum result = listRoleCodeEnumRepository.save(listRoleCodeEnum);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listRoleCodeEnum.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /list-role-code-enums/:id} : Partial updates given fields of an existing listRoleCodeEnum, field will ignore if it is null
     *
     * @param id the id of the listRoleCodeEnum to save.
     * @param listRoleCodeEnum the listRoleCodeEnum to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listRoleCodeEnum,
     * or with status {@code 400 (Bad Request)} if the listRoleCodeEnum is not valid,
     * or with status {@code 404 (Not Found)} if the listRoleCodeEnum is not found,
     * or with status {@code 500 (Internal Server Error)} if the listRoleCodeEnum couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/list-role-code-enums/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ListRoleCodeEnum> partialUpdateListRoleCodeEnum(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ListRoleCodeEnum listRoleCodeEnum
    ) throws URISyntaxException {
        log.debug("REST request to partial update ListRoleCodeEnum partially : {}, {}", id, listRoleCodeEnum);
        if (listRoleCodeEnum.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, listRoleCodeEnum.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!listRoleCodeEnumRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ListRoleCodeEnum> result = listRoleCodeEnumRepository
            .findById(listRoleCodeEnum.getId())
            .map(
                existingListRoleCodeEnum -> {
                    if (listRoleCodeEnum.getR() != null) {
                        existingListRoleCodeEnum.setR(listRoleCodeEnum.getR());
                    }

                    return existingListRoleCodeEnum;
                }
            )
            .map(listRoleCodeEnumRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, listRoleCodeEnum.getId().toString())
        );
    }

    /**
     * {@code GET  /list-role-code-enums} : get all the listRoleCodeEnums.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listRoleCodeEnums in body.
     */
    @GetMapping("/list-role-code-enums")
    public List<ListRoleCodeEnum> getAllListRoleCodeEnums() {
        log.debug("REST request to get all ListRoleCodeEnums");
        return listRoleCodeEnumRepository.findAll();
    }

    /**
     * {@code GET  /list-role-code-enums/:id} : get the "id" listRoleCodeEnum.
     *
     * @param id the id of the listRoleCodeEnum to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listRoleCodeEnum, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-role-code-enums/{id}")
    public ResponseEntity<ListRoleCodeEnum> getListRoleCodeEnum(@PathVariable Long id) {
        log.debug("REST request to get ListRoleCodeEnum : {}", id);
        Optional<ListRoleCodeEnum> listRoleCodeEnum = listRoleCodeEnumRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(listRoleCodeEnum);
    }

    /**
     * {@code DELETE  /list-role-code-enums/:id} : delete the "id" listRoleCodeEnum.
     *
     * @param id the id of the listRoleCodeEnum to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-role-code-enums/{id}")
    public ResponseEntity<Void> deleteListRoleCodeEnum(@PathVariable Long id) {
        log.debug("REST request to delete ListRoleCodeEnum : {}", id);
        listRoleCodeEnumRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
