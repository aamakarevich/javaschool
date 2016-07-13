package com.tsystems.ecare.config.root;

import com.allanditzel.springframework.security.web.csrf.CsrfTokenResponseHeaderBindingFilter;
import com.tsystems.ecare.app.security.AjaxAuthenticationFailureHandler;
import com.tsystems.ecare.app.security.AjaxAuthenticationSuccessHandler;
import com.tsystems.ecare.app.security.AjaxLogoutSuccessHandler;
import com.tsystems.ecare.app.security.SecurityUserDetailsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;

import javax.sql.DataSource;

/**
 * The Spring Security configuration for the application.
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = Logger.getLogger(AppSecurityConfig.class);

    public static final String USER = "hasRole('ROLE_USER')";
    public static final String MANAGER_OR_ADMIN = "hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')";
    private static final String ADMIN = "hasRole('ROLE_ADMIN')";

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new ShaPasswordEncoder(256));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CsrfTokenResponseHeaderBindingFilter csrfTokenFilter = new CsrfTokenResponseHeaderBindingFilter();
        http.addFilterAfter(csrfTokenFilter, CsrfFilter.class);

        http
                .authorizeRequests()
                .antMatchers("/resources/index.jsp").permitAll()
                .antMatchers("/resources/public/**").permitAll()
                .antMatchers("/resources/img/**").permitAll()
                .antMatchers("/resources/js/**").permitAll()
                .antMatchers("/resources/css/**").permitAll()
                .antMatchers("/resources/fonts/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/profile").access(USER)
                .antMatchers(HttpMethod.PUT, "/profile/lock").access(USER)
                .antMatchers(HttpMethod.GET, "/customer").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.GET, "/customer/**").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.DELETE, "/customer/**").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.POST, "/customer").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.PUT, "/customer").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.PUT, "/customer/lock").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.PUT, "/role/activate").access(ADMIN)
                .antMatchers(HttpMethod.GET, "/plan").permitAll()
                .antMatchers(HttpMethod.GET, "/plan/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/plan/**").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.POST, "/plan").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.PUT, "/plan").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.PUT, "/allow").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.PUT, "/link").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.GET, "/option").permitAll()
                .antMatchers(HttpMethod.GET, "/option/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/option/**").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.POST, "/option").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.PUT, "/option").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.GET, "/contract").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.GET, "/contract/**").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.DELETE, "/contract/**").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.POST, "/contract").access(MANAGER_OR_ADMIN)
                .antMatchers(HttpMethod.PUT, "/contract").access(MANAGER_OR_ADMIN)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/resources/index.jsp")
                .loginProcessingUrl("/authenticate")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new AjaxAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler()))
                .failureHandler(new AjaxAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler()))
                .and()
                .httpBasic()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/resources/index.jsp")
                .logoutSuccessHandler(new AjaxLogoutSuccessHandler(new SimpleUrlLogoutSuccessHandler()))
                .invalidateHttpSession(true)
                .deleteCookies("ecare.username", "ecare.firstname", "ecare.lastname",
                        "ecare.admin", "ecare.manager", "ecare.suser")
                .permitAll();

        if ("true".equals(System.getProperty("httpsOnly"))) {
            LOGGER.info("launching the application in HTTPS-only mode");
            http.requiresChannel().anyRequest().requiresSecure();
        }
    }
}
