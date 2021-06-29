package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static com.platformsandsolutions.hcpnphiesportal.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.CostToBeneficiaryComponent;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.CostToBeneficiaryTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.CostToBeneficiaryComponentRepository;
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
 * Integration tests for the {@link CostToBeneficiaryComponentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CostToBeneficiaryComponentResourceIT {

    private static final CostToBeneficiaryTypeEnum DEFAULT_TYPE = CostToBeneficiaryTypeEnum.Gpvisit;
    private static final CostToBeneficiaryTypeEnum UPDATED_TYPE = CostToBeneficiaryTypeEnum.Spvisit;

    private static final Boolean DEFAULT_IS_MONEY = false;
    private static final Boolean UPDATED_IS_MONEY = true;

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/cost-to-beneficiary-components";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CostToBeneficiaryComponentRepository costToBeneficiaryComponentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCostToBeneficiaryComponentMockMvc;

    private CostToBeneficiaryComponent costToBeneficiaryComponent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CostToBeneficiaryComponent createEntity(EntityManager em) {
        CostToBeneficiaryComponent costToBeneficiaryComponent = new CostToBeneficiaryComponent()
            .type(DEFAULT_TYPE)
            .isMoney(DEFAULT_IS_MONEY)
            .value(DEFAULT_VALUE);
        return costToBeneficiaryComponent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CostToBeneficiaryComponent createUpdatedEntity(EntityManager em) {
        CostToBeneficiaryComponent costToBeneficiaryComponent = new CostToBeneficiaryComponent()
            .type(UPDATED_TYPE)
            .isMoney(UPDATED_IS_MONEY)
            .value(UPDATED_VALUE);
        return costToBeneficiaryComponent;
    }

    @BeforeEach
    public void initTest() {
        costToBeneficiaryComponent = createEntity(em);
    }

    @Test
    @Transactional
    void createCostToBeneficiaryComponent() throws Exception {
        int databaseSizeBeforeCreate = costToBeneficiaryComponentRepository.findAll().size();
        // Create the CostToBeneficiaryComponent
        restCostToBeneficiaryComponentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(costToBeneficiaryComponent))
            )
            .andExpect(status().isCreated());

        // Validate the CostToBeneficiaryComponent in the database
        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeCreate + 1);
        CostToBeneficiaryComponent testCostToBeneficiaryComponent = costToBeneficiaryComponentList.get(
            costToBeneficiaryComponentList.size() - 1
        );
        assertThat(testCostToBeneficiaryComponent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCostToBeneficiaryComponent.getIsMoney()).isEqualTo(DEFAULT_IS_MONEY);
        assertThat(testCostToBeneficiaryComponent.getValue()).isEqualByComparingTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createCostToBeneficiaryComponentWithExistingId() throws Exception {
        // Create the CostToBeneficiaryComponent with an existing ID
        costToBeneficiaryComponent.setId(1L);

        int databaseSizeBeforeCreate = costToBeneficiaryComponentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCostToBeneficiaryComponentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(costToBeneficiaryComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostToBeneficiaryComponent in the database
        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = costToBeneficiaryComponentRepository.findAll().size();
        // set the field null
        costToBeneficiaryComponent.setValue(null);

        // Create the CostToBeneficiaryComponent, which fails.

        restCostToBeneficiaryComponentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(costToBeneficiaryComponent))
            )
            .andExpect(status().isBadRequest());

        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCostToBeneficiaryComponents() throws Exception {
        // Initialize the database
        costToBeneficiaryComponentRepository.saveAndFlush(costToBeneficiaryComponent);

        // Get all the costToBeneficiaryComponentList
        restCostToBeneficiaryComponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costToBeneficiaryComponent.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isMoney").value(hasItem(DEFAULT_IS_MONEY.booleanValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(sameNumber(DEFAULT_VALUE))));
    }

    @Test
    @Transactional
    void getCostToBeneficiaryComponent() throws Exception {
        // Initialize the database
        costToBeneficiaryComponentRepository.saveAndFlush(costToBeneficiaryComponent);

        // Get the costToBeneficiaryComponent
        restCostToBeneficiaryComponentMockMvc
            .perform(get(ENTITY_API_URL_ID, costToBeneficiaryComponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(costToBeneficiaryComponent.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.isMoney").value(DEFAULT_IS_MONEY.booleanValue()))
            .andExpect(jsonPath("$.value").value(sameNumber(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getNonExistingCostToBeneficiaryComponent() throws Exception {
        // Get the costToBeneficiaryComponent
        restCostToBeneficiaryComponentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCostToBeneficiaryComponent() throws Exception {
        // Initialize the database
        costToBeneficiaryComponentRepository.saveAndFlush(costToBeneficiaryComponent);

        int databaseSizeBeforeUpdate = costToBeneficiaryComponentRepository.findAll().size();

        // Update the costToBeneficiaryComponent
        CostToBeneficiaryComponent updatedCostToBeneficiaryComponent = costToBeneficiaryComponentRepository
            .findById(costToBeneficiaryComponent.getId())
            .get();
        // Disconnect from session so that the updates on updatedCostToBeneficiaryComponent are not directly saved in db
        em.detach(updatedCostToBeneficiaryComponent);
        updatedCostToBeneficiaryComponent.type(UPDATED_TYPE).isMoney(UPDATED_IS_MONEY).value(UPDATED_VALUE);

        restCostToBeneficiaryComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCostToBeneficiaryComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCostToBeneficiaryComponent))
            )
            .andExpect(status().isOk());

        // Validate the CostToBeneficiaryComponent in the database
        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeUpdate);
        CostToBeneficiaryComponent testCostToBeneficiaryComponent = costToBeneficiaryComponentList.get(
            costToBeneficiaryComponentList.size() - 1
        );
        assertThat(testCostToBeneficiaryComponent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCostToBeneficiaryComponent.getIsMoney()).isEqualTo(UPDATED_IS_MONEY);
        assertThat(testCostToBeneficiaryComponent.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingCostToBeneficiaryComponent() throws Exception {
        int databaseSizeBeforeUpdate = costToBeneficiaryComponentRepository.findAll().size();
        costToBeneficiaryComponent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCostToBeneficiaryComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, costToBeneficiaryComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(costToBeneficiaryComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostToBeneficiaryComponent in the database
        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCostToBeneficiaryComponent() throws Exception {
        int databaseSizeBeforeUpdate = costToBeneficiaryComponentRepository.findAll().size();
        costToBeneficiaryComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCostToBeneficiaryComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(costToBeneficiaryComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostToBeneficiaryComponent in the database
        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCostToBeneficiaryComponent() throws Exception {
        int databaseSizeBeforeUpdate = costToBeneficiaryComponentRepository.findAll().size();
        costToBeneficiaryComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCostToBeneficiaryComponentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(costToBeneficiaryComponent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CostToBeneficiaryComponent in the database
        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCostToBeneficiaryComponentWithPatch() throws Exception {
        // Initialize the database
        costToBeneficiaryComponentRepository.saveAndFlush(costToBeneficiaryComponent);

        int databaseSizeBeforeUpdate = costToBeneficiaryComponentRepository.findAll().size();

        // Update the costToBeneficiaryComponent using partial update
        CostToBeneficiaryComponent partialUpdatedCostToBeneficiaryComponent = new CostToBeneficiaryComponent();
        partialUpdatedCostToBeneficiaryComponent.setId(costToBeneficiaryComponent.getId());

        partialUpdatedCostToBeneficiaryComponent.isMoney(UPDATED_IS_MONEY);

        restCostToBeneficiaryComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCostToBeneficiaryComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCostToBeneficiaryComponent))
            )
            .andExpect(status().isOk());

        // Validate the CostToBeneficiaryComponent in the database
        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeUpdate);
        CostToBeneficiaryComponent testCostToBeneficiaryComponent = costToBeneficiaryComponentList.get(
            costToBeneficiaryComponentList.size() - 1
        );
        assertThat(testCostToBeneficiaryComponent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCostToBeneficiaryComponent.getIsMoney()).isEqualTo(UPDATED_IS_MONEY);
        assertThat(testCostToBeneficiaryComponent.getValue()).isEqualByComparingTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateCostToBeneficiaryComponentWithPatch() throws Exception {
        // Initialize the database
        costToBeneficiaryComponentRepository.saveAndFlush(costToBeneficiaryComponent);

        int databaseSizeBeforeUpdate = costToBeneficiaryComponentRepository.findAll().size();

        // Update the costToBeneficiaryComponent using partial update
        CostToBeneficiaryComponent partialUpdatedCostToBeneficiaryComponent = new CostToBeneficiaryComponent();
        partialUpdatedCostToBeneficiaryComponent.setId(costToBeneficiaryComponent.getId());

        partialUpdatedCostToBeneficiaryComponent.type(UPDATED_TYPE).isMoney(UPDATED_IS_MONEY).value(UPDATED_VALUE);

        restCostToBeneficiaryComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCostToBeneficiaryComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCostToBeneficiaryComponent))
            )
            .andExpect(status().isOk());

        // Validate the CostToBeneficiaryComponent in the database
        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeUpdate);
        CostToBeneficiaryComponent testCostToBeneficiaryComponent = costToBeneficiaryComponentList.get(
            costToBeneficiaryComponentList.size() - 1
        );
        assertThat(testCostToBeneficiaryComponent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCostToBeneficiaryComponent.getIsMoney()).isEqualTo(UPDATED_IS_MONEY);
        assertThat(testCostToBeneficiaryComponent.getValue()).isEqualByComparingTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingCostToBeneficiaryComponent() throws Exception {
        int databaseSizeBeforeUpdate = costToBeneficiaryComponentRepository.findAll().size();
        costToBeneficiaryComponent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCostToBeneficiaryComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, costToBeneficiaryComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(costToBeneficiaryComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostToBeneficiaryComponent in the database
        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCostToBeneficiaryComponent() throws Exception {
        int databaseSizeBeforeUpdate = costToBeneficiaryComponentRepository.findAll().size();
        costToBeneficiaryComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCostToBeneficiaryComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(costToBeneficiaryComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostToBeneficiaryComponent in the database
        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCostToBeneficiaryComponent() throws Exception {
        int databaseSizeBeforeUpdate = costToBeneficiaryComponentRepository.findAll().size();
        costToBeneficiaryComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCostToBeneficiaryComponentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(costToBeneficiaryComponent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CostToBeneficiaryComponent in the database
        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCostToBeneficiaryComponent() throws Exception {
        // Initialize the database
        costToBeneficiaryComponentRepository.saveAndFlush(costToBeneficiaryComponent);

        int databaseSizeBeforeDelete = costToBeneficiaryComponentRepository.findAll().size();

        // Delete the costToBeneficiaryComponent
        restCostToBeneficiaryComponentMockMvc
            .perform(delete(ENTITY_API_URL_ID, costToBeneficiaryComponent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CostToBeneficiaryComponent> costToBeneficiaryComponentList = costToBeneficiaryComponentRepository.findAll();
        assertThat(costToBeneficiaryComponentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
