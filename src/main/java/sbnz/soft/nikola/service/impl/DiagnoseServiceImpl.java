package sbnz.soft.nikola.service.impl;

import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.soft.nikola.domain.*;
import sbnz.soft.nikola.repository.*;
import sbnz.soft.nikola.security.SecurityUtils;
import sbnz.soft.nikola.service.DiagnoseService;
import sbnz.soft.nikola.service.dto.*;
import sbnz.soft.nikola.service.mapper.DiagnoseMapper;
import sbnz.soft.nikola.service.mapper.DiseaseMapper;
import sbnz.soft.nikola.service.mapper.PatientMapper;
import sbnz.soft.nikola.service.util.KieSessionUtil;
import sbnz.soft.nikola.service.util.SortingUtil;
import sbnz.soft.nikola.web.rest.errors.BadRequestAlertException;

import java.util.*;

/**
 * Service Implementation for managing Diagnose.
 */
@Service
@Transactional
public class DiagnoseServiceImpl implements DiagnoseService {

    private final Logger log = LoggerFactory.getLogger(DiagnoseServiceImpl.class);

    private final DiagnoseRepository diagnoseRepository;

    private final DiagnoseMapper diagnoseMapper;

    private final DiseaseRepository diseaseRepository;

    private final KieSessionUtil kieSessionUtil;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    private final MedicineRepository medicineRepository;
    private final SymptomRepository symptomRepository;
    private final DiseaseMapper diseaseMapper;
    private final PatientMapper patientMapper;

    public DiagnoseServiceImpl(DiagnoseRepository diagnoseRepository, DiagnoseMapper diagnoseMapper,
                               DiseaseRepository diseaseRepository,
                               KieSessionUtil kieSessionUtil,
                               PatientRepository patientRepository,
                               UserRepository userRepository,
                               DiseaseMapper diseaseMapper,
                               MedicineRepository medicineRepository,
                               SymptomRepository symptomRepository,
                               PatientMapper patientMapper) {
        this.diagnoseRepository = diagnoseRepository;
        this.diagnoseMapper = diagnoseMapper;
        this.diseaseRepository = diseaseRepository;
        this.kieSessionUtil = kieSessionUtil;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.diseaseMapper = diseaseMapper;
        this.medicineRepository = medicineRepository;
        this.symptomRepository = symptomRepository;
        this.patientMapper = patientMapper;
    }

    /**
     * Save a diagnose.
     *
     * @param diagnoseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DiagnoseDTO save(DiagnoseDTO diagnoseDTO) {
        log.debug("Request to save Diagnose : {}", diagnoseDTO);
        //  Diagnose diagnose = diagnoseMapper.toEntity(diagnoseDTO);

        Diagnose d = null;

        if (diagnoseDTO.getId() != null) {
            d = diagnoseRepository.findById(diagnoseDTO.getId()).get();
        } else {
            d = new Diagnose();
        }
        Optional<User> doctor = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
        // diagnose.setDoctor(doctor.get());


        Optional<Patient> user = this.patientRepository.findById(diagnoseDTO.getPatientId());
        if (!user.isPresent()) {
            throw new BadRequestAlertException("User does not exist!", "User", "User");
        }
        // diagnose.setPatient(user.get());

        d.setDoctor(doctor.get());
        d.setDisease(diseaseRepository.findById(diagnoseDTO.getDiseaseId()).orElse(null));
        d.setPatient(user.get());
        d.setMedicines(new HashSet<>());
        d.setSymptoms(new HashSet<>());
        d.setDiagnoseDate(diagnoseDTO.getDiagnoseDate());
        for (MedicineDTO m : diagnoseDTO.getMedicines()) {
            d.getMedicines().add(medicineRepository.findById(m.getId()).orElse(null));
        }
        for (SymptomDTO s : diagnoseDTO.getSymptoms()) {
            d.getSymptoms().add(symptomRepository.findById(s.getId()).orElse(null));
        }

        List<Medicine> allergicOn = new ArrayList<>();
        List<Medicine> allergicOnIngredients = new ArrayList<>();

        KieSession session = this.kieSessionUtil.getUserSession(SecurityUtils.getCurrentUserLogin().get());
        session.setGlobal("allergicOn", allergicOn);
        session.setGlobal("allergicOnIngredients", allergicOnIngredients);
        session.insert(d);
        session.insert(user);

        session.getAgenda().getAgendaGroup("allergies").setFocus();

        session.fireAllRules();

        //session.fireUntilHalt();
        System.out.println(allergicOn.size());
        System.out.println(allergicOnIngredients.size());

        session.delete(session.getFactHandle(d));
        session.delete(session.getFactHandle(user));


        String message = "";

        if (allergicOn.size() != 0) {
            message += "Patient is alergic on ";

            for (Medicine m : allergicOn) {
                message += m.getName() + " ";
            }

            message += "as whole medicines!\n";
        }

        for (Medicine m : allergicOnIngredients) {
            message += "Patient is allergic on " + m.getName() + " because ";
            m.getIngredients().retainAll(user.get().getAllergicIngredients());
            for (Ingredient i : m.getIngredients()) {
                message += i.getName() + " ";
            }
            message += "ingredients\n";
        }

        if (allergicOn.size() != 0 || allergicOnIngredients.size() != 0) {
            throw new BadRequestAlertException(message, "Patient", "patient");
        }

        this.diagnoseRepository.save(d);
        user.get().getDiagnoses().add(d);
        this.patientRepository.save(user.get());


        d = diagnoseRepository.save(d);
        return diagnoseMapper.toDto(d);
    }

    /**
     * Get all the diagnoses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiagnoseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Diagnoses");
        return diagnoseRepository.findAll(pageable)
            .map(diagnoseMapper::toDto);
    }

    /**
     * Get all the Diagnose with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<DiagnoseDTO> findAllWithEagerRelationships(Pageable pageable) {
        return diagnoseRepository.findAllWithEagerRelationships(pageable).map(diagnoseMapper::toDto);
    }


    /**
     * Get one diagnose by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiagnoseDTO> findOne(Long id) {
        log.debug("Request to get Diagnose : {}", id);
        //Optional<Diagnose> diagnose = diagnoseRepository.findOneWithEagerRelationships(id);
        return diagnoseRepository.findOneWithEagerRelationships(id)
            .map(diagnoseMapper::toDto);
    }

    /**
     * Delete the diagnose by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Diagnose : {}", id);
        diagnoseRepository.deleteById(id);
    }

    public DiseaseDTO checkForDisease(Set<Symptom> symptoms, String email, Patient patient) {

        Disease disease = new Disease();

        KieSession session = this.kieSessionUtil.getUserSession(email);
        for (Symptom sys : symptoms) {
            session.insert(sys);
        }
        for (Diagnose dig : patient.getDiagnoses()) {
            session.insert(dig);
        }
        System.out.println(disease.getName() == null);
        session.insert(disease);
        session.getAgenda().getAgendaGroup("diseaseAnalysis").setFocus();
        session.fireAllRules();


        for (Symptom sys : symptoms) {
            session.delete(session.getFactHandle(sys));
        }
        for (Diagnose dig : patient.getDiagnoses()) {
            session.delete(session.getFactHandle(dig));
        }
        session.delete(session.getFactHandle(disease));

        if (disease.getName() != null) {
            disease = this.diseaseRepository.findByName(disease.getName());
        }

        return diseaseMapper.toDto(disease);
    }

    public void addDiagnoseAndCheckAlergies(Diagnose diagnose, Long patientId, String doctor) {


    }

    public List<PatientDTO> chronicsReport(String doctor) {
        List<Patient> allPatients = patientRepository.findAll();
        List<Disease> allDiseases = diseaseRepository.findAll();
        KieSession session = this.kieSessionUtil.getUserSession(doctor);
        for (Patient pat : allPatients) {
            session.insert(pat);
        }
        for (Disease dis : allDiseases) {
            session.insert(dis);
        }

        QueryResults results = session.getQueryResults("report chronics");
        Set<Patient> patients = new HashSet<>();
        for (QueryResultsRow qrr : results) {
            patients.add((Patient) qrr.get("patient"));
        }


        for (Patient pat : allPatients) {
            session.delete(session.getFactHandle(pat));
        }
        for (Disease dis : allDiseases) {
            session.delete(session.getFactHandle(dis));
        }

        return patientMapper.toDto(new ArrayList<>(patients));
    }

    public List<PatientDTO> addictsReport(String doctor) {
        List<Patient> allPatients = patientRepository.findAll();
        List<Disease> allDiseases = diseaseRepository.findAll();
        KieSession session = this.kieSessionUtil.getUserSession(doctor);
        for (Patient pat : allPatients) {
            session.insert(pat);
        }
        for (Disease dis : allDiseases) {
            session.insert(dis);
        }
        QueryResults results = session.getQueryResults("report addicts");
        Set<Patient> patients = new HashSet<>();
        for (QueryResultsRow qrr : results) {
            patients.add((Patient) qrr.get("patient"));
        }
        for (Patient pat : allPatients) {
            session.delete(session.getFactHandle(pat));
        }
        for (Disease dis : allDiseases) {
            session.delete(session.getFactHandle(dis));
        }
        return patientMapper.toDto(new ArrayList<>(patients));
    }

    public List<PatientDTO> immunityReport(String doctor) {
        List<Patient> allPatients = patientRepository.findAll();
        List<Disease> allDiseases = diseaseRepository.findAll();
        KieSession session = this.kieSessionUtil.getUserSession(doctor);
        for (Patient pat : allPatients) {
            session.insert(pat);
        }
        for (Disease dis : allDiseases) {
            session.insert(dis);
        }

        QueryResults results = session.getQueryResults("report immunity");

        Set<Patient> patients = new HashSet<>();
        for (QueryResultsRow qrr : results) {
            patients.add((Patient) qrr.get("patient"));
        }
        for (Patient pat : allPatients) {
            session.delete(session.getFactHandle(pat));
        }
        for (Disease dis : allDiseases) {
            session.delete(session.getFactHandle(dis));
        }
        return patientMapper.toDto(new ArrayList<>(patients));
    }

    public List<DiseaseDTO> filterBySymptoms(Set<Symptom> symptoms, String doctor) {
        List<Disease> allDiseases = diseaseRepository.findAll();

        KieSession session = this.kieSessionUtil.getUserSession(doctor);
        for (Disease dis : allDiseases) {
            session.insert(dis);
        }

        List<Symptom> symptomsEntered = new ArrayList<>();
        symptomsEntered.addAll(symptoms);
        QueryResults results = session.getQueryResults("filter diseases", new Object[]{symptomsEntered});

        Map<Disease, Integer> filter = new HashMap<Disease, Integer>();
        for (QueryResultsRow qrr : results) {
            Disease disease = (Disease) qrr.get("disease");
            Integer numberOfBasics = (Integer) qrr.get("numOfGeneralSymps");
            Integer numberOfSpecific = (Integer) qrr.get("numOfSpecificSymps");
            System.out.println(disease.getName());
            System.out.println(numberOfBasics);
            System.out.println(numberOfSpecific);

            filter.put(disease, numberOfBasics + numberOfSpecific);
        }

        Map<Disease, Integer> sorted = SortingUtil.sortByValue(filter);

        List<Disease> diseases = new ArrayList<>();
        for (Disease d : sorted.keySet()) {
            diseases.add(d);
        }

        for (Disease dis : allDiseases) {
            session.delete(session.getFactHandle(dis));
        }

        return diseaseMapper.toDto(diseases);
    }

}
