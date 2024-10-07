package hk.ust.comp3021;

import hk.ust.comp3021.rank.PendingOrderRank;

public class CustomerPriorityRank implements PendingOrderRank {
    public int compare(Order o1, Order o2){
        return Integer.compare(o1.getCustomer().customerType,o2.getCustomer().customerType);
    }
}
