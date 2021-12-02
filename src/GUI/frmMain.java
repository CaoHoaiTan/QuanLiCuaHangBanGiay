package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import MODEL.Account;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JInternalFrame;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class frmMain extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DangNhap frame = new DangNhap();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public frmMain(Account curUser) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, IOException, SQLException {
		ImageIcon imgSanPham = new ImageIcon("resources/icon/shop.png");
		Image img = imgSanPham.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		setIconImage(img);
		setTitle("QUẢN LÝ CỬA HÀNG BÁN GIÀY");
		setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		UIManager.setLookAndFeel(new FlatLightLaf());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1120, 700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5, 5));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		
		JInternalFrame qlKhachHang = new QuanLyKhachHang();
		tabbedPane.addTab("Khách hàng", null, qlKhachHang, null);
		tabbedPane.setBackgroundAt(0, new Color(255, 255, 255));
		
		JInternalFrame qlHangHoa = new QuanLyHangHoa();
		qlHangHoa.getContentPane().setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Hàng hóa", null, qlHangHoa, null);
		tabbedPane.setBackgroundAt(1, new Color(255, 255, 255));

		JInternalFrame qlDonHang = new QuanLyDonHang(curUser);
		tabbedPane.addTab("Đơn hàng", null, qlDonHang, null);
		tabbedPane.setBackgroundAt(2, new Color(255, 255, 255));

		JInternalFrame thongKe = new ThongKeDoanhThu();
		thongKe.getContentPane().setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Thống kê", null, thongKe, null);
		tabbedPane.setBackgroundAt(3, new Color(255, 255, 255));
		
		if (curUser.isAdmin()) {
			JInternalFrame qlThuongHieu = new QuanLyThuongHieu();
			tabbedPane.addTab("Thương hiệu", null, qlThuongHieu, null);
			tabbedPane.setBackgroundAt(4, new Color(255, 255, 255));

			JInternalFrame qlNhanVien = new QuanLyNhanVien();
			tabbedPane.addTab("Nhân viên", null, qlNhanVien, null);
			tabbedPane.setBackgroundAt(5, new Color(255, 255, 255));
		}
		
		tabbedPane.setEnabledAt(0, true);

		JLabel lblUserInfo = new JLabel("Tài khoản đang sử dụng: " + curUser.getUsername() + " - Quyền: "
				+ (curUser.isAdmin() ? "Admin" : "Nhân viên"));
		lblUserInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblUserInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblUserInfo, BorderLayout.SOUTH);
	}
	
	// Get and resize Image to ImageIcon
	public static ImageIcon getIcon(String path, int width, int height) {
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		return icon;
	}
}
