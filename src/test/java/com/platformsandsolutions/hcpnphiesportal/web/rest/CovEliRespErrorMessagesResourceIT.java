package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.CovEliRespErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.CovEliRespErrorMessagesRepository;
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
 * Integration tests for the {@link CovEliRespErrorMessagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CovEliRespErrorMessagesResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cov-eli-resp-error-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CovEliRespErrorMessagesRepository covEliRespErrorMessagesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCovEliRespErrorMessagesMockMvc;

    private CovEliRespErrorMessages covEliRespErrorMessages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CovEliRespErrorMessages createEntity(EntityManager em) {
        CovEliRespErrorMessages covEliRespErrorMessages = new CovEliRespErrorMessages().message(DEFAULT_MESSAGE);
        return covEliRespErrorMessages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CovEliRespErrorMessages createUpdatedEntity(EntityManager em) {
        CovEliRespErrorMessages covEliRespErrorMessages = new CovEliRespErrorMessages().message(UPDATED_MESSAGE);
        return covEliRespErrorMessages;
    }

    @BeforeEach
    public void initTest() {
        covEliRespErrorMessages = createEntity(em);
    }

    @Test
    @Transactional
    void createCovEliRespErrorMessages() throws Exception {
        int databaseSizeBeforeCreate = covEliRespErrorMessagesRepository.findAll().size();
        // Create the CovEliRespErrorMessages
        restCovEliRespErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(covEliRespErrorMessages))
            )
            .andExpect(status().isCreated());

        // Validate the CovEliRespErrorMessages in the database
        List<CovEliRespErrorMessages> covEliRespErrorMessagesList = covEliRespErrorMessagesRepository.findAll();
        assertThat(covEliRespErrorMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        CovEliRespErrorMessages testCovEliRespErrorMessages = covEliRespErrorMessagesList.get(covEliRespErrorMessagesList.size() - 1);
        assertThat(testCovEliRespErrorMessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void createCovEliRespErrorMessagesWithExistingId() throws Exception {
        // Create the CovEliRespErrorMessages with an existing ID
        covEliRespErrorMessages.setId(1L);

        int databaseSizeBeforeCreate = covEliRespErrorMessagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCovEliRespErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(covEliRespErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CovEliRespErrorMessages in the database
        List<CovEliRespErrorMessages> covEliRespErrorMessagesList = covEliRespErrorMessagesRepository.findAll();
        assertThat(covEliRespErrorMessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCovEliRespErrorMessages() throws Exception {
        // Initialize the database
        covEliRespErrorMessagesRepository.saveAndFlush(covEliRespErrorMessages);

        // Get all the covEliRespErrorMessagesList
        restCovEliRespErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(covEliRespErrorMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    void getCovEliRespErrorMessages() throws Exception {
        // Initialize the database
        covEliRespErrorMessagesRepository.saveAndFlush(covEliRespErrorMessages);

        // Get the covEliRespErrorMessages
        restCovEliRespErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL_ID, covEliRespErrorMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(covEliRespErrorMessages.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingCovEliRespErrorMessages() throws Exception {
        // Get the covEliRespErrorMessages
        restCovEliRespErrorMessagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCovEliRespErrorMessages() throws Exception {
        // Initialize the database
        covEliRespErrorMessagesRepository.saveAndFlush(covEliRespErrorMessages);

        int databaseSizeBeforeUpdate = covEliRespErrorMessagesRepository.findAll().size();

        // Update the covEliRespErrorMessages
        CovEliRespErrorMessages updatedCovEliRespErrorMessages = covEliRespErrorMessagesRepository
            .findById(covEliRespErrorMessages.getId())
            .get();
        // Disconnect from session so that the updates on updatedCovEliRespErrorMessages are not directly saved in db
        em.detach(updatedCovEliRespErrorMessages);
        updatedCovEliRespErrorMessages.message(UPDATED_MESSAGE);

        restCovEliRespErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCovEliRespErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCovEliRespErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the CovEliRespErrorMessages in the database
        List<CovEliRespErrorMessages> covEliRespErrorMessagesList = covEliRespErrorMessagesRepository.findAll();
        assertThat(covEliRespErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        CovEliRespErrorMessages testCovEliRespErrorMessages = covEliRespErrorMessagesList.get(covEliRespErrorMessagesList.size() - 1);
        assertThat(testCovEliRespErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingCovEliRespErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = covEliRespErrorMessagesRepository.findAll().size();
        covEliRespErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCovEliRespErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, covEliRespErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(covEliRespErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CovEliRespErrorMessages in the database
        List<CovEliRespErrorMessages> covEliRespErrorMessagesList = covEliRespErrorMessagesRepository.findAll();
        assertThat(covEliRespErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCovEliRespErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = covEliRespErrorMessagesRepository.findAll().size();
        covEliRespErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCovEliRespErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(covEliRespErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CovEliRespErrorMessages in the database
        List<CovEliRespErrorMessages> covEliRespErrorMessagesList = covEliRespErrorMessagesRepository.findAll();
        assertThat(covEliRespErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCovEliRespErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = covEliRespErrorMessagesRepository.findAll().size();
        covEliRespErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCovEliRespErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(covEliRespErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CovEliRespErrorMessages in the database
        List<CovEliRespErrorMessages> covEliRespErrorMessagesList = covEliRespErrorMessagesRepository.findAll();
        assertThat(covEliRespErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCovEliRespErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        covEliRespErrorMessagesRepository.saveAndFlush(covEliRespErrorMessages);

        int databaseSizeBeforeUpdate = covEliRespErrorMessagesRepository.findAll().size();

        // Update the covEliRespErrorMessages using partial update
        CovEliRespErrorMessages partialUpdatedCovEliRespErrorMessages = new CovEliRespErrorMessages();
        partialUpdatedCovEliRespErrorMessages.setId(covEliRespErrorMessages.getId());

        partialUpdatedCovEliRespErrorMessages.message(UPDATED_MESSAGE);

        restCovEliRespErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCovEliRespErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCovEliRespErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the CovEliRespErrorMessages in the database
        List<CovEliRespErrorMessages> covEliRespErrorMessagesList = covEliRespErrorMessagesRepository.findAll();
        assertThat(covEliRespErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        CovEliRespErrorMessages testCovEliRespErrorMessages = covEliRespErrorMessagesList.get(covEliRespErrorMessagesList.size() - 1);
        assertThat(testCovEliRespErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateCovEliRespErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        covEliRespErrorMessagesRepository.saveAndFlush(covEliRespErrorMessages);

        int databaseSizeBeforeUpdate = covEliRespErrorMessagesRepository.findAll().size();

        // Update the covEliRespErrorMessages using partial update
        CovEliRespErrorMessages partialUpdatedCovEliRespErrorMessages = new CovEliRespErrorMessages();
        partialUpdatedCovEliRespErrorMessages.setId(covEliRespErrorMessages.getId());

        partialUpdatedCovEliRespErrorMessages.message(UPDATED_MESSAGE);

        restCovEliRespErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCovEliRespErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCovEliRespErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the CovEliRespErrorMessages in the database
        List<CovEliRespErrorMessages> covEliRespErrorMessagesList = covEliRespErrorMessagesRepository.findAll();
        assertThat(covEliRespErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        CovEliRespErrorMessages testCovEliRespErrorMessages = covEliRespErrorMessagesList.get(covEliRespErrorMessagesList.size() - 1);
        assertThat(testCovEliRespErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingCovEliRespErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = covEliRespErrorMessagesRepository.findAll().size();
        covEliRespErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCovEliRespErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, covEliRespErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(covEliRespErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CovEliRespErrorMessages in the database
        List<CovEliRespErrorMessages> covEliRespErrorMessagesList = covEliRespErrorMessagesRepository.findAll();
        assertThat(covEliRespErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCovEliRespErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = covEliRespErrorMessagesRepository.findAll().size();
        covEliRespErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCovEliRespErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(covEliRespErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CovEliRespErrorMessages in the database
        List<CovEliRespErrorMessages> covEliRespErrorMessagesList = covEliRespErrorMessagesRepository.findAll();
        assertThat(covEliRespErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCovEliRespErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = covEliRespErrorMessagesRepository.findAll().size();
        covEliRespErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCovEliRespErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(covEliRespErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CovEliRespErrorMessages in the database
        List<CovEliRespErrorMessages> covEliRespErrorMessagesList = covEliRespErrorMessagesRepository.findAll();
        assertThat(covEliRespErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCovEliRespErrorMessages() throws Exception {
        // Initialize the database
        covEliRespErrorMessagesRepository.saveAndFlush(covEliRespErrorMessages);

        int databaseSizeBeforeDelete = covEliRespErrorMessagesRepository.findAll().size();

        // Delete the covEliRespErrorMessages
        restCovEliRespErrorMessagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, covEliRespErrorMessages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CovEliRespErrorMessages> covEliRespErrorMessagesList = covEliRespErrorMessagesRepository.findAll();
        assertThat(covEliRespErrorMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
