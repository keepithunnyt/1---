package Model;

public class DTO_members {

	private String member_id;
	private String member_name;
	private String member_pw;
	private int money;
	public static String[] members = {"ȸ�� ID", "ȸ�� �̸�", "������"};
	
	public DTO_members(String member_id, String member_name, String member_pw, int money) {
		this.member_id = member_id;
		this.member_name = member_name;
		this.member_pw = member_pw;
		this.money = money;
	}
	
	public DTO_members(String member_id, String member_name, int money) {
		// ��й�ȣ ���� ������ �� �ְ� �����ε�
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
