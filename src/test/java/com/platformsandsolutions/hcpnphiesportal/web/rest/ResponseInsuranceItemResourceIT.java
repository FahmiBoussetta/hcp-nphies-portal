package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ResponseInsuranceItem;
import com.platformsandsolutions.hcpnphiesportal.repository.ResponseInsuranceItemRepository;
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
 * Integration tests for the {@link ResponseInsuranceItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResponseInsuranceItemResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXCLUDED = false;
    private static final Boolean UPDATED_EXCLUDED = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NETWORK = "AAAAAAAAAA";
    private static final String UPDATED_NETWORK = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_TERM = "AAAAAAAAAA";
    private static final String UPDATED_TERM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/response-insurance-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResponseInsuranceItemRepository responseInsuranceItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponseInsuranceItemMockMvc;

    private ResponseInsuranceItem responseInsuranceItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponseInsuranceItem createEntity(EntityManager em) {
        ResponseInsuranceItem responseInsuranceItem = new ResponseInsuranceItem()
            .category(DEFAULT_CATEGORY)
            .excluded(DEFAULT_EXCLUDED)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .network(DEFAULT_NETWORK)
            .unit(DEFAULT_UNIT)
            .term(DEFAULT_TERM);
        return responseInsuranceItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResponseInsuranceItem createUpdatedEntity(EntityManager em) {
        ResponseInsuranceItem responseInsuranceItem = new ResponseInsuranceItem()
            .category(UPDATED_CATEGORY)
            .excluded(UPDATED_EXCLUDED)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .network(UPDATED_NETWORK)
            .unit(UPDATED_UNIT)
            .term(UPDATED_TERM);
        return responseInsuranceItem;
    }

    @BeforeEach
    public void initTest() {
        responseInsuranceItem = createEntity(em);
    }

    @Test
    @Transactional
    void createResponseInsuranceItem() throws Exception {
        int databaseSizeBeforeCreate = responseInsuranceItemRepository.findAll().size();
        // Create the ResponseInsuranceItem
        restResponseInsuranceItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responseInsuranceItem))
            )
            .andExpect(status().isCreated());

        // Validate the ResponseInsuranceItem in the database
        List<ResponseInsuranceItem> responseInsuranceItemList = responseInsuranceItemRepository.findAll();
        assertThat(responseInsuranceItemList).hasSize(databaseSizeBeforeCreate + 1);
        ResponseInsuranceItem testResponseInsuranceItem = responseInsuranceItemList.get(responseInsuranceItemList.size() - 1);
        assertThat(testResponseInsuranceItem.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testResponseInsuranceItem.getExcluded()).isEqualTo(DEFAULT_EXCLUDED);
        assertThat(testResponseInsuranceItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResponseInsuranceItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testResponseInsuranceItem.getNetwork()).isEqualTo(DEFAULT_NETWORK);
        assertThat(testResponseInsuranceItem.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testResponseInsuranceItem.getTerm()).isEqualTo(DEFAULT_TERM);
    }

    @Test
    @Transactional
    void createResponseInsuranceItemWithExistingId() throws Exception {
        // Create the ResponseInsuranceItem with an existing ID
        responseInsuranceItem.setId(1L);

        int databaseSizeBeforeCreate = responseInsuranceItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponseInsuranceItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responseInsuranceItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseInsuranceItem in the database
        List<ResponseInsuranceItem> responseInsuranceItemList = responseInsuranceItemRepository.findAll();
        assertThat(responseInsuranceItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResponseInsuranceItems() throws Exception {
        // Initialize the database
        responseInsuranceItemRepository.saveAndFlush(responseInsuranceItem);

        // Get all the responseInsuranceItemList
        restResponseInsuranceItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responseInsuranceItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].excluded").value(hasItem(DEFAULT_EXCLUDED.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].network").value(hasItem(DEFAULT_NETWORK)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)));
    }

    @Test
    @Transactional
    void getResponseInsuranceItem() throws Exception {
        // Initialize the database
        responseInsuranceItemRepository.saveAndFlush(responseInsuranceItem);

        // Get the responseInsuranceItem
        restResponseInsuranceItemMockMvc
            .perform(get(ENTITY_API_URL_ID, responseInsuranceItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responseInsuranceItem.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.excluded").value(DEFAULT_EXCLUDED.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.network").value(DEFAULT_NETWORK))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM));
    }

    @Test
    @Transactional
    void getNonExistingResponseInsuranceItem() throws Exception {
        // Get the responseInsuranceItem
        restResponseInsuranceItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResponseInsuranceItem() throws Exception {
        // Initialize the database
        responseInsuranceItemRepository.saveAndFlush(responseInsuranceItem);

        int databaseSizeBeforeUpdate = responseInsuranceItemRepository.findAll().size();

        // Update the responseInsuranceItem
        ResponseInsuranceItem updatedResponseInsuranceItem = responseInsuranceItemRepository.findById(responseInsuranceItem.getId()).get();
        // Disconnect from session so that the updates on updatedResponseInsuranceItem are not directly saved in db
        em.detach(updatedResponseInsuranceItem);
        updatedResponseInsuranceItem
            .category(UPDATED_CATEGORY)
            .excluded(UPDATED_EXCLUDED)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .network(UPDATED_NETWORK)
            .unit(UPDATED_UNIT)
            .term(UPDATED_TERM);

        restResponseInsuranceItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedResponseInsuranceItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedResponseInsuranceItem))
            )
            .andExpect(status().isOk());

        // Validate the ResponseInsuranceItem in the database
        List<ResponseInsuranceItem> responseInsuranceItemList = responseInsuranceItemRepository.findAll();
        assertThat(responseInsuranceItemList).hasSize(databaseSizeBeforeUpdate);
        ResponseInsuranceItem testResponseInsuranceItem = responseInsuranceItemList.get(responseInsuranceItemList.size() - 1);
        assertThat(testResponseInsuranceItem.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testResponseInsuranceItem.getExcluded()).isEqualTo(UPDATED_EXCLUDED);
        assertThat(testResponseInsuranceItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResponseInsuranceItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testResponseInsuranceItem.getNetwork()).isEqualTo(UPDATED_NETWORK);
        assertThat(testResponseInsuranceItem.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testResponseInsuranceItem.getTerm()).isEqualTo(UPDATED_TERM);
    }

    @Test
    @Transactional
    void putNonExistingResponseInsuranceItem() throws Exception {
        int databaseSizeBeforeUpdate = responseInsuranceItemRepository.findAll().size();
        responseInsuranceItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponseInsuranceItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responseInsuranceItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responseInsuranceItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseInsuranceItem in the database
        List<ResponseInsuranceItem> responseInsuranceItemList = responseInsuranceItemRepository.findAll();
        assertThat(responseInsuranceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponseInsuranceItem() throws Exception {
        int databaseSizeBeforeUpdate = responseInsuranceItemRepository.findAll().size();
        responseInsuranceItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseInsuranceItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responseInsuranceItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseInsuranceItem in the database
        List<ResponseInsuranceItem> responseInsuranceItemList = responseInsuranceItemRepository.findAll();
        assertThat(responseInsuranceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponseInsuranceItem() throws Exception {
        int databaseSizeBeforeUpdate = responseInsuranceItemRepository.findAll().size();
        responseInsuranceItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseInsuranceItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responseInsuranceItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponseInsuranceItem in the database
        List<ResponseInsuranceItem> responseInsuranceItemList = responseInsuranceItemRepository.findAll();
        assertThat(responseInsuranceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponseInsuranceItemWithPatch() throws Exception {
        // Initialize the database
        responseInsuranceItemRepository.saveAndFlush(responseInsuranceItem);

        int databaseSizeBeforeUpdate = responseInsuranceItemRepository.findAll().size();

        // Update the responseInsuranceItem using partial update
        ResponseInsuranceItem partialUpdatedResponseInsuranceItem = new ResponseInsuranceItem();
        partialUpdatedResponseInsuranceItem.setId(responseInsuranceItem.getId());

        partialUpdatedResponseInsuranceItem.category(UPDATED_CATEGORY).excluded(UPDATED_EXCLUDED).description(UPDATED_DESCRIPTION);

        restResponseInsuranceItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponseInsuranceItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponseInsuranceItem))
            )
            .andExpect(status().isOk());

        // Validate the ResponseInsuranceItem in the database
        List<ResponseInsuranceItem> responseInsuranceItemList = responseInsuranceItemRepository.findAll();
        assertThat(responseInsuranceItemList).hasSize(databaseSizeBeforeUpdate);
        ResponseInsuranceItem testResponseInsuranceItem = responseInsuranceItemList.get(responseInsuranceItemList.size() - 1);
        assertThat(testResponseInsuranceItem.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testResponseInsuranceItem.getExcluded()).isEqualTo(UPDATED_EXCLUDED);
        assertThat(testResponseInsuranceItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResponseInsuranceItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testResponseInsuranceItem.getNetwork()).isEqualTo(DEFAULT_NETWORK);
        assertThat(testResponseInsuranceItem.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testResponseInsuranceItem.getTerm()).isEqualTo(DEFAULT_TERM);
    }

    @Test
    @Transactional
    void fullUpdateResponseInsuranceItemWithPatch() throws Exception {
        // Initialize the database
        responseInsuranceItemRepository.saveAndFlush(responseInsuranceItem);

        int databaseSizeBeforeUpdate = responseInsuranceItemRepository.findAll().size();

        // Update the responseInsuranceItem using partial update
        ResponseInsuranceItem partialUpdatedResponseInsuranceItem = new ResponseInsuranceItem();
        partialUpdatedResponseInsuranceItem.setId(responseInsuranceItem.getId());

        partialUpdatedResponseInsuranceItem
            .category(UPDATED_CATEGORY)
            .excluded(UPDATED_EXCLUDED)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .network(UPDATED_NETWORK)
            .unit(UPDATED_UNIT)
            .term(UPDATED_TERM);

        restResponseInsuranceItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponseInsuranceItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponseInsuranceItem))
            )
            .andExpect(status().isOk());

        // Validate the ResponseInsuranceItem in the database
        List<ResponseInsuranceItem> responseInsuranceItemList = responseInsuranceItemRepository.findAll();
        assertThat(responseInsuranceItemList).hasSize(databaseSizeBeforeUpdate);
        ResponseInsuranceItem testResponseInsuranceItem = responseInsuranceItemList.get(responseInsuranceItemList.size() - 1);
        assertThat(testResponseInsuranceItem.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testResponseInsuranceItem.getExcluded()).isEqualTo(UPDATED_EXCLUDED);
        assertThat(testResponseInsuranceItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResponseInsuranceItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testResponseInsuranceItem.getNetwork()).isEqualTo(UPDATED_NETWORK);
        assertThat(testResponseInsuranceItem.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testResponseInsuranceItem.getTerm()).isEqualTo(UPDATED_TERM);
    }

    @Test
    @Transactional
    void patchNonExistingResponseInsuranceItem() throws Exception {
        int databaseSizeBeforeUpdate = responseInsuranceItemRepository.findAll().size();
        responseInsuranceItem.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponseInsuranceItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responseInsuranceItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responseInsuranceItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseInsuranceItem in the database
        List<ResponseInsuranceItem> responseInsuranceItemList = responseInsuranceItemRepository.findAll();
        assertThat(responseInsuranceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponseInsuranceItem() throws Exception {
        int databaseSizeBeforeUpdate = responseInsuranceItemRepository.findAll().size();
        responseInsuranceItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseInsuranceItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responseInsuranceItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResponseInsuranceItem in the database
        List<ResponseInsuranceItem> responseInsuranceItemList = responseInsuranceItemRepository.findAll();
        assertThat(responseInsuranceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponseInsuranceItem() throws Exception {
        int databaseSizeBeforeUpdate = responseInsuranceItemRepository.findAll().size();
        responseInsuranceItem.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponseInsuranceItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responseInsuranceItem))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResponseInsuranceItem in the database
        List<ResponseInsuranceItem> responseInsuranceItemList = responseInsuranceItemRepository.findAll();
        assertThat(responseInsuranceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponseInsuranceItem() throws Exception {
        // Initialize the database
        responseInsuranceItemRepository.saveAndFlush(responseInsuranceItem);

        int databaseSizeBeforeDelete = responseInsuranceItemRepository.findAll().size();

        // Delete the responseInsuranceItem
        restResponseInsuranceItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, responseInsuranceItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResponseInsuranceItem> responseInsuranceItemList = responseInsuranceItemRepository.findAll();
        assertThat(responseInsuranceItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
