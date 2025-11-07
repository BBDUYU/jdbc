package days05.board.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.util.DBConn; 

import days05.board.domain.BoardDTO;

public class BoardDAOImpl_수정전 implements BoardDAO{

	//게시글조회
	@Override
	public List<BoardDTO> select() throws SQLException {
		String sql = "select seq,title,writer,email,writedate,readed "
				+ "from tbl_cstvsboard "
				+ "order by seq desc";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<BoardDTO> list = null;
		BoardDTO vo = null;

		int seq,readed;
		String title, writer, email;
		Date writedate;


		try {
			conn = DBConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				list = new ArrayList<BoardDTO>();
				do {
					seq = rs.getInt("seq");
					readed = rs.getInt("readed");

					title = rs.getString("title");
					writer = rs.getString("writer");
					email = rs.getString("email");

					writedate = rs.getDate("writedate");

					vo = new BoardDTO().builder()
							.seq(seq)
							.readed(readed)
							.title(title)
							.writer(writer)
							.email(email)
							.writedate(writedate).build();

					list.add(vo);

				} while (rs.next());

			}

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				DBConn.close();
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		} 

		return list;
	}

	//게시글입력
	@Override
	public int insert(BoardDTO dto) throws SQLException {
		String sql="INSERT INTO tbl_cstvsboard\r\n"
				+ "(seq,writer,pwd,email,title,tag,content)\r\n"
				+ "VALUES (seq_tbl_cstvsboard.NEXTVAL,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		int rowCount=0;
		
		try {
			conn = DBConn.getConnection();
			pstmt =  conn.prepareStatement(sql);
			// ? ? IN 매개변수가 누락되었다.
			pstmt.setString(1, dto.getWriter());
			pstmt.setString(2, dto.getPwd());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getTitle());
			pstmt.setInt(5, dto.getTag());
			pstmt.setString(6, dto.getContent());
			rowCount = pstmt.executeUpdate();

		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				DBConn.close();   
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		} 
		return rowCount;
	}

	@Override
	public int increaseReaded(int seq) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BoardDTO view(int seq) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(int seq) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(BoardDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BoardDTO> search(String Condition, String Word) {
		// TODO Auto-generated method stub
		return null;
	}

}

