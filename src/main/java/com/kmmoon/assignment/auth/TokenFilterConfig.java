package com.kmmoon.assignment.auth;

import com.kmmoon.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class TokenFilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final UserRepository userRepository;

    @Override
    public void configure(HttpSecurity http){
        http.addFilterBefore(new TokenFilter(userRepository), UsernamePasswordAuthenticationFilter.class);
    }

}
