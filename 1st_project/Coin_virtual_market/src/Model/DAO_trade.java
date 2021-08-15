package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DAO_trade {

	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs;
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
			if(psmt != null) {
				psmt.close();
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 아직 확정 제대로 안됨
	public void buyAccount(DTO_members user, DTO_sales_details user_trade) {
		// 쓴 액수 만큼 member테이블에서 해당 id인 사람의 money차감 DAO
		
		getConn();
		
		try {

			sql = "update members set money = ? where member_id = ? ";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, user.getMoney() - (int)user_trade.getCoin_count()*user_trade.getTrade_price());
			psmt.setString(2, user.getMember_id());
			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	// 아직 확정 제대로 안됨
	public void sellAccount(DTO_members user, int sellPrice) {
		// 쓴 액수 만큼 member테이블에서 해당 id인 사람의 money차감 DAO
		getConn();
		
		try {

			sql = "update members set money = ? where member_id = ? ";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, user.getMoney() + sellPrice);
			psmt.setString(2, user.getMember_id());
			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	// 임시 구매내역 정보
	public void tradeRecord(DTO_members user, DTO_sales_details user_trade) {
		
		getConn();

		try {

			sql = "insert into sales_details values (SALES_TNUM_SEQ.nextval,?,?,?,?,sysdate)";
			psmt = conn.prepareStatement(sql);

			psmt.setString(1, user.getMember_id()); // 멤버 아이디
			psmt.setString(2, user_trade.getCoin_id()); // 코인 아이디
			psmt.setDouble(3, user_trade.getCoin_count()); // 코인 개수
			psmt.setInt(4, user_trade.getTrade_price()); // 매수 가격

			cnt = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

	}

	// 거래기록을 전부 꺼내올 DAO
	public ArrayList<DTO_sales_details> DAO_selectSalesDetails(DTO_members user) {

		ArrayList<DTO_sales_details> arr = new ArrayList<DTO_sales_details>();

		try {
			
			getConn();
			sql = "select coin_id ,AVG(trade_price) from sales_details where member_id = ? GROUP BY coin_id";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getMember_id());
			rs = psmt.executeQuery();

			while (rs.next()) {
				arr.add(new DTO_sales_details(rs.getString(1),rs.getInt(2)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr;
	}
	
	public int DAO_sellCoinNum(DTO_members user, String coin_id) {
		int result = 0;
		try {
			
			getConn();
			sql = "select coin_id ,SUM(coin_count) from sales_details where member_id = ? AND coin_id = ? GROUP BY coin_id";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, user.getMember_id());
			psmt.setString(2, coin_id);
			rs = psmt.executeQuery();

			while (rs.next()) {
				result = rs.getInt(2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	// 거래시 액수만큼 업데이트 시켜줄 DAO
	public int DAO_updateMoney(String member_id, int money) {
		
		getConn();
		
		try {

			sql = "update members set money = ? where member_id = ?";
			psmt.setInt(1, money);
			psmt.setString(2, member_id);

			cnt = psmt.executeUpdate();

		} catch (Exception e) {
		} finally {
			close();
		}
		return cnt;
	}

	// 구매내역 저장
	public int DAO_insert(String coin_id, int variation_price, String coin_date) {
		try {
			getConn();

			sql = "insert into VARIATION_HISTORY (coin_id, variation_price, coin_date) values(?,?,?)";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, coin_id);
			psmt.setInt(2, variation_price);
			psmt.setString(3, coin_date);

			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return cnt;

	}

	// 매매할때 변동 시켜줄 메소드
	public int DAO_update(int trade_number, String member_id, String coin_id, int coin_count, int trade_price,
			String trade_date) {

		try {
			getConn();

			sql = "update sales_details set trade_number = ? member_id = ? coin_id =? coin_count = ? trade_price = ? trade_date = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, trade_number);
			psmt.setString(2, member_id);
			psmt.setString(3, coin_id);
			psmt.setInt(4, coin_count);
			psmt.setInt(5, trade_price);
			psmt.setString(6, trade_date);

			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return cnt;
	}

	// sales_details테이블에서 id값이 같은 사람것의 구매내역을 가져온다
	public void DAO_deleteSales(String coin_id) {

		try {
			getConn();
			sql = "delete from sales_details where coin_id = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, coin_id);

			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
