/*
 * ClassName : CMRespDto(costomResponseDto)
 * 
 * Version Information : 1.0.0
 * 
 * Copyright Notice : kangmin(2022.05.11 ~ 2027.05.11)
 * 
 */
//패키지 정보들
package com.springboot.study.web.dto; 

//import 정보
import java.util.Scanner; // 왜 들고 왔는지

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Description(클래스 정보)
 * 
 * @author 누가 작성했는지 깃허브 아이디를 적는다 ex)aaa1, bbb2
 * @version 1.0.0
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CMRespDto<T> {

	/*
	 * code = 1 또는 -1 를 리턴
	 * 1 == 성공, -1 == 실패
	 */
	private int code;
	/*
	 * 응답 메세지 내용
	 */
	private String msg;
	/*
	 * 응답 데이터
	 */
	private T data;
}
