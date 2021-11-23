package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import DAO.ConnectionUtils;
import MODEL.KhachHangModel;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class QuanLyKhachHang extends JInternalFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtPhoneNumber;
	private JTextField txtFullName;
	private JTextField txtAddress;
	private JTextField txtEmail;
	private JTable table;
	private JTextField txtSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuanLyKhachHang frame = new QuanLyKhachHang();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Load data
	private void load_data() throws SQLException, ClassNotFoundException {
		Connection conn = ConnectionUtils.getConnection();
		String[] colName = { "SĐT", "Tên KH", "Giới tính", "Địa chỉ", "Email", "Tổng tiền đã mua", "Ưu đãi"};
		Object[][] data = KhachHangModel.load_KhachHang_to_Obj(conn);
		table.setModel(new DefaultTableModel(data, colName));
	}

	/**
	 * Create the frame.
	 *
	 * @throws UnsupportedLookAndFeelException
	 */
	public QuanLyKhachHang() throws UnsupportedLookAndFeelException {
		setFrameIcon(null);
		setBorder(null);
		UIManager.setLookAndFeel(new FlatLightLaf());

		setBounds(100, 100, 891, 706);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[][][grow][]"));

		JPanel input_panel = new JPanel();
		getContentPane().add(input_panel, "cell 0 0,grow");
		input_panel.setLayout(new MigLayout("", "[][][][][][grow]", "[24px][24px][24px][24px][24px][24px]"));

		JLabel lblNewLabel = new JLabel("Số điện thoại:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(lblNewLabel, "cell 0 0,alignx left,aligny center");

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(txtPhoneNumber, "cell 1 0 5 1,growx,aligny top");
		txtPhoneNumber.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Họ và tên:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(lblNewLabel_1, "cell 0 1,alignx left,aligny center");

		txtFullName = new JTextField();
		txtFullName.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtFullName.setColumns(10);
		input_panel.add(txtFullName, "cell 1 1 5 1,growx,aligny top");

		JLabel lblNewLabel_2 = new JLabel("Giới tính:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(lblNewLabel_2, "cell 0 2,alignx left,aligny center");

		JRadioButton rdbtnMale = new JRadioButton("Nam");
		rdbtnMale.setFont(new Font("Tahoma", Font.BOLD, 14));
		input_panel.add(rdbtnMale, "cell 1 2,alignx left,aligny center");

		JLabel lblNewLabel_3 = new JLabel("Địa chỉ:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(lblNewLabel_3, "cell 0 3,alignx left,aligny center");

		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtAddress.setColumns(10);
		input_panel.add(txtAddress, "cell 1 3 5 1,growx,aligny top");

		JLabel lblNewLabel_4 = new JLabel("Email:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(lblNewLabel_4, "cell 0 4,alignx left,aligny center");

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtEmail.setColumns(10);
		input_panel.add(txtEmail, "cell 1 4 5 1,growx,aligny top");

		JLabel lblNewLabel_5 = new JLabel("Ưu đãi 0%");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(lblNewLabel_5, "cell 0 5,alignx left,aligny center");

		JRadioButton rdbtnFemale = new JRadioButton("Nữ");
		rdbtnFemale.setFont(new Font("Tahoma", Font.BOLD, 14));
		input_panel.add(rdbtnFemale, "cell 3 2,alignx left,aligny center");

		JLabel lblNewLabel_5_1 = new JLabel("Tổng số tiền đã mua: vnđ");
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(lblNewLabel_5_1, "cell 5 5,growx,aligny center");

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[grow][]", "[25px]"));

		txtSearch = new JTextField();
		txtSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtSearch.getText().equals("Nhập để tìm kiếm"))
					txtSearch.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtSearch.getText().isBlank())
					txtSearch.setText("Nhập để tìm kiếm");
			}
		});
		txtSearch.setText("Nhập để tìm kiếm");
		txtSearch.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtSearch.setColumns(100);
		panel_1.add(txtSearch, "cell 0 0,alignx center,aligny center");

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 2,grow");

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.BOLD, 16));
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		getContentPane().add(panel, "cell 0 3,grow");
		panel.setLayout(new MigLayout("", "[79px][63px][63px][63px][63px]", "[29px]"));

		JButton btnThm = new JButton("Thêm");
		btnThm.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(btnThm, "cell 0 0,alignx left,aligny top");

		JButton btnSa = new JButton("Sửa");
		btnSa.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(btnSa, "cell 1 0,alignx left,aligny top");

		JButton btnXa = new JButton("Xóa");
		btnXa.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(btnXa, "cell 2 0,alignx left,aligny top");

		JButton btnLu = new JButton("Lưu");
		btnLu.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(btnLu, "cell 3 0,alignx left,aligny top");

		JButton btnNewButton = new JButton("Hủy");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(btnNewButton, "cell 4 0,alignx left,aligny top");

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
