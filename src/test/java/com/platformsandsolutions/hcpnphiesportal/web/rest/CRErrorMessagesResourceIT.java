package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.CRErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.CRErrorMessagesRepository;
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
 * Integration tests for the {@link CRErrorMessagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CRErrorMessagesResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cr-error-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CRErrorMessagesRepository cRErrorMessagesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCRErrorMessagesMockMvc;

    private CRErrorMessages cRErrorMessages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRErrorMessages createEntity(EntityManager em) {
        CRErrorMessages cRErrorMessages = new CRErrorMessages().message(DEFAULT_MESSAGE);
        return cRErrorMessages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRErrorMessages createUpdatedEntity(EntityManager em) {
        CRErrorMessages cRErrorMessages = new CRErrorMessages().message(UPDATED_MESSAGE);
        return cRErrorMessages;
    }

    @BeforeEach
    public void initTest() {
        cRErrorMessages = createEntity(em);
    }

    @Test
    @Transactional
    void createCRErrorMessages() throws Exception {
        int databaseSizeBeforeCreate = cRErrorMessagesRepository.findAll().size();
        // Create the CRErrorMessages
        restCRErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cRErrorMessages))
            )
            .andExpect(status().isCreated());

        // Validate the CRErrorMessages in the database
        List<CRErrorMessages> cRErrorMessagesList = cRErrorMessagesRepository.findAll();
        assertThat(cRErrorMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        CRErrorMessages testCRErrorMessages = cRErrorMessagesList.get(cRErrorMessagesList.size() - 1);
        assertThat(testCRErrorMessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void createCRErrorMessagesWithExistingId() throws Exception {
        // Create the CRErrorMessages with an existing ID
        cRErrorMessages.setId(1L);

        int databaseSizeBeforeCreate = cRErrorMessagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cRErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CRErrorMessages in the database
        List<CRErrorMessages> cRErrorMessagesList = cRErrorMessagesRepository.findAll();
        assertThat(cRErrorMessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCRErrorMessages() throws Exception {
        // Initialize the database
        cRErrorMessagesRepository.saveAndFlush(cRErrorMessages);

        // Get all the cRErrorMessagesList
        restCRErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRErrorMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    void getCRErrorMessages() throws Exception {
        // Initialize the database
        cRErrorMessagesRepository.saveAndFlush(cRErrorMessages);

        // Get the cRErrorMessages
        restCRErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL_ID, cRErrorMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cRErrorMessages.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingCRErrorMessages() throws Exception {
        // Get the cRErrorMessages
        restCRErrorMessagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCRErrorMessages() throws Exception {
        // Initialize the database
        cRErrorMessagesRepository.saveAndFlush(cRErrorMessages);

        int databaseSizeBeforeUpdate = cRErrorMessagesRepository.findAll().size();

        // Update the cRErrorMessages
        CRErrorMessages updatedCRErrorMessages = cRErrorMessagesRepository.findById(cRErrorMessages.getId()).get();
        // Disconnect from session so that the updates on updatedCRErrorMessages are not directly saved in db
        em.detach(updatedCRErrorMessages);
        updatedCRErrorMessages.message(UPDATED_MESSAGE);

        restCRErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCRErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCRErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the CRErrorMessages in the database
        List<CRErrorMessages> cRErrorMessagesList = cRErrorMessagesRepository.findAll();
        assertThat(cRErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        CRErrorMessages testCRErrorMessages = cRErrorMessagesList.get(cRErrorMessagesList.size() - 1);
        assertThat(testCRErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingCRErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = cRErrorMessagesRepository.findAll().size();
        cRErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCRErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cRErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cRErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CRErrorMessages in the database
        List<CRErrorMessages> cRErrorMessagesList = cRErrorMessagesRepository.findAll();
        assertThat(cRErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCRErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = cRErrorMessagesRepository.findAll().size();
        cRErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCRErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cRErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CRErrorMessages in the database
        List<CRErrorMessages> cRErrorMessagesList = cRErrorMessagesRepository.findAll();
        assertThat(cRErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCRErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = cRErrorMessagesRepository.findAll().size();
        cRErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCRErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cRErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CRErrorMessages in the database
        List<CRErrorMessages> cRErrorMessagesList = cRErrorMessagesRepository.findAll();
        assertThat(cRErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCRErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        cRErrorMessagesRepository.saveAndFlush(cRErrorMessages);

        int databaseSizeBeforeUpdate = cRErrorMessagesRepository.findAll().size();

        // Update the cRErrorMessages using partial update
        CRErrorMessages partialUpdatedCRErrorMessages = new CRErrorMessages();
        partialUpdatedCRErrorMessages.setId(cRErrorMessages.getId());

        partialUpdatedCRErrorMessages.message(UPDATED_MESSAGE);

        restCRErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCRErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCRErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the CRErrorMessages in the database
        List<CRErrorMessages> cRErrorMessagesList = cRErrorMessagesRepository.findAll();
        assertThat(cRErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        CRErrorMessages testCRErrorMessages = cRErrorMessagesList.get(cRErrorMessagesList.size() - 1);
        assertThat(testCRErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateCRErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        cRErrorMessagesRepository.saveAndFlush(cRErrorMessages);

        int databaseSizeBeforeUpdate = cRErrorMessagesRepository.findAll().size();

        // Update the cRErrorMessages using partial update
        CRErrorMessages partialUpdatedCRErrorMessages = new CRErrorMessages();
        partialUpdatedCRErrorMessages.setId(cRErrorMessages.getId());

        partialUpdatedCRErrorMessages.message(UPDATED_MESSAGE);

        restCRErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCRErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCRErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the CRErrorMessages in the database
        List<CRErrorMessages> cRErrorMessagesList = cRErrorMessagesRepository.findAll();
        assertThat(cRErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        CRErrorMessages testCRErrorMessages = cRErrorMessagesList.get(cRErrorMessagesList.size() - 1);
        assertThat(testCRErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingCRErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = cRErrorMessagesRepository.findAll().size();
        cRErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCRErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cRErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cRErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CRErrorMessages in the database
        List<CRErrorMessages> cRErrorMessagesList = cRErrorMessagesRepository.findAll();
        assertThat(cRErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCRErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = cRErrorMessagesRepository.findAll().size();
        cRErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCRErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cRErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the CRErrorMessages in the database
        List<CRErrorMessages> cRErrorMessagesList = cRErrorMessagesRepository.findAll();
        assertThat(cRErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCRErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = cRErrorMessagesRepository.findAll().size();
        cRErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCRErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cRErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CRErrorMessages in the database
        List<CRErrorMessages> cRErrorMessagesList = cRErrorMessagesRepository.findAll();
        assertThat(cRErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCRErrorMessages() throws Exception {
        // Initialize the database
        cRErrorMessagesRepository.saveAndFlush(cRErrorMessages);

        int databaseSizeBeforeDelete = cRErrorMessagesRepository.findAll().size();

        // Delete the cRErrorMessages
        restCRErrorMessagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, cRErrorMessages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CRErrorMessages> cRErrorMessagesList = cRErrorMessagesRepository.findAll();
        assertThat(cRErrorMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
