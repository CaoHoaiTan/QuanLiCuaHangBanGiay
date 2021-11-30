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
import com.sun.jdi.Value;

import DAO.ConnectionUtils;
import MODEL.Account;
import MODEL.AccountModel;
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
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.regex.PatternSyntaxException;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

public class QuanLyNhanVien extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtFullName;
	private JTextField txtAvatar;
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
	private JLabel lblDiscount = new JLabel("SDT:");
	private JButton btnTim = new JButton("Tìm kiếm");
	private boolean isInsert;
	private final JTextField txtSDT = new JTextField();
	private final JLabel lblaCh = new JLabel("Địa chỉ:");
	private final JLabel lblNgySinh = new JLabel("Ngày sinh:");
	private final JLabel lblIsadmin = new JLabel("IsAdmin");
	private final JRadioButton rdbtnTrue = new JRadioButton("True");
	private final JRadioButton rdbtnFalse = new JRadioButton("False");
	private final JTextField txtDiaChi = new JTextField();
	private final JTextField txtNgaySinh = new JTextField();
	private final JLabel lblUsernmae = new JLabel("Tên đăng nhập:");
	private final JLabel lblPassword = new JLabel("Password:");
	private final JTextField txtUserName = new JTextField();
	private final JTextField txtPass = new JTextField();
	private final JPanel panelHead = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JLabel lblAvatar = new JLabel("IMG");

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
		if(txtFullName.getText().isBlank() || txtSDT.getText().isBlank() || txtNgaySinh.getText().isBlank()
				|| txtUserName.getText().isBlank() || txtPass.getText().isBlank()) {
			return false;
		}
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		LocalDate tuoiDate= LocalDate.parse(txtNgaySinh.getText());
		LocalDate namHienTaiDate = LocalDate.now();
		
		if(namHienTaiDate.compareTo(tuoiDate)<18) {
			return false;
		}
		if (txtPass.getText().length() <8) {
			return false;
		}
		return true;
	}

	// Load data
	private void load_data() throws SQLException, ClassNotFoundException {
		// Load dữ liệu lên JTable
		Connection conn = ConnectionUtils.getConnection();
		String[] colName = { "ID", "Họ và tên", "Giới tính", "Avatar", "SDT", "Địa chỉ", "Ngày sinh", "Quyền"
				, "Tên đăng nhập", "Mật khẩu" };
		final Class<?>[] colClass = new Class[] { Integer.class, String.class, String.class, String.class, String.class,
				String.class, String.class, String.class, String.class, String.class};
		Object[][] data = AccountModel.load_Nhanvien_to_Obj(conn);

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

		// Bật tắt các nút
		btnThem.setEnabled(true);
		btnSua.setEnabled(true);
		btnXoa.setEnabled(true);

		btnLuu.setEnabled(false);
		btnHuy.setEnabled(false);

		// Reset và tắt input panel
		setEnableAll(input_panel, false);
		
		resetInputText();
		
		// Bật search panel
		setEnableAll(seacrh_panel, true);
	}

	// Cài đặt bật tắt cho container như jpanel
	private void setEnableAll(Component component, boolean enable) {
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
		txtUserName.setText("");
		txtPass.setText("");
		txtFullName.setText("");
		txtAvatar.setText("");
		txtNgaySinh.setText("");
		txtSDT.setText("");
		txtDiaChi.setText("");
		txtID.setText("");
		rdbtnFalse.setSelected(false);
		rdbtnTrue.setSelected(false);
		rdbtnMale.setSelected(false);
		rdbtnFemale.setSelected(false);
		
		
		
	}

	// data binding
	private void data_binding_to_intput_panel(int row) {
		try {

			// binding text
			txtID.setText(table.getValueAt(row, 0).toString());
			txtFullName.setText(table.getValueAt(row, 1).toString());
			txtAvatar.setText(table.getValueAt(row, 3).toString());
			txtSDT.setText(table.getValueAt(row, 4).toString());
			txtDiaChi.setText(table.getValueAt(row, 5).toString());
			txtNgaySinh.setText(table.getValueAt(row, 6).toString());
			txtUserName.setText(table.getValueAt(row, 8).toString());
			txtPass.setText(table.getValueAt(row, 9).toString());
			
			// binding quyền
			String isAminString=table.getValueAt(row, 7).toString();
			if(isAminString.equals("1"))
				rdbtnTrue.setSelected(true);
			else {
				rdbtnFalse.setSelected(true);
			}
				

			// binding giới tính
			String sexString = table.getValueAt(row, 2).toString();
			if (sexString.equals("Nam"))
				rdbtnMale.setSelected(true);
			else
				rdbtnFemale.setSelected(true);

			// binding image
			ImageIcon imgNhanVien = new ImageIcon("resources/" + txtAvatar.getText());
			Image img = imgNhanVien.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			imgNhanVien = new ImageIcon(img);
			lblAvatar.setIcon(imgNhanVien);
			lblAvatar.setText("");
			
			
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
	public QuanLyNhanVien() throws UnsupportedLookAndFeelException, ClassNotFoundException, SQLException {
		setFrameIcon(null);
		setBorder(null);
		UIManager.setLookAndFeel(new FlatLightLaf());

		setBounds(100, 100, 891, 706);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[][][grow][]"));

		
		getContentPane().add(panelHead, "cell 0 0,grow");
		input_panel.setLayout(new MigLayout("", "[][150px][100px][100px][grow]", "[24px,grow][24px][24px][24px][24px][24px][][][][][24px]"));	

		panelHead.setLayout(new MigLayout("", "[grow][200px]", "[312px,grow]"));
		panelHead.add(input_panel, "cell 0 0,grow");
		
		
		panelHead.add(panel_1, "cell 1 0,grow");
		panel_1.setLayout(new BorderLayout(0, 0));
		lblAvatar.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblAvatar, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(lblNewLabel, "cell 1 0,alignx left,aligny center");

		txtID = new JTextField();
		txtID.setHorizontalAlignment(SwingConstants.LEFT);
		txtID.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(txtID, "cell 2 0 3 1,growx,aligny top");
		txtID.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Họ và tên:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(lblNewLabel_1, "cell 1 1,alignx left,aligny center");

		txtFullName = new JTextField();
		txtFullName.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtFullName.setColumns(10);
		input_panel.add(txtFullName, "cell 2 1 3 1,growx,aligny top");

		JLabel lblNewLabel_2 = new JLabel("Giới tính:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(lblNewLabel_2, "cell 1 2,alignx left,aligny top");

		rdbtnMale.setFont(new Font("Tahoma", Font.BOLD, 14));
		input_panel.add(rdbtnMale, "cell 2 2,alignx left,aligny center");
		rdbtnMale.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (rdbtnFemale.isSelected() == rdbtnMale.isSelected())
					rdbtnFemale.setSelected(!rdbtnMale.isSelected());
			}
		});
		rdbtnFemale.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (rdbtnFemale.isSelected() == rdbtnMale.isSelected())
					rdbtnMale.setSelected(!rdbtnFemale.isSelected());
			}
		});
		
				rdbtnFemale.setFont(new Font("Tahoma", Font.BOLD, 14));
				input_panel.add(rdbtnFemale, "cell 3 2,alignx left,aligny center");

		JLabel lblNewLabel_3 = new JLabel("Avatar (link):");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(lblNewLabel_3, "cell 1 3,alignx left,aligny center");

		txtAvatar = new JTextField();
		txtAvatar.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtAvatar.setColumns(10);
		input_panel.add(txtAvatar, "cell 2 3 3 1,growx,aligny top");

		lblDiscount.setFont(new Font("Tahoma", Font.BOLD, 16));
		input_panel.add(lblDiscount, "cell 1 4,alignx left,aligny center");
		txtSDT.setText("");
		txtSDT.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtSDT.setColumns(10);
		
		input_panel.add(txtSDT, "cell 2 4 3 1,growx");
		lblaCh.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		input_panel.add(lblaCh, "cell 1 5,alignx left,aligny center");
		txtDiaChi.setText("");
		txtDiaChi.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtDiaChi.setColumns(10);
		
		input_panel.add(txtDiaChi, "cell 2 5 3 1,growx,aligny top");
		lblNgySinh.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		input_panel.add(lblNgySinh, "cell 1 6,alignx left,aligny center");
		txtNgaySinh.setText("");
		txtNgaySinh.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtNgaySinh.setColumns(10);
		
		input_panel.add(txtNgaySinh, "cell 2 6 2 1,growx");
		lblIsadmin.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		input_panel.add(lblIsadmin, "cell 1 7");
		rdbtnTrue.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (rdbtnTrue.isSelected() == rdbtnFalse.isSelected())
					rdbtnFalse.setSelected(!rdbtnTrue.isSelected());
			}
		});
		rdbtnTrue.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		input_panel.add(rdbtnTrue, "cell 2 7,alignx left,aligny center");
		rdbtnFalse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (rdbtnTrue.isSelected() == rdbtnFalse.isSelected())
					rdbtnTrue.setSelected(!rdbtnFalse.isSelected());
			}
		});
		rdbtnFalse.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		input_panel.add(rdbtnFalse, "cell 3 7,alignx left,aligny center");
		lblUsernmae.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		input_panel.add(lblUsernmae, "cell 1 8");
		txtUserName.setText("");
		txtUserName.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtUserName.setColumns(10);
		
		input_panel.add(txtUserName, "cell 2 8 3 1,growx");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		input_panel.add(lblPassword, "cell 1 9,aligny baseline");
		txtPass.setText("");
		txtPass.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtPass.setColumns(10);
		
		input_panel.add(txtPass, "cell 2 9 3 1,growx,aligny top");

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
		txtSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtSearch.setColumns(100);
		seacrh_panel.add(txtSearch, "cell 0 0,alignx left,aligny center");
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
		btnTim.setFont(new Font("Tahoma", Font.BOLD, 16));

		seacrh_panel.add(btnTim, "cell 1 0");

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
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(WIDTH);
		scrollPane.setViewportView(table);
		load_data();

		getContentPane().add(button_panel, "cell 0 3,grow");
		button_panel.setLayout(new MigLayout("", "[79px][63px][63px][63px][63px]", "[29px]"));

		btnThem.setFont(new Font("Tahoma", Font.BOLD, 16));
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

				
				// Không cho nhập ID
				txtID.setEnabled(false);
			}
		});

		btnSua.setFont(new Font("Tahoma", Font.BOLD, 16));
		button_panel.add(btnSua, "cell 1 0,alignx left,aligny top");
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtID.getText().isBlank())
					JOptionPane.showMessageDialog(txtID, "Vui lòng chọn một Account trước khi sửa!");
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
					txtID.setEditable(false);
				}
			}
		});

		btnXoa.setFont(new Font("Tahoma", Font.BOLD, 16));
		button_panel.add(btnXoa, "cell 2 0,alignx left,aligny top");
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtID.getText().isBlank())
					JOptionPane.showMessageDialog(txtID, "Vui lòng chọn một nhân viên muốn xóa!");
				else {
					int opt = JOptionPane.showConfirmDialog(txtFullName,
							"Bạn thật sự muốn xóa Nhân viên: '" + txtFullName.getText() + "' ?");
					if (opt == 0) {
						// Do delete
						try {
							Connection conn = ConnectionUtils.getConnection();
							boolean isSuccess = AccountModel.delete_NhanVien(conn, txtID.getText());
							if (isSuccess) {
								JOptionPane.showMessageDialog(txtID, "Xóa Nhân viên '" + txtFullName.getText() +"' thành công!");
								load_data();
							}
							else
								JOptionPane.showMessageDialog(txtID, "Xóa Nhân viên '" + txtFullName.getText() +"' thất bại!");
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});

		btnLuu.setFont(new Font("Tahoma", Font.BOLD, 16));
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
							Account NV = new Account();
							
							NV.setUsername(txtUserName.getText());
							NV.setPassword(txtPass.getText());
							NV.setFullname(txtFullName.getText());
							NV.setAvatar(txtAvatar.getText());
							NV.setPhoneNumber(txtSDT.getText());
							NV.setAddress(txtDiaChi.getText());

							if (rdbtnMale.isSelected())
								NV.setSex("Nam");
							else
								NV.setSex("Nữ");
							NV.setDateOfBirth(Date.valueOf(txtNgaySinh.getText()));
							if (rdbtnTrue.isSelected())
								NV.setIsAdmin(1);
							else
								NV.setIsAdmin(0);
//							KH.setEmail(txtEmail.getText());
							boolean isSuccess = AccountModel.insert_NhanVien(conn, NV);
							if (isSuccess) {
								JOptionPane.showMessageDialog(txtID, "Thêm Nhân viên '" + txtFullName.getText() +"' thành công!");
								load_data();
							}
							else
								JOptionPane.showMessageDialog(txtID, "Thêm Nhân viên '" + txtFullName.getText() +"' thất bại!");
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						// Do update
						try {
							Connection conn = ConnectionUtils.getConnection();
							Account NV = new Account();
							NV.setId(Integer.valueOf(txtID.getText()));
							NV.setUsername(txtUserName.getText());
							NV.setPassword(txtPass.getText());
							NV.setFullname(txtFullName.getText());
							NV.setAvatar(txtAvatar.getText());
							NV.setPhoneNumber(txtSDT.getText());
							NV.setAddress(txtDiaChi.getText());

							if (rdbtnMale.isSelected())
								NV.setSex("Nam");
							else
								NV.setSex("Nữ");
							NV.setDateOfBirth(Date.valueOf(txtNgaySinh.getText()));
							if (rdbtnTrue.isSelected())
								NV.setIsAdmin(1);
							else
								NV.setIsAdmin(0);
							boolean isSuccess = AccountModel.update_NhanVien(conn, NV);
							if (isSuccess) {
								JOptionPane.showMessageDialog(txtID, "Cập nhật thông tin Nhân Viên '" + txtFullName.getText() +"' thành công!");
								load_data();
							}
							else
								JOptionPane.showMessageDialog(txtID, "Cập nhật thông tin Nhân Viên '" + txtFullName.getText() +"' thất bại!");
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} else {
					// Báo lỗi
					JOptionPane.showMessageDialog(input_panel, "Dữ liệu nhập vào không hợp lệ\n"
							+ "Điều kiện:\n"
							+ "Họ tên, sdt, ngày sinh(>17 tuổi), username,password(>7 kí tự) không được bỏ trống!");
				}
			}
		});

		btnHuy.setFont(new Font("Tahoma", Font.BOLD, 16));
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