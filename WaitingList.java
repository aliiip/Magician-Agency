
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class WaitingList {

    //Adds a user to the waitinglist if there are no available magicians
    public static void addToWaitingList(String holiday, String customer, Timestamp timestamp) {

        if(timestamp == null)
            timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

        try {
            PreparedStatement setList = Connect.getConnection().prepareStatement("INSERT INTO Waitlist(customer, holiday, timestamp) VALUES (?, ?, ?)");
            setList.setString(1, customer);
            setList.setString(2, holiday);
            setList.setTimestamp(3, timestamp);
            setList.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    //Returns the waitinglist
    public static ArrayList<WaitingListEntry> getWaitingList() {

        ArrayList<WaitingListEntry> waitList = new ArrayList<>();

        try {
            ResultSet rs = Connect.getConnection().prepareStatement("SELECT * FROM Waitlist ORDER BY timestamp").executeQuery();

            while (rs.next()) {
                WaitingListEntry next = new WaitingListEntry(rs.getString("Holiday"), rs.getString("Customer"), rs.getTimestamp("Timestamp"));
                waitList.add(next);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return waitList;
    }

    //Cancels a waitinglist entry when prompted by the user
    public static void cancelWaitingList(String customerName) {

        try {
            PreparedStatement statement = Connect.getConnection().prepareStatement("DELETE FROM WaitList WHERE customer = ?");
            statement.setString(1, customerName);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Waitlist booking cancelled.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

    //Checks if there is a user on the waitlist whenever a magician becomes available
    public static void checkWaitingList(String magician) {

        try {
            ResultSet rs = Connect.getConnection().prepareStatement("SELECT * FROM Waitlist ORDER BY timestamp").executeQuery();

            if (rs.next()) {
                Booking.addBooking(rs.getString("holiday"), rs.getString("customer"), magician, rs.getTimestamp("timestamp"));
                PreparedStatement ps = Connect.getConnection().prepareStatement("DELETE FROM Waitlist WHERE customer = ?");
                ps.setString(1, rs.getString("customer"));
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

}
