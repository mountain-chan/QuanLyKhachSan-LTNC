package model;

import java.io.Serializable;
import java.util.Date;

public class DatPhong implements Serializable {

    private static final long serialVersionUID = 5436453121L;
    
    int id;
    String taiKhoan;
    int idPhong;
    Date ngayDat;
    Date ngayDen;
    Date ngayTra;
    String dichVu;
    String ghiChu;
    int thanhTien;
    
    
}
