package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationDetailNotes;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationDetailNotesRepository;
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
 * Integration tests for the {@link AdjudicationDetailNotesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdjudicationDetailNotesResourceIT {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/adjudication-detail-notes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdjudicationDetailNotesRepository adjudicationDetailNotesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdjudicationDetailNotesMockMvc;

    private AdjudicationDetailNotes adjudicationDetailNotes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjudicationDetailNotes createEntity(EntityManager em) {
        AdjudicationDetailNotes adjudicationDetailNotes = new AdjudicationDetailNotes().note(DEFAULT_NOTE);
        return adjudicationDetailNotes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjudicationDetailNotes createUpdatedEntity(EntityManager em) {
        AdjudicationDetailNotes adjudicationDetailNotes = new AdjudicationDetailNotes().note(UPDATED_NOTE);
        return adjudicationDetailNotes;
    }

    @BeforeEach
    public void initTest() {
        adjudicationDetailNotes = createEntity(em);
    }

    @Test
    @Transactional
    void createAdjudicationDetailNotes() throws Exception {
        int databaseSizeBeforeCreate = adjudicationDetailNotesRepository.findAll().size();
        // Create the AdjudicationDetailNotes
        restAdjudicationDetailNotesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailNotes))
            )
            .andExpect(status().isCreated());

        // Validate the AdjudicationDetailNotes in the database
        List<AdjudicationDetailNotes> adjudicationDetailNotesList = adjudicationDetailNotesRepository.findAll();
        assertThat(adjudicationDetailNotesList).hasSize(databaseSizeBeforeCreate + 1);
        AdjudicationDetailNotes testAdjudicationDetailNotes = adjudicationDetailNotesList.get(adjudicationDetailNotesList.size() - 1);
        assertThat(testAdjudicationDetailNotes.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void createAdjudicationDetailNotesWithExistingId() throws Exception {
        // Create the AdjudicationDetailNotes with an existing ID
        adjudicationDetailNotes.setId(1L);

        int databaseSizeBeforeCreate = adjudicationDetailNotesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjudicationDetailNotesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationDetailNotes in the database
        List<AdjudicationDetailNotes> adjudicationDetailNotesList = adjudicationDetailNotesRepository.findAll();
        assertThat(adjudicationDetailNotesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdjudicationDetailNotes() throws Exception {
        // Initialize the database
        adjudicationDetailNotesRepository.saveAndFlush(adjudicationDetailNotes);

        // Get all the adjudicationDetailNotesList
        restAdjudicationDetailNotesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjudicationDetailNotes.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    @Transactional
    void getAdjudicationDetailNotes() throws Exception {
        // Initialize the database
        adjudicationDetailNotesRepository.saveAndFlush(adjudicationDetailNotes);

        // Get the adjudicationDetailNotes
        restAdjudicationDetailNotesMockMvc
            .perform(get(ENTITY_API_URL_ID, adjudicationDetailNotes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adjudicationDetailNotes.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    void getNonExistingAdjudicationDetailNotes() throws Exception {
        // Get the adjudicationDetailNotes
        restAdjudicationDetailNotesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdjudicationDetailNotes() throws Exception {
        // Initialize the database
        adjudicationDetailNotesRepository.saveAndFlush(adjudicationDetailNotes);

        int databaseSizeBeforeUpdate = adjudicationDetailNotesRepository.findAll().size();

        // Update the adjudicationDetailNotes
        AdjudicationDetailNotes updatedAdjudicationDetailNotes = adjudicationDetailNotesRepository
            .findById(adjudicationDetailNotes.getId())
            .get();
        // Disconnect from session so that the updates on updatedAdjudicationDetailNotes are not directly saved in db
        em.detach(updatedAdjudicationDetailNotes);
        updatedAdjudicationDetailNotes.note(UPDATED_NOTE);

        restAdjudicationDetailNotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdjudicationDetailNotes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdjudicationDetailNotes))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationDetailNotes in the database
        List<AdjudicationDetailNotes> adjudicationDetailNotesList = adjudicationDetailNotesRepository.findAll();
        assertThat(adjudicationDetailNotesList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationDetailNotes testAdjudicationDetailNotes = adjudicationDetailNotesList.get(adjudicationDetailNotesList.size() - 1);
        assertThat(testAdjudicationDetailNotes.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingAdjudicationDetailNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationDetailNotesRepository.findAll().size();
        adjudicationDetailNotes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationDetailNotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adjudicationDetailNotes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationDetailNotes in the database
        List<AdjudicationDetailNotes> adjudicationDetailNotesList = adjudicationDetailNotesRepository.findAll();
        assertThat(adjudicationDetailNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdjudicationDetailNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationDetailNotesRepository.findAll().size();
        adjudicationDetailNotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationDetailNotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationDetailNotes in the database
        List<AdjudicationDetailNotes> adjudicationDetailNotesList = adjudicationDetailNotesRepository.findAll();
        assertThat(adjudicationDetailNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdjudicationDetailNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationDetailNotesRepository.findAll().size();
        adjudicationDetailNotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationDetailNotesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailNotes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdjudicationDetailNotes in the database
        List<AdjudicationDetailNotes> adjudicationDetailNotesList = adjudicationDetailNotesRepository.findAll();
        assertThat(adjudicationDetailNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdjudicationDetailNotesWithPatch() throws Exception {
        // Initialize the database
        adjudicationDetailNotesRepository.saveAndFlush(adjudicationDetailNotes);

        int databaseSizeBeforeUpdate = adjudicationDetailNotesRepository.findAll().size();

        // Update the adjudicationDetailNotes using partial update
        AdjudicationDetailNotes partialUpdatedAdjudicationDetailNotes = new AdjudicationDetailNotes();
        partialUpdatedAdjudicationDetailNotes.setId(adjudicationDetailNotes.getId());

        restAdjudicationDetailNotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudicationDetailNotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudicationDetailNotes))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationDetailNotes in the database
        List<AdjudicationDetailNotes> adjudicationDetailNotesList = adjudicationDetailNotesRepository.findAll();
        assertThat(adjudicationDetailNotesList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationDetailNotes testAdjudicationDetailNotes = adjudicationDetailNotesList.get(adjudicationDetailNotesList.size() - 1);
        assertThat(testAdjudicationDetailNotes.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateAdjudicationDetailNotesWithPatch() throws Exception {
        // Initialize the database
        adjudicationDetailNotesRepository.saveAndFlush(adjudicationDetailNotes);

        int databaseSizeBeforeUpdate = adjudicationDetailNotesRepository.findAll().size();

        // Update the adjudicationDetailNotes using partial update
        AdjudicationDetailNotes partialUpdatedAdjudicationDetailNotes = new AdjudicationDetailNotes();
        partialUpdatedAdjudicationDetailNotes.setId(adjudicationDetailNotes.getId());

        partialUpdatedAdjudicationDetailNotes.note(UPDATED_NOTE);

        restAdjudicationDetailNotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudicationDetailNotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudicationDetailNotes))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationDetailNotes in the database
        List<AdjudicationDetailNotes> adjudicationDetailNotesList = adjudicationDetailNotesRepository.findAll();
        assertThat(adjudicationDetailNotesList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationDetailNotes testAdjudicationDetailNotes = adjudicationDetailNotesList.get(adjudicationDetailNotesList.size() - 1);
        assertThat(testAdjudicationDetailNotes.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingAdjudicationDetailNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationDetailNotesRepository.findAll().size();
        adjudicationDetailNotes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationDetailNotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adjudicationDetailNotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationDetailNotes in the database
        List<AdjudicationDetailNotes> adjudicationDetailNotesList = adjudicationDetailNotesRepository.findAll();
        assertThat(adjudicationDetailNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdjudicationDetailNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationDetailNotesRepository.findAll().size();
        adjudicationDetailNotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationDetailNotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationDetailNotes in the database
        List<AdjudicationDetailNotes> adjudicationDetailNotesList = adjudicationDetailNotesRepository.findAll();
        assertThat(adjudicationDetailNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdjudicationDetailNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationDetailNotesRepository.findAll().size();
        adjudicationDetailNotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationDetailNotesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailNotes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdjudicationDetailNotes in the database
        List<AdjudicationDetailNotes> adjudicationDetailNotesList = adjudicationDetailNotesRepository.findAll();
        assertThat(adjudicationDetailNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdjudicationDetailNotes() throws Exception {
        // Initialize the database
        adjudicationDetailNotesRepository.saveAndFlush(adjudicationDetailNotes);

        int databaseSizeBeforeDelete = adjudicationDetailNotesRepository.findAll().size();

        // Delete the adjudicationDetailNotes
        restAdjudicationDetailNotesMockMvc
            .perform(delete(ENTITY_API_URL_ID, adjudicationDetailNotes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdjudicationDetailNotes> adjudicationDetailNotesList = adjudicationDetailNotesRepository.findAll();
        assertThat(adjudicationDetailNotesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
