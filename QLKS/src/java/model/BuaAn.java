package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class BuaAn implements Serializable {

    private static final long serialVersionUID = 1124521354L;

    public static ArrayList<BuaAn> listBuaAn = new ArrayList() {
        {
            add(new BuaAn(0, "Không có"));
            add(new BuaAn(1, "Bữa Sáng"));
            add(new BuaAn(2, "Bữa Sáng Và Trưa"));
            add(new BuaAn(3, "Bữa Sáng Và Tối"));
            add(new BuaAn(4, "Cả Ba Bữa"));
        }
    };

    int Id;
    String Ten;

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

    public BuaAn() {
    }

    public BuaAn(int Id, String Ten) {
        this.Id = Id;
        this.Ten = Ten;
    }

}
