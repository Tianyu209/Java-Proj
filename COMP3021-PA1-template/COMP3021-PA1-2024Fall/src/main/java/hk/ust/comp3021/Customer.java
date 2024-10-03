package hk.ust.comp3021;


public class Customer extends Account{
    public Integer customerType;
    String gender;
    String email;
    public Customer(Long id,String accountType, String name, String contactNumber, Location location, Integer customerType, String gender, String email){
        this.id = id;
        this.accountType = accountType;
        this.contactNumber = contactNumber;
        this.name = name;
        this.location = location;
        this.customerType = customerType;
        this.gender = gender;
        this.email = email;
    }
    public void register(){
        Account.accountManager.addCustomer(this);

    }
    public Account getCustomerById(Long l){
        return Account.getAccountById(l);
    }
    /// Do not modify this method.
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", accountType='" + accountType + '\'' +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", location=" + location +
                ", customerType=" + customerType +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
