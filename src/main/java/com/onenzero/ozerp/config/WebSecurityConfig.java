package com.onenzero.ozerp.config;

import com.onenzero.ozerp.core.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	@Lazy
    private JwtFilter jwtFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
				.dispatcherTypeMatchers(HttpMethod.valueOf("/**/register"))
				.dispatcherTypeMatchers(HttpMethod.valueOf("/verifyRegistration"))
				.dispatcherTypeMatchers(HttpMethod.valueOf("/resendVerifyToken"))
				.dispatcherTypeMatchers(HttpMethod.valueOf("/**/resetPassword"))
				.dispatcherTypeMatchers(HttpMethod.valueOf("/**/reset/password"))
				.dispatcherTypeMatchers(HttpMethod.valueOf("/**/changePassword"))
				.dispatcherTypeMatchers(HttpMethod.valueOf("/**/login"));
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.cors()
				.and()
				.csrf()
				.disable()
				.authorizeRequests()
				.anyRequest()
				.authenticated()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}