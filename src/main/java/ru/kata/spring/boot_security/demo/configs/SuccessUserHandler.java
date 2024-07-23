package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = Logger.getLogger(SuccessUserHandler.class.getName());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        logger.info("Roles: " + roles);

        if (roles.contains("ROLE_USER")) {
            logger.info("Redirecting to /user");
            httpServletResponse.sendRedirect("/user");
        } else if (roles.contains("ROLE_ADMIN")) {
            logger.info("Redirecting to /admin");
            httpServletResponse.sendRedirect("/admin");
        } else {
            logger.warning("No matching role found, redirecting to /login");
            httpServletResponse.sendRedirect("/login");
        }
    }
}
