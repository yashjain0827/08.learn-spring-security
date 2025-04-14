package com.yashtojava.learn_spring_security.basic;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuthSecurityConfiguration {
	@Bean
	SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
		// http.formLogin(withDefaults());
		http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.httpBasic(withDefaults());
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin();
		return http.build();
	}

	// in memory authentication(user details service)

	// @Bean
	// public UserDetailsService userDetailsService() {
	// var user =
	// User.withUsername("yash").password("{noop}yash123").roles("USER").build();
	// var admin =
	// User.withUsername("ayush").password("{noop}ayush123").roles("ADMIN").build();
	// return new InMemoryUserDetailsManager(user, admin);
	//
	// }

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}

	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		var user = User.withUsername("yash").password("{noop}yash123").roles("USER").build();
		var admin = User.withUsername("ayush").password("{noop}ayush123").roles("ADMIN").build();
		var JdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		JdbcUserDetailsManager.createUser(user);
		JdbcUserDetailsManager.createUser(admin);

		return JdbcUserDetailsManager;

	}

}
