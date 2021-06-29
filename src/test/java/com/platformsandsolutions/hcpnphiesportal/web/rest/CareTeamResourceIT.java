package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.CareTeam;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.CareTeamRoleEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.CareTeamRepository;
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
 * Integration tests for the {@link CareTeamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CareTeamResourceIT {

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final CareTeamRoleEnum DEFAULT_ROLE = CareTeamRoleEnum.Primary;
    private static final CareTeamRoleEnum UPDATED_ROLE = CareTeamRoleEnum.Assist;

    private static final String ENTITY_API_URL = "/api/care-teams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CareTeamRepository careTeamRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCareTeamMockMvc;

    private CareTeam careTeam;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CareTeam createEntity(EntityManager em) {
        CareTeam careTeam = new CareTeam().sequence(DEFAULT_SEQUENCE).role(DEFAULT_ROLE);
        return careTeam;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CareTeam createUpdatedEntity(EntityManager em) {
        CareTeam careTeam = new CareTeam().sequence(UPDATED_SEQUENCE).role(UPDATED_ROLE);
        return careTeam;
    }

    @BeforeEach
    public void initTest() {
        careTeam = createEntity(em);
    }

    @Test
    @Transactional
    void createCareTeam() throws Exception {
        int databaseSizeBeforeCreate = careTeamRepository.findAll().size();
        // Create the CareTeam
        restCareTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(careTeam)))
            .andExpect(status().isCreated());

        // Validate the CareTeam in the database
        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeCreate + 1);
        CareTeam testCareTeam = careTeamList.get(careTeamList.size() - 1);
        assertThat(testCareTeam.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testCareTeam.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void createCareTeamWithExistingId() throws Exception {
        // Create the CareTeam with an existing ID
        careTeam.setId(1L);

        int databaseSizeBeforeCreate = careTeamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCareTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(careTeam)))
            .andExpect(status().isBadRequest());

        // Validate the CareTeam in the database
        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = careTeamRepository.findAll().size();
        // set the field null
        careTeam.setSequence(null);

        // Create the CareTeam, which fails.

        restCareTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(careTeam)))
            .andExpect(status().isBadRequest());

        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = careTeamRepository.findAll().size();
        // set the field null
        careTeam.setRole(null);

        // Create the CareTeam, which fails.

        restCareTeamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(careTeam)))
            .andExpect(status().isBadRequest());

        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCareTeams() throws Exception {
        // Initialize the database
        careTeamRepository.saveAndFlush(careTeam);

        // Get all the careTeamList
        restCareTeamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(careTeam.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }

    @Test
    @Transactional
    void getCareTeam() throws Exception {
        // Initialize the database
        careTeamRepository.saveAndFlush(careTeam);

        // Get the careTeam
        restCareTeamMockMvc
            .perform(get(ENTITY_API_URL_ID, careTeam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(careTeam.getId().intValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCareTeam() throws Exception {
        // Get the careTeam
        restCareTeamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCareTeam() throws Exception {
        // Initialize the database
        careTeamRepository.saveAndFlush(careTeam);

        int databaseSizeBeforeUpdate = careTeamRepository.findAll().size();

        // Update the careTeam
        CareTeam updatedCareTeam = careTeamRepository.findById(careTeam.getId()).get();
        // Disconnect from session so that the updates on updatedCareTeam are not directly saved in db
        em.detach(updatedCareTeam);
        updatedCareTeam.sequence(UPDATED_SEQUENCE).role(UPDATED_ROLE);

        restCareTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCareTeam.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCareTeam))
            )
            .andExpect(status().isOk());

        // Validate the CareTeam in the database
        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeUpdate);
        CareTeam testCareTeam = careTeamList.get(careTeamList.size() - 1);
        assertThat(testCareTeam.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testCareTeam.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void putNonExistingCareTeam() throws Exception {
        int databaseSizeBeforeUpdate = careTeamRepository.findAll().size();
        careTeam.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCareTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, careTeam.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(careTeam))
            )
            .andExpect(status().isBadRequest());

        // Validate the CareTeam in the database
        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCareTeam() throws Exception {
        int databaseSizeBeforeUpdate = careTeamRepository.findAll().size();
        careTeam.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCareTeamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(careTeam))
            )
            .andExpect(status().isBadRequest());

        // Validate the CareTeam in the database
        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCareTeam() throws Exception {
        int databaseSizeBeforeUpdate = careTeamRepository.findAll().size();
        careTeam.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCareTeamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(careTeam)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CareTeam in the database
        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCareTeamWithPatch() throws Exception {
        // Initialize the database
        careTeamRepository.saveAndFlush(careTeam);

        int databaseSizeBeforeUpdate = careTeamRepository.findAll().size();

        // Update the careTeam using partial update
        CareTeam partialUpdatedCareTeam = new CareTeam();
        partialUpdatedCareTeam.setId(careTeam.getId());

        partialUpdatedCareTeam.sequence(UPDATED_SEQUENCE);

        restCareTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCareTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCareTeam))
            )
            .andExpect(status().isOk());

        // Validate the CareTeam in the database
        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeUpdate);
        CareTeam testCareTeam = careTeamList.get(careTeamList.size() - 1);
        assertThat(testCareTeam.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testCareTeam.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void fullUpdateCareTeamWithPatch() throws Exception {
        // Initialize the database
        careTeamRepository.saveAndFlush(careTeam);

        int databaseSizeBeforeUpdate = careTeamRepository.findAll().size();

        // Update the careTeam using partial update
        CareTeam partialUpdatedCareTeam = new CareTeam();
        partialUpdatedCareTeam.setId(careTeam.getId());

        partialUpdatedCareTeam.sequence(UPDATED_SEQUENCE).role(UPDATED_ROLE);

        restCareTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCareTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCareTeam))
            )
            .andExpect(status().isOk());

        // Validate the CareTeam in the database
        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeUpdate);
        CareTeam testCareTeam = careTeamList.get(careTeamList.size() - 1);
        assertThat(testCareTeam.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testCareTeam.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void patchNonExistingCareTeam() throws Exception {
        int databaseSizeBeforeUpdate = careTeamRepository.findAll().size();
        careTeam.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCareTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, careTeam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(careTeam))
            )
            .andExpect(status().isBadRequest());

        // Validate the CareTeam in the database
        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCareTeam() throws Exception {
        int databaseSizeBeforeUpdate = careTeamRepository.findAll().size();
        careTeam.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCareTeamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(careTeam))
            )
            .andExpect(status().isBadRequest());

        // Validate the CareTeam in the database
        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCareTeam() throws Exception {
        int databaseSizeBeforeUpdate = careTeamRepository.findAll().size();
        careTeam.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCareTeamMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(careTeam)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CareTeam in the database
        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCareTeam() throws Exception {
        // Initialize the database
        careTeamRepository.saveAndFlush(careTeam);

        int databaseSizeBeforeDelete = careTeamRepository.findAll().size();

        // Delete the careTeam
        restCareTeamMockMvc
            .perform(delete(ENTITY_API_URL_ID, careTeam.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CareTeam> careTeamList = careTeamRepository.findAll();
        assertThat(careTeamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
