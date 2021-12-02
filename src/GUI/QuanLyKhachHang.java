package GUI;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.formdev.flatlaf.FlatLightLaf;

import DAO.ConnectionUtils;
import MODEL.KhachHang;
import MODEL.KhachHangModel;

import java.awt.Font;
import java.awt.Image;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class QuanLyKhachHang extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtPhoneNumber;
	private JTextField txtFullName;
	private JTextField txtAddress;
	private JTextField txtEmail;
	private JTextField txtSearch;
	private JTable table;
	private JButton btnHuy = new JButton("Hủy");
	private JButton btnLuu = new JButton("Lưu");
	private JButton btnXoa = new JButton("Xóa");
	private JButton btnSua = new JButton("Sửa");
	private JButton btnThem = new JButton("Thêm");
	private JPanel button_panel = new JPanel();
	private JPanel data_panel = new JPanel();
	private JPanel seacrh_panel = new JPanel();
	private JPanel input_panel = new JPanel();
	private JRadioButton rdbtnFemale = new JRadioButton("Nữ");
	private JRadioButton rdbtnMale = new JRadioButton("Nam");
	private JLabel lblTotalcost = new JLabel("Tổng số tiền đã mua: vnđ");
	private JLabel lblDiscount = new JLabel("Ưu đãi 0%");
	private JButton btnTim = new JButton("Tìm kiếm");
	private boolean isInsert;

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

	// Kiểm tra thông tin nhập vào
	private boolean isValidInput() {
		String sdt = txtPhoneNumber.getText();
		// Tên và sđt là bắt buộc
		if (sdt.isBlank() || txtFullName.getText().isBlank())
			return false;
		// Kiểm tra sđt
		if (sdt.length() != 10)
			return false;
		for (int i=0; i<sdt.length(); i++) {
			if (sdt.charAt(i) < '0' || sdt.charAt(i) > '9')
				return false;
		}
		return true;
	}
	
	// Get and resize Image to ImageIcon
	public static ImageIcon getIcon(String path, int width, int height) {
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		return icon;
	}

	// Load data
	private void load_data() throws SQLException, ClassNotFoundException {
		// Load dữ liệu lên JTable
		Connection conn = ConnectionUtils.getConnection();
		String[] colName = { "SĐT", "Tên KH", "Giới tính", "Địa chỉ", "Email", "Tổng tiền (vnđ)", "Ưu đãi (%)" };
		final Class<?>[] colClass = new Class[] { String.class, String.class, String.class, String.class, String.class,
				Double.class, Integer.class };
		Object[][] data = KhachHangModel.load_KhachHang_to_Obj(conn);

		DefaultTableModel dfModel = new DefaultTableModel(data, colName) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// Set class của column
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return colClass[columnIndex];
			}

			// Không cho phép sửa trên table
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Row sorter dùng để tìm kiếm
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dfModel);

		table.setModel(dfModel);
		table.setRowSorter(sorter);
		table.setEnabled(true);
		btnThem.setBackground(new Color(255, 255, 255));

		// Bật tắt các nút
		btnThem.setEnabled(true);
		btnSua.setBackground(new Color(255, 255, 255));
		btnSua.setEnabled(true);
		btnXoa.setBackground(new Color(255, 255, 255));
		btnXoa.setEnabled(true);
		btnLuu.setBackground(new Color(255, 255, 255));

		btnLuu.setEnabled(false);
		btnHuy.setBackground(new Color(255, 255, 255));
		btnHuy.setEnabled(false);

		// Reset và tắt input panel
		setEnableAll(input_panel, false);
		resetInputText();

		// Bật search panel
		setEnableAll(seacrh_panel, true);
	}

	// Cài đặt bật tắt cho container như jpanel
	private void setEnableAll(Component component, boolean enable) {
		input_panel.setBackground(new Color(255, 255, 255));
		component.setEnabled(enable);
		try {
			Component[] components = ((JComponent) component).getComponents();
			for (int i = 0; i < components.length; i++) {
				setEnableAll(components[i], enable);
			}
		} catch (ClassCastException e) {

		}
	}

	// Reset input text
	private void resetInputText() {
		txtPhoneNumber.setText("");
		txtFullName.setText("");
		txtAddress.setText("");
		txtEmail.setText("");
		lblDiscount.setText("Ưu đãi: 0%");
		lblTotalcost.setText("Tổng tiền đã mua: 0vnđ");
	}

	// data binding
	private void data_binding_to_intput_panel(int row) {
		try {

			// binding text
			txtPhoneNumber.setText(table.getValueAt(row, 0).toString());
			txtFullName.setText(table.getValueAt(row, 1).toString());
			txtAddress.setText(table.getValueAt(row, 3).toString());
			txtEmail.setText(table.getValueAt(row, 4).toString());

			// binding giới tính
			String sexString = table.getValueAt(row, 2).toString();
			if (sexString.equals("Nam"))
				rdbtnMale.setSelected(true);
			else
				rdbtnFemale.setSelected(true);

			// binding ưu đãi
			lblTotalcost.setText("Tổng tiền đã mua: " + String.format("%,.0f", table.getValueAt(row, 5)) + "vnđ");
			lblDiscount.setText("Ưu đãi: " + table.getValueAt(row, 6) + "%");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Create the frame.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public QuanLyKhachHang() throws UnsupportedLookAndFeelException, ClassNotFoundException, SQLException {
		getContentPane().setBackground(new Color(255, 255, 255));
		setFrameIcon(null);
		setBorder(null);
		UIManager.setLookAndFeel(new FlatLightLaf());

		setBounds(100, 100, 891, 706);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[][][grow][]"));

		getContentPane().add(input_panel, "cell 0 0,grow");
		input_panel.setLayout(new MigLayout("", "[][][][][][grow]", "[24px][24px][24px][24px][24px][24px]"));

		JLabel lblNewLabel = new JLabel("Số điện thoại:");
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel, "cell 0 0,alignx left,aligny center");

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(txtPhoneNumber, "cell 1 0 5 1,growx,aligny top");
		txtPhoneNumber.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Họ và tên:");
		lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_1, "cell 0 1,alignx left,aligny center");

		txtFullName = new JTextField();
		txtFullName.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		txtFullName.setColumns(10);
		input_panel.add(txtFullName, "cell 1 1 5 1,growx,aligny top");

		JLabel lblNewLabel_2 = new JLabel("Giới tính:");
		lblNewLabel_2.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_2, "cell 0 2,alignx left,aligny center");
		rdbtnMale.setBackground(new Color(255, 255, 255));

		rdbtnMale.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		input_panel.add(rdbtnMale, "cell 1 2,alignx left,aligny center");
		rdbtnMale.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (rdbtnFemale.isSelected() == rdbtnMale.isSelected())
					rdbtnFemale.setSelected(!rdbtnMale.isSelected());
			}
		});

		JLabel lblNewLabel_3 = new JLabel("Địa chỉ:");
		lblNewLabel_3.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_3, "cell 0 3,alignx left,aligny center");

		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		txtAddress.setColumns(10);
		input_panel.add(txtAddress, "cell 1 3 5 1,growx,aligny top");

		JLabel lblNewLabel_4 = new JLabel("Email:");
		lblNewLabel_4.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_4, "cell 0 4,alignx left,aligny center");

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		txtEmail.setColumns(10);
		input_panel.add(txtEmail, "cell 1 4 5 1,growx,aligny top");

		lblDiscount.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblDiscount, "cell 0 5,alignx left,aligny center");
		rdbtnFemale.setBackground(new Color(255, 255, 255));
		rdbtnFemale.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (rdbtnFemale.isSelected() == rdbtnMale.isSelected())
					rdbtnMale.setSelected(!rdbtnFemale.isSelected());
			}
		});

		rdbtnFemale.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		input_panel.add(rdbtnFemale, "cell 3 2,alignx left,aligny center");

		lblTotalcost.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblTotalcost, "cell 5 5,growx,aligny center");
		seacrh_panel.setBackground(new Color(255, 255, 255));

		getContentPane().add(seacrh_panel, "cell 0 1,grow");
		seacrh_panel.setLayout(new MigLayout("", "[grow][][]", "[25px]"));

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
		txtSearch.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		txtSearch.setColumns(100);
		seacrh_panel.add(txtSearch, "cell 0 0,alignx left,aligny center");
		
		btnTim.setIcon(getIcon("resources/icon/search.png", 30, 30));
		btnTim.setBackground(new Color(255, 255, 255));
		btnTim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Lấy sorter ra để tìm kiếm (filter)
				@SuppressWarnings("unchecked")
				TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) table.getRowSorter();

				// Chuỗi cần tìm
				String searchString = txtSearch.getText();
				if (searchString.isBlank() || searchString.equals("Nhập để tìm kiếm")) {
					sorter.setRowFilter(null);
				} else {
					try {
						// Đặt selected row về đầu để tránh lỗi!
						table.setRowSelectionInterval(0, 0);
						sorter.setRowFilter(RowFilter.regexFilter(searchString));

						// Nếu không tìm thấy
						if (table.getRowCount() <= 0) {
							JOptionPane.showMessageDialog(txtSearch,
									"Không tìm thấy kết quả cho '" + searchString + "'");
							sorter.setRowFilter(null);
						}
					} catch (PatternSyntaxException pse) {
						System.out.println("Bad regex pattern");
					}
				}
			}
		});
		btnTim.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));

		seacrh_panel.add(btnTim, "cell 1 0");
		data_panel.setBackground(new Color(255, 255, 255));

		getContentPane().add(data_panel, "cell 0 2,grow");
		data_panel.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JScrollPane scrollPane = new JScrollPane();
		data_panel.add(scrollPane, "cell 0 0,grow");

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				int row = table.getSelectedRow();
				if (row >= 0)
					data_binding_to_intput_panel(row);
			}
		});
		table.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(WIDTH);
		scrollPane.setViewportView(table);
		load_data();
		button_panel.setBackground(new Color(255, 255, 255));

		getContentPane().add(button_panel, "cell 0 3,grow");
		button_panel.setLayout(new MigLayout("", "[79px][63px][63px][63px][63px]", "[29px]"));

		btnThem.setIcon(getIcon("resources/icon/add.png", 30, 30));
		btnThem.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		button_panel.add(btnThem, "cell 0 0,alignx left,aligny top");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Biến Insert = true
				isInsert = true;

				// Bật input_panel và reset all input text
				setEnableAll(input_panel, true);
				resetInputText();

				// Bật nút Hủy, Lưu
				btnHuy.setEnabled(true);
				btnLuu.setEnabled(true);

				// Tắt nút Sửa, Xóa, Thêm, search panel, table
				btnSua.setEnabled(false);
				btnXoa.setEnabled(false);
				btnThem.setEnabled(false);
				setEnableAll(seacrh_panel, false);
				table.setEnabled(false);

				// bật sửa sđt
				txtPhoneNumber.setEditable(true);
			}
		});

		btnSua.setIcon(getIcon("resources/icon/edit.png", 30, 30));
		btnSua.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		button_panel.add(btnSua, "cell 1 0,alignx left,aligny top");
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtPhoneNumber.getText().isBlank())
					JOptionPane.showMessageDialog(txtPhoneNumber, "Vui lòng chọn một hàng trước khi sửa!");
				else {
					// Biến Insert = false
					isInsert = false;

					// Bật input_panel
					setEnableAll(input_panel, true);

					// Bật nút Hủy, Lưu
					btnHuy.setEnabled(true);
					btnLuu.setEnabled(true);

					// Tắt nút Sửa, Xóa, Thêm, search panel, table
					btnSua.setEnabled(false);
					btnXoa.setEnabled(false);
					btnThem.setEnabled(false);
					setEnableAll(seacrh_panel, false);
					table.setEnabled(false);

					// Không cho sửa sđt
					txtPhoneNumber.setEditable(false);
				}
			}
		});

		btnXoa.setIcon(getIcon("resources/icon/delete.png", 30, 30));
		btnXoa.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		button_panel.add(btnXoa, "cell 2 0,alignx left,aligny top");
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtPhoneNumber.getText().isBlank())
					JOptionPane.showMessageDialog(txtPhoneNumber, "Vui lòng chọn một khách hàng muốn xóa!");
				else {
					int opt = JOptionPane.showConfirmDialog(txtFullName,
							"Bạn thật sự muốn xóa Khách hàng: '" + txtFullName.getText() + "' ?");
					if (opt == 0) {
						// Do delete
						try {
							Connection conn = ConnectionUtils.getConnection();
							boolean isSuccess = KhachHangModel.delete_KhachHang(conn, txtPhoneNumber.getText());
							if (isSuccess) {
								JOptionPane.showMessageDialog(txtPhoneNumber, "Xóa khách hàng '" + txtFullName.getText() +"' thành công!");
								load_data();
							}
							else
								JOptionPane.showMessageDialog(txtPhoneNumber, "Xóa khách hàng '" + txtFullName.getText() +"' thất bại!");
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});

		btnLuu.setIcon(getIcon("resources/icon/save.png", 30, 30));
		btnLuu.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		button_panel.add(btnLuu, "cell 3 0,alignx left,aligny top");
		btnLuu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Kiểm tra thông tin nhập vào
				if (isValidInput()) {
					if (isInsert) {
						// Do insert
						try {
							Connection conn = ConnectionUtils.getConnection();
							KhachHang KH = new KhachHang();
							KH.setPhoneNumber(txtPhoneNumber.getText());
							KH.setFullname(txtFullName.getText());
							if (rdbtnMale.isSelected())
								KH.setSex("Nam");
							else
								KH.setSex("Nữ");
							KH.setAddress(txtAddress.getText());
							KH.setEmail(txtEmail.getText());
							boolean isSuccess = KhachHangModel.insert_KhachHang(conn, KH);
							if (isSuccess) {
								JOptionPane.showMessageDialog(txtPhoneNumber, "Thêm khách hàng '" + txtFullName.getText() +"' thành công!");
								load_data();
							}
							else
								JOptionPane.showMessageDialog(txtPhoneNumber, "Thêm khách hàng '" + txtFullName.getText() +"' thất bại!");
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						// Do update
						try {
							Connection conn = ConnectionUtils.getConnection();
							KhachHang KH = new KhachHang();
							KH.setPhoneNumber(txtPhoneNumber.getText());
							KH.setFullname(txtFullName.getText());
							if (rdbtnMale.isSelected())
								KH.setSex("Nam");
							else
								KH.setSex("Nữ");
							KH.setAddress(txtAddress.getText());
							KH.setEmail(txtEmail.getText());
							boolean isSuccess = KhachHangModel.update_KhachHang(conn, KH);
							if (isSuccess) {
								JOptionPane.showMessageDialog(txtPhoneNumber, "Cập nhật thông tin khách hàng '" + txtFullName.getText() +"' thành công!");
								load_data();
							}
							else
								JOptionPane.showMessageDialog(txtPhoneNumber, "Cập nhật thông tin khách hàng '" + txtFullName.getText() +"' thất bại!");
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					// Báo lỗi
					JOptionPane.showMessageDialog(input_panel, "Dữ liệu nhập vào không hợp lệ");
				}
			}
		});
		
		btnHuy.setIcon(getIcon("resources/icon/cancel.png", 30, 30));
		btnHuy.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		button_panel.add(btnHuy, "cell 4 0,alignx left,aligny top");
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					load_data();
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

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
