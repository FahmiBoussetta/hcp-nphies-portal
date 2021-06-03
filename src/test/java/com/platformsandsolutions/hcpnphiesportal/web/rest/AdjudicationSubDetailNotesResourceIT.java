package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationSubDetailNotes;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationSubDetailNotesRepository;
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
 * Integration tests for the {@link AdjudicationSubDetailNotesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdjudicationSubDetailNotesResourceIT {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/adjudication-sub-detail-notes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdjudicationSubDetailNotesRepository adjudicationSubDetailNotesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdjudicationSubDetailNotesMockMvc;

    private AdjudicationSubDetailNotes adjudicationSubDetailNotes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjudicationSubDetailNotes createEntity(EntityManager em) {
        AdjudicationSubDetailNotes adjudicationSubDetailNotes = new AdjudicationSubDetailNotes().note(DEFAULT_NOTE);
        return adjudicationSubDetailNotes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjudicationSubDetailNotes createUpdatedEntity(EntityManager em) {
        AdjudicationSubDetailNotes adjudicationSubDetailNotes = new AdjudicationSubDetailNotes().note(UPDATED_NOTE);
        return adjudicationSubDetailNotes;
    }

    @BeforeEach
    public void initTest() {
        adjudicationSubDetailNotes = createEntity(em);
    }

    @Test
    @Transactional
    void createAdjudicationSubDetailNotes() throws Exception {
        int databaseSizeBeforeCreate = adjudicationSubDetailNotesRepository.findAll().size();
        // Create the AdjudicationSubDetailNotes
        restAdjudicationSubDetailNotesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailNotes))
            )
            .andExpect(status().isCreated());

        // Validate the AdjudicationSubDetailNotes in the database
        List<AdjudicationSubDetailNotes> adjudicationSubDetailNotesList = adjudicationSubDetailNotesRepository.findAll();
        assertThat(adjudicationSubDetailNotesList).hasSize(databaseSizeBeforeCreate + 1);
        AdjudicationSubDetailNotes testAdjudicationSubDetailNotes = adjudicationSubDetailNotesList.get(
            adjudicationSubDetailNotesList.size() - 1
        );
        assertThat(testAdjudicationSubDetailNotes.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void createAdjudicationSubDetailNotesWithExistingId() throws Exception {
        // Create the AdjudicationSubDetailNotes with an existing ID
        adjudicationSubDetailNotes.setId(1L);

        int databaseSizeBeforeCreate = adjudicationSubDetailNotesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjudicationSubDetailNotesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationSubDetailNotes in the database
        List<AdjudicationSubDetailNotes> adjudicationSubDetailNotesList = adjudicationSubDetailNotesRepository.findAll();
        assertThat(adjudicationSubDetailNotesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdjudicationSubDetailNotes() throws Exception {
        // Initialize the database
        adjudicationSubDetailNotesRepository.saveAndFlush(adjudicationSubDetailNotes);

        // Get all the adjudicationSubDetailNotesList
        restAdjudicationSubDetailNotesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjudicationSubDetailNotes.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    @Transactional
    void getAdjudicationSubDetailNotes() throws Exception {
        // Initialize the database
        adjudicationSubDetailNotesRepository.saveAndFlush(adjudicationSubDetailNotes);

        // Get the adjudicationSubDetailNotes
        restAdjudicationSubDetailNotesMockMvc
            .perform(get(ENTITY_API_URL_ID, adjudicationSubDetailNotes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adjudicationSubDetailNotes.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    void getNonExistingAdjudicationSubDetailNotes() throws Exception {
        // Get the adjudicationSubDetailNotes
        restAdjudicationSubDetailNotesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdjudicationSubDetailNotes() throws Exception {
        // Initialize the database
        adjudicationSubDetailNotesRepository.saveAndFlush(adjudicationSubDetailNotes);

        int databaseSizeBeforeUpdate = adjudicationSubDetailNotesRepository.findAll().size();

        // Update the adjudicationSubDetailNotes
        AdjudicationSubDetailNotes updatedAdjudicationSubDetailNotes = adjudicationSubDetailNotesRepository
            .findById(adjudicationSubDetailNotes.getId())
            .get();
        // Disconnect from session so that the updates on updatedAdjudicationSubDetailNotes are not directly saved in db
        em.detach(updatedAdjudicationSubDetailNotes);
        updatedAdjudicationSubDetailNotes.note(UPDATED_NOTE);

        restAdjudicationSubDetailNotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdjudicationSubDetailNotes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdjudicationSubDetailNotes))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationSubDetailNotes in the database
        List<AdjudicationSubDetailNotes> adjudicationSubDetailNotesList = adjudicationSubDetailNotesRepository.findAll();
        assertThat(adjudicationSubDetailNotesList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationSubDetailNotes testAdjudicationSubDetailNotes = adjudicationSubDetailNotesList.get(
            adjudicationSubDetailNotesList.size() - 1
        );
        assertThat(testAdjudicationSubDetailNotes.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingAdjudicationSubDetailNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationSubDetailNotesRepository.findAll().size();
        adjudicationSubDetailNotes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationSubDetailNotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adjudicationSubDetailNotes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationSubDetailNotes in the database
        List<AdjudicationSubDetailNotes> adjudicationSubDetailNotesList = adjudicationSubDetailNotesRepository.findAll();
        assertThat(adjudicationSubDetailNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdjudicationSubDetailNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationSubDetailNotesRepository.findAll().size();
        adjudicationSubDetailNotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationSubDetailNotesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationSubDetailNotes in the database
        List<AdjudicationSubDetailNotes> adjudicationSubDetailNotesList = adjudicationSubDetailNotesRepository.findAll();
        assertThat(adjudicationSubDetailNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdjudicationSubDetailNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationSubDetailNotesRepository.findAll().size();
        adjudicationSubDetailNotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationSubDetailNotesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailNotes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdjudicationSubDetailNotes in the database
        List<AdjudicationSubDetailNotes> adjudicationSubDetailNotesList = adjudicationSubDetailNotesRepository.findAll();
        assertThat(adjudicationSubDetailNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdjudicationSubDetailNotesWithPatch() throws Exception {
        // Initialize the database
        adjudicationSubDetailNotesRepository.saveAndFlush(adjudicationSubDetailNotes);

        int databaseSizeBeforeUpdate = adjudicationSubDetailNotesRepository.findAll().size();

        // Update the adjudicationSubDetailNotes using partial update
        AdjudicationSubDetailNotes partialUpdatedAdjudicationSubDetailNotes = new AdjudicationSubDetailNotes();
        partialUpdatedAdjudicationSubDetailNotes.setId(adjudicationSubDetailNotes.getId());

        restAdjudicationSubDetailNotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudicationSubDetailNotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudicationSubDetailNotes))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationSubDetailNotes in the database
        List<AdjudicationSubDetailNotes> adjudicationSubDetailNotesList = adjudicationSubDetailNotesRepository.findAll();
        assertThat(adjudicationSubDetailNotesList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationSubDetailNotes testAdjudicationSubDetailNotes = adjudicationSubDetailNotesList.get(
            adjudicationSubDetailNotesList.size() - 1
        );
        assertThat(testAdjudicationSubDetailNotes.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateAdjudicationSubDetailNotesWithPatch() throws Exception {
        // Initialize the database
        adjudicationSubDetailNotesRepository.saveAndFlush(adjudicationSubDetailNotes);

        int databaseSizeBeforeUpdate = adjudicationSubDetailNotesRepository.findAll().size();

        // Update the adjudicationSubDetailNotes using partial update
        AdjudicationSubDetailNotes partialUpdatedAdjudicationSubDetailNotes = new AdjudicationSubDetailNotes();
        partialUpdatedAdjudicationSubDetailNotes.setId(adjudicationSubDetailNotes.getId());

        partialUpdatedAdjudicationSubDetailNotes.note(UPDATED_NOTE);

        restAdjudicationSubDetailNotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudicationSubDetailNotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudicationSubDetailNotes))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationSubDetailNotes in the database
        List<AdjudicationSubDetailNotes> adjudicationSubDetailNotesList = adjudicationSubDetailNotesRepository.findAll();
        assertThat(adjudicationSubDetailNotesList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationSubDetailNotes testAdjudicationSubDetailNotes = adjudicationSubDetailNotesList.get(
            adjudicationSubDetailNotesList.size() - 1
        );
        assertThat(testAdjudicationSubDetailNotes.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingAdjudicationSubDetailNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationSubDetailNotesRepository.findAll().size();
        adjudicationSubDetailNotes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationSubDetailNotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adjudicationSubDetailNotes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationSubDetailNotes in the database
        List<AdjudicationSubDetailNotes> adjudicationSubDetailNotesList = adjudicationSubDetailNotesRepository.findAll();
        assertThat(adjudicationSubDetailNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdjudicationSubDetailNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationSubDetailNotesRepository.findAll().size();
        adjudicationSubDetailNotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationSubDetailNotesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailNotes))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationSubDetailNotes in the database
        List<AdjudicationSubDetailNotes> adjudicationSubDetailNotesList = adjudicationSubDetailNotesRepository.findAll();
        assertThat(adjudicationSubDetailNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdjudicationSubDetailNotes() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationSubDetailNotesRepository.findAll().size();
        adjudicationSubDetailNotes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationSubDetailNotesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailNotes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdjudicationSubDetailNotes in the database
        List<AdjudicationSubDetailNotes> adjudicationSubDetailNotesList = adjudicationSubDetailNotesRepository.findAll();
        assertThat(adjudicationSubDetailNotesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdjudicationSubDetailNotes() throws Exception {
        // Initialize the database
        adjudicationSubDetailNotesRepository.saveAndFlush(adjudicationSubDetailNotes);

        int databaseSizeBeforeDelete = adjudicationSubDetailNotesRepository.findAll().size();

        // Delete the adjudicationSubDetailNotes
        restAdjudicationSubDetailNotesMockMvc
            .perform(delete(ENTITY_API_URL_ID, adjudicationSubDetailNotes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdjudicationSubDetailNotes> adjudicationSubDetailNotesList = adjudicationSubDetailNotesRepository.findAll();
        assertThat(adjudicationSubDetailNotesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
