package days05.board.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import days05.board.domain.BoardDTO;
import days05.board.persistence.BoardDAO;
import days05.board.persistence.BoardDAOImpl;

class BoardDAOTest {

	@Test
	void test() throws SQLException {
		BoardDAO dao=new BoardDAOImpl();
		List<BoardDTO> list=dao.select();
		list.forEach(dto->{
			System.out.printf("%d %s\n",dto.getSeq()
									   ,dto.getTitle());
		});
	}

}
