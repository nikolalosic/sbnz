package sbnz.soft.nikola.domain.events;

import org.drools.core.ClassObjectFilter;
import org.drools.core.ClockType;
import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sbnz.soft.nikola.SbnzApp;
import sbnz.soft.nikola.domain.Diagnose;
import sbnz.soft.nikola.domain.Disease;
import sbnz.soft.nikola.domain.Patient;


import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbnzApp.class)
public class DialysisTests {

    @Test
    public void successfulDialysis() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("sbnz.soft.nikola", "drools_rules", "1.0.0-SNAPSHOT"));

        KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
        kbconf.setOption(EventProcessingOption.STREAM);
        KieBase kbase = kContainer.newKieBase(kbconf);

        KieSessionConfiguration ksconf2 = ks.newKieSessionConfiguration();
        ksconf2.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
        KieSession session1 = kbase.newKieSession(ksconf2, null);
        runPseudoClockExampleSuccessfulFiring(session1);
    }

    @Test
    public void unsuccessfulDialysis() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("sbnz.soft.nikola", "drools_rules", "1.0.0-SNAPSHOT"));

        KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
        kbconf.setOption(EventProcessingOption.STREAM);
        KieBase kbase = kContainer.newKieBase(kbconf);

        KieSessionConfiguration ksconf2 = ks.newKieSessionConfiguration();
        ksconf2.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
        KieSession session1 = kbase.newKieSession(ksconf2, null);
        KieSession session2 = kbase.newKieSession(ksconf2, null);
        KieSession session3 = kbase.newKieSession(ksconf2, null);
        runPseudoClockExampleUnsuceessfulFiringNoUser(session1);
        runPseudoClockExampleUnsuccessfulFiringNo10HeartBeats(session2);
        runPseudoClockExampleUnsuccessfulFiringOver100ml(session3);
    }

    private void runPseudoClockExampleUnsuceessfulFiringNoUser(KieSession ksession) {

        SessionPseudoClock clock = ksession.getSessionClock();
        ksession.getAgenda().getAgendaGroup("emergency-dialysis").setFocus();

        ksession.insert(new DialysisEvent(new Date(clock.getCurrentTime()), 500));

        clock.advanceTime(6, TimeUnit.HOURS);

        assertThat(ksession.fireAllRules(), equalTo(0));

        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(UrgentDialysisEvent.class));
        assertThat(newEvents.size(), equalTo(0));
        ksession.dispose();

    }

    private void runPseudoClockExampleUnsuccessfulFiringNo10HeartBeats(KieSession ksession) {

        SessionPseudoClock clock = ksession.getSessionClock();
        ksession.getAgenda().getAgendaGroup("emergency-dialysis").setFocus();

        Patient u = new Patient();
        Disease d = new Disease();
        d.setName("Hronicna bubrezna bolest");
        Diagnose diagnose = new Diagnose();
        diagnose.setDisease(d);
        u.getDiagnoses().add(diagnose);
        System.out.println(u.getDiagnoses().size());
        ksession.insert(u);

        clock.advanceTime(6, TimeUnit.HOURS);

        assertThat(ksession.fireAllRules(), equalTo(0));

        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(UrgentDialysisEvent.class));
        assertThat(newEvents.size(), equalTo(0));
        ksession.dispose();

    }

    private void runPseudoClockExampleUnsuccessfulFiringOver100ml(KieSession ksession) {

        SessionPseudoClock clock = ksession.getSessionClock();
        ksession.getAgenda().getAgendaGroup("emergency-dialysis").setFocus();

        Patient u = new Patient();
        Disease d = new Disease();
        d.setName("Hronicna bubrezna bolest");
        Diagnose diagnose = new Diagnose();
        diagnose.setDisease(d);
        u.getDiagnoses().add(diagnose);
        ksession.insert(u);

        clock.advanceTime(10, TimeUnit.MINUTES);

        for (int index = 0; index < 35; index++) {
            HeartBeatEvent hbe = new HeartBeatEvent(new Date(clock.getCurrentTime()));
            ksession.insert(hbe);
            clock.advanceTime(55, TimeUnit.MILLISECONDS);
        }

        ksession.insert(new DialysisEvent(new Date(clock.getCurrentTime()), 100));

        assertThat(ksession.fireAllRules(), equalTo(0));

        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(UrgentDialysisEvent.class));
        assertThat(newEvents.size(), equalTo(0));
        ksession.dispose();

    }

    private void runPseudoClockExampleSuccessfulFiring(KieSession ksession) {

        SessionPseudoClock clock = ksession.getSessionClock();
        ksession.getAgenda().getAgendaGroup("emergency-dialysis").setFocus();

        Patient u = new Patient();
        Disease d = new Disease();
        d.setName("Hronicna bubrezna bolest");
        Diagnose diagnose = new Diagnose();
        diagnose.setDisease(d);
        u.getDiagnoses().add(diagnose);
        ksession.insert(u);

        clock.advanceTime(10, TimeUnit.MINUTES);

        for (int index = 0; index < 35; index++) {
            HeartBeatEvent hbe = new HeartBeatEvent(new Date(clock.getCurrentTime()));
            ksession.insert(hbe);
            clock.advanceTime(55, TimeUnit.MILLISECONDS);
        }

        ksession.insert(new DialysisEvent(new Date(clock.getCurrentTime()), 50));

        clock.advanceTime(2, TimeUnit.HOURS);

        for (int index = 0; index < 35; index++) {
            HeartBeatEvent hbe = new HeartBeatEvent(new Date(clock.getCurrentTime()));
            ksession.insert(hbe);
            clock.advanceTime(55, TimeUnit.MILLISECONDS);
        }

        ksession.insert(new DialysisEvent(new Date(clock.getCurrentTime()), 49));

        assertThat(ksession.fireAllRules(), equalTo(1));

        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(UrgentDialysisEvent.class));
        assertThat(newEvents.size(), equalTo(1));
        ksession.dispose();

    }
}
