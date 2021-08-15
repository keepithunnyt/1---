package Model;

public class DTO_variation_history {
	private String coin_id;
	private int variation_price;
	private double coin_rate;
	private String coin_date;
	
	public DTO_variation_history(String coin_id, int variation_price, double coin_rate, String coin_date) {
		this.coin_id = coin_id;
		this.variation_price = variation_price;
		this.coin_rate = coin_rate;
		this.coin_date = coin_date;
	}
	
	public DTO_variation_history(int variation_price, double coin_rate) {
		this.variation_price = variation_price;
		this.coin_rate = coin_rate;
	}
	public String getCoin_id() {
		return coin_id;
	}
	public void setCoin_id(String coin_id) {
		this.coin_id = coin_id;
	}
	public int getVariation_price() {
		return variation_price;
	}
	public void setVariation_price(int variation_price) {
		this.variation_price = variation_price;
	}
	public String getCoin_date() {
		return coin_date;
	}
	public void setCoin_date(String coin_date) {
		this.coin_date = coin_date;
	}
	public double getCoin_rate() {
		return coin_rate;
	}
	public void setCoin_rate(double coin_rate) {
		this.coin_rate = coin_rate;
	}
	
}