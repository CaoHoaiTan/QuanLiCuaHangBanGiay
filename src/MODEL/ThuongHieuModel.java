package MODEL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import DAO.ConnectionUtils;

public class ThuongHieuModel {
	 public static Object[][] listThuongHieu_to_Obj(List<ThuongHieu> listTH) {
	        Object[][] obj = new Object[listTH.size()][4];
	        for (int i = 0; i < listTH.size(); i++) {
	            obj[i][0] = listTH.get(i).getId();
	            obj[i][1] = listTH.get(i).getName();
	            obj[i][2] = listTH.get(i).getEmail();
	            obj[i][3] = listTH.get(i).getLogo();	           
	        }
	        return obj;
	    }
	    
	    // load ThuongHieu from DB
	    public static List<ThuongHieu> load_ThuongHieu(Connection conn) throws SQLException {
	        Statement stm = conn.createStatement();
	        List<ThuongHieu> listTH = new ArrayList<>();

	        ResultSet rs = stm.executeQuery("select * from Brand");
	        while (rs.next()) {
	            ThuongHieu th = new ThuongHieu(
	                    rs.getInt(1),
	                    rs.getString(2),
	                    rs.getString(3),
	                    rs.getString(4));
	            listTH.add(th);
	        }
	        return listTH;
	    }

	    // load ThuongHieu from DB to Obj[][]
	    public static Object[][] load_ThuongHieu_to_Obj(Connection conn) throws SQLException {
	        return listThuongHieu_to_Obj(load_ThuongHieu(conn));
	    }
	    
	    // delete ThuongHieu from DB by sdt
	    // return true nếu thành công, false nếu lỗi
	    public static boolean delete_ThuongHieu(Connection conn, String MaTH) throws SQLException {
	    	PreparedStatement pstm = conn.prepareStatement("delete from Brand where id = ?");
	    	pstm.setString(1, MaTH);
	    	if (pstm.executeUpdate() > 0)
	    		return true;
	    	return false;
	    }

	 // insert ThuongHieu to DB
	    public static boolean insert_ThuongHieu(Connection conn, ThuongHieu TH) throws SQLException {
	    	PreparedStatement pstm = conn.prepareStatement("insert into Brand([name], email, logo) "
	    			+ "	values(?, ?, ?)");
	    	pstm.setString(1, TH.getName());
	    	pstm.setString(2, TH.getEmail());
	    	pstm.setString(3, TH.getLogo());	    	
	    	if (pstm.executeUpdate() > 0)
	    		return true;
	    	return false;
	    }
	    
	    // update ThuongHieu to DB
	    public static boolean update_ThuongHieu(Connection conn, ThuongHieu TH) throws SQLException {
	    	PreparedStatement pstm = conn.prepareStatement("update Brand set "
	    			+ "name = ?, "
	    			+ "email = ?, "
	    			+ "logo = ? "
	    			+ "where id = ?");
	    	pstm.setString(1, TH.getName());
	    	pstm.setString(2, TH.getEmail());
	    	pstm.setString(3, TH.getLogo());
	    	pstm.setInt(4, TH.getId());
	    	if (pstm.executeUpdate() > 0)
	    		return true;
	    	return false;
	    }
	    
	    public static void main(String[] args) throws SQLException, ClassNotFoundException {
	    	//System.out.println(load_ThuongHieu(ConnectionUtils.getConnection()));
	    	Connection connection= ConnectionUtils.getConnection();
//	    	List<ThuongHieu> thuongHieus= ThuongHieuModel.load_ThuongHieu(connection);
//	    	for(ThuongHieu thuongHieu : thuongHieus) {
//	    		 System.out.println(thuongHieu);
//	    	}
	    
//	        
//	    	ThuongHieu thuongHieu= new ThuongHieu();
//	    	thuongHieu.setId(7);
//	    	thuongHieu.setName("OK");
//	    	thuongHieu.setEmail("OKla");
//	    	thuongHieu.setLogo("m");
//	    	boolean ok = update_ThuongHieu(connection, thuongHieu);
//	    	System.out.println(ok);

			LocalDate tuoiDate= LocalDate.parse("2001-10-10");
			LocalDate namHienTaiDate = LocalDate.now();
			
			System.out.println(namHienTaiDate.compareTo(tuoiDate));
			
	    }
}
