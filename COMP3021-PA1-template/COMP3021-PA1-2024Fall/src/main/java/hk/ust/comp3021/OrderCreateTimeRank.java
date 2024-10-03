package hk.ust.comp3021;

import javax.swing.plaf.nimbus.State;

public class OrderCreateTimeRank implements PendingOrderRank {
    public int compare(Order o1, Order o2){
        if (o1.getCreateTime().equals(o2.getCreateTime())) return 0;
        return o1.getCreateTime()>o2.getCreateTime()?1:-1;
    }

}
