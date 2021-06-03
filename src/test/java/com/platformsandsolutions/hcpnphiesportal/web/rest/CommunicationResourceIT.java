package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.Communication;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.CommunicationPriorityEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.CommunicationRepository;
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
 * Integration tests for the {@link CommunicationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommunicationResourceIT {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_QUEUED = false;
    private static final Boolean UPDATED_IS_QUEUED = true;

    private static final String DEFAULT_PARSED = "AAAAAAAAAA";
    private static final String UPDATED_PARSED = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final CommunicationPriorityEnum DEFAULT_PRIORITY = CommunicationPriorityEnum.Stat;
    private static final CommunicationPriorityEnum UPDATED_PRIORITY = CommunicationPriorityEnum.Asap;

    private static final String ENTITY_API_URL = "/api/communications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommunicationMockMvc;

    private Communication communication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Communication createEntity(EntityManager em) {
        Communication communication = new Communication()
            .guid(DEFAULT_GUID)
            .isQueued(DEFAULT_IS_QUEUED)
            .parsed(DEFAULT_PARSED)
            .identifier(DEFAULT_IDENTIFIER)
            .priority(DEFAULT_PRIORITY);
        return communication;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Communication createUpdatedEntity(EntityManager em) {
        Communication communication = new Communication()
            .guid(UPDATED_GUID)
            .isQueued(UPDATED_IS_QUEUED)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .priority(UPDATED_PRIORITY);
        return communication;
    }

    @BeforeEach
    public void initTest() {
        communication = createEntity(em);
    }

    @Test
    @Transactional
    void createCommunication() throws Exception {
        int databaseSizeBeforeCreate = communicationRepository.findAll().size();
        // Create the Communication
        restCommunicationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(communication)))
            .andExpect(status().isCreated());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeCreate + 1);
        Communication testCommunication = communicationList.get(communicationList.size() - 1);
        assertThat(testCommunication.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testCommunication.getIsQueued()).isEqualTo(DEFAULT_IS_QUEUED);
        assertThat(testCommunication.getParsed()).isEqualTo(DEFAULT_PARSED);
        assertThat(testCommunication.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testCommunication.getPriority()).isEqualTo(DEFAULT_PRIORITY);
    }

    @Test
    @Transactional
    void createCommunicationWithExistingId() throws Exception {
        // Create the Communication with an existing ID
        communication.setId(1L);

        int databaseSizeBeforeCreate = communicationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommunicationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(communication)))
            .andExpect(status().isBadRequest());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommunications() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get all the communicationList
        restCommunicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(communication.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].isQueued").value(hasItem(DEFAULT_IS_QUEUED.booleanValue())))
            .andExpect(jsonPath("$.[*].parsed").value(hasItem(DEFAULT_PARSED)))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())));
    }

    @Test
    @Transactional
    void getCommunication() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        // Get the communication
        restCommunicationMockMvc
            .perform(get(ENTITY_API_URL_ID, communication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(communication.getId().intValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.isQueued").value(DEFAULT_IS_QUEUED.booleanValue()))
            .andExpect(jsonPath("$.parsed").value(DEFAULT_PARSED))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCommunication() throws Exception {
        // Get the communication
        restCommunicationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCommunication() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();

        // Update the communication
        Communication updatedCommunication = communicationRepository.findById(communication.getId()).get();
        // Disconnect from session so that the updates on updatedCommunication are not directly saved in db
        em.detach(updatedCommunication);
        updatedCommunication
            .guid(UPDATED_GUID)
            .isQueued(UPDATED_IS_QUEUED)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .priority(UPDATED_PRIORITY);

        restCommunicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCommunication.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCommunication))
            )
            .andExpect(status().isOk());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate);
        Communication testCommunication = communicationList.get(communicationList.size() - 1);
        assertThat(testCommunication.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testCommunication.getIsQueued()).isEqualTo(UPDATED_IS_QUEUED);
        assertThat(testCommunication.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testCommunication.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testCommunication.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void putNonExistingCommunication() throws Exception {
        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();
        communication.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, communication.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communication))
            )
            .andExpect(status().isBadRequest());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommunication() throws Exception {
        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();
        communication.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(communication))
            )
            .andExpect(status().isBadRequest());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommunication() throws Exception {
        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();
        communication.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunicationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(communication)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommunicationWithPatch() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();

        // Update the communication using partial update
        Communication partialUpdatedCommunication = new Communication();
        partialUpdatedCommunication.setId(communication.getId());

        partialUpdatedCommunication
            .guid(UPDATED_GUID)
            .isQueued(UPDATED_IS_QUEUED)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .priority(UPDATED_PRIORITY);

        restCommunicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommunication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommunication))
            )
            .andExpect(status().isOk());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate);
        Communication testCommunication = communicationList.get(communicationList.size() - 1);
        assertThat(testCommunication.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testCommunication.getIsQueued()).isEqualTo(UPDATED_IS_QUEUED);
        assertThat(testCommunication.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testCommunication.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testCommunication.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void fullUpdateCommunicationWithPatch() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();

        // Update the communication using partial update
        Communication partialUpdatedCommunication = new Communication();
        partialUpdatedCommunication.setId(communication.getId());

        partialUpdatedCommunication
            .guid(UPDATED_GUID)
            .isQueued(UPDATED_IS_QUEUED)
            .parsed(UPDATED_PARSED)
            .identifier(UPDATED_IDENTIFIER)
            .priority(UPDATED_PRIORITY);

        restCommunicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommunication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommunication))
            )
            .andExpect(status().isOk());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate);
        Communication testCommunication = communicationList.get(communicationList.size() - 1);
        assertThat(testCommunication.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testCommunication.getIsQueued()).isEqualTo(UPDATED_IS_QUEUED);
        assertThat(testCommunication.getParsed()).isEqualTo(UPDATED_PARSED);
        assertThat(testCommunication.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testCommunication.getPriority()).isEqualTo(UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    void patchNonExistingCommunication() throws Exception {
        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();
        communication.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommunicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, communication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communication))
            )
            .andExpect(status().isBadRequest());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommunication() throws Exception {
        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();
        communication.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(communication))
            )
            .andExpect(status().isBadRequest());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommunication() throws Exception {
        int databaseSizeBeforeUpdate = communicationRepository.findAll().size();
        communication.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommunicationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(communication))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Communication in the database
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommunication() throws Exception {
        // Initialize the database
        communicationRepository.saveAndFlush(communication);

        int databaseSizeBeforeDelete = communicationRepository.findAll().size();

        // Delete the communication
        restCommunicationMockMvc
            .perform(delete(ENTITY_API_URL_ID, communication.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Communication> communicationList = communicationRepository.findAll();
        assertThat(communicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
