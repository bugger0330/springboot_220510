package com.springboot.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration //컨포넌트랑 같다
public class SecurityConfig extends WebSecurityConfigurerAdapter {//WebSecurityConfigurerAdapter 안에는 모든 시큐리티 설정들이 들어있다.

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.anyRequest()
			.permitAll();
	}
}
