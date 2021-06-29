package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.AckErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.AckErrorMessagesRepository;
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
 * Integration tests for the {@link AckErrorMessagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AckErrorMessagesResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ack-error-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AckErrorMessagesRepository ackErrorMessagesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAckErrorMessagesMockMvc;

    private AckErrorMessages ackErrorMessages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AckErrorMessages createEntity(EntityManager em) {
        AckErrorMessages ackErrorMessages = new AckErrorMessages().message(DEFAULT_MESSAGE);
        return ackErrorMessages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AckErrorMessages createUpdatedEntity(EntityManager em) {
        AckErrorMessages ackErrorMessages = new AckErrorMessages().message(UPDATED_MESSAGE);
        return ackErrorMessages;
    }

    @BeforeEach
    public void initTest() {
        ackErrorMessages = createEntity(em);
    }

    @Test
    @Transactional
    void createAckErrorMessages() throws Exception {
        int databaseSizeBeforeCreate = ackErrorMessagesRepository.findAll().size();
        // Create the AckErrorMessages
        restAckErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ackErrorMessages))
            )
            .andExpect(status().isCreated());

        // Validate the AckErrorMessages in the database
        List<AckErrorMessages> ackErrorMessagesList = ackErrorMessagesRepository.findAll();
        assertThat(ackErrorMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        AckErrorMessages testAckErrorMessages = ackErrorMessagesList.get(ackErrorMessagesList.size() - 1);
        assertThat(testAckErrorMessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void createAckErrorMessagesWithExistingId() throws Exception {
        // Create the AckErrorMessages with an existing ID
        ackErrorMessages.setId(1L);

        int databaseSizeBeforeCreate = ackErrorMessagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAckErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ackErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the AckErrorMessages in the database
        List<AckErrorMessages> ackErrorMessagesList = ackErrorMessagesRepository.findAll();
        assertThat(ackErrorMessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAckErrorMessages() throws Exception {
        // Initialize the database
        ackErrorMessagesRepository.saveAndFlush(ackErrorMessages);

        // Get all the ackErrorMessagesList
        restAckErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ackErrorMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    void getAckErrorMessages() throws Exception {
        // Initialize the database
        ackErrorMessagesRepository.saveAndFlush(ackErrorMessages);

        // Get the ackErrorMessages
        restAckErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL_ID, ackErrorMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ackErrorMessages.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingAckErrorMessages() throws Exception {
        // Get the ackErrorMessages
        restAckErrorMessagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAckErrorMessages() throws Exception {
        // Initialize the database
        ackErrorMessagesRepository.saveAndFlush(ackErrorMessages);

        int databaseSizeBeforeUpdate = ackErrorMessagesRepository.findAll().size();

        // Update the ackErrorMessages
        AckErrorMessages updatedAckErrorMessages = ackErrorMessagesRepository.findById(ackErrorMessages.getId()).get();
        // Disconnect from session so that the updates on updatedAckErrorMessages are not directly saved in db
        em.detach(updatedAckErrorMessages);
        updatedAckErrorMessages.message(UPDATED_MESSAGE);

        restAckErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAckErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAckErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the AckErrorMessages in the database
        List<AckErrorMessages> ackErrorMessagesList = ackErrorMessagesRepository.findAll();
        assertThat(ackErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        AckErrorMessages testAckErrorMessages = ackErrorMessagesList.get(ackErrorMessagesList.size() - 1);
        assertThat(testAckErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingAckErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = ackErrorMessagesRepository.findAll().size();
        ackErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAckErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ackErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ackErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the AckErrorMessages in the database
        List<AckErrorMessages> ackErrorMessagesList = ackErrorMessagesRepository.findAll();
        assertThat(ackErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAckErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = ackErrorMessagesRepository.findAll().size();
        ackErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAckErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ackErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the AckErrorMessages in the database
        List<AckErrorMessages> ackErrorMessagesList = ackErrorMessagesRepository.findAll();
        assertThat(ackErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAckErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = ackErrorMessagesRepository.findAll().size();
        ackErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAckErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ackErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AckErrorMessages in the database
        List<AckErrorMessages> ackErrorMessagesList = ackErrorMessagesRepository.findAll();
        assertThat(ackErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAckErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        ackErrorMessagesRepository.saveAndFlush(ackErrorMessages);

        int databaseSizeBeforeUpdate = ackErrorMessagesRepository.findAll().size();

        // Update the ackErrorMessages using partial update
        AckErrorMessages partialUpdatedAckErrorMessages = new AckErrorMessages();
        partialUpdatedAckErrorMessages.setId(ackErrorMessages.getId());

        partialUpdatedAckErrorMessages.message(UPDATED_MESSAGE);

        restAckErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAckErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAckErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the AckErrorMessages in the database
        List<AckErrorMessages> ackErrorMessagesList = ackErrorMessagesRepository.findAll();
        assertThat(ackErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        AckErrorMessages testAckErrorMessages = ackErrorMessagesList.get(ackErrorMessagesList.size() - 1);
        assertThat(testAckErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateAckErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        ackErrorMessagesRepository.saveAndFlush(ackErrorMessages);

        int databaseSizeBeforeUpdate = ackErrorMessagesRepository.findAll().size();

        // Update the ackErrorMessages using partial update
        AckErrorMessages partialUpdatedAckErrorMessages = new AckErrorMessages();
        partialUpdatedAckErrorMessages.setId(ackErrorMessages.getId());

        partialUpdatedAckErrorMessages.message(UPDATED_MESSAGE);

        restAckErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAckErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAckErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the AckErrorMessages in the database
        List<AckErrorMessages> ackErrorMessagesList = ackErrorMessagesRepository.findAll();
        assertThat(ackErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        AckErrorMessages testAckErrorMessages = ackErrorMessagesList.get(ackErrorMessagesList.size() - 1);
        assertThat(testAckErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingAckErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = ackErrorMessagesRepository.findAll().size();
        ackErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAckErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ackErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ackErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the AckErrorMessages in the database
        List<AckErrorMessages> ackErrorMessagesList = ackErrorMessagesRepository.findAll();
        assertThat(ackErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAckErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = ackErrorMessagesRepository.findAll().size();
        ackErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAckErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ackErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the AckErrorMessages in the database
        List<AckErrorMessages> ackErrorMessagesList = ackErrorMessagesRepository.findAll();
        assertThat(ackErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAckErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = ackErrorMessagesRepository.findAll().size();
        ackErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAckErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ackErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AckErrorMessages in the database
        List<AckErrorMessages> ackErrorMessagesList = ackErrorMessagesRepository.findAll();
        assertThat(ackErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAckErrorMessages() throws Exception {
        // Initialize the database
        ackErrorMessagesRepository.saveAndFlush(ackErrorMessages);

        int databaseSizeBeforeDelete = ackErrorMessagesRepository.findAll().size();

        // Delete the ackErrorMessages
        restAckErrorMessagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, ackErrorMessages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AckErrorMessages> ackErrorMessagesList = ackErrorMessagesRepository.findAll();
        assertThat(ackErrorMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
