package com.example.lockerAuth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.lockerAuth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    @Autowired
    private UserRepository repo;

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration conf) throws Exception {
        return conf.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider dp = new DaoAuthenticationProvider();
        dp.setUserDetailsService(userDetailService());
        dp.setPasswordEncoder(passEncoder());
        return dp;
    }
    @Bean
    public PasswordEncoder passEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailService() {
        return user -> repo.findByU_nombre(user)
        .orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado"));
    }
}
