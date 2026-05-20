package com.salesianostriana.dam.proyectobookversedanielmejias2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {

		http.authorizeHttpRequests(
				(authz) -> authz
					.requestMatchers("/producto/**").authenticated()
					.requestMatchers("/", "/index", "/login","/catalogo","/libros","/libros/**", "/css/**", "/js/**", "/img/**").permitAll()
					.anyRequest()
					.authenticated())
				  .requestCache(cache -> {
		              HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		              requestCache.setMatchingRequestParameterName(null);
		              cache.requestCache(requestCache);
		          })
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/index", true)
						.permitAll()
				);

		http.csrf((csrf) -> {
			csrf.ignoringRequestMatchers("/h2/**");
		});
		
		http.headers((headers) -> headers.frameOptions((opts) -> opts.disable()));

		return http.build();
	}
	
}
