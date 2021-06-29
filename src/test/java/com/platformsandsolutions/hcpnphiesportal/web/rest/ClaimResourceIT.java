package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Claim;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ClaimSubTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ClaimTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.FundsReserveEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.PriorityEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.Use;
import com.platformsandsolutions.hcpnphiesportal.repository.ClaimRepository;
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
 * Integration tests for the {@link ClaimResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_QUEUED = false;
    private static final Boolean UPDATED_IS_QUEUED = true;

    private static final String DEFAULT_PARSED = "AAAAAAAAAA";
    private static final String UPDATED_PARSED = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final Use DEFAULT_USE = Use.Claim;
    private static final Use UPDATED_USE = Use.PreAuthorization;

    private static final ClaimTypeEnum DEFAULT_TYPE = ClaimTypeEnum.Institutional;
    private static final ClaimTypeEnum UPDATED_TYPE = ClaimTypeEnum.Oral;

    private static final ClaimSubTypeEnum DEFAULT_SUB_TYPE = ClaimSubTypeEnum.Ip;
    private static final ClaimSubTypeEnum UPDATED_SUB_TYPE = ClaimSubTypeEnum.Op;

    private static final String DEFAULT_ELIGIBILITY_OFFLINE = "AAAAAAAAAA";
    private static final String UPDATED_ELIGIBILITY_OFFLINE = "BBBBBBBBBB";

    private static final Instant DEFAULT_ELIGIBILITY_OFFLINE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ELIGIBILITY_OFFLINE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_AUTHORIZATION_OFFLINE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AUTHORIZATION_OFFLINE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BILLABLE_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BILLABLE_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BILLABLE_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BILLABLE_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final PriorityEnum DEFAULT_PRIORITY = PriorityEnum.Stat;
    private static final PriorityEnum UPDATED_PRIORITY = PriorityEnum.Normal;

    private static final FundsReserveEnum DEFAULT_FUNDS_RESERVE = FundsReserveEnum.Patient;
    private static final FundsReserveEnum UPDATED_FUNDS_RESERVE = FundsReserveEnum.Provider;

    private static final String ENTITY_API_URL = "/api/claims";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimMockMvc;

    private Claim claim;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Claim createEntity(EntityManager em) {
        Claim claim = new Claim()
            .guid(DEFAULT_GUID)
            .isQueued(DEFAULT_IS_QUEUED)
            .parsed(DEFAULT_PARSED)
            .identifier(DEFAULT_IDENTIFIER)
            .use(DEFAULT_USE)
            .type(DEFAULT_TYPE)
            .subType(DEFAULT_SUB_TYPE)
            .eligibilityOffline(DEFAULT_ELIGIBILITY_OFFLINE)
            .eligibilityOfflineDate(DEFAULT_ELIGIBILITY_OFFLINE_DATE)
            .authorizationOfflineDate(DEFAULT_AUTHORIZATION_OFFLINE_DATE)
            .billableStart(DEFAULT_BILLABLE_START)
            .billableEnd(DEFAULT_BILLABLE_END)
            .priority(DEFAULT_PRIORITY)
            .fundsReserve(DEFAULT_FUNDS_RESERVE);
        return claim;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Claim createUpdatedEntity(EntityManager em) {
        Claim claim = new Claim()
            .guid(UPDATED_GUID)
            .isQueued(UPDATED_IS_QUEUED)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .use(UPDATED_USE)
            .type(UPDATED_TYPE)
            .subType(UPDATED_SUB_TYPE)
            .eligibilityOffline(UPDATED_ELIGIBILITY_OFFLINE)
            .eligibilityOfflineDate(UPDATED_ELIGIBILITY_OFFLINE_DATE)
            .authorizationOfflineDate(UPDATED_AUTHORIZATION_OFFLINE_DATE)
            .billableStart(UPDATED_BILLABLE_START)
            .billableEnd(UPDATED_BILLABLE_END)
            .priority(UPDATED_PRIORITY)
            .fundsReserve(UPDATED_FUNDS_RESERVE);
        return claim;
    }

    @BeforeEach
    public void initTest() {
        claim = createEntity(em);
    }

    @Test
    @Transactional
    void createClaim() throws Exception {
        int databaseSizeBeforeCreate = claimRepository.findAll().size();
        // Create the Claim
        restClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isCreated());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeCreate + 1);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testClaim.getIsQueued()).isEqualTo(DEFAULT_IS_QUEUED);
        assertThat(testClaim.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testClaim.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testClaim.getUse()).isEqualTo(DEFAULT_USE);
        assertThat(testClaim.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testClaim.getSubType()).isEqualTo(DEFAULT_SUB_TYPE);
        assertThat(testClaim.getEligibilityOffline()).isEqualTo(DEFAULT_ELIGIBILITY_OFFLINE);
        assertThat(testClaim.getEligibilityOfflineDate()).isEqualTo(DEFAULT_ELIGIBILITY_OFFLINE_DATE);
        assertThat(testClaim.getAuthorizationOfflineDate()).isEqualTo(DEFAULT_AUTHORIZATION_OFFLINE_DATE);
        assertThat(testClaim.getBillableStart()).isEqualTo(DEFAULT_BILLABLE_START);
        assertThat(testClaim.getBillableEnd()).isEqualTo(DEFAULT_BILLABLE_END);
        assertThat(testClaim.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testClaim.getFundsReserve()).isEqualTo(DEFAULT_FUNDS_RESERVE);
    }

    @Test
    @Transactional
    void createClaimWithExistingId() throws Exception {
        // Create the Claim with an existing ID
        claim.setId(1L);

        int databaseSizeBeforeCreate = claimRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUseIsRequired() throws Exception {
        int databaseSizeBeforeTest = claimRepository.findAll().size();
        // set the field null
        claim.setUse(null);

        // Create the Claim, which fails.

        restClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isBadRequest());

        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = claimRepository.findAll().size();
        // set the field null
        claim.setType(null);

        // Create the Claim, which fails.

        restClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isBadRequest());

        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriorityIsRequired() throws Exception {
        int databaseSizeBeforeTest = claimRepository.findAll().size();
        // set the field null
        claim.setPriority(null);

        // Create the Claim, which fails.

        restClaimMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isBadRequest());

        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClaims() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList
        restClaimMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claim.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].isQueued").value(hasItem(DEFAULT_IS_QUEUED.booleanValue())))
            .andExpect(jsonPath("$.[*].parsed").value(hasItem(DEFAULT_PARSED)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].use").value(hasItem(DEFAULT_USE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].subType").value(hasItem(DEFAULT_SUB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].eligibilityOffline").value(hasItem(DEFAULT_ELIGIBILITY_OFFLINE)))
            .andExpect(jsonPath("$.[*].eligibilityOfflineDate").value(hasItem(DEFAULT_ELIGIBILITY_OFFLINE_DATE.toString())))
            .andExpect(jsonPath("$.[*].authorizationOfflineDate").value(hasItem(DEFAULT_AUTHORIZATION_OFFLINE_DATE.toString())))
            .andExpect(jsonPath("$.[*].billableStart").value(hasItem(DEFAULT_BILLABLE_START.toString())))
            .andExpect(jsonPath("$.[*].billableEnd").value(hasItem(DEFAULT_BILLABLE_END.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
            .andExpect(jsonPath("$.[*].fundsReserve").value(hasItem(DEFAULT_FUNDS_RESERVE.toString())));
    }

    @Test
    @Transactional
    void getClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get the claim
        restClaimMockMvc
            .perform(get(ENTITY_API_URL_ID, claim.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claim.getId().intValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.isQueued").value(DEFAULT_IS_QUEUED.booleanValue()))
            .andExpect(jsonPath("$.parsed").value(DEFAULT_PARSED))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.use").value(DEFAULT_USE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.subType").value(DEFAULT_SUB_TYPE.toString()))
            .andExpect(jsonPath("$.eligibilityOffline").value(DEFAULT_ELIGIBILITY_OFFLINE))
            .andExpect(jsonPath("$.eligibilityOfflineDate").value(DEFAULT_ELIGIBILITY_OFFLINE_DATE.toString()))
            .andExpect(jsonPath("$.authorizationOfflineDate").value(DEFAULT_AUTHORIZATION_OFFLINE_DATE.toString()))
            .andExpect(jsonPath("$.billableStart").value(DEFAULT_BILLABLE_START.toString()))
            .andExpect(jsonPath("$.billableEnd").value(DEFAULT_BILLABLE_END.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.fundsReserve").value(DEFAULT_FUNDS_RESERVE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingClaim() throws Exception {
        // Get the claim
        restClaimMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim
        Claim updatedClaim = claimRepository.findById(claim.getId()).get();
        // Disconnect from session so that the updates on updatedClaim are not directly saved in db
        em.detach(updatedClaim);
        updatedClaim
            .guid(UPDATED_GUID)
            .isQueued(UPDATED_IS_QUEUED)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .use(UPDATED_USE)
            .type(UPDATED_TYPE)
            .subType(UPDATED_SUB_TYPE)
            .eligibilityOffline(UPDATED_ELIGIBILITY_OFFLINE)
            .eligibilityOfflineDate(UPDATED_ELIGIBILITY_OFFLINE_DATE)
            .authorizationOfflineDate(UPDATED_AUTHORIZATION_OFFLINE_DATE)
            .billableStart(UPDATED_BILLABLE_START)
            .billableEnd(UPDATED_BILLABLE_END)
            .priority(UPDATED_PRIORITY)
            .fundsReserve(UPDATED_FUNDS_RESERVE);

        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClaim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClaim))
            )
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testClaim.getIsQueued()).isEqualTo(UPDATED_IS_QUEUED);
        assertThat(testClaim.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testClaim.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testClaim.getUse()).isEqualTo(UPDATED_USE);
        assertThat(testClaim.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testClaim.getSubType()).isEqualTo(UPDATED_SUB_TYPE);
        assertThat(testClaim.getEligibilityOffline()).isEqualTo(UPDATED_ELIGIBILITY_OFFLINE);
        assertThat(testClaim.getEligibilityOfflineDate()).isEqualTo(UPDATED_ELIGIBILITY_OFFLINE_DATE);
        assertThat(testClaim.getAuthorizationOfflineDate()).isEqualTo(UPDATED_AUTHORIZATION_OFFLINE_DATE);
        assertThat(testClaim.getBillableStart()).isEqualTo(UPDATED_BILLABLE_START);
        assertThat(testClaim.getBillableEnd()).isEqualTo(UPDATED_BILLABLE_END);
        assertThat(testClaim.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testClaim.getFundsReserve()).isEqualTo(UPDATED_FUNDS_RESERVE);
    }

    @Test
    @Transactional
    void putNonExistingClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claim.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimWithPatch() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim using partial update
        Claim partialUpdatedClaim = new Claim();
        partialUpdatedClaim.setId(claim.getId());

        partialUpdatedClaim
            .isQueued(UPDATED_IS_QUEUED)
            .parsed(UPDATED_PARSED)
            .subType(UPDATED_SUB_TYPE)
            .authorizationOfflineDate(UPDATED_AUTHORIZATION_OFFLINE_DATE)
            .billableStart(UPDATED_BILLABLE_START)
            .billableEnd(UPDATED_BILLABLE_END)
            .fundsReserve(UPDATED_FUNDS_RESERVE);

        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaim))
            )
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testClaim.getIsQueued()).isEqualTo(UPDATED_IS_QUEUED);
        assertThat(testClaim.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testClaim.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testClaim.getUse()).isEqualTo(DEFAULT_USE);
        assertThat(testClaim.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testClaim.getSubType()).isEqualTo(UPDATED_SUB_TYPE);
        assertThat(testClaim.getEligibilityOffline()).isEqualTo(DEFAULT_ELIGIBILITY_OFFLINE);
        assertThat(testClaim.getEligibilityOfflineDate()).isEqualTo(DEFAULT_ELIGIBILITY_OFFLINE_DATE);
        assertThat(testClaim.getAuthorizationOfflineDate()).isEqualTo(UPDATED_AUTHORIZATION_OFFLINE_DATE);
        assertThat(testClaim.getBillableStart()).isEqualTo(UPDATED_BILLABLE_START);
        assertThat(testClaim.getBillableEnd()).isEqualTo(UPDATED_BILLABLE_END);
        assertThat(testClaim.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testClaim.getFundsReserve()).isEqualTo(UPDATED_FUNDS_RESERVE);
    }

    @Test
    @Transactional
    void fullUpdateClaimWithPatch() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim using partial update
        Claim partialUpdatedClaim = new Claim();
        partialUpdatedClaim.setId(claim.getId());

        partialUpdatedClaim
            .guid(UPDATED_GUID)
            .isQueued(UPDATED_IS_QUEUED)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .use(UPDATED_USE)
            .type(UPDATED_TYPE)
            .subType(UPDATED_SUB_TYPE)
            .eligibilityOffline(UPDATED_ELIGIBILITY_OFFLINE)
            .eligibilityOfflineDate(UPDATED_ELIGIBILITY_OFFLINE_DATE)
            .authorizationOfflineDate(UPDATED_AUTHORIZATION_OFFLINE_DATE)
            .billableStart(UPDATED_BILLABLE_START)
            .billableEnd(UPDATED_BILLABLE_END)
            .priority(UPDATED_PRIORITY)
            .fundsReserve(UPDATED_FUNDS_RESERVE);

        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaim))
            )
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testClaim.getIsQueued()).isEqualTo(UPDATED_IS_QUEUED);
        assertThat(testClaim.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testClaim.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testClaim.getUse()).isEqualTo(UPDATED_USE);
        assertThat(testClaim.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testClaim.getSubType()).isEqualTo(UPDATED_SUB_TYPE);
        assertThat(testClaim.getEligibilityOffline()).isEqualTo(UPDATED_ELIGIBILITY_OFFLINE);
        assertThat(testClaim.getEligibilityOfflineDate()).isEqualTo(UPDATED_ELIGIBILITY_OFFLINE_DATE);
        assertThat(testClaim.getAuthorizationOfflineDate()).isEqualTo(UPDATED_AUTHORIZATION_OFFLINE_DATE);
        assertThat(testClaim.getBillableStart()).isEqualTo(UPDATED_BILLABLE_START);
        assertThat(testClaim.getBillableEnd()).isEqualTo(UPDATED_BILLABLE_END);
        assertThat(testClaim.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testClaim.getFundsReserve()).isEqualTo(UPDATED_FUNDS_RESERVE);
    }

    @Test
    @Transactional
    void patchNonExistingClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claim.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claim))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(claim)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeDelete = claimRepository.findAll().size();

        // Delete the claim
        restClaimMockMvc
            .perform(delete(ENTITY_API_URL_ID, claim.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
