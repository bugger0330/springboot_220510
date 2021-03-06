package com.springboot.study.web.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardResponseDto {
	
	
	private int boardCode;
	
	private String title;
	
	private String content;
	
	private int usercode;
	
	private String username;
	
	private int boardCount;
	
	private long boardCountAll;
}
