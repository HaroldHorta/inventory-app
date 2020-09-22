package com.company.storeapi.core.security;

import com.company.storeapi.core.security.jwt.AuthEntryPointJwt;
import com.company.storeapi.core.security.jwt.AuthTokenFilter;
import com.company.storeapi.core.security.service.UserDetailsServiceImpl;
import com.company.storeapi.model.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsServiceImpl userDetailsService;

	private final AuthEntryPointJwt unauthorizedHandler;

	public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
		this.userDetailsService = userDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
				.antMatchers("/v3/api-docs", "/swagger-ui.html","/swagger-ui.html/**", "/configuration/**",  "/swagger-ui/**",  "/webjars/**").permitAll()

				//create user and login
				.antMatchers(HttpMethod.POST,"/api/user/**").permitAll()
				//category
				.antMatchers(HttpMethod.GET,"/api/category/**").permitAll()
				.antMatchers(HttpMethod.POST,"/api/category/**").hasAnyAuthority(String.valueOf(Role.ADMINISTRATOR),String.valueOf(Role.SUPER_ADMINISTRATOR))
				.antMatchers(HttpMethod.PUT,"/api/category/**").hasAnyAuthority(String.valueOf(Role.ADMINISTRATOR),String.valueOf(Role.SUPER_ADMINISTRATOR))
				.antMatchers(HttpMethod.DELETE,"/api/category/**").hasAnyAuthority(String.valueOf(Role.SUPER_ADMINISTRATOR))

				//prodcut
				.antMatchers(HttpMethod.GET,"/api/product/**").permitAll()
				.antMatchers(HttpMethod.POST,"/api/product/**").hasAnyAuthority(String.valueOf(Role.ADMINISTRATOR),String.valueOf(Role.SUPER_ADMINISTRATOR))
				.antMatchers(HttpMethod.PUT,"/api/product/**").hasAnyAuthority(String.valueOf(Role.ADMINISTRATOR),String.valueOf(Role.SUPER_ADMINISTRATOR))
				.antMatchers(HttpMethod.DELETE,"/api/product/**").hasAnyAuthority(String.valueOf(Role.SUPER_ADMINISTRATOR))


				.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
