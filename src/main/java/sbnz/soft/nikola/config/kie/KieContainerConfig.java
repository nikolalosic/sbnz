package sbnz.soft.nikola.config.kie;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class KieContainerConfig {

    public static KieSessionConfiguration sessionConfiguration;
    public static KieBase kieBase;

    @Bean
    public KieContainer kieContainer() {
        KieServices ks = KieServices.Factory.get();

        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("sbnz.soft.nikola", "drools_rules", "1.0.0-SNAPSHOT"));
        //   KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
        //   kbconf.setOption(EventProcessingOption.STREAM);

        //     KieBase kbase = kContainer.newKieBase(kbconf);
        //     KieSessionConfiguration ksconf2 = ks.newKieSessionConfiguration();
        // ksconf2.setOption(KieSessionOption);
        //   ksconf2.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
        //   sessionConfiguration = ksconf2;
        // kieBase = kbase;
        //KieSession session1 = kbase.newKieSession(ksconf2, null);
        KieScanner kScanner = ks.newKieScanner(kContainer);
        kScanner.start(10000L);

        return kContainer;
    }


    @Bean
    public HashMap<String, KieSession> kieSessions() {
        return new HashMap<>();
    }
}
