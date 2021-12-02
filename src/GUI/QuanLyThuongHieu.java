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
import MODEL.ThuongHieu;
import MODEL.ThuongHieuModel;

import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;

public class QuanLyThuongHieu extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtTenTH;
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
	private JButton btnTim = new JButton("Tìm kiếm");
	private boolean isInsert;
	private final JTextField txtMaTH = new JTextField();
	private final JLabel lblNewLabel_4_1 = new JLabel("Logo (Link):");
	private final JTextField txtLogo = new JTextField();
	private final JLabel lblImageLogo = new JLabel("IMG");

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
		if (txtTenTH.getText().isBlank() || txtEmail.getText().isBlank()) {
			return false;
		}
		return true;
	}

	// Load data
	private void load_data() throws SQLException, ClassNotFoundException {
		// Load dữ liệu lên JTable
		Connection conn = ConnectionUtils.getConnection();
		String[] colName = { "Mã thương hiệu", "Tên TH", "Email","Logo"};
		final Class<?>[] colClass = new Class[] { String.class, String.class, String.class, String.class };
		Object[][] data = ThuongHieuModel.load_ThuongHieu_to_Obj(conn);

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
		lblImageLogo.setEnabled(true);
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
		txtMaTH.setText("");
		txtTenTH.setText("");
		txtEmail.setText("");
		txtLogo.setText("");
	}

	// data binding
	private void data_binding_to_intput_panel(int row) {
		try {

			// binding text
			txtMaTH.setText(table.getValueAt(row, 0).toString());
			txtTenTH.setText(table.getValueAt(row, 1).toString());
			txtEmail.setText(table.getValueAt(row, 2).toString());
			txtLogo.setText(table.getValueAt(row, 3).toString());
			
			// binding image
			ImageIcon imgThuongHieu = new ImageIcon("resources/" + txtLogo.getText());
			Image img = imgThuongHieu.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			imgThuongHieu = new ImageIcon(img);
			lblImageLogo.setIcon(imgThuongHieu);
			lblImageLogo.setText("");
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
	public QuanLyThuongHieu () throws UnsupportedLookAndFeelException, ClassNotFoundException, SQLException {
		setFrameIcon(null);
		setBorder(null);
		UIManager.setLookAndFeel(new FlatLightLaf());

		setBounds(100, 100, 891, 706);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[][][grow][]"));

		getContentPane().add(input_panel, "cell 0 0,grow");
		input_panel.setLayout(new MigLayout("", "[][grow][][grow][][grow]", "[24px,grow][24px][][24px,grow][24px][24px][24px]"));

		JLabel lblNewLabel = new JLabel("Mã thương hiệu:");
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel, "cell 0 0,alignx trailing,aligny center");
		txtMaTH.setText("");
		txtMaTH.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		txtMaTH.setColumns(10);
		
		input_panel.add(txtMaTH, "cell 1 0,alignx left");

		JLabel lblNewLabel_1 = new JLabel("Tên thương hiệu:");
		lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_1, "cell 0 1,alignx left,aligny center");

		txtTenTH = new JTextField();
		txtTenTH.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		txtTenTH.setColumns(10);
		input_panel.add(txtTenTH, "cell 1 1,growx,aligny top");
				
				input_panel.add(lblImageLogo, "cell 3 0 2 6,alignx center,aligny center");
		
				JLabel lblNewLabel_4 = new JLabel("Email:");
				lblNewLabel_4.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
				input_panel.add(lblNewLabel_4, "cell 0 2,alignx left,aligny center");
		
				txtEmail = new JTextField();
				txtEmail.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
				txtEmail.setColumns(10);
				input_panel.add(txtEmail, "cell 1 2,growx,aligny top");
				lblNewLabel_4_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
				
				input_panel.add(lblNewLabel_4_1, "cell 0 3,alignx left");
				txtLogo.setText("");
				txtLogo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
				txtLogo.setColumns(10);
				
				input_panel.add(txtLogo, "cell 1 3,growx");

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

		getContentPane().add(data_panel, "cell 0 2,grow");
		data_panel.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JScrollPane scrollPane = new JScrollPane();
		data_panel.add(scrollPane, "cell 0 0,grow");

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				data_binding_to_intput_panel(row);
			}
		});
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
				
				// Không cho nhập MaTH
				txtMaTH.setEditable(false);

				
			}
		});

		btnSua.setIcon(getIcon("resources/icon/edit.png", 30, 30));
		btnSua.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		button_panel.add(btnSua, "cell 1 0,alignx left,aligny top");
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtMaTH.getText().isBlank())
					JOptionPane.showMessageDialog(txtMaTH, "Vui lòng chọn một thương hiệu trước khi sửa!");
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

					// Không cho sửa MaTH
					txtMaTH.setEditable(false);
				}
			}
		});

		btnXoa.setIcon(getIcon("resources/icon/delete.png", 30, 30));
		btnXoa.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		button_panel.add(btnXoa, "cell 2 0,alignx left,aligny top");
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtMaTH.getText().isBlank())
					JOptionPane.showMessageDialog(txtMaTH, "Vui lòng chọn một Thương hiệu muốn xóa!");
				else {
					int opt = JOptionPane.showConfirmDialog(txtTenTH,
							"Bạn thật sự muốn xóa Thương hiệu: '" + txtTenTH.getText() + "' ?");
					if (opt == 0) {
						// Do delete
						try {
							Connection conn = ConnectionUtils.getConnection();
							boolean isSuccess = ThuongHieuModel.delete_ThuongHieu(conn, txtMaTH.getText());
							if (isSuccess) {
								JOptionPane.showMessageDialog(txtMaTH, "Xóa khách hàng '" + txtTenTH.getText() +"' thành công!");
								load_data();
							}
							else
								JOptionPane.showMessageDialog(txtMaTH, "Xóa khách hàng '" + txtTenTH.getText() +"' thất bại!");
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
							ThuongHieu TH = new ThuongHieu();
							
							TH.setName(txtTenTH.getText());
							TH.setEmail(txtEmail.getText());
							TH.setLogo(txtLogo.getText());
							boolean isSuccess = ThuongHieuModel.insert_ThuongHieu(conn, TH);
							if (isSuccess) {
								JOptionPane.showMessageDialog(txtMaTH, "Thêm Thương hiệu '" + txtTenTH.getText() +"' thành công!");
								load_data();
							}
							else
								JOptionPane.showMessageDialog(txtMaTH, "Thêm Thương hiệu '" + txtTenTH.getText() +"' thất bại!");
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						// Do update
						try {
							Connection conn = ConnectionUtils.getConnection();
							ThuongHieu TH = new ThuongHieu();
							TH.setId(Integer.valueOf(txtMaTH.getText()));
							TH.setName(txtTenTH.getText());
							TH.setEmail(txtEmail.getText());			
							TH.setLogo(txtLogo.getText());
							boolean isSuccess = ThuongHieuModel.update_ThuongHieu(conn, TH);
							if (isSuccess) {
								JOptionPane.showMessageDialog(txtMaTH, "Cập nhật thông tin khách hàng '" + txtTenTH.getText() +"' thành công!");
								load_data();
							}
							else
								JOptionPane.showMessageDialog(txtMaTH, "Cập nhật thông tin khách hàng '" + txtTenTH.getText() +"' thất bại!");
						} catch (ClassNotFoundException | SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
					}
				} else {
					// Báo lỗi
					JOptionPane.showMessageDialog(input_panel, "Dữ liệu nhập vào không hợp lệ \n"
							+ "Điều kiện:\n"
							+ "Tên và email thương hiệu là bắt buộc");
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
