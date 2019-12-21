package bean;

import static bean.BeanThanhPho.hashThanhPho;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import model.DatPhong;
import model.KhachSan;
import model.Phong;

@ManagedBean(name = "beanDatPhong", eager = true)
@SessionScoped
public class BeanDatPhong implements Serializable {

    private static final long serialVersionUID = 2786783L;

    private ArrayList<DatPhong> listDatPhong;
    private Phong phongDangDat;

    Connection con;

    public BeanDatPhong() {
        try {
            listDatPhong = new ArrayList();
            con = dao.SQLConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from DatPhong");
            while (rs.next()) {
                DatPhong tmp = new DatPhong();
                tmp.setId(rs.getInt("Id"));
                tmp.setTaiKhoan(rs.getString("TaiKhoan"));
                tmp.setIdPhong(rs.getInt("IdPhong"));
                tmp.setNgayDat(rs.getDate("NgayDat"));
                tmp.setNgayDen(rs.getDate("NgayDen"));
                tmp.setNgayTra(rs.getDate("NgayTra"));
                tmp.setDichVu(rs.getString("DichVu"));
                tmp.setGhiChu(rs.getString("GhiChu"));
                tmp.setThanhTien(rs.getInt("ThanhTien"));
                tmp.setDaHuy(rs.getBoolean("DaHuy"));
                listDatPhong.add(tmp);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void ThongTinPhong(Phong p) {
        phongDangDat = p;
    }

    //
    // Get - Set, Don't care
    //
    public ArrayList<DatPhong> getListDatPhong() {
        return listDatPhong;
    }

    public void setListDatPhong(ArrayList<DatPhong> listDatPhong) {
        this.listDatPhong = listDatPhong;
    }

    public Phong getPhongDangDat() {
        return phongDangDat;
    }

    public void setPhongDangDat(Phong phongDangDat) {
        this.phongDangDat = phongDangDat;
    }

}
