package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.formdev.flatlaf.FlatLightLaf;

import DAO.ConnectionUtils;
import MODEL.SanPham;
import MODEL.SanPhamModel;
import MODEL.ThuongHieu;
import MODEL.ThuongHieuModel;

import java.awt.Font;
import java.awt.Image;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.awt.Component;

import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class QuanLyHang extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextArea txtName;
	private final JTextField txtImage;
	private final JTextArea txtDescribe;
	private final JTextField txtCost;
	private final JTable table;
	private final JPanel button_panel = new JPanel();
	private final JLabel lblQuantity = new JLabel("Còn lại: 0");
	private final JLabel lblSaleDate = new JLabel("Ngày bán: dd/mm/yyyy");
	private final JPanel right_panel = new JPanel();
	private final JComboBox<ThuongHieu> cmbBrand = new JComboBox<ThuongHieu>();
	private final JPanel input_panel = new JPanel();
	private final JPanel img_panel = new JPanel();
	private final JPanel left_panel = new JPanel();
	private final JButton btnHuy = new JButton("Hủy");
	private final JButton btnLuu = new JButton("Lưu");
	private final JButton btnXoa = new JButton("Xóa");
	private final JButton btnSua = new JButton("Sửa");
	private final JPanel seacrh_panel = new JPanel();
	private final JTextField txtSearch = new JTextField();
	private final JButton btnTim = new JButton("Tìm kiếm");
	private final JLabel lblImage = new JLabel("IMG");
	private List<ThuongHieu> listTH;
	private final JLabel lblId = new JLabel("ID:");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuanLyHang frame = new QuanLyHang();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
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

	// Load data
	private void load_data() throws SQLException, ClassNotFoundException {
		// Connection
		Connection conn = ConnectionUtils.getConnection();

		// Combobox ThuongHieu
		cmbBrand.removeAllItems();
		listTH = ThuongHieuModel.load_ThuongHieu(conn);
		for (ThuongHieu th : listTH)
			cmbBrand.addItem(th);

		// table
		String[] colName = { "ID", "Thương hiệu", "Tên sản phẩm", "Ảnh", "Mô tả", "Số lượng", "Giá bán", "Ngày bán" };
		Object[][] data = SanPhamModel.load_SanPham_to_Obj(conn);
		DefaultTableModel dfModel = new DefaultTableModel(data, colName) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Object getValueAt(int row, int column) {
				// Cột thương hiệu
				if (column == 1)
					return findTH(Integer.valueOf(super.getValueAt(row, column).toString()));
				return super.getValueAt(row, column);
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

		// Ẩn các column
		hideColumn(3); // Ảnh
		hideColumn(4);
		btnSua.setBackground(new Color(255, 255, 255));
		btnSua.setEnabled(true);
		btnXoa.setBackground(new Color(255, 255, 255));
		btnXoa.setEnabled(true);
		btnLuu.setBackground(new Color(255, 255, 255));

		btnLuu.setEnabled(false);
		btnHuy.setBackground(new Color(255, 255, 255));
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					load_data();
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnHuy.setEnabled(false);

		// Reset và tắt input panel
		setEnableAll(input_panel, false);
		resetLeftPanel();

		// Bật search panel
		setEnableAll(seacrh_panel, true);
	}

	// Ẩn cột trong bảng bằng cách set witdh = 0
	private void hideColumn(int index) {
		table.getColumnModel().getColumn(index).setMinWidth(0);
		table.getColumnModel().getColumn(index).setMaxWidth(0);
		table.getColumnModel().getColumn(index).setWidth(0);
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

	// Reset left panel
	private void resetLeftPanel() {
		// reset input panel
		txtName.setText("");
		txtImage.setText("");
		txtDescribe.setText("");
		txtCost.setText("");
		lblQuantity.setText("Còn lại: 0");
		lblSaleDate.setText("Ngày bán: dd/mm/yyyy");
		lblId.setText("ID: ");

		// reset IMG
		lblImage.setIcon(null);
		lblImage.setText("IMG");
	}

	// Tìm thương hiệu trong listTH
	private ThuongHieu findTH(int maTH) {
		for (ThuongHieu th : listTH)
			if (th.getId() == maTH)
				return th;
		return null;
	}

	// data binding
	private void data_binding_to_left_panel(int row) {
		try {
			// binding combobox
			cmbBrand.setSelectedItem(table.getValueAt(row, 1));

			// binding text
			txtName.setText(table.getValueAt(row, 2).toString());
			txtImage.setText(table.getValueAt(row, 3).toString());
			txtDescribe.setText(table.getValueAt(row, 4).toString());
			txtCost.setText(table.getValueAt(row, 6).toString() + " vnđ");

			// binding label
			lblQuantity.setText("Còn lại:" + table.getValueAt(row, 5));
			lblSaleDate.setText("Ngày bán: " + table.getValueAt(row, 7));
			lblId.setText("ID: " + table.getValueAt(row, 0));

			// binding image
			ImageIcon imgSanPham = new ImageIcon("resources/" + txtImage.getText());
			Image img = imgSanPham.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
			imgSanPham = new ImageIcon(img);
			lblImage.setIcon(imgSanPham);
			lblImage.setText("");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public QuanLyHang() throws UnsupportedLookAndFeelException, ClassNotFoundException, SQLException {
		getContentPane().setBackground(new Color(255, 255, 255));
		setFrameIcon(null);
		setBorder(null);
		UIManager.setLookAndFeel(new FlatLightLaf());

		setBounds(100, 100, 891, 706);
		getContentPane().setLayout(new MigLayout("", "[grow][growprio 101,grow]", "[grow]"));
		left_panel.setBackground(new Color(255, 255, 255));

		getContentPane().add(left_panel, "cell 0 0,grow");
		left_panel.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
		img_panel.setBackground(new Color(255, 255, 255));

		left_panel.add(img_panel, "cell 0 0,grow");
		img_panel.setLayout(new MigLayout("", "[grow][grow,center][grow]", "[grow]"));

		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		img_panel.add(lblImage, "cell 1 0,grow");

		left_panel.add(input_panel, "cell 0 1,grow");
		input_panel.setLayout(new MigLayout("", "[][grow][]", "[grow][grow][grow][grow][grow][grow]"));

		JLabel lblNewLabel = new JLabel("Thương hiệu:");
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel, "cell 0 0,alignx left,aligny center");

		cmbBrand.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
		input_panel.add(cmbBrand, "cell 1 0 2 1,growx");

		JLabel lblNewLabel_1 = new JLabel("Tên sản phẩm:");
		lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_1, "cell 0 1,alignx left,aligny top");

		txtName = new JTextArea();
		txtName.setLineWrap(true);
		txtName.setTabSize(2);
		txtName.setRows(1);
		txtName.setFont(new Font("Segoe UI Semibold", Font.BOLD, 12));
		input_panel.add(txtName, "cell 1 1 2 1,growx,aligny top");
		txtName.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Ảnh:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_1_1, "cell 0 2,alignx left");

		txtImage = new JTextField();
		txtImage.setFont(new Font("Segoe UI Semibold", Font.BOLD, 12));
		txtImage.setColumns(10);
		input_panel.add(txtImage, "cell 1 2 2 1,growx");

		JLabel lblNewLabel_1_1_1 = new JLabel("Mô tả:");
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_1_1_1, "cell 0 3,alignx left,aligny top");

		txtDescribe = new JTextArea();
		txtDescribe.setLineWrap(true);
		txtDescribe.setTabSize(2);
		txtDescribe.setRows(5);
		txtDescribe.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		txtDescribe.setColumns(10);
		input_panel.add(txtDescribe, "cell 1 3 2 1,growx");

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Giá:");
		lblNewLabel_1_1_1_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_1_1_1_1, "cell 0 4,alignx left");

		txtCost = new JTextField();
		txtCost.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		txtCost.setColumns(10);
		input_panel.add(txtCost, "cell 1 4 2 1,growx");

		lblQuantity.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		input_panel.add(lblQuantity, "cell 0 5");

		lblSaleDate.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		input_panel.add(lblSaleDate, "cell 1 5");
		lblId.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));

		input_panel.add(lblId, "cell 2 5");
		right_panel.setBackground(new Color(255, 255, 255));

		getContentPane().add(right_panel, "cell 1 0,grow");
		right_panel.setLayout(new MigLayout("", "[grow]", "[][grow][]"));
		seacrh_panel.setBackground(new Color(255, 255, 255));

		right_panel.add(seacrh_panel, "cell 0 0,grow");
		seacrh_panel.setLayout(new MigLayout("", "[grow][]", "[]"));
		btnTim.setBackground(new Color(255, 255, 255));
		btnTim.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		seacrh_panel.add(btnTim, "cell 1 0");
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

		txtSearch.setText("Nhập để tìm kiếm");
		txtSearch.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		txtSearch.setColumns(10);
		seacrh_panel.add(txtSearch, "cell 0 0,growx");

		final JScrollPane scrollPane = new JScrollPane();
		right_panel.add(scrollPane, "cell 0 1,grow");

		table = new JTable();
		scrollPane.setViewportView(table);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				int row = table.getSelectedRow();
				if (row >= 0)
					data_binding_to_left_panel(row);
			}
		});
		button_panel.setBackground(new Color(255, 255, 255));

		right_panel.add(button_panel, "cell 0 2,alignx right,growy");

		btnSua.setIcon(getIcon("resources/icon/edit.png", 30, 30));
		btnSua.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		button_panel.add(btnSua);
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Lấy id ra
				String id = lblId.getText().replace("ID: ", "");
				if (id.isBlank())
					JOptionPane.showMessageDialog(scrollPane, "Vui lòng chọn một hàng trước khi sửa!");
				else {
					// Bật input_panel
					setEnableAll(input_panel, true);

					// Bật nút Hủy, Lưu
					btnHuy.setEnabled(true);
					btnLuu.setEnabled(true);

					// Tắt nút Sửa, Xóa, Thêm, search panel, table
					btnSua.setEnabled(false);
					btnXoa.setEnabled(false);
					setEnableAll(seacrh_panel, false);
					table.setEnabled(false);
				}
			}
		});

		btnXoa.setIcon(getIcon("resources/icon/delete.png", 30, 30));
		btnXoa.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		button_panel.add(btnXoa);
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Lấy id ra
				String id = lblId.getText().replace("ID: ", "");
				if (id.isBlank())
					JOptionPane.showMessageDialog(scrollPane, "Vui lòng chọn một sản phẩm muốn xóa!");
				else {
					int opt = JOptionPane.showConfirmDialog(input_panel,
							"Bạn thật sự muốn xóa mặt hàng: '" + txtName.getText() + "' ?");
					if (opt == 0) {
						// Do delete
						try {
							Connection conn = ConnectionUtils.getConnection();
							boolean isSuccess = SanPhamModel.delete_SanPham(conn, id);
							if (isSuccess) {
								JOptionPane.showMessageDialog(input_panel,
										"Xóa mặt hàng '" + txtName.getText() + "' thành công!");
								load_data();
							} else
								JOptionPane.showMessageDialog(input_panel,
										"Xóa mặt hàng '" + txtName.getText() + "' thất bại!");
						} catch (ClassNotFoundException | SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		btnLuu.setIcon(getIcon("resources/icon/save.png", 30, 30));
		btnLuu.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		button_panel.add(btnLuu);
		btnLuu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Lấy id ra
				String id = lblId.getText().replace("ID: ", "");
				// Do update
				try {
					Connection conn = ConnectionUtils.getConnection();
					SanPham SP = new SanPham();
					SP.setId(Integer.valueOf(id));
					SP.setIdBrand(((ThuongHieu) cmbBrand.getSelectedItem()).getId());
					SP.setName(txtName.getText());
					SP.setImage(txtImage.getText());
					SP.setDescribe(txtDescribe.getText());

					// Lấy giá tiền
					String costString = txtCost.getText().replace(" vnđ", "");
					costString = costString.replace(",", "");
					SP.setCost(Double.valueOf(costString));
					boolean isSuccess = SanPhamModel.update_SanPham(conn, SP);
					if (isSuccess) {
						JOptionPane.showMessageDialog(input_panel,
								"Cập nhật thông tin mặt hàng '" + txtName.getText() + "' thành công!");
						load_data();
					} else
						JOptionPane.showMessageDialog(input_panel,
								"Cập nhật thông tin mặt hàng '" + txtName.getText() + "' thất bại!");
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		
		btnHuy.setIcon(getIcon("resources/icon/cancel.png", 30, 30));
		btnHuy.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		button_panel.add(btnHuy);
		load_data();

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
