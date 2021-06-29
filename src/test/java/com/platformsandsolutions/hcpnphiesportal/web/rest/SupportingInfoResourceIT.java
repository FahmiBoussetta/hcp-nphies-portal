package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.SupportingInfo;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SupportingInfoCategoryEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SupportingInfoCodeFdiEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SupportingInfoCodeVisitEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SupportingInfoReasonEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SupportingInfoReasonMissingToothEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.SupportingInfoRepository;
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
 * Integration tests for the {@link SupportingInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SupportingInfoResourceIT {

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final String DEFAULT_CODE_LOINC = "AAAAAAAAAA";
    private static final String UPDATED_CODE_LOINC = "BBBBBBBBBB";

    private static final SupportingInfoCategoryEnum DEFAULT_CATEGORY = SupportingInfoCategoryEnum.Info;
    private static final SupportingInfoCategoryEnum UPDATED_CATEGORY = SupportingInfoCategoryEnum.Discharge;

    private static final SupportingInfoCodeVisitEnum DEFAULT_CODE_VISIT = SupportingInfoCodeVisitEnum.New_visit;
    private static final SupportingInfoCodeVisitEnum UPDATED_CODE_VISIT = SupportingInfoCodeVisitEnum.Follow_up;

    private static final SupportingInfoCodeFdiEnum DEFAULT_CODE_FDI_ORAL = SupportingInfoCodeFdiEnum.N11;
    private static final SupportingInfoCodeFdiEnum UPDATED_CODE_FDI_ORAL = SupportingInfoCodeFdiEnum.N12;

    private static final Instant DEFAULT_TIMING = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMING = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TIMING_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIMING_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_VALUE_BOOLEAN = false;
    private static final Boolean UPDATED_VALUE_BOOLEAN = true;

    private static final String DEFAULT_VALUE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_STRING = "BBBBBBBBBB";

    private static final SupportingInfoReasonEnum DEFAULT_REASON = SupportingInfoReasonEnum.Missing_info;
    private static final SupportingInfoReasonEnum UPDATED_REASON = SupportingInfoReasonEnum.Missing_attach;

    private static final SupportingInfoReasonMissingToothEnum DEFAULT_REASON_MISSING_TOOTH = SupportingInfoReasonMissingToothEnum.E;
    private static final SupportingInfoReasonMissingToothEnum UPDATED_REASON_MISSING_TOOTH = SupportingInfoReasonMissingToothEnum.C;

    private static final String ENTITY_API_URL = "/api/supporting-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SupportingInfoRepository supportingInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupportingInfoMockMvc;

    private SupportingInfo supportingInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportingInfo createEntity(EntityManager em) {
        SupportingInfo supportingInfo = new SupportingInfo()
            .sequence(DEFAULT_SEQUENCE)
            .codeLOINC(DEFAULT_CODE_LOINC)
            .category(DEFAULT_CATEGORY)
            .codeVisit(DEFAULT_CODE_VISIT)
            .codeFdiOral(DEFAULT_CODE_FDI_ORAL)
            .timing(DEFAULT_TIMING)
            .timingEnd(DEFAULT_TIMING_END)
            .valueBoolean(DEFAULT_VALUE_BOOLEAN)
            .valueString(DEFAULT_VALUE_STRING)
            .reason(DEFAULT_REASON)
            .reasonMissingTooth(DEFAULT_REASON_MISSING_TOOTH);
        return supportingInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupportingInfo createUpdatedEntity(EntityManager em) {
        SupportingInfo supportingInfo = new SupportingInfo()
            .sequence(UPDATED_SEQUENCE)
            .codeLOINC(UPDATED_CODE_LOINC)
            .category(UPDATED_CATEGORY)
            .codeVisit(UPDATED_CODE_VISIT)
            .codeFdiOral(UPDATED_CODE_FDI_ORAL)
            .timing(UPDATED_TIMING)
            .timingEnd(UPDATED_TIMING_END)
            .valueBoolean(UPDATED_VALUE_BOOLEAN)
            .valueString(UPDATED_VALUE_STRING)
            .reason(UPDATED_REASON)
            .reasonMissingTooth(UPDATED_REASON_MISSING_TOOTH);
        return supportingInfo;
    }

    @BeforeEach
    public void initTest() {
        supportingInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createSupportingInfo() throws Exception {
        int databaseSizeBeforeCreate = supportingInfoRepository.findAll().size();
        // Create the SupportingInfo
        restSupportingInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supportingInfo))
            )
            .andExpect(status().isCreated());

        // Validate the SupportingInfo in the database
        List<SupportingInfo> supportingInfoList = supportingInfoRepository.findAll();
        assertThat(supportingInfoList).hasSize(databaseSizeBeforeCreate + 1);
        SupportingInfo testSupportingInfo = supportingInfoList.get(supportingInfoList.size() - 1);
        assertThat(testSupportingInfo.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testSupportingInfo.getCodeLOINC()).isEqualTo(DEFAULT_CODE_LOINC);
        assertThat(testSupportingInfo.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testSupportingInfo.getCodeVisit()).isEqualTo(DEFAULT_CODE_VISIT);
        assertThat(testSupportingInfo.getCodeFdiOral()).isEqualTo(DEFAULT_CODE_FDI_ORAL);
        assertThat(testSupportingInfo.getTiming()).isEqualTo(DEFAULT_TIMING);
        assertThat(testSupportingInfo.getTimingEnd()).isEqualTo(DEFAULT_TIMING_END);
        assertThat(testSupportingInfo.getValueBoolean()).isEqualTo(DEFAULT_VALUE_BOOLEAN);
        assertThat(testSupportingInfo.getValueString()).isEqualTo(DEFAULT_VALUE_STRING);
        assertThat(testSupportingInfo.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testSupportingInfo.getReasonMissingTooth()).isEqualTo(DEFAULT_REASON_MISSING_TOOTH);
    }

    @Test
    @Transactional
    void createSupportingInfoWithExistingId() throws Exception {
        // Create the SupportingInfo with an existing ID
        supportingInfo.setId(1L);

        int databaseSizeBeforeCreate = supportingInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupportingInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supportingInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportingInfo in the database
        List<SupportingInfo> supportingInfoList = supportingInfoRepository.findAll();
        assertThat(supportingInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSupportingInfos() throws Exception {
        // Initialize the database
        supportingInfoRepository.saveAndFlush(supportingInfo);

        // Get all the supportingInfoList
        restSupportingInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supportingInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].codeLOINC").value(hasItem(DEFAULT_CODE_LOINC)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].codeVisit").value(hasItem(DEFAULT_CODE_VISIT.toString())))
            .andExpect(jsonPath("$.[*].codeFdiOral").value(hasItem(DEFAULT_CODE_FDI_ORAL.toString())))
            .andExpect(jsonPath("$.[*].timing").value(hasItem(DEFAULT_TIMING.toString())))
            .andExpect(jsonPath("$.[*].timingEnd").value(hasItem(DEFAULT_TIMING_END.toString())))
            .andExpect(jsonPath("$.[*].valueBoolean").value(hasItem(DEFAULT_VALUE_BOOLEAN.booleanValue())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].reasonMissingTooth").value(hasItem(DEFAULT_REASON_MISSING_TOOTH.toString())));
    }

    @Test
    @Transactional
    void getSupportingInfo() throws Exception {
        // Initialize the database
        supportingInfoRepository.saveAndFlush(supportingInfo);

        // Get the supportingInfo
        restSupportingInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, supportingInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supportingInfo.getId().intValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.codeLOINC").value(DEFAULT_CODE_LOINC))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.codeVisit").value(DEFAULT_CODE_VISIT.toString()))
            .andExpect(jsonPath("$.codeFdiOral").value(DEFAULT_CODE_FDI_ORAL.toString()))
            .andExpect(jsonPath("$.timing").value(DEFAULT_TIMING.toString()))
            .andExpect(jsonPath("$.timingEnd").value(DEFAULT_TIMING_END.toString()))
            .andExpect(jsonPath("$.valueBoolean").value(DEFAULT_VALUE_BOOLEAN.booleanValue()))
            .andExpect(jsonPath("$.valueString").value(DEFAULT_VALUE_STRING))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.reasonMissingTooth").value(DEFAULT_REASON_MISSING_TOOTH.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSupportingInfo() throws Exception {
        // Get the supportingInfo
        restSupportingInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSupportingInfo() throws Exception {
        // Initialize the database
        supportingInfoRepository.saveAndFlush(supportingInfo);

        int databaseSizeBeforeUpdate = supportingInfoRepository.findAll().size();

        // Update the supportingInfo
        SupportingInfo updatedSupportingInfo = supportingInfoRepository.findById(supportingInfo.getId()).get();
        // Disconnect from session so that the updates on updatedSupportingInfo are not directly saved in db
        em.detach(updatedSupportingInfo);
        updatedSupportingInfo
            .sequence(UPDATED_SEQUENCE)
            .codeLOINC(UPDATED_CODE_LOINC)
            .category(UPDATED_CATEGORY)
            .codeVisit(UPDATED_CODE_VISIT)
            .codeFdiOral(UPDATED_CODE_FDI_ORAL)
            .timing(UPDATED_TIMING)
            .timingEnd(UPDATED_TIMING_END)
            .valueBoolean(UPDATED_VALUE_BOOLEAN)
            .valueString(UPDATED_VALUE_STRING)
            .reason(UPDATED_REASON)
            .reasonMissingTooth(UPDATED_REASON_MISSING_TOOTH);

        restSupportingInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSupportingInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSupportingInfo))
            )
            .andExpect(status().isOk());

        // Validate the SupportingInfo in the database
        List<SupportingInfo> supportingInfoList = supportingInfoRepository.findAll();
        assertThat(supportingInfoList).hasSize(databaseSizeBeforeUpdate);
        SupportingInfo testSupportingInfo = supportingInfoList.get(supportingInfoList.size() - 1);
        assertThat(testSupportingInfo.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testSupportingInfo.getCodeLOINC()).isEqualTo(UPDATED_CODE_LOINC);
        assertThat(testSupportingInfo.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSupportingInfo.getCodeVisit()).isEqualTo(UPDATED_CODE_VISIT);
        assertThat(testSupportingInfo.getCodeFdiOral()).isEqualTo(UPDATED_CODE_FDI_ORAL);
        assertThat(testSupportingInfo.getTiming()).isEqualTo(UPDATED_TIMING);
        assertThat(testSupportingInfo.getTimingEnd()).isEqualTo(UPDATED_TIMING_END);
        assertThat(testSupportingInfo.getValueBoolean()).isEqualTo(UPDATED_VALUE_BOOLEAN);
        assertThat(testSupportingInfo.getValueString()).isEqualTo(UPDATED_VALUE_STRING);
        assertThat(testSupportingInfo.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testSupportingInfo.getReasonMissingTooth()).isEqualTo(UPDATED_REASON_MISSING_TOOTH);
    }

    @Test
    @Transactional
    void putNonExistingSupportingInfo() throws Exception {
        int databaseSizeBeforeUpdate = supportingInfoRepository.findAll().size();
        supportingInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportingInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supportingInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportingInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportingInfo in the database
        List<SupportingInfo> supportingInfoList = supportingInfoRepository.findAll();
        assertThat(supportingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSupportingInfo() throws Exception {
        int databaseSizeBeforeUpdate = supportingInfoRepository.findAll().size();
        supportingInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupportingInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supportingInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportingInfo in the database
        List<SupportingInfo> supportingInfoList = supportingInfoRepository.findAll();
        assertThat(supportingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSupportingInfo() throws Exception {
        int databaseSizeBeforeUpdate = supportingInfoRepository.findAll().size();
        supportingInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupportingInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supportingInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupportingInfo in the database
        List<SupportingInfo> supportingInfoList = supportingInfoRepository.findAll();
        assertThat(supportingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSupportingInfoWithPatch() throws Exception {
        // Initialize the database
        supportingInfoRepository.saveAndFlush(supportingInfo);

        int databaseSizeBeforeUpdate = supportingInfoRepository.findAll().size();

        // Update the supportingInfo using partial update
        SupportingInfo partialUpdatedSupportingInfo = new SupportingInfo();
        partialUpdatedSupportingInfo.setId(supportingInfo.getId());

        partialUpdatedSupportingInfo
            .codeLOINC(UPDATED_CODE_LOINC)
            .codeFdiOral(UPDATED_CODE_FDI_ORAL)
            .timingEnd(UPDATED_TIMING_END)
            .valueBoolean(UPDATED_VALUE_BOOLEAN);

        restSupportingInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupportingInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupportingInfo))
            )
            .andExpect(status().isOk());

        // Validate the SupportingInfo in the database
        List<SupportingInfo> supportingInfoList = supportingInfoRepository.findAll();
        assertThat(supportingInfoList).hasSize(databaseSizeBeforeUpdate);
        SupportingInfo testSupportingInfo = supportingInfoList.get(supportingInfoList.size() - 1);
        assertThat(testSupportingInfo.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testSupportingInfo.getCodeLOINC()).isEqualTo(UPDATED_CODE_LOINC);
        assertThat(testSupportingInfo.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testSupportingInfo.getCodeVisit()).isEqualTo(DEFAULT_CODE_VISIT);
        assertThat(testSupportingInfo.getCodeFdiOral()).isEqualTo(UPDATED_CODE_FDI_ORAL);
        assertThat(testSupportingInfo.getTiming()).isEqualTo(DEFAULT_TIMING);
        assertThat(testSupportingInfo.getTimingEnd()).isEqualTo(UPDATED_TIMING_END);
        assertThat(testSupportingInfo.getValueBoolean()).isEqualTo(UPDATED_VALUE_BOOLEAN);
        assertThat(testSupportingInfo.getValueString()).isEqualTo(DEFAULT_VALUE_STRING);
        assertThat(testSupportingInfo.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testSupportingInfo.getReasonMissingTooth()).isEqualTo(DEFAULT_REASON_MISSING_TOOTH);
    }

    @Test
    @Transactional
    void fullUpdateSupportingInfoWithPatch() throws Exception {
        // Initialize the database
        supportingInfoRepository.saveAndFlush(supportingInfo);

        int databaseSizeBeforeUpdate = supportingInfoRepository.findAll().size();

        // Update the supportingInfo using partial update
        SupportingInfo partialUpdatedSupportingInfo = new SupportingInfo();
        partialUpdatedSupportingInfo.setId(supportingInfo.getId());

        partialUpdatedSupportingInfo
            .sequence(UPDATED_SEQUENCE)
            .codeLOINC(UPDATED_CODE_LOINC)
            .category(UPDATED_CATEGORY)
            .codeVisit(UPDATED_CODE_VISIT)
            .codeFdiOral(UPDATED_CODE_FDI_ORAL)
            .timing(UPDATED_TIMING)
            .timingEnd(UPDATED_TIMING_END)
            .valueBoolean(UPDATED_VALUE_BOOLEAN)
            .valueString(UPDATED_VALUE_STRING)
            .reason(UPDATED_REASON)
            .reasonMissingTooth(UPDATED_REASON_MISSING_TOOTH);

        restSupportingInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupportingInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupportingInfo))
            )
            .andExpect(status().isOk());

        // Validate the SupportingInfo in the database
        List<SupportingInfo> supportingInfoList = supportingInfoRepository.findAll();
        assertThat(supportingInfoList).hasSize(databaseSizeBeforeUpdate);
        SupportingInfo testSupportingInfo = supportingInfoList.get(supportingInfoList.size() - 1);
        assertThat(testSupportingInfo.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testSupportingInfo.getCodeLOINC()).isEqualTo(UPDATED_CODE_LOINC);
        assertThat(testSupportingInfo.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSupportingInfo.getCodeVisit()).isEqualTo(UPDATED_CODE_VISIT);
        assertThat(testSupportingInfo.getCodeFdiOral()).isEqualTo(UPDATED_CODE_FDI_ORAL);
        assertThat(testSupportingInfo.getTiming()).isEqualTo(UPDATED_TIMING);
        assertThat(testSupportingInfo.getTimingEnd()).isEqualTo(UPDATED_TIMING_END);
        assertThat(testSupportingInfo.getValueBoolean()).isEqualTo(UPDATED_VALUE_BOOLEAN);
        assertThat(testSupportingInfo.getValueString()).isEqualTo(UPDATED_VALUE_STRING);
        assertThat(testSupportingInfo.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testSupportingInfo.getReasonMissingTooth()).isEqualTo(UPDATED_REASON_MISSING_TOOTH);
    }

    @Test
    @Transactional
    void patchNonExistingSupportingInfo() throws Exception {
        int databaseSizeBeforeUpdate = supportingInfoRepository.findAll().size();
        supportingInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupportingInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supportingInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supportingInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportingInfo in the database
        List<SupportingInfo> supportingInfoList = supportingInfoRepository.findAll();
        assertThat(supportingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSupportingInfo() throws Exception {
        int databaseSizeBeforeUpdate = supportingInfoRepository.findAll().size();
        supportingInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupportingInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supportingInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupportingInfo in the database
        List<SupportingInfo> supportingInfoList = supportingInfoRepository.findAll();
        assertThat(supportingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSupportingInfo() throws Exception {
        int databaseSizeBeforeUpdate = supportingInfoRepository.findAll().size();
        supportingInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupportingInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(supportingInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupportingInfo in the database
        List<SupportingInfo> supportingInfoList = supportingInfoRepository.findAll();
        assertThat(supportingInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSupportingInfo() throws Exception {
        // Initialize the database
        supportingInfoRepository.saveAndFlush(supportingInfo);

        int databaseSizeBeforeDelete = supportingInfoRepository.findAll().size();

        // Delete the supportingInfo
        restSupportingInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, supportingInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupportingInfo> supportingInfoList = supportingInfoRepository.findAll();
        assertThat(supportingInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
