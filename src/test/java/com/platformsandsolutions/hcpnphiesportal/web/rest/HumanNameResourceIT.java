package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.HumanName;
import com.platformsandsolutions.hcpnphiesportal.repository.HumanNameRepository;
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
 * Integration tests for the {@link HumanNameResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HumanNameResourceIT {

    private static final String DEFAULT_FAMILY = "AAAAAAAAAA";
    private static final String UPDATED_FAMILY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/human-names";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HumanNameRepository humanNameRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHumanNameMockMvc;

    private HumanName humanName;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HumanName createEntity(EntityManager em) {
        HumanName humanName = new HumanName().family(DEFAULT_FAMILY);
        return humanName;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HumanName createUpdatedEntity(EntityManager em) {
        HumanName humanName = new HumanName().family(UPDATED_FAMILY);
        return humanName;
    }

    @BeforeEach
    public void initTest() {
        humanName = createEntity(em);
    }

    @Test
    @Transactional
    void createHumanName() throws Exception {
        int databaseSizeBeforeCreate = humanNameRepository.findAll().size();
        // Create the HumanName
        restHumanNameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(humanName)))
            .andExpect(status().isCreated());

        // Validate the HumanName in the database
        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeCreate + 1);
        HumanName testHumanName = humanNameList.get(humanNameList.size() - 1);
        assertThat(testHumanName.getFamily()).isEqualTo(DEFAULT_FAMILY);
    }

    @Test
    @Transactional
    void createHumanNameWithExistingId() throws Exception {
        // Create the HumanName with an existing ID
        humanName.setId(1L);

        int databaseSizeBeforeCreate = humanNameRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHumanNameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(humanName)))
            .andExpect(status().isBadRequest());

        // Validate the HumanName in the database
        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFamilyIsRequired() throws Exception {
        int databaseSizeBeforeTest = humanNameRepository.findAll().size();
        // set the field null
        humanName.setFamily(null);

        // Create the HumanName, which fails.

        restHumanNameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(humanName)))
            .andExpect(status().isBadRequest());

        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHumanNames() throws Exception {
        // Initialize the database
        humanNameRepository.saveAndFlush(humanName);

        // Get all the humanNameList
        restHumanNameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(humanName.getId().intValue())))
            .andExpect(jsonPath("$.[*].family").value(hasItem(DEFAULT_FAMILY)));
    }

    @Test
    @Transactional
    void getHumanName() throws Exception {
        // Initialize the database
        humanNameRepository.saveAndFlush(humanName);

        // Get the humanName
        restHumanNameMockMvc
            .perform(get(ENTITY_API_URL_ID, humanName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(humanName.getId().intValue()))
            .andExpect(jsonPath("$.family").value(DEFAULT_FAMILY));
    }

    @Test
    @Transactional
    void getNonExistingHumanName() throws Exception {
        // Get the humanName
        restHumanNameMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHumanName() throws Exception {
        // Initialize the database
        humanNameRepository.saveAndFlush(humanName);

        int databaseSizeBeforeUpdate = humanNameRepository.findAll().size();

        // Update the humanName
        HumanName updatedHumanName = humanNameRepository.findById(humanName.getId()).get();
        // Disconnect from session so that the updates on updatedHumanName are not directly saved in db
        em.detach(updatedHumanName);
        updatedHumanName.family(UPDATED_FAMILY);

        restHumanNameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHumanName.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHumanName))
            )
            .andExpect(status().isOk());

        // Validate the HumanName in the database
        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeUpdate);
        HumanName testHumanName = humanNameList.get(humanNameList.size() - 1);
        assertThat(testHumanName.getFamily()).isEqualTo(UPDATED_FAMILY);
    }

    @Test
    @Transactional
    void putNonExistingHumanName() throws Exception {
        int databaseSizeBeforeUpdate = humanNameRepository.findAll().size();
        humanName.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHumanNameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, humanName.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(humanName))
            )
            .andExpect(status().isBadRequest());

        // Validate the HumanName in the database
        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHumanName() throws Exception {
        int databaseSizeBeforeUpdate = humanNameRepository.findAll().size();
        humanName.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHumanNameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(humanName))
            )
            .andExpect(status().isBadRequest());

        // Validate the HumanName in the database
        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHumanName() throws Exception {
        int databaseSizeBeforeUpdate = humanNameRepository.findAll().size();
        humanName.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHumanNameMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(humanName)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HumanName in the database
        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHumanNameWithPatch() throws Exception {
        // Initialize the database
        humanNameRepository.saveAndFlush(humanName);

        int databaseSizeBeforeUpdate = humanNameRepository.findAll().size();

        // Update the humanName using partial update
        HumanName partialUpdatedHumanName = new HumanName();
        partialUpdatedHumanName.setId(humanName.getId());

        partialUpdatedHumanName.family(UPDATED_FAMILY);

        restHumanNameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHumanName.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHumanName))
            )
            .andExpect(status().isOk());

        // Validate the HumanName in the database
        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeUpdate);
        HumanName testHumanName = humanNameList.get(humanNameList.size() - 1);
        assertThat(testHumanName.getFamily()).isEqualTo(UPDATED_FAMILY);
    }

    @Test
    @Transactional
    void fullUpdateHumanNameWithPatch() throws Exception {
        // Initialize the database
        humanNameRepository.saveAndFlush(humanName);

        int databaseSizeBeforeUpdate = humanNameRepository.findAll().size();

        // Update the humanName using partial update
        HumanName partialUpdatedHumanName = new HumanName();
        partialUpdatedHumanName.setId(humanName.getId());

        partialUpdatedHumanName.family(UPDATED_FAMILY);

        restHumanNameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHumanName.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHumanName))
            )
            .andExpect(status().isOk());

        // Validate the HumanName in the database
        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeUpdate);
        HumanName testHumanName = humanNameList.get(humanNameList.size() - 1);
        assertThat(testHumanName.getFamily()).isEqualTo(UPDATED_FAMILY);
    }

    @Test
    @Transactional
    void patchNonExistingHumanName() throws Exception {
        int databaseSizeBeforeUpdate = humanNameRepository.findAll().size();
        humanName.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHumanNameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, humanName.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(humanName))
            )
            .andExpect(status().isBadRequest());

        // Validate the HumanName in the database
        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHumanName() throws Exception {
        int databaseSizeBeforeUpdate = humanNameRepository.findAll().size();
        humanName.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHumanNameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(humanName))
            )
            .andExpect(status().isBadRequest());

        // Validate the HumanName in the database
        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHumanName() throws Exception {
        int databaseSizeBeforeUpdate = humanNameRepository.findAll().size();
        humanName.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHumanNameMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(humanName))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HumanName in the database
        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHumanName() throws Exception {
        // Initialize the database
        humanNameRepository.saveAndFlush(humanName);

        int databaseSizeBeforeDelete = humanNameRepository.findAll().size();

        // Delete the humanName
        restHumanNameMockMvc
            .perform(delete(ENTITY_API_URL_ID, humanName.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HumanName> humanNameList = humanNameRepository.findAll();
        assertThat(humanNameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
