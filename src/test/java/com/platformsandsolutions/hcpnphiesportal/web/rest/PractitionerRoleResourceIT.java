package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.PractitionerRole;
import com.platformsandsolutions.hcpnphiesportal.repository.PractitionerRoleRepository;
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
 * Integration tests for the {@link PractitionerRoleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PractitionerRoleResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_FORCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FORCE_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/practitioner-roles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PractitionerRoleRepository practitionerRoleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPractitionerRoleMockMvc;

    private PractitionerRole practitionerRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PractitionerRole createEntity(EntityManager em) {
        PractitionerRole practitionerRole = new PractitionerRole()
            .guid(DEFAULT_GUID)
            .forceId(DEFAULT_FORCE_ID)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return practitionerRole;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PractitionerRole createUpdatedEntity(EntityManager em) {
        PractitionerRole practitionerRole = new PractitionerRole()
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .start(UPDATED_START)
            .end(UPDATED_END);
        return practitionerRole;
    }

    @BeforeEach
    public void initTest() {
        practitionerRole = createEntity(em);
    }

    @Test
    @Transactional
    void createPractitionerRole() throws Exception {
        int databaseSizeBeforeCreate = practitionerRoleRepository.findAll().size();
        // Create the PractitionerRole
        restPractitionerRoleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practitionerRole))
            )
            .andExpect(status().isCreated());

        // Validate the PractitionerRole in the database
        List<PractitionerRole> practitionerRoleList = practitionerRoleRepository.findAll();
        assertThat(practitionerRoleList).hasSize(databaseSizeBeforeCreate + 1);
        PractitionerRole testPractitionerRole = practitionerRoleList.get(practitionerRoleList.size() - 1);
        assertThat(testPractitionerRole.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testPractitionerRole.getForceId()).isEqualTo(DEFAULT_FORCE_ID);
        assertThat(testPractitionerRole.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testPractitionerRole.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    void createPractitionerRoleWithExistingId() throws Exception {
        // Create the PractitionerRole with an existing ID
        practitionerRole.setId(1L);

        int databaseSizeBeforeCreate = practitionerRoleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPractitionerRoleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practitionerRole))
            )
            .andExpect(status().isBadRequest());

        // Validate the PractitionerRole in the database
        List<PractitionerRole> practitionerRoleList = practitionerRoleRepository.findAll();
        assertThat(practitionerRoleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPractitionerRoles() throws Exception {
        // Initialize the database
        practitionerRoleRepository.saveAndFlush(practitionerRole);

        // Get all the practitionerRoleList
        restPractitionerRoleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(practitionerRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].forceId").value(hasItem(DEFAULT_FORCE_ID)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }

    @Test
    @Transactional
    void getPractitionerRole() throws Exception {
        // Initialize the database
        practitionerRoleRepository.saveAndFlush(practitionerRole);

        // Get the practitionerRole
        restPractitionerRoleMockMvc
            .perform(get(ENTITY_API_URL_ID, practitionerRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(practitionerRole.getId().intValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.forceId").value(DEFAULT_FORCE_ID))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPractitionerRole() throws Exception {
        // Get the practitionerRole
        restPractitionerRoleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPractitionerRole() throws Exception {
        // Initialize the database
        practitionerRoleRepository.saveAndFlush(practitionerRole);

        int databaseSizeBeforeUpdate = practitionerRoleRepository.findAll().size();

        // Update the practitionerRole
        PractitionerRole updatedPractitionerRole = practitionerRoleRepository.findById(practitionerRole.getId()).get();
        // Disconnect from session so that the updates on updatedPractitionerRole are not directly saved in db
        em.detach(updatedPractitionerRole);
        updatedPractitionerRole.guid(UPDATED_GUID).forceId(UPDATED_FORCE_ID).start(UPDATED_START).end(UPDATED_END);

        restPractitionerRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPractitionerRole.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPractitionerRole))
            )
            .andExpect(status().isOk());

        // Validate the PractitionerRole in the database
        List<PractitionerRole> practitionerRoleList = practitionerRoleRepository.findAll();
        assertThat(practitionerRoleList).hasSize(databaseSizeBeforeUpdate);
        PractitionerRole testPractitionerRole = practitionerRoleList.get(practitionerRoleList.size() - 1);
        assertThat(testPractitionerRole.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPractitionerRole.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testPractitionerRole.getStart()).isEqualTo(UPDATED_START);
        assertThat(testPractitionerRole.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    void putNonExistingPractitionerRole() throws Exception {
        int databaseSizeBeforeUpdate = practitionerRoleRepository.findAll().size();
        practitionerRole.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPractitionerRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, practitionerRole.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(practitionerRole))
            )
            .andExpect(status().isBadRequest());

        // Validate the PractitionerRole in the database
        List<PractitionerRole> practitionerRoleList = practitionerRoleRepository.findAll();
        assertThat(practitionerRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPractitionerRole() throws Exception {
        int databaseSizeBeforeUpdate = practitionerRoleRepository.findAll().size();
        practitionerRole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPractitionerRoleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(practitionerRole))
            )
            .andExpect(status().isBadRequest());

        // Validate the PractitionerRole in the database
        List<PractitionerRole> practitionerRoleList = practitionerRoleRepository.findAll();
        assertThat(practitionerRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPractitionerRole() throws Exception {
        int databaseSizeBeforeUpdate = practitionerRoleRepository.findAll().size();
        practitionerRole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPractitionerRoleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(practitionerRole))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PractitionerRole in the database
        List<PractitionerRole> practitionerRoleList = practitionerRoleRepository.findAll();
        assertThat(practitionerRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePractitionerRoleWithPatch() throws Exception {
        // Initialize the database
        practitionerRoleRepository.saveAndFlush(practitionerRole);

        int databaseSizeBeforeUpdate = practitionerRoleRepository.findAll().size();

        // Update the practitionerRole using partial update
        PractitionerRole partialUpdatedPractitionerRole = new PractitionerRole();
        partialUpdatedPractitionerRole.setId(practitionerRole.getId());

        partialUpdatedPractitionerRole.forceId(UPDATED_FORCE_ID);

        restPractitionerRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPractitionerRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPractitionerRole))
            )
            .andExpect(status().isOk());

        // Validate the PractitionerRole in the database
        List<PractitionerRole> practitionerRoleList = practitionerRoleRepository.findAll();
        assertThat(practitionerRoleList).hasSize(databaseSizeBeforeUpdate);
        PractitionerRole testPractitionerRole = practitionerRoleList.get(practitionerRoleList.size() - 1);
        assertThat(testPractitionerRole.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testPractitionerRole.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testPractitionerRole.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testPractitionerRole.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    void fullUpdatePractitionerRoleWithPatch() throws Exception {
        // Initialize the database
        practitionerRoleRepository.saveAndFlush(practitionerRole);

        int databaseSizeBeforeUpdate = practitionerRoleRepository.findAll().size();

        // Update the practitionerRole using partial update
        PractitionerRole partialUpdatedPractitionerRole = new PractitionerRole();
        partialUpdatedPractitionerRole.setId(practitionerRole.getId());

        partialUpdatedPractitionerRole.guid(UPDATED_GUID).forceId(UPDATED_FORCE_ID).start(UPDATED_START).end(UPDATED_END);

        restPractitionerRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPractitionerRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPractitionerRole))
            )
            .andExpect(status().isOk());

        // Validate the PractitionerRole in the database
        List<PractitionerRole> practitionerRoleList = practitionerRoleRepository.findAll();
        assertThat(practitionerRoleList).hasSize(databaseSizeBeforeUpdate);
        PractitionerRole testPractitionerRole = practitionerRoleList.get(practitionerRoleList.size() - 1);
        assertThat(testPractitionerRole.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPractitionerRole.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testPractitionerRole.getStart()).isEqualTo(UPDATED_START);
        assertThat(testPractitionerRole.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    void patchNonExistingPractitionerRole() throws Exception {
        int databaseSizeBeforeUpdate = practitionerRoleRepository.findAll().size();
        practitionerRole.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPractitionerRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, practitionerRole.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(practitionerRole))
            )
            .andExpect(status().isBadRequest());

        // Validate the PractitionerRole in the database
        List<PractitionerRole> practitionerRoleList = practitionerRoleRepository.findAll();
        assertThat(practitionerRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPractitionerRole() throws Exception {
        int databaseSizeBeforeUpdate = practitionerRoleRepository.findAll().size();
        practitionerRole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPractitionerRoleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(practitionerRole))
            )
            .andExpect(status().isBadRequest());

        // Validate the PractitionerRole in the database
        List<PractitionerRole> practitionerRoleList = practitionerRoleRepository.findAll();
        assertThat(practitionerRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPractitionerRole() throws Exception {
        int databaseSizeBeforeUpdate = practitionerRoleRepository.findAll().size();
        practitionerRole.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPractitionerRoleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(practitionerRole))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PractitionerRole in the database
        List<PractitionerRole> practitionerRoleList = practitionerRoleRepository.findAll();
        assertThat(practitionerRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePractitionerRole() throws Exception {
        // Initialize the database
        practitionerRoleRepository.saveAndFlush(practitionerRole);

        int databaseSizeBeforeDelete = practitionerRoleRepository.findAll().size();

        // Delete the practitionerRole
        restPractitionerRoleMockMvc
            .perform(delete(ENTITY_API_URL_ID, practitionerRole.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PractitionerRole> practitionerRoleList = practitionerRoleRepository.findAll();
        assertThat(practitionerRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
