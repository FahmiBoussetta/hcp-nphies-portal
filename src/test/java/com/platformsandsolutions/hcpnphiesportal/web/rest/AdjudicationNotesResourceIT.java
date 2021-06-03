package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationNotes;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationNotesRepository;
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
 * Integration tests for the {@link AdjudicationNotesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdjudicationNotesResourceIT {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/adjudication-notes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdjudicationNotesRepository adjudicationNotesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdjudicationNotesMockMvc;

    private AdjudicationNotes adjudicationNotes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjudicationNotes createEntity(EntityManager em) {
        AdjudicationNotes adjudicationNotes = new AdjudicationNotes().note(DEFAULT_NOTE);
        return adjudicationNotes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjudicationNotes createUpdatedEntity(EntityManager em) {
        AdjudicationNotes adjudicationNotes = new AdjudicationNotes().note(UPDATED_NOTE);
        return adjudicationNotes;
    }

    @BeforeEach
    public void initTest() {
        adjudicationNotes = createEntity(em);
    }

    @Test
    @Transactional
    void createAdjudicationNotes() throws Exception {
        int databaseSizeBeforeCreate = adjudicationNotesRepository.findAll().size();
        // Create the AdjudicationNotes
        restAdjudicationNotesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adjudicationNotes))
            )
            .andExpect(status().isCreated());

        // Validate the AdjudicationNotes in the database
        List<AdjudicationNotes> adjudicationNotesList = adjudicationNotesRepository.findAll();
        assertThat(adjudicationNotesList).hasSize(databaseSizeBeforeCreate + 1);
        AdjudicationNotes testAdjudicationNotes = adjudicationNotesList.get(adjudicationNotesList.size() - 1);
        assertThat(testAdjudicationNotes.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void createAdjudicationNotesWithExistingId() throws Exception {
        // Create the AdjudicationNotes with an existing ID
        adjudicationNotes.setId(1L);

        int databaseSizeBeforeCreate = adjudicationNotesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjudicationNotesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adjudicationNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationNotes in the database
        List<AdjudicationNotes> adjudicationNotesList = adjudicationNotesRepository.findAll();
        assertThat(adjudicationNotesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdjudicationNotes() throws Exception {
        // Initialize the database
        adjudicationNotesRepository.saveAndFlush(adjudicationNotes);

        // Get all the adjudicationNotesList
        restAdjudicationNotesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjudicationNotes.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    @Transactional
    void getAdjudicationNotes() throws Exception {
        // Initialize the database
        adjudicationNotesRepository.saveAndFlush(adjudicationNotes);

        // Get the adjudicationNotes
        restAdjudicationNotesMockMvc
            .perform(get(ENTITY_API_URL_ID, adjudicationNotes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adjudicationNotes.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    void getNonExistingAdjudicationNotes() throws Exception {
        // Get the adjudicationNotes
        restAdjudicationNotesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdjudicationNotes() throws Exception {
        // Initialize the database
        adjudicationNotesRepository.saveAndFlush(adjudicationNotes);

        int databaseSizeBeforeUpdate = adjudicationNotesRepository.findAll().size();

        // Update the adjudicationNotes
        AdjudicationNotes updatedAdjudicationNotes = adjudicationNotesRepository.findById(adjudicationNotes.getId()).get();
        // Disconnect from session so that the updates on updatedAdjudicationNotes are not directly saved in db
        em.detach(updatedAdjudicationNotes);
        updatedAdjudicationNotes.note(UPDATED_NOTE);

        restAdjudicationNotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdjudicationNotes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdjudicationNotes))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationNotes in the database
        List<AdjudicationNotes> adjudicationNotesList = adjudicationNotesRepository.findAll();
        assertThat(adjudicationNotesList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationNotes testAdjudicationNotes = adjudicationNotesList.get(adjudicationNotesList.size() - 1);
        assertThat(testAdjudicationNotes.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingAdjudicationNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationNotesRepository.findAll().size();
        adjudicationNotes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationNotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adjudicationNotes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationNotes in the database
        List<AdjudicationNotes> adjudicationNotesList = adjudicationNotesRepository.findAll();
        assertThat(adjudicationNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdjudicationNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationNotesRepository.findAll().size();
        adjudicationNotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationNotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationNotes in the database
        List<AdjudicationNotes> adjudicationNotesList = adjudicationNotesRepository.findAll();
        assertThat(adjudicationNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdjudicationNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationNotesRepository.findAll().size();
        adjudicationNotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationNotesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adjudicationNotes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdjudicationNotes in the database
        List<AdjudicationNotes> adjudicationNotesList = adjudicationNotesRepository.findAll();
        assertThat(adjudicationNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdjudicationNotesWithPatch() throws Exception {
        // Initialize the database
        adjudicationNotesRepository.saveAndFlush(adjudicationNotes);

        int databaseSizeBeforeUpdate = adjudicationNotesRepository.findAll().size();

        // Update the adjudicationNotes using partial update
        AdjudicationNotes partialUpdatedAdjudicationNotes = new AdjudicationNotes();
        partialUpdatedAdjudicationNotes.setId(adjudicationNotes.getId());

        restAdjudicationNotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudicationNotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudicationNotes))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationNotes in the database
        List<AdjudicationNotes> adjudicationNotesList = adjudicationNotesRepository.findAll();
        assertThat(adjudicationNotesList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationNotes testAdjudicationNotes = adjudicationNotesList.get(adjudicationNotesList.size() - 1);
        assertThat(testAdjudicationNotes.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateAdjudicationNotesWithPatch() throws Exception {
        // Initialize the database
        adjudicationNotesRepository.saveAndFlush(adjudicationNotes);

        int databaseSizeBeforeUpdate = adjudicationNotesRepository.findAll().size();

        // Update the adjudicationNotes using partial update
        AdjudicationNotes partialUpdatedAdjudicationNotes = new AdjudicationNotes();
        partialUpdatedAdjudicationNotes.setId(adjudicationNotes.getId());

        partialUpdatedAdjudicationNotes.note(UPDATED_NOTE);

        restAdjudicationNotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudicationNotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudicationNotes))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationNotes in the database
        List<AdjudicationNotes> adjudicationNotesList = adjudicationNotesRepository.findAll();
        assertThat(adjudicationNotesList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationNotes testAdjudicationNotes = adjudicationNotesList.get(adjudicationNotesList.size() - 1);
        assertThat(testAdjudicationNotes.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingAdjudicationNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationNotesRepository.findAll().size();
        adjudicationNotes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationNotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adjudicationNotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationNotes in the database
        List<AdjudicationNotes> adjudicationNotesList = adjudicationNotesRepository.findAll();
        assertThat(adjudicationNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdjudicationNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationNotesRepository.findAll().size();
        adjudicationNotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationNotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationNotes in the database
        List<AdjudicationNotes> adjudicationNotesList = adjudicationNotesRepository.findAll();
        assertThat(adjudicationNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdjudicationNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationNotesRepository.findAll().size();
        adjudicationNotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationNotesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationNotes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdjudicationNotes in the database
        List<AdjudicationNotes> adjudicationNotesList = adjudicationNotesRepository.findAll();
        assertThat(adjudicationNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdjudicationNotes() throws Exception {
        // Initialize the database
        adjudicationNotesRepository.saveAndFlush(adjudicationNotes);

        int databaseSizeBeforeDelete = adjudicationNotesRepository.findAll().size();

        // Delete the adjudicationNotes
        restAdjudicationNotesMockMvc
            .perform(delete(ENTITY_API_URL_ID, adjudicationNotes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdjudicationNotes> adjudicationNotesList = adjudicationNotesRepository.findAll();
        assertThat(adjudicationNotesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
