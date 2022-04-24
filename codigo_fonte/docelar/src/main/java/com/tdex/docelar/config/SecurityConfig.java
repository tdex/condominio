package com.tdex.docelar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tdex.docelar.security.JwtAuthFilter;
import com.tdex.docelar.security.JwtService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static final String PERFIL_ADMIN = "ADMIN";
	private static final String PERFIL_USER = "USER";

	@Autowired
	private UsuarioServiceConfig usuarioService;

	@Autowired
	private JwtService jwtService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, usuarioService);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/api/apartamento/**")
					.hasRole(PERFIL_ADMIN)
				.antMatchers("/api/predio/**")
					.hasRole(PERFIL_ADMIN)
				.antMatchers("/api/veiculo/**")
					.hasRole(PERFIL_ADMIN)
				.antMatchers("/api/pessoa/**")
					.hasAnyRole(PERFIL_ADMIN, PERFIL_USER)
				.antMatchers("/api/telefone/**")
					.hasAnyRole(PERFIL_ADMIN, PERFIL_USER)
				.antMatchers(HttpMethod.PATCH, "/api/agendamentoarea/**")
					.hasRole(PERFIL_ADMIN)
				.antMatchers(HttpMethod.POST, "/api/usuario/**")
					.permitAll()
				.anyRequest().authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
