package com.sun.prms.web.rest;

import com.sun.prms.PrmsApp;

import com.sun.prms.domain.EmployeeSalary;
import com.sun.prms.repository.EmployeeSalaryRepository;
import com.sun.prms.service.EmployeeSalaryService;
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
 * Test class for the EmployeeSalaryResource REST controller.
 *
 * @see EmployeeSalaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrmsApp.class)
public class EmployeeSalaryResourceIntTest {

    private static final Integer DEFAULT_EMPLOYEE_ID = 1;
    private static final Integer UPDATED_EMPLOYEE_ID = 2;

    private static final Float DEFAULT_SALARY = 1F;
    private static final Float UPDATED_SALARY = 2F;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    @Autowired
    private EmployeeSalaryRepository employeeSalaryRepository;

    @Autowired
    private EmployeeSalaryService employeeSalaryService;

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

    private MockMvc restEmployeeSalaryMockMvc;

    private EmployeeSalary employeeSalary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeSalaryResource employeeSalaryResource = new EmployeeSalaryResource(employeeSalaryService);
        this.restEmployeeSalaryMockMvc = MockMvcBuilders.standaloneSetup(employeeSalaryResource)
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
    public static EmployeeSalary createEntity(EntityManager em) {
        EmployeeSalary employeeSalary = new EmployeeSalary()
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .salary(DEFAULT_SALARY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .createdBy(DEFAULT_CREATED_BY);
        return employeeSalary;
    }

    @Before
    public void initTest() {
        employeeSalary = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeSalary() throws Exception {
        int databaseSizeBeforeCreate = employeeSalaryRepository.findAll().size();

        // Create the EmployeeSalary
        restEmployeeSalaryMockMvc.perform(post("/api/employee-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isCreated());

        // Validate the EmployeeSalary in the database
        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeSalary testEmployeeSalary = employeeSalaryList.get(employeeSalaryList.size() - 1);
        assertThat(testEmployeeSalary.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testEmployeeSalary.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testEmployeeSalary.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testEmployeeSalary.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testEmployeeSalary.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createEmployeeSalaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeSalaryRepository.findAll().size();

        // Create the EmployeeSalary with an existing ID
        employeeSalary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeSalaryMockMvc.perform(post("/api/employee-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSalary in the database
        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployeeSalaries() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get all the employeeSalaryList
        restEmployeeSalaryMockMvc.perform(get("/api/employee-salaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeSalary.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }
    
    @Test
    @Transactional
    public void getEmployeeSalary() throws Exception {
        // Initialize the database
        employeeSalaryRepository.saveAndFlush(employeeSalary);

        // Get the employeeSalary
        restEmployeeSalaryMockMvc.perform(get("/api/employee-salaries/{id}", employeeSalary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeSalary.getId().intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeSalary() throws Exception {
        // Get the employeeSalary
        restEmployeeSalaryMockMvc.perform(get("/api/employee-salaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeSalary() throws Exception {
        // Initialize the database
        employeeSalaryService.save(employeeSalary);

        int databaseSizeBeforeUpdate = employeeSalaryRepository.findAll().size();

        // Update the employeeSalary
        EmployeeSalary updatedEmployeeSalary = employeeSalaryRepository.findById(employeeSalary.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeSalary are not directly saved in db
        em.detach(updatedEmployeeSalary);
        updatedEmployeeSalary
            .employeeId(UPDATED_EMPLOYEE_ID)
            .salary(UPDATED_SALARY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .createdBy(UPDATED_CREATED_BY);

        restEmployeeSalaryMockMvc.perform(put("/api/employee-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeSalary)))
            .andExpect(status().isOk());

        // Validate the EmployeeSalary in the database
        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSalary testEmployeeSalary = employeeSalaryList.get(employeeSalaryList.size() - 1);
        assertThat(testEmployeeSalary.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeSalary.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testEmployeeSalary.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testEmployeeSalary.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testEmployeeSalary.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeSalary() throws Exception {
        int databaseSizeBeforeUpdate = employeeSalaryRepository.findAll().size();

        // Create the EmployeeSalary

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeSalaryMockMvc.perform(put("/api/employee-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalary)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSalary in the database
        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeSalary() throws Exception {
        // Initialize the database
        employeeSalaryService.save(employeeSalary);

        int databaseSizeBeforeDelete = employeeSalaryRepository.findAll().size();

        // Get the employeeSalary
        restEmployeeSalaryMockMvc.perform(delete("/api/employee-salaries/{id}", employeeSalary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeSalary> employeeSalaryList = employeeSalaryRepository.findAll();
        assertThat(employeeSalaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSalary.class);
        EmployeeSalary employeeSalary1 = new EmployeeSalary();
        employeeSalary1.setId(1L);
        EmployeeSalary employeeSalary2 = new EmployeeSalary();
        employeeSalary2.setId(employeeSalary1.getId());
        assertThat(employeeSalary1).isEqualTo(employeeSalary2);
        employeeSalary2.setId(2L);
        assertThat(employeeSalary1).isNotEqualTo(employeeSalary2);
        employeeSalary1.setId(null);
        assertThat(employeeSalary1).isNotEqualTo(employeeSalary2);
    }
}
