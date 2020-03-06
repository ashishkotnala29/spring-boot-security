package com.ashish.springbootsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationFailureHandler handler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(a -> a
				.antMatchers("/","/error","/webjars/**").permitAll()
				.anyRequest().authenticated()
				)
				.exceptionHandling(e -> e
						.authenticationEntryPoint(
								new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
						)
				.logout(l -> l
						.logoutSuccessUrl("/").permitAll()
						)
				.csrf(c -> c
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
						)
				.oauth2Login(o -> o
						.failureHandler((req, res, ex) -> {
							req.getSession().setAttribute("error.message", ex.getMessage());
							handler.onAuthenticationFailure(req, res, ex);
						})
		);
	}
}
