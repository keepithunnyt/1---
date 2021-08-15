package View;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import Controller.Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {

	private JFrame frame;
	private JTextField txt_id;
	private JTextField txt_pw;
	private JLabel lbl_background;
	private Controller controller = new Controller();
	private Join join;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		initialize();
		
	}
	

	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 960, 540);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btn_join = new JButton("");
		btn_join.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				join = new Join();
				frame.dispose();
				
			}
			
		});
		btn_join.setBounds(594, 345, 124, 54);
		frame.getContentPane().add(btn_join);
		btn_join.setBorderPainted(false);
		btn_join.setContentAreaFilled(false);
		
		JButton btn_login = new JButton("");
		btn_login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				controller.login(txt_id, txt_pw, frame);
				
			}
		});
		btn_login.setBounds(594, 274, 124, 61);
		frame.getContentPane().add(btn_login);
		btn_login.setBorderPainted(false);
		btn_login.setContentAreaFilled(false);
		
		
		txt_id = new JTextField();
		txt_id.setColumns(10);
		txt_id.setBounds(293, 289, 289, 31);
		frame.getContentPane().add(txt_id);
		
		txt_pw = new JTextField();
		txt_pw.setColumns(10);
		txt_pw.setBounds(293, 357, 289, 31);
		frame.getContentPane().add(txt_pw);
		
		String path = this.getClass().getResource("").getPath();
		Image img = new ImageIcon(path+"image\\LoginBG.jpg").getImage();
		//이미지의 크기를 변환
		img = img.getScaledInstance(960, 540, Image.SCALE_SMOOTH);
		//픽셀이 안깨지게 하고 싶다면 -> Image.SCALE_SMOOTH
		//움직이는 짤을 넣고 싶다면 -> Image.SCALE_DEFAULT
		
		//라벨에 넣을 수 있게 ImageIcon형태로 변환
		ImageIcon icon = new ImageIcon(img);
		
		lbl_background = new JLabel("");
		lbl_background.setIcon(icon); // 아이콘 넣기
		lbl_background.setBounds(0, 0, 960, 540);
		frame.getContentPane().add(lbl_background);
	}
}
