package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.CovEliErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.CovEliErrorMessagesRepository;
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
 * Integration tests for the {@link CovEliErrorMessagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CovEliErrorMessagesResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cov-eli-error-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CovEliErrorMessagesRepository covEliErrorMessagesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCovEliErrorMessagesMockMvc;

    private CovEliErrorMessages covEliErrorMessages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CovEliErrorMessages createEntity(EntityManager em) {
        CovEliErrorMessages covEliErrorMessages = new CovEliErrorMessages().message(DEFAULT_MESSAGE);
        return covEliErrorMessages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CovEliErrorMessages createUpdatedEntity(EntityManager em) {
        CovEliErrorMessages covEliErrorMessages = new CovEliErrorMessages().message(UPDATED_MESSAGE);
        return covEliErrorMessages;
    }

    @BeforeEach
    public void initTest() {
        covEliErrorMessages = createEntity(em);
    }

    @Test
    @Transactional
    void createCovEliErrorMessages() throws Exception {
        int databaseSizeBeforeCreate = covEliErrorMessagesRepository.findAll().size();
        // Create the CovEliErrorMessages
        restCovEliErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(covEliErrorMessages))
            )
            .andExpect(status().isCreated());

        // Validate the CovEliErrorMessages in the database
        List<CovEliErrorMessages> covEliErrorMessagesList = covEliErrorMessagesRepository.findAll();
        assertThat(covEliErrorMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        CovEliErrorMessages testCovEliErrorMessages = covEliErrorMessagesList.get(covEliErrorMessagesList.size() - 1);
        assertThat(testCovEliErrorMessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void createCovEliErrorMessagesWithExistingId() throws Exception {
        // Create the CovEliErrorMessages with an existing ID
        covEliErrorMessages.setId(1L);

        int databaseSizeBeforeCreate = covEliErrorMessagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCovEliErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(covEliErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CovEliErrorMessages in the database
        List<CovEliErrorMessages> covEliErrorMessagesList = covEliErrorMessagesRepository.findAll();
        assertThat(covEliErrorMessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCovEliErrorMessages() throws Exception {
        // Initialize the database
        covEliErrorMessagesRepository.saveAndFlush(covEliErrorMessages);

        // Get all the covEliErrorMessagesList
        restCovEliErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(covEliErrorMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    void getCovEliErrorMessages() throws Exception {
        // Initialize the database
        covEliErrorMessagesRepository.saveAndFlush(covEliErrorMessages);

        // Get the covEliErrorMessages
        restCovEliErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL_ID, covEliErrorMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(covEliErrorMessages.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingCovEliErrorMessages() throws Exception {
        // Get the covEliErrorMessages
        restCovEliErrorMessagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCovEliErrorMessages() throws Exception {
        // Initialize the database
        covEliErrorMessagesRepository.saveAndFlush(covEliErrorMessages);

        int databaseSizeBeforeUpdate = covEliErrorMessagesRepository.findAll().size();

        // Update the covEliErrorMessages
        CovEliErrorMessages updatedCovEliErrorMessages = covEliErrorMessagesRepository.findById(covEliErrorMessages.getId()).get();
        // Disconnect from session so that the updates on updatedCovEliErrorMessages are not directly saved in db
        em.detach(updatedCovEliErrorMessages);
        updatedCovEliErrorMessages.message(UPDATED_MESSAGE);

        restCovEliErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCovEliErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCovEliErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the CovEliErrorMessages in the database
        List<CovEliErrorMessages> covEliErrorMessagesList = covEliErrorMessagesRepository.findAll();
        assertThat(covEliErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        CovEliErrorMessages testCovEliErrorMessages = covEliErrorMessagesList.get(covEliErrorMessagesList.size() - 1);
        assertThat(testCovEliErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingCovEliErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = covEliErrorMessagesRepository.findAll().size();
        covEliErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCovEliErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, covEliErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(covEliErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CovEliErrorMessages in the database
        List<CovEliErrorMessages> covEliErrorMessagesList = covEliErrorMessagesRepository.findAll();
        assertThat(covEliErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCovEliErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = covEliErrorMessagesRepository.findAll().size();
        covEliErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCovEliErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(covEliErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CovEliErrorMessages in the database
        List<CovEliErrorMessages> covEliErrorMessagesList = covEliErrorMessagesRepository.findAll();
        assertThat(covEliErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCovEliErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = covEliErrorMessagesRepository.findAll().size();
        covEliErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCovEliErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(covEliErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CovEliErrorMessages in the database
        List<CovEliErrorMessages> covEliErrorMessagesList = covEliErrorMessagesRepository.findAll();
        assertThat(covEliErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCovEliErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        covEliErrorMessagesRepository.saveAndFlush(covEliErrorMessages);

        int databaseSizeBeforeUpdate = covEliErrorMessagesRepository.findAll().size();

        // Update the covEliErrorMessages using partial update
        CovEliErrorMessages partialUpdatedCovEliErrorMessages = new CovEliErrorMessages();
        partialUpdatedCovEliErrorMessages.setId(covEliErrorMessages.getId());

        partialUpdatedCovEliErrorMessages.message(UPDATED_MESSAGE);

        restCovEliErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCovEliErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCovEliErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the CovEliErrorMessages in the database
        List<CovEliErrorMessages> covEliErrorMessagesList = covEliErrorMessagesRepository.findAll();
        assertThat(covEliErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        CovEliErrorMessages testCovEliErrorMessages = covEliErrorMessagesList.get(covEliErrorMessagesList.size() - 1);
        assertThat(testCovEliErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateCovEliErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        covEliErrorMessagesRepository.saveAndFlush(covEliErrorMessages);

        int databaseSizeBeforeUpdate = covEliErrorMessagesRepository.findAll().size();

        // Update the covEliErrorMessages using partial update
        CovEliErrorMessages partialUpdatedCovEliErrorMessages = new CovEliErrorMessages();
        partialUpdatedCovEliErrorMessages.setId(covEliErrorMessages.getId());

        partialUpdatedCovEliErrorMessages.message(UPDATED_MESSAGE);

        restCovEliErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCovEliErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCovEliErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the CovEliErrorMessages in the database
        List<CovEliErrorMessages> covEliErrorMessagesList = covEliErrorMessagesRepository.findAll();
        assertThat(covEliErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        CovEliErrorMessages testCovEliErrorMessages = covEliErrorMessagesList.get(covEliErrorMessagesList.size() - 1);
        assertThat(testCovEliErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingCovEliErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = covEliErrorMessagesRepository.findAll().size();
        covEliErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCovEliErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, covEliErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(covEliErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CovEliErrorMessages in the database
        List<CovEliErrorMessages> covEliErrorMessagesList = covEliErrorMessagesRepository.findAll();
        assertThat(covEliErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCovEliErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = covEliErrorMessagesRepository.findAll().size();
        covEliErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCovEliErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(covEliErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CovEliErrorMessages in the database
        List<CovEliErrorMessages> covEliErrorMessagesList = covEliErrorMessagesRepository.findAll();
        assertThat(covEliErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCovEliErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = covEliErrorMessagesRepository.findAll().size();
        covEliErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCovEliErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(covEliErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CovEliErrorMessages in the database
        List<CovEliErrorMessages> covEliErrorMessagesList = covEliErrorMessagesRepository.findAll();
        assertThat(covEliErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCovEliErrorMessages() throws Exception {
        // Initialize the database
        covEliErrorMessagesRepository.saveAndFlush(covEliErrorMessages);

        int databaseSizeBeforeDelete = covEliErrorMessagesRepository.findAll().size();

        // Delete the covEliErrorMessages
        restCovEliErrorMessagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, covEliErrorMessages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CovEliErrorMessages> covEliErrorMessagesList = covEliErrorMessagesRepository.findAll();
        assertThat(covEliErrorMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
