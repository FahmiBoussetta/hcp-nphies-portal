package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.PayNotErrorMessages;
import com.platformsandsolutions.hcpnphiesportal.repository.PayNotErrorMessagesRepository;
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
 * Integration tests for the {@link PayNotErrorMessagesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PayNotErrorMessagesResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pay-not-error-messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PayNotErrorMessagesRepository payNotErrorMessagesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayNotErrorMessagesMockMvc;

    private PayNotErrorMessages payNotErrorMessages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayNotErrorMessages createEntity(EntityManager em) {
        PayNotErrorMessages payNotErrorMessages = new PayNotErrorMessages().message(DEFAULT_MESSAGE);
        return payNotErrorMessages;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayNotErrorMessages createUpdatedEntity(EntityManager em) {
        PayNotErrorMessages payNotErrorMessages = new PayNotErrorMessages().message(UPDATED_MESSAGE);
        return payNotErrorMessages;
    }

    @BeforeEach
    public void initTest() {
        payNotErrorMessages = createEntity(em);
    }

    @Test
    @Transactional
    void createPayNotErrorMessages() throws Exception {
        int databaseSizeBeforeCreate = payNotErrorMessagesRepository.findAll().size();
        // Create the PayNotErrorMessages
        restPayNotErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payNotErrorMessages))
            )
            .andExpect(status().isCreated());

        // Validate the PayNotErrorMessages in the database
        List<PayNotErrorMessages> payNotErrorMessagesList = payNotErrorMessagesRepository.findAll();
        assertThat(payNotErrorMessagesList).hasSize(databaseSizeBeforeCreate + 1);
        PayNotErrorMessages testPayNotErrorMessages = payNotErrorMessagesList.get(payNotErrorMessagesList.size() - 1);
        assertThat(testPayNotErrorMessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    void createPayNotErrorMessagesWithExistingId() throws Exception {
        // Create the PayNotErrorMessages with an existing ID
        payNotErrorMessages.setId(1L);

        int databaseSizeBeforeCreate = payNotErrorMessagesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayNotErrorMessagesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payNotErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayNotErrorMessages in the database
        List<PayNotErrorMessages> payNotErrorMessagesList = payNotErrorMessagesRepository.findAll();
        assertThat(payNotErrorMessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayNotErrorMessages() throws Exception {
        // Initialize the database
        payNotErrorMessagesRepository.saveAndFlush(payNotErrorMessages);

        // Get all the payNotErrorMessagesList
        restPayNotErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payNotErrorMessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    void getPayNotErrorMessages() throws Exception {
        // Initialize the database
        payNotErrorMessagesRepository.saveAndFlush(payNotErrorMessages);

        // Get the payNotErrorMessages
        restPayNotErrorMessagesMockMvc
            .perform(get(ENTITY_API_URL_ID, payNotErrorMessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payNotErrorMessages.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    void getNonExistingPayNotErrorMessages() throws Exception {
        // Get the payNotErrorMessages
        restPayNotErrorMessagesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPayNotErrorMessages() throws Exception {
        // Initialize the database
        payNotErrorMessagesRepository.saveAndFlush(payNotErrorMessages);

        int databaseSizeBeforeUpdate = payNotErrorMessagesRepository.findAll().size();

        // Update the payNotErrorMessages
        PayNotErrorMessages updatedPayNotErrorMessages = payNotErrorMessagesRepository.findById(payNotErrorMessages.getId()).get();
        // Disconnect from session so that the updates on updatedPayNotErrorMessages are not directly saved in db
        em.detach(updatedPayNotErrorMessages);
        updatedPayNotErrorMessages.message(UPDATED_MESSAGE);

        restPayNotErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayNotErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayNotErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the PayNotErrorMessages in the database
        List<PayNotErrorMessages> payNotErrorMessagesList = payNotErrorMessagesRepository.findAll();
        assertThat(payNotErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        PayNotErrorMessages testPayNotErrorMessages = payNotErrorMessagesList.get(payNotErrorMessagesList.size() - 1);
        assertThat(testPayNotErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void putNonExistingPayNotErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = payNotErrorMessagesRepository.findAll().size();
        payNotErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayNotErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payNotErrorMessages.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payNotErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayNotErrorMessages in the database
        List<PayNotErrorMessages> payNotErrorMessagesList = payNotErrorMessagesRepository.findAll();
        assertThat(payNotErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayNotErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = payNotErrorMessagesRepository.findAll().size();
        payNotErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayNotErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payNotErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayNotErrorMessages in the database
        List<PayNotErrorMessages> payNotErrorMessagesList = payNotErrorMessagesRepository.findAll();
        assertThat(payNotErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayNotErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = payNotErrorMessagesRepository.findAll().size();
        payNotErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayNotErrorMessagesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payNotErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayNotErrorMessages in the database
        List<PayNotErrorMessages> payNotErrorMessagesList = payNotErrorMessagesRepository.findAll();
        assertThat(payNotErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePayNotErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        payNotErrorMessagesRepository.saveAndFlush(payNotErrorMessages);

        int databaseSizeBeforeUpdate = payNotErrorMessagesRepository.findAll().size();

        // Update the payNotErrorMessages using partial update
        PayNotErrorMessages partialUpdatedPayNotErrorMessages = new PayNotErrorMessages();
        partialUpdatedPayNotErrorMessages.setId(payNotErrorMessages.getId());

        partialUpdatedPayNotErrorMessages.message(UPDATED_MESSAGE);

        restPayNotErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayNotErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayNotErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the PayNotErrorMessages in the database
        List<PayNotErrorMessages> payNotErrorMessagesList = payNotErrorMessagesRepository.findAll();
        assertThat(payNotErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        PayNotErrorMessages testPayNotErrorMessages = payNotErrorMessagesList.get(payNotErrorMessagesList.size() - 1);
        assertThat(testPayNotErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void fullUpdatePayNotErrorMessagesWithPatch() throws Exception {
        // Initialize the database
        payNotErrorMessagesRepository.saveAndFlush(payNotErrorMessages);

        int databaseSizeBeforeUpdate = payNotErrorMessagesRepository.findAll().size();

        // Update the payNotErrorMessages using partial update
        PayNotErrorMessages partialUpdatedPayNotErrorMessages = new PayNotErrorMessages();
        partialUpdatedPayNotErrorMessages.setId(payNotErrorMessages.getId());

        partialUpdatedPayNotErrorMessages.message(UPDATED_MESSAGE);

        restPayNotErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayNotErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayNotErrorMessages))
            )
            .andExpect(status().isOk());

        // Validate the PayNotErrorMessages in the database
        List<PayNotErrorMessages> payNotErrorMessagesList = payNotErrorMessagesRepository.findAll();
        assertThat(payNotErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
        PayNotErrorMessages testPayNotErrorMessages = payNotErrorMessagesList.get(payNotErrorMessagesList.size() - 1);
        assertThat(testPayNotErrorMessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingPayNotErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = payNotErrorMessagesRepository.findAll().size();
        payNotErrorMessages.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayNotErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payNotErrorMessages.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payNotErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayNotErrorMessages in the database
        List<PayNotErrorMessages> payNotErrorMessagesList = payNotErrorMessagesRepository.findAll();
        assertThat(payNotErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayNotErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = payNotErrorMessagesRepository.findAll().size();
        payNotErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayNotErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payNotErrorMessages))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayNotErrorMessages in the database
        List<PayNotErrorMessages> payNotErrorMessagesList = payNotErrorMessagesRepository.findAll();
        assertThat(payNotErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayNotErrorMessages() throws Exception {
        int databaseSizeBeforeUpdate = payNotErrorMessagesRepository.findAll().size();
        payNotErrorMessages.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayNotErrorMessagesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payNotErrorMessages))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayNotErrorMessages in the database
        List<PayNotErrorMessages> payNotErrorMessagesList = payNotErrorMessagesRepository.findAll();
        assertThat(payNotErrorMessagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayNotErrorMessages() throws Exception {
        // Initialize the database
        payNotErrorMessagesRepository.saveAndFlush(payNotErrorMessages);

        int databaseSizeBeforeDelete = payNotErrorMessagesRepository.findAll().size();

        // Delete the payNotErrorMessages
        restPayNotErrorMessagesMockMvc
            .perform(delete(ENTITY_API_URL_ID, payNotErrorMessages.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PayNotErrorMessages> payNotErrorMessagesList = payNotErrorMessagesRepository.findAll();
        assertThat(payNotErrorMessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
