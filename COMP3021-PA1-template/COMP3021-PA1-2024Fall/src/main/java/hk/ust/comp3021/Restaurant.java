package hk.ust.comp3021;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Restaurant extends Account{
    String district;
    String street;
    List<Dish> dishes = new ArrayList<>();
    public Restaurant(Long id, String accountType, String name, String contactNumber, Location location, String district, String street) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.location = location;
        this.district = district;
        this.street = street;
        this.accountType = "RESTAURANT";
    }
    public void register(){
        Account.accountManager.addRestaurant(this);
    }
    public void addDish(Dish d){
        dishes.add(d);
    }
    public Restaurant getRestaurantById(Long l){
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

    public boolean hasDish(Long dishId) {
        for(Dish d : dishes){
            if (d.getId().equals(dishId)) return true;
        }
        return false;
    }
}
