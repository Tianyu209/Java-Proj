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

        private static List<Customer> registeredCustomers;

        private static List<Restaurant> registeredRestaurants;

        private static List<Rider> registeredRiders;

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
        public List<Rider> getRiders(){
            return registeredRiders;
        }
        public Account getAccountById(Long id) {
            for (Account account : registeredAccounts) {
                if (account.getId().equals(id)) {
                    return account;
                }
            }
            return null;
        }

        /// Hint: Do not forget to add the account to the registeredAccounts list.
        public void addCustomer(Customer customer) {
            registeredCustomers.add(customer);
            registeredAccounts.add(customer);
        }

        static public Customer getCustomerById(Long id) {
            for (Customer c : registeredCustomers){
                if(c.getId().equals(id)) return c;
            }
            return null;
        }

        /// Hint: Do not forget to add the account to the registeredAccounts list.
        public void addRestaurant(Restaurant restaurant) {
            registeredRestaurants.add(restaurant);
            registeredAccounts.add(restaurant);
        }

        static public Restaurant getRestaurantById(Long id) {
            for (Restaurant r : registeredRestaurants){
                if(r.getId().equals(id)) {
                    return r;
                }
            }
            return null;
        }

        /// Hint: Do not forget to add the account to the registeredAccounts list.
        public void addRider(Rider rider) {
            registeredRiders.add(rider);
            registeredAccounts.add(rider);
        }

        static public Rider getRiderById(Long id) {
            for(Rider r : registeredRiders){
                if (r.getId().equals(id)) return r;
            }
            return null;
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
