package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Practitioner;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.AdministrativeGenderEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.PractitionerRepository;
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
 * Integration tests for the {@link PractitionerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PractitionerResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_FORCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FORCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PRACTITIONER_LICENSE = "AAAAAAAAAA";
    private static final String UPDATED_PRACTITIONER_LICENSE = "BBBBBBBBBB";

    private static final AdministrativeGenderEnum DEFAULT_GENDER = AdministrativeGenderEnum.Male;
    private static final AdministrativeGenderEnum UPDATED_GENDER = AdministrativeGenderEnum.Female;

    private static final String ENTITY_API_URL = "/api/practitioners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PractitionerRepository practitionerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPractitionerMockMvc;

    private Practitioner practitioner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Practitioner createEntity(EntityManager em) {
        Practitioner practitioner = new Practitioner()
            .guid(DEFAULT_GUID)
            .forceId(DEFAULT_FORCE_ID)
            .practitionerLicense(DEFAULT_PRACTITIONER_LICENSE)
            .gender(DEFAULT_GENDER);
        return practitioner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Practitioner createUpdatedEntity(EntityManager em) {
        Practitioner practitioner = new Practitioner()
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .practitionerLicense(UPDATED_PRACTITIONER_LICENSE)
            .gender(UPDATED_GENDER);
        return practitioner;
    }

    @BeforeEach
    public void initTest() {
        practitioner = createEntity(em);
    }

    @Test
    @Transactional
    void createPractitioner() throws Exception {
        int databaseSizeBeforeCreate = practitionerRepository.findAll().size();
        // Create the Practitioner
        restPractitionerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practitioner)))
            .andExpect(status().isCreated());

        // Validate the Practitioner in the database
        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeCreate + 1);
        Practitioner testPractitioner = practitionerList.get(practitionerList.size() - 1);
        assertThat(testPractitioner.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testPractitioner.getForceId()).isEqualTo(DEFAULT_FORCE_ID);
        assertThat(testPractitioner.getPractitionerLicense()).isEqualTo(DEFAULT_PRACTITIONER_LICENSE);
        assertThat(testPractitioner.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    void createPractitionerWithExistingId() throws Exception {
        // Create the Practitioner with an existing ID
        practitioner.setId(1L);

        int databaseSizeBeforeCreate = practitionerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPractitionerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practitioner)))
            .andExpect(status().isBadRequest());

        // Validate the Practitioner in the database
        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPractitionerLicenseIsRequired() throws Exception {
        int databaseSizeBeforeTest = practitionerRepository.findAll().size();
        // set the field null
        practitioner.setPractitionerLicense(null);

        // Create the Practitioner, which fails.

        restPractitionerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practitioner)))
            .andExpect(status().isBadRequest());

        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPractitioners() throws Exception {
        // Initialize the database
        practitionerRepository.saveAndFlush(practitioner);

        // Get all the practitionerList
        restPractitionerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(practitioner.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].forceId").value(hasItem(DEFAULT_FORCE_ID)))
            .andExpect(jsonPath("$.[*].practitionerLicense").value(hasItem(DEFAULT_PRACTITIONER_LICENSE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));
    }

    @Test
    @Transactional
    void getPractitioner() throws Exception {
        // Initialize the database
        practitionerRepository.saveAndFlush(practitioner);

        // Get the practitioner
        restPractitionerMockMvc
            .perform(get(ENTITY_API_URL_ID, practitioner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(practitioner.getId().intValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.forceId").value(DEFAULT_FORCE_ID))
            .andExpect(jsonPath("$.practitionerLicense").value(DEFAULT_PRACTITIONER_LICENSE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPractitioner() throws Exception {
        // Get the practitioner
        restPractitionerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPractitioner() throws Exception {
        // Initialize the database
        practitionerRepository.saveAndFlush(practitioner);

        int databaseSizeBeforeUpdate = practitionerRepository.findAll().size();

        // Update the practitioner
        Practitioner updatedPractitioner = practitionerRepository.findById(practitioner.getId()).get();
        // Disconnect from session so that the updates on updatedPractitioner are not directly saved in db
        em.detach(updatedPractitioner);
        updatedPractitioner
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .practitionerLicense(UPDATED_PRACTITIONER_LICENSE)
            .gender(UPDATED_GENDER);

        restPractitionerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPractitioner.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPractitioner))
            )
            .andExpect(status().isOk());

        // Validate the Practitioner in the database
        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeUpdate);
        Practitioner testPractitioner = practitionerList.get(practitionerList.size() - 1);
        assertThat(testPractitioner.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPractitioner.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testPractitioner.getPractitionerLicense()).isEqualTo(UPDATED_PRACTITIONER_LICENSE);
        assertThat(testPractitioner.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    void putNonExistingPractitioner() throws Exception {
        int databaseSizeBeforeUpdate = practitionerRepository.findAll().size();
        practitioner.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPractitionerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, practitioner.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(practitioner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Practitioner in the database
        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPractitioner() throws Exception {
        int databaseSizeBeforeUpdate = practitionerRepository.findAll().size();
        practitioner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPractitionerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(practitioner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Practitioner in the database
        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPractitioner() throws Exception {
        int databaseSizeBeforeUpdate = practitionerRepository.findAll().size();
        practitioner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPractitionerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practitioner)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Practitioner in the database
        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePractitionerWithPatch() throws Exception {
        // Initialize the database
        practitionerRepository.saveAndFlush(practitioner);

        int databaseSizeBeforeUpdate = practitionerRepository.findAll().size();

        // Update the practitioner using partial update
        Practitioner partialUpdatedPractitioner = new Practitioner();
        partialUpdatedPractitioner.setId(practitioner.getId());

        partialUpdatedPractitioner.guid(UPDATED_GUID);

        restPractitionerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPractitioner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPractitioner))
            )
            .andExpect(status().isOk());

        // Validate the Practitioner in the database
        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeUpdate);
        Practitioner testPractitioner = practitionerList.get(practitionerList.size() - 1);
        assertThat(testPractitioner.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPractitioner.getForceId()).isEqualTo(DEFAULT_FORCE_ID);
        assertThat(testPractitioner.getPractitionerLicense()).isEqualTo(DEFAULT_PRACTITIONER_LICENSE);
        assertThat(testPractitioner.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    void fullUpdatePractitionerWithPatch() throws Exception {
        // Initialize the database
        practitionerRepository.saveAndFlush(practitioner);

        int databaseSizeBeforeUpdate = practitionerRepository.findAll().size();

        // Update the practitioner using partial update
        Practitioner partialUpdatedPractitioner = new Practitioner();
        partialUpdatedPractitioner.setId(practitioner.getId());

        partialUpdatedPractitioner
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .practitionerLicense(UPDATED_PRACTITIONER_LICENSE)
            .gender(UPDATED_GENDER);

        restPractitionerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPractitioner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPractitioner))
            )
            .andExpect(status().isOk());

        // Validate the Practitioner in the database
        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeUpdate);
        Practitioner testPractitioner = practitionerList.get(practitionerList.size() - 1);
        assertThat(testPractitioner.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPractitioner.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testPractitioner.getPractitionerLicense()).isEqualTo(UPDATED_PRACTITIONER_LICENSE);
        assertThat(testPractitioner.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    void patchNonExistingPractitioner() throws Exception {
        int databaseSizeBeforeUpdate = practitionerRepository.findAll().size();
        practitioner.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPractitionerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, practitioner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(practitioner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Practitioner in the database
        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPractitioner() throws Exception {
        int databaseSizeBeforeUpdate = practitionerRepository.findAll().size();
        practitioner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPractitionerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(practitioner))
            )
            .andExpect(status().isBadRequest());

        // Validate the Practitioner in the database
        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPractitioner() throws Exception {
        int databaseSizeBeforeUpdate = practitionerRepository.findAll().size();
        practitioner.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPractitionerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(practitioner))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Practitioner in the database
        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePractitioner() throws Exception {
        // Initialize the database
        practitionerRepository.saveAndFlush(practitioner);

        int databaseSizeBeforeDelete = practitionerRepository.findAll().size();

        // Delete the practitioner
        restPractitionerMockMvc
            .perform(delete(ENTITY_API_URL_ID, practitioner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Practitioner> practitionerList = practitionerRepository.findAll();
        assertThat(practitionerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
