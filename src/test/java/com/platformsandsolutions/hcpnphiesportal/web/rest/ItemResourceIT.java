package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Item;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.BodySiteEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SubSiteEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ItemRepository;
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
 * Integration tests for the {@link ItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ItemResourceIT {

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final Boolean DEFAULT_IS_PACKAGE = false;
    private static final Boolean UPDATED_IS_PACKAGE = true;

    private static final BigDecimal DEFAULT_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAYER_SHARE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYER_SHARE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PATIENT_SHARE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PATIENT_SHARE = new BigDecimal(2);

    private static final Integer DEFAULT_CARE_TEAM_SEQUENCE = 1;
    private static final Integer UPDATED_CARE_TEAM_SEQUENCE = 2;

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

    private static final Instant DEFAULT_SERVICED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SERVICED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SERVICED_DATE_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SERVICED_DATE_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SERVICED_DATE_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SERVICED_DATE_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Integer DEFAULT_UNIT_PRICE = 1;
    private static final Integer UPDATED_UNIT_PRICE = 2;

    private static final BigDecimal DEFAULT_FACTOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_FACTOR = new BigDecimal(2);

    private static final BodySiteEnum DEFAULT_BODY_SITE = BodySiteEnum.E1;
    private static final BodySiteEnum UPDATED_BODY_SITE = BodySiteEnum.E2;

    private static final SubSiteEnum DEFAULT_SUB_SITE = SubSiteEnum.R;
    private static final SubSiteEnum UPDATED_SUB_SITE = SubSiteEnum.L;

    private static final String ENTITY_API_URL = "/api/items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemMockMvc;

    private Item item;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Item createEntity(EntityManager em) {
        Item item = new Item()
            .sequence(DEFAULT_SEQUENCE)
            .isPackage(DEFAULT_IS_PACKAGE)
            .tax(DEFAULT_TAX)
            .payerShare(DEFAULT_PAYER_SHARE)
            .patientShare(DEFAULT_PATIENT_SHARE)
            .careTeamSequence(DEFAULT_CARE_TEAM_SEQUENCE)
            .transportationSRCA(DEFAULT_TRANSPORTATION_SRCA)
            .imaging(DEFAULT_IMAGING)
            .laboratory(DEFAULT_LABORATORY)
            .medicalDevice(DEFAULT_MEDICAL_DEVICE)
            .oralHealthIP(DEFAULT_ORAL_HEALTH_IP)
            .oralHealthOP(DEFAULT_ORAL_HEALTH_OP)
            .procedure(DEFAULT_PROCEDURE)
            .services(DEFAULT_SERVICES)
            .medicationCode(DEFAULT_MEDICATION_CODE)
            .servicedDate(DEFAULT_SERVICED_DATE)
            .servicedDateStart(DEFAULT_SERVICED_DATE_START)
            .servicedDateEnd(DEFAULT_SERVICED_DATE_END)
            .quantity(DEFAULT_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .factor(DEFAULT_FACTOR)
            .bodySite(DEFAULT_BODY_SITE)
            .subSite(DEFAULT_SUB_SITE);
        return item;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Item createUpdatedEntity(EntityManager em) {
        Item item = new Item()
            .sequence(UPDATED_SEQUENCE)
            .isPackage(UPDATED_IS_PACKAGE)
            .tax(UPDATED_TAX)
            .payerShare(UPDATED_PAYER_SHARE)
            .patientShare(UPDATED_PATIENT_SHARE)
            .careTeamSequence(UPDATED_CARE_TEAM_SEQUENCE)
            .transportationSRCA(UPDATED_TRANSPORTATION_SRCA)
            .imaging(UPDATED_IMAGING)
            .laboratory(UPDATED_LABORATORY)
            .medicalDevice(UPDATED_MEDICAL_DEVICE)
            .oralHealthIP(UPDATED_ORAL_HEALTH_IP)
            .oralHealthOP(UPDATED_ORAL_HEALTH_OP)
            .procedure(UPDATED_PROCEDURE)
            .services(UPDATED_SERVICES)
            .medicationCode(UPDATED_MEDICATION_CODE)
            .servicedDate(UPDATED_SERVICED_DATE)
            .servicedDateStart(UPDATED_SERVICED_DATE_START)
            .servicedDateEnd(UPDATED_SERVICED_DATE_END)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .factor(UPDATED_FACTOR)
            .bodySite(UPDATED_BODY_SITE)
            .subSite(UPDATED_SUB_SITE);
        return item;
    }

    @BeforeEach
    public void initTest() {
        item = createEntity(em);
    }

    @Test
    @Transactional
    void createItem() throws Exception {
        int databaseSizeBeforeCreate = itemRepository.findAll().size();
        // Create the Item
        restItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isCreated());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeCreate + 1);
        Item testItem = itemList.get(itemList.size() - 1);
        assertThat(testItem.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testItem.getIsPackage()).isEqualTo(DEFAULT_IS_PACKAGE);
        assertThat(testItem.getTax()).isEqualByComparingTo(DEFAULT_TAX);
        assertThat(testItem.getPayerShare()).isEqualByComparingTo(DEFAULT_PAYER_SHARE);
        assertThat(testItem.getPatientShare()).isEqualByComparingTo(DEFAULT_PATIENT_SHARE);
        assertThat(testItem.getCareTeamSequence()).isEqualTo(DEFAULT_CARE_TEAM_SEQUENCE);
        assertThat(testItem.getTransportationSRCA()).isEqualTo(DEFAULT_TRANSPORTATION_SRCA);
        assertThat(testItem.getImaging()).isEqualTo(DEFAULT_IMAGING);
        assertThat(testItem.getLaboratory()).isEqualTo(DEFAULT_LABORATORY);
        assertThat(testItem.getMedicalDevice()).isEqualTo(DEFAULT_MEDICAL_DEVICE);
        assertThat(testItem.getOralHealthIP()).isEqualTo(DEFAULT_ORAL_HEALTH_IP);
        assertThat(testItem.getOralHealthOP()).isEqualTo(DEFAULT_ORAL_HEALTH_OP);
        assertThat(testItem.getProcedure()).isEqualTo(DEFAULT_PROCEDURE);
        assertThat(testItem.getServices()).isEqualTo(DEFAULT_SERVICES);
        assertThat(testItem.getMedicationCode()).isEqualTo(DEFAULT_MEDICATION_CODE);
        assertThat(testItem.getServicedDate()).isEqualTo(DEFAULT_SERVICED_DATE);
        assertThat(testItem.getServicedDateStart()).isEqualTo(DEFAULT_SERVICED_DATE_START);
        assertThat(testItem.getServicedDateEnd()).isEqualTo(DEFAULT_SERVICED_DATE_END);
        assertThat(testItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testItem.getFactor()).isEqualByComparingTo(DEFAULT_FACTOR);
        assertThat(testItem.getBodySite()).isEqualTo(DEFAULT_BODY_SITE);
        assertThat(testItem.getSubSite()).isEqualTo(DEFAULT_SUB_SITE);
    }

    @Test
    @Transactional
    void createItemWithExistingId() throws Exception {
        // Create the Item with an existing ID
        item.setId(1L);

        int databaseSizeBeforeCreate = itemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isBadRequest());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setSequence(null);

        // Create the Item, which fails.

        restItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPatientShareIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setPatientShare(null);

        // Create the Item, which fails.

        restItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCareTeamSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setCareTeamSequence(null);

        // Create the Item, which fails.

        restItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setQuantity(null);

        // Create the Item, which fails.

        restItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnitPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemRepository.findAll().size();
        // set the field null
        item.setUnitPrice(null);

        // Create the Item, which fails.

        restItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isBadRequest());

        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllItems() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get all the itemList
        restItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(item.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].isPackage").value(hasItem(DEFAULT_IS_PACKAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(sameNumber(DEFAULT_TAX))))
            .andExpect(jsonPath("$.[*].payerShare").value(hasItem(sameNumber(DEFAULT_PAYER_SHARE))))
            .andExpect(jsonPath("$.[*].patientShare").value(hasItem(sameNumber(DEFAULT_PATIENT_SHARE))))
            .andExpect(jsonPath("$.[*].careTeamSequence").value(hasItem(DEFAULT_CARE_TEAM_SEQUENCE)))
            .andExpect(jsonPath("$.[*].transportationSRCA").value(hasItem(DEFAULT_TRANSPORTATION_SRCA)))
            .andExpect(jsonPath("$.[*].imaging").value(hasItem(DEFAULT_IMAGING)))
            .andExpect(jsonPath("$.[*].laboratory").value(hasItem(DEFAULT_LABORATORY)))
            .andExpect(jsonPath("$.[*].medicalDevice").value(hasItem(DEFAULT_MEDICAL_DEVICE)))
            .andExpect(jsonPath("$.[*].oralHealthIP").value(hasItem(DEFAULT_ORAL_HEALTH_IP)))
            .andExpect(jsonPath("$.[*].oralHealthOP").value(hasItem(DEFAULT_ORAL_HEALTH_OP)))
            .andExpect(jsonPath("$.[*].procedure").value(hasItem(DEFAULT_PROCEDURE)))
            .andExpect(jsonPath("$.[*].services").value(hasItem(DEFAULT_SERVICES)))
            .andExpect(jsonPath("$.[*].medicationCode").value(hasItem(DEFAULT_MEDICATION_CODE)))
            .andExpect(jsonPath("$.[*].servicedDate").value(hasItem(DEFAULT_SERVICED_DATE.toString())))
            .andExpect(jsonPath("$.[*].servicedDateStart").value(hasItem(DEFAULT_SERVICED_DATE_START.toString())))
            .andExpect(jsonPath("$.[*].servicedDateEnd").value(hasItem(DEFAULT_SERVICED_DATE_END.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE)))
            .andExpect(jsonPath("$.[*].factor").value(hasItem(sameNumber(DEFAULT_FACTOR))))
            .andExpect(jsonPath("$.[*].bodySite").value(hasItem(DEFAULT_BODY_SITE.toString())))
            .andExpect(jsonPath("$.[*].subSite").value(hasItem(DEFAULT_SUB_SITE.toString())));
    }

    @Test
    @Transactional
    void getItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        // Get the item
        restItemMockMvc
            .perform(get(ENTITY_API_URL_ID, item.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(item.getId().intValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.isPackage").value(DEFAULT_IS_PACKAGE.booleanValue()))
            .andExpect(jsonPath("$.tax").value(sameNumber(DEFAULT_TAX)))
            .andExpect(jsonPath("$.payerShare").value(sameNumber(DEFAULT_PAYER_SHARE)))
            .andExpect(jsonPath("$.patientShare").value(sameNumber(DEFAULT_PATIENT_SHARE)))
            .andExpect(jsonPath("$.careTeamSequence").value(DEFAULT_CARE_TEAM_SEQUENCE))
            .andExpect(jsonPath("$.transportationSRCA").value(DEFAULT_TRANSPORTATION_SRCA))
            .andExpect(jsonPath("$.imaging").value(DEFAULT_IMAGING))
            .andExpect(jsonPath("$.laboratory").value(DEFAULT_LABORATORY))
            .andExpect(jsonPath("$.medicalDevice").value(DEFAULT_MEDICAL_DEVICE))
            .andExpect(jsonPath("$.oralHealthIP").value(DEFAULT_ORAL_HEALTH_IP))
            .andExpect(jsonPath("$.oralHealthOP").value(DEFAULT_ORAL_HEALTH_OP))
            .andExpect(jsonPath("$.procedure").value(DEFAULT_PROCEDURE))
            .andExpect(jsonPath("$.services").value(DEFAULT_SERVICES))
            .andExpect(jsonPath("$.medicationCode").value(DEFAULT_MEDICATION_CODE))
            .andExpect(jsonPath("$.servicedDate").value(DEFAULT_SERVICED_DATE.toString()))
            .andExpect(jsonPath("$.servicedDateStart").value(DEFAULT_SERVICED_DATE_START.toString()))
            .andExpect(jsonPath("$.servicedDateEnd").value(DEFAULT_SERVICED_DATE_END.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE))
            .andExpect(jsonPath("$.factor").value(sameNumber(DEFAULT_FACTOR)))
            .andExpect(jsonPath("$.bodySite").value(DEFAULT_BODY_SITE.toString()))
            .andExpect(jsonPath("$.subSite").value(DEFAULT_SUB_SITE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingItem() throws Exception {
        // Get the item
        restItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Update the item
        Item updatedItem = itemRepository.findById(item.getId()).get();
        // Disconnect from session so that the updates on updatedItem are not directly saved in db
        em.detach(updatedItem);
        updatedItem
            .sequence(UPDATED_SEQUENCE)
            .isPackage(UPDATED_IS_PACKAGE)
            .tax(UPDATED_TAX)
            .payerShare(UPDATED_PAYER_SHARE)
            .patientShare(UPDATED_PATIENT_SHARE)
            .careTeamSequence(UPDATED_CARE_TEAM_SEQUENCE)
            .transportationSRCA(UPDATED_TRANSPORTATION_SRCA)
            .imaging(UPDATED_IMAGING)
            .laboratory(UPDATED_LABORATORY)
            .medicalDevice(UPDATED_MEDICAL_DEVICE)
            .oralHealthIP(UPDATED_ORAL_HEALTH_IP)
            .oralHealthOP(UPDATED_ORAL_HEALTH_OP)
            .procedure(UPDATED_PROCEDURE)
            .services(UPDATED_SERVICES)
            .medicationCode(UPDATED_MEDICATION_CODE)
            .servicedDate(UPDATED_SERVICED_DATE)
            .servicedDateStart(UPDATED_SERVICED_DATE_START)
            .servicedDateEnd(UPDATED_SERVICED_DATE_END)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .factor(UPDATED_FACTOR)
            .bodySite(UPDATED_BODY_SITE)
            .subSite(UPDATED_SUB_SITE);

        restItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedItem))
            )
            .andExpect(status().isOk());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
        Item testItem = itemList.get(itemList.size() - 1);
        assertThat(testItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testItem.getIsPackage()).isEqualTo(UPDATED_IS_PACKAGE);
        assertThat(testItem.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testItem.getPayerShare()).isEqualTo(UPDATED_PAYER_SHARE);
        assertThat(testItem.getPatientShare()).isEqualTo(UPDATED_PATIENT_SHARE);
        assertThat(testItem.getCareTeamSequence()).isEqualTo(UPDATED_CARE_TEAM_SEQUENCE);
        assertThat(testItem.getTransportationSRCA()).isEqualTo(UPDATED_TRANSPORTATION_SRCA);
        assertThat(testItem.getImaging()).isEqualTo(UPDATED_IMAGING);
        assertThat(testItem.getLaboratory()).isEqualTo(UPDATED_LABORATORY);
        assertThat(testItem.getMedicalDevice()).isEqualTo(UPDATED_MEDICAL_DEVICE);
        assertThat(testItem.getOralHealthIP()).isEqualTo(UPDATED_ORAL_HEALTH_IP);
        assertThat(testItem.getOralHealthOP()).isEqualTo(UPDATED_ORAL_HEALTH_OP);
        assertThat(testItem.getProcedure()).isEqualTo(UPDATED_PROCEDURE);
        assertThat(testItem.getServices()).isEqualTo(UPDATED_SERVICES);
        assertThat(testItem.getMedicationCode()).isEqualTo(UPDATED_MEDICATION_CODE);
        assertThat(testItem.getServicedDate()).isEqualTo(UPDATED_SERVICED_DATE);
        assertThat(testItem.getServicedDateStart()).isEqualTo(UPDATED_SERVICED_DATE_START);
        assertThat(testItem.getServicedDateEnd()).isEqualTo(UPDATED_SERVICED_DATE_END);
        assertThat(testItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testItem.getFactor()).isEqualTo(UPDATED_FACTOR);
        assertThat(testItem.getBodySite()).isEqualTo(UPDATED_BODY_SITE);
        assertThat(testItem.getSubSite()).isEqualTo(UPDATED_SUB_SITE);
    }

    @Test
    @Transactional
    void putNonExistingItem() throws Exception {
        int databaseSizeBeforeUpdate = itemRepository.findAll().size();
        item.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, item.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(item))
            )
            .andExpect(status().isBadRequest());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItem() throws Exception {
        int databaseSizeBeforeUpdate = itemRepository.findAll().size();
        item.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(item))
            )
            .andExpect(status().isBadRequest());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItem() throws Exception {
        int databaseSizeBeforeUpdate = itemRepository.findAll().size();
        item.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemWithPatch() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Update the item using partial update
        Item partialUpdatedItem = new Item();
        partialUpdatedItem.setId(item.getId());

        partialUpdatedItem
            .payerShare(UPDATED_PAYER_SHARE)
            .imaging(UPDATED_IMAGING)
            .laboratory(UPDATED_LABORATORY)
            .medicalDevice(UPDATED_MEDICAL_DEVICE)
            .oralHealthIP(UPDATED_ORAL_HEALTH_IP)
            .oralHealthOP(UPDATED_ORAL_HEALTH_OP)
            .services(UPDATED_SERVICES)
            .medicationCode(UPDATED_MEDICATION_CODE)
            .servicedDate(UPDATED_SERVICED_DATE)
            .servicedDateEnd(UPDATED_SERVICED_DATE_END)
            .bodySite(UPDATED_BODY_SITE);

        restItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItem))
            )
            .andExpect(status().isOk());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
        Item testItem = itemList.get(itemList.size() - 1);
        assertThat(testItem.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testItem.getIsPackage()).isEqualTo(DEFAULT_IS_PACKAGE);
        assertThat(testItem.getTax()).isEqualByComparingTo(DEFAULT_TAX);
        assertThat(testItem.getPayerShare()).isEqualByComparingTo(UPDATED_PAYER_SHARE);
        assertThat(testItem.getPatientShare()).isEqualByComparingTo(DEFAULT_PATIENT_SHARE);
        assertThat(testItem.getCareTeamSequence()).isEqualTo(DEFAULT_CARE_TEAM_SEQUENCE);
        assertThat(testItem.getTransportationSRCA()).isEqualTo(DEFAULT_TRANSPORTATION_SRCA);
        assertThat(testItem.getImaging()).isEqualTo(UPDATED_IMAGING);
        assertThat(testItem.getLaboratory()).isEqualTo(UPDATED_LABORATORY);
        assertThat(testItem.getMedicalDevice()).isEqualTo(UPDATED_MEDICAL_DEVICE);
        assertThat(testItem.getOralHealthIP()).isEqualTo(UPDATED_ORAL_HEALTH_IP);
        assertThat(testItem.getOralHealthOP()).isEqualTo(UPDATED_ORAL_HEALTH_OP);
        assertThat(testItem.getProcedure()).isEqualTo(DEFAULT_PROCEDURE);
        assertThat(testItem.getServices()).isEqualTo(UPDATED_SERVICES);
        assertThat(testItem.getMedicationCode()).isEqualTo(UPDATED_MEDICATION_CODE);
        assertThat(testItem.getServicedDate()).isEqualTo(UPDATED_SERVICED_DATE);
        assertThat(testItem.getServicedDateStart()).isEqualTo(DEFAULT_SERVICED_DATE_START);
        assertThat(testItem.getServicedDateEnd()).isEqualTo(UPDATED_SERVICED_DATE_END);
        assertThat(testItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testItem.getFactor()).isEqualByComparingTo(DEFAULT_FACTOR);
        assertThat(testItem.getBodySite()).isEqualTo(UPDATED_BODY_SITE);
        assertThat(testItem.getSubSite()).isEqualTo(DEFAULT_SUB_SITE);
    }

    @Test
    @Transactional
    void fullUpdateItemWithPatch() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        int databaseSizeBeforeUpdate = itemRepository.findAll().size();

        // Update the item using partial update
        Item partialUpdatedItem = new Item();
        partialUpdatedItem.setId(item.getId());

        partialUpdatedItem
            .sequence(UPDATED_SEQUENCE)
            .isPackage(UPDATED_IS_PACKAGE)
            .tax(UPDATED_TAX)
            .payerShare(UPDATED_PAYER_SHARE)
            .patientShare(UPDATED_PATIENT_SHARE)
            .careTeamSequence(UPDATED_CARE_TEAM_SEQUENCE)
            .transportationSRCA(UPDATED_TRANSPORTATION_SRCA)
            .imaging(UPDATED_IMAGING)
            .laboratory(UPDATED_LABORATORY)
            .medicalDevice(UPDATED_MEDICAL_DEVICE)
            .oralHealthIP(UPDATED_ORAL_HEALTH_IP)
            .oralHealthOP(UPDATED_ORAL_HEALTH_OP)
            .procedure(UPDATED_PROCEDURE)
            .services(UPDATED_SERVICES)
            .medicationCode(UPDATED_MEDICATION_CODE)
            .servicedDate(UPDATED_SERVICED_DATE)
            .servicedDateStart(UPDATED_SERVICED_DATE_START)
            .servicedDateEnd(UPDATED_SERVICED_DATE_END)
            .quantity(UPDATED_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .factor(UPDATED_FACTOR)
            .bodySite(UPDATED_BODY_SITE)
            .subSite(UPDATED_SUB_SITE);

        restItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItem))
            )
            .andExpect(status().isOk());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
        Item testItem = itemList.get(itemList.size() - 1);
        assertThat(testItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testItem.getIsPackage()).isEqualTo(UPDATED_IS_PACKAGE);
        assertThat(testItem.getTax()).isEqualByComparingTo(UPDATED_TAX);
        assertThat(testItem.getPayerShare()).isEqualByComparingTo(UPDATED_PAYER_SHARE);
        assertThat(testItem.getPatientShare()).isEqualByComparingTo(UPDATED_PATIENT_SHARE);
        assertThat(testItem.getCareTeamSequence()).isEqualTo(UPDATED_CARE_TEAM_SEQUENCE);
        assertThat(testItem.getTransportationSRCA()).isEqualTo(UPDATED_TRANSPORTATION_SRCA);
        assertThat(testItem.getImaging()).isEqualTo(UPDATED_IMAGING);
        assertThat(testItem.getLaboratory()).isEqualTo(UPDATED_LABORATORY);
        assertThat(testItem.getMedicalDevice()).isEqualTo(UPDATED_MEDICAL_DEVICE);
        assertThat(testItem.getOralHealthIP()).isEqualTo(UPDATED_ORAL_HEALTH_IP);
        assertThat(testItem.getOralHealthOP()).isEqualTo(UPDATED_ORAL_HEALTH_OP);
        assertThat(testItem.getProcedure()).isEqualTo(UPDATED_PROCEDURE);
        assertThat(testItem.getServices()).isEqualTo(UPDATED_SERVICES);
        assertThat(testItem.getMedicationCode()).isEqualTo(UPDATED_MEDICATION_CODE);
        assertThat(testItem.getServicedDate()).isEqualTo(UPDATED_SERVICED_DATE);
        assertThat(testItem.getServicedDateStart()).isEqualTo(UPDATED_SERVICED_DATE_START);
        assertThat(testItem.getServicedDateEnd()).isEqualTo(UPDATED_SERVICED_DATE_END);
        assertThat(testItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testItem.getFactor()).isEqualByComparingTo(UPDATED_FACTOR);
        assertThat(testItem.getBodySite()).isEqualTo(UPDATED_BODY_SITE);
        assertThat(testItem.getSubSite()).isEqualTo(UPDATED_SUB_SITE);
    }

    @Test
    @Transactional
    void patchNonExistingItem() throws Exception {
        int databaseSizeBeforeUpdate = itemRepository.findAll().size();
        item.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, item.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(item))
            )
            .andExpect(status().isBadRequest());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItem() throws Exception {
        int databaseSizeBeforeUpdate = itemRepository.findAll().size();
        item.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(item))
            )
            .andExpect(status().isBadRequest());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItem() throws Exception {
        int databaseSizeBeforeUpdate = itemRepository.findAll().size();
        item.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(item)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Item in the database
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItem() throws Exception {
        // Initialize the database
        itemRepository.saveAndFlush(item);

        int databaseSizeBeforeDelete = itemRepository.findAll().size();

        // Delete the item
        restItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, item.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Item> itemList = itemRepository.findAll();
        assertThat(itemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
