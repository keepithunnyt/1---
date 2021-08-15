package Controller;

import Model.Crawling;
import Model.DAO_board;
import Model.DAO_coin;
import Model.DAO_trade;
import Model.DTO_board;
import Model.DTO_coinInfo;
import Model.DTO_coins;
import Model.DTO_members;
import Model.DTO_news;
import Model.DTO_sales_details;
import Model.DTO_variation_history;
import View.Main_pro;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Controller {

	private Crawling craw = new Crawling();
	private DAO_coin daoCoin = new DAO_coin();
	private boolean check = false;
	private DAO_trade trade = new DAO_trade();
	private DAO_board bd = new DAO_board();

	// login method
	public void login(JTextField txt_id, JTextField txt_pw, JFrame frame) {

		int check = daoCoin.DAO_login(txt_id.getText(), txt_pw.getText());

		switch (check) {
		case 0: // ���̵� ���� -> ȸ������ ���ּ��� �˾�
			JOptionPane.showMessageDialog(null, "���̵� �����ϴ�.", "�α��� ����", JOptionPane.OK_OPTION);
			break;
		case 1: // �α��� ����
			Main_pro mainPro = new Main_pro(txt_id.getText());
			frame.dispose();// ���߿� mainâ ��������� �ٲܰ�
			break;
		case 2: // ��й�ȣ�� Ʋ���ϴ�.
			JOptionPane.showMessageDialog(null, "��й�ȣ�� Ʋ�Ƚ��ϴ�.", "�α��� ����", JOptionPane.OK_OPTION);
			break;

		}
	}

	// id�ߺ��� Ȯ�����ִ� method
	public boolean check_id(JTextField txt_id) {

		if (txt_id.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "���̵� �Է����ּ���.", "Id is null", JOptionPane.OK_OPTION);
			check = false;
		} else {
			check = daoCoin.DAO_check(txt_id.getText());
			if (check == false) {
				JOptionPane.showMessageDialog(null, "�ߺ��� ���̵� �Դϴ�.", "�ߺ��� ID", JOptionPane.OK_OPTION);
			} else {
				JOptionPane.showMessageDialog(null, "��밡���� ���̵� �Դϴ�.", "��밡���� ID", JOptionPane.OK_OPTION);
			}
		}
		return check;
	}

	// ȸ�������� DB�� �����Ű�� method
	public void join_new(JTextField txt_id, JTextField txt_pw, JTextField txt_name) {

		if (check == false) {
			JOptionPane.showMessageDialog(null, "�ߺ�Ȯ���� ���ּ���.", "���� ����", JOptionPane.OK_OPTION);
		} else {

			daoCoin.DAO_insert(txt_id.getText(), txt_pw.getText(), txt_name.getText());
		}
	}

	// ũ�Ѹ� �ؿͼ� Main ȭ�鿡�� ������ table�� ����� method
	public DTO_members startMain(JTable table, String member_id) {

		Object[][] data = craw.crawler();

		DefaultTableModel model = new DefaultTableModel(data, craw.getColumns()){

			@Override
			public boolean isCellEditable(int row, int column) { //cell ���� ����
				
				return false;
			}
			
		};

		table.setModel(model);

		return daoCoin.getMemberInfo(member_id);

	}

	public DTO_members F5(JTable table, String member_id) {
		return startMain(table, member_id);
	}

	public int checkPrice(JTextField txt_price, DTO_members user, DTO_coins coin_info) {
		int coinNum = 0;
		try {
			coinNum = Integer.parseInt(txt_price.getText());
			if (user.getMoney() < coinNum * coin_info.getCoin_price()) {
				txt_price.setText(user.getMoney() / coin_info.getCoin_price() + "");
				coinNum = user.getMoney() / coin_info.getCoin_price();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "���ڸ� �Է����ּ���.", "Ȯ�� ����", JOptionPane.OK_OPTION);
		}
		return coinNum;

	}

	public void buy(DTO_members user, DTO_sales_details user_trade) {
		trade.tradeRecord(user, user_trade);
		trade.buyAccount(user, user_trade);
	}

	public void trade_list(DTO_members user, JTable table, DAO_coin coin) {

		ArrayList<DTO_sales_details> arr = trade.DAO_selectSalesDetails(user);
		String[] columns = { "���� ID", "��� ���Ű�", "���ͷ�" };
		Object[][] data = new Object[arr.size()][3];

		for (int i = 0; i < arr.size(); i++) {
			data[i][0] = arr.get(i).getCoin_id();
			data[i][1] = arr.get(i).getTrade_price();
			DTO_coins coin_info = coin.DAO_selectCoins(arr.get(i).getCoin_id());
			data[i][2] = (double) (coin_info.getCoin_price() - arr.get(i).getTrade_price()) / coin_info.getCoin_price()
					* 100;
		}

		DefaultTableModel model = new DefaultTableModel(data, columns);

		table.setModel(model);
	}

	public int getSellCoin(JTable table_sell, DTO_members user, JLabel lbl_sellcoin, JLabel lbl_sellprice) {
		String sellcoin = (String) table_sell.getValueAt(table_sell.getSelectedRow(), 0);
		lbl_sellcoin.setText(sellcoin);
		int coinPrice = (int) table_sell.getValueAt(table_sell.getSelectedRow(), 1);
		int coinNum = trade.DAO_sellCoinNum(user, sellcoin);
		lbl_sellprice.setText(coinPrice * coinNum + "");
		return coinPrice * coinNum;
	}

	public void sellAll(DTO_members user, int selling, JLabel lbl_sellcoin) {
		trade.sellAccount(user, selling);
		trade.DAO_deleteSales(lbl_sellcoin.getText());
	}

	public void myMoney(DTO_members user, JLabel lbl1, JLabel lbl2) {

		lbl1.setText(user.getMoney() + "");
		lbl2.setText(user.getMoney() + "");

	}

	public void setNewsTable(JTable table) {
		String[] columns = { "��� ����", "url" };
		ArrayList<DTO_news> news = craw.crawlingNews();
		Object[][] data = new Object[news.size()][columns.length];

		for (int i = 0; i < news.size(); i++) {
			data[i][0] = news.get(i).getTitle();
			data[i][1] = news.get(i).getUrl();
			
		}

		DefaultTableModel model = new DefaultTableModel(data, columns) {

			@Override
			public boolean isCellEditable(int row, int column) { //cell ���� ����
				
				return false;
			}
			
		};
		table.setModel(model);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					URI uri = new URI((String)table.getValueAt(table.getSelectedRow(), 1)); //��ũ ����
					Desktop desktop = Desktop.getDesktop(); //���ͳ�â�� ���� ��ü
					desktop.browse(uri); //���ͳ� â ����
				} catch (URISyntaxException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

	}
	
	public DefaultCategoryDataset getDataSet(String coin_id) {
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		ArrayList<DTO_variation_history> his = daoCoin.getHistory(coin_id);
		int check = 0;
		while (check < his.size()) {
			dataSet.addValue(his.get(check).getCoin_rate(),"�����", check + "");
			check++;
		}
		return dataSet;
	}
	
	public JFreeChart setChart(String coin_id) {
		JFreeChart chart = ChartFactory.createBarChart(coin_id, // ����
				"timeline", // x��
				"rate", // y��
				getDataSet(coin_id), // ������
				PlotOrientation.VERTICAL, // ����
				true, // ����
				true, // ��ǳ��
				false); // url
		chart.getTitle().setFont(new Font("�����ٸ����", Font.BOLD, 15));
		return chart;
	}
	
	public String getCoinInfo(String coin_id) {
		
		String result = "";
		ArrayList<DTO_coinInfo> coinInfo = daoCoin.setCoinInfo();
		
		for(int i = 0; i < coinInfo.size(); i++) {
			if(coinInfo.get(i).getCoin_id() == coin_id) {
				result = coinInfo.get(i).getInfo();
				break;
			}
		}
		
		return result;
	}

	public DTO_board setContents(String title) {
		DAO_board bd = new DAO_board();
		DTO_board board = bd.board_select(title);
		
		return board;
	}
	
	public void setBoard(JTable table) {
		ArrayList<DTO_board> list = bd.board_select();
		// column��
		String[] col = { "�۾���", "����", "�ۼ�����" };
		// database���� ������ �����͸� �ְ� �� Object 2���� �迭 
		Object[][] data = new Object[list.size()][col.length];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = list.get(i).getMember_id(); //�����ͺ��̽����� ������ ������ �־��ֱ�!
			data[i][1] = list.get(i).getTitle();
			data[i][2] = list.get(i).getDates();
	
		}
		
		DefaultTableModel resultModel = new DefaultTableModel(data, col){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(resultModel);
	}

}
