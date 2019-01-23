package sbnz.soft.nikola.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import sbnz.soft.nikola.security.SecurityUtils;
import sbnz.soft.nikola.service.util.KieSessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

public class AjaxLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final KieSessionUtil kieSessionUtil;

    public AjaxLogoutSuccessHandler(KieSessionUtil kieSessionUtil) {
        this.kieSessionUtil = kieSessionUtil;
    }

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal();
            kieSessionUtil.deleteKieSession(user.getUsername());
            log.debug("LOGGED OUT: {}", user.getUsername());
        }
        response.setStatus(200);
    }
}
