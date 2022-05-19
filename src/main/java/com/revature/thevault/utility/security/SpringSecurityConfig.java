package com.revature.thevault.utility.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	NoOpPasswordEncoder encoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests()
			.antMatchers("/").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.permitAll()
			.and()
		.logout()
			.permitAll();
			
				
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("select username, password, 'true' from login_credential_table where username = ?")
			.authoritiesByUsernameQuery("select username,'USER' from login_credential_table where username = ?")
			.passwordEncoder(encoder);
	}
}
