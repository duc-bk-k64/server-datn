package hust.project3.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import hust.project3.config.jwtconfig.AuthEntryPointJWT;
import hust.project3.config.jwtconfig.JwtFilter;
import hust.project3.service.CustomAccessDeniedHandler;
import hust.project3.service.CustomOAuth2UserService;
import hust.project3.service.CustomOauth2User;
import hust.project3.service.CustomUserDetailService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Autowired
	private CustomUserDetailService userDetailService;
	@Autowired
	private AuthEntryPointJWT authEntryPointJWT;

	@Autowired
	private CustomOAuth2UserService oauthUserService;

	@Bean
	public JwtFilter jwtFiller() {
		return new JwtFilter();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v3/api-docs", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html",
				"/webjars/**", "/swagger-ui/**", "/configuration/security");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(authEntryPointJWT);
		http.authorizeRequests()
				.antMatchers("/api/v1/project/auth/**", "/swagger-ui/**", "/swagger-ui.html", "/ws/**", "/oauth/**")
				.permitAll().anyRequest().authenticated();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFiller(), UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
}
