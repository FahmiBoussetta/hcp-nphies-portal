package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ListCommunicationMediumEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.CommunicationMediumEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ListCommunicationMediumEnumRepository;
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
 * Integration tests for the {@link ListCommunicationMediumEnumResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ListCommunicationMediumEnumResourceIT {

    private static final CommunicationMediumEnum DEFAULT_CM = CommunicationMediumEnum.ELECTRONIC;
    private static final CommunicationMediumEnum UPDATED_CM = CommunicationMediumEnum.PHYSICAL;

    private static final String ENTITY_API_URL = "/api/list-communication-medium-enums";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ListCommunicationMediumEnumRepository listCommunicationMediumEnumRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListCommunicationMediumEnumMockMvc;

    private ListCommunicationMediumEnum listCommunicationMediumEnum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListCommunicationMediumEnum createEntity(EntityManager em) {
        ListCommunicationMediumEnum listCommunicationMediumEnum = new ListCommunicationMediumEnum().cm(DEFAULT_CM);
        return listCommunicationMediumEnum;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListCommunicationMediumEnum createUpdatedEntity(EntityManager em) {
        ListCommunicationMediumEnum listCommunicationMediumEnum = new ListCommunicationMediumEnum().cm(UPDATED_CM);
        return listCommunicationMediumEnum;
    }

    @BeforeEach
    public void initTest() {
        listCommunicationMediumEnum = createEntity(em);
    }

    @Test
    @Transactional
    void createListCommunicationMediumEnum() throws Exception {
        int databaseSizeBeforeCreate = listCommunicationMediumEnumRepository.findAll().size();
        // Create the ListCommunicationMediumEnum
        restListCommunicationMediumEnumMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationMediumEnum))
            )
            .andExpect(status().isCreated());

        // Validate the ListCommunicationMediumEnum in the database
        List<ListCommunicationMediumEnum> listCommunicationMediumEnumList = listCommunicationMediumEnumRepository.findAll();
        assertThat(listCommunicationMediumEnumList).hasSize(databaseSizeBeforeCreate + 1);
        ListCommunicationMediumEnum testListCommunicationMediumEnum = listCommunicationMediumEnumList.get(
            listCommunicationMediumEnumList.size() - 1
        );
        assertThat(testListCommunicationMediumEnum.getCm()).isEqualTo(DEFAULT_CM);
    }

    @Test
    @Transactional
    void createListCommunicationMediumEnumWithExistingId() throws Exception {
        // Create the ListCommunicationMediumEnum with an existing ID
        listCommunicationMediumEnum.setId(1L);

        int databaseSizeBeforeCreate = listCommunicationMediumEnumRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListCommunicationMediumEnumMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationMediumEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListCommunicationMediumEnum in the database
        List<ListCommunicationMediumEnum> listCommunicationMediumEnumList = listCommunicationMediumEnumRepository.findAll();
        assertThat(listCommunicationMediumEnumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllListCommunicationMediumEnums() throws Exception {
        // Initialize the database
        listCommunicationMediumEnumRepository.saveAndFlush(listCommunicationMediumEnum);

        // Get all the listCommunicationMediumEnumList
        restListCommunicationMediumEnumMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listCommunicationMediumEnum.getId().intValue())))
            .andExpect(jsonPath("$.[*].cm").value(hasItem(DEFAULT_CM.toString())));
    }

    @Test
    @Transactional
    void getListCommunicationMediumEnum() throws Exception {
        // Initialize the database
        listCommunicationMediumEnumRepository.saveAndFlush(listCommunicationMediumEnum);

        // Get the listCommunicationMediumEnum
        restListCommunicationMediumEnumMockMvc
            .perform(get(ENTITY_API_URL_ID, listCommunicationMediumEnum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listCommunicationMediumEnum.getId().intValue()))
            .andExpect(jsonPath("$.cm").value(DEFAULT_CM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingListCommunicationMediumEnum() throws Exception {
        // Get the listCommunicationMediumEnum
        restListCommunicationMediumEnumMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewListCommunicationMediumEnum() throws Exception {
        // Initialize the database
        listCommunicationMediumEnumRepository.saveAndFlush(listCommunicationMediumEnum);

        int databaseSizeBeforeUpdate = listCommunicationMediumEnumRepository.findAll().size();

        // Update the listCommunicationMediumEnum
        ListCommunicationMediumEnum updatedListCommunicationMediumEnum = listCommunicationMediumEnumRepository
            .findById(listCommunicationMediumEnum.getId())
            .get();
        // Disconnect from session so that the updates on updatedListCommunicationMediumEnum are not directly saved in db
        em.detach(updatedListCommunicationMediumEnum);
        updatedListCommunicationMediumEnum.cm(UPDATED_CM);

        restListCommunicationMediumEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedListCommunicationMediumEnum.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedListCommunicationMediumEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListCommunicationMediumEnum in the database
        List<ListCommunicationMediumEnum> listCommunicationMediumEnumList = listCommunicationMediumEnumRepository.findAll();
        assertThat(listCommunicationMediumEnumList).hasSize(databaseSizeBeforeUpdate);
        ListCommunicationMediumEnum testListCommunicationMediumEnum = listCommunicationMediumEnumList.get(
            listCommunicationMediumEnumList.size() - 1
        );
        assertThat(testListCommunicationMediumEnum.getCm()).isEqualTo(UPDATED_CM);
    }

    @Test
    @Transactional
    void putNonExistingListCommunicationMediumEnum() throws Exception {
        int databaseSizeBeforeUpdate = listCommunicationMediumEnumRepository.findAll().size();
        listCommunicationMediumEnum.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListCommunicationMediumEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listCommunicationMediumEnum.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationMediumEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListCommunicationMediumEnum in the database
        List<ListCommunicationMediumEnum> listCommunicationMediumEnumList = listCommunicationMediumEnumRepository.findAll();
        assertThat(listCommunicationMediumEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListCommunicationMediumEnum() throws Exception {
        int databaseSizeBeforeUpdate = listCommunicationMediumEnumRepository.findAll().size();
        listCommunicationMediumEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListCommunicationMediumEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationMediumEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListCommunicationMediumEnum in the database
        List<ListCommunicationMediumEnum> listCommunicationMediumEnumList = listCommunicationMediumEnumRepository.findAll();
        assertThat(listCommunicationMediumEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListCommunicationMediumEnum() throws Exception {
        int databaseSizeBeforeUpdate = listCommunicationMediumEnumRepository.findAll().size();
        listCommunicationMediumEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListCommunicationMediumEnumMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationMediumEnum))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListCommunicationMediumEnum in the database
        List<ListCommunicationMediumEnum> listCommunicationMediumEnumList = listCommunicationMediumEnumRepository.findAll();
        assertThat(listCommunicationMediumEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListCommunicationMediumEnumWithPatch() throws Exception {
        // Initialize the database
        listCommunicationMediumEnumRepository.saveAndFlush(listCommunicationMediumEnum);

        int databaseSizeBeforeUpdate = listCommunicationMediumEnumRepository.findAll().size();

        // Update the listCommunicationMediumEnum using partial update
        ListCommunicationMediumEnum partialUpdatedListCommunicationMediumEnum = new ListCommunicationMediumEnum();
        partialUpdatedListCommunicationMediumEnum.setId(listCommunicationMediumEnum.getId());

        restListCommunicationMediumEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListCommunicationMediumEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListCommunicationMediumEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListCommunicationMediumEnum in the database
        List<ListCommunicationMediumEnum> listCommunicationMediumEnumList = listCommunicationMediumEnumRepository.findAll();
        assertThat(listCommunicationMediumEnumList).hasSize(databaseSizeBeforeUpdate);
        ListCommunicationMediumEnum testListCommunicationMediumEnum = listCommunicationMediumEnumList.get(
            listCommunicationMediumEnumList.size() - 1
        );
        assertThat(testListCommunicationMediumEnum.getCm()).isEqualTo(DEFAULT_CM);
    }

    @Test
    @Transactional
    void fullUpdateListCommunicationMediumEnumWithPatch() throws Exception {
        // Initialize the database
        listCommunicationMediumEnumRepository.saveAndFlush(listCommunicationMediumEnum);

        int databaseSizeBeforeUpdate = listCommunicationMediumEnumRepository.findAll().size();

        // Update the listCommunicationMediumEnum using partial update
        ListCommunicationMediumEnum partialUpdatedListCommunicationMediumEnum = new ListCommunicationMediumEnum();
        partialUpdatedListCommunicationMediumEnum.setId(listCommunicationMediumEnum.getId());

        partialUpdatedListCommunicationMediumEnum.cm(UPDATED_CM);

        restListCommunicationMediumEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListCommunicationMediumEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListCommunicationMediumEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListCommunicationMediumEnum in the database
        List<ListCommunicationMediumEnum> listCommunicationMediumEnumList = listCommunicationMediumEnumRepository.findAll();
        assertThat(listCommunicationMediumEnumList).hasSize(databaseSizeBeforeUpdate);
        ListCommunicationMediumEnum testListCommunicationMediumEnum = listCommunicationMediumEnumList.get(
            listCommunicationMediumEnumList.size() - 1
        );
        assertThat(testListCommunicationMediumEnum.getCm()).isEqualTo(UPDATED_CM);
    }

    @Test
    @Transactional
    void patchNonExistingListCommunicationMediumEnum() throws Exception {
        int databaseSizeBeforeUpdate = listCommunicationMediumEnumRepository.findAll().size();
        listCommunicationMediumEnum.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListCommunicationMediumEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listCommunicationMediumEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationMediumEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListCommunicationMediumEnum in the database
        List<ListCommunicationMediumEnum> listCommunicationMediumEnumList = listCommunicationMediumEnumRepository.findAll();
        assertThat(listCommunicationMediumEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListCommunicationMediumEnum() throws Exception {
        int databaseSizeBeforeUpdate = listCommunicationMediumEnumRepository.findAll().size();
        listCommunicationMediumEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListCommunicationMediumEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationMediumEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListCommunicationMediumEnum in the database
        List<ListCommunicationMediumEnum> listCommunicationMediumEnumList = listCommunicationMediumEnumRepository.findAll();
        assertThat(listCommunicationMediumEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListCommunicationMediumEnum() throws Exception {
        int databaseSizeBeforeUpdate = listCommunicationMediumEnumRepository.findAll().size();
        listCommunicationMediumEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListCommunicationMediumEnumMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listCommunicationMediumEnum))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListCommunicationMediumEnum in the database
        List<ListCommunicationMediumEnum> listCommunicationMediumEnumList = listCommunicationMediumEnumRepository.findAll();
        assertThat(listCommunicationMediumEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListCommunicationMediumEnum() throws Exception {
        // Initialize the database
        listCommunicationMediumEnumRepository.saveAndFlush(listCommunicationMediumEnum);

        int databaseSizeBeforeDelete = listCommunicationMediumEnumRepository.findAll().size();

        // Delete the listCommunicationMediumEnum
        restListCommunicationMediumEnumMockMvc
            .perform(delete(ENTITY_API_URL_ID, listCommunicationMediumEnum.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListCommunicationMediumEnum> listCommunicationMediumEnumList = listCommunicationMediumEnumRepository.findAll();
        assertThat(listCommunicationMediumEnumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
