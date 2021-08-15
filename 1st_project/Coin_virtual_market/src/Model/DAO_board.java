package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DAO_board {
	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;

	DTO_members dto = null;
	int cnt = 0;
	String sql = "";

	public void getConn() {
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String dbid = "hr";
			String dbpw = "hr";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, dbid, dbpw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			psmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int board_insert(DTO_board bo) {
		try {
			getConn();
			String sql = "insert into board(member_id,title,dates) values (?, ?,SYSDATE)";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, bo.getMember_id());
			psmt.setString(2, bo.getTitle());

			cnt = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	public int board_save(DTO_board bo) {
		try {
			getConn();
			String sql = "insert into board(member_id,title,content,dates) values (?,?,?,SYSDATE)";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, bo.getMember_id());
			psmt.setString(2, bo.getTitle());
			psmt.setString(3, bo.getContents());

			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	public ArrayList<DTO_board> board_select() {
		ArrayList<DTO_board> arr = new ArrayList<DTO_board>();
		try {

			getConn();
			String sql = "select * from board";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String title = rs.getString(2);
				String dates = rs.getString(4);
				DTO_board board = new DTO_board(id, title, dates);
				arr.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}

	public DTO_board board_select(String title) {
		DTO_board board = null;
		try {

			getConn();
			String sql = "select * from board where title = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			rs = psmt.executeQuery();
			while (rs.next()) {
				String writer = rs.getString(1);
				String contents = rs.getString(3);
				board = new DTO_board(writer, title, contents, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return board;
	}
}
