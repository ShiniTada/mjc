package com.epam.esm.web.config;

import com.epam.esm.service.jwt.JwtTokenProvider;
import com.epam.esm.web.exception.CustomAccessDeniedHandler;
import com.epam.esm.web.exception.ExceptionHandlingFilter;
import com.epam.esm.web.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String AUTH_LOGIN = "/auth/login";
    private static final String AUTH_REGISTRATION = "/auth/registration";
    private static final String CERTIFICATES = "/certificates/**";


    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandlerBean() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public ExceptionHandlingFilter exceptionHandlerFilterBean() {
        return new ExceptionHandlingFilter();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilterBean() {
        return new JwtTokenFilter(jwtTokenProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, AUTH_LOGIN).anonymous()
                .antMatchers(HttpMethod.POST, AUTH_REGISTRATION).anonymous()
                .antMatchers(HttpMethod.GET, CERTIFICATES).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerBean())
                .and().cors();
    }
}
