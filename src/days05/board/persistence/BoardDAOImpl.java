package days05.board.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import days05.board.domain.BoardDTO;

public class BoardDAOImpl implements BoardDAO{
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private BoardDTO vo = null;

	//1. 생성자 DI
	public BoardDAOImpl() {
		super();
	}
	public BoardDAOImpl(Connection conn) {
		super();
		this.conn = conn;
	}


	//2. Setter DI

	public void setConn(Connection conn) {
		this.conn = conn;
	}


	public Connection getConn() {
		return conn;
	}


	//게시글조회
	@Override
	public List<BoardDTO> select() throws SQLException {
		String sql = "select seq,title,writer,email,writedate,readed "
				+ "from tbl_cstvsboard "
				+ "order by seq desc";

		ArrayList<BoardDTO> list = null;

		int seq,readed;
		String title, writer, email;
		Date writedate;


		try {
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

		int rowCount=0;

		try {
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
			} catch (SQLException e) { 
				e.printStackTrace();
			}
		} 
		return rowCount;
	}

	@Override
	public int increaseReaded(int seq) throws SQLException {
		String sql = "UPDATE tbl_cstvsboard "
				+"SET readed = readed + 1 "
				+"WHERE seq = ? ";
		this.pstmt=this.conn.prepareStatement(sql);
		this.pstmt.setInt(1, seq);
		int rowCount=this.pstmt.executeUpdate();
		return rowCount;
	}

	//try catch문 삭제
	@Override
	public BoardDTO view(int seq) throws SQLException {
		String sql = "select seq,title,writer,email,writedate,readed,content "
				+ "from tbl_cstvsboard "
				+ "WHERE seq = ? ";

		this.vo = null;

		int readed;
		String title, writer, email,content;
		Date writedate;



		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, seq);
		rs = pstmt.executeQuery();

		if (rs.next()) {

			seq = rs.getInt("seq");
			readed = rs.getInt("readed");

			title = rs.getString("title");
			writer = rs.getString("writer");
			email = rs.getString("email");

			writedate = rs.getDate("writedate");
			content = rs.getString("content");

			vo = new BoardDTO().builder()
					.seq(seq)
					.readed(readed)
					.title(title)
					.writer(writer)
					.email(email)
					.writedate(writedate)
					.content(content)
					.build();			

		}

		if(rs!=null)rs.close();
		if(pstmt!=null)pstmt.close();

		try {
			rs.close();
			pstmt.close();
		} catch (SQLException e) { 
			e.printStackTrace();
		}

		return this.vo;
	}


	@Override
	public int delete(int seq) throws SQLException {
		String sql = "DELETE FROM tbl_cstvsboard "
				+ "WHERE seq = ? ";

		int rowCount=0;

		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, seq);
		rowCount = pstmt.executeUpdate();

		if(pstmt!=null)pstmt.close();			
		if (rowCount == 0) {
			throw new SQLException("삭제할 데이터가 존재하지 않습니다. seq=" + seq);
		}
		return rowCount;
	}


	@Override
	public int update(BoardDTO dto) throws SQLException {
		String sql="UPDATE tbl_cstvsboard "
				+ "SET email = ?, title = ?, content = ? "
				+ "WHERE seq = ? ";
		int rowCount=0;

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, dto.getEmail());
		pstmt.setString(2, dto.getTitle());
		pstmt.setString(3, dto.getContent());
		pstmt.setInt(4, dto.getSeq());

		rowCount = pstmt.executeUpdate();
		if(pstmt!=null)pstmt.close();	
		return rowCount;
	}
	@Override
	public List<BoardDTO> search(String Condition, String Word) {
		String sql = "SELECT seq, title, writer, email, writedate, readed, content "
				+ "FROM tbl_cstvsboard "
				+ "WHERE ";

		// 조건별 SQL 조합
		if (Condition.equalsIgnoreCase("t")) {
			sql += " REGEXP_LIKE(title, ?, 'i')";
		} else if (Condition.equalsIgnoreCase("c")) {
			sql += " REGEXP_LIKE(content, ?, 'i')";
		} else if (Condition.equalsIgnoreCase("w")) {
			sql += " REGEXP_LIKE(writer, ?, 'i')";
		} else if (Condition.equalsIgnoreCase("tc")) {
			sql += " REGEXP_LIKE(title, ?, 'i') OR REGEXP_LIKE(content, ?, 'i')";
		}

		sql += " ORDER BY seq DESC";

		List<BoardDTO> list = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Condition);
			this.pstmt.setString(1, Word);
			if( Condition.equals("tc") ) this.pstmt.setString(2, Word); 


			rs = pstmt.executeQuery();

			if (rs.next()) {
				list = new ArrayList<>();
				do {
					BoardDTO vo = new BoardDTO().builder()
							.seq(rs.getInt("seq"))
							.title(rs.getString("title"))
							.writer(rs.getString("writer"))
							.email(rs.getString("email"))
							.writedate(rs.getDate("writedate"))
							.readed(rs.getInt("readed"))
							.content(rs.getString("content"))
							.build();

					list.add(vo);
				} while (rs.next());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

}

