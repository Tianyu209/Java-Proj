package hk.ust.comp3021;

public class CustomerPriorityRank implements PendingOrderRank {
    public int compare(Order o1, Order o2){
        if (o1.getCustomer().customerType.equals(o2.getCustomer().customerType) ) return 0;
        return o1.getCustomer().customerType==1?1:-1;
    };

}
