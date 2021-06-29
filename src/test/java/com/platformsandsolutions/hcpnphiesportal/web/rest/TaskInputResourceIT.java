package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.TaskInput;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.EventCodingEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.EventCodingEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ResourceTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ResourceTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.TaskInputRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link TaskInputResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TaskInputResourceIT {

    private static final ResourceTypeEnum DEFAULT_INPUT_INCLUDE = ResourceTypeEnum.Claim;
    private static final ResourceTypeEnum UPDATED_INPUT_INCLUDE = ResourceTypeEnum.ClaimResponse;

    private static final ResourceTypeEnum DEFAULT_INPUT_EXCLUDE = ResourceTypeEnum.Claim;
    private static final ResourceTypeEnum UPDATED_INPUT_EXCLUDE = ResourceTypeEnum.ClaimResponse;

    private static final EventCodingEnum DEFAULT_INPUT_INCLUDE_MESSAGE = EventCodingEnum.Eligibility_request;
    private static final EventCodingEnum UPDATED_INPUT_INCLUDE_MESSAGE = EventCodingEnum.Eligibility_response;

    private static final EventCodingEnum DEFAULT_INPUT_EXCLUDE_MESSAGE = EventCodingEnum.Eligibility_request;
    private static final EventCodingEnum UPDATED_INPUT_EXCLUDE_MESSAGE = EventCodingEnum.Eligibility_response;

    private static final Integer DEFAULT_INPUT_COUNT = 1;
    private static final Integer UPDATED_INPUT_COUNT = 2;

    private static final Instant DEFAULT_INPUT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INPUT_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INPUT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INPUT_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_INPUT_LINE_ITEM = 1;
    private static final Integer UPDATED_INPUT_LINE_ITEM = 2;

    private static final String ENTITY_API_URL = "/api/task-inputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TaskInputRepository taskInputRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskInputMockMvc;

    private TaskInput taskInput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskInput createEntity(EntityManager em) {
        TaskInput taskInput = new TaskInput()
            .inputInclude(DEFAULT_INPUT_INCLUDE)
            .inputExclude(DEFAULT_INPUT_EXCLUDE)
            .inputIncludeMessage(DEFAULT_INPUT_INCLUDE_MESSAGE)
            .inputExcludeMessage(DEFAULT_INPUT_EXCLUDE_MESSAGE)
            .inputCount(DEFAULT_INPUT_COUNT)
            .inputStart(DEFAULT_INPUT_START)
            .inputEnd(DEFAULT_INPUT_END)
            .inputLineItem(DEFAULT_INPUT_LINE_ITEM);
        return taskInput;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskInput createUpdatedEntity(EntityManager em) {
        TaskInput taskInput = new TaskInput()
            .inputInclude(UPDATED_INPUT_INCLUDE)
            .inputExclude(UPDATED_INPUT_EXCLUDE)
            .inputIncludeMessage(UPDATED_INPUT_INCLUDE_MESSAGE)
            .inputExcludeMessage(UPDATED_INPUT_EXCLUDE_MESSAGE)
            .inputCount(UPDATED_INPUT_COUNT)
            .inputStart(UPDATED_INPUT_START)
            .inputEnd(UPDATED_INPUT_END)
            .inputLineItem(UPDATED_INPUT_LINE_ITEM);
        return taskInput;
    }

    @BeforeEach
    public void initTest() {
        taskInput = createEntity(em);
    }

    @Test
    @Transactional
    void createTaskInput() throws Exception {
        int databaseSizeBeforeCreate = taskInputRepository.findAll().size();
        // Create the TaskInput
        restTaskInputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskInput)))
            .andExpect(status().isCreated());

        // Validate the TaskInput in the database
        List<TaskInput> taskInputList = taskInputRepository.findAll();
        assertThat(taskInputList).hasSize(databaseSizeBeforeCreate + 1);
        TaskInput testTaskInput = taskInputList.get(taskInputList.size() - 1);
        assertThat(testTaskInput.getInputInclude()).isEqualTo(DEFAULT_INPUT_INCLUDE);
        assertThat(testTaskInput.getInputExclude()).isEqualTo(DEFAULT_INPUT_EXCLUDE);
        assertThat(testTaskInput.getInputIncludeMessage()).isEqualTo(DEFAULT_INPUT_INCLUDE_MESSAGE);
        assertThat(testTaskInput.getInputExcludeMessage()).isEqualTo(DEFAULT_INPUT_EXCLUDE_MESSAGE);
        assertThat(testTaskInput.getInputCount()).isEqualTo(DEFAULT_INPUT_COUNT);
        assertThat(testTaskInput.getInputStart()).isEqualTo(DEFAULT_INPUT_START);
        assertThat(testTaskInput.getInputEnd()).isEqualTo(DEFAULT_INPUT_END);
        assertThat(testTaskInput.getInputLineItem()).isEqualTo(DEFAULT_INPUT_LINE_ITEM);
    }

    @Test
    @Transactional
    void createTaskInputWithExistingId() throws Exception {
        // Create the TaskInput with an existing ID
        taskInput.setId(1L);

        int databaseSizeBeforeCreate = taskInputRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskInputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskInput)))
            .andExpect(status().isBadRequest());

        // Validate the TaskInput in the database
        List<TaskInput> taskInputList = taskInputRepository.findAll();
        assertThat(taskInputList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaskInputs() throws Exception {
        // Initialize the database
        taskInputRepository.saveAndFlush(taskInput);

        // Get all the taskInputList
        restTaskInputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskInput.getId().intValue())))
            .andExpect(jsonPath("$.[*].inputInclude").value(hasItem(DEFAULT_INPUT_INCLUDE.toString())))
            .andExpect(jsonPath("$.[*].inputExclude").value(hasItem(DEFAULT_INPUT_EXCLUDE.toString())))
            .andExpect(jsonPath("$.[*].inputIncludeMessage").value(hasItem(DEFAULT_INPUT_INCLUDE_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].inputExcludeMessage").value(hasItem(DEFAULT_INPUT_EXCLUDE_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].inputCount").value(hasItem(DEFAULT_INPUT_COUNT)))
            .andExpect(jsonPath("$.[*].inputStart").value(hasItem(DEFAULT_INPUT_START.toString())))
            .andExpect(jsonPath("$.[*].inputEnd").value(hasItem(DEFAULT_INPUT_END.toString())))
            .andExpect(jsonPath("$.[*].inputLineItem").value(hasItem(DEFAULT_INPUT_LINE_ITEM)));
    }

    @Test
    @Transactional
    void getTaskInput() throws Exception {
        // Initialize the database
        taskInputRepository.saveAndFlush(taskInput);

        // Get the taskInput
        restTaskInputMockMvc
            .perform(get(ENTITY_API_URL_ID, taskInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskInput.getId().intValue()))
            .andExpect(jsonPath("$.inputInclude").value(DEFAULT_INPUT_INCLUDE.toString()))
            .andExpect(jsonPath("$.inputExclude").value(DEFAULT_INPUT_EXCLUDE.toString()))
            .andExpect(jsonPath("$.inputIncludeMessage").value(DEFAULT_INPUT_INCLUDE_MESSAGE.toString()))
            .andExpect(jsonPath("$.inputExcludeMessage").value(DEFAULT_INPUT_EXCLUDE_MESSAGE.toString()))
            .andExpect(jsonPath("$.inputCount").value(DEFAULT_INPUT_COUNT))
            .andExpect(jsonPath("$.inputStart").value(DEFAULT_INPUT_START.toString()))
            .andExpect(jsonPath("$.inputEnd").value(DEFAULT_INPUT_END.toString()))
            .andExpect(jsonPath("$.inputLineItem").value(DEFAULT_INPUT_LINE_ITEM));
    }

    @Test
    @Transactional
    void getNonExistingTaskInput() throws Exception {
        // Get the taskInput
        restTaskInputMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTaskInput() throws Exception {
        // Initialize the database
        taskInputRepository.saveAndFlush(taskInput);

        int databaseSizeBeforeUpdate = taskInputRepository.findAll().size();

        // Update the taskInput
        TaskInput updatedTaskInput = taskInputRepository.findById(taskInput.getId()).get();
        // Disconnect from session so that the updates on updatedTaskInput are not directly saved in db
        em.detach(updatedTaskInput);
        updatedTaskInput
            .inputInclude(UPDATED_INPUT_INCLUDE)
            .inputExclude(UPDATED_INPUT_EXCLUDE)
            .inputIncludeMessage(UPDATED_INPUT_INCLUDE_MESSAGE)
            .inputExcludeMessage(UPDATED_INPUT_EXCLUDE_MESSAGE)
            .inputCount(UPDATED_INPUT_COUNT)
            .inputStart(UPDATED_INPUT_START)
            .inputEnd(UPDATED_INPUT_END)
            .inputLineItem(UPDATED_INPUT_LINE_ITEM);

        restTaskInputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTaskInput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTaskInput))
            )
            .andExpect(status().isOk());

        // Validate the TaskInput in the database
        List<TaskInput> taskInputList = taskInputRepository.findAll();
        assertThat(taskInputList).hasSize(databaseSizeBeforeUpdate);
        TaskInput testTaskInput = taskInputList.get(taskInputList.size() - 1);
        assertThat(testTaskInput.getInputInclude()).isEqualTo(UPDATED_INPUT_INCLUDE);
        assertThat(testTaskInput.getInputExclude()).isEqualTo(UPDATED_INPUT_EXCLUDE);
        assertThat(testTaskInput.getInputIncludeMessage()).isEqualTo(UPDATED_INPUT_INCLUDE_MESSAGE);
        assertThat(testTaskInput.getInputExcludeMessage()).isEqualTo(UPDATED_INPUT_EXCLUDE_MESSAGE);
        assertThat(testTaskInput.getInputCount()).isEqualTo(UPDATED_INPUT_COUNT);
        assertThat(testTaskInput.getInputStart()).isEqualTo(UPDATED_INPUT_START);
        assertThat(testTaskInput.getInputEnd()).isEqualTo(UPDATED_INPUT_END);
        assertThat(testTaskInput.getInputLineItem()).isEqualTo(UPDATED_INPUT_LINE_ITEM);
    }

    @Test
    @Transactional
    void putNonExistingTaskInput() throws Exception {
        int databaseSizeBeforeUpdate = taskInputRepository.findAll().size();
        taskInput.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskInputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, taskInput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskInput))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskInput in the database
        List<TaskInput> taskInputList = taskInputRepository.findAll();
        assertThat(taskInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaskInput() throws Exception {
        int databaseSizeBeforeUpdate = taskInputRepository.findAll().size();
        taskInput.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskInputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(taskInput))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskInput in the database
        List<TaskInput> taskInputList = taskInputRepository.findAll();
        assertThat(taskInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaskInput() throws Exception {
        int databaseSizeBeforeUpdate = taskInputRepository.findAll().size();
        taskInput.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskInputMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(taskInput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskInput in the database
        List<TaskInput> taskInputList = taskInputRepository.findAll();
        assertThat(taskInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTaskInputWithPatch() throws Exception {
        // Initialize the database
        taskInputRepository.saveAndFlush(taskInput);

        int databaseSizeBeforeUpdate = taskInputRepository.findAll().size();

        // Update the taskInput using partial update
        TaskInput partialUpdatedTaskInput = new TaskInput();
        partialUpdatedTaskInput.setId(taskInput.getId());

        partialUpdatedTaskInput
            .inputExclude(UPDATED_INPUT_EXCLUDE)
            .inputExcludeMessage(UPDATED_INPUT_EXCLUDE_MESSAGE)
            .inputCount(UPDATED_INPUT_COUNT)
            .inputEnd(UPDATED_INPUT_END)
            .inputLineItem(UPDATED_INPUT_LINE_ITEM);

        restTaskInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskInput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskInput))
            )
            .andExpect(status().isOk());

        // Validate the TaskInput in the database
        List<TaskInput> taskInputList = taskInputRepository.findAll();
        assertThat(taskInputList).hasSize(databaseSizeBeforeUpdate);
        TaskInput testTaskInput = taskInputList.get(taskInputList.size() - 1);
        assertThat(testTaskInput.getInputInclude()).isEqualTo(DEFAULT_INPUT_INCLUDE);
        assertThat(testTaskInput.getInputExclude()).isEqualTo(UPDATED_INPUT_EXCLUDE);
        assertThat(testTaskInput.getInputIncludeMessage()).isEqualTo(DEFAULT_INPUT_INCLUDE_MESSAGE);
        assertThat(testTaskInput.getInputExcludeMessage()).isEqualTo(UPDATED_INPUT_EXCLUDE_MESSAGE);
        assertThat(testTaskInput.getInputCount()).isEqualTo(UPDATED_INPUT_COUNT);
        assertThat(testTaskInput.getInputStart()).isEqualTo(DEFAULT_INPUT_START);
        assertThat(testTaskInput.getInputEnd()).isEqualTo(UPDATED_INPUT_END);
        assertThat(testTaskInput.getInputLineItem()).isEqualTo(UPDATED_INPUT_LINE_ITEM);
    }

    @Test
    @Transactional
    void fullUpdateTaskInputWithPatch() throws Exception {
        // Initialize the database
        taskInputRepository.saveAndFlush(taskInput);

        int databaseSizeBeforeUpdate = taskInputRepository.findAll().size();

        // Update the taskInput using partial update
        TaskInput partialUpdatedTaskInput = new TaskInput();
        partialUpdatedTaskInput.setId(taskInput.getId());

        partialUpdatedTaskInput
            .inputInclude(UPDATED_INPUT_INCLUDE)
            .inputExclude(UPDATED_INPUT_EXCLUDE)
            .inputIncludeMessage(UPDATED_INPUT_INCLUDE_MESSAGE)
            .inputExcludeMessage(UPDATED_INPUT_EXCLUDE_MESSAGE)
            .inputCount(UPDATED_INPUT_COUNT)
            .inputStart(UPDATED_INPUT_START)
            .inputEnd(UPDATED_INPUT_END)
            .inputLineItem(UPDATED_INPUT_LINE_ITEM);

        restTaskInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaskInput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaskInput))
            )
            .andExpect(status().isOk());

        // Validate the TaskInput in the database
        List<TaskInput> taskInputList = taskInputRepository.findAll();
        assertThat(taskInputList).hasSize(databaseSizeBeforeUpdate);
        TaskInput testTaskInput = taskInputList.get(taskInputList.size() - 1);
        assertThat(testTaskInput.getInputInclude()).isEqualTo(UPDATED_INPUT_INCLUDE);
        assertThat(testTaskInput.getInputExclude()).isEqualTo(UPDATED_INPUT_EXCLUDE);
        assertThat(testTaskInput.getInputIncludeMessage()).isEqualTo(UPDATED_INPUT_INCLUDE_MESSAGE);
        assertThat(testTaskInput.getInputExcludeMessage()).isEqualTo(UPDATED_INPUT_EXCLUDE_MESSAGE);
        assertThat(testTaskInput.getInputCount()).isEqualTo(UPDATED_INPUT_COUNT);
        assertThat(testTaskInput.getInputStart()).isEqualTo(UPDATED_INPUT_START);
        assertThat(testTaskInput.getInputEnd()).isEqualTo(UPDATED_INPUT_END);
        assertThat(testTaskInput.getInputLineItem()).isEqualTo(UPDATED_INPUT_LINE_ITEM);
    }

    @Test
    @Transactional
    void patchNonExistingTaskInput() throws Exception {
        int databaseSizeBeforeUpdate = taskInputRepository.findAll().size();
        taskInput.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, taskInput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskInput))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskInput in the database
        List<TaskInput> taskInputList = taskInputRepository.findAll();
        assertThat(taskInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaskInput() throws Exception {
        int databaseSizeBeforeUpdate = taskInputRepository.findAll().size();
        taskInput.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(taskInput))
            )
            .andExpect(status().isBadRequest());

        // Validate the TaskInput in the database
        List<TaskInput> taskInputList = taskInputRepository.findAll();
        assertThat(taskInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaskInput() throws Exception {
        int databaseSizeBeforeUpdate = taskInputRepository.findAll().size();
        taskInput.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTaskInputMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(taskInput))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TaskInput in the database
        List<TaskInput> taskInputList = taskInputRepository.findAll();
        assertThat(taskInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaskInput() throws Exception {
        // Initialize the database
        taskInputRepository.saveAndFlush(taskInput);

        int databaseSizeBeforeDelete = taskInputRepository.findAll().size();

        // Delete the taskInput
        restTaskInputMockMvc
            .perform(delete(ENTITY_API_URL_ID, taskInput.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskInput> taskInputList = taskInputRepository.findAll();
        assertThat(taskInputList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
