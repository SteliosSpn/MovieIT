package com.movieit.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select email as principal, password as credentails, true from public.user where email=?")
		.authoritiesByUsernameQuery("select user_email as principal, role_name as role from public.user_roles where user_email=?")
		.passwordEncoder(passwordEncoder()).rolePrefix("ROLE_");  
		
	}
   
	@Bean
	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public LayoutDialect layoutDialect() {
	    return new LayoutDialect();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/register", "/", "/about", "/login", "/css/**", "/webjars/**").permitAll()
				.antMatchers("/profile","/movie").hasAnyRole("USER,ADMIN")
				.antMatchers("/addMovie").hasRole("ADMIN")
				  //.anyRequest().hasAnyRole("USER,ADMIN")
				.and().formLogin().loginPage("/login").permitAll()
				.defaultSuccessUrl("/index").and().logout().logoutSuccessUrl("/login");
	}
}
