package com.kmmoon.assignment.auth;

import com.kmmoon.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Override
    public void configure(WebSecurity web) {

        web.ignoring().antMatchers("/v2/api-docs")
                .antMatchers("/v3/api-docs")
                .antMatchers("/v3/api-docs/swagger-config")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/configuration/**")
                .antMatchers("/webjars/**")
                .antMatchers("/public")
                .antMatchers("/h2-console/**/**")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.GET, "/user/**")
                .antMatchers(HttpMethod.GET, "/post/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/h2-console/**/**").permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**").permitAll()
//                .antMatchers("/user/**").permitAll()
                .anyRequest().authenticated();

        http.apply(new TokenFilterConfig(userRepository));
    }
}
