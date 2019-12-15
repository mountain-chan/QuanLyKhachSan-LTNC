
import javax.faces.context.FacesContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionCreate implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie[] cookies = request.getCookies();
        System.out.println("bao nhieu");
        if (cookies != null) {
            Cookie tmp;
            for (int i = 0; i < cookies.length; i++) {
                tmp = cookies[i];
                System.out.println("Value" + tmp.getValue());
            }
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        
    }

}
