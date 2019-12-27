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

@ManagedBean(name = "beanThanhPho", eager = true)
@ApplicationScoped
public class BeanThanhPho implements Serializable {

    private static final long serialVersionUID = 1124771L;
    private static final String url = "Content/Images/ThanhPho/";

    public static HashMap<Integer, String> hashThanhPho;
    
    private ThanhPho thanhPho;
    private UploadedFile file;
    private ArrayList<ThanhPho> listThanhPho;
    private Connection con;

    public BeanThanhPho() {
        try {
            thanhPho = new ThanhPho();
            hashThanhPho = new HashMap();
            listThanhPho = new ArrayList();
            con = dao.SQLConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select T.Id as A, T.Ten as B, T.MoTa as C, "
                    + "T.UrlHinhAnh as D, count(K.Id) as E from ThanhPho T left join KhachSan K "
                    + "on T.Id = K.IdThanhPho group by T.Id, T.Ten, T.MoTa, T.UrlHinhAnh");
            while (rs.next()) {
                ThanhPho tmp = new ThanhPho();
                tmp.setId(rs.getInt("A"));
                tmp.setTen(rs.getString("B"));
                tmp.setMoTa(rs.getString("C"));
                tmp.setUrlHinhAnh(rs.getString("D"));
                tmp.setSoKhachSan(String.format("%,d", rs.getInt("E") * 135));
                listThanhPho.add(tmp);
                hashThanhPho.put(tmp.getId(), tmp.getTen());
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void reset() {
        thanhPho = new ThanhPho();
        thanhPho.setTen("");
        thanhPho.setMoTa("");
        thanhPho.setUrlHinhAnh("");
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        thanhPho.setUrlHinhAnh(url + file.getFileName());
    }

    public void insert(ThanhPho tmp) {
        if (tmp.getTen().length() == 0 || file == null) {
            pf.Message.errorMessage("Thất Bại", "Không được để trống tên hoặc hình ảnh!");
            return;
        }
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            File f = new File(path + url + file.getFileName());
            try (FileOutputStream fos = new FileOutputStream(f)) {
                byte[] content = file.getContents();
                fos.write(content);
            }
            file = null;
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
            pf.Message.addMessage("Thành Công", "Thêm Thành phố thành công!");
        } catch (Exception e) {
            System.out.println("loi:" + e.toString());
            pf.Message.errorMessage("Thất Bại", "Thêm Thành phố thất bại!");
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialog_them').hide();");
    }

    public void update(ThanhPho tmp) {
        if (tmp.getTen().length() == 0) {
            pf.Message.errorMessage("Thất Bại", "Không được để trống tên!");
            return;
        }
        try {
            if (file != null) {
                String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
                File f = new File(path + url + file.getFileName());
                try (FileOutputStream fos = new FileOutputStream(f)) {
                    byte[] content = file.getContents();
                    fos.write(content);
                }
                file = null;
            }
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
            pf.Message.addMessage("Thành Công", "Sửa Thành phố thành công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Sửa Thành Phố thất bại!");
        }
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialog_sua').hide();");
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
            pf.Message.addMessage("Thành Công", "Xóa Thành phố thành công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Xóa Thành phố thất bại!");
        }
    }

    //
    // Get - Set, Don't care
    //
    public ThanhPho getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(ThanhPho thanhPho) {
        this.thanhPho = thanhPho;
    }

    public ArrayList<ThanhPho> getListThanhPho() {
        return listThanhPho;
    }

    public void setListThanhPho(ArrayList<ThanhPho> listThanhPho) {
        this.listThanhPho = listThanhPho;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
