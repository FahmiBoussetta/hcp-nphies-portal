package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.CareTeam;
import com.platformsandsolutions.hcpnphiesportal.repository.CareTeamRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.CareTeam}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CareTeamResource {

    private final Logger log = LoggerFactory.getLogger(CareTeamResource.class);

    private static final String ENTITY_NAME = "careTeam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CareTeamRepository careTeamRepository;

    public CareTeamResource(CareTeamRepository careTeamRepository) {
        this.careTeamRepository = careTeamRepository;
    }

    /**
     * {@code POST  /care-teams} : Create a new careTeam.
     *
     * @param careTeam the careTeam to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new careTeam, or with status {@code 400 (Bad Request)} if the careTeam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/care-teams")
    public ResponseEntity<CareTeam> createCareTeam(@Valid @RequestBody CareTeam careTeam) throws URISyntaxException {
        log.debug("REST request to save CareTeam : {}", careTeam);
        if (careTeam.getId() != null) {
            throw new BadRequestAlertException("A new careTeam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CareTeam result = careTeamRepository.save(careTeam);
        return ResponseEntity
            .created(new URI("/api/care-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /care-teams/:id} : Updates an existing careTeam.
     *
     * @param id the id of the careTeam to save.
     * @param careTeam the careTeam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated careTeam,
     * or with status {@code 400 (Bad Request)} if the careTeam is not valid,
     * or with status {@code 500 (Internal Server Error)} if the careTeam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/care-teams/{id}")
    public ResponseEntity<CareTeam> updateCareTeam(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CareTeam careTeam
    ) throws URISyntaxException {
        log.debug("REST request to update CareTeam : {}, {}", id, careTeam);
        if (careTeam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, careTeam.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!careTeamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CareTeam result = careTeamRepository.save(careTeam);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, careTeam.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /care-teams/:id} : Partial updates given fields of an existing careTeam, field will ignore if it is null
     *
     * @param id the id of the careTeam to save.
     * @param careTeam the careTeam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated careTeam,
     * or with status {@code 400 (Bad Request)} if the careTeam is not valid,
     * or with status {@code 404 (Not Found)} if the careTeam is not found,
     * or with status {@code 500 (Internal Server Error)} if the careTeam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/care-teams/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CareTeam> partialUpdateCareTeam(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CareTeam careTeam
    ) throws URISyntaxException {
        log.debug("REST request to partial update CareTeam partially : {}, {}", id, careTeam);
        if (careTeam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, careTeam.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!careTeamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CareTeam> result = careTeamRepository
            .findById(careTeam.getId())
            .map(
                existingCareTeam -> {
                    if (careTeam.getSequence() != null) {
                        existingCareTeam.setSequence(careTeam.getSequence());
                    }
                    if (careTeam.getRole() != null) {
                        existingCareTeam.setRole(careTeam.getRole());
                    }

                    return existingCareTeam;
                }
            )
            .map(careTeamRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, careTeam.getId().toString())
        );
    }

    /**
     * {@code GET  /care-teams} : get all the careTeams.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of careTeams in body.
     */
    @GetMapping("/care-teams")
    public List<CareTeam> getAllCareTeams() {
        log.debug("REST request to get all CareTeams");
        return careTeamRepository.findAll();
    }

    /**
     * {@code GET  /care-teams/:id} : get the "id" careTeam.
     *
     * @param id the id of the careTeam to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the careTeam, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/care-teams/{id}")
    public ResponseEntity<CareTeam> getCareTeam(@PathVariable Long id) {
        log.debug("REST request to get CareTeam : {}", id);
        Optional<CareTeam> careTeam = careTeamRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(careTeam);
    }

    /**
     * {@code DELETE  /care-teams/:id} : delete the "id" careTeam.
     *
     * @param id the id of the careTeam to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/care-teams/{id}")
    public ResponseEntity<Void> deleteCareTeam(@PathVariable Long id) {
        log.debug("REST request to delete CareTeam : {}", id);
        careTeamRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
