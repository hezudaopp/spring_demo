package spittr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import spittr.global.Constants;

/**
 * Created by 273cn on 16/12/24.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(Constants.RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/spitters").hasAuthority("ROLE_ADMIN") // role name should start with ROLE_
                .antMatchers("/spitters/**").authenticated()
                .antMatchers(HttpMethod.POST, "/spittles/**").authenticated()
                .anyRequest().permitAll();
        http.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
