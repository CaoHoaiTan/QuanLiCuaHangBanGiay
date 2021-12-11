package GUI;

import java.awt.Component;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import DAO.ConnectionUtils;
import MODEL.*;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Font;
import java.awt.Image;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class QuanLyDonHang extends JInternalFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final JTextField txtTenKH;
	private final JTextField txtAddress;
	private final JTextField txtTotalCost;
	private final JTextField txtSearch;
	private final JComboBox<String> cmbPhone;
	private JTable tblDetail;
	private JScrollPane scrollPane = new JScrollPane();
	private final JTextField txtTenNV;
	private JButton btnHuy = new JButton("Hủy");
	private JButton btnLuu = new JButton("Lưu");
	private JButton btnXoa = new JButton("Xóa");
	private JButton btnSua = new JButton("Sửa");
	private JButton btnThem = new JButton("Thêm");
	private final JButton btnTim;
	private final JPanel search_panel;
	private final JPanel input_panel;
	private boolean them = true;
	private boolean tim = true;
	private DefaultTableModel model;

	private int discount = 0;

	private int rowtable=0;
	private final JLabel lblDiscount;
	private final JLabel lblTotalcost;
	private final JTextField txtMaNV;

	JComboBox<String> addcusToTable(JComboBox<String> cmbx)
	{
		//JComboBox cbxBox = null;
		try {
			Connection conn = ConnectionUtils.getConnection();
			Statement st = conn.createStatement();

			String sql = "Select * from Product";

			ResultSet rs = st.executeQuery(sql);
			//	cbxBox = new JComboBox();
			while(rs.next())
			{
				cmbx.addItem(rs.getString(1));
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.print(e.getMessage());
		}
		return cmbx;
	}
	void initDataTable(JComboBox<String> cmbx) {
		DefaultCellEditor cusCell = new DefaultCellEditor(addcusToTable(cmbx));

		tblDetail.getColumnModel().getColumn(1).setCellEditor(cusCell);
		tblDetail.setColumnSelectionAllowed(true);

	}
	//Tạo table
	void createTable()
	{
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 2,grow");

		//Tạo table
		tblDetail = new JTable();

		//Tạo DefaultTableModel
		model = new DefaultTableModel();

		model.addColumn("STT");
		model.addColumn("Mã Sản phẩm");
		model.addColumn("Tên sản phẩm");
		model.addColumn("Đơn giá");
		model.addColumn("Số lượng");
		model.addColumn("Thành tiền");


		Object[] data = {1,"","",0,0,""};

		model.addRow(data);


		tblDetail.setModel(model);

		tblDetail.getColumnModel().getColumn(1);
		JComboBox<String> cmbHH = new JComboBox<String>();


		//Add comboBox
		initDataTable(cmbHH);




		tblDetail.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub

				// Tự động tính thành tiền
				if(e.getColumn()==3 || e.getColumn() ==4) {
					int row = e.getFirstRow();
					int gia = Integer.valueOf(tblDetail.getValueAt(row, 3).toString());
					int soLuong =  Integer.valueOf(tblDetail.getValueAt(row, 4).toString());

					if(gia >=0 && soLuong >=0) {
						float thanhtien = gia * soLuong;
						Float sum = Float.parseFloat(txtTotalCost.getText());
						sum += thanhtien - thanhtien * discount /100;
						txtTotalCost.setText(String.format("%.0f", sum));

						tblDetail.setValueAt(String.format("%.0f", thanhtien), row, 5);

					}
					//Update lại tổng tiền đơn hàng

					float sum = 0;


					for(int i=0;i< tblDetail.getRowCount();i++)
					{
						if(tblDetail.getValueAt(i,5) != "")
							sum += Float.parseFloat(String.valueOf(tblDetail.getValueAt(i,5)));
					}

					sum -= sum * discount /100;
					txtTotalCost.setText(String.format("%.0f", sum));



				}
				else if(e.getColumn() == 1)
				{
					//CHọn mã hàng hóa để lấy giá tiền
					int row = e.getFirstRow();
					int col = e.getColumn();

					int maSP = Integer.parseInt(tblDetail.getValueAt(row, col).toString());
					try {
						Connection conn = ConnectionUtils.getConnection();
						double gia = SanPhamModel.getSanPhamBymaSP(conn, maSP).getCost();
						String tenSP = SanPhamModel.getSanPhamBymaSP(conn, maSP).getName();

						tblDetail.setValueAt(tenSP, row, 2);
						tblDetail.setValueAt(String.format("%.0f", gia), row, 3);
						//
						tblDetail.setValueAt(1, row, 4);

					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}



					if(e.getFirstRow() == tblDetail.getRowCount() - 1)
					{
						model.addRow(new Object[] {tblDetail.getRowCount() + 1 ,"","",0,0,""});

						tblDetail.getColumnModel().getColumn(1);
						JComboBox<String> cmbHH = new JComboBox<String>();


						//Add comboBox
						initDataTable(cmbHH);

						tblDetail.setModel(model);

					}

				}


			}
		});


		scrollPane.setViewportView(tblDetail);


		tblDetail.setEnabled(false);

		//
		them = true;
		tim = true;

		// Bật tắt các nút
		btnThem.setEnabled(true);
		btnSua.setEnabled(false);
		btnXoa.setEnabled(true);
		btnTim.setEnabled(true);

		btnLuu.setEnabled(false);
		btnHuy.setEnabled(false);

		// Reset và tắt input panel
		setEnableAll(input_panel, false);
		resetInputText();

		// Bật search panel
		setEnableAll(search_panel, true);
	}



	private void AddItemPhone(JComboBox<String> cmbPhone) throws SQLException, ClassNotFoundException {
		Connection conn = ConnectionUtils.getConnection();
		Statement st = conn.createStatement();

		String sql = "Select * from Guess";

		ResultSet rs = st.executeQuery(sql);
		while(rs.next())
		{
			cmbPhone.addItem(rs.getString(1));
		}

	}

	private void LoadTxt_FromPhone(JComboBox<String> cmbPhone) throws SQLException, ClassNotFoundException {
		String Phone = String.valueOf(cmbPhone.getSelectedItem());
		try {
			Connection conn = ConnectionUtils.getConnection();
			String sql = "Select fullname, address, discount,totalCost  from Guess where phoneNumber = ?";

			PreparedStatement pstm = conn.prepareStatement(sql);


			pstm.setString(1, Phone);

			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				txtTenKH.setText(rs.getString("fullname"));
				txtAddress.setText(rs.getString("address"));
				discount = rs.getInt("discount");
				lblDiscount.setText("Ưu đãi: " + rs.getInt("discount") + "%");
				lblTotalcost.setText("Tổng số tiền đã mua: " + String.format("%,.0f", rs.getFloat("totalCost"))+ " vnđ");
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	void getChiTietDonHangToTable(Connection conn,int maDH) throws SQLException
	{
		List<ChiTietDonHang> listCTDH = new ArrayList<ChiTietDonHang>();

		try {
			listCTDH = ChiTietDonHangModel.getChiTietDonHangBymaDH(conn, maDH);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tblDetail = new JTable();


		//Tạo DefaultTableModel
		model = new DefaultTableModel();


		model.addColumn("STT");
		model.addColumn("Mã Sản phẩm");
		model.addColumn("Tên sản phẩm");
		model.addColumn("Đơn giá");
		model.addColumn("Số lượng");
		model.addColumn("Thành tiền");


		for(int i=0 ; i<listCTDH.size();i++) {
			model.addRow(new Object[] {i + 1 ,
					listCTDH.get(i).getIdProduct(),
					(SanPhamModel.getSanPhamBymaSP(conn, listCTDH.get(i).getIdProduct())).getName(),
					String.format("%.0f", (SanPhamModel.getSanPhamBymaSP(conn, listCTDH.get(i).getIdProduct())).getCost()),
					listCTDH.get(i).getQuantity(),
					String.format("%.0f",(listCTDH.get(i).getQuantity() * (SanPhamModel.getSanPhamBymaSP(conn, listCTDH.get(i).getIdProduct())).getCost()))
			});

			tblDetail.setModel(model);

			tblDetail.getColumnModel().getColumn(1);
			JComboBox<String> cmbHH = new JComboBox<String>();

			//Add comboBox
			initDataTable(cmbHH);


		}

//			Object[] data = {listCTDH.size() +1 ,"","",0,0,""};
//
//	  		model.addRow(data);





		// Tự động tính thành tiền
		tblDetail.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub

				//
				if(e.getColumn()==3 || e.getColumn() ==4) {
					int row = e.getFirstRow();
					Float gia = Float.valueOf(tblDetail.getValueAt(row, 3).toString());
					int soLuong =  Integer.valueOf(tblDetail.getValueAt(row, 4).toString());


					if(gia >=0 && soLuong >=0) {
						float thanhtien = gia * soLuong;
//	  						float sum = Float.parseFloat(txtTotalCost.getText());
//	  						sum += thanhtien - thanhtien * discount /100;
//	  						txtTotalCost.setText(String.format("%.0f", sum));

						tblDetail.setValueAt(String.format("%.0f", thanhtien), row, 5);
					}

					//Update lại tổng tiền đơn hàng

					float sum = 0;


					for(int i=0;i< tblDetail.getRowCount();i++)
					{
						if(tblDetail.getValueAt(i,5) != "")
							sum += Float.parseFloat(String.valueOf(tblDetail.getValueAt(i,5)));
					}

					sum -= sum * discount /100;
					txtTotalCost.setText(String.format("%.0f", sum));
				}
				else if(e.getColumn() == 1)
				{
					//CHọn mã hàng hóa để lấy giá tiền
					int row = e.getFirstRow();
					int col = e.getColumn();

					int maSP = Integer.parseInt(tblDetail.getValueAt(row, col).toString());
					try {
						Connection conn = ConnectionUtils.getConnection();
						double gia = SanPhamModel.getSanPhamBymaSP(conn, maSP).getCost();
						String tenSP = SanPhamModel.getSanPhamBymaSP(conn, maSP).getName();

						tblDetail.setValueAt(tenSP, row, 2);
						tblDetail.setValueAt(String.format("%.0f", gia), row, 3);
						//
						tblDetail.setValueAt(1, row, 4);

					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if(e.getFirstRow() == tblDetail.getRowCount() - 1)
					{
						model.addRow(new Object[] {tblDetail.getRowCount() + 1 ,"","",0,0,""});

						tblDetail.getColumnModel().getColumn(1);
						JComboBox<String> cmbHH = new JComboBox<String>();


						//Add comboBox
						initDataTable(cmbHH);

						tblDetail.setModel(model);

					}

				}


			}
		});

		//
		scrollPane.setViewportView(tblDetail);
	}

	// Cài đặt bật tắt cho container như jpanel
	private void setEnableAll(Component component, boolean enable) {
		component.setEnabled(enable);
//			try {
//				Component[] components = ((JComponent) component).getComponents();
//				for (int i = 0; i < components.length; i++) {
//					setEnableAll(components[i], enable);
//				}
//			} catch (ClassCastException e) {
//
//			}
	}

	// Reset input text
	private void resetInputText() {
		txtTotalCost.setText("0");
//		lblDiscount.setText("Ưu đãi: 0%");
//		lblTotalcost.setText("Tổng tiền đã mua: 0vnđ");
	}
	/**
	 * Create the frame.
	 *
	 * @throws UnsupportedLookAndFeelException
	 */
	public QuanLyDonHang(Account curUser) throws UnsupportedLookAndFeelException {
		getContentPane().setBackground(new Color(255, 255, 255));
		setFrameIcon(null);
		setBorder(null);
		UIManager.setLookAndFeel(new FlatLightLaf());
		//Tạo frame thông báo
		final JFrame frame = new JFrame();
		ImageIcon imgSanPham = new ImageIcon("resources/icon/shop.png");
		Image img = imgSanPham.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		frame.setIconImage(img);
		//

		setBounds(100, 100, 891, 706);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[][][grow][]"));
		//
		input_panel = new JPanel();
		input_panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(input_panel, "cell 0 0,grow");
		input_panel.setLayout(new MigLayout("", "[][grow][][][][grow]", "[24px][24px][24px][24px][24px]"));

		//
		JLabel lblNewLabel_1 = new JLabel("Số điện thoại:");
		lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_1, "flowy,cell 0 0,alignx left,aligny center");

		//Thêm các item vào ComboBox
		cmbPhone = new JComboBox<String>();
		try {
			AddItemPhone(cmbPhone);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		input_panel.add(cmbPhone, "cell 1 0,growx");
		
		txtMaNV = new JTextField();
		txtMaNV.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		txtMaNV.setEnabled(false);
		txtMaNV.setColumns(10);
		input_panel.add(txtMaNV, "cell 5 0,growx");


		JLabel lblNewLabel = new JLabel("Tên khách hàng:");
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel, "cell 0 1,alignx trailing,aligny center");

		txtTenKH = new JTextField();
		txtTenKH.setEnabled(false);
		txtTenKH.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		txtTenKH.setColumns(10);
		input_panel.add(txtTenKH, "cell 1 1,growx,aligny top");

		JLabel lblNewLabel_3 = new JLabel("Địa chỉ:");
		lblNewLabel_3.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_3, "cell 0 2,alignx left,aligny center");

		txtAddress = new JTextField();
		txtAddress.setEnabled(false);
		txtAddress.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		txtAddress.setColumns(10);
		input_panel.add(txtAddress, "cell 1 2,growx,aligny top");

		lblDiscount = new JLabel();
		lblDiscount.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblDiscount, "cell 0 4,alignx left,aligny center");

		lblTotalcost = new JLabel();
		lblTotalcost.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblTotalcost, "cell 1 4,growx,aligny center");
		//Load giá trị txt từ cmbPhone
		try {
			LoadTxt_FromPhone(cmbPhone);
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		//Event ComboBox Phone
		cmbPhone.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub

				@SuppressWarnings("unchecked")
				JComboBox<String> cmb = (JComboBox<String>) e.getSource();

				String phone = String.valueOf(cmb.getSelectedItem());
				try {
					Connection conn = ConnectionUtils.getConnection();
					String sql = "Select fullname, address, discount,totalCost  from Guess where phoneNumber = ?";

					PreparedStatement pstm = conn.prepareStatement(sql);

					pstm.setString(1, phone);

					ResultSet rs = pstm.executeQuery();
					while (rs.next()) {
						txtTenKH.setText(rs.getString("fullname"));
						txtAddress.setText(rs.getString("address"));
						discount = rs.getInt("discount");
						lblDiscount.setText("Ưu đãi: " + rs.getInt("discount") + "%");
						lblTotalcost.setText("Tổng số tiền đã mua: " + String.format("%,.0f", rs.getFloat("totalCost"))+ " vnđ");
					}

					//Update lại tổng tiền đơn hàng

					float sum = 0;

					//Do có hàng cuối trống nên -1
					for(int i=0;i< tblDetail.getRowCount()-1;i++)
					{
						sum += Float.parseFloat(String.valueOf(tblDetail.getValueAt(i,5)) );
					}

					sum -= sum * discount /100;
					txtTotalCost.setText(String.format("%.0f", sum));


				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		JLabel lblNewLabel_2 = new JLabel("Tài khoản nhân viên:");
		lblNewLabel_2.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_2, "flowx,cell 3 0");



		//



		JLabel lblTnNhnVin = new JLabel("Tên nhân viên:");
		lblTnNhnVin.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblTnNhnVin, "cell 3 1");

		//


		txtTenNV = new JTextField();
		txtTenNV.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		txtTenNV.setEnabled(false);
		input_panel.add(txtTenNV, "cell 5 1,growx");
		txtTenNV.setColumns(10);

		//


		JLabel lblNewLabel_4 = new JLabel("Tổng tiền:");
		lblNewLabel_4.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		input_panel.add(lblNewLabel_4, "cell 0 3,alignx left,aligny center");

		txtTotalCost = new JTextField();
		txtTotalCost.setEnabled(false);
		txtTotalCost.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		txtTotalCost.setColumns(10);
		txtTotalCost.setText("0");
		input_panel.add(txtTotalCost, "cell 1 3,growx,aligny top");



		search_panel = new JPanel();
		search_panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(search_panel, "cell 0 1,grow");
		search_panel.setLayout(new MigLayout("", "[grow][][]", "[25px]"));

		txtSearch = new JTextField("Nhập mã đơn hàng cần tìm kiếm");
		txtSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtSearch.getText().equals("Nhập mã đơn hàng cần tìm kiếm"))
					txtSearch.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (txtSearch.getText().isBlank())
					txtSearch.setText("Nhập mã đơn hàng cần tìm kiếm");
			}
		});
		txtSearch.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		txtSearch.setColumns(100);
		search_panel.add(txtSearch, "cell 0 0,alignx center,aligny center");

		//Btn Tìm
		btnTim = new JButton("Tìm");
		search_panel.add(btnTim, "cell 1 0");
		btnTim.setIcon(getIcon("resources/icon/search.png", 30, 30));
		btnTim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Reset button
				btnThem.setEnabled(false);
				btnSua.setEnabled(true);
				btnXoa.setEnabled(true);
				btnTim.setEnabled(false);
				btnHuy.setEnabled(true);

				//Set tim,them = false

				tim = true;
				them = false;

				//set table
				setEnableAll(search_panel, false);
				//



				String maDH = txtSearch.getText();

				if(maDH.equals("") || maDH.equals("Nhập mã đơn hàng cần tìm kiếm") )
				{

					JOptionPane.showMessageDialog(frame,
							"Vui lòng nhập mã đơn hàng",
							"Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					try {
						int idDH = Integer.parseInt(maDH);
						Connection conn = ConnectionUtils.getConnection();
						//Load don hang
						DonHang dh = DonHangModel.getDonHangBymaDH(conn, idDH);

						//txtngayDH.setText(dh.getNgayDonHang().toString());
						cmbPhone.setSelectedItem(dh.getPhoneNumGuess());
						txtTotalCost.setText(String.format("%.0f",dh.getCost()));


						//Load chi tiet don hang
						getChiTietDonHangToTable(conn, idDH);
						tblDetail.setEnabled(false);

						// Nếu không tìm thấy
						if (tblDetail.getRowCount() <= 0) {
							JOptionPane.showMessageDialog(txtSearch,
									"Không tìm thấy kết quả cho '" + maDH + "'");
						}

					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(frame,
								"Lỗi không tìm được",
								"Lỗi",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}

			}
		});
		btnTim.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));


		//Tạo table
		createTable();

		//
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(panel, "cell 0 3,grow");
		panel.setLayout(new MigLayout("", "[79px][][][63px][63px][63px][63px][]", "[29px]"));

		//Btn thêm
		btnThem = new JButton("Thêm");
		btnThem.setBackground(new Color(255, 255, 255));
		btnThem.setIcon(getIcon("resources/icon/add.png", 30, 30));
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				them = true;
				tim = false;
				// Bật input_panel và reset all input text
				setEnableAll(input_panel, true);
				//	resetInputText();

				// Bật nút Hủy, Lưu
				btnHuy.setEnabled(true);
				btnLuu.setEnabled(true);

				// Tắt nút Sửa Thêm, search panel,
				//Bật Xóa, table
				btnSua.setEnabled(false);
				btnXoa.setEnabled(true);
				btnThem.setEnabled(false);
				btnTim.setEnabled(false);
				setEnableAll(search_panel, false);
				tblDetail.setEnabled(true);

			}

		});
		btnThem.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		panel.add(btnThem, "cell 0 0,alignx left,aligny top");


		btnSua = new JButton("Sửa");
		btnSua.setBackground(new Color(255, 255, 255));
		btnSua.setEnabled(false);
		btnSua.setIcon(getIcon("resources/icon/edit.png", 30, 30));
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				them = false;
				tim = true;
				// Bật input_panel và reset all input text
				setEnableAll(input_panel, true);
				resetInputText();

				// Bật nút Hủy, Lưu
				btnHuy.setEnabled(true);
				btnLuu.setEnabled(true);

				// Tắt nút Sửa, Xóa, Thêm, search panel, table
				btnSua.setEnabled(false);
				btnXoa.setEnabled(true);
				btnThem.setEnabled(false);
				setEnableAll(search_panel, false);
				tblDetail.setEnabled(true);

				//Thêm 1 hàng mới
				Object[] data = {tblDetail.getRowCount() +1 ,"","",0,0,""};
				model.addRow(data);

			}
		});
		btnSua.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		panel.add(btnSua, "cell 1 0,alignx left,aligny top");

		btnXoa = new JButton("Xóa");
		btnXoa.setBackground(new Color(255, 255, 255));
		btnXoa.setIcon(getIcon("resources/icon/delete.png", 30, 30));
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//Xóa hết đơn hàng
				if(them == true && tim == true) {
					//Xóa hết đơn hàng
					try {
						Connection conn = ConnectionUtils.getConnection();

						String maDH = txtSearch.getText();
						if(maDH.equals("") || maDH.equals("Nhập mã đơn hàng cần tìm kiếm") )
						{

							JOptionPane.showMessageDialog(frame,
									"Vui lòng nhập số đơn hàng",
									"Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{
							int result = JOptionPane.showConfirmDialog(frame,
									"Bạn có chắc muốn xóa đơn này",
									"Xác nhận",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE);
							if(result == JOptionPane.YES_OPTION){
								int idDH = Integer.parseInt(maDH);
								ChiTietDonHangModel.deleteChiTietDonHangByIdOrder(conn, idDH);
								DonHangModel.deleteDonHang(conn, idDH);

								JOptionPane.showMessageDialog(frame,
										"Xóa thành công",
										"Thông báo",
										JOptionPane.INFORMATION_MESSAGE);
							}

						}

					}catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block

						JOptionPane.showMessageDialog(frame,
								"Lỗi không xóa được",
								"Lỗi",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}

					//Xóa table Tạo ScrollPane
					getContentPane().remove(scrollPane);
					createTable();

					//Reset text
					resetInputText();
				}

				//Xóa 1 dòng
				else if((them == true && tim == false) ||(them == false && tim == true) )
				{
					try {
						//Xóa 1 dòng trong table
						//Lỗi lần đầu tiên là hàng đầu
						rowtable = tblDetail.getSelectedRow();
						model.removeRow(rowtable);
						//lblDiscount.setText(String.valueOf(rowtable));

						//Reset STT và tổng tiền
						float sum = 0;

						for(int i=0;i< tblDetail.getRowCount();i++)
						{
							tblDetail.setValueAt(i+1, i, 0);
						}

						//Do có hàng cuối trống
						for(int i=0;i< tblDetail.getRowCount()-1;i++)
						{
							sum += Float.parseFloat(String.valueOf(tblDetail.getValueAt(i,5)) );
						}

						sum -= sum * discount /100;
						txtTotalCost.setText(String.format("%.0f", sum));
					} catch (Exception  e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(frame,
								"Vui lòng chọn hàng muốn xóa",
								"Lỗi",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}


				}

			}
		});
		btnXoa.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		panel.add(btnXoa, "cell 2 0,alignx left,aligny top");

		btnLuu = new JButton("Lưu");
		btnLuu.setBackground(new Color(255, 255, 255));
		btnLuu.setEnabled(false);
		btnLuu.setIcon(getIcon("resources/icon/save.png", 30, 30));
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(them == true) {
					// Đơn hàng
					DonHang dHang = new DonHang();

					dHang.setCost(Float.parseFloat(txtTotalCost.getText()));
					dHang.setPhoneNumGuess(cmbPhone.getSelectedItem().toString());
					dHang.setStaffName(txtMaNV.getText());

					try {
						if(tblDetail.getRowCount()>1) {
							Connection conn = ConnectionUtils.getConnection();
							//Thêm đơn hàng và Lấy MaDH vừa mới thêm
							int idDH = DonHangModel.insertDonHang(conn, dHang);

							//Chi tiết đơn  hàng
							ChiTietDonHang ctDH = new ChiTietDonHang();

							//
							ctDH.setIdOrder(idDH);
							for(int i=0;i< tblDetail.getRowCount()-1;i++)
							{
								ctDH.setIdProduct(Integer.parseInt(tblDetail.getValueAt(i, 1).toString()));
								ctDH.setQuantity(Integer.parseInt( tblDetail.getValueAt(i, 4).toString()));
								ctDH.setCost(Integer.parseInt(tblDetail.getValueAt(i, 5).toString()));
								//Thêm chi tiết đơn hàng
								ChiTietDonHangModel.insertChiTietDonHang(conn, ctDH);
							}



							JOptionPane.showMessageDialog(frame,
									"Thêm thành công",
									"Thông báo",
									JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(frame,
									"Cần thêm sản phẩm vào đơn hàng",
									"Lỗi",
									JOptionPane.ERROR_MESSAGE);
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(frame,
								"Lỗi không thêm được",
								"Lỗi",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
				//Sửa
				else {

					int result = JOptionPane.showConfirmDialog(frame,
							"Bạn có chắc muốn sửa đơn này",
							"Xác nhận",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if(result == JOptionPane.YES_OPTION){
						//Lấy mã đơn hàng
						int maDH = Integer.parseInt( txtSearch.getText());
						try {
							Connection conn = ConnectionUtils.getConnection();

							//Xóa đơn hàng và chi tiết đơn hàng vs mã maDH
							ChiTietDonHangModel.deleteChiTietDonHangByIdOrder(conn, maDH);

							//Chi tiết đơn  hàng
							ChiTietDonHang ctDH = new ChiTietDonHang();

							if(tblDetail.getRowCount()>=1) {

								ctDH.setIdOrder(maDH);
								for(int i=0;i< tblDetail.getRowCount()-1;i++)
								{
									ctDH.setIdProduct(Integer.parseInt(tblDetail.getValueAt(i, 1).toString()));
									ctDH.setQuantity(Integer.parseInt( tblDetail.getValueAt(i, 4).toString()));
									ctDH.setCost(Float.parseFloat(tblDetail.getValueAt(i, 5).toString()));
									//Thêm chi tiết đơn hàng
									ChiTietDonHangModel.insertChiTietDonHang(conn, ctDH);
								}

								JOptionPane.showMessageDialog(frame,
										"Chỉnh sửa thành công",
										"Thông báo",
										JOptionPane.INFORMATION_MESSAGE);
							}
							else {
								JOptionPane.showMessageDialog(frame,
										"Cần thêm sản phẩm vào đơn hàng",
										"Lỗi",
										JOptionPane.ERROR_MESSAGE);
							}

							//Cập nhật lại đơn hàng

							DonHang dHang = new DonHang();

							dHang.setCost(Float.parseFloat(txtTotalCost.getText()));
							dHang.setPhoneNumGuess(cmbPhone.getSelectedItem().toString());
							dHang.setStaffName(txtMaNV.getText());

							DonHangModel.updateDonHang(conn, dHang, maDH);


						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(frame,
									"Lỗi không sửa được",
									"Lỗi",
									JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
							e1.printStackTrace();
						}
					}
				}

				//
				getContentPane().remove(scrollPane);
				createTable();

			}
		});
		btnLuu.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		panel.add(btnLuu, "cell 3 0,alignx left,aligny top");


		//Btn Hủy
		btnHuy = new JButton("Hủy");
		btnHuy.setBackground(new Color(255, 255, 255));
		btnHuy.setEnabled(false);
		btnHuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(scrollPane);
				createTable();

			}
		});
		
		btnHuy.setIcon(getIcon("resources/icon/cancel.png", 30, 30));
		btnHuy.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
		panel.add(btnHuy, "cell 4 0,alignx left,aligny top");

		txtTenNV.setText(curUser.getFullname());
		txtMaNV.setText(curUser.getUsername());

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
