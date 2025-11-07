package days05.board.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.util.DBConn;

import days05.board.domain.BoardDTO;
import days05.board.persistence.BoardDAO;
import days05.board.persistence.BoardDAOImpl;
import days05.board.service.BoardService;


class BoardServiceTest {

   /*
   @Test
   void testSelectServie() throws SQLException {

      Connection conn = DBConn.getConnection();
      BoardDAO dao = new BoardDAOImpl(conn);
      BoardService service = new BoardService(dao);
      
      List<BoardDTO> list = service.selectService();
      list.forEach(dto -> {
         System.out.printf("%d %s\n"
               ,dto.getSeq()
               ,dto.getTitle());
      });

   }
   */

   
   @Test
    void testInsertService() throws SQLException {

      String writer = "문종범";
      String pwd = "1234";
      String email = "test@naver.com";
      String title = "혼자 먹고 늦게 들어오고..";
      int tag = 1;
      String content = "이러면 곤란해요....";

      BoardDTO dto = new BoardDTO().builder()
            .writer(writer)
            .pwd(pwd)
            .email(email)
            .title(title)
            .tag(tag)
            .content(content)
            .build();

      Connection conn = DBConn.getConnection();
       BoardDAOImpl dao = new BoardDAOImpl();
       dao.setConn(conn);
       BoardService service = new BoardService(dao);
       
       int rowCount = service.insertService(dto);
       
  

       if (rowCount==1) {
           System.out.println("> 게시글 작성 성공");
        } else {
           System.out.println("> 게시글 작성 실패");
        }

   }
    



}
