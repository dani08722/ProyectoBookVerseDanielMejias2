package com.salesianostriana.dam.proyectobookversedanielmejias2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {

		http.authorizeHttpRequests(
				(authz) -> authz
					.requestMatchers("/", "/index", "/login", "/registro", "/acceso-denegado","/catalogo","/libros","/libros/**", "/css/**", "/js/**", "/img/**").permitAll()
					.requestMatchers("/admin/**").hasRole("ADMIN")
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
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/index")
						.permitAll()
				)
				.exceptionHandling(exception -> exception
						.accessDeniedHandler((request, response, accessDeniedException) ->
								response.sendRedirect(request.getContextPath() + "/acceso-denegado"))
				);

		http.csrf((csrf) -> {
			csrf.ignoringRequestMatchers("/h2/**");
		});
		
		http.headers((headers) -> headers.frameOptions((opts) -> opts.disable()));

		return http.build();
	}
	
	public boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return isAuthenticated()
				&& authentication.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.anyMatch("ROLE_ADMIN"::equals);
	}

	public boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return authentication != null
				&& authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken);
	}

	public String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return isAuthenticated() ? authentication.getName() : "";
	}

}
