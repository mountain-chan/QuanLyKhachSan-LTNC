package model;

import java.io.Serializable;

public class BuaAn implements Serializable {

    private static final long serialVersionUID = 1124521354L;
    
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
