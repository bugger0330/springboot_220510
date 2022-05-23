package com.springboot.study.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.springboot.study.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User { 

	private static final long serialVersionUID = 1L;
	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //권한이 자동으로 들어온다, 권한을 담고 있는 컬렉션이다.
		
		Role role = new Role();
		user.getRoleList();
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		user.getRoleList().forEach(r -> { //람다식
			System.out.println("권한 : " + r);
			authorities.add(() -> r);
			/*authorities.add(() -> r); 를 풀어쓰면
			 * authorities.add(new GrantedAuthority() {
			 * 
			 * @Override public String getAuthority() { // TODO Auto-generated method stub
			 * return null; }
			 * 
			 * });
			 */
		});
		authorities.forEach(r -> {
			System.out.println("리스트에 들어있는 권한 : " + r.getAuthority());
		});
		return authorities;
	}

	/* =========================================== */ // 이걸 보고 로그인을 시켜준다
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	
	/* =========================================== *///하나라도 false가 있으면 실행이 안된다

	@Override
	public boolean isAccountNonExpired() { // 계정이 만료되었는지 확인
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // 비밀번호가 지정한 횟수 이상 틀리면 잠김
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //자격 증명이 만료가 되면 계정사용 불가
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() { // 휴먼계정
		// TODO Auto-generated method stub
		return true;
	}

	/* =========================================== */
	
	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
