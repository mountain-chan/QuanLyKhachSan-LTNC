package bean;

import static bean.BeanKhachSan.hashKhachSan;
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

@ManagedBean(name = "beanPhong", eager = true)
@ApplicationScoped
public class BeanPhong implements Serializable {

    private static final long serialVersionUID = 1786783L;

    public static HashMap<Integer, KhachSan> hashPhongKhachSan;
    public static HashMap<Integer, String> hashPhong;
    
    private Phong phong;
    private ArrayList<Phong> listPhong;
    private Connection con;

    public BeanPhong() {
        phong = new Phong();
        hashPhongKhachSan = new HashMap();
        hashPhong = new HashMap();
        try {
            listPhong = new ArrayList();
            con = dao.SQLConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select P.Id, P.Ten, P.DienTich, P.GiaThue, P.TienNghi, "
                    + "P.MoTa, P.LoaiGiuong, P.IdKhachSan, K.Ten as TenKhachSan from Phong P, "
                    + "KhachSan K where P.IdKhachSan=K.Id");
            while (rs.next()) {
                Phong tmp = new Phong();
                tmp.setId(rs.getInt("Id"));
                tmp.setTen(rs.getString("Ten"));
                tmp.setDienTich(rs.getInt("DienTich"));
                tmp.setGiaThue(rs.getInt("GiaThue"));
                tmp.setTienNghi(rs.getString("TienNghi"));
                tmp.setMoTa(rs.getString("MoTa"));
                tmp.setLoaiGiuong(rs.getInt("LoaiGiuong"));
                tmp.setIdKhachSan(rs.getInt("IdKhachSan"));
                tmp.setTenKhachSan(rs.getString("TenKhachSan"));
                listPhong.add(tmp);
                KhachSan ks = new KhachSan();
                ks.setId(tmp.getIdKhachSan());
                ks.setTen(tmp.getTenKhachSan());
                hashPhongKhachSan.put(tmp.getId(), ks);
                hashPhong.put(tmp.getId(), tmp.getTen());
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void reset() {
        phong = new Phong();
        phong.setTen("");
        phong.setDienTich(0);
        phong.setGiaThue(0);
        phong.setTienNghi("");
        phong.setMoTa("");
        phong.setLoaiGiuong(0);
        phong.setIdKhachSan(1);
        phong.setTenKhachSan("");
    }

    public void insert(Phong tmp) {
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("insert into Phong output inserted.Id values(?,?,?,?,?,?,?)");
            stmt.setString(1, tmp.getTen());
            stmt.setInt(2, tmp.getDienTich());
            stmt.setInt(3, tmp.getGiaThue());
            stmt.setString(4, tmp.getTienNghi());
            stmt.setString(5, tmp.getMoTa());
            stmt.setInt(6, tmp.getLoaiGiuong());
            stmt.setInt(7, tmp.getIdKhachSan());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tmp.setId(rs.getInt("Id"));
                tmp.setTenKhachSan(hashKhachSan.get(tmp.getIdKhachSan()));
            }
            con.close();
            Phong p = new Phong(tmp);
            listPhong.add(p);
            pf.Message.addMessage("Thành Công", "Thêm Phòng thành công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Thêm Phòng thất bại!");
        }
    }

    public void update(Phong tmp) {
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("update Phong set Ten=?, DienTich=?, GiaThue=?, TienNghi=?, MoTa=?, LoaiGiuong=?, IdKhachSan=? where Id=?");
            stmt.setString(1, tmp.getTen());
            stmt.setInt(2, tmp.getDienTich());
            stmt.setInt(3, tmp.getGiaThue());
            stmt.setString(4, tmp.getTienNghi());
            stmt.setString(5, tmp.getMoTa());
            stmt.setInt(6, tmp.getLoaiGiuong());
            stmt.setInt(7, tmp.getIdKhachSan());
            stmt.setInt(8, tmp.getId());
            stmt.executeUpdate();
            con.close();
            int Id = tmp.getId();
            tmp.setTenKhachSan(hashKhachSan.get(tmp.getIdKhachSan()));
            for (Phong p : listPhong) {
                if (p.getId() == Id) {
                    p.reload(Id, tmp.getTen(), tmp.getDienTich(), tmp.getGiaThue(), tmp.getTienNghi(), tmp.getMoTa(), tmp.getLoaiGiuong(), tmp.getIdKhachSan(), tmp.getTenKhachSan());
                    break;
                }
            }
            pf.Message.addMessage("Thành Công", "Sửa Phòng thành công!");
        } catch (Exception e) {
            System.out.println(e.toString());
            pf.Message.errorMessage("Thất Bại", "Sửa Phòng thất bại!");
        }
    }

    public void delete(int Id) {
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("delete from Phong where id=?");
            stmt.setInt(1, Id);
            stmt.executeUpdate();
            con.close();
            for (Phong p : listPhong) {
                if (p.getId() == Id) {
                    listPhong.remove(p);
                    break;
                }
            }
            pf.Message.addMessage("Thành Công", "Xóa Phòng thành công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Xóa Phòng thất bại!");
        }
    }

    //
    // Get - Set, Don't care
    //
    public Phong getPhong() {
        return phong;
    }

    public void setPhong(Phong phong) {
        this.phong = phong;
    }

    public ArrayList<Phong> getListPhong() {
        return listPhong;
    }

    public void setListPhong(ArrayList<Phong> listPhong) {
        this.listPhong = listPhong;
    }

}
