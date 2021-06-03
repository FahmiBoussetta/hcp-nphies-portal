package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ComErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.ComErrorMessagesRepository;
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
 * Integration tests for the {@link ComErrorMessagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComErrorMessagesResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/com-error-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComErrorMessagesRepository comErrorMessagesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComErrorMessagesMockMvc;

    private ComErrorMessages comErrorMessages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComErrorMessages createEntity(EntityManager em) {
        ComErrorMessages comErrorMessages = new ComErrorMessages().message(DEFAULT_MESSAGE);
        return comErrorMessages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComErrorMessages createUpdatedEntity(EntityManager em) {
        ComErrorMessages comErrorMessages = new ComErrorMessages().message(UPDATED_MESSAGE);
        return comErrorMessages;
    }

    @BeforeEach
    public void initTest() {
        comErrorMessages = createEntity(em);
    }

    @Test
    @Transactional
    void createComErrorMessages() throws Exception {
        int databaseSizeBeforeCreate = comErrorMessagesRepository.findAll().size();
        // Create the ComErrorMessages
        restComErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comErrorMessages))
            )
            .andExpect(status().isCreated());

        // Validate the ComErrorMessages in the database
        List<ComErrorMessages> comErrorMessagesList = comErrorMessagesRepository.findAll();
        assertThat(comErrorMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        ComErrorMessages testComErrorMessages = comErrorMessagesList.get(comErrorMessagesList.size() - 1);
        assertThat(testComErrorMessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void createComErrorMessagesWithExistingId() throws Exception {
        // Create the ComErrorMessages with an existing ID
        comErrorMessages.setId(1L);

        int databaseSizeBeforeCreate = comErrorMessagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComErrorMessages in the database
        List<ComErrorMessages> comErrorMessagesList = comErrorMessagesRepository.findAll();
        assertThat(comErrorMessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllComErrorMessages() throws Exception {
        // Initialize the database
        comErrorMessagesRepository.saveAndFlush(comErrorMessages);

        // Get all the comErrorMessagesList
        restComErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comErrorMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    void getComErrorMessages() throws Exception {
        // Initialize the database
        comErrorMessagesRepository.saveAndFlush(comErrorMessages);

        // Get the comErrorMessages
        restComErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL_ID, comErrorMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comErrorMessages.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingComErrorMessages() throws Exception {
        // Get the comErrorMessages
        restComErrorMessagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComErrorMessages() throws Exception {
        // Initialize the database
        comErrorMessagesRepository.saveAndFlush(comErrorMessages);

        int databaseSizeBeforeUpdate = comErrorMessagesRepository.findAll().size();

        // Update the comErrorMessages
        ComErrorMessages updatedComErrorMessages = comErrorMessagesRepository.findById(comErrorMessages.getId()).get();
        // Disconnect from session so that the updates on updatedComErrorMessages are not directly saved in db
        em.detach(updatedComErrorMessages);
        updatedComErrorMessages.message(UPDATED_MESSAGE);

        restComErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedComErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedComErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the ComErrorMessages in the database
        List<ComErrorMessages> comErrorMessagesList = comErrorMessagesRepository.findAll();
        assertThat(comErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        ComErrorMessages testComErrorMessages = comErrorMessagesList.get(comErrorMessagesList.size() - 1);
        assertThat(testComErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingComErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = comErrorMessagesRepository.findAll().size();
        comErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComErrorMessages in the database
        List<ComErrorMessages> comErrorMessagesList = comErrorMessagesRepository.findAll();
        assertThat(comErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = comErrorMessagesRepository.findAll().size();
        comErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComErrorMessages in the database
        List<ComErrorMessages> comErrorMessagesList = comErrorMessagesRepository.findAll();
        assertThat(comErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = comErrorMessagesRepository.findAll().size();
        comErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComErrorMessages in the database
        List<ComErrorMessages> comErrorMessagesList = comErrorMessagesRepository.findAll();
        assertThat(comErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        comErrorMessagesRepository.saveAndFlush(comErrorMessages);

        int databaseSizeBeforeUpdate = comErrorMessagesRepository.findAll().size();

        // Update the comErrorMessages using partial update
        ComErrorMessages partialUpdatedComErrorMessages = new ComErrorMessages();
        partialUpdatedComErrorMessages.setId(comErrorMessages.getId());

        partialUpdatedComErrorMessages.message(UPDATED_MESSAGE);

        restComErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the ComErrorMessages in the database
        List<ComErrorMessages> comErrorMessagesList = comErrorMessagesRepository.findAll();
        assertThat(comErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        ComErrorMessages testComErrorMessages = comErrorMessagesList.get(comErrorMessagesList.size() - 1);
        assertThat(testComErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdateComErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        comErrorMessagesRepository.saveAndFlush(comErrorMessages);

        int databaseSizeBeforeUpdate = comErrorMessagesRepository.findAll().size();

        // Update the comErrorMessages using partial update
        ComErrorMessages partialUpdatedComErrorMessages = new ComErrorMessages();
        partialUpdatedComErrorMessages.setId(comErrorMessages.getId());

        partialUpdatedComErrorMessages.message(UPDATED_MESSAGE);

        restComErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the ComErrorMessages in the database
        List<ComErrorMessages> comErrorMessagesList = comErrorMessagesRepository.findAll();
        assertThat(comErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        ComErrorMessages testComErrorMessages = comErrorMessagesList.get(comErrorMessagesList.size() - 1);
        assertThat(testComErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingComErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = comErrorMessagesRepository.findAll().size();
        comErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, comErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComErrorMessages in the database
        List<ComErrorMessages> comErrorMessagesList = comErrorMessagesRepository.findAll();
        assertThat(comErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = comErrorMessagesRepository.findAll().size();
        comErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComErrorMessages in the database
        List<ComErrorMessages> comErrorMessagesList = comErrorMessagesRepository.findAll();
        assertThat(comErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = comErrorMessagesRepository.findAll().size();
        comErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComErrorMessages in the database
        List<ComErrorMessages> comErrorMessagesList = comErrorMessagesRepository.findAll();
        assertThat(comErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComErrorMessages() throws Exception {
        // Initialize the database
        comErrorMessagesRepository.saveAndFlush(comErrorMessages);

        int databaseSizeBeforeDelete = comErrorMessagesRepository.findAll().size();

        // Delete the comErrorMessages
        restComErrorMessagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, comErrorMessages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComErrorMessages> comErrorMessagesList = comErrorMessagesRepository.findAll();
        assertThat(comErrorMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
