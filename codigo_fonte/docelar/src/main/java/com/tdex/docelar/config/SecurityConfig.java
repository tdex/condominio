package com.tdex.docelar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tdex.docelar.security.JwtAuthFilter;
import com.tdex.docelar.security.JwtService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private static final String PERFIL_ADMIN = "ADMIN";
	private static final String PERFIL_USER = "USER";

	@Autowired
	private UsuarioServiceConfig usuarioService;

	@Autowired
	private JwtService jwtService;
    
	@Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
	@Bean
	OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, usuarioService);
	}

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/apartamento/**")
                        	.hasRole(PERFIL_ADMIN)
                        .requestMatchers("/api/predio/**")
                        	.hasRole(PERFIL_ADMIN)
                        .requestMatchers("/api/veiculo/**")
                        	.hasRole(PERFIL_ADMIN)
                        .requestMatchers("/api/pessoa/**")
                        	.hasAnyRole(PERFIL_ADMIN, PERFIL_USER)
                        .requestMatchers("/api/telefone/**")
                        	.hasAnyRole(PERFIL_ADMIN, PERFIL_USER)
                        .requestMatchers(HttpMethod.PATCH, "/api/agendamentoarea/**")
                        	.hasRole(PERFIL_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/usuario/**")
                        	.permitAll()
                        .anyRequest().authenticated()
                        );
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
	}
}
