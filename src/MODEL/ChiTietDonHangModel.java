package MODEL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangModel {

	public static void insertChiTietDonHang(Connection conn,ChiTietDonHang CTDH)throws SQLException{
		String sql ="insert into OrderDetail(idOrder,idProduct, quantity, cost) "
				+ "values(?,?, ?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, CTDH.getIdOrder());
		pstm.setInt(2, CTDH.getIdProduct());
		pstm.setInt(3, CTDH.getQuantity());
		pstm.setFloat(4, CTDH.getCost());

		pstm.executeUpdate();
	}
	public static void deleteChiTietDonHangByIdOrder(Connection conn, int maDH)throws SQLException{
		String sql ="DELETE FROM OrderDetail\n"
				+ "WHERE idOrder =?;";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, maDH);
		
		pstm.executeUpdate();
	}
	
	public static List<ChiTietDonHang> getChiTietDonHangBymaDH(Connection conn, int maDH) throws SQLException{
		String sql = "select * from OrderDetail where idOrder = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, maDH);
		ResultSet rs = pstm.executeQuery();
		List<ChiTietDonHang> listCTDH = new ArrayList<ChiTietDonHang>();
		while (rs.next()) {
			int maSP = rs.getInt("idProduct");
			Integer soLuong = rs.getInt("quantity");
			Float tongTien = rs.getFloat("cost");
			
			ChiTietDonHang ctdh = new ChiTietDonHang(maSP,soLuong,tongTien);
			listCTDH.add(ctdh);
		}
		return listCTDH;
	}
}
