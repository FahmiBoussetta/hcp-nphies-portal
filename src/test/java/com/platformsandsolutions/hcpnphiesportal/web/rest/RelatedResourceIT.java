package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Related;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ClaimRelationshipEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.RelatedRepository;
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
 * Integration tests for the {@link RelatedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RelatedResourceIT {

    private static final ClaimRelationshipEnum DEFAULT_RELATION_SHIP = ClaimRelationshipEnum.Prior;
    private static final ClaimRelationshipEnum UPDATED_RELATION_SHIP = ClaimRelationshipEnum.Associated;

    private static final String ENTITY_API_URL = "/api/relateds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RelatedRepository relatedRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelatedMockMvc;

    private Related related;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Related createEntity(EntityManager em) {
        Related related = new Related().relationShip(DEFAULT_RELATION_SHIP);
        return related;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Related createUpdatedEntity(EntityManager em) {
        Related related = new Related().relationShip(UPDATED_RELATION_SHIP);
        return related;
    }

    @BeforeEach
    public void initTest() {
        related = createEntity(em);
    }

    @Test
    @Transactional
    void createRelated() throws Exception {
        int databaseSizeBeforeCreate = relatedRepository.findAll().size();
        // Create the Related
        restRelatedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(related)))
            .andExpect(status().isCreated());

        // Validate the Related in the database
        List<Related> relatedList = relatedRepository.findAll();
        assertThat(relatedList).hasSize(databaseSizeBeforeCreate + 1);
        Related testRelated = relatedList.get(relatedList.size() - 1);
        assertThat(testRelated.getRelationShip()).isEqualTo(DEFAULT_RELATION_SHIP);
    }

    @Test
    @Transactional
    void createRelatedWithExistingId() throws Exception {
        // Create the Related with an existing ID
        related.setId(1L);

        int databaseSizeBeforeCreate = relatedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelatedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(related)))
            .andExpect(status().isBadRequest());

        // Validate the Related in the database
        List<Related> relatedList = relatedRepository.findAll();
        assertThat(relatedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRelateds() throws Exception {
        // Initialize the database
        relatedRepository.saveAndFlush(related);

        // Get all the relatedList
        restRelatedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(related.getId().intValue())))
            .andExpect(jsonPath("$.[*].relationShip").value(hasItem(DEFAULT_RELATION_SHIP.toString())));
    }

    @Test
    @Transactional
    void getRelated() throws Exception {
        // Initialize the database
        relatedRepository.saveAndFlush(related);

        // Get the related
        restRelatedMockMvc
            .perform(get(ENTITY_API_URL_ID, related.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(related.getId().intValue()))
            .andExpect(jsonPath("$.relationShip").value(DEFAULT_RELATION_SHIP.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRelated() throws Exception {
        // Get the related
        restRelatedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRelated() throws Exception {
        // Initialize the database
        relatedRepository.saveAndFlush(related);

        int databaseSizeBeforeUpdate = relatedRepository.findAll().size();

        // Update the related
        Related updatedRelated = relatedRepository.findById(related.getId()).get();
        // Disconnect from session so that the updates on updatedRelated are not directly saved in db
        em.detach(updatedRelated);
        updatedRelated.relationShip(UPDATED_RELATION_SHIP);

        restRelatedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRelated.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRelated))
            )
            .andExpect(status().isOk());

        // Validate the Related in the database
        List<Related> relatedList = relatedRepository.findAll();
        assertThat(relatedList).hasSize(databaseSizeBeforeUpdate);
        Related testRelated = relatedList.get(relatedList.size() - 1);
        assertThat(testRelated.getRelationShip()).isEqualTo(UPDATED_RELATION_SHIP);
    }

    @Test
    @Transactional
    void putNonExistingRelated() throws Exception {
        int databaseSizeBeforeUpdate = relatedRepository.findAll().size();
        related.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, related.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(related))
            )
            .andExpect(status().isBadRequest());

        // Validate the Related in the database
        List<Related> relatedList = relatedRepository.findAll();
        assertThat(relatedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelated() throws Exception {
        int databaseSizeBeforeUpdate = relatedRepository.findAll().size();
        related.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(related))
            )
            .andExpect(status().isBadRequest());

        // Validate the Related in the database
        List<Related> relatedList = relatedRepository.findAll();
        assertThat(relatedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelated() throws Exception {
        int databaseSizeBeforeUpdate = relatedRepository.findAll().size();
        related.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(related)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Related in the database
        List<Related> relatedList = relatedRepository.findAll();
        assertThat(relatedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelatedWithPatch() throws Exception {
        // Initialize the database
        relatedRepository.saveAndFlush(related);

        int databaseSizeBeforeUpdate = relatedRepository.findAll().size();

        // Update the related using partial update
        Related partialUpdatedRelated = new Related();
        partialUpdatedRelated.setId(related.getId());

        restRelatedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelated.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelated))
            )
            .andExpect(status().isOk());

        // Validate the Related in the database
        List<Related> relatedList = relatedRepository.findAll();
        assertThat(relatedList).hasSize(databaseSizeBeforeUpdate);
        Related testRelated = relatedList.get(relatedList.size() - 1);
        assertThat(testRelated.getRelationShip()).isEqualTo(DEFAULT_RELATION_SHIP);
    }

    @Test
    @Transactional
    void fullUpdateRelatedWithPatch() throws Exception {
        // Initialize the database
        relatedRepository.saveAndFlush(related);

        int databaseSizeBeforeUpdate = relatedRepository.findAll().size();

        // Update the related using partial update
        Related partialUpdatedRelated = new Related();
        partialUpdatedRelated.setId(related.getId());

        partialUpdatedRelated.relationShip(UPDATED_RELATION_SHIP);

        restRelatedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelated.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelated))
            )
            .andExpect(status().isOk());

        // Validate the Related in the database
        List<Related> relatedList = relatedRepository.findAll();
        assertThat(relatedList).hasSize(databaseSizeBeforeUpdate);
        Related testRelated = relatedList.get(relatedList.size() - 1);
        assertThat(testRelated.getRelationShip()).isEqualTo(UPDATED_RELATION_SHIP);
    }

    @Test
    @Transactional
    void patchNonExistingRelated() throws Exception {
        int databaseSizeBeforeUpdate = relatedRepository.findAll().size();
        related.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, related.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(related))
            )
            .andExpect(status().isBadRequest());

        // Validate the Related in the database
        List<Related> relatedList = relatedRepository.findAll();
        assertThat(relatedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelated() throws Exception {
        int databaseSizeBeforeUpdate = relatedRepository.findAll().size();
        related.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(related))
            )
            .andExpect(status().isBadRequest());

        // Validate the Related in the database
        List<Related> relatedList = relatedRepository.findAll();
        assertThat(relatedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelated() throws Exception {
        int databaseSizeBeforeUpdate = relatedRepository.findAll().size();
        related.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(related)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Related in the database
        List<Related> relatedList = relatedRepository.findAll();
        assertThat(relatedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelated() throws Exception {
        // Initialize the database
        relatedRepository.saveAndFlush(related);

        int databaseSizeBeforeDelete = relatedRepository.findAll().size();

        // Delete the related
        restRelatedMockMvc
            .perform(delete(ENTITY_API_URL_ID, related.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Related> relatedList = relatedRepository.findAll();
        assertThat(relatedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
