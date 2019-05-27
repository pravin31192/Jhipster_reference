package com.sun.prms.web.rest;

import com.sun.prms.PrmsApp;

import com.sun.prms.domain.Billing;
import com.sun.prms.repository.BillingRepository;
import com.sun.prms.service.BillingService;
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
 * Test class for the BillingResource REST controller.
 *
 * @see BillingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrmsApp.class)
public class BillingResourceIntTest {

    private static final Integer DEFAULT_CLIENT_ID = 1;
    private static final Integer UPDATED_CLIENT_ID = 2;

    private static final Integer DEFAULT_PROJECT = 1;
    private static final Integer UPDATED_PROJECT = 2;

    private static final Integer DEFAULT_RESOURCE = 1;
    private static final Integer UPDATED_RESOURCE = 2;

    private static final Double DEFAULT_HOURS_ALLOCATED = 1D;
    private static final Double UPDATED_HOURS_ALLOCATED = 2D;

    private static final Double DEFAULT_BILL_RATE = 1D;
    private static final Double UPDATED_BILL_RATE = 2D;

    private static final Double DEFAULT_SALARY = 1D;
    private static final Double UPDATED_SALARY = 2D;

    private static final Double DEFAULT_OTHER_COST = 1D;
    private static final Double UPDATED_OTHER_COST = 2D;

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private BillingService billingService;

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

    private MockMvc restBillingMockMvc;

    private Billing billing;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillingResource billingResource = new BillingResource(billingService);
        this.restBillingMockMvc = MockMvcBuilders.standaloneSetup(billingResource)
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
    public static Billing createEntity(EntityManager em) {
        Billing billing = new Billing()
            .clientId(DEFAULT_CLIENT_ID)
            .project(DEFAULT_PROJECT)
            .resource(DEFAULT_RESOURCE)
            .hoursAllocated(DEFAULT_HOURS_ALLOCATED)
            .billRate(DEFAULT_BILL_RATE)
            .salary(DEFAULT_SALARY)
            .otherCost(DEFAULT_OTHER_COST)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return billing;
    }

    @Before
    public void initTest() {
        billing = createEntity(em);
    }

    @Test
    @Transactional
    public void createBilling() throws Exception {
        int databaseSizeBeforeCreate = billingRepository.findAll().size();

        // Create the Billing
        restBillingMockMvc.perform(post("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billing)))
            .andExpect(status().isCreated());

        // Validate the Billing in the database
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeCreate + 1);
        Billing testBilling = billingList.get(billingList.size() - 1);
        assertThat(testBilling.getClientName()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testBilling.getProject()).isEqualTo(DEFAULT_PROJECT);
        assertThat(testBilling.getResource()).isEqualTo(DEFAULT_RESOURCE);
        assertThat(testBilling.getHoursAllocated()).isEqualTo(DEFAULT_HOURS_ALLOCATED);
        assertThat(testBilling.getBillRate()).isEqualTo(DEFAULT_BILL_RATE);
        assertThat(testBilling.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testBilling.getOtherCost()).isEqualTo(DEFAULT_OTHER_COST);
        assertThat(testBilling.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testBilling.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createBillingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingRepository.findAll().size();

        // Create the Billing with an existing ID
        billing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingMockMvc.perform(post("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billing)))
            .andExpect(status().isBadRequest());

        // Validate the Billing in the database
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBillings() throws Exception {
        // Initialize the database
        billingRepository.saveAndFlush(billing);

        // Get all the billingList
        restBillingMockMvc.perform(get("/api/billings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billing.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT)))
            .andExpect(jsonPath("$.[*].resource").value(hasItem(DEFAULT_RESOURCE)))
            .andExpect(jsonPath("$.[*].hoursAllocated").value(hasItem(DEFAULT_HOURS_ALLOCATED.doubleValue())))
            .andExpect(jsonPath("$.[*].billRate").value(hasItem(DEFAULT_BILL_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].otherCost").value(hasItem(DEFAULT_OTHER_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getBilling() throws Exception {
        // Initialize the database
        billingRepository.saveAndFlush(billing);

        // Get the billing
        restBillingMockMvc.perform(get("/api/billings/{id}", billing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billing.getId().intValue()))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT))
            .andExpect(jsonPath("$.resource").value(DEFAULT_RESOURCE))
            .andExpect(jsonPath("$.hoursAllocated").value(DEFAULT_HOURS_ALLOCATED.doubleValue()))
            .andExpect(jsonPath("$.billRate").value(DEFAULT_BILL_RATE.doubleValue()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()))
            .andExpect(jsonPath("$.otherCost").value(DEFAULT_OTHER_COST.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBilling() throws Exception {
        // Get the billing
        restBillingMockMvc.perform(get("/api/billings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBilling() throws Exception {
        // Initialize the database
        billingService.save(billing);

        int databaseSizeBeforeUpdate = billingRepository.findAll().size();

        // Update the billing
        Billing updatedBilling = billingRepository.findById(billing.getId()).get();
        // Disconnect from session so that the updates on updatedBilling are not directly saved in db
        em.detach(updatedBilling);
        updatedBilling
            .clientName(UPDATED_CLIENT_NAME)
            .project(UPDATED_PROJECT)
            .resource(UPDATED_RESOURCE)
            .hoursAllocated(UPDATED_HOURS_ALLOCATED)
            .billRate(UPDATED_BILL_RATE)
            .salary(UPDATED_SALARY)
            .otherCost(UPDATED_OTHER_COST)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restBillingMockMvc.perform(put("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBilling)))
            .andExpect(status().isOk());

        // Validate the Billing in the database
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeUpdate);
        Billing testBilling = billingList.get(billingList.size() - 1);
        assertThat(testBilling.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testBilling.getProject()).isEqualTo(UPDATED_PROJECT);
        assertThat(testBilling.getResource()).isEqualTo(UPDATED_RESOURCE);
        assertThat(testBilling.getHoursAllocated()).isEqualTo(UPDATED_HOURS_ALLOCATED);
        assertThat(testBilling.getBillRate()).isEqualTo(UPDATED_BILL_RATE);
        assertThat(testBilling.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testBilling.getOtherCost()).isEqualTo(UPDATED_OTHER_COST);
        assertThat(testBilling.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testBilling.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingBilling() throws Exception {
        int databaseSizeBeforeUpdate = billingRepository.findAll().size();

        // Create the Billing

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingMockMvc.perform(put("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billing)))
            .andExpect(status().isBadRequest());

        // Validate the Billing in the database
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBilling() throws Exception {
        // Initialize the database
        billingService.save(billing);

        int databaseSizeBeforeDelete = billingRepository.findAll().size();

        // Get the billing
        restBillingMockMvc.perform(delete("/api/billings/{id}", billing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Billing.class);
        Billing billing1 = new Billing();
        billing1.setId(1L);
        Billing billing2 = new Billing();
        billing2.setId(billing1.getId());
        assertThat(billing1).isEqualTo(billing2);
        billing2.setId(2L);
        assertThat(billing1).isNotEqualTo(billing2);
        billing1.setId(null);
        assertThat(billing1).isNotEqualTo(billing2);
    }
}
