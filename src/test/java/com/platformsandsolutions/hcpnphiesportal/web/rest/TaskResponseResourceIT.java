package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.TaskResponse;
import com.platformsandsolutions.hcpnphiesportal.repository.TaskResponseRepository;
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
 * Integration tests for the {@link TaskResponseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskResponseResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_PARSED = "AAAAAAAAAA";
    private static final String UPDATED_PARSED = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/task-responses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaskResponseRepository taskResponseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskResponseMockMvc;

    private TaskResponse taskResponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskResponse createEntity(EntityManager em) {
        TaskResponse taskResponse = new TaskResponse()
            .value(DEFAULT_VALUE)
            .system(DEFAULT_SYSTEM)
            .parsed(DEFAULT_PARSED)
            .status(DEFAULT_STATUS);
        return taskResponse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskResponse createUpdatedEntity(EntityManager em) {
        TaskResponse taskResponse = new TaskResponse()
            .value(UPDATED_VALUE)
            .system(UPDATED_SYSTEM)
            .parsed(UPDATED_PARSED)
            .status(UPDATED_STATUS);
        return taskResponse;
    }

    @BeforeEach
    public void initTest() {
        taskResponse = createEntity(em);
    }

    @Test
    @Transactional
    void createTaskResponse() throws Exception {
        int databaseSizeBeforeCreate = taskResponseRepository.findAll().size();
        // Create the TaskResponse
        restTaskResponseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskResponse)))
            .andExpect(status().isCreated());

        // Validate the TaskResponse in the database
        List<TaskResponse> taskResponseList = taskResponseRepository.findAll();
        assertThat(taskResponseList).hasSize(databaseSizeBeforeCreate + 1);
        TaskResponse testTaskResponse = taskResponseList.get(taskResponseList.size() - 1);
        assertThat(testTaskResponse.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTaskResponse.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testTaskResponse.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testTaskResponse.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createTaskResponseWithExistingId() throws Exception {
        // Create the TaskResponse with an existing ID
        taskResponse.setId(1L);

        int databaseSizeBeforeCreate = taskResponseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskResponseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskResponse)))
            .andExpect(status().isBadRequest());

        // Validate the TaskResponse in the database
        List<TaskResponse> taskResponseList = taskResponseRepository.findAll();
        assertThat(taskResponseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaskResponses() throws Exception {
        // Initialize the database
        taskResponseRepository.saveAndFlush(taskResponse);

        // Get all the taskResponseList
        restTaskResponseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM)))
            .andExpect(jsonPath("$.[*].parsed").value(hasItem(DEFAULT_PARSED)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getTaskResponse() throws Exception {
        // Initialize the database
        taskResponseRepository.saveAndFlush(taskResponse);

        // Get the taskResponse
        restTaskResponseMockMvc
            .perform(get(ENTITY_API_URL_ID, taskResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskResponse.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM))
            .andExpect(jsonPath("$.parsed").value(DEFAULT_PARSED))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingTaskResponse() throws Exception {
        // Get the taskResponse
        restTaskResponseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaskResponse() throws Exception {
        // Initialize the database
        taskResponseRepository.saveAndFlush(taskResponse);

        int databaseSizeBeforeUpdate = taskResponseRepository.findAll().size();

        // Update the taskResponse
        TaskResponse updatedTaskResponse = taskResponseRepository.findById(taskResponse.getId()).get();
        // Disconnect from session so that the updates on updatedTaskResponse are not directly saved in db
        em.detach(updatedTaskResponse);
        updatedTaskResponse.value(UPDATED_VALUE).system(UPDATED_SYSTEM).parsed(UPDATED_PARSED).status(UPDATED_STATUS);

        restTaskResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaskResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaskResponse))
            )
            .andExpect(status().isOk());

        // Validate the TaskResponse in the database
        List<TaskResponse> taskResponseList = taskResponseRepository.findAll();
        assertThat(taskResponseList).hasSize(databaseSizeBeforeUpdate);
        TaskResponse testTaskResponse = taskResponseList.get(taskResponseList.size() - 1);
        assertThat(testTaskResponse.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTaskResponse.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testTaskResponse.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testTaskResponse.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingTaskResponse() throws Exception {
        int databaseSizeBeforeUpdate = taskResponseRepository.findAll().size();
        taskResponse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taskResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskResponse in the database
        List<TaskResponse> taskResponseList = taskResponseRepository.findAll();
        assertThat(taskResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaskResponse() throws Exception {
        int databaseSizeBeforeUpdate = taskResponseRepository.findAll().size();
        taskResponse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskResponse in the database
        List<TaskResponse> taskResponseList = taskResponseRepository.findAll();
        assertThat(taskResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaskResponse() throws Exception {
        int databaseSizeBeforeUpdate = taskResponseRepository.findAll().size();
        taskResponse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskResponseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskResponse in the database
        List<TaskResponse> taskResponseList = taskResponseRepository.findAll();
        assertThat(taskResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskResponseWithPatch() throws Exception {
        // Initialize the database
        taskResponseRepository.saveAndFlush(taskResponse);

        int databaseSizeBeforeUpdate = taskResponseRepository.findAll().size();

        // Update the taskResponse using partial update
        TaskResponse partialUpdatedTaskResponse = new TaskResponse();
        partialUpdatedTaskResponse.setId(taskResponse.getId());

        partialUpdatedTaskResponse.value(UPDATED_VALUE).system(UPDATED_SYSTEM);

        restTaskResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskResponse))
            )
            .andExpect(status().isOk());

        // Validate the TaskResponse in the database
        List<TaskResponse> taskResponseList = taskResponseRepository.findAll();
        assertThat(taskResponseList).hasSize(databaseSizeBeforeUpdate);
        TaskResponse testTaskResponse = taskResponseList.get(taskResponseList.size() - 1);
        assertThat(testTaskResponse.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTaskResponse.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testTaskResponse.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testTaskResponse.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateTaskResponseWithPatch() throws Exception {
        // Initialize the database
        taskResponseRepository.saveAndFlush(taskResponse);

        int databaseSizeBeforeUpdate = taskResponseRepository.findAll().size();

        // Update the taskResponse using partial update
        TaskResponse partialUpdatedTaskResponse = new TaskResponse();
        partialUpdatedTaskResponse.setId(taskResponse.getId());

        partialUpdatedTaskResponse.value(UPDATED_VALUE).system(UPDATED_SYSTEM).parsed(UPDATED_PARSED).status(UPDATED_STATUS);

        restTaskResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskResponse))
            )
            .andExpect(status().isOk());

        // Validate the TaskResponse in the database
        List<TaskResponse> taskResponseList = taskResponseRepository.findAll();
        assertThat(taskResponseList).hasSize(databaseSizeBeforeUpdate);
        TaskResponse testTaskResponse = taskResponseList.get(taskResponseList.size() - 1);
        assertThat(testTaskResponse.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTaskResponse.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testTaskResponse.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testTaskResponse.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingTaskResponse() throws Exception {
        int databaseSizeBeforeUpdate = taskResponseRepository.findAll().size();
        taskResponse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taskResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskResponse in the database
        List<TaskResponse> taskResponseList = taskResponseRepository.findAll();
        assertThat(taskResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaskResponse() throws Exception {
        int databaseSizeBeforeUpdate = taskResponseRepository.findAll().size();
        taskResponse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskResponse in the database
        List<TaskResponse> taskResponseList = taskResponseRepository.findAll();
        assertThat(taskResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaskResponse() throws Exception {
        int databaseSizeBeforeUpdate = taskResponseRepository.findAll().size();
        taskResponse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskResponseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(taskResponse))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskResponse in the database
        List<TaskResponse> taskResponseList = taskResponseRepository.findAll();
        assertThat(taskResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaskResponse() throws Exception {
        // Initialize the database
        taskResponseRepository.saveAndFlush(taskResponse);

        int databaseSizeBeforeDelete = taskResponseRepository.findAll().size();

        // Delete the taskResponse
        restTaskResponseMockMvc
            .perform(delete(ENTITY_API_URL_ID, taskResponse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskResponse> taskResponseList = taskResponseRepository.findAll();
        assertThat(taskResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
