package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Coverage;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.CoverageTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.RelationShipEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.CoverageRepository;
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
 * Integration tests for the {@link CoverageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CoverageResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_FORCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FORCE_ID = "BBBBBBBBBB";

    private static final CoverageTypeEnum DEFAULT_COVERAGE_TYPE = CoverageTypeEnum.EHCPOL;
    private static final CoverageTypeEnum UPDATED_COVERAGE_TYPE = CoverageTypeEnum.PUBLICPOL;

    private static final String DEFAULT_SUBSCRIBER_ID = "AAAAAAAAAA";
    private static final String UPDATED_SUBSCRIBER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DEPENDENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPENDENT = "BBBBBBBBBB";

    private static final RelationShipEnum DEFAULT_RELATION_SHIP = RelationShipEnum.Child;
    private static final RelationShipEnum UPDATED_RELATION_SHIP = RelationShipEnum.Parent;

    private static final String DEFAULT_NETWORK = "AAAAAAAAAA";
    private static final String UPDATED_NETWORK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SUBROGATION = false;
    private static final Boolean UPDATED_SUBROGATION = true;

    private static final String ENTITY_API_URL = "/api/coverages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CoverageRepository coverageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoverageMockMvc;

    private Coverage coverage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coverage createEntity(EntityManager em) {
        Coverage coverage = new Coverage()
            .guid(DEFAULT_GUID)
            .forceId(DEFAULT_FORCE_ID)
            .coverageType(DEFAULT_COVERAGE_TYPE)
            .subscriberId(DEFAULT_SUBSCRIBER_ID)
            .dependent(DEFAULT_DEPENDENT)
            .relationShip(DEFAULT_RELATION_SHIP)
            .network(DEFAULT_NETWORK)
            .subrogation(DEFAULT_SUBROGATION);
        return coverage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coverage createUpdatedEntity(EntityManager em) {
        Coverage coverage = new Coverage()
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .coverageType(UPDATED_COVERAGE_TYPE)
            .subscriberId(UPDATED_SUBSCRIBER_ID)
            .dependent(UPDATED_DEPENDENT)
            .relationShip(UPDATED_RELATION_SHIP)
            .network(UPDATED_NETWORK)
            .subrogation(UPDATED_SUBROGATION);
        return coverage;
    }

    @BeforeEach
    public void initTest() {
        coverage = createEntity(em);
    }

    @Test
    @Transactional
    void createCoverage() throws Exception {
        int databaseSizeBeforeCreate = coverageRepository.findAll().size();
        // Create the Coverage
        restCoverageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coverage)))
            .andExpect(status().isCreated());

        // Validate the Coverage in the database
        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeCreate + 1);
        Coverage testCoverage = coverageList.get(coverageList.size() - 1);
        assertThat(testCoverage.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testCoverage.getForceId()).isEqualTo(DEFAULT_FORCE_ID);
        assertThat(testCoverage.getCoverageType()).isEqualTo(DEFAULT_COVERAGE_TYPE);
        assertThat(testCoverage.getSubscriberId()).isEqualTo(DEFAULT_SUBSCRIBER_ID);
        assertThat(testCoverage.getDependent()).isEqualTo(DEFAULT_DEPENDENT);
        assertThat(testCoverage.getRelationShip()).isEqualTo(DEFAULT_RELATION_SHIP);
        assertThat(testCoverage.getNetwork()).isEqualTo(DEFAULT_NETWORK);
        assertThat(testCoverage.getSubrogation()).isEqualTo(DEFAULT_SUBROGATION);
    }

    @Test
    @Transactional
    void createCoverageWithExistingId() throws Exception {
        // Create the Coverage with an existing ID
        coverage.setId(1L);

        int databaseSizeBeforeCreate = coverageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoverageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coverage)))
            .andExpect(status().isBadRequest());

        // Validate the Coverage in the database
        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCoverageTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = coverageRepository.findAll().size();
        // set the field null
        coverage.setCoverageType(null);

        // Create the Coverage, which fails.

        restCoverageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coverage)))
            .andExpect(status().isBadRequest());

        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRelationShipIsRequired() throws Exception {
        int databaseSizeBeforeTest = coverageRepository.findAll().size();
        // set the field null
        coverage.setRelationShip(null);

        // Create the Coverage, which fails.

        restCoverageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coverage)))
            .andExpect(status().isBadRequest());

        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCoverages() throws Exception {
        // Initialize the database
        coverageRepository.saveAndFlush(coverage);

        // Get all the coverageList
        restCoverageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coverage.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].forceId").value(hasItem(DEFAULT_FORCE_ID)))
            .andExpect(jsonPath("$.[*].coverageType").value(hasItem(DEFAULT_COVERAGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].subscriberId").value(hasItem(DEFAULT_SUBSCRIBER_ID)))
            .andExpect(jsonPath("$.[*].dependent").value(hasItem(DEFAULT_DEPENDENT)))
            .andExpect(jsonPath("$.[*].relationShip").value(hasItem(DEFAULT_RELATION_SHIP.toString())))
            .andExpect(jsonPath("$.[*].network").value(hasItem(DEFAULT_NETWORK)))
            .andExpect(jsonPath("$.[*].subrogation").value(hasItem(DEFAULT_SUBROGATION.booleanValue())));
    }

    @Test
    @Transactional
    void getCoverage() throws Exception {
        // Initialize the database
        coverageRepository.saveAndFlush(coverage);

        // Get the coverage
        restCoverageMockMvc
            .perform(get(ENTITY_API_URL_ID, coverage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coverage.getId().intValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.forceId").value(DEFAULT_FORCE_ID))
            .andExpect(jsonPath("$.coverageType").value(DEFAULT_COVERAGE_TYPE.toString()))
            .andExpect(jsonPath("$.subscriberId").value(DEFAULT_SUBSCRIBER_ID))
            .andExpect(jsonPath("$.dependent").value(DEFAULT_DEPENDENT))
            .andExpect(jsonPath("$.relationShip").value(DEFAULT_RELATION_SHIP.toString()))
            .andExpect(jsonPath("$.network").value(DEFAULT_NETWORK))
            .andExpect(jsonPath("$.subrogation").value(DEFAULT_SUBROGATION.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingCoverage() throws Exception {
        // Get the coverage
        restCoverageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCoverage() throws Exception {
        // Initialize the database
        coverageRepository.saveAndFlush(coverage);

        int databaseSizeBeforeUpdate = coverageRepository.findAll().size();

        // Update the coverage
        Coverage updatedCoverage = coverageRepository.findById(coverage.getId()).get();
        // Disconnect from session so that the updates on updatedCoverage are not directly saved in db
        em.detach(updatedCoverage);
        updatedCoverage
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .coverageType(UPDATED_COVERAGE_TYPE)
            .subscriberId(UPDATED_SUBSCRIBER_ID)
            .dependent(UPDATED_DEPENDENT)
            .relationShip(UPDATED_RELATION_SHIP)
            .network(UPDATED_NETWORK)
            .subrogation(UPDATED_SUBROGATION);

        restCoverageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCoverage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCoverage))
            )
            .andExpect(status().isOk());

        // Validate the Coverage in the database
        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeUpdate);
        Coverage testCoverage = coverageList.get(coverageList.size() - 1);
        assertThat(testCoverage.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testCoverage.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testCoverage.getCoverageType()).isEqualTo(UPDATED_COVERAGE_TYPE);
        assertThat(testCoverage.getSubscriberId()).isEqualTo(UPDATED_SUBSCRIBER_ID);
        assertThat(testCoverage.getDependent()).isEqualTo(UPDATED_DEPENDENT);
        assertThat(testCoverage.getRelationShip()).isEqualTo(UPDATED_RELATION_SHIP);
        assertThat(testCoverage.getNetwork()).isEqualTo(UPDATED_NETWORK);
        assertThat(testCoverage.getSubrogation()).isEqualTo(UPDATED_SUBROGATION);
    }

    @Test
    @Transactional
    void putNonExistingCoverage() throws Exception {
        int databaseSizeBeforeUpdate = coverageRepository.findAll().size();
        coverage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoverageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, coverage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coverage in the database
        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCoverage() throws Exception {
        int databaseSizeBeforeUpdate = coverageRepository.findAll().size();
        coverage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoverageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coverage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coverage in the database
        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCoverage() throws Exception {
        int databaseSizeBeforeUpdate = coverageRepository.findAll().size();
        coverage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoverageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coverage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Coverage in the database
        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCoverageWithPatch() throws Exception {
        // Initialize the database
        coverageRepository.saveAndFlush(coverage);

        int databaseSizeBeforeUpdate = coverageRepository.findAll().size();

        // Update the coverage using partial update
        Coverage partialUpdatedCoverage = new Coverage();
        partialUpdatedCoverage.setId(coverage.getId());

        partialUpdatedCoverage
            .forceId(UPDATED_FORCE_ID)
            .coverageType(UPDATED_COVERAGE_TYPE)
            .dependent(UPDATED_DEPENDENT)
            .subrogation(UPDATED_SUBROGATION);

        restCoverageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoverage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoverage))
            )
            .andExpect(status().isOk());

        // Validate the Coverage in the database
        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeUpdate);
        Coverage testCoverage = coverageList.get(coverageList.size() - 1);
        assertThat(testCoverage.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testCoverage.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testCoverage.getCoverageType()).isEqualTo(UPDATED_COVERAGE_TYPE);
        assertThat(testCoverage.getSubscriberId()).isEqualTo(DEFAULT_SUBSCRIBER_ID);
        assertThat(testCoverage.getDependent()).isEqualTo(UPDATED_DEPENDENT);
        assertThat(testCoverage.getRelationShip()).isEqualTo(DEFAULT_RELATION_SHIP);
        assertThat(testCoverage.getNetwork()).isEqualTo(DEFAULT_NETWORK);
        assertThat(testCoverage.getSubrogation()).isEqualTo(UPDATED_SUBROGATION);
    }

    @Test
    @Transactional
    void fullUpdateCoverageWithPatch() throws Exception {
        // Initialize the database
        coverageRepository.saveAndFlush(coverage);

        int databaseSizeBeforeUpdate = coverageRepository.findAll().size();

        // Update the coverage using partial update
        Coverage partialUpdatedCoverage = new Coverage();
        partialUpdatedCoverage.setId(coverage.getId());

        partialUpdatedCoverage
            .guid(UPDATED_GUID)
            .forceId(UPDATED_FORCE_ID)
            .coverageType(UPDATED_COVERAGE_TYPE)
            .subscriberId(UPDATED_SUBSCRIBER_ID)
            .dependent(UPDATED_DEPENDENT)
            .relationShip(UPDATED_RELATION_SHIP)
            .network(UPDATED_NETWORK)
            .subrogation(UPDATED_SUBROGATION);

        restCoverageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoverage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoverage))
            )
            .andExpect(status().isOk());

        // Validate the Coverage in the database
        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeUpdate);
        Coverage testCoverage = coverageList.get(coverageList.size() - 1);
        assertThat(testCoverage.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testCoverage.getForceId()).isEqualTo(UPDATED_FORCE_ID);
        assertThat(testCoverage.getCoverageType()).isEqualTo(UPDATED_COVERAGE_TYPE);
        assertThat(testCoverage.getSubscriberId()).isEqualTo(UPDATED_SUBSCRIBER_ID);
        assertThat(testCoverage.getDependent()).isEqualTo(UPDATED_DEPENDENT);
        assertThat(testCoverage.getRelationShip()).isEqualTo(UPDATED_RELATION_SHIP);
        assertThat(testCoverage.getNetwork()).isEqualTo(UPDATED_NETWORK);
        assertThat(testCoverage.getSubrogation()).isEqualTo(UPDATED_SUBROGATION);
    }

    @Test
    @Transactional
    void patchNonExistingCoverage() throws Exception {
        int databaseSizeBeforeUpdate = coverageRepository.findAll().size();
        coverage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoverageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, coverage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coverage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coverage in the database
        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCoverage() throws Exception {
        int databaseSizeBeforeUpdate = coverageRepository.findAll().size();
        coverage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoverageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coverage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Coverage in the database
        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCoverage() throws Exception {
        int databaseSizeBeforeUpdate = coverageRepository.findAll().size();
        coverage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoverageMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(coverage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Coverage in the database
        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCoverage() throws Exception {
        // Initialize the database
        coverageRepository.saveAndFlush(coverage);

        int databaseSizeBeforeDelete = coverageRepository.findAll().size();

        // Delete the coverage
        restCoverageMockMvc
            .perform(delete(ENTITY_API_URL_ID, coverage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Coverage> coverageList = coverageRepository.findAll();
        assertThat(coverageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
