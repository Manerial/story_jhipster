package com.jher.nid_aux_histoires.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import com.jher.nid_aux_histoires.security.AuthoritiesConstants;
import com.jher.nid_aux_histoires.security.jwt.JWTConfigurer;
import com.jher.nid_aux_histoires.security.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final TokenProvider tokenProvider;

	private final CorsFilter corsFilter;
	private final SecurityProblemSupport problemSupport;

	public SecurityConfiguration(TokenProvider tokenProvider, CorsFilter corsFilter,
			SecurityProblemSupport problemSupport) {
		this.tokenProvider = tokenProvider;
		this.corsFilter = corsFilter;
		this.problemSupport = problemSupport;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/app/**/*.{js,html}").antMatchers("/i18n/**")
				.antMatchers("/content/**").antMatchers("/h2-console/**").antMatchers("/swagger-ui/index.html")
				.antMatchers("/test/**");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
        http
            .csrf()
            .disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport)
        .and()
            .headers()
            .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
        .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
        .and()
            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
        .and()
            .frameOptions()
            .deny()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/export/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/users/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/books/import").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/word-analyses/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/ideas/**").hasAuthority(AuthoritiesConstants.ADMIN)
            
            .antMatchers("/api/parts/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.AUTHOR)
            .antMatchers("/api/chapters/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.AUTHOR)
            .antMatchers("/api/scenes/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.AUTHOR)
            .antMatchers("/api/covers/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.AUTHOR)
            .antMatchers("/api/bonus/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.AUTHOR)
            .antMatchers("/api/bonus/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER)
            
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
        .and()
            .httpBasic()
        .and()
            .apply(securityConfigurerAdapter());
        // @formatter:on
	}

	private JWTConfigurer securityConfigurerAdapter() {
		return new JWTConfigurer(tokenProvider);
	}

	public static Authentication getLoggedUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static void CheckLoggedUser(String login) throws Exception {
		if (IsLoggedUser(login) || IsAdmin()) {
			return;
		}

		throw new Exception("User cannot access this resource : role does not match");
	}

	public static boolean IsLoggedUser(String login) {
		Authentication auth = getLoggedUser();
		return login != null && login.equals(auth.getName());
	}

	public static boolean IsAdmin() {
		Authentication auth = getLoggedUser();
		return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(AuthoritiesConstants.ADMIN));
	}
}
