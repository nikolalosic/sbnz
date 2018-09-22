package sbnz.soft.nikola.web.rest;

import sbnz.soft.nikola.SbnzApp;

import sbnz.soft.nikola.domain.Diagnose;
import sbnz.soft.nikola.repository.DiagnoseRepository;
import sbnz.soft.nikola.service.DiagnoseService;
import sbnz.soft.nikola.service.dto.DiagnoseDTO;
import sbnz.soft.nikola.service.mapper.DiagnoseMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import static sbnz.soft.nikola.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DiagnoseResource REST controller.
 *
 * @see DiagnoseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbnzApp.class)
public class DiagnoseResourceIntTest {

    private static final Instant DEFAULT_DIAGNOSE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DIAGNOSE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DiagnoseRepository diagnoseRepository;

    @Mock
    private DiagnoseRepository diagnoseRepositoryMock;

    @Autowired
    private DiagnoseMapper diagnoseMapper;
    

    @Mock
    private DiagnoseService diagnoseServiceMock;

    @Autowired
    private DiagnoseService diagnoseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDiagnoseMockMvc;

    private Diagnose diagnose;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiagnoseResource diagnoseResource = new DiagnoseResource(diagnoseService);
        this.restDiagnoseMockMvc = MockMvcBuilders.standaloneSetup(diagnoseResource)
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
    public static Diagnose createEntity(EntityManager em) {
        Diagnose diagnose = new Diagnose()
            .diagnoseDate(DEFAULT_DIAGNOSE_DATE);
        return diagnose;
    }

    @Before
    public void initTest() {
        diagnose = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiagnose() throws Exception {
        int databaseSizeBeforeCreate = diagnoseRepository.findAll().size();

        // Create the Diagnose
        DiagnoseDTO diagnoseDTO = diagnoseMapper.toDto(diagnose);
        restDiagnoseMockMvc.perform(post("/api/diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diagnoseDTO)))
            .andExpect(status().isCreated());

        // Validate the Diagnose in the database
        List<Diagnose> diagnoseList = diagnoseRepository.findAll();
        assertThat(diagnoseList).hasSize(databaseSizeBeforeCreate + 1);
        Diagnose testDiagnose = diagnoseList.get(diagnoseList.size() - 1);
        assertThat(testDiagnose.getDiagnoseDate()).isEqualTo(DEFAULT_DIAGNOSE_DATE);
    }

    @Test
    @Transactional
    public void createDiagnoseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diagnoseRepository.findAll().size();

        // Create the Diagnose with an existing ID
        diagnose.setId(1L);
        DiagnoseDTO diagnoseDTO = diagnoseMapper.toDto(diagnose);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiagnoseMockMvc.perform(post("/api/diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diagnoseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diagnose in the database
        List<Diagnose> diagnoseList = diagnoseRepository.findAll();
        assertThat(diagnoseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDiagnoseDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = diagnoseRepository.findAll().size();
        // set the field null
        diagnose.setDiagnoseDate(null);

        // Create the Diagnose, which fails.
        DiagnoseDTO diagnoseDTO = diagnoseMapper.toDto(diagnose);

        restDiagnoseMockMvc.perform(post("/api/diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diagnoseDTO)))
            .andExpect(status().isBadRequest());

        List<Diagnose> diagnoseList = diagnoseRepository.findAll();
        assertThat(diagnoseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiagnoses() throws Exception {
        // Initialize the database
        diagnoseRepository.saveAndFlush(diagnose);

        // Get all the diagnoseList
        restDiagnoseMockMvc.perform(get("/api/diagnoses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diagnose.getId().intValue())))
            .andExpect(jsonPath("$.[*].diagnoseDate").value(hasItem(DEFAULT_DIAGNOSE_DATE.toString())));
    }
    
    public void getAllDiagnosesWithEagerRelationshipsIsEnabled() throws Exception {
        DiagnoseResource diagnoseResource = new DiagnoseResource(diagnoseServiceMock);
        when(diagnoseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restDiagnoseMockMvc = MockMvcBuilders.standaloneSetup(diagnoseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDiagnoseMockMvc.perform(get("/api/diagnoses?eagerload=true"))
        .andExpect(status().isOk());

        verify(diagnoseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllDiagnosesWithEagerRelationshipsIsNotEnabled() throws Exception {
        DiagnoseResource diagnoseResource = new DiagnoseResource(diagnoseServiceMock);
            when(diagnoseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restDiagnoseMockMvc = MockMvcBuilders.standaloneSetup(diagnoseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restDiagnoseMockMvc.perform(get("/api/diagnoses?eagerload=true"))
        .andExpect(status().isOk());

            verify(diagnoseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDiagnose() throws Exception {
        // Initialize the database
        diagnoseRepository.saveAndFlush(diagnose);

        // Get the diagnose
        restDiagnoseMockMvc.perform(get("/api/diagnoses/{id}", diagnose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diagnose.getId().intValue()))
            .andExpect(jsonPath("$.diagnoseDate").value(DEFAULT_DIAGNOSE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiagnose() throws Exception {
        // Get the diagnose
        restDiagnoseMockMvc.perform(get("/api/diagnoses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiagnose() throws Exception {
        // Initialize the database
        diagnoseRepository.saveAndFlush(diagnose);

        int databaseSizeBeforeUpdate = diagnoseRepository.findAll().size();

        // Update the diagnose
        Diagnose updatedDiagnose = diagnoseRepository.findById(diagnose.getId()).get();
        // Disconnect from session so that the updates on updatedDiagnose are not directly saved in db
        em.detach(updatedDiagnose);
        updatedDiagnose
            .diagnoseDate(UPDATED_DIAGNOSE_DATE);
        DiagnoseDTO diagnoseDTO = diagnoseMapper.toDto(updatedDiagnose);

        restDiagnoseMockMvc.perform(put("/api/diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diagnoseDTO)))
            .andExpect(status().isOk());

        // Validate the Diagnose in the database
        List<Diagnose> diagnoseList = diagnoseRepository.findAll();
        assertThat(diagnoseList).hasSize(databaseSizeBeforeUpdate);
        Diagnose testDiagnose = diagnoseList.get(diagnoseList.size() - 1);
        assertThat(testDiagnose.getDiagnoseDate()).isEqualTo(UPDATED_DIAGNOSE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDiagnose() throws Exception {
        int databaseSizeBeforeUpdate = diagnoseRepository.findAll().size();

        // Create the Diagnose
        DiagnoseDTO diagnoseDTO = diagnoseMapper.toDto(diagnose);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiagnoseMockMvc.perform(put("/api/diagnoses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diagnoseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Diagnose in the database
        List<Diagnose> diagnoseList = diagnoseRepository.findAll();
        assertThat(diagnoseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiagnose() throws Exception {
        // Initialize the database
        diagnoseRepository.saveAndFlush(diagnose);

        int databaseSizeBeforeDelete = diagnoseRepository.findAll().size();

        // Get the diagnose
        restDiagnoseMockMvc.perform(delete("/api/diagnoses/{id}", diagnose.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Diagnose> diagnoseList = diagnoseRepository.findAll();
        assertThat(diagnoseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Diagnose.class);
        Diagnose diagnose1 = new Diagnose();
        diagnose1.setId(1L);
        Diagnose diagnose2 = new Diagnose();
        diagnose2.setId(diagnose1.getId());
        assertThat(diagnose1).isEqualTo(diagnose2);
        diagnose2.setId(2L);
        assertThat(diagnose1).isNotEqualTo(diagnose2);
        diagnose1.setId(null);
        assertThat(diagnose1).isNotEqualTo(diagnose2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiagnoseDTO.class);
        DiagnoseDTO diagnoseDTO1 = new DiagnoseDTO();
        diagnoseDTO1.setId(1L);
        DiagnoseDTO diagnoseDTO2 = new DiagnoseDTO();
        assertThat(diagnoseDTO1).isNotEqualTo(diagnoseDTO2);
        diagnoseDTO2.setId(diagnoseDTO1.getId());
        assertThat(diagnoseDTO1).isEqualTo(diagnoseDTO2);
        diagnoseDTO2.setId(2L);
        assertThat(diagnoseDTO1).isNotEqualTo(diagnoseDTO2);
        diagnoseDTO1.setId(null);
        assertThat(diagnoseDTO1).isNotEqualTo(diagnoseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(diagnoseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(diagnoseMapper.fromId(null)).isNull();
    }
}
