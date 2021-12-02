package MODEL;

import DAO.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountModel {
	// List KhachHang to object[][]
	public static Object[][] listAccount_to_Obj(List<Account> listNV) {
		Object[][] obj = new Object[listNV.size()][10];
		for (int i = 0; i < listNV.size(); i++) {
			obj[i][0] = listNV.get(i).getId();
			obj[i][1] = listNV.get(i).getFullname();
			obj[i][2] = listNV.get(i).getSex();
			obj[i][3] = listNV.get(i).getAvatar();
			obj[i][4] = listNV.get(i).getPhoneNumber();
			obj[i][5] = listNV.get(i).getAddress();
			obj[i][6] = listNV.get(i).getDateOfBirth();
			obj[i][7] = listNV.get(i).isAdmin();
			obj[i][8] = listNV.get(i).getUsername();
			obj[i][9] = listNV.get(i).getPassword();

		}
		return obj;
	}

	// load Account from DB
	public static List<Account> load_Account(Connection conn) throws SQLException {
		Statement stm = conn.createStatement();
		List<Account> listNV = new ArrayList<>();

		ResultSet rs = stm.executeQuery("select * from Account");
		while (rs.next()) {
			Account nv = new Account(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getBoolean(10));
			listNV.add(nv);
		}
		return listNV;
	}

	// load Account from DB to Obj[][]
	public static Object[][] load_Nhanvien_to_Obj(Connection conn) throws SQLException {
		return listAccount_to_Obj(load_Account(conn));
	}

	// delete NhanVien from DB by id
	// return true nếu thành công, false nếu lỗi
	public static boolean delete_NhanVien(Connection conn, String ID) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement("delete from Account where id = ?");
		pstm.setString(1, ID);
		if (pstm.executeUpdate() > 0)
			return true;
		return false;
	}

	// insert NhanVien to DB
	public static boolean insert_NhanVien(Connection conn, Account NV) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement(
				"insert into Account(username, password, fullname, avatar, phoneNumber, address, sex, dateofBirth, isAdmin) "
						+ "	values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
		pstm.setString(1, NV.getUsername());
		pstm.setString(2, NV.getPassword());
		pstm.setString(3, NV.getFullname());
		pstm.setString(4, NV.getAvatar());
		pstm.setString(5, NV.getPhoneNumber());
		pstm.setString(6, NV.getAddress());
		pstm.setString(7, NV.getSex());
		pstm.setString(8, NV.getDateOfBirth().toString());
		pstm.setBoolean(9, NV.isAdmin());
		if (pstm.executeUpdate() > 0)
			return true;
		return false;
	}

	// update NhanVien to DB
	public static boolean update_NhanVien(Connection conn, Account NV) throws SQLException {
		PreparedStatement pstm = conn.prepareStatement("update Account set " + "username = ?, " + "password = ?, "
				+ "fullname = ?, " + "avatar= ?, " + "phoneNumber= ?, " + "address= ?, " + "sex= ?, "
				+ "dateofBirth= ?, " + "isAdmin= ? " + " where id = ?");
		pstm.setString(1, NV.getUsername());
		pstm.setString(2, NV.getPassword());
		pstm.setString(3, NV.getFullname());
		pstm.setString(4, NV.getAvatar());
		pstm.setString(5, NV.getPhoneNumber());
		pstm.setString(6, NV.getAddress());
		pstm.setString(7, NV.getSex());
		pstm.setString(8, NV.getDateOfBirth().toString());
		pstm.setBoolean(9, NV.isAdmin());
		pstm.setInt(10, NV.getId());
		if (pstm.executeUpdate() > 0)
			return true;
		return false;
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// System.out.println(load_Account(ConnectionUtils.getConnection()));
		Connection connection = ConnectionUtils.getConnection();
		List<Account> accounts = AccountModel.load_Account(connection);
		for (Account account : accounts) {
			System.out.println(account);
		}
	}

	public static Account findAccount(Connection conn, String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pstm = conn.prepareStatement("Select * from Account where username = ? and password = ?");
		pstm.setString(1, username);
		pstm.setString(2, password);

		ResultSet rs = pstm.executeQuery();
		if (rs.next()) {
			Account account = new Account(
					rs.getInt(1), 
					rs.getString(2), 
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					rs.getString(6),
					rs.getString(7), 
					rs.getString(8), 
					rs.getDate(9),
					rs.getBoolean(10));
			return account;
		}
		return null;
	}
}
