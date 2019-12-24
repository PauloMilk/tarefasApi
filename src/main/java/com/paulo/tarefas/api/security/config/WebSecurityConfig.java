package com.paulo.tarefas.api.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.paulo.tarefas.api.security.JwtAuthenticationEntryPoint;
import com.paulo.tarefas.api.security.filters.JwtAuthenticationTokenFilter;
import com.paulo.tarefas.api.service.impl.JwtUserDetailsServiceImpl;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint uNAuthenticationEntryPoint;
	
	
	@Autowired
	private JwtUserDetailsServiceImpl userDetailsService;

	@Bean
	public JwtAuthenticationTokenFilter authenticationTolenFilterBean() throws Exception {
		return new JwtAuthenticationTokenFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(uNAuthenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/webjars/**", "/css/**", "/image/**", "/js/**").permitAll()
				.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
				.permitAll()
				.antMatchers("/api/auth/**","/api/usuarios/**").permitAll()
				.anyRequest().authenticated();
		http.addFilterBefore(authenticationTolenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		http.headers().cacheControl();
	}
	
	 @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("*"));
	        configuration.setAllowedMethods(Arrays.asList("*"));
	        configuration.setAllowedHeaders(Arrays.asList("*"));
	        configuration.setAllowCredentials(true);
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }

}
