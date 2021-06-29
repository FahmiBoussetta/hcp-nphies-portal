package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.CoverageEligibilityResponse;
import com.platformsandsolutions.hcpnphiesportal.repository.CoverageEligibilityResponseRepository;
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
 * Integration tests for the {@link CoverageEligibilityResponseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CoverageEligibilityResponseResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_PARSED = "AAAAAAAAAA";
    private static final String UPDATED_PARSED = "BBBBBBBBBB";

    private static final String DEFAULT_OUTCOME = "AAAAAAAAAA";
    private static final String UPDATED_OUTCOME = "BBBBBBBBBB";

    private static final Instant DEFAULT_SERVICED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SERVICED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SERVICED_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SERVICED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DISPOSITION = "AAAAAAAAAA";
    private static final String UPDATED_DISPOSITION = "BBBBBBBBBB";

    private static final String DEFAULT_NOT_INFORCE_REASON = "AAAAAAAAAA";
    private static final String UPDATED_NOT_INFORCE_REASON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/coverage-eligibility-responses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CoverageEligibilityResponseRepository coverageEligibilityResponseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoverageEligibilityResponseMockMvc;

    private CoverageEligibilityResponse coverageEligibilityResponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoverageEligibilityResponse createEntity(EntityManager em) {
        CoverageEligibilityResponse coverageEligibilityResponse = new CoverageEligibilityResponse()
            .value(DEFAULT_VALUE)
            .system(DEFAULT_SYSTEM)
            .parsed(DEFAULT_PARSED)
            .outcome(DEFAULT_OUTCOME)
            .serviced(DEFAULT_SERVICED)
            .servicedEnd(DEFAULT_SERVICED_END)
            .disposition(DEFAULT_DISPOSITION)
            .notInforceReason(DEFAULT_NOT_INFORCE_REASON);
        return coverageEligibilityResponse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoverageEligibilityResponse createUpdatedEntity(EntityManager em) {
        CoverageEligibilityResponse coverageEligibilityResponse = new CoverageEligibilityResponse()
            .value(UPDATED_VALUE)
            .system(UPDATED_SYSTEM)
            .parsed(UPDATED_PARSED)
            .outcome(UPDATED_OUTCOME)
            .serviced(UPDATED_SERVICED)
            .servicedEnd(UPDATED_SERVICED_END)
            .disposition(UPDATED_DISPOSITION)
            .notInforceReason(UPDATED_NOT_INFORCE_REASON);
        return coverageEligibilityResponse;
    }

    @BeforeEach
    public void initTest() {
        coverageEligibilityResponse = createEntity(em);
    }

    @Test
    @Transactional
    void createCoverageEligibilityResponse() throws Exception {
        int databaseSizeBeforeCreate = coverageEligibilityResponseRepository.findAll().size();
        // Create the CoverageEligibilityResponse
        restCoverageEligibilityResponseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityResponse))
            )
            .andExpect(status().isCreated());

        // Validate the CoverageEligibilityResponse in the database
        List<CoverageEligibilityResponse> coverageEligibilityResponseList = coverageEligibilityResponseRepository.findAll();
        assertThat(coverageEligibilityResponseList).hasSize(databaseSizeBeforeCreate + 1);
        CoverageEligibilityResponse testCoverageEligibilityResponse = coverageEligibilityResponseList.get(
            coverageEligibilityResponseList.size() - 1
        );
        assertThat(testCoverageEligibilityResponse.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCoverageEligibilityResponse.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testCoverageEligibilityResponse.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testCoverageEligibilityResponse.getOutcome()).isEqualTo(DEFAULT_OUTCOME);
        assertThat(testCoverageEligibilityResponse.getServiced()).isEqualTo(DEFAULT_SERVICED);
        assertThat(testCoverageEligibilityResponse.getServicedEnd()).isEqualTo(DEFAULT_SERVICED_END);
        assertThat(testCoverageEligibilityResponse.getDisposition()).isEqualTo(DEFAULT_DISPOSITION);
        assertThat(testCoverageEligibilityResponse.getNotInforceReason()).isEqualTo(DEFAULT_NOT_INFORCE_REASON);
    }

    @Test
    @Transactional
    void createCoverageEligibilityResponseWithExistingId() throws Exception {
        // Create the CoverageEligibilityResponse with an existing ID
        coverageEligibilityResponse.setId(1L);

        int databaseSizeBeforeCreate = coverageEligibilityResponseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoverageEligibilityResponseMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoverageEligibilityResponse in the database
        List<CoverageEligibilityResponse> coverageEligibilityResponseList = coverageEligibilityResponseRepository.findAll();
        assertThat(coverageEligibilityResponseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCoverageEligibilityResponses() throws Exception {
        // Initialize the database
        coverageEligibilityResponseRepository.saveAndFlush(coverageEligibilityResponse);

        // Get all the coverageEligibilityResponseList
        restCoverageEligibilityResponseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coverageEligibilityResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM)))
            .andExpect(jsonPath("$.[*].parsed").value(hasItem(DEFAULT_PARSED)))
            .andExpect(jsonPath("$.[*].outcome").value(hasItem(DEFAULT_OUTCOME)))
            .andExpect(jsonPath("$.[*].serviced").value(hasItem(DEFAULT_SERVICED.toString())))
            .andExpect(jsonPath("$.[*].servicedEnd").value(hasItem(DEFAULT_SERVICED_END.toString())))
            .andExpect(jsonPath("$.[*].disposition").value(hasItem(DEFAULT_DISPOSITION)))
            .andExpect(jsonPath("$.[*].notInforceReason").value(hasItem(DEFAULT_NOT_INFORCE_REASON)));
    }

    @Test
    @Transactional
    void getCoverageEligibilityResponse() throws Exception {
        // Initialize the database
        coverageEligibilityResponseRepository.saveAndFlush(coverageEligibilityResponse);

        // Get the coverageEligibilityResponse
        restCoverageEligibilityResponseMockMvc
            .perform(get(ENTITY_API_URL_ID, coverageEligibilityResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coverageEligibilityResponse.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM))
            .andExpect(jsonPath("$.parsed").value(DEFAULT_PARSED))
            .andExpect(jsonPath("$.outcome").value(DEFAULT_OUTCOME))
            .andExpect(jsonPath("$.serviced").value(DEFAULT_SERVICED.toString()))
            .andExpect(jsonPath("$.servicedEnd").value(DEFAULT_SERVICED_END.toString()))
            .andExpect(jsonPath("$.disposition").value(DEFAULT_DISPOSITION))
            .andExpect(jsonPath("$.notInforceReason").value(DEFAULT_NOT_INFORCE_REASON));
    }

    @Test
    @Transactional
    void getNonExistingCoverageEligibilityResponse() throws Exception {
        // Get the coverageEligibilityResponse
        restCoverageEligibilityResponseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCoverageEligibilityResponse() throws Exception {
        // Initialize the database
        coverageEligibilityResponseRepository.saveAndFlush(coverageEligibilityResponse);

        int databaseSizeBeforeUpdate = coverageEligibilityResponseRepository.findAll().size();

        // Update the coverageEligibilityResponse
        CoverageEligibilityResponse updatedCoverageEligibilityResponse = coverageEligibilityResponseRepository
            .findById(coverageEligibilityResponse.getId())
            .get();
        // Disconnect from session so that the updates on updatedCoverageEligibilityResponse are not directly saved in db
        em.detach(updatedCoverageEligibilityResponse);
        updatedCoverageEligibilityResponse
            .value(UPDATED_VALUE)
            .system(UPDATED_SYSTEM)
            .parsed(UPDATED_PARSED)
            .outcome(UPDATED_OUTCOME)
            .serviced(UPDATED_SERVICED)
            .servicedEnd(UPDATED_SERVICED_END)
            .disposition(UPDATED_DISPOSITION)
            .notInforceReason(UPDATED_NOT_INFORCE_REASON);

        restCoverageEligibilityResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCoverageEligibilityResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCoverageEligibilityResponse))
            )
            .andExpect(status().isOk());

        // Validate the CoverageEligibilityResponse in the database
        List<CoverageEligibilityResponse> coverageEligibilityResponseList = coverageEligibilityResponseRepository.findAll();
        assertThat(coverageEligibilityResponseList).hasSize(databaseSizeBeforeUpdate);
        CoverageEligibilityResponse testCoverageEligibilityResponse = coverageEligibilityResponseList.get(
            coverageEligibilityResponseList.size() - 1
        );
        assertThat(testCoverageEligibilityResponse.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCoverageEligibilityResponse.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testCoverageEligibilityResponse.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testCoverageEligibilityResponse.getOutcome()).isEqualTo(UPDATED_OUTCOME);
        assertThat(testCoverageEligibilityResponse.getServiced()).isEqualTo(UPDATED_SERVICED);
        assertThat(testCoverageEligibilityResponse.getServicedEnd()).isEqualTo(UPDATED_SERVICED_END);
        assertThat(testCoverageEligibilityResponse.getDisposition()).isEqualTo(UPDATED_DISPOSITION);
        assertThat(testCoverageEligibilityResponse.getNotInforceReason()).isEqualTo(UPDATED_NOT_INFORCE_REASON);
    }

    @Test
    @Transactional
    void putNonExistingCoverageEligibilityResponse() throws Exception {
        int databaseSizeBeforeUpdate = coverageEligibilityResponseRepository.findAll().size();
        coverageEligibilityResponse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoverageEligibilityResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, coverageEligibilityResponse.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoverageEligibilityResponse in the database
        List<CoverageEligibilityResponse> coverageEligibilityResponseList = coverageEligibilityResponseRepository.findAll();
        assertThat(coverageEligibilityResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCoverageEligibilityResponse() throws Exception {
        int databaseSizeBeforeUpdate = coverageEligibilityResponseRepository.findAll().size();
        coverageEligibilityResponse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoverageEligibilityResponseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoverageEligibilityResponse in the database
        List<CoverageEligibilityResponse> coverageEligibilityResponseList = coverageEligibilityResponseRepository.findAll();
        assertThat(coverageEligibilityResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCoverageEligibilityResponse() throws Exception {
        int databaseSizeBeforeUpdate = coverageEligibilityResponseRepository.findAll().size();
        coverageEligibilityResponse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoverageEligibilityResponseMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityResponse))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CoverageEligibilityResponse in the database
        List<CoverageEligibilityResponse> coverageEligibilityResponseList = coverageEligibilityResponseRepository.findAll();
        assertThat(coverageEligibilityResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCoverageEligibilityResponseWithPatch() throws Exception {
        // Initialize the database
        coverageEligibilityResponseRepository.saveAndFlush(coverageEligibilityResponse);

        int databaseSizeBeforeUpdate = coverageEligibilityResponseRepository.findAll().size();

        // Update the coverageEligibilityResponse using partial update
        CoverageEligibilityResponse partialUpdatedCoverageEligibilityResponse = new CoverageEligibilityResponse();
        partialUpdatedCoverageEligibilityResponse.setId(coverageEligibilityResponse.getId());

        partialUpdatedCoverageEligibilityResponse
            .value(UPDATED_VALUE)
            .system(UPDATED_SYSTEM)
            .serviced(UPDATED_SERVICED)
            .disposition(UPDATED_DISPOSITION);

        restCoverageEligibilityResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoverageEligibilityResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoverageEligibilityResponse))
            )
            .andExpect(status().isOk());

        // Validate the CoverageEligibilityResponse in the database
        List<CoverageEligibilityResponse> coverageEligibilityResponseList = coverageEligibilityResponseRepository.findAll();
        assertThat(coverageEligibilityResponseList).hasSize(databaseSizeBeforeUpdate);
        CoverageEligibilityResponse testCoverageEligibilityResponse = coverageEligibilityResponseList.get(
            coverageEligibilityResponseList.size() - 1
        );
        assertThat(testCoverageEligibilityResponse.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCoverageEligibilityResponse.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testCoverageEligibilityResponse.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testCoverageEligibilityResponse.getOutcome()).isEqualTo(DEFAULT_OUTCOME);
        assertThat(testCoverageEligibilityResponse.getServiced()).isEqualTo(UPDATED_SERVICED);
        assertThat(testCoverageEligibilityResponse.getServicedEnd()).isEqualTo(DEFAULT_SERVICED_END);
        assertThat(testCoverageEligibilityResponse.getDisposition()).isEqualTo(UPDATED_DISPOSITION);
        assertThat(testCoverageEligibilityResponse.getNotInforceReason()).isEqualTo(DEFAULT_NOT_INFORCE_REASON);
    }

    @Test
    @Transactional
    void fullUpdateCoverageEligibilityResponseWithPatch() throws Exception {
        // Initialize the database
        coverageEligibilityResponseRepository.saveAndFlush(coverageEligibilityResponse);

        int databaseSizeBeforeUpdate = coverageEligibilityResponseRepository.findAll().size();

        // Update the coverageEligibilityResponse using partial update
        CoverageEligibilityResponse partialUpdatedCoverageEligibilityResponse = new CoverageEligibilityResponse();
        partialUpdatedCoverageEligibilityResponse.setId(coverageEligibilityResponse.getId());

        partialUpdatedCoverageEligibilityResponse
            .value(UPDATED_VALUE)
            .system(UPDATED_SYSTEM)
            .parsed(UPDATED_PARSED)
            .outcome(UPDATED_OUTCOME)
            .serviced(UPDATED_SERVICED)
            .servicedEnd(UPDATED_SERVICED_END)
            .disposition(UPDATED_DISPOSITION)
            .notInforceReason(UPDATED_NOT_INFORCE_REASON);

        restCoverageEligibilityResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoverageEligibilityResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoverageEligibilityResponse))
            )
            .andExpect(status().isOk());

        // Validate the CoverageEligibilityResponse in the database
        List<CoverageEligibilityResponse> coverageEligibilityResponseList = coverageEligibilityResponseRepository.findAll();
        assertThat(coverageEligibilityResponseList).hasSize(databaseSizeBeforeUpdate);
        CoverageEligibilityResponse testCoverageEligibilityResponse = coverageEligibilityResponseList.get(
            coverageEligibilityResponseList.size() - 1
        );
        assertThat(testCoverageEligibilityResponse.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCoverageEligibilityResponse.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testCoverageEligibilityResponse.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testCoverageEligibilityResponse.getOutcome()).isEqualTo(UPDATED_OUTCOME);
        assertThat(testCoverageEligibilityResponse.getServiced()).isEqualTo(UPDATED_SERVICED);
        assertThat(testCoverageEligibilityResponse.getServicedEnd()).isEqualTo(UPDATED_SERVICED_END);
        assertThat(testCoverageEligibilityResponse.getDisposition()).isEqualTo(UPDATED_DISPOSITION);
        assertThat(testCoverageEligibilityResponse.getNotInforceReason()).isEqualTo(UPDATED_NOT_INFORCE_REASON);
    }

    @Test
    @Transactional
    void patchNonExistingCoverageEligibilityResponse() throws Exception {
        int databaseSizeBeforeUpdate = coverageEligibilityResponseRepository.findAll().size();
        coverageEligibilityResponse.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoverageEligibilityResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, coverageEligibilityResponse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoverageEligibilityResponse in the database
        List<CoverageEligibilityResponse> coverageEligibilityResponseList = coverageEligibilityResponseRepository.findAll();
        assertThat(coverageEligibilityResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCoverageEligibilityResponse() throws Exception {
        int databaseSizeBeforeUpdate = coverageEligibilityResponseRepository.findAll().size();
        coverageEligibilityResponse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoverageEligibilityResponseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityResponse))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoverageEligibilityResponse in the database
        List<CoverageEligibilityResponse> coverageEligibilityResponseList = coverageEligibilityResponseRepository.findAll();
        assertThat(coverageEligibilityResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCoverageEligibilityResponse() throws Exception {
        int databaseSizeBeforeUpdate = coverageEligibilityResponseRepository.findAll().size();
        coverageEligibilityResponse.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoverageEligibilityResponseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityResponse))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CoverageEligibilityResponse in the database
        List<CoverageEligibilityResponse> coverageEligibilityResponseList = coverageEligibilityResponseRepository.findAll();
        assertThat(coverageEligibilityResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCoverageEligibilityResponse() throws Exception {
        // Initialize the database
        coverageEligibilityResponseRepository.saveAndFlush(coverageEligibilityResponse);

        int databaseSizeBeforeDelete = coverageEligibilityResponseRepository.findAll().size();

        // Delete the coverageEligibilityResponse
        restCoverageEligibilityResponseMockMvc
            .perform(delete(ENTITY_API_URL_ID, coverageEligibilityResponse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CoverageEligibilityResponse> coverageEligibilityResponseList = coverageEligibilityResponseRepository.findAll();
        assertThat(coverageEligibilityResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
