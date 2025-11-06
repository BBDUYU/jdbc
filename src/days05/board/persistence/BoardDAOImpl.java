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

public class BoardDAOImpl implements BoardDAO{

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

}
