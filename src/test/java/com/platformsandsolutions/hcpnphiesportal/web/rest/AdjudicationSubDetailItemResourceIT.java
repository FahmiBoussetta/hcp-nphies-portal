package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationSubDetailItem;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationSubDetailItemRepository;
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
 * Integration tests for the {@link AdjudicationSubDetailItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdjudicationSubDetailItemResourceIT {

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final String ENTITY_API_URL = "/api/adjudication-sub-detail-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdjudicationSubDetailItemRepository adjudicationSubDetailItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdjudicationSubDetailItemMockMvc;

    private AdjudicationSubDetailItem adjudicationSubDetailItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjudicationSubDetailItem createEntity(EntityManager em) {
        AdjudicationSubDetailItem adjudicationSubDetailItem = new AdjudicationSubDetailItem().sequence(DEFAULT_SEQUENCE);
        return adjudicationSubDetailItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjudicationSubDetailItem createUpdatedEntity(EntityManager em) {
        AdjudicationSubDetailItem adjudicationSubDetailItem = new AdjudicationSubDetailItem().sequence(UPDATED_SEQUENCE);
        return adjudicationSubDetailItem;
    }

    @BeforeEach
    public void initTest() {
        adjudicationSubDetailItem = createEntity(em);
    }

    @Test
    @Transactional
    void createAdjudicationSubDetailItem() throws Exception {
        int databaseSizeBeforeCreate = adjudicationSubDetailItemRepository.findAll().size();
        // Create the AdjudicationSubDetailItem
        restAdjudicationSubDetailItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailItem))
            )
            .andExpect(status().isCreated());

        // Validate the AdjudicationSubDetailItem in the database
        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeCreate + 1);
        AdjudicationSubDetailItem testAdjudicationSubDetailItem = adjudicationSubDetailItemList.get(
            adjudicationSubDetailItemList.size() - 1
        );
        assertThat(testAdjudicationSubDetailItem.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
    }

    @Test
    @Transactional
    void createAdjudicationSubDetailItemWithExistingId() throws Exception {
        // Create the AdjudicationSubDetailItem with an existing ID
        adjudicationSubDetailItem.setId(1L);

        int databaseSizeBeforeCreate = adjudicationSubDetailItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjudicationSubDetailItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationSubDetailItem in the database
        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = adjudicationSubDetailItemRepository.findAll().size();
        // set the field null
        adjudicationSubDetailItem.setSequence(null);

        // Create the AdjudicationSubDetailItem, which fails.

        restAdjudicationSubDetailItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailItem))
            )
            .andExpect(status().isBadRequest());

        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdjudicationSubDetailItems() throws Exception {
        // Initialize the database
        adjudicationSubDetailItemRepository.saveAndFlush(adjudicationSubDetailItem);

        // Get all the adjudicationSubDetailItemList
        restAdjudicationSubDetailItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjudicationSubDetailItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)));
    }

    @Test
    @Transactional
    void getAdjudicationSubDetailItem() throws Exception {
        // Initialize the database
        adjudicationSubDetailItemRepository.saveAndFlush(adjudicationSubDetailItem);

        // Get the adjudicationSubDetailItem
        restAdjudicationSubDetailItemMockMvc
            .perform(get(ENTITY_API_URL_ID, adjudicationSubDetailItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adjudicationSubDetailItem.getId().intValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE));
    }

    @Test
    @Transactional
    void getNonExistingAdjudicationSubDetailItem() throws Exception {
        // Get the adjudicationSubDetailItem
        restAdjudicationSubDetailItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdjudicationSubDetailItem() throws Exception {
        // Initialize the database
        adjudicationSubDetailItemRepository.saveAndFlush(adjudicationSubDetailItem);

        int databaseSizeBeforeUpdate = adjudicationSubDetailItemRepository.findAll().size();

        // Update the adjudicationSubDetailItem
        AdjudicationSubDetailItem updatedAdjudicationSubDetailItem = adjudicationSubDetailItemRepository
            .findById(adjudicationSubDetailItem.getId())
            .get();
        // Disconnect from session so that the updates on updatedAdjudicationSubDetailItem are not directly saved in db
        em.detach(updatedAdjudicationSubDetailItem);
        updatedAdjudicationSubDetailItem.sequence(UPDATED_SEQUENCE);

        restAdjudicationSubDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdjudicationSubDetailItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdjudicationSubDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationSubDetailItem in the database
        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationSubDetailItem testAdjudicationSubDetailItem = adjudicationSubDetailItemList.get(
            adjudicationSubDetailItemList.size() - 1
        );
        assertThat(testAdjudicationSubDetailItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    void putNonExistingAdjudicationSubDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationSubDetailItemRepository.findAll().size();
        adjudicationSubDetailItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationSubDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adjudicationSubDetailItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationSubDetailItem in the database
        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdjudicationSubDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationSubDetailItemRepository.findAll().size();
        adjudicationSubDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationSubDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationSubDetailItem in the database
        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdjudicationSubDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationSubDetailItemRepository.findAll().size();
        adjudicationSubDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationSubDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdjudicationSubDetailItem in the database
        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdjudicationSubDetailItemWithPatch() throws Exception {
        // Initialize the database
        adjudicationSubDetailItemRepository.saveAndFlush(adjudicationSubDetailItem);

        int databaseSizeBeforeUpdate = adjudicationSubDetailItemRepository.findAll().size();

        // Update the adjudicationSubDetailItem using partial update
        AdjudicationSubDetailItem partialUpdatedAdjudicationSubDetailItem = new AdjudicationSubDetailItem();
        partialUpdatedAdjudicationSubDetailItem.setId(adjudicationSubDetailItem.getId());

        partialUpdatedAdjudicationSubDetailItem.sequence(UPDATED_SEQUENCE);

        restAdjudicationSubDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudicationSubDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudicationSubDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationSubDetailItem in the database
        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationSubDetailItem testAdjudicationSubDetailItem = adjudicationSubDetailItemList.get(
            adjudicationSubDetailItemList.size() - 1
        );
        assertThat(testAdjudicationSubDetailItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    void fullUpdateAdjudicationSubDetailItemWithPatch() throws Exception {
        // Initialize the database
        adjudicationSubDetailItemRepository.saveAndFlush(adjudicationSubDetailItem);

        int databaseSizeBeforeUpdate = adjudicationSubDetailItemRepository.findAll().size();

        // Update the adjudicationSubDetailItem using partial update
        AdjudicationSubDetailItem partialUpdatedAdjudicationSubDetailItem = new AdjudicationSubDetailItem();
        partialUpdatedAdjudicationSubDetailItem.setId(adjudicationSubDetailItem.getId());

        partialUpdatedAdjudicationSubDetailItem.sequence(UPDATED_SEQUENCE);

        restAdjudicationSubDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudicationSubDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudicationSubDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationSubDetailItem in the database
        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationSubDetailItem testAdjudicationSubDetailItem = adjudicationSubDetailItemList.get(
            adjudicationSubDetailItemList.size() - 1
        );
        assertThat(testAdjudicationSubDetailItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    void patchNonExistingAdjudicationSubDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationSubDetailItemRepository.findAll().size();
        adjudicationSubDetailItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationSubDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adjudicationSubDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationSubDetailItem in the database
        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdjudicationSubDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationSubDetailItemRepository.findAll().size();
        adjudicationSubDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationSubDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationSubDetailItem in the database
        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdjudicationSubDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationSubDetailItemRepository.findAll().size();
        adjudicationSubDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationSubDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationSubDetailItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdjudicationSubDetailItem in the database
        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdjudicationSubDetailItem() throws Exception {
        // Initialize the database
        adjudicationSubDetailItemRepository.saveAndFlush(adjudicationSubDetailItem);

        int databaseSizeBeforeDelete = adjudicationSubDetailItemRepository.findAll().size();

        // Delete the adjudicationSubDetailItem
        restAdjudicationSubDetailItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, adjudicationSubDetailItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdjudicationSubDetailItem> adjudicationSubDetailItemList = adjudicationSubDetailItemRepository.findAll();
        assertThat(adjudicationSubDetailItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
