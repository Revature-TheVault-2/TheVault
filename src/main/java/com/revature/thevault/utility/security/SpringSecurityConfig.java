package com.revature.thevault.utility.security;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	// Holds the credentials for the backend database used for authentication.
	@Autowired
	DataSource dataSource;
	
	@Autowired
	NoOpPasswordEncoder encoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.cors() // Enables CORS filter.
			.and()
		.csrf().disable() // Disables CSRF protection.
		.authorizeRequests()
			.antMatchers("/login", "/create", "/profile/create").permitAll() // Whitelist of URLs that can be accessed without credentials.
			.anyRequest().authenticated()
			.and()
		.httpBasic() // Enable HTTP Basic Authentication.
			.and()
		.logout() // Whitelist logout and enable default logout functionality.
			.permitAll();
			
				
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/*
		 * Configures Spring Security to connect to our preexisting database for validating credentials.
		 * Defines the queries used to retrieve credentials from the database and defines the password encoder to hash passwords with.
		 */
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("select username, password, 'true' from login_credential_table where username = ?")
			.authoritiesByUsernameQuery("select username,'USER' from login_credential_table where username = ?")
			.passwordEncoder(encoder);
	}
	
	/**
	 * Configures the CORS filter to be used on all incoming HTTP requests.
	 * 
	 * @return
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
