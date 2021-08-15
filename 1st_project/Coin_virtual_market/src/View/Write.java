package View;


import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import Model.DAO_board;
import Model.DTO_board;

import javax.swing.JEditorPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

public class Write {
	
	
	private JFrame frame;
	private JTextField textField;
	DAO_board bo = new DAO_board();
	private String path = this.getClass().getResource("").getPath();
	
	public Write(String member_id) {
		initialize(member_id);
		frame.setVisible(true);
	}

	private void initialize(String member_id) {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 411, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setFont(new Font("Sandoll 삼립호빵체 TTF Basic", Font.PLAIN, 15));
		editorPane.setBounds(12, 89, 371, 346);
		frame.getContentPane().add(editorPane);
		
		JButton btn_OK = new JButton("");
		btn_OK.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//1. 작성자
				String id = member_id; //나중에 로그인한 아이디로 꼭 바꾸기... ??
				//2. title
				String title = textField.getText();
				//3. 글내용
				String content = editorPane.getText();
				
				
				DTO_board dto = new DTO_board(id, title, null, content);
				int cnt = bo.board_save(dto);
				if(cnt > 0) {
					//현재 창을 닫기
					ArrayList<DTO_board> list = bo.board_select();
					// column명
					String[] col = { "글쓴이", "제목", "작성일자" };
					// database에서 가져온 데이터를 넣게 될 Object 2차원 배열 
					Object[][] data = new Object[list.size()][col.length];
					for (int i = 0; i < data.length; i++) {
						data[i][0] = list.get(i).getMember_id(); //데이터베이스에서 가져온 데이터 넣어주기!
						data[i][1] = list.get(i).getTitle();
						data[i][2] = list.get(i).getDates();
					}
				
					DefaultTableModel resultModel = new DefaultTableModel(data, col) {
						@Override
						public boolean isCellEditable(int row, int column) {
							return false;
						}
					};
					Main_pro.table_1.setModel(resultModel);
					frame.dispose();
					
				}
				
			}
		});
		btn_OK.setBounds(12, 458, 97, 23);
		frame.getContentPane().add(btn_OK);
		Image imgbtn_ok = new ImageIcon(path + "image\\write.jpg").getImage();
		imgbtn_ok = imgbtn_ok.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon iconbtn_ok = new ImageIcon(imgbtn_ok);
		btn_OK.setIcon(iconbtn_ok);
		btn_OK.setBorderPainted(false);
		btn_OK.setContentAreaFilled(false);
		
		
		
		JButton btn_close = new JButton("");
		btn_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		btn_close.setBounds(121, 458, 97, 23);
		frame.getContentPane().add(btn_close);
		Image imgbtn_close = new ImageIcon(path + "image\\close.jpg").getImage();
		imgbtn_close = imgbtn_close.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon iconbtn_close = new ImageIcon(imgbtn_close);
		btn_close.setIcon(iconbtn_close);
		btn_close.setBorderPainted(false);
		btn_close.setContentAreaFilled(false);
		
		
		
		
		
		JLabel lblNewLabel = new JLabel("\uC81C\uBAA9 :");
		lblNewLabel.setFont(new Font("Sandoll 삼립호빵체 TTF Basic", Font.PLAIN, 15));
		lblNewLabel.setBounds(12, 10, 90, 40);
		frame.getContentPane().add(lblNewLabel);
		Image imgbtn_title = new ImageIcon(path + "image\\title.jpg").getImage();
		imgbtn_title = imgbtn_title.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon iconbtn_title = new ImageIcon(imgbtn_title);
		lblNewLabel.setIcon(iconbtn_title);
		
		
		
		
		textField = new JTextField();
		textField.setFont(new Font("Sandoll 삼립호빵체 TTF Basic", Font.PLAIN, 15));
		textField.setBounds(104, 20, 279, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\uB0B4\uC6A9");
		lblNewLabel_1.setFont(new Font("Sandoll 삼립호빵체 TTF Basic", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(12, 42, 90, 40);
		frame.getContentPane().add(lblNewLabel_1);
		Image imgbtn_cont = new ImageIcon(path + "image\\contents.jpg").getImage();
		imgbtn_cont = imgbtn_cont.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon iconbtn_cont = new ImageIcon(imgbtn_cont);
		lblNewLabel_1.setIcon(iconbtn_cont);
		
		
	}
}
