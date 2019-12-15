package bean;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import model.*;

@ManagedBean(name = "beanNavigation")
@RequestScoped
public class BeanNavigation implements Serializable {

    @ManagedProperty(value = "#{param.pageId}")
    private int pageId;

    @ManagedProperty(value = "#{beanThanhPho.listThanhPho}")
    private ArrayList<ThanhPho> listThanhPho;

    @ManagedProperty(value = "#{beanLoaiKhachSan.listLoaiKhachSan}")
    private ArrayList<LoaiKhachSan> listLoaiKhachSan;

    @ManagedProperty(value = "#{beanKhachSan.listKhachSan}")
    private ArrayList<KhachSan> listKhachSan;
    
    private ThanhPho thanhPho;
    private LoaiKhachSan loaiKhachSan;
    private KhachSan khachSan;

    public String ThanhPho() {
        for (ThanhPho tmp : listThanhPho) {
            if (tmp.getId() == pageId) {
                thanhPho = tmp;
                break;
            }
        }
        return "thanhpho";
    }

    public String LoaiKhachSan() {
        for (LoaiKhachSan tmp : listLoaiKhachSan) {
            if (tmp.getId() == pageId) {
                loaiKhachSan = tmp;
                break;
            }
        }
        return "loaikhachsan";
    }

    public String KhachSan() {
        for (KhachSan tmp : listKhachSan) {
            if (tmp.getId() == pageId) {
                khachSan = tmp;
                break;
            }
        }
        return "loaikhachsan";
    }
    
    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public ArrayList<ThanhPho> getListThanhPho() {
        return listThanhPho;
    }

    public void setListThanhPho(ArrayList<ThanhPho> listThanhPho) {
        this.listThanhPho = listThanhPho;
    }

    public ArrayList<LoaiKhachSan> getListLoaiKhachSan() {
        return listLoaiKhachSan;
    }

    public void setListLoaiKhachSan(ArrayList<LoaiKhachSan> listLoaiKhachSan) {
        this.listLoaiKhachSan = listLoaiKhachSan;
    }

    public ThanhPho getThanhPho() {
        return thanhPho;
    }

    public void setThanhPho(ThanhPho thanhPho) {
        this.thanhPho = thanhPho;
    }

    public LoaiKhachSan getLoaiKhachSan() {
        return loaiKhachSan;
    }

    public void setLoaiKhachSan(LoaiKhachSan loaiKhachSan) {
        this.loaiKhachSan = loaiKhachSan;
    }

    public ArrayList<KhachSan> getListKhachSan() {
        return listKhachSan;
    }

    public void setListKhachSan(ArrayList<KhachSan> listKhachSan) {
        this.listKhachSan = listKhachSan;
    }

    public KhachSan getKhachSan() {
        return khachSan;
    }

    public void setKhachSan(KhachSan khachSan) {
        this.khachSan = khachSan;
    }

}
