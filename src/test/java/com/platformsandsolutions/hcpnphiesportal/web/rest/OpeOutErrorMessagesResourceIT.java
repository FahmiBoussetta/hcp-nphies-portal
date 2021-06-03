package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.OpeOutErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.OpeOutErrorMessagesRepository;
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
 * Integration tests for the {@link OpeOutErrorMessagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpeOutErrorMessagesResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ope-out-error-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OpeOutErrorMessagesRepository opeOutErrorMessagesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpeOutErrorMessagesMockMvc;

    private OpeOutErrorMessages opeOutErrorMessages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpeOutErrorMessages createEntity(EntityManager em) {
        OpeOutErrorMessages opeOutErrorMessages = new OpeOutErrorMessages().message(DEFAULT_MESSAGE);
        return opeOutErrorMessages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpeOutErrorMessages createUpdatedEntity(EntityManager em) {
        OpeOutErrorMessages opeOutErrorMessages = new OpeOutErrorMessages().message(UPDATED_MESSAGE);
        return opeOutErrorMessages;
    }

    @BeforeEach
    public void initTest() {
        opeOutErrorMessages = createEntity(em);
    }

    @Test
    @Transactional
    void createOpeOutErrorMessages() throws Exception {
        int databaseSizeBeforeCreate = opeOutErrorMessagesRepository.findAll().size();
        // Create the OpeOutErrorMessages
        restOpeOutErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opeOutErrorMessages))
            )
            .andExpect(status().isCreated());

        // Validate the OpeOutErrorMessages in the database
        List<OpeOutErrorMessages> opeOutErrorMessagesList = opeOutErrorMessagesRepository.findAll();
        assertThat(opeOutErrorMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        OpeOutErrorMessages testOpeOutErrorMessages = opeOutErrorMessagesList.get(opeOutErrorMessagesList.size() - 1);
        assertThat(testOpeOutErrorMessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void createOpeOutErrorMessagesWithExistingId() throws Exception {
        // Create the OpeOutErrorMessages with an existing ID
        opeOutErrorMessages.setId(1L);

        int databaseSizeBeforeCreate = opeOutErrorMessagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpeOutErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opeOutErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpeOutErrorMessages in the database
        List<OpeOutErrorMessages> opeOutErrorMessagesList = opeOutErrorMessagesRepository.findAll();
        assertThat(opeOutErrorMessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpeOutErrorMessages() throws Exception {
        // Initialize the database
        opeOutErrorMessagesRepository.saveAndFlush(opeOutErrorMessages);

        // Get all the opeOutErrorMessagesList
        restOpeOutErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opeOutErrorMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    void getOpeOutErrorMessages() throws Exception {
        // Initialize the database
        opeOutErrorMessagesRepository.saveAndFlush(opeOutErrorMessages);

        // Get the opeOutErrorMessages
        restOpeOutErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL_ID, opeOutErrorMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opeOutErrorMessages.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingOpeOutErrorMessages() throws Exception {
        // Get the opeOutErrorMessages
        restOpeOutErrorMessagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOpeOutErrorMessages() throws Exception {
        // Initialize the database
        opeOutErrorMessagesRepository.saveAndFlush(opeOutErrorMessages);

        int databaseSizeBeforeUpdate = opeOutErrorMessagesRepository.findAll().size();

        // Update the opeOutErrorMessages
        OpeOutErrorMessages updatedOpeOutErrorMessages = opeOutErrorMessagesRepository.findById(opeOutErrorMessages.getId()).get();
        // Disconnect from session so that the updates on updatedOpeOutErrorMessages are not directly saved in db
        em.detach(updatedOpeOutErrorMessages);
        updatedOpeOutErrorMessages.message(UPDATED_MESSAGE);

        restOpeOutErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOpeOutErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedOpeOutErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the OpeOutErrorMessages in the database
        List<OpeOutErrorMessages> opeOutErrorMessagesList = opeOutErrorMessagesRepository.findAll();
        assertThat(opeOutErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        OpeOutErrorMessages testOpeOutErrorMessages = opeOutErrorMessagesList.get(opeOutErrorMessagesList.size() - 1);
        assertThat(testOpeOutErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingOpeOutErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = opeOutErrorMessagesRepository.findAll().size();
        opeOutErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpeOutErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opeOutErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opeOutErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpeOutErrorMessages in the database
        List<OpeOutErrorMessages> opeOutErrorMessagesList = opeOutErrorMessagesRepository.findAll();
        assertThat(opeOutErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpeOutErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = opeOutErrorMessagesRepository.findAll().size();
        opeOutErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpeOutErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opeOutErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpeOutErrorMessages in the database
        List<OpeOutErrorMessages> opeOutErrorMessagesList = opeOutErrorMessagesRepository.findAll();
        assertThat(opeOutErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpeOutErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = opeOutErrorMessagesRepository.findAll().size();
        opeOutErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpeOutErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opeOutErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpeOutErrorMessages in the database
        List<OpeOutErrorMessages> opeOutErrorMessagesList = opeOutErrorMessagesRepository.findAll();
        assertThat(opeOutErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpeOutErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        opeOutErrorMessagesRepository.saveAndFlush(opeOutErrorMessages);

        int databaseSizeBeforeUpdate = opeOutErrorMessagesRepository.findAll().size();

        // Update the opeOutErrorMessages using partial update
        OpeOutErrorMessages partialUpdatedOpeOutErrorMessages = new OpeOutErrorMessages();
        partialUpdatedOpeOutErrorMessages.setId(opeOutErrorMessages.getId());

        restOpeOutErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpeOutErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpeOutErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the OpeOutErrorMessages in the database
        List<OpeOutErrorMessages> opeOutErrorMessagesList = opeOutErrorMessagesRepository.findAll();
        assertThat(opeOutErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        OpeOutErrorMessages testOpeOutErrorMessages = opeOutErrorMessagesList.get(opeOutErrorMessagesList.size() - 1);
        assertThat(testOpeOutErrorMessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateOpeOutErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        opeOutErrorMessagesRepository.saveAndFlush(opeOutErrorMessages);

        int databaseSizeBeforeUpdate = opeOutErrorMessagesRepository.findAll().size();

        // Update the opeOutErrorMessages using partial update
        OpeOutErrorMessages partialUpdatedOpeOutErrorMessages = new OpeOutErrorMessages();
        partialUpdatedOpeOutErrorMessages.setId(opeOutErrorMessages.getId());

        partialUpdatedOpeOutErrorMessages.message(UPDATED_MESSAGE);

        restOpeOutErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpeOutErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpeOutErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the OpeOutErrorMessages in the database
        List<OpeOutErrorMessages> opeOutErrorMessagesList = opeOutErrorMessagesRepository.findAll();
        assertThat(opeOutErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        OpeOutErrorMessages testOpeOutErrorMessages = opeOutErrorMessagesList.get(opeOutErrorMessagesList.size() - 1);
        assertThat(testOpeOutErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingOpeOutErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = opeOutErrorMessagesRepository.findAll().size();
        opeOutErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpeOutErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opeOutErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opeOutErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpeOutErrorMessages in the database
        List<OpeOutErrorMessages> opeOutErrorMessagesList = opeOutErrorMessagesRepository.findAll();
        assertThat(opeOutErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpeOutErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = opeOutErrorMessagesRepository.findAll().size();
        opeOutErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpeOutErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opeOutErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the OpeOutErrorMessages in the database
        List<OpeOutErrorMessages> opeOutErrorMessagesList = opeOutErrorMessagesRepository.findAll();
        assertThat(opeOutErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpeOutErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = opeOutErrorMessagesRepository.findAll().size();
        opeOutErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpeOutErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opeOutErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OpeOutErrorMessages in the database
        List<OpeOutErrorMessages> opeOutErrorMessagesList = opeOutErrorMessagesRepository.findAll();
        assertThat(opeOutErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpeOutErrorMessages() throws Exception {
        // Initialize the database
        opeOutErrorMessagesRepository.saveAndFlush(opeOutErrorMessages);

        int databaseSizeBeforeDelete = opeOutErrorMessagesRepository.findAll().size();

        // Delete the opeOutErrorMessages
        restOpeOutErrorMessagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, opeOutErrorMessages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OpeOutErrorMessages> opeOutErrorMessagesList = opeOutErrorMessagesRepository.findAll();
        assertThat(opeOutErrorMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
