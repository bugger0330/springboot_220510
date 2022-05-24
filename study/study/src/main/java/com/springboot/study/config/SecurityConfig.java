package com.springboot.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.springboot.study.config.oauth2.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity // 기존의 웹 시큐리티(WebSecurityConfigurerAdapter)의 설정을 비활성화 시키고, 현재 클래스(SecurityConfig)의 설정을 따르겠다.
@Configuration //컨포넌트랑 같다 얘가 있어야만 @Bean 을 달 수 있다. 설정 관련 객체가 있으면 이게 붙는다고 생각하면 됨
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {//WebSecurityConfigurerAdapter 안에는 모든 시큐리티 설정들이 들어있다.

	private final PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}// 리턴에서 생성된 객체가 bean에 의해 ioc에 등록된다, @Configuration 가 달려있어야만 한다
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();//로그인 페이지를 서버가 띄울때 각종 input태그에 토큰을 심어둬서, 토큰을 먼저 비교한다, 하지만 우리는 .formLogin() 밑에 요청 페이지 주소를 지정해뒀기 때문에 안쓴다
		
		http.authorizeRequests() // 인증 관련 요청
			.antMatchers("/api/board/**", "/", "/board/list") //1. 이런 요청이 들어오면 무조건
			.authenticated() // 2. 인증이 필요하다(권한은 상관없지만 로그인을 해라)
			.antMatchers("/api/v1/user/**", "/user/account/**") //해당 ROLE을 가지고 있는 아이디만 접속 시켜라
			.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/manager/**")
			.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/api/v1/admin/**")
			.access("hasRole('ROLE_ADMIN')")
			.anyRequest() // 다른 모든 요청들
			.permitAll()// 그외 모든 요청은 => 권한이 필요없다.(모두에게 권한을 주겠다)
			//http.authorizeRequests() 안의 모든 설정을 해주는 것들,
			.and() // 새로운 객체의 설정을 해야 할때 구분을 지어준다
			
			.formLogin()//이 객체 안에 있는 여러가지 설정들을 한다
			.loginPage("/auth/signin") // 로그인 페이지 get 요청(view)
			.loginProcessingUrl("/auth/signin") // 로그인 post 요청(PrincipalDetailService => loadUserByUsername 호출)
			.defaultSuccessUrl("/") //로그인이 성공하면  localhost:8000/ 으로 이동해라
			.and()
			.oauth2Login()
			.loginPage("/auth/signin")
			.userInfoEndpoint()
			/*0. .userInfoEndpoint() 가 실행되면
			 * 1. 코드를 받는다(google, naver, kakao 등 로그인 요청을 했을때 부여되는 코드번호)
			 * 2. access 토큰을 발급받는다(JWT)
			 * 3. 사용자(스코프) 정보에 접근할 수 있는 권한이 생긴다.
			 * 4. 해당 경로를 시큐리티에서 활용하면 된다.
			 */
			.userService(principalOauth2UserService)
			.and()
			.defaultSuccessUrl("/");
		
		
		
		
		
		
		
	}
}
