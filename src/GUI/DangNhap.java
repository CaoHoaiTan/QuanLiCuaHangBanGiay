package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.formdev.flatlaf.FlatLightLaf;

import DAO.ConnectionUtils;
import MODEL.Account;
import MODEL.AccountModel;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DangNhap extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	/**
	 * Create the frame.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 */
	public DangNhap() throws UnsupportedLookAndFeelException {
		setBackground(new Color(0, 128, 128));
		setForeground(new Color(0, 153, 102));
		setResizable(false);
		setIconImage(getIcon("resources/icon/shop.png", 250, 250).getImage());
		UIManager.setLookAndFeel(new FlatLightLaf());

		setTitle("QUẢN LÝ CỬA HÀNG BÁN GIÀY");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][grow]", "[grow]"));

		JPanel left_panel = new JPanel();
		left_panel.setBackground(new Color(255, 255, 255));
		contentPane.add(left_panel, "cell 0 0,grow");
		left_panel.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JLabel lblImage = new JLabel("[IMG]");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setFont(new Font("Tahoma", Font.BOLD, 16));
		left_panel.add(lblImage, "cell 0 0,alignx center,aligny center");

		JPanel right_panel = new JPanel();
		right_panel.setForeground(new Color(0, 0, 0));
		right_panel.setBackground(new Color(255, 255, 255));
		contentPane.add(right_panel, "cell 1 0,grow");
		right_panel.setLayout(new MigLayout("", "[][grow]", "[grow][grow][grow][grow]"));

		JLabel lblNewLabel_1 = new JLabel("QUẢN LÝ CỬA HÀNG BÁN GIÀY");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
		right_panel.add(lblNewLabel_1, "cell 0 0 3 1,alignx center,aligny center");

		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		right_panel.add(lblNewLabel, "cell 0 1");

		txtUsername = new JTextField();
		txtUsername.setBackground(new Color(255, 255, 255));
		txtUsername.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
		right_panel.add(txtUsername, "cell 1 1,growx");
		txtUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(0, 0, 0));
		lblPassword.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		right_panel.add(lblPassword, "cell 0 2");

		txtPassword = new JPasswordField();
		txtPassword.setBackground(new Color(255, 255, 255));
		txtPassword.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
		right_panel.add(txtPassword, "cell 1 2,growx");

		JButton btnLogin = new JButton("Đăng nhập");
		btnLogin.setVerticalAlignment(SwingConstants.TOP);
		btnLogin.setHorizontalAlignment(SwingConstants.LEFT);
		btnLogin.setForeground(new Color(0, 0, 128));
		btnLogin.setBackground(new Color(255, 255, 255));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtUsername.getText() == null || txtUsername.getText().isBlank()
						|| txtPassword.getPassword() == null || txtPassword.getPassword().length <= 0)
					JOptionPane.showMessageDialog(right_panel, "Vui lòng nhập tài khoản và mật khẩu!!!");
				else {
					try {
						Connection conn = ConnectionUtils.getConnection();
						String username = txtUsername.getText();
						String password = String.valueOf(txtPassword.getPassword());
						Account curUser = AccountModel.findAccount(conn, username, password);
						if (curUser == null)
							JOptionPane.showMessageDialog(right_panel, "Tài khoản hoặc mật khẩu không chính xác!!!");
						else {
							frmMain frame = new frmMain(curUser);
							frame.setVisible(true);
							dispose();
						}
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			}
		});
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
		
		// Set img btn
		btnLogin.setIcon(getIcon("resources/icon/login.png", 30, 30));
		right_panel.add(btnLogin, "cell 0 3 3 1,alignx center,aligny center");

		// Set img lbl
		lblImage.setIcon(getIcon("resources/img/banner.png", (int) (794 / 3.5), (int) (1123 / 3.5)));
		lblImage.setText("");
		
		txtUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
					btnLogin.doClick();
			}
		});
		txtPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) 
					btnLogin.doClick();
			}
		});
	}
	
	// Get and resize Image to ImageIcon
	public static ImageIcon getIcon(String path, int width, int height) {
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		return icon;
	}

}
