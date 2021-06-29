package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ResponseInsurance;
import com.platformsandsolutions.hcpnphiesportal.repository.ResponseInsuranceRepository;
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
 * Integration tests for the {@link ResponseInsuranceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResponseInsuranceResourceIT {

    private static final String DEFAULT_NOT_INFORCE_REASON = "AAAAAAAAAA";
    private static final String UPDATED_NOT_INFORCE_REASON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INFORCE = false;
    private static final Boolean UPDATED_INFORCE = true;

    private static final Instant DEFAULT_BENEFIT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BENEFIT_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BENEFIT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BENEFIT_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/response-insurances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResponseInsuranceRepository responseInsuranceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponseInsuranceMockMvc;

    private ResponseInsurance responseInsurance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponseInsurance createEntity(EntityManager em) {
        ResponseInsurance responseInsurance = new ResponseInsurance()
            .notInforceReason(DEFAULT_NOT_INFORCE_REASON)
            .inforce(DEFAULT_INFORCE)
            .benefitStart(DEFAULT_BENEFIT_START)
            .benefitEnd(DEFAULT_BENEFIT_END);
        return responseInsurance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponseInsurance createUpdatedEntity(EntityManager em) {
        ResponseInsurance responseInsurance = new ResponseInsurance()
            .notInforceReason(UPDATED_NOT_INFORCE_REASON)
            .inforce(UPDATED_INFORCE)
            .benefitStart(UPDATED_BENEFIT_START)
            .benefitEnd(UPDATED_BENEFIT_END);
        return responseInsurance;
    }

    @BeforeEach
    public void initTest() {
        responseInsurance = createEntity(em);
    }

    @Test
    @Transactional
    void createResponseInsurance() throws Exception {
        int databaseSizeBeforeCreate = responseInsuranceRepository.findAll().size();
        // Create the ResponseInsurance
        restResponseInsuranceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responseInsurance))
            )
            .andExpect(status().isCreated());

        // Validate the ResponseInsurance in the database
        List<ResponseInsurance> responseInsuranceList = responseInsuranceRepository.findAll();
        assertThat(responseInsuranceList).hasSize(databaseSizeBeforeCreate + 1);
        ResponseInsurance testResponseInsurance = responseInsuranceList.get(responseInsuranceList.size() - 1);
        assertThat(testResponseInsurance.getNotInforceReason()).isEqualTo(DEFAULT_NOT_INFORCE_REASON);
        assertThat(testResponseInsurance.getInforce()).isEqualTo(DEFAULT_INFORCE);
        assertThat(testResponseInsurance.getBenefitStart()).isEqualTo(DEFAULT_BENEFIT_START);
        assertThat(testResponseInsurance.getBenefitEnd()).isEqualTo(DEFAULT_BENEFIT_END);
    }

    @Test
    @Transactional
    void createResponseInsuranceWithExistingId() throws Exception {
        // Create the ResponseInsurance with an existing ID
        responseInsurance.setId(1L);

        int databaseSizeBeforeCreate = responseInsuranceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponseInsuranceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responseInsurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseInsurance in the database
        List<ResponseInsurance> responseInsuranceList = responseInsuranceRepository.findAll();
        assertThat(responseInsuranceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResponseInsurances() throws Exception {
        // Initialize the database
        responseInsuranceRepository.saveAndFlush(responseInsurance);

        // Get all the responseInsuranceList
        restResponseInsuranceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responseInsurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].notInforceReason").value(hasItem(DEFAULT_NOT_INFORCE_REASON)))
            .andExpect(jsonPath("$.[*].inforce").value(hasItem(DEFAULT_INFORCE.booleanValue())))
            .andExpect(jsonPath("$.[*].benefitStart").value(hasItem(DEFAULT_BENEFIT_START.toString())))
            .andExpect(jsonPath("$.[*].benefitEnd").value(hasItem(DEFAULT_BENEFIT_END.toString())));
    }

    @Test
    @Transactional
    void getResponseInsurance() throws Exception {
        // Initialize the database
        responseInsuranceRepository.saveAndFlush(responseInsurance);

        // Get the responseInsurance
        restResponseInsuranceMockMvc
            .perform(get(ENTITY_API_URL_ID, responseInsurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responseInsurance.getId().intValue()))
            .andExpect(jsonPath("$.notInforceReason").value(DEFAULT_NOT_INFORCE_REASON))
            .andExpect(jsonPath("$.inforce").value(DEFAULT_INFORCE.booleanValue()))
            .andExpect(jsonPath("$.benefitStart").value(DEFAULT_BENEFIT_START.toString()))
            .andExpect(jsonPath("$.benefitEnd").value(DEFAULT_BENEFIT_END.toString()));
    }

    @Test
    @Transactional
    void getNonExistingResponseInsurance() throws Exception {
        // Get the responseInsurance
        restResponseInsuranceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResponseInsurance() throws Exception {
        // Initialize the database
        responseInsuranceRepository.saveAndFlush(responseInsurance);

        int databaseSizeBeforeUpdate = responseInsuranceRepository.findAll().size();

        // Update the responseInsurance
        ResponseInsurance updatedResponseInsurance = responseInsuranceRepository.findById(responseInsurance.getId()).get();
        // Disconnect from session so that the updates on updatedResponseInsurance are not directly saved in db
        em.detach(updatedResponseInsurance);
        updatedResponseInsurance
            .notInforceReason(UPDATED_NOT_INFORCE_REASON)
            .inforce(UPDATED_INFORCE)
            .benefitStart(UPDATED_BENEFIT_START)
            .benefitEnd(UPDATED_BENEFIT_END);

        restResponseInsuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResponseInsurance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResponseInsurance))
            )
            .andExpect(status().isOk());

        // Validate the ResponseInsurance in the database
        List<ResponseInsurance> responseInsuranceList = responseInsuranceRepository.findAll();
        assertThat(responseInsuranceList).hasSize(databaseSizeBeforeUpdate);
        ResponseInsurance testResponseInsurance = responseInsuranceList.get(responseInsuranceList.size() - 1);
        assertThat(testResponseInsurance.getNotInforceReason()).isEqualTo(UPDATED_NOT_INFORCE_REASON);
        assertThat(testResponseInsurance.getInforce()).isEqualTo(UPDATED_INFORCE);
        assertThat(testResponseInsurance.getBenefitStart()).isEqualTo(UPDATED_BENEFIT_START);
        assertThat(testResponseInsurance.getBenefitEnd()).isEqualTo(UPDATED_BENEFIT_END);
    }

    @Test
    @Transactional
    void putNonExistingResponseInsurance() throws Exception {
        int databaseSizeBeforeUpdate = responseInsuranceRepository.findAll().size();
        responseInsurance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponseInsuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responseInsurance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responseInsurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseInsurance in the database
        List<ResponseInsurance> responseInsuranceList = responseInsuranceRepository.findAll();
        assertThat(responseInsuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponseInsurance() throws Exception {
        int databaseSizeBeforeUpdate = responseInsuranceRepository.findAll().size();
        responseInsurance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseInsuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responseInsurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseInsurance in the database
        List<ResponseInsurance> responseInsuranceList = responseInsuranceRepository.findAll();
        assertThat(responseInsuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponseInsurance() throws Exception {
        int databaseSizeBeforeUpdate = responseInsuranceRepository.findAll().size();
        responseInsurance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseInsuranceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responseInsurance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponseInsurance in the database
        List<ResponseInsurance> responseInsuranceList = responseInsuranceRepository.findAll();
        assertThat(responseInsuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponseInsuranceWithPatch() throws Exception {
        // Initialize the database
        responseInsuranceRepository.saveAndFlush(responseInsurance);

        int databaseSizeBeforeUpdate = responseInsuranceRepository.findAll().size();

        // Update the responseInsurance using partial update
        ResponseInsurance partialUpdatedResponseInsurance = new ResponseInsurance();
        partialUpdatedResponseInsurance.setId(responseInsurance.getId());

        partialUpdatedResponseInsurance.benefitStart(UPDATED_BENEFIT_START);

        restResponseInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponseInsurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponseInsurance))
            )
            .andExpect(status().isOk());

        // Validate the ResponseInsurance in the database
        List<ResponseInsurance> responseInsuranceList = responseInsuranceRepository.findAll();
        assertThat(responseInsuranceList).hasSize(databaseSizeBeforeUpdate);
        ResponseInsurance testResponseInsurance = responseInsuranceList.get(responseInsuranceList.size() - 1);
        assertThat(testResponseInsurance.getNotInforceReason()).isEqualTo(DEFAULT_NOT_INFORCE_REASON);
        assertThat(testResponseInsurance.getInforce()).isEqualTo(DEFAULT_INFORCE);
        assertThat(testResponseInsurance.getBenefitStart()).isEqualTo(UPDATED_BENEFIT_START);
        assertThat(testResponseInsurance.getBenefitEnd()).isEqualTo(DEFAULT_BENEFIT_END);
    }

    @Test
    @Transactional
    void fullUpdateResponseInsuranceWithPatch() throws Exception {
        // Initialize the database
        responseInsuranceRepository.saveAndFlush(responseInsurance);

        int databaseSizeBeforeUpdate = responseInsuranceRepository.findAll().size();

        // Update the responseInsurance using partial update
        ResponseInsurance partialUpdatedResponseInsurance = new ResponseInsurance();
        partialUpdatedResponseInsurance.setId(responseInsurance.getId());

        partialUpdatedResponseInsurance
            .notInforceReason(UPDATED_NOT_INFORCE_REASON)
            .inforce(UPDATED_INFORCE)
            .benefitStart(UPDATED_BENEFIT_START)
            .benefitEnd(UPDATED_BENEFIT_END);

        restResponseInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponseInsurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponseInsurance))
            )
            .andExpect(status().isOk());

        // Validate the ResponseInsurance in the database
        List<ResponseInsurance> responseInsuranceList = responseInsuranceRepository.findAll();
        assertThat(responseInsuranceList).hasSize(databaseSizeBeforeUpdate);
        ResponseInsurance testResponseInsurance = responseInsuranceList.get(responseInsuranceList.size() - 1);
        assertThat(testResponseInsurance.getNotInforceReason()).isEqualTo(UPDATED_NOT_INFORCE_REASON);
        assertThat(testResponseInsurance.getInforce()).isEqualTo(UPDATED_INFORCE);
        assertThat(testResponseInsurance.getBenefitStart()).isEqualTo(UPDATED_BENEFIT_START);
        assertThat(testResponseInsurance.getBenefitEnd()).isEqualTo(UPDATED_BENEFIT_END);
    }

    @Test
    @Transactional
    void patchNonExistingResponseInsurance() throws Exception {
        int databaseSizeBeforeUpdate = responseInsuranceRepository.findAll().size();
        responseInsurance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponseInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responseInsurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responseInsurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseInsurance in the database
        List<ResponseInsurance> responseInsuranceList = responseInsuranceRepository.findAll();
        assertThat(responseInsuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponseInsurance() throws Exception {
        int databaseSizeBeforeUpdate = responseInsuranceRepository.findAll().size();
        responseInsurance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responseInsurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseInsurance in the database
        List<ResponseInsurance> responseInsuranceList = responseInsuranceRepository.findAll();
        assertThat(responseInsuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponseInsurance() throws Exception {
        int databaseSizeBeforeUpdate = responseInsuranceRepository.findAll().size();
        responseInsurance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responseInsurance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponseInsurance in the database
        List<ResponseInsurance> responseInsuranceList = responseInsuranceRepository.findAll();
        assertThat(responseInsuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponseInsurance() throws Exception {
        // Initialize the database
        responseInsuranceRepository.saveAndFlush(responseInsurance);

        int databaseSizeBeforeDelete = responseInsuranceRepository.findAll().size();

        // Delete the responseInsurance
        restResponseInsuranceMockMvc
            .perform(delete(ENTITY_API_URL_ID, responseInsurance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResponseInsurance> responseInsuranceList = responseInsuranceRepository.findAll();
        assertThat(responseInsuranceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
