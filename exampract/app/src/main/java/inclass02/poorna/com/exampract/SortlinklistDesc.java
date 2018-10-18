package inclass02.poorna.com.exampract;

import java.util.Comparator;

/**
 * Created by poorn on 3/12/2018.
 */

public class SortlinklistDesc implements Comparator<iTunes> {

        public int compare(iTunes t1, iTunes t2) {
            Double d1=t1.getPrice();
            Double d2=t2.getPrice();
            return d2.compareTo(d1);
        }

}
