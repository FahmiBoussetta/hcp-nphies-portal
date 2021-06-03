package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ListRoleCodeEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.RoleCodeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ListRoleCodeEnumRepository;
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
 * Integration tests for the {@link ListRoleCodeEnumResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ListRoleCodeEnumResourceIT {

    private static final RoleCodeEnum DEFAULT_R = RoleCodeEnum.Doctor;
    private static final RoleCodeEnum UPDATED_R = RoleCodeEnum.Nurse;

    private static final String ENTITY_API_URL = "/api/list-role-code-enums";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ListRoleCodeEnumRepository listRoleCodeEnumRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListRoleCodeEnumMockMvc;

    private ListRoleCodeEnum listRoleCodeEnum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListRoleCodeEnum createEntity(EntityManager em) {
        ListRoleCodeEnum listRoleCodeEnum = new ListRoleCodeEnum().r(DEFAULT_R);
        return listRoleCodeEnum;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListRoleCodeEnum createUpdatedEntity(EntityManager em) {
        ListRoleCodeEnum listRoleCodeEnum = new ListRoleCodeEnum().r(UPDATED_R);
        return listRoleCodeEnum;
    }

    @BeforeEach
    public void initTest() {
        listRoleCodeEnum = createEntity(em);
    }

    @Test
    @Transactional
    void createListRoleCodeEnum() throws Exception {
        int databaseSizeBeforeCreate = listRoleCodeEnumRepository.findAll().size();
        // Create the ListRoleCodeEnum
        restListRoleCodeEnumMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listRoleCodeEnum))
            )
            .andExpect(status().isCreated());

        // Validate the ListRoleCodeEnum in the database
        List<ListRoleCodeEnum> listRoleCodeEnumList = listRoleCodeEnumRepository.findAll();
        assertThat(listRoleCodeEnumList).hasSize(databaseSizeBeforeCreate + 1);
        ListRoleCodeEnum testListRoleCodeEnum = listRoleCodeEnumList.get(listRoleCodeEnumList.size() - 1);
        assertThat(testListRoleCodeEnum.getR()).isEqualTo(DEFAULT_R);
    }

    @Test
    @Transactional
    void createListRoleCodeEnumWithExistingId() throws Exception {
        // Create the ListRoleCodeEnum with an existing ID
        listRoleCodeEnum.setId(1L);

        int databaseSizeBeforeCreate = listRoleCodeEnumRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restListRoleCodeEnumMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listRoleCodeEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListRoleCodeEnum in the database
        List<ListRoleCodeEnum> listRoleCodeEnumList = listRoleCodeEnumRepository.findAll();
        assertThat(listRoleCodeEnumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllListRoleCodeEnums() throws Exception {
        // Initialize the database
        listRoleCodeEnumRepository.saveAndFlush(listRoleCodeEnum);

        // Get all the listRoleCodeEnumList
        restListRoleCodeEnumMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listRoleCodeEnum.getId().intValue())))
            .andExpect(jsonPath("$.[*].r").value(hasItem(DEFAULT_R.toString())));
    }

    @Test
    @Transactional
    void getListRoleCodeEnum() throws Exception {
        // Initialize the database
        listRoleCodeEnumRepository.saveAndFlush(listRoleCodeEnum);

        // Get the listRoleCodeEnum
        restListRoleCodeEnumMockMvc
            .perform(get(ENTITY_API_URL_ID, listRoleCodeEnum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listRoleCodeEnum.getId().intValue()))
            .andExpect(jsonPath("$.r").value(DEFAULT_R.toString()));
    }

    @Test
    @Transactional
    void getNonExistingListRoleCodeEnum() throws Exception {
        // Get the listRoleCodeEnum
        restListRoleCodeEnumMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewListRoleCodeEnum() throws Exception {
        // Initialize the database
        listRoleCodeEnumRepository.saveAndFlush(listRoleCodeEnum);

        int databaseSizeBeforeUpdate = listRoleCodeEnumRepository.findAll().size();

        // Update the listRoleCodeEnum
        ListRoleCodeEnum updatedListRoleCodeEnum = listRoleCodeEnumRepository.findById(listRoleCodeEnum.getId()).get();
        // Disconnect from session so that the updates on updatedListRoleCodeEnum are not directly saved in db
        em.detach(updatedListRoleCodeEnum);
        updatedListRoleCodeEnum.r(UPDATED_R);

        restListRoleCodeEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedListRoleCodeEnum.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedListRoleCodeEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListRoleCodeEnum in the database
        List<ListRoleCodeEnum> listRoleCodeEnumList = listRoleCodeEnumRepository.findAll();
        assertThat(listRoleCodeEnumList).hasSize(databaseSizeBeforeUpdate);
        ListRoleCodeEnum testListRoleCodeEnum = listRoleCodeEnumList.get(listRoleCodeEnumList.size() - 1);
        assertThat(testListRoleCodeEnum.getR()).isEqualTo(UPDATED_R);
    }

    @Test
    @Transactional
    void putNonExistingListRoleCodeEnum() throws Exception {
        int databaseSizeBeforeUpdate = listRoleCodeEnumRepository.findAll().size();
        listRoleCodeEnum.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListRoleCodeEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, listRoleCodeEnum.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listRoleCodeEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListRoleCodeEnum in the database
        List<ListRoleCodeEnum> listRoleCodeEnumList = listRoleCodeEnumRepository.findAll();
        assertThat(listRoleCodeEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchListRoleCodeEnum() throws Exception {
        int databaseSizeBeforeUpdate = listRoleCodeEnumRepository.findAll().size();
        listRoleCodeEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListRoleCodeEnumMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(listRoleCodeEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListRoleCodeEnum in the database
        List<ListRoleCodeEnum> listRoleCodeEnumList = listRoleCodeEnumRepository.findAll();
        assertThat(listRoleCodeEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamListRoleCodeEnum() throws Exception {
        int databaseSizeBeforeUpdate = listRoleCodeEnumRepository.findAll().size();
        listRoleCodeEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListRoleCodeEnumMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(listRoleCodeEnum))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListRoleCodeEnum in the database
        List<ListRoleCodeEnum> listRoleCodeEnumList = listRoleCodeEnumRepository.findAll();
        assertThat(listRoleCodeEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateListRoleCodeEnumWithPatch() throws Exception {
        // Initialize the database
        listRoleCodeEnumRepository.saveAndFlush(listRoleCodeEnum);

        int databaseSizeBeforeUpdate = listRoleCodeEnumRepository.findAll().size();

        // Update the listRoleCodeEnum using partial update
        ListRoleCodeEnum partialUpdatedListRoleCodeEnum = new ListRoleCodeEnum();
        partialUpdatedListRoleCodeEnum.setId(listRoleCodeEnum.getId());

        partialUpdatedListRoleCodeEnum.r(UPDATED_R);

        restListRoleCodeEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListRoleCodeEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListRoleCodeEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListRoleCodeEnum in the database
        List<ListRoleCodeEnum> listRoleCodeEnumList = listRoleCodeEnumRepository.findAll();
        assertThat(listRoleCodeEnumList).hasSize(databaseSizeBeforeUpdate);
        ListRoleCodeEnum testListRoleCodeEnum = listRoleCodeEnumList.get(listRoleCodeEnumList.size() - 1);
        assertThat(testListRoleCodeEnum.getR()).isEqualTo(UPDATED_R);
    }

    @Test
    @Transactional
    void fullUpdateListRoleCodeEnumWithPatch() throws Exception {
        // Initialize the database
        listRoleCodeEnumRepository.saveAndFlush(listRoleCodeEnum);

        int databaseSizeBeforeUpdate = listRoleCodeEnumRepository.findAll().size();

        // Update the listRoleCodeEnum using partial update
        ListRoleCodeEnum partialUpdatedListRoleCodeEnum = new ListRoleCodeEnum();
        partialUpdatedListRoleCodeEnum.setId(listRoleCodeEnum.getId());

        partialUpdatedListRoleCodeEnum.r(UPDATED_R);

        restListRoleCodeEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedListRoleCodeEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedListRoleCodeEnum))
            )
            .andExpect(status().isOk());

        // Validate the ListRoleCodeEnum in the database
        List<ListRoleCodeEnum> listRoleCodeEnumList = listRoleCodeEnumRepository.findAll();
        assertThat(listRoleCodeEnumList).hasSize(databaseSizeBeforeUpdate);
        ListRoleCodeEnum testListRoleCodeEnum = listRoleCodeEnumList.get(listRoleCodeEnumList.size() - 1);
        assertThat(testListRoleCodeEnum.getR()).isEqualTo(UPDATED_R);
    }

    @Test
    @Transactional
    void patchNonExistingListRoleCodeEnum() throws Exception {
        int databaseSizeBeforeUpdate = listRoleCodeEnumRepository.findAll().size();
        listRoleCodeEnum.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListRoleCodeEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, listRoleCodeEnum.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listRoleCodeEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListRoleCodeEnum in the database
        List<ListRoleCodeEnum> listRoleCodeEnumList = listRoleCodeEnumRepository.findAll();
        assertThat(listRoleCodeEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchListRoleCodeEnum() throws Exception {
        int databaseSizeBeforeUpdate = listRoleCodeEnumRepository.findAll().size();
        listRoleCodeEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListRoleCodeEnumMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listRoleCodeEnum))
            )
            .andExpect(status().isBadRequest());

        // Validate the ListRoleCodeEnum in the database
        List<ListRoleCodeEnum> listRoleCodeEnumList = listRoleCodeEnumRepository.findAll();
        assertThat(listRoleCodeEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamListRoleCodeEnum() throws Exception {
        int databaseSizeBeforeUpdate = listRoleCodeEnumRepository.findAll().size();
        listRoleCodeEnum.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restListRoleCodeEnumMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(listRoleCodeEnum))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ListRoleCodeEnum in the database
        List<ListRoleCodeEnum> listRoleCodeEnumList = listRoleCodeEnumRepository.findAll();
        assertThat(listRoleCodeEnumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteListRoleCodeEnum() throws Exception {
        // Initialize the database
        listRoleCodeEnumRepository.saveAndFlush(listRoleCodeEnum);

        int databaseSizeBeforeDelete = listRoleCodeEnumRepository.findAll().size();

        // Delete the listRoleCodeEnum
        restListRoleCodeEnumMockMvc
            .perform(delete(ENTITY_API_URL_ID, listRoleCodeEnum.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListRoleCodeEnum> listRoleCodeEnumList = listRoleCodeEnumRepository.findAll();
        assertThat(listRoleCodeEnumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
