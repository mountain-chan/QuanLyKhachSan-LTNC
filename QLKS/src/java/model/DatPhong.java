package model;

import java.io.Serializable;
import java.util.Date;

public class DatPhong implements Serializable {

    private static final long serialVersionUID = 1L;
    
    int Id;
    String TaiKhoan;
    int IdPhong;
    Date NgayDat;
    Date NgayDen;
    Date NgayTra;
    String DichVu;
    String GhiChu;
    int ThanhTien;
}
