package board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import board.dto.BoardDto;


public interface BoardService {

	List<BoardDto> selectBoardList() throws Exception;
	
	void insertBoard(BoardDto board) throws Exception;
	
	
}
