package com.sun.prms.web.rest;

import com.sun.prms.PrmsApp;

import com.sun.prms.domain.RoleUserMapping;
import com.sun.prms.repository.RoleUserMappingRepository;
import com.sun.prms.service.RoleUserMappingService;
import com.sun.prms.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.sun.prms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RoleUserMappingResource REST controller.
 *
 * @see RoleUserMappingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrmsApp.class)
public class RoleUserMappingResourceIntTest {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final Integer DEFAULT_ROLE_ID = 1;
    private static final Integer UPDATED_ROLE_ID = 2;

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    @Autowired
    private RoleUserMappingRepository roleUserMappingRepository;

    @Autowired
    private RoleUserMappingService roleUserMappingService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRoleUserMappingMockMvc;

    private RoleUserMapping roleUserMapping;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoleUserMappingResource roleUserMappingResource = new RoleUserMappingResource(roleUserMappingService);
        this.restRoleUserMappingMockMvc = MockMvcBuilders.standaloneSetup(roleUserMappingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleUserMapping createEntity(EntityManager em) {
        RoleUserMapping roleUserMapping = new RoleUserMapping()
            .userId(DEFAULT_USER_ID)
            .roleId(DEFAULT_ROLE_ID)
            .userName(DEFAULT_USER_NAME)
            .roleName(DEFAULT_ROLE_NAME)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY);
        return roleUserMapping;
    }

    @Before
    public void initTest() {
        roleUserMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoleUserMapping() throws Exception {
        int databaseSizeBeforeCreate = roleUserMappingRepository.findAll().size();

        // Create the RoleUserMapping
        restRoleUserMappingMockMvc.perform(post("/api/role-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleUserMapping)))
            .andExpect(status().isCreated());

        // Validate the RoleUserMapping in the database
        List<RoleUserMapping> roleUserMappingList = roleUserMappingRepository.findAll();
        assertThat(roleUserMappingList).hasSize(databaseSizeBeforeCreate + 1);
        RoleUserMapping testRoleUserMapping = roleUserMappingList.get(roleUserMappingList.size() - 1);
        assertThat(testRoleUserMapping.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testRoleUserMapping.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testRoleUserMapping.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testRoleUserMapping.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
        assertThat(testRoleUserMapping.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRoleUserMapping.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testRoleUserMapping.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createRoleUserMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleUserMappingRepository.findAll().size();

        // Create the RoleUserMapping with an existing ID
        roleUserMapping.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleUserMappingMockMvc.perform(post("/api/role-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleUserMapping)))
            .andExpect(status().isBadRequest());

        // Validate the RoleUserMapping in the database
        List<RoleUserMapping> roleUserMappingList = roleUserMappingRepository.findAll();
        assertThat(roleUserMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRoleUserMappings() throws Exception {
        // Initialize the database
        roleUserMappingRepository.saveAndFlush(roleUserMapping);

        // Get all the roleUserMappingList
        restRoleUserMappingMockMvc.perform(get("/api/role-user-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleUserMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }
    
    @Test
    @Transactional
    public void getRoleUserMapping() throws Exception {
        // Initialize the database
        roleUserMappingRepository.saveAndFlush(roleUserMapping);

        // Get the roleUserMapping
        restRoleUserMappingMockMvc.perform(get("/api/role-user-mappings/{id}", roleUserMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roleUserMapping.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingRoleUserMapping() throws Exception {
        // Get the roleUserMapping
        restRoleUserMappingMockMvc.perform(get("/api/role-user-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoleUserMapping() throws Exception {
        // Initialize the database
        roleUserMappingService.save(roleUserMapping);

        int databaseSizeBeforeUpdate = roleUserMappingRepository.findAll().size();

        // Update the roleUserMapping
        RoleUserMapping updatedRoleUserMapping = roleUserMappingRepository.findById(roleUserMapping.getId()).get();
        // Disconnect from session so that the updates on updatedRoleUserMapping are not directly saved in db
        em.detach(updatedRoleUserMapping);
        updatedRoleUserMapping
            .userId(UPDATED_USER_ID)
            .roleId(UPDATED_ROLE_ID)
            .userName(UPDATED_USER_NAME)
            .roleName(UPDATED_ROLE_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY);

        restRoleUserMappingMockMvc.perform(put("/api/role-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoleUserMapping)))
            .andExpect(status().isOk());

        // Validate the RoleUserMapping in the database
        List<RoleUserMapping> roleUserMappingList = roleUserMappingRepository.findAll();
        assertThat(roleUserMappingList).hasSize(databaseSizeBeforeUpdate);
        RoleUserMapping testRoleUserMapping = roleUserMappingList.get(roleUserMappingList.size() - 1);
        assertThat(testRoleUserMapping.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testRoleUserMapping.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testRoleUserMapping.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testRoleUserMapping.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
        assertThat(testRoleUserMapping.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRoleUserMapping.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testRoleUserMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingRoleUserMapping() throws Exception {
        int databaseSizeBeforeUpdate = roleUserMappingRepository.findAll().size();

        // Create the RoleUserMapping

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleUserMappingMockMvc.perform(put("/api/role-user-mappings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleUserMapping)))
            .andExpect(status().isBadRequest());

        // Validate the RoleUserMapping in the database
        List<RoleUserMapping> roleUserMappingList = roleUserMappingRepository.findAll();
        assertThat(roleUserMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoleUserMapping() throws Exception {
        // Initialize the database
        roleUserMappingService.save(roleUserMapping);

        int databaseSizeBeforeDelete = roleUserMappingRepository.findAll().size();

        // Get the roleUserMapping
        restRoleUserMappingMockMvc.perform(delete("/api/role-user-mappings/{id}", roleUserMapping.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RoleUserMapping> roleUserMappingList = roleUserMappingRepository.findAll();
        assertThat(roleUserMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleUserMapping.class);
        RoleUserMapping roleUserMapping1 = new RoleUserMapping();
        roleUserMapping1.setId(1L);
        RoleUserMapping roleUserMapping2 = new RoleUserMapping();
        roleUserMapping2.setId(roleUserMapping1.getId());
        assertThat(roleUserMapping1).isEqualTo(roleUserMapping2);
        roleUserMapping2.setId(2L);
        assertThat(roleUserMapping1).isNotEqualTo(roleUserMapping2);
        roleUserMapping1.setId(null);
        assertThat(roleUserMapping1).isNotEqualTo(roleUserMapping2);
    }
}
