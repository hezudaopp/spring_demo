package spittr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import spittr.exceptions.ErrorResponse;
import spittr.global.Constants;
import spittr.service.SpitterAuthorityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 273cn on 16/12/21.
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SpitterAuthorityService spitterAuthorityService;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(spitterAuthorityService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('read')")
                .regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
                .access("#oauth2.clientHasRole('CLIENT') and (hasRole('USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
                .regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
                .access("#oauth2.clientHasRole('CLIENT') and (hasRole('USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
                .regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
                .access("#oauth2.clientHasRole('CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')")
                .anyRequest().permitAll();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    @Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        return handler;
    }

    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }


    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public CustomBasicAuthenticationEntryPoint customBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }

    private class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

        @Override
        public void commence(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final AuthenticationException authException) throws IOException, ServletException {
            //Authentication failed, send error response.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");

            PrintWriter writer = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            writer.println(mapper.writeValueAsString(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), authException.getMessage())));
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            setRealmName(Constants.REALM);
            super.afterPropertiesSet();
        }
    }
}
