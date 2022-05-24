package com.springboot.study.domain.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

	private int user_code;
	private String email;
	private String name;
	private String username;
	private String oAuth2_username;
	private String password;
	private String roles; // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN 등이 이렇게 들어간다
	//ROLE_ 를 붙이면 무조건 시큐리티 형식이다
	private String provider;
	
	public List<String> getRoleList(){
		
		if(this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));//Arrays.asList 는 문자열을 각각의 형식에 맞게 (this.roles.split(",")내용을 리스트로 반환해준다
			//(this.roles.split(",") 이 배열을 .asList 이걸로 리스트로 변환
		}//***this.roles.split(",")은 쉼표를 기준으로 배열을 만들어 준다.Arrays.asList는 배열=>리스트로 변환해준다
		return new ArrayList<String>();
	}
}
