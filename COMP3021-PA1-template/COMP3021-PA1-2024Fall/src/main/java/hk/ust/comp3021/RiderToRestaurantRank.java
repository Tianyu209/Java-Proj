package hk.ust.comp3021;

public class RiderToRestaurantRank implements TaskRank{
    public int compare(Task t1,Task t2){
        return t1.rider.userRating.compareTo(t2.rider.userRating);
    };
}
