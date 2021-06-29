package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.PaymentReconciliation;
import com.platformsandsolutions.hcpnphiesportal.repository.PaymentReconciliationRepository;
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
 * Integration tests for the {@link PaymentReconciliationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentReconciliationResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM = "BBBBBBBBBB";

    private static final String DEFAULT_PARSED = "AAAAAAAAAA";
    private static final String UPDATED_PARSED = "BBBBBBBBBB";

    private static final Instant DEFAULT_PERIOD_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PERIOD_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PERIOD_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PERIOD_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OUTCOME = "AAAAAAAAAA";
    private static final String UPDATED_OUTCOME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPOSITION = "AAAAAAAAAA";
    private static final String UPDATED_DISPOSITION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PAYMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_PAYMENT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_IDENTIFIER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-reconciliations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentReconciliationRepository paymentReconciliationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentReconciliationMockMvc;

    private PaymentReconciliation paymentReconciliation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentReconciliation createEntity(EntityManager em) {
        PaymentReconciliation paymentReconciliation = new PaymentReconciliation()
            .value(DEFAULT_VALUE)
            .system(DEFAULT_SYSTEM)
            .parsed(DEFAULT_PARSED)
            .periodStart(DEFAULT_PERIOD_START)
            .periodEnd(DEFAULT_PERIOD_END)
            .outcome(DEFAULT_OUTCOME)
            .disposition(DEFAULT_DISPOSITION)
            .paymentAmount(DEFAULT_PAYMENT_AMOUNT)
            .paymentIdentifier(DEFAULT_PAYMENT_IDENTIFIER);
        return paymentReconciliation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentReconciliation createUpdatedEntity(EntityManager em) {
        PaymentReconciliation paymentReconciliation = new PaymentReconciliation()
            .value(UPDATED_VALUE)
            .system(UPDATED_SYSTEM)
            .parsed(UPDATED_PARSED)
            .periodStart(UPDATED_PERIOD_START)
            .periodEnd(UPDATED_PERIOD_END)
            .outcome(UPDATED_OUTCOME)
            .disposition(UPDATED_DISPOSITION)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .paymentIdentifier(UPDATED_PAYMENT_IDENTIFIER);
        return paymentReconciliation;
    }

    @BeforeEach
    public void initTest() {
        paymentReconciliation = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentReconciliation() throws Exception {
        int databaseSizeBeforeCreate = paymentReconciliationRepository.findAll().size();
        // Create the PaymentReconciliation
        restPaymentReconciliationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentReconciliation))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentReconciliation in the database
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertThat(paymentReconciliationList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentReconciliation testPaymentReconciliation = paymentReconciliationList.get(paymentReconciliationList.size() - 1);
        assertThat(testPaymentReconciliation.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPaymentReconciliation.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testPaymentReconciliation.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testPaymentReconciliation.getPeriodStart()).isEqualTo(DEFAULT_PERIOD_START);
        assertThat(testPaymentReconciliation.getPeriodEnd()).isEqualTo(DEFAULT_PERIOD_END);
        assertThat(testPaymentReconciliation.getOutcome()).isEqualTo(DEFAULT_OUTCOME);
        assertThat(testPaymentReconciliation.getDisposition()).isEqualTo(DEFAULT_DISPOSITION);
        assertThat(testPaymentReconciliation.getPaymentAmount()).isEqualByComparingTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testPaymentReconciliation.getPaymentIdentifier()).isEqualTo(DEFAULT_PAYMENT_IDENTIFIER);
    }

    @Test
    @Transactional
    void createPaymentReconciliationWithExistingId() throws Exception {
        // Create the PaymentReconciliation with an existing ID
        paymentReconciliation.setId(1L);

        int databaseSizeBeforeCreate = paymentReconciliationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentReconciliationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentReconciliation))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentReconciliation in the database
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertThat(paymentReconciliationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentReconciliations() throws Exception {
        // Initialize the database
        paymentReconciliationRepository.saveAndFlush(paymentReconciliation);

        // Get all the paymentReconciliationList
        restPaymentReconciliationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentReconciliation.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].system").value(hasItem(DEFAULT_SYSTEM)))
            .andExpect(jsonPath("$.[*].parsed").value(hasItem(DEFAULT_PARSED)))
            .andExpect(jsonPath("$.[*].periodStart").value(hasItem(DEFAULT_PERIOD_START.toString())))
            .andExpect(jsonPath("$.[*].periodEnd").value(hasItem(DEFAULT_PERIOD_END.toString())))
            .andExpect(jsonPath("$.[*].outcome").value(hasItem(DEFAULT_OUTCOME)))
            .andExpect(jsonPath("$.[*].disposition").value(hasItem(DEFAULT_DISPOSITION)))
            .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(sameNumber(DEFAULT_PAYMENT_AMOUNT))))
            .andExpect(jsonPath("$.[*].paymentIdentifier").value(hasItem(DEFAULT_PAYMENT_IDENTIFIER)));
    }

    @Test
    @Transactional
    void getPaymentReconciliation() throws Exception {
        // Initialize the database
        paymentReconciliationRepository.saveAndFlush(paymentReconciliation);

        // Get the paymentReconciliation
        restPaymentReconciliationMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentReconciliation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentReconciliation.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.system").value(DEFAULT_SYSTEM))
            .andExpect(jsonPath("$.parsed").value(DEFAULT_PARSED))
            .andExpect(jsonPath("$.periodStart").value(DEFAULT_PERIOD_START.toString()))
            .andExpect(jsonPath("$.periodEnd").value(DEFAULT_PERIOD_END.toString()))
            .andExpect(jsonPath("$.outcome").value(DEFAULT_OUTCOME))
            .andExpect(jsonPath("$.disposition").value(DEFAULT_DISPOSITION))
            .andExpect(jsonPath("$.paymentAmount").value(sameNumber(DEFAULT_PAYMENT_AMOUNT)))
            .andExpect(jsonPath("$.paymentIdentifier").value(DEFAULT_PAYMENT_IDENTIFIER));
    }

    @Test
    @Transactional
    void getNonExistingPaymentReconciliation() throws Exception {
        // Get the paymentReconciliation
        restPaymentReconciliationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentReconciliation() throws Exception {
        // Initialize the database
        paymentReconciliationRepository.saveAndFlush(paymentReconciliation);

        int databaseSizeBeforeUpdate = paymentReconciliationRepository.findAll().size();

        // Update the paymentReconciliation
        PaymentReconciliation updatedPaymentReconciliation = paymentReconciliationRepository.findById(paymentReconciliation.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentReconciliation are not directly saved in db
        em.detach(updatedPaymentReconciliation);
        updatedPaymentReconciliation
            .value(UPDATED_VALUE)
            .system(UPDATED_SYSTEM)
            .parsed(UPDATED_PARSED)
            .periodStart(UPDATED_PERIOD_START)
            .periodEnd(UPDATED_PERIOD_END)
            .outcome(UPDATED_OUTCOME)
            .disposition(UPDATED_DISPOSITION)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .paymentIdentifier(UPDATED_PAYMENT_IDENTIFIER);

        restPaymentReconciliationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentReconciliation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaymentReconciliation))
            )
            .andExpect(status().isOk());

        // Validate the PaymentReconciliation in the database
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertThat(paymentReconciliationList).hasSize(databaseSizeBeforeUpdate);
        PaymentReconciliation testPaymentReconciliation = paymentReconciliationList.get(paymentReconciliationList.size() - 1);
        assertThat(testPaymentReconciliation.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPaymentReconciliation.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testPaymentReconciliation.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testPaymentReconciliation.getPeriodStart()).isEqualTo(UPDATED_PERIOD_START);
        assertThat(testPaymentReconciliation.getPeriodEnd()).isEqualTo(UPDATED_PERIOD_END);
        assertThat(testPaymentReconciliation.getOutcome()).isEqualTo(UPDATED_OUTCOME);
        assertThat(testPaymentReconciliation.getDisposition()).isEqualTo(UPDATED_DISPOSITION);
        assertThat(testPaymentReconciliation.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaymentReconciliation.getPaymentIdentifier()).isEqualTo(UPDATED_PAYMENT_IDENTIFIER);
    }

    @Test
    @Transactional
    void putNonExistingPaymentReconciliation() throws Exception {
        int databaseSizeBeforeUpdate = paymentReconciliationRepository.findAll().size();
        paymentReconciliation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentReconciliationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentReconciliation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentReconciliation))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentReconciliation in the database
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertThat(paymentReconciliationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentReconciliation() throws Exception {
        int databaseSizeBeforeUpdate = paymentReconciliationRepository.findAll().size();
        paymentReconciliation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentReconciliationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentReconciliation))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentReconciliation in the database
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertThat(paymentReconciliationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentReconciliation() throws Exception {
        int databaseSizeBeforeUpdate = paymentReconciliationRepository.findAll().size();
        paymentReconciliation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentReconciliationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentReconciliation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentReconciliation in the database
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertThat(paymentReconciliationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentReconciliationWithPatch() throws Exception {
        // Initialize the database
        paymentReconciliationRepository.saveAndFlush(paymentReconciliation);

        int databaseSizeBeforeUpdate = paymentReconciliationRepository.findAll().size();

        // Update the paymentReconciliation using partial update
        PaymentReconciliation partialUpdatedPaymentReconciliation = new PaymentReconciliation();
        partialUpdatedPaymentReconciliation.setId(paymentReconciliation.getId());

        partialUpdatedPaymentReconciliation.parsed(UPDATED_PARSED);

        restPaymentReconciliationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentReconciliation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentReconciliation))
            )
            .andExpect(status().isOk());

        // Validate the PaymentReconciliation in the database
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertThat(paymentReconciliationList).hasSize(databaseSizeBeforeUpdate);
        PaymentReconciliation testPaymentReconciliation = paymentReconciliationList.get(paymentReconciliationList.size() - 1);
        assertThat(testPaymentReconciliation.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPaymentReconciliation.getSystem()).isEqualTo(DEFAULT_SYSTEM);
        assertThat(testPaymentReconciliation.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testPaymentReconciliation.getPeriodStart()).isEqualTo(DEFAULT_PERIOD_START);
        assertThat(testPaymentReconciliation.getPeriodEnd()).isEqualTo(DEFAULT_PERIOD_END);
        assertThat(testPaymentReconciliation.getOutcome()).isEqualTo(DEFAULT_OUTCOME);
        assertThat(testPaymentReconciliation.getDisposition()).isEqualTo(DEFAULT_DISPOSITION);
        assertThat(testPaymentReconciliation.getPaymentAmount()).isEqualByComparingTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testPaymentReconciliation.getPaymentIdentifier()).isEqualTo(DEFAULT_PAYMENT_IDENTIFIER);
    }

    @Test
    @Transactional
    void fullUpdatePaymentReconciliationWithPatch() throws Exception {
        // Initialize the database
        paymentReconciliationRepository.saveAndFlush(paymentReconciliation);

        int databaseSizeBeforeUpdate = paymentReconciliationRepository.findAll().size();

        // Update the paymentReconciliation using partial update
        PaymentReconciliation partialUpdatedPaymentReconciliation = new PaymentReconciliation();
        partialUpdatedPaymentReconciliation.setId(paymentReconciliation.getId());

        partialUpdatedPaymentReconciliation
            .value(UPDATED_VALUE)
            .system(UPDATED_SYSTEM)
            .parsed(UPDATED_PARSED)
            .periodStart(UPDATED_PERIOD_START)
            .periodEnd(UPDATED_PERIOD_END)
            .outcome(UPDATED_OUTCOME)
            .disposition(UPDATED_DISPOSITION)
            .paymentAmount(UPDATED_PAYMENT_AMOUNT)
            .paymentIdentifier(UPDATED_PAYMENT_IDENTIFIER);

        restPaymentReconciliationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentReconciliation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentReconciliation))
            )
            .andExpect(status().isOk());

        // Validate the PaymentReconciliation in the database
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertThat(paymentReconciliationList).hasSize(databaseSizeBeforeUpdate);
        PaymentReconciliation testPaymentReconciliation = paymentReconciliationList.get(paymentReconciliationList.size() - 1);
        assertThat(testPaymentReconciliation.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPaymentReconciliation.getSystem()).isEqualTo(UPDATED_SYSTEM);
        assertThat(testPaymentReconciliation.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testPaymentReconciliation.getPeriodStart()).isEqualTo(UPDATED_PERIOD_START);
        assertThat(testPaymentReconciliation.getPeriodEnd()).isEqualTo(UPDATED_PERIOD_END);
        assertThat(testPaymentReconciliation.getOutcome()).isEqualTo(UPDATED_OUTCOME);
        assertThat(testPaymentReconciliation.getDisposition()).isEqualTo(UPDATED_DISPOSITION);
        assertThat(testPaymentReconciliation.getPaymentAmount()).isEqualByComparingTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPaymentReconciliation.getPaymentIdentifier()).isEqualTo(UPDATED_PAYMENT_IDENTIFIER);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentReconciliation() throws Exception {
        int databaseSizeBeforeUpdate = paymentReconciliationRepository.findAll().size();
        paymentReconciliation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentReconciliationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentReconciliation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentReconciliation))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentReconciliation in the database
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertThat(paymentReconciliationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentReconciliation() throws Exception {
        int databaseSizeBeforeUpdate = paymentReconciliationRepository.findAll().size();
        paymentReconciliation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentReconciliationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentReconciliation))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentReconciliation in the database
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertThat(paymentReconciliationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentReconciliation() throws Exception {
        int databaseSizeBeforeUpdate = paymentReconciliationRepository.findAll().size();
        paymentReconciliation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentReconciliationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentReconciliation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentReconciliation in the database
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertThat(paymentReconciliationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentReconciliation() throws Exception {
        // Initialize the database
        paymentReconciliationRepository.saveAndFlush(paymentReconciliation);

        int databaseSizeBeforeDelete = paymentReconciliationRepository.findAll().size();

        // Delete the paymentReconciliation
        restPaymentReconciliationMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentReconciliation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertThat(paymentReconciliationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
