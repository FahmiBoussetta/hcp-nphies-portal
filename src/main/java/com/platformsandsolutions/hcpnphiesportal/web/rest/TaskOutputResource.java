package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.TaskOutput;
import com.platformsandsolutions.hcpnphiesportal.repository.TaskOutputRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.TaskOutput}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaskOutputResource {

    private final Logger log = LoggerFactory.getLogger(TaskOutputResource.class);

    private static final String ENTITY_NAME = "taskOutput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskOutputRepository taskOutputRepository;

    public TaskOutputResource(TaskOutputRepository taskOutputRepository) {
        this.taskOutputRepository = taskOutputRepository;
    }

    /**
     * {@code POST  /task-outputs} : Create a new taskOutput.
     *
     * @param taskOutput the taskOutput to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskOutput, or with status {@code 400 (Bad Request)} if the taskOutput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-outputs")
    public ResponseEntity<TaskOutput> createTaskOutput(@RequestBody TaskOutput taskOutput) throws URISyntaxException {
        log.debug("REST request to save TaskOutput : {}", taskOutput);
        if (taskOutput.getId() != null) {
            throw new BadRequestAlertException("A new taskOutput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskOutput result = taskOutputRepository.save(taskOutput);
        return ResponseEntity
            .created(new URI("/api/task-outputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-outputs/:id} : Updates an existing taskOutput.
     *
     * @param id the id of the taskOutput to save.
     * @param taskOutput the taskOutput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskOutput,
     * or with status {@code 400 (Bad Request)} if the taskOutput is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskOutput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-outputs/{id}")
    public ResponseEntity<TaskOutput> updateTaskOutput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskOutput taskOutput
    ) throws URISyntaxException {
        log.debug("REST request to update TaskOutput : {}, {}", id, taskOutput);
        if (taskOutput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskOutput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskOutputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaskOutput result = taskOutputRepository.save(taskOutput);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskOutput.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-outputs/:id} : Partial updates given fields of an existing taskOutput, field will ignore if it is null
     *
     * @param id the id of the taskOutput to save.
     * @param taskOutput the taskOutput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskOutput,
     * or with status {@code 400 (Bad Request)} if the taskOutput is not valid,
     * or with status {@code 404 (Not Found)} if the taskOutput is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskOutput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-outputs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaskOutput> partialUpdateTaskOutput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskOutput taskOutput
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaskOutput partially : {}, {}", id, taskOutput);
        if (taskOutput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskOutput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskOutputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaskOutput> result = taskOutputRepository
            .findById(taskOutput.getId())
            .map(
                existingTaskOutput -> {
                    if (taskOutput.getStatus() != null) {
                        existingTaskOutput.setStatus(taskOutput.getStatus());
                    }
                    if (taskOutput.getErrorOutput() != null) {
                        existingTaskOutput.setErrorOutput(taskOutput.getErrorOutput());
                    }

                    return existingTaskOutput;
                }
            )
            .map(taskOutputRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskOutput.getId().toString())
        );
    }

    /**
     * {@code GET  /task-outputs} : get all the taskOutputs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskOutputs in body.
     */
    @GetMapping("/task-outputs")
    public List<TaskOutput> getAllTaskOutputs() {
        log.debug("REST request to get all TaskOutputs");
        return taskOutputRepository.findAll();
    }

    /**
     * {@code GET  /task-outputs/:id} : get the "id" taskOutput.
     *
     * @param id the id of the taskOutput to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskOutput, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-outputs/{id}")
    public ResponseEntity<TaskOutput> getTaskOutput(@PathVariable Long id) {
        log.debug("REST request to get TaskOutput : {}", id);
        Optional<TaskOutput> taskOutput = taskOutputRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taskOutput);
    }

    /**
     * {@code DELETE  /task-outputs/:id} : delete the "id" taskOutput.
     *
     * @param id the id of the taskOutput to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-outputs/{id}")
    public ResponseEntity<Void> deleteTaskOutput(@PathVariable Long id) {
        log.debug("REST request to delete TaskOutput : {}", id);
        taskOutputRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
