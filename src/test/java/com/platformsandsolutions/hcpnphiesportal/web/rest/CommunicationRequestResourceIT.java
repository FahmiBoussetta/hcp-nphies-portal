package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.CommunicationRequest;
import com.platformsandsolutions.hcpnphiesportal.repository.CommunicationRequestRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CommunicationRequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommunicationRequestResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_PARSED = "AAAAAAAAAA";
    private static final String UPDATED_PARSED = "BBBBBBBBBB";

    private static final Instant DEFAULT_LIMIT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LIMIT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/communication-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommunicationRequestRepository communicationRequestRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommunicationRequestMockMvc;

    private CommunicationRequest communicationRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommunicationRequest createEntity(EntityManager em) {
        CommunicationRequest communicationRequest = new CommunicationRequest()
            .value(DEFAULT_VALUE)
            .system(DEFAULT_SYSTEM)
            .parsed(DEFAULT_PARSED)
            .limitDate(DEFAULT_LIMIT_DATE);
        return communicationRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommunicationRequest createUpdatedEntity(EntityManager em) {
        CommunicationRequest communicationRequest = new CommunicationRequest()
            .value(UPDATED_VALUE)
            .system(UPDATED_SYSTEM)
            .parsed(UPDATED_PARSED)
            .limitDate(UPDATED_LIMIT_DATE);
        return communicationRequest;
    }

    @BeforeEach
    public void initTest() {
        communicationRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createCommunicationRequest() throws Exception {
        int databaseSizeBeforeCreate = communicationRequestRepository.findAll().size();
        // Create the CommunicationRequest
        restCommunicationRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communicationRequest))
            )
            .andExpect(status().isCreated());

        // Validate the CommunicationRequest in the database
        List<CommunicationRequest> communicationRequestList = communicationRequestRepository.findAll();
        assertThat(communicationRequestList).hasSize(databaseSizeBeforeCreate + 1);
        CommunicationRequest testCommunicationRequest = communicationRequestList.get(communicationRequestList.size() - 1);
        assertThat(testCommunicationRequest.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCommunicationRequest.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testCommunicationRequest.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testCommunicationRequest.getLimitDate()).isEqualTo(DEFAULT_LIMIT_DATE);
    }

    @Test
    @Transactional
    void createCommunicationRequestWithExistingId() throws Exception {
        // Create the CommunicationRequest with an existing ID
        communicationRequest.setId(1L);

        int databaseSizeBeforeCreate = communicationRequestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunicationRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communicationRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunicationRequest in the database
        List<CommunicationRequest> communicationRequestList = communicationRequestRepository.findAll();
        assertThat(communicationRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommunicationRequests() throws Exception {
        // Initialize the database
        communicationRequestRepository.saveAndFlush(communicationRequest);

        // Get all the communicationRequestList
        restCommunicationRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(communicationRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM)))
            .andExpect(jsonPath("$.[*].parsed").value(hasItem(DEFAULT_PARSED)))
            .andExpect(jsonPath("$.[*].limitDate").value(hasItem(DEFAULT_LIMIT_DATE.toString())));
    }

    @Test
    @Transactional
    void getCommunicationRequest() throws Exception {
        // Initialize the database
        communicationRequestRepository.saveAndFlush(communicationRequest);

        // Get the communicationRequest
        restCommunicationRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, communicationRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(communicationRequest.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM))
            .andExpect(jsonPath("$.parsed").value(DEFAULT_PARSED))
            .andExpect(jsonPath("$.limitDate").value(DEFAULT_LIMIT_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCommunicationRequest() throws Exception {
        // Get the communicationRequest
        restCommunicationRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCommunicationRequest() throws Exception {
        // Initialize the database
        communicationRequestRepository.saveAndFlush(communicationRequest);

        int databaseSizeBeforeUpdate = communicationRequestRepository.findAll().size();

        // Update the communicationRequest
        CommunicationRequest updatedCommunicationRequest = communicationRequestRepository.findById(communicationRequest.getId()).get();
        // Disconnect from session so that the updates on updatedCommunicationRequest are not directly saved in db
        em.detach(updatedCommunicationRequest);
        updatedCommunicationRequest.value(UPDATED_VALUE).system(UPDATED_SYSTEM).parsed(UPDATED_PARSED).limitDate(UPDATED_LIMIT_DATE);

        restCommunicationRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCommunicationRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCommunicationRequest))
            )
            .andExpect(status().isOk());

        // Validate the CommunicationRequest in the database
        List<CommunicationRequest> communicationRequestList = communicationRequestRepository.findAll();
        assertThat(communicationRequestList).hasSize(databaseSizeBeforeUpdate);
        CommunicationRequest testCommunicationRequest = communicationRequestList.get(communicationRequestList.size() - 1);
        assertThat(testCommunicationRequest.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCommunicationRequest.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testCommunicationRequest.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testCommunicationRequest.getLimitDate()).isEqualTo(UPDATED_LIMIT_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCommunicationRequest() throws Exception {
        int databaseSizeBeforeUpdate = communicationRequestRepository.findAll().size();
        communicationRequest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunicationRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, communicationRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communicationRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunicationRequest in the database
        List<CommunicationRequest> communicationRequestList = communicationRequestRepository.findAll();
        assertThat(communicationRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommunicationRequest() throws Exception {
        int databaseSizeBeforeUpdate = communicationRequestRepository.findAll().size();
        communicationRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunicationRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communicationRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunicationRequest in the database
        List<CommunicationRequest> communicationRequestList = communicationRequestRepository.findAll();
        assertThat(communicationRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommunicationRequest() throws Exception {
        int databaseSizeBeforeUpdate = communicationRequestRepository.findAll().size();
        communicationRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunicationRequestMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(communicationRequest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommunicationRequest in the database
        List<CommunicationRequest> communicationRequestList = communicationRequestRepository.findAll();
        assertThat(communicationRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommunicationRequestWithPatch() throws Exception {
        // Initialize the database
        communicationRequestRepository.saveAndFlush(communicationRequest);

        int databaseSizeBeforeUpdate = communicationRequestRepository.findAll().size();

        // Update the communicationRequest using partial update
        CommunicationRequest partialUpdatedCommunicationRequest = new CommunicationRequest();
        partialUpdatedCommunicationRequest.setId(communicationRequest.getId());

        partialUpdatedCommunicationRequest.value(UPDATED_VALUE).system(UPDATED_SYSTEM);

        restCommunicationRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommunicationRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommunicationRequest))
            )
            .andExpect(status().isOk());

        // Validate the CommunicationRequest in the database
        List<CommunicationRequest> communicationRequestList = communicationRequestRepository.findAll();
        assertThat(communicationRequestList).hasSize(databaseSizeBeforeUpdate);
        CommunicationRequest testCommunicationRequest = communicationRequestList.get(communicationRequestList.size() - 1);
        assertThat(testCommunicationRequest.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCommunicationRequest.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testCommunicationRequest.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testCommunicationRequest.getLimitDate()).isEqualTo(DEFAULT_LIMIT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCommunicationRequestWithPatch() throws Exception {
        // Initialize the database
        communicationRequestRepository.saveAndFlush(communicationRequest);

        int databaseSizeBeforeUpdate = communicationRequestRepository.findAll().size();

        // Update the communicationRequest using partial update
        CommunicationRequest partialUpdatedCommunicationRequest = new CommunicationRequest();
        partialUpdatedCommunicationRequest.setId(communicationRequest.getId());

        partialUpdatedCommunicationRequest.value(UPDATED_VALUE).system(UPDATED_SYSTEM).parsed(UPDATED_PARSED).limitDate(UPDATED_LIMIT_DATE);

        restCommunicationRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommunicationRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommunicationRequest))
            )
            .andExpect(status().isOk());

        // Validate the CommunicationRequest in the database
        List<CommunicationRequest> communicationRequestList = communicationRequestRepository.findAll();
        assertThat(communicationRequestList).hasSize(databaseSizeBeforeUpdate);
        CommunicationRequest testCommunicationRequest = communicationRequestList.get(communicationRequestList.size() - 1);
        assertThat(testCommunicationRequest.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCommunicationRequest.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testCommunicationRequest.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testCommunicationRequest.getLimitDate()).isEqualTo(UPDATED_LIMIT_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCommunicationRequest() throws Exception {
        int databaseSizeBeforeUpdate = communicationRequestRepository.findAll().size();
        communicationRequest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunicationRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, communicationRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communicationRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunicationRequest in the database
        List<CommunicationRequest> communicationRequestList = communicationRequestRepository.findAll();
        assertThat(communicationRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommunicationRequest() throws Exception {
        int databaseSizeBeforeUpdate = communicationRequestRepository.findAll().size();
        communicationRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunicationRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communicationRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the CommunicationRequest in the database
        List<CommunicationRequest> communicationRequestList = communicationRequestRepository.findAll();
        assertThat(communicationRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommunicationRequest() throws Exception {
        int databaseSizeBeforeUpdate = communicationRequestRepository.findAll().size();
        communicationRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunicationRequestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communicationRequest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CommunicationRequest in the database
        List<CommunicationRequest> communicationRequestList = communicationRequestRepository.findAll();
        assertThat(communicationRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommunicationRequest() throws Exception {
        // Initialize the database
        communicationRequestRepository.saveAndFlush(communicationRequest);

        int databaseSizeBeforeDelete = communicationRequestRepository.findAll().size();

        // Delete the communicationRequest
        restCommunicationRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, communicationRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommunicationRequest> communicationRequestList = communicationRequestRepository.findAll();
        assertThat(communicationRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
