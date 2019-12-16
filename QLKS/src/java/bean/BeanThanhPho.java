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

@ManagedBean(name = "beanThanhPho", eager = true)
@ApplicationScoped
public class BeanThanhPho implements Serializable {

    private static final long serialVersionUID = 1124771L;
    
    public static HashMap<Integer, String> hashThanhPho;

    ThanhPho thanhPho;
    ArrayList<ThanhPho> listThanhPho;
    Connection con;

    public ThanhPho getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(ThanhPho thanhPho) {
        this.thanhPho = thanhPho;
    }

    public ArrayList<ThanhPho> getListThanhPho() {
        return listThanhPho;
    }
    
    public BeanThanhPho() {
        try {
            thanhPho = new ThanhPho();
            hashThanhPho = new HashMap();
            listThanhPho = new ArrayList();
            con = dao.SQLConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ThanhPho");
            while (rs.next()) {
                ThanhPho tmp = new ThanhPho();
                tmp.setId(rs.getInt("Id"));
                tmp.setTen(rs.getString("Ten"));
                tmp.setMoTa(rs.getString("MoTa"));
                tmp.setUrlHinhAnh(rs.getString("UrlHinhAnh"));
                listThanhPho.add(tmp);
                hashThanhPho.put(tmp.getId(), tmp.getTen());
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void reset() {
        thanhPho.setTen("");
        thanhPho.setMoTa("");
        thanhPho.setUrlHinhAnh("");
    }

    public void insert(ThanhPho tmp) {
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("insert into ThanhPho output inserted.Id values(?,?,?)");
            stmt.setString(1, tmp.getTen());
            stmt.setString(2, tmp.getMoTa());
            stmt.setString(3, tmp.getUrlHinhAnh());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tmp.setId(rs.getInt("Id"));
            }
            con.close();
            ThanhPho tp = new ThanhPho(tmp);
            listThanhPho.add(tp);
            pf.Message.addMessage("Thành Công", "Thêm Thành Phố Thành Công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Thêm Thành Phố Thất Bại!");
        }
    }

    public void update(ThanhPho tmp) {
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("update ThanhPho set Ten=?, MoTa=?, UrlHinhAnh=? where Id=?");
            stmt.setString(1, tmp.getTen());
            stmt.setString(2, tmp.getMoTa());
            stmt.setString(3, tmp.getUrlHinhAnh());
            stmt.setInt(4, tmp.getId());
            stmt.executeUpdate();
            con.close();
            int id = tmp.getId();
            for (ThanhPho tp : listThanhPho) {
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
            PreparedStatement stmt = con.prepareStatement("delete from ThanhPho where Id=?");
            stmt.setInt(1, Id);
            stmt.executeUpdate();
            con.close();
            for (ThanhPho tp : listThanhPho) {
                if (tp.getId() == Id) {
                    listThanhPho.remove(tp);
                    break;
                }
            }
            pf.Message.addMessage("Thành Công", "Xóa Thành Phố Thành Công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Xóa Thành Phố Thất Bại!");
        }
    }

}
