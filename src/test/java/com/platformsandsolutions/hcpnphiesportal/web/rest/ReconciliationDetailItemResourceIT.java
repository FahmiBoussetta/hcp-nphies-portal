package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ReconciliationDetailItem;
import com.platformsandsolutions.hcpnphiesportal.repository.ReconciliationDetailItemRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ReconciliationDetailItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReconciliationDetailItemResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_PREDECESSOR = "AAAAAAAAAA";
    private static final String UPDATED_PREDECESSOR = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/reconciliation-detail-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReconciliationDetailItemRepository reconciliationDetailItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReconciliationDetailItemMockMvc;

    private ReconciliationDetailItem reconciliationDetailItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReconciliationDetailItem createEntity(EntityManager em) {
        ReconciliationDetailItem reconciliationDetailItem = new ReconciliationDetailItem()
            .identifier(DEFAULT_IDENTIFIER)
            .predecessor(DEFAULT_PREDECESSOR)
            .type(DEFAULT_TYPE)
            .date(DEFAULT_DATE)
            .amount(DEFAULT_AMOUNT);
        return reconciliationDetailItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReconciliationDetailItem createUpdatedEntity(EntityManager em) {
        ReconciliationDetailItem reconciliationDetailItem = new ReconciliationDetailItem()
            .identifier(UPDATED_IDENTIFIER)
            .predecessor(UPDATED_PREDECESSOR)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT);
        return reconciliationDetailItem;
    }

    @BeforeEach
    public void initTest() {
        reconciliationDetailItem = createEntity(em);
    }

    @Test
    @Transactional
    void createReconciliationDetailItem() throws Exception {
        int databaseSizeBeforeCreate = reconciliationDetailItemRepository.findAll().size();
        // Create the ReconciliationDetailItem
        restReconciliationDetailItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reconciliationDetailItem))
            )
            .andExpect(status().isCreated());

        // Validate the ReconciliationDetailItem in the database
        List<ReconciliationDetailItem> reconciliationDetailItemList = reconciliationDetailItemRepository.findAll();
        assertThat(reconciliationDetailItemList).hasSize(databaseSizeBeforeCreate + 1);
        ReconciliationDetailItem testReconciliationDetailItem = reconciliationDetailItemList.get(reconciliationDetailItemList.size() - 1);
        assertThat(testReconciliationDetailItem.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testReconciliationDetailItem.getPredecessor()).isEqualTo(DEFAULT_PREDECESSOR);
        assertThat(testReconciliationDetailItem.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReconciliationDetailItem.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testReconciliationDetailItem.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void createReconciliationDetailItemWithExistingId() throws Exception {
        // Create the ReconciliationDetailItem with an existing ID
        reconciliationDetailItem.setId(1L);

        int databaseSizeBeforeCreate = reconciliationDetailItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReconciliationDetailItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reconciliationDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReconciliationDetailItem in the database
        List<ReconciliationDetailItem> reconciliationDetailItemList = reconciliationDetailItemRepository.findAll();
        assertThat(reconciliationDetailItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReconciliationDetailItems() throws Exception {
        // Initialize the database
        reconciliationDetailItemRepository.saveAndFlush(reconciliationDetailItem);

        // Get all the reconciliationDetailItemList
        restReconciliationDetailItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reconciliationDetailItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].predecessor").value(hasItem(DEFAULT_PREDECESSOR)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))));
    }

    @Test
    @Transactional
    void getReconciliationDetailItem() throws Exception {
        // Initialize the database
        reconciliationDetailItemRepository.saveAndFlush(reconciliationDetailItem);

        // Get the reconciliationDetailItem
        restReconciliationDetailItemMockMvc
            .perform(get(ENTITY_API_URL_ID, reconciliationDetailItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reconciliationDetailItem.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.predecessor").value(DEFAULT_PREDECESSOR))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)));
    }

    @Test
    @Transactional
    void getNonExistingReconciliationDetailItem() throws Exception {
        // Get the reconciliationDetailItem
        restReconciliationDetailItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReconciliationDetailItem() throws Exception {
        // Initialize the database
        reconciliationDetailItemRepository.saveAndFlush(reconciliationDetailItem);

        int databaseSizeBeforeUpdate = reconciliationDetailItemRepository.findAll().size();

        // Update the reconciliationDetailItem
        ReconciliationDetailItem updatedReconciliationDetailItem = reconciliationDetailItemRepository
            .findById(reconciliationDetailItem.getId())
            .get();
        // Disconnect from session so that the updates on updatedReconciliationDetailItem are not directly saved in db
        em.detach(updatedReconciliationDetailItem);
        updatedReconciliationDetailItem
            .identifier(UPDATED_IDENTIFIER)
            .predecessor(UPDATED_PREDECESSOR)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT);

        restReconciliationDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedReconciliationDetailItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedReconciliationDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the ReconciliationDetailItem in the database
        List<ReconciliationDetailItem> reconciliationDetailItemList = reconciliationDetailItemRepository.findAll();
        assertThat(reconciliationDetailItemList).hasSize(databaseSizeBeforeUpdate);
        ReconciliationDetailItem testReconciliationDetailItem = reconciliationDetailItemList.get(reconciliationDetailItemList.size() - 1);
        assertThat(testReconciliationDetailItem.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testReconciliationDetailItem.getPredecessor()).isEqualTo(UPDATED_PREDECESSOR);
        assertThat(testReconciliationDetailItem.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReconciliationDetailItem.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testReconciliationDetailItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingReconciliationDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = reconciliationDetailItemRepository.findAll().size();
        reconciliationDetailItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReconciliationDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reconciliationDetailItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reconciliationDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReconciliationDetailItem in the database
        List<ReconciliationDetailItem> reconciliationDetailItemList = reconciliationDetailItemRepository.findAll();
        assertThat(reconciliationDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReconciliationDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = reconciliationDetailItemRepository.findAll().size();
        reconciliationDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReconciliationDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reconciliationDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReconciliationDetailItem in the database
        List<ReconciliationDetailItem> reconciliationDetailItemList = reconciliationDetailItemRepository.findAll();
        assertThat(reconciliationDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReconciliationDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = reconciliationDetailItemRepository.findAll().size();
        reconciliationDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReconciliationDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reconciliationDetailItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReconciliationDetailItem in the database
        List<ReconciliationDetailItem> reconciliationDetailItemList = reconciliationDetailItemRepository.findAll();
        assertThat(reconciliationDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReconciliationDetailItemWithPatch() throws Exception {
        // Initialize the database
        reconciliationDetailItemRepository.saveAndFlush(reconciliationDetailItem);

        int databaseSizeBeforeUpdate = reconciliationDetailItemRepository.findAll().size();

        // Update the reconciliationDetailItem using partial update
        ReconciliationDetailItem partialUpdatedReconciliationDetailItem = new ReconciliationDetailItem();
        partialUpdatedReconciliationDetailItem.setId(reconciliationDetailItem.getId());

        partialUpdatedReconciliationDetailItem.predecessor(UPDATED_PREDECESSOR);

        restReconciliationDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReconciliationDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReconciliationDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the ReconciliationDetailItem in the database
        List<ReconciliationDetailItem> reconciliationDetailItemList = reconciliationDetailItemRepository.findAll();
        assertThat(reconciliationDetailItemList).hasSize(databaseSizeBeforeUpdate);
        ReconciliationDetailItem testReconciliationDetailItem = reconciliationDetailItemList.get(reconciliationDetailItemList.size() - 1);
        assertThat(testReconciliationDetailItem.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testReconciliationDetailItem.getPredecessor()).isEqualTo(UPDATED_PREDECESSOR);
        assertThat(testReconciliationDetailItem.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testReconciliationDetailItem.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testReconciliationDetailItem.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateReconciliationDetailItemWithPatch() throws Exception {
        // Initialize the database
        reconciliationDetailItemRepository.saveAndFlush(reconciliationDetailItem);

        int databaseSizeBeforeUpdate = reconciliationDetailItemRepository.findAll().size();

        // Update the reconciliationDetailItem using partial update
        ReconciliationDetailItem partialUpdatedReconciliationDetailItem = new ReconciliationDetailItem();
        partialUpdatedReconciliationDetailItem.setId(reconciliationDetailItem.getId());

        partialUpdatedReconciliationDetailItem
            .identifier(UPDATED_IDENTIFIER)
            .predecessor(UPDATED_PREDECESSOR)
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT);

        restReconciliationDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReconciliationDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReconciliationDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the ReconciliationDetailItem in the database
        List<ReconciliationDetailItem> reconciliationDetailItemList = reconciliationDetailItemRepository.findAll();
        assertThat(reconciliationDetailItemList).hasSize(databaseSizeBeforeUpdate);
        ReconciliationDetailItem testReconciliationDetailItem = reconciliationDetailItemList.get(reconciliationDetailItemList.size() - 1);
        assertThat(testReconciliationDetailItem.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testReconciliationDetailItem.getPredecessor()).isEqualTo(UPDATED_PREDECESSOR);
        assertThat(testReconciliationDetailItem.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testReconciliationDetailItem.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testReconciliationDetailItem.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingReconciliationDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = reconciliationDetailItemRepository.findAll().size();
        reconciliationDetailItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReconciliationDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reconciliationDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reconciliationDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReconciliationDetailItem in the database
        List<ReconciliationDetailItem> reconciliationDetailItemList = reconciliationDetailItemRepository.findAll();
        assertThat(reconciliationDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReconciliationDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = reconciliationDetailItemRepository.findAll().size();
        reconciliationDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReconciliationDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reconciliationDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReconciliationDetailItem in the database
        List<ReconciliationDetailItem> reconciliationDetailItemList = reconciliationDetailItemRepository.findAll();
        assertThat(reconciliationDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReconciliationDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = reconciliationDetailItemRepository.findAll().size();
        reconciliationDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReconciliationDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reconciliationDetailItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReconciliationDetailItem in the database
        List<ReconciliationDetailItem> reconciliationDetailItemList = reconciliationDetailItemRepository.findAll();
        assertThat(reconciliationDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReconciliationDetailItem() throws Exception {
        // Initialize the database
        reconciliationDetailItemRepository.saveAndFlush(reconciliationDetailItem);

        int databaseSizeBeforeDelete = reconciliationDetailItemRepository.findAll().size();

        // Delete the reconciliationDetailItem
        restReconciliationDetailItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, reconciliationDetailItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReconciliationDetailItem> reconciliationDetailItemList = reconciliationDetailItemRepository.findAll();
        assertThat(reconciliationDetailItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
