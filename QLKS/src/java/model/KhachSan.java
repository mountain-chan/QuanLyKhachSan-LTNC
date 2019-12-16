package model;

import java.io.Serializable;

public class KhachSan implements Serializable  {

    private static final long serialVersionUID = 1234551L;
    
    int Id;
    String Ten;
    String DiaChi;
    String SoDienThoai;
    int CachTrungTam;
    String MoTa;
    boolean GiapBien;
    int DanhGia;
    int BuaAn;
    int IdThanhPho;
    String TenThanhPho;
    int IdLoaiKhachSan;
    String TenLoaiKhachSan;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String Ten) {
        this.Ten = Ten;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public int getCachTrungTam() {
        return CachTrungTam;
    }

    public void setCachTrungTam(int CachTrungTam) {
        this.CachTrungTam = CachTrungTam;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public boolean isGiapBien() {
        return GiapBien;
    }

    public void setGiapBien(boolean GiapBien) {
        this.GiapBien = GiapBien;
    }

    public int getDanhGia() {
        return DanhGia;
    }

    public void setDanhGia(int DanhGia) {
        this.DanhGia = DanhGia;
    }

    public int getBuaAn() {
        return BuaAn;
    }

    public void setBuaAn(int BuaAn) {
        this.BuaAn = BuaAn;
    }

    public int getIdThanhPho() {
        return IdThanhPho;
    }

    public void setIdThanhPho(int IdThanhPho) {
        this.IdThanhPho = IdThanhPho;
    }

    public void setTenThanhPho(String TenThanhPho) {
        this.TenThanhPho = TenThanhPho;
    }

    public String getTenThanhPho() {
        return TenThanhPho;
    }

    public int getIdLoaiKhachSan() {
        return IdLoaiKhachSan;
    }

    public void setIdLoaiKhachSan(int IdLoaiKhachSan) {
        this.IdLoaiKhachSan = IdLoaiKhachSan;
    }

    public String getTenLoaiKhachSan() {
        return TenLoaiKhachSan;
    }

    public void setTenLoaiKhachSan(String TenLoaiKhachSan) {
        this.TenLoaiKhachSan = TenLoaiKhachSan;
    }

    public KhachSan() {
    }

    public KhachSan(int Id, String Ten, String DiaChi, String SoDienThoai, int CachTrungTam, String MoTa, boolean GiapBien, int DanhGia, int BuaAn, int IdThanhPho, String TenThanhPho, int IdLoaiKhachSan, String TenLoaiKhachSan) {
        this.Id = Id;
        this.Ten = Ten;
        this.DiaChi = DiaChi;
        this.SoDienThoai = SoDienThoai;
        this.CachTrungTam = CachTrungTam;
        this.MoTa = MoTa;
        this.GiapBien = GiapBien;
        this.DanhGia = DanhGia;
        this.BuaAn = BuaAn;
        this.IdThanhPho = IdThanhPho;
        this.TenThanhPho = TenThanhPho;
        this.IdLoaiKhachSan = IdLoaiKhachSan;
        this.TenLoaiKhachSan = TenLoaiKhachSan;
    }

    public KhachSan(KhachSan ks) {
        this.Id = ks.Id;
        this.Ten = ks.Ten;
        this.DiaChi = ks.DiaChi;
        this.SoDienThoai = ks.SoDienThoai;
        this.CachTrungTam = ks.CachTrungTam;
        this.MoTa = ks.MoTa;
        this.GiapBien = ks.GiapBien;
        this.DanhGia = ks.DanhGia;
        this.BuaAn = ks.BuaAn;
        this.IdThanhPho = ks.IdThanhPho;
        this.TenThanhPho = ks.TenThanhPho;
        this.IdLoaiKhachSan = ks.IdLoaiKhachSan;
        this.TenLoaiKhachSan = ks.TenLoaiKhachSan;
    }
    
    public void reload(int Id, String Ten, String DiaChi, String SoDienThoai, int CachTrungTam, String MoTa, boolean GiapBien, int DanhGia, int BuaAn, int IdThanhPho, String TenThanhPho, int IdLoaiKhachSan, String TenLoaiKhachSan) {
        this.Id = Id;
        this.Ten = Ten;
        this.DiaChi = DiaChi;
        this.SoDienThoai = SoDienThoai;
        this.CachTrungTam = CachTrungTam;
        this.MoTa = MoTa;
        this.GiapBien = GiapBien;
        this.DanhGia = DanhGia;
        this.BuaAn = BuaAn;
        this.IdThanhPho = IdThanhPho;
        this.TenThanhPho = TenThanhPho;
        this.IdLoaiKhachSan = IdLoaiKhachSan;
        this.TenLoaiKhachSan = TenLoaiKhachSan;
    }
    
}
