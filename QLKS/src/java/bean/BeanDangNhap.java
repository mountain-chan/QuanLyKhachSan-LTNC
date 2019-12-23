package bean;

import dao.SQLConnection;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

@ManagedBean(name = "beanDangNhap", eager = true)
@SessionScoped
public class BeanDangNhap implements Serializable {

    private static final long serialVersionUID = 1437123L;

    private TaiKhoan taiKhoanDangNhap;
    private String nhapLaiMatKhau;
    Connection con;

    public BeanDangNhap() {
        taiKhoanDangNhap = new TaiKhoan();
    }

    public void dangNhap() {
        TaiKhoan tk = SQLConnection.getTaiKhoan(taiKhoanDangNhap.getTenTaiKhoan(), taiKhoanDangNhap.getMatKhau());
        if (tk != null) {
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
                    ec.redirect(ec.getRequestContextPath() + "/faces/Admin/admintaikhoan.xhtml");
                } catch (IOException e) {
                }
            }
        } else {
            pf.Message.errorMessage("Thất Bại", "Sai tên tài khoản hoặc mật khẩu!");
        }
    }

    public void dangKy() {
        if (taiKhoanDangNhap.getTenTaiKhoan().isEmpty() || taiKhoanDangNhap.getMatKhau().isEmpty()) {
            pf.Message.errorMessage("Thất Bại", "Không được để trống tài khoản hoặc mật khẩu!");
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

}
