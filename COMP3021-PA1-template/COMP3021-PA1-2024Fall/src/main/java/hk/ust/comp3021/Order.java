package hk.ust.comp3021;

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
    void calculateEstimatedTime(){

    }
}
