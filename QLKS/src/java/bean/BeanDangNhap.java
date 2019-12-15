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

    private static final long serialVersionUID = 1L;

    private TaiKhoan dangNhap;

    public void DangNhap() {
        TaiKhoan tk = SQLConnection.getTaiKhoan(dangNhap.getTenTaiKhoan(), dangNhap.getMatKhau());
        if (tk != null) {
            msg.Message.addMessage("Thành Công", "Đăng nhập thành công!");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            Cookie cookie = new Cookie("TenTaiKhoan", tk.getTenTaiKhoan());
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            cookie = new Cookie("MatKhau", tk.getMatKhau());
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                Cookie tmp;
                for (int i = 0; i < cookies.length; i++) {
                    tmp = cookies[i];
                    System.out.println("Value" + tmp.getValue());
                }
            }
            HttpSession session = request.getSession(); //(HttpSession) facesContext.getExternalContext().getSession(false);
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

    public BeanDangNhap() {
        dangNhap = new TaiKhoan();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
//        Cookie cookie = new Cookie("TenTaiKhoan2", "Haha");
//        cookie.setMaxAge(3600);
//        response.addCookie(cookie);
//        cookie = new Cookie("MatKhau2", "Hihi");
//        cookie.setMaxAge(3600);
//        response.addCookie(cookie);
        String TenTaiKhoan = null, MatKhau = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Cookie tmp;
            for (int i = 0; i < cookies.length; i++) {
                tmp = cookies[i];
                System.out.println("Value" + tmp.getValue());
                if (tmp.getName().equals("TenTaiKhoan")) {
                    TenTaiKhoan = tmp.getValue();
                } else if (tmp.getName().equals("MatKhau")) {
                    MatKhau = tmp.getValue();
                }
            }
        }
        System.out.println("Oh");
        TaiKhoan tk = null;
        if (TenTaiKhoan != null && MatKhau != null) {
            tk = SQLConnection.getTaiKhoan(TenTaiKhoan, MatKhau);
        }
        if (tk != null) {
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
        }
    }

    public TaiKhoan getDangNhap() {
        return dangNhap;
    }

    public void setDangNhap(TaiKhoan dangNhap) {
        this.dangNhap = dangNhap;
    }

}
