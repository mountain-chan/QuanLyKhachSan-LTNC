package filter;

import dao.SQLConnection;
import java.io.IOException;
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

@WebFilter(filterName = "IndexFilter", urlPatterns = {"*.xhtml"})
public class IndexFilter implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        return;
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        Cookie[] cookies = req.getCookies();
//        String TenTaiKhoan = null, MatKhau = null;
//        if (cookies != null) {
//            for (Cookie tmp : cookies) {
//                if (tmp.getName() == "TenTaiKhoan") {
//                    TenTaiKhoan = tmp.getValue();
//                } else if (tmp.getName() == "MatKhau") {
//                    MatKhau = tmp.getValue();
//                }
//            }
//        }
//        TaiKhoan tk = null;
//        if (TenTaiKhoan != null && MatKhau != null) {
//            tk = SQLConnection.getTaiKhoan(TenTaiKhoan, MatKhau);
//        }
////        tk=new TaiKhoan();
////        tk.setHoTen("dm");
////        tk.setIsAdmin(true);
//        if (tk != null) {
//            HttpSession session = req.getSession();
//            session.setAttribute("TaiKhoan", tk);
//        }
////        
////        TaiKhoan tk = (TaiKhoan) session.getAttribute("TaiKhoan");
//        response.setContentType("text/html;charset=UTF-8");
//        if (tk != null && tk.isIsAdmin()) {
//            request.getRequestDispatcher("Admin/admintaikhoan.xhtml").include(request, response);
//        } else {
//            request.getRequestDispatcher("index.xhtml").include(request, response);
//        }
////        tk = new TaiKhoan();
////        tk.setIsAdmin(true);
////        session.setAttribute("TaiKhoan", tk);
////        session.removeAttribute("attributeKey");
////        session.invalidate();
////        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
