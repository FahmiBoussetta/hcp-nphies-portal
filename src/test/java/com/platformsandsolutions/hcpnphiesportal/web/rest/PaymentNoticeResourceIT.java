package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.PaymentNotice;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.PaymentStatusEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.PaymentNoticeRepository;
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
 * Integration tests for the {@link PaymentNoticeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentNoticeResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_PARSED = "AAAAAAAAAA";
    private static final String UPDATED_PARSED = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final Instant DEFAULT_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final PaymentStatusEnum DEFAULT_PAYMENT_STATUS = PaymentStatusEnum.Paid;
    private static final PaymentStatusEnum UPDATED_PAYMENT_STATUS = PaymentStatusEnum.Cleared;

    private static final String ENTITY_API_URL = "/api/payment-notices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentNoticeRepository paymentNoticeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentNoticeMockMvc;

    private PaymentNotice paymentNotice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentNotice createEntity(EntityManager em) {
        PaymentNotice paymentNotice = new PaymentNotice()
            .guid(DEFAULT_GUID)
            .parsed(DEFAULT_PARSED)
            .identifier(DEFAULT_IDENTIFIER)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .amount(DEFAULT_AMOUNT)
            .paymentStatus(DEFAULT_PAYMENT_STATUS);
        return paymentNotice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentNotice createUpdatedEntity(EntityManager em) {
        PaymentNotice paymentNotice = new PaymentNotice()
            .guid(UPDATED_GUID)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .amount(UPDATED_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS);
        return paymentNotice;
    }

    @BeforeEach
    public void initTest() {
        paymentNotice = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentNotice() throws Exception {
        int databaseSizeBeforeCreate = paymentNoticeRepository.findAll().size();
        // Create the PaymentNotice
        restPaymentNoticeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentNotice)))
            .andExpect(status().isCreated());

        // Validate the PaymentNotice in the database
        List<PaymentNotice> paymentNoticeList = paymentNoticeRepository.findAll();
        assertThat(paymentNoticeList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentNotice testPaymentNotice = paymentNoticeList.get(paymentNoticeList.size() - 1);
        assertThat(testPaymentNotice.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testPaymentNotice.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testPaymentNotice.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testPaymentNotice.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testPaymentNotice.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testPaymentNotice.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void createPaymentNoticeWithExistingId() throws Exception {
        // Create the PaymentNotice with an existing ID
        paymentNotice.setId(1L);

        int databaseSizeBeforeCreate = paymentNoticeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentNoticeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentNotice)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentNotice in the database
        List<PaymentNotice> paymentNoticeList = paymentNoticeRepository.findAll();
        assertThat(paymentNoticeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentNotices() throws Exception {
        // Initialize the database
        paymentNoticeRepository.saveAndFlush(paymentNotice);

        // Get all the paymentNoticeList
        restPaymentNoticeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentNotice.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].parsed").value(hasItem(DEFAULT_PARSED)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(sameNumber(DEFAULT_AMOUNT))))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getPaymentNotice() throws Exception {
        // Initialize the database
        paymentNoticeRepository.saveAndFlush(paymentNotice);

        // Get the paymentNotice
        restPaymentNoticeMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentNotice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentNotice.getId().intValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.parsed").value(DEFAULT_PARSED))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(sameNumber(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentNotice() throws Exception {
        // Get the paymentNotice
        restPaymentNoticeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPaymentNotice() throws Exception {
        // Initialize the database
        paymentNoticeRepository.saveAndFlush(paymentNotice);

        int databaseSizeBeforeUpdate = paymentNoticeRepository.findAll().size();

        // Update the paymentNotice
        PaymentNotice updatedPaymentNotice = paymentNoticeRepository.findById(paymentNotice.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentNotice are not directly saved in db
        em.detach(updatedPaymentNotice);
        updatedPaymentNotice
            .guid(UPDATED_GUID)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .amount(UPDATED_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restPaymentNoticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPaymentNotice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPaymentNotice))
            )
            .andExpect(status().isOk());

        // Validate the PaymentNotice in the database
        List<PaymentNotice> paymentNoticeList = paymentNoticeRepository.findAll();
        assertThat(paymentNoticeList).hasSize(databaseSizeBeforeUpdate);
        PaymentNotice testPaymentNotice = paymentNoticeList.get(paymentNoticeList.size() - 1);
        assertThat(testPaymentNotice.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPaymentNotice.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testPaymentNotice.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testPaymentNotice.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPaymentNotice.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPaymentNotice.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingPaymentNotice() throws Exception {
        int databaseSizeBeforeUpdate = paymentNoticeRepository.findAll().size();
        paymentNotice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentNoticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentNotice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentNotice))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentNotice in the database
        List<PaymentNotice> paymentNoticeList = paymentNoticeRepository.findAll();
        assertThat(paymentNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentNotice() throws Exception {
        int databaseSizeBeforeUpdate = paymentNoticeRepository.findAll().size();
        paymentNotice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentNoticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentNotice))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentNotice in the database
        List<PaymentNotice> paymentNoticeList = paymentNoticeRepository.findAll();
        assertThat(paymentNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentNotice() throws Exception {
        int databaseSizeBeforeUpdate = paymentNoticeRepository.findAll().size();
        paymentNotice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentNoticeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentNotice)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentNotice in the database
        List<PaymentNotice> paymentNoticeList = paymentNoticeRepository.findAll();
        assertThat(paymentNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentNoticeWithPatch() throws Exception {
        // Initialize the database
        paymentNoticeRepository.saveAndFlush(paymentNotice);

        int databaseSizeBeforeUpdate = paymentNoticeRepository.findAll().size();

        // Update the paymentNotice using partial update
        PaymentNotice partialUpdatedPaymentNotice = new PaymentNotice();
        partialUpdatedPaymentNotice.setId(paymentNotice.getId());

        partialUpdatedPaymentNotice.guid(UPDATED_GUID).paymentDate(UPDATED_PAYMENT_DATE).paymentStatus(UPDATED_PAYMENT_STATUS);

        restPaymentNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentNotice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentNotice))
            )
            .andExpect(status().isOk());

        // Validate the PaymentNotice in the database
        List<PaymentNotice> paymentNoticeList = paymentNoticeRepository.findAll();
        assertThat(paymentNoticeList).hasSize(databaseSizeBeforeUpdate);
        PaymentNotice testPaymentNotice = paymentNoticeList.get(paymentNoticeList.size() - 1);
        assertThat(testPaymentNotice.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPaymentNotice.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testPaymentNotice.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testPaymentNotice.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPaymentNotice.getAmount()).isEqualByComparingTo(DEFAULT_AMOUNT);
        assertThat(testPaymentNotice.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdatePaymentNoticeWithPatch() throws Exception {
        // Initialize the database
        paymentNoticeRepository.saveAndFlush(paymentNotice);

        int databaseSizeBeforeUpdate = paymentNoticeRepository.findAll().size();

        // Update the paymentNotice using partial update
        PaymentNotice partialUpdatedPaymentNotice = new PaymentNotice();
        partialUpdatedPaymentNotice.setId(paymentNotice.getId());

        partialUpdatedPaymentNotice
            .guid(UPDATED_GUID)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .amount(UPDATED_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS);

        restPaymentNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentNotice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentNotice))
            )
            .andExpect(status().isOk());

        // Validate the PaymentNotice in the database
        List<PaymentNotice> paymentNoticeList = paymentNoticeRepository.findAll();
        assertThat(paymentNoticeList).hasSize(databaseSizeBeforeUpdate);
        PaymentNotice testPaymentNotice = paymentNoticeList.get(paymentNoticeList.size() - 1);
        assertThat(testPaymentNotice.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPaymentNotice.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testPaymentNotice.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testPaymentNotice.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPaymentNotice.getAmount()).isEqualByComparingTo(UPDATED_AMOUNT);
        assertThat(testPaymentNotice.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentNotice() throws Exception {
        int databaseSizeBeforeUpdate = paymentNoticeRepository.findAll().size();
        paymentNotice.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentNotice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentNotice))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentNotice in the database
        List<PaymentNotice> paymentNoticeList = paymentNoticeRepository.findAll();
        assertThat(paymentNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentNotice() throws Exception {
        int databaseSizeBeforeUpdate = paymentNoticeRepository.findAll().size();
        paymentNotice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentNotice))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentNotice in the database
        List<PaymentNotice> paymentNoticeList = paymentNoticeRepository.findAll();
        assertThat(paymentNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentNotice() throws Exception {
        int databaseSizeBeforeUpdate = paymentNoticeRepository.findAll().size();
        paymentNotice.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentNotice))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentNotice in the database
        List<PaymentNotice> paymentNoticeList = paymentNoticeRepository.findAll();
        assertThat(paymentNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentNotice() throws Exception {
        // Initialize the database
        paymentNoticeRepository.saveAndFlush(paymentNotice);

        int databaseSizeBeforeDelete = paymentNoticeRepository.findAll().size();

        // Delete the paymentNotice
        restPaymentNoticeMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentNotice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentNotice> paymentNoticeList = paymentNoticeRepository.findAll();
        assertThat(paymentNoticeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
