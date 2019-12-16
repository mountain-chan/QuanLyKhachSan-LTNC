package model;

import java.io.Serializable;

public class TaiKhoan implements Serializable {

    private static final long serialVersionUID = 45312311L;
    
    String TenTaiKhoan;
    String MatKhau;
    String HoTen;
    boolean GioiTinh;
    String SoDienThoai;
    String Email;
    boolean IsAdmin;

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String TenTaiKhoan) {
        this.TenTaiKhoan = TenTaiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public boolean isIsAdmin() {
        return IsAdmin;
    }

    public void setIsAdmin(boolean IsAdmin) {
        this.IsAdmin = IsAdmin;
    }

    public TaiKhoan() {
    }

    public TaiKhoan(String TenTaiKhoan, String MatKhau, String HoTen, boolean GioiTinh, String SoDienThoai, String Email, boolean IsAdmin) {
        this.TenTaiKhoan = TenTaiKhoan;
        this.MatKhau = MatKhau;
        this.HoTen = HoTen;
        this.GioiTinh = GioiTinh;
        this.SoDienThoai = SoDienThoai;
        this.Email = Email;
        this.IsAdmin = IsAdmin;
    }
    
    public TaiKhoan(TaiKhoan tk){
        this.TenTaiKhoan = tk.TenTaiKhoan;
        this.MatKhau = tk.MatKhau;
        this.HoTen = tk.HoTen;
        this.GioiTinh = tk.GioiTinh;
        this.SoDienThoai = tk.SoDienThoai;
        this.Email = tk.Email;
        this.IsAdmin = tk.IsAdmin;
    }
    
    public void reload(String TenTaiKhoan, String MatKhau, String HoTen, boolean GioiTinh, String SoDienThoai, String Email, boolean IsAdmin) {
        this.TenTaiKhoan = TenTaiKhoan;
        this.MatKhau = MatKhau;
        this.HoTen = HoTen;
        this.GioiTinh = GioiTinh;
        this.SoDienThoai = SoDienThoai;
        this.Email = Email;
        this.IsAdmin = IsAdmin;
    }
    
}
