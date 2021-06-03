package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.SupportingInfo;
import com.platformsandsolutions.hcpnphiesportal.repository.SupportingInfoRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.SupportingInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SupportingInfoResource {

    private final Logger log = LoggerFactory.getLogger(SupportingInfoResource.class);

    private static final String ENTITY_NAME = "supportingInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupportingInfoRepository supportingInfoRepository;

    public SupportingInfoResource(SupportingInfoRepository supportingInfoRepository) {
        this.supportingInfoRepository = supportingInfoRepository;
    }

    /**
     * {@code POST  /supporting-infos} : Create a new supportingInfo.
     *
     * @param supportingInfo the supportingInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supportingInfo, or with status {@code 400 (Bad Request)} if the supportingInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supporting-infos")
    public ResponseEntity<SupportingInfo> createSupportingInfo(@RequestBody SupportingInfo supportingInfo) throws URISyntaxException {
        log.debug("REST request to save SupportingInfo : {}", supportingInfo);
        if (supportingInfo.getId() != null) {
            throw new BadRequestAlertException("A new supportingInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupportingInfo result = supportingInfoRepository.save(supportingInfo);
        return ResponseEntity
            .created(new URI("/api/supporting-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supporting-infos/:id} : Updates an existing supportingInfo.
     *
     * @param id the id of the supportingInfo to save.
     * @param supportingInfo the supportingInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportingInfo,
     * or with status {@code 400 (Bad Request)} if the supportingInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supportingInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supporting-infos/{id}")
    public ResponseEntity<SupportingInfo> updateSupportingInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SupportingInfo supportingInfo
    ) throws URISyntaxException {
        log.debug("REST request to update SupportingInfo : {}, {}", id, supportingInfo);
        if (supportingInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supportingInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supportingInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SupportingInfo result = supportingInfoRepository.save(supportingInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supportingInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /supporting-infos/:id} : Partial updates given fields of an existing supportingInfo, field will ignore if it is null
     *
     * @param id the id of the supportingInfo to save.
     * @param supportingInfo the supportingInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportingInfo,
     * or with status {@code 400 (Bad Request)} if the supportingInfo is not valid,
     * or with status {@code 404 (Not Found)} if the supportingInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the supportingInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/supporting-infos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SupportingInfo> partialUpdateSupportingInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SupportingInfo supportingInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update SupportingInfo partially : {}, {}", id, supportingInfo);
        if (supportingInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supportingInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supportingInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SupportingInfo> result = supportingInfoRepository
            .findById(supportingInfo.getId())
            .map(
                existingSupportingInfo -> {
                    if (supportingInfo.getSequence() != null) {
                        existingSupportingInfo.setSequence(supportingInfo.getSequence());
                    }
                    if (supportingInfo.getCodeLOINC() != null) {
                        existingSupportingInfo.setCodeLOINC(supportingInfo.getCodeLOINC());
                    }
                    if (supportingInfo.getCategory() != null) {
                        existingSupportingInfo.setCategory(supportingInfo.getCategory());
                    }
                    if (supportingInfo.getCodeVisit() != null) {
                        existingSupportingInfo.setCodeVisit(supportingInfo.getCodeVisit());
                    }
                    if (supportingInfo.getCodeFdiOral() != null) {
                        existingSupportingInfo.setCodeFdiOral(supportingInfo.getCodeFdiOral());
                    }
                    if (supportingInfo.getTiming() != null) {
                        existingSupportingInfo.setTiming(supportingInfo.getTiming());
                    }
                    if (supportingInfo.getTimingEnd() != null) {
                        existingSupportingInfo.setTimingEnd(supportingInfo.getTimingEnd());
                    }
                    if (supportingInfo.getValueBoolean() != null) {
                        existingSupportingInfo.setValueBoolean(supportingInfo.getValueBoolean());
                    }
                    if (supportingInfo.getValueString() != null) {
                        existingSupportingInfo.setValueString(supportingInfo.getValueString());
                    }
                    if (supportingInfo.getReason() != null) {
                        existingSupportingInfo.setReason(supportingInfo.getReason());
                    }
                    if (supportingInfo.getReasonMissingTooth() != null) {
                        existingSupportingInfo.setReasonMissingTooth(supportingInfo.getReasonMissingTooth());
                    }

                    return existingSupportingInfo;
                }
            )
            .map(supportingInfoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supportingInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /supporting-infos} : get all the supportingInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supportingInfos in body.
     */
    @GetMapping("/supporting-infos")
    public List<SupportingInfo> getAllSupportingInfos() {
        log.debug("REST request to get all SupportingInfos");
        return supportingInfoRepository.findAll();
    }

    /**
     * {@code GET  /supporting-infos/:id} : get the "id" supportingInfo.
     *
     * @param id the id of the supportingInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supportingInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supporting-infos/{id}")
    public ResponseEntity<SupportingInfo> getSupportingInfo(@PathVariable Long id) {
        log.debug("REST request to get SupportingInfo : {}", id);
        Optional<SupportingInfo> supportingInfo = supportingInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(supportingInfo);
    }

    /**
     * {@code DELETE  /supporting-infos/:id} : delete the "id" supportingInfo.
     *
     * @param id the id of the supportingInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supporting-infos/{id}")
    public ResponseEntity<Void> deleteSupportingInfo(@PathVariable Long id) {
        log.debug("REST request to delete SupportingInfo : {}", id);
        supportingInfoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
