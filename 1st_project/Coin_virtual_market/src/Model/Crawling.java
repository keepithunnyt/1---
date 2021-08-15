package Model;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawling {
	String[] columns = { "���� ID", "���� �̸�", "���� ����", "���ϴ�� ������" };
	Object[][] coin_data;
	String[] coin_arr = { "BTC", "ETH", "LTC", "ETC", "XRP", "BCH", "EOS", "ARW", "MKR", "AAVE" };
	String[] coin_name = { "��Ʈ����", "�̴�����", "����Ʈ����", "�̴����� Ŭ����", "����", "��Ʈ����ĳ��", "�̿���", "�Ʒοͳ���ū", "����Ŀ", "���̺�" };
	Object[][] arr;
	// BTC (��Ʈ����) ETH(�̴�����) LTC(����Ʈ����)
	// ETC(�̴����� Ŭ����) XRP(����) BCH(��Ʈ����ĳ��) EOS(�̿���)
	// ARW (�Ʒοͳ���ū), WET(������ū), MIX(�ͽ�����)
	// �� �ߵǴ°� Ȯ��
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
			String sprice = eprice.text().replace(",", "").replace(" ��", "");
			arr[i][2] = Integer.parseInt(sprice);

			Element erate = doc.selectFirst("strong#assetRealRate" + coin_arr[i] + "_KRW");
			String srate = erate.text().replace("%", "");
			arr[i][3] = Double.parseDouble(srate);

			update.DAO_updateC(coin_arr[i], Integer.parseInt(sprice), Double.parseDouble(srate));
			update.DAO_insert(coin_arr[i], Integer.parseInt(sprice), Double.parseDouble(srate));

		}

//		String elementk = element2.text();
//		elementk = elementk.replace(",", ""); 
//		elementk = elementk.replace(" ��", ""); // replace�� ��, %, �޸� ������ �� ����� �ǰڴ�.
////		Integer.parseInt(money);
//		System.out.println(Integer.parseInt(elementk)/100); // -�� �� ��ȯ�ȴ�.

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
			String sprice = eprice.text().replace(",", "").replace(" ��", "");

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
