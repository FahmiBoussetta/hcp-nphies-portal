package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.InsuranceBenefit;
import com.platformsandsolutions.hcpnphiesportal.repository.InsuranceBenefitRepository;
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
 * Integration tests for the {@link InsuranceBenefitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InsuranceBenefitResourceIT {

    private static final String DEFAULT_ALLOWED = "AAAAAAAAAA";
    private static final String UPDATED_ALLOWED = "BBBBBBBBBB";

    private static final String DEFAULT_USED = "AAAAAAAAAA";
    private static final String UPDATED_USED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/insurance-benefits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InsuranceBenefitRepository insuranceBenefitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInsuranceBenefitMockMvc;

    private InsuranceBenefit insuranceBenefit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InsuranceBenefit createEntity(EntityManager em) {
        InsuranceBenefit insuranceBenefit = new InsuranceBenefit().allowed(DEFAULT_ALLOWED).used(DEFAULT_USED);
        return insuranceBenefit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InsuranceBenefit createUpdatedEntity(EntityManager em) {
        InsuranceBenefit insuranceBenefit = new InsuranceBenefit().allowed(UPDATED_ALLOWED).used(UPDATED_USED);
        return insuranceBenefit;
    }

    @BeforeEach
    public void initTest() {
        insuranceBenefit = createEntity(em);
    }

    @Test
    @Transactional
    void createInsuranceBenefit() throws Exception {
        int databaseSizeBeforeCreate = insuranceBenefitRepository.findAll().size();
        // Create the InsuranceBenefit
        restInsuranceBenefitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insuranceBenefit))
            )
            .andExpect(status().isCreated());

        // Validate the InsuranceBenefit in the database
        List<InsuranceBenefit> insuranceBenefitList = insuranceBenefitRepository.findAll();
        assertThat(insuranceBenefitList).hasSize(databaseSizeBeforeCreate + 1);
        InsuranceBenefit testInsuranceBenefit = insuranceBenefitList.get(insuranceBenefitList.size() - 1);
        assertThat(testInsuranceBenefit.getAllowed()).isEqualTo(DEFAULT_ALLOWED);
        assertThat(testInsuranceBenefit.getUsed()).isEqualTo(DEFAULT_USED);
    }

    @Test
    @Transactional
    void createInsuranceBenefitWithExistingId() throws Exception {
        // Create the InsuranceBenefit with an existing ID
        insuranceBenefit.setId(1L);

        int databaseSizeBeforeCreate = insuranceBenefitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsuranceBenefitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insuranceBenefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the InsuranceBenefit in the database
        List<InsuranceBenefit> insuranceBenefitList = insuranceBenefitRepository.findAll();
        assertThat(insuranceBenefitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInsuranceBenefits() throws Exception {
        // Initialize the database
        insuranceBenefitRepository.saveAndFlush(insuranceBenefit);

        // Get all the insuranceBenefitList
        restInsuranceBenefitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insuranceBenefit.getId().intValue())))
            .andExpect(jsonPath("$.[*].allowed").value(hasItem(DEFAULT_ALLOWED)))
            .andExpect(jsonPath("$.[*].used").value(hasItem(DEFAULT_USED)));
    }

    @Test
    @Transactional
    void getInsuranceBenefit() throws Exception {
        // Initialize the database
        insuranceBenefitRepository.saveAndFlush(insuranceBenefit);

        // Get the insuranceBenefit
        restInsuranceBenefitMockMvc
            .perform(get(ENTITY_API_URL_ID, insuranceBenefit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(insuranceBenefit.getId().intValue()))
            .andExpect(jsonPath("$.allowed").value(DEFAULT_ALLOWED))
            .andExpect(jsonPath("$.used").value(DEFAULT_USED));
    }

    @Test
    @Transactional
    void getNonExistingInsuranceBenefit() throws Exception {
        // Get the insuranceBenefit
        restInsuranceBenefitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInsuranceBenefit() throws Exception {
        // Initialize the database
        insuranceBenefitRepository.saveAndFlush(insuranceBenefit);

        int databaseSizeBeforeUpdate = insuranceBenefitRepository.findAll().size();

        // Update the insuranceBenefit
        InsuranceBenefit updatedInsuranceBenefit = insuranceBenefitRepository.findById(insuranceBenefit.getId()).get();
        // Disconnect from session so that the updates on updatedInsuranceBenefit are not directly saved in db
        em.detach(updatedInsuranceBenefit);
        updatedInsuranceBenefit.allowed(UPDATED_ALLOWED).used(UPDATED_USED);

        restInsuranceBenefitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInsuranceBenefit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInsuranceBenefit))
            )
            .andExpect(status().isOk());

        // Validate the InsuranceBenefit in the database
        List<InsuranceBenefit> insuranceBenefitList = insuranceBenefitRepository.findAll();
        assertThat(insuranceBenefitList).hasSize(databaseSizeBeforeUpdate);
        InsuranceBenefit testInsuranceBenefit = insuranceBenefitList.get(insuranceBenefitList.size() - 1);
        assertThat(testInsuranceBenefit.getAllowed()).isEqualTo(UPDATED_ALLOWED);
        assertThat(testInsuranceBenefit.getUsed()).isEqualTo(UPDATED_USED);
    }

    @Test
    @Transactional
    void putNonExistingInsuranceBenefit() throws Exception {
        int databaseSizeBeforeUpdate = insuranceBenefitRepository.findAll().size();
        insuranceBenefit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsuranceBenefitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, insuranceBenefit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(insuranceBenefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the InsuranceBenefit in the database
        List<InsuranceBenefit> insuranceBenefitList = insuranceBenefitRepository.findAll();
        assertThat(insuranceBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInsuranceBenefit() throws Exception {
        int databaseSizeBeforeUpdate = insuranceBenefitRepository.findAll().size();
        insuranceBenefit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsuranceBenefitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(insuranceBenefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the InsuranceBenefit in the database
        List<InsuranceBenefit> insuranceBenefitList = insuranceBenefitRepository.findAll();
        assertThat(insuranceBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInsuranceBenefit() throws Exception {
        int databaseSizeBeforeUpdate = insuranceBenefitRepository.findAll().size();
        insuranceBenefit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsuranceBenefitMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insuranceBenefit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InsuranceBenefit in the database
        List<InsuranceBenefit> insuranceBenefitList = insuranceBenefitRepository.findAll();
        assertThat(insuranceBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInsuranceBenefitWithPatch() throws Exception {
        // Initialize the database
        insuranceBenefitRepository.saveAndFlush(insuranceBenefit);

        int databaseSizeBeforeUpdate = insuranceBenefitRepository.findAll().size();

        // Update the insuranceBenefit using partial update
        InsuranceBenefit partialUpdatedInsuranceBenefit = new InsuranceBenefit();
        partialUpdatedInsuranceBenefit.setId(insuranceBenefit.getId());

        partialUpdatedInsuranceBenefit.allowed(UPDATED_ALLOWED);

        restInsuranceBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInsuranceBenefit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInsuranceBenefit))
            )
            .andExpect(status().isOk());

        // Validate the InsuranceBenefit in the database
        List<InsuranceBenefit> insuranceBenefitList = insuranceBenefitRepository.findAll();
        assertThat(insuranceBenefitList).hasSize(databaseSizeBeforeUpdate);
        InsuranceBenefit testInsuranceBenefit = insuranceBenefitList.get(insuranceBenefitList.size() - 1);
        assertThat(testInsuranceBenefit.getAllowed()).isEqualTo(UPDATED_ALLOWED);
        assertThat(testInsuranceBenefit.getUsed()).isEqualTo(DEFAULT_USED);
    }

    @Test
    @Transactional
    void fullUpdateInsuranceBenefitWithPatch() throws Exception {
        // Initialize the database
        insuranceBenefitRepository.saveAndFlush(insuranceBenefit);

        int databaseSizeBeforeUpdate = insuranceBenefitRepository.findAll().size();

        // Update the insuranceBenefit using partial update
        InsuranceBenefit partialUpdatedInsuranceBenefit = new InsuranceBenefit();
        partialUpdatedInsuranceBenefit.setId(insuranceBenefit.getId());

        partialUpdatedInsuranceBenefit.allowed(UPDATED_ALLOWED).used(UPDATED_USED);

        restInsuranceBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInsuranceBenefit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInsuranceBenefit))
            )
            .andExpect(status().isOk());

        // Validate the InsuranceBenefit in the database
        List<InsuranceBenefit> insuranceBenefitList = insuranceBenefitRepository.findAll();
        assertThat(insuranceBenefitList).hasSize(databaseSizeBeforeUpdate);
        InsuranceBenefit testInsuranceBenefit = insuranceBenefitList.get(insuranceBenefitList.size() - 1);
        assertThat(testInsuranceBenefit.getAllowed()).isEqualTo(UPDATED_ALLOWED);
        assertThat(testInsuranceBenefit.getUsed()).isEqualTo(UPDATED_USED);
    }

    @Test
    @Transactional
    void patchNonExistingInsuranceBenefit() throws Exception {
        int databaseSizeBeforeUpdate = insuranceBenefitRepository.findAll().size();
        insuranceBenefit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsuranceBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, insuranceBenefit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(insuranceBenefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the InsuranceBenefit in the database
        List<InsuranceBenefit> insuranceBenefitList = insuranceBenefitRepository.findAll();
        assertThat(insuranceBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInsuranceBenefit() throws Exception {
        int databaseSizeBeforeUpdate = insuranceBenefitRepository.findAll().size();
        insuranceBenefit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsuranceBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(insuranceBenefit))
            )
            .andExpect(status().isBadRequest());

        // Validate the InsuranceBenefit in the database
        List<InsuranceBenefit> insuranceBenefitList = insuranceBenefitRepository.findAll();
        assertThat(insuranceBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInsuranceBenefit() throws Exception {
        int databaseSizeBeforeUpdate = insuranceBenefitRepository.findAll().size();
        insuranceBenefit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsuranceBenefitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(insuranceBenefit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InsuranceBenefit in the database
        List<InsuranceBenefit> insuranceBenefitList = insuranceBenefitRepository.findAll();
        assertThat(insuranceBenefitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInsuranceBenefit() throws Exception {
        // Initialize the database
        insuranceBenefitRepository.saveAndFlush(insuranceBenefit);

        int databaseSizeBeforeDelete = insuranceBenefitRepository.findAll().size();

        // Delete the insuranceBenefit
        restInsuranceBenefitMockMvc
            .perform(delete(ENTITY_API_URL_ID, insuranceBenefit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InsuranceBenefit> insuranceBenefitList = insuranceBenefitRepository.findAll();
        assertThat(insuranceBenefitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
