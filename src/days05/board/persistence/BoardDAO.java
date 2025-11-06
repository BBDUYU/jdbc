package days05.board.persistence;

import java.sql.SQLException;
import java.util.List;

import days05.board.domain.BoardDTO;

public interface BoardDAO {
	
	//1. 게시글 목록 조회 + 페이징 처리 X
	List<BoardDTO> select() throws SQLException;
}
