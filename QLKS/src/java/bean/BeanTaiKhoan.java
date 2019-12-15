package bean;

import java.io.Serializable;
import model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "beanTaiKhoan", eager = true)
@ApplicationScoped
public class BeanTaiKhoan implements Serializable {

    private static final long serialVersionUID = 1L;
    
    TaiKhoan taiKhoan;
    ArrayList<TaiKhoan> listTaiKhoan;
    Connection con;
    
    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public ArrayList<TaiKhoan> getListTaiKhoan() {
        return listTaiKhoan;
    }
    
    public BeanTaiKhoan() {
        taiKhoan = new TaiKhoan();
        try {
            listTaiKhoan = new ArrayList();
            con = dao.SQLConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from TaiKhoan");
            while (rs.next()) {
                TaiKhoan tmp = new TaiKhoan();
                tmp.setTenTaiKhoan(rs.getString(1));
                tmp.setMatKhau(rs.getString(2));
                tmp.setHoTen(rs.getString(3));
                tmp.setGioiTinh(rs.getBoolean(4));
                tmp.setSoDienThoai(rs.getString(5));
                tmp.setEmail(rs.getString(6));
                tmp.setIsAdmin(rs.getBoolean(7));
                listTaiKhoan.add(tmp);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void reset() {
        taiKhoan.setTenTaiKhoan("");
        taiKhoan.setMatKhau("");
        taiKhoan.setHoTen("");
        taiKhoan.setGioiTinh(true);
        taiKhoan.setSoDienThoai("");
        taiKhoan.setEmail("");
        taiKhoan.setIsAdmin(false);
    }
    
    public void insert(TaiKhoan tmp) {
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("insert into TaiKhoan values(?,?,?,?,?,?,?)");
            stmt.setString(1, tmp.getTenTaiKhoan());
            stmt.setString(2, tmp.getMatKhau());
            stmt.setString(3, tmp.getHoTen());
            stmt.setBoolean(4, tmp.isGioiTinh());
            stmt.setString(5, tmp.getSoDienThoai());
            stmt.setString(6, tmp.getEmail());
            stmt.setBoolean(7, tmp.isIsAdmin());
            stmt.executeUpdate();
            con.close();
            TaiKhoan tk = new TaiKhoan(tmp);
            listTaiKhoan.add(tk);
            msg.Message.addMessage("Thành Công", "Thêm Tài Khoản Thành Công!");
        } catch (Exception e) {
            msg.Message.errorMessage("Thất Bại", "Thêm Tài Khoản Thất Bại!");
        }
    }
    
    public void update(TaiKhoan tmp) {
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("update TaiKhoan set MatKhau=?, HoTen=?, GioiTinh=?, SoDienThoai=?, Email=?, IsAdmin=? where TenTaiKhoan=?");
            stmt.setString(1, tmp.getMatKhau());
            stmt.setString(2, tmp.getHoTen());
            stmt.setBoolean(3, tmp.isGioiTinh());
            stmt.setString(4, tmp.getSoDienThoai());
            stmt.setString(5, tmp.getEmail());
            stmt.setBoolean(6, tmp.isIsAdmin());
            stmt.setString(7, tmp.getTenTaiKhoan());
            stmt.executeUpdate();
            con.close();
            String TenTaiKhoan = tmp.getTenTaiKhoan();
            for (TaiKhoan tk : listTaiKhoan) {
                if (tk.getTenTaiKhoan() == TenTaiKhoan) {
                    tk.reload(tmp.getTenTaiKhoan(), tmp.getMatKhau(), tmp.getHoTen(), tmp.isGioiTinh(), tmp.getSoDienThoai(), tmp.getEmail(), tmp.isIsAdmin());
                    break;
                }
            }
            msg.Message.addMessage("Thành Công", "Sửa Tài Khoản Thành Công!");
        } catch (Exception e) {
            msg.Message.errorMessage("Thất Bại", "Sửa Tài Khoản Thất Bại!");
        }
    }
    
    public void delete(String TenTaiKhoan) {
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("delete from TaiKhoan where TenTaiKhoan=?");
            stmt.setString(1, TenTaiKhoan);
            stmt.executeUpdate();
            con.close();
            for (TaiKhoan tk : listTaiKhoan) {
                if (tk.getTenTaiKhoan() == TenTaiKhoan) {
                    listTaiKhoan.remove(tk);
                    break;
                }
            }
            msg.Message.addMessage("Thành Công", "Xóa Tài Khoản Thành Công!");
        } catch (Exception e) {
            msg.Message.errorMessage("Thất Bại", "Xóa Tài Khoản Thất Bại!");
        }
    }

}
