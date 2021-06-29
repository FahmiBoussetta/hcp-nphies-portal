package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Givens;
import com.platformsandsolutions.hcpnphiesportal.repository.GivensRepository;
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
 * Integration tests for the {@link GivensResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GivensResourceIT {

    private static final String DEFAULT_GIVEN = "AAAAAAAAAA";
    private static final String UPDATED_GIVEN = "BBBBBBBBBB";

    private static final String DEFAULT_PREFIX = "AAAAAAAAAA";
    private static final String UPDATED_PREFIX = "BBBBBBBBBB";

    private static final String DEFAULT_SUFFIX = "AAAAAAAAAA";
    private static final String UPDATED_SUFFIX = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/givens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GivensRepository givensRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGivensMockMvc;

    private Givens givens;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Givens createEntity(EntityManager em) {
        Givens givens = new Givens().given(DEFAULT_GIVEN).prefix(DEFAULT_PREFIX).suffix(DEFAULT_SUFFIX).textName(DEFAULT_TEXT_NAME);
        return givens;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Givens createUpdatedEntity(EntityManager em) {
        Givens givens = new Givens().given(UPDATED_GIVEN).prefix(UPDATED_PREFIX).suffix(UPDATED_SUFFIX).textName(UPDATED_TEXT_NAME);
        return givens;
    }

    @BeforeEach
    public void initTest() {
        givens = createEntity(em);
    }

    @Test
    @Transactional
    void createGivens() throws Exception {
        int databaseSizeBeforeCreate = givensRepository.findAll().size();
        // Create the Givens
        restGivensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(givens)))
            .andExpect(status().isCreated());

        // Validate the Givens in the database
        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeCreate + 1);
        Givens testGivens = givensList.get(givensList.size() - 1);
        assertThat(testGivens.getGiven()).isEqualTo(DEFAULT_GIVEN);
        assertThat(testGivens.getPrefix()).isEqualTo(DEFAULT_PREFIX);
        assertThat(testGivens.getSuffix()).isEqualTo(DEFAULT_SUFFIX);
        assertThat(testGivens.getTextName()).isEqualTo(DEFAULT_TEXT_NAME);
    }

    @Test
    @Transactional
    void createGivensWithExistingId() throws Exception {
        // Create the Givens with an existing ID
        givens.setId(1L);

        int databaseSizeBeforeCreate = givensRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGivensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(givens)))
            .andExpect(status().isBadRequest());

        // Validate the Givens in the database
        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGivenIsRequired() throws Exception {
        int databaseSizeBeforeTest = givensRepository.findAll().size();
        // set the field null
        givens.setGiven(null);

        // Create the Givens, which fails.

        restGivensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(givens)))
            .andExpect(status().isBadRequest());

        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGivens() throws Exception {
        // Initialize the database
        givensRepository.saveAndFlush(givens);

        // Get all the givensList
        restGivensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(givens.getId().intValue())))
            .andExpect(jsonPath("$.[*].given").value(hasItem(DEFAULT_GIVEN)))
            .andExpect(jsonPath("$.[*].prefix").value(hasItem(DEFAULT_PREFIX)))
            .andExpect(jsonPath("$.[*].suffix").value(hasItem(DEFAULT_SUFFIX)))
            .andExpect(jsonPath("$.[*].textName").value(hasItem(DEFAULT_TEXT_NAME)));
    }

    @Test
    @Transactional
    void getGivens() throws Exception {
        // Initialize the database
        givensRepository.saveAndFlush(givens);

        // Get the givens
        restGivensMockMvc
            .perform(get(ENTITY_API_URL_ID, givens.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(givens.getId().intValue()))
            .andExpect(jsonPath("$.given").value(DEFAULT_GIVEN))
            .andExpect(jsonPath("$.prefix").value(DEFAULT_PREFIX))
            .andExpect(jsonPath("$.suffix").value(DEFAULT_SUFFIX))
            .andExpect(jsonPath("$.textName").value(DEFAULT_TEXT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingGivens() throws Exception {
        // Get the givens
        restGivensMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGivens() throws Exception {
        // Initialize the database
        givensRepository.saveAndFlush(givens);

        int databaseSizeBeforeUpdate = givensRepository.findAll().size();

        // Update the givens
        Givens updatedGivens = givensRepository.findById(givens.getId()).get();
        // Disconnect from session so that the updates on updatedGivens are not directly saved in db
        em.detach(updatedGivens);
        updatedGivens.given(UPDATED_GIVEN).prefix(UPDATED_PREFIX).suffix(UPDATED_SUFFIX).textName(UPDATED_TEXT_NAME);

        restGivensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGivens.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGivens))
            )
            .andExpect(status().isOk());

        // Validate the Givens in the database
        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeUpdate);
        Givens testGivens = givensList.get(givensList.size() - 1);
        assertThat(testGivens.getGiven()).isEqualTo(UPDATED_GIVEN);
        assertThat(testGivens.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testGivens.getSuffix()).isEqualTo(UPDATED_SUFFIX);
        assertThat(testGivens.getTextName()).isEqualTo(UPDATED_TEXT_NAME);
    }

    @Test
    @Transactional
    void putNonExistingGivens() throws Exception {
        int databaseSizeBeforeUpdate = givensRepository.findAll().size();
        givens.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGivensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, givens.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(givens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Givens in the database
        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGivens() throws Exception {
        int databaseSizeBeforeUpdate = givensRepository.findAll().size();
        givens.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGivensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(givens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Givens in the database
        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGivens() throws Exception {
        int databaseSizeBeforeUpdate = givensRepository.findAll().size();
        givens.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGivensMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(givens)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Givens in the database
        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGivensWithPatch() throws Exception {
        // Initialize the database
        givensRepository.saveAndFlush(givens);

        int databaseSizeBeforeUpdate = givensRepository.findAll().size();

        // Update the givens using partial update
        Givens partialUpdatedGivens = new Givens();
        partialUpdatedGivens.setId(givens.getId());

        partialUpdatedGivens.prefix(UPDATED_PREFIX);

        restGivensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGivens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGivens))
            )
            .andExpect(status().isOk());

        // Validate the Givens in the database
        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeUpdate);
        Givens testGivens = givensList.get(givensList.size() - 1);
        assertThat(testGivens.getGiven()).isEqualTo(DEFAULT_GIVEN);
        assertThat(testGivens.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testGivens.getSuffix()).isEqualTo(DEFAULT_SUFFIX);
        assertThat(testGivens.getTextName()).isEqualTo(DEFAULT_TEXT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateGivensWithPatch() throws Exception {
        // Initialize the database
        givensRepository.saveAndFlush(givens);

        int databaseSizeBeforeUpdate = givensRepository.findAll().size();

        // Update the givens using partial update
        Givens partialUpdatedGivens = new Givens();
        partialUpdatedGivens.setId(givens.getId());

        partialUpdatedGivens.given(UPDATED_GIVEN).prefix(UPDATED_PREFIX).suffix(UPDATED_SUFFIX).textName(UPDATED_TEXT_NAME);

        restGivensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGivens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGivens))
            )
            .andExpect(status().isOk());

        // Validate the Givens in the database
        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeUpdate);
        Givens testGivens = givensList.get(givensList.size() - 1);
        assertThat(testGivens.getGiven()).isEqualTo(UPDATED_GIVEN);
        assertThat(testGivens.getPrefix()).isEqualTo(UPDATED_PREFIX);
        assertThat(testGivens.getSuffix()).isEqualTo(UPDATED_SUFFIX);
        assertThat(testGivens.getTextName()).isEqualTo(UPDATED_TEXT_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingGivens() throws Exception {
        int databaseSizeBeforeUpdate = givensRepository.findAll().size();
        givens.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGivensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, givens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(givens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Givens in the database
        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGivens() throws Exception {
        int databaseSizeBeforeUpdate = givensRepository.findAll().size();
        givens.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGivensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(givens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Givens in the database
        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGivens() throws Exception {
        int databaseSizeBeforeUpdate = givensRepository.findAll().size();
        givens.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGivensMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(givens)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Givens in the database
        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGivens() throws Exception {
        // Initialize the database
        givensRepository.saveAndFlush(givens);

        int databaseSizeBeforeDelete = givensRepository.findAll().size();

        // Delete the givens
        restGivensMockMvc
            .perform(delete(ENTITY_API_URL_ID, givens.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Givens> givensList = givensRepository.findAll();
        assertThat(givensList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
