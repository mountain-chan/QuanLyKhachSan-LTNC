package util;

import java.util.Date;

public class CompareDate {

    public static int compareNoTime(Date date1, Date date2) {
        int d1 = date1.getDate(), m1 = date1.getMonth(), y1 = date1.getYear();
        int d2 = date2.getDate(), m2 = date2.getMonth(), y2 = date2.getYear();
        if (y1 == y2) {
            if (m1 == m2) {
                if (d1 == d2) {
                    return 0;
                }
                if (d1 < d2) {
                    return -1;
                }
                return 1;
            }
            if (m1 < m2) {
                return -1;
            }
            return 1;
        }
        if (y1 < y2) {
            return -1;
        }
        return 1;
    }
}
