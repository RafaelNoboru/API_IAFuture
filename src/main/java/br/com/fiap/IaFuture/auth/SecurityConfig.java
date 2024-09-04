package br.com.fiap.IaFuture.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain config(HttpSecurity http, AuthorizationFilter authorizationFilter) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "contas").permitAll()
                .requestMatchers(HttpMethod.GET, "contas/{username}").permitAll()
                .requestMatchers(HttpMethod.PUT, "contas").authenticated()
                .requestMatchers(HttpMethod.POST, "contas").permitAll()
                .requestMatchers(HttpMethod.POST, "login").permitAll()
                .requestMatchers(HttpMethod.PUT, "login").authenticated()
                .anyRequest().authenticated()
    );
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

