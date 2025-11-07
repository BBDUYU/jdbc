package days05.board.persistence;

import java.sql.SQLException;
import java.util.List;

import days05.board.domain.BoardDTO;

public interface BoardDAO {
	
	//1. 게시글 목록 조회 + 페이징 처리 X
	List<BoardDTO> select() throws SQLException;
	
	//2. 게시글 작성
	 int insert(BoardDTO dto) throws SQLException;

	 //3-1. 조회수 증가
	 int increaseReaded(int seq) throws SQLException;

	 //3-2. 게시글 상세보기
	 BoardDTO view(int seq) throws SQLException;

	 //4. 게시글 삭제
	 int delete(int seq) throws SQLException;

	 //5. 게시글 수정
	 int update(BoardDTO dto) throws SQLException;

	 //게시글 검색
	 List<BoardDTO> search(String searchCondition, String searchWord);
}
