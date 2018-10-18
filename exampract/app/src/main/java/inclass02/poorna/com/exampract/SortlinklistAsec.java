package inclass02.poorna.com.exampract;

import java.util.Comparator;

/**
 * Created by poorn on 3/12/2018.
 */

public class SortlinklistAsec implements Comparator<iTunes> {
    @Override
    public int compare(iTunes t1, iTunes t2) {
        Double d1=t1.getPrice();
        Double d2=t2.getPrice();
        return d1.compareTo(d2);
    }
}
