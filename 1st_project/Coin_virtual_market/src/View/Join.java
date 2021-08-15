package View;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Controller.Controller;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Join {

	private JFrame frame;
	private JLabel lbl_join;
	private JTextField txt_id;
	private JTextField txt_pw;
	private JTextField txt_name;
	private JButton btn_check;
	private JButton btn_joinok;
	private Controller controller = new Controller();
	private boolean check = false;
	private JButton btn_cancel;

	public Join() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 960, 540);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		String path = this.getClass().getResource("").getPath();
		Image img = new ImageIcon(path+"image\\Join_img.jpg").getImage();
		img = img.getScaledInstance(944, 501, Image.SCALE_SMOOTH);
		
		ImageIcon icon = new ImageIcon(img);
		
		lbl_join = new JLabel("New label");
		lbl_join.setIcon(icon);
		lbl_join.setBounds(0, 0, 944, 501);
		frame.getContentPane().add(lbl_join);
		
		txt_id = new JTextField();
		txt_id.setBackground(Color.WHITE);
		txt_id.setBounds(254, 229, 280, 21);
		frame.getContentPane().add(txt_id);
		
		txt_pw = new JTextField();
		txt_pw.setBackground(Color.WHITE);
		txt_pw.setBounds(254, 305, 280, 21);
		frame.getContentPane().add(txt_pw);
		
		txt_name = new JTextField();
		txt_name.setBackground(Color.WHITE);
		txt_name.setBounds(254, 382, 280, 21);
		frame.getContentPane().add(txt_name);
		
		btn_check = new JButton("");
		btn_check.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				check = controller.check_id(txt_id);
				btn_joinok.setEnabled(check);
			}
		});
		btn_check.setBounds(546, 217, 106, 43);
		frame.getContentPane().add(btn_check);
		btn_check.setBorderPainted(false);
		btn_check.setContentAreaFilled(false);
		
		btn_joinok = new JButton("");
		btn_joinok.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.join_new(txt_id, txt_pw, txt_name);
				if(check == true) {
					Login.main(null);
					frame.dispose();
				}
			}
		});
		btn_joinok.setBounds(404, 425, 130, 43);
		frame.getContentPane().add(btn_joinok);
		btn_joinok.setBorderPainted(false);
		btn_joinok.setContentAreaFilled(false);
		
		btn_cancel = new JButton("");
		btn_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login.main(null);
				frame.dispose();				
			}
		});
		btn_cancel.setBounds(731, 425, 130, 43);
		frame.getContentPane().add(btn_cancel);
		btn_cancel.setBorderPainted(false);
		btn_cancel.setContentAreaFilled(false);
		
	}
}
