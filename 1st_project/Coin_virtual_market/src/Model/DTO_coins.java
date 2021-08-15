package Model;

public class DTO_coins {
	private String coin_id;
	private String coin_name;
	private int coin_price;
	private double coin_rate;
	
	public DTO_coins(String coin_id, String coin_name, int coin_price, double coin_rate) {
		
		this.coin_id = coin_id;
		this.coin_name = coin_name;
		this.coin_price = coin_price;
		this.coin_rate = coin_rate;
	}
	public String getCoin_id() {
		return coin_id;
	}
	public void setCoin_id(String coin_id) {
		this.coin_id = coin_id;
	}
	public String getCoin_name() {
		return coin_name;
	}
	public void setCoin_name(String coin_name) {
		this.coin_name = coin_name;
	}
	public int getCoin_price() {
		return coin_price;
	}
	public void setCoin_price(int coin_price) {
		this.coin_price = coin_price;
	}
	public double getCoin_rate() {
		return coin_rate;
	}
	public void setCoin_rate(int coin_rate) {
		this.coin_rate = coin_rate;
	}

}
