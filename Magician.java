
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Magician {

    //Returns all magicians from the Magicians table
    public static String[] getAllMagicians() {

        ArrayList<String> magicians = new ArrayList<>();

        try {
            ResultSet rs = Connect.getConnection().prepareStatement("SELECT * from Magicians").executeQuery();

            while (rs.next()) {
                magicians.add(rs.getString("magicianName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        String[] mag = new String[magicians.size()];

        for (int i = 0; i < magicians.size(); i++) {
            mag[i] = magicians.get(i);
        }

        return mag;
    }

    //Returns a magician that isn't already booked
    public static String getFreeMagician() {

        String magician;

        try {
            PreparedStatement ps = Connect.getConnection().prepareStatement("SELECT magicianName FROM Magicians WHERE magicianName NOT IN (SELECT magician FROM Bookings)");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                magician = rs.getString("magicianName");
            } else {
                magician = null;
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            magician = null;
        }

        return magician;
    }

    //Adds a magician when prompted by the user
    public static void addMagician(String magician) {

        try {
            PreparedStatement statement = Connect.getConnection().prepareStatement("INSERT INTO Magicians(magicianName) VALUES(?)");
            statement.setString(1, magician);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Magician successfully added.");
            WaitingList.checkWaitingList(magician);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Magician already exists.");
        }

    }

    //Removes a magician when prompted by the user
    //If a user was booked by that magician, they are reassigned an available magician
    //If there are no available magicians, they are added to the top of the waitlist
    public static void removeMagician(String magician) {
        
        try {

            ArrayList<BookingEntry> removeMags = new ArrayList<>();
            removeMags = Booking.getBookingsByMagician(magician);

            for (BookingEntry e : removeMags) {
                Booking.cancelBooking(e.getCustomer());
                WaitingList.addToWaitingList(e.getHoliday(), e.getCustomer(), e.getTimestamp());
                JOptionPane.showMessageDialog(null, "No magicians available. Customer has been added to the waitlist.");
                magicianJFrame.waitingListStatus();
            }

            PreparedStatement statement = Connect.getConnection().prepareStatement("DELETE FROM Magicians WHERE magicianName = ?");
            statement.setString(1, magician);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Magician successfully removed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    
    

}
