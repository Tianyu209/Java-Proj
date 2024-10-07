package hk.ust.comp3021;

import hk.ust.comp3021.rank.PendingOrderRank;



public class OrderCreateTimeRank implements PendingOrderRank {
    public int compare(Order o1, Order o2) {
        return Long.compare(o1.getCreateTime(), o2.getCreateTime());
    }
}
