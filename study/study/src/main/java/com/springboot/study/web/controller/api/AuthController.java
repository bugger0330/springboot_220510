package com.springboot.study.web.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.study.config.auth.PrincipalDetails;
import com.springboot.study.domain.user.User;
import com.springboot.study.domain.user.UserRepository;
import com.springboot.study.web.dto.CMRespDto;
import com.springboot.study.web.dto.SigninRequestDto;
import com.springboot.study.web.dto.SignupRequestDto;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody User user) {
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); // 암호화 시켜준다(키값 필요없다)
		
		
//		List<String> roles = List.of("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN");
		
		List<String> roles = List.of("ROLE_USER");
		//리스트에 바로 값 넣는 방법
		user.setRoles(String.join(",", roles)); //"ROLE_USER, ROLE_MANAGER, ROLE_ADMIN"
		//join => 리스트에 들어있는 문자열들을 쉼표(,)로 구분해서 합쳐주는 방법
		
		userRepository.insertUser(user);
		
		return new ResponseEntity<>(new CMRespDto<User>(1, "회원가입완료", user), HttpStatus.OK);
		
	}
	
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

	
	
	@GetMapping("/authentication")
	public ResponseEntity<?> getAuthentication(@AuthenticationPrincipal PrincipalDetails principalDetails){
		System.out.println(principalDetails.getUser().getUser_code());
		System.out.println(principalDetails.getUser().getPassword());//암호화된 비밀번호 출력
		String password = principalDetails.getUser().getPassword();
		System.out.println(bCryptPasswordEncoder.matches("1234", password));//원래 알고있는 비밀번호가 맞는지 틀리는지를 확인해준다 => 결과값은 true/false
		return new ResponseEntity<>(new CMRespDto<PrincipalDetails>(1, "세션정보", principalDetails), HttpStatus.OK);
		
	}
	
//	@GetMapping("/user")
//	public ResponseEntity<?> testUser(){
//		
//		return new ResponseEntity<>(new CMRespDto<String>(1, "유저권한", "role_user"),HttpStatus.OK);
//	}
	@GetMapping("/manager")
	public ResponseEntity<?> testManager(){
		
		return new ResponseEntity<>(new CMRespDto<String>(1, "매니저권한", "role_manager"),HttpStatus.OK);
	}
	@GetMapping("/admin")
	public ResponseEntity<?> testAdmin(){
		
		return new ResponseEntity<>(new CMRespDto<String>(1, "관리자권한", "role_admin"),HttpStatus.OK);
	}
	
}










