package bean;

import dao.SQLConnection;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.*;

@ManagedBean(name = "beanDangNhap")
@SessionScoped
public class BeanDangNhap {

    private TaiKhoan dangNhap;

    public void DangNhap() throws IOException {
        TaiKhoan tk = SQLConnection.getTaiKhoan(dangNhap.getTenTaiKhoan(), dangNhap.getMatKhau());
        if (tk != null) {
            msg.Message.addMessage("Thành Công", "Đăng nhập thành công!");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
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
    }

    public TaiKhoan getDangNhap() {
        return dangNhap;
    }

    public void setDangNhap(TaiKhoan dangNhap) {
        this.dangNhap = dangNhap;
    }

}
