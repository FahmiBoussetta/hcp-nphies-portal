package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ExemptionComponent;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ExemptionTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ExemptionComponentRepository;
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
 * Integration tests for the {@link ExemptionComponentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExemptionComponentResourceIT {

    private static final ExemptionTypeEnum DEFAULT_TYPE = ExemptionTypeEnum.Retired;
    private static final ExemptionTypeEnum UPDATED_TYPE = ExemptionTypeEnum.Foster;

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/exemption-components";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExemptionComponentRepository exemptionComponentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExemptionComponentMockMvc;

    private ExemptionComponent exemptionComponent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExemptionComponent createEntity(EntityManager em) {
        ExemptionComponent exemptionComponent = new ExemptionComponent().type(DEFAULT_TYPE).start(DEFAULT_START).end(DEFAULT_END);
        return exemptionComponent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExemptionComponent createUpdatedEntity(EntityManager em) {
        ExemptionComponent exemptionComponent = new ExemptionComponent().type(UPDATED_TYPE).start(UPDATED_START).end(UPDATED_END);
        return exemptionComponent;
    }

    @BeforeEach
    public void initTest() {
        exemptionComponent = createEntity(em);
    }

    @Test
    @Transactional
    void createExemptionComponent() throws Exception {
        int databaseSizeBeforeCreate = exemptionComponentRepository.findAll().size();
        // Create the ExemptionComponent
        restExemptionComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exemptionComponent))
            )
            .andExpect(status().isCreated());

        // Validate the ExemptionComponent in the database
        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeCreate + 1);
        ExemptionComponent testExemptionComponent = exemptionComponentList.get(exemptionComponentList.size() - 1);
        assertThat(testExemptionComponent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testExemptionComponent.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testExemptionComponent.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    void createExemptionComponentWithExistingId() throws Exception {
        // Create the ExemptionComponent with an existing ID
        exemptionComponent.setId(1L);

        int databaseSizeBeforeCreate = exemptionComponentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExemptionComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exemptionComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExemptionComponent in the database
        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = exemptionComponentRepository.findAll().size();
        // set the field null
        exemptionComponent.setType(null);

        // Create the ExemptionComponent, which fails.

        restExemptionComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exemptionComponent))
            )
            .andExpect(status().isBadRequest());

        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExemptionComponents() throws Exception {
        // Initialize the database
        exemptionComponentRepository.saveAndFlush(exemptionComponent);

        // Get all the exemptionComponentList
        restExemptionComponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exemptionComponent.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }

    @Test
    @Transactional
    void getExemptionComponent() throws Exception {
        // Initialize the database
        exemptionComponentRepository.saveAndFlush(exemptionComponent);

        // Get the exemptionComponent
        restExemptionComponentMockMvc
            .perform(get(ENTITY_API_URL_ID, exemptionComponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exemptionComponent.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    void getNonExistingExemptionComponent() throws Exception {
        // Get the exemptionComponent
        restExemptionComponentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExemptionComponent() throws Exception {
        // Initialize the database
        exemptionComponentRepository.saveAndFlush(exemptionComponent);

        int databaseSizeBeforeUpdate = exemptionComponentRepository.findAll().size();

        // Update the exemptionComponent
        ExemptionComponent updatedExemptionComponent = exemptionComponentRepository.findById(exemptionComponent.getId()).get();
        // Disconnect from session so that the updates on updatedExemptionComponent are not directly saved in db
        em.detach(updatedExemptionComponent);
        updatedExemptionComponent.type(UPDATED_TYPE).start(UPDATED_START).end(UPDATED_END);

        restExemptionComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedExemptionComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedExemptionComponent))
            )
            .andExpect(status().isOk());

        // Validate the ExemptionComponent in the database
        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeUpdate);
        ExemptionComponent testExemptionComponent = exemptionComponentList.get(exemptionComponentList.size() - 1);
        assertThat(testExemptionComponent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testExemptionComponent.getStart()).isEqualTo(UPDATED_START);
        assertThat(testExemptionComponent.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    void putNonExistingExemptionComponent() throws Exception {
        int databaseSizeBeforeUpdate = exemptionComponentRepository.findAll().size();
        exemptionComponent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExemptionComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, exemptionComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exemptionComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExemptionComponent in the database
        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExemptionComponent() throws Exception {
        int databaseSizeBeforeUpdate = exemptionComponentRepository.findAll().size();
        exemptionComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExemptionComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exemptionComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExemptionComponent in the database
        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExemptionComponent() throws Exception {
        int databaseSizeBeforeUpdate = exemptionComponentRepository.findAll().size();
        exemptionComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExemptionComponentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exemptionComponent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExemptionComponent in the database
        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExemptionComponentWithPatch() throws Exception {
        // Initialize the database
        exemptionComponentRepository.saveAndFlush(exemptionComponent);

        int databaseSizeBeforeUpdate = exemptionComponentRepository.findAll().size();

        // Update the exemptionComponent using partial update
        ExemptionComponent partialUpdatedExemptionComponent = new ExemptionComponent();
        partialUpdatedExemptionComponent.setId(exemptionComponent.getId());

        partialUpdatedExemptionComponent.start(UPDATED_START).end(UPDATED_END);

        restExemptionComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExemptionComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExemptionComponent))
            )
            .andExpect(status().isOk());

        // Validate the ExemptionComponent in the database
        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeUpdate);
        ExemptionComponent testExemptionComponent = exemptionComponentList.get(exemptionComponentList.size() - 1);
        assertThat(testExemptionComponent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testExemptionComponent.getStart()).isEqualTo(UPDATED_START);
        assertThat(testExemptionComponent.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    void fullUpdateExemptionComponentWithPatch() throws Exception {
        // Initialize the database
        exemptionComponentRepository.saveAndFlush(exemptionComponent);

        int databaseSizeBeforeUpdate = exemptionComponentRepository.findAll().size();

        // Update the exemptionComponent using partial update
        ExemptionComponent partialUpdatedExemptionComponent = new ExemptionComponent();
        partialUpdatedExemptionComponent.setId(exemptionComponent.getId());

        partialUpdatedExemptionComponent.type(UPDATED_TYPE).start(UPDATED_START).end(UPDATED_END);

        restExemptionComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExemptionComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExemptionComponent))
            )
            .andExpect(status().isOk());

        // Validate the ExemptionComponent in the database
        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeUpdate);
        ExemptionComponent testExemptionComponent = exemptionComponentList.get(exemptionComponentList.size() - 1);
        assertThat(testExemptionComponent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testExemptionComponent.getStart()).isEqualTo(UPDATED_START);
        assertThat(testExemptionComponent.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    void patchNonExistingExemptionComponent() throws Exception {
        int databaseSizeBeforeUpdate = exemptionComponentRepository.findAll().size();
        exemptionComponent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExemptionComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, exemptionComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exemptionComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExemptionComponent in the database
        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExemptionComponent() throws Exception {
        int databaseSizeBeforeUpdate = exemptionComponentRepository.findAll().size();
        exemptionComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExemptionComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exemptionComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExemptionComponent in the database
        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExemptionComponent() throws Exception {
        int databaseSizeBeforeUpdate = exemptionComponentRepository.findAll().size();
        exemptionComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExemptionComponentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exemptionComponent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExemptionComponent in the database
        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExemptionComponent() throws Exception {
        // Initialize the database
        exemptionComponentRepository.saveAndFlush(exemptionComponent);

        int databaseSizeBeforeDelete = exemptionComponentRepository.findAll().size();

        // Delete the exemptionComponent
        restExemptionComponentMockMvc
            .perform(delete(ENTITY_API_URL_ID, exemptionComponent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExemptionComponent> exemptionComponentList = exemptionComponentRepository.findAll();
        assertThat(exemptionComponentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
