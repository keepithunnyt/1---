package Model;

public class DTO_coinInfo {
	
	private String coin_id;
	private String info;
	
	public DTO_coinInfo(String coin_id, String info) {
		this.coin_id = coin_id;
		this.info = info;
	}
	
	public String getCoin_id() {
		return coin_id;
	}
	public void setCoin_id(String coin_id) {
		this.coin_id = coin_id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	

}
