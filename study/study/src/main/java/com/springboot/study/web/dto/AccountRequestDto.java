package com.springboot.study.web.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountRequestDto {
	
	@NotBlank(message = "빈값일 수 없습니다.")
	private String name;
	
	@NotBlank(message = "빈값일 수 없습니다.")
	@Email(message = "이메일의 형식을 확인해 주세요.")
	private String email;
	
	
	
	

}
