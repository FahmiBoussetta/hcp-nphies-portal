package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.SubDetailItem;
import com.platformsandsolutions.hcpnphiesportal.repository.SubDetailItemRepository;
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
 * Integration tests for the {@link SubDetailItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubDetailItemResourceIT {

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

    private static final String ENTITY_API_URL = "/api/sub-detail-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubDetailItemRepository subDetailItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubDetailItemMockMvc;

    private SubDetailItem subDetailItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubDetailItem createEntity(EntityManager em) {
        SubDetailItem subDetailItem = new SubDetailItem()
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
        return subDetailItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubDetailItem createUpdatedEntity(EntityManager em) {
        SubDetailItem subDetailItem = new SubDetailItem()
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
        return subDetailItem;
    }

    @BeforeEach
    public void initTest() {
        subDetailItem = createEntity(em);
    }

    @Test
    @Transactional
    void createSubDetailItem() throws Exception {
        int databaseSizeBeforeCreate = subDetailItemRepository.findAll().size();
        // Create the SubDetailItem
        restSubDetailItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subDetailItem)))
            .andExpect(status().isCreated());

        // Validate the SubDetailItem in the database
        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeCreate + 1);
        SubDetailItem testSubDetailItem = subDetailItemList.get(subDetailItemList.size() - 1);
        assertThat(testSubDetailItem.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testSubDetailItem.getTax()).isEqualByComparingTo(DEFAULT_TAX);
        assertThat(testSubDetailItem.getTransportationSRCA()).isEqualTo(DEFAULT_TRANSPORTATION_SRCA);
        assertThat(testSubDetailItem.getImaging()).isEqualTo(DEFAULT_IMAGING);
        assertThat(testSubDetailItem.getLaboratory()).isEqualTo(DEFAULT_LABORATORY);
        assertThat(testSubDetailItem.getMedicalDevice()).isEqualTo(DEFAULT_MEDICAL_DEVICE);
        assertThat(testSubDetailItem.getOralHealthIP()).isEqualTo(DEFAULT_ORAL_HEALTH_IP);
        assertThat(testSubDetailItem.getOralHealthOP()).isEqualTo(DEFAULT_ORAL_HEALTH_OP);
        assertThat(testSubDetailItem.getProcedure()).isEqualTo(DEFAULT_PROCEDURE);
        assertThat(testSubDetailItem.getServices()).isEqualTo(DEFAULT_SERVICES);
        assertThat(testSubDetailItem.getMedicationCode()).isEqualTo(DEFAULT_MEDICATION_CODE);
        assertThat(testSubDetailItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testSubDetailItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
    }

    @Test
    @Transactional
    void createSubDetailItemWithExistingId() throws Exception {
        // Create the SubDetailItem with an existing ID
        subDetailItem.setId(1L);

        int databaseSizeBeforeCreate = subDetailItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubDetailItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subDetailItem)))
            .andExpect(status().isBadRequest());

        // Validate the SubDetailItem in the database
        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = subDetailItemRepository.findAll().size();
        // set the field null
        subDetailItem.setSequence(null);

        // Create the SubDetailItem, which fails.

        restSubDetailItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subDetailItem)))
            .andExpect(status().isBadRequest());

        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = subDetailItemRepository.findAll().size();
        // set the field null
        subDetailItem.setQuantity(null);

        // Create the SubDetailItem, which fails.

        restSubDetailItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subDetailItem)))
            .andExpect(status().isBadRequest());

        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = subDetailItemRepository.findAll().size();
        // set the field null
        subDetailItem.setUnitPrice(null);

        // Create the SubDetailItem, which fails.

        restSubDetailItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subDetailItem)))
            .andExpect(status().isBadRequest());

        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSubDetailItems() throws Exception {
        // Initialize the database
        subDetailItemRepository.saveAndFlush(subDetailItem);

        // Get all the subDetailItemList
        restSubDetailItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subDetailItem.getId().intValue())))
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
    void getSubDetailItem() throws Exception {
        // Initialize the database
        subDetailItemRepository.saveAndFlush(subDetailItem);

        // Get the subDetailItem
        restSubDetailItemMockMvc
            .perform(get(ENTITY_API_URL_ID, subDetailItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subDetailItem.getId().intValue()))
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
    void getNonExistingSubDetailItem() throws Exception {
        // Get the subDetailItem
        restSubDetailItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubDetailItem() throws Exception {
        // Initialize the database
        subDetailItemRepository.saveAndFlush(subDetailItem);

        int databaseSizeBeforeUpdate = subDetailItemRepository.findAll().size();

        // Update the subDetailItem
        SubDetailItem updatedSubDetailItem = subDetailItemRepository.findById(subDetailItem.getId()).get();
        // Disconnect from session so that the updates on updatedSubDetailItem are not directly saved in db
        em.detach(updatedSubDetailItem);
        updatedSubDetailItem
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

        restSubDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubDetailItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSubDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the SubDetailItem in the database
        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeUpdate);
        SubDetailItem testSubDetailItem = subDetailItemList.get(subDetailItemList.size() - 1);
        assertThat(testSubDetailItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testSubDetailItem.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testSubDetailItem.getTransportationSRCA()).isEqualTo(UPDATED_TRANSPORTATION_SRCA);
        assertThat(testSubDetailItem.getImaging()).isEqualTo(UPDATED_IMAGING);
        assertThat(testSubDetailItem.getLaboratory()).isEqualTo(UPDATED_LABORATORY);
        assertThat(testSubDetailItem.getMedicalDevice()).isEqualTo(UPDATED_MEDICAL_DEVICE);
        assertThat(testSubDetailItem.getOralHealthIP()).isEqualTo(UPDATED_ORAL_HEALTH_IP);
        assertThat(testSubDetailItem.getOralHealthOP()).isEqualTo(UPDATED_ORAL_HEALTH_OP);
        assertThat(testSubDetailItem.getProcedure()).isEqualTo(UPDATED_PROCEDURE);
        assertThat(testSubDetailItem.getServices()).isEqualTo(UPDATED_SERVICES);
        assertThat(testSubDetailItem.getMedicationCode()).isEqualTo(UPDATED_MEDICATION_CODE);
        assertThat(testSubDetailItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSubDetailItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingSubDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = subDetailItemRepository.findAll().size();
        subDetailItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subDetailItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubDetailItem in the database
        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = subDetailItemRepository.findAll().size();
        subDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubDetailItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubDetailItem in the database
        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = subDetailItemRepository.findAll().size();
        subDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubDetailItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subDetailItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubDetailItem in the database
        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubDetailItemWithPatch() throws Exception {
        // Initialize the database
        subDetailItemRepository.saveAndFlush(subDetailItem);

        int databaseSizeBeforeUpdate = subDetailItemRepository.findAll().size();

        // Update the subDetailItem using partial update
        SubDetailItem partialUpdatedSubDetailItem = new SubDetailItem();
        partialUpdatedSubDetailItem.setId(subDetailItem.getId());

        partialUpdatedSubDetailItem
            .tax(UPDATED_TAX)
            .medicalDevice(UPDATED_MEDICAL_DEVICE)
            .oralHealthIP(UPDATED_ORAL_HEALTH_IP)
            .procedure(UPDATED_PROCEDURE)
            .unitPrice(UPDATED_UNIT_PRICE);

        restSubDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the SubDetailItem in the database
        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeUpdate);
        SubDetailItem testSubDetailItem = subDetailItemList.get(subDetailItemList.size() - 1);
        assertThat(testSubDetailItem.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testSubDetailItem.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testSubDetailItem.getTransportationSRCA()).isEqualTo(DEFAULT_TRANSPORTATION_SRCA);
        assertThat(testSubDetailItem.getImaging()).isEqualTo(DEFAULT_IMAGING);
        assertThat(testSubDetailItem.getLaboratory()).isEqualTo(DEFAULT_LABORATORY);
        assertThat(testSubDetailItem.getMedicalDevice()).isEqualTo(UPDATED_MEDICAL_DEVICE);
        assertThat(testSubDetailItem.getOralHealthIP()).isEqualTo(UPDATED_ORAL_HEALTH_IP);
        assertThat(testSubDetailItem.getOralHealthOP()).isEqualTo(DEFAULT_ORAL_HEALTH_OP);
        assertThat(testSubDetailItem.getProcedure()).isEqualTo(UPDATED_PROCEDURE);
        assertThat(testSubDetailItem.getServices()).isEqualTo(DEFAULT_SERVICES);
        assertThat(testSubDetailItem.getMedicationCode()).isEqualTo(DEFAULT_MEDICATION_CODE);
        assertThat(testSubDetailItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testSubDetailItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateSubDetailItemWithPatch() throws Exception {
        // Initialize the database
        subDetailItemRepository.saveAndFlush(subDetailItem);

        int databaseSizeBeforeUpdate = subDetailItemRepository.findAll().size();

        // Update the subDetailItem using partial update
        SubDetailItem partialUpdatedSubDetailItem = new SubDetailItem();
        partialUpdatedSubDetailItem.setId(subDetailItem.getId());

        partialUpdatedSubDetailItem
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

        restSubDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubDetailItem))
            )
            .andExpect(status().isOk());

        // Validate the SubDetailItem in the database
        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeUpdate);
        SubDetailItem testSubDetailItem = subDetailItemList.get(subDetailItemList.size() - 1);
        assertThat(testSubDetailItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testSubDetailItem.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testSubDetailItem.getTransportationSRCA()).isEqualTo(UPDATED_TRANSPORTATION_SRCA);
        assertThat(testSubDetailItem.getImaging()).isEqualTo(UPDATED_IMAGING);
        assertThat(testSubDetailItem.getLaboratory()).isEqualTo(UPDATED_LABORATORY);
        assertThat(testSubDetailItem.getMedicalDevice()).isEqualTo(UPDATED_MEDICAL_DEVICE);
        assertThat(testSubDetailItem.getOralHealthIP()).isEqualTo(UPDATED_ORAL_HEALTH_IP);
        assertThat(testSubDetailItem.getOralHealthOP()).isEqualTo(UPDATED_ORAL_HEALTH_OP);
        assertThat(testSubDetailItem.getProcedure()).isEqualTo(UPDATED_PROCEDURE);
        assertThat(testSubDetailItem.getServices()).isEqualTo(UPDATED_SERVICES);
        assertThat(testSubDetailItem.getMedicationCode()).isEqualTo(UPDATED_MEDICATION_CODE);
        assertThat(testSubDetailItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSubDetailItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingSubDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = subDetailItemRepository.findAll().size();
        subDetailItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subDetailItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubDetailItem in the database
        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = subDetailItemRepository.findAll().size();
        subDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subDetailItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubDetailItem in the database
        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubDetailItem() throws Exception {
        int databaseSizeBeforeUpdate = subDetailItemRepository.findAll().size();
        subDetailItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubDetailItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subDetailItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubDetailItem in the database
        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubDetailItem() throws Exception {
        // Initialize the database
        subDetailItemRepository.saveAndFlush(subDetailItem);

        int databaseSizeBeforeDelete = subDetailItemRepository.findAll().size();

        // Delete the subDetailItem
        restSubDetailItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, subDetailItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubDetailItem> subDetailItemList = subDetailItemRepository.findAll();
        assertThat(subDetailItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
