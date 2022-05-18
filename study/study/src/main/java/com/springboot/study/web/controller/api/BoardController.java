package com.springboot.study.web.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.study.service.board.BoardService;
import com.springboot.study.web.dto.CMRespDto;
import com.springboot.study.web.dto.board.BoardInsertRequestDto;
import com.springboot.study.web.dto.board.BoardResponseDto;
import com.springboot.study.web.dto.board.BoardUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/board/list")
	public ResponseEntity<?> getBoardList(int page) throws Exception {
		List<BoardResponseDto> boardResponseDtos = boardService.getBoardListByPage(page);
		
		return new ResponseEntity<>(new CMRespDto<List<BoardResponseDto>>(1, "게시글 목록 로드", boardResponseDtos), HttpStatus.OK);
	}

	@PostMapping("/board")
	public ResponseEntity<?> createBoard(@Valid @RequestBody BoardInsertRequestDto boardInsertRequestDto, BindingResult bindingResult) throws Exception{
		
		int boardCode = boardService.createBoard(boardInsertRequestDto);
		
		return new ResponseEntity<>(new CMRespDto<Integer>(1, "게시글 작성 완료", boardCode), HttpStatus.OK);
		
	}
	
	@GetMapping("/board/{boardCode}")
	public ResponseEntity<?> getBoard(@PathVariable int boardCode) throws Exception{
		BoardResponseDto boardResponseDto = boardService.getBoard(boardCode);
		
		return new ResponseEntity<>(new CMRespDto<BoardResponseDto>(1, "게시글 조회 성공", boardResponseDto), HttpStatus.OK);
	}
	
	@PutMapping("/board/{boardCode}")
	public ResponseEntity<?> updateBoard(@PathVariable int boardCode, @Valid BoardUpdateRequestDto boardUpdateRequestDto, BindingResult bindingResult) throws Exception {
		int result = boardService.updateBoard(boardCode, boardUpdateRequestDto);
		
		
		return new ResponseEntity<>(new CMRespDto<Integer>(1, "게시글 수정 성공", result), HttpStatus.OK);
	}
	
	@DeleteMapping("/board/{boardCode}")
	public ResponseEntity<?> deleteBoard(@PathVariable int boardCode) throws Exception{
		int result = boardService.deleteBoard(boardCode);
		
		return new ResponseEntity<>(new CMRespDto<Integer>(1, "게시글 삭제 완료", result), HttpStatus.OK);
	}
	

	
}
