package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO_coin {

	Connection conn = null;
	PreparedStatement psmt = null;
	ResultSet rs;
	int cnt = 0;
	String sql = "";
	DTO_members member_info;
	DTO_coins coin_info;
	Crawling craw = new Crawling();

	// txt_id, txt_pw를 받아와 db와 일치할 시 로그인, 아니면 다시 입력

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int DAO_login(String txt_id, String txt_pw) { // 로그인 시도
		int check = 0;
		getConn();

		try {
			sql = "select member_id, member_pw from members where member_id = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, txt_id);
			rs = psmt.executeQuery();

			while (rs.next()) {
				String members_id = rs.getString(1);
				String members_pw = rs.getString(2);
				if (members_id == null) {
					check = 0;
				} else if (members_id.equals(txt_id) && members_pw.equals(txt_pw)) {
					check = 1;
				} else if (members_id.equals(txt_id) && !members_pw.equals(txt_pw)) {
					check = 2;
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return check;

	}

	public boolean DAO_check(String member_id) { // ID 중복체크
		boolean check = false;

		try {

			getConn();

			sql = "select member_id from members where member_id = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member_id);
			rs = psmt.executeQuery();

			if (rs.next()) {
				check = false;
			} else {
				check = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return check;
	}

	public int DAO_insert(String member_id, String member_pw, String member_name) { // 회원가입

		int cnt = 0;

		try {

			getConn();

			sql = "insert into members values(?, ?, ?, 1000000)";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, member_id);
			psmt.setString(2, member_pw);
			psmt.setString(3, member_name);

			cnt = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return cnt;

	}

	// coins테이블에서 정보를 전부 가져온다
	public DTO_coins DAO_selectCoins(String coin_id) {

		try {
			getConn();
			sql = "select * from coins where coin_id = ?";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, coin_id);
			rs = psmt.executeQuery();

			while (rs.next()) {
				String coin_name = rs.getString(2);
				int coin_price = rs.getInt(3);
				double coin_rate = rs.getInt(4);

				coin_info = new DTO_coins(coin_id, coin_name, coin_price, coin_rate);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return coin_info;
	}
	
	public DTO_members getMemberInfo(String member_id) {
		
		try {
			getConn();
			sql = "select member_name, money from members";
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			rs.next();
			member_info = new DTO_members(member_id, rs.getString(1), rs.getInt(2));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return member_info;
	}
	
	public ArrayList<DTO_variation_history> getHistory(String coin_id) {
		getConn();

		ArrayList<DTO_variation_history> arr_dto = new ArrayList<DTO_variation_history>();
		try {

			sql = "select variation_price, variation_rate from variation_history where coin_id = ? AND rownum < 50";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, coin_id);

			rs = psmt.executeQuery();

			while (rs.next()) {
				int variation_price = rs.getInt(1);
				double variation_rate = rs.getDouble(2);
				arr_dto.add(new DTO_variation_history(variation_price, variation_rate));
			} // DTO를 통해 묶어줄 필요가 있다.
				// 이를 묶어줄 데이터타입 하나를 만들어준다고 생각하면 편하다.
				// DTO or VO 둘다 동일하다. 요즘은 VO라고 더 많이 쓴다.
				// VO는 DTO에 추가적으로 처리가 더 들어간것... 은닉이라던지... 이런거

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return arr_dto;

	}
	
	
	
	
	
	
	public ArrayList<DTO_coinInfo> setCoinInfo(){
		ArrayList<DTO_coinInfo> arr = new ArrayList<DTO_coinInfo>();
//		String[] coin_arr = { "BTC", "ETH", "LTC", "ETC", "XRP", "BCH", "EOS", "ARW", "MKR", "AAVE" };
		int i = 0;
		arr.add(new DTO_coinInfo(craw.coin_arr[i++], "코인 소개\r\n"
				+ "비트코인은 최초로 구현된 디지털 자산입니다. 발행 및 유통을 관리하는 중앙권력이나 중간상인 없이, P2P 네트워크 기술을 이용하여 네트워크에 참여하는 사용자들이 주체적으로 화폐를 발행하고 이체내용을 공동으로 관리합니다. 이를 가능하게 한 블록체인 기술을 처음으로 코인에 도입한 것이 바로 비트코인입니다.\r\n"
				+ "\r\n"
				+ "비트코인을 사용하는 개인과 사업자의 수는 꾸준히 증가하고 있으며, 여기에는 식당, 아파트, 법률사무소, 온라인 서비스를 비롯한 소매상들이 포함됩니다. 비트코인은 새로운 사회 현상이지만 아주 빠르게 성장하고 있습니다. 이를 바탕으로 가치 증대는 물론, 매일 수백만 달러의 비트코인이 교환되고 있습니다.\r\n"
				+ "\r\n"
				+ "비트코인은 디지털 자산 시장에서 현재 유통시가총액과 코인의 가치가 가장 크고, 거래량 또한 안정적입니다. 이더리움이 빠르게 추격하고 있지만 아직은 가장 견고한 디지털 자산 이라고 볼 수 있습니다.\r\n"
				+ "\r\n"
				+ "코인 특징\r\n"
				+ "1. 중앙주체 없이 사용자들에 의해 거래내용이 관리될 수 있는 비트코인의 운영 시스템은 블록체인 기술에서 기인합니다. 블록체인은 쉽게 말해 다 같이 장부를 공유하고, 항상 서로의 컴퓨터에 있는 장부 파일을 비교함으로써 같은 내용만 인정하는 방식으로 운영됩니다. 따라서 전통적인 금융기관에서 장부에 대한 접근을 튼튼하게 방어하던 것과는 정반대의 작업을 통해 보안을 달성합니다. 장부를 해킹하려면 51%의 장부를 동시에 조작해야 하는데, 이는 사실상 불가능합니다. 왜냐하면, 이를 실행하기 위해서는 컴퓨팅 파워가 어마어마하게 소요되고, 이것이 가능한 슈퍼컴퓨터는 세상에 존재하지 않기 때문입니다. 또한, 장부의 자료들은 줄글로 기록되는 것이 아니라 암호화 해시 함수형태로 블록에 저장되고, 이 블록들은 서로 연결되어 있어서 더 강력한 보안을 제공합니다.\r\n"
				+ "\r\n"
				+ "2. 비트코인은 블록발행보상을 채굴자에게 지급하는 방식으로 신규 코인을 발행합니다. 블록발행보상은 매 21만 블록(약 4년)을 기준으로 발행량이 절반으로 줄어듭니다. 처음에는 50비트코인씩 발행이 되었고, 4년마다 계속 반으로 감소하고 있습니다. 코인의 총량이 2,100만 개에 도달하면 신규 발행은 종료되고, 이후에는 거래 수수료만을 통해 시스템이 지탱될 것입니다.\r\n"
				+ "\r\n"
				+ "핵심 가치\r\n"
				+ "(키워드: 통화로 사용될 수 있는 보편성 및 편의성)\r\n"
				+ "\r\n"
				+ "1. 다양한 알트코인들의 등장에 앞서 비트코인은 디지털 자산 시장에서 독보적이었기 때문에, 현재 가장 보편적인 결제수단으로 사용됩니다. 실생활에서 이를 활용할 수 있는 가맹점이 알트코인들보다 압도적으로 많을 뿐만 아니라, 이 또한 증가하고 있습니다. 일례로 일본 업체들이 비트코인 결제 시스템을 도입하면서 곧 비트코인을 오프라인 점포 26만 곳에서 이용할 수 있게 될 것입니다.\r\n"
				+ "\r\n"
				+ "2. 여러 나라에서 비트코인을 정식 결제 수단으로 인정하면서, 실물화폐와 디지털 자산을 거래할 때 더는 부가가치세가 부과되지 않게 된다고 합니다. 실제로 일본과 호주에서는 이미 비트코인을 합법적 결제 수단으로 인정하면서 제도권 안으로 들여오고 있고, 미국에서는 비트코인 ETF 승인 노력도 진행되고 있습니다. 각국에 비트코인을 기반으로 한 ATM 기계도 설치되었다고 합니다."));
		arr.add(new DTO_coinInfo(craw.coin_arr[i++], "코인 소개\r\n"
				+ "이더리움은 블록체인에 기초해 다양한 앱 개발을 지원하는 플랫폼이며, 여기서 사용되는 디지털 자산 또한 같은 이름으로 통용됩니다.\r\n"
				+ "\r\n"
				+ "1. 플랫폼: 이더리움은 블록체인을 활용한 모든 것을 프로그래밍할 수 있는 플랫폼입니다. 거래 기록뿐만 아니라 탈중앙화 앱(DAPP)들을 투명하게 운영할 수 있는 확장성을 제공합니다. 플랫폼은 프로그램된 계약을 정확히 실행하도록 설계된 스마트 컨트랙트를 기반으로 운영됩니다.\r\n"
				+ "\r\n"
				+ "2. 화폐: 이더리움 플랫폼에서 이용되는 디지털 자산으로, DAPP을 만드는 개발자들과 스마트 컨트랙트를 이용하고자 하는 사용자들이 필요로 합니다.\r\n"
				+ "\r\n"
				+ "현재 디지털 자산 시가총액 기준, 비트코인 다음으로 독보적인 위치에 있습니다. 디지털 분야에서 가장 혁명적인 기술 중 하나로 평가받는 분위기이며, 실제 '월드테크놀로지(The WTN) IT S/W' 부문에서 경쟁작인 마크 저커버그의 페이스북을 제치고 수상을 한 바 있습니다.\r\n"
				+ "\r\n"
				+ "코인 특징\r\n"
				+ "1. 이더리움에서는 다양한 애플리케이션들을 구동시킬 수 있는데, 이런 DAPP들은 금융, 공유경제, SNS, 그리고 탈중앙화 자율 조직(DAO)까지 확장될 수 있습니다. 대표적으로 분산화된 예측시장을 제공하는 노시스(GNO)와 어거(AUG), 분산화된 크라우드펀딩 플랫폼인 웨이펀드(WeiFund), 사물인터넷과 스마트 자산을 위한 액세스 프로토콜인 에어로크(Airlock) 등이 있습니다.\r\n"
				+ "\r\n"
				+ "2. 비트코인의 제한된 명령어와 달리 다양한 수준의 응용을 지원하기 위해 Python과 유사한 구조의 Serpent, C++과 유사한 Solidity, Lisp과 유사한 LLL 등의 튜링 완전언어를 통해 스마트 컨트랙트 코딩을 지원합니다. 사용자들은 이와 같은 프로그래밍 언어를 사용해 컨트랙트와 DAPP를 만들 수 있습니다.\r\n"
				+ "\r\n"
				+ "3. 발행 한도는 존재하지 않지만, 1년에 발행할 수 있는 코인은 1,800만 개로 제한되어 있습니다. 코인의 발행은 일정하지 않은데, 이는 인플레이션을 고려해 채굴 난이도 알고리즘에 변화를 주기 때문입니다. 이는 블록 주기를 조정하는 역할을 하며, 기본적으로 정해져 있는 알고리즘 공식과 상호작용해 발행량을 유동적으로 조절합니다. (기본적인 인플레이션 공식은 블록마다 5이더가 발행되고, 엉클에게도 일부 신규 코인이 추가 발행된다는 것입니다.)\r\n"
				+ "\r\n"
				+ "활용 분야\r\n"
				+ "(키워드: 스마트 컨트랙트, DAPP을 통한 이더리움 플랫폼의 확장성)\r\n"
				+ "\r\n"
				+ "1. 스마트 컨트랙트: 컴퓨터 언어인 실행 코드로 작성되기 때문에, 특정 조건이 달성되면 자동으로 프로그램이 실행되어 계약이 이행됩니다. 강제로 계약이 이행되므로 상대를 신뢰할 수 없는 경우에서 강력한 힘을 발휘할 수 있습니다. 따라서 시스템 오류, 사기, 제3자의 간섭 가능성을 제거할 수 있습니다.\r\n"
				+ "\r\n"
				+ "2. DAPP을 통한 이더리움 플랫폼의 확장성: 이더리움의 경우, 화폐 자체보다 네트워크 플랫폼이 더 큰 의미가 있습니다. 분산화된 DAPP과 스마트 컨트랙트, 그리고 사물 인터넷(IoT)을 접목한 기술은 향후 인간의 개입을 완전히 배제한 기계 간 금융거래도 까지도 가능하게 할 것이라고 합니다. 100개가 넘는 코인들이 이더리움 플랫폼 위에서 운영되고 있는 것으로 볼 때, 이것의 확장성을 짐작할 수 있습니다.핵심 가치\r\n"
				+ "(키워드: 스마트 컨트랙트, DAPP을 통한 플랫폼의 확장성)\r\n"
				+ "\r\n"
				+ "이더리움 클래식은 포크되지 않은 기존의 블록체인을 유지한 것이기 때문에 사용하는 프로토콜이나 기술들은 이더리움과 동일합니다. 따라서 이더리움 플랫폼의 핵심인 스마트 컨트랙트와 플랫폼 위에 탈중앙화 앱(DAPP)을 개발할 수 있는 기술은 이더리움 클래식 플랫폼에서도 똑같이 적용됩니다. 현재 이더리움 클래식 플랫폼 위에서 운영되는 코인은 Corion platform(COR), ETCWin(ETC), GeoFunders(GUNS) 등이 있습니다."));
		arr.add(new DTO_coinInfo(craw.coin_arr[i++], "코인 소개\r\n"
				+ "비트코인이 금이라면, 자신들은 은이라고 표현하는 라이트 코인은 비트코인에 기초해 만들어진 디지털 자산으로, 기술적인 면에서 비트코인과 거의 동일한 알고리즘을 사용했기 때문에 사실상 비트코인의 파생 화폐라고 볼 수 있습니다. 그러나 비트코인의 단점을 보완하고 좀 더 나은 디지털 자산이 되는 것이 라이트 코인의 목표라고 합니다. 일례로, 비트코인에 앞서 세그윗을 2017년에 이미 실행했을 뿐만 아니라, 이를 기반으로 라이트닝 네트워크도 개발 중입니다.\r\n"
				+ "\r\n"
				+ "코인 특징\r\n"
				+ "라이트 코인은 비트코인과 비슷하지만, 차별화되는 점들이 크게 세 가지가 있습니다.\r\n"
				+ "\r\n"
				+ "1. 세그윗이 아직 진행 중인 비트코인과 달리 라이트 코인은 2017년 5월 이를 성공적으로 실행했습니다. 세그윗이란 프로토콜의 서명 부분을 개선함으로써, 블록 크기를 증가시키지 않아도 4MB로 확장된 것과 같은 효과를 내는 기술입니다. 라이트코인은 거래량이 늘어나면 구조적으로 제기될 수밖에 없는 스케일링 문제가 제기되기도 전에 이미 해결한 상태입니다. 또한, 신기술들의 개발을 촉진할 수 있다는 점에서 세그윗의 빠른 실행은 긍정적인 평가를 받고 있습니다.\r\n"
				+ "\r\n"
				+ "2. 코인 생성 주기를 1/4로 줄여 거래 속도가 빨라졌고, 통화 발행량도 4배만큼 늘렸습니다.\r\n"
				+ "\r\n"
				+ "3. 암호화 해시 함수도 다른 것을 이용합니다. 비트코인은 SHA-256 해싱 알고리즘을 사용하는데, 이는 ASIC이라는 채굴 장비를 적용할 수 있어 난이도가 상승했고, 채굴의 진입장벽이 높아졌습니다. 라이트 코인은 이렇게 채굴파워가 집중되는 것을 막기 위해 ASIC 저항성이 있는 스크립트(Scrypt)라는 알고리즘을 사용합니다. 처음에는 일반인도 채굴할 수 있다는 본래의 취지를 실현할 수 있을 것 같았지만, 이후 스크립트 전용의 ASIC 장비가 등장하면서 본래의 목적을 달성하지 못했습니다.\r\n"
				+ "\r\n"
				+ "핵심 가치\r\n"
				+ "(키워드: 거래속도)\r\n"
				+ "\r\n"
				+ "코인 생성 주기를 10분에서 2.5분으로 줄여서, 비트코인 보다 약 4배 정도 빠른 거래가 이뤄집니다. 더 나아가, 세그윗을 바탕으로 실행될 수 있는 라이트닝 네트워크를 개발하고 있는데, 이것이 완성되어 도입된다면 현재보다 100만 배 정도 거래 속도를 올릴 수 있다고 합니다."));
		arr.add(new DTO_coinInfo(craw.coin_arr[i++], "코인 소개\r\n"
				+ "이더리움클래식은 이더리움의 하드포크로 인해 생성된 다른 블록체인에 기초한 디지털 자산입니다. 하드포크란 인위적으로 블록체인을 분절하는 작업을 의미하는데, 이더리움클래식의 하드포크는 2016년 7월 20일에 단행되었습니다.\r\n"
				+ "\r\n"
				+ "이는 2016년 6월 어떤 사람 혹은 사람들이 이더리움 플랫폼에 기반을 둔 주요 프로젝트 중 하나인 다오(DAO) 시스템상의 코드 오류를 이용해 부당이득을 취한 사건에서 시작합니다. 이더리움 개발팀은 잘못된 거래기록을 무효화시켜 투자자들에게 돈을 다시 돌려주기 위해 체인을 오류 이전 상태로 되돌려 새로운 체인을 만들어 나가기로 했습니다.\r\n"
				+ "\r\n"
				+ "그러면 기존에 해킹당한 체인은 소멸해야 하는데, 하드포크에 반대하던 약 10%의 사람들이 업데이트하지 않고 잔류하며 블록을 생성했고 이것이 이더리움 클래식의 블록체인이 된 것입니다. 즉, 도난된 이더리움이 포함된 원래의 블록체인이 이더리움 클래식이라는 다른 블록체인이자 코인으로 정착한 것입니다.\r\n"
				+ "\r\n"
				+ "코인 특징\r\n"
				+ "1. \"코드는 법이다(Code is law)\"라는 신념 하에 블록체인의 중심가치인 \"불변성\"을 최우선으로 중시합니다. 불변성이란 유효한 거래라면 지워지거나 잊히면 안 된다는 것을 의미하고, 이들이 이더리움 DAO 사태 이후 하드포크 하지 않고 기존 체인에 잔류한 이유 중 가장 큰 부분을 차지합니다.\r\n"
				+ "\r\n"
				+ "2. 이더리움 클래식은 이더리움의 기존 인플레이션 정책(무제한 발행)에서 비트코인과 같은 고정 공급방식으로 통화발행정책을 변경했습니다. 그래서 최대 발행량이 2.3억 개로 제한되어있고, 총 공급은 약 2.1억 개가 될 것으로 예상합니다. 채굴 보상은 500만 블록마다 20%씩 감소합니다."));
		arr.add(new DTO_coinInfo(craw.coin_arr[i++], "코인 소개\r\n"
				+ "리플코인은 금융 거래를 위한 인터넷 프로토콜인 ‘리플 프로토콜’에서 사용되는 디지털 자산이자 기초 화폐입니다. 이 코인을 매개로 전 세계의 화폐를 몇 초 내로 송금할 수 있어 혁신적인 실시간 통화 거래가 가능합니다. '리플 프로토콜'이란 분산원장을 기반으로 한 ‘실시간 총액결제 시스템(RTS)’으로 국제결제시스템망(SWIFT)을 대체할 새로운 대안으로 주목받고 있습니다.\r\n"
				+ "\r\n"
				+ "송금 시스템에 가까운 특성이 있어 일반적인 디지털 자산과는 그 구조가 다릅니다. 사용하는 합의 프로토콜도 독자적일 뿐만 아니라, 다른 코인들과 다르게 현실적으로 주도권의 집중화가 있기 때문입니다. 따라서 리플의 시스템은 한정된 참여자만 분산 네트워크를 구성한다는 의미에서 프라이빗 블록체인(private blockchain)이라고 불리기도 합니다.\r\n"
				+ "\r\n"
				+ "리플사는 총량의 62%의 코인을 보유하고 있으며, 사업 개발 단계에 따라 전략적으로 시장에 유통하고 있습니다. 리플사는 그들이 전체 유통의 반수 이상으로 보유하고 있는 코인을 악용할 우려를 잠재우기 위해 암호화를 통해 보호된 조건부 날인 계정(escrow)에 리플을 넣어둔다고 합니다.\r\n"
				+ "\r\n"
				+ "코인 특징\r\n"
				+ "1. 현재 주로 은행 간 이체서비스에 집중하여 진행 중이며, 리플 네트워크에는 미쓰비시 도쿄UFJ 은행, 스웨덴 SEB, 중동의 아부다비 국립은행과 인도 Axis 은행을 비롯한 세계 주요 금융권 75개가 참여하고 있습니다. 매년 빠른 속도로 확장하고 있으며, 최근 이들의 적극적인 참여로 상호 송금 거래 규모는 하루 7700만 달러를 넘어섰습니다.\r\n"
				+ "\r\n"
				+ "2. 리플은 연결 통화(bridge currency)로 역할 하므로 기존 통화시장과 달리 수요가 적은 희귀통화를 거래하기 쉽습니다. 복잡한 거래 과정이 불필요하고, 추가적인 비용도 지출되지 않기 때문입니다.\r\n"
				+ "\r\n"
				+ "3. 거래마다 0.0004달러의 수수료를 지급합니다. 이는 네트워크 스팸 공격을 막기 위한 것이며, 매우 적은 액수이므로 기존의 결제시스템보다 비교우위에 있습니다. 또한, 이 수수료는 영원히 소멸하게 되는데, 이는 시간이 지날수록 토큰의 가치가 조금씩 높아짐을 의미합니다.\r\n"
				+ "\r\n"
				+ "4. 인플레이션이 없습니다. 시간이 지날수록 화폐 총량이 늘어나는 대부분 디지털자산과 달리, 리플은 초기에 총 1천억 개 발행되었고 이제 더 추가로 생성되지 않기 때문입니다.\r\n"
				+ "\r\n"
				+ "핵심 가치\r\n"
				+ "(키워드: 환전/송금 거래에 P2P 네트워크의 적용)\r\n"
				+ "\r\n"
				+ "P2P 네트워크는 서로 모르는 사람 혹은 은행 간의 외환 거래를 중개하는 다리를 만들어 줍니다. 따라서 SWIFT와 같은 중개기관이 필요하지 않아 저렴한 비용으로 신속한 국제 결제 서비스를 가능하게 합니다.\r\n"
				+ "\r\n"
				+ "1. 결제 비용 감소: 중개기관을 거치지 않으므로 수수료를 절약 가능합니다. 특히 환전 시 달러 거래와 달리 은행 계좌를 요구하지 않기 때문에 서비스 수수료나 추가 운용 비용이 발생하지 않습니다. 따라서 리플은 기존 환율보다 훨씬 더 경쟁력 있는 외환 가격을 제공합니다.\r\n"
				+ "\r\n"
				+ "2. 실시간 결제: 전 세계 어디든 거래당 4초가 걸리며 즉각적인 정산이 가능합니다. XRP는 연중무휴로 초당 50,000건의 거래를 처리합니다."));
		arr.add(new DTO_coinInfo(craw.coin_arr[i++], "코인 소개\r\n"
				+ "비트코인캐시는 비트코인 블록체인에서 하드포크되어 나온 비트코인캐시 블록체인에서 사용되는 디지털 자산입니다. 하드포크란 인위적으로 블록체인을 분절하는 작업을 의미하며, 비트코인캐시의 하드포크는 2017년 8월 1일에 시행되었습니다. 비트코인을 보유하고 있던 사용자들은 같은 양의 비트코인캐시를 추가로 받았으며, 이 방식으로 기존 비트코인의 채굴량만큼 배포되었습니다.\r\n"
				+ "\r\n"
				+ "하드포크에 대한 논의는 1MB로 제한되어 있던 비트코인의 블록 크기에서 시작되었습니다. 비트코인은 단기간에 급격히 성장하며 거래가 많이 늘었는데, 블록 크기는 그대로여서 높은 수수료를 내지 않으면 거래 승인이 지연되는 문제가 발생했습니다. 이에 대해 세그윗과 블록 크기 증대라는 두 가지 해결방안이 고안되었고, 비트코인 네트워크는 세그윗에 합의했지만 이에 반대하던 세력들이 존재했습니다. viaBTC과 비트메인을 주로 하는 블록 크기 증대 지지자들은 세그윗을 실행하는 비트코인에서 하드포크했으며, 분리된 블록체인을 비트코인캐시로 부르게 되었습니다. 현재 비트코인과 비트코인캐시는 별개의 블록체인으로 운영되고 있습니다.\r\n"
				+ "\r\n"
				+ "코인 특징\r\n"
				+ "1. 비트코인캐시 블록체인은 블록 크기를 2MB부터 8MB까지 유동적으로 확장할 수 있는 Bitcoin ABC 기술을 적용했습니다. 기존 1MB의 블록 크기를 증가시킴으로써, 한 블록에 들어가는 거래의 수량을 늘릴 수 있게 되었습니다. 따라서 기존 비트코인보다 더 낮은 수수료를 지급해도 되고, 더 빨리 컨펌을 받을 수 있게 됩니다. 이 방식은 세그윗과 다른 접근으로 비트코인 스케일링 문제를 해결합니다.\r\n"
				+ "\r\n"
				+ "* 세그윗이란 프로토콜의 서명 부분을 개선함으로써, 블록 크기를 증가시키지 않아도 4MB로 확장된 것과 같은 효과를 내는 스케일링 방법입니다.\r\n"
				+ "\r\n"
				+ "2. 2016개의 블록마다 난이도를 조정하는 비트코인과 달리, 비트코인캐시는 6개의 블록마다 난이도를 조정합니다. 하드포크될 당시 비트코인의 높은 난이도를 그대로 가져왔는데, 비트코인과 달리 해쉬파워가 많지 않아 블록 생성에 긴 시간이 소요되었습니다. 현재 이는 조정 중이고, 평균 10분을 맞추기 위해 노력 중이라고 합니다."));
		arr.add(new DTO_coinInfo(craw.coin_arr[i++], "코인 소개\r\n"
				+ "이오스는 탈중앙화 애플리케이션의 수직/수평 확장이 가능하도록 디자인된 블록체인 플랫폼입니다. 수백 개의 CPU 코어 또는 클러스터를 통해 계정(accounts), 인증(authentication), 데이터베이스(databases), 비동기 통신(asynchronous communication), 애플리케이션의 스케쥴링(application scheduling)을 제공합니다. 이에 따라 초당 수백만 건의 트랜잭션 처리 능력을 갖추면서도, 수수료가 없고, 빠르고 쉽게 애플리케이션을 개발할 수 있는 블록체인 아키텍처 기술이 탄생했습니다.\r\n"
				+ "\r\n"
				+ "이오스는 Delegated Proof of Stake (위임지분증명) 방식을 채택합니다. DPOS 시스템에서는 토큰 보유자가 상시 운영되는 투표 시스템을 통해 블록 생산자(block producer)를 선출할 수 있습니다. 또한 누구나 블록 생산자로 참여할 수 있으며, 블록을 생산할 기회는 모든 다른 생산자들이 받은 전체 투표 중 본인이 받은 투표 비율에 따라 결정됩니다. 대표자들은 거래를 입증하고 합의를 이끌어내며 시스템을 통한 그들의 노력에 대해 보상을 받게 됩니다.\r\n"
				+ "\r\n"
				+ "이오스는 빠른 트랜잭션이 가능하고, 사용자는 수수료를 지불하지 않아도 되는 장점이 있습니다. 가령 일부 플랫폼 코인의 경우 개발된 서비스를 이용할 때마다(트렌젝션 발생 시마다) 수수료를 지불해야 하지만, 이오스 기반은 트랜잭션 자체에 비용을 내지는 않아도 됩니다. 대신 dapp 제공자가 EOS를 보유해야만 트랜잭션이 발생시킬 수 있다는 점에서 차이가 있습니다.\r\n"
				+ "\r\n"
				+ "이오스는 지난 2018년 6월 14일 공식 메인넷 런칭이 완료되면서 독자적인 플랫폼을 구축하는 데 성공했습니다."));
		arr.add(new DTO_coinInfo(craw.coin_arr[i++], "정보 없음"));
		arr.add(new DTO_coinInfo(craw.coin_arr[i++], "정보 없음"));
		arr.add(new DTO_coinInfo(craw.coin_arr[i++], "정보 없음"));
		
		return arr;
	}
	

}
