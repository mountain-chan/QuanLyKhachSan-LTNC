package bean;

import dao.SQLConnection;
import java.io.IOException;
import java.io.Serializable;
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

    private TaiKhoan dangNhap;

    public void DangNhap() {
        TaiKhoan tk = SQLConnection.getTaiKhoan(dangNhap.getTenTaiKhoan(), dangNhap.getMatKhau());
        if (tk != null) {
            msg.Message.addMessage("Thành Công", "Đăng nhập thành công!");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
//            response.setContentType("text/html");
            Cookie cookie = new Cookie("TenTaiKhoan", tk.getTenTaiKhoan());
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);
            cookie = new Cookie("MatKhau", tk.getMatKhau());
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);
            HttpSession session = request.getSession();
            session.setAttribute("TaiKhoan", tk);
            if (tk.isIsAdmin()) {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    ec.redirect(ec.getRequestContextPath() + "/faces/Admin/admintaikhoan.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            msg.Message.addMessage("Thất Bại", "Sai tên tài khoản hoặc mật khẩu!");
        }
    }

    public void DangXuat() {
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
            e.printStackTrace();
        }
    }

    public BeanDangNhap() {
        dangNhap = new TaiKhoan();
    }

    public TaiKhoan getDangNhap() {
        return dangNhap;
    }

    public void setDangNhap(TaiKhoan dangNhap) {
        this.dangNhap = dangNhap;
    }

}
