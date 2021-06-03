package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.ClassComponent;
import com.platformsandsolutions.hcpnphiesportal.repository.ClassComponentRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.ClassComponent}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClassComponentResource {

    private final Logger log = LoggerFactory.getLogger(ClassComponentResource.class);

    private static final String ENTITY_NAME = "classComponent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassComponentRepository classComponentRepository;

    public ClassComponentResource(ClassComponentRepository classComponentRepository) {
        this.classComponentRepository = classComponentRepository;
    }

    /**
     * {@code POST  /class-components} : Create a new classComponent.
     *
     * @param classComponent the classComponent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classComponent, or with status {@code 400 (Bad Request)} if the classComponent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-components")
    public ResponseEntity<ClassComponent> createClassComponent(@Valid @RequestBody ClassComponent classComponent)
        throws URISyntaxException {
        log.debug("REST request to save ClassComponent : {}", classComponent);
        if (classComponent.getId() != null) {
            throw new BadRequestAlertException("A new classComponent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassComponent result = classComponentRepository.save(classComponent);
        return ResponseEntity
            .created(new URI("/api/class-components/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-components/:id} : Updates an existing classComponent.
     *
     * @param id the id of the classComponent to save.
     * @param classComponent the classComponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classComponent,
     * or with status {@code 400 (Bad Request)} if the classComponent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classComponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-components/{id}")
    public ResponseEntity<ClassComponent> updateClassComponent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClassComponent classComponent
    ) throws URISyntaxException {
        log.debug("REST request to update ClassComponent : {}, {}", id, classComponent);
        if (classComponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classComponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClassComponent result = classComponentRepository.save(classComponent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classComponent.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /class-components/:id} : Partial updates given fields of an existing classComponent, field will ignore if it is null
     *
     * @param id the id of the classComponent to save.
     * @param classComponent the classComponent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classComponent,
     * or with status {@code 400 (Bad Request)} if the classComponent is not valid,
     * or with status {@code 404 (Not Found)} if the classComponent is not found,
     * or with status {@code 500 (Internal Server Error)} if the classComponent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/class-components/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ClassComponent> partialUpdateClassComponent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClassComponent classComponent
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClassComponent partially : {}, {}", id, classComponent);
        if (classComponent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classComponent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classComponentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClassComponent> result = classComponentRepository
            .findById(classComponent.getId())
            .map(
                existingClassComponent -> {
                    if (classComponent.getType() != null) {
                        existingClassComponent.setType(classComponent.getType());
                    }
                    if (classComponent.getValue() != null) {
                        existingClassComponent.setValue(classComponent.getValue());
                    }
                    if (classComponent.getName() != null) {
                        existingClassComponent.setName(classComponent.getName());
                    }

                    return existingClassComponent;
                }
            )
            .map(classComponentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classComponent.getId().toString())
        );
    }

    /**
     * {@code GET  /class-components} : get all the classComponents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classComponents in body.
     */
    @GetMapping("/class-components")
    public List<ClassComponent> getAllClassComponents() {
        log.debug("REST request to get all ClassComponents");
        return classComponentRepository.findAll();
    }

    /**
     * {@code GET  /class-components/:id} : get the "id" classComponent.
     *
     * @param id the id of the classComponent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classComponent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-components/{id}")
    public ResponseEntity<ClassComponent> getClassComponent(@PathVariable Long id) {
        log.debug("REST request to get ClassComponent : {}", id);
        Optional<ClassComponent> classComponent = classComponentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classComponent);
    }

    /**
     * {@code DELETE  /class-components/:id} : delete the "id" classComponent.
     *
     * @param id the id of the classComponent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-components/{id}")
    public ResponseEntity<Void> deleteClassComponent(@PathVariable Long id) {
        log.debug("REST request to delete ClassComponent : {}", id);
        classComponentRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
