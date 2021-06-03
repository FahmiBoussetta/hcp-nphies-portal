package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Adjudication;
import com.platformsandsolutions.hcpnphiesportal.repository.AdjudicationRepository;
import java.math.BigDecimal;
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
 * Integration tests for the {@link AdjudicationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdjudicationResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/adjudications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdjudicationRepository adjudicationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdjudicationMockMvc;

    private Adjudication adjudication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adjudication createEntity(EntityManager em) {
        Adjudication adjudication = new Adjudication()
            .category(DEFAULT_CATEGORY)
            .reason(DEFAULT_REASON)
            .amount(DEFAULT_AMOUNT)
            .value(DEFAULT_VALUE);
        return adjudication;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adjudication createUpdatedEntity(EntityManager em) {
        Adjudication adjudication = new Adjudication()
            .category(UPDATED_CATEGORY)
            .reason(UPDATED_REASON)
            .amount(UPDATED_AMOUNT)
            .value(UPDATED_VALUE);
        return adjudication;
    }

    @BeforeEach
    public void initTest() {
        adjudication = createEntity(em);
    }

    @Test
    @Transactional
    void createAdjudication() throws Exception {
        int databaseSizeBeforeCreate = adjudicationRepository.findAll().size();
        // Create the Adjudication
        restAdjudicationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adjudication)))
            .andExpect(status().isCreated());

        // Validate the Adjudication in the database
        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeCreate + 1);
        Adjudication testAdjudication = adjudicationList.get(adjudicationList.size() - 1);
        assertThat(testAdjudication.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testAdjudication.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testAdjudication.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAdjudication.getValue()).isEqualByComparingTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createAdjudicationWithExistingId() throws Exception {
        // Create the Adjudication with an existing ID
        adjudication.setId(1L);

        int databaseSizeBeforeCreate = adjudicationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjudicationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adjudication)))
            .andExpect(status().isBadRequest());

        // Validate the Adjudication in the database
        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = adjudicationRepository.findAll().size();
        // set the field null
        adjudication.setCategory(null);

        // Create the Adjudication, which fails.

        restAdjudicationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adjudication)))
            .andExpect(status().isBadRequest());

        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = adjudicationRepository.findAll().size();
        // set the field null
        adjudication.setAmount(null);

        // Create the Adjudication, which fails.

        restAdjudicationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adjudication)))
            .andExpect(status().isBadRequest());

        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdjudications() throws Exception {
        // Initialize the database
        adjudicationRepository.saveAndFlush(adjudication);

        // Get all the adjudicationList
        restAdjudicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjudication.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(sameNumber(DEFAULT_VALUE))));
    }

    @Test
    @Transactional
    void getAdjudication() throws Exception {
        // Initialize the database
        adjudicationRepository.saveAndFlush(adjudication);

        // Get the adjudication
        restAdjudicationMockMvc
            .perform(get(ENTITY_API_URL_ID, adjudication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adjudication.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.value").value(sameNumber(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getNonExistingAdjudication() throws Exception {
        // Get the adjudication
        restAdjudicationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdjudication() throws Exception {
        // Initialize the database
        adjudicationRepository.saveAndFlush(adjudication);

        int databaseSizeBeforeUpdate = adjudicationRepository.findAll().size();

        // Update the adjudication
        Adjudication updatedAdjudication = adjudicationRepository.findById(adjudication.getId()).get();
        // Disconnect from session so that the updates on updatedAdjudication are not directly saved in db
        em.detach(updatedAdjudication);
        updatedAdjudication.category(UPDATED_CATEGORY).reason(UPDATED_REASON).amount(UPDATED_AMOUNT).value(UPDATED_VALUE);

        restAdjudicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdjudication.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAdjudication))
            )
            .andExpect(status().isOk());

        // Validate the Adjudication in the database
        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeUpdate);
        Adjudication testAdjudication = adjudicationList.get(adjudicationList.size() - 1);
        assertThat(testAdjudication.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testAdjudication.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testAdjudication.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAdjudication.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingAdjudication() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationRepository.findAll().size();
        adjudication.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adjudication.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudication))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adjudication in the database
        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdjudication() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationRepository.findAll().size();
        adjudication.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjudication))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adjudication in the database
        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdjudication() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationRepository.findAll().size();
        adjudication.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adjudication)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adjudication in the database
        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdjudicationWithPatch() throws Exception {
        // Initialize the database
        adjudicationRepository.saveAndFlush(adjudication);

        int databaseSizeBeforeUpdate = adjudicationRepository.findAll().size();

        // Update the adjudication using partial update
        Adjudication partialUpdatedAdjudication = new Adjudication();
        partialUpdatedAdjudication.setId(adjudication.getId());

        partialUpdatedAdjudication.category(UPDATED_CATEGORY).value(UPDATED_VALUE);

        restAdjudicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudication))
            )
            .andExpect(status().isOk());

        // Validate the Adjudication in the database
        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeUpdate);
        Adjudication testAdjudication = adjudicationList.get(adjudicationList.size() - 1);
        assertThat(testAdjudication.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testAdjudication.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testAdjudication.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAdjudication.getValue()).isEqualByComparingTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateAdjudicationWithPatch() throws Exception {
        // Initialize the database
        adjudicationRepository.saveAndFlush(adjudication);

        int databaseSizeBeforeUpdate = adjudicationRepository.findAll().size();

        // Update the adjudication using partial update
        Adjudication partialUpdatedAdjudication = new Adjudication();
        partialUpdatedAdjudication.setId(adjudication.getId());

        partialUpdatedAdjudication.category(UPDATED_CATEGORY).reason(UPDATED_REASON).amount(UPDATED_AMOUNT).value(UPDATED_VALUE);

        restAdjudicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdjudication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdjudication))
            )
            .andExpect(status().isOk());

        // Validate the Adjudication in the database
        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeUpdate);
        Adjudication testAdjudication = adjudicationList.get(adjudicationList.size() - 1);
        assertThat(testAdjudication.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testAdjudication.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testAdjudication.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAdjudication.getValue()).isEqualByComparingTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingAdjudication() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationRepository.findAll().size();
        adjudication.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjudicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adjudication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudication))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adjudication in the database
        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdjudication() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationRepository.findAll().size();
        adjudication.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adjudication))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adjudication in the database
        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdjudication() throws Exception {
        int databaseSizeBeforeUpdate = adjudicationRepository.findAll().size();
        adjudication.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdjudicationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adjudication))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adjudication in the database
        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdjudication() throws Exception {
        // Initialize the database
        adjudicationRepository.saveAndFlush(adjudication);

        int databaseSizeBeforeDelete = adjudicationRepository.findAll().size();

        // Delete the adjudication
        restAdjudicationMockMvc
            .perform(delete(ENTITY_API_URL_ID, adjudication.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Adjudication> adjudicationList = adjudicationRepository.findAll();
        assertThat(adjudicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
