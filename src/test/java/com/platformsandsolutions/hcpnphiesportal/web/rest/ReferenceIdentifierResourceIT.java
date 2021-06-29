package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ReferenceIdentifier;
import com.platformsandsolutions.hcpnphiesportal.repository.ReferenceIdentifierRepository;
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
 * Integration tests for the {@link ReferenceIdentifierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReferenceIdentifierResourceIT {

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final String DEFAULT_ID_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_ID_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/reference-identifiers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReferenceIdentifierRepository referenceIdentifierRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReferenceIdentifierMockMvc;

    private ReferenceIdentifier referenceIdentifier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferenceIdentifier createEntity(EntityManager em) {
        ReferenceIdentifier referenceIdentifier = new ReferenceIdentifier()
            .ref(DEFAULT_REF)
            .idValue(DEFAULT_ID_VALUE)
            .identifier(DEFAULT_IDENTIFIER)
            .display(DEFAULT_DISPLAY);
        return referenceIdentifier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferenceIdentifier createUpdatedEntity(EntityManager em) {
        ReferenceIdentifier referenceIdentifier = new ReferenceIdentifier()
            .ref(UPDATED_REF)
            .idValue(UPDATED_ID_VALUE)
            .identifier(UPDATED_IDENTIFIER)
            .display(UPDATED_DISPLAY);
        return referenceIdentifier;
    }

    @BeforeEach
    public void initTest() {
        referenceIdentifier = createEntity(em);
    }

    @Test
    @Transactional
    void createReferenceIdentifier() throws Exception {
        int databaseSizeBeforeCreate = referenceIdentifierRepository.findAll().size();
        // Create the ReferenceIdentifier
        restReferenceIdentifierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(referenceIdentifier))
            )
            .andExpect(status().isCreated());

        // Validate the ReferenceIdentifier in the database
        List<ReferenceIdentifier> referenceIdentifierList = referenceIdentifierRepository.findAll();
        assertThat(referenceIdentifierList).hasSize(databaseSizeBeforeCreate + 1);
        ReferenceIdentifier testReferenceIdentifier = referenceIdentifierList.get(referenceIdentifierList.size() - 1);
        assertThat(testReferenceIdentifier.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testReferenceIdentifier.getIdValue()).isEqualTo(DEFAULT_ID_VALUE);
        assertThat(testReferenceIdentifier.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testReferenceIdentifier.getDisplay()).isEqualTo(DEFAULT_DISPLAY);
    }

    @Test
    @Transactional
    void createReferenceIdentifierWithExistingId() throws Exception {
        // Create the ReferenceIdentifier with an existing ID
        referenceIdentifier.setId(1L);

        int databaseSizeBeforeCreate = referenceIdentifierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferenceIdentifierMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(referenceIdentifier))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferenceIdentifier in the database
        List<ReferenceIdentifier> referenceIdentifierList = referenceIdentifierRepository.findAll();
        assertThat(referenceIdentifierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReferenceIdentifiers() throws Exception {
        // Initialize the database
        referenceIdentifierRepository.saveAndFlush(referenceIdentifier);

        // Get all the referenceIdentifierList
        restReferenceIdentifierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referenceIdentifier.getId().intValue())))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].idValue").value(hasItem(DEFAULT_ID_VALUE)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY)));
    }

    @Test
    @Transactional
    void getReferenceIdentifier() throws Exception {
        // Initialize the database
        referenceIdentifierRepository.saveAndFlush(referenceIdentifier);

        // Get the referenceIdentifier
        restReferenceIdentifierMockMvc
            .perform(get(ENTITY_API_URL_ID, referenceIdentifier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(referenceIdentifier.getId().intValue()))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF))
            .andExpect(jsonPath("$.idValue").value(DEFAULT_ID_VALUE))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.display").value(DEFAULT_DISPLAY));
    }

    @Test
    @Transactional
    void getNonExistingReferenceIdentifier() throws Exception {
        // Get the referenceIdentifier
        restReferenceIdentifierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReferenceIdentifier() throws Exception {
        // Initialize the database
        referenceIdentifierRepository.saveAndFlush(referenceIdentifier);

        int databaseSizeBeforeUpdate = referenceIdentifierRepository.findAll().size();

        // Update the referenceIdentifier
        ReferenceIdentifier updatedReferenceIdentifier = referenceIdentifierRepository.findById(referenceIdentifier.getId()).get();
        // Disconnect from session so that the updates on updatedReferenceIdentifier are not directly saved in db
        em.detach(updatedReferenceIdentifier);
        updatedReferenceIdentifier.ref(UPDATED_REF).idValue(UPDATED_ID_VALUE).identifier(UPDATED_IDENTIFIER).display(UPDATED_DISPLAY);

        restReferenceIdentifierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReferenceIdentifier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReferenceIdentifier))
            )
            .andExpect(status().isOk());

        // Validate the ReferenceIdentifier in the database
        List<ReferenceIdentifier> referenceIdentifierList = referenceIdentifierRepository.findAll();
        assertThat(referenceIdentifierList).hasSize(databaseSizeBeforeUpdate);
        ReferenceIdentifier testReferenceIdentifier = referenceIdentifierList.get(referenceIdentifierList.size() - 1);
        assertThat(testReferenceIdentifier.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testReferenceIdentifier.getIdValue()).isEqualTo(UPDATED_ID_VALUE);
        assertThat(testReferenceIdentifier.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testReferenceIdentifier.getDisplay()).isEqualTo(UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    void putNonExistingReferenceIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = referenceIdentifierRepository.findAll().size();
        referenceIdentifier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferenceIdentifierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, referenceIdentifier.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(referenceIdentifier))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferenceIdentifier in the database
        List<ReferenceIdentifier> referenceIdentifierList = referenceIdentifierRepository.findAll();
        assertThat(referenceIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReferenceIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = referenceIdentifierRepository.findAll().size();
        referenceIdentifier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReferenceIdentifierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(referenceIdentifier))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferenceIdentifier in the database
        List<ReferenceIdentifier> referenceIdentifierList = referenceIdentifierRepository.findAll();
        assertThat(referenceIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReferenceIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = referenceIdentifierRepository.findAll().size();
        referenceIdentifier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReferenceIdentifierMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(referenceIdentifier))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReferenceIdentifier in the database
        List<ReferenceIdentifier> referenceIdentifierList = referenceIdentifierRepository.findAll();
        assertThat(referenceIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReferenceIdentifierWithPatch() throws Exception {
        // Initialize the database
        referenceIdentifierRepository.saveAndFlush(referenceIdentifier);

        int databaseSizeBeforeUpdate = referenceIdentifierRepository.findAll().size();

        // Update the referenceIdentifier using partial update
        ReferenceIdentifier partialUpdatedReferenceIdentifier = new ReferenceIdentifier();
        partialUpdatedReferenceIdentifier.setId(referenceIdentifier.getId());

        partialUpdatedReferenceIdentifier.ref(UPDATED_REF).display(UPDATED_DISPLAY);

        restReferenceIdentifierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReferenceIdentifier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReferenceIdentifier))
            )
            .andExpect(status().isOk());

        // Validate the ReferenceIdentifier in the database
        List<ReferenceIdentifier> referenceIdentifierList = referenceIdentifierRepository.findAll();
        assertThat(referenceIdentifierList).hasSize(databaseSizeBeforeUpdate);
        ReferenceIdentifier testReferenceIdentifier = referenceIdentifierList.get(referenceIdentifierList.size() - 1);
        assertThat(testReferenceIdentifier.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testReferenceIdentifier.getIdValue()).isEqualTo(DEFAULT_ID_VALUE);
        assertThat(testReferenceIdentifier.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testReferenceIdentifier.getDisplay()).isEqualTo(UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    void fullUpdateReferenceIdentifierWithPatch() throws Exception {
        // Initialize the database
        referenceIdentifierRepository.saveAndFlush(referenceIdentifier);

        int databaseSizeBeforeUpdate = referenceIdentifierRepository.findAll().size();

        // Update the referenceIdentifier using partial update
        ReferenceIdentifier partialUpdatedReferenceIdentifier = new ReferenceIdentifier();
        partialUpdatedReferenceIdentifier.setId(referenceIdentifier.getId());

        partialUpdatedReferenceIdentifier
            .ref(UPDATED_REF)
            .idValue(UPDATED_ID_VALUE)
            .identifier(UPDATED_IDENTIFIER)
            .display(UPDATED_DISPLAY);

        restReferenceIdentifierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReferenceIdentifier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReferenceIdentifier))
            )
            .andExpect(status().isOk());

        // Validate the ReferenceIdentifier in the database
        List<ReferenceIdentifier> referenceIdentifierList = referenceIdentifierRepository.findAll();
        assertThat(referenceIdentifierList).hasSize(databaseSizeBeforeUpdate);
        ReferenceIdentifier testReferenceIdentifier = referenceIdentifierList.get(referenceIdentifierList.size() - 1);
        assertThat(testReferenceIdentifier.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testReferenceIdentifier.getIdValue()).isEqualTo(UPDATED_ID_VALUE);
        assertThat(testReferenceIdentifier.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testReferenceIdentifier.getDisplay()).isEqualTo(UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    void patchNonExistingReferenceIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = referenceIdentifierRepository.findAll().size();
        referenceIdentifier.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferenceIdentifierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, referenceIdentifier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(referenceIdentifier))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferenceIdentifier in the database
        List<ReferenceIdentifier> referenceIdentifierList = referenceIdentifierRepository.findAll();
        assertThat(referenceIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReferenceIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = referenceIdentifierRepository.findAll().size();
        referenceIdentifier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReferenceIdentifierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(referenceIdentifier))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReferenceIdentifier in the database
        List<ReferenceIdentifier> referenceIdentifierList = referenceIdentifierRepository.findAll();
        assertThat(referenceIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReferenceIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = referenceIdentifierRepository.findAll().size();
        referenceIdentifier.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReferenceIdentifierMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(referenceIdentifier))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReferenceIdentifier in the database
        List<ReferenceIdentifier> referenceIdentifierList = referenceIdentifierRepository.findAll();
        assertThat(referenceIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReferenceIdentifier() throws Exception {
        // Initialize the database
        referenceIdentifierRepository.saveAndFlush(referenceIdentifier);

        int databaseSizeBeforeDelete = referenceIdentifierRepository.findAll().size();

        // Delete the referenceIdentifier
        restReferenceIdentifierMockMvc
            .perform(delete(ENTITY_API_URL_ID, referenceIdentifier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReferenceIdentifier> referenceIdentifierList = referenceIdentifierRepository.findAll();
        assertThat(referenceIdentifierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
