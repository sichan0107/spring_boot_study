package board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import board.dto.BoardDto;

@Service
public class BoardService {

	List<BoardDto> selectBoardList() throws Exception;
}
