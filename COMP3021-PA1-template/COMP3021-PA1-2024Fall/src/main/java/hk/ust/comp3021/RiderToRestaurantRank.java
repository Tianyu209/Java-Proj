package hk.ust.comp3021;

import hk.ust.comp3021.rank.TaskRank;

public class RiderToRestaurantRank implements TaskRank {
    public int compare(Task t1,Task t2){
        double distance1 = t1.rider.location.distanceTo(t1.order.getRestaurant().location);
        double distance2 = t2.rider.location.distanceTo(t2.order.getRestaurant().location);

        return Double.compare(distance1, distance2);
    }
}
