package hk.ust.comp3021;

import java.util.LinkedList;
import java.util.List;

public class Restaurant extends Account{
    String district;
    String street;
    List<Dish> dishes;
    public Restaurant(Long id,String accountType, String name, String contactNumber, Location location, String district, String street){
        this.id = id;
        this.accountType = accountType;
        this.contactNumber = contactNumber;
        this.name = name;
        this.location = location;
        this.street = street;
        this.district = district;
    }
    public void register(){
        Account.accountManager.addRestaurant(this);
    }
    void addDish(Dish d){
        dishes.add(d);
    }
    Restaurant getRestaurantById(Long l){
        return Account.accountManager.getRestaurantById(l);
    }
    /// Do not modify this method.
    @Override
    public String toString() {

        List<Long> dishIds = new LinkedList<>(dishes.stream().map(Dish::getId).toList());
        dishIds.sort(Long::compareTo);
        return "Restaurant{" +
                "id=" + id +
                ", accountType='" + accountType + '\'' +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", location=" + location +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", dishIds='" + dishIds + '\'' +
                '}';
    }

}
