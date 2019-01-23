package sbnz.soft.nikola.config;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import sbnz.soft.nikola.security.SecurityUtils;
import sbnz.soft.nikola.service.util.KieSessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final KieSessionUtil kieSessionUtil;

    public AjaxAuthenticationSuccessHandler(KieSessionUtil kieSessionUtil) {
        this.kieSessionUtil = kieSessionUtil;
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.debug("LOGGED IN: {}", SecurityUtils.getCurrentUserLogin().isPresent());
        this.kieSessionUtil.createKieSession(SecurityUtils.getCurrentUserLogin().get());
        response.setStatus(200);
    }
}
