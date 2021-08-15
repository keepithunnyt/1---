package Model;

public class DTO_members {

	private String member_id;
	private String member_name;
	private String member_pw;
	private int money;
	public static String[] members = {"회원 ID", "회원 이름", "소지금"};
	
	public DTO_members(String member_id, String member_name, String member_pw, int money) {
		this.member_id = member_id;
		this.member_name = member_name;
		this.member_pw = member_pw;
		this.money = money;
	}
	
	public DTO_members(String member_id, String member_name, int money) {
		// 비밀번호 없이 생성할 수 있게 오버로딩
		this.member_id = member_id;
		this.member_name = member_name;
		this.money = money;
	}
	
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_pw() {
		return member_pw;
	}
	public void setMember_pw(String member_pw) {
		this.member_pw = member_pw;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}

}
