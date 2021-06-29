package com.platformsandsolutions.hcpnphiesportal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.platformsandsolutions.hcpnphiesportal.IntegrationTest;
import com.platformsandsolutions.hcpnphiesportal.domain.ClassComponent;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ClassTypeEnum;
import com.platformsandsolutions.hcpnphiesportal.repository.ClassComponentRepository;
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
 * Integration tests for the {@link ClassComponentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClassComponentResourceIT {

    private static final ClassTypeEnum DEFAULT_TYPE = ClassTypeEnum.Group;
    private static final ClassTypeEnum UPDATED_TYPE = ClassTypeEnum.Subgroup;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/class-components";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClassComponentRepository classComponentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassComponentMockMvc;

    private ClassComponent classComponent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassComponent createEntity(EntityManager em) {
        ClassComponent classComponent = new ClassComponent().type(DEFAULT_TYPE).value(DEFAULT_VALUE).name(DEFAULT_NAME);
        return classComponent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassComponent createUpdatedEntity(EntityManager em) {
        ClassComponent classComponent = new ClassComponent().type(UPDATED_TYPE).value(UPDATED_VALUE).name(UPDATED_NAME);
        return classComponent;
    }

    @BeforeEach
    public void initTest() {
        classComponent = createEntity(em);
    }

    @Test
    @Transactional
    void createClassComponent() throws Exception {
        int databaseSizeBeforeCreate = classComponentRepository.findAll().size();
        // Create the ClassComponent
        restClassComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classComponent))
            )
            .andExpect(status().isCreated());

        // Validate the ClassComponent in the database
        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeCreate + 1);
        ClassComponent testClassComponent = classComponentList.get(classComponentList.size() - 1);
        assertThat(testClassComponent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testClassComponent.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testClassComponent.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createClassComponentWithExistingId() throws Exception {
        // Create the ClassComponent with an existing ID
        classComponent.setId(1L);

        int databaseSizeBeforeCreate = classComponentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassComponent in the database
        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = classComponentRepository.findAll().size();
        // set the field null
        classComponent.setType(null);

        // Create the ClassComponent, which fails.

        restClassComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classComponent))
            )
            .andExpect(status().isBadRequest());

        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = classComponentRepository.findAll().size();
        // set the field null
        classComponent.setValue(null);

        // Create the ClassComponent, which fails.

        restClassComponentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classComponent))
            )
            .andExpect(status().isBadRequest());

        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClassComponents() throws Exception {
        // Initialize the database
        classComponentRepository.saveAndFlush(classComponent);

        // Get all the classComponentList
        restClassComponentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classComponent.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getClassComponent() throws Exception {
        // Initialize the database
        classComponentRepository.saveAndFlush(classComponent);

        // Get the classComponent
        restClassComponentMockMvc
            .perform(get(ENTITY_API_URL_ID, classComponent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classComponent.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingClassComponent() throws Exception {
        // Get the classComponent
        restClassComponentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClassComponent() throws Exception {
        // Initialize the database
        classComponentRepository.saveAndFlush(classComponent);

        int databaseSizeBeforeUpdate = classComponentRepository.findAll().size();

        // Update the classComponent
        ClassComponent updatedClassComponent = classComponentRepository.findById(classComponent.getId()).get();
        // Disconnect from session so that the updates on updatedClassComponent are not directly saved in db
        em.detach(updatedClassComponent);
        updatedClassComponent.type(UPDATED_TYPE).value(UPDATED_VALUE).name(UPDATED_NAME);

        restClassComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClassComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClassComponent))
            )
            .andExpect(status().isOk());

        // Validate the ClassComponent in the database
        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeUpdate);
        ClassComponent testClassComponent = classComponentList.get(classComponentList.size() - 1);
        assertThat(testClassComponent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testClassComponent.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testClassComponent.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingClassComponent() throws Exception {
        int databaseSizeBeforeUpdate = classComponentRepository.findAll().size();
        classComponent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classComponent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassComponent in the database
        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassComponent() throws Exception {
        int databaseSizeBeforeUpdate = classComponentRepository.findAll().size();
        classComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassComponentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassComponent in the database
        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassComponent() throws Exception {
        int databaseSizeBeforeUpdate = classComponentRepository.findAll().size();
        classComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassComponentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classComponent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassComponent in the database
        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClassComponentWithPatch() throws Exception {
        // Initialize the database
        classComponentRepository.saveAndFlush(classComponent);

        int databaseSizeBeforeUpdate = classComponentRepository.findAll().size();

        // Update the classComponent using partial update
        ClassComponent partialUpdatedClassComponent = new ClassComponent();
        partialUpdatedClassComponent.setId(classComponent.getId());

        partialUpdatedClassComponent.value(UPDATED_VALUE).name(UPDATED_NAME);

        restClassComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassComponent))
            )
            .andExpect(status().isOk());

        // Validate the ClassComponent in the database
        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeUpdate);
        ClassComponent testClassComponent = classComponentList.get(classComponentList.size() - 1);
        assertThat(testClassComponent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testClassComponent.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testClassComponent.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateClassComponentWithPatch() throws Exception {
        // Initialize the database
        classComponentRepository.saveAndFlush(classComponent);

        int databaseSizeBeforeUpdate = classComponentRepository.findAll().size();

        // Update the classComponent using partial update
        ClassComponent partialUpdatedClassComponent = new ClassComponent();
        partialUpdatedClassComponent.setId(classComponent.getId());

        partialUpdatedClassComponent.type(UPDATED_TYPE).value(UPDATED_VALUE).name(UPDATED_NAME);

        restClassComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassComponent))
            )
            .andExpect(status().isOk());

        // Validate the ClassComponent in the database
        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeUpdate);
        ClassComponent testClassComponent = classComponentList.get(classComponentList.size() - 1);
        assertThat(testClassComponent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testClassComponent.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testClassComponent.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingClassComponent() throws Exception {
        int databaseSizeBeforeUpdate = classComponentRepository.findAll().size();
        classComponent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classComponent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassComponent in the database
        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassComponent() throws Exception {
        int databaseSizeBeforeUpdate = classComponentRepository.findAll().size();
        classComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassComponentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classComponent))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassComponent in the database
        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassComponent() throws Exception {
        int databaseSizeBeforeUpdate = classComponentRepository.findAll().size();
        classComponent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassComponentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(classComponent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassComponent in the database
        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClassComponent() throws Exception {
        // Initialize the database
        classComponentRepository.saveAndFlush(classComponent);

        int databaseSizeBeforeDelete = classComponentRepository.findAll().size();

        // Delete the classComponent
        restClassComponentMockMvc
            .perform(delete(ENTITY_API_URL_ID, classComponent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassComponent> classComponentList = classComponentRepository.findAll();
        assertThat(classComponentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
