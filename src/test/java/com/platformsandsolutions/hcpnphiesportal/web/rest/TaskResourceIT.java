package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Task;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.TaskCodeEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.TaskReasonCodeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.TaskRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TaskResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_QUEUED = false;
    private static final Boolean UPDATED_IS_QUEUED = true;

    private static final String DEFAULT_PARSED = "AAAAAAAAAA";
    private static final String UPDATED_PARSED = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final TaskCodeEnum DEFAULT_CODE = TaskCodeEnum.Cancel;
    private static final TaskCodeEnum UPDATED_CODE = TaskCodeEnum.Nullify;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FOCUS = "AAAAAAAAAA";
    private static final String UPDATED_FOCUS = "BBBBBBBBBB";

    private static final TaskReasonCodeEnum DEFAULT_REASON_CODE = TaskReasonCodeEnum.WI;
    private static final TaskReasonCodeEnum UPDATED_REASON_CODE = TaskReasonCodeEnum.NP;

    private static final String ENTITY_API_URL = "/api/tasks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskMockMvc;

    private Task task;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createEntity(EntityManager em) {
        Task task = new Task()
            .guid(DEFAULT_GUID)
            .isQueued(DEFAULT_IS_QUEUED)
            .parsed(DEFAULT_PARSED)
            .identifier(DEFAULT_IDENTIFIER)
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .focus(DEFAULT_FOCUS)
            .reasonCode(DEFAULT_REASON_CODE);
        return task;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Task createUpdatedEntity(EntityManager em) {
        Task task = new Task()
            .guid(UPDATED_GUID)
            .isQueued(UPDATED_IS_QUEUED)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .focus(UPDATED_FOCUS)
            .reasonCode(UPDATED_REASON_CODE);
        return task;
    }

    @BeforeEach
    public void initTest() {
        task = createEntity(em);
    }

    @Test
    @Transactional
    void createTask() throws Exception {
        int databaseSizeBeforeCreate = taskRepository.findAll().size();
        // Create the Task
        restTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isCreated());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate + 1);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testTask.getIsQueued()).isEqualTo(DEFAULT_IS_QUEUED);
        assertThat(testTask.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testTask.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testTask.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTask.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTask.getFocus()).isEqualTo(DEFAULT_FOCUS);
        assertThat(testTask.getReasonCode()).isEqualTo(DEFAULT_REASON_CODE);
    }

    @Test
    @Transactional
    void createTaskWithExistingId() throws Exception {
        // Create the Task with an existing ID
        task.setId(1L);

        int databaseSizeBeforeCreate = taskRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTasks() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get all the taskList
        restTaskMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(task.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].isQueued").value(hasItem(DEFAULT_IS_QUEUED.booleanValue())))
            .andExpect(jsonPath("$.[*].parsed").value(hasItem(DEFAULT_PARSED)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].focus").value(hasItem(DEFAULT_FOCUS)))
            .andExpect(jsonPath("$.[*].reasonCode").value(hasItem(DEFAULT_REASON_CODE.toString())));
    }

    @Test
    @Transactional
    void getTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        // Get the task
        restTaskMockMvc
            .perform(get(ENTITY_API_URL_ID, task.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(task.getId().intValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.isQueued").value(DEFAULT_IS_QUEUED.booleanValue()))
            .andExpect(jsonPath("$.parsed").value(DEFAULT_PARSED))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.focus").value(DEFAULT_FOCUS))
            .andExpect(jsonPath("$.reasonCode").value(DEFAULT_REASON_CODE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTask() throws Exception {
        // Get the task
        restTaskMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task
        Task updatedTask = taskRepository.findById(task.getId()).get();
        // Disconnect from session so that the updates on updatedTask are not directly saved in db
        em.detach(updatedTask);
        updatedTask
            .guid(UPDATED_GUID)
            .isQueued(UPDATED_IS_QUEUED)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .focus(UPDATED_FOCUS)
            .reasonCode(UPDATED_REASON_CODE);

        restTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTask.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTask))
            )
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testTask.getIsQueued()).isEqualTo(UPDATED_IS_QUEUED);
        assertThat(testTask.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testTask.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testTask.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTask.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTask.getFocus()).isEqualTo(UPDATED_FOCUS);
        assertThat(testTask.getReasonCode()).isEqualTo(UPDATED_REASON_CODE);
    }

    @Test
    @Transactional
    void putNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, task.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(task))
            )
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(task))
            )
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskWithPatch() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task using partial update
        Task partialUpdatedTask = new Task();
        partialUpdatedTask.setId(task.getId());

        partialUpdatedTask.isQueued(UPDATED_IS_QUEUED).identifier(UPDATED_IDENTIFIER).reasonCode(UPDATED_REASON_CODE);

        restTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTask))
            )
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testTask.getIsQueued()).isEqualTo(UPDATED_IS_QUEUED);
        assertThat(testTask.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testTask.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testTask.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTask.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTask.getFocus()).isEqualTo(DEFAULT_FOCUS);
        assertThat(testTask.getReasonCode()).isEqualTo(UPDATED_REASON_CODE);
    }

    @Test
    @Transactional
    void fullUpdateTaskWithPatch() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeUpdate = taskRepository.findAll().size();

        // Update the task using partial update
        Task partialUpdatedTask = new Task();
        partialUpdatedTask.setId(task.getId());

        partialUpdatedTask
            .guid(UPDATED_GUID)
            .isQueued(UPDATED_IS_QUEUED)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .focus(UPDATED_FOCUS)
            .reasonCode(UPDATED_REASON_CODE);

        restTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTask.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTask))
            )
            .andExpect(status().isOk());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
        Task testTask = taskList.get(taskList.size() - 1);
        assertThat(testTask.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testTask.getIsQueued()).isEqualTo(UPDATED_IS_QUEUED);
        assertThat(testTask.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testTask.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testTask.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTask.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTask.getFocus()).isEqualTo(UPDATED_FOCUS);
        assertThat(testTask.getReasonCode()).isEqualTo(UPDATED_REASON_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, task.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(task))
            )
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(task))
            )
            .andExpect(status().isBadRequest());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTask() throws Exception {
        int databaseSizeBeforeUpdate = taskRepository.findAll().size();
        task.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(task)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Task in the database
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTask() throws Exception {
        // Initialize the database
        taskRepository.saveAndFlush(task);

        int databaseSizeBeforeDelete = taskRepository.findAll().size();

        // Delete the task
        restTaskMockMvc
            .perform(delete(ENTITY_API_URL_ID, task.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
