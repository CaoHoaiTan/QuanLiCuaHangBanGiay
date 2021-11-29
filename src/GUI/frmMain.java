package GUI;

import com.formdev.flatlaf.FlatLightLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
	 */
	public frmMain() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, IOException, SQLException {
		setIconImage(ImageIO.read(getClass().getResourceAsStream("/resources/icon/shop.png")));
		setTitle("QUẢN LÝ CỬA HÀNG BÁN GIÀY");
		UIManager.setLookAndFeel(new FlatLightLaf());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane);

		JInternalFrame internalFrame = new QuanLyKhachHang();
		tabbedPane.addTab("Khách hàng", null, internalFrame, null);
		tabbedPane.setEnabledAt(0, true);

		JInternalFrame internalFrame_1 = new JInternalFrame("JIFrm2");
		tabbedPane.addTab("Hàng hóa", null, internalFrame_1, null);

		JInternalFrame internalFrame_2 = new QuanLyDonHang();
		tabbedPane.addTab("Đơn hàng", null, internalFrame_2, null);
		
		JInternalFrame internalFrame_3 = new JInternalFrame("New JInternalFrame");
		tabbedPane.addTab("Thương hiệu", null, internalFrame_3, null);
		
		JInternalFrame internalFrame_4 = new ThongKeDoanhThu();
		tabbedPane.addTab("Thống kê", null, internalFrame_4, null);
		
		JInternalFrame internalFrame_5 = new JInternalFrame("New JInternalFrame");
		tabbedPane.addTab("Nhân viên", null, internalFrame_5, null);
		internalFrame_5.setVisible(true);
		internalFrame_4.setVisible(true);
		internalFrame_3.setVisible(true);
		internalFrame_2.setVisible(true);
		internalFrame_1.setVisible(true);
		internalFrame.setVisible(true);
	}
}
