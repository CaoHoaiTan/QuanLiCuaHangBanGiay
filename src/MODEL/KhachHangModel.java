package MODEL;

import DAO.ConnectionUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

public class KhachHangModel {
    // List KhachHang to object[][]
    public static Object[][] listKhachHang_to_Obj(List<KhachHang> listKH) {
        Object[][] obj = new Object[listKH.size()][7];
        for (int i = 0; i < listKH.size(); i++) {
            obj[i][0] = listKH.get(i).getPhoneNumber();
            obj[i][1] = listKH.get(i).getFullname();
            obj[i][2] = listKH.get(i).getSex();
            obj[i][3] = listKH.get(i).getAddress();
            obj[i][4] = listKH.get(i).getEmail();
            obj[i][5] = listKH.get(i).getTotalCost();
            obj[i][6] = listKH.get(i).getDiscount();
        }
        return obj;
    }
    
    // load KhachHang from DB
    public static List<KhachHang> load_KhachHang(Connection conn) throws SQLException {
        Statement stm = conn.createStatement();
        List<KhachHang> listKH = new ArrayList<>();

        ResultSet rs = stm.executeQuery("select * from Guess");
        while (rs.next()) {
            KhachHang kh = new KhachHang(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getDouble(6),
                    rs.getInt(7));
            listKH.add(kh);
        }
        return listKH;
    }
    // delete KhachHang from DB by sdt
    // return true nếu thành công, false nếu lỗi
    public static boolean delete_KhachHang(Connection conn, String phoneNumber) throws SQLException {
        PreparedStatement pstm = conn.prepareStatement("delete from Guess where phoneNumber = ?");
        pstm.setString(1, phoneNumber);
        if (pstm.executeUpdate() > 0)
            return true;
        return false;
    }
    // load KhachHang from DB to Obj[][]
    public static Object[][] load_KhachHang_to_Obj(Connection conn) throws SQLException {
        return listKhachHang_to_Obj(load_KhachHang(conn));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println(load_KhachHang(ConnectionUtils.getConnection()));
    }
}
