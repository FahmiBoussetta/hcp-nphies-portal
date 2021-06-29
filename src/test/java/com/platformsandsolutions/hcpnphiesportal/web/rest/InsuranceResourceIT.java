package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Insurance;
import com.platformsandsolutions.hcpnphiesportal.repository.InsuranceRepository;
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
 * Integration tests for the {@link InsuranceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InsuranceResourceIT {

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final Boolean DEFAULT_FOCAL = false;
    private static final Boolean UPDATED_FOCAL = true;

    private static final String DEFAULT_PRE_AUTH_REF = "AAAAAAAAAA";
    private static final String UPDATED_PRE_AUTH_REF = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/insurances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInsuranceMockMvc;

    private Insurance insurance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Insurance createEntity(EntityManager em) {
        Insurance insurance = new Insurance().sequence(DEFAULT_SEQUENCE).focal(DEFAULT_FOCAL).preAuthRef(DEFAULT_PRE_AUTH_REF);
        return insurance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Insurance createUpdatedEntity(EntityManager em) {
        Insurance insurance = new Insurance().sequence(UPDATED_SEQUENCE).focal(UPDATED_FOCAL).preAuthRef(UPDATED_PRE_AUTH_REF);
        return insurance;
    }

    @BeforeEach
    public void initTest() {
        insurance = createEntity(em);
    }

    @Test
    @Transactional
    void createInsurance() throws Exception {
        int databaseSizeBeforeCreate = insuranceRepository.findAll().size();
        // Create the Insurance
        restInsuranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insurance)))
            .andExpect(status().isCreated());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeCreate + 1);
        Insurance testInsurance = insuranceList.get(insuranceList.size() - 1);
        assertThat(testInsurance.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testInsurance.getFocal()).isEqualTo(DEFAULT_FOCAL);
        assertThat(testInsurance.getPreAuthRef()).isEqualTo(DEFAULT_PRE_AUTH_REF);
    }

    @Test
    @Transactional
    void createInsuranceWithExistingId() throws Exception {
        // Create the Insurance with an existing ID
        insurance.setId(1L);

        int databaseSizeBeforeCreate = insuranceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsuranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insurance)))
            .andExpect(status().isBadRequest());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceRepository.findAll().size();
        // set the field null
        insurance.setSequence(null);

        // Create the Insurance, which fails.

        restInsuranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insurance)))
            .andExpect(status().isBadRequest());

        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFocalIsRequired() throws Exception {
        int databaseSizeBeforeTest = insuranceRepository.findAll().size();
        // set the field null
        insurance.setFocal(null);

        // Create the Insurance, which fails.

        restInsuranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insurance)))
            .andExpect(status().isBadRequest());

        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInsurances() throws Exception {
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);

        // Get all the insuranceList
        restInsuranceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].focal").value(hasItem(DEFAULT_FOCAL.booleanValue())))
            .andExpect(jsonPath("$.[*].preAuthRef").value(hasItem(DEFAULT_PRE_AUTH_REF)));
    }

    @Test
    @Transactional
    void getInsurance() throws Exception {
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);

        // Get the insurance
        restInsuranceMockMvc
            .perform(get(ENTITY_API_URL_ID, insurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(insurance.getId().intValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.focal").value(DEFAULT_FOCAL.booleanValue()))
            .andExpect(jsonPath("$.preAuthRef").value(DEFAULT_PRE_AUTH_REF));
    }

    @Test
    @Transactional
    void getNonExistingInsurance() throws Exception {
        // Get the insurance
        restInsuranceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInsurance() throws Exception {
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);

        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();

        // Update the insurance
        Insurance updatedInsurance = insuranceRepository.findById(insurance.getId()).get();
        // Disconnect from session so that the updates on updatedInsurance are not directly saved in db
        em.detach(updatedInsurance);
        updatedInsurance.sequence(UPDATED_SEQUENCE).focal(UPDATED_FOCAL).preAuthRef(UPDATED_PRE_AUTH_REF);

        restInsuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInsurance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInsurance))
            )
            .andExpect(status().isOk());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);
        Insurance testInsurance = insuranceList.get(insuranceList.size() - 1);
        assertThat(testInsurance.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testInsurance.getFocal()).isEqualTo(UPDATED_FOCAL);
        assertThat(testInsurance.getPreAuthRef()).isEqualTo(UPDATED_PRE_AUTH_REF);
    }

    @Test
    @Transactional
    void putNonExistingInsurance() throws Exception {
        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();
        insurance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, insurance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(insurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInsurance() throws Exception {
        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();
        insurance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsuranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(insurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInsurance() throws Exception {
        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();
        insurance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsuranceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(insurance)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInsuranceWithPatch() throws Exception {
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);

        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();

        // Update the insurance using partial update
        Insurance partialUpdatedInsurance = new Insurance();
        partialUpdatedInsurance.setId(insurance.getId());

        partialUpdatedInsurance.focal(UPDATED_FOCAL);

        restInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInsurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInsurance))
            )
            .andExpect(status().isOk());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);
        Insurance testInsurance = insuranceList.get(insuranceList.size() - 1);
        assertThat(testInsurance.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testInsurance.getFocal()).isEqualTo(UPDATED_FOCAL);
        assertThat(testInsurance.getPreAuthRef()).isEqualTo(DEFAULT_PRE_AUTH_REF);
    }

    @Test
    @Transactional
    void fullUpdateInsuranceWithPatch() throws Exception {
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);

        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();

        // Update the insurance using partial update
        Insurance partialUpdatedInsurance = new Insurance();
        partialUpdatedInsurance.setId(insurance.getId());

        partialUpdatedInsurance.sequence(UPDATED_SEQUENCE).focal(UPDATED_FOCAL).preAuthRef(UPDATED_PRE_AUTH_REF);

        restInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInsurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInsurance))
            )
            .andExpect(status().isOk());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);
        Insurance testInsurance = insuranceList.get(insuranceList.size() - 1);
        assertThat(testInsurance.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testInsurance.getFocal()).isEqualTo(UPDATED_FOCAL);
        assertThat(testInsurance.getPreAuthRef()).isEqualTo(UPDATED_PRE_AUTH_REF);
    }

    @Test
    @Transactional
    void patchNonExistingInsurance() throws Exception {
        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();
        insurance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, insurance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(insurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInsurance() throws Exception {
        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();
        insurance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(insurance))
            )
            .andExpect(status().isBadRequest());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInsurance() throws Exception {
        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();
        insurance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInsuranceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(insurance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInsurance() throws Exception {
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);

        int databaseSizeBeforeDelete = insuranceRepository.findAll().size();

        // Delete the insurance
        restInsuranceMockMvc
            .perform(delete(ENTITY_API_URL_ID, insurance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
