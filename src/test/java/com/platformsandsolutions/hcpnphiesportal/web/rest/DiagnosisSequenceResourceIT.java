package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.DiagnosisSequence;
import com.platformsandsolutions.hcpnphiesportal.repository.DiagnosisSequenceRepository;
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
 * Integration tests for the {@link DiagnosisSequenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DiagnosisSequenceResourceIT {

    private static final Integer DEFAULT_DIAG_SEQ = 1;
    private static final Integer UPDATED_DIAG_SEQ = 2;

    private static final String ENTITY_API_URL = "/api/diagnosis-sequences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiagnosisSequenceRepository diagnosisSequenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiagnosisSequenceMockMvc;

    private DiagnosisSequence diagnosisSequence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiagnosisSequence createEntity(EntityManager em) {
        DiagnosisSequence diagnosisSequence = new DiagnosisSequence().diagSeq(DEFAULT_DIAG_SEQ);
        return diagnosisSequence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiagnosisSequence createUpdatedEntity(EntityManager em) {
        DiagnosisSequence diagnosisSequence = new DiagnosisSequence().diagSeq(UPDATED_DIAG_SEQ);
        return diagnosisSequence;
    }

    @BeforeEach
    public void initTest() {
        diagnosisSequence = createEntity(em);
    }

    @Test
    @Transactional
    void createDiagnosisSequence() throws Exception {
        int databaseSizeBeforeCreate = diagnosisSequenceRepository.findAll().size();
        // Create the DiagnosisSequence
        restDiagnosisSequenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diagnosisSequence))
            )
            .andExpect(status().isCreated());

        // Validate the DiagnosisSequence in the database
        List<DiagnosisSequence> diagnosisSequenceList = diagnosisSequenceRepository.findAll();
        assertThat(diagnosisSequenceList).hasSize(databaseSizeBeforeCreate + 1);
        DiagnosisSequence testDiagnosisSequence = diagnosisSequenceList.get(diagnosisSequenceList.size() - 1);
        assertThat(testDiagnosisSequence.getDiagSeq()).isEqualTo(DEFAULT_DIAG_SEQ);
    }

    @Test
    @Transactional
    void createDiagnosisSequenceWithExistingId() throws Exception {
        // Create the DiagnosisSequence with an existing ID
        diagnosisSequence.setId(1L);

        int databaseSizeBeforeCreate = diagnosisSequenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiagnosisSequenceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diagnosisSequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosisSequence in the database
        List<DiagnosisSequence> diagnosisSequenceList = diagnosisSequenceRepository.findAll();
        assertThat(diagnosisSequenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDiagnosisSequences() throws Exception {
        // Initialize the database
        diagnosisSequenceRepository.saveAndFlush(diagnosisSequence);

        // Get all the diagnosisSequenceList
        restDiagnosisSequenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diagnosisSequence.getId().intValue())))
            .andExpect(jsonPath("$.[*].diagSeq").value(hasItem(DEFAULT_DIAG_SEQ)));
    }

    @Test
    @Transactional
    void getDiagnosisSequence() throws Exception {
        // Initialize the database
        diagnosisSequenceRepository.saveAndFlush(diagnosisSequence);

        // Get the diagnosisSequence
        restDiagnosisSequenceMockMvc
            .perform(get(ENTITY_API_URL_ID, diagnosisSequence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diagnosisSequence.getId().intValue()))
            .andExpect(jsonPath("$.diagSeq").value(DEFAULT_DIAG_SEQ));
    }

    @Test
    @Transactional
    void getNonExistingDiagnosisSequence() throws Exception {
        // Get the diagnosisSequence
        restDiagnosisSequenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDiagnosisSequence() throws Exception {
        // Initialize the database
        diagnosisSequenceRepository.saveAndFlush(diagnosisSequence);

        int databaseSizeBeforeUpdate = diagnosisSequenceRepository.findAll().size();

        // Update the diagnosisSequence
        DiagnosisSequence updatedDiagnosisSequence = diagnosisSequenceRepository.findById(diagnosisSequence.getId()).get();
        // Disconnect from session so that the updates on updatedDiagnosisSequence are not directly saved in db
        em.detach(updatedDiagnosisSequence);
        updatedDiagnosisSequence.diagSeq(UPDATED_DIAG_SEQ);

        restDiagnosisSequenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDiagnosisSequence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDiagnosisSequence))
            )
            .andExpect(status().isOk());

        // Validate the DiagnosisSequence in the database
        List<DiagnosisSequence> diagnosisSequenceList = diagnosisSequenceRepository.findAll();
        assertThat(diagnosisSequenceList).hasSize(databaseSizeBeforeUpdate);
        DiagnosisSequence testDiagnosisSequence = diagnosisSequenceList.get(diagnosisSequenceList.size() - 1);
        assertThat(testDiagnosisSequence.getDiagSeq()).isEqualTo(UPDATED_DIAG_SEQ);
    }

    @Test
    @Transactional
    void putNonExistingDiagnosisSequence() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisSequenceRepository.findAll().size();
        diagnosisSequence.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiagnosisSequenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, diagnosisSequence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diagnosisSequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosisSequence in the database
        List<DiagnosisSequence> diagnosisSequenceList = diagnosisSequenceRepository.findAll();
        assertThat(diagnosisSequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDiagnosisSequence() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisSequenceRepository.findAll().size();
        diagnosisSequence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosisSequenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(diagnosisSequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosisSequence in the database
        List<DiagnosisSequence> diagnosisSequenceList = diagnosisSequenceRepository.findAll();
        assertThat(diagnosisSequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDiagnosisSequence() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisSequenceRepository.findAll().size();
        diagnosisSequence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosisSequenceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(diagnosisSequence))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiagnosisSequence in the database
        List<DiagnosisSequence> diagnosisSequenceList = diagnosisSequenceRepository.findAll();
        assertThat(diagnosisSequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDiagnosisSequenceWithPatch() throws Exception {
        // Initialize the database
        diagnosisSequenceRepository.saveAndFlush(diagnosisSequence);

        int databaseSizeBeforeUpdate = diagnosisSequenceRepository.findAll().size();

        // Update the diagnosisSequence using partial update
        DiagnosisSequence partialUpdatedDiagnosisSequence = new DiagnosisSequence();
        partialUpdatedDiagnosisSequence.setId(diagnosisSequence.getId());

        restDiagnosisSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiagnosisSequence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiagnosisSequence))
            )
            .andExpect(status().isOk());

        // Validate the DiagnosisSequence in the database
        List<DiagnosisSequence> diagnosisSequenceList = diagnosisSequenceRepository.findAll();
        assertThat(diagnosisSequenceList).hasSize(databaseSizeBeforeUpdate);
        DiagnosisSequence testDiagnosisSequence = diagnosisSequenceList.get(diagnosisSequenceList.size() - 1);
        assertThat(testDiagnosisSequence.getDiagSeq()).isEqualTo(DEFAULT_DIAG_SEQ);
    }

    @Test
    @Transactional
    void fullUpdateDiagnosisSequenceWithPatch() throws Exception {
        // Initialize the database
        diagnosisSequenceRepository.saveAndFlush(diagnosisSequence);

        int databaseSizeBeforeUpdate = diagnosisSequenceRepository.findAll().size();

        // Update the diagnosisSequence using partial update
        DiagnosisSequence partialUpdatedDiagnosisSequence = new DiagnosisSequence();
        partialUpdatedDiagnosisSequence.setId(diagnosisSequence.getId());

        partialUpdatedDiagnosisSequence.diagSeq(UPDATED_DIAG_SEQ);

        restDiagnosisSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDiagnosisSequence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDiagnosisSequence))
            )
            .andExpect(status().isOk());

        // Validate the DiagnosisSequence in the database
        List<DiagnosisSequence> diagnosisSequenceList = diagnosisSequenceRepository.findAll();
        assertThat(diagnosisSequenceList).hasSize(databaseSizeBeforeUpdate);
        DiagnosisSequence testDiagnosisSequence = diagnosisSequenceList.get(diagnosisSequenceList.size() - 1);
        assertThat(testDiagnosisSequence.getDiagSeq()).isEqualTo(UPDATED_DIAG_SEQ);
    }

    @Test
    @Transactional
    void patchNonExistingDiagnosisSequence() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisSequenceRepository.findAll().size();
        diagnosisSequence.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiagnosisSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, diagnosisSequence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diagnosisSequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosisSequence in the database
        List<DiagnosisSequence> diagnosisSequenceList = diagnosisSequenceRepository.findAll();
        assertThat(diagnosisSequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDiagnosisSequence() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisSequenceRepository.findAll().size();
        diagnosisSequence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosisSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diagnosisSequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the DiagnosisSequence in the database
        List<DiagnosisSequence> diagnosisSequenceList = diagnosisSequenceRepository.findAll();
        assertThat(diagnosisSequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDiagnosisSequence() throws Exception {
        int databaseSizeBeforeUpdate = diagnosisSequenceRepository.findAll().size();
        diagnosisSequence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDiagnosisSequenceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(diagnosisSequence))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DiagnosisSequence in the database
        List<DiagnosisSequence> diagnosisSequenceList = diagnosisSequenceRepository.findAll();
        assertThat(diagnosisSequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDiagnosisSequence() throws Exception {
        // Initialize the database
        diagnosisSequenceRepository.saveAndFlush(diagnosisSequence);

        int databaseSizeBeforeDelete = diagnosisSequenceRepository.findAll().size();

        // Delete the diagnosisSequence
        restDiagnosisSequenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, diagnosisSequence.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiagnosisSequence> diagnosisSequenceList = diagnosisSequenceRepository.findAll();
        assertThat(diagnosisSequenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
