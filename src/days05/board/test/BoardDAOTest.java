package days05.board.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days05.board.domain.BoardDTO;
import days05.board.persistence.BoardDAO;
import days05.board.persistence.BoardDAOImpl;

class BoardDAOTest {
/*
	@Test
	void test() throws SQLException {
		Connection conn = DBConn.getConnection();
		BoardDAO dao=new BoardDAOImpl(conn);
		List<BoardDTO> list=dao.select();
		list.forEach(dto->{
			System.out.printf("%d %s\n",dto.getSeq()
									   ,dto.getTitle());
		});
		DBConn.close();
	}
	*/
	
	@Test
	void testInsert() throws SQLException {
		String writer="문종범";
		String pwd="1234";
		String email="test@naver.com";
		String title="월요일 지각 X";
		int tag=1;
		String content="!!!";
		
		BoardDTO dto=new BoardDTO().builder()
				.writer(writer)
				.pwd(pwd)
				.email(email)
				.title(title)
				.tag(tag)
				.content(content)
				.build();
		
		Connection conn = DBConn.getConnection();
		BoardDAOImpl dao=new BoardDAOImpl();
		dao.setConn(conn);
		int rowCount=dao.insert(dto);
		
		if (rowCount==1) {
			System.out.println("> 게시글 작성 성공");
		} else {
			System.out.println("> 게시글 작성 실패");
		}
		DBConn.close();
	}
	
}
