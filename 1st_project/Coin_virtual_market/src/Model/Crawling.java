package Model;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawling {
	String[] columns = { "코인 ID", "코인 이름", "현재 가격", "전일대비 증감율" };
	Object[][] coin_data;
	String[] coin_arr = { "BTC", "ETH", "LTC", "ETC", "XRP", "BCH", "EOS", "ARW", "MKR", "AAVE" };
	String[] coin_name = { "비트코인", "이더리움", "라이트코인", "이더리움 클래식", "리플", "비트코인캐시", "이오스", "아로와나토큰", "메이커", "에이브" };
	Object[][] arr;
	// BTC (비트코인) ETH(이더리움) LTC(라이트코인)
	// ETC(이더리움 클래식) XRP(리플) BCH(비트코인캐시) EOS(이오스)
	// ARW (아로와나토큰), WET(위쇼토큰), MIX(믹스마블)
	// 다 잘되는거 확인
	private String news_url = "https://search.naver.com/search.naver?where=news&sm=tab_pge&query=%EC%BD%94%EC%9D%B8&sort=0&photo=0&field=0&pd=0&ds=&de=&cluster_rank=71&mynews=0&office_type=0&office_section_code=0&news_office_checked=&nso=so:r,p:all,a:all&start=";
	private String url = "https://www.bithumb.com";
	private DAO_update update = new DAO_update();

	public Object[][] crawler() {

		arr = new Object[coin_arr.length][columns.length];
		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < coin_arr.length; i++) {
			arr[i][0] = coin_arr[i];
			arr[i][1] = coin_name[i];

			Element eprice = doc.selectFirst("strong#assetReal" + coin_arr[i] + "_KRW");
			String sprice = eprice.text().replace(",", "").replace(" 원", "");
			arr[i][2] = Integer.parseInt(sprice);

			Element erate = doc.selectFirst("strong#assetRealRate" + coin_arr[i] + "_KRW");
			String srate = erate.text().replace("%", "");
			arr[i][3] = Double.parseDouble(srate);

			update.DAO_updateC(coin_arr[i], Integer.parseInt(sprice), Double.parseDouble(srate));
			update.DAO_insert(coin_arr[i], Integer.parseInt(sprice), Double.parseDouble(srate));

		}

//		String elementk = element2.text();
//		elementk = elementk.replace(",", ""); 
//		elementk = elementk.replace(" 원", ""); // replace로 원, %, 콤마 같은거 다 지우면 되겠다.
////		Integer.parseInt(money);
//		System.out.println(Integer.parseInt(elementk)/100); // -도 잘 변환된다.

		return arr;
	}

	public void insertCrawler() {

		arr = new Object[coin_arr.length][columns.length];
		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < coin_arr.length; i++) {

			Element eprice = doc.selectFirst("strong#assetReal" + coin_arr[i] + "_KRW");
			String sprice = eprice.text().replace(",", "").replace(" 원", "");

			Element erate = doc.selectFirst("strong#assetRealRate" + coin_arr[i] + "_KRW");
			String srate = erate.text().replace("%", "");

			update.DAO_insert(coin_arr[i], coin_name[i], Integer.parseInt(sprice), Double.parseDouble(srate));

		}
	}

	public ArrayList<DTO_news> crawlingNews() {
		ArrayList<DTO_news> arrNews = new ArrayList<DTO_news>();

		Document docNews = null;
		for (int i = 0; i < 102; i += 10) {
			try {
				docNews = Jsoup.connect(news_url + i).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Elements newsUrl = docNews.select("a.news_tit"); 
			for(int j = 0; j < newsUrl.size(); j++){
				String newsName = newsUrl.get(j).text();
				String newsHyper = newsUrl.get(j).attr("abs:href") ;
				// <html> <body> <a href=""></a> </body> </html>
				
				arrNews.add(new DTO_news(newsHyper, newsName));
			}
			
		}
		return arrNews;
	}

	public String[] getColumns() {
		return columns;
	}

}
