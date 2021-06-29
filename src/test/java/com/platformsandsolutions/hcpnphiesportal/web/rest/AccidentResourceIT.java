package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Accident;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.AccidentTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.AccidentRepository;
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
 * Integration tests for the {@link AccidentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccidentResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final AccidentTypeEnum DEFAULT_TYPE = AccidentTypeEnum.MVA;
    private static final AccidentTypeEnum UPDATED_TYPE = AccidentTypeEnum.SCHOOL;

    private static final String ENTITY_API_URL = "/api/accidents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccidentRepository accidentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccidentMockMvc;

    private Accident accident;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accident createEntity(EntityManager em) {
        Accident accident = new Accident().date(DEFAULT_DATE).type(DEFAULT_TYPE);
        return accident;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accident createUpdatedEntity(EntityManager em) {
        Accident accident = new Accident().date(UPDATED_DATE).type(UPDATED_TYPE);
        return accident;
    }

    @BeforeEach
    public void initTest() {
        accident = createEntity(em);
    }

    @Test
    @Transactional
    void createAccident() throws Exception {
        int databaseSizeBeforeCreate = accidentRepository.findAll().size();
        // Create the Accident
        restAccidentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accident)))
            .andExpect(status().isCreated());

        // Validate the Accident in the database
        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeCreate + 1);
        Accident testAccident = accidentList.get(accidentList.size() - 1);
        assertThat(testAccident.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAccident.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createAccidentWithExistingId() throws Exception {
        // Create the Accident with an existing ID
        accident.setId(1L);

        int databaseSizeBeforeCreate = accidentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccidentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accident)))
            .andExpect(status().isBadRequest());

        // Validate the Accident in the database
        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = accidentRepository.findAll().size();
        // set the field null
        accident.setDate(null);

        // Create the Accident, which fails.

        restAccidentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accident)))
            .andExpect(status().isBadRequest());

        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accidentRepository.findAll().size();
        // set the field null
        accident.setType(null);

        // Create the Accident, which fails.

        restAccidentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accident)))
            .andExpect(status().isBadRequest());

        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccidents() throws Exception {
        // Initialize the database
        accidentRepository.saveAndFlush(accident);

        // Get all the accidentList
        restAccidentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accident.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getAccident() throws Exception {
        // Initialize the database
        accidentRepository.saveAndFlush(accident);

        // Get the accident
        restAccidentMockMvc
            .perform(get(ENTITY_API_URL_ID, accident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accident.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAccident() throws Exception {
        // Get the accident
        restAccidentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccident() throws Exception {
        // Initialize the database
        accidentRepository.saveAndFlush(accident);

        int databaseSizeBeforeUpdate = accidentRepository.findAll().size();

        // Update the accident
        Accident updatedAccident = accidentRepository.findById(accident.getId()).get();
        // Disconnect from session so that the updates on updatedAccident are not directly saved in db
        em.detach(updatedAccident);
        updatedAccident.date(UPDATED_DATE).type(UPDATED_TYPE);

        restAccidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAccident.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAccident))
            )
            .andExpect(status().isOk());

        // Validate the Accident in the database
        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeUpdate);
        Accident testAccident = accidentList.get(accidentList.size() - 1);
        assertThat(testAccident.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAccident.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingAccident() throws Exception {
        int databaseSizeBeforeUpdate = accidentRepository.findAll().size();
        accident.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accident.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accident in the database
        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccident() throws Exception {
        int databaseSizeBeforeUpdate = accidentRepository.findAll().size();
        accident.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccidentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accident in the database
        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccident() throws Exception {
        int databaseSizeBeforeUpdate = accidentRepository.findAll().size();
        accident.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccidentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accident)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accident in the database
        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccidentWithPatch() throws Exception {
        // Initialize the database
        accidentRepository.saveAndFlush(accident);

        int databaseSizeBeforeUpdate = accidentRepository.findAll().size();

        // Update the accident using partial update
        Accident partialUpdatedAccident = new Accident();
        partialUpdatedAccident.setId(accident.getId());

        partialUpdatedAccident.date(UPDATED_DATE).type(UPDATED_TYPE);

        restAccidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccident.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccident))
            )
            .andExpect(status().isOk());

        // Validate the Accident in the database
        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeUpdate);
        Accident testAccident = accidentList.get(accidentList.size() - 1);
        assertThat(testAccident.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAccident.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateAccidentWithPatch() throws Exception {
        // Initialize the database
        accidentRepository.saveAndFlush(accident);

        int databaseSizeBeforeUpdate = accidentRepository.findAll().size();

        // Update the accident using partial update
        Accident partialUpdatedAccident = new Accident();
        partialUpdatedAccident.setId(accident.getId());

        partialUpdatedAccident.date(UPDATED_DATE).type(UPDATED_TYPE);

        restAccidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccident.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccident))
            )
            .andExpect(status().isOk());

        // Validate the Accident in the database
        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeUpdate);
        Accident testAccident = accidentList.get(accidentList.size() - 1);
        assertThat(testAccident.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAccident.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingAccident() throws Exception {
        int databaseSizeBeforeUpdate = accidentRepository.findAll().size();
        accident.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accident.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accident in the database
        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccident() throws Exception {
        int databaseSizeBeforeUpdate = accidentRepository.findAll().size();
        accident.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccidentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accident))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accident in the database
        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccident() throws Exception {
        int databaseSizeBeforeUpdate = accidentRepository.findAll().size();
        accident.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccidentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accident)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accident in the database
        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccident() throws Exception {
        // Initialize the database
        accidentRepository.saveAndFlush(accident);

        int databaseSizeBeforeDelete = accidentRepository.findAll().size();

        // Delete the accident
        restAccidentMockMvc
            .perform(delete(ENTITY_API_URL_ID, accident.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accident> accidentList = accidentRepository.findAll();
        assertThat(accidentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
