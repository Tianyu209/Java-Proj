package hk.ust.comp3021;

import java.math.BigDecimal;

public class Dish {
    Long id;
    String name;
    String desc;
    BigDecimal price;
    Long restaurantId;
    public Dish(Long id,String name,String desc,BigDecimal price,Long restaurantId){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.restaurantId = restaurantId;
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
