package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.DetailItem;
import com.platformsandsolutions.hcpnphiesportal.repository.DetailItemRepository;
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
 * Integration tests for the {@link DetailItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetailItemResourceIT {

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final BigDecimal DEFAULT_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX = new BigDecimal(2);

    private static final String DEFAULT_TRANSPORTATION_SRCA = "AAAAAAAAAA";
    private static final String UPDATED_TRANSPORTATION_SRCA = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGING = "AAAAAAAAAA";
    private static final String UPDATED_IMAGING = "BBBBBBBBBB";

    private static final String DEFAULT_LABORATORY = "AAAAAAAAAA";
    private static final String UPDATED_LABORATORY = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICAL_DEVICE = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAL_DEVICE = "BBBBBBBBBB";

    private static final String DEFAULT_ORAL_HEALTH_IP = "AAAAAAAAAA";
    private static final String UPDATED_ORAL_HEALTH_IP = "BBBBBBBBBB";

    private static final String DEFAULT_ORAL_HEALTH_OP = "AAAAAAAAAA";
    private static final String UPDATED_ORAL_HEALTH_OP = "BBBBBBBBBB";

    private static final String DEFAULT_PROCEDURE = "AAAAAAAAAA";
    private static final String UPDATED_PROCEDURE = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICES = "AAAAAAAAAA";
    private static final String UPDATED_SERVICES = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MEDICATION_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Integer DEFAULT_UNIT_PRICE = 1;
    private static final Integer UPDATED_UNIT_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/detail-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetailItemRepository detailItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetailItemMockMvc;

    private DetailItem detailItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailItem createEntity(EntityManager em) {
        DetailItem detailItem = new DetailItem()
            .sequence(DEFAULT_SEQUENCE)
            .tax(DEFAULT_TAX)
            .transportationSRCA(DEFAULT_TRANSPORTATION_SRCA)
            .imaging(DEFAULT_IMAGING)
            .laboratory(DEFAULT_LABORATORY)
            .medicalDevice(DEFAULT_MEDICAL_DEVICE)
            .oralHealthIP(DEFAULT_ORAL_HEALTH_IP)
            .oralHealthOP(DEFAULT_ORAL_HEALTH_OP)
            .procedure(DEFAULT_PROCEDURE)
            .services(DEFAULT_SERVICES)
            .medicationCode(DEFAULT_MEDICATION_CODE)
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE);
        return detailItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailItem createUpdatedEntity(EntityManager em) {
        DetailItem detailItem = new DetailItem()
            .sequence(UPDATED_SEQUENCE)
            .tax(UPDATED_TAX)
            .transportationSRCA(UPDATED_TRANSPORTATION_SRCA)
            .imaging(UPDATED_IMAGING)
            .laboratory(UPDATED_LABORATORY)
            .medicalDevice(UPDATED_MEDICAL_DEVICE)
            .oralHealthIP(UPDATED_ORAL_HEALTH_IP)
            .oralHealthOP(UPDATED_ORAL_HEALTH_OP)
            .procedure(UPDATED_PROCEDURE)
            .services(UPDATED_SERVICES)
            .medicationCode(UPDATED_MEDICATION_CODE)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE);
        return detailItem;
    }

    @BeforeEach
    public void initTest() {
        detailItem = createEntity(em);
    }

    @Test
    @Transactional
    void createDetailItem() throws Exception {
        int databaseSizeBeforeCreate = detailItemRepository.findAll().size();
        // Create the DetailItem
        restDetailItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailItem)))
            .andExpect(status().isCreated());

        // Validate the DetailItem in the database
        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeCreate + 1);
        DetailItem testDetailItem = detailItemList.get(detailItemList.size() - 1);
        assertThat(testDetailItem.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testDetailItem.getTax()).isEqualByComparingTo(DEFAULT_TAX);
        assertThat(testDetailItem.getTransportationSRCA()).isEqualTo(DEFAULT_TRANSPORTATION_SRCA);
        assertThat(testDetailItem.getImaging()).isEqualTo(DEFAULT_IMAGING);
        assertThat(testDetailItem.getLaboratory()).isEqualTo(DEFAULT_LABORATORY);
        assertThat(testDetailItem.getMedicalDevice()).isEqualTo(DEFAULT_MEDICAL_DEVICE);
        assertThat(testDetailItem.getOralHealthIP()).isEqualTo(DEFAULT_ORAL_HEALTH_IP);
        assertThat(testDetailItem.getOralHealthOP()).isEqualTo(DEFAULT_ORAL_HEALTH_OP);
        assertThat(testDetailItem.getProcedure()).isEqualTo(DEFAULT_PROCEDURE);
        assertThat(testDetailItem.getServices()).isEqualTo(DEFAULT_SERVICES);
        assertThat(testDetailItem.getMedicationCode()).isEqualTo(DEFAULT_MEDICATION_CODE);
        assertThat(testDetailItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDetailItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
    }

    @Test
    @Transactional
    void createDetailItemWithExistingId() throws Exception {
        // Create the DetailItem with an existing ID
        detailItem.setId(1L);

        int databaseSizeBeforeCreate = detailItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailItem)))
            .andExpect(status().isBadRequest());

        // Validate the DetailItem in the database
        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailItemRepository.findAll().size();
        // set the field null
        detailItem.setSequence(null);

        // Create the DetailItem, which fails.

        restDetailItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailItem)))
            .andExpect(status().isBadRequest());

        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailItemRepository.findAll().size();
        // set the field null
        detailItem.setQuantity(null);

        // Create the DetailItem, which fails.

        restDetailItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailItem)))
            .andExpect(status().isBadRequest());

        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailItemRepository.findAll().size();
        // set the field null
        detailItem.setUnitPrice(null);

        // Create the DetailItem, which fails.

        restDetailItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailItem)))
            .andExpect(status().isBadRequest());

        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDetailItems() throws Exception {
        // Initialize the database
        detailItemRepository.saveAndFlush(detailItem);

        // Get all the detailItemList
        restDetailItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(sameNumber(DEFAULT_TAX))))
            .andExpect(jsonPath("$.[*].transportationSRCA").value(hasItem(DEFAULT_TRANSPORTATION_SRCA)))
            .andExpect(jsonPath("$.[*].imaging").value(hasItem(DEFAULT_IMAGING)))
            .andExpect(jsonPath("$.[*].laboratory").value(hasItem(DEFAULT_LABORATORY)))
            .andExpect(jsonPath("$.[*].medicalDevice").value(hasItem(DEFAULT_MEDICAL_DEVICE)))
            .andExpect(jsonPath("$.[*].oralHealthIP").value(hasItem(DEFAULT_ORAL_HEALTH_IP)))
            .andExpect(jsonPath("$.[*].oralHealthOP").value(hasItem(DEFAULT_ORAL_HEALTH_OP)))
            .andExpect(jsonPath("$.[*].procedure").value(hasItem(DEFAULT_PROCEDURE)))
            .andExpect(jsonPath("$.[*].services").value(hasItem(DEFAULT_SERVICES)))
            .andExpect(jsonPath("$.[*].medicationCode").value(hasItem(DEFAULT_MEDICATION_CODE)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE)));
    }

    @Test
    @Transactional
    void getDetailItem() throws Exception {
        // Initialize the database
        detailItemRepository.saveAndFlush(detailItem);

        // Get the detailItem
        restDetailItemMockMvc
            .perform(get(ENTITY_API_URL_ID, detailItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detailItem.getId().intValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.tax").value(sameNumber(DEFAULT_TAX)))
            .andExpect(jsonPath("$.transportationSRCA").value(DEFAULT_TRANSPORTATION_SRCA))
            .andExpect(jsonPath("$.imaging").value(DEFAULT_IMAGING))
            .andExpect(jsonPath("$.laboratory").value(DEFAULT_LABORATORY))
            .andExpect(jsonPath("$.medicalDevice").value(DEFAULT_MEDICAL_DEVICE))
            .andExpect(jsonPath("$.oralHealthIP").value(DEFAULT_ORAL_HEALTH_IP))
            .andExpect(jsonPath("$.oralHealthOP").value(DEFAULT_ORAL_HEALTH_OP))
            .andExpect(jsonPath("$.procedure").value(DEFAULT_PROCEDURE))
            .andExpect(jsonPath("$.services").value(DEFAULT_SERVICES))
            .andExpect(jsonPath("$.medicationCode").value(DEFAULT_MEDICATION_CODE))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingDetailItem() throws Exception {
        // Get the detailItem
        restDetailItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDetailItem() throws Exception {
        // Initialize the database
        detailItemRepository.saveAndFlush(detailItem);

        int databaseSizeBeforeUpdate = detailItemRepository.findAll().size();

        // Update the detailItem
        DetailItem updatedDetailItem = detailItemRepository.findById(detailItem.getId()).get();
        // Disconnect from session so that the updates on updatedDetailItem are not directly saved in db
        em.detach(updatedDetailItem);
        updatedDetailItem
            .sequence(UPDATED_SEQUENCE)
            .tax(UPDATED_TAX)
            .transportationSRCA(UPDATED_TRANSPORTATION_SRCA)
            .imaging(UPDATED_IMAGING)
            .laboratory(UPDATED_LABORATORY)
            .medicalDevice(UPDATED_MEDICAL_DEVICE)
            .oralHealthIP(UPDATED_ORAL_HEALTH_IP)
            .oralHealthOP(UPDATED_ORAL_HEALTH_OP)
            .procedure(UPDATED_PROCEDURE)
            .services(UPDATED_SERVICES)
            .medicationCode(UPDATED_MEDICATION_CODE)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE);

        restDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDetailItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the DetailItem in the database
        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeUpdate);
        DetailItem testDetailItem = detailItemList.get(detailItemList.size() - 1);
        assertThat(testDetailItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testDetailItem.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testDetailItem.getTransportationSRCA()).isEqualTo(UPDATED_TRANSPORTATION_SRCA);
        assertThat(testDetailItem.getImaging()).isEqualTo(UPDATED_IMAGING);
        assertThat(testDetailItem.getLaboratory()).isEqualTo(UPDATED_LABORATORY);
        assertThat(testDetailItem.getMedicalDevice()).isEqualTo(UPDATED_MEDICAL_DEVICE);
        assertThat(testDetailItem.getOralHealthIP()).isEqualTo(UPDATED_ORAL_HEALTH_IP);
        assertThat(testDetailItem.getOralHealthOP()).isEqualTo(UPDATED_ORAL_HEALTH_OP);
        assertThat(testDetailItem.getProcedure()).isEqualTo(UPDATED_PROCEDURE);
        assertThat(testDetailItem.getServices()).isEqualTo(UPDATED_SERVICES);
        assertThat(testDetailItem.getMedicationCode()).isEqualTo(UPDATED_MEDICATION_CODE);
        assertThat(testDetailItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDetailItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = detailItemRepository.findAll().size();
        detailItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detailItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailItem in the database
        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = detailItemRepository.findAll().size();
        detailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailItem in the database
        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = detailItemRepository.findAll().size();
        detailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailItem in the database
        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetailItemWithPatch() throws Exception {
        // Initialize the database
        detailItemRepository.saveAndFlush(detailItem);

        int databaseSizeBeforeUpdate = detailItemRepository.findAll().size();

        // Update the detailItem using partial update
        DetailItem partialUpdatedDetailItem = new DetailItem();
        partialUpdatedDetailItem.setId(detailItem.getId());

        partialUpdatedDetailItem
            .tax(UPDATED_TAX)
            .transportationSRCA(UPDATED_TRANSPORTATION_SRCA)
            .laboratory(UPDATED_LABORATORY)
            .medicalDevice(UPDATED_MEDICAL_DEVICE)
            .oralHealthIP(UPDATED_ORAL_HEALTH_IP)
            .oralHealthOP(UPDATED_ORAL_HEALTH_OP)
            .procedure(UPDATED_PROCEDURE)
            .quantity(UPDATED_QUANTITY);

        restDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the DetailItem in the database
        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeUpdate);
        DetailItem testDetailItem = detailItemList.get(detailItemList.size() - 1);
        assertThat(testDetailItem.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testDetailItem.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testDetailItem.getTransportationSRCA()).isEqualTo(UPDATED_TRANSPORTATION_SRCA);
        assertThat(testDetailItem.getImaging()).isEqualTo(DEFAULT_IMAGING);
        assertThat(testDetailItem.getLaboratory()).isEqualTo(UPDATED_LABORATORY);
        assertThat(testDetailItem.getMedicalDevice()).isEqualTo(UPDATED_MEDICAL_DEVICE);
        assertThat(testDetailItem.getOralHealthIP()).isEqualTo(UPDATED_ORAL_HEALTH_IP);
        assertThat(testDetailItem.getOralHealthOP()).isEqualTo(UPDATED_ORAL_HEALTH_OP);
        assertThat(testDetailItem.getProcedure()).isEqualTo(UPDATED_PROCEDURE);
        assertThat(testDetailItem.getServices()).isEqualTo(DEFAULT_SERVICES);
        assertThat(testDetailItem.getMedicationCode()).isEqualTo(DEFAULT_MEDICATION_CODE);
        assertThat(testDetailItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDetailItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateDetailItemWithPatch() throws Exception {
        // Initialize the database
        detailItemRepository.saveAndFlush(detailItem);

        int databaseSizeBeforeUpdate = detailItemRepository.findAll().size();

        // Update the detailItem using partial update
        DetailItem partialUpdatedDetailItem = new DetailItem();
        partialUpdatedDetailItem.setId(detailItem.getId());

        partialUpdatedDetailItem
            .sequence(UPDATED_SEQUENCE)
            .tax(UPDATED_TAX)
            .transportationSRCA(UPDATED_TRANSPORTATION_SRCA)
            .imaging(UPDATED_IMAGING)
            .laboratory(UPDATED_LABORATORY)
            .medicalDevice(UPDATED_MEDICAL_DEVICE)
            .oralHealthIP(UPDATED_ORAL_HEALTH_IP)
            .oralHealthOP(UPDATED_ORAL_HEALTH_OP)
            .procedure(UPDATED_PROCEDURE)
            .services(UPDATED_SERVICES)
            .medicationCode(UPDATED_MEDICATION_CODE)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE);

        restDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the DetailItem in the database
        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeUpdate);
        DetailItem testDetailItem = detailItemList.get(detailItemList.size() - 1);
        assertThat(testDetailItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testDetailItem.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testDetailItem.getTransportationSRCA()).isEqualTo(UPDATED_TRANSPORTATION_SRCA);
        assertThat(testDetailItem.getImaging()).isEqualTo(UPDATED_IMAGING);
        assertThat(testDetailItem.getLaboratory()).isEqualTo(UPDATED_LABORATORY);
        assertThat(testDetailItem.getMedicalDevice()).isEqualTo(UPDATED_MEDICAL_DEVICE);
        assertThat(testDetailItem.getOralHealthIP()).isEqualTo(UPDATED_ORAL_HEALTH_IP);
        assertThat(testDetailItem.getOralHealthOP()).isEqualTo(UPDATED_ORAL_HEALTH_OP);
        assertThat(testDetailItem.getProcedure()).isEqualTo(UPDATED_PROCEDURE);
        assertThat(testDetailItem.getServices()).isEqualTo(UPDATED_SERVICES);
        assertThat(testDetailItem.getMedicationCode()).isEqualTo(UPDATED_MEDICATION_CODE);
        assertThat(testDetailItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDetailItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = detailItemRepository.findAll().size();
        detailItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailItem in the database
        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = detailItemRepository.findAll().size();
        detailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailItem in the database
        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = detailItemRepository.findAll().size();
        detailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(detailItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailItem in the database
        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetailItem() throws Exception {
        // Initialize the database
        detailItemRepository.saveAndFlush(detailItem);

        int databaseSizeBeforeDelete = detailItemRepository.findAll().size();

        // Delete the detailItem
        restDetailItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, detailItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailItem> detailItemList = detailItemRepository.findAll();
        assertThat(detailItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
