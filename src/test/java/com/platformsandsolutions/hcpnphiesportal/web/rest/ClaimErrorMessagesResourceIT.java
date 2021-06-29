package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ClaimErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.ClaimErrorMessagesRepository;
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
 * Integration tests for the {@link ClaimErrorMessagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimErrorMessagesResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/claim-error-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimErrorMessagesRepository claimErrorMessagesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimErrorMessagesMockMvc;

    private ClaimErrorMessages claimErrorMessages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimErrorMessages createEntity(EntityManager em) {
        ClaimErrorMessages claimErrorMessages = new ClaimErrorMessages().message(DEFAULT_MESSAGE);
        return claimErrorMessages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimErrorMessages createUpdatedEntity(EntityManager em) {
        ClaimErrorMessages claimErrorMessages = new ClaimErrorMessages().message(UPDATED_MESSAGE);
        return claimErrorMessages;
    }

    @BeforeEach
    public void initTest() {
        claimErrorMessages = createEntity(em);
    }

    @Test
    @Transactional
    void createClaimErrorMessages() throws Exception {
        int databaseSizeBeforeCreate = claimErrorMessagesRepository.findAll().size();
        // Create the ClaimErrorMessages
        restClaimErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claimErrorMessages))
            )
            .andExpect(status().isCreated());

        // Validate the ClaimErrorMessages in the database
        List<ClaimErrorMessages> claimErrorMessagesList = claimErrorMessagesRepository.findAll();
        assertThat(claimErrorMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        ClaimErrorMessages testClaimErrorMessages = claimErrorMessagesList.get(claimErrorMessagesList.size() - 1);
        assertThat(testClaimErrorMessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void createClaimErrorMessagesWithExistingId() throws Exception {
        // Create the ClaimErrorMessages with an existing ID
        claimErrorMessages.setId(1L);

        int databaseSizeBeforeCreate = claimErrorMessagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claimErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimErrorMessages in the database
        List<ClaimErrorMessages> claimErrorMessagesList = claimErrorMessagesRepository.findAll();
        assertThat(claimErrorMessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClaimErrorMessages() throws Exception {
        // Initialize the database
        claimErrorMessagesRepository.saveAndFlush(claimErrorMessages);

        // Get all the claimErrorMessagesList
        restClaimErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimErrorMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    void getClaimErrorMessages() throws Exception {
        // Initialize the database
        claimErrorMessagesRepository.saveAndFlush(claimErrorMessages);

        // Get the claimErrorMessages
        restClaimErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL_ID, claimErrorMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claimErrorMessages.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingClaimErrorMessages() throws Exception {
        // Get the claimErrorMessages
        restClaimErrorMessagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClaimErrorMessages() throws Exception {
        // Initialize the database
        claimErrorMessagesRepository.saveAndFlush(claimErrorMessages);

        int databaseSizeBeforeUpdate = claimErrorMessagesRepository.findAll().size();

        // Update the claimErrorMessages
        ClaimErrorMessages updatedClaimErrorMessages = claimErrorMessagesRepository.findById(claimErrorMessages.getId()).get();
        // Disconnect from session so that the updates on updatedClaimErrorMessages are not directly saved in db
        em.detach(updatedClaimErrorMessages);
        updatedClaimErrorMessages.message(UPDATED_MESSAGE);

        restClaimErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClaimErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClaimErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the ClaimErrorMessages in the database
        List<ClaimErrorMessages> claimErrorMessagesList = claimErrorMessagesRepository.findAll();
        assertThat(claimErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        ClaimErrorMessages testClaimErrorMessages = claimErrorMessagesList.get(claimErrorMessagesList.size() - 1);
        assertThat(testClaimErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingClaimErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = claimErrorMessagesRepository.findAll().size();
        claimErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claimErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimErrorMessages in the database
        List<ClaimErrorMessages> claimErrorMessagesList = claimErrorMessagesRepository.findAll();
        assertThat(claimErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaimErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = claimErrorMessagesRepository.findAll().size();
        claimErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimErrorMessages in the database
        List<ClaimErrorMessages> claimErrorMessagesList = claimErrorMessagesRepository.findAll();
        assertThat(claimErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaimErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = claimErrorMessagesRepository.findAll().size();
        claimErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claimErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimErrorMessages in the database
        List<ClaimErrorMessages> claimErrorMessagesList = claimErrorMessagesRepository.findAll();
        assertThat(claimErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        claimErrorMessagesRepository.saveAndFlush(claimErrorMessages);

        int databaseSizeBeforeUpdate = claimErrorMessagesRepository.findAll().size();

        // Update the claimErrorMessages using partial update
        ClaimErrorMessages partialUpdatedClaimErrorMessages = new ClaimErrorMessages();
        partialUpdatedClaimErrorMessages.setId(claimErrorMessages.getId());

        restClaimErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the ClaimErrorMessages in the database
        List<ClaimErrorMessages> claimErrorMessagesList = claimErrorMessagesRepository.findAll();
        assertThat(claimErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        ClaimErrorMessages testClaimErrorMessages = claimErrorMessagesList.get(claimErrorMessagesList.size() - 1);
        assertThat(testClaimErrorMessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateClaimErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        claimErrorMessagesRepository.saveAndFlush(claimErrorMessages);

        int databaseSizeBeforeUpdate = claimErrorMessagesRepository.findAll().size();

        // Update the claimErrorMessages using partial update
        ClaimErrorMessages partialUpdatedClaimErrorMessages = new ClaimErrorMessages();
        partialUpdatedClaimErrorMessages.setId(claimErrorMessages.getId());

        partialUpdatedClaimErrorMessages.message(UPDATED_MESSAGE);

        restClaimErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the ClaimErrorMessages in the database
        List<ClaimErrorMessages> claimErrorMessagesList = claimErrorMessagesRepository.findAll();
        assertThat(claimErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        ClaimErrorMessages testClaimErrorMessages = claimErrorMessagesList.get(claimErrorMessagesList.size() - 1);
        assertThat(testClaimErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingClaimErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = claimErrorMessagesRepository.findAll().size();
        claimErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claimErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimErrorMessages in the database
        List<ClaimErrorMessages> claimErrorMessagesList = claimErrorMessagesRepository.findAll();
        assertThat(claimErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaimErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = claimErrorMessagesRepository.findAll().size();
        claimErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimErrorMessages in the database
        List<ClaimErrorMessages> claimErrorMessagesList = claimErrorMessagesRepository.findAll();
        assertThat(claimErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaimErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = claimErrorMessagesRepository.findAll().size();
        claimErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimErrorMessages in the database
        List<ClaimErrorMessages> claimErrorMessagesList = claimErrorMessagesRepository.findAll();
        assertThat(claimErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaimErrorMessages() throws Exception {
        // Initialize the database
        claimErrorMessagesRepository.saveAndFlush(claimErrorMessages);

        int databaseSizeBeforeDelete = claimErrorMessagesRepository.findAll().size();

        // Delete the claimErrorMessages
        restClaimErrorMessagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, claimErrorMessages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClaimErrorMessages> claimErrorMessagesList = claimErrorMessagesRepository.findAll();
        assertThat(claimErrorMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
