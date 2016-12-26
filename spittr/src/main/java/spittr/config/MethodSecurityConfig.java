package spittr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import spittr.service.SpitterAuthorityService;

/**
 * Created by 273cn on 16/12/24.
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    @Autowired
    private SpitterAuthorityService spitterAuthorityService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(spitterAuthorityService);
    }

//    @Override
//    protected MethodSecurityExpressionHandler createExpressionHandler() {
//        return new OAuth2MethodSecurityExpressionHandler();
//    }
}
