package sbnz.soft.nikola.service.util;

import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sbnz.soft.nikola.config.kie.KieContainerConfig;
import sbnz.soft.nikola.domain.Disease;
import sbnz.soft.nikola.domain.User;
import sbnz.soft.nikola.repository.DiseaseRepository;
import sbnz.soft.nikola.repository.UserRepository;
import sbnz.soft.nikola.security.SecurityUtils;


import java.util.HashMap;
import java.util.Optional;

@Service
public class KieSessionUtil {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    public KieContainer kieContainer;

    @Autowired
    private HashMap<String, KieSession> kieSessions;

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Autowired
    private UserRepository userRepository;

    public KieSession getCurrentUsersSession() {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (!login.isPresent())
            return null;

        KieSession kieSession = kieSessions.get(login.get());
        return kieSession;
    }

    public KieSession getUserSession(String login) {
        KieSession kieSession = kieSessions.get(login);
        return kieSession;
    }


    public void createKieSession(String username) {
        KieServices kieServices = KieServices.Factory.get();
        //     KieSession kieSession = kieContainer.newKieSession("ksession-rules");
        KieSession kieSession = kieContainer.newKieSession("ksession-rules");

     //   KieSession kieSession = KieContainerConfig.kieBase.newKieSession(KieContainerConfig.sessionConfiguration, null);
        // FIXME: SHOULD REMOVE THIS to allow change while running
        for (Disease d : this.diseaseRepository.findAll()) {
            kieSession.insert(d);
        }
        // FIXME: not really needed
        for (User u : this.userRepository.findAll()) {
            kieSession.insert(u);
        }
        KieRuntimeLogger kieLogger = kieServices.getLoggers().newFileLogger(kieSession, "audit");
        this.kieSessions.put(username, kieSession);
    }

    public void deleteKieSession(String username) {
        KieSession sess = this.kieSessions.get(username);
        if (sess != null) {
            sess.dispose();
            this.kieSessions.remove(username);
        }
    }


}
