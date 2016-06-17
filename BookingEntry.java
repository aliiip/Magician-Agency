import java.sql.Timestamp;

public class BookingEntry {
    
    private final String customer;
    private final String holiday;
    private final String magician;
    private final Timestamp timestamp;
    
    //BookingEntry constructor
    public BookingEntry(String customer, String holiday, String magician, Timestamp timestamp){
        
        this.customer = customer;
        this.holiday = holiday;
        this.magician = magician;
        this.timestamp = timestamp;    
    }
    
    public String getCustomer() {
        return customer;
    }

    public String getHoliday() {
        return holiday;
    }

    public String getMagician() {
        return magician;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    
}
