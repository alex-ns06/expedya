package br.pucpr.expedya.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Desativa CSRF para APIs REST
                .csrf(csrf -> csrf.disable())

                // Necessário para permitir o H2 usar frames HTML
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // Política stateless (JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // H2 Console liberado
                        .requestMatchers("/h2-console/**").permitAll()

                        // Swagger liberado
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // Endpoints públicos
                        .requestMatchers("/api/v1/auth/**").permitAll() // Login
                        .requestMatchers(HttpMethod.POST, "/api/v1/clientes").permitAll() // Cadastro de cliente

                        // Todo o resto exige autenticação
                        .anyRequest().authenticated()
                )

                // Filtro JWT antes do filtro padrão
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
