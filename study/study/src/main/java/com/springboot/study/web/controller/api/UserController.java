package com.springboot.study.web.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.study.web.controller.api.data.User;
import com.springboot.study.web.dto.AccountRequestDto;
import com.springboot.study.web.dto.CMRespDto;
import com.springboot.study.web.dto.SigninRequestDto;
import com.springboot.study.web.dto.SignupRequestDto;

@RestController
public class UserController {

	@GetMapping("/user/{usercode}")
	public ResponseEntity<?> getUser(@PathVariable int usercode){
		System.out.println(usercode);
		return new ResponseEntity<>(10, HttpStatus.BAD_REQUEST);
	}
	/*
	 * 데이터베이스가 없는 상황임!
	 * 
	 * 0. 사용자이름 중복확인(/auth/signup/check/???) => User객체에 들어있는 사용자이름과 같으면 '사용할 수 없는 아이디 입니다'
	 * 		'사용할 수 있는 아이디 입니다'
	 * 1. 회원가입(/auth/signup)
	 * 		회원가입 정보 출력(콘솔창), 응답은 200 ok, '회원가입 완료'
	 * 2. 로그인(/auth/signin)
	 * 		User객체의 정보와 일치하면(아이디, 비밀번호) '로그인 성공/실패'
	 * 3. 회원정보 수정(/account/aaa) => name, email 수정 => 회원정보 수정완료/실패
	 * 
	 * 4. 회원탈퇴(/account/aaa) => '회원탈퇴 완료/실패' (마일리지가 있으면 탈퇴실패)
	 */

	@GetMapping("/auth/signup/check/{username}")
	public ResponseEntity<?> checkUsername(@PathVariable String username){
		CMRespDto<String> cmRespDto = null;
		HttpStatus status = null;
		User user = new User();
		if(user.getUsername().equals(username)) {
			cmRespDto = new CMRespDto<String>(-1, "사용할 수 없는 사용자 이름", username);
			status = HttpStatus.BAD_REQUEST;
		}else {
			cmRespDto = new CMRespDto<String>(1, "사용할 수 있는 사용자 이름", username);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(cmRespDto, status);
	}
	@PostMapping("/auth/signup")
	public ResponseEntity<?> signup(@Valid SignupRequestDto signupRequestDto, BindingResult bindingResult){
//https://bamdule.tistory.com/35 참고
//		if(bindingResult.hasErrors()) {
//			Map<String, String> errorMap = new HashMap<String, String>();
//			for(FieldError error : bindingResult.getFieldErrors()) {
//				errorMap.put(error.getField(), error.getDefaultMessage());
//			}
//			return new ResponseEntity<>(new CMRespDto<Map<String, String>>(-1, "필드 오류", errorMap), HttpStatus.BAD_REQUEST);
//		}
		
		
		return new ResponseEntity<>(new CMRespDto<SignupRequestDto>(1, "회원가입 완료", signupRequestDto), HttpStatus.OK);
	}
	
	@PostMapping("/auth/signin")
	public ResponseEntity<?> signin(@Valid SigninRequestDto signinRequestDto, BindingResult bindingResult){
//		if(bindingResult.hasErrors()) {
//			Map<String, String> errorMap = new HashMap<String, String>();
//			for(FieldError error : bindingResult.getFieldErrors()) {
//				errorMap.put(error.getField(), error.getDefaultMessage());
//			}
//			return new ResponseEntity<>(new CMRespDto<Map<String, String>>(-1, "필드 오류", errorMap), HttpStatus.BAD_REQUEST);
//		}
		User user = new User();
		
		if(signinRequestDto.getUsername().equals(user.getUsername()) && signinRequestDto.getPassword().equals(user.getPassword())) {
			return new ResponseEntity<>(new CMRespDto<User>(1, "로그인 완료", user), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new CMRespDto<SigninRequestDto>(-1, "로그인 실패", signinRequestDto), HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	@PutMapping("/account/{username}")
	public ResponseEntity<?> updateUser(@PathVariable String username, @Valid AccountRequestDto accountRequestDto, BindingResult bindingResult){
//		if(bindingResult.hasErrors()) {
//			Map<String, String> errorMap = new HashMap<String, String>();
//			for(FieldError error : bindingResult.getFieldErrors()) {
//				errorMap.put(error.getField(), error.getDefaultMessage());
//			}
//			return new ResponseEntity<>(new CMRespDto<Map<String, String>>(-1, "필드 오류", errorMap), HttpStatus.BAD_REQUEST);
//		}
		
		User user = new User();
		
		if(!user.getUsername().equals(username)) {
			return new ResponseEntity<>(new CMRespDto<String>(1, "회원정보 수정 실패", username), HttpStatus.BAD_REQUEST);
		}
		
		user.setUsername(accountRequestDto.getName());
		user.setEmail(accountRequestDto.getEmail());
		
		return new ResponseEntity<>(new CMRespDto<String>(1, "회원정보 수정 완료", username), HttpStatus.OK);
	}
	
	@DeleteMapping("/account/{username}")
	public ResponseEntity<?> delete(@PathVariable String username){
		
		User user = new User();
		if(!user.getUsername().equals(username)) {
			return new ResponseEntity<>(new CMRespDto<String>(-1, "회원탈퇴 실패", username), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(new CMRespDto<String>(1, "회원탈퇴 완료", username), HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
