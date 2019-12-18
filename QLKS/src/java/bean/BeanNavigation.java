package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
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
    private ArrayList<KhachSan> listKhachSanSave;
    private KhachSan khachSan;
    private String tenThanhPhoTimKiem;
    private List<Date> thoiGianTimKiem;
    private Date minDate;

    // Các danh sách lọc
    private ArrayList<Checkbox> listXepHang;
    private ArrayList<Checkbox> listLoaiKhachSan;
    private ArrayList<Checkbox> listBuaAn;
    private ArrayList<Checkbox> listCachTrungTam;
    private ArrayList<Checkbox> listGiapBien;

    // Khởi tạo
    public BeanNavigation() {
        // Ngày tìm kiếm nhập vào nhỏ nhất là hôm nay (new Date return hôm nay)
        minDate = new Date();
        // Khởi tạo danh sách lọc
        listXepHang = new ArrayList();
        listXepHang.add(new Checkbox("Không xếp hạng"));
        listXepHang.add(new Checkbox("1 Sao"));
        listXepHang.add(new Checkbox("2 Sao"));
        listXepHang.add(new Checkbox("3 Sao"));
        listXepHang.add(new Checkbox("4 Sao"));
        listXepHang.add(new Checkbox("5 Sao"));
        listLoaiKhachSan = new ArrayList();
        for (Entry<Integer, String> entry : BeanLoaiKhachSan.hashLoaiKhachSan.entrySet()) {
            listLoaiKhachSan.add(new Checkbox(entry.getValue()));
        }
        listBuaAn = new ArrayList();
        for (BuaAn tmp : BuaAn.listBuaAn) {
            listBuaAn.add(new Checkbox(tmp.getTen()));
        }
        listCachTrungTam = new ArrayList();
        listCachTrungTam.add(new Checkbox("Dưới 1 Km"));
        listCachTrungTam.add(new Checkbox("Dưới 3 Km"));
        listCachTrungTam.add(new Checkbox("Dưới 5 Km"));
        listGiapBien = new ArrayList();
        listGiapBien.add(new Checkbox("Không giáp"));
        listGiapBien.add(new Checkbox("Có giáp"));
    }

    // Reset Các thông tin tìm kiếm
    private void resetTimKiem() {
        listXepHang.forEach((tmp) -> {
            tmp.setChecked(false);
        });
        listLoaiKhachSan.forEach((tmp) -> {
            tmp.setChecked(false);
        });
        listBuaAn.forEach((tmp) -> {
            tmp.setChecked(false);
        });
        listCachTrungTam.forEach((tmp) -> {
            tmp.setChecked(false);
        });
        listGiapBien.forEach((tmp) -> {
            tmp.setChecked(false);
        });
        listKhachSanSave = listKhachSan;
    }

    // Các link trên Header
    public String TrangChu() {
        return "index";
    }

    public String ToanBoKhachSan() {
        listKhachSan = new ArrayList();
        for (KhachSan tmp : lstKS) {
            listKhachSan.add(tmp);
        }
        resetTimKiem();
        return "dskhachsan";
    }

    public String CaNhan() {
        return "index";
    }

    public String TinTuc() {
        return "index";
    }

    public String LienHe() {
        return "index";
    }

    // Tìm kiếm ở Trang chủ, mới chỉ có tìm theo Thành phố, chưa có theo thời gian
    public String TimKiem() {
        String tenThanhPhoKoDau = util.VNCharacterUtils.removeAccent(tenThanhPhoTimKiem.toLowerCase());
        String s;
        listKhachSan = new ArrayList();
        for (KhachSan tmp : lstKS) {
            s = util.VNCharacterUtils.removeAccent(tmp.getTenThanhPho().toLowerCase());
            if (s.contains(tenThanhPhoKoDau)) {
                listKhachSan.add(tmp);
            }
        }
        resetTimKiem();
        return "dskhachsan";
    }

    // Các link khi bấm chọn một Thành phố, Loại khách sạn, Khách sạn
    public String ThanhPho(int pageId) {
        listKhachSan = new ArrayList();
        for (KhachSan tmp : lstKS) {
            if (tmp.getIdThanhPho() == pageId) {
                listKhachSan.add(tmp);
            }
        }
        resetTimKiem();
        return "dskhachsan";
    }

    public String LoaiKhachSan(int pageId) {
        listKhachSan = new ArrayList();
        for (KhachSan tmp : lstKS) {
            if (tmp.getIdLoaiKhachSan() == pageId) {
                listKhachSan.add(tmp);
            }
        }
        resetTimKiem();
        return "dskhachsan";
    }

    public String KhachSan(int pageId) {
        for (KhachSan tmp : lstKS) {
            if (tmp.getId() == pageId) {
                khachSan = tmp;
                break;
            }
        }
        return "khachsan";
    }

    // Các hàm lọc
    public void Loc() {
        ArrayList<KhachSan> lst = new ArrayList();
        for (KhachSan tmp : listKhachSanSave) {
            if (locXepHang(tmp) && locLoaiKhachSan(tmp) && locBuaAn(tmp) && locCachTrungTam(tmp) && locGiapBien(tmp)) {
                lst.add(tmp);
            }
        }
        listKhachSan = lst;
    }

    private boolean locXepHang(KhachSan ks) {
        boolean check = false;
        for (int i = 0; i <= 5; i++) {
            if (listXepHang.get(i).isChecked()) {
                check = true;
                if (ks.getDanhGia() == i) {
                    return true;
                }
            }
        }
        return !check;
    }

    private boolean locLoaiKhachSan(KhachSan ks) {
        boolean check = false;
        for (Checkbox tmp : listLoaiKhachSan) {
            if (tmp.isChecked()) {
                check = true;
                if (ks.getTenLoaiKhachSan().equals(tmp.getLabel())) {
                    return true;
                }
            }
        }
        return !check;
    }

    private boolean locBuaAn(KhachSan ks) {
        boolean check = false;
        for (int i = 0; i <= 4; i++) {
            if (listBuaAn.get(i).isChecked()) {
                check = true;
                if (ks.getBuaAn() == i) {
                    return true;
                }
            }
        }
        return !check;
    }

    private boolean locCachTrungTam(KhachSan ks) {
        boolean check = false;
        int khoangCach = ks.getCachTrungTam();
        if (listCachTrungTam.get(0).isChecked()) {
            check = true;
            if (khoangCach < 1000) {
                return true;
            }
        }
        if (listCachTrungTam.get(1).isChecked()) {
            check = true;
            if (khoangCach < 3000) {
                return true;
            }
        }
        if (listCachTrungTam.get(2).isChecked()) {
            check = true;
            if (khoangCach < 5000) {
                return true;
            }
        }
        return !check;
    }

    private boolean locGiapBien(KhachSan ks) {
        boolean check = false;
        boolean giapBien = ks.isGiapBien();
        if (listGiapBien.get(0).isChecked()) {
            check = true;
            if (giapBien == false) {
                return true;
            }
        }
        if (listGiapBien.get(1).isChecked()) {
            check = true;
            if (giapBien == true) {
                return true;
            }
        }
        return !check;
    }

    //
    // Get - Set, Don't care
    //
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

    public String getTenThanhPhoTimKiem() {
        return tenThanhPhoTimKiem;
    }

    public void setTenThanhPhoTimKiem(String tenThanhPhoTimKiem) {
        this.tenThanhPhoTimKiem = tenThanhPhoTimKiem;
    }

    public List<Date> getThoiGianTimKiem() {
        return thoiGianTimKiem;
    }

    public void setThoiGianTimKiem(List<Date> thoiGianTimKiem) {
        this.thoiGianTimKiem = thoiGianTimKiem;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public ArrayList<Checkbox> getListXepHang() {
        return listXepHang;
    }

    public void setListXepHang(ArrayList<Checkbox> listXepHang) {
        this.listXepHang = listXepHang;
    }

    public ArrayList<Checkbox> getListLoaiKhachSan() {
        return listLoaiKhachSan;
    }

    public void setListLoaiKhachSan(ArrayList<Checkbox> listLoaiKhachSan) {
        this.listLoaiKhachSan = listLoaiKhachSan;
    }

    public ArrayList<Checkbox> getListBuaAn() {
        return listBuaAn;
    }

    public void setListBuaAn(ArrayList<Checkbox> listBuaAn) {
        this.listBuaAn = listBuaAn;
    }

    public ArrayList<Checkbox> getListCachTrungTam() {
        return listCachTrungTam;
    }

    public void setListCachTrungTam(ArrayList<Checkbox> listCachTrungTam) {
        this.listCachTrungTam = listCachTrungTam;
    }

    public ArrayList<Checkbox> getListGiapBien() {
        return listGiapBien;
    }

    public void setListGiapBien(ArrayList<Checkbox> listGiapBien) {
        this.listGiapBien = listGiapBien;
    }

    public KhachSan getKhachSan() {
        return khachSan;
    }

    public void setKhachSan(KhachSan khachSan) {
        this.khachSan = khachSan;
    }

}
