package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Encounter;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ActPriorityEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.EncounterClassEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ServiceTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.EncounterRepository;
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
 * Integration tests for the {@link EncounterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EncounterResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_FORCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FORCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final EncounterClassEnum DEFAULT_ENCOUNTER_CLASS = EncounterClassEnum.AMB;
    private static final EncounterClassEnum UPDATED_ENCOUNTER_CLASS = EncounterClassEnum.EMER;

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ServiceTypeEnum DEFAULT_SERVICE_TYPE = ServiceTypeEnum.N237;
    private static final ServiceTypeEnum UPDATED_SERVICE_TYPE = ServiceTypeEnum.N576;

    private static final ActPriorityEnum DEFAULT_PRIORITY = ActPriorityEnum.EM;
    private static final ActPriorityEnum UPDATED_PRIORITY = ActPriorityEnum.EL;

    private static final String ENTITY_API_URL = "/api/encounters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EncounterRepository encounterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEncounterMockMvc;

    private Encounter encounter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Encounter createEntity(EntityManager em) {
        Encounter encounter = new Encounter()
            .guid(DEFAULT_GUID)
            .forceId(DEFAULT_FORCE_ID)
            .identifier(DEFAULT_IDENTIFIER)
            .encounterClass(DEFAULT_ENCOUNTER_CLASS)
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .serviceType(DEFAULT_SERVICE_TYPE)
            .priority(DEFAULT_PRIORITY);
        return encounter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Encounter createUpdatedEntity(EntityManager em) {
        Encounter encounter = new Encounter()
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .identifier(UPDATED_IDENTIFIER)
            .encounterClass(UPDATED_ENCOUNTER_CLASS)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .serviceType(UPDATED_SERVICE_TYPE)
            .priority(UPDATED_PRIORITY);
        return encounter;
    }

    @BeforeEach
    public void initTest() {
        encounter = createEntity(em);
    }

    @Test
    @Transactional
    void createEncounter() throws Exception {
        int databaseSizeBeforeCreate = encounterRepository.findAll().size();
        // Create the Encounter
        restEncounterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(encounter)))
            .andExpect(status().isCreated());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeCreate + 1);
        Encounter testEncounter = encounterList.get(encounterList.size() - 1);
        assertThat(testEncounter.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testEncounter.getForceId()).isEqualTo(DEFAULT_FORCE_ID);
        assertThat(testEncounter.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testEncounter.getEncounterClass()).isEqualTo(DEFAULT_ENCOUNTER_CLASS);
        assertThat(testEncounter.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testEncounter.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testEncounter.getServiceType()).isEqualTo(DEFAULT_SERVICE_TYPE);
        assertThat(testEncounter.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void createEncounterWithExistingId() throws Exception {
        // Create the Encounter with an existing ID
        encounter.setId(1L);

        int databaseSizeBeforeCreate = encounterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncounterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(encounter)))
            .andExpect(status().isBadRequest());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEncounters() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get all the encounterList
        restEncounterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encounter.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].forceId").value(hasItem(DEFAULT_FORCE_ID)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].encounterClass").value(hasItem(DEFAULT_ENCOUNTER_CLASS.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())));
    }

    @Test
    @Transactional
    void getEncounter() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        // Get the encounter
        restEncounterMockMvc
            .perform(get(ENTITY_API_URL_ID, encounter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(encounter.getId().intValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.forceId").value(DEFAULT_FORCE_ID))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.encounterClass").value(DEFAULT_ENCOUNTER_CLASS.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.serviceType").value(DEFAULT_SERVICE_TYPE.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEncounter() throws Exception {
        // Get the encounter
        restEncounterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEncounter() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();

        // Update the encounter
        Encounter updatedEncounter = encounterRepository.findById(encounter.getId()).get();
        // Disconnect from session so that the updates on updatedEncounter are not directly saved in db
        em.detach(updatedEncounter);
        updatedEncounter
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .identifier(UPDATED_IDENTIFIER)
            .encounterClass(UPDATED_ENCOUNTER_CLASS)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .serviceType(UPDATED_SERVICE_TYPE)
            .priority(UPDATED_PRIORITY);

        restEncounterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEncounter.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEncounter))
            )
            .andExpect(status().isOk());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
        Encounter testEncounter = encounterList.get(encounterList.size() - 1);
        assertThat(testEncounter.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testEncounter.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testEncounter.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testEncounter.getEncounterClass()).isEqualTo(UPDATED_ENCOUNTER_CLASS);
        assertThat(testEncounter.getStart()).isEqualTo(UPDATED_START);
        assertThat(testEncounter.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testEncounter.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
        assertThat(testEncounter.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void putNonExistingEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();
        encounter.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncounterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, encounter.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encounter))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();
        encounter.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncounterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(encounter))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();
        encounter.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncounterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(encounter)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEncounterWithPatch() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();

        // Update the encounter using partial update
        Encounter partialUpdatedEncounter = new Encounter();
        partialUpdatedEncounter.setId(encounter.getId());

        partialUpdatedEncounter
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .identifier(UPDATED_IDENTIFIER)
            .encounterClass(UPDATED_ENCOUNTER_CLASS)
            .start(UPDATED_START)
            .priority(UPDATED_PRIORITY);

        restEncounterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEncounter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEncounter))
            )
            .andExpect(status().isOk());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
        Encounter testEncounter = encounterList.get(encounterList.size() - 1);
        assertThat(testEncounter.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testEncounter.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testEncounter.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testEncounter.getEncounterClass()).isEqualTo(UPDATED_ENCOUNTER_CLASS);
        assertThat(testEncounter.getStart()).isEqualTo(UPDATED_START);
        assertThat(testEncounter.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testEncounter.getServiceType()).isEqualTo(DEFAULT_SERVICE_TYPE);
        assertThat(testEncounter.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void fullUpdateEncounterWithPatch() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();

        // Update the encounter using partial update
        Encounter partialUpdatedEncounter = new Encounter();
        partialUpdatedEncounter.setId(encounter.getId());

        partialUpdatedEncounter
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .identifier(UPDATED_IDENTIFIER)
            .encounterClass(UPDATED_ENCOUNTER_CLASS)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .serviceType(UPDATED_SERVICE_TYPE)
            .priority(UPDATED_PRIORITY);

        restEncounterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEncounter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEncounter))
            )
            .andExpect(status().isOk());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
        Encounter testEncounter = encounterList.get(encounterList.size() - 1);
        assertThat(testEncounter.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testEncounter.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testEncounter.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testEncounter.getEncounterClass()).isEqualTo(UPDATED_ENCOUNTER_CLASS);
        assertThat(testEncounter.getStart()).isEqualTo(UPDATED_START);
        assertThat(testEncounter.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testEncounter.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
        assertThat(testEncounter.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void patchNonExistingEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();
        encounter.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncounterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, encounter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(encounter))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();
        encounter.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncounterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(encounter))
            )
            .andExpect(status().isBadRequest());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEncounter() throws Exception {
        int databaseSizeBeforeUpdate = encounterRepository.findAll().size();
        encounter.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEncounterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(encounter))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Encounter in the database
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEncounter() throws Exception {
        // Initialize the database
        encounterRepository.saveAndFlush(encounter);

        int databaseSizeBeforeDelete = encounterRepository.findAll().size();

        // Delete the encounter
        restEncounterMockMvc
            .perform(delete(ENTITY_API_URL_ID, encounter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Encounter> encounterList = encounterRepository.findAll();
        assertThat(encounterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
