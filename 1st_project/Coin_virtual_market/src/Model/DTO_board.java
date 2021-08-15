package Model;

public class DTO_board {

	public String member_id;
	public String title;
	public String dates;
	public String contents;
	public int i = 0;
	
	// 테이블에 나오는 용도
	public DTO_board(String member_id, String title, String dates) {
		this.member_id = member_id;
		this.title = title;
		this.dates = dates;
	}
	
	public DTO_board(String member_id, String title, String contents, int i) {
		this.member_id = member_id;
		this.title = title;
		this.contents = contents;
	}
	
	
	// DB에 저장되고 가져올 수 있게 하는 용도
	public DTO_board(String member_id, String title, String dates, String contents) {
		this.member_id = member_id;
		this.title = title;
		this.dates = dates;
		this.contents = contents;
	}



	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}



	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDates() {
		return dates;
	}

	public void setDate(String dates) {
		this.dates = dates;
	}
	
}
