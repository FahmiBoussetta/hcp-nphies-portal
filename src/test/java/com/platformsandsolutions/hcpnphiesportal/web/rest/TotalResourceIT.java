package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Total;
import com.platformsandsolutions.hcpnphiesportal.repository.TotalRepository;
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
 * Integration tests for the {@link TotalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TotalResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final String ENTITY_API_URL = "/api/totals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TotalRepository totalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTotalMockMvc;

    private Total total;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Total createEntity(EntityManager em) {
        Total total = new Total().category(DEFAULT_CATEGORY).amount(DEFAULT_AMOUNT);
        return total;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Total createUpdatedEntity(EntityManager em) {
        Total total = new Total().category(UPDATED_CATEGORY).amount(UPDATED_AMOUNT);
        return total;
    }

    @BeforeEach
    public void initTest() {
        total = createEntity(em);
    }

    @Test
    @Transactional
    void createTotal() throws Exception {
        int databaseSizeBeforeCreate = totalRepository.findAll().size();
        // Create the Total
        restTotalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(total)))
            .andExpect(status().isCreated());

        // Validate the Total in the database
        List<Total> totalList = totalRepository.findAll();
        assertThat(totalList).hasSize(databaseSizeBeforeCreate + 1);
        Total testTotal = totalList.get(totalList.size() - 1);
        assertThat(testTotal.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testTotal.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void createTotalWithExistingId() throws Exception {
        // Create the Total with an existing ID
        total.setId(1L);

        int databaseSizeBeforeCreate = totalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTotalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(total)))
            .andExpect(status().isBadRequest());

        // Validate the Total in the database
        List<Total> totalList = totalRepository.findAll();
        assertThat(totalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTotals() throws Exception {
        // Initialize the database
        totalRepository.saveAndFlush(total);

        // Get all the totalList
        restTotalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(total.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)));
    }

    @Test
    @Transactional
    void getTotal() throws Exception {
        // Initialize the database
        totalRepository.saveAndFlush(total);

        // Get the total
        restTotalMockMvc
            .perform(get(ENTITY_API_URL_ID, total.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(total.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT));
    }

    @Test
    @Transactional
    void getNonExistingTotal() throws Exception {
        // Get the total
        restTotalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTotal() throws Exception {
        // Initialize the database
        totalRepository.saveAndFlush(total);

        int databaseSizeBeforeUpdate = totalRepository.findAll().size();

        // Update the total
        Total updatedTotal = totalRepository.findById(total.getId()).get();
        // Disconnect from session so that the updates on updatedTotal are not directly saved in db
        em.detach(updatedTotal);
        updatedTotal.category(UPDATED_CATEGORY).amount(UPDATED_AMOUNT);

        restTotalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTotal.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTotal))
            )
            .andExpect(status().isOk());

        // Validate the Total in the database
        List<Total> totalList = totalRepository.findAll();
        assertThat(totalList).hasSize(databaseSizeBeforeUpdate);
        Total testTotal = totalList.get(totalList.size() - 1);
        assertThat(testTotal.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTotal.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingTotal() throws Exception {
        int databaseSizeBeforeUpdate = totalRepository.findAll().size();
        total.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTotalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, total.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(total))
            )
            .andExpect(status().isBadRequest());

        // Validate the Total in the database
        List<Total> totalList = totalRepository.findAll();
        assertThat(totalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTotal() throws Exception {
        int databaseSizeBeforeUpdate = totalRepository.findAll().size();
        total.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(total))
            )
            .andExpect(status().isBadRequest());

        // Validate the Total in the database
        List<Total> totalList = totalRepository.findAll();
        assertThat(totalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTotal() throws Exception {
        int databaseSizeBeforeUpdate = totalRepository.findAll().size();
        total.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(total)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Total in the database
        List<Total> totalList = totalRepository.findAll();
        assertThat(totalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTotalWithPatch() throws Exception {
        // Initialize the database
        totalRepository.saveAndFlush(total);

        int databaseSizeBeforeUpdate = totalRepository.findAll().size();

        // Update the total using partial update
        Total partialUpdatedTotal = new Total();
        partialUpdatedTotal.setId(total.getId());

        partialUpdatedTotal.category(UPDATED_CATEGORY);

        restTotalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTotal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTotal))
            )
            .andExpect(status().isOk());

        // Validate the Total in the database
        List<Total> totalList = totalRepository.findAll();
        assertThat(totalList).hasSize(databaseSizeBeforeUpdate);
        Total testTotal = totalList.get(totalList.size() - 1);
        assertThat(testTotal.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTotal.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateTotalWithPatch() throws Exception {
        // Initialize the database
        totalRepository.saveAndFlush(total);

        int databaseSizeBeforeUpdate = totalRepository.findAll().size();

        // Update the total using partial update
        Total partialUpdatedTotal = new Total();
        partialUpdatedTotal.setId(total.getId());

        partialUpdatedTotal.category(UPDATED_CATEGORY).amount(UPDATED_AMOUNT);

        restTotalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTotal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTotal))
            )
            .andExpect(status().isOk());

        // Validate the Total in the database
        List<Total> totalList = totalRepository.findAll();
        assertThat(totalList).hasSize(databaseSizeBeforeUpdate);
        Total testTotal = totalList.get(totalList.size() - 1);
        assertThat(testTotal.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTotal.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingTotal() throws Exception {
        int databaseSizeBeforeUpdate = totalRepository.findAll().size();
        total.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTotalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, total.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(total))
            )
            .andExpect(status().isBadRequest());

        // Validate the Total in the database
        List<Total> totalList = totalRepository.findAll();
        assertThat(totalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTotal() throws Exception {
        int databaseSizeBeforeUpdate = totalRepository.findAll().size();
        total.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(total))
            )
            .andExpect(status().isBadRequest());

        // Validate the Total in the database
        List<Total> totalList = totalRepository.findAll();
        assertThat(totalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTotal() throws Exception {
        int databaseSizeBeforeUpdate = totalRepository.findAll().size();
        total.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTotalMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(total)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Total in the database
        List<Total> totalList = totalRepository.findAll();
        assertThat(totalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTotal() throws Exception {
        // Initialize the database
        totalRepository.saveAndFlush(total);

        int databaseSizeBeforeDelete = totalRepository.findAll().size();

        // Delete the total
        restTotalMockMvc
            .perform(delete(ENTITY_API_URL_ID, total.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Total> totalList = totalRepository.findAll();
        assertThat(totalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
