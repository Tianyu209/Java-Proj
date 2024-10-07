package hk.ust.comp3021;

public class Rider extends Account {
    String gender;
    Integer status;
    Double userRating;
    Integer monthTaskCount;
    public Rider(Long id,String accountType, String name, String contactNumber, Location location, String gender,Integer status,Double userRating,Integer monthTaskCount){
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.location = location;
        this.gender = gender;
        this.status = status;
        this.userRating = userRating;
        this.monthTaskCount = monthTaskCount;
        this.accountType = "RIDER";
    }

    public Integer getStatus() {
        return status;
    }

    public void register(){
    Account.accountManager.addRider(this);
    }
    Rider getRiderById(long l){
        return Account.accountManager.getRiderById(l);
    }
    /// Do not modify this method.
    @Override
    public String toString() {
        return "Rider{" +
                "id=" + id +
                ", accountType='" + accountType + '\'' +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", location=" + location +
                ", gender='" + gender + '\'' +
                ", status=" + status +
                ", userRating=" + userRating +
                ", monthTaskCount=" + monthTaskCount +
                '}';
    }
}
