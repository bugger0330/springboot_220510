package com.springboot.study.service.board;

import java.util.List;

import com.springboot.study.web.dto.board.BoardInsertRequestDto;
import com.springboot.study.web.dto.board.BoardResponseDto;
import com.springboot.study.web.dto.board.BoardUpdateRequestDto;

public interface BoardService {
	
	public List<BoardResponseDto> getBoardListAll() throws Exception;
	public List<BoardResponseDto> getBoardListByPage(int page) throws Exception;
	
	public int createBoard(BoardInsertRequestDto boardInsertRequestDto) throws Exception;
	public BoardResponseDto getBoard(int boardCode) throws Exception;
	public int updateBoard(int boardCode, BoardUpdateRequestDto boardUpdateRequestDto) throws Exception ;
	public int deleteBoard(int boardCode) throws Exception;
}
