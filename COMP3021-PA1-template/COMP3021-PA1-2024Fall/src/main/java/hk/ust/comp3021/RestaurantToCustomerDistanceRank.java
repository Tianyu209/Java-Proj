package hk.ust.comp3021;

public class RestaurantToCustomerDistanceRank implements PendingOrderRank{
    public int compare(Order o1, Order o2) {
        double distance1 = o1.getRestaurant().location.distanceTo(o1.getCustomer().location);
        double distance2 = o2.getRestaurant().location.distanceTo(o2.getCustomer().location);

        return Double.compare(distance1, distance2);
    }

}
