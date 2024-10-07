package hk.ust.comp3021;

public class RestaurantToCustomerDistanceRank implements PendingOrderRank{
    public int compare(Order o1,Order o2){
        return o1.getEstimatedTime().compareTo(o2.getEstimatedTime());
    };

}
