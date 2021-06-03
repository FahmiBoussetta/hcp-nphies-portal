package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ListEligibilityPurposeEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.EligibilityPurposeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ListEligibilityPurposeEnumRepository;
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
 * Integration tests for the {@link ListEligibilityPurposeEnumResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ListEligibilityPurposeEnumResourceIT {

    private static final EligibilityPurposeEnum DEFAULT_ERP = EligibilityPurposeEnum.Benefits;
    private static final EligibilityPurposeEnum UPDATED_ERP = EligibilityPurposeEnum.Discovery;

    private static final String ENTITY_API_URL = "/api/list-eligibility-purpose-enums";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ListEligibilityPurposeEnumRepository listEligibilityPurposeEnumRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListEligibilityPurposeEnumMockMvc;

    private ListEligibilityPurposeEnum listEligibilityPurposeEnum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListEligibilityPurposeEnum createEntity(EntityManager em) {
        ListEligibilityPurposeEnum listEligibilityPurposeEnum = new ListEligibilityPurposeEnum().erp(DEFAULT_ERP);
        return listEligibilityPurposeEnum;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListEligibilityPurposeEnum createUpdatedEntity(EntityManager em) {
        ListEligibilityPurposeEnum listEligibilityPurposeEnum = new ListEligibilityPurposeEnum().erp(UPDATED_ERP);
        return listEligibilityPurposeEnum;
    }

    @BeforeEach
    public void initTest() {
        listEligibilityPurposeEnum = createEntity(em);
    }

    @Test
    @Transactional
    void createListEligibilityPurposeEnum() throws Exception {
        int databaseSizeBeforeCreate = listEligibilityPurposeEnumRepository.findAll().size();
        // Create the ListEligibilityPurposeEnum
        restListEligibilityPurposeEnumMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listEligibilityPurposeEnum))
            )
            .andExpect(status().isCreated());

        // Validate the ListEligibilityPurposeEnum in the database
        List<ListEligibilityPurposeEnum> listEligibilityPurposeEnumList = listEligibilityPurposeEnumRepository.findAll();
        assertThat(listEligibilityPurposeEnumList).hasSize(databaseSizeBeforeCreate + 1);
        ListEligibilityPurposeEnum testListEligibilityPurposeEnum = listEligibilityPurposeEnumList.get(
            listEligibilityPurposeEnumList.size() - 1
        );
        assertThat(testListEligibilityPurposeEnum.getErp()).isEqualTo(DEFAULT_ERP);
    }

    @Test
    @Transactional
    void createListEligibilityPurposeEnumWithExistingId() throws Exception {
        // Create the ListEligibilityPurposeEnum with an existing ID
        listEligibilityPurposeEnum.setId(1L);

        int databaseSizeBeforeCreate = listEligibilityPurposeEnumRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListEligibilityPurposeEnumMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listEligibilityPurposeEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListEligibilityPurposeEnum in the database
        List<ListEligibilityPurposeEnum> listEligibilityPurposeEnumList = listEligibilityPurposeEnumRepository.findAll();
        assertThat(listEligibilityPurposeEnumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllListEligibilityPurposeEnums() throws Exception {
        // Initialize the database
        listEligibilityPurposeEnumRepository.saveAndFlush(listEligibilityPurposeEnum);

        // Get all the listEligibilityPurposeEnumList
        restListEligibilityPurposeEnumMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listEligibilityPurposeEnum.getId().intValue())))
            .andExpect(jsonPath("$.[*].erp").value(hasItem(DEFAULT_ERP.toString())));
    }

    @Test
    @Transactional
    void getListEligibilityPurposeEnum() throws Exception {
        // Initialize the database
        listEligibilityPurposeEnumRepository.saveAndFlush(listEligibilityPurposeEnum);

        // Get the listEligibilityPurposeEnum
        restListEligibilityPurposeEnumMockMvc
            .perform(get(ENTITY_API_URL_ID, listEligibilityPurposeEnum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listEligibilityPurposeEnum.getId().intValue()))
            .andExpect(jsonPath("$.erp").value(DEFAULT_ERP.toString()));
    }

    @Test
    @Transactional
    void getNonExistingListEligibilityPurposeEnum() throws Exception {
        // Get the listEligibilityPurposeEnum
        restListEligibilityPurposeEnumMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewListEligibilityPurposeEnum() throws Exception {
        // Initialize the database
        listEligibilityPurposeEnumRepository.saveAndFlush(listEligibilityPurposeEnum);

        int databaseSizeBeforeUpdate = listEligibilityPurposeEnumRepository.findAll().size();

        // Update the listEligibilityPurposeEnum
        ListEligibilityPurposeEnum updatedListEligibilityPurposeEnum = listEligibilityPurposeEnumRepository
            .findById(listEligibilityPurposeEnum.getId())
            .get();
        // Disconnect from session so that the updates on updatedListEligibilityPurposeEnum are not directly saved in db
        em.detach(updatedListEligibilityPurposeEnum);
        updatedListEligibilityPurposeEnum.erp(UPDATED_ERP);

        restListEligibilityPurposeEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedListEligibilityPurposeEnum.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedListEligibilityPurposeEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListEligibilityPurposeEnum in the database
        List<ListEligibilityPurposeEnum> listEligibilityPurposeEnumList = listEligibilityPurposeEnumRepository.findAll();
        assertThat(listEligibilityPurposeEnumList).hasSize(databaseSizeBeforeUpdate);
        ListEligibilityPurposeEnum testListEligibilityPurposeEnum = listEligibilityPurposeEnumList.get(
            listEligibilityPurposeEnumList.size() - 1
        );
        assertThat(testListEligibilityPurposeEnum.getErp()).isEqualTo(UPDATED_ERP);
    }

    @Test
    @Transactional
    void putNonExistingListEligibilityPurposeEnum() throws Exception {
        int databaseSizeBeforeUpdate = listEligibilityPurposeEnumRepository.findAll().size();
        listEligibilityPurposeEnum.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListEligibilityPurposeEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listEligibilityPurposeEnum.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listEligibilityPurposeEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListEligibilityPurposeEnum in the database
        List<ListEligibilityPurposeEnum> listEligibilityPurposeEnumList = listEligibilityPurposeEnumRepository.findAll();
        assertThat(listEligibilityPurposeEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListEligibilityPurposeEnum() throws Exception {
        int databaseSizeBeforeUpdate = listEligibilityPurposeEnumRepository.findAll().size();
        listEligibilityPurposeEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListEligibilityPurposeEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listEligibilityPurposeEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListEligibilityPurposeEnum in the database
        List<ListEligibilityPurposeEnum> listEligibilityPurposeEnumList = listEligibilityPurposeEnumRepository.findAll();
        assertThat(listEligibilityPurposeEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListEligibilityPurposeEnum() throws Exception {
        int databaseSizeBeforeUpdate = listEligibilityPurposeEnumRepository.findAll().size();
        listEligibilityPurposeEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListEligibilityPurposeEnumMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listEligibilityPurposeEnum))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListEligibilityPurposeEnum in the database
        List<ListEligibilityPurposeEnum> listEligibilityPurposeEnumList = listEligibilityPurposeEnumRepository.findAll();
        assertThat(listEligibilityPurposeEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListEligibilityPurposeEnumWithPatch() throws Exception {
        // Initialize the database
        listEligibilityPurposeEnumRepository.saveAndFlush(listEligibilityPurposeEnum);

        int databaseSizeBeforeUpdate = listEligibilityPurposeEnumRepository.findAll().size();

        // Update the listEligibilityPurposeEnum using partial update
        ListEligibilityPurposeEnum partialUpdatedListEligibilityPurposeEnum = new ListEligibilityPurposeEnum();
        partialUpdatedListEligibilityPurposeEnum.setId(listEligibilityPurposeEnum.getId());

        partialUpdatedListEligibilityPurposeEnum.erp(UPDATED_ERP);

        restListEligibilityPurposeEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListEligibilityPurposeEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListEligibilityPurposeEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListEligibilityPurposeEnum in the database
        List<ListEligibilityPurposeEnum> listEligibilityPurposeEnumList = listEligibilityPurposeEnumRepository.findAll();
        assertThat(listEligibilityPurposeEnumList).hasSize(databaseSizeBeforeUpdate);
        ListEligibilityPurposeEnum testListEligibilityPurposeEnum = listEligibilityPurposeEnumList.get(
            listEligibilityPurposeEnumList.size() - 1
        );
        assertThat(testListEligibilityPurposeEnum.getErp()).isEqualTo(UPDATED_ERP);
    }

    @Test
    @Transactional
    void fullUpdateListEligibilityPurposeEnumWithPatch() throws Exception {
        // Initialize the database
        listEligibilityPurposeEnumRepository.saveAndFlush(listEligibilityPurposeEnum);

        int databaseSizeBeforeUpdate = listEligibilityPurposeEnumRepository.findAll().size();

        // Update the listEligibilityPurposeEnum using partial update
        ListEligibilityPurposeEnum partialUpdatedListEligibilityPurposeEnum = new ListEligibilityPurposeEnum();
        partialUpdatedListEligibilityPurposeEnum.setId(listEligibilityPurposeEnum.getId());

        partialUpdatedListEligibilityPurposeEnum.erp(UPDATED_ERP);

        restListEligibilityPurposeEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListEligibilityPurposeEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListEligibilityPurposeEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListEligibilityPurposeEnum in the database
        List<ListEligibilityPurposeEnum> listEligibilityPurposeEnumList = listEligibilityPurposeEnumRepository.findAll();
        assertThat(listEligibilityPurposeEnumList).hasSize(databaseSizeBeforeUpdate);
        ListEligibilityPurposeEnum testListEligibilityPurposeEnum = listEligibilityPurposeEnumList.get(
            listEligibilityPurposeEnumList.size() - 1
        );
        assertThat(testListEligibilityPurposeEnum.getErp()).isEqualTo(UPDATED_ERP);
    }

    @Test
    @Transactional
    void patchNonExistingListEligibilityPurposeEnum() throws Exception {
        int databaseSizeBeforeUpdate = listEligibilityPurposeEnumRepository.findAll().size();
        listEligibilityPurposeEnum.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListEligibilityPurposeEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listEligibilityPurposeEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listEligibilityPurposeEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListEligibilityPurposeEnum in the database
        List<ListEligibilityPurposeEnum> listEligibilityPurposeEnumList = listEligibilityPurposeEnumRepository.findAll();
        assertThat(listEligibilityPurposeEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListEligibilityPurposeEnum() throws Exception {
        int databaseSizeBeforeUpdate = listEligibilityPurposeEnumRepository.findAll().size();
        listEligibilityPurposeEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListEligibilityPurposeEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listEligibilityPurposeEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListEligibilityPurposeEnum in the database
        List<ListEligibilityPurposeEnum> listEligibilityPurposeEnumList = listEligibilityPurposeEnumRepository.findAll();
        assertThat(listEligibilityPurposeEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListEligibilityPurposeEnum() throws Exception {
        int databaseSizeBeforeUpdate = listEligibilityPurposeEnumRepository.findAll().size();
        listEligibilityPurposeEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListEligibilityPurposeEnumMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listEligibilityPurposeEnum))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListEligibilityPurposeEnum in the database
        List<ListEligibilityPurposeEnum> listEligibilityPurposeEnumList = listEligibilityPurposeEnumRepository.findAll();
        assertThat(listEligibilityPurposeEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListEligibilityPurposeEnum() throws Exception {
        // Initialize the database
        listEligibilityPurposeEnumRepository.saveAndFlush(listEligibilityPurposeEnum);

        int databaseSizeBeforeDelete = listEligibilityPurposeEnumRepository.findAll().size();

        // Delete the listEligibilityPurposeEnum
        restListEligibilityPurposeEnumMockMvc
            .perform(delete(ENTITY_API_URL_ID, listEligibilityPurposeEnum.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListEligibilityPurposeEnum> listEligibilityPurposeEnumList = listEligibilityPurposeEnumRepository.findAll();
        assertThat(listEligibilityPurposeEnumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
