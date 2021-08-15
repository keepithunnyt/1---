package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.jfree.chart.ChartPanel;

import Controller.Controller;
import Model.DAO_coin;
import Model.DAO_trade;
import Model.DTO_coins;
import Model.DTO_members;
import Model.DTO_sales_details;
import javax.swing.JEditorPane;

public class Main_pro {

	private JFrame frame;
	private JTextField txt_buyprice;
	private JLabel lbl_back;
	private JLabel lbl_buynum;
	private JTable table;
	private Controller controller = new Controller();
	private DAO_coin coin = new DAO_coin();
	private DAO_trade trade = new DAO_trade();
	private DTO_coins coin_info = null;
	private DTO_members user = null;
	private DTO_sales_details user_trade;
	private JTable table_sell;
	private JLabel lbl_sellcoin;
	private JLabel lbl_sellprice;
	private int selling;
	private JLabel lbl_money1;
	private JLabel lbl_money2;
	private JTable table_news;
	private String coinChart;
	private JPanel panel_chart;
	private String path = this.getClass().getResource("").getPath();
	private JButton btn_buycheck;
	private JLabel lbl_havemo;
//	private JPanel panel_chart;
	private JPanel chart = null;
	static JTable table_1;

	public Main_pro(String member_id) {
		this.user_trade = new DTO_sales_details(member_id);
		this.user = coin.getMemberInfo(member_id);
		initialize(member_id);
		frame.setVisible(true);
	}

	private void initialize(String member_id) {

		frame = new JFrame();
		frame.setBounds(100, 100, 1146, 628);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Image img = new ImageIcon(path + "image\\mainpage.jpg").getImage();
		img = img.getScaledInstance(1128, 587, Image.SCALE_SMOOTH);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(622, 109, 439, 443);
		tabbedPane.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 15));
		tabbedPane.setBackground(Color.WHITE);
		frame.getContentPane().add(tabbedPane);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		tabbedPane.addTab("\uAC70\uB798\uC18C", null, panel_1, null);
		panel_1.setLayout(null);

		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 15));
		tabbedPane_1.setForeground(Color.BLACK);
		tabbedPane_1.setBackground(Color.WHITE);
		tabbedPane_1.setBounds(0, 0, 439, 415);
		panel_1.add(tabbedPane_1);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.WHITE);
		tabbedPane_1.addTab("\uAD6C\uB9E4", null, panel_6, null);
		panel_6.setLayout(null);

		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.WHITE);
		tabbedPane_1.addTab("\uD310\uB9E4", null, panel_7, null);
		panel_7.setLayout(null);

		JButton btn_allsell = new JButton("");
		btn_allsell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				controller.sellAll(user, selling, lbl_sellcoin);
				user = controller.F5(table, member_id);
				controller.trade_list(user, table_sell, coin);
				lbl_sellcoin.setText("");
				lbl_sellprice.setText("");
				controller.myMoney(user, lbl_money2, lbl_money1);

			}
		});
		btn_allsell.setBounds(217, 264, 159, 73);
		panel_7.add(btn_allsell);

		JLabel lbl_sellnum = new JLabel("");
		lbl_sellnum.setBounds(90, 120, 129, 26);
		panel_7.add(lbl_sellnum);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 10, 410, 219);
		panel_7.add(scrollPane_2);

		JLabel lbl_coinSelect = new JLabel("\uCF54\uC778 \uC120\uD0DD");
		lbl_coinSelect.setBounds(0, 10, 90, 40);
		panel_6.add(lbl_coinSelect);

		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 12));
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String coinNameTemp = (String) comboBox.getSelectedItem();
				coin_info = coin.DAO_selectCoins(coinNameTemp);
				user_trade.setCoin_id(coinNameTemp);
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(
				new String[] { "BTC", "ETH", "LTC", "ETC", "XRP", "BCH", "EOS", "ARW", "MKR", "AAVE" }));
		comboBox.setBounds(102, 17, 264, 26);
		panel_6.add(comboBox);

		JLabel lblNewLabel_2 = new JLabel("\uB9E4\uC218 \uAC2F\uC218");
		lblNewLabel_2.setBounds(0, 60, 90, 40);
		panel_6.add(lblNewLabel_2);

		table_sell = new JTable();
		table_sell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selling = controller.getSellCoin(table_sell, user, lbl_sellcoin, lbl_sellprice);
			}
		});
		scrollPane_2.setViewportView(table_sell);
		controller.trade_list(user, table_sell, coin);

		lbl_sellcoin = new JLabel("");
		lbl_sellcoin.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 12));
		lbl_sellcoin.setBounds(90, 303, 115, 26);
		panel_7.add(lbl_sellcoin);

		lbl_sellprice = new JLabel("");
		lbl_sellprice.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 12));
		lbl_sellprice.setBounds(90, 346, 97, 26);
		panel_7.add(lbl_sellprice);

		lbl_money2 = new JLabel("");
		lbl_money2.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 12));
		lbl_money2.setBounds(90, 258, 112, 30);
		panel_7.add(lbl_money2);

		txt_buyprice = new JTextField();
		txt_buyprice.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 12));
		txt_buyprice.setBounds(102, 67, 159, 26);
		panel_6.add(txt_buyprice);
		txt_buyprice.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("\uB9E4\uC218 \uAC00\uACA9");
		lblNewLabel_3.setBounds(0, 110, 90, 40);
		panel_6.add(lblNewLabel_3);

		JButton btn_buyok = new JButton("");
		btn_buyok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// user, user_trade πﬁæ∆ ±‚∑œ¿˙¿Â
				controller.buy(user, user_trade);
				user = controller.F5(table, member_id);
				controller.trade_list(user, table_sell, coin);
				controller.myMoney(user, lbl_money2, lbl_money1);
				txt_buyprice.setText("");
				lbl_buynum.setText("");

			}
		});
		btn_buyok.setBounds(217, 264, 159, 73);
		panel_6.add(btn_buyok);

		lbl_buynum = new JLabel("");
		lbl_buynum.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 12));
		lbl_buynum.setBounds(102, 117, 159, 26);
		panel_6.add(lbl_buynum);

		btn_buycheck = new JButton("");
		btn_buycheck.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int coin_count = controller.checkPrice(txt_buyprice, user, coin_info);
				lbl_buynum.setText(coin_count * coin_info.getCoin_price() + "");
				user_trade.setCoin_count(coin_count);
				user_trade.setTrade_price(coin_info.getCoin_price());
				controller.myMoney(user, lbl_money2, lbl_money1);
			}
		});
		btn_buycheck.setBounds(276, 60, 90, 40);
		panel_6.add(btn_buycheck);

		lbl_havemo = new JLabel("\uBCF4\uC720\uC790\uC0B0");
		lbl_havemo.setBounds(0, 280, 90, 40);
		panel_6.add(lbl_havemo);

		lbl_money1 = new JLabel(user.getMoney() + "");
		lbl_money1.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 12));
		lbl_money1.setBounds(102, 285, 97, 30);
		panel_6.add(lbl_money1);

		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("\uCF54\uC778\uB274\uC2A4", null, panel_4, null);
		panel_4.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 10, 410, 348);
		panel_4.add(scrollPane_1);

		table_news = new JTable();
		scrollPane_1.setViewportView(table_news);
		controller.setNewsTable(table_news);

		JButton btn_reset = new JButton("");
		btn_reset.setBounds(848, 29, 81, 28);
		btn_reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				user = controller.F5(table, member_id);
				controller.trade_list(user, table_sell, coin);
				controller.myMoney(user, lbl_money2, lbl_money1);
				controller.setBoard(table_1);

			}
		});
		frame.getContentPane().add(btn_reset);
//		btn_reset.setBorderPainted(false);
//	    btn_reset.setContentAreaFilled(false);

		UIManager.put("TabbedPane.contentOpaque", false);
		JTabbedPane tabbedPane_3 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_3.setBounds(75, 109, 439, 443);
		tabbedPane_3.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 15));
		frame.getContentPane().add(tabbedPane_3);

		JPanel panel_11 = new JPanel();
		tabbedPane_3.addTab("\uC804\uCCB4 \uCC28\uD2B8 \uADF8\uB798\uD504", null, panel_11, null);
		panel_11.setLayout(null);
		panel_11.setOpaque(false); // panel, textField .... πË∞Ê¿« ≈ı∏Ì«œ∞‘ ∏∏µÂ¥¬ πÊπ˝
		panel_11.setBorder(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 410, 394);
		panel_11.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		user = controller.startMain(table, member_id);

		JPanel panel_12 = new JPanel();
		panel_12.setBackground(Color.WHITE);
		tabbedPane_3.addTab("\uC6D0\uD558\uB294 \uCF54\uC778 \uC815\uBCF4", null, panel_12, null);
		panel_12.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("\uCF54\uC778 \uC120\uD0DD");
		lblNewLabel_4.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(12, 10, 68, 25);
		panel_12.add(lblNewLabel_4);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(12, 247, 410, 157);
		panel_12.add(scrollPane_3);
		
		JEditorPane editor_coinInfo = new JEditorPane();
		editor_coinInfo.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 14));
		scrollPane_3.setViewportView(editor_coinInfo);

		JComboBox comboBox_3 = new JComboBox();

		comboBox_3.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 15));
		comboBox_3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				if (chart != null) {
					chart.setVisible(false);
				}
				coinChart = (String) comboBox_3.getSelectedItem();
				chart = new ChartPanel(controller.setChart(coinChart));
				chart.setPreferredSize(new Dimension(400,180));
				chart.setBounds(12, 45, 400, 180);
				panel_chart.add(chart);
				editor_coinInfo.setText(controller.getCoinInfo(coinChart));
			}
		});
		comboBox_3.setModel(new DefaultComboBoxModel(
				new String[] { "BTC", "ETH", "LTC", "ETC", "XRP", "BCH", "EOS", "ARW", "MKR", "AAVE" }));
		comboBox_3.setBounds(81, 10, 173, 24);
		panel_12.add(comboBox_3);

		panel_chart = new JPanel();
		panel_chart.setBounds(12, 45, 410, 192);
		panel_12.add(panel_chart);

//		panel_chart = new ChartPanel(null);
//		panel_chart.setBounds(12, 45, 410, 192);
//		panel_12.add(panel_chart);

		JButton btn_logout = new JButton("");
		btn_logout.setBounds(941, 29, 81, 28);
		btn_logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login.main(null);
				frame.dispose();
			}
		});
		frame.getContentPane().add(btn_logout);
		panel_4.setBackground(Color.WHITE);

		btn_reset.setBorderPainted(false);
		btn_reset.setContentAreaFilled(false);
		btn_logout.setBorderPainted(false);
		btn_logout.setContentAreaFilled(false);

		JLabel lbl_havemo2 = new JLabel("New label");
		lbl_havemo2.setBounds(0, 253, 90, 40);
		panel_7.add(lbl_havemo2);
		Image img_havemo2 = new ImageIcon(path + "image\\havemo.jpg").getImage();
		img_havemo2 = img_havemo2.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon icon_havemo2 = new ImageIcon(img_havemo2);
		lbl_havemo2.setIcon(icon_havemo2);

		JLabel lbl_coink = new JLabel("New label");
		lbl_coink.setBounds(0, 296, 90, 40);
		panel_7.add(lbl_coink);
		Image img_coink = new ImageIcon(path + "image\\coinkind.jpg").getImage();
		img_coink = img_coink.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon icon_coink = new ImageIcon(img_coink);
		lbl_coink.setIcon(icon_coink);

		JLabel lbl_coinpri = new JLabel("New label");
		lbl_coinpri.setBounds(0, 339, 90, 40);
		panel_7.add(lbl_coinpri);

		Image img_coinpri = new ImageIcon(path + "image\\price.jpg").getImage();
		img_coinpri = img_coinpri.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon icon_coinpri = new ImageIcon(img_coinpri);
		lbl_coinpri.setIcon(icon_coinpri);

		Image img_havemo = new ImageIcon(path + "image\\havemo.jpg").getImage();
		img_havemo = img_havemo.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon icon_havemo = new ImageIcon(img_havemo);

		Image img_allsell = new ImageIcon(path + "image\\aoeh.jpg").getImage();
		img_allsell = img_allsell.getScaledInstance(159, 73, Image.SCALE_SMOOTH);
		ImageIcon icon_allsell = new ImageIcon(img_allsell);
		btn_allsell.setIcon(icon_allsell);
		btn_allsell.setBorderPainted(false);
		btn_allsell.setContentAreaFilled(false);

		Image img_buycheck = new ImageIcon(path + "image\\aotn.jpg").getImage();
		img_buycheck = img_buycheck.getScaledInstance(159, 73, Image.SCALE_SMOOTH);
		ImageIcon icon_buycheck = new ImageIcon(img_buycheck);

		Image imgNewLabel_2 = new ImageIcon(path + "image\\count.jpg").getImage();
		imgNewLabel_2 = imgNewLabel_2.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon iconNewLabel_2 = new ImageIcon(imgNewLabel_2);

		Image img_buyok = new ImageIcon(path + "image\\ok.jpg").getImage();
		img_buyok = img_buyok.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon icon_buyok = new ImageIcon(img_buyok);

		Image img_price = new ImageIcon(path + "image\\price.jpg").getImage();
		img_price = img_price.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon icon_price = new ImageIcon(img_price);

		Image imgCoinSelect = new ImageIcon(path + "image\\consel.jpg").getImage();
		imgCoinSelect = imgCoinSelect.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon iconCoinSelect = new ImageIcon(imgCoinSelect);

		
		
		// =========================================================

//		JLabel lbl_havemo = new JLabel("New label");
//	    lbl_havemo.setBounds(0, 264, 90, 40);
//	    panel_6.add(lbl_havemo);
		lbl_havemo.setIcon(icon_havemo);

		btn_buyok.setIcon(icon_buycheck);
		btn_buyok.setBorderPainted(false);
		btn_buyok.setContentAreaFilled(false);
		lblNewLabel_2.setIcon(iconNewLabel_2); // æ∆¿Ãƒ‹ ≥÷±‚
		btn_buycheck.setIcon(icon_buyok);
		btn_buycheck.setBorderPainted(false);
		btn_buycheck.setContentAreaFilled(false);
		lblNewLabel_3.setIcon(icon_price); // æ∆¿Ãƒ‹ ≥÷±‚
		lbl_coinSelect.setIcon(iconCoinSelect);
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		tabbedPane.addTab("\uAC8C\uC2DC\uD310", null, panel_3, null);
		panel_3.setLayout(null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(12, 10, 410, 347);
		panel_3.add(scrollPane_4);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String title = (String)table_1.getValueAt(table_1.getSelectedRow(), 1);
				contents content = new contents(title);
				
			}
		});
		table_1.setFont(new Font("Sandoll ªÔ∏≥»£ªß√º TTF Basic", Font.PLAIN, 12));
		scrollPane_4.setViewportView(table_1);
		controller.setBoard(table_1);
		
		JButton btn_write = new JButton("");
		btn_write.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Write write = new Write(member_id);
				
			}
			
		});
		btn_write.setBounds(332, 367, 90, 40);
		panel_3.add(btn_write);

		Image imgbtn_write = new ImageIcon(path + "image\\write.jpg").getImage();
		imgbtn_write = imgbtn_write.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon iconbtn_write = new ImageIcon(imgbtn_write);
		btn_write.setIcon(iconbtn_write);
		btn_write.setBorderPainted(false);
		btn_write.setContentAreaFilled(false);


		ImageIcon icon = new ImageIcon(img);
		lbl_back = new JLabel("lbl_back");
		lbl_back.setBounds(0, 0, 1128, 587);
		lbl_back.setIcon(icon);
		frame.getContentPane().add(lbl_back);
	}
}
