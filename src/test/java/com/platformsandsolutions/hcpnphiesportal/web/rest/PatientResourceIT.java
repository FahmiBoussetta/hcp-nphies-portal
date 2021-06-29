package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Patient;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.AdministrativeGenderEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.MaritalStatusEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ReligionEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.PatientRepository;
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
 * Integration tests for the {@link PatientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PatientResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_FORCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FORCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RESIDENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_RESIDENT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONAL_HEALTH_ID = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_HEALTH_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IQAMA = "AAAAAAAAAA";
    private static final String UPDATED_IQAMA = "BBBBBBBBBB";

    private static final ReligionEnum DEFAULT_RELIGION = ReligionEnum.N0;
    private static final ReligionEnum UPDATED_RELIGION = ReligionEnum.N1;

    private static final AdministrativeGenderEnum DEFAULT_GENDER = AdministrativeGenderEnum.Male;
    private static final AdministrativeGenderEnum UPDATED_GENDER = AdministrativeGenderEnum.Female;

    private static final Instant DEFAULT_BIRTH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DECEASED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DECEASED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final MaritalStatusEnum DEFAULT_MARITAL_STATUS = MaritalStatusEnum.L;
    private static final MaritalStatusEnum UPDATED_MARITAL_STATUS = MaritalStatusEnum.D;

    private static final String ENTITY_API_URL = "/api/patients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientMockMvc;

    private Patient patient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createEntity(EntityManager em) {
        Patient patient = new Patient()
            .guid(DEFAULT_GUID)
            .forceId(DEFAULT_FORCE_ID)
            .residentNumber(DEFAULT_RESIDENT_NUMBER)
            .passportNumber(DEFAULT_PASSPORT_NUMBER)
            .nationalHealthId(DEFAULT_NATIONAL_HEALTH_ID)
            .iqama(DEFAULT_IQAMA)
            .religion(DEFAULT_RELIGION)
            .gender(DEFAULT_GENDER)
            .birthDate(DEFAULT_BIRTH_DATE)
            .deceasedDate(DEFAULT_DECEASED_DATE)
            .maritalStatus(DEFAULT_MARITAL_STATUS);
        return patient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createUpdatedEntity(EntityManager em) {
        Patient patient = new Patient()
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .residentNumber(UPDATED_RESIDENT_NUMBER)
            .passportNumber(UPDATED_PASSPORT_NUMBER)
            .nationalHealthId(UPDATED_NATIONAL_HEALTH_ID)
            .iqama(UPDATED_IQAMA)
            .religion(UPDATED_RELIGION)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .deceasedDate(UPDATED_DECEASED_DATE)
            .maritalStatus(UPDATED_MARITAL_STATUS);
        return patient;
    }

    @BeforeEach
    public void initTest() {
        patient = createEntity(em);
    }

    @Test
    @Transactional
    void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();
        // Create the Patient
        restPatientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testPatient.getForceId()).isEqualTo(DEFAULT_FORCE_ID);
        assertThat(testPatient.getResidentNumber()).isEqualTo(DEFAULT_RESIDENT_NUMBER);
        assertThat(testPatient.getPassportNumber()).isEqualTo(DEFAULT_PASSPORT_NUMBER);
        assertThat(testPatient.getNationalHealthId()).isEqualTo(DEFAULT_NATIONAL_HEALTH_ID);
        assertThat(testPatient.getIqama()).isEqualTo(DEFAULT_IQAMA);
        assertThat(testPatient.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testPatient.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatient.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPatient.getDeceasedDate()).isEqualTo(DEFAULT_DECEASED_DATE);
        assertThat(testPatient.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void createPatientWithExistingId() throws Exception {
        // Create the Patient with an existing ID
        patient.setId(1L);

        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList
        restPatientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].forceId").value(hasItem(DEFAULT_FORCE_ID)))
            .andExpect(jsonPath("$.[*].residentNumber").value(hasItem(DEFAULT_RESIDENT_NUMBER)))
            .andExpect(jsonPath("$.[*].passportNumber").value(hasItem(DEFAULT_PASSPORT_NUMBER)))
            .andExpect(jsonPath("$.[*].nationalHealthId").value(hasItem(DEFAULT_NATIONAL_HEALTH_ID)))
            .andExpect(jsonPath("$.[*].iqama").value(hasItem(DEFAULT_IQAMA)))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].deceasedDate").value(hasItem(DEFAULT_DECEASED_DATE.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())));
    }

    @Test
    @Transactional
    void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc
            .perform(get(ENTITY_API_URL_ID, patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.forceId").value(DEFAULT_FORCE_ID))
            .andExpect(jsonPath("$.residentNumber").value(DEFAULT_RESIDENT_NUMBER))
            .andExpect(jsonPath("$.passportNumber").value(DEFAULT_PASSPORT_NUMBER))
            .andExpect(jsonPath("$.nationalHealthId").value(DEFAULT_NATIONAL_HEALTH_ID))
            .andExpect(jsonPath("$.iqama").value(DEFAULT_IQAMA))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.deceasedDate").value(DEFAULT_DECEASED_DATE.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findById(patient.getId()).get();
        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        updatedPatient
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .residentNumber(UPDATED_RESIDENT_NUMBER)
            .passportNumber(UPDATED_PASSPORT_NUMBER)
            .nationalHealthId(UPDATED_NATIONAL_HEALTH_ID)
            .iqama(UPDATED_IQAMA)
            .religion(UPDATED_RELIGION)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .deceasedDate(UPDATED_DECEASED_DATE)
            .maritalStatus(UPDATED_MARITAL_STATUS);

        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPatient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPatient))
            )
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPatient.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testPatient.getResidentNumber()).isEqualTo(UPDATED_RESIDENT_NUMBER);
        assertThat(testPatient.getPassportNumber()).isEqualTo(UPDATED_PASSPORT_NUMBER);
        assertThat(testPatient.getNationalHealthId()).isEqualTo(UPDATED_NATIONAL_HEALTH_ID);
        assertThat(testPatient.getIqama()).isEqualTo(UPDATED_IQAMA);
        assertThat(testPatient.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testPatient.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatient.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPatient.getDeceasedDate()).isEqualTo(UPDATED_DECEASED_DATE);
        assertThat(testPatient.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patient.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatientWithPatch() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient using partial update
        Patient partialUpdatedPatient = new Patient();
        partialUpdatedPatient.setId(patient.getId());

        partialUpdatedPatient
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .residentNumber(UPDATED_RESIDENT_NUMBER)
            .passportNumber(UPDATED_PASSPORT_NUMBER)
            .iqama(UPDATED_IQAMA)
            .maritalStatus(UPDATED_MARITAL_STATUS);

        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatient))
            )
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPatient.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testPatient.getResidentNumber()).isEqualTo(UPDATED_RESIDENT_NUMBER);
        assertThat(testPatient.getPassportNumber()).isEqualTo(UPDATED_PASSPORT_NUMBER);
        assertThat(testPatient.getNationalHealthId()).isEqualTo(DEFAULT_NATIONAL_HEALTH_ID);
        assertThat(testPatient.getIqama()).isEqualTo(UPDATED_IQAMA);
        assertThat(testPatient.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testPatient.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatient.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPatient.getDeceasedDate()).isEqualTo(DEFAULT_DECEASED_DATE);
        assertThat(testPatient.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePatientWithPatch() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient using partial update
        Patient partialUpdatedPatient = new Patient();
        partialUpdatedPatient.setId(patient.getId());

        partialUpdatedPatient
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .residentNumber(UPDATED_RESIDENT_NUMBER)
            .passportNumber(UPDATED_PASSPORT_NUMBER)
            .nationalHealthId(UPDATED_NATIONAL_HEALTH_ID)
            .iqama(UPDATED_IQAMA)
            .religion(UPDATED_RELIGION)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .deceasedDate(UPDATED_DECEASED_DATE)
            .maritalStatus(UPDATED_MARITAL_STATUS);

        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatient))
            )
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPatient.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testPatient.getResidentNumber()).isEqualTo(UPDATED_RESIDENT_NUMBER);
        assertThat(testPatient.getPassportNumber()).isEqualTo(UPDATED_PASSPORT_NUMBER);
        assertThat(testPatient.getNationalHealthId()).isEqualTo(UPDATED_NATIONAL_HEALTH_ID);
        assertThat(testPatient.getIqama()).isEqualTo(UPDATED_IQAMA);
        assertThat(testPatient.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testPatient.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatient.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPatient.getDeceasedDate()).isEqualTo(UPDATED_DECEASED_DATE);
        assertThat(testPatient.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patient))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Delete the patient
        restPatientMockMvc
            .perform(delete(ENTITY_API_URL_ID, patient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
