package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Payload;
import com.platformsandsolutions.hcpnphiesportal.repository.PayloadRepository;
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
 * Integration tests for the {@link PayloadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PayloadResourceIT {

    private static final String DEFAULT_CONTENT_STRING = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_STRING = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payloads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PayloadRepository payloadRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayloadMockMvc;

    private Payload payload;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payload createEntity(EntityManager em) {
        Payload payload = new Payload().contentString(DEFAULT_CONTENT_STRING);
        return payload;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payload createUpdatedEntity(EntityManager em) {
        Payload payload = new Payload().contentString(UPDATED_CONTENT_STRING);
        return payload;
    }

    @BeforeEach
    public void initTest() {
        payload = createEntity(em);
    }

    @Test
    @Transactional
    void createPayload() throws Exception {
        int databaseSizeBeforeCreate = payloadRepository.findAll().size();
        // Create the Payload
        restPayloadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payload)))
            .andExpect(status().isCreated());

        // Validate the Payload in the database
        List<Payload> payloadList = payloadRepository.findAll();
        assertThat(payloadList).hasSize(databaseSizeBeforeCreate + 1);
        Payload testPayload = payloadList.get(payloadList.size() - 1);
        assertThat(testPayload.getContentString()).isEqualTo(DEFAULT_CONTENT_STRING);
    }

    @Test
    @Transactional
    void createPayloadWithExistingId() throws Exception {
        // Create the Payload with an existing ID
        payload.setId(1L);

        int databaseSizeBeforeCreate = payloadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayloadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payload)))
            .andExpect(status().isBadRequest());

        // Validate the Payload in the database
        List<Payload> payloadList = payloadRepository.findAll();
        assertThat(payloadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayloads() throws Exception {
        // Initialize the database
        payloadRepository.saveAndFlush(payload);

        // Get all the payloadList
        restPayloadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payload.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentString").value(hasItem(DEFAULT_CONTENT_STRING)));
    }

    @Test
    @Transactional
    void getPayload() throws Exception {
        // Initialize the database
        payloadRepository.saveAndFlush(payload);

        // Get the payload
        restPayloadMockMvc
            .perform(get(ENTITY_API_URL_ID, payload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payload.getId().intValue()))
            .andExpect(jsonPath("$.contentString").value(DEFAULT_CONTENT_STRING));
    }

    @Test
    @Transactional
    void getNonExistingPayload() throws Exception {
        // Get the payload
        restPayloadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPayload() throws Exception {
        // Initialize the database
        payloadRepository.saveAndFlush(payload);

        int databaseSizeBeforeUpdate = payloadRepository.findAll().size();

        // Update the payload
        Payload updatedPayload = payloadRepository.findById(payload.getId()).get();
        // Disconnect from session so that the updates on updatedPayload are not directly saved in db
        em.detach(updatedPayload);
        updatedPayload.contentString(UPDATED_CONTENT_STRING);

        restPayloadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayload.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayload))
            )
            .andExpect(status().isOk());

        // Validate the Payload in the database
        List<Payload> payloadList = payloadRepository.findAll();
        assertThat(payloadList).hasSize(databaseSizeBeforeUpdate);
        Payload testPayload = payloadList.get(payloadList.size() - 1);
        assertThat(testPayload.getContentString()).isEqualTo(UPDATED_CONTENT_STRING);
    }

    @Test
    @Transactional
    void putNonExistingPayload() throws Exception {
        int databaseSizeBeforeUpdate = payloadRepository.findAll().size();
        payload.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayloadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payload.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payload))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payload in the database
        List<Payload> payloadList = payloadRepository.findAll();
        assertThat(payloadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayload() throws Exception {
        int databaseSizeBeforeUpdate = payloadRepository.findAll().size();
        payload.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayloadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payload))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payload in the database
        List<Payload> payloadList = payloadRepository.findAll();
        assertThat(payloadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayload() throws Exception {
        int databaseSizeBeforeUpdate = payloadRepository.findAll().size();
        payload.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayloadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payload)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payload in the database
        List<Payload> payloadList = payloadRepository.findAll();
        assertThat(payloadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePayloadWithPatch() throws Exception {
        // Initialize the database
        payloadRepository.saveAndFlush(payload);

        int databaseSizeBeforeUpdate = payloadRepository.findAll().size();

        // Update the payload using partial update
        Payload partialUpdatedPayload = new Payload();
        partialUpdatedPayload.setId(payload.getId());

        partialUpdatedPayload.contentString(UPDATED_CONTENT_STRING);

        restPayloadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayload.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayload))
            )
            .andExpect(status().isOk());

        // Validate the Payload in the database
        List<Payload> payloadList = payloadRepository.findAll();
        assertThat(payloadList).hasSize(databaseSizeBeforeUpdate);
        Payload testPayload = payloadList.get(payloadList.size() - 1);
        assertThat(testPayload.getContentString()).isEqualTo(UPDATED_CONTENT_STRING);
    }

    @Test
    @Transactional
    void fullUpdatePayloadWithPatch() throws Exception {
        // Initialize the database
        payloadRepository.saveAndFlush(payload);

        int databaseSizeBeforeUpdate = payloadRepository.findAll().size();

        // Update the payload using partial update
        Payload partialUpdatedPayload = new Payload();
        partialUpdatedPayload.setId(payload.getId());

        partialUpdatedPayload.contentString(UPDATED_CONTENT_STRING);

        restPayloadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayload.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayload))
            )
            .andExpect(status().isOk());

        // Validate the Payload in the database
        List<Payload> payloadList = payloadRepository.findAll();
        assertThat(payloadList).hasSize(databaseSizeBeforeUpdate);
        Payload testPayload = payloadList.get(payloadList.size() - 1);
        assertThat(testPayload.getContentString()).isEqualTo(UPDATED_CONTENT_STRING);
    }

    @Test
    @Transactional
    void patchNonExistingPayload() throws Exception {
        int databaseSizeBeforeUpdate = payloadRepository.findAll().size();
        payload.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayloadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payload.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payload))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payload in the database
        List<Payload> payloadList = payloadRepository.findAll();
        assertThat(payloadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayload() throws Exception {
        int databaseSizeBeforeUpdate = payloadRepository.findAll().size();
        payload.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayloadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payload))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payload in the database
        List<Payload> payloadList = payloadRepository.findAll();
        assertThat(payloadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayload() throws Exception {
        int databaseSizeBeforeUpdate = payloadRepository.findAll().size();
        payload.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayloadMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(payload)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payload in the database
        List<Payload> payloadList = payloadRepository.findAll();
        assertThat(payloadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayload() throws Exception {
        // Initialize the database
        payloadRepository.saveAndFlush(payload);

        int databaseSizeBeforeDelete = payloadRepository.findAll().size();

        // Delete the payload
        restPayloadMockMvc
            .perform(delete(ENTITY_API_URL_ID, payload.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payload> payloadList = payloadRepository.findAll();
        assertThat(payloadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
