package days05.board.service;

import java.sql.SQLException;
import java.util.List;

import days05.board.domain.BoardDTO;
import days05.board.persistence.BoardDAO;
import days05.board.persistence.BoardDAOImpl;

// 트랜잭션 처리( 가장 중요한 기능 )
public class BoardService {


	private BoardDAO dao=null;


	//생성자 di
	public BoardService(BoardDAO dao) {
		this.dao=dao;
	}

	//setter di
	public void setDao(BoardDAO dao) {
		this.dao=dao;
	}

	//1. 게시글목록
	public List<BoardDTO> selectService() {
		List<BoardDTO> list=null;
		try {

			((BoardDAOImpl)this.dao).getConn().setAutoCommit(false);
			//1.
			list=this.dao.select();
			//2. 로그 기록 DB처리
			System.out.println("> 게시글 목록 : 로그 기록 서비스 작업");
			//3. 문자 / 메일
			System.out.println("> 게시글 목록 : 문자/메일 전송 서비스 작업");
			// 수동 커밋
			((BoardDAOImpl)this.dao).getConn().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				((BoardDAOImpl)this.dao).getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}finally {
			try {
				((BoardDAOImpl)this.dao).getConn().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list; //BoardController 전달
	}
	//2. 게시글작성
	public int insertService(BoardDTO dto) {
		int rowCount=0;

		try {

			((BoardDAOImpl)this.dao).getConn().setAutoCommit(false);
			//1.
			rowCount=this.dao.insert(dto);
			//2. 로그 기록 DB처리
			System.out.println("> 게시글 작성 : 로그 기록 서비스 작업");
			//3. 문자 / 메일
			System.out.println("> 게시글 작성 : 문자/메일 전송 서비스 작업");
			// 수동 커밋
			((BoardDAOImpl)this.dao).getConn().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				((BoardDAOImpl)this.dao).getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}finally {
			try {
				((BoardDAOImpl)this.dao).getConn().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rowCount;
	}

	//3. 게시글 상세보기
	public BoardDTO viewService(int seq) {
		BoardDTO dto=null;
		try {

			((BoardDAOImpl)this.dao).getConn().setAutoCommit(false);

			//1-1. 해당 게시글 조회수 증가
			int rowCount=this.dao.increaseReaded(seq);
			//1-2. 해당 게시글 가져오기
			dto=this.dao.view(seq);

			//2. 로그 기록 DB처리
			System.out.println("> 게시글 보기 : 로그 기록 서비스 작업");
			//3. 문자 / 메일
			System.out.println("> 게시글 보기 : 문자/메일 전송 서비스 작업");

			// 수동 커밋
			((BoardDAOImpl)this.dao).getConn().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				((BoardDAOImpl)this.dao).getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}finally {
			try {
				((BoardDAOImpl)this.dao).getConn().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dto; //BoardController 전달
	}

	public int deleteService(int seq) {
		int rowCount=0;

		try {

			((BoardDAOImpl)this.dao).getConn().setAutoCommit(false);
			//1.
			rowCount=this.dao.delete(seq);
			//2. 로그 기록 DB처리
			System.out.println("> 게시글 삭제 : 로그 기록 서비스 작업");
			//3. 문자 / 메일
			System.out.println("> 게시글 삭제 : 문자/메일 전송 서비스 작업");
			// 수동 커밋
			((BoardDAOImpl)this.dao).getConn().commit();
		} catch (SQLException e) {
			//e.printStackTrace();
			try {
				System.out.println("> 삭제 실패로 로그/문자 전송 트랜잭션 롤백됩니다.");
				((BoardDAOImpl)this.dao).getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}finally {
			try {
				((BoardDAOImpl)this.dao).getConn().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rowCount;
	}

	public int updateService(BoardDTO dto) {
		int rowCount=0;
		
		try {	
			((BoardDAOImpl)this.dao).getConn().setAutoCommit(false);
			//1.
			rowCount=this.dao.update(dto);
			//2. 로그 기록 DB처리
			System.out.println("> 게시글 수정 : 로그 기록 서비스 작업");
			//3. 문자 / 메일
			System.out.println("> 게시글 수정 : 문자/메일 전송 서비스 작업");
			// 수동 커밋
			((BoardDAOImpl)this.dao).getConn().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				((BoardDAOImpl)this.dao).getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}finally {
			try {
				((BoardDAOImpl)this.dao).getConn().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rowCount;	
	}
	public List<BoardDTO> searchService(String Condition, String Word) {
	    List<BoardDTO> list = null;
	    try {
	        ((BoardDAOImpl)this.dao).getConn().setAutoCommit(false);

	        //1.
	        list = this.dao.search(Condition, Word);

			//2. 로그 기록 DB처리	        
	        System.out.println("> 게시글 검색 : 로그 기록 서비스 작업");
			//3. 문자 / 메일
	        System.out.println("> 게시글 검색 : 문자/메일 전송 서비스 작업");

	        ((BoardDAOImpl)this.dao).getConn().commit();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            ((BoardDAOImpl)this.dao).getConn().rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    } finally {
	        try {
	            ((BoardDAOImpl)this.dao).getConn().setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return list;
	}

}
