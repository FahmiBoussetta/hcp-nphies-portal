package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ClaimResponse;
import com.platformsandsolutions.hcpnphiesportal.repository.ClaimResponseRepository;
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
 * Integration tests for the {@link ClaimResponseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimResponseResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_PARSED = "AAAAAAAAAA";
    private static final String UPDATED_PARSED = "BBBBBBBBBB";

    private static final String DEFAULT_OUTCOME = "AAAAAAAAAA";
    private static final String UPDATED_OUTCOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/claim-responses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimResponseRepository claimResponseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimResponseMockMvc;

    private ClaimResponse claimResponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimResponse createEntity(EntityManager em) {
        ClaimResponse claimResponse = new ClaimResponse()
            .value(DEFAULT_VALUE)
            .system(DEFAULT_SYSTEM)
            .parsed(DEFAULT_PARSED)
            .outcome(DEFAULT_OUTCOME);
        return claimResponse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClaimResponse createUpdatedEntity(EntityManager em) {
        ClaimResponse claimResponse = new ClaimResponse()
            .value(UPDATED_VALUE)
            .system(UPDATED_SYSTEM)
            .parsed(UPDATED_PARSED)
            .outcome(UPDATED_OUTCOME);
        return claimResponse;
    }

    @BeforeEach
    public void initTest() {
        claimResponse = createEntity(em);
    }

    @Test
    @Transactional
    void createClaimResponse() throws Exception {
        int databaseSizeBeforeCreate = claimResponseRepository.findAll().size();
        // Create the ClaimResponse
        restClaimResponseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claimResponse)))
            .andExpect(status().isCreated());

        // Validate the ClaimResponse in the database
        List<ClaimResponse> claimResponseList = claimResponseRepository.findAll();
        assertThat(claimResponseList).hasSize(databaseSizeBeforeCreate + 1);
        ClaimResponse testClaimResponse = claimResponseList.get(claimResponseList.size() - 1);
        assertThat(testClaimResponse.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testClaimResponse.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testClaimResponse.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testClaimResponse.getOutcome()).isEqualTo(DEFAULT_OUTCOME);
    }

    @Test
    @Transactional
    void createClaimResponseWithExistingId() throws Exception {
        // Create the ClaimResponse with an existing ID
        claimResponse.setId(1L);

        int databaseSizeBeforeCreate = claimResponseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimResponseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claimResponse)))
            .andExpect(status().isBadRequest());

        // Validate the ClaimResponse in the database
        List<ClaimResponse> claimResponseList = claimResponseRepository.findAll();
        assertThat(claimResponseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClaimResponses() throws Exception {
        // Initialize the database
        claimResponseRepository.saveAndFlush(claimResponse);

        // Get all the claimResponseList
        restClaimResponseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claimResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM)))
            .andExpect(jsonPath("$.[*].parsed").value(hasItem(DEFAULT_PARSED)))
            .andExpect(jsonPath("$.[*].outcome").value(hasItem(DEFAULT_OUTCOME)));
    }

    @Test
    @Transactional
    void getClaimResponse() throws Exception {
        // Initialize the database
        claimResponseRepository.saveAndFlush(claimResponse);

        // Get the claimResponse
        restClaimResponseMockMvc
            .perform(get(ENTITY_API_URL_ID, claimResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claimResponse.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM))
            .andExpect(jsonPath("$.parsed").value(DEFAULT_PARSED))
            .andExpect(jsonPath("$.outcome").value(DEFAULT_OUTCOME));
    }

    @Test
    @Transactional
    void getNonExistingClaimResponse() throws Exception {
        // Get the claimResponse
        restClaimResponseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClaimResponse() throws Exception {
        // Initialize the database
        claimResponseRepository.saveAndFlush(claimResponse);

        int databaseSizeBeforeUpdate = claimResponseRepository.findAll().size();

        // Update the claimResponse
        ClaimResponse updatedClaimResponse = claimResponseRepository.findById(claimResponse.getId()).get();
        // Disconnect from session so that the updates on updatedClaimResponse are not directly saved in db
        em.detach(updatedClaimResponse);
        updatedClaimResponse.value(UPDATED_VALUE).system(UPDATED_SYSTEM).parsed(UPDATED_PARSED).outcome(UPDATED_OUTCOME);

        restClaimResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClaimResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClaimResponse))
            )
            .andExpect(status().isOk());

        // Validate the ClaimResponse in the database
        List<ClaimResponse> claimResponseList = claimResponseRepository.findAll();
        assertThat(claimResponseList).hasSize(databaseSizeBeforeUpdate);
        ClaimResponse testClaimResponse = claimResponseList.get(claimResponseList.size() - 1);
        assertThat(testClaimResponse.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testClaimResponse.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testClaimResponse.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testClaimResponse.getOutcome()).isEqualTo(UPDATED_OUTCOME);
    }

    @Test
    @Transactional
    void putNonExistingClaimResponse() throws Exception {
        int databaseSizeBeforeUpdate = claimResponseRepository.findAll().size();
        claimResponse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claimResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimResponse in the database
        List<ClaimResponse> claimResponseList = claimResponseRepository.findAll();
        assertThat(claimResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaimResponse() throws Exception {
        int databaseSizeBeforeUpdate = claimResponseRepository.findAll().size();
        claimResponse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimResponse in the database
        List<ClaimResponse> claimResponseList = claimResponseRepository.findAll();
        assertThat(claimResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaimResponse() throws Exception {
        int databaseSizeBeforeUpdate = claimResponseRepository.findAll().size();
        claimResponse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimResponseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claimResponse)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimResponse in the database
        List<ClaimResponse> claimResponseList = claimResponseRepository.findAll();
        assertThat(claimResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimResponseWithPatch() throws Exception {
        // Initialize the database
        claimResponseRepository.saveAndFlush(claimResponse);

        int databaseSizeBeforeUpdate = claimResponseRepository.findAll().size();

        // Update the claimResponse using partial update
        ClaimResponse partialUpdatedClaimResponse = new ClaimResponse();
        partialUpdatedClaimResponse.setId(claimResponse.getId());

        partialUpdatedClaimResponse.value(UPDATED_VALUE).parsed(UPDATED_PARSED).outcome(UPDATED_OUTCOME);

        restClaimResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimResponse))
            )
            .andExpect(status().isOk());

        // Validate the ClaimResponse in the database
        List<ClaimResponse> claimResponseList = claimResponseRepository.findAll();
        assertThat(claimResponseList).hasSize(databaseSizeBeforeUpdate);
        ClaimResponse testClaimResponse = claimResponseList.get(claimResponseList.size() - 1);
        assertThat(testClaimResponse.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testClaimResponse.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testClaimResponse.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testClaimResponse.getOutcome()).isEqualTo(UPDATED_OUTCOME);
    }

    @Test
    @Transactional
    void fullUpdateClaimResponseWithPatch() throws Exception {
        // Initialize the database
        claimResponseRepository.saveAndFlush(claimResponse);

        int databaseSizeBeforeUpdate = claimResponseRepository.findAll().size();

        // Update the claimResponse using partial update
        ClaimResponse partialUpdatedClaimResponse = new ClaimResponse();
        partialUpdatedClaimResponse.setId(claimResponse.getId());

        partialUpdatedClaimResponse.value(UPDATED_VALUE).system(UPDATED_SYSTEM).parsed(UPDATED_PARSED).outcome(UPDATED_OUTCOME);

        restClaimResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaimResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaimResponse))
            )
            .andExpect(status().isOk());

        // Validate the ClaimResponse in the database
        List<ClaimResponse> claimResponseList = claimResponseRepository.findAll();
        assertThat(claimResponseList).hasSize(databaseSizeBeforeUpdate);
        ClaimResponse testClaimResponse = claimResponseList.get(claimResponseList.size() - 1);
        assertThat(testClaimResponse.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testClaimResponse.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testClaimResponse.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testClaimResponse.getOutcome()).isEqualTo(UPDATED_OUTCOME);
    }

    @Test
    @Transactional
    void patchNonExistingClaimResponse() throws Exception {
        int databaseSizeBeforeUpdate = claimResponseRepository.findAll().size();
        claimResponse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claimResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimResponse in the database
        List<ClaimResponse> claimResponseList = claimResponseRepository.findAll();
        assertThat(claimResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaimResponse() throws Exception {
        int databaseSizeBeforeUpdate = claimResponseRepository.findAll().size();
        claimResponse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClaimResponse in the database
        List<ClaimResponse> claimResponseList = claimResponseRepository.findAll();
        assertThat(claimResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaimResponse() throws Exception {
        int databaseSizeBeforeUpdate = claimResponseRepository.findAll().size();
        claimResponse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimResponseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(claimResponse))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClaimResponse in the database
        List<ClaimResponse> claimResponseList = claimResponseRepository.findAll();
        assertThat(claimResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaimResponse() throws Exception {
        // Initialize the database
        claimResponseRepository.saveAndFlush(claimResponse);

        int databaseSizeBeforeDelete = claimResponseRepository.findAll().size();

        // Delete the claimResponse
        restClaimResponseMockMvc
            .perform(delete(ENTITY_API_URL_ID, claimResponse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClaimResponse> claimResponseList = claimResponseRepository.findAll();
        assertThat(claimResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
