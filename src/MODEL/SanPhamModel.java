package MODEL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import DAO.ConnectionUtils;

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
	
	// List SanPham to object[][]
		public static Object[][] listSanPham_to_Obj(List<SanPham> listSP) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
			Object[][] obj = new Object[listSP.size()][8];
			for (int i = 0; i < listSP.size(); i++) {
				obj[i][0] = listSP.get(i).getId();
				obj[i][1] = listSP.get(i).getIdBrand();
				obj[i][2] = listSP.get(i).getName();
				obj[i][3] = listSP.get(i).getImage();
				obj[i][4] = listSP.get(i).getDescribe();
				obj[i][5] = listSP.get(i).getQuantity();
				obj[i][6] = String.format("%,.0f", listSP.get(i).getCost());
				obj[i][7] = dateFormat.format( listSP.get(i).getSaleDate() );
			}
			return obj;
		}
		
	    // load SanPham from DB
	    public static List<SanPham> load_SanPham(Connection conn) throws SQLException {
	        Statement stm = conn.createStatement();
	        List<SanPham> listSP = new ArrayList<>();

	        ResultSet rs = stm.executeQuery("select * from Product");
	        while (rs.next()) {
	            SanPham sp = new SanPham(
	                    rs.getInt(1),
	                    rs.getInt(2),
	                    rs.getString(3),
	                    rs.getString(4),
	                    rs.getString(5),
	                    rs.getInt(6),
	                    rs.getDouble(7),
	                    rs.getDate(8));
	            listSP.add(sp);
	        }
	        return listSP;
	    }
	    
	    // delete SanPham from DB by sdt
	    // return true nếu thành công, false nếu lỗi
	    public static boolean delete_SanPham(Connection conn, String id) throws SQLException {
	    	PreparedStatement pstm = conn.prepareStatement("delete from Product where id = ?");
	    	pstm.setString(1, id);
	    	if (pstm.executeUpdate() > 0)
	    		return true;
	    	return false;
	    }
	    
	    // find SanPham by id
	    public static SanPham find_SanPham(Connection conn, int id) throws SQLException {
	        PreparedStatement pstm = conn.prepareStatement("Select * from Product where id = ?");
	        pstm.setInt(1, id);
	        ResultSet rs = pstm.executeQuery();
	        
	        if (rs.next()) {
	            SanPham sp = new SanPham(
	                    rs.getInt(1),
	                    rs.getInt(2),
	                    rs.getString(3),
	                    rs.getString(4),
	                    rs.getString(5),
	                    rs.getInt(6),
	                    rs.getDouble(7),
	                    rs.getDate(8));
	            return sp;
	        }
	        return null;
	    }
	    
	    // insert SanPham to DB
	    public static boolean insert_SanPham(Connection conn, SanPham SP) throws SQLException {
	    	PreparedStatement pstm = conn.prepareStatement("SET IDENTITY_INSERT Product ON "
	    			+ "INSERT Product (id, idBrand, name, image, describe, quantity, cost, saleDate) "
	    			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
	    			+ "SET IDENTITY_INSERT Product OFF ");
	    	pstm.setInt(1, SP.getId());
	    	pstm.setInt(2, SP.getIdBrand());
	    	pstm.setString(3, SP.getName());
	    	pstm.setString(4, SP.getImage());
	    	pstm.setString(5, SP.getDescribe());
	    	pstm.setInt(6, SP.getQuantity());
	    	pstm.setDouble(7, SP.getCost());
	    	pstm.setDate(8, SP.getSaleDate());
	    	if (pstm.executeUpdate() > 0)
	    		return true;
	    	return false;
	    }
	    
	    // update info SanPham to DB
	    public static boolean update_SanPham(Connection conn, SanPham SP) throws SQLException {
	    	PreparedStatement pstm = conn.prepareStatement("update Product set "
	    			+ "idBrand = ?, "
	    			+ "name = ?, "
	    			+ "image = ?, "
	    			+ "describe = ?, "
	    			+ "cost = ? "
	    			+ "where id = ?");
	    	pstm.setInt(1, SP.getIdBrand());
	    	pstm.setString(2, SP.getName());
	    	pstm.setString(3, SP.getImage());
	    	pstm.setString(4, SP.getDescribe());
	    	pstm.setDouble(5, SP.getCost());
	    	pstm.setInt(6, SP.getId());
	    	if (pstm.executeUpdate() > 0)
	    		return true;
	    	return false;
	    }
	    
	    // update add SanPham to DB
	    public static boolean update_SanPham(Connection conn, int id, int quantity) throws SQLException {
	    	PreparedStatement pstm = conn.prepareStatement("update Product set "
	    			+ "quantity += ? "
	    			+ "where id = ?");
	    	pstm.setInt(1, quantity);
	    	pstm.setInt(2, id);
	    	
	    	if (pstm.executeUpdate() > 0)
	    		return true;
	    	return false;
	    }

	    // load SanPham from DB to Obj[][]
	    public static Object[][] load_SanPham_to_Obj(Connection conn) throws SQLException {
	        return listSanPham_to_Obj(load_SanPham(conn));
	    }
	    
	    // get last Product id from DB
	    public static int getLastID_SanPham(Connection conn) throws SQLException {
	    	Statement stm = conn.createStatement();
	    	ResultSet rs = stm.executeQuery("Select max(id) from Product");
	    	
	    	if (rs.next())
	    		return rs.getInt(1);
	    	return -1;
	    }

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			try {
				System.out.println(getLastID_SanPham(ConnectionUtils.getConnection()));
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
