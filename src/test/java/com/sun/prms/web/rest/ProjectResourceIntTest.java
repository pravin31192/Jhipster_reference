package com.sun.prms.web.rest;

import com.sun.prms.PrmsApp;

import com.sun.prms.domain.Project;
import com.sun.prms.repository.ProjectRepository;
import com.sun.prms.service.ProjectService;
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
 * Test class for the ProjectResource REST controller.
 *
 * @see ProjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrmsApp.class)
public class ProjectResourceIntTest {

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_CODE = "BBBBBBBBBB";

    private static final Float DEFAULT_BILLABLE_VALUE = 1F;
    private static final Float UPDATED_BILLABLE_VALUE = 2F;

    private static final Integer DEFAULT_NO_OF_RESOURCES = 1;
    private static final Integer UPDATED_NO_OF_RESOURCES = 2;

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_ESTIMATED_STAFF_HOURS = 1F;
    private static final Float UPDATED_ESTIMATED_STAFF_HOURS = 2F;

    private static final Float DEFAULT_ACTUAL_STAFF_HOURS = 1F;
    private static final Float UPDATED_ACTUAL_STAFF_HOURS = 2F;

    private static final Float DEFAULT_PERCENTAGE_COMPLETE = 1F;
    private static final Float UPDATED_PERCENTAGE_COMPLETE = 2F;

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERABLES = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERABLES = "BBBBBBBBBB";

    private static final String DEFAULT_ATTACHMENTS = "AAAAAAAAAA";
    private static final String UPDATED_ATTACHMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

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

    private MockMvc restProjectMockMvc;

    private Project project;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectResource projectResource = new ProjectResource(projectService);
        this.restProjectMockMvc = MockMvcBuilders.standaloneSetup(projectResource)
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
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .projectName(DEFAULT_PROJECT_NAME)
            .clientName(DEFAULT_CLIENT_NAME)
            .clientCode(DEFAULT_CLIENT_CODE)
            .projectCode(DEFAULT_PROJECT_CODE)
            .billableValue(DEFAULT_BILLABLE_VALUE)
            .noOfResources(DEFAULT_NO_OF_RESOURCES)
            .createdBy(DEFAULT_CREATED_BY)
            .status(DEFAULT_STATUS)
            .type(DEFAULT_TYPE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .estimatedStaffHours(DEFAULT_ESTIMATED_STAFF_HOURS)
            .actualStaffHours(DEFAULT_ACTUAL_STAFF_HOURS)
            .percentageComplete(DEFAULT_PERCENTAGE_COMPLETE)
            .details(DEFAULT_DETAILS)
            .deliverables(DEFAULT_DELIVERABLES)
            .attachments(DEFAULT_ATTACHMENTS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return project;
    }

    @Before
    public void initTest() {
        project = createEntity(em);
    }

    @Test
    @Transactional
    public void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testProject.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testProject.getClientCode()).isEqualTo(DEFAULT_CLIENT_CODE);
        assertThat(testProject.getProjectCode()).isEqualTo(DEFAULT_PROJECT_CODE);
        assertThat(testProject.getBillableValue()).isEqualTo(DEFAULT_BILLABLE_VALUE);
        assertThat(testProject.getNoOfResources()).isEqualTo(DEFAULT_NO_OF_RESOURCES);
        assertThat(testProject.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProject.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProject.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProject.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testProject.getEstimatedStaffHours()).isEqualTo(DEFAULT_ESTIMATED_STAFF_HOURS);
        assertThat(testProject.getActualStaffHours()).isEqualTo(DEFAULT_ACTUAL_STAFF_HOURS);
        assertThat(testProject.getPercentageComplete()).isEqualTo(DEFAULT_PERCENTAGE_COMPLETE);
        assertThat(testProject.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testProject.getDeliverables()).isEqualTo(DEFAULT_DELIVERABLES);
        assertThat(testProject.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testProject.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testProject.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project with an existing ID
        project.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList
        restProjectMockMvc.perform(get("/api/projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientCode").value(hasItem(DEFAULT_CLIENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].projectCode").value(hasItem(DEFAULT_PROJECT_CODE.toString())))
            .andExpect(jsonPath("$.[*].billableValue").value(hasItem(DEFAULT_BILLABLE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfResources").value(hasItem(DEFAULT_NO_OF_RESOURCES)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].estimatedStaffHours").value(hasItem(DEFAULT_ESTIMATED_STAFF_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].actualStaffHours").value(hasItem(DEFAULT_ACTUAL_STAFF_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].percentageComplete").value(hasItem(DEFAULT_PERCENTAGE_COMPLETE.doubleValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].deliverables").value(hasItem(DEFAULT_DELIVERABLES.toString())))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(DEFAULT_ATTACHMENTS.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME.toString()))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME.toString()))
            .andExpect(jsonPath("$.clientCode").value(DEFAULT_CLIENT_CODE.toString()))
            .andExpect(jsonPath("$.projectCode").value(DEFAULT_PROJECT_CODE.toString()))
            .andExpect(jsonPath("$.billableValue").value(DEFAULT_BILLABLE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.noOfResources").value(DEFAULT_NO_OF_RESOURCES))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.estimatedStaffHours").value(DEFAULT_ESTIMATED_STAFF_HOURS.doubleValue()))
            .andExpect(jsonPath("$.actualStaffHours").value(DEFAULT_ACTUAL_STAFF_HOURS.doubleValue()))
            .andExpect(jsonPath("$.percentageComplete").value(DEFAULT_PERCENTAGE_COMPLETE.doubleValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.deliverables").value(DEFAULT_DELIVERABLES.toString()))
            .andExpect(jsonPath("$.attachments").value(DEFAULT_ATTACHMENTS.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProject() throws Exception {
        // Initialize the database
        projectService.save(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).get();
        // Disconnect from session so that the updates on updatedProject are not directly saved in db
        em.detach(updatedProject);
        updatedProject
            .projectName(UPDATED_PROJECT_NAME)
            .clientName(UPDATED_CLIENT_NAME)
            .clientCode(UPDATED_CLIENT_CODE)
            .projectCode(UPDATED_PROJECT_CODE)
            .billableValue(UPDATED_BILLABLE_VALUE)
            .noOfResources(UPDATED_NO_OF_RESOURCES)
            .createdBy(UPDATED_CREATED_BY)
            .status(UPDATED_STATUS)
            .type(UPDATED_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .estimatedStaffHours(UPDATED_ESTIMATED_STAFF_HOURS)
            .actualStaffHours(UPDATED_ACTUAL_STAFF_HOURS)
            .percentageComplete(UPDATED_PERCENTAGE_COMPLETE)
            .details(UPDATED_DETAILS)
            .deliverables(UPDATED_DELIVERABLES)
            .attachments(UPDATED_ATTACHMENTS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProject)))
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testProject.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testProject.getClientCode()).isEqualTo(UPDATED_CLIENT_CODE);
        assertThat(testProject.getProjectCode()).isEqualTo(UPDATED_PROJECT_CODE);
        assertThat(testProject.getBillableValue()).isEqualTo(UPDATED_BILLABLE_VALUE);
        assertThat(testProject.getNoOfResources()).isEqualTo(UPDATED_NO_OF_RESOURCES);
        assertThat(testProject.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProject.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProject.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProject.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProject.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProject.getEstimatedStaffHours()).isEqualTo(UPDATED_ESTIMATED_STAFF_HOURS);
        assertThat(testProject.getActualStaffHours()).isEqualTo(UPDATED_ACTUAL_STAFF_HOURS);
        assertThat(testProject.getPercentageComplete()).isEqualTo(UPDATED_PERCENTAGE_COMPLETE);
        assertThat(testProject.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testProject.getDeliverables()).isEqualTo(UPDATED_DELIVERABLES);
        assertThat(testProject.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testProject.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProject.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Create the Project

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProject() throws Exception {
        // Initialize the database
        projectService.save(project);

        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Get the project
        restProjectMockMvc.perform(delete("/api/projects/{id}", project.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Project.class);
        Project project1 = new Project();
        project1.setId(1L);
        Project project2 = new Project();
        project2.setId(project1.getId());
        assertThat(project1).isEqualTo(project2);
        project2.setId(2L);
        assertThat(project1).isNotEqualTo(project2);
        project1.setId(null);
        assertThat(project1).isNotEqualTo(project2);
    }
}
