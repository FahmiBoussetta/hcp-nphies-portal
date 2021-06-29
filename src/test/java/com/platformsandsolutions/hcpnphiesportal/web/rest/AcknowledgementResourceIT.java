package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Acknowledgement;
import com.platformsandsolutions.hcpnphiesportal.repository.AcknowledgementRepository;
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
 * Integration tests for the {@link AcknowledgementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AcknowledgementResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_PARSED = "AAAAAAAAAA";
    private static final String UPDATED_PARSED = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/acknowledgements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AcknowledgementRepository acknowledgementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcknowledgementMockMvc;

    private Acknowledgement acknowledgement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acknowledgement createEntity(EntityManager em) {
        Acknowledgement acknowledgement = new Acknowledgement().value(DEFAULT_VALUE).system(DEFAULT_SYSTEM).parsed(DEFAULT_PARSED);
        return acknowledgement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acknowledgement createUpdatedEntity(EntityManager em) {
        Acknowledgement acknowledgement = new Acknowledgement().value(UPDATED_VALUE).system(UPDATED_SYSTEM).parsed(UPDATED_PARSED);
        return acknowledgement;
    }

    @BeforeEach
    public void initTest() {
        acknowledgement = createEntity(em);
    }

    @Test
    @Transactional
    void createAcknowledgement() throws Exception {
        int databaseSizeBeforeCreate = acknowledgementRepository.findAll().size();
        // Create the Acknowledgement
        restAcknowledgementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acknowledgement))
            )
            .andExpect(status().isCreated());

        // Validate the Acknowledgement in the database
        List<Acknowledgement> acknowledgementList = acknowledgementRepository.findAll();
        assertThat(acknowledgementList).hasSize(databaseSizeBeforeCreate + 1);
        Acknowledgement testAcknowledgement = acknowledgementList.get(acknowledgementList.size() - 1);
        assertThat(testAcknowledgement.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAcknowledgement.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testAcknowledgement.getParsed()).isEqualTo(DEFAULT_PARSED);
    }

    @Test
    @Transactional
    void createAcknowledgementWithExistingId() throws Exception {
        // Create the Acknowledgement with an existing ID
        acknowledgement.setId(1L);

        int databaseSizeBeforeCreate = acknowledgementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcknowledgementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acknowledgement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acknowledgement in the database
        List<Acknowledgement> acknowledgementList = acknowledgementRepository.findAll();
        assertThat(acknowledgementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAcknowledgements() throws Exception {
        // Initialize the database
        acknowledgementRepository.saveAndFlush(acknowledgement);

        // Get all the acknowledgementList
        restAcknowledgementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acknowledgement.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM)))
            .andExpect(jsonPath("$.[*].parsed").value(hasItem(DEFAULT_PARSED)));
    }

    @Test
    @Transactional
    void getAcknowledgement() throws Exception {
        // Initialize the database
        acknowledgementRepository.saveAndFlush(acknowledgement);

        // Get the acknowledgement
        restAcknowledgementMockMvc
            .perform(get(ENTITY_API_URL_ID, acknowledgement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acknowledgement.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM))
            .andExpect(jsonPath("$.parsed").value(DEFAULT_PARSED));
    }

    @Test
    @Transactional
    void getNonExistingAcknowledgement() throws Exception {
        // Get the acknowledgement
        restAcknowledgementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAcknowledgement() throws Exception {
        // Initialize the database
        acknowledgementRepository.saveAndFlush(acknowledgement);

        int databaseSizeBeforeUpdate = acknowledgementRepository.findAll().size();

        // Update the acknowledgement
        Acknowledgement updatedAcknowledgement = acknowledgementRepository.findById(acknowledgement.getId()).get();
        // Disconnect from session so that the updates on updatedAcknowledgement are not directly saved in db
        em.detach(updatedAcknowledgement);
        updatedAcknowledgement.value(UPDATED_VALUE).system(UPDATED_SYSTEM).parsed(UPDATED_PARSED);

        restAcknowledgementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAcknowledgement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAcknowledgement))
            )
            .andExpect(status().isOk());

        // Validate the Acknowledgement in the database
        List<Acknowledgement> acknowledgementList = acknowledgementRepository.findAll();
        assertThat(acknowledgementList).hasSize(databaseSizeBeforeUpdate);
        Acknowledgement testAcknowledgement = acknowledgementList.get(acknowledgementList.size() - 1);
        assertThat(testAcknowledgement.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAcknowledgement.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testAcknowledgement.getParsed()).isEqualTo(UPDATED_PARSED);
    }

    @Test
    @Transactional
    void putNonExistingAcknowledgement() throws Exception {
        int databaseSizeBeforeUpdate = acknowledgementRepository.findAll().size();
        acknowledgement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcknowledgementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, acknowledgement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acknowledgement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acknowledgement in the database
        List<Acknowledgement> acknowledgementList = acknowledgementRepository.findAll();
        assertThat(acknowledgementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcknowledgement() throws Exception {
        int databaseSizeBeforeUpdate = acknowledgementRepository.findAll().size();
        acknowledgement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcknowledgementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(acknowledgement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acknowledgement in the database
        List<Acknowledgement> acknowledgementList = acknowledgementRepository.findAll();
        assertThat(acknowledgementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcknowledgement() throws Exception {
        int databaseSizeBeforeUpdate = acknowledgementRepository.findAll().size();
        acknowledgement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcknowledgementMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(acknowledgement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Acknowledgement in the database
        List<Acknowledgement> acknowledgementList = acknowledgementRepository.findAll();
        assertThat(acknowledgementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcknowledgementWithPatch() throws Exception {
        // Initialize the database
        acknowledgementRepository.saveAndFlush(acknowledgement);

        int databaseSizeBeforeUpdate = acknowledgementRepository.findAll().size();

        // Update the acknowledgement using partial update
        Acknowledgement partialUpdatedAcknowledgement = new Acknowledgement();
        partialUpdatedAcknowledgement.setId(acknowledgement.getId());

        partialUpdatedAcknowledgement.value(UPDATED_VALUE).parsed(UPDATED_PARSED);

        restAcknowledgementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcknowledgement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcknowledgement))
            )
            .andExpect(status().isOk());

        // Validate the Acknowledgement in the database
        List<Acknowledgement> acknowledgementList = acknowledgementRepository.findAll();
        assertThat(acknowledgementList).hasSize(databaseSizeBeforeUpdate);
        Acknowledgement testAcknowledgement = acknowledgementList.get(acknowledgementList.size() - 1);
        assertThat(testAcknowledgement.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAcknowledgement.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testAcknowledgement.getParsed()).isEqualTo(UPDATED_PARSED);
    }

    @Test
    @Transactional
    void fullUpdateAcknowledgementWithPatch() throws Exception {
        // Initialize the database
        acknowledgementRepository.saveAndFlush(acknowledgement);

        int databaseSizeBeforeUpdate = acknowledgementRepository.findAll().size();

        // Update the acknowledgement using partial update
        Acknowledgement partialUpdatedAcknowledgement = new Acknowledgement();
        partialUpdatedAcknowledgement.setId(acknowledgement.getId());

        partialUpdatedAcknowledgement.value(UPDATED_VALUE).system(UPDATED_SYSTEM).parsed(UPDATED_PARSED);

        restAcknowledgementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcknowledgement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcknowledgement))
            )
            .andExpect(status().isOk());

        // Validate the Acknowledgement in the database
        List<Acknowledgement> acknowledgementList = acknowledgementRepository.findAll();
        assertThat(acknowledgementList).hasSize(databaseSizeBeforeUpdate);
        Acknowledgement testAcknowledgement = acknowledgementList.get(acknowledgementList.size() - 1);
        assertThat(testAcknowledgement.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAcknowledgement.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testAcknowledgement.getParsed()).isEqualTo(UPDATED_PARSED);
    }

    @Test
    @Transactional
    void patchNonExistingAcknowledgement() throws Exception {
        int databaseSizeBeforeUpdate = acknowledgementRepository.findAll().size();
        acknowledgement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcknowledgementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, acknowledgement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acknowledgement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acknowledgement in the database
        List<Acknowledgement> acknowledgementList = acknowledgementRepository.findAll();
        assertThat(acknowledgementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcknowledgement() throws Exception {
        int databaseSizeBeforeUpdate = acknowledgementRepository.findAll().size();
        acknowledgement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcknowledgementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acknowledgement))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acknowledgement in the database
        List<Acknowledgement> acknowledgementList = acknowledgementRepository.findAll();
        assertThat(acknowledgementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcknowledgement() throws Exception {
        int databaseSizeBeforeUpdate = acknowledgementRepository.findAll().size();
        acknowledgement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcknowledgementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(acknowledgement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Acknowledgement in the database
        List<Acknowledgement> acknowledgementList = acknowledgementRepository.findAll();
        assertThat(acknowledgementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcknowledgement() throws Exception {
        // Initialize the database
        acknowledgementRepository.saveAndFlush(acknowledgement);

        int databaseSizeBeforeDelete = acknowledgementRepository.findAll().size();

        // Delete the acknowledgement
        restAcknowledgementMockMvc
            .perform(delete(ENTITY_API_URL_ID, acknowledgement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Acknowledgement> acknowledgementList = acknowledgementRepository.findAll();
        assertThat(acknowledgementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
