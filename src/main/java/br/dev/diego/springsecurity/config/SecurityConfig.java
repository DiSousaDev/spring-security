package br.dev.diego.springsecurity.config;

import br.dev.diego.springsecurity.config.handler.CustomAccessDeniedHandler;
import br.dev.diego.springsecurity.config.handler.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

import static br.dev.diego.springsecurity.entities.enums.Role.ADMIN;
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Environment env;
    private final SecurityFilter securityFilter;

    public SecurityConfig(Environment env, SecurityFilter securityFilter) {
        this.env = env;
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {

        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers(HttpMethod.GET, "/home").permitAll();
                            auth.requestMatchers(HttpMethod.GET, "/products").permitAll();
                            auth.requestMatchers(HttpMethod.POST, "/login").permitAll();
                            auth.requestMatchers(HttpMethod.POST, "/products").hasRole(ADMIN.toString());
                            auth.requestMatchers(toH2Console()).permitAll();
                            auth.anyRequest().authenticated();
                        }
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        config -> {
                            config.authenticationEntryPoint(authenticationFailureHandler());
                            config.accessDeniedHandler(accessDeniedHandler());
                        }
                )
                .build();
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}
