package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.OperationOutcome;
import com.platformsandsolutions.hcpnphiesportal.repository.OperationOutcomeRepository;
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
 * Integration tests for the {@link OperationOutcomeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OperationOutcomeResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_PARSED = "AAAAAAAAAA";
    private static final String UPDATED_PARSED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/operation-outcomes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OperationOutcomeRepository operationOutcomeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperationOutcomeMockMvc;

    private OperationOutcome operationOutcome;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationOutcome createEntity(EntityManager em) {
        OperationOutcome operationOutcome = new OperationOutcome().value(DEFAULT_VALUE).system(DEFAULT_SYSTEM).parsed(DEFAULT_PARSED);
        return operationOutcome;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationOutcome createUpdatedEntity(EntityManager em) {
        OperationOutcome operationOutcome = new OperationOutcome().value(UPDATED_VALUE).system(UPDATED_SYSTEM).parsed(UPDATED_PARSED);
        return operationOutcome;
    }

    @BeforeEach
    public void initTest() {
        operationOutcome = createEntity(em);
    }

    @Test
    @Transactional
    void createOperationOutcome() throws Exception {
        int databaseSizeBeforeCreate = operationOutcomeRepository.findAll().size();
        // Create the OperationOutcome
        restOperationOutcomeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationOutcome))
            )
            .andExpect(status().isCreated());

        // Validate the OperationOutcome in the database
        List<OperationOutcome> operationOutcomeList = operationOutcomeRepository.findAll();
        assertThat(operationOutcomeList).hasSize(databaseSizeBeforeCreate + 1);
        OperationOutcome testOperationOutcome = operationOutcomeList.get(operationOutcomeList.size() - 1);
        assertThat(testOperationOutcome.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOperationOutcome.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testOperationOutcome.getParsed()).isEqualTo(DEFAULT_PARSED);
    }

    @Test
    @Transactional
    void createOperationOutcomeWithExistingId() throws Exception {
        // Create the OperationOutcome with an existing ID
        operationOutcome.setId(1L);

        int databaseSizeBeforeCreate = operationOutcomeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationOutcomeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationOutcome))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationOutcome in the database
        List<OperationOutcome> operationOutcomeList = operationOutcomeRepository.findAll();
        assertThat(operationOutcomeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOperationOutcomes() throws Exception {
        // Initialize the database
        operationOutcomeRepository.saveAndFlush(operationOutcome);

        // Get all the operationOutcomeList
        restOperationOutcomeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationOutcome.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM)))
            .andExpect(jsonPath("$.[*].parsed").value(hasItem(DEFAULT_PARSED)));
    }

    @Test
    @Transactional
    void getOperationOutcome() throws Exception {
        // Initialize the database
        operationOutcomeRepository.saveAndFlush(operationOutcome);

        // Get the operationOutcome
        restOperationOutcomeMockMvc
            .perform(get(ENTITY_API_URL_ID, operationOutcome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operationOutcome.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM))
            .andExpect(jsonPath("$.parsed").value(DEFAULT_PARSED));
    }

    @Test
    @Transactional
    void getNonExistingOperationOutcome() throws Exception {
        // Get the operationOutcome
        restOperationOutcomeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOperationOutcome() throws Exception {
        // Initialize the database
        operationOutcomeRepository.saveAndFlush(operationOutcome);

        int databaseSizeBeforeUpdate = operationOutcomeRepository.findAll().size();

        // Update the operationOutcome
        OperationOutcome updatedOperationOutcome = operationOutcomeRepository.findById(operationOutcome.getId()).get();
        // Disconnect from session so that the updates on updatedOperationOutcome are not directly saved in db
        em.detach(updatedOperationOutcome);
        updatedOperationOutcome.value(UPDATED_VALUE).system(UPDATED_SYSTEM).parsed(UPDATED_PARSED);

        restOperationOutcomeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOperationOutcome.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOperationOutcome))
            )
            .andExpect(status().isOk());

        // Validate the OperationOutcome in the database
        List<OperationOutcome> operationOutcomeList = operationOutcomeRepository.findAll();
        assertThat(operationOutcomeList).hasSize(databaseSizeBeforeUpdate);
        OperationOutcome testOperationOutcome = operationOutcomeList.get(operationOutcomeList.size() - 1);
        assertThat(testOperationOutcome.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOperationOutcome.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testOperationOutcome.getParsed()).isEqualTo(UPDATED_PARSED);
    }

    @Test
    @Transactional
    void putNonExistingOperationOutcome() throws Exception {
        int databaseSizeBeforeUpdate = operationOutcomeRepository.findAll().size();
        operationOutcome.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationOutcomeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, operationOutcome.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationOutcome))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationOutcome in the database
        List<OperationOutcome> operationOutcomeList = operationOutcomeRepository.findAll();
        assertThat(operationOutcomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOperationOutcome() throws Exception {
        int databaseSizeBeforeUpdate = operationOutcomeRepository.findAll().size();
        operationOutcome.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationOutcomeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(operationOutcome))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationOutcome in the database
        List<OperationOutcome> operationOutcomeList = operationOutcomeRepository.findAll();
        assertThat(operationOutcomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOperationOutcome() throws Exception {
        int databaseSizeBeforeUpdate = operationOutcomeRepository.findAll().size();
        operationOutcome.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationOutcomeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(operationOutcome))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationOutcome in the database
        List<OperationOutcome> operationOutcomeList = operationOutcomeRepository.findAll();
        assertThat(operationOutcomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOperationOutcomeWithPatch() throws Exception {
        // Initialize the database
        operationOutcomeRepository.saveAndFlush(operationOutcome);

        int databaseSizeBeforeUpdate = operationOutcomeRepository.findAll().size();

        // Update the operationOutcome using partial update
        OperationOutcome partialUpdatedOperationOutcome = new OperationOutcome();
        partialUpdatedOperationOutcome.setId(operationOutcome.getId());

        partialUpdatedOperationOutcome.system(UPDATED_SYSTEM);

        restOperationOutcomeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationOutcome.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationOutcome))
            )
            .andExpect(status().isOk());

        // Validate the OperationOutcome in the database
        List<OperationOutcome> operationOutcomeList = operationOutcomeRepository.findAll();
        assertThat(operationOutcomeList).hasSize(databaseSizeBeforeUpdate);
        OperationOutcome testOperationOutcome = operationOutcomeList.get(operationOutcomeList.size() - 1);
        assertThat(testOperationOutcome.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOperationOutcome.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testOperationOutcome.getParsed()).isEqualTo(DEFAULT_PARSED);
    }

    @Test
    @Transactional
    void fullUpdateOperationOutcomeWithPatch() throws Exception {
        // Initialize the database
        operationOutcomeRepository.saveAndFlush(operationOutcome);

        int databaseSizeBeforeUpdate = operationOutcomeRepository.findAll().size();

        // Update the operationOutcome using partial update
        OperationOutcome partialUpdatedOperationOutcome = new OperationOutcome();
        partialUpdatedOperationOutcome.setId(operationOutcome.getId());

        partialUpdatedOperationOutcome.value(UPDATED_VALUE).system(UPDATED_SYSTEM).parsed(UPDATED_PARSED);

        restOperationOutcomeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOperationOutcome.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOperationOutcome))
            )
            .andExpect(status().isOk());

        // Validate the OperationOutcome in the database
        List<OperationOutcome> operationOutcomeList = operationOutcomeRepository.findAll();
        assertThat(operationOutcomeList).hasSize(databaseSizeBeforeUpdate);
        OperationOutcome testOperationOutcome = operationOutcomeList.get(operationOutcomeList.size() - 1);
        assertThat(testOperationOutcome.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOperationOutcome.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testOperationOutcome.getParsed()).isEqualTo(UPDATED_PARSED);
    }

    @Test
    @Transactional
    void patchNonExistingOperationOutcome() throws Exception {
        int databaseSizeBeforeUpdate = operationOutcomeRepository.findAll().size();
        operationOutcome.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationOutcomeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, operationOutcome.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationOutcome))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationOutcome in the database
        List<OperationOutcome> operationOutcomeList = operationOutcomeRepository.findAll();
        assertThat(operationOutcomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOperationOutcome() throws Exception {
        int databaseSizeBeforeUpdate = operationOutcomeRepository.findAll().size();
        operationOutcome.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationOutcomeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationOutcome))
            )
            .andExpect(status().isBadRequest());

        // Validate the OperationOutcome in the database
        List<OperationOutcome> operationOutcomeList = operationOutcomeRepository.findAll();
        assertThat(operationOutcomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOperationOutcome() throws Exception {
        int databaseSizeBeforeUpdate = operationOutcomeRepository.findAll().size();
        operationOutcome.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOperationOutcomeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(operationOutcome))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OperationOutcome in the database
        List<OperationOutcome> operationOutcomeList = operationOutcomeRepository.findAll();
        assertThat(operationOutcomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOperationOutcome() throws Exception {
        // Initialize the database
        operationOutcomeRepository.saveAndFlush(operationOutcome);

        int databaseSizeBeforeDelete = operationOutcomeRepository.findAll().size();

        // Delete the operationOutcome
        restOperationOutcomeMockMvc
            .perform(delete(ENTITY_API_URL_ID, operationOutcome.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OperationOutcome> operationOutcomeList = operationOutcomeRepository.findAll();
        assertThat(operationOutcomeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
