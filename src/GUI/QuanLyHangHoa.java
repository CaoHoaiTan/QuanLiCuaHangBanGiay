package GUI;

import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JInternalFrame;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.JTabbedPane;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;

public class QuanLyHangHoa extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuanLyHangHoa frame = new QuanLyHangHoa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws UnsupportedLookAndFeelException 
	 * @throws ClassNotFoundException 
	 */
	public QuanLyHangHoa() throws ClassNotFoundException, UnsupportedLookAndFeelException, SQLException {
		setBorder(null);
		setBounds(100, 100, 1075, 707);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JInternalFrame quanLy = new QuanLyHang();
		tabbedPane.addTab("Quản lý", null, quanLy, null);
		
		JInternalFrame nhapHang = new NhapHang();
		tabbedPane.addTab("Nhập hàng", null, nhapHang, null);
		nhapHang.setVisible(true);
		quanLy.setVisible(true);

	}

	// Tắt title bar của JInternalFrame
	@Override
	public void setUI(InternalFrameUI ui) {
		super.setUI(ui);
		BasicInternalFrameUI frameUI = (BasicInternalFrameUI) getUI();
		if (frameUI != null)
			frameUI.setNorthPane(null);
	}

}
