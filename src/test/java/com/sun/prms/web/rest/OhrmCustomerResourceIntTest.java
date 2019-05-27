package com.sun.prms.web.rest;

import com.sun.prms.PrmsApp;

import com.sun.prms.domain.OhrmCustomer;
import com.sun.prms.repository.OhrmCustomerRepository;
import com.sun.prms.service.OhrmCustomerService;
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
import java.util.List;


import static com.sun.prms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OhrmCustomerResource REST controller.
 *
 * @see OhrmCustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PrmsApp.class)
public class OhrmCustomerResourceIntTest {

    private static final Integer DEFAULT_CUSTOMER_ID = 1;
    private static final Integer UPDATED_CUSTOMER_ID = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_DELETED = 1;
    private static final Integer UPDATED_IS_DELETED = 2;

    @Autowired
    private OhrmCustomerRepository ohrmCustomerRepository;

    @Autowired
    private OhrmCustomerService ohrmCustomerService;

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

    private MockMvc restOhrmCustomerMockMvc;

    private OhrmCustomer ohrmCustomer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OhrmCustomerResource ohrmCustomerResource = new OhrmCustomerResource(ohrmCustomerService);
        this.restOhrmCustomerMockMvc = MockMvcBuilders.standaloneSetup(ohrmCustomerResource)
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
    public static OhrmCustomer createEntity(EntityManager em) {
        OhrmCustomer ohrmCustomer = new OhrmCustomer()
            .customerId(DEFAULT_CUSTOMER_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .isDeleted(DEFAULT_IS_DELETED);
        return ohrmCustomer;
    }

    @Before
    public void initTest() {
        ohrmCustomer = createEntity(em);
    }

    @Test
    @Transactional
    public void createOhrmCustomer() throws Exception {
        int databaseSizeBeforeCreate = ohrmCustomerRepository.findAll().size();

        // Create the OhrmCustomer
        restOhrmCustomerMockMvc.perform(post("/api/ohrm-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ohrmCustomer)))
            .andExpect(status().isCreated());

        // Validate the OhrmCustomer in the database
        List<OhrmCustomer> ohrmCustomerList = ohrmCustomerRepository.findAll();
        assertThat(ohrmCustomerList).hasSize(databaseSizeBeforeCreate + 1);
        OhrmCustomer testOhrmCustomer = ohrmCustomerList.get(ohrmCustomerList.size() - 1);
        assertThat(testOhrmCustomer.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testOhrmCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOhrmCustomer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOhrmCustomer.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    public void createOhrmCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ohrmCustomerRepository.findAll().size();

        // Create the OhrmCustomer with an existing ID
        ohrmCustomer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOhrmCustomerMockMvc.perform(post("/api/ohrm-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ohrmCustomer)))
            .andExpect(status().isBadRequest());

        // Validate the OhrmCustomer in the database
        List<OhrmCustomer> ohrmCustomerList = ohrmCustomerRepository.findAll();
        assertThat(ohrmCustomerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOhrmCustomers() throws Exception {
        // Initialize the database
        ohrmCustomerRepository.saveAndFlush(ohrmCustomer);

        // Get all the ohrmCustomerList
        restOhrmCustomerMockMvc.perform(get("/api/ohrm-customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ohrmCustomer.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED)));
    }
    
    @Test
    @Transactional
    public void getOhrmCustomer() throws Exception {
        // Initialize the database
        ohrmCustomerRepository.saveAndFlush(ohrmCustomer);

        // Get the ohrmCustomer
        restOhrmCustomerMockMvc.perform(get("/api/ohrm-customers/{id}", ohrmCustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ohrmCustomer.getId().intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED));
    }

    @Test
    @Transactional
    public void getNonExistingOhrmCustomer() throws Exception {
        // Get the ohrmCustomer
        restOhrmCustomerMockMvc.perform(get("/api/ohrm-customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOhrmCustomer() throws Exception {
        // Initialize the database
        ohrmCustomerService.save(ohrmCustomer);

        int databaseSizeBeforeUpdate = ohrmCustomerRepository.findAll().size();

        // Update the ohrmCustomer
        OhrmCustomer updatedOhrmCustomer = ohrmCustomerRepository.findById(ohrmCustomer.getId()).get();
        // Disconnect from session so that the updates on updatedOhrmCustomer are not directly saved in db
        em.detach(updatedOhrmCustomer);
        updatedOhrmCustomer
            .customerId(UPDATED_CUSTOMER_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isDeleted(UPDATED_IS_DELETED);

        restOhrmCustomerMockMvc.perform(put("/api/ohrm-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOhrmCustomer)))
            .andExpect(status().isOk());

        // Validate the OhrmCustomer in the database
        List<OhrmCustomer> ohrmCustomerList = ohrmCustomerRepository.findAll();
        assertThat(ohrmCustomerList).hasSize(databaseSizeBeforeUpdate);
        OhrmCustomer testOhrmCustomer = ohrmCustomerList.get(ohrmCustomerList.size() - 1);
        assertThat(testOhrmCustomer.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testOhrmCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOhrmCustomer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOhrmCustomer.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingOhrmCustomer() throws Exception {
        int databaseSizeBeforeUpdate = ohrmCustomerRepository.findAll().size();

        // Create the OhrmCustomer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOhrmCustomerMockMvc.perform(put("/api/ohrm-customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ohrmCustomer)))
            .andExpect(status().isBadRequest());

        // Validate the OhrmCustomer in the database
        List<OhrmCustomer> ohrmCustomerList = ohrmCustomerRepository.findAll();
        assertThat(ohrmCustomerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOhrmCustomer() throws Exception {
        // Initialize the database
        ohrmCustomerService.save(ohrmCustomer);

        int databaseSizeBeforeDelete = ohrmCustomerRepository.findAll().size();

        // Get the ohrmCustomer
        restOhrmCustomerMockMvc.perform(delete("/api/ohrm-customers/{id}", ohrmCustomer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OhrmCustomer> ohrmCustomerList = ohrmCustomerRepository.findAll();
        assertThat(ohrmCustomerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OhrmCustomer.class);
        OhrmCustomer ohrmCustomer1 = new OhrmCustomer();
        ohrmCustomer1.setId(1L);
        OhrmCustomer ohrmCustomer2 = new OhrmCustomer();
        ohrmCustomer2.setId(ohrmCustomer1.getId());
        assertThat(ohrmCustomer1).isEqualTo(ohrmCustomer2);
        ohrmCustomer2.setId(2L);
        assertThat(ohrmCustomer1).isNotEqualTo(ohrmCustomer2);
        ohrmCustomer1.setId(null);
        assertThat(ohrmCustomer1).isNotEqualTo(ohrmCustomer2);
    }
}
