package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import DAO.ConnectionUtils;

import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import MODEL.SanPham;
import MODEL.SanPhamModel;
import MODEL.ThuongHieu;
import MODEL.ThuongHieuModel;

import javax.swing.JTextArea;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class NhapHangMoi extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtQuantity;
	private JTextField txtCost;
	private JComboBox<ThuongHieu> cmbBrand = new JComboBox<ThuongHieu>();
	private List<ThuongHieu> listTH;
	private JTextField txtID;
	private JPanel input_panel;
	private JTextField txtDay;
	private JTextField txtImage;
	private JTextField txtMonth;
	private JTextField txtYear;
	private JTextArea txtName = new JTextArea();
	private JTextArea txtDescribe = new JTextArea();
	private JLabel lblImage = new JLabel("[IMG]");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NhapHangMoi frame = new NhapHangMoi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loadData() throws SQLException, ClassNotFoundException {
		resetText();

		// Connection
		Connection conn = ConnectionUtils.getConnection();
		cmbBrand.setEditable(true);
		input_panel.add(cmbBrand, "cell 1 1 4 1,growx");

		// Combobox ThuongHieu
		cmbBrand.removeAllItems();
		listTH = ThuongHieuModel.load_ThuongHieu(conn);
		for (ThuongHieu th : listTH)
			cmbBrand.addItem(th);

		// New id
		txtID.setText("" + (SanPhamModel.getLastID_SanPham(conn) + 1));

		// Ngày hiện tại
		Date curDate = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		txtDay.setText("" + cal.get(Calendar.DATE));
		txtMonth.setText("" + (cal.get(Calendar.MONTH) + 1));
		txtYear.setText("" + cal.get(Calendar.YEAR));
	}

	public void resetText() {
		txtID.setText("");
		txtCost.setText("");
		txtDay.setText("");
		txtMonth.setText("");
		txtYear.setText("");
		txtQuantity.setText("");
		txtImage.setText("");
		txtName.setText("");
		txtDescribe.setText("");
		lblImage.setText("[IMG]");
		lblImage.setIcon(null);
	}

	public boolean isValidInput() {
		try {
			Integer.parseInt(txtQuantity.getText());
			Double.parseDouble(txtCost.getText());
			Date.valueOf(txtYear.getText() + "-" + txtMonth.getText() + "-" + txtDay.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(input_panel, "DỮ LIỆU NHẬP VÀO KHÔNG HỢP LỆ!!!");
			return false;
		}
		return true;
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public NhapHangMoi() throws ClassNotFoundException, SQLException {
		getContentPane().setBackground(new Color(255, 255, 255));
		setBorder(new TitledBorder(null, "NHẬP HÀNG MỚI", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Segoe UI Semibold", Font.BOLD, 16), null));
		setBounds(100, 100, 450, 700);
		getContentPane().setLayout(new MigLayout("", "[grow][200px]", "[200px][grow][]"));

		input_panel = new JPanel();
		input_panel.setBackground(new Color(255, 255, 255));
		input_panel.setBorder(null);
		getContentPane().add(input_panel, "cell 0 0,grow");
		input_panel.setLayout(new MigLayout("", "[][grow][grow][grow]", "[grow][grow][grow][grow][grow]"));

		JLabel lblNewLabel_1_4_2 = new JLabel("ID mới:");
		lblNewLabel_1_4_2.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		input_panel.add(lblNewLabel_1_4_2, "cell 0 0,alignx left,aligny center");

		txtID = new JTextField();
		txtID.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		txtID.setEditable(false);
		txtID.setColumns(10);
		input_panel.add(txtID, "cell 1 0 4 1,growx");

		JLabel lblNewLabel_1_1 = new JLabel("Thương hiệu:");
		input_panel.add(lblNewLabel_1_1, "cell 0 1");
		lblNewLabel_1_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		cmbBrand.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));

		getContentPane().add(lblImage, "cell 1 0,alignx center,aligny center");

		JPanel info_panel = new JPanel();
		info_panel.setBackground(new Color(255, 255, 255));
		info_panel.setBorder(null);
		getContentPane().add(info_panel, "cell 0 1 2 1,grow");
		info_panel.setLayout(new MigLayout("", "[grow][grow][]", "[grow][grow][]"));

		JLabel lblNewLabel_1_2 = new JLabel("Tên:");
		lblNewLabel_1_2.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		info_panel.add(lblNewLabel_1_2, "cell 0 0,aligny top");

		txtName.setLineWrap(true);
		txtName.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		info_panel.add(txtName, "cell 1 0 2 1,grow");

		JLabel lblNewLabel_1_3 = new JLabel("Mô tả:");
		lblNewLabel_1_3.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		info_panel.add(lblNewLabel_1_3, "cell 0 1,aligny top");

		txtDescribe.setLineWrap(true);
		txtDescribe.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		info_panel.add(txtDescribe, "cell 1 1 2 1,grow");

		JLabel lblNewLabel_1_4_1_1 = new JLabel("Hình:");
		lblNewLabel_1_4_1_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		info_panel.add(lblNewLabel_1_4_1_1, "cell 0 2,alignx left");

		txtImage = new JTextField();
		txtImage.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		txtImage.setColumns(10);
		info_panel.add(txtImage, "cell 1 2,growx");

		JButton btnXem = new JButton("Xem");
		btnXem.setBackground(new Color(255, 255, 255));
		btnXem.setIcon(getIcon("resources/icon/picture.png", 30, 30));
		btnXem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// binding image
					ImageIcon imgSanPham = new ImageIcon("resources/" + txtImage.getText());
					Image img = imgSanPham.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
					imgSanPham = new ImageIcon(img);
					lblImage.setIcon(imgSanPham);
					lblImage.setText("");
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(txtImage, "Không tìm thấy hình!");
				}
			}
		});
		btnXem.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		info_panel.add(btnXem, "cell 2 2");

		JPanel button_panel = new JPanel();
		button_panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(button_panel, "cell 0 2 2 1,grow");
		button_panel.setLayout(new MigLayout("", "[grow][grow]", "[grow]"));

		JButton btnHuy = new JButton("Hủy");
		btnHuy.setBackground(new Color(255, 255, 255));
		btnHuy.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		button_panel.add(btnHuy, "cell 0 0,alignx left,aligny center");

		JButton btnNhapHang = new JButton("Nhập hàng");
		btnNhapHang.setBackground(new Color(255, 255, 255));
		btnNhapHang.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		button_panel.add(btnNhapHang, "cell 1 0,alignx right,aligny center");

		JLabel lblNewLabel_1_4 = new JLabel("Số lượng:");
		input_panel.add(lblNewLabel_1_4, "cell 0 2");
		lblNewLabel_1_4.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));

		txtQuantity = new JTextField();
		input_panel.add(txtQuantity, "cell 1 2 4 1,growx");
		txtQuantity.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		txtQuantity.setColumns(10);

		JLabel lblNewLabel_1_4_1 = new JLabel("Giá (vnđ):");
		input_panel.add(lblNewLabel_1_4_1, "cell 0 3");
		lblNewLabel_1_4_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));

		txtCost = new JTextField();
		input_panel.add(txtCost, "cell 1 3 4 1,growx");
		txtCost.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		txtCost.setColumns(10);

		JLabel lblNewLabel_1_4_1_11 = new JLabel("Ngày bán:");
		lblNewLabel_1_4_1_11.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		input_panel.add(lblNewLabel_1_4_1_11, "cell 0 4,alignx left,aligny center");

		txtDay = new JTextField();
		txtDay.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		txtDay.setColumns(10);
		input_panel.add(txtDay, "cell 1 4,growx");

		txtMonth = new JTextField();
		txtMonth.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		txtMonth.setColumns(10);
		input_panel.add(txtMonth, "cell 2 4,growx");

		txtYear = new JTextField();
		txtYear.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		txtYear.setColumns(10);
		input_panel.add(txtYear, "cell 3 4,growx");

		loadData();

		btnHuy.setIcon(getIcon("resources/icon/cancel.png", 30, 30));
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					loadData();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnNhapHang.setIcon(getIcon("resources/icon/add.png", 30, 30));
		btnNhapHang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isValidInput()) {
					int quantity = Integer.parseInt(txtQuantity.getText());
					double cost = Double.parseDouble(txtCost.getText());
					Date saleDate = Date.valueOf(txtYear.getText() + "-" + txtMonth.getText() + "-" + txtDay.getText());
					int id = Integer.parseInt(txtID.getText());
					int idBrand = ((ThuongHieu) cmbBrand.getSelectedItem()).getId();
					try {
						Connection conn = ConnectionUtils.getConnection();
						SanPham sp = new SanPham(id, idBrand, txtName.getText(), txtImage.getText(),
								txtDescribe.getText(), quantity, cost, saleDate);
						SanPhamModel.insert_SanPham(conn, sp);
						JOptionPane.showMessageDialog(input_panel, "Thêm sản phẩm (ID: '" + id + "') thành công!!!");
						loadData();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
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

	// Tắt title bar của JInternalFrame
	@Override
	public void setUI(InternalFrameUI ui) {
		super.setUI(ui);
		BasicInternalFrameUI frameUI = (BasicInternalFrameUI) getUI();
		if (frameUI != null)
			frameUI.setNorthPane(null);
	}

}
