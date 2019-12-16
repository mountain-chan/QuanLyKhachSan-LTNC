package filter;

import dao.SQLConnection;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.TaiKhoan;

@WebFilter(filterName = "Filter", urlPatterns = {"*.xhtml"})
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String reqURI = req.getRequestURI();
        HttpSession session = req.getSession();
        TaiKhoan tk = (TaiKhoan) session.getAttribute("TaiKhoan");
        if (tk == null) {
            Cookie[] cookies = req.getCookies();
            String TenTaiKhoan = null, MatKhau = null;
            if (cookies != null) {
                System.out.println("asdasdon roi");
                Cookie tmp;
                for (int i = 0; i < cookies.length; i++) {
                    tmp = cookies[i];
                    System.out.println("Value: " + tmp.getValue());
                    if (tmp.getName().equals("TenTaiKhoan")) {
                        TenTaiKhoan = tmp.getValue();
                    } else if (tmp.getName().equals("MatKhau")) {
                        MatKhau = tmp.getValue();
                    }
                }
                if (TenTaiKhoan != null && MatKhau != null) {
                    tk = SQLConnection.getTaiKhoan(TenTaiKhoan, MatKhau);
                }
                if (tk != null) {
                    session.setAttribute("TaiKhoan", tk);
                }
            } else {
                System.out.println("ko on roi");
            }
        }
        if (tk != null) {
            if (tk.isIsAdmin()) {
                if (!reqURI.contains("/Admin/")) {
                    res.sendRedirect(req.getContextPath() + "/faces/Admin/admintaikhoan.xhtml");
                } else {
                    chain.doFilter(request, response);
                }
            }
        }
        if (tk == null || !tk.isIsAdmin()) {
            if (reqURI.contains("/Admin/")) {
                res.sendRedirect(req.getContextPath() + "/faces/index.xhtml");
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
    }

}
