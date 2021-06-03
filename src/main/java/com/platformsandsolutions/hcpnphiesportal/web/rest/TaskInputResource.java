package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.TaskInput;
import com.platformsandsolutions.hcpnphiesportal.repository.TaskInputRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.TaskInput}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaskInputResource {

    private final Logger log = LoggerFactory.getLogger(TaskInputResource.class);

    private static final String ENTITY_NAME = "taskInput";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskInputRepository taskInputRepository;

    public TaskInputResource(TaskInputRepository taskInputRepository) {
        this.taskInputRepository = taskInputRepository;
    }

    /**
     * {@code POST  /task-inputs} : Create a new taskInput.
     *
     * @param taskInput the taskInput to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskInput, or with status {@code 400 (Bad Request)} if the taskInput has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-inputs")
    public ResponseEntity<TaskInput> createTaskInput(@RequestBody TaskInput taskInput) throws URISyntaxException {
        log.debug("REST request to save TaskInput : {}", taskInput);
        if (taskInput.getId() != null) {
            throw new BadRequestAlertException("A new taskInput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskInput result = taskInputRepository.save(taskInput);
        return ResponseEntity
            .created(new URI("/api/task-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-inputs/:id} : Updates an existing taskInput.
     *
     * @param id the id of the taskInput to save.
     * @param taskInput the taskInput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskInput,
     * or with status {@code 400 (Bad Request)} if the taskInput is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskInput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-inputs/{id}")
    public ResponseEntity<TaskInput> updateTaskInput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskInput taskInput
    ) throws URISyntaxException {
        log.debug("REST request to update TaskInput : {}, {}", id, taskInput);
        if (taskInput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskInput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskInputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaskInput result = taskInputRepository.save(taskInput);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskInput.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-inputs/:id} : Partial updates given fields of an existing taskInput, field will ignore if it is null
     *
     * @param id the id of the taskInput to save.
     * @param taskInput the taskInput to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskInput,
     * or with status {@code 400 (Bad Request)} if the taskInput is not valid,
     * or with status {@code 404 (Not Found)} if the taskInput is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskInput couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-inputs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaskInput> partialUpdateTaskInput(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskInput taskInput
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaskInput partially : {}, {}", id, taskInput);
        if (taskInput.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskInput.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskInputRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaskInput> result = taskInputRepository
            .findById(taskInput.getId())
            .map(
                existingTaskInput -> {
                    if (taskInput.getInputInclude() != null) {
                        existingTaskInput.setInputInclude(taskInput.getInputInclude());
                    }
                    if (taskInput.getInputExclude() != null) {
                        existingTaskInput.setInputExclude(taskInput.getInputExclude());
                    }
                    if (taskInput.getInputIncludeMessage() != null) {
                        existingTaskInput.setInputIncludeMessage(taskInput.getInputIncludeMessage());
                    }
                    if (taskInput.getInputExcludeMessage() != null) {
                        existingTaskInput.setInputExcludeMessage(taskInput.getInputExcludeMessage());
                    }
                    if (taskInput.getInputCount() != null) {
                        existingTaskInput.setInputCount(taskInput.getInputCount());
                    }
                    if (taskInput.getInputStart() != null) {
                        existingTaskInput.setInputStart(taskInput.getInputStart());
                    }
                    if (taskInput.getInputEnd() != null) {
                        existingTaskInput.setInputEnd(taskInput.getInputEnd());
                    }
                    if (taskInput.getInputLineItem() != null) {
                        existingTaskInput.setInputLineItem(taskInput.getInputLineItem());
                    }

                    return existingTaskInput;
                }
            )
            .map(taskInputRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskInput.getId().toString())
        );
    }

    /**
     * {@code GET  /task-inputs} : get all the taskInputs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskInputs in body.
     */
    @GetMapping("/task-inputs")
    public List<TaskInput> getAllTaskInputs() {
        log.debug("REST request to get all TaskInputs");
        return taskInputRepository.findAll();
    }

    /**
     * {@code GET  /task-inputs/:id} : get the "id" taskInput.
     *
     * @param id the id of the taskInput to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskInput, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-inputs/{id}")
    public ResponseEntity<TaskInput> getTaskInput(@PathVariable Long id) {
        log.debug("REST request to get TaskInput : {}", id);
        Optional<TaskInput> taskInput = taskInputRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taskInput);
    }

    /**
     * {@code DELETE  /task-inputs/:id} : delete the "id" taskInput.
     *
     * @param id the id of the taskInput to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-inputs/{id}")
    public ResponseEntity<Void> deleteTaskInput(@PathVariable Long id) {
        log.debug("REST request to delete TaskInput : {}", id);
        taskInputRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
