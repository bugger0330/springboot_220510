package com.springboot.study.service.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.springboot.study.domain.board.BoardMst;
import com.springboot.study.domain.board.BoardRepository;
import com.springboot.study.web.dto.board.BoardInsertRequestDto;
import com.springboot.study.web.dto.board.BoardResponseDto;
import com.springboot.study.web.dto.board.BoardUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
// @RequiredArgsConstructor 는 @authwired 와 같은 기능을 하는데, private에 final만 붙이면 각각 따로 @ 안써도 된다
	
	private final BoardRepository boardRepository;
	
	@Override
	public List<BoardResponseDto> getBoardListAll() throws Exception {
		List<BoardResponseDto> boardResponseDtos = new ArrayList<BoardResponseDto>();
		
		List<Map<String, Object>> boardListAll = boardRepository.getBoardListAll();
		for(Map<String, Object> boardMap : boardListAll) {
			boardResponseDtos.add(BoardResponseDto.builder()
					.boardCode((Integer) (boardMap.get("board_code")))
					.title((String) (boardMap.get("board_title"))) 
					.content((String) (boardMap.get("board_content")))
					.usercode((Integer) (boardMap.get("board_writer")))
					.username((String) (boardMap.get("board_username")))
					.boardCount((Integer) (boardMap.get("board_count")))
					.build());
		}
		
		return boardResponseDtos;
	}
	
	@Override
		public List<BoardResponseDto> getBoardListByPage(int page) throws Exception {
			List<BoardResponseDto> boardResponseDtos = new ArrayList<BoardResponseDto>();
		
			List<Map<String, Object>> boardListAll = boardRepository.getBoardListByPage((page - 1)*5);
			for(Map<String, Object> boardMap : boardListAll) {
				boardResponseDtos.add(BoardResponseDto.builder()
						.boardCode((Integer) (boardMap.get("board_code")))
						.title((String) (boardMap.get("board_title"))) 
						.content((String) (boardMap.get("board_content")))
						.usercode((Integer) (boardMap.get("board_writer")))
						.username((String) (boardMap.get("board_username")))
						.boardCount((Integer) (boardMap.get("board_count")))
						.boardCountAll((Long) (boardMap.get("board_count_all")))
						.build());
			}
		
			return boardResponseDtos;
		}
	
	@Override
	public int createBoard(BoardInsertRequestDto boardInsertRequestDto) throws Exception {
		
		BoardMst boardMst = boardInsertRequestDto.toBoardMstEntity();
		int result = boardRepository.insertBoard(boardMst);
		
		if(result > 0) {
			return boardMst.getBoard_code();
		}
		
		return 0;
	}

	@Override
	public BoardResponseDto getBoard(int boardCode) throws Exception {
		Map<String, Object> boardMap = boardRepository.getBoardByBoardCode(boardCode);
		
		return BoardResponseDto.builder()
				.boardCode((Integer) (boardMap.get("board_code")))
				.title((String) (boardMap.get("board_title"))) 
				.content((String) (boardMap.get("board_content")))
				.usercode((Integer) (boardMap.get("board_writer")))
				.username((String) (boardMap.get("board_username")))
				.boardCount((Integer) (boardMap.get("board_count")))
				.build();
	}

	@Override
	public int updateBoard(int boardCode, BoardUpdateRequestDto boardUpdateRequestDto) throws Exception {
		BoardMst boardMst = boardUpdateRequestDto.toEntity(boardCode);
		
		return boardRepository.updateBoard(boardMst) > 0 ? boardCode : 0;
	}

	@Override
	public int deleteBoard(int boardCode) throws Exception {
		
		return boardRepository.deleteBoard(boardCode) > 0 ? boardCode : 0;
	}


}
