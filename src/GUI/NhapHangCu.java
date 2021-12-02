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
import MODEL.SanPham;
import MODEL.SanPhamModel;
import MODEL.ThuongHieu;
import MODEL.ThuongHieuModel;

import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class NhapHangCu extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextField txtID;
	private final JTextField txtQuantity_new;
	private final JTextArea txtQuantity;
	private final JTextArea txtCost;
	private List<ThuongHieu> listTH;
	private final JTextArea txtName = new JTextArea();
	private final JTextArea txtDescribe = new JTextArea();
	private JTextArea txtBrand = new JTextArea();
	private final JLabel lblImage = new JLabel("[IMG]");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NhapHangCu frame = new NhapHangCu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loadData() throws SQLException, ClassNotFoundException {
		// Connection
		Connection conn = ConnectionUtils.getConnection();
		listTH = ThuongHieuModel.load_ThuongHieu(conn);
	}

	// Tìm thương hiệu trong listTH
	private ThuongHieu findTH(int maTH) {
		for (ThuongHieu th : listTH)
			if (th.getId() == maTH)
				return th;
		return null;
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public NhapHangCu() throws ClassNotFoundException, SQLException {
		getContentPane().setBackground(new Color(255, 255, 255));
		setBorder(new TitledBorder(null, "NHẬP HÀNG CŨ", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Segoe UI Semibold", Font.BOLD, 16), null));
		setBounds(100, 100, 450, 700);
		getContentPane().setLayout(new MigLayout("", "[grow][200px]", "[200px][grow]"));

		final JPanel input_panel = new JPanel();
		input_panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(input_panel, "cell 0 0,grow");
		input_panel.setLayout(new MigLayout("", "[][grow]", "[grow][grow][grow]"));

		JLabel lblNewLabel_1 = new JLabel("ID:");
		lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		input_panel.add(lblNewLabel_1, "cell 0 0,alignx left,aligny center");

		txtID = new JTextField();
		txtID.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		txtID.setColumns(10);
		input_panel.add(txtID, "cell 1 0,growx,aligny center");

		JLabel lblNewLabel_5_1 = new JLabel("Số lượng:");
		lblNewLabel_5_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		input_panel.add(lblNewLabel_5_1, "cell 0 1,alignx left,aligny center");

		txtQuantity_new = new JTextField();
		txtQuantity_new.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		txtQuantity_new.setColumns(10);
		input_panel.add(txtQuantity_new, "cell 1 1,growx,aligny center");

		JButton btnKiemTra = new JButton("Kiểm tra");
		btnKiemTra.setBackground(new Color(255, 255, 255));
		btnKiemTra.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		input_panel.add(btnKiemTra, "cell 0 2,alignx center");

		JButton btnNhapHang = new JButton("Nhập hàng");
		btnNhapHang.setBackground(new Color(255, 255, 255));
		btnNhapHang.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		input_panel.add(btnNhapHang, "cell 1 2,alignx right");

		
		getContentPane().add(lblImage, "cell 1 0,alignx center,aligny center");

		JPanel info_panel = new JPanel();
		info_panel.setBackground(new Color(255, 255, 255));
		info_panel.setBorder(new TitledBorder(null, "Thông tin:", TitledBorder.LEADING, TitledBorder.TOP,
				new Font("Segoe UI Semibold", Font.BOLD, 14), null));
		getContentPane().add(info_panel, "cell 0 1 2 1,grow");
		info_panel.setLayout(new MigLayout("", "[][grow]", "[][grow][grow][][]"));

		JLabel lblNewLabel_1_1 = new JLabel("Thương hiệu:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		info_panel.add(lblNewLabel_1_1, "cell 0 0,alignx trailing,aligny top");
		loadData();
		
		txtBrand = new JTextArea();
		txtBrand.setTabSize(1);
		txtBrand.setLineWrap(true);
		txtBrand.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		txtBrand.setEditable(false);
		info_panel.add(txtBrand, "cell 1 0,grow");

		JLabel lblNewLabel_1_2 = new JLabel("Tên:");
		lblNewLabel_1_2.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		info_panel.add(lblNewLabel_1_2, "cell 0 1,aligny top");

		txtName.setLineWrap(true);
		txtName.setEditable(false);
		txtName.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		info_panel.add(txtName, "cell 1 1,grow");

		JLabel lblNewLabel_1_3 = new JLabel("Mô tả:");
		lblNewLabel_1_3.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		info_panel.add(lblNewLabel_1_3, "cell 0 2,aligny top");

		txtDescribe.setLineWrap(true);
		txtDescribe.setEditable(false);
		txtDescribe.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		info_panel.add(txtDescribe, "cell 1 2,grow");

		JLabel lblNewLabel_1_4 = new JLabel("Còn lại:");
		lblNewLabel_1_4.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		info_panel.add(lblNewLabel_1_4, "cell 0 3,alignx left");

		txtQuantity = new JTextArea();
		txtQuantity.setEditable(false);
		txtQuantity.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		txtQuantity.setColumns(10);
		info_panel.add(txtQuantity, "cell 1 3,growx");

		JLabel lblNewLabel_1_4_1 = new JLabel("Giá:");
		lblNewLabel_1_4_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		info_panel.add(lblNewLabel_1_4_1, "cell 0 4,alignx left");

		txtCost = new JTextArea();
		txtCost.setEditable(false);
		txtCost.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		txtCost.setColumns(10);
		info_panel.add(txtCost, "cell 1 4,growx");

		btnKiemTra.setIcon(getIcon("resources/icon/check.png", 30, 30));
		btnKiemTra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(txtID.getText());
					Connection conn = ConnectionUtils.getConnection();
					SanPham sp = SanPhamModel.find_SanPham(conn, id);
					if (sp == null) {
						JOptionPane.showMessageDialog(txtID, "Không tìm thấy sản phẩm ID: '" + id + "'");
					} else {
						txtName.setText(sp.getName());
						txtDescribe.setText(sp.getDescribe());
						txtCost.setText(String.format("%,.0f vnđ", sp.getCost()));
						txtQuantity.setText(String.valueOf(sp.getQuantity()));
						txtBrand.setText(findTH(sp.getIdBrand()).getName());
						ImageIcon imgSanPham = new ImageIcon("resources/" + sp.getImage());
						Image img = imgSanPham.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
						imgSanPham = new ImageIcon(img);
						lblImage.setIcon(imgSanPham);
						lblImage.setText("");
					}
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(txtID, "ID: KHÔNG HỢP LỆ! (id phải là kiểu số)");
				}
			}
		});

		btnNhapHang.setIcon(getIcon("resources/icon/add.png", 30, 30));
		btnNhapHang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = Integer.parseInt(txtID.getText());
					int quantity = Integer.parseInt(txtQuantity_new.getText());
					Connection conn = ConnectionUtils.getConnection();
					SanPham sp = SanPhamModel.find_SanPham(conn, id);
					if (sp == null) {
						JOptionPane.showMessageDialog(txtID, "Không tìm thấy sản phẩm ID: '" + id + "'");
					} else {
						SanPhamModel.update_SanPham(conn, id, quantity);
						JOptionPane.showMessageDialog(input_panel, "Nhập hàng id '" + id
								+ "' thành công, số lượng hiện tại: " + (sp.getQuantity() + quantity));
						resetText();
					}
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(txtQuantity_new,
							"SỐ LƯỢNG KHÔNG HỢP LỆ! (isố lượng phải là kiểu số)");
				}
			}
		});
	}

	public void resetText() {
		txtID.setText("");
		txtCost.setText("");
		txtQuantity.setText("");
		txtQuantity_new.setText("");
		txtDescribe.setText("");
		txtBrand.setText("");
		txtName.setText("");
		lblImage.setIcon(null);
		lblImage.setText("[IMG]");
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
