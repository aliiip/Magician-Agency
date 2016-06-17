
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class Booking {

    //General booking done by customer
    public static void addBooking(String holiday, String customer, String magician, Timestamp currentTimestamp) {

        if(currentTimestamp == null)
            currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());

        if (magician == null) {
            WaitingList.addToWaitingList(holiday, customer, currentTimestamp);
            JOptionPane.showMessageDialog(null, "No magicians available. You have been added to the waiting list.");
        } else {
            try {
                PreparedStatement setBooking = Connect.getConnection().prepareStatement("INSERT INTO Bookings (customer, holiday, magician, timestamp) VALUES (?,?,?,?)");
                setBooking.setString(1, customer);
                setBooking.setString(2, holiday);
                setBooking.setString(3, magician);
                setBooking.setTimestamp(4, currentTimestamp);
                setBooking.executeUpdate();
                JOptionPane.showMessageDialog(null, "Booking successful!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    //Sorts the bookings based on magician
    public static ArrayList<BookingEntry> getBookingsByMagician(String magician) {

        ArrayList<BookingEntry> allBookings = new ArrayList<>();

        try {
            PreparedStatement statement = Connect.getConnection().prepareStatement("SELECT * FROM Bookings WHERE magician = ?");
            statement.setString(1, magician);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BookingEntry next = new BookingEntry(rs.getString("Customer"), rs.getString("Holiday"), rs.getString("Magician"), rs.getTimestamp("Timestamp"));
                allBookings.add(next);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return allBookings;
    }

    //Sorts the bookings based on holiday
    public static ArrayList<BookingEntry> getBookingsByHoliday(String holiday) {

        ArrayList<BookingEntry> allBookings = new ArrayList<>();

        try {
            PreparedStatement statement = Connect.getConnection().prepareStatement("SELECT * FROM Bookings WHERE holiday = ?");
            statement.setString(1, holiday);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BookingEntry next = new BookingEntry(rs.getString("Customer"), rs.getString("Holiday"), rs.getString("Magician"), rs.getTimestamp("Timestamp"));
                allBookings.add(next);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return allBookings;
    }

    //Returns all bookings
    public static ArrayList<BookingEntry> getAllBookings() {

        ArrayList<BookingEntry> allBookings = new ArrayList<>();

        try {
            PreparedStatement statement = Connect.getConnection().prepareStatement("SELECT * FROM Bookings");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                BookingEntry next = new BookingEntry(rs.getString("Customer"), rs.getString("Holiday"), rs.getString("Magician"), rs.getTimestamp("Timestamp"));
                allBookings.add(next);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return allBookings;
    }

    //Cancels a booking based on the customer
    public static String cancelBooking(String customerName) {

        String mag = "";

        try {
            PreparedStatement ps = Connect.getConnection().prepareStatement("SELECT * FROM Bookings WHERE customer = ?");
            ps.setString(1, customerName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            mag = rs.getString("Magician");

            try {
                PreparedStatement statement = Connect.getConnection().prepareStatement("DELETE FROM Bookings WHERE customer = ?");
                statement.setString(1, customerName);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Booking cancelled.");

            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return mag;

    }

}
