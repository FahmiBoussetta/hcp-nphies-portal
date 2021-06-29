package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationDetailItem;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationDetailItemRepository;
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
 * Integration tests for the {@link AdjudicationDetailItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdjudicationDetailItemResourceIT {

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final String ENTITY_API_URL = "/api/adjudication-detail-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdjudicationDetailItemRepository adjudicationDetailItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdjudicationDetailItemMockMvc;

    private AdjudicationDetailItem adjudicationDetailItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjudicationDetailItem createEntity(EntityManager em) {
        AdjudicationDetailItem adjudicationDetailItem = new AdjudicationDetailItem().sequence(DEFAULT_SEQUENCE);
        return adjudicationDetailItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjudicationDetailItem createUpdatedEntity(EntityManager em) {
        AdjudicationDetailItem adjudicationDetailItem = new AdjudicationDetailItem().sequence(UPDATED_SEQUENCE);
        return adjudicationDetailItem;
    }

    @BeforeEach
    public void initTest() {
        adjudicationDetailItem = createEntity(em);
    }

    @Test
    @Transactional
    void createAdjudicationDetailItem() throws Exception {
        int databaseSizeBeforeCreate = adjudicationDetailItemRepository.findAll().size();
        // Create the AdjudicationDetailItem
        restAdjudicationDetailItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailItem))
            )
            .andExpect(status().isCreated());

        // Validate the AdjudicationDetailItem in the database
        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeCreate + 1);
        AdjudicationDetailItem testAdjudicationDetailItem = adjudicationDetailItemList.get(adjudicationDetailItemList.size() - 1);
        assertThat(testAdjudicationDetailItem.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
    }

    @Test
    @Transactional
    void createAdjudicationDetailItemWithExistingId() throws Exception {
        // Create the AdjudicationDetailItem with an existing ID
        adjudicationDetailItem.setId(1L);

        int databaseSizeBeforeCreate = adjudicationDetailItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjudicationDetailItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationDetailItem in the database
        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = adjudicationDetailItemRepository.findAll().size();
        // set the field null
        adjudicationDetailItem.setSequence(null);

        // Create the AdjudicationDetailItem, which fails.

        restAdjudicationDetailItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailItem))
            )
            .andExpect(status().isBadRequest());

        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdjudicationDetailItems() throws Exception {
        // Initialize the database
        adjudicationDetailItemRepository.saveAndFlush(adjudicationDetailItem);

        // Get all the adjudicationDetailItemList
        restAdjudicationDetailItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjudicationDetailItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)));
    }

    @Test
    @Transactional
    void getAdjudicationDetailItem() throws Exception {
        // Initialize the database
        adjudicationDetailItemRepository.saveAndFlush(adjudicationDetailItem);

        // Get the adjudicationDetailItem
        restAdjudicationDetailItemMockMvc
            .perform(get(ENTITY_API_URL_ID, adjudicationDetailItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adjudicationDetailItem.getId().intValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE));
    }

    @Test
    @Transactional
    void getNonExistingAdjudicationDetailItem() throws Exception {
        // Get the adjudicationDetailItem
        restAdjudicationDetailItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdjudicationDetailItem() throws Exception {
        // Initialize the database
        adjudicationDetailItemRepository.saveAndFlush(adjudicationDetailItem);

        int databaseSizeBeforeUpdate = adjudicationDetailItemRepository.findAll().size();

        // Update the adjudicationDetailItem
        AdjudicationDetailItem updatedAdjudicationDetailItem = adjudicationDetailItemRepository
            .findById(adjudicationDetailItem.getId())
            .get();
        // Disconnect from session so that the updates on updatedAdjudicationDetailItem are not directly saved in db
        em.detach(updatedAdjudicationDetailItem);
        updatedAdjudicationDetailItem.sequence(UPDATED_SEQUENCE);

        restAdjudicationDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdjudicationDetailItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdjudicationDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationDetailItem in the database
        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationDetailItem testAdjudicationDetailItem = adjudicationDetailItemList.get(adjudicationDetailItemList.size() - 1);
        assertThat(testAdjudicationDetailItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    void putNonExistingAdjudicationDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationDetailItemRepository.findAll().size();
        adjudicationDetailItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adjudicationDetailItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationDetailItem in the database
        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdjudicationDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationDetailItemRepository.findAll().size();
        adjudicationDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationDetailItem in the database
        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdjudicationDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationDetailItemRepository.findAll().size();
        adjudicationDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdjudicationDetailItem in the database
        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdjudicationDetailItemWithPatch() throws Exception {
        // Initialize the database
        adjudicationDetailItemRepository.saveAndFlush(adjudicationDetailItem);

        int databaseSizeBeforeUpdate = adjudicationDetailItemRepository.findAll().size();

        // Update the adjudicationDetailItem using partial update
        AdjudicationDetailItem partialUpdatedAdjudicationDetailItem = new AdjudicationDetailItem();
        partialUpdatedAdjudicationDetailItem.setId(adjudicationDetailItem.getId());

        restAdjudicationDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudicationDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudicationDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationDetailItem in the database
        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationDetailItem testAdjudicationDetailItem = adjudicationDetailItemList.get(adjudicationDetailItemList.size() - 1);
        assertThat(testAdjudicationDetailItem.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
    }

    @Test
    @Transactional
    void fullUpdateAdjudicationDetailItemWithPatch() throws Exception {
        // Initialize the database
        adjudicationDetailItemRepository.saveAndFlush(adjudicationDetailItem);

        int databaseSizeBeforeUpdate = adjudicationDetailItemRepository.findAll().size();

        // Update the adjudicationDetailItem using partial update
        AdjudicationDetailItem partialUpdatedAdjudicationDetailItem = new AdjudicationDetailItem();
        partialUpdatedAdjudicationDetailItem.setId(adjudicationDetailItem.getId());

        partialUpdatedAdjudicationDetailItem.sequence(UPDATED_SEQUENCE);

        restAdjudicationDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudicationDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudicationDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationDetailItem in the database
        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationDetailItem testAdjudicationDetailItem = adjudicationDetailItemList.get(adjudicationDetailItemList.size() - 1);
        assertThat(testAdjudicationDetailItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    void patchNonExistingAdjudicationDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationDetailItemRepository.findAll().size();
        adjudicationDetailItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adjudicationDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationDetailItem in the database
        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdjudicationDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationDetailItemRepository.findAll().size();
        adjudicationDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationDetailItem in the database
        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdjudicationDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationDetailItemRepository.findAll().size();
        adjudicationDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationDetailItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdjudicationDetailItem in the database
        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdjudicationDetailItem() throws Exception {
        // Initialize the database
        adjudicationDetailItemRepository.saveAndFlush(adjudicationDetailItem);

        int databaseSizeBeforeDelete = adjudicationDetailItemRepository.findAll().size();

        // Delete the adjudicationDetailItem
        restAdjudicationDetailItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, adjudicationDetailItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdjudicationDetailItem> adjudicationDetailItemList = adjudicationDetailItemRepository.findAll();
        assertThat(adjudicationDetailItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
