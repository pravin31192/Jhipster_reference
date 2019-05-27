package com.sun.prms.web.rest;

import com.sun.prms.PrmsApp;

import com.sun.prms.domain.ProjectResourceAssign;
import com.sun.prms.repository.ProjectResourceAssignRepository;
import com.sun.prms.service.ProjectResourceAssignService;
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
 * Test class for the ProjectResourceAssignResource REST controller.
 *
 * @see ProjectResourceAssignResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrmsApp.class)
public class ProjectResourceAssignResourceIntTest {

    private static final Integer DEFAULT_PROJECT_ID = 1;
    private static final Integer UPDATED_PROJECT_ID = 2;

    private static final Integer DEFAULT_EMP_ID = 1;
    private static final Integer UPDATED_EMP_ID = 2;

    private static final Integer DEFAULT_ROLE = 1;
    private static final Integer UPDATED_ROLE = 2;

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_BILLING_TYPE = 1;
    private static final Integer UPDATED_BILLING_TYPE = 2;

    private static final Float DEFAULT_BILL_VALUE = 1F;
    private static final Float UPDATED_BILL_VALUE = 2F;

    private static final Float DEFAULT_PERCENTAGE_OF_WORK = 1F;
    private static final Float UPDATED_PERCENTAGE_OF_WORK = 2F;

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_ESTIMATED_STAFF_HOURS = 1F;
    private static final Float UPDATED_ESTIMATED_STAFF_HOURS = 2F;

    private static final Float DEFAULT_ACTUAL_STAFF_HOURS = 1F;
    private static final Float UPDATED_ACTUAL_STAFF_HOURS = 2F;

    private static final Integer DEFAULT_LOCATION = 1;
    private static final Integer UPDATED_LOCATION = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProjectResourceAssignRepository projectResourceAssignRepository;

    @Autowired
    private ProjectResourceAssignService projectResourceAssignService;

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

    private MockMvc restProjectResourceAssignMockMvc;

    private ProjectResourceAssign projectResourceAssign;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectResourceAssignResource projectResourceAssignResource = new ProjectResourceAssignResource(projectResourceAssignService);
        this.restProjectResourceAssignMockMvc = MockMvcBuilders.standaloneSetup(projectResourceAssignResource)
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
    public static ProjectResourceAssign createEntity(EntityManager em) {
        ProjectResourceAssign projectResourceAssign = new ProjectResourceAssign()
            .projectId(DEFAULT_PROJECT_ID)
            .empId(DEFAULT_EMP_ID)
            .role(DEFAULT_ROLE)
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .billingType(DEFAULT_BILLING_TYPE)
            .billValue(DEFAULT_BILL_VALUE)
            .percentageOfWork(DEFAULT_PERCENTAGE_OF_WORK)
            .projectName(DEFAULT_PROJECT_NAME)
            .empName(DEFAULT_EMP_NAME)
            .roleName(DEFAULT_ROLE_NAME)
            .clientName(DEFAULT_CLIENT_NAME)
            .estimatedStaffHours(DEFAULT_ESTIMATED_STAFF_HOURS)
            .actualStaffHours(DEFAULT_ACTUAL_STAFF_HOURS)
            .location(DEFAULT_LOCATION)
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return projectResourceAssign;
    }

    @Before
    public void initTest() {
        projectResourceAssign = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectResourceAssign() throws Exception {
        int databaseSizeBeforeCreate = projectResourceAssignRepository.findAll().size();

        // Create the ProjectResourceAssign
        restProjectResourceAssignMockMvc.perform(post("/api/project-resource-assigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectResourceAssign)))
            .andExpect(status().isCreated());

        // Validate the ProjectResourceAssign in the database
        List<ProjectResourceAssign> projectResourceAssignList = projectResourceAssignRepository.findAll();
        assertThat(projectResourceAssignList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectResourceAssign testProjectResourceAssign = projectResourceAssignList.get(projectResourceAssignList.size() - 1);
        assertThat(testProjectResourceAssign.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testProjectResourceAssign.getEmpId()).isEqualTo(DEFAULT_EMP_ID);
        assertThat(testProjectResourceAssign.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testProjectResourceAssign.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testProjectResourceAssign.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testProjectResourceAssign.getBillingType()).isEqualTo(DEFAULT_BILLING_TYPE);
        assertThat(testProjectResourceAssign.getBillValue()).isEqualTo(DEFAULT_BILL_VALUE);
        assertThat(testProjectResourceAssign.getPercentageOfWork()).isEqualTo(DEFAULT_PERCENTAGE_OF_WORK);
        assertThat(testProjectResourceAssign.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testProjectResourceAssign.getEmpName()).isEqualTo(DEFAULT_EMP_NAME);
        assertThat(testProjectResourceAssign.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
        assertThat(testProjectResourceAssign.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testProjectResourceAssign.getEstimatedStaffHours()).isEqualTo(DEFAULT_ESTIMATED_STAFF_HOURS);
        assertThat(testProjectResourceAssign.getActualStaffHours()).isEqualTo(DEFAULT_ACTUAL_STAFF_HOURS);
        assertThat(testProjectResourceAssign.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testProjectResourceAssign.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProjectResourceAssign.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testProjectResourceAssign.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createProjectResourceAssignWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectResourceAssignRepository.findAll().size();

        // Create the ProjectResourceAssign with an existing ID
        projectResourceAssign.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectResourceAssignMockMvc.perform(post("/api/project-resource-assigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectResourceAssign)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectResourceAssign in the database
        List<ProjectResourceAssign> projectResourceAssignList = projectResourceAssignRepository.findAll();
        assertThat(projectResourceAssignList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProjectResourceAssigns() throws Exception {
        // Initialize the database
        projectResourceAssignRepository.saveAndFlush(projectResourceAssign);

        // Get all the projectResourceAssignList
        restProjectResourceAssignMockMvc.perform(get("/api/project-resource-assigns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectResourceAssign.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID)))
            .andExpect(jsonPath("$.[*].empId").value(hasItem(DEFAULT_EMP_ID)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].billingType").value(hasItem(DEFAULT_BILLING_TYPE)))
            .andExpect(jsonPath("$.[*].billValue").value(hasItem(DEFAULT_BILL_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].percentageOfWork").value(hasItem(DEFAULT_PERCENTAGE_OF_WORK.doubleValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].empName").value(hasItem(DEFAULT_EMP_NAME.toString())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].estimatedStaffHours").value(hasItem(DEFAULT_ESTIMATED_STAFF_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].actualStaffHours").value(hasItem(DEFAULT_ACTUAL_STAFF_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectResourceAssign() throws Exception {
        // Initialize the database
        projectResourceAssignRepository.saveAndFlush(projectResourceAssign);

        // Get the projectResourceAssign
        restProjectResourceAssignMockMvc.perform(get("/api/project-resource-assigns/{id}", projectResourceAssign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectResourceAssign.getId().intValue()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID))
            .andExpect(jsonPath("$.empId").value(DEFAULT_EMP_ID))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.billingType").value(DEFAULT_BILLING_TYPE))
            .andExpect(jsonPath("$.billValue").value(DEFAULT_BILL_VALUE.doubleValue()))
            .andExpect(jsonPath("$.percentageOfWork").value(DEFAULT_PERCENTAGE_OF_WORK.doubleValue()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME.toString()))
            .andExpect(jsonPath("$.empName").value(DEFAULT_EMP_NAME.toString()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME.toString()))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME.toString()))
            .andExpect(jsonPath("$.estimatedStaffHours").value(DEFAULT_ESTIMATED_STAFF_HOURS.doubleValue()))
            .andExpect(jsonPath("$.actualStaffHours").value(DEFAULT_ACTUAL_STAFF_HOURS.doubleValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectResourceAssign() throws Exception {
        // Get the projectResourceAssign
        restProjectResourceAssignMockMvc.perform(get("/api/project-resource-assigns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectResourceAssign() throws Exception {
        // Initialize the database
        projectResourceAssignService.save(projectResourceAssign);

        int databaseSizeBeforeUpdate = projectResourceAssignRepository.findAll().size();

        // Update the projectResourceAssign
        ProjectResourceAssign updatedProjectResourceAssign = projectResourceAssignRepository.findById(projectResourceAssign.getId()).get();
        // Disconnect from session so that the updates on updatedProjectResourceAssign are not directly saved in db
        em.detach(updatedProjectResourceAssign);
        updatedProjectResourceAssign
            .projectId(UPDATED_PROJECT_ID)
            .empId(UPDATED_EMP_ID)
            .role(UPDATED_ROLE)
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .billingType(UPDATED_BILLING_TYPE)
            .billValue(UPDATED_BILL_VALUE)
            .percentageOfWork(UPDATED_PERCENTAGE_OF_WORK)
            .projectName(UPDATED_PROJECT_NAME)
            .empName(UPDATED_EMP_NAME)
            .roleName(UPDATED_ROLE_NAME)
            .clientName(UPDATED_CLIENT_NAME)
            .estimatedStaffHours(UPDATED_ESTIMATED_STAFF_HOURS)
            .actualStaffHours(UPDATED_ACTUAL_STAFF_HOURS)
            .location(UPDATED_LOCATION)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restProjectResourceAssignMockMvc.perform(put("/api/project-resource-assigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectResourceAssign)))
            .andExpect(status().isOk());

        // Validate the ProjectResourceAssign in the database
        List<ProjectResourceAssign> projectResourceAssignList = projectResourceAssignRepository.findAll();
        assertThat(projectResourceAssignList).hasSize(databaseSizeBeforeUpdate);
        ProjectResourceAssign testProjectResourceAssign = projectResourceAssignList.get(projectResourceAssignList.size() - 1);
        assertThat(testProjectResourceAssign.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
        assertThat(testProjectResourceAssign.getEmpId()).isEqualTo(UPDATED_EMP_ID);
        assertThat(testProjectResourceAssign.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testProjectResourceAssign.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testProjectResourceAssign.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testProjectResourceAssign.getBillingType()).isEqualTo(UPDATED_BILLING_TYPE);
        assertThat(testProjectResourceAssign.getBillValue()).isEqualTo(UPDATED_BILL_VALUE);
        assertThat(testProjectResourceAssign.getPercentageOfWork()).isEqualTo(UPDATED_PERCENTAGE_OF_WORK);
        assertThat(testProjectResourceAssign.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testProjectResourceAssign.getEmpName()).isEqualTo(UPDATED_EMP_NAME);
        assertThat(testProjectResourceAssign.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
        assertThat(testProjectResourceAssign.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testProjectResourceAssign.getEstimatedStaffHours()).isEqualTo(UPDATED_ESTIMATED_STAFF_HOURS);
        assertThat(testProjectResourceAssign.getActualStaffHours()).isEqualTo(UPDATED_ACTUAL_STAFF_HOURS);
        assertThat(testProjectResourceAssign.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProjectResourceAssign.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProjectResourceAssign.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProjectResourceAssign.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectResourceAssign() throws Exception {
        int databaseSizeBeforeUpdate = projectResourceAssignRepository.findAll().size();

        // Create the ProjectResourceAssign

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectResourceAssignMockMvc.perform(put("/api/project-resource-assigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectResourceAssign)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectResourceAssign in the database
        List<ProjectResourceAssign> projectResourceAssignList = projectResourceAssignRepository.findAll();
        assertThat(projectResourceAssignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectResourceAssign() throws Exception {
        // Initialize the database
        projectResourceAssignService.save(projectResourceAssign);

        int databaseSizeBeforeDelete = projectResourceAssignRepository.findAll().size();

        // Get the projectResourceAssign
        restProjectResourceAssignMockMvc.perform(delete("/api/project-resource-assigns/{id}", projectResourceAssign.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProjectResourceAssign> projectResourceAssignList = projectResourceAssignRepository.findAll();
        assertThat(projectResourceAssignList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectResourceAssign.class);
        ProjectResourceAssign projectResourceAssign1 = new ProjectResourceAssign();
        projectResourceAssign1.setId(1L);
        ProjectResourceAssign projectResourceAssign2 = new ProjectResourceAssign();
        projectResourceAssign2.setId(projectResourceAssign1.getId());
        assertThat(projectResourceAssign1).isEqualTo(projectResourceAssign2);
        projectResourceAssign2.setId(2L);
        assertThat(projectResourceAssign1).isNotEqualTo(projectResourceAssign2);
        projectResourceAssign1.setId(null);
        assertThat(projectResourceAssign1).isNotEqualTo(projectResourceAssign2);
    }
}
