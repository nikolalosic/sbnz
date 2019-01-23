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

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SbnzApp.class)
public class OxygenTests {

	@Test
    public void successfulOxygen() {
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
    public void unsuccessfulOxygen() {
		KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("sbnz.soft.nikola", "drools_rules", "1.0.0-SNAPSHOT"));
        
        KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
        kbconf.setOption(EventProcessingOption.STREAM);
        KieBase kbase = kContainer.newKieBase(kbconf);
        
        KieSessionConfiguration ksconf2 = ks.newKieSessionConfiguration();
        ksconf2.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
        KieSession session1 = kbase.newKieSession(ksconf2, null);
        runPseudoClockExampleUnsuceessfulFiring(session1);
    }

	
	private void runPseudoClockExampleSuccessfulFiring(KieSession ksession) {
		
		SessionPseudoClock clock = ksession.getSessionClock();
		ksession.getAgenda().getAgendaGroup("oxygen-problems").setFocus();

		// NOTE: nije zabelezen rast nivoa kiseonika u poslednjih 15 min(20 min advance)
		// oxygen level is 5
		ksession.insert(new OxygenEvent(new Date(clock.getCurrentTime()), 5));
		
		clock.advanceTime(10, TimeUnit.MINUTES);

		// oxygen level is 2
		ksession.insert(new OxygenEvent(new Date(clock.getCurrentTime()), 2));
		
		clock.advanceTime(10, TimeUnit.MINUTES);
		
		assertThat(ksession.fireAllRules(), equalTo(1));
		
		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(OxygenProblemEvent.class));
		assertThat(newEvents.size(), equalTo(1));
		ksession.dispose();
	}

	
	private void runPseudoClockExampleUnsuceessfulFiring(KieSession ksession) {
		SessionPseudoClock clock = ksession.getSessionClock();
		ksession.getAgenda().getAgendaGroup("oxygen-problems").setFocus();
		
		ksession.insert(new OxygenEvent(new Date(clock.getCurrentTime()), 500));
		
		clock.advanceTime(10, TimeUnit.MINUTES);
		
		ksession.insert(new OxygenEvent(new Date(clock.getCurrentTime()), 510));
		
		assertThat(ksession.fireAllRules(), equalTo(0));
		
		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(OxygenProblemEvent.class));
		assertThat(newEvents.size(), equalTo(0));
		ksession.dispose();
		
	}
	
}
