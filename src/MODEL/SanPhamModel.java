package MODEL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SanPhamModel {
	
	public static SanPham getSanPhamBymaSP(Connection conn, int maSP) throws SQLException{
		String sql = "select * from Product where id = ?";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, maSP);
		ResultSet rs = pstm.executeQuery();
		SanPham sp = new SanPham();
		while (rs.next()) {
			int maTH = rs.getInt("idBrand");
			int soLuong = rs.getInt("quantity");
			String ten = rs.getString("name");
			String anh = rs.getString("image");
			String moTa = rs.getString("describe");
			float giaBan = rs.getFloat("cost");
			Date ngayNhap = rs.getDate("saleDate");
			sp = new SanPham(maTH,soLuong,ten,anh,moTa,giaBan,ngayNhap);
		}
		return sp;
	}
}
