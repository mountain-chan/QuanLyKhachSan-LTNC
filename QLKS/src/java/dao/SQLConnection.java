package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.TaiKhoan;

public class SQLConnection {

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QuanLyKhachSanDb;user=sa;password=123456");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

    public static TaiKhoan getTaiKhoan(String TenTaiKhoan, String MatKhau) {
        TaiKhoan tmp = null;
        try {
            Connection con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("select * from TaiKhoan where TenTaiKhoan=? and MatKhau=?");
            stmt.setString(1, TenTaiKhoan);
            stmt.setString(2, MatKhau);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tmp = new TaiKhoan();
                tmp.setTenTaiKhoan(rs.getString(1));
                tmp.setMatKhau(rs.getString(2));
                tmp.setHoTen(rs.getString(3));
                tmp.setGioiTinh(rs.getBoolean(4));
                tmp.setSoDienThoai(rs.getString(5));
                tmp.setEmail(rs.getString(6));
                tmp.setIsAdmin(rs.getBoolean(7));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return tmp;
    }
}
