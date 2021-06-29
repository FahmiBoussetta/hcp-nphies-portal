package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.CoverageEligibilityRequest;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.PriorityEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.CoverageEligibilityRequestRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CoverageEligibilityRequestResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CoverageEligibilityRequestResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_PARSED = "AAAAAAAAAA";
    private static final String UPDATED_PARSED = "BBBBBBBBBB";

    private static final PriorityEnum DEFAULT_PRIORITY = PriorityEnum.Stat;
    private static final PriorityEnum UPDATED_PRIORITY = PriorityEnum.Normal;

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final Instant DEFAULT_SERVICED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SERVICED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SERVICED_DATE_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SERVICED_DATE_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/coverage-eligibility-requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CoverageEligibilityRequestRepository coverageEligibilityRequestRepository;

    @Mock
    private CoverageEligibilityRequestRepository coverageEligibilityRequestRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoverageEligibilityRequestMockMvc;

    private CoverageEligibilityRequest coverageEligibilityRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoverageEligibilityRequest createEntity(EntityManager em) {
        CoverageEligibilityRequest coverageEligibilityRequest = new CoverageEligibilityRequest()
            .guid(DEFAULT_GUID)
            .parsed(DEFAULT_PARSED)
            .priority(DEFAULT_PRIORITY)
            .identifier(DEFAULT_IDENTIFIER)
            .servicedDate(DEFAULT_SERVICED_DATE)
            .servicedDateEnd(DEFAULT_SERVICED_DATE_END);
        return coverageEligibilityRequest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoverageEligibilityRequest createUpdatedEntity(EntityManager em) {
        CoverageEligibilityRequest coverageEligibilityRequest = new CoverageEligibilityRequest()
            .guid(UPDATED_GUID)
            .parsed(UPDATED_PARSED)
            .priority(UPDATED_PRIORITY)
            .identifier(UPDATED_IDENTIFIER)
            .servicedDate(UPDATED_SERVICED_DATE)
            .servicedDateEnd(UPDATED_SERVICED_DATE_END);
        return coverageEligibilityRequest;
    }

    @BeforeEach
    public void initTest() {
        coverageEligibilityRequest = createEntity(em);
    }

    @Test
    @Transactional
    void createCoverageEligibilityRequest() throws Exception {
        int databaseSizeBeforeCreate = coverageEligibilityRequestRepository.findAll().size();
        // Create the CoverageEligibilityRequest
        restCoverageEligibilityRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityRequest))
            )
            .andExpect(status().isCreated());

        // Validate the CoverageEligibilityRequest in the database
        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeCreate + 1);
        CoverageEligibilityRequest testCoverageEligibilityRequest = coverageEligibilityRequestList.get(
            coverageEligibilityRequestList.size() - 1
        );
        assertThat(testCoverageEligibilityRequest.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testCoverageEligibilityRequest.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testCoverageEligibilityRequest.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testCoverageEligibilityRequest.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testCoverageEligibilityRequest.getServicedDate()).isEqualTo(DEFAULT_SERVICED_DATE);
        assertThat(testCoverageEligibilityRequest.getServicedDateEnd()).isEqualTo(DEFAULT_SERVICED_DATE_END);
    }

    @Test
    @Transactional
    void createCoverageEligibilityRequestWithExistingId() throws Exception {
        // Create the CoverageEligibilityRequest with an existing ID
        coverageEligibilityRequest.setId(1L);

        int databaseSizeBeforeCreate = coverageEligibilityRequestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoverageEligibilityRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoverageEligibilityRequest in the database
        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = coverageEligibilityRequestRepository.findAll().size();
        // set the field null
        coverageEligibilityRequest.setPriority(null);

        // Create the CoverageEligibilityRequest, which fails.

        restCoverageEligibilityRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityRequest))
            )
            .andExpect(status().isBadRequest());

        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkServicedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = coverageEligibilityRequestRepository.findAll().size();
        // set the field null
        coverageEligibilityRequest.setServicedDate(null);

        // Create the CoverageEligibilityRequest, which fails.

        restCoverageEligibilityRequestMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityRequest))
            )
            .andExpect(status().isBadRequest());

        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCoverageEligibilityRequests() throws Exception {
        // Initialize the database
        coverageEligibilityRequestRepository.saveAndFlush(coverageEligibilityRequest);

        // Get all the coverageEligibilityRequestList
        restCoverageEligibilityRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coverageEligibilityRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].parsed").value(hasItem(DEFAULT_PARSED)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].servicedDate").value(hasItem(DEFAULT_SERVICED_DATE.toString())))
            .andExpect(jsonPath("$.[*].servicedDateEnd").value(hasItem(DEFAULT_SERVICED_DATE_END.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCoverageEligibilityRequestsWithEagerRelationshipsIsEnabled() throws Exception {
        when(coverageEligibilityRequestRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCoverageEligibilityRequestMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(coverageEligibilityRequestRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCoverageEligibilityRequestsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(coverageEligibilityRequestRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCoverageEligibilityRequestMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(coverageEligibilityRequestRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getCoverageEligibilityRequest() throws Exception {
        // Initialize the database
        coverageEligibilityRequestRepository.saveAndFlush(coverageEligibilityRequest);

        // Get the coverageEligibilityRequest
        restCoverageEligibilityRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, coverageEligibilityRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coverageEligibilityRequest.getId().intValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.parsed").value(DEFAULT_PARSED))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.servicedDate").value(DEFAULT_SERVICED_DATE.toString()))
            .andExpect(jsonPath("$.servicedDateEnd").value(DEFAULT_SERVICED_DATE_END.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCoverageEligibilityRequest() throws Exception {
        // Get the coverageEligibilityRequest
        restCoverageEligibilityRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCoverageEligibilityRequest() throws Exception {
        // Initialize the database
        coverageEligibilityRequestRepository.saveAndFlush(coverageEligibilityRequest);

        int databaseSizeBeforeUpdate = coverageEligibilityRequestRepository.findAll().size();

        // Update the coverageEligibilityRequest
        CoverageEligibilityRequest updatedCoverageEligibilityRequest = coverageEligibilityRequestRepository
            .findById(coverageEligibilityRequest.getId())
            .get();
        // Disconnect from session so that the updates on updatedCoverageEligibilityRequest are not directly saved in db
        em.detach(updatedCoverageEligibilityRequest);
        updatedCoverageEligibilityRequest
            .guid(UPDATED_GUID)
            .parsed(UPDATED_PARSED)
            .priority(UPDATED_PRIORITY)
            .identifier(UPDATED_IDENTIFIER)
            .servicedDate(UPDATED_SERVICED_DATE)
            .servicedDateEnd(UPDATED_SERVICED_DATE_END);

        restCoverageEligibilityRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCoverageEligibilityRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCoverageEligibilityRequest))
            )
            .andExpect(status().isOk());

        // Validate the CoverageEligibilityRequest in the database
        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeUpdate);
        CoverageEligibilityRequest testCoverageEligibilityRequest = coverageEligibilityRequestList.get(
            coverageEligibilityRequestList.size() - 1
        );
        assertThat(testCoverageEligibilityRequest.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testCoverageEligibilityRequest.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testCoverageEligibilityRequest.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testCoverageEligibilityRequest.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testCoverageEligibilityRequest.getServicedDate()).isEqualTo(UPDATED_SERVICED_DATE);
        assertThat(testCoverageEligibilityRequest.getServicedDateEnd()).isEqualTo(UPDATED_SERVICED_DATE_END);
    }

    @Test
    @Transactional
    void putNonExistingCoverageEligibilityRequest() throws Exception {
        int databaseSizeBeforeUpdate = coverageEligibilityRequestRepository.findAll().size();
        coverageEligibilityRequest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoverageEligibilityRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, coverageEligibilityRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoverageEligibilityRequest in the database
        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCoverageEligibilityRequest() throws Exception {
        int databaseSizeBeforeUpdate = coverageEligibilityRequestRepository.findAll().size();
        coverageEligibilityRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoverageEligibilityRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoverageEligibilityRequest in the database
        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCoverageEligibilityRequest() throws Exception {
        int databaseSizeBeforeUpdate = coverageEligibilityRequestRepository.findAll().size();
        coverageEligibilityRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoverageEligibilityRequestMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityRequest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CoverageEligibilityRequest in the database
        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCoverageEligibilityRequestWithPatch() throws Exception {
        // Initialize the database
        coverageEligibilityRequestRepository.saveAndFlush(coverageEligibilityRequest);

        int databaseSizeBeforeUpdate = coverageEligibilityRequestRepository.findAll().size();

        // Update the coverageEligibilityRequest using partial update
        CoverageEligibilityRequest partialUpdatedCoverageEligibilityRequest = new CoverageEligibilityRequest();
        partialUpdatedCoverageEligibilityRequest.setId(coverageEligibilityRequest.getId());

        partialUpdatedCoverageEligibilityRequest.guid(UPDATED_GUID).parsed(UPDATED_PARSED).servicedDateEnd(UPDATED_SERVICED_DATE_END);

        restCoverageEligibilityRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoverageEligibilityRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoverageEligibilityRequest))
            )
            .andExpect(status().isOk());

        // Validate the CoverageEligibilityRequest in the database
        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeUpdate);
        CoverageEligibilityRequest testCoverageEligibilityRequest = coverageEligibilityRequestList.get(
            coverageEligibilityRequestList.size() - 1
        );
        assertThat(testCoverageEligibilityRequest.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testCoverageEligibilityRequest.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testCoverageEligibilityRequest.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testCoverageEligibilityRequest.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testCoverageEligibilityRequest.getServicedDate()).isEqualTo(DEFAULT_SERVICED_DATE);
        assertThat(testCoverageEligibilityRequest.getServicedDateEnd()).isEqualTo(UPDATED_SERVICED_DATE_END);
    }

    @Test
    @Transactional
    void fullUpdateCoverageEligibilityRequestWithPatch() throws Exception {
        // Initialize the database
        coverageEligibilityRequestRepository.saveAndFlush(coverageEligibilityRequest);

        int databaseSizeBeforeUpdate = coverageEligibilityRequestRepository.findAll().size();

        // Update the coverageEligibilityRequest using partial update
        CoverageEligibilityRequest partialUpdatedCoverageEligibilityRequest = new CoverageEligibilityRequest();
        partialUpdatedCoverageEligibilityRequest.setId(coverageEligibilityRequest.getId());

        partialUpdatedCoverageEligibilityRequest
            .guid(UPDATED_GUID)
            .parsed(UPDATED_PARSED)
            .priority(UPDATED_PRIORITY)
            .identifier(UPDATED_IDENTIFIER)
            .servicedDate(UPDATED_SERVICED_DATE)
            .servicedDateEnd(UPDATED_SERVICED_DATE_END);

        restCoverageEligibilityRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoverageEligibilityRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoverageEligibilityRequest))
            )
            .andExpect(status().isOk());

        // Validate the CoverageEligibilityRequest in the database
        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeUpdate);
        CoverageEligibilityRequest testCoverageEligibilityRequest = coverageEligibilityRequestList.get(
            coverageEligibilityRequestList.size() - 1
        );
        assertThat(testCoverageEligibilityRequest.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testCoverageEligibilityRequest.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testCoverageEligibilityRequest.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testCoverageEligibilityRequest.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testCoverageEligibilityRequest.getServicedDate()).isEqualTo(UPDATED_SERVICED_DATE);
        assertThat(testCoverageEligibilityRequest.getServicedDateEnd()).isEqualTo(UPDATED_SERVICED_DATE_END);
    }

    @Test
    @Transactional
    void patchNonExistingCoverageEligibilityRequest() throws Exception {
        int databaseSizeBeforeUpdate = coverageEligibilityRequestRepository.findAll().size();
        coverageEligibilityRequest.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoverageEligibilityRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, coverageEligibilityRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoverageEligibilityRequest in the database
        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCoverageEligibilityRequest() throws Exception {
        int databaseSizeBeforeUpdate = coverageEligibilityRequestRepository.findAll().size();
        coverageEligibilityRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoverageEligibilityRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityRequest))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoverageEligibilityRequest in the database
        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCoverageEligibilityRequest() throws Exception {
        int databaseSizeBeforeUpdate = coverageEligibilityRequestRepository.findAll().size();
        coverageEligibilityRequest.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoverageEligibilityRequestMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coverageEligibilityRequest))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CoverageEligibilityRequest in the database
        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCoverageEligibilityRequest() throws Exception {
        // Initialize the database
        coverageEligibilityRequestRepository.saveAndFlush(coverageEligibilityRequest);

        int databaseSizeBeforeDelete = coverageEligibilityRequestRepository.findAll().size();

        // Delete the coverageEligibilityRequest
        restCoverageEligibilityRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, coverageEligibilityRequest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CoverageEligibilityRequest> coverageEligibilityRequestList = coverageEligibilityRequestRepository.findAll();
        assertThat(coverageEligibilityRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
