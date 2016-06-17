import java.sql.Timestamp;

public class WaitingListEntry {
    
    private final String holiday;
    private final String customer;
    private final Timestamp timestamp;
    
    //WaitingListEntry constructor
    WaitingListEntry(String holiday, String customer, Timestamp timestamp){
        this.holiday = holiday;
        this.customer = customer;
        this.timestamp = timestamp;
    }
    
    public String getHoliday() {
        return holiday;
    }

    public String getCustomer() {
        return customer;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    
}
