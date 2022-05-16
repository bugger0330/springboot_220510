package com.springboot.study.web.dto.board;

import javax.validation.constraints.NotBlank;

import com.springboot.study.domain.board.BoardMst;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardUpdateRequestDto {

	@NotBlank
	private String Title;
	@NotBlank
	private String Content;
	

	
	public BoardMst toEntity(int boardCode) {
		return BoardMst.builder()
				.board_code(boardCode)
				.board_title(Title)
				.board_content(Content)
				.build();
	}
}
