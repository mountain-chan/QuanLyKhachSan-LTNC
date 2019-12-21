package bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
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
    @ManagedProperty(value = "#{beanPhong.listPhong}")
    private ArrayList<Phong> lstP;
    @ManagedProperty(value = "#{beanDatPhong.listDatPhong}")
    private ArrayList<DatPhong> lstDP;
    private ArrayList<KhachSan> listKhachSan;
    private ArrayList<KhachSan> listKhachSanSave;
    private KhachSan khachSan;
    private ArrayList<Phong> listPhong;
    private ArrayList<Phong> listPhongSave;
    private String tenThanhPhoTimKiem;
    private ArrayList<Date> thoiGianTimKiem;
    private Date minDate;
    private Date ngayDat;
    private Date ngayDen;
    private Date ngayDi;
    private boolean daKiemTraPhongTrong;
    Connection con;
    
    // Các danh sách lọc
    private ArrayList<Checkbox> listXepHang;
    private ArrayList<Checkbox> listLoaiKhachSan;
    private ArrayList<Checkbox> listBuaAn;
    private ArrayList<Checkbox> listCachTrungTam;
    private ArrayList<Checkbox> listGiapBien;

    // Khởi tạo, khi có session mới, tất cả các Bean SessionScoped sẽ được gọi bao gồm cả hàm này
    // Lí do Bean này là Session vì mỗi người (trình duyệt) sẽ có một trạng thái checkbox khác nhau
    // Nếu để ApplicationScoped thì bên máy này tích vào một ô thì máy khác cũng thấy thế
    public BeanNavigation() {
        // Ngày tìm kiếm nhập vào nhỏ nhất là hôm nay (new Date return hôm nay)
        minDate = new Date();
        ngayDat = new Date();
        ngayDen = new Date();
        ngayDi = new Date();
        thoiGianTimKiem = new ArrayList();
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

    // Reset Các thông tin tìm kiếm: xóa trạng thái đã tích của check box, nếu hàm này ko được
    // gọi thì tích vào checkbox nào, load lại trang checkbox vẫn còn, đồng thời hàm lưu lại danh sách
    // khách sạn đã tìm thấy listKhachSanSave, về sau sẽ lọc trong danh sách này và hiển thị bằng listKhachSan
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
    // Trước tiên đưa hết từ khóa tìm kiếm về chữ thường, sau đó loại bỏ dấu rồi mới
    // so sánh với các khách sạn trong danh sách
    public String TimKiem() {
        System.out.println("a");
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
    // Khởi tạo một danh sách, check trong danh sách toàn bộ khách sạn, khách sạn nào có Id thành phố
    // đúng bằng pageId thì cho vào danh sách, pageId chính là Id thành phố được truyền vào hàm
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
        listPhong = new ArrayList();
        for (Phong tmp : lstP) {
            if (tmp.getIdKhachSan() == pageId) {
                listPhong.add(tmp);
            }
        }
        daKiemTraPhongTrong = false;
        listPhongSave = listPhong;
        return "khachsan";
    }

    // Các hàm lọc, ý tưởng mỗi hàm lọc là dùng 1 biến check kiểm tra xem có ô nào được tích không
    // Chạy trong toàn bộ danh sách lọc, ô đó được tích thì xem khách sạn đang check có thỏa mãn ko
    // Nếu thỏa mãn cho vào danh sách, cuối cùng nếu check=false nghĩa là ko có ô nào được tích
    // => khách sạn đó thỏa mãn
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

    // Check một phòng đã bị đặt trong một khoảng thời gian cho trước chưa
    public void TimPhongTrong() {
        daKiemTraPhongTrong = true;
        listPhong = new ArrayList();
        for (Phong tmp : listPhongSave) {
            if (kiemTraPhongTrong(tmp, ngayDen, ngayDi)) {
                listPhong.add(tmp);
            }
        }
        System.out.println("Â");
    }

    public boolean kiemTraPhongTrong(Phong p, Date ngayDen, Date ngayDi) {
        for (DatPhong tmp : lstDP) {
            if (!tmp.isDaHuy() && tmp.getIdPhong() == p.getId()) {
                if (!(ngayDen.after(tmp.getNgayTra()) || ngayDi.before(tmp.getNgayDen()))) {
                    return false;
                }
            }
        }
        return true;
    }

    // Đặt phòng
    public void DatPhong(Phong tmp) {
        if (!daKiemTraPhongTrong){
            System.out.println(tmp.getTen());
            pf.Message.addMessage("Thất Bại", "Bạn vẫn chưa kiểm tra phòng trống!");
        }
//        try {
//            // Check đăng nhập trước
//            con = dao.SQLConnection.getConnection();
//            PreparedStatement stmt = con.prepareStatement("insert into DatPhong values(?,?,?,?,?,?,?,?,?,?)");
//            stmt.setString(1, "TaiKhoan");
//            ResultSet rs = stmt.executeQuery();
//            con.close();
//            listPhong.remove(tmp);
//            pf.Message.addMessage("Thành Công", "Đặt Phòng thành công, vui lòng vào Lịch Sử trong Trang Cá Nhân để xem thông tin Đặt Phòng!");
//        } catch (Exception e) {
//            pf.Message.errorMessage("Thất Bại", "Đặt Phòng thất bại!");
//        }
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

    public ArrayList<Date> getThoiGianTimKiem() {
        return thoiGianTimKiem;
    }

    public void setThoiGianTimKiem(ArrayList<Date> thoiGianTimKiem) {
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

    public ArrayList<Phong> getListPhong() {
        return listPhong;
    }

    public void setListPhong(ArrayList<Phong> listPhong) {
        this.listPhong = listPhong;
    }

    public ArrayList<Phong> getLstP() {
        return lstP;
    }

    public void setLstP(ArrayList<Phong> lstP) {
        this.lstP = lstP;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }

    public Date getNgayDen() {
        return ngayDen;
    }

    public void setNgayDen(Date ngayDen) {
        this.ngayDen = ngayDen;
    }

    public Date getNgayDi() {
        return ngayDi;
    }

    public void setNgayDi(Date ngayDi) {
        this.ngayDi = ngayDi;
    }

    public ArrayList<DatPhong> getLstDP() {
        return lstDP;
    }

    public void setLstDP(ArrayList<DatPhong> lstDP) {
        this.lstDP = lstDP;
    }

    public boolean isDaKiemTraPhongTrong() {
        return daKiemTraPhongTrong;
    }

    public void setDaKiemTraPhongTrong(boolean daKiemTraPhongTrong) {
        this.daKiemTraPhongTrong = daKiemTraPhongTrong;
    }

}
