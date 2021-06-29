package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Payee;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.PayeeTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.PayeeRepository;
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
 * Integration tests for the {@link PayeeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PayeeResourceIT {

    private static final PayeeTypeEnum DEFAULT_TYPE = PayeeTypeEnum.Subscriber;
    private static final PayeeTypeEnum UPDATED_TYPE = PayeeTypeEnum.Provider;

    private static final String ENTITY_API_URL = "/api/payees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PayeeRepository payeeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayeeMockMvc;

    private Payee payee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payee createEntity(EntityManager em) {
        Payee payee = new Payee().type(DEFAULT_TYPE);
        return payee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payee createUpdatedEntity(EntityManager em) {
        Payee payee = new Payee().type(UPDATED_TYPE);
        return payee;
    }

    @BeforeEach
    public void initTest() {
        payee = createEntity(em);
    }

    @Test
    @Transactional
    void createPayee() throws Exception {
        int databaseSizeBeforeCreate = payeeRepository.findAll().size();
        // Create the Payee
        restPayeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payee)))
            .andExpect(status().isCreated());

        // Validate the Payee in the database
        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeCreate + 1);
        Payee testPayee = payeeList.get(payeeList.size() - 1);
        assertThat(testPayee.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createPayeeWithExistingId() throws Exception {
        // Create the Payee with an existing ID
        payee.setId(1L);

        int databaseSizeBeforeCreate = payeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payee)))
            .andExpect(status().isBadRequest());

        // Validate the Payee in the database
        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = payeeRepository.findAll().size();
        // set the field null
        payee.setType(null);

        // Create the Payee, which fails.

        restPayeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payee)))
            .andExpect(status().isBadRequest());

        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPayees() throws Exception {
        // Initialize the database
        payeeRepository.saveAndFlush(payee);

        // Get all the payeeList
        restPayeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payee.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getPayee() throws Exception {
        // Initialize the database
        payeeRepository.saveAndFlush(payee);

        // Get the payee
        restPayeeMockMvc
            .perform(get(ENTITY_API_URL_ID, payee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payee.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPayee() throws Exception {
        // Get the payee
        restPayeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPayee() throws Exception {
        // Initialize the database
        payeeRepository.saveAndFlush(payee);

        int databaseSizeBeforeUpdate = payeeRepository.findAll().size();

        // Update the payee
        Payee updatedPayee = payeeRepository.findById(payee.getId()).get();
        // Disconnect from session so that the updates on updatedPayee are not directly saved in db
        em.detach(updatedPayee);
        updatedPayee.type(UPDATED_TYPE);

        restPayeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayee))
            )
            .andExpect(status().isOk());

        // Validate the Payee in the database
        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeUpdate);
        Payee testPayee = payeeList.get(payeeList.size() - 1);
        assertThat(testPayee.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPayee() throws Exception {
        int databaseSizeBeforeUpdate = payeeRepository.findAll().size();
        payee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payee.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payee in the database
        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayee() throws Exception {
        int databaseSizeBeforeUpdate = payeeRepository.findAll().size();
        payee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payee in the database
        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayee() throws Exception {
        int databaseSizeBeforeUpdate = payeeRepository.findAll().size();
        payee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayeeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payee in the database
        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePayeeWithPatch() throws Exception {
        // Initialize the database
        payeeRepository.saveAndFlush(payee);

        int databaseSizeBeforeUpdate = payeeRepository.findAll().size();

        // Update the payee using partial update
        Payee partialUpdatedPayee = new Payee();
        partialUpdatedPayee.setId(payee.getId());

        restPayeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayee))
            )
            .andExpect(status().isOk());

        // Validate the Payee in the database
        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeUpdate);
        Payee testPayee = payeeList.get(payeeList.size() - 1);
        assertThat(testPayee.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePayeeWithPatch() throws Exception {
        // Initialize the database
        payeeRepository.saveAndFlush(payee);

        int databaseSizeBeforeUpdate = payeeRepository.findAll().size();

        // Update the payee using partial update
        Payee partialUpdatedPayee = new Payee();
        partialUpdatedPayee.setId(payee.getId());

        partialUpdatedPayee.type(UPDATED_TYPE);

        restPayeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayee))
            )
            .andExpect(status().isOk());

        // Validate the Payee in the database
        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeUpdate);
        Payee testPayee = payeeList.get(payeeList.size() - 1);
        assertThat(testPayee.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPayee() throws Exception {
        int databaseSizeBeforeUpdate = payeeRepository.findAll().size();
        payee.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payee in the database
        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayee() throws Exception {
        int databaseSizeBeforeUpdate = payeeRepository.findAll().size();
        payee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payee))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payee in the database
        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayee() throws Exception {
        int databaseSizeBeforeUpdate = payeeRepository.findAll().size();
        payee.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayeeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(payee)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payee in the database
        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayee() throws Exception {
        // Initialize the database
        payeeRepository.saveAndFlush(payee);

        int databaseSizeBeforeDelete = payeeRepository.findAll().size();

        // Delete the payee
        restPayeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, payee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payee> payeeList = payeeRepository.findAll();
        assertThat(payeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
