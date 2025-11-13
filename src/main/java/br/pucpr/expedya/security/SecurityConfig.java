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
@EnableMethodSecurity // Habilita o uso do @PreAuthorize nos controllers
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Bean para expor o PasswordEncoder (BCrypt) para a aplicação.
     * Usado no ClienteService para criptografar a senha antes de salvar.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean para expor o AuthenticationManager.
     * Usado no AuthController para processar a tentativa de login.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desativa CSRF (para APIs REST)
                .csrf(csrf -> csrf.disable())
                // Define política stateless (sem sessão de servidor)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Libera acesso ao Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // 🔓 Libera endpoints públicos
                        .requestMatchers("/api/v1/auth/**").permitAll() // Login
                        .requestMatchers(HttpMethod.POST, "/api/v1/clientes").permitAll() // Cadastro de cliente

                        // 🔒 Qualquer outra rota exige autenticação
                        // As regras específicas (ADMIN, USER) serão tratadas
                        // nos controllers com @PreAuthorize
                        .anyRequest().authenticated()
                )
                // 🧱 Adiciona o filtro JWT antes do filtro padrão de autenticação
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}