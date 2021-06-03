package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ListSpecialtyEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.SpecialtyEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ListSpecialtyEnumRepository;
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
 * Integration tests for the {@link ListSpecialtyEnumResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ListSpecialtyEnumResourceIT {

    private static final SpecialtyEnum DEFAULT_S = SpecialtyEnum.DOT01_00;
    private static final SpecialtyEnum UPDATED_S = SpecialtyEnum.DOT01_01;

    private static final String ENTITY_API_URL = "/api/list-specialty-enums";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ListSpecialtyEnumRepository listSpecialtyEnumRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListSpecialtyEnumMockMvc;

    private ListSpecialtyEnum listSpecialtyEnum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListSpecialtyEnum createEntity(EntityManager em) {
        ListSpecialtyEnum listSpecialtyEnum = new ListSpecialtyEnum().s(DEFAULT_S);
        return listSpecialtyEnum;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListSpecialtyEnum createUpdatedEntity(EntityManager em) {
        ListSpecialtyEnum listSpecialtyEnum = new ListSpecialtyEnum().s(UPDATED_S);
        return listSpecialtyEnum;
    }

    @BeforeEach
    public void initTest() {
        listSpecialtyEnum = createEntity(em);
    }

    @Test
    @Transactional
    void createListSpecialtyEnum() throws Exception {
        int databaseSizeBeforeCreate = listSpecialtyEnumRepository.findAll().size();
        // Create the ListSpecialtyEnum
        restListSpecialtyEnumMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listSpecialtyEnum))
            )
            .andExpect(status().isCreated());

        // Validate the ListSpecialtyEnum in the database
        List<ListSpecialtyEnum> listSpecialtyEnumList = listSpecialtyEnumRepository.findAll();
        assertThat(listSpecialtyEnumList).hasSize(databaseSizeBeforeCreate + 1);
        ListSpecialtyEnum testListSpecialtyEnum = listSpecialtyEnumList.get(listSpecialtyEnumList.size() - 1);
        assertThat(testListSpecialtyEnum.getS()).isEqualTo(DEFAULT_S);
    }

    @Test
    @Transactional
    void createListSpecialtyEnumWithExistingId() throws Exception {
        // Create the ListSpecialtyEnum with an existing ID
        listSpecialtyEnum.setId(1L);

        int databaseSizeBeforeCreate = listSpecialtyEnumRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListSpecialtyEnumMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listSpecialtyEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListSpecialtyEnum in the database
        List<ListSpecialtyEnum> listSpecialtyEnumList = listSpecialtyEnumRepository.findAll();
        assertThat(listSpecialtyEnumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllListSpecialtyEnums() throws Exception {
        // Initialize the database
        listSpecialtyEnumRepository.saveAndFlush(listSpecialtyEnum);

        // Get all the listSpecialtyEnumList
        restListSpecialtyEnumMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listSpecialtyEnum.getId().intValue())))
            .andExpect(jsonPath("$.[*].s").value(hasItem(DEFAULT_S.toString())));
    }

    @Test
    @Transactional
    void getListSpecialtyEnum() throws Exception {
        // Initialize the database
        listSpecialtyEnumRepository.saveAndFlush(listSpecialtyEnum);

        // Get the listSpecialtyEnum
        restListSpecialtyEnumMockMvc
            .perform(get(ENTITY_API_URL_ID, listSpecialtyEnum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listSpecialtyEnum.getId().intValue()))
            .andExpect(jsonPath("$.s").value(DEFAULT_S.toString()));
    }

    @Test
    @Transactional
    void getNonExistingListSpecialtyEnum() throws Exception {
        // Get the listSpecialtyEnum
        restListSpecialtyEnumMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewListSpecialtyEnum() throws Exception {
        // Initialize the database
        listSpecialtyEnumRepository.saveAndFlush(listSpecialtyEnum);

        int databaseSizeBeforeUpdate = listSpecialtyEnumRepository.findAll().size();

        // Update the listSpecialtyEnum
        ListSpecialtyEnum updatedListSpecialtyEnum = listSpecialtyEnumRepository.findById(listSpecialtyEnum.getId()).get();
        // Disconnect from session so that the updates on updatedListSpecialtyEnum are not directly saved in db
        em.detach(updatedListSpecialtyEnum);
        updatedListSpecialtyEnum.s(UPDATED_S);

        restListSpecialtyEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedListSpecialtyEnum.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedListSpecialtyEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListSpecialtyEnum in the database
        List<ListSpecialtyEnum> listSpecialtyEnumList = listSpecialtyEnumRepository.findAll();
        assertThat(listSpecialtyEnumList).hasSize(databaseSizeBeforeUpdate);
        ListSpecialtyEnum testListSpecialtyEnum = listSpecialtyEnumList.get(listSpecialtyEnumList.size() - 1);
        assertThat(testListSpecialtyEnum.getS()).isEqualTo(UPDATED_S);
    }

    @Test
    @Transactional
    void putNonExistingListSpecialtyEnum() throws Exception {
        int databaseSizeBeforeUpdate = listSpecialtyEnumRepository.findAll().size();
        listSpecialtyEnum.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListSpecialtyEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listSpecialtyEnum.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listSpecialtyEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListSpecialtyEnum in the database
        List<ListSpecialtyEnum> listSpecialtyEnumList = listSpecialtyEnumRepository.findAll();
        assertThat(listSpecialtyEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListSpecialtyEnum() throws Exception {
        int databaseSizeBeforeUpdate = listSpecialtyEnumRepository.findAll().size();
        listSpecialtyEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListSpecialtyEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listSpecialtyEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListSpecialtyEnum in the database
        List<ListSpecialtyEnum> listSpecialtyEnumList = listSpecialtyEnumRepository.findAll();
        assertThat(listSpecialtyEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListSpecialtyEnum() throws Exception {
        int databaseSizeBeforeUpdate = listSpecialtyEnumRepository.findAll().size();
        listSpecialtyEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListSpecialtyEnumMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listSpecialtyEnum))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListSpecialtyEnum in the database
        List<ListSpecialtyEnum> listSpecialtyEnumList = listSpecialtyEnumRepository.findAll();
        assertThat(listSpecialtyEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListSpecialtyEnumWithPatch() throws Exception {
        // Initialize the database
        listSpecialtyEnumRepository.saveAndFlush(listSpecialtyEnum);

        int databaseSizeBeforeUpdate = listSpecialtyEnumRepository.findAll().size();

        // Update the listSpecialtyEnum using partial update
        ListSpecialtyEnum partialUpdatedListSpecialtyEnum = new ListSpecialtyEnum();
        partialUpdatedListSpecialtyEnum.setId(listSpecialtyEnum.getId());

        restListSpecialtyEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListSpecialtyEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListSpecialtyEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListSpecialtyEnum in the database
        List<ListSpecialtyEnum> listSpecialtyEnumList = listSpecialtyEnumRepository.findAll();
        assertThat(listSpecialtyEnumList).hasSize(databaseSizeBeforeUpdate);
        ListSpecialtyEnum testListSpecialtyEnum = listSpecialtyEnumList.get(listSpecialtyEnumList.size() - 1);
        assertThat(testListSpecialtyEnum.getS()).isEqualTo(DEFAULT_S);
    }

    @Test
    @Transactional
    void fullUpdateListSpecialtyEnumWithPatch() throws Exception {
        // Initialize the database
        listSpecialtyEnumRepository.saveAndFlush(listSpecialtyEnum);

        int databaseSizeBeforeUpdate = listSpecialtyEnumRepository.findAll().size();

        // Update the listSpecialtyEnum using partial update
        ListSpecialtyEnum partialUpdatedListSpecialtyEnum = new ListSpecialtyEnum();
        partialUpdatedListSpecialtyEnum.setId(listSpecialtyEnum.getId());

        partialUpdatedListSpecialtyEnum.s(UPDATED_S);

        restListSpecialtyEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListSpecialtyEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListSpecialtyEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListSpecialtyEnum in the database
        List<ListSpecialtyEnum> listSpecialtyEnumList = listSpecialtyEnumRepository.findAll();
        assertThat(listSpecialtyEnumList).hasSize(databaseSizeBeforeUpdate);
        ListSpecialtyEnum testListSpecialtyEnum = listSpecialtyEnumList.get(listSpecialtyEnumList.size() - 1);
        assertThat(testListSpecialtyEnum.getS()).isEqualTo(UPDATED_S);
    }

    @Test
    @Transactional
    void patchNonExistingListSpecialtyEnum() throws Exception {
        int databaseSizeBeforeUpdate = listSpecialtyEnumRepository.findAll().size();
        listSpecialtyEnum.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListSpecialtyEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listSpecialtyEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listSpecialtyEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListSpecialtyEnum in the database
        List<ListSpecialtyEnum> listSpecialtyEnumList = listSpecialtyEnumRepository.findAll();
        assertThat(listSpecialtyEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListSpecialtyEnum() throws Exception {
        int databaseSizeBeforeUpdate = listSpecialtyEnumRepository.findAll().size();
        listSpecialtyEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListSpecialtyEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listSpecialtyEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListSpecialtyEnum in the database
        List<ListSpecialtyEnum> listSpecialtyEnumList = listSpecialtyEnumRepository.findAll();
        assertThat(listSpecialtyEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListSpecialtyEnum() throws Exception {
        int databaseSizeBeforeUpdate = listSpecialtyEnumRepository.findAll().size();
        listSpecialtyEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListSpecialtyEnumMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listSpecialtyEnum))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListSpecialtyEnum in the database
        List<ListSpecialtyEnum> listSpecialtyEnumList = listSpecialtyEnumRepository.findAll();
        assertThat(listSpecialtyEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListSpecialtyEnum() throws Exception {
        // Initialize the database
        listSpecialtyEnumRepository.saveAndFlush(listSpecialtyEnum);

        int databaseSizeBeforeDelete = listSpecialtyEnumRepository.findAll().size();

        // Delete the listSpecialtyEnum
        restListSpecialtyEnumMockMvc
            .perform(delete(ENTITY_API_URL_ID, listSpecialtyEnum.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListSpecialtyEnum> listSpecialtyEnumList = listSpecialtyEnumRepository.findAll();
        assertThat(listSpecialtyEnumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
