package bean;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import model.*;

@ManagedBean(name = "beanNavigation")
@SessionScoped
public class BeanNavigation implements Serializable {

    private static final long serialVersionUID = 16533786L;

    @ManagedProperty(value = "#{beanKhachSan.listKhachSan}")
    private ArrayList<KhachSan> lstKS;

    private ArrayList<KhachSan> listKhachSan;

    public String ThanhPho(int pageId) {
        listKhachSan = new ArrayList();
        for (KhachSan tmp : lstKS) {
            if (tmp.getIdThanhPho() == pageId) {
                listKhachSan.add(tmp);
            }
        }
        return "dskhachsan";
    }

    public String LoaiKhachSan(int pageId) {
        listKhachSan = new ArrayList();
        for (KhachSan tmp : lstKS) {
            if (tmp.getIdLoaiKhachSan() == pageId) {
                listKhachSan.add(tmp);
            }
        }
        return "dskhachsan";
    }

    public String KhachSan(int pageId) {
        System.out.println("???88");
        listKhachSan = new ArrayList();
        for (KhachSan tmp : lstKS) {
            if (tmp.getId() == pageId) {
                listKhachSan.add(tmp);
                break;
            }
        }
        return "dskhachsan";
    }

    public ArrayList<KhachSan> getLstKS() {
        return lstKS;
    }

    public void setLstKS(ArrayList<KhachSan> lstKS) {
        this.lstKS = lstKS;
    }

    public ArrayList<KhachSan> getListKhachSan() {
        return listKhachSan;
    }

    public void setListKhachSan(ArrayList<KhachSan> listKhachSan) {
        this.listKhachSan = listKhachSan;
    }

}
