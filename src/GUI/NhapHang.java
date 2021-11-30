package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import net.miginfocom.swing.MigLayout;
import java.sql.SQLException;

public class NhapHang extends JInternalFrame {

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
					NhapHang frame = new NhapHang();
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
	 * @throws ClassNotFoundException 
	 */
	public NhapHang() throws ClassNotFoundException, SQLException {
		setBorder(null);
		setBounds(100, 100, 1000, 700);
		getContentPane().setLayout(new MigLayout("", "[grow][grow]", "[grow]"));
		
		JInternalFrame internalFrame = new NhapHangCu();
		getContentPane().add(internalFrame, "cell 0 0 1 2,grow");
		
		JInternalFrame internalFrame_1 = new NhapHangMoi();
		getContentPane().add(internalFrame_1, "cell 1 0,grow");
		internalFrame_1.setVisible(true);
		internalFrame.setVisible(true);

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
