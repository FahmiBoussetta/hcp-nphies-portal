package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Hospitalization;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.AdmitSourceEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.DischargeDispositionEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ReAdmissionEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.HospitalizationRepository;
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
 * Integration tests for the {@link HospitalizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HospitalizationResourceIT {

    private static final AdmitSourceEnum DEFAULT_ADMIT_SOURCE = AdmitSourceEnum.IA;
    private static final AdmitSourceEnum UPDATED_ADMIT_SOURCE = AdmitSourceEnum.EER;

    private static final ReAdmissionEnum DEFAULT_RE_ADMISSION = ReAdmissionEnum.R;
    private static final ReAdmissionEnum UPDATED_RE_ADMISSION = ReAdmissionEnum.R;

    private static final DischargeDispositionEnum DEFAULT_DISCHARGE_DISPOSITION = DischargeDispositionEnum.Home;
    private static final DischargeDispositionEnum UPDATED_DISCHARGE_DISPOSITION = DischargeDispositionEnum.DASHalt_home;

    private static final String ENTITY_API_URL = "/api/hospitalizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HospitalizationRepository hospitalizationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHospitalizationMockMvc;

    private Hospitalization hospitalization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hospitalization createEntity(EntityManager em) {
        Hospitalization hospitalization = new Hospitalization()
            .admitSource(DEFAULT_ADMIT_SOURCE)
            .reAdmission(DEFAULT_RE_ADMISSION)
            .dischargeDisposition(DEFAULT_DISCHARGE_DISPOSITION);
        return hospitalization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hospitalization createUpdatedEntity(EntityManager em) {
        Hospitalization hospitalization = new Hospitalization()
            .admitSource(UPDATED_ADMIT_SOURCE)
            .reAdmission(UPDATED_RE_ADMISSION)
            .dischargeDisposition(UPDATED_DISCHARGE_DISPOSITION);
        return hospitalization;
    }

    @BeforeEach
    public void initTest() {
        hospitalization = createEntity(em);
    }

    @Test
    @Transactional
    void createHospitalization() throws Exception {
        int databaseSizeBeforeCreate = hospitalizationRepository.findAll().size();
        // Create the Hospitalization
        restHospitalizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalization))
            )
            .andExpect(status().isCreated());

        // Validate the Hospitalization in the database
        List<Hospitalization> hospitalizationList = hospitalizationRepository.findAll();
        assertThat(hospitalizationList).hasSize(databaseSizeBeforeCreate + 1);
        Hospitalization testHospitalization = hospitalizationList.get(hospitalizationList.size() - 1);
        assertThat(testHospitalization.getAdmitSource()).isEqualTo(DEFAULT_ADMIT_SOURCE);
        assertThat(testHospitalization.getReAdmission()).isEqualTo(DEFAULT_RE_ADMISSION);
        assertThat(testHospitalization.getDischargeDisposition()).isEqualTo(DEFAULT_DISCHARGE_DISPOSITION);
    }

    @Test
    @Transactional
    void createHospitalizationWithExistingId() throws Exception {
        // Create the Hospitalization with an existing ID
        hospitalization.setId(1L);

        int databaseSizeBeforeCreate = hospitalizationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHospitalizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospitalization in the database
        List<Hospitalization> hospitalizationList = hospitalizationRepository.findAll();
        assertThat(hospitalizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHospitalizations() throws Exception {
        // Initialize the database
        hospitalizationRepository.saveAndFlush(hospitalization);

        // Get all the hospitalizationList
        restHospitalizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospitalization.getId().intValue())))
            .andExpect(jsonPath("$.[*].admitSource").value(hasItem(DEFAULT_ADMIT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].reAdmission").value(hasItem(DEFAULT_RE_ADMISSION.toString())))
            .andExpect(jsonPath("$.[*].dischargeDisposition").value(hasItem(DEFAULT_DISCHARGE_DISPOSITION.toString())));
    }

    @Test
    @Transactional
    void getHospitalization() throws Exception {
        // Initialize the database
        hospitalizationRepository.saveAndFlush(hospitalization);

        // Get the hospitalization
        restHospitalizationMockMvc
            .perform(get(ENTITY_API_URL_ID, hospitalization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hospitalization.getId().intValue()))
            .andExpect(jsonPath("$.admitSource").value(DEFAULT_ADMIT_SOURCE.toString()))
            .andExpect(jsonPath("$.reAdmission").value(DEFAULT_RE_ADMISSION.toString()))
            .andExpect(jsonPath("$.dischargeDisposition").value(DEFAULT_DISCHARGE_DISPOSITION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHospitalization() throws Exception {
        // Get the hospitalization
        restHospitalizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHospitalization() throws Exception {
        // Initialize the database
        hospitalizationRepository.saveAndFlush(hospitalization);

        int databaseSizeBeforeUpdate = hospitalizationRepository.findAll().size();

        // Update the hospitalization
        Hospitalization updatedHospitalization = hospitalizationRepository.findById(hospitalization.getId()).get();
        // Disconnect from session so that the updates on updatedHospitalization are not directly saved in db
        em.detach(updatedHospitalization);
        updatedHospitalization
            .admitSource(UPDATED_ADMIT_SOURCE)
            .reAdmission(UPDATED_RE_ADMISSION)
            .dischargeDisposition(UPDATED_DISCHARGE_DISPOSITION);

        restHospitalizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHospitalization.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHospitalization))
            )
            .andExpect(status().isOk());

        // Validate the Hospitalization in the database
        List<Hospitalization> hospitalizationList = hospitalizationRepository.findAll();
        assertThat(hospitalizationList).hasSize(databaseSizeBeforeUpdate);
        Hospitalization testHospitalization = hospitalizationList.get(hospitalizationList.size() - 1);
        assertThat(testHospitalization.getAdmitSource()).isEqualTo(UPDATED_ADMIT_SOURCE);
        assertThat(testHospitalization.getReAdmission()).isEqualTo(UPDATED_RE_ADMISSION);
        assertThat(testHospitalization.getDischargeDisposition()).isEqualTo(UPDATED_DISCHARGE_DISPOSITION);
    }

    @Test
    @Transactional
    void putNonExistingHospitalization() throws Exception {
        int databaseSizeBeforeUpdate = hospitalizationRepository.findAll().size();
        hospitalization.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospitalizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hospitalization.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hospitalization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospitalization in the database
        List<Hospitalization> hospitalizationList = hospitalizationRepository.findAll();
        assertThat(hospitalizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHospitalization() throws Exception {
        int databaseSizeBeforeUpdate = hospitalizationRepository.findAll().size();
        hospitalization.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHospitalizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hospitalization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospitalization in the database
        List<Hospitalization> hospitalizationList = hospitalizationRepository.findAll();
        assertThat(hospitalizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHospitalization() throws Exception {
        int databaseSizeBeforeUpdate = hospitalizationRepository.findAll().size();
        hospitalization.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHospitalizationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalization))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hospitalization in the database
        List<Hospitalization> hospitalizationList = hospitalizationRepository.findAll();
        assertThat(hospitalizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHospitalizationWithPatch() throws Exception {
        // Initialize the database
        hospitalizationRepository.saveAndFlush(hospitalization);

        int databaseSizeBeforeUpdate = hospitalizationRepository.findAll().size();

        // Update the hospitalization using partial update
        Hospitalization partialUpdatedHospitalization = new Hospitalization();
        partialUpdatedHospitalization.setId(hospitalization.getId());

        partialUpdatedHospitalization
            .admitSource(UPDATED_ADMIT_SOURCE)
            .reAdmission(UPDATED_RE_ADMISSION)
            .dischargeDisposition(UPDATED_DISCHARGE_DISPOSITION);

        restHospitalizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHospitalization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHospitalization))
            )
            .andExpect(status().isOk());

        // Validate the Hospitalization in the database
        List<Hospitalization> hospitalizationList = hospitalizationRepository.findAll();
        assertThat(hospitalizationList).hasSize(databaseSizeBeforeUpdate);
        Hospitalization testHospitalization = hospitalizationList.get(hospitalizationList.size() - 1);
        assertThat(testHospitalization.getAdmitSource()).isEqualTo(UPDATED_ADMIT_SOURCE);
        assertThat(testHospitalization.getReAdmission()).isEqualTo(UPDATED_RE_ADMISSION);
        assertThat(testHospitalization.getDischargeDisposition()).isEqualTo(UPDATED_DISCHARGE_DISPOSITION);
    }

    @Test
    @Transactional
    void fullUpdateHospitalizationWithPatch() throws Exception {
        // Initialize the database
        hospitalizationRepository.saveAndFlush(hospitalization);

        int databaseSizeBeforeUpdate = hospitalizationRepository.findAll().size();

        // Update the hospitalization using partial update
        Hospitalization partialUpdatedHospitalization = new Hospitalization();
        partialUpdatedHospitalization.setId(hospitalization.getId());

        partialUpdatedHospitalization
            .admitSource(UPDATED_ADMIT_SOURCE)
            .reAdmission(UPDATED_RE_ADMISSION)
            .dischargeDisposition(UPDATED_DISCHARGE_DISPOSITION);

        restHospitalizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHospitalization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHospitalization))
            )
            .andExpect(status().isOk());

        // Validate the Hospitalization in the database
        List<Hospitalization> hospitalizationList = hospitalizationRepository.findAll();
        assertThat(hospitalizationList).hasSize(databaseSizeBeforeUpdate);
        Hospitalization testHospitalization = hospitalizationList.get(hospitalizationList.size() - 1);
        assertThat(testHospitalization.getAdmitSource()).isEqualTo(UPDATED_ADMIT_SOURCE);
        assertThat(testHospitalization.getReAdmission()).isEqualTo(UPDATED_RE_ADMISSION);
        assertThat(testHospitalization.getDischargeDisposition()).isEqualTo(UPDATED_DISCHARGE_DISPOSITION);
    }

    @Test
    @Transactional
    void patchNonExistingHospitalization() throws Exception {
        int databaseSizeBeforeUpdate = hospitalizationRepository.findAll().size();
        hospitalization.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospitalizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hospitalization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hospitalization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospitalization in the database
        List<Hospitalization> hospitalizationList = hospitalizationRepository.findAll();
        assertThat(hospitalizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHospitalization() throws Exception {
        int databaseSizeBeforeUpdate = hospitalizationRepository.findAll().size();
        hospitalization.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHospitalizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hospitalization))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospitalization in the database
        List<Hospitalization> hospitalizationList = hospitalizationRepository.findAll();
        assertThat(hospitalizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHospitalization() throws Exception {
        int databaseSizeBeforeUpdate = hospitalizationRepository.findAll().size();
        hospitalization.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHospitalizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hospitalization))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hospitalization in the database
        List<Hospitalization> hospitalizationList = hospitalizationRepository.findAll();
        assertThat(hospitalizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHospitalization() throws Exception {
        // Initialize the database
        hospitalizationRepository.saveAndFlush(hospitalization);

        int databaseSizeBeforeDelete = hospitalizationRepository.findAll().size();

        // Delete the hospitalization
        restHospitalizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, hospitalization.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hospitalization> hospitalizationList = hospitalizationRepository.findAll();
        assertThat(hospitalizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
