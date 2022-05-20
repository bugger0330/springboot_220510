package com.springboot.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration //컨포넌트랑 같다
public class SecurityConfig extends WebSecurityConfigurerAdapter {//WebSecurityConfigurerAdapter 안에는 모든 시큐리티 설정들이 들어있다.

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}// 리턴에서 생성된 객체가 bean에 의해 ioc에 등록된다, @Configuration 가 달려있어야만 한다
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/api/board/**", "/", "/board/list") //1. 이런 요청이 들어오면 무조건
			.authenticated() // 2. 인증이 필요하다
			.antMatchers("/api/v1/user/**")
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/manager/**")
			.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/admin/**")
			.access("hasRole('ROLE_ADMIN')")
			.anyRequest()
			.permitAll()// 그외 모든 요청은 => 권한이 필요없다.
			.and()
			.formLogin()
			.loginPage("/auth/signin") // 로그인 페이지 get 요청(view)
			.loginProcessingUrl("/auth/signin") // 로그인 post 요청(PrincipalDetailService => loadUserByUsername 호출)
			.defaultSuccessUrl("/"); //로그인이 성공하면  localhost:8000/ 으로 이동해라
	}
}
