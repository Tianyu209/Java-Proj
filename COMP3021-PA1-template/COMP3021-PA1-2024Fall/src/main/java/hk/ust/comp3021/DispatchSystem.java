package hk.ust.comp3021;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static hk.ust.comp3021.Constants.PENDING_ORDER;
import static hk.ust.comp3021.Constants.RIDER_ONLINE_ORDER;

public class DispatchSystem {

    /// The singleton you will use in the project.
    private static volatile DispatchSystem dispatchSystem;

    /// The field represents the current time stamp, we assume the current time stamp is 3600 seconds.
    private Long currentTimestamp = 3600L;

    /// The list stores the dishes parsed from the file.
    private List<Dish> availableDishes;

    /// The list stores the orders parsed from the file.
    private List<Order> availableOrders;

    /// The list stores the orders that is dispatched this time, and the orders should have a non-null rider field and calculated estimated time.
    private List<Order> dispatchedOrders;

    /// Task 1: Implement the constructor of the singleton pattern for the DispatchSystem class.
    /// Hint: Check if the dispatchSystem is null or not null, skip it when not null. Initialize the fields.

    private DispatchSystem() {
        if(dispatchSystem == null) return;
        availableDishes = new ArrayList<>();
        availableOrders = new ArrayList<>();
        dispatchedOrders = new ArrayList<>();
    }

    /// Task 1: Implement the getInstance() method for the singleton pattern.
    /// Hint: Check if the dispatchSystem is null or not null and create a new instance here.
    public static DispatchSystem getInstance() {
        if (dispatchSystem == null) {
            synchronized (DispatchSystem.class) {
                if (dispatchSystem == null) dispatchSystem = new DispatchSystem();
            }
        }
        return dispatchSystem;
    }

    public Dish getDishById(Long id) {
        return availableDishes.get(Math.toIntExact(id));
    }

    public Boolean checkDishesInRestaurant(Restaurant restaurant, Long[] dishIds) {
        int valid =0;
        for (Dish d : restaurant.dishes){
            if (d.getId().equals(dishIds[valid])) valid++;
        }
        return valid == dishIds.length;
    }

    /// Task 2: Implement the parseAccounts() method to parse the accounts from the file.
    /// Hint: Do not forget to register the accounts into the static manager.
    public void parseAccounts(String fileName) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            // Read the file and parse the accounts.
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length < 2) {
                    throw new IOException("The account file is not well formatted!");
                }

                for (int i = 0; i < fields.length; i++) {
                    fields[i] = fields[i].trim();
                }

                String accountType = fields[1];
                Long id = Long.parseLong(fields[0]);
                fields[4] = fields[4].substring(1, fields[4].length() - 1);
                String[] parts = fields[4].split(" ");
                List<Double> arr = new ArrayList<>();
                for (String part : parts) {
                    arr.add(Double.parseDouble(part));
                }
                Location location = new Location(arr);
                switch (accountType) {
                    case "CUSTOMER" -> Account.accountManager.addCustomer(new Customer(id,fields[1],fields[2],fields[3],location,Integer.parseInt(fields[5]),fields[6],fields[7]));
                    case "RIDER" -> Account.accountManager.addRider(new Rider(id,fields[1],fields[2],fields[3],location,fields[5],Integer.parseInt(fields[6]),Double.parseDouble(fields[7]),Integer.parseInt(fields[8])));
                    case "RESTAURANT" -> Account.accountManager.addRestaurant(new Restaurant(id,fields[1],fields[2],fields[3],location,fields[5],fields[6]));
                }
                // TODO.

            }
        }
    }

    /// Task 3: Implement the parseDishes() method to parse the dishes from the file.
    /// Hint: Do not forget to add the dishes to the corresponding restaurant and the availableDishes list.
    public void parseDishes(String fileName) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            // Read the file and parse the dishes.
            String line;
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                String[] fields = line.split(",");
                if (fields.length < 2) {
                    throw new IOException("The dish file is not well formatted!");
                }

                for (int i = 0; i < fields.length; i++) {
                    fields[i] = fields[i].trim();
                }
                Long id = Long.parseLong(fields[0]);
                BigDecimal price = new BigDecimal(fields[3]);
                Long Rid = Long.parseLong(fields[4]);
                // create the dish and add to the account
                Dish d = new Dish(id,fields[1],fields[2],price,Rid);
                availableDishes.add(d);
                //add the dish to the restaurant
                Account.accountManager.getRestaurantById(Rid).addDish(d);
                // TODO.

            }
        }
    }

    /// Task 4: Implement the parseOrders() method to parse the orders from the file.
    /// Hint: Do not forget to add the order to the availableOrders list and check if the dishes ordered are in the same restaurant, skip the record if not. You can use getDishById(), checkDishesInRestaurant(), and etc. here.
    public void parseOrders(String fileName) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            // Read the file and parse the orders.
            String line;
            while ((line = bufferedReader.readLine()) != null && !line.isEmpty()) {
                String[] fields = line.split(",");
                if (fields.length < 2) {
                    throw new IOException("The order file is not well formatted!");
                }

                for (int i = 0; i < fields.length; i++) {
                    fields[i] = fields[i].trim();
                }
                Long id = Long.parseLong(fields[0]);
                Integer status = Integer.parseInt(fields[1]);
                Long Rid = Long.parseLong(fields[2]);
                Long Cid = Long.parseLong(fields[3]);
                Long Ct = Long.parseLong(fields[4]);
                Boolean ispayed = Boolean.parseBoolean(fields[5]);

                fields[6] = fields[6].substring(1, fields[6].length() - 1);
                String[] parts = fields[6].split(" ");
                Long [] arr = new Long[parts.length];
                int i=0;
                for (String part : parts) {
                    arr[i] = Long.parseLong(part);
                    i++;
                }
                List<Dish> dishes = new ArrayList<>();
                for(Long l:arr){
                    dishes.add(getDishById(l));
                }
                checkDishesInRestaurant(Account.accountManager.getRestaurantById(Rid),arr );
                Rider rider;
                if(fields[7].equals("NA"))  rider = null;
                else rider = Account.accountManager.getRiderById(Long.parseLong(fields[7]));
                // TODO.
                Order o = new Order(id,status,Account.accountManager.getRestaurantById(Rid),Account.accountManager.getCustomerById(Cid),Ct,ispayed,dishes,rider);
                availableOrders.add(o);
                

            }
        }
    }

    /// Task 5: Implement the getAvailablePendingOrders() method to get the available pending orders.
    /// Hint: The available pending orders should have the status of PENDING_ORDER, is payed, and the rider is null.
    public List<Order> getAvailablePendingOrders() {
        List<Order> res = new ArrayList<>();
        for (Order o : availableOrders){
            if (o.getStatus().equals(PENDING_ORDER) && o.getIsPayed() && o.getRider() == null) res.add(o);
        }
        return res;
    }

    /// Task 6: Implement the getRankedPendingOrders() method to rank the pending orders.
    /// Hint: Use the comparators you defined before, and sort the pending orders in order of the customer type (Top priority), order creation time (Second priority), and restaurant to customer distance (Least priority).
    public List<Order> getRankedPendingOrders(List<Order> pendingOrders) {
        pendingOrders.sort((o1, o2) -> {
            int customerPriority = new CustomerPriorityRank().compare(o1, o2);
            if (customerPriority != 0) return customerPriority;
            int createTimePriority = new OrderCreateTimeRank().compare(o1, o2);
            if (createTimePriority != 0) return createTimePriority;
            return new RestaurantToCustomerDistanceRank().compare(o1, o2);
        });
        return pendingOrders;

    }

    /// Task 7: Implement the getAvailableRiders() method to get the available riders to dispatch.
    /// Hint: The available riders should have the status of RIDER_ONLINE_ORDER.
    public List<Rider> getAvailableRiders() {
        List<Rider>res = new ArrayList<>();
        for (Rider r : Account.accountManager.getRiders()){
            if(Objects.equals(r.getStatus(), RIDER_ONLINE_ORDER)) res.add(r);
        }
        return res;
    }

    /// Task 8: Implement the matchTheBestTask() method to choose the best rider for the order.
    /// Hint: The best rider should have the highest rank ranked in order of the distance between the rider and the restaurant (Top priority), the rider's user rating (Second priority), and the rider's month task count (Least priority).
    /// Use the comparators you defined before, you will also use the Task class here and the availableRiders here should be the currently available riders.
    public Task matchTheBestTask(Order order, List<Rider> availableRiders) {

        for (Rider r : availableRiders){
            r.location.distanceTo(order.getRestaurant().location);
        }
        return null;
    }

    /// Task 9: Implement the dispatchFirstRound() method to dispatch the first round of orders.
    /// Hint: The strategy is that we assign the best rider to the orders ranked one by one until the orders or riders list is empty.
    /// Do not forget to 1. remove the dispatched rider every iteration, 2. change the status of the order and the rider after the order is dispatched, and 3. calculate the estimated time for the order.
    public void dispatchFirstRound() {
    }

    /// Do not modify the method. You should use the method to output orders for us to check the correctness of your implementation.
    public void writeOrders(String fileName, List<Order> orders) throws IOException {
        List<Order> orderedOrders = orders.stream().sorted(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getId().compareTo(o2.getId());
            }
        }).toList();

        // Write the dispatched orders to the file.
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (Order order : orderedOrders) {
                bufferedWriter.write(order.getId() + ", " + order.getStatus() + ", " + order.getRestaurant() + ", "
                        + order.getCustomer() + ", " + order.getCreateTime() + ", " + order.getIsPayed() + ", " +
                        order.getOrderedDishes() + ", " + order.getRider() + ", " + String.format("%.4f", order.getEstimatedTime()) + "\n");
            }
        }
    }

    /// Do not modify the method.
    public void writeAccounts(String fileName, List<Account> accounts) throws IOException {
        List<Account> orderedAccounts = accounts.stream().sorted(new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {
                return o1.getId().compareTo(o2.getId());
            }
        }).toList();

        // Write the dispatched orders to the file.
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (Account account : orderedAccounts) {
                bufferedWriter.write(account.toString() + "\n");
            }
        }
    }

    /// Do not modify the method.
    public void writeDishes(String fileName, List<Dish> dishes) throws IOException {
        List<Dish> orderedDishes = dishes.stream().sorted(new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return o1.getId().compareTo(o2.getId());
            }
        }).toList();

        // Write the dispatched orders to the file.
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (Dish dish : orderedDishes) {
                bufferedWriter.write(dish.getId() + ", " + dish.getName() + ", " + dish.getDesc() + ", "
                        + dish.getPrice() + ", " + dish.getRestaurantId() + "\n");
            }
        }
    }

    /// Task 10: Implement the getTimeoutDispatchedOrders() method to get the timeout dispatched orders.
    /// Hint: Do not forget to take the current time stamp into consideration.
    public List<Order> getTimeoutDispatchedOrders() {
        return null;
    }

    /// Do not modify the method.
    public List<Order> getAvailableOrders() {
        return availableOrders;
    }

    /// Do not modify the method.
    public List<Order> getDispatchedOrders() {
        return dispatchedOrders;
    }

    /// Do not modify the method.
    public List<Account> getAccounts() {
        Account.AccountManager manager = Account.getAccountManager();
        return manager.getRegisteredAccounts();
    }

    /// Do not modify the method.
    public List<Dish> getDishes() {
        return availableDishes;
    }

    /// Finish the main method to test your implementation.a
    public static void main(String[] args) {
        DispatchSystem system = new DispatchSystem();
        try {
            system.parseAccounts("Accounts.txt");
            system.parseDishes("Dishes.txt");
            system.parseOrders("Orders.txt");
            system.writeOrders("availableOrders.txt", system.availableOrders);

            system.dispatchFirstRound();

            system.writeOrders("firstRoundDispatchedOrders.txt", system.dispatchedOrders);
            List<Order> timeoutOrders = system.getTimeoutDispatchedOrders();

            system.writeOrders("timeoutDispatchedOrders.txt", timeoutOrders);

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

}
