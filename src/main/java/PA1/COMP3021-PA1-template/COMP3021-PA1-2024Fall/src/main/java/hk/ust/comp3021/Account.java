public abstract class Account {

    protected Long id;

    protected String accountType;

    protected String name;

    protected String contactNumber;

    protected Location location;

    /// This is where the registered accounts are stored.
    @Data
    protected static class AccountManager {

        private List<Account> registeredAccounts;

        private List<Customer> registeredCustomers;

        private List<Restaurant> registeredRestaurants;

        private List<Rider> registeredRiders;

        public AccountManager() {
        }

        /// Do not modify this method.
        public List<Account> getRegisteredAccounts() {
            return registeredAccounts;
        }

        public Account getAccountById(Long id) {
        }

        /// Hint: Do not forget to add the account to the registeredAccounts list.
        public void addCustomer(Customer customer) {
        }

        public Customer getCustomerById(Long id) {
        }

        /// Hint: Do not forget to add the account to the registeredAccounts list.
        public void addRestaurant(Restaurant restaurant) {
        }

        public Restaurant getRestaurantById(Long id) {
        }

        /// Hint: Do not forget to add the account to the registeredAccounts list.
        public void addRider(Rider rider) {
        }

        public Rider getRiderById(Long id) {
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
