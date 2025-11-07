package days05;

import java.sql.Connection;

import com.util.DBConn;

import days05.board.controller.BoardController;
import days05.board.persistence.BoardDAO;
import days05.board.persistence.BoardDAOImpl;
import days05.board.service.BoardService;

public class Ex01 {

	public static void main(String[] args) {
		
		Connection conn=DBConn.getConnection();
		BoardDAO dao=new BoardDAOImpl(conn);
		BoardService service = new BoardService(dao);
		BoardController controller=new BoardController(service);
		
		controller.boardStart();
		
		
		//문제점 1) 상세보기 시 없은 seq를 입력하면 트랜잭션 처리 X
		
		/*
		 * MVC 패턴으로 구현
		 * - 설계패턴
		 * - Model		: 비즈니스 로직처리 DTO, DAO, Service
		 * 			domain 패키지 - DTO, VO
		 * 			persistence 패키지 - DAO
		 * 			service 패키지 - 서비스
		 * - View		: 뷰, 화면출력
		 * - Controller	: 컨트롤러
		 * 		contoller 패키지
		 *	
		 * */
		/*
			1. tbl_cstvsboard 생성
			2. tbl_cstvsboard 시퀀스 생성 또는 확인
			3. BoardDTO 생성
			4. BoardDAO 생성
			5. BoardDAOImpl 생성
				select, insert 구현
			6. BoardDAOImpl_수정전 파일 생성
			7. BoardDAOTest 단위테스트
			8. BoardService 생성
		*/
	}
}
