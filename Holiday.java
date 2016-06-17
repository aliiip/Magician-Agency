
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Holiday {

    //Returns all holidays in the Holiday table
    public static String[] getAllHolidays() {

        ArrayList<String> holidays = new ArrayList<>();

        try {
            ResultSet rs = Connect.getConnection().prepareStatement("SELECT * from Holidays").executeQuery();

            while (rs.next()) {
                holidays.add(rs.getString("holiday"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        String[] hol = new String[holidays.size()];

        for (int i = 0; i < holidays.size(); i++) {
            hol[i] = holidays.get(i);
        }

        return hol;
    }

    //Adds a holiday when prompted by the user
    public static void addHoliday(String holiday) {

        try {
            PreparedStatement statement = Connect.getConnection().prepareStatement("INSERT INTO Holidays(holiday) VALUES(?)");
            statement.setString(1, holiday);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Holiday successfully added.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Holiday already exists.");
        }
    }

}
