package Model;

public class DTO_sales_details {
	private int trade_number;
	private String member_id;
	private String coin_id;
	private Double coin_count;
	private int trade_price;
	private String trade_date;
	
	public DTO_sales_details(String member_id) {
	
		this.member_id = member_id;
		
	}
	public DTO_sales_details(String coin_id, int trade_price) {

		this.coin_id = coin_id;
		this.trade_price = trade_price;
	}
	public int getTrade_number() {
		return trade_number;
	}
	public void setTrade_number(int trade_number) {
		this.trade_number = trade_number;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getCoin_id() {
		return coin_id;
	}
	public void setCoin_id(String coin_id) {
		this.coin_id = coin_id;
	}
	public double getCoin_count() {
		return coin_count;
	}
	public void setCoin_count(double coin_count) {
		this.coin_count = coin_count;
	}
	public int getTrade_price() {
		return trade_price;
	}
	public void setTrade_price(int trade_price) {
		this.trade_price = trade_price;
	}
	public String getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
	}
	
}
