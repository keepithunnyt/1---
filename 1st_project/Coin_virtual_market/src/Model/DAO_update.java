package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAO_update {

	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;

	DTO_sales_details dto_sales_details = null;
	DTO_coins dto_coins = null;
	String sql = "";
	int cnt = 0;
	Object[][] arr;

	public void getConn() {
		try {
			String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
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

	// coin���̺� update�� DAO
	// ���ΰ�ħ ��ư ������ coin_DAO_update ����
	// dto_coin

	public void DAO_updateC(String coin_id, int coin_price, double coin_rate) {
		// table coin ������Ʈ DAO
		getConn();

		try {
			sql = "update coins set coin_price = ?, coin_rate = ? where coin_id = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, coin_price);
			psmt.setDouble(2, coin_rate);
			psmt.setString(3, coin_id);
			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	public void DAO_insert(String coin_id, int coin_price, double coin_rate) {
		/** ���� ���� ���ΰ�ħ �Ҷ����� �����صδ� variation_history ���̺� ���� �����ϴ� ��� */

		getConn();

		try {
			sql = "insert into variation_history values(?,?,sysdate, ?)";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, coin_id);
			psmt.setInt(2, coin_price);
			psmt.setDouble(3, coin_rate);
			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}
	
	public void DAO_insert(String coin_id, String coin_name,  int coin_price, double coin_rate) {
		/** �����ε�. �ʱ� ���ΰ��� insert���ִ� �ڵ� */

		getConn();

		try {
			sql = "insert into coins values(?,?,?, ?)";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, coin_id);
			psmt.setString(2, coin_name);
			psmt.setInt(3, coin_price);
			psmt.setDouble(4, coin_rate);
			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	}

}
