package pf;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "calendarView", eager = true)
@ViewScoped
public class CalendarView implements Serializable {
 
    private List<Date> range;
    private Date minDate;
 
    @PostConstruct
    public void init() {
        long oneDay = 24 * 60 * 60 * 1000;
        Date today = new Date();
        minDate = today;
    }

    public List<Date> getRange() {
        return range;
    }

    public void setRange(List<Date> range) {
        this.range = range;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }
    
    
    
}
