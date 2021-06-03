package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.InformationSequence;
import com.platformsandsolutions.hcpnphiesportal.repository.InformationSequenceRepository;
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
 * Integration tests for the {@link InformationSequenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InformationSequenceResourceIT {

    private static final Integer DEFAULT_INF_SEQ = 1;
    private static final Integer UPDATED_INF_SEQ = 2;

    private static final String ENTITY_API_URL = "/api/information-sequences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InformationSequenceRepository informationSequenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInformationSequenceMockMvc;

    private InformationSequence informationSequence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InformationSequence createEntity(EntityManager em) {
        InformationSequence informationSequence = new InformationSequence().infSeq(DEFAULT_INF_SEQ);
        return informationSequence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InformationSequence createUpdatedEntity(EntityManager em) {
        InformationSequence informationSequence = new InformationSequence().infSeq(UPDATED_INF_SEQ);
        return informationSequence;
    }

    @BeforeEach
    public void initTest() {
        informationSequence = createEntity(em);
    }

    @Test
    @Transactional
    void createInformationSequence() throws Exception {
        int databaseSizeBeforeCreate = informationSequenceRepository.findAll().size();
        // Create the InformationSequence
        restInformationSequenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(informationSequence))
            )
            .andExpect(status().isCreated());

        // Validate the InformationSequence in the database
        List<InformationSequence> informationSequenceList = informationSequenceRepository.findAll();
        assertThat(informationSequenceList).hasSize(databaseSizeBeforeCreate + 1);
        InformationSequence testInformationSequence = informationSequenceList.get(informationSequenceList.size() - 1);
        assertThat(testInformationSequence.getInfSeq()).isEqualTo(DEFAULT_INF_SEQ);
    }

    @Test
    @Transactional
    void createInformationSequenceWithExistingId() throws Exception {
        // Create the InformationSequence with an existing ID
        informationSequence.setId(1L);

        int databaseSizeBeforeCreate = informationSequenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInformationSequenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(informationSequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the InformationSequence in the database
        List<InformationSequence> informationSequenceList = informationSequenceRepository.findAll();
        assertThat(informationSequenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInformationSequences() throws Exception {
        // Initialize the database
        informationSequenceRepository.saveAndFlush(informationSequence);

        // Get all the informationSequenceList
        restInformationSequenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(informationSequence.getId().intValue())))
            .andExpect(jsonPath("$.[*].infSeq").value(hasItem(DEFAULT_INF_SEQ)));
    }

    @Test
    @Transactional
    void getInformationSequence() throws Exception {
        // Initialize the database
        informationSequenceRepository.saveAndFlush(informationSequence);

        // Get the informationSequence
        restInformationSequenceMockMvc
            .perform(get(ENTITY_API_URL_ID, informationSequence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(informationSequence.getId().intValue()))
            .andExpect(jsonPath("$.infSeq").value(DEFAULT_INF_SEQ));
    }

    @Test
    @Transactional
    void getNonExistingInformationSequence() throws Exception {
        // Get the informationSequence
        restInformationSequenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInformationSequence() throws Exception {
        // Initialize the database
        informationSequenceRepository.saveAndFlush(informationSequence);

        int databaseSizeBeforeUpdate = informationSequenceRepository.findAll().size();

        // Update the informationSequence
        InformationSequence updatedInformationSequence = informationSequenceRepository.findById(informationSequence.getId()).get();
        // Disconnect from session so that the updates on updatedInformationSequence are not directly saved in db
        em.detach(updatedInformationSequence);
        updatedInformationSequence.infSeq(UPDATED_INF_SEQ);

        restInformationSequenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInformationSequence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInformationSequence))
            )
            .andExpect(status().isOk());

        // Validate the InformationSequence in the database
        List<InformationSequence> informationSequenceList = informationSequenceRepository.findAll();
        assertThat(informationSequenceList).hasSize(databaseSizeBeforeUpdate);
        InformationSequence testInformationSequence = informationSequenceList.get(informationSequenceList.size() - 1);
        assertThat(testInformationSequence.getInfSeq()).isEqualTo(UPDATED_INF_SEQ);
    }

    @Test
    @Transactional
    void putNonExistingInformationSequence() throws Exception {
        int databaseSizeBeforeUpdate = informationSequenceRepository.findAll().size();
        informationSequence.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformationSequenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, informationSequence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(informationSequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the InformationSequence in the database
        List<InformationSequence> informationSequenceList = informationSequenceRepository.findAll();
        assertThat(informationSequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInformationSequence() throws Exception {
        int databaseSizeBeforeUpdate = informationSequenceRepository.findAll().size();
        informationSequence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInformationSequenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(informationSequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the InformationSequence in the database
        List<InformationSequence> informationSequenceList = informationSequenceRepository.findAll();
        assertThat(informationSequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInformationSequence() throws Exception {
        int databaseSizeBeforeUpdate = informationSequenceRepository.findAll().size();
        informationSequence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInformationSequenceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(informationSequence))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InformationSequence in the database
        List<InformationSequence> informationSequenceList = informationSequenceRepository.findAll();
        assertThat(informationSequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInformationSequenceWithPatch() throws Exception {
        // Initialize the database
        informationSequenceRepository.saveAndFlush(informationSequence);

        int databaseSizeBeforeUpdate = informationSequenceRepository.findAll().size();

        // Update the informationSequence using partial update
        InformationSequence partialUpdatedInformationSequence = new InformationSequence();
        partialUpdatedInformationSequence.setId(informationSequence.getId());

        partialUpdatedInformationSequence.infSeq(UPDATED_INF_SEQ);

        restInformationSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInformationSequence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInformationSequence))
            )
            .andExpect(status().isOk());

        // Validate the InformationSequence in the database
        List<InformationSequence> informationSequenceList = informationSequenceRepository.findAll();
        assertThat(informationSequenceList).hasSize(databaseSizeBeforeUpdate);
        InformationSequence testInformationSequence = informationSequenceList.get(informationSequenceList.size() - 1);
        assertThat(testInformationSequence.getInfSeq()).isEqualTo(UPDATED_INF_SEQ);
    }

    @Test
    @Transactional
    void fullUpdateInformationSequenceWithPatch() throws Exception {
        // Initialize the database
        informationSequenceRepository.saveAndFlush(informationSequence);

        int databaseSizeBeforeUpdate = informationSequenceRepository.findAll().size();

        // Update the informationSequence using partial update
        InformationSequence partialUpdatedInformationSequence = new InformationSequence();
        partialUpdatedInformationSequence.setId(informationSequence.getId());

        partialUpdatedInformationSequence.infSeq(UPDATED_INF_SEQ);

        restInformationSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInformationSequence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInformationSequence))
            )
            .andExpect(status().isOk());

        // Validate the InformationSequence in the database
        List<InformationSequence> informationSequenceList = informationSequenceRepository.findAll();
        assertThat(informationSequenceList).hasSize(databaseSizeBeforeUpdate);
        InformationSequence testInformationSequence = informationSequenceList.get(informationSequenceList.size() - 1);
        assertThat(testInformationSequence.getInfSeq()).isEqualTo(UPDATED_INF_SEQ);
    }

    @Test
    @Transactional
    void patchNonExistingInformationSequence() throws Exception {
        int databaseSizeBeforeUpdate = informationSequenceRepository.findAll().size();
        informationSequence.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformationSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, informationSequence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(informationSequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the InformationSequence in the database
        List<InformationSequence> informationSequenceList = informationSequenceRepository.findAll();
        assertThat(informationSequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInformationSequence() throws Exception {
        int databaseSizeBeforeUpdate = informationSequenceRepository.findAll().size();
        informationSequence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInformationSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(informationSequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the InformationSequence in the database
        List<InformationSequence> informationSequenceList = informationSequenceRepository.findAll();
        assertThat(informationSequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInformationSequence() throws Exception {
        int databaseSizeBeforeUpdate = informationSequenceRepository.findAll().size();
        informationSequence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInformationSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(informationSequence))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InformationSequence in the database
        List<InformationSequence> informationSequenceList = informationSequenceRepository.findAll();
        assertThat(informationSequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInformationSequence() throws Exception {
        // Initialize the database
        informationSequenceRepository.saveAndFlush(informationSequence);

        int databaseSizeBeforeDelete = informationSequenceRepository.findAll().size();

        // Delete the informationSequence
        restInformationSequenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, informationSequence.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InformationSequence> informationSequenceList = informationSequenceRepository.findAll();
        assertThat(informationSequenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
