package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Quantity;
import com.platformsandsolutions.hcpnphiesportal.repository.QuantityRepository;
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
 * Integration tests for the {@link QuantityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuantityResourceIT {

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/quantities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuantityRepository quantityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuantityMockMvc;

    private Quantity quantity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quantity createEntity(EntityManager em) {
        Quantity quantity = new Quantity().value(DEFAULT_VALUE).unit(DEFAULT_UNIT);
        return quantity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quantity createUpdatedEntity(EntityManager em) {
        Quantity quantity = new Quantity().value(UPDATED_VALUE).unit(UPDATED_UNIT);
        return quantity;
    }

    @BeforeEach
    public void initTest() {
        quantity = createEntity(em);
    }

    @Test
    @Transactional
    void createQuantity() throws Exception {
        int databaseSizeBeforeCreate = quantityRepository.findAll().size();
        // Create the Quantity
        restQuantityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quantity)))
            .andExpect(status().isCreated());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeCreate + 1);
        Quantity testQuantity = quantityList.get(quantityList.size() - 1);
        assertThat(testQuantity.getValue()).isEqualByComparingTo(DEFAULT_VALUE);
        assertThat(testQuantity.getUnit()).isEqualTo(DEFAULT_UNIT);
    }

    @Test
    @Transactional
    void createQuantityWithExistingId() throws Exception {
        // Create the Quantity with an existing ID
        quantity.setId(1L);

        int databaseSizeBeforeCreate = quantityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuantityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quantity)))
            .andExpect(status().isBadRequest());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuantities() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        // Get all the quantityList
        restQuantityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quantity.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(sameNumber(DEFAULT_VALUE))))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)));
    }

    @Test
    @Transactional
    void getQuantity() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        // Get the quantity
        restQuantityMockMvc
            .perform(get(ENTITY_API_URL_ID, quantity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quantity.getId().intValue()))
            .andExpect(jsonPath("$.value").value(sameNumber(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT));
    }

    @Test
    @Transactional
    void getNonExistingQuantity() throws Exception {
        // Get the quantity
        restQuantityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuantity() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();

        // Update the quantity
        Quantity updatedQuantity = quantityRepository.findById(quantity.getId()).get();
        // Disconnect from session so that the updates on updatedQuantity are not directly saved in db
        em.detach(updatedQuantity);
        updatedQuantity.value(UPDATED_VALUE).unit(UPDATED_UNIT);

        restQuantityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuantity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuantity))
            )
            .andExpect(status().isOk());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
        Quantity testQuantity = quantityList.get(quantityList.size() - 1);
        assertThat(testQuantity.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testQuantity.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    void putNonExistingQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();
        quantity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuantityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quantity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quantity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();
        quantity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuantityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quantity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();
        quantity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuantityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quantity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuantityWithPatch() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();

        // Update the quantity using partial update
        Quantity partialUpdatedQuantity = new Quantity();
        partialUpdatedQuantity.setId(quantity.getId());

        partialUpdatedQuantity.unit(UPDATED_UNIT);

        restQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuantity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuantity))
            )
            .andExpect(status().isOk());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
        Quantity testQuantity = quantityList.get(quantityList.size() - 1);
        assertThat(testQuantity.getValue()).isEqualByComparingTo(DEFAULT_VALUE);
        assertThat(testQuantity.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    void fullUpdateQuantityWithPatch() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();

        // Update the quantity using partial update
        Quantity partialUpdatedQuantity = new Quantity();
        partialUpdatedQuantity.setId(quantity.getId());

        partialUpdatedQuantity.value(UPDATED_VALUE).unit(UPDATED_UNIT);

        restQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuantity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuantity))
            )
            .andExpect(status().isOk());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
        Quantity testQuantity = quantityList.get(quantityList.size() - 1);
        assertThat(testQuantity.getValue()).isEqualByComparingTo(UPDATED_VALUE);
        assertThat(testQuantity.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    void patchNonExistingQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();
        quantity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quantity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quantity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();
        quantity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuantityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quantity))
            )
            .andExpect(status().isBadRequest());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuantity() throws Exception {
        int databaseSizeBeforeUpdate = quantityRepository.findAll().size();
        quantity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuantityMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(quantity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Quantity in the database
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuantity() throws Exception {
        // Initialize the database
        quantityRepository.saveAndFlush(quantity);

        int databaseSizeBeforeDelete = quantityRepository.findAll().size();

        // Delete the quantity
        restQuantityMockMvc
            .perform(delete(ENTITY_API_URL_ID, quantity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quantity> quantityList = quantityRepository.findAll();
        assertThat(quantityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
