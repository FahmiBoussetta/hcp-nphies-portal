package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.TaskOutput;
import com.platformsandsolutions.hcpnphiesportal.repository.TaskOutputRepository;
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
 * Integration tests for the {@link TaskOutputResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskOutputResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_OUTPUT = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_OUTPUT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/task-outputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaskOutputRepository taskOutputRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskOutputMockMvc;

    private TaskOutput taskOutput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskOutput createEntity(EntityManager em) {
        TaskOutput taskOutput = new TaskOutput().status(DEFAULT_STATUS).errorOutput(DEFAULT_ERROR_OUTPUT);
        return taskOutput;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskOutput createUpdatedEntity(EntityManager em) {
        TaskOutput taskOutput = new TaskOutput().status(UPDATED_STATUS).errorOutput(UPDATED_ERROR_OUTPUT);
        return taskOutput;
    }

    @BeforeEach
    public void initTest() {
        taskOutput = createEntity(em);
    }

    @Test
    @Transactional
    void createTaskOutput() throws Exception {
        int databaseSizeBeforeCreate = taskOutputRepository.findAll().size();
        // Create the TaskOutput
        restTaskOutputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskOutput)))
            .andExpect(status().isCreated());

        // Validate the TaskOutput in the database
        List<TaskOutput> taskOutputList = taskOutputRepository.findAll();
        assertThat(taskOutputList).hasSize(databaseSizeBeforeCreate + 1);
        TaskOutput testTaskOutput = taskOutputList.get(taskOutputList.size() - 1);
        assertThat(testTaskOutput.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTaskOutput.getErrorOutput()).isEqualTo(DEFAULT_ERROR_OUTPUT);
    }

    @Test
    @Transactional
    void createTaskOutputWithExistingId() throws Exception {
        // Create the TaskOutput with an existing ID
        taskOutput.setId(1L);

        int databaseSizeBeforeCreate = taskOutputRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskOutputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskOutput)))
            .andExpect(status().isBadRequest());

        // Validate the TaskOutput in the database
        List<TaskOutput> taskOutputList = taskOutputRepository.findAll();
        assertThat(taskOutputList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaskOutputs() throws Exception {
        // Initialize the database
        taskOutputRepository.saveAndFlush(taskOutput);

        // Get all the taskOutputList
        restTaskOutputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskOutput.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].errorOutput").value(hasItem(DEFAULT_ERROR_OUTPUT)));
    }

    @Test
    @Transactional
    void getTaskOutput() throws Exception {
        // Initialize the database
        taskOutputRepository.saveAndFlush(taskOutput);

        // Get the taskOutput
        restTaskOutputMockMvc
            .perform(get(ENTITY_API_URL_ID, taskOutput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskOutput.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.errorOutput").value(DEFAULT_ERROR_OUTPUT));
    }

    @Test
    @Transactional
    void getNonExistingTaskOutput() throws Exception {
        // Get the taskOutput
        restTaskOutputMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaskOutput() throws Exception {
        // Initialize the database
        taskOutputRepository.saveAndFlush(taskOutput);

        int databaseSizeBeforeUpdate = taskOutputRepository.findAll().size();

        // Update the taskOutput
        TaskOutput updatedTaskOutput = taskOutputRepository.findById(taskOutput.getId()).get();
        // Disconnect from session so that the updates on updatedTaskOutput are not directly saved in db
        em.detach(updatedTaskOutput);
        updatedTaskOutput.status(UPDATED_STATUS).errorOutput(UPDATED_ERROR_OUTPUT);

        restTaskOutputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaskOutput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaskOutput))
            )
            .andExpect(status().isOk());

        // Validate the TaskOutput in the database
        List<TaskOutput> taskOutputList = taskOutputRepository.findAll();
        assertThat(taskOutputList).hasSize(databaseSizeBeforeUpdate);
        TaskOutput testTaskOutput = taskOutputList.get(taskOutputList.size() - 1);
        assertThat(testTaskOutput.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaskOutput.getErrorOutput()).isEqualTo(UPDATED_ERROR_OUTPUT);
    }

    @Test
    @Transactional
    void putNonExistingTaskOutput() throws Exception {
        int databaseSizeBeforeUpdate = taskOutputRepository.findAll().size();
        taskOutput.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskOutputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taskOutput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskOutput))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskOutput in the database
        List<TaskOutput> taskOutputList = taskOutputRepository.findAll();
        assertThat(taskOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaskOutput() throws Exception {
        int databaseSizeBeforeUpdate = taskOutputRepository.findAll().size();
        taskOutput.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskOutputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskOutput))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskOutput in the database
        List<TaskOutput> taskOutputList = taskOutputRepository.findAll();
        assertThat(taskOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaskOutput() throws Exception {
        int databaseSizeBeforeUpdate = taskOutputRepository.findAll().size();
        taskOutput.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskOutputMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskOutput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskOutput in the database
        List<TaskOutput> taskOutputList = taskOutputRepository.findAll();
        assertThat(taskOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskOutputWithPatch() throws Exception {
        // Initialize the database
        taskOutputRepository.saveAndFlush(taskOutput);

        int databaseSizeBeforeUpdate = taskOutputRepository.findAll().size();

        // Update the taskOutput using partial update
        TaskOutput partialUpdatedTaskOutput = new TaskOutput();
        partialUpdatedTaskOutput.setId(taskOutput.getId());

        partialUpdatedTaskOutput.status(UPDATED_STATUS).errorOutput(UPDATED_ERROR_OUTPUT);

        restTaskOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskOutput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskOutput))
            )
            .andExpect(status().isOk());

        // Validate the TaskOutput in the database
        List<TaskOutput> taskOutputList = taskOutputRepository.findAll();
        assertThat(taskOutputList).hasSize(databaseSizeBeforeUpdate);
        TaskOutput testTaskOutput = taskOutputList.get(taskOutputList.size() - 1);
        assertThat(testTaskOutput.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaskOutput.getErrorOutput()).isEqualTo(UPDATED_ERROR_OUTPUT);
    }

    @Test
    @Transactional
    void fullUpdateTaskOutputWithPatch() throws Exception {
        // Initialize the database
        taskOutputRepository.saveAndFlush(taskOutput);

        int databaseSizeBeforeUpdate = taskOutputRepository.findAll().size();

        // Update the taskOutput using partial update
        TaskOutput partialUpdatedTaskOutput = new TaskOutput();
        partialUpdatedTaskOutput.setId(taskOutput.getId());

        partialUpdatedTaskOutput.status(UPDATED_STATUS).errorOutput(UPDATED_ERROR_OUTPUT);

        restTaskOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskOutput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskOutput))
            )
            .andExpect(status().isOk());

        // Validate the TaskOutput in the database
        List<TaskOutput> taskOutputList = taskOutputRepository.findAll();
        assertThat(taskOutputList).hasSize(databaseSizeBeforeUpdate);
        TaskOutput testTaskOutput = taskOutputList.get(taskOutputList.size() - 1);
        assertThat(testTaskOutput.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTaskOutput.getErrorOutput()).isEqualTo(UPDATED_ERROR_OUTPUT);
    }

    @Test
    @Transactional
    void patchNonExistingTaskOutput() throws Exception {
        int databaseSizeBeforeUpdate = taskOutputRepository.findAll().size();
        taskOutput.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taskOutput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskOutput))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskOutput in the database
        List<TaskOutput> taskOutputList = taskOutputRepository.findAll();
        assertThat(taskOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaskOutput() throws Exception {
        int databaseSizeBeforeUpdate = taskOutputRepository.findAll().size();
        taskOutput.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskOutputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskOutput))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskOutput in the database
        List<TaskOutput> taskOutputList = taskOutputRepository.findAll();
        assertThat(taskOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaskOutput() throws Exception {
        int databaseSizeBeforeUpdate = taskOutputRepository.findAll().size();
        taskOutput.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskOutputMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(taskOutput))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskOutput in the database
        List<TaskOutput> taskOutputList = taskOutputRepository.findAll();
        assertThat(taskOutputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaskOutput() throws Exception {
        // Initialize the database
        taskOutputRepository.saveAndFlush(taskOutput);

        int databaseSizeBeforeDelete = taskOutputRepository.findAll().size();

        // Delete the taskOutput
        restTaskOutputMockMvc
            .perform(delete(ENTITY_API_URL_ID, taskOutput.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskOutput> taskOutputList = taskOutputRepository.findAll();
        assertThat(taskOutputList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
