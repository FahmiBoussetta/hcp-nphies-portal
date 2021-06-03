package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ListCommunicationReasonEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.CommunicationReasonEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ListCommunicationReasonEnumRepository;
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
 * Integration tests for the {@link ListCommunicationReasonEnumResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ListCommunicationReasonEnumResourceIT {

    private static final CommunicationReasonEnum DEFAULT_CR = CommunicationReasonEnum.Missing_info;
    private static final CommunicationReasonEnum UPDATED_CR = CommunicationReasonEnum.Missing_attach;

    private static final String ENTITY_API_URL = "/api/list-communication-reason-enums";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ListCommunicationReasonEnumRepository listCommunicationReasonEnumRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListCommunicationReasonEnumMockMvc;

    private ListCommunicationReasonEnum listCommunicationReasonEnum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListCommunicationReasonEnum createEntity(EntityManager em) {
        ListCommunicationReasonEnum listCommunicationReasonEnum = new ListCommunicationReasonEnum().cr(DEFAULT_CR);
        return listCommunicationReasonEnum;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListCommunicationReasonEnum createUpdatedEntity(EntityManager em) {
        ListCommunicationReasonEnum listCommunicationReasonEnum = new ListCommunicationReasonEnum().cr(UPDATED_CR);
        return listCommunicationReasonEnum;
    }

    @BeforeEach
    public void initTest() {
        listCommunicationReasonEnum = createEntity(em);
    }

    @Test
    @Transactional
    void createListCommunicationReasonEnum() throws Exception {
        int databaseSizeBeforeCreate = listCommunicationReasonEnumRepository.findAll().size();
        // Create the ListCommunicationReasonEnum
        restListCommunicationReasonEnumMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationReasonEnum))
            )
            .andExpect(status().isCreated());

        // Validate the ListCommunicationReasonEnum in the database
        List<ListCommunicationReasonEnum> listCommunicationReasonEnumList = listCommunicationReasonEnumRepository.findAll();
        assertThat(listCommunicationReasonEnumList).hasSize(databaseSizeBeforeCreate + 1);
        ListCommunicationReasonEnum testListCommunicationReasonEnum = listCommunicationReasonEnumList.get(
            listCommunicationReasonEnumList.size() - 1
        );
        assertThat(testListCommunicationReasonEnum.getCr()).isEqualTo(DEFAULT_CR);
    }

    @Test
    @Transactional
    void createListCommunicationReasonEnumWithExistingId() throws Exception {
        // Create the ListCommunicationReasonEnum with an existing ID
        listCommunicationReasonEnum.setId(1L);

        int databaseSizeBeforeCreate = listCommunicationReasonEnumRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListCommunicationReasonEnumMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationReasonEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListCommunicationReasonEnum in the database
        List<ListCommunicationReasonEnum> listCommunicationReasonEnumList = listCommunicationReasonEnumRepository.findAll();
        assertThat(listCommunicationReasonEnumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllListCommunicationReasonEnums() throws Exception {
        // Initialize the database
        listCommunicationReasonEnumRepository.saveAndFlush(listCommunicationReasonEnum);

        // Get all the listCommunicationReasonEnumList
        restListCommunicationReasonEnumMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listCommunicationReasonEnum.getId().intValue())))
            .andExpect(jsonPath("$.[*].cr").value(hasItem(DEFAULT_CR.toString())));
    }

    @Test
    @Transactional
    void getListCommunicationReasonEnum() throws Exception {
        // Initialize the database
        listCommunicationReasonEnumRepository.saveAndFlush(listCommunicationReasonEnum);

        // Get the listCommunicationReasonEnum
        restListCommunicationReasonEnumMockMvc
            .perform(get(ENTITY_API_URL_ID, listCommunicationReasonEnum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listCommunicationReasonEnum.getId().intValue()))
            .andExpect(jsonPath("$.cr").value(DEFAULT_CR.toString()));
    }

    @Test
    @Transactional
    void getNonExistingListCommunicationReasonEnum() throws Exception {
        // Get the listCommunicationReasonEnum
        restListCommunicationReasonEnumMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewListCommunicationReasonEnum() throws Exception {
        // Initialize the database
        listCommunicationReasonEnumRepository.saveAndFlush(listCommunicationReasonEnum);

        int databaseSizeBeforeUpdate = listCommunicationReasonEnumRepository.findAll().size();

        // Update the listCommunicationReasonEnum
        ListCommunicationReasonEnum updatedListCommunicationReasonEnum = listCommunicationReasonEnumRepository
            .findById(listCommunicationReasonEnum.getId())
            .get();
        // Disconnect from session so that the updates on updatedListCommunicationReasonEnum are not directly saved in db
        em.detach(updatedListCommunicationReasonEnum);
        updatedListCommunicationReasonEnum.cr(UPDATED_CR);

        restListCommunicationReasonEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedListCommunicationReasonEnum.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedListCommunicationReasonEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListCommunicationReasonEnum in the database
        List<ListCommunicationReasonEnum> listCommunicationReasonEnumList = listCommunicationReasonEnumRepository.findAll();
        assertThat(listCommunicationReasonEnumList).hasSize(databaseSizeBeforeUpdate);
        ListCommunicationReasonEnum testListCommunicationReasonEnum = listCommunicationReasonEnumList.get(
            listCommunicationReasonEnumList.size() - 1
        );
        assertThat(testListCommunicationReasonEnum.getCr()).isEqualTo(UPDATED_CR);
    }

    @Test
    @Transactional
    void putNonExistingListCommunicationReasonEnum() throws Exception {
        int databaseSizeBeforeUpdate = listCommunicationReasonEnumRepository.findAll().size();
        listCommunicationReasonEnum.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListCommunicationReasonEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listCommunicationReasonEnum.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationReasonEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListCommunicationReasonEnum in the database
        List<ListCommunicationReasonEnum> listCommunicationReasonEnumList = listCommunicationReasonEnumRepository.findAll();
        assertThat(listCommunicationReasonEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListCommunicationReasonEnum() throws Exception {
        int databaseSizeBeforeUpdate = listCommunicationReasonEnumRepository.findAll().size();
        listCommunicationReasonEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListCommunicationReasonEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationReasonEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListCommunicationReasonEnum in the database
        List<ListCommunicationReasonEnum> listCommunicationReasonEnumList = listCommunicationReasonEnumRepository.findAll();
        assertThat(listCommunicationReasonEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListCommunicationReasonEnum() throws Exception {
        int databaseSizeBeforeUpdate = listCommunicationReasonEnumRepository.findAll().size();
        listCommunicationReasonEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListCommunicationReasonEnumMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationReasonEnum))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListCommunicationReasonEnum in the database
        List<ListCommunicationReasonEnum> listCommunicationReasonEnumList = listCommunicationReasonEnumRepository.findAll();
        assertThat(listCommunicationReasonEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListCommunicationReasonEnumWithPatch() throws Exception {
        // Initialize the database
        listCommunicationReasonEnumRepository.saveAndFlush(listCommunicationReasonEnum);

        int databaseSizeBeforeUpdate = listCommunicationReasonEnumRepository.findAll().size();

        // Update the listCommunicationReasonEnum using partial update
        ListCommunicationReasonEnum partialUpdatedListCommunicationReasonEnum = new ListCommunicationReasonEnum();
        partialUpdatedListCommunicationReasonEnum.setId(listCommunicationReasonEnum.getId());

        partialUpdatedListCommunicationReasonEnum.cr(UPDATED_CR);

        restListCommunicationReasonEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListCommunicationReasonEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListCommunicationReasonEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListCommunicationReasonEnum in the database
        List<ListCommunicationReasonEnum> listCommunicationReasonEnumList = listCommunicationReasonEnumRepository.findAll();
        assertThat(listCommunicationReasonEnumList).hasSize(databaseSizeBeforeUpdate);
        ListCommunicationReasonEnum testListCommunicationReasonEnum = listCommunicationReasonEnumList.get(
            listCommunicationReasonEnumList.size() - 1
        );
        assertThat(testListCommunicationReasonEnum.getCr()).isEqualTo(UPDATED_CR);
    }

    @Test
    @Transactional
    void fullUpdateListCommunicationReasonEnumWithPatch() throws Exception {
        // Initialize the database
        listCommunicationReasonEnumRepository.saveAndFlush(listCommunicationReasonEnum);

        int databaseSizeBeforeUpdate = listCommunicationReasonEnumRepository.findAll().size();

        // Update the listCommunicationReasonEnum using partial update
        ListCommunicationReasonEnum partialUpdatedListCommunicationReasonEnum = new ListCommunicationReasonEnum();
        partialUpdatedListCommunicationReasonEnum.setId(listCommunicationReasonEnum.getId());

        partialUpdatedListCommunicationReasonEnum.cr(UPDATED_CR);

        restListCommunicationReasonEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListCommunicationReasonEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListCommunicationReasonEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListCommunicationReasonEnum in the database
        List<ListCommunicationReasonEnum> listCommunicationReasonEnumList = listCommunicationReasonEnumRepository.findAll();
        assertThat(listCommunicationReasonEnumList).hasSize(databaseSizeBeforeUpdate);
        ListCommunicationReasonEnum testListCommunicationReasonEnum = listCommunicationReasonEnumList.get(
            listCommunicationReasonEnumList.size() - 1
        );
        assertThat(testListCommunicationReasonEnum.getCr()).isEqualTo(UPDATED_CR);
    }

    @Test
    @Transactional
    void patchNonExistingListCommunicationReasonEnum() throws Exception {
        int databaseSizeBeforeUpdate = listCommunicationReasonEnumRepository.findAll().size();
        listCommunicationReasonEnum.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListCommunicationReasonEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listCommunicationReasonEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationReasonEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListCommunicationReasonEnum in the database
        List<ListCommunicationReasonEnum> listCommunicationReasonEnumList = listCommunicationReasonEnumRepository.findAll();
        assertThat(listCommunicationReasonEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListCommunicationReasonEnum() throws Exception {
        int databaseSizeBeforeUpdate = listCommunicationReasonEnumRepository.findAll().size();
        listCommunicationReasonEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListCommunicationReasonEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationReasonEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListCommunicationReasonEnum in the database
        List<ListCommunicationReasonEnum> listCommunicationReasonEnumList = listCommunicationReasonEnumRepository.findAll();
        assertThat(listCommunicationReasonEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListCommunicationReasonEnum() throws Exception {
        int databaseSizeBeforeUpdate = listCommunicationReasonEnumRepository.findAll().size();
        listCommunicationReasonEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListCommunicationReasonEnumMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationReasonEnum))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListCommunicationReasonEnum in the database
        List<ListCommunicationReasonEnum> listCommunicationReasonEnumList = listCommunicationReasonEnumRepository.findAll();
        assertThat(listCommunicationReasonEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListCommunicationReasonEnum() throws Exception {
        // Initialize the database
        listCommunicationReasonEnumRepository.saveAndFlush(listCommunicationReasonEnum);

        int databaseSizeBeforeDelete = listCommunicationReasonEnumRepository.findAll().size();

        // Delete the listCommunicationReasonEnum
        restListCommunicationReasonEnumMockMvc
            .perform(delete(ENTITY_API_URL_ID, listCommunicationReasonEnum.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListCommunicationReasonEnum> listCommunicationReasonEnumList = listCommunicationReasonEnumRepository.findAll();
        assertThat(listCommunicationReasonEnumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
