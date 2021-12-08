package MODEL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonHangModel {
	
	public static int insertDonHang(Connection conn,DonHang DH)throws SQLException{		
		PreparedStatement pstm = conn.prepareCall("insert into [Order](staffName, phoneNumGuess) " +
                "output inserted.id values (?,?)");
		
		pstm.setString(1, DH.getStaffName());
		pstm.setString(2, DH.getPhoneNumGuess());

		ResultSet rs = pstm.executeQuery();
		
		 if (rs.next()) {
	            return rs.getInt(1);
	        }
	    return -1;
	}
	public static void deleteDonHang(Connection conn, int maDH)throws SQLException{
		String sql ="DELETE FROM [Order]\n"
				+ "WHERE id =?;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, maDH);
		
		pstm.executeUpdate();
	}
	
	public static void updateDonHang(Connection conn,DonHang dh, int maDH)throws SQLException{
		String sql ="UPDATE [Order]\n"
				+ "SET staffName = ?, phoneNumGuess = ? \n"
				+ "WHERE id = ?;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, dh.getStaffName());
		pstm.setString(2, dh.getPhoneNumGuess());
		pstm.setInt(3, maDH);
		
		pstm.executeUpdate();
	}

	public static DonHang getDonHangBymaDH(Connection conn, int maDH) throws SQLException{
		String sql = "select * from [Order] where id = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, maDH);
		ResultSet rs = pstm.executeQuery();
		DonHang dh = new DonHang();
		while (rs.next()) {
			String soDT = rs.getString("phoneNumGuess");
			String tenNV = rs.getString("staffName");
			Float totalCost = rs.getFloat("cost");
			dh = new DonHang(tenNV,soDT,totalCost);		
		}
		return dh;
	}
	
	public static List<DonHang> thongKeDoanhThuTheoNgay(Connection conn) throws SQLException{
	      String sql = " Select purchaseDate as ngayBan, Sum(cost)as tongTien\r\n"
	      		+ "From [Order]\r\n"
	      		+ "Group by purchaseDate";
		
		//String sql = "{call getDoanhThuTheoNgay()}";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		List<DonHang> list = new ArrayList<DonHang>();
		
		while (rs.next()) {
		  Date ngayMua = rs.getDate("ngayBan");
		  float tongTien = rs.getInt("tongTien");
		  DonHang dh = new DonHang( tongTien,ngayMua);
		  list.add(dh);
		}
		return list;
	}
	
	public static List<DonHang> thongKeDoanhThuTheoThang(Connection conn) throws SQLException{
	      String sql = " Select Month(purchaseDate) as thangBan, Sum(cost)as tongTien\r\n"
	      		+ "From [Order]\r\n"
	      		+ "Group by Month(purchaseDate)";
		
		//String sql = "{call getDoanhThuTheoNgay()}";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();
		List<DonHang> list = new ArrayList<DonHang>();
		
		while (rs.next()) {
		  int thang = rs.getInt("thangBan");
		  float tongTien = rs.getInt("tongTien");
		  DonHang dh = new DonHang( tongTien,thang);
		  list.add(dh);
		}
		return list;
	}
	

}
