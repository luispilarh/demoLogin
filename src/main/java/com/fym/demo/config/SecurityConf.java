package com.fym.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConf {
	@Autowired
	DataSource dataSource;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
			.httpBasic()
			.and()
				.authorizeRequests()
					.antMatchers("/swagger-ui**").permitAll()
					.anyRequest().authenticated();

		httpSecurity.csrf().disable();
		return httpSecurity.build();

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
		throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select email,password,enabled "
				+ "from users "
				+ "where email = ?")
			.authoritiesByUsernameQuery("select email,role "
				+ "from roles "
				+ "where email = ?");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
