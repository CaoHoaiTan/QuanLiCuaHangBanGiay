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

import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JInternalFrame;
import java.io.IOException;
import java.sql.SQLException;

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
					frmMain frame = new frmMain();
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
	public frmMain() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, IOException, SQLException {
		ImageIcon imgSanPham = new ImageIcon("resources/icon/shop.png");
		Image img = imgSanPham.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		setIconImage(img);
		setTitle("QUẢN LÝ CỬA HÀNG BÁN GIÀY");
		setFont(new Font("Tahoma", Font.BOLD, 16));
		UIManager.setLookAndFeel(new FlatLightLaf());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1120, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(5, 5));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);
		JInternalFrame qlKhachHang = new QuanLyKhachHang();
		tabbedPane.addTab("Khách hàng", null, qlKhachHang, null);
		JInternalFrame qlHangHoa = new QuanLyHangHoa();
		tabbedPane.addTab("Hàng hóa", null, qlHangHoa, null);

		JInternalFrame qlDonHang = new QuanLyDonHang();
		tabbedPane.addTab("Đơn hàng", null, qlDonHang, null);
		
		JInternalFrame thongKe = new ThongKeDoanhThu();
		tabbedPane.addTab("Thống kê", null, thongKe, null);
		
		JInternalFrame qlThuongHieu = new QuanLyThuongHieu();
		tabbedPane.addTab("Thương hiệu", null, qlThuongHieu, null);
		qlThuongHieu.setVisible(true);
		
		JInternalFrame qlNhanVien = new QuanLyNhanVien();
		tabbedPane.addTab("Nhân viên", null, qlNhanVien, null);
		
//		qlNhanVien.setVisible(true);
//		qlDonHang.setVisible(true);
//		thongKe.setVisible(true);
//		qlKhachHang.setVisible(true);
//		qlHangHoa.setVisible(true);
		
		tabbedPane.setEnabledAt(0, true);
		tabbedPane.setEnabledAt(1, true);
		tabbedPane.setEnabledAt(2, true);
		tabbedPane.setEnabledAt(3, true);
		tabbedPane.setEnabledAt(4, true);
		tabbedPane.setEnabledAt(5, true);
	}
}
