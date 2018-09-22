package sbnz.soft.nikola.web.rest;

import sbnz.soft.nikola.SbnzApp;

import sbnz.soft.nikola.domain.Disease;
import sbnz.soft.nikola.repository.DiseaseRepository;
import sbnz.soft.nikola.service.DiseaseService;
import sbnz.soft.nikola.service.dto.DiseaseDTO;
import sbnz.soft.nikola.service.mapper.DiseaseMapper;
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

/**
 * Test class for the DiseaseResource REST controller.
 *
 * @see DiseaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbnzApp.class)
public class DiseaseResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Mock
    private DiseaseRepository diseaseRepositoryMock;

    @Autowired
    private DiseaseMapper diseaseMapper;
    

    @Mock
    private DiseaseService diseaseServiceMock;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDiseaseMockMvc;

    private Disease disease;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiseaseResource diseaseResource = new DiseaseResource(diseaseService);
        this.restDiseaseMockMvc = MockMvcBuilders.standaloneSetup(diseaseResource)
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
    public static Disease createEntity(EntityManager em) {
        Disease disease = new Disease()
            .name(DEFAULT_NAME);
        return disease;
    }

    @Before
    public void initTest() {
        disease = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisease() throws Exception {
        int databaseSizeBeforeCreate = diseaseRepository.findAll().size();

        // Create the Disease
        DiseaseDTO diseaseDTO = diseaseMapper.toDto(disease);
        restDiseaseMockMvc.perform(post("/api/diseases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diseaseDTO)))
            .andExpect(status().isCreated());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeCreate + 1);
        Disease testDisease = diseaseList.get(diseaseList.size() - 1);
        assertThat(testDisease.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDiseaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diseaseRepository.findAll().size();

        // Create the Disease with an existing ID
        disease.setId(1L);
        DiseaseDTO diseaseDTO = diseaseMapper.toDto(disease);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiseaseMockMvc.perform(post("/api/diseases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diseaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = diseaseRepository.findAll().size();
        // set the field null
        disease.setName(null);

        // Create the Disease, which fails.
        DiseaseDTO diseaseDTO = diseaseMapper.toDto(disease);

        restDiseaseMockMvc.perform(post("/api/diseases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diseaseDTO)))
            .andExpect(status().isBadRequest());

        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiseases() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

        // Get all the diseaseList
        restDiseaseMockMvc.perform(get("/api/diseases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disease.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    public void getAllDiseasesWithEagerRelationshipsIsEnabled() throws Exception {
        DiseaseResource diseaseResource = new DiseaseResource(diseaseServiceMock);
        when(diseaseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDiseaseMockMvc = MockMvcBuilders.standaloneSetup(diseaseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDiseaseMockMvc.perform(get("/api/diseases?eagerload=true"))
        .andExpect(status().isOk());

        verify(diseaseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllDiseasesWithEagerRelationshipsIsNotEnabled() throws Exception {
        DiseaseResource diseaseResource = new DiseaseResource(diseaseServiceMock);
            when(diseaseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDiseaseMockMvc = MockMvcBuilders.standaloneSetup(diseaseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDiseaseMockMvc.perform(get("/api/diseases?eagerload=true"))
        .andExpect(status().isOk());

            verify(diseaseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDisease() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

        // Get the disease
        restDiseaseMockMvc.perform(get("/api/diseases/{id}", disease.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(disease.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDisease() throws Exception {
        // Get the disease
        restDiseaseMockMvc.perform(get("/api/diseases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisease() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

        int databaseSizeBeforeUpdate = diseaseRepository.findAll().size();

        // Update the disease
        Disease updatedDisease = diseaseRepository.findById(disease.getId()).get();
        // Disconnect from session so that the updates on updatedDisease are not directly saved in db
        em.detach(updatedDisease);
        updatedDisease
            .name(UPDATED_NAME);
        DiseaseDTO diseaseDTO = diseaseMapper.toDto(updatedDisease);

        restDiseaseMockMvc.perform(put("/api/diseases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diseaseDTO)))
            .andExpect(status().isOk());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeUpdate);
        Disease testDisease = diseaseList.get(diseaseList.size() - 1);
        assertThat(testDisease.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDisease() throws Exception {
        int databaseSizeBeforeUpdate = diseaseRepository.findAll().size();

        // Create the Disease
        DiseaseDTO diseaseDTO = diseaseMapper.toDto(disease);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiseaseMockMvc.perform(put("/api/diseases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diseaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disease in the database
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisease() throws Exception {
        // Initialize the database
        diseaseRepository.saveAndFlush(disease);

        int databaseSizeBeforeDelete = diseaseRepository.findAll().size();

        // Get the disease
        restDiseaseMockMvc.perform(delete("/api/diseases/{id}", disease.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Disease> diseaseList = diseaseRepository.findAll();
        assertThat(diseaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Disease.class);
        Disease disease1 = new Disease();
        disease1.setId(1L);
        Disease disease2 = new Disease();
        disease2.setId(disease1.getId());
        assertThat(disease1).isEqualTo(disease2);
        disease2.setId(2L);
        assertThat(disease1).isNotEqualTo(disease2);
        disease1.setId(null);
        assertThat(disease1).isNotEqualTo(disease2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiseaseDTO.class);
        DiseaseDTO diseaseDTO1 = new DiseaseDTO();
        diseaseDTO1.setId(1L);
        DiseaseDTO diseaseDTO2 = new DiseaseDTO();
        assertThat(diseaseDTO1).isNotEqualTo(diseaseDTO2);
        diseaseDTO2.setId(diseaseDTO1.getId());
        assertThat(diseaseDTO1).isEqualTo(diseaseDTO2);
        diseaseDTO2.setId(2L);
        assertThat(diseaseDTO1).isNotEqualTo(diseaseDTO2);
        diseaseDTO1.setId(null);
        assertThat(diseaseDTO1).isNotEqualTo(diseaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(diseaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(diseaseMapper.fromId(null)).isNull();
    }
}
