package com.example.restws.config;

import com.example.restws.security.AuthenticationFilter;
import com.example.restws.security.AuthorizationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager()));
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/auth");

        return authenticationFilter;
    }
}
