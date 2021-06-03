package com.platformsandsolutions.hcpnphiesportal.web.rest;

import com.platformsandsolutions.hcpnphiesportal.domain.TaskResponse;
import com.platformsandsolutions.hcpnphiesportal.repository.TaskResponseRepository;
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
 * REST controller for managing {@link com.platformsandsolutions.hcpnphiesportal.domain.TaskResponse}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaskResponseResource {

    private final Logger log = LoggerFactory.getLogger(TaskResponseResource.class);

    private static final String ENTITY_NAME = "taskResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskResponseRepository taskResponseRepository;

    public TaskResponseResource(TaskResponseRepository taskResponseRepository) {
        this.taskResponseRepository = taskResponseRepository;
    }

    /**
     * {@code POST  /task-responses} : Create a new taskResponse.
     *
     * @param taskResponse the taskResponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskResponse, or with status {@code 400 (Bad Request)} if the taskResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-responses")
    public ResponseEntity<TaskResponse> createTaskResponse(@RequestBody TaskResponse taskResponse) throws URISyntaxException {
        log.debug("REST request to save TaskResponse : {}", taskResponse);
        if (taskResponse.getId() != null) {
            throw new BadRequestAlertException("A new taskResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskResponse result = taskResponseRepository.save(taskResponse);
        return ResponseEntity
            .created(new URI("/api/task-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-responses/:id} : Updates an existing taskResponse.
     *
     * @param id the id of the taskResponse to save.
     * @param taskResponse the taskResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskResponse,
     * or with status {@code 400 (Bad Request)} if the taskResponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-responses/{id}")
    public ResponseEntity<TaskResponse> updateTaskResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskResponse taskResponse
    ) throws URISyntaxException {
        log.debug("REST request to update TaskResponse : {}, {}", id, taskResponse);
        if (taskResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TaskResponse result = taskResponseRepository.save(taskResponse);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskResponse.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /task-responses/:id} : Partial updates given fields of an existing taskResponse, field will ignore if it is null
     *
     * @param id the id of the taskResponse to save.
     * @param taskResponse the taskResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskResponse,
     * or with status {@code 400 (Bad Request)} if the taskResponse is not valid,
     * or with status {@code 404 (Not Found)} if the taskResponse is not found,
     * or with status {@code 500 (Internal Server Error)} if the taskResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/task-responses/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TaskResponse> partialUpdateTaskResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TaskResponse taskResponse
    ) throws URISyntaxException {
        log.debug("REST request to partial update TaskResponse partially : {}, {}", id, taskResponse);
        if (taskResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, taskResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!taskResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TaskResponse> result = taskResponseRepository
            .findById(taskResponse.getId())
            .map(
                existingTaskResponse -> {
                    if (taskResponse.getValue() != null) {
                        existingTaskResponse.setValue(taskResponse.getValue());
                    }
                    if (taskResponse.getSystem() != null) {
                        existingTaskResponse.setSystem(taskResponse.getSystem());
                    }
                    if (taskResponse.getParsed() != null) {
                        existingTaskResponse.setParsed(taskResponse.getParsed());
                    }
                    if (taskResponse.getStatus() != null) {
                        existingTaskResponse.setStatus(taskResponse.getStatus());
                    }

                    return existingTaskResponse;
                }
            )
            .map(taskResponseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskResponse.getId().toString())
        );
    }

    /**
     * {@code GET  /task-responses} : get all the taskResponses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskResponses in body.
     */
    @GetMapping("/task-responses")
    public List<TaskResponse> getAllTaskResponses() {
        log.debug("REST request to get all TaskResponses");
        return taskResponseRepository.findAll();
    }

    /**
     * {@code GET  /task-responses/:id} : get the "id" taskResponse.
     *
     * @param id the id of the taskResponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-responses/{id}")
    public ResponseEntity<TaskResponse> getTaskResponse(@PathVariable Long id) {
        log.debug("REST request to get TaskResponse : {}", id);
        Optional<TaskResponse> taskResponse = taskResponseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(taskResponse);
    }

    /**
     * {@code DELETE  /task-responses/:id} : delete the "id" taskResponse.
     *
     * @param id the id of the taskResponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-responses/{id}")
    public ResponseEntity<Void> deleteTaskResponse(@PathVariable Long id) {
        log.debug("REST request to delete TaskResponse : {}", id);
        taskResponseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
