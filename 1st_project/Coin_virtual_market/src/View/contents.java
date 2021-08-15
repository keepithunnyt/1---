package View;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import Controller.Controller;
import Model.DTO_board;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class contents {
	private DTO_board dto;
	private JFrame frame;
	private Controller controller = new Controller();
	private JEditorPane editor_text;
	private String path = this.getClass().getResource("").getPath();
	
	public contents(String writer) {
		dto = controller.setContents(writer);
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 569);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 434, 530);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uC81C\uBAA9 :");
		lblNewLabel.setFont(new Font("Sandoll »ï¸³È£»§Ã¼ TTF Basic", Font.PLAIN, 15));
		lblNewLabel.setBounds(0, 10, 90, 40);
		panel.add(lblNewLabel);
		Image imgbtn_title = new ImageIcon(path + "image\\title.jpg").getImage();
		imgbtn_title = imgbtn_title.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon iconbtn_title = new ImageIcon(imgbtn_title);
		lblNewLabel.setIcon(iconbtn_title);
		
		
		
		
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setFont(new Font("Sandoll »ï¸³È£»§Ã¼ TTF Basic", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(0, 50, 90, 40);
		panel.add(lblNewLabel_1);
		Image imgbtn_cont = new ImageIcon(path + "image\\contents.jpg").getImage();
		imgbtn_cont = imgbtn_cont.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon iconbtn_cont = new ImageIcon(imgbtn_cont);
		lblNewLabel_1.setIcon(iconbtn_cont);
		
		
		
		
		
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Image imgbtn_close = new ImageIcon(path + "image\\close.jpg").getImage();
		imgbtn_close = imgbtn_close.getScaledInstance(90, 40, Image.SCALE_SMOOTH);
		ImageIcon iconbtn_close = new ImageIcon(imgbtn_close);
		btnNewButton.setIcon(iconbtn_close);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		
		
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				frame.dispose();
			}
		});
		btnNewButton.setBounds(12, 497, 97, 23);
		panel.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 90, 412, 395);
		panel.add(scrollPane);
		
		editor_text = new JEditorPane();
		editor_text.setFont(new Font("Sandoll »ï¸³È£»§Ã¼ TTF Basic", Font.PLAIN, 15));
		scrollPane.setViewportView(editor_text);
		editor_text.setText(dto.contents);
		
		JLabel lbl_title = new JLabel(dto.title);
		lbl_title.setFont(new Font("Sandoll »ï¸³È£»§Ã¼ TTF Basic", Font.PLAIN, 15));
		lbl_title.setBounds(101, 19, 290, 23);
		panel.add(lbl_title);
	}
}
