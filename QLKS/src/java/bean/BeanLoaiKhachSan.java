package bean;

import java.io.Serializable;
import model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "beanLoaiKhachSan", eager = true)
@ApplicationScoped
public class BeanLoaiKhachSan implements Serializable {

    private static final long serialVersionUID = 185755L;

    public static HashMap<Integer, String> hashLoaiKhachSan;
    LoaiKhachSan loaiKhachSan;
    ArrayList<LoaiKhachSan> listLoaiKhachSan;
    Connection con;

    public BeanLoaiKhachSan() {
        try {
            loaiKhachSan = new LoaiKhachSan();
            hashLoaiKhachSan = new HashMap();
            listLoaiKhachSan = new ArrayList();
            con = dao.SQLConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from LoaiKhachSan");
            while (rs.next()) {
                LoaiKhachSan tmp = new LoaiKhachSan();
                tmp.setId(rs.getInt("Id"));
                tmp.setTen(rs.getString("Ten"));
                tmp.setMoTa(rs.getString("MoTa"));
                tmp.setUrlHinhAnh(rs.getString("UrlHinhAnh"));
                listLoaiKhachSan.add(tmp);
                hashLoaiKhachSan.put(tmp.getId(), tmp.getTen());
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void reset() {
        loaiKhachSan.setTen("");
        loaiKhachSan.setMoTa("");
        loaiKhachSan.setUrlHinhAnh("");
    }

    public void insert(LoaiKhachSan tmp) {
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("insert into LoaiKhachSan output inserted.Id values(?,?,?)");
            stmt.setString(1, tmp.getTen());
            stmt.setString(2, tmp.getMoTa());
            stmt.setString(3, tmp.getUrlHinhAnh());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tmp.setId(rs.getInt("Id"));
            }
            con.close();
            LoaiKhachSan tp = new LoaiKhachSan(tmp);
            listLoaiKhachSan.add(tp);
            pf.Message.addMessage("Thành Công", "Thêm Thành Phố Thành Công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Thêm Thành Phố Thất Bại!");
        }
    }

    public void update(LoaiKhachSan tmp) {
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("update LoaiKhachSan set Ten=?, MoTa=?, UrlHinhAnh=? where Id=?");
            stmt.setString(1, tmp.getTen());
            stmt.setString(2, tmp.getMoTa());
            stmt.setString(3, tmp.getUrlHinhAnh());
            stmt.setInt(4, tmp.getId());
            stmt.executeUpdate();
            con.close();
            int id = tmp.getId();
            for (LoaiKhachSan tp : listLoaiKhachSan) {
                if (tp.getId() == id) {
                    tp.reload(tmp.getId(), tmp.getTen(), tmp.getMoTa(), tmp.getUrlHinhAnh());
                    break;
                }
            }
            pf.Message.addMessage("Thành Công", "Sửa Thành Phố Thành Công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Sửa Thành Phố Thất Bại!");
        }
    }

    public void delete(int Id) {
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("delete from LoaiKhachSan where Id=?");
            stmt.setInt(1, Id);
            stmt.executeUpdate();
            con.close();
            for (LoaiKhachSan tp : listLoaiKhachSan) {
                if (tp.getId() == Id) {
                    listLoaiKhachSan.remove(tp);
                    break;
                }
            }
            pf.Message.addMessage("Thành Công", "Xóa Thành Phố Thành Công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Xóa Thành Phố Thất Bại!");
        }
    }

    //
    // Get - Set, Don't care
    //
    public LoaiKhachSan getLoaiKhachSan() {
        return loaiKhachSan;
    }

    public void setLoaiKhachSan(LoaiKhachSan loaiKhachSan) {
        this.loaiKhachSan = loaiKhachSan;
    }

    public ArrayList<LoaiKhachSan> getListLoaiKhachSan() {
        return listLoaiKhachSan;
    }

    public void setListLoaiKhachSan(ArrayList<LoaiKhachSan> listLoaiKhachSan) {
        this.listLoaiKhachSan = listLoaiKhachSan;
    }

}
