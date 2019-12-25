package bean;

import dao.SQLConnection;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "beanNguoiDung", eager = true)
@SessionScoped
public class BeanNguoiDung implements Serializable {

    private static final long serialVersionUID = 1437123L;
    private static final DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    @ManagedProperty(value = "#{beanKhachSan.listKhachSan}")
    private ArrayList<KhachSan> lstKS;
    @ManagedProperty(value = "#{beanNavigation.listDatPhong}")
    private ArrayList<DatPhong> lstDP;
    private TaiKhoan taiKhoanDangNhap;
    private String nhapLaiMatKhau;
    private KhachSan khachSanGoiY;
    private ArrayList<LichSu> listLichSu;
    private Connection con;

    public BeanNguoiDung() {
        taiKhoanDangNhap = new TaiKhoan();
    }

    public void dangNhap() {
        if (taiKhoanDangNhap.getTenTaiKhoan().isEmpty() || taiKhoanDangNhap.getMatKhau().isEmpty()) {
            pf.Message.errorMessage("Thất Bại", "Không được để trống Tên tài khoản hoặc Mật khẩu!");
            return;
        }
        TaiKhoan tk = SQLConnection.getTaiKhoan(taiKhoanDangNhap.getTenTaiKhoan(), taiKhoanDangNhap.getMatKhau());
        if (tk != null) {
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dialog_dangnhap').hide();");
            pf.Message.addMessage("Thành Công", "Đăng nhập thành công!");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            Cookie cookie = new Cookie("TenTaiKhoan", tk.getTenTaiKhoan());
            cookie.setMaxAge(36000);
            cookie.setPath("/");
            response.addCookie(cookie);
            cookie = new Cookie("MatKhau", tk.getMatKhau());
            cookie.setMaxAge(36000);
            cookie.setPath("/");
            response.addCookie(cookie);
            HttpSession session = request.getSession();
            session.setAttribute("TaiKhoan", tk);
            if (tk.isIsAdmin()) {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    ec.redirect(ec.getRequestContextPath() + "/faces/Admin/adminTaiKhoan.xhtml");
                } catch (IOException e) {
                }
            }
        } else {
            pf.Message.errorMessage("Thất Bại", "Sai tên tài khoản hoặc mật khẩu!");
        }
    }

    public void dangKy() {
        if (taiKhoanDangNhap.getTenTaiKhoan().isEmpty() || taiKhoanDangNhap.getMatKhau().isEmpty()) {
            pf.Message.errorMessage("Thất Bại", "Không được để trống Tên tài khoản hoặc Mật khẩu!");
            return;
        }
        if (!taiKhoanDangNhap.getMatKhau().equals(nhapLaiMatKhau)) {
            pf.Message.errorMessage("Thất Bại", "Mật khẩu không khớp!");
            return;
        }
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("insert into TaiKhoan values(?,?,?,?,?,?,?)");
            stmt.setString(1, taiKhoanDangNhap.getTenTaiKhoan());
            stmt.setString(2, taiKhoanDangNhap.getMatKhau());
            stmt.setString(3, taiKhoanDangNhap.getHoTen());
            stmt.setBoolean(4, taiKhoanDangNhap.isGioiTinh());
            stmt.setString(5, taiKhoanDangNhap.getSoDienThoai());
            stmt.setString(6, taiKhoanDangNhap.getEmail());
            stmt.setBoolean(7, false);
            stmt.executeUpdate();
            con.close();
            pf.Message.addMessage("Thành Công", "Đăng ký thành công!");
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dialog_dangky').hide();");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Tên tài khoản đã được sử dụng!");
        }
    }

    public void dangXuat() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        HttpSession session = request.getSession();
        session.invalidate();
        Cookie cookie = new Cookie("TenTaiKhoan", "");
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
        } catch (IOException e) {
        }
    }

    public void caNhan() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        TaiKhoan tk = (TaiKhoan) session.getAttribute("TaiKhoan");
        if (tk == null) {
            pf.Message.addMessage("Thông Báo", "Bạn cần đăng nhập trước!");
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dialog_dangnhap').show();");
            return;
        }
        taiKhoanDangNhap = new TaiKhoan(tk);
        nhapLaiMatKhau = tk.getMatKhau();
        Random rand = new Random();
        khachSanGoiY = lstKS.get(rand.nextInt(lstKS.size()));
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath() + "/faces/caNhan.xhtml");
        } catch (IOException e) {
        }
    }

    public void capNhatThongTin() {
        if (taiKhoanDangNhap.getMatKhau().isEmpty()) {
            pf.Message.errorMessage("Thất Bại", "Không được để trống mật khẩu!");
            return;
        }
        if (!taiKhoanDangNhap.getMatKhau().equals(nhapLaiMatKhau)) {
            pf.Message.errorMessage("Thất Bại", "Mật khẩu không khớp!");
            return;
        }
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("update TaiKhoan set MatKhau=?, HoTen=?, GioiTinh=?, SoDienThoai=?, Email=? where TenTaiKhoan=?");
            stmt.setString(1, taiKhoanDangNhap.getMatKhau());
            stmt.setString(2, taiKhoanDangNhap.getHoTen());
            stmt.setBoolean(3, taiKhoanDangNhap.isGioiTinh());
            stmt.setString(4, taiKhoanDangNhap.getSoDienThoai());
            stmt.setString(5, taiKhoanDangNhap.getEmail());
            stmt.setString(6, taiKhoanDangNhap.getTenTaiKhoan());
            stmt.executeUpdate();
            con.close();
            pf.Message.addMessage("Thành Công", "Cập nhật thành công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Cập nhật thất bại!");
        }
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        TaiKhoan tk = new TaiKhoan(taiKhoanDangNhap);
        session.setAttribute("TaiKhoan", tk);
    }

    public String lichSu() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        TaiKhoan tk = (TaiKhoan) session.getAttribute("TaiKhoan");
        listLichSu = new ArrayList();
        KhachSan ks;
        String thanhTien;
        Date homNay = new Date();
        int trangThai;
        String strNgayDat, strNgayDen, strNgayTra;
        for (DatPhong tmp : lstDP) {
            if (tmp.getTaiKhoan().equals(tk.getTenTaiKhoan())) {
                ks = BeanPhong.hashPhongKhachSan.get(tmp.getIdPhong());
                thanhTien = String.format("%,d", tmp.getThanhTien() * 1000);
                if (tmp.isDaHuy()) {
                    trangThai = 2;
                } else {
                    trangThai = (util.CompareDate.compareNoTime(tmp.getNgayDen(), homNay) == -1) ? 1 : 0;
                }
                strNgayDat = formatter.format(tmp.getNgayDat());
                strNgayDen = formatter.format(tmp.getNgayDen());
                strNgayTra = formatter.format(tmp.getNgayTra());
                LichSu ls = new LichSu(tmp.getId(), BeanPhong.hashPhong.get(tmp.getIdPhong()), ks.getId(),
                        ks.getTen(), strNgayDat, strNgayDen, strNgayTra,
                        tmp.getDichVu(), tmp.getGhiChu(), thanhTien, trangThai);
                listLichSu.add(0, ls);
            }
        }
        return "lichSu";
    }

    public void huyDatPhong(int id) {
        try {
            con = dao.SQLConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement("update DatPhong set DaHuy=? where Id=?");
            stmt.setBoolean(1, true);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            con.close();
            for (LichSu tmp : listLichSu) {
                if (tmp.getId() == id) {
                    tmp.setTrangThai(2);
                    break;
                }
            }
            for (DatPhong tmp : lstDP) {
                if (tmp.getId() == id) {
                    tmp.setDaHuy(true);
                    break;
                }
            }
            pf.Message.addMessage("Thành Công", "Hủy đặt phòng thành công!");
        } catch (Exception e) {
            pf.Message.errorMessage("Thất Bại", "Hủy đặt phòng thất bại!");
        }
    }

    //
    // Get - Set, Don't care
    //
    public TaiKhoan getTaiKhoanDangNhap() {
        return taiKhoanDangNhap;
    }

    public void setTaiKhoanDangNhap(TaiKhoan taiKhoanDangNhap) {
        this.taiKhoanDangNhap = taiKhoanDangNhap;
    }

    public String getNhapLaiMatKhau() {
        return nhapLaiMatKhau;
    }

    public void setNhapLaiMatKhau(String nhapLaiMatKhau) {
        this.nhapLaiMatKhau = nhapLaiMatKhau;
    }

    public KhachSan getKhachSanGoiY() {
        return khachSanGoiY;
    }

    public void setKhachSanGoiY(KhachSan khachSanGoiY) {
        this.khachSanGoiY = khachSanGoiY;
    }

    public ArrayList<KhachSan> getLstKS() {
        return lstKS;
    }

    public void setLstKS(ArrayList<KhachSan> lstKS) {
        this.lstKS = lstKS;
    }

    public ArrayList<DatPhong> getLstDP() {
        return lstDP;
    }

    public void setLstDP(ArrayList<DatPhong> lstDP) {
        this.lstDP = lstDP;
    }

    public ArrayList<LichSu> getListLichSu() {
        return listLichSu;
    }

    public void setListLichSu(ArrayList<LichSu> listLichSu) {
        this.listLichSu = listLichSu;
    }

}
