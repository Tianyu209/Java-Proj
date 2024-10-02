package hk.ust.comp3021;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    protected Long id;

    protected String accountType;

    protected String name;

    protected String contactNumber;

    protected Location location;

    public Long getId() {
        return id;
    }

    /// This is where the registered accounts are stored.
    @Data
    protected static class AccountManager {

        private List<Account> registeredAccounts;

        private List<Customer> registeredCustomers;

        private List<Restaurant> registeredRestaurants;

        private List<Rider> registeredRiders;

        public AccountManager() {
            registeredAccounts = new ArrayList<>();
            registeredCustomers = new ArrayList<>();
            registeredRestaurants = new ArrayList<>();
            registeredRiders = new ArrayList<>();
        }

        /// Do not modify this method.
        public List<Account> getRegisteredAccounts() {
            return registeredAccounts;
        }

        public Account getAccountById(Long id) {
            return registeredAccounts.get(Math.toIntExact(id));
        }

        /// Hint: Do not forget to add the account to the registeredAccounts list.
        public void addCustomer(Customer customer) {
            registeredCustomers.add(customer);
            registeredAccounts.add(customer);
        }

        public Customer getCustomerById(Long id) {
            return registeredCustomers.get(id.intValue());
        }

        /// Hint: Do not forget to add the account to the registeredAccounts list.
        public void addRestaurant(Restaurant restaurant) {
            registeredRestaurants.add(restaurant);
            registeredAccounts.add(restaurant);
        }

        public Restaurant getRestaurantById(Long id) {
            return registeredRestaurants.get(id.intValue());
        }

        /// Hint: Do not forget to add the account to the registeredAccounts list.
        public void addRider(Rider rider) {
            registeredRiders.add(rider);
            registeredAccounts.add(rider);
        }

        public Rider getRiderById(Long id) {
            return registeredRiders.get(id.intValue());
        }

    }

    protected static AccountManager accountManager = new AccountManager();

    /// Task 2: Implement the register method.
    public abstract void register();

    public static Account getAccountById(Long id) {
        return accountManager.getAccountById(id);
    }

    /// Do not modify this method.
    public static AccountManager getAccountManager() {
        return accountManager;
    }

}
