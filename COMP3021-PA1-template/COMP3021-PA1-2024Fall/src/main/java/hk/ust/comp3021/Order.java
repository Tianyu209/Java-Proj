package hk.ust.comp3021;

import java.util.List;

public class Order {
    Long id;
    Integer status;
    Restaurant restaurant;
    Customer customer;
    Long createTime;
    Boolean isPayed;
    List<Dish> orderedDishes;
    Rider rider;
    Double estimatedTime;
    public Order(Long id,Integer status,Restaurant restaurant,Customer customer,Long createTime,Boolean isPayed,List<Dish>arr,Rider rider){
        this.id = id;
        this.status = status;
        this.restaurant =  restaurant;
        this.customer = customer;
        this.createTime = createTime;
        this.isPayed = isPayed;
        this.orderedDishes = arr;
        this.rider = rider;

    }
    public Double calculateEstimatedTime() {
        double v = Constants.DELIVERY_SPEED;
        double s = customer.location.distanceTo(restaurant.location);
        double d = restaurant.location.distanceTo(rider.location);
        return s/v +d/v;
    }


    public Customer getCustomer() {
        return customer;
    }

    public Long getId() {
    return id;
    }

    public List<Dish> getOrderedDishes() {
        return orderedDishes;
    }

    public Integer getStatus() {
        return status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Boolean getIsPayed() {
        return isPayed;
    }

    public Rider getRider() {
        return rider;
    }

    public Double getEstimatedTime() {
        return estimatedTime;
    }
}
