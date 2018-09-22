package sbnz.soft.nikola.web.rest;

import sbnz.soft.nikola.SbnzApp;

import sbnz.soft.nikola.domain.Medicine;
import sbnz.soft.nikola.repository.MedicineRepository;
import sbnz.soft.nikola.service.MedicineService;
import sbnz.soft.nikola.service.dto.MedicineDTO;
import sbnz.soft.nikola.service.mapper.MedicineMapper;
import sbnz.soft.nikola.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static sbnz.soft.nikola.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import sbnz.soft.nikola.domain.enumeration.MedicineType;
/**
 * Test class for the MedicineResource REST controller.
 *
 * @see MedicineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbnzApp.class)
public class MedicineResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final MedicineType DEFAULT_TYPE = MedicineType.ANTIBIOTIC;
    private static final MedicineType UPDATED_TYPE = MedicineType.ANELGETIC;

    @Autowired
    private MedicineRepository medicineRepository;

    @Mock
    private MedicineRepository medicineRepositoryMock;

    @Autowired
    private MedicineMapper medicineMapper;
    

    @Mock
    private MedicineService medicineServiceMock;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedicineMockMvc;

    private Medicine medicine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicineResource medicineResource = new MedicineResource(medicineService);
        this.restMedicineMockMvc = MockMvcBuilders.standaloneSetup(medicineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicine createEntity(EntityManager em) {
        Medicine medicine = new Medicine()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE);
        return medicine;
    }

    @Before
    public void initTest() {
        medicine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicine() throws Exception {
        int databaseSizeBeforeCreate = medicineRepository.findAll().size();

        // Create the Medicine
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);
        restMedicineMockMvc.perform(post("/api/medicines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicineDTO)))
            .andExpect(status().isCreated());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeCreate + 1);
        Medicine testMedicine = medicineList.get(medicineList.size() - 1);
        assertThat(testMedicine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMedicine.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createMedicineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicineRepository.findAll().size();

        // Create the Medicine with an existing ID
        medicine.setId(1L);
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicineMockMvc.perform(post("/api/medicines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicineRepository.findAll().size();
        // set the field null
        medicine.setName(null);

        // Create the Medicine, which fails.
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);

        restMedicineMockMvc.perform(post("/api/medicines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicineDTO)))
            .andExpect(status().isBadRequest());

        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicineRepository.findAll().size();
        // set the field null
        medicine.setType(null);

        // Create the Medicine, which fails.
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);

        restMedicineMockMvc.perform(post("/api/medicines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicineDTO)))
            .andExpect(status().isBadRequest());

        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedicines() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList
        restMedicineMockMvc.perform(get("/api/medicines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    public void getAllMedicinesWithEagerRelationshipsIsEnabled() throws Exception {
        MedicineResource medicineResource = new MedicineResource(medicineServiceMock);
        when(medicineServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMedicineMockMvc = MockMvcBuilders.standaloneSetup(medicineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMedicineMockMvc.perform(get("/api/medicines?eagerload=true"))
        .andExpect(status().isOk());

        verify(medicineServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllMedicinesWithEagerRelationshipsIsNotEnabled() throws Exception {
        MedicineResource medicineResource = new MedicineResource(medicineServiceMock);
            when(medicineServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMedicineMockMvc = MockMvcBuilders.standaloneSetup(medicineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMedicineMockMvc.perform(get("/api/medicines?eagerload=true"))
        .andExpect(status().isOk());

            verify(medicineServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMedicine() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get the medicine
        restMedicineMockMvc.perform(get("/api/medicines/{id}", medicine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicine.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicine() throws Exception {
        // Get the medicine
        restMedicineMockMvc.perform(get("/api/medicines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicine() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();

        // Update the medicine
        Medicine updatedMedicine = medicineRepository.findById(medicine.getId()).get();
        // Disconnect from session so that the updates on updatedMedicine are not directly saved in db
        em.detach(updatedMedicine);
        updatedMedicine
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE);
        MedicineDTO medicineDTO = medicineMapper.toDto(updatedMedicine);

        restMedicineMockMvc.perform(put("/api/medicines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicineDTO)))
            .andExpect(status().isOk());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
        Medicine testMedicine = medicineList.get(medicineList.size() - 1);
        assertThat(testMedicine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedicine.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicine() throws Exception {
        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();

        // Create the Medicine
        MedicineDTO medicineDTO = medicineMapper.toDto(medicine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineMockMvc.perform(put("/api/medicines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicine() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        int databaseSizeBeforeDelete = medicineRepository.findAll().size();

        // Get the medicine
        restMedicineMockMvc.perform(delete("/api/medicines/{id}", medicine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medicine.class);
        Medicine medicine1 = new Medicine();
        medicine1.setId(1L);
        Medicine medicine2 = new Medicine();
        medicine2.setId(medicine1.getId());
        assertThat(medicine1).isEqualTo(medicine2);
        medicine2.setId(2L);
        assertThat(medicine1).isNotEqualTo(medicine2);
        medicine1.setId(null);
        assertThat(medicine1).isNotEqualTo(medicine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicineDTO.class);
        MedicineDTO medicineDTO1 = new MedicineDTO();
        medicineDTO1.setId(1L);
        MedicineDTO medicineDTO2 = new MedicineDTO();
        assertThat(medicineDTO1).isNotEqualTo(medicineDTO2);
        medicineDTO2.setId(medicineDTO1.getId());
        assertThat(medicineDTO1).isEqualTo(medicineDTO2);
        medicineDTO2.setId(2L);
        assertThat(medicineDTO1).isNotEqualTo(medicineDTO2);
        medicineDTO1.setId(null);
        assertThat(medicineDTO1).isNotEqualTo(medicineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medicineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medicineMapper.fromId(null)).isNull();
    }
}
