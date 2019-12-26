package bean;

import java.io.File;
import java.io.FileOutputStream;
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
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "beanLoaiKhachSan", eager = true)
@ApplicationScoped
public class BeanLoaiKhachSan implements Serializable {

    private static final long serialVersionUID = 185755L;

    public static HashMap<Integer, String> hashLoaiKhachSan;
    
    private UploadedFile file;
    private LoaiKhachSan loaiKhachSan;
    private ArrayList<LoaiKhachSan> listLoaiKhachSan;
    private Connection con;

    public BeanLoaiKhachSan() {
        try {
            loaiKhachSan = new LoaiKhachSan();
            hashLoaiKhachSan = new HashMap();
            listLoaiKhachSan = new ArrayList();
            con = dao.SQLConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select L.Id as A, L.Ten as B, L.MoTa as C, "
                    + "L.UrlHinhAnh as D, count(L.Id) as E from LoaiKhachSan L left join KhachSan K "
                    + "on L.Id = K.IdLoaiKhachSan group by L.Id, L.Ten, L.MoTa, L.UrlHinhAnh");
            while (rs.next()) {
                LoaiKhachSan tmp = new LoaiKhachSan();
                tmp.setId(rs.getInt("A"));
                tmp.setTen(rs.getString("B"));
                tmp.setMoTa(rs.getString("C"));
                tmp.setUrlHinhAnh(rs.getString("D"));
                tmp.setSoKhachSan(String.format("%,d", rs.getInt("E") * 135));
                listLoaiKhachSan.add(tmp);
                hashLoaiKhachSan.put(tmp.getId(), tmp.getTen());
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void reset() {
        loaiKhachSan = new LoaiKhachSan();
        loaiKhachSan.setTen("");
        loaiKhachSan.setMoTa("");
        loaiKhachSan.setUrlHinhAnh("");
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        loaiKhachSan.setUrlHinhAnh("Content/Images/LoaiKhachSan/" + file.getFileName());
    }
    
    public void insert(LoaiKhachSan tmp) {
        if (tmp.getTen().length() == 0 || file == null) {
            pf.Message.errorMessage("Thất Bại", "Không được để trống tên hoặc hình ảnh!");
            return;
        }
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            File f = new File(path + "Content/Images/LoaiKhachSan/" + file.getFileName());
            try (FileOutputStream fos = new FileOutputStream(f)) {
                byte[] content = file.getContents();
                fos.write(content);
            }
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
            pf.Message.addMessage("Thành Công", "Thêm Loại khách sạn thành công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Thêm Loại khách sạn thất bại!");
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialog_them').hide();");
    }

    public void update(LoaiKhachSan tmp) {
        if (tmp.getTen().length() == 0 || file == null) {
            pf.Message.errorMessage("Thất Bại", "Không được để trống tên hoặc hình ảnh!");
            return;
        }
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            File f = new File(path + "Content/Images/LoaiKhachSan/" + file.getFileName());
            try (FileOutputStream fos = new FileOutputStream(f)) {
                byte[] content = file.getContents();
                fos.write(content);
            }
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
            pf.Message.addMessage("Thành Công", "Sửa Loại khách sạn thành công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Sửa Loại khách sạn thất bại!");
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialog_sua').hide();");
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
            pf.Message.addMessage("Thành Công", "Xóa Loại khách sạn thành công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Xóa Loại khách sạn thất bại!");
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
