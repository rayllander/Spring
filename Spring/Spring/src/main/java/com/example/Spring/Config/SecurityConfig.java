package com.example.Spring.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/api/produtos/**").permitAll()    // Permitir acesso público
                .requestMatchers("/api/usuarios/**").authenticated() // Requer autenticação
                .requestMatchers("/error").permitAll()              // Permitir acesso à rota de erro
                .requestMatchers("/favicon.ico").permitAll() 
                .requestMatchers("/**").permitAll()
                )
            .exceptionHandling(exception -> exception
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    System.out.println("Acesso negado para " + request.getRequestURI());
                    response.sendError(403, "Acesso negado");
                })
            )
            .httpBasic(withDefaults());  // Habilitar autenticação básica

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("rayllander")
            .password("{noop}123")  // {noop} indica que a senha não está codificada
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}
