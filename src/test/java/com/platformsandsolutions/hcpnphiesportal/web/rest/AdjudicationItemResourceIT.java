package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.AdjudicationItem;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationItemRepository;
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
 * Integration tests for the {@link AdjudicationItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdjudicationItemResourceIT {

    private static final String DEFAULT_OUTCOME = "AAAAAAAAAA";
    private static final String UPDATED_OUTCOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final String ENTITY_API_URL = "/api/adjudication-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdjudicationItemRepository adjudicationItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdjudicationItemMockMvc;

    private AdjudicationItem adjudicationItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjudicationItem createEntity(EntityManager em) {
        AdjudicationItem adjudicationItem = new AdjudicationItem().outcome(DEFAULT_OUTCOME).sequence(DEFAULT_SEQUENCE);
        return adjudicationItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjudicationItem createUpdatedEntity(EntityManager em) {
        AdjudicationItem adjudicationItem = new AdjudicationItem().outcome(UPDATED_OUTCOME).sequence(UPDATED_SEQUENCE);
        return adjudicationItem;
    }

    @BeforeEach
    public void initTest() {
        adjudicationItem = createEntity(em);
    }

    @Test
    @Transactional
    void createAdjudicationItem() throws Exception {
        int databaseSizeBeforeCreate = adjudicationItemRepository.findAll().size();
        // Create the AdjudicationItem
        restAdjudicationItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adjudicationItem))
            )
            .andExpect(status().isCreated());

        // Validate the AdjudicationItem in the database
        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeCreate + 1);
        AdjudicationItem testAdjudicationItem = adjudicationItemList.get(adjudicationItemList.size() - 1);
        assertThat(testAdjudicationItem.getOutcome()).isEqualTo(DEFAULT_OUTCOME);
        assertThat(testAdjudicationItem.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
    }

    @Test
    @Transactional
    void createAdjudicationItemWithExistingId() throws Exception {
        // Create the AdjudicationItem with an existing ID
        adjudicationItem.setId(1L);

        int databaseSizeBeforeCreate = adjudicationItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjudicationItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adjudicationItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationItem in the database
        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = adjudicationItemRepository.findAll().size();
        // set the field null
        adjudicationItem.setSequence(null);

        // Create the AdjudicationItem, which fails.

        restAdjudicationItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adjudicationItem))
            )
            .andExpect(status().isBadRequest());

        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdjudicationItems() throws Exception {
        // Initialize the database
        adjudicationItemRepository.saveAndFlush(adjudicationItem);

        // Get all the adjudicationItemList
        restAdjudicationItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjudicationItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].outcome").value(hasItem(DEFAULT_OUTCOME)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)));
    }

    @Test
    @Transactional
    void getAdjudicationItem() throws Exception {
        // Initialize the database
        adjudicationItemRepository.saveAndFlush(adjudicationItem);

        // Get the adjudicationItem
        restAdjudicationItemMockMvc
            .perform(get(ENTITY_API_URL_ID, adjudicationItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adjudicationItem.getId().intValue()))
            .andExpect(jsonPath("$.outcome").value(DEFAULT_OUTCOME))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE));
    }

    @Test
    @Transactional
    void getNonExistingAdjudicationItem() throws Exception {
        // Get the adjudicationItem
        restAdjudicationItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdjudicationItem() throws Exception {
        // Initialize the database
        adjudicationItemRepository.saveAndFlush(adjudicationItem);

        int databaseSizeBeforeUpdate = adjudicationItemRepository.findAll().size();

        // Update the adjudicationItem
        AdjudicationItem updatedAdjudicationItem = adjudicationItemRepository.findById(adjudicationItem.getId()).get();
        // Disconnect from session so that the updates on updatedAdjudicationItem are not directly saved in db
        em.detach(updatedAdjudicationItem);
        updatedAdjudicationItem.outcome(UPDATED_OUTCOME).sequence(UPDATED_SEQUENCE);

        restAdjudicationItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdjudicationItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdjudicationItem))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationItem in the database
        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationItem testAdjudicationItem = adjudicationItemList.get(adjudicationItemList.size() - 1);
        assertThat(testAdjudicationItem.getOutcome()).isEqualTo(UPDATED_OUTCOME);
        assertThat(testAdjudicationItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    void putNonExistingAdjudicationItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationItemRepository.findAll().size();
        adjudicationItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adjudicationItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationItem in the database
        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdjudicationItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationItemRepository.findAll().size();
        adjudicationItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationItem in the database
        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdjudicationItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationItemRepository.findAll().size();
        adjudicationItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adjudicationItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdjudicationItem in the database
        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdjudicationItemWithPatch() throws Exception {
        // Initialize the database
        adjudicationItemRepository.saveAndFlush(adjudicationItem);

        int databaseSizeBeforeUpdate = adjudicationItemRepository.findAll().size();

        // Update the adjudicationItem using partial update
        AdjudicationItem partialUpdatedAdjudicationItem = new AdjudicationItem();
        partialUpdatedAdjudicationItem.setId(adjudicationItem.getId());

        partialUpdatedAdjudicationItem.outcome(UPDATED_OUTCOME);

        restAdjudicationItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudicationItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudicationItem))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationItem in the database
        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationItem testAdjudicationItem = adjudicationItemList.get(adjudicationItemList.size() - 1);
        assertThat(testAdjudicationItem.getOutcome()).isEqualTo(UPDATED_OUTCOME);
        assertThat(testAdjudicationItem.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
    }

    @Test
    @Transactional
    void fullUpdateAdjudicationItemWithPatch() throws Exception {
        // Initialize the database
        adjudicationItemRepository.saveAndFlush(adjudicationItem);

        int databaseSizeBeforeUpdate = adjudicationItemRepository.findAll().size();

        // Update the adjudicationItem using partial update
        AdjudicationItem partialUpdatedAdjudicationItem = new AdjudicationItem();
        partialUpdatedAdjudicationItem.setId(adjudicationItem.getId());

        partialUpdatedAdjudicationItem.outcome(UPDATED_OUTCOME).sequence(UPDATED_SEQUENCE);

        restAdjudicationItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudicationItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudicationItem))
            )
            .andExpect(status().isOk());

        // Validate the AdjudicationItem in the database
        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeUpdate);
        AdjudicationItem testAdjudicationItem = adjudicationItemList.get(adjudicationItemList.size() - 1);
        assertThat(testAdjudicationItem.getOutcome()).isEqualTo(UPDATED_OUTCOME);
        assertThat(testAdjudicationItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    void patchNonExistingAdjudicationItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationItemRepository.findAll().size();
        adjudicationItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adjudicationItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationItem in the database
        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdjudicationItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationItemRepository.findAll().size();
        adjudicationItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjudicationItem in the database
        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdjudicationItem() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationItemRepository.findAll().size();
        adjudicationItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudicationItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdjudicationItem in the database
        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdjudicationItem() throws Exception {
        // Initialize the database
        adjudicationItemRepository.saveAndFlush(adjudicationItem);

        int databaseSizeBeforeDelete = adjudicationItemRepository.findAll().size();

        // Delete the adjudicationItem
        restAdjudicationItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, adjudicationItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdjudicationItem> adjudicationItemList = adjudicationItemRepository.findAll();
        assertThat(adjudicationItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
