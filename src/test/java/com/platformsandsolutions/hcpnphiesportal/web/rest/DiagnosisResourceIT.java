package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Diagnosis;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.DiagnosisOnAdmissionEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.DiagnosisTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.DiagnosisRepository;
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
 * Integration tests for the {@link DiagnosisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiagnosisResourceIT {

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final String DEFAULT_DIAGNOSIS = "AAAAAAAAAA";
    private static final String UPDATED_DIAGNOSIS = "BBBBBBBBBB";

    private static final DiagnosisTypeEnum DEFAULT_TYPE = DiagnosisTypeEnum.Admitting;
    private static final DiagnosisTypeEnum UPDATED_TYPE = DiagnosisTypeEnum.Clinical;

    private static final DiagnosisOnAdmissionEnum DEFAULT_ON_ADMISSION = DiagnosisOnAdmissionEnum.Y;
    private static final DiagnosisOnAdmissionEnum UPDATED_ON_ADMISSION = DiagnosisOnAdmissionEnum.N;

    private static final String ENTITY_API_URL = "/api/diagnoses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiagnosisMockMvc;

    private Diagnosis diagnosis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diagnosis createEntity(EntityManager em) {
        Diagnosis diagnosis = new Diagnosis()
            .sequence(DEFAULT_SEQUENCE)
            .diagnosis(DEFAULT_DIAGNOSIS)
            .type(DEFAULT_TYPE)
            .onAdmission(DEFAULT_ON_ADMISSION);
        return diagnosis;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diagnosis createUpdatedEntity(EntityManager em) {
        Diagnosis diagnosis = new Diagnosis()
            .sequence(UPDATED_SEQUENCE)
            .diagnosis(UPDATED_DIAGNOSIS)
            .type(UPDATED_TYPE)
            .onAdmission(UPDATED_ON_ADMISSION);
        return diagnosis;
    }

    @BeforeEach
    public void initTest() {
        diagnosis = createEntity(em);
    }

    @Test
    @Transactional
    void createDiagnosis() throws Exception {
        int databaseSizeBeforeCreate = diagnosisRepository.findAll().size();
        // Create the Diagnosis
        restDiagnosisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diagnosis)))
            .andExpect(status().isCreated());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeCreate + 1);
        Diagnosis testDiagnosis = diagnosisList.get(diagnosisList.size() - 1);
        assertThat(testDiagnosis.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testDiagnosis.getDiagnosis()).isEqualTo(DEFAULT_DIAGNOSIS);
        assertThat(testDiagnosis.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDiagnosis.getOnAdmission()).isEqualTo(DEFAULT_ON_ADMISSION);
    }

    @Test
    @Transactional
    void createDiagnosisWithExistingId() throws Exception {
        // Create the Diagnosis with an existing ID
        diagnosis.setId(1L);

        int databaseSizeBeforeCreate = diagnosisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiagnosisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diagnosis)))
            .andExpect(status().isBadRequest());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = diagnosisRepository.findAll().size();
        // set the field null
        diagnosis.setSequence(null);

        // Create the Diagnosis, which fails.

        restDiagnosisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diagnosis)))
            .andExpect(status().isBadRequest());

        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDiagnosisIsRequired() throws Exception {
        int databaseSizeBeforeTest = diagnosisRepository.findAll().size();
        // set the field null
        diagnosis.setDiagnosis(null);

        // Create the Diagnosis, which fails.

        restDiagnosisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diagnosis)))
            .andExpect(status().isBadRequest());

        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = diagnosisRepository.findAll().size();
        // set the field null
        diagnosis.setType(null);

        // Create the Diagnosis, which fails.

        restDiagnosisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diagnosis)))
            .andExpect(status().isBadRequest());

        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDiagnoses() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);

        // Get all the diagnosisList
        restDiagnosisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diagnosis.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].diagnosis").value(hasItem(DEFAULT_DIAGNOSIS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].onAdmission").value(hasItem(DEFAULT_ON_ADMISSION.toString())));
    }

    @Test
    @Transactional
    void getDiagnosis() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);

        // Get the diagnosis
        restDiagnosisMockMvc
            .perform(get(ENTITY_API_URL_ID, diagnosis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diagnosis.getId().intValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.diagnosis").value(DEFAULT_DIAGNOSIS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.onAdmission").value(DEFAULT_ON_ADMISSION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDiagnosis() throws Exception {
        // Get the diagnosis
        restDiagnosisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDiagnosis() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);

        int databaseSizeBeforeUpdate = diagnosisRepository.findAll().size();

        // Update the diagnosis
        Diagnosis updatedDiagnosis = diagnosisRepository.findById(diagnosis.getId()).get();
        // Disconnect from session so that the updates on updatedDiagnosis are not directly saved in db
        em.detach(updatedDiagnosis);
        updatedDiagnosis.sequence(UPDATED_SEQUENCE).diagnosis(UPDATED_DIAGNOSIS).type(UPDATED_TYPE).onAdmission(UPDATED_ON_ADMISSION);

        restDiagnosisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDiagnosis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDiagnosis))
            )
            .andExpect(status().isOk());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeUpdate);
        Diagnosis testDiagnosis = diagnosisList.get(diagnosisList.size() - 1);
        assertThat(testDiagnosis.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testDiagnosis.getDiagnosis()).isEqualTo(UPDATED_DIAGNOSIS);
        assertThat(testDiagnosis.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDiagnosis.getOnAdmission()).isEqualTo(UPDATED_ON_ADMISSION);
    }

    @Test
    @Transactional
    void putNonExistingDiagnosis() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisRepository.findAll().size();
        diagnosis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiagnosisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diagnosis.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diagnosis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiagnosis() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisRepository.findAll().size();
        diagnosis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diagnosis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiagnosis() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisRepository.findAll().size();
        diagnosis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diagnosis)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiagnosisWithPatch() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);

        int databaseSizeBeforeUpdate = diagnosisRepository.findAll().size();

        // Update the diagnosis using partial update
        Diagnosis partialUpdatedDiagnosis = new Diagnosis();
        partialUpdatedDiagnosis.setId(diagnosis.getId());

        partialUpdatedDiagnosis.sequence(UPDATED_SEQUENCE).type(UPDATED_TYPE).onAdmission(UPDATED_ON_ADMISSION);

        restDiagnosisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiagnosis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiagnosis))
            )
            .andExpect(status().isOk());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeUpdate);
        Diagnosis testDiagnosis = diagnosisList.get(diagnosisList.size() - 1);
        assertThat(testDiagnosis.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testDiagnosis.getDiagnosis()).isEqualTo(DEFAULT_DIAGNOSIS);
        assertThat(testDiagnosis.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDiagnosis.getOnAdmission()).isEqualTo(UPDATED_ON_ADMISSION);
    }

    @Test
    @Transactional
    void fullUpdateDiagnosisWithPatch() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);

        int databaseSizeBeforeUpdate = diagnosisRepository.findAll().size();

        // Update the diagnosis using partial update
        Diagnosis partialUpdatedDiagnosis = new Diagnosis();
        partialUpdatedDiagnosis.setId(diagnosis.getId());

        partialUpdatedDiagnosis
            .sequence(UPDATED_SEQUENCE)
            .diagnosis(UPDATED_DIAGNOSIS)
            .type(UPDATED_TYPE)
            .onAdmission(UPDATED_ON_ADMISSION);

        restDiagnosisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiagnosis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiagnosis))
            )
            .andExpect(status().isOk());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeUpdate);
        Diagnosis testDiagnosis = diagnosisList.get(diagnosisList.size() - 1);
        assertThat(testDiagnosis.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testDiagnosis.getDiagnosis()).isEqualTo(UPDATED_DIAGNOSIS);
        assertThat(testDiagnosis.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDiagnosis.getOnAdmission()).isEqualTo(UPDATED_ON_ADMISSION);
    }

    @Test
    @Transactional
    void patchNonExistingDiagnosis() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisRepository.findAll().size();
        diagnosis.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiagnosisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, diagnosis.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diagnosis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiagnosis() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisRepository.findAll().size();
        diagnosis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diagnosis))
            )
            .andExpect(status().isBadRequest());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiagnosis() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisRepository.findAll().size();
        diagnosis.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosisMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(diagnosis))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiagnosis() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);

        int databaseSizeBeforeDelete = diagnosisRepository.findAll().size();

        // Delete the diagnosis
        restDiagnosisMockMvc
            .perform(delete(ENTITY_API_URL_ID, diagnosis.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Diagnosis> diagnosisList = diagnosisRepository.findAll();
        assertThat(diagnosisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
